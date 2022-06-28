/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory;

import io.github.mikenakis.bathyscaphe.internal.assessments.Assessment;
import io.github.mikenakis.bathyscaphe.internal.helpers.Helpers;

import java.util.List;

/**
 * Signifies that a class is provisory due to multiple reasons.
 *
 * @author michael.gr
 */
public final class MultiReasonProvisoryTypeAssessment extends ProvisoryTypeAssessment
{
	public final List<ProvisoryTypeAssessment> reasons;

	public MultiReasonProvisoryTypeAssessment( Class<?> jvmClass, boolean threadSafe, List<ProvisoryTypeAssessment> reasons )
	{
		super( jvmClass, threadSafe );
		assert Helpers.isClass( jvmClass );
		this.reasons = reasons;
	}

	@Override public List<Assessment> children() { return List.copyOf( reasons ); }
	@Override public boolean isThreadSafe() { return threadSafe || reasons.stream().allMatch( reason -> reason.isThreadSafe() ); }
}
