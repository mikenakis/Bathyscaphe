/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
 */

package io.github.mikenakis.bathyscaphe.internal.type.assessments;

import io.github.mikenakis.bathyscaphe.internal.assessments.Assessment;

/**
 * Base class for {@link Assessment}s of types.
 *
 * @author michael.gr
 */
public abstract class TypeAssessment extends Assessment
{
	public enum Mode
	{
		Assessed, //issued after assessment.
		Preassessed, //pre-assessment added by the user, overriding a 'mutable' assessment that would have normally been issued.
		PreassessedByDefault //pre-assessment added by the immutability assessment facility.
	}

	protected TypeAssessment()
	{
	}
}
