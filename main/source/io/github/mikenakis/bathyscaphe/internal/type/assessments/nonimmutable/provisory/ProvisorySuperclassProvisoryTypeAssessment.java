/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory;

import io.github.mikenakis.bathyscaphe.internal.assessments.Assessment;

import java.util.List;

/**
 * Signifies that a class is provisory because it has a provisory superclass.
 *
 * @author michael.gr
 */
public final class ProvisorySuperclassProvisoryTypeAssessment extends ProvisoryTypeAssessment
{
	public final ProvisoryTypeAssessment superclassAssessment;

	public ProvisorySuperclassProvisoryTypeAssessment( Class<?> jvmClass, boolean threadSafe, ProvisoryTypeAssessment superclassAssessment )
	{
		super( jvmClass, threadSafe );
		assert jvmClass.getSuperclass() == superclassAssessment.type;
		this.superclassAssessment = superclassAssessment;
	}

	@Override public List<Assessment> children() { return List.of( superclassAssessment ); }

	@Override public boolean isThreadSafe() { return threadSafe || superclassAssessment.isThreadSafe(); }
}
