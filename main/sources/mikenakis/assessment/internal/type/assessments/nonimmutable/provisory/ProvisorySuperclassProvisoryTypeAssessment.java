package mikenakis.assessment.internal.type.assessments.nonimmutable.provisory;

import mikenakis.assessment.internal.assessments.Assessment;

import java.util.List;

/**
 * Signifies that a class has a provisory superclass.
 */
public final class ProvisorySuperclassProvisoryTypeAssessment extends ProvisoryTypeAssessment
{
	public final ProvisoryTypeAssessment superclassAssessment;

	public ProvisorySuperclassProvisoryTypeAssessment( Class<?> jvmClass, ProvisoryTypeAssessment superclassAssessment )
	{
		super( jvmClass );
		assert jvmClass.getSuperclass() == superclassAssessment.type;
		this.superclassAssessment = superclassAssessment;
	}

	@Override public List<Assessment> children() { return List.of( superclassAssessment ); }
}
