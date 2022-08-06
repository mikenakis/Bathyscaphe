/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.mykit;

import java.lang.reflect.Field;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Useful static utility methods.
 *
 * @author michael.gr
 */
public final class MyKit
{
	private MyKit() { }

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Assertions & Debugging

	//PEARL: this has been observed to return false even though assertions are enabled, when invoked from a static context, e.g. main()
	public static boolean areAssertionsEnabled()
	{
		boolean b = false;
		//noinspection AssertWithSideEffects
		assert b = true;
		//noinspection ConstantConditions
		return b;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Object-related and miscellaneous.

	/**
	 * Casts a {@link Class} of any type to a {@link Class} of a given type
	 *
	 * @param jvmClass the {@link Class} to cast
	 * @param <T>      the type of the {@link Class}
	 *
	 * @return the {@link Class} cast to the requested type.
	 */
	public static <T> Class<T> uncheckedClassCast( @SuppressWarnings( "rawtypes" ) Class jvmClass )
	{
		@SuppressWarnings( "unchecked" ) Class<T> result = (Class<T>)jvmClass;
		return result;
	}

	/**
	 * Gets the class of an object as a generic class parametrized with the type of that object.
	 * <p>
	 * Useful because {@link Object#getClass()} returns a generic class parametrized with a wildcard, not with the actual type of the object on which getClass()
	 * was called.
	 *
	 * @param object the object whose class is to be obtained.
	 * @param <T>    the type of the object.
	 *
	 * @return the class of the object parametrized with the type of the object.
	 */
	public static <T> Class<T> getClass( T object )
	{
		return uncheckedClassCast( object.getClass() );
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Reflection stuff

	public static Object getFieldValue( Object object, Field field )
	{
		if( !field.canAccess( object ) ) //TODO: assess whether performing this check saves any time (as opposed to always invoking setAccessible without the check.)
			field.setAccessible( true );
		Object fieldValue;
		try
		{
			fieldValue = field.get( object );
		}
		catch( IllegalAccessException e )
		{
			throw new RuntimeException( e );
		}
		return fieldValue;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// StringBuilder stuff

	public static void appendEscapedForJava( StringBuilder stringBuilder, String s, char quote )
	{
		if( s == null )
		{
			stringBuilder.append( "null" );
			return;
		}
		stringBuilder.append( quote );
		for( char c : s.toCharArray() )
		{
			if( c == '"' )
				stringBuilder.append( "\\\"" );
			else if( c == '\r' )
				stringBuilder.append( "\\r" );
			else if( c == '\n' )
				stringBuilder.append( "\\n" );
			else if( c == '\t' )
				stringBuilder.append( "\\t" );
			else if( c < 32 )
				stringBuilder.append( String.format( "\\x%02x", (int)c ) );
			else if( !Character.isDefined( c ) )
				stringBuilder.append( String.format( "\\u%04x", (int)c ) );
			else
				stringBuilder.append( c );
		}
		stringBuilder.append( quote );
	}

	/**
	 * Appends the string representation of an {@link Object} to a {@link StringBuilder}. The difference between this function and {@link
	 * StringBuilder#append(Object)} is that this function treats {@link String} and {@link Character} differently: they are escaped and surrounded with
	 * quotes.
	 *
	 * @param stringBuilder the StringBuilder to append to.
	 * @param object        the object whose string representation is to be appended to the StringBuilder.
	 */
	public static void append( StringBuilder stringBuilder, Object object )
	{
		//IntellijIdea blooper: good code red: Currently, (August 2022) IntellijIdea does not know anything about JDK 19, and it is not smart enough to
		//figure out that feature-wise it must be a superset of the last JDK that it knows, which is JDK 17.
		//As a result, it marks the following code with "Patterns in switch are not supported at language level '19'", which is just plain wrong.
		switch( object )
		{
			case String s -> appendEscapedForJava( stringBuilder, s, '"' );
			case Character c -> appendEscapedForJava( stringBuilder, String.valueOf( c ), '\'' );
			default -> stringBuilder.append( object );
		}
	}
	/**
	 * Gets the string representation of an {@link Object}.
	 *
	 * @param object the object whose string representation is requested.
	 */
	public static String stringFromObject( Object object )
	{
		StringBuilder stringBuilder = new StringBuilder();
		append( stringBuilder, object );
		return stringBuilder.toString();
	}

	/***
	 * Obtains a {@link Stream} from an {@link Iterable}.
	 * Because Java makes it awfully difficult, whereas it should have been so easy as to not even require a cast. (Ideally, Stream would extend
	 * Iterable. I know, it can't. But ideally, it would.)
	 */
	public static <T> Stream<T> streamFromIterable( Iterable<T> iterable )
	{
		return StreamSupport.stream( iterable.spliterator(), false );
	}
}
