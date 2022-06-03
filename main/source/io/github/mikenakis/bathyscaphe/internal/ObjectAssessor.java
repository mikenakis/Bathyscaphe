/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal;

import io.github.mikenakis.bathyscaphe.ImmutabilitySelfAssessable;
import io.github.mikenakis.bathyscaphe.internal.assessments.ImmutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.MutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.ObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.mutable.MutableArrayElementMutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.mutable.MutableClassMutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.mutable.MutableComponentMutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.mutable.MutableFieldValueMutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.mutable.MutableSuperObjectMutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.mutable.NonEmptyArrayMutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.mutable.SelfAssessedMutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.helpers.IterableOnArrayObject;
import io.github.mikenakis.bathyscaphe.internal.mykit.MyKit;
import io.github.mikenakis.bathyscaphe.internal.mykit.collections.IdentityLinkedHashSet;
import io.github.mikenakis.bathyscaphe.internal.type.TypeAssessor;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.ImmutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.TypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.ArrayMutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.MutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ArrayOfProvisoryElementTypeProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.CompositeProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ExtensibleProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.MultiReasonProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisoryFieldProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisorySuperclassProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.SelfAssessableProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.nonimmutable.provisory.ProvisoryFieldTypeProvisoryFieldAssessment;

import java.lang.reflect.Array;
import java.util.Set;

/**
 * Deeply assesses the immutability of objects. DO NOT USE; FOR INTERNAL USE ONLY.
 *
 * @author michael.gr
 */
public final class ObjectAssessor
{
	public static final ObjectAssessor instance = new ObjectAssessor();

	private final TypeAssessor typeAssessor = TypeAssessor.create();

	private ObjectAssessor()
	{
	}

	public void addImmutablePreassessment( Class<?> jvmClass )
	{
		typeAssessor.addImmutablePreassessment( jvmClass );
	}

	public void addThreadSafePreassessment( Class<?> jvmClass )
	{
		typeAssessor.addThreadSafePreassessment( jvmClass );
	}

	public ObjectAssessment assess( Object object )
	{
		Set<Object> visitedValues = new IdentityLinkedHashSet<>();
		return assessRecursively( object, visitedValues );
	}

	private <T> ObjectAssessment assessRecursively( T object, Set<Object> visitedObjects )
	{
		if( object == null )
			return ImmutableObjectAssessment.instance;
		if( visitedObjects.contains( object ) )
			return ImmutableObjectAssessment.instance;
		visitedObjects.add( object );
		Class<T> objectClass = MyKit.getClass( object );
		TypeAssessment typeAssessment = typeAssessor.assess( objectClass );
		return assessRecursively( object, typeAssessment, visitedObjects );
	}

	private <T> ObjectAssessment assessRecursively( T object, TypeAssessment typeAssessment, Set<Object> visitedValues )
	{
		return switch( typeAssessment )
			{
				case ImmutableTypeAssessment ignore -> ImmutableObjectAssessment.instance;
				//Class is extensible but otherwise immutable, and object is of this exact class and not of a further derived class, so object is immutable.
				case ExtensibleProvisoryTypeAssessment ignore -> ImmutableObjectAssessment.instance;
				case CompositeProvisoryTypeAssessment<?,?> provisoryCompositeAssessment ->
					assessComposite( object, provisoryCompositeAssessment, visitedValues );
				case SelfAssessableProvisoryTypeAssessment selfAssessableAssessment ->
					assessSelfAssessable( selfAssessableAssessment, (ImmutabilitySelfAssessable)object );
				case MultiReasonProvisoryTypeAssessment multiReasonAssessment -> assessMultiReason( object, multiReasonAssessment, visitedValues );
				case ProvisorySuperclassProvisoryTypeAssessment provisorySuperclassAssessment ->
					assessSuperObject( object, provisorySuperclassAssessment, provisorySuperclassAssessment.superclassAssessment, visitedValues );
				case ProvisoryFieldProvisoryTypeAssessment provisoryFieldAssessment ->
					assessField( object, provisoryFieldAssessment, provisoryFieldAssessment.fieldAssessment, visitedValues );
				case ArrayMutableTypeAssessment arrayAssessment -> assessArray( object, arrayAssessment );
				case MutableTypeAssessment mutableTypeAssessment -> new MutableClassMutableObjectAssessment( object, mutableTypeAssessment );
				default -> throw new AssertionError( typeAssessment );
			};
	}

	private static ObjectAssessment assessArray( Object arrayObject, ArrayMutableTypeAssessment mutableArrayAssessment )
	{
		if( Array.getLength( arrayObject ) == 0 )
			return ImmutableObjectAssessment.instance;
		return new NonEmptyArrayMutableObjectAssessment( arrayObject, mutableArrayAssessment );
	}

