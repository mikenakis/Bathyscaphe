/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type;

import io.github.mikenakis.bathyscaphe.internal.helpers.ConcreteMapEntry;
import io.github.mikenakis.bathyscaphe.internal.mykit.MyKit;
import io.github.mikenakis.bathyscaphe.internal.mykit.collections.ConvertingIterable;

import java.util.Collection;
import java.util.Map;

/**
 * PEARL: the JDK {@link Map} interface does not extend {@link Collection} of {@link Map.Entry}, even though conceptually it is a collection of entries.
 * Instead, we have to invoke {@link Map#entrySet()} to obtain the collection of entries.
 */
class JdkMapDecomposer<K, V> implements Decomposer<Map<K,V>,ConcreteMapEntry<K,V>>
{
	private static final JdkMapDecomposer<Object,Object> instance = new JdkMapDecomposer<>();

	static <K, V> Decomposer<Map<K,V>,ConcreteMapEntry<K,V>> instance()
	{
		@SuppressWarnings( "unchecked" ) Decomposer<Map<K,V>,ConcreteMapEntry<K,V>> result = (JdkMapDecomposer<K,V>)instance;
		return result;
	}

	@Override public Iterable<ConcreteMapEntry<K,V>> decompose( Map<K,V> jdkMap )
	{
		/**
		 * PEARL: the decomposer for jdk maps cannot just return {@link Map#entrySet()} because the entry-set is a nested class,
		 * so it has a 'this$0' field, which points back to the map, which is mutable, thus making the entry-set mutable!
		 * So, the decomposer has to iterate the entry-set and yield each element in it.
		 * PEARL: the decomposer cannot just yield each element yielded by the entry-set, because these are instances of {@link java.util.KeyValueHolder}
		 * which cannot be reflected because it is inaccessible! Therefore, the decomposer has to convert each element to a proximal key-value class which
		 * is accessible.
		 */
		Iterable<Map.Entry<K,V>> entrySet = jdkMap.entrySet();
		if( MyKit.areAssertionsEnabled() )
			entrySet = MyKit.streamFromIterable( entrySet ).sorted( JdkMapDecomposer::compareMapEntries ).toList();
		return new ConvertingIterable<>( entrySet, JdkMapDecomposer::mapEntryConverter );
	}

	private static int compareMapEntries( Map.Entry<?,?> entry1, Map.Entry<?,?> entry2 )
	{
		String a1 = entry1.getKey().toString();
		String a2 = entry2.getKey().toString();
		return a1.compareTo( a2 );
	}

	private static <K, V> ConcreteMapEntry<K,V> mapEntryConverter( Map.Entry<K,V> mapEntry )
	{
		return new ConcreteMapEntry<>( mapEntry.getKey(), mapEntry.getValue() );
	}
}
