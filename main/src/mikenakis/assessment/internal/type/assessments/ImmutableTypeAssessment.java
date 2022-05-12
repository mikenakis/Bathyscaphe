package mikenakis.assessment.internal.type.assessments;

/**
 * Signifies that a type is immutable.
 */
public final class ImmutableTypeAssessment extends TypeAssessment
{
	public static final ImmutableTypeAssessment instance = new ImmutableTypeAssessment();

	private ImmutableTypeAssessment()
	{
	}
}
