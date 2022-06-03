/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory;

import io.github.mikenakis.bathyscaphe.internal.assessments.Assessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.nonimmutable.provisory.ProvisoryFieldTypeProvisoryFieldAssessment;

import java.util.List;

/**
 * Signifies that a class is provisory because it contains a provisory field.
 *
 * @author michael.gr
 */
public final class ProvisoryFieldProvisoryTypeAssessment extends ProvisoryTypeAssessment
{
	public final ProvisoryFieldTypeProvisoryFieldAssessment fieldAssessment;

	public ProvisoryFieldProvisoryTypeAssessment( Class<?> jvmClass, boolean threadSafe, ProvisoryFieldTypeProvisoryFieldAssessment fieldAssessment )
	{
		super( jvmClass, threadSafe );
		assert fieldAssessment.field.getDeclaringClass() == jvmClass;
		this.fieldAssessment = fieldAssessment;
	}

	@Override public List<Assessment> children() { return List.of( fieldAssessment ); }

	@Override public boolean isThreadSafe()
	{
		return threadSafe || fieldAssessment.threadSafe;
	}
}
