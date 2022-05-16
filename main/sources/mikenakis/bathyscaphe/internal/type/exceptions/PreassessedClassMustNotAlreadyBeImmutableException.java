package mikenakis.bathyscaphe.internal.type.exceptions;

import mikenakis.bathyscaphe.internal.mykit.UncheckedException;

/**
 * Thrown when a class is preassessed as immutable, but it is found to already be immutable.
 * An already immutable class need not, and should not, be preassessed as immutable.
 *
 * @author michael.gr
 */
public class PreassessedClassMustNotAlreadyBeImmutableException extends UncheckedException
{
	public final Class<?> jvmClass;

	public PreassessedClassMustNotAlreadyBeImmutableException( Class<?> jvmClass )
	{
		this.jvmClass = jvmClass;
	}
}
