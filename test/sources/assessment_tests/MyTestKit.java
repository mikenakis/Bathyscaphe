package assessment_tests;

import mikenakis.debug.Debug;

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
}
