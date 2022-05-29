/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable;

import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.NonImmutableTypeAssessment;

/**
 * Signifies that a class is mutable.
 *
 * @author michael.gr
 */
public abstract class MutableTypeAssessment extends NonImmutableTypeAssessment
{
	protected MutableTypeAssessment( Class<?> type ) { super( type ); }
}
