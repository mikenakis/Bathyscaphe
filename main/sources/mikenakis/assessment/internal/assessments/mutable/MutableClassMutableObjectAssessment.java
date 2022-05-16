package mikenakis.assessment.internal.assessments.mutable;

import mikenakis.assessment.internal.assessments.Assessment;
import mikenakis.assessment.internal.assessments.MutableObjectAssessment;
import mikenakis.assessment.internal.type.assessments.nonimmutable.NonImmutableTypeAssessment;
import mikenakis.assessment.internal.type.assessments.nonimmutable.mutable.MutableTypeAssessment;

import java.util.List;

/**
 * Signifies that an object is mutable because its class is mutable.
 */
public final class MutableClassMutableObjectAssessment extends MutableObjectAssessment
{
	public final Object object;
	public final MutableTypeAssessment typeAssessment;

	public MutableClassMutableObjectAssessment( Object object, MutableTypeAssessment typeAssessment )
	{
		assert object.getClass() == typeAssessment.type;
		this.object = object;
		this.typeAssessment = typeAssessment;
	}

	@Override public Object object() { return object; }
	@Override public NonImmutableTypeAssessment typeAssessment() { return typeAssessment; }
	@Override public List<Assessment> children() { return List.of( typeAssessment ); }
}
