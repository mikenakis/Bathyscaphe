/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.assessments.mutable;

import io.github.mikenakis.bathyscaphe.internal.assessments.Assessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.MutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.NonImmutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisorySuperclassProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisoryTypeAssessment;

import java.util.List;

/**
 * Signifies that an object is mutable because its super object is mutable.
 *
 * @author michael.gr
 */
public final class MutableSuperObjectMutableObjectAssessment extends MutableObjectAssessment
{
	public final Object object;
	public final ProvisoryTypeAssessment typeAssessment;
	public final MutableObjectAssessment mutableSuperObjectAssessment;

	public MutableSuperObjectMutableObjectAssessment( Object object, ProvisoryTypeAssessment typeAssessment, MutableObjectAssessment mutableSuperObjectAssessment )
	{
		assert typeAssessment.type == object.getClass();
		assert typeAssessment instanceof ProvisorySuperclassProvisoryTypeAssessment;
		assert mutableSuperObjectAssessment.object() == object;
		this.object = object;
		this.typeAssessment = typeAssessment;
		this.mutableSuperObjectAssessment = mutableSuperObjectAssessment;
	}

	@Override public Object object() { return object; }
	@Override public NonImmutableTypeAssessment typeAssessment() { return typeAssessment; }
	@Override public List<Assessment> children() { return List.of( typeAssessment, mutableSuperObjectAssessment ); }
}
