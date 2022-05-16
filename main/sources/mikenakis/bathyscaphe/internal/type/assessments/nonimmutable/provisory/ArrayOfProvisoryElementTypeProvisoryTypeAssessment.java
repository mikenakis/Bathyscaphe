package mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory;

import mikenakis.bathyscaphe.internal.assessments.Assessment;

import java.util.List;

/**
 * Signifies that an array is provisory because it is of provisory element type.
 */
public class ArrayOfProvisoryElementTypeProvisoryTypeAssessment extends ProvisoryTypeAssessment
{
	public final ProvisoryTypeAssessment elementTypeAssessment;

	public ArrayOfProvisoryElementTypeProvisoryTypeAssessment( Class<?> jvmClass, ProvisoryTypeAssessment elementTypeAssessment )
	{
		super( jvmClass );
		assert jvmClass.isArray();
		assert jvmClass.getComponentType() == elementTypeAssessment.type;
		this.elementTypeAssessment = elementTypeAssessment;
	}

	@Override public List<Assessment> children() { return List.of( elementTypeAssessment ); }
}
