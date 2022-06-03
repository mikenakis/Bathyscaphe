/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type;

import java.util.Map;

/**
 * Decomposes an object by returning an iteration over its constituent components.
 * <p>
 * This interface is necessary for assessing types like {@link Map}, which are not iterable. Classes derived from {@link Map} must be explicitly preassessed
 * as composite, supplying a method that will convert the map to an {@link Iterable} of entries.
 *
 * @param <T> The type of the composite object.
 * @param <E> The type of the constituent components of the composite object.
 *
 * @author michael.gr
 */
public interface Decomposer<T, E>
{
	Iterable<E> decompose( T object );
}
