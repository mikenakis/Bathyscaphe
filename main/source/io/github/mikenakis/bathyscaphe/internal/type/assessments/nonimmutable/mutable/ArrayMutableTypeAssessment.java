/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable;

/**
 * Signifies that a type is mutable because it is an array.
 *
 * @author michael.gr
 */
public final class ArrayMutableTypeAssessment extends MutableTypeAssessment
{
	public ArrayMutableTypeAssessment( Class<?> jvmClass )
	{
		super( jvmClass );
		assert jvmClass.isArray();
	}
}
