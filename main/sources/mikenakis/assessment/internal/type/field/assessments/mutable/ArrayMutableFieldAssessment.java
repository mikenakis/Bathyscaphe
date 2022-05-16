package mikenakis.assessment.internal.type.field.assessments.mutable;

import mikenakis.assessment.internal.type.field.FieldAssessor;
import mikenakis.assessment.annotations.InvariableArray;

import java.lang.reflect.Field;

/**
 * Signifies that an invariable field is mutable because it is an array field, and it has not been annotated with @{@link InvariableArray}. (So each array
 * element is still variable, regardless of the assessment of the element itself.)
 * <p>
 * Note: we could turn this into a provisory assessment because the array may still turn out to be immutable if it is of zero length, (if the array has no
 * elements, then there are no elements to vary,) but this would complicate things, and it would not help much, because it would not save us from having to
 * perform runtime checks, because we would still have to discover that the array has zero elements.
 */
public final class ArrayMutableFieldAssessment extends MutableFieldAssessment
{
	public ArrayMutableFieldAssessment( Field field )
	{
		super( field );
		assert field.getType().isArray();
		assert FieldAssessor.isInvariableField( field );
	}
}
