/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type;

import io.github.mikenakis.bathyscaphe.internal.helpers.ConcreteMapEntry;
import io.github.mikenakis.bathyscaphe.internal.mykit.collections.ConvertingIterable;
import io.github.mikenakis.bathyscaphe.internal.mykit.MyKit;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.ImmutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.TypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.CompositeProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ExtensibleProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisoryTypeAssessment;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * Adds default preassessments to a {@link TypeAssessor}
 *
 * @author michael.gr
 */
final class DefaultPreassessments
{
	static void apply( TypeAssessor assessor )
	{
		addDefaultImmutablePreassessment( assessor, Class.class ); //contains caching
		addDefaultImmutablePreassessment( assessor, String.class ); //contains caching
		addDefaultExtensiblePreassessment( assessor, BigDecimal.class, true ); //is extensible, contains caching, and has a problematic 'precision' field; nonetheless, people say it is immutable; who am I to disagree.
		addDefaultImmutablePreassessment( assessor, Method.class ); //contains caching
		addDefaultImmutablePreassessment( assessor, Constructor.class ); //contains caching
		addDefaultImmutablePreassessment( assessor, URI.class ); //has mutable fields, although it is guaranteed to remain constant.
		addDefaultImmutablePreassessment( assessor, URL.class ); //has mutable fields, although it is guaranteed to remain constant.
		addDefaultImmutablePreassessment( assessor, Locale.class ); //has mutable fields, although it is guaranteed to remain constant.
		addDefaultExtensiblePreassessment( assessor, BigInteger.class, true ); //is extensible, contains mutable fields.
		addDefaultImmutablePreassessment( assessor, StackTraceElement.class );
		addDefaultImmutablePreassessment( assessor, File.class );
		addDefaultExtensiblePreassessment( assessor, InetAddress.class, true ); //is extensible, contains mutable fields.
		addDefaultImmutablePreassessment( assessor, Inet4Address.class );
		addDefaultImmutablePreassessment( assessor, Inet6Address.class );
		addDefaultImmutablePreassessment( assessor, InetSocketAddress.class );
		addDefaultIterablePreassessment( assessor, MyKit.getClass( List.of() ), true );
		addDefaultIterablePreassessment( assessor, MyKit.getClass( List.of( 1 ) ), true );
		//addDefaultIterablePreassessment( assessor, MyKit.uncheckedClassCast( ConvertingIterable.class ) );
		addSupperficiallyImmutableJdkMap( assessor, Map.of() );
		addSupperficiallyImmutableJdkMap( assessor, Map.of( "", "" ) );
		addDefaultCompositePreassessment( assessor, MyKit.uncheckedClassCast( Optional.class ), true, OptionalDecomposer.instance );
		addDefaultImmutablePreassessment( assessor, ZonedDateTime.class );
	}

	private static void addDefaultExtensiblePreassessment( TypeAssessor assessor, Class<?> jvmClass, boolean threadSafe )
	{
		assert !(new TypeAssessor().assess( jvmClass ) instanceof ExtensibleProvisoryTypeAssessment);
		ExtensibleProvisoryTypeAssessment assessment = new ExtensibleProvisoryTypeAssessment( TypeAssessment.Mode.PreassessedByDefault, jvmClass, threadSafe );
		assessor.addDefaultPreassessment( jvmClass, assessment );
	}

	private static void addDefaultImmutablePreassessment( TypeAssessor assessor, Class<?> jvmClass )
	{
		assert !(new TypeAssessor().assess( jvmClass ) instanceof ImmutableTypeAssessment);
		assessor.addDefaultPreassessment( jvmClass, ImmutableTypeAssessment.instance );
	}

	private static <T extends Iterable<E>,E> void addDefaultIterablePreassessment( TypeAssessor assessor, Class<T> jvmClass, boolean threadSafe )
	{
		assert !(new TypeAssessor().assess( jvmClass ) instanceof CompositeProvisoryTypeAssessment);
		Decomposer<T,E> decomposer = getIterableDecomposer();
		ProvisoryTypeAssessment objectAssessment = (ProvisoryTypeAssessment)assessor.assess( Object.class );
		var assessment = new CompositeProvisoryTypeAssessment<>( TypeAssessment.Mode.PreassessedByDefault, jvmClass, threadSafe, objectAssessment, decomposer );
		assessor.addDefaultPreassessment( jvmClass, assessment );
	}

	private static final Decomposer<? extends Iterable<Object>,Object> iterableDecomposer = new Decomposer<>()
	{
		@Override public Iterable<Object> decompose( Iterable<Object> object )
		{
			return object;
		}
	};

