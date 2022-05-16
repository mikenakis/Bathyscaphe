package mikenakis.assessment.internal.type.field.assessments.mutable;

import mikenakis.assessment.internal.type.field.assessments.NonImmutableFieldAssessment;

import java.lang.reflect.Field;

/**
 * Signifies that a field is mutable.
 */
public abstract class MutableFieldAssessment extends NonImmutableFieldAssessment
{
	protected MutableFieldAssessment( Field field ) { super( field ); }
}
