/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
 */

package io.github.mikenakis.bathyscaphe.internal.type.exceptions;

import io.github.mikenakis.bathyscaphe.internal.helpers.Helpers;
import io.github.mikenakis.bathyscaphe.ImmutabilitySelfAssessable;
import io.github.mikenakis.bathyscaphe.internal.mykit.UncheckedException;

/**
 * Thrown if the {@link ImmutabilitySelfAssessable} annotation is placed on an interface.
 *
 * @author michael.gr
 */
public class SelfAssessableAnnotationIsOnlyApplicableToClassException extends UncheckedException
{
	public final Class<?> jvmClass;

	public SelfAssessableAnnotationIsOnlyApplicableToClassException( Class<?> jvmClass )
	{
		assert !Helpers.isClass( jvmClass );
		this.jvmClass = jvmClass;
	}
}
