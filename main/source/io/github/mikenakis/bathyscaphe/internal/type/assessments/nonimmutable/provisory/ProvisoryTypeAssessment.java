/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory;

import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.NonImmutableTypeAssessment;

/**
 * Signifies that a class cannot be conclusively classified as either mutable or immutable, so further runtime checks are necessary on instances of this class.
 *
 * @author michael.gr
 */
public abstract class ProvisoryTypeAssessment extends NonImmutableTypeAssessment
{
	protected ProvisoryTypeAssessment( Class<?> type ) { super( type ); }
}
