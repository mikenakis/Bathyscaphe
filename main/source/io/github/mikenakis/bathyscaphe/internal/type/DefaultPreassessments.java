/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type;

import io.github.mikenakis.bathyscaphe.internal.mykit.MyKit;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.ImmutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.TypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.MutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.ThreadSafeMutableTypeAssessment;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Exchanger;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.Phaser;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

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
		addDefaultExtensiblePreassessment( assessor, BigDecimal.class ); //is extensible, contains caching, and has a problematic 'precision' field; nonetheless, people say it is immutable; who am I to disagree.
		addDefaultImmutablePreassessment( assessor, Method.class ); //contains caching
		addDefaultImmutablePreassessment( assessor, Constructor.class ); //contains caching
		addDefaultImmutablePreassessment( assessor, URI.class ); //has mutable fields, although it is guaranteed to remain constant.
		addDefaultImmutablePreassessment( assessor, URL.class ); //has mutable fields, although it is guaranteed to remain constant.
		addDefaultImmutablePreassessment( assessor, Locale.class ); //has mutable fields, although it is guaranteed to remain constant.
		addDefaultExtensiblePreassessment( assessor, BigInteger.class ); //is extensible, contains mutable fields.
		addDefaultImmutablePreassessment( assessor, StackTraceElement.class );
		addDefaultImmutablePreassessment( assessor, File.class );
		addDefaultExtensiblePreassessment( assessor, InetAddress.class ); //is extensible, contains mutable fields.
		addDefaultImmutablePreassessment( assessor, Inet4Address.class );
		addDefaultImmutablePreassessment( assessor, Inet6Address.class );
		addDefaultImmutablePreassessment( assessor, InetSocketAddress.class );
		addDefaultIterablePreassessment( assessor, MyKit.getClass( List.of() ) );
		addDefaultIterablePreassessment( assessor, MyKit.getClass( List.of( 1 ) ) );
		//addDefaultIterablePreassessment( assessor, MyKit.uncheckedClassCast( ConvertingIterable.class ) );
		/**
		 * PEARL: the default assessment of superficially-immutable jdk map is actually very mutable, because it extends {@link java.util.AbstractMap} which is
		 *        mutable, because (get a load of this!) its 'keySet' and 'values' fields are non-final!
		 */
		addDefaultCompositePreassessment( assessor, MyKit.getClass( Map.of() ), true, JdkMapDecomposer.instance() );
		addDefaultCompositePreassessment( assessor, MyKit.getClass( Map.of( "", "" ) ), true, JdkMapDecomposer.instance() );
		addDefaultCompositePreassessment( assessor, MyKit.uncheckedClassCast( Optional.class ), true, OptionalDecomposer.instance );
		addDefaultImmutablePreassessment( assessor, ZonedDateTime.class );

		addDefaultThreadSafePreassessment( assessor, ConcurrentHashMap.class );
		addDefaultThreadSafePreassessment( assessor, ConcurrentLinkedDeque.class );
		addDefaultThreadSafePreassessment( assessor, ConcurrentLinkedQueue.class );
		addDefaultThreadSafePreassessment( assessor, ConcurrentSkipListMap.class );
		addDefaultThreadSafePreassessment( assessor, ConcurrentSkipListSet.class );
		addDefaultThreadSafePreassessment( assessor, CopyOnWriteArrayList.class );
		addDefaultThreadSafePreassessment( assessor, CopyOnWriteArraySet.class );
		addDefaultThreadSafePreassessment( assessor, CountDownLatch.class );
		addDefaultThreadSafePreassessment( assessor, CyclicBarrier.class );
		addDefaultThreadSafePreassessment( assessor, DelayQueue.class );
		addDefaultThreadSafePreassessment( assessor, Exchanger.class );
		addDefaultThreadSafePreassessment( assessor, LinkedBlockingDeque.class );
		addDefaultThreadSafePreassessment( assessor, LinkedBlockingQueue.class );
		addDefaultThreadSafePreassessment( assessor, LinkedTransferQueue.class );
		addDefaultThreadSafePreassessment( assessor, Phaser.class );
		addDefaultThreadSafePreassessment( assessor, PriorityBlockingQueue.class );
		addDefaultThreadSafePreassessment( assessor, Semaphore.class );
		addDefaultThreadSafePreassessment( assessor, SynchronousQueue.class );
	}

	private static void addDefaultExtensiblePreassessment( TypeAssessor assessor, Class<?> jvmClass )
	{
		assert !(new TypeAssessor().assess( jvmClass ) instanceof ExtensibleProvisoryTypeAssessment);
		ExtensibleProvisoryTypeAssessment assessment = new ExtensibleProvisoryTypeAssessment( TypeAssessment.Mode.PreassessedByDefault, jvmClass, true );
		assessor.addDefaultPreassessment( jvmClass, assessment );
	}

	private static void addDefaultImmutablePreassessment( TypeAssessor assessor, Class<?> jvmClass )
	{
		assert !(new TypeAssessor().assess( jvmClass ) instanceof ImmutableTypeAssessment);
		assessor.addDefaultPreassessment( jvmClass, ImmutableTypeAssessment.instance );
	}

	private static void addDefaultThreadSafePreassessment( TypeAssessor assessor, Class<?> jvmClass )
	{
		assert !(new TypeAssessor().assess( jvmClass ) instanceof ImmutableTypeAssessment);
		assessor.addDefaultPreassessment( jvmClass, new ThreadSafeMutableTypeAssessment( jvmClass ) );
	}

	private static <T extends Iterable<E>, E> void addDefaultIterablePreassessment( TypeAssessor assessor, Class<T> jvmClass )
	{
		assert !(new TypeAssessor().assess( jvmClass ) instanceof CompositeProvisoryTypeAssessment);
		ProvisoryTypeAssessment objectAssessment = (ProvisoryTypeAssessment)assessor.assess( Object.class );
		var assessment = new CompositeProvisoryTypeAssessment<>( TypeAssessment.Mode.PreassessedByDefault, jvmClass, true, objectAssessment, IterableDecomposer.instance() );
		assessor.addDefaultPreassessment( jvmClass, assessment );
	}

	private static <T, E> void addDefaultCompositePreassessment( TypeAssessor assessor, Class<T> compositeType, boolean threadSafe, Decomposer<T,E> decomposer )
	{
		ProvisoryTypeAssessment componentTypeAssessment = (ProvisoryTypeAssessment)assessor.assess( Object.class );
		var assessment = new CompositeProvisoryTypeAssessment<>( TypeAssessment.Mode.PreassessedByDefault, compositeType, threadSafe, componentTypeAssessment, decomposer );
		assessor.addDefaultPreassessment( compositeType, assessment );
	}
}
