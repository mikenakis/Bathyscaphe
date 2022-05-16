package io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable;

import io.github.mikenakis.bathyscaphe.internal.assessments.Assessment;

import java.util.List;

/**
 * Signifies that a class is mutable due to multiple reasons.
 */
public class MultiReasonMutableTypeAssessment extends MutableTypeAssessment
{
	public final List<MutableTypeAssessment> mutableTypeAssessments;

	public MultiReasonMutableTypeAssessment( Class<?> jvmClass, List<MutableTypeAssessment> mutableTypeAssessments )
	{
		super( jvmClass );
		this.mutableTypeAssessments = mutableTypeAssessments;
	}

	@Override public List<Assessment> children() { return List.copyOf( mutableTypeAssessments ); }
}
