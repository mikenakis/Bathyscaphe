package io.github.mikenakis.bathyscaphe.internal.type.assessments;

import io.github.mikenakis.bathyscaphe.internal.assessments.Assessment;

public abstract class TypeAssessment extends Assessment
{
	public enum Mode
	{
		Assessed, //assessed by dona.
		Preassessed, //preassessment by the user, overriding a 'mutable' assessment that would have normally been given by dona.
		PreassessedByDefault //standard preassessment by dona, overriding a 'mutable' assessment that would have normally been given by dona.
	}

	protected TypeAssessment()
	{
	}
}
