/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.field;

import io.github.mikenakis.bathyscaphe.annotations.Invariable;
import io.github.mikenakis.bathyscaphe.annotations.InvariableArray;
import io.github.mikenakis.bathyscaphe.internal.mykit.MyKit;
import io.github.mikenakis.bathyscaphe.internal.type.TypeAssessor;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.ImmutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.TypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.UnderAssessmentTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.ArrayOfMutableElementTypeMutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.MutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ArrayOfProvisoryElementTypeProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.AnnotatedInvariableArrayFieldMustBePrivateException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.AnnotatedInvariableFieldMayNotAlreadyBeInvariableException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.AnnotatedInvariableFieldMustBePrivateException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.NonArrayFieldMayNotBeAnnotatedInvariableArrayException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.VariableFieldMayNotBeAnnotatedInvariableArrayException;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.FieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.ImmutableFieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.UnderAssessmentFieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.mutable.ArrayMutableFieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.mutable.MutableFieldTypeMutableFieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.mutable.VariableMutableFieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.provisory.ProvisoryFieldTypeProvisoryFieldAssessment;

import java.lang.reflect.Field;
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
		boolean isInvariableField = isInvariableField( field );
		Class<?> fieldType = field.getType();
		boolean isArray = fieldType.isArray();
		boolean isInvariableArray = field.isAnnotationPresent( InvariableArray.class );
		if( !isArray && isInvariableArray )
			throw new NonArrayFieldMayNotBeAnnotatedInvariableArrayException( field );
		else if( !isInvariableField && isInvariableArray )
			throw new VariableFieldMayNotBeAnnotatedInvariableArrayException( field );
		else if( !isInvariableField )
			return new VariableMutableFieldAssessment( field );
		else if( isArray && !isInvariableArray )
			return new ArrayMutableFieldAssessment( field );
		else if( isArray )
		{
			assert isArray && isInvariableArray;
			if( !Modifier.isPrivate( field.getModifiers() ) )
				throw new AnnotatedInvariableArrayFieldMustBePrivateException( field );
			TypeAssessment arrayElementTypeAssessment = typeAssessor.assess( fieldType.getComponentType() );
			return switch( arrayElementTypeAssessment )
				{
					case UnderAssessmentTypeAssessment ignore -> UnderAssessmentFieldAssessment.instance;
					case ProvisoryTypeAssessment provisoryTypeAssessment ->	newProvisoryArrayFieldAssessment( field, MyKit.uncheckedClassCast( fieldType ), provisoryTypeAssessment );
					case ImmutableTypeAssessment ignore -> ImmutableFieldAssessment.instance;
					case MutableTypeAssessment mutableTypeAssessment -> newMutableArrayFieldAssessment( field, fieldType, mutableTypeAssessment );
					//DoNotCover
					default -> throw new AssertionError( arrayElementTypeAssessment );
				};
		}
		TypeAssessment fieldTypeAssessment = typeAssessor.assess( fieldType );
		return switch( fieldTypeAssessment )
			{
				case UnderAssessmentTypeAssessment ignore -> UnderAssessmentFieldAssessment.instance;
				case ProvisoryTypeAssessment provisoryTypeAssessment -> new ProvisoryFieldTypeProvisoryFieldAssessment( field, provisoryTypeAssessment );
				case ImmutableTypeAssessment ignore -> ImmutableFieldAssessment.instance;
				case MutableTypeAssessment mutableTypeAssessment -> new MutableFieldTypeMutableFieldAssessment( field, mutableTypeAssessment );
				//DoNotCover
				default -> throw new AssertionError( fieldTypeAssessment );
			};
	}

	//private static final Decomposer<Object[],Object> arrayDecomposer = Arrays::asList;

	private static FieldAssessment newProvisoryArrayFieldAssessment( Field field, Class<Object[]> fieldType, ProvisoryTypeAssessment elementTypeAssessment )
	{
		ProvisoryTypeAssessment fieldTypeAssessment = new ArrayOfProvisoryElementTypeProvisoryTypeAssessment( fieldType, elementTypeAssessment );
		//ProvisoryTypeAssessment fieldTypeAssessment = new CompositeProvisoryTypeAssessment<>( TypeAssessment.Mode.Assessed, fieldType, elementTypeAssessment, arrayDecomposer );
		return new ProvisoryFieldTypeProvisoryFieldAssessment( field, fieldTypeAssessment );
	}

	private static FieldAssessment newMutableArrayFieldAssessment( Field field, Class<?> fieldType, MutableTypeAssessment elementTypeAssessment )
	{
		MutableTypeAssessment fieldTypeAssessment = new ArrayOfMutableElementTypeMutableTypeAssessment( fieldType, elementTypeAssessment );
		return new MutableFieldTypeMutableFieldAssessment( field, fieldTypeAssessment );
	}

	public static boolean isInvariableField( Field field )
	{
		int fieldModifiers = field.getModifiers();
		assert !Modifier.isStatic( fieldModifiers );
		boolean isAnnotatedInvariable = field.isAnnotationPresent( Invariable.class );
		boolean isPrivate = Modifier.isPrivate( fieldModifiers );
		if( isAnnotatedInvariable && !isPrivate )
			throw new AnnotatedInvariableFieldMustBePrivateException( field );
		boolean isDeclaredInvariable = Modifier.isFinal( fieldModifiers );
		if( isDeclaredInvariable && isAnnotatedInvariable )
			throw new AnnotatedInvariableFieldMayNotAlreadyBeInvariableException( field );
		return isDeclaredInvariable || isAnnotatedInvariable;
	}
}
