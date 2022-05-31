/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable;

/**
 * Signifies that a type is thread-safe.
 *
 * @author michael.gr
 */
public final class ThreadSafeMutableTypeAssessment extends MutableTypeAssessment
{
	public ThreadSafeMutableTypeAssessment( Class<?> jvmClass )
	{
		super( jvmClass, true );
	}
}
