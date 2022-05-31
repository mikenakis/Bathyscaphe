/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.field.assessments.nonimmutable;

import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.FieldAssessment;

import java.lang.reflect.Field;

/**
 * Base class for non-immutable {@link FieldAssessment}s.
 *
 * @author michael.gr
 */
public abstract class NonImmutableFieldAssessment extends FieldAssessment
{
	public final Field field;
	public final boolean threadSafe;

	protected NonImmutableFieldAssessment( Field field, boolean threadSafe )
	{
		this.field = field;
		this.threadSafe = threadSafe;
	}
}
