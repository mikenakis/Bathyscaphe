/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.assessments.mutable;

import io.github.mikenakis.bathyscaphe.internal.assessments.Assessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.MutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.NonImmutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisoryTypeAssessment;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Signifies that an array is mutable because it has at least one element which is mutable.
 *
 * @author michael.gr
 */
public final class MutableArrayElementMutableObjectAssessment extends MutableObjectAssessment
{
	public final Object object;
	public final ProvisoryTypeAssessment typeAssessment;
	public final int elementIndex;
	public final MutableObjectAssessment elementAssessment;

	public MutableArrayElementMutableObjectAssessment( Object object, ProvisoryTypeAssessment typeAssessment, int elementIndex, MutableObjectAssessment elementAssessment )
	{
		assert object.getClass().isArray();
		assert Array.get( object, elementIndex ) == elementAssessment.object();
		assert typeAssessment.type == object.getClass();
		this.object = object;
		this.typeAssessment = typeAssessment;
		this.elementIndex = elementIndex;
		this.elementAssessment = elementAssessment;
	}

	@Override public Object object() { return object; }
	@Override public NonImmutableTypeAssessment typeAssessment() { return typeAssessment; }
	@Override public List<Assessment> children() { return List.of( elementAssessment ); }
}
