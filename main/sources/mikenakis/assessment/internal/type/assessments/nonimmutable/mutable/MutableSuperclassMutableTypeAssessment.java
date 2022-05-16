package mikenakis.assessment.internal.type.assessments.nonimmutable.mutable;

import mikenakis.assessment.internal.assessments.Assessment;

import java.util.List;

/**
 * Signifies that a class is mutable because its superclass is mutable.
 */
public class MutableSuperclassMutableTypeAssessment extends MutableTypeAssessment
{
	public final MutableTypeAssessment superclassAssessment;

	public MutableSuperclassMutableTypeAssessment( Class<?> jvmClass, MutableTypeAssessment superclassAssessment )
	{
		super( jvmClass );
		assert jvmClass.getSuperclass() == superclassAssessment.type;
		this.superclassAssessment = superclassAssessment;
	}

	@Override public List<Assessment> children() { return List.of( superclassAssessment ); }
}
