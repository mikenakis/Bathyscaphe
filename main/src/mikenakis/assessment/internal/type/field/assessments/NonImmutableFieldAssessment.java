package mikenakis.assessment.internal.type.field.assessments;

import java.lang.reflect.Field;

public abstract class NonImmutableFieldAssessment extends FieldAssessment
{
	public final Field field;

	protected NonImmutableFieldAssessment( Field field ) { this.field = field; }
}
