package io.github.mikenakis.bathyscaphe.internal.helpers;

import java.util.Iterator;
import java.util.function.Function;

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
