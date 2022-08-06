/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.field;

import io.github.mikenakis.bathyscaphe.annotations.Invariable;
import io.github.mikenakis.bathyscaphe.annotations.InvariableArray;
import io.github.mikenakis.bathyscaphe.annotations.ThreadSafe;
import io.github.mikenakis.bathyscaphe.annotations.ThreadSafeArray;
import io.github.mikenakis.bathyscaphe.internal.mykit.MyKit;
import io.github.mikenakis.bathyscaphe.internal.type.TypeAssessor;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.ImmutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.TypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.UnderAssessmentTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.ArrayOfMutableElementTypeMutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.MutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ArrayOfProvisoryElementTypeProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.AnnotatedFieldMustBePrivateException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.AnnotatedInvariableFieldMayNotAlreadyBeInvariableException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.NonArrayFieldMayNotBeAnnotatedInvariableArrayException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.NonArrayFieldMayNotBeAnnotatedThreadSafeArrayException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.VariableFieldMayNotBeAnnotatedInvariableArrayException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.VariableFieldMayNotBeAnnotatedThreadSafeArrayException;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.FieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.ImmutableFieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.UnderAssessmentFieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.nonimmutable.mutable.ArrayMutableFieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.nonimmutable.mutable.MutableFieldTypeMutableFieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.nonimmutable.mutable.VariableMutableFieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.nonimmutable.provisory.ProvisoryFieldTypeProvisoryFieldAssessment;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

/**
 * Deeply assesses the immutability of fields. DO NOT USE; FOR INTERNAL USE ONLY.
 *
 * @author michael.gr
 */
public class FieldAssessor
{
	private final TypeAssessor typeAssessor;

	/**
	 * Constructor. DO NOT USE; FOR INTERNAL USE ONLY.
	 */
	public FieldAssessor( TypeAssessor typeAssessor )
	{
		this.typeAssessor = typeAssessor;
	}

	public FieldAssessment assessField( Field field )
	{
		assert !Modifier.isStatic( field.getModifiers() );
		Class<?> fieldType = field.getType();
		boolean isArray = fieldType.isArray();
		boolean isInvariable = isInvariable( field );
		boolean isInvariableArray = isInvariableArray( field );
		boolean isThreadSafe = isThreadSafe( field );
		boolean isThreadSafeArray = isThreadSafeArray( field );
		if( !isInvariable )
			return new VariableMutableFieldAssessment( field, isThreadSafe );
		if( isArray )
		{
			if( !isInvariableArray )
				return new ArrayMutableFieldAssessment( field, isThreadSafe && isThreadSafeArray );
			TypeAssessment arrayElementTypeAssessment = typeAssessor.assess( fieldType.getComponentType() );
			//IntellijIdea blooper: good code red: Currently, (August 2022) IntellijIdea does not know anything about JDK 19, and it is not smart enough to
			//figure out that feature-wise it must be a superset of the last JDK that it knows, which is JDK 17.
			//As a result, it marks the following code with "Patterns in switch are not supported at language level '19'", which is just plain wrong.
			return switch( arrayElementTypeAssessment )
				{
					case UnderAssessmentTypeAssessment ignore -> UnderAssessmentFieldAssessment.instance;
					case ProvisoryTypeAssessment provisoryTypeAssessment ->
						newProvisoryArrayFieldAssessment( field, isThreadSafe, isThreadSafeArray, MyKit.uncheckedClassCast( fieldType ), provisoryTypeAssessment );
					case ImmutableTypeAssessment ignore -> ImmutableFieldAssessment.instance;
					case MutableTypeAssessment mutableTypeAssessment ->
						newMutableArrayFieldAssessment( field, isThreadSafe, isThreadSafeArray, MyKit.uncheckedClassCast( fieldType ), mutableTypeAssessment );
					//DoNotCover
					default -> throw new AssertionError( arrayElementTypeAssessment );
				};
		}
		TypeAssessment fieldTypeAssessment = typeAssessor.assess( fieldType );
		//IntellijIdea blooper: good code red: Currently, (August 2022) IntellijIdea does not know anything about JDK 19, and it is not smart enough to
		//figure out that feature-wise it must be a superset of the last JDK that it knows, which is JDK 17.
		//As a result, it marks the following code with "Patterns in switch are not supported at language level '19'", which is just plain wrong.
		return switch( fieldTypeAssessment )
			{
				case UnderAssessmentTypeAssessment ignore -> UnderAssessmentFieldAssessment.instance;
				case ProvisoryTypeAssessment provisoryTypeAssessment ->
					new ProvisoryFieldTypeProvisoryFieldAssessment( field, isThreadSafe, provisoryTypeAssessment );
				case ImmutableTypeAssessment ignore -> ImmutableFieldAssessment.instance;
				case MutableTypeAssessment mutableTypeAssessment -> new MutableFieldTypeMutableFieldAssessment( field, isThreadSafe, mutableTypeAssessment );
				//DoNotCover
				default -> throw new AssertionError( fieldTypeAssessment );
			};
	}

