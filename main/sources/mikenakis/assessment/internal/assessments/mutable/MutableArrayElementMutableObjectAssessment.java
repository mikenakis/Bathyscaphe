package mikenakis.assessment.internal.assessments.mutable;

import mikenakis.assessment.internal.assessments.Assessment;
import mikenakis.assessment.internal.assessments.MutableObjectAssessment;
import mikenakis.assessment.internal.type.assessments.nonimmutable.NonImmutableTypeAssessment;
import mikenakis.assessment.internal.type.assessments.nonimmutable.provisory.ProvisoryTypeAssessment;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Signifies that an array is mutable because it has at least one element which is mutable.
 */
public final class MutableArrayElementMutableObjectAssessment extends MutableObjectAssessment
{
	public final Object object;
	public final ProvisoryTypeAssessment typeAssessment;
	public final int elementIndex;
	public final MutableObjectAssessment elementAssessment;

	public MutableArrayElementMutableObjectAssessment( Object object, ProvisoryTypeAssessment typeAssessment, int elementIndex, MutableObjectAssessment elementAssessment )
	{
		assert object.getClass().isArray();
		assert Array.get( object, elementIndex ) == elementAssessment.object();
		assert typeAssessment.type == object.getClass();
		this.object = object;
		this.typeAssessment = typeAssessment;
		this.elementIndex = elementIndex;
		this.elementAssessment = elementAssessment;
	}

	@Override public Object object() { return object; }
	@Override public NonImmutableTypeAssessment typeAssessment() { return typeAssessment; }
	@Override public List<Assessment> children() { return List.of( elementAssessment ); }
}
