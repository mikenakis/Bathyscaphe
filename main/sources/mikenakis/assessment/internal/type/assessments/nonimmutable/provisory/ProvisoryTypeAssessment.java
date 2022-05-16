package mikenakis.assessment.internal.type.assessments.nonimmutable.provisory;

import mikenakis.assessment.internal.type.assessments.nonimmutable.NonImmutableTypeAssessment;

/**
 * Signifies that a class cannot be conclusively classified as either mutable or immutable, so further runtime checks are necessary on instances of this class.
 */
public abstract class ProvisoryTypeAssessment extends NonImmutableTypeAssessment
{
	protected ProvisoryTypeAssessment( Class<?> type ) { super( type ); }
}
