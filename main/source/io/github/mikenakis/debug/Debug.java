package io.github.mikenakis.debug;

import java.util.function.Supplier;

/**
 * IMPORTANT NOTE: For the methods in this class to work, the debugger must be configured to stop not only on uncaught exceptions, but also on caught exceptions
 * if (and only if) they are caught within this class.
 */
public final class Debug
{
	/**
	 * Allows temporarily disabling the behavior of this class for the purpose of testing.
	 * <p>
	 * If a caller expects a certain piece of code-under-test to throw a certain exception, but the code-under-test is making use of this class to have the
	 * debugger stop at throwing statements, the caller can set this flag to {@code true} before invoking the code-under-test, so that the debugger will not
	 * stop at throwing statements, and exceptions will instead propagate up to the caller, so that they can be caught and examined.
	 * <p>
	 * Note: the {@link #breakPoint()} method is not affected by this flag and will always break.
	 */
	public static boolean expectingException;

	private Debug()
	{
	}

	/**
	 * Causes the debugger to break in this method, even if there exists a catch-all clause higher up the call tree.
	 */
	public static void breakPoint()
	{
		try
		{
			throw new RuntimeException();
		}
		catch( RuntimeException ignored )
		{
		}
	}

	/**
	 * Invokes the given {@link Runnable}, allowing the debugger to break on any exceptions that may be thrown within the {@link Runnable} and go uncaught by
	 * the {@link Runnable}. If the debugger is properly configured, (see class comment,) the debugger will break on any throwing statement within the call tree
	 * of the {@link Supplier} even if there exists a catch-all clause in the call tree above the call to this {@link #boundary(Runnable)}.
	 *
	 * @param procedure0 the {@link Runnable} to invoke.
	 */
	public static void boundary( Runnable procedure0 )
	{
		if( expectingException )
		{
			procedure0.run();
			return;
		}
		//noinspection CaughtExceptionImmediatelyRethrown
		try
		{
			procedure0.run();
		}
		catch( Throwable throwable )
		{
			throw throwable;
		}
	}

	/**
	 * Invokes a given {@link Supplier} and returns the result, allowing the debugger to break on any exceptions that may be thrown within the {@link Supplier}
	 * and go uncaught by the {@link Supplier}. If the debugger is properly configured, (see class comment,) the debugger will break on any throwing statement
	 * within the call tree of the {@link Supplier} even if there exists a catch-all clause in the call tree above the call to this {@link #boundary(Supplier)}
	 * method.
	 *
	 * @param function0 the {@link Supplier} to invoke.
	 * @param <T>       the type of the result returned by the {@link Supplier}.
	 *
	 * @return whatever the {@link Supplier} returned.
	 */
	public static <T> T boundary( Supplier<T> function0 )
	{
		if( expectingException )
			return function0.get();
		//noinspection CaughtExceptionImmediatelyRethrown
		try
		{
			return function0.get();
		}
		catch( Throwable throwable )
		{
			throw throwable;
		}
	}
}
