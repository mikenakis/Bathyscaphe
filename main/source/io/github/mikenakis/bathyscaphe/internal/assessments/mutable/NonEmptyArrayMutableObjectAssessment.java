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
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.ArrayMutableTypeAssessment;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Signifies that an object is mutable because it is a non-empty array.
 *
 * @author michael.gr
 */
public final class NonEmptyArrayMutableObjectAssessment extends MutableObjectAssessment
{
	public final Object object;
	public final ArrayMutableTypeAssessment typeAssessment;

	public NonEmptyArrayMutableObjectAssessment( Object object, ArrayMutableTypeAssessment typeAssessment )
	{
		assert object.getClass().isArray();
		assert Array.getLength( object ) > 0;
		this.object = object;
		this.typeAssessment = typeAssessment;
	}

	@Override public Object object() { return object; }
	@Override public NonImmutableTypeAssessment typeAssessment() { return typeAssessment; }
	@Override public List<Assessment> children() { return List.of( typeAssessment ); }
}
