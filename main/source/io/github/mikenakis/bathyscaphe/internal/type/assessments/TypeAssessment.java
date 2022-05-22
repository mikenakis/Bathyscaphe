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
		Assessed, //assessed by dona.
		Preassessed, //preassessment by the user, overriding a 'mutable' assessment that would have normally been given by dona.
		PreassessedByDefault //standard preassessment by dona, overriding a 'mutable' assessment that would have normally been given by dona.
	}

	protected TypeAssessment()
	{
	}
}
