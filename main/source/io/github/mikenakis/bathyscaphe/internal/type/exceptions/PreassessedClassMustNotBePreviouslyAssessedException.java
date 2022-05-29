/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.exceptions;

import io.github.mikenakis.bathyscaphe.internal.type.assessments.TypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.mykit.UncheckedException;

/**
 * Thrown when an attempt is made to preassess a class, but there has already been a request to assess this class, and the answer that was given was that the
 * class is mutable. The order of operations should be changed so as to avoid this inconsistency.
 *
 * @author michael.gr
 */
public class PreassessedClassMustNotBePreviouslyAssessedException extends UncheckedException
{
	public final TypeAssessment previousTypeAssessment;

	public PreassessedClassMustNotBePreviouslyAssessedException( TypeAssessment previousTypeAssessment )
	{
		this.previousTypeAssessment = previousTypeAssessment;
	}
}
