/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
 */

package io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory;

/**
 * Signifies that a type is provisory because it is an interface.
 *
 * @author michael.gr
 */
public final class InterfaceProvisoryTypeAssessment extends ProvisoryTypeAssessment
{
	public InterfaceProvisoryTypeAssessment( Class<?> type )
	{
		super( type );
		assert type.isInterface();
	}
}
