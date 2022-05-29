/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.helpers;

import java.lang.reflect.Modifier;

/**
 * Helpers.
 *
 * @author michael.gr
 */
public class Helpers
{
	public static boolean isClass( Class<?> type )
	{
		if( type.isInterface() )
			return false;
		if( type.isArray() )
			return false;
		if( type.isAnnotation() )
			return false;
		return true;
	}

	public static boolean isExtensible( Class<?> type )
	{
		return !Modifier.isFinal( type.getModifiers() );
	}
}
