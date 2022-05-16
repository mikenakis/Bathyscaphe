package io.github.mikenakis.bathyscaphe.internal.type.exceptions;

import io.github.mikenakis.bathyscaphe.internal.helpers.Helpers;
import io.github.mikenakis.bathyscaphe.ImmutabilitySelfAssessable;
import io.github.mikenakis.bathyscaphe.internal.mykit.UncheckedException;

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
