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
 * Signifies that an array is provisory because it is of provisory element type.
 *
 * @author michael.gr
 */
public class ArrayOfProvisoryElementTypeProvisoryTypeAssessment extends ProvisoryTypeAssessment
{
	public final ProvisoryTypeAssessment elementTypeAssessment;

	public ArrayOfProvisoryElementTypeProvisoryTypeAssessment( Class<?> jvmClass, boolean threadSafe, ProvisoryTypeAssessment elementTypeAssessment )
	{
		super( jvmClass, threadSafe );
		assert jvmClass.isArray();
		assert jvmClass.getComponentType() == elementTypeAssessment.type;
		this.elementTypeAssessment = elementTypeAssessment;
	}

	@Override public List<Assessment> children() { return List.of( elementTypeAssessment ); }
}
