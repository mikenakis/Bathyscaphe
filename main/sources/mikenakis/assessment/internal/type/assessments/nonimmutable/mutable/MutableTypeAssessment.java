package mikenakis.assessment.internal.type.assessments.nonimmutable.mutable;

import mikenakis.assessment.internal.type.assessments.nonimmutable.NonImmutableTypeAssessment;

/**
 * Signifies that a class is mutable.
 */
public abstract class MutableTypeAssessment extends NonImmutableTypeAssessment
{
	protected MutableTypeAssessment( Class<?> type ) { super( type ); }
}
