/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.test;

import io.github.mikenakis.bathyscaphe.Bathyscaphe;
import io.github.mikenakis.bathyscaphe.ObjectMustBeThreadSafeException;
import io.github.mikenakis.bathyscaphe.internal.mykit.MyKit;
import org.junit.Test;

import javax.swing.KeyStroke;
import java.io.File;
import java.lang.reflect.InaccessibleObjectException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;
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
import java.util.regex.Pattern;

/**
 * Test.
 *
 * @author michael.gr
 */
public class T21_FamousThreadSafeObjects
{
	public T21_FamousThreadSafeObjects()
	{
		if( !MyKit.areAssertionsEnabled() )
			throw new AssertionError();
	}

	private static Inet4Address getInet4Address()
	{
		return MyTestKit.unchecked( () -> (Inet4Address)InetAddress.getByAddress( new byte[4] ) );
	}

	private static Inet6Address getInet6Address()
	{
		return MyTestKit.unchecked( () -> (Inet6Address)InetAddress.getByAddress( new byte[16] ) );
	}

	private static URL getUrl()
	{
		return MyTestKit.unchecked( () -> URI.create( "file:///" ).toURL() );
	}

	private static StackTraceElement getStackTraceElement()
	{
		try
		{
			throw new RuntimeException();
		}
		catch( RuntimeException e )
		{
			return e.getStackTrace()[0];
		}
	}

	@Test public void famous_immutable_objects_are_threadSafe()
	{
		List<Object> objects = List.of( new Object(), getClass(), false, 'c', (byte)1, (short)1, 1, 1L, 1.0f, 1.0, "", //
			Instant.EPOCH, Duration.ZERO, UUID.randomUUID(), LocalDate.EPOCH, LocalDateTime.now(), LocalTime.MIDNIGHT, MonthDay.now(), OffsetDateTime.now(), //
			OffsetTime.now(), Period.ZERO, Year.now(), YearMonth.now(), ZoneOffset.UTC, ZonedDateTime.now(), //
			new BigDecimal( 1 ), new BigInteger( "1" ), getInet4Address(), getInet6Address(), InetSocketAddress.createUnresolved( "", 0 ), //
			getClass().getDeclaredMethods()[0], getClass().getConstructors()[0], URI.create( "file:///" ), getUrl(), Locale.ROOT, //
			getStackTraceElement(), File.listRoots()[0] );
		for( Object object : objects )
			assert Bathyscaphe.objectMustBeThreadSafeAssertion( object );
	}

	@Test public void famous_threadSafe_objects_are_threadSafe()
	{
		List<Object> objects = List.of( new ConcurrentHashMap<>(), new ConcurrentLinkedDeque<>(), new ConcurrentLinkedQueue<>(), new ConcurrentSkipListMap<>(), //
			new ConcurrentSkipListSet<>(), new CopyOnWriteArrayList<>(), new CopyOnWriteArraySet<>(), new CountDownLatch( 1 ), new CyclicBarrier( 1 ), //
			new DelayQueue<>(), new Exchanger<>(), new LinkedBlockingDeque<>(), new LinkedBlockingQueue<>(), new LinkedTransferQueue<>(), new Phaser(), //
			new PriorityBlockingQueue<>(), new Semaphore( 1 ), new SynchronousQueue<>() );
		for( Object object : objects )
			assert Bathyscaphe.objectMustBeThreadSafeAssertion( object );
	}

	@Test public void optional_is_threadSafe_or_nonThreadSafe_depending_on_payload()
	{
		Bathyscaphe.objectMustBeThreadSafeAssertion( Optional.empty() );
		Bathyscaphe.objectMustBeThreadSafeAssertion( Optional.of( 1 ) );
		MyTestKit.expect( ObjectMustBeThreadSafeException.class, () -> Bathyscaphe.objectMustBeThreadSafeAssertion( Optional.of( new StringBuilder() ) ) );
	}

	@Test public void certain_classes_are_messed_up()
	{
		/* PEARL: ZoneId.systemDefault() returns an instance of ZoneRegion, which is mutable! And since ZoneRegion is jdk-internal, we cannot preassess it! */
		MyTestKit.expect( ObjectMustBeThreadSafeException.class, () -> Bathyscaphe.objectMustBeThreadSafeAssertion( ZoneId.systemDefault() ) );

		/* PEARL: Clock.systemUTC() returns an instance of SystemClock, which is inaccessible, so we cannot assess it! */
		MyTestKit.expect( InaccessibleObjectException.class, () -> Bathyscaphe.objectMustBeThreadSafeAssertion( Clock.systemUTC() ) );
	}

	@Test public void famous_nonThreadSafe_objects_are_nonThreadSafe()
	{
		List<Object> objects = List.of( new ArrayList<>(), new HashMap<>(), new Date(), new HashSet<>(), new LinkedList<>(), new Properties(), //
			new Random(), Pattern.compile( "" ).matcher( "" ), new StringBuilder(), new LinkedHashMap<>(), new LinkedHashSet<>(), //
			new SimpleDateFormat( "", Locale.ROOT ), new StringTokenizer( "" ), KeyStroke.getKeyStroke( 'c' ) );
		for( Object object : objects )
			MyTestKit.expect( ObjectMustBeThreadSafeException.class, () -> Bathyscaphe.objectMustBeThreadSafeAssertion( object ) );
	}
}
