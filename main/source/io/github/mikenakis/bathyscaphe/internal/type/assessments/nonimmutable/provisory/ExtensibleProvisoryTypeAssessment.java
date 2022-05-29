/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory;

import io.github.mikenakis.bathyscaphe.internal.helpers.Helpers;

/**
 * Signifies that a class is immutable in all regards except that it is extensible.
 * <p>
 * This means that a field with a field-type of this class may receive a value which is of a more derived class, which may be mutable; therefore, the field value of
 * needs to be assessed on each field with a field-type of this class.
 *
 * @author michael.gr
 */
public final class ExtensibleProvisoryTypeAssessment extends ProvisoryTypeAssessment
{
	public final Mode mode;

	public ExtensibleProvisoryTypeAssessment( Mode mode, Class<?> jvmClass )
	{
		super( jvmClass );
		assert Helpers.isClass( jvmClass );
		assert Helpers.isExtensible( jvmClass );
		this.mode = mode;
	}
}
