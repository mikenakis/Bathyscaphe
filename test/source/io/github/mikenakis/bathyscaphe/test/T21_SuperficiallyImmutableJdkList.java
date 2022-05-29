/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.test;

import io.github.mikenakis.bathyscaphe.internal.assessments.ImmutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.MutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.ObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.mutable.MutableComponentMutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.mykit.MyKit;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Test.
 *
 * @author michael.gr
 */
@SuppressWarnings( { "FieldMayBeFinal", "InstanceVariableMayNotBeInitialized" } )
public class T21_SuperficiallyImmutableJdkList
{
	public T21_SuperficiallyImmutableJdkList()
	{
		if( !MyKit.areAssertionsEnabled() )
			throw new AssertionError();
	}

	/**
	 * Make sure that the JDK is using the same jdk-internal superficially-immutable list class for any number of elements above 3.
	 */
	@Test public void superficially_immutable_jdk_list_class_for_3_elements_is_same_as_for_N_elements()
	{
		assert MyKit.getClass( List.of( 1, 2, 3 ) ) == MyKit.getClass( List.of( 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ) );
	}

	@Test public void superficially_immutable_jdk_list_of_size_0_is_actually_immutable()
	{
		Method method = Helper.getCurrentMethod();
		List<?> object = List.of();
		ObjectAssessment assessment = Helper.assess( method, object );
		assert assessment instanceof ImmutableObjectAssessment;
	}

	@Test public void superficially_immutable_jdk_list_of_size_1_with_immutable_elements_is_actually_immutable()
	{
		Method method = Helper.getCurrentMethod();
		List<?> object = List.of( 1 );
		ObjectAssessment assessment = Helper.assess( method, object );
		assert assessment instanceof ImmutableObjectAssessment;
	}

	@Test public void superficially_immutable_jdk_list_of_size_2_with_immutable_elements_is_actually_immutable()
	{
		Method method = Helper.getCurrentMethod();
		List<?> object = List.of( 1, 2 );
		ObjectAssessment assessment = Helper.assess( method, object );
		assert assessment instanceof ImmutableObjectAssessment;
	}

	@Test public void superficially_immutable_jdk_list_of_size_3_with_immutable_elements_is_actually_immutable()
	{
		Method method = Helper.getCurrentMethod();
		List<?> object = List.of( 1, 2, 3 );
		ObjectAssessment assessment = Helper.assess( method, object );
		assert assessment instanceof ImmutableObjectAssessment;
	}

	@Test public void superficially_immutable_jdk_list_of_size_1_with_a_mutable_element_is_actually_mutable()
	{
		Method method = Helper.getCurrentMethod();
		Object mutableElement = new StringBuilder();
		List<?> object = List.of( mutableElement );
		ObjectAssessment assessment = Helper.assess( method, object );
		checkMutableAssessmentOfSuperficiallyImmutableJdkList( mutableElement, object, assessment, 1 );
	}

	@Test public void superficially_immutable_jdk_list_of_size_2_with_a_mutable_element_is_actually_mutable()
	{
		Method method = Helper.getCurrentMethod();
		Object mutableElement = new ArrayList<>();
		List<?> object = List.of( 1, mutableElement );
		ObjectAssessment assessment = Helper.assess( method, object );
		checkMutableAssessmentOfSuperficiallyImmutableJdkList( mutableElement, object, assessment, 2 );
	}

	@Test public void superficially_immutable_jdk_list_of_size_3_with_a_mutable_element_is_actually_mutable()
	{
		Method method = Helper.getCurrentMethod();
		Object mutableElement = new ArrayList<>();
		List<?> object = List.of( 1, 2, mutableElement );
		ObjectAssessment assessment = Helper.assess( method, object );
		checkMutableAssessmentOfSuperficiallyImmutableJdkList( mutableElement, object, assessment, 3 );
	}

	private static void checkMutableAssessmentOfSuperficiallyImmutableJdkList( Object mutableElement, List<?> superficiallyImmutableJdkList, ObjectAssessment assessment, int size )
	{
		assert assessment instanceof MutableObjectAssessment;
		MutableObjectAssessment mutableObjectAssessment = (MutableObjectAssessment)assessment;
		assert mutableObjectAssessment.object() == superficiallyImmutableJdkList;
		assert mutableObjectAssessment.typeAssessment().type == superficiallyImmutableJdkList.getClass();
		MutableComponentMutableObjectAssessment<?,?> mutableComponentAssessment = (MutableComponentMutableObjectAssessment<?,?>)mutableObjectAssessment;
		assert mutableComponentAssessment.elementIndex == size - 1;
		assert mutableComponentAssessment.elementAssessment.object() == mutableElement;
	}
}
