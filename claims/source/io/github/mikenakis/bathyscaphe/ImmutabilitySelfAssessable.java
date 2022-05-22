/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.txt.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe;

/**
 * Represents an object that is capable of assessing by itself whether it is immutable or not.
 *
 * @author michael.gr
 */
public interface ImmutabilitySelfAssessable
{
	boolean isImmutable();
}
