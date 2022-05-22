/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
 */

package bathyscaphe_tests;

import io.github.mikenakis.debug.Debug;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Various static helper methods.
 *
 * @author michael.gr
 */
final class MyTestKit
{
	private MyTestKit()
	{
	}

	static <T extends Throwable> T expect( Class<T> expectedThrowableClass, Runnable procedure )
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
}
