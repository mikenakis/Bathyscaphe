/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type;

import io.github.mikenakis.bathyscaphe.internal.helpers.Helpers;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.ImmutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.TypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.UnderAssessmentTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.ThreadSafeMutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.PreassessedClassMustNotAlreadyBeImmutableException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.PreassessedClassMustNotBeExtensibleException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.PreassessedClassMustNotBePreviouslyAssessedException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.PreassessedTypeMustBeClassException;
import io.github.mikenakis.debug.Debug;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Deeply assesses the immutability of types. DO NOT USE; FOR INTERNAL USE ONLY.
 *
 * @author michael.gr
 */
public final class TypeAssessor
{
	/**
	 * Factory method. DO NOT USE; FOR INTERNAL USE ONLY.
	 */
	public static TypeAssessor create()
	{
		TypeAssessor assessor = new TypeAssessor();
		DefaultPreassessments.apply( assessor );
		return assessor;
	}

	private final Map<Class<?>,TypeAssessment> assessmentsByType = new HashMap<>();
	private final Reflector reflector = new Reflector( this );

	TypeAssessor()
	{
	}

	public void addImmutablePreassessment( Class<?> jvmClass )
	{
		assert addedClassMustNotBePreviouslyAssessedAssertion( jvmClass );
		assert addedClassMustBeClassTypeAssertion( jvmClass );
		assert addedClassMustNotBeExtensibleClassTypeAssertion( jvmClass );
		assert addedClassMustNotAlreadyBeImmutableAssertion( jvmClass );
		synchronized( assessmentsByType )
		{
			assessmentsByType.put( jvmClass, ImmutableTypeAssessment.instance );
		}
	}

	public void addThreadSafePreassessment( Class<?> jvmClass )
	{
		assert addedClassMustNotBePreviouslyAssessedAssertion( jvmClass );
		assert addedClassMustBeClassTypeAssertion( jvmClass );
		assert addedClassMustNotBeExtensibleClassTypeAssertion( jvmClass );
		assert addedClassMustNotAlreadyBeImmutableAssertion( jvmClass );
		synchronized( assessmentsByType )
		{
			assessmentsByType.put( jvmClass, new ThreadSafeMutableTypeAssessment( jvmClass ) );
		}
	}

	public TypeAssessment assess( Class<?> type )
	{
		synchronized( assessmentsByType )
		{
			return Debug.boundary( () -> //
			{
				TypeAssessment existingAssessment = assessmentsByType.get( type );
				if( existingAssessment != null )
					return existingAssessment;
				assessmentsByType.put( type, UnderAssessmentTypeAssessment.instance );
				TypeAssessment newAssessment = reflector.assess( type );
				TypeAssessment oldAssessment = assessmentsByType.put( type, newAssessment );
				assert oldAssessment == UnderAssessmentTypeAssessment.instance;
				assert !(newAssessment instanceof UnderAssessmentTypeAssessment);
				return newAssessment;
			} );
		}
	}

	void addDefaultPreassessment( Class<?> jvmClass, TypeAssessment classAssessment )
	{
		assessmentsByType.put( jvmClass, classAssessment );
	}

	private boolean addedClassMustNotBePreviouslyAssessedAssertion( Class<?> jvmClass )
	{
		TypeAssessment previousClassAssessment = assessmentsByType.get( jvmClass );
		assert previousClassAssessment == null : new PreassessedClassMustNotBePreviouslyAssessedException( previousClassAssessment );
		return true;
	}

	private static boolean addedClassMustBeClassTypeAssertion( Class<?> jvmClass )
	{
		if( !Helpers.isClass( jvmClass ) )
			throw new PreassessedTypeMustBeClassException( jvmClass );
		return true;
	}

	private static boolean addedClassMustNotBeExtensibleClassTypeAssertion( Class<?> jvmClass )
	{
		if( !Modifier.isFinal( jvmClass.getModifiers() ) )
			throw new PreassessedClassMustNotBeExtensibleException( jvmClass );
		return true;
	}

	private boolean addedClassMustNotAlreadyBeImmutableAssertion( Class<?> jvmClass )
	{
		TypeAssessment assessment = reflector.assess( jvmClass );
		if( assessment instanceof ImmutableTypeAssessment ignore )
			throw new PreassessedClassMustNotAlreadyBeImmutableException( jvmClass );
		return true;
	}
}
