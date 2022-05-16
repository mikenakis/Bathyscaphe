package io.github.mikenakis.bathyscaphe.exceptions;

import io.github.mikenakis.bathyscaphe.internal.assessments.MutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.mykit.UncheckedException;

/**
 * Thrown when an object is expected to be immutable, but it is not.
 *
 * @author michael.gr
 */
public final class ObjectMustBeImmutableException extends UncheckedException
{
	public final MutableObjectAssessment mutableObjectAssessment;

	public ObjectMustBeImmutableException( MutableObjectAssessment mutableObjectAssessment )
	{
		this.mutableObjectAssessment = mutableObjectAssessment;
	}
}
