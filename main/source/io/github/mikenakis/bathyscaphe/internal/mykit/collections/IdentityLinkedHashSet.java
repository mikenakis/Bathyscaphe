/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
 */

package io.github.mikenakis.bathyscaphe.internal.mykit.collections;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * An identity {@link LinkedHashSet}.
 *
 * @param <T> the type of the elements in the set.
 *
 * @author michael.gr
 */
public class IdentityLinkedHashSet<T> extends SetOnMap<T>
{
	public IdentityLinkedHashSet()
	{
		super( new LinkedHashMap<>() );
	}
}
