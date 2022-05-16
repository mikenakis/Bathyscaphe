package mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable;

import mikenakis.bathyscaphe.internal.assessments.Assessment;

import java.util.List;

/**
 * Signifies that an array is mutable because it is of mutable element type.
 */
public class ArrayOfMutableElementTypeMutableTypeAssessment extends MutableTypeAssessment
{
	public final MutableTypeAssessment elementTypeAssessment;

	public ArrayOfMutableElementTypeMutableTypeAssessment( Class<?> jvmClass, MutableTypeAssessment elementTypeAssessment )
	{
		super( jvmClass );
		assert jvmClass.isArray();
		assert jvmClass.getComponentType() == elementTypeAssessment.type;
		this.elementTypeAssessment = elementTypeAssessment;
	}

	@Override public List<Assessment> children() { return List.of( elementTypeAssessment ); }
}
