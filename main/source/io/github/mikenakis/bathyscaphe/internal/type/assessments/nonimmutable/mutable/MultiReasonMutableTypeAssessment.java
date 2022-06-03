/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable;

import io.github.mikenakis.bathyscaphe.internal.assessments.Assessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.NonImmutableTypeAssessment;

import java.util.List;

/**
 * Signifies that a class is mutable due to multiple reasons.
 *
 * @author michael.gr
 */
public class MultiReasonMutableTypeAssessment extends MutableTypeAssessment
{
	public final List<NonImmutableTypeAssessment> reasons;

	public MultiReasonMutableTypeAssessment( Class<?> jvmClass, boolean threadSafe, List<NonImmutableTypeAssessment> reasons )
	{
		super( jvmClass, threadSafe );
		this.reasons = reasons;
	}

	@Override public List<Assessment> children() { return List.copyOf( reasons ); }
	@Override public boolean isThreadSafe() { return threadSafe && reasons.stream().allMatch( reason -> reason.isThreadSafe() ); }
}
