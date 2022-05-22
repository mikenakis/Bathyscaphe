/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
 */

package io.github.mikenakis.bathyscaphe.internal.assessments;

/**
 * Signifies that an object is immutable.
 *
 * @author michael.gr
 */
public final class ImmutableObjectAssessment extends ObjectAssessment
{
	public static final ImmutableObjectAssessment instance = new ImmutableObjectAssessment();

	private ImmutableObjectAssessment()
	{
	}
}
