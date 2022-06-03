/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe;

import io.github.mikenakis.bathyscaphe.internal.ObjectAssessor;
import io.github.mikenakis.bathyscaphe.internal.assessments.ImmutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.MutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.ObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.diagnostic.AssessmentPrinter;

import java.util.List;

/**
 * Deep immutability assessment for Java objects.
 *
 * @author michael.gr
 */
public final class Bathyscaphe
{
	/**
	 * Asserts that a certain object is immutable.
	 *
	 * @param object the object whose immutability is to be assessed.
	 *
	 * @return always true.
	 * @throws  ObjectMustBeImmutableException if the object is mutable.
	 */
	public static boolean objectMustBeImmutableAssertion( Object object )
	{
		ObjectAssessment assessment = ObjectAssessor.instance.assess( object );
		if( assessment instanceof MutableObjectAssessment mutableObjectAssessment )
			throw new ObjectMustBeImmutableException( mutableObjectAssessment );
		assert assessment instanceof ImmutableObjectAssessment;
		return true;
	}

	/**
	 * Asserts that a certain object is thread-safe.
	 *
	 * @param object the object whose thread-safety is to be assessed.
	 *
	 * @return always true.
	 * @throws  ObjectMustBeThreadSafeException if the object is not thread-safe.
	 */
	public static boolean objectMustBeThreadSafeAssertion( Object object )
	{
		ObjectAssessment assessment = ObjectAssessor.instance.assess( object );
		if( !assessment.isThreadSafe() )
		{
			MutableObjectAssessment mutableObjectAssessment = (MutableObjectAssessment)assessment;
			throw new ObjectMustBeThreadSafeException( mutableObjectAssessment );
		}
		return true;
	}

	/**
	 * Adds an "immutable" preassessment for a given class, overriding the "mutable" assessment that the class would normally receive.
	 *
	 * @param jvmClass the class to treat as immutable.
	 */
	public static void addImmutablePreassessment( Class<?> jvmClass )
	{
		ObjectAssessor.instance.addImmutablePreassessment( jvmClass );
	}

	/**
	 * Adds a "thread-safe" preassessment for a given class, overriding the "non-thread-safe" assessment that the class would normally receive.
	 *
	 * @param jvmClass the class to treat as immutable.
	 */
	public static void addThreadSafePreassessment( Class<?> jvmClass )
	{
		ObjectAssessor.instance.addThreadSafePreassessment( jvmClass );
	}

	/**
	 * Obtains a detailed human-readable diagnostic text explaining why an assessment was issued.
	 *
	 * @param objectMustBeImmutableException the {@link ObjectMustBeImmutableException} to explain.
	 */
	public static List<String> explain( ObjectMustBeImmutableException objectMustBeImmutableException )
	{
		return AssessmentPrinter.getText( objectMustBeImmutableException );
	}
}
