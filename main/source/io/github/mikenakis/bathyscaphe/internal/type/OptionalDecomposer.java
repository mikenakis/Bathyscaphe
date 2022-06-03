/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type;

import java.util.List;
import java.util.Optional;

class OptionalDecomposer implements Decomposer<Optional<?>,Object>
{
	public static Decomposer<Optional<?>,Object> instance = new OptionalDecomposer();

	private OptionalDecomposer()
	{
	}

	@Override public Iterable<Object> decompose( Optional<?> optional )
	{
		if( optional.isEmpty() )
			return List.of();
		return List.of( optional.get() );
	}
}
