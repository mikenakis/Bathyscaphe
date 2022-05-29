/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.assessments;

/**
 * Signifies that a type is immutable.
 *
 * @author michael.gr
 */
public final class ImmutableTypeAssessment extends TypeAssessment
{
	public static final ImmutableTypeAssessment instance = new ImmutableTypeAssessment();

	private ImmutableTypeAssessment()
	{
	}
}
