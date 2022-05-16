package io.github.mikenakis.bathyscaphe.internal.type.field.assessments.provisory;

import io.github.mikenakis.bathyscaphe.internal.assessments.Assessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.NonImmutableFieldAssessment;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Signifies that a field is provisory because it is of a field type which is provisory.
 */
public final class ProvisoryFieldTypeProvisoryFieldAssessment extends NonImmutableFieldAssessment
{
	public final ProvisoryTypeAssessment fieldTypeAssessment;

	public ProvisoryFieldTypeProvisoryFieldAssessment( Field field, ProvisoryTypeAssessment fieldTypeAssessment )
	{
		super( field );
		assert fieldTypeAssessment.type == field.getType();
		this.fieldTypeAssessment = fieldTypeAssessment;
	}

	@Override public List<Assessment> children() { return List.of( fieldTypeAssessment ); }
}