	//private static final Decomposer<Object[],Object> arrayDecomposer = Arrays::asList;

	private static FieldAssessment newProvisoryArrayFieldAssessment( Field field, boolean threadSafe, boolean threadSafeArray, Class<Object[]> fieldType, ProvisoryTypeAssessment elementTypeAssessment )
	{
		ProvisoryTypeAssessment fieldTypeAssessment = new ArrayOfProvisoryElementTypeProvisoryTypeAssessment( fieldType, threadSafeArray, elementTypeAssessment );
		//ProvisoryTypeAssessment fieldTypeAssessment = new CompositeProvisoryTypeAssessment<>( TypeAssessment.Mode.Assessed, fieldType, elementTypeAssessment, arrayDecomposer );
		return new ProvisoryFieldTypeProvisoryFieldAssessment( field, threadSafe, fieldTypeAssessment );
	}

	private static FieldAssessment newMutableArrayFieldAssessment( Field field, boolean threadSafe, boolean threadSafeArray, Class<Object[]> fieldType, MutableTypeAssessment elementTypeAssessment )
	{
		MutableTypeAssessment fieldTypeAssessment = new ArrayOfMutableElementTypeMutableTypeAssessment( fieldType, threadSafeArray, elementTypeAssessment );
		return new MutableFieldTypeMutableFieldAssessment( field, threadSafe, fieldTypeAssessment );
	}

	public static boolean isInvariable( Field field )
	{
		if( !field.isAnnotationPresent( Invariable.class ) )
			return isFinal( field );
		if( !isPrivate( field ) )
			throw new AnnotatedFieldMustBePrivateException( field );
		if( isFinal( field ) )
			throw new AnnotatedInvariableFieldMayNotAlreadyBeInvariableException( field );
		return true;
	}

	private static boolean isInvariableArray( Field field )
	{
		if( !field.isAnnotationPresent( InvariableArray.class ) )
			return false;
		if( !field.getType().isArray() )
			throw new NonArrayFieldMayNotBeAnnotatedInvariableArrayException( field );
		if( !isPrivate( field ) )
			throw new AnnotatedFieldMustBePrivateException( field );
		if( !isInvariable( field ) )
			throw new VariableFieldMayNotBeAnnotatedInvariableArrayException( field );
		return true;
	}

	private static boolean isThreadSafe( Field field )
	{
		if( !field.isAnnotationPresent( ThreadSafe.class ) )
			return false;
		if( !isPrivate( field ) )
			throw new AnnotatedFieldMustBePrivateException( field );
		return true;
	}

	private static boolean isThreadSafeArray( Field field )
	{
		if( !field.isAnnotationPresent( ThreadSafeArray.class ) )
			return false;
		if( !field.getType().isArray() )
			throw new NonArrayFieldMayNotBeAnnotatedThreadSafeArrayException( field );
		if( !isPrivate( field ) )
			throw new AnnotatedFieldMustBePrivateException( field );
		if( !isInvariable( field ) )
			throw new VariableFieldMayNotBeAnnotatedThreadSafeArrayException( field );
		return true;
	}

	private static boolean isFinal( Member field )
	{
		int fieldModifiers = field.getModifiers();
		return Modifier.isFinal( fieldModifiers );
	}

	private static boolean isPrivate( Member field )
	{
		int fieldModifiers = field.getModifiers();
		return Modifier.isPrivate( fieldModifiers );
	}
}
