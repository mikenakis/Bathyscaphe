/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
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

	public MutableSuperclassMutableTypeAssessment( Class<?> jvmClass, MutableTypeAssessment superclassAssessment )
	{
		super( jvmClass );
		assert jvmClass.getSuperclass() == superclassAssessment.type;
		this.superclassAssessment = superclassAssessment;
	}

	@Override public List<Assessment> children() { return List.of( superclassAssessment ); }
}
