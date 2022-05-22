/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
 */

package io.github.mikenakis.bathyscaphe.internal.type.field.assessments;

import java.lang.reflect.Field;

/**
 * Base class for non-immutable {@link FieldAssessment}s.
 *
 * @author michael.gr
 */
public abstract class NonImmutableFieldAssessment extends FieldAssessment
{
	public final Field field;

	protected NonImmutableFieldAssessment( Field field ) { this.field = field; }
}
