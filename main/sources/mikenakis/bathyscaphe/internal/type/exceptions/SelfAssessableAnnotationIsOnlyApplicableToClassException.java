package mikenakis.bathyscaphe.internal.type.exceptions;

import mikenakis.bathyscaphe.internal.helpers.Helpers;
import mikenakis.bathyscaphe.ImmutabilitySelfAssessable;
import mikenakis.bathyscaphe.internal.mykit.UncheckedException;

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
