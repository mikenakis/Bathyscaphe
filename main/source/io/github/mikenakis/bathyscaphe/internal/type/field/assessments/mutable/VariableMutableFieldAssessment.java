/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.field.assessments.mutable;

import io.github.mikenakis.bathyscaphe.internal.type.field.FieldAssessor;
import io.github.mikenakis.bathyscaphe.annotations.Invariable;

import java.lang.reflect.Field;

/**
 * Signifies that a field is mutable because it is not {@code final}, and it has not been annotated with @{@link Invariable}. (Thus, the field is mutable
 * regardless of the assessment of the field type.)
 *
 * @author michael.gr
 */
public final class VariableMutableFieldAssessment extends MutableFieldAssessment
{
	public VariableMutableFieldAssessment( Field field )
	{
		super( field );
		assert !FieldAssessor.isInvariableField( field );
	}
}
