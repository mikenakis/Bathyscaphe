/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
 */

package io.github.mikenakis.bathyscaphe;

import io.github.mikenakis.bathyscaphe.internal.ObjectAssessor;
import io.github.mikenakis.bathyscaphe.internal.assessments.ImmutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.MutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.ObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.mykit.collections.IdentityLinkedHashSet;

import java.util.Set;

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
	 * @return always true. (Will throw {@link ObjectMustBeImmutableException} if the object is found to be mutable.)
	 */
	public static boolean objectMustBeImmutableAssertion( Object object )
	{
		Set<Object> visitedValues = new IdentityLinkedHashSet<>();
		ObjectAssessment assessment = ObjectAssessor.instance.assessRecursively( object, visitedValues );
		if( assessment instanceof MutableObjectAssessment mutableObjectAssessment )
			throw new ObjectMustBeImmutableException( mutableObjectAssessment );
		assert assessment instanceof ImmutableObjectAssessment;
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
}
