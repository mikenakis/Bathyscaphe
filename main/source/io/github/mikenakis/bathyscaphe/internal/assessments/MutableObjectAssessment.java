package io.github.mikenakis.bathyscaphe.internal.assessments;

import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.NonImmutableTypeAssessment;

/**
 * Signifies that an object is mutable.
 */
public abstract class MutableObjectAssessment extends ObjectAssessment
{
	protected MutableObjectAssessment()
	{
	}

	public abstract Object object();
	public abstract NonImmutableTypeAssessment typeAssessment();
}
