/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.exceptions;

import io.github.mikenakis.bathyscaphe.annotations.Invariable;
import io.github.mikenakis.bathyscaphe.internal.mykit.UncheckedException;

import java.lang.reflect.Field;

/**
 * Thrown when a {@code final} field is annotated with @{@link Invariable}.
 * A {@code final} final need not, and should not, be annotated with @{@link Invariable}.
 *
 * @author michael.gr
 */
public class AnnotatedInvariableFieldMayNotAlreadyBeInvariableException extends UncheckedException
{
	public final Field field;

	public AnnotatedInvariableFieldMayNotAlreadyBeInvariableException( Field field )
	{
		this.field = field;
	}
}
