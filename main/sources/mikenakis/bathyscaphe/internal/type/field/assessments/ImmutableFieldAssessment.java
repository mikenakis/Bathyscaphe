package mikenakis.bathyscaphe.internal.type.field.assessments;

/**
 * Signifies that a field is immutable.
 */
public final class ImmutableFieldAssessment extends FieldAssessment
{
	public static final ImmutableFieldAssessment instance = new ImmutableFieldAssessment();

	private ImmutableFieldAssessment() { }
}
