/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.field.assessments.mutable;

import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.NonImmutableFieldAssessment;

import java.lang.reflect.Field;

/**
 * Signifies that a field is mutable.
 *
 * @author michael.gr
 */
public abstract class MutableFieldAssessment extends NonImmutableFieldAssessment
{
	protected MutableFieldAssessment( Field field ) { super( field ); }
}
