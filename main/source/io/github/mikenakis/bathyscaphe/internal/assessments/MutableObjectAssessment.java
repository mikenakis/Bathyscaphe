/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.assessments;

import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.NonImmutableTypeAssessment;

/**
 * Signifies that an object is mutable.
 *
 * @author michael.gr
 */
public abstract class MutableObjectAssessment extends ObjectAssessment
{
	protected MutableObjectAssessment()
	{
	}

	public abstract Object object();
	public abstract NonImmutableTypeAssessment typeAssessment();
}
