/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
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
