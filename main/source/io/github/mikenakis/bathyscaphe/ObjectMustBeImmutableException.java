/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe;

import io.github.mikenakis.bathyscaphe.internal.assessments.MutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.mykit.UncheckedException;

/**
 * Thrown when an object that was expected to be immutable has been found to be mutable.
 *
 * @author michael.gr
 */
public final class ObjectMustBeImmutableException extends UncheckedException
{
	public final MutableObjectAssessment mutableObjectAssessment;

	/**
	 * Constructor.
	 *
	 * @param mutableObjectAssessment the {@link MutableObjectAssessment} that was issued for the object.
	 */
	public ObjectMustBeImmutableException( MutableObjectAssessment mutableObjectAssessment )
	{
		this.mutableObjectAssessment = mutableObjectAssessment;
	}
}
