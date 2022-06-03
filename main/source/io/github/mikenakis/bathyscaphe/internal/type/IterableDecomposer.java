/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type;

public final class IterableDecomposer implements Decomposer<Iterable<Object>,Object>
{
	private static final Decomposer<Iterable<Object>,Object> iterableDecomposer = new IterableDecomposer();

	public static <T extends Iterable<E>,E> Decomposer<T,E> instance()
	{
		@SuppressWarnings( "unchecked" ) Decomposer<T,E> result = (Decomposer<T,E>)iterableDecomposer;
		return result;
	}

	@Override public Iterable<Object> decompose( Iterable<Object> object )
	{
		return object;
	}
}
