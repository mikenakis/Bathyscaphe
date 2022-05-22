/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
 */

package io.github.mikenakis.bathyscaphe.internal.mykit;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Base class for unchecked exceptions. (Which are the only kind of exceptions that I use by my own free will.)
 * Disallows the setting of a human-readable message, builds the human-readable message using reflection.
 * Disallows overriding getMessage() and toString() to prevent misuse.
 *
 * @author michael.gr
 */
public class UncheckedException extends RuntimeException
{
	public UncheckedException()
	{
	}

	public UncheckedException( Optional<? extends Throwable> cause )
	{
		super( cause.orElse( null ) );
	}

	public UncheckedException( Throwable cause )
	{
		super( cause );
	}

	@Override public final String getMessage()
	{
		//NOTE: we are not adding the message of the base class because we have not set a value to it, so super.getMessage() would return the message of the
		// cause exception, which is useless, since there will be a "caused by" message anyway.
		return Arrays.stream( getClass().getFields() ) //
			.filter( field -> UncheckedException.class.isAssignableFrom( field.getDeclaringClass() ) ) //
			.map( field -> field.getName() + "=" + stringFromFieldValue( field ) ) //
			.collect( Collectors.joining( "; " ) );
	}

	private String stringFromFieldValue( Field field )
	{
		Object value = fieldValue( field );
		return MyKit.stringFromObject( value );
	}

	private Object fieldValue( Field field )
	{
		try
		{
			return field.get( this );
		}
		catch( IllegalAccessException ignore )
		{
			return "<inaccessible>";
		}
	}

	@SuppressWarnings( "EmptyMethod" ) @Override public final String toString()
	{
		return super.toString();
	}
}
