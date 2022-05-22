/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
 */

package io.github.mikenakis.bathyscaphe.internal.type;

import io.github.mikenakis.debug.Debug;
import io.github.mikenakis.bathyscaphe.internal.helpers.Helpers;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.ImmutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.TypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.UnderAssessmentTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.PreassessedClassMustNotAlreadyBeImmutableException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.PreassessedClassMustNotBeExtensibleException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.PreassessedClassMustNotBePreviouslyAssessedException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.PreassessedTypeMustBeClassException;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Deeply assesses the nature of types. DO NOT USE; FOR INTERNAL USE ONLY.
 * <p>
 * For now, types can be of mutable, immutable, or provisory nature.
 * <p>
 * Provisory means that the type cannot be assessed as mutable or immutable by just looking at the type, so instances of the type will need to be assessed.
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

	public TypeAssessment assess( Class<?> type )
	{
		synchronized( assessmentsByType )
		{
			return Debug.boundary( () -> {
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
