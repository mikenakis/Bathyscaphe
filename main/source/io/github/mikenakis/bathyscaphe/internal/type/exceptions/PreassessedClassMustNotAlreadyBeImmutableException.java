/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
 */

package io.github.mikenakis.bathyscaphe.internal.type.exceptions;

import io.github.mikenakis.bathyscaphe.internal.mykit.UncheckedException;

/**
 * Thrown when a class is preassessed as immutable, but it is found to already be immutable.
 * An already immutable class need not, and should not, be preassessed as immutable.
 *
 * @author michael.gr
 */
public class PreassessedClassMustNotAlreadyBeImmutableException extends UncheckedException
{
	public final Class<?> jvmClass;

	public PreassessedClassMustNotAlreadyBeImmutableException( Class<?> jvmClass )
	{
		this.jvmClass = jvmClass;
	}
}
