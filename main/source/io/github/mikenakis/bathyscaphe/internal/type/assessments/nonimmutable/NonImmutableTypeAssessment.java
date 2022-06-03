/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable;

import io.github.mikenakis.bathyscaphe.internal.type.assessments.TypeAssessment;

/**
 * Base class for non-immutable {@link TypeAssessment}s.
 *
 * @author michael.gr
 */
public abstract class NonImmutableTypeAssessment extends TypeAssessment
{
	public final Class<?> type;
	protected final boolean threadSafe;

	protected NonImmutableTypeAssessment( Class<?> type, boolean threadSafe )
	{
		this.type = type;
		this.threadSafe = threadSafe;
	}

	public abstract boolean isThreadSafe();
}
