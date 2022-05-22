/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
 */

package io.github.mikenakis.bathyscaphe.internal.type.assessments;

/**
 * Signifies that a type is currently under assessment.  Used internally, should never be seen by the user.
 *
 * @author michael.gr
 */
public final class UnderAssessmentTypeAssessment extends TypeAssessment
{
	public static final UnderAssessmentTypeAssessment instance = new UnderAssessmentTypeAssessment();

	private UnderAssessmentTypeAssessment() { }
}
