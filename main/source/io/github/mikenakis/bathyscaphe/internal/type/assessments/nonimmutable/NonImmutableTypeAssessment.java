/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
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

	protected NonImmutableTypeAssessment( Class<?> type ) { this.type = type; }
}
