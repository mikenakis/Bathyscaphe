/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.exceptions;

import io.github.mikenakis.bathyscaphe.annotations.InvariableArray;
import io.github.mikenakis.bathyscaphe.internal.mykit.UncheckedException;

import java.lang.reflect.Field;

/**
 * Thrown if a non-array field is annotated with the @{@link InvariableArray} annotation.
 * The @{@link InvariableArray} annotation is only valid for array fields.
 *
 * @author michael.gr
 */
public class NonArrayFieldMayNotBeAnnotatedInvariableArrayException extends UncheckedException
{
	public final Field field;

	public NonArrayFieldMayNotBeAnnotatedInvariableArrayException( Field field )
	{
		this.field = field;
	}
}
