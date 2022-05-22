/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.txt.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * For fields:
 * <p>
 * Represents a promise that a field will mutate in a thread-safe way. Can only be used on private, non-final fields. Note that the promise is given only with
 * regard to the field, and not to whatever object might be referenced by the field, which will need to be assessed separately.
 * <p>
 * For classes:
 * <p>
 * Represents a promise that the class will always behave in a thread-safe way.
 * <p>
 * For interfaces:
 * <p>
 * represents a promise that all classes implementing the interface will always behave in a thread-safe way.
 *
 * @author michael.gr
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( {ElementType.FIELD, ElementType.TYPE} )
public @interface ThreadSafe
{ }
