/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.assessments;

import java.util.List;

/**
 * Common base class for all assessments. Exists just so that we can create trees of assessments.
 *
 * @author michael.gr
 */
public abstract class Assessment
{
	protected Assessment()
	{
	}

	public List<Assessment> children() { return List.of(); }
}
