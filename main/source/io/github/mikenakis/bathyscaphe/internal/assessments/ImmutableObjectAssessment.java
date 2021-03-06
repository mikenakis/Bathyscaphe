/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
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

	@Override public boolean isThreadSafe() { return true; }
}
