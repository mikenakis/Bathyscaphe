/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
 */

package io.github.mikenakis.bathyscaphe.internal.helpers;

import java.util.Iterator;
import java.util.function.Function;

/**
 * A decorator of {@link Iterable} which uses a converter to convert elements from one type to another.
 *
 * @param <T>
 * @param <F>
 *
 * @author michael.gr
 */
public class ConvertingIterable<T, F> implements Iterable<T>
{
	public final Iterable<F> sourceIterable;
	public final Function<F,T> converter;

	public ConvertingIterable( Iterable<F> sourceIterable, Function<F,T> converter )
	{
		this.sourceIterable = sourceIterable;
		this.converter = converter;
	}

	@Override public Iterator<T> iterator()
	{
		Iterator<F> sourceIterator = sourceIterable.iterator();
		return new Iterator<>()
		{
			@Override public boolean hasNext()
			{
				return sourceIterator.hasNext();
			}
			@Override public T next()
			{
				F sourceElement = sourceIterator.next();
				return converter.apply( sourceElement );
			}
		};
	}
}
