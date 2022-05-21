/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under the APACHE-2.0 license; see LICENSE.md for details.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents a promise that the elements of an array, which are non-final by nature, can be considered as final, so that the declaring class can be assessed as
 * immutable if it meets all other requirements for immutability.
 * <p>
 * This is essentially a promise that the array will be used in such a way that will not affect the immutability of the declaring class, for example:
 * <p>
 * - Array elements will be initialized in the constructor of the declaring class and will never be written again once the constructor returns.
 * <p>
 * - Array elements may be written by methods other than the constructor, but each element will be written at most once, and will never be read before it is
 * written.
 * <p>
 * - Array elements may be written multiple times, but in a thread-safe way, and they do not participate in the hashCode computation.
 * <p>
 * For example, in a hypothetical re-implementation of class {@link String} the array of characters would be marked with this annotation, thus allowing the
 * string class to be assessed as immutable.
 * <p>
 * Note that this annotation only makes sense on array fields that are {@code private} and are either {@code final} or annotated with {@link Invariable}.
 * <p>
 * Note that this annotation only promises shallow immutability; Bathyscaphe will also examine the type of the array elements and possibly also the actual value
 * of each array element in order to assess deep immutability.
 * <p>
 * Also see jdk.internal.vm.annotation.Stable which gives a similar promise but for slightly different purposes. (Strangely enough, in {@link java.lang.String}
 * even though the character array is marked with jdk.internal.vm.annotation.Stable, the cached hashcode is not; I do not know why.)
 * <p>
 *
 * @author michael.gr
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface InvariableArray
{ }
