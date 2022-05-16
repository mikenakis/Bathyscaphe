package io.github.mikenakis.bathyscaphe.internal.assessments;

/**
 * Signifies that an object is immutable.
 */
public final class ImmutableObjectAssessment extends ObjectAssessment
{
	public static final ImmutableObjectAssessment instance = new ImmutableObjectAssessment();

	private ImmutableObjectAssessment()
	{
	}
}