	private static <T extends Iterable<E>,E> Decomposer<T,E> getIterableDecomposer()
	{
		@SuppressWarnings( "unchecked" ) Decomposer<T,E> result = (Decomposer<T,E>)iterableDecomposer;
		return result;
	}

	private static <T, E> void addDefaultCompositePreassessment( TypeAssessor assessor, Class<T> compositeType, boolean threadSafe, Decomposer<T,E> decomposer )
	{
		ProvisoryTypeAssessment componentTypeAssessment = (ProvisoryTypeAssessment)assessor.assess( Object.class );
		var assessment = new CompositeProvisoryTypeAssessment<>( TypeAssessment.Mode.PreassessedByDefault, compositeType, threadSafe, componentTypeAssessment, decomposer );
		assessor.addDefaultPreassessment( compositeType, assessment );
	}

	/**
	 * PEARL: by default, the assessment of superficially-immutable jdk map is actually very mutable, because it extends {@link java.util.AbstractMap} which is
	 *        mutable, because (get a load of this!) its 'keySet' and 'values' fields are non-final!
	 */
	private static <K, V> void addSupperficiallyImmutableJdkMap( TypeAssessor assessor, Map<K,V> superficiallyImmutableJdkMap )
	{
		Class<Map<K,V>> mapClass = MyKit.getClass( superficiallyImmutableJdkMap );
		Decomposer<Map<K,V>,ConcreteMapEntry<K,V>> decomposer = getSuperficiallyImmutableJdkMapDecomposer();
		addDefaultCompositePreassessment( assessor, mapClass, true, decomposer );
	}

	private static class SuperficiallyImmutableJdkMapDecomposer<K, V> implements Decomposer<Map<K,V>,ConcreteMapEntry<K,V>>
	{
		@Override public Iterable<ConcreteMapEntry<K,V>> decompose( Map<K,V> map )
		{
			/**
			 * PEARL: the decomposer for the superficially-immutable jdk map cannot just return {@link Map#entrySet()} because the entry-set is a nested class,
			 * so it has a 'this$0' field, which points back to the map, which is mutable, thus making the entry-set mutable!
			 * So, the decomposer has to iterate the entry-set and yield each element in it.
			 * PEARL: the decomposer cannot just yield each element yielded by the entry-set, because these are instances of {@link java.util.KeyValueHolder}
			 * which cannot be reflected because it is inaccessible! Therefore, the decomposer has to convert each element to a proximal key-value class which
			 * is accessible.
			 */
			Iterable<Map.Entry<K,V>> entrySet = map.entrySet();
			if( MyKit.areAssertionsEnabled() )
				entrySet = MyKit.streamFromIterable( entrySet ).sorted( DefaultPreassessments::compareMapEntries ).toList();
			return new ConvertingIterable<>( entrySet, DefaultPreassessments::mapEntryConverter );
		}
	}

	private static int compareMapEntries( Map.Entry<?,?> entry1, Map.Entry<?,?> entry2 )
	{
		String a1 = entry1.getKey().toString();
		String a2 = entry2.getKey().toString();
		return a1.compareTo( a2 );
	}

	private static final SuperficiallyImmutableJdkMapDecomposer<Object,Object> superficiallyImmutableJdkMapDeconstructorInstance = new SuperficiallyImmutableJdkMapDecomposer<>();

	private static <K, V> Decomposer<Map<K,V>,ConcreteMapEntry<K,V>> getSuperficiallyImmutableJdkMapDecomposer()
	{
		@SuppressWarnings( "unchecked" ) Decomposer<Map<K,V>,ConcreteMapEntry<K,V>> result = (SuperficiallyImmutableJdkMapDecomposer<K,V>)superficiallyImmutableJdkMapDeconstructorInstance;
		return result;
	}

	private static <K, V> ConcreteMapEntry<K,V> mapEntryConverter( Map.Entry<K,V> mapEntry )
	{
		return new ConcreteMapEntry<>( mapEntry.getKey(), mapEntry.getValue() );
	}

	private static class OptionalDecomposer implements Decomposer<Optional<?>,Object>
	{
		public static Decomposer<Optional<?>,Object> instance = new OptionalDecomposer();

		private OptionalDecomposer()
		{
		}

		@Override public Iterable<Object> decompose( Optional<?> optional )
		{
			if( optional.isEmpty() )
				return List.of();
			return List.of( optional.get() );
		}
	}
}
