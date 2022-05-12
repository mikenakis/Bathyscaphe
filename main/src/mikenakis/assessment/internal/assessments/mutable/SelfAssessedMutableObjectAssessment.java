package mikenakis.assessment.internal.assessments.mutable;

import mikenakis.assessment.internal.type.assessments.nonimmutable.NonImmutableTypeAssessment;
import mikenakis.assessment.internal.type.assessments.nonimmutable.provisory.SelfAssessableProvisoryTypeAssessment;
import mikenakis.assessment.ImmutabilitySelfAssessable;
import mikenakis.assessment.internal.assessments.Assessment;
import mikenakis.assessment.internal.assessments.MutableObjectAssessment;

import java.util.List;

/**
 * Signifies that a self-assessable object has assessed itself as mutable.
 */
public final class SelfAssessedMutableObjectAssessment extends MutableObjectAssessment
{
	public final ImmutabilitySelfAssessable object;
	public final SelfAssessableProvisoryTypeAssessment typeAssessment;

	public SelfAssessedMutableObjectAssessment( SelfAssessableProvisoryTypeAssessment typeAssessment, //
		ImmutabilitySelfAssessable object )
	{
		assert object.getClass() == typeAssessment.type;
		this.object = object;
		this.typeAssessment = typeAssessment;
	}

	@Override public Object object() { return object; }
	@Override public NonImmutableTypeAssessment typeAssessment() { return typeAssessment; }
	@Override public List<Assessment> children() { return List.of( typeAssessment ); }
}
