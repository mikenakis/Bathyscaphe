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
 * Thrown when an object that was expected to be thread-safe has been found to be non-thread-safe.
 *
 * @author michael.gr
 */
public final class ObjectMustBeThreadSafeException extends UncheckedException
{
	public final MutableObjectAssessment mutableObjectAssessment;

	/**
	 * Constructor.
	 *
	 * @param mutableObjectAssessment the {@link MutableObjectAssessment} that was issued for the object, and has a non-thread-safe type assessment.
	 */
	public ObjectMustBeThreadSafeException( MutableObjectAssessment mutableObjectAssessment )
	{
		assert !mutableObjectAssessment.typeAssessment().threadSafe;
		this.mutableObjectAssessment = mutableObjectAssessment;
	}
}
