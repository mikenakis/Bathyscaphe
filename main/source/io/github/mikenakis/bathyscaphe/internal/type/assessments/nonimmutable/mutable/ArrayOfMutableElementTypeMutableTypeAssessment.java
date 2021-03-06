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
 * Signifies that an array is mutable because it is of mutable element type.
 *
 * @author michael.gr
 */
public class ArrayOfMutableElementTypeMutableTypeAssessment extends MutableTypeAssessment
{
	public final MutableTypeAssessment elementTypeAssessment;

	public ArrayOfMutableElementTypeMutableTypeAssessment( Class<?> jvmClass, boolean threadSafe, MutableTypeAssessment elementTypeAssessment )
	{
		super( jvmClass, threadSafe );
		assert jvmClass.isArray();
		assert jvmClass.getComponentType() == elementTypeAssessment.type;
		this.elementTypeAssessment = elementTypeAssessment;
	}

	@Override public List<Assessment> children() { return List.of( elementTypeAssessment ); }
	@Override public boolean isThreadSafe() { return threadSafe && elementTypeAssessment.isThreadSafe(); }
}
