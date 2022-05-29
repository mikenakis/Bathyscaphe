/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.exceptions;

import io.github.mikenakis.bathyscaphe.internal.mykit.UncheckedException;

/**
 * Thrown when an attempt is made to preassess a class as immutable, but the class is found to be extensible.
 * The class should be preassessed as extensible-provisory instead.
 *
 * @author michael.gr
 */
public class PreassessedClassMustNotBeExtensibleException extends UncheckedException
{
	public final Class<?> jvmClass;

	public PreassessedClassMustNotBeExtensibleException( Class<?> jvmClass )
	{
		this.jvmClass = jvmClass;
	}
}
