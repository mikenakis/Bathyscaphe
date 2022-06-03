/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory;

import io.github.mikenakis.bathyscaphe.ImmutabilitySelfAssessable;

/**
 * Signifies that a type is provisory because it is self-assessable. (Instances implement the {@link ImmutabilitySelfAssessable} interface.)
 *
 * @author michael.gr
 */
public final class SelfAssessableProvisoryTypeAssessment extends ProvisoryTypeAssessment
{
	public SelfAssessableProvisoryTypeAssessment( Class<?> type, boolean threadSafe )
	{
		super( type, threadSafe );
		assert ImmutabilitySelfAssessable.class.isAssignableFrom( type );
	}

	@Override public boolean isThreadSafe() { return threadSafe; }
}
