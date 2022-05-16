package io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory;

import io.github.mikenakis.bathyscaphe.internal.assessments.Assessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.provisory.ProvisoryFieldTypeProvisoryFieldAssessment;

import java.util.List;

/**
 * Signifies that a class is provisory because it contains a provisory field.
 */
public final class ProvisoryFieldProvisoryTypeAssessment extends ProvisoryTypeAssessment
{
	public final ProvisoryFieldTypeProvisoryFieldAssessment fieldAssessment;

	public ProvisoryFieldProvisoryTypeAssessment( Class<?> jvmClass, ProvisoryFieldTypeProvisoryFieldAssessment fieldAssessment )
	{
		super( jvmClass );
		assert fieldAssessment.field.getDeclaringClass() == jvmClass;
		this.fieldAssessment = fieldAssessment;
	}

	@Override public List<Assessment> children() { return List.of( fieldAssessment ); }
}
