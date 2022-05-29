/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
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
