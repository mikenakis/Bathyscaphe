package io.github.mikenakis.bathyscaphe.internal.assessments.mutable;

import io.github.mikenakis.bathyscaphe.internal.assessments.Assessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.MutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.NonImmutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.CompositeProvisoryTypeAssessment;

import java.util.List;

/**
 * Signifies that an object is mutable because it is a composite of which at least one component is mutable.
 */
public final class MutableComponentMutableObjectAssessment<T, E> extends MutableObjectAssessment
{
	public final T compositeObject;
	public final CompositeProvisoryTypeAssessment<T,E> typeAssessment;
	public final int elementIndex;
	public final MutableObjectAssessment elementAssessment;

	public MutableComponentMutableObjectAssessment( T compositeObject, CompositeProvisoryTypeAssessment<T,E> typeAssessment, //
		int elementIndex, MutableObjectAssessment elementAssessment )
	{
		assert compositeObject.getClass() == typeAssessment.type;
		this.compositeObject = compositeObject;
		this.typeAssessment = typeAssessment;
		this.elementIndex = elementIndex;
		this.elementAssessment = elementAssessment;
	}

	@Override public Object object() { return compositeObject; }
	@Override public NonImmutableTypeAssessment typeAssessment() { return typeAssessment; }
	@Override public List<Assessment> children() { return List.of( typeAssessment, elementAssessment ); }
}
