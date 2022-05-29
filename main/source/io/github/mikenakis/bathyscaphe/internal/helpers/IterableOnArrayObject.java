/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.helpers;

import java.lang.reflect.Array;
import java.util.Iterator;

/**
 * Represents an array object as an {@link Iterable}.
 *
 * @author michael.gr
 */
@SuppressWarnings( "overrides" )
public class IterableOnArrayObject implements Iterable<Object>
{
	private final Object array;
	private final int length;

	public IterableOnArrayObject( Object array )
	{
		assert array.getClass().isArray();
		this.array = array;
		length = Array.getLength( array );
	}

	@Override public Iterator<Object> iterator()
	{
		return new Iterator<>()
		{
			private int index = 0;

			@Override public boolean hasNext()
			{
				return index < length;
			}

			@Override public Object next()
			{
				return Array.get( array, index++ );
			}
		};
	}

	@Override public boolean equals( Object other )
	{
		return other == array || super.equals( other );
	}
}
