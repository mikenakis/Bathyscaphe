/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.field.assessments;

/**
 * Signifies that a field is immutable.
 *
 * @author michael.gr
 */
public final class ImmutableFieldAssessment extends FieldAssessment
{
	public static final ImmutableFieldAssessment instance = new ImmutableFieldAssessment();

	private ImmutableFieldAssessment() { }
}
