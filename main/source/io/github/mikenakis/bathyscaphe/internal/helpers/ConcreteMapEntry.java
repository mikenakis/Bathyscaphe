/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.helpers;

/**
 * Represents a map entry.
 *
 * @param key   the key.
 * @param value the value.
 * @param <K>   the type of the key.
 * @param <V>   the type of the value.
 *
 * @author michael.gr
 */
public record ConcreteMapEntry<K, V>( K key, V value ) { }
