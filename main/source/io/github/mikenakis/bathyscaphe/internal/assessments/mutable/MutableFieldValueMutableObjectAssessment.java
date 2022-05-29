/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.assessments.mutable;

import io.github.mikenakis.bathyscaphe.internal.assessments.Assessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.MutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.mykit.MyKit;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.NonImmutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.provisory.ProvisoryFieldTypeProvisoryFieldAssessment;

import java.util.List;

/**
 * Signifies that an object is mutable because it contains a provisory field which has mutable value.
 *
 * @author michael.gr
 */
public final class MutableFieldValueMutableObjectAssessment extends MutableObjectAssessment
{
	public final Object object;
	public final ProvisoryTypeAssessment declaringTypeAssessment;
	public final ProvisoryFieldTypeProvisoryFieldAssessment provisoryFieldAssessment;
	public final MutableObjectAssessment fieldValueAssessment;

	public MutableFieldValueMutableObjectAssessment( Object object, ProvisoryTypeAssessment declaringTypeAssessment, ProvisoryFieldTypeProvisoryFieldAssessment provisoryFieldAssessment, //
		MutableObjectAssessment fieldValueAssessment )
	{
		assert declaringTypeAssessment.type.isAssignableFrom( object.getClass() );
		assert declaringTypeAssessment.type == provisoryFieldAssessment.field.getDeclaringClass();
		assert List.of( declaringTypeAssessment.type.getDeclaredFields() ).contains( provisoryFieldAssessment.field );
		assert fieldValueAssessment.object().equals( MyKit.getFieldValue( object, provisoryFieldAssessment.field ) );
		this.object = object;
		this.declaringTypeAssessment = declaringTypeAssessment;
		this.provisoryFieldAssessment = provisoryFieldAssessment;
		this.fieldValueAssessment = fieldValueAssessment;
	}

	@Override public Object object() { return object; }
	@Override public NonImmutableTypeAssessment typeAssessment() { return declaringTypeAssessment; }
	@Override public List<Assessment> children() { return List.of( declaringTypeAssessment/*, provisoryFieldAssessment*/, fieldValueAssessment ); }
}
