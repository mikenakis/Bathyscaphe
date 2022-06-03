/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory;

/**
 * Signifies that a type is provisory because it is an interface.
 *
 * @author michael.gr
 */
public final class InterfaceProvisoryTypeAssessment extends ProvisoryTypeAssessment
{
	public InterfaceProvisoryTypeAssessment( Class<?> type, boolean threadSafe )
	{
		super( type, threadSafe );
		assert type.isInterface();
	}

	@Override public boolean isThreadSafe() { return threadSafe; }
}
