/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.field.assessments.nonimmutable.mutable;

import io.github.mikenakis.bathyscaphe.internal.assessments.Assessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.MutableTypeAssessment;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Signifies that a field is mutable because even though it is invariable, it is of a mutable type.
 *
 * @author michael.gr
 */
public final class MutableFieldTypeMutableFieldAssessment extends MutableFieldAssessment
{
	public final MutableTypeAssessment fieldTypeAssessment;

	public MutableFieldTypeMutableFieldAssessment( Field field, boolean threadSafe, MutableTypeAssessment fieldTypeAssessment )
	{
		super( field, threadSafe );
		assert field.getType() == fieldTypeAssessment.type;
		this.fieldTypeAssessment = fieldTypeAssessment;
	}

	@Override public List<Assessment> children() { return List.of( fieldTypeAssessment ); }
}