	private <T, E> ObjectAssessment assessComposite( T compositeObject, CompositeProvisoryTypeAssessment<?,?> wildcardTypeAssessment, Set<Object> visitedValues )
	{
		@SuppressWarnings( "unchecked" ) CompositeProvisoryTypeAssessment<T,E> typeAssessment = (CompositeProvisoryTypeAssessment<T,E>)wildcardTypeAssessment;
		Iterable<E> iterableObject = typeAssessment.decomposer.decompose( compositeObject );
		int index = 0;
		for( var element : iterableObject )
		{
			ObjectAssessment elementAssessment = assessRecursively( element, visitedValues );
			if( elementAssessment instanceof MutableObjectAssessment mutableObjectAssessment )
				return new MutableComponentMutableObjectAssessment<>( compositeObject, typeAssessment, index, mutableObjectAssessment );
			assert elementAssessment instanceof ImmutableObjectAssessment;
			index++;
		}
		return ImmutableObjectAssessment.instance;
	}

	private static ObjectAssessment assessSelfAssessable( SelfAssessableProvisoryTypeAssessment typeAssessment, ImmutabilitySelfAssessable selfAssessableObject )
	{
		if( selfAssessableObject.isImmutable() )
			return ImmutableObjectAssessment.instance;
		return new SelfAssessedMutableObjectAssessment( typeAssessment, selfAssessableObject );
	}

	private ObjectAssessment assessMultiReason( Object object, MultiReasonProvisoryTypeAssessment multiReasonProvisoryTypeAssessment, Set<Object> visitedValues )
	{
		for( ProvisoryTypeAssessment provisoryReason : multiReasonProvisoryTypeAssessment.reasons )
		{
			ObjectAssessment objectAssessment = switch( provisoryReason )
				{
					case ProvisorySuperclassProvisoryTypeAssessment provisorySuperclassAssessment ->
						assessSuperObject( object, multiReasonProvisoryTypeAssessment, provisorySuperclassAssessment.superclassAssessment, visitedValues );
					case ProvisoryFieldProvisoryTypeAssessment provisoryFieldAssessment ->
						assessField( object, multiReasonProvisoryTypeAssessment, provisoryFieldAssessment.fieldAssessment, visitedValues );
					default -> throw new AssertionError( provisoryReason );
				};
			if( !(objectAssessment instanceof ImmutableObjectAssessment) )
				return objectAssessment;
		}
		return ImmutableObjectAssessment.instance;
	}

	private ObjectAssessment assessSuperObject( Object object, ProvisoryTypeAssessment provisoryTypeAssessment, ProvisoryTypeAssessment superTypeAssessment, Set<Object> visitedValues )
	{
		ObjectAssessment superObjectAssessment = assessRecursively( object, superTypeAssessment, visitedValues );
		if( superObjectAssessment instanceof MutableObjectAssessment mutableSuperObjectAssessment )
			return new MutableSuperObjectMutableObjectAssessment( object, provisoryTypeAssessment, mutableSuperObjectAssessment );
		assert superObjectAssessment instanceof ImmutableObjectAssessment;
		return superObjectAssessment;
	}

	private ObjectAssessment assessField( Object object, ProvisoryTypeAssessment provisoryTypeAssessment, ProvisoryFieldTypeProvisoryFieldAssessment provisoryFieldAssessment, //
		Set<Object> visitedValues )
	{
		Object fieldValue = MyKit.getFieldValue( object, provisoryFieldAssessment.field );
		ObjectAssessment fieldValueAssessment = switch( provisoryFieldAssessment.fieldTypeAssessment )
		{
			case ArrayOfProvisoryElementTypeProvisoryTypeAssessment assessment -> assessInvariableArray( fieldValue, assessment, visitedValues );
			default -> assessRecursively( fieldValue, visitedValues );
		};
		if( fieldValueAssessment instanceof MutableObjectAssessment mutableFieldValueAssessment )
			return new MutableFieldValueMutableObjectAssessment( object, provisoryTypeAssessment, provisoryFieldAssessment, mutableFieldValueAssessment );
		assert fieldValueAssessment instanceof ImmutableObjectAssessment;
		return ImmutableObjectAssessment.instance;
	}

	private ObjectAssessment assessInvariableArray( Object array, ProvisoryTypeAssessment arrayTypeAssessment, Set<Object> visitedValues )
	{
		int index = 0;
		for( Object element : new IterableOnArrayObject( array ) )
		{
			ObjectAssessment elementAssessment = assessRecursively( element, visitedValues );
			if( elementAssessment instanceof MutableObjectAssessment mutableElementAssessment )
				return new MutableArrayElementMutableObjectAssessment( array, arrayTypeAssessment, index, mutableElementAssessment );
			index++;
		}
		return ImmutableObjectAssessment.instance;
	}
}
