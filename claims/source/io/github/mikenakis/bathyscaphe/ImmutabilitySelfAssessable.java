/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under the APACHE-2.0 license; see LICENSE.md for details.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe;

/**
 * Represents an object that is capable of assessing by itself whether it is immutable or not.
 */
public interface ImmutabilitySelfAssessable
{
	boolean isImmutable();
}
