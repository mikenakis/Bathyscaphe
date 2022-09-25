package io.github.mikenakis.bathyscaphe.internal.mykit.functional;

/**
 * A method which accepts no arguments and returns a value.
 * (Corresponds to Java's {@link java.util.function.Supplier}.)
 *
 * @param <R> the type of the return value.
 *
 * @author Mike Nakis (michael.gr)
 */
public interface Function0<R>
{
	static <R> R invoke( Function0<R> function )
	{
		return function.invoke();
	}

	R invoke();
}
