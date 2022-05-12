package mikenakis.assessment.internal.assessments;

import mikenakis.assessment.internal.type.assessments.nonimmutable.NonImmutableTypeAssessment;

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
