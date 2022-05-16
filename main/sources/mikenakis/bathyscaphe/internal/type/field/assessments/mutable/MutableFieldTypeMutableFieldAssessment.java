package mikenakis.bathyscaphe.internal.type.field.assessments.mutable;

import mikenakis.bathyscaphe.internal.assessments.Assessment;
import mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.MutableTypeAssessment;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Signifies that a field is mutable because even though it is invariable, it is of a mutable type.
 */
public final class MutableFieldTypeMutableFieldAssessment extends MutableFieldAssessment
{
	public final MutableTypeAssessment fieldTypeAssessment;

	public MutableFieldTypeMutableFieldAssessment( Field field, MutableTypeAssessment fieldTypeAssessment )
	{
		super( field );
		assert field.getType() == fieldTypeAssessment.type;
		this.fieldTypeAssessment = fieldTypeAssessment;
	}

	@Override public List<Assessment> children() { return List.of( fieldTypeAssessment ); }
}
