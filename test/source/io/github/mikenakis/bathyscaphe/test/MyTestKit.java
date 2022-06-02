/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.test;

import io.github.mikenakis.debug.Debug;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Various static helper methods.
 *
 * @author michael.gr
 */
public final class MyTestKit
{
	private MyTestKit()
	{
	}

	public static <T extends Throwable> T expect( Class<T> expectedThrowableClass, Runnable procedure )
	{
		Throwable caughtThrowable = invokeAndCatch( procedure );
		assert caughtThrowable != null : expectedThrowableClass;
		caughtThrowable = unwrap( caughtThrowable );
		assert caughtThrowable.getClass() == expectedThrowableClass : caughtThrowable;
		return expectedThrowableClass.cast( caughtThrowable );
	}

	private static Throwable unwrap( Throwable throwable )
	{
		while( throwable instanceof AssertionError && throwable.getCause() != null )
			throwable = throwable.getCause();
		return throwable;
	}

	private static Throwable invokeAndCatch( Runnable procedure )
	{
		assert !Debug.expectingException;
		Debug.expectingException = true;
		try
		{
			procedure.run();
			return null;
		}
		catch( Throwable throwable )
		{
			return throwable;
		}
		finally
		{
			assert Debug.expectingException;
			Debug.expectingException = false;
		}
	}

	// PEARL: Windows has a stupid notion of a "current directory", which is a mutable global variable of process-wide scope.
	//        This means that any thread can modify it, and all other threads will be affected by the modification.
	//        (And in a DotNet process, any AppDomain can modify it, and all other AppDomains will be affected! So much for AppDomain isolation!)
	//        Java does not exactly have such a notion, but the "user.dir" system property (which you can get and set) is effectively the same.
	// NOTE:  When maven is running tests, the "user.dir" system property contains the root directory of the current module being tested.
	//        When testana is running tests, it sets the "user.dir" property accordingly.
	//        Thus, when running tests either via maven or via testana, we can obtain the path to the root directory of the current module.
	public static Path getWorkingDirectory()
	{
		return Paths.get( System.getProperty( "user.dir" ) ).toAbsolutePath().normalize();
	}

	/**
	 * A method which accepts no arguments, returns a value, and declares a checked exception.
	 *
	 * @param <R> the type of the return value.
	 * @param <E> the type of the checked exception that may be thrown.
	 *
	 * @author Mike Nakis (michael.gr)
	 */
	public interface ThrowingFunction0<R, E extends Exception>
	{
		R invoke() throws E;
	}

	/**
	 * <p>Invokes a given {@link ThrowingFunction0} and returns the result, converting the checked exception to unchecked.</p>
	 * <p>The conversion occurs at compilation time, so that:
	 * <ul>
	 *     <li>It does not incur any runtime overhead.</li>
	 *     <li>In the event that an exception is thrown, it does not prevent the debugger from stopping at the throwing statement.</li>
	 * </ul></p>
	 *
	 * @param throwingFunction the {@link ThrowingFunction0} to invoke.
	 * @param <E>              the type of checked exception declared by the {@link ThrowingFunction0}.
	 * @param <R>              the type of the result.
	 */
	public static <R, E extends Exception> R unchecked( ThrowingFunction0<R,E> throwingFunction )
	{
		@SuppressWarnings( "unchecked" ) ThrowingFunction0<R,RuntimeException> f = (ThrowingFunction0<R,RuntimeException>)throwingFunction;
		return f.invoke();
	}

	/**
	 * A method which accepts no arguments, does not return a value, and declares a checked exception.
	 *
	 * @param <E> the type of the checked exception that may be thrown.
	 *
	 * @author Mike Nakis (michael.gr)
	 */
	public interface ThrowingProcedure0<E extends Throwable>
	{
		void invoke() throws E;
	}

	/**
	 * <p>Invokes a given {@link ThrowingProcedure0} converting the checked exception to unchecked.</p>
	 * <p>The conversion occurs at compilation time, so that:
	 * <ul>
	 *     <li>It does not incur any runtime overhead.</li>
	 *     <li>In the event that an exception is thrown, it does not prevent the debugger from stopping at the throwing statement.</li>
	 * </ul></p>
	 *
	 * @param throwingProcedure the {@link ThrowingProcedure0} to invoke.
	 * @param <E>               the type of checked exception declared by the {@link ThrowingProcedure0}.
	 */
	public static <E extends Exception> void unchecked( ThrowingProcedure0<E> throwingProcedure )
	{
		@SuppressWarnings( "unchecked" ) ThrowingProcedure0<RuntimeException> p = (ThrowingProcedure0<RuntimeException>)throwingProcedure;
		p.invoke();
	}
}
