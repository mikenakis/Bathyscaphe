/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable;

import io.github.mikenakis.bathyscaphe.internal.assessments.Assessment;

import java.util.List;

/**
 * Signifies that a class is mutable because its superclass is mutable.
 *
 * @author michael.gr
 */
public class MutableSuperclassMutableTypeAssessment extends MutableTypeAssessment
{
	public final MutableTypeAssessment superclassAssessment;

	public MutableSuperclassMutableTypeAssessment( Class<?> jvmClass, boolean threadSafe, MutableTypeAssessment superclassAssessment )
	{
		super( jvmClass, threadSafe );
		assert jvmClass.getSuperclass() == superclassAssessment.type;
		this.superclassAssessment = superclassAssessment;
	}

	@Override public List<Assessment> children() { return List.of( superclassAssessment ); }
	@Override public boolean isThreadSafe() { return threadSafe && superclassAssessment.isThreadSafe(); }
}
