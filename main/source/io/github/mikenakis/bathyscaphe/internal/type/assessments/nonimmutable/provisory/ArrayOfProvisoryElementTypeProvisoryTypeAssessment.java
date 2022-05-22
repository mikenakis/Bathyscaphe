/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
 */

package io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory;

import io.github.mikenakis.bathyscaphe.internal.assessments.Assessment;

import java.util.List;

/**
 * Signifies that an array is provisory because it is of provisory element type.
 *
 * @author michael.gr
 */
public class ArrayOfProvisoryElementTypeProvisoryTypeAssessment extends ProvisoryTypeAssessment
{
	public final ProvisoryTypeAssessment elementTypeAssessment;

	public ArrayOfProvisoryElementTypeProvisoryTypeAssessment( Class<?> jvmClass, ProvisoryTypeAssessment elementTypeAssessment )
	{
		super( jvmClass );
		assert jvmClass.isArray();
		assert jvmClass.getComponentType() == elementTypeAssessment.type;
		this.elementTypeAssessment = elementTypeAssessment;
	}

	@Override public List<Assessment> children() { return List.of( elementTypeAssessment ); }
}
