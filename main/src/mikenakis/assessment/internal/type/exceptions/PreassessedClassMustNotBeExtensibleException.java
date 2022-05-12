package mikenakis.assessment.internal.type.exceptions;

import mikenakis.assessment.internal.mykit.UncheckedException;

/**
 * Thrown when an attempt is made to preassess a class as immutable, but the class is found to be extensible.
 * The class should be preassessed as extensible-provisory instead.
 *
 * @author michael.gr
 */
public class PreassessedClassMustNotBeExtensibleException extends UncheckedException
{
	public final Class<?> jvmClass;

	public PreassessedClassMustNotBeExtensibleException( Class<?> jvmClass )
	{
		this.jvmClass = jvmClass;
	}
}
