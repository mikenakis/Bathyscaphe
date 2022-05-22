/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
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
