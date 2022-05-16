package mikenakis.bathyscaphe.internal.type.assessments;

/**
 * Signifies that a type is currently under assessment.  Used internally, should never be seen by the user.
 */
public final class UnderAssessmentTypeAssessment extends TypeAssessment
{
	public static final UnderAssessmentTypeAssessment instance = new UnderAssessmentTypeAssessment();

	private UnderAssessmentTypeAssessment() { }
}
