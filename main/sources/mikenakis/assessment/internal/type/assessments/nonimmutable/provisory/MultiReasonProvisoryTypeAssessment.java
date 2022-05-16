package mikenakis.assessment.internal.type.assessments.nonimmutable.provisory;

import mikenakis.assessment.internal.helpers.Helpers;
import mikenakis.assessment.internal.assessments.Assessment;

import java.util.List;

/**
 * Signifies that a class is provisory due to multiple reasons.
 */
public final class MultiReasonProvisoryTypeAssessment extends ProvisoryTypeAssessment
{
	public final List<ProvisoryTypeAssessment> provisoryReasons;

	public MultiReasonProvisoryTypeAssessment( Class<?> jvmClass, List<ProvisoryTypeAssessment> provisoryReasons )
	{
		super( jvmClass );
		assert Helpers.isClass( jvmClass );
		this.provisoryReasons = provisoryReasons;
	}

	@Override public List<Assessment> children() { return List.copyOf( provisoryReasons ); }
}
