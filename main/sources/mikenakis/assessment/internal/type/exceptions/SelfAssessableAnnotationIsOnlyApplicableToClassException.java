package mikenakis.assessment.internal.type.exceptions;

import mikenakis.assessment.internal.helpers.Helpers;
import mikenakis.assessment.ImmutabilitySelfAssessable;
import mikenakis.assessment.internal.mykit.UncheckedException;

/**
 * Thrown if the {@link ImmutabilitySelfAssessable} annotation is placed on an interface.
 *
 * @author michael.gr
 */
public class SelfAssessableAnnotationIsOnlyApplicableToClassException extends UncheckedException
{
	public final Class<?> jvmClass;

	public SelfAssessableAnnotationIsOnlyApplicableToClassException( Class<?> jvmClass )
	{
		assert !Helpers.isClass( jvmClass );
		this.jvmClass = jvmClass;
	}
}
