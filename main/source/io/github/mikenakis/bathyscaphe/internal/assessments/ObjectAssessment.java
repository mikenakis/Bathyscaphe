/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.assessments;

import io.github.mikenakis.bathyscaphe.internal.diagnostic.AssessmentPrinter;

/**
 * Base class for object {@link Assessment}s.
 *
 * @author michael.gr
 */
public abstract class ObjectAssessment extends Assessment
{
	protected ObjectAssessment()
	{
	}

	public abstract boolean isThreadSafe();

	@Override public final String toString()
	{
		return String.join( "\n", AssessmentPrinter.getText( this ) );
	}
}
