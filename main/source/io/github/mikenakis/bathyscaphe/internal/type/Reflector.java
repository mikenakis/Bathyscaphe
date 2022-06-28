/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type;

import io.github.mikenakis.bathyscaphe.ImmutabilitySelfAssessable;
import io.github.mikenakis.bathyscaphe.annotations.ThreadSafe;
import io.github.mikenakis.bathyscaphe.internal.helpers.Helpers;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.ImmutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.TypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.UnderAssessmentTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.NonImmutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.ArrayMutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.MultiReasonMutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.MutableFieldMutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.MutableSuperclassMutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.MutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ExtensibleProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.InterfaceProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.MultiReasonProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisoryFieldProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisorySuperclassProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.SelfAssessableProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.SelfAssessableClassMustNotBeImmutableException;
import io.github.mikenakis.bathyscaphe.internal.type.field.FieldAssessor;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.FieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.ImmutableFieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.UnderAssessmentFieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.nonimmutable.mutable.MutableFieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.nonimmutable.provisory.ProvisoryFieldTypeProvisoryFieldAssessment;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Deeply assesses the immutability of types using reflection. DO NOT USE. FOR INTERNAL USE ONLY.
 *
 * @author michael.gr
 */
final class Reflector
{
	private final TypeAssessor typeAssessor;
	private final FieldAssessor fieldAssessor;

	Reflector( TypeAssessor typeAssessor )
	{
		this.typeAssessor = typeAssessor;
		fieldAssessor = new FieldAssessor( typeAssessor );
	}

	TypeAssessment assess( Class<?> type )
	{
		TypeAssessment assessment = assess0( type );
		if( ImmutabilitySelfAssessable.class.isAssignableFrom( type ) )
		{
			assert !(assessment instanceof ImmutableTypeAssessment) : new SelfAssessableClassMustNotBeImmutableException( type );
			NonImmutableTypeAssessment nonImmutableTypeAssessment = (NonImmutableTypeAssessment)assessment;
			return new SelfAssessableProvisoryTypeAssessment( type, nonImmutableTypeAssessment.isThreadSafe() );
		}
		return assessment;
	}

	private TypeAssessment assess0( Class<?> type )
	{
		boolean threadSafe = isAnnotatedThreadSafe( type );
		if( type.isArray() )
			return new ArrayMutableTypeAssessment( type, threadSafe );
		if( type.isInterface() )
			return new InterfaceProvisoryTypeAssessment( type, threadSafe );

		List<ProvisoryTypeAssessment> provisoryReasons = new ArrayList<>();
		List<MutableTypeAssessment> mutableReasons = new ArrayList<>();
		Class<?> superclass = type.getSuperclass();
		if( superclass != null )
		{
			TypeAssessment superclassAssessment = assessSuperclass( superclass );
			switch( superclassAssessment )
			{
				case MutableTypeAssessment mutableTypeAssessment:
					mutableReasons.add( new MutableSuperclassMutableTypeAssessment( type, threadSafe, mutableTypeAssessment ) );
					break;
				case ProvisoryTypeAssessment provisoryTypeAssessment:
					provisoryReasons.add( new ProvisorySuperclassProvisoryTypeAssessment( type, threadSafe, provisoryTypeAssessment ) );
					break;
				default:
					assert superclassAssessment instanceof ImmutableTypeAssessment || superclassAssessment instanceof UnderAssessmentTypeAssessment;
			}
		}

		for( Field field : type.getDeclaredFields() )
		{
			if( Modifier.isStatic( field.getModifiers() ) )
				continue;
			FieldAssessment fieldAssessment = fieldAssessor.assessField( field );
			switch( fieldAssessment )
			{
				case ProvisoryFieldTypeProvisoryFieldAssessment provisoryFieldAssessment:
					provisoryReasons.add( new ProvisoryFieldProvisoryTypeAssessment( type, threadSafe, provisoryFieldAssessment ) );
					break;
				case MutableFieldAssessment mutableFieldAssessment:
					mutableReasons.add( new MutableFieldMutableTypeAssessment( type, threadSafe, mutableFieldAssessment ) );
					break;
				default:
					assert fieldAssessment instanceof ImmutableFieldAssessment || fieldAssessment instanceof UnderAssessmentFieldAssessment;
			}
		}

		if( !mutableReasons.isEmpty() )
			return mutableReasons.size() == 1 ? mutableReasons.get( 0 ) : new MultiReasonMutableTypeAssessment( type, threadSafe, Stream.concat( mutableReasons.stream(), provisoryReasons.stream() ).toList() );

		if( !provisoryReasons.isEmpty() )
			return provisoryReasons.size() == 1 ? provisoryReasons.get( 0 ) : new MultiReasonProvisoryTypeAssessment( type, threadSafe, provisoryReasons );

		if( Helpers.isExtensible( type ) )
			return new ExtensibleProvisoryTypeAssessment( TypeAssessment.Mode.Assessed, type, threadSafe );

		return ImmutableTypeAssessment.instance;
	}

	private TypeAssessment assessSuperclass( Class<?> superclass )
	{
		TypeAssessment superclassAssessment = typeAssessor.assess( superclass );
		return switch( superclassAssessment )
			{
				//Cannot happen, because the superclass has obviously been extended, so it is extensible, so it can not be immutable.
				//DoNotCover
				case ImmutableTypeAssessment immutableTypeAssessment -> throw new AssertionError( immutableTypeAssessment );
				//Cannot happen, because the supertype of a class cannot be an interface.
				//DoNotCover
				case InterfaceProvisoryTypeAssessment interfaceAssessment -> throw new AssertionError( interfaceAssessment );
				case UnderAssessmentTypeAssessment underAssessmentTypeAssessment -> underAssessmentTypeAssessment;
				case MutableTypeAssessment mutableTypeAssessment -> mutableTypeAssessment;
				//This means that the supertype is immutable in all aspects except that it is extensible, so the supertype is not preventing us from being immutable.
				case ExtensibleProvisoryTypeAssessment ignore -> ImmutableTypeAssessment.instance;
				case ProvisoryTypeAssessment provisoryTypeAssessment -> provisoryTypeAssessment;
				//DoNotCover
				default -> throw new AssertionError( superclassAssessment );
			};
	}

	private static boolean isAnnotatedThreadSafe( Class<?> type )
	{
		for( ;; )
		{
			if( type.isAnnotationPresent( ThreadSafe.class ) )
				return true;
			for( Class<?> interfaceClass : type.getInterfaces() )
				if( isAnnotatedThreadSafe( interfaceClass ) )
					return true;
			type = type.getSuperclass();
			if( type == null )
				break;
		}
		return false;
	}
}
