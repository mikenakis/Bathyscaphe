package mikenakis.assessment.internal.type.exceptions;

import mikenakis.assessment.internal.helpers.Helpers;
import mikenakis.assessment.ImmutabilitySelfAssessable;
import mikenakis.assessment.internal.mykit.UncheckedException;

/**
 * Thrown when a class implements {@link ImmutabilitySelfAssessable} but the class is already immutable.
 *
 * @author michael.gr
 */
public class SelfAssessableClassMustNotBeImmutableException extends UncheckedException
{
	public final Class<?> jvmClass;

	public SelfAssessableClassMustNotBeImmutableException( Class<?> jvmClass )
	{
		assert !Helpers.isExtensible( jvmClass );
		this.jvmClass = jvmClass;
	}
}
