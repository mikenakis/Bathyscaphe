/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.exceptions;

import io.github.mikenakis.bathyscaphe.annotations.ThreadSafe;
import io.github.mikenakis.bathyscaphe.internal.mykit.UncheckedException;

import java.lang.reflect.Field;

/**
 * Thrown when a non-private field is annotated with @{@link ThreadSafe}.
 * A class is not in a position of making any guarantees about the thread-safety of a field if the field is not private.
 *
 * @author michael.gr
 */
public class AnnotatedThreadSafeFieldMustBePrivateException extends UncheckedException
{
	public final Field field;

	public AnnotatedThreadSafeFieldMustBePrivateException( Field field )
	{
		this.field = field;
	}
}
