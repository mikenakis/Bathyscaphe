/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory;

import io.github.mikenakis.bathyscaphe.internal.assessments.Assessment;
import io.github.mikenakis.bathyscaphe.internal.type.Decomposer;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.TypeAssessment;

import java.util.List;

/**
 * Signifies that a class is provisory because it is composite, so its components will need to be assessed.
 * <p>
 * This is used for classes that are not iterable but contain elements, i.e. implementations of {@link java.util.Map}, which is conceptually a collection of
 * entries, but does not extend {@link java.util.Collection<java.util.Map.Entry>} and instead offers an {@link java.util.Map#entrySet()} method.
 * <p>
 * If the mode is {@link TypeAssessment.Mode#Assessed} then the class has been assessed to be immutable in all other respects.
 * <p>
 * If the mode is {@link TypeAssessment.Mode#Preassessed} then this assessment overrules an assessment that would normally come out as mutable, and promises
 * that this class will behave as immutable, with the provision that its components must be assessed and must be found to also be mutable.
 *
 * @author michael.gr
 */
public final class CompositeProvisoryTypeAssessment<T, E> extends ProvisoryTypeAssessment
{
	public final Mode mode;
	public final ProvisoryTypeAssessment componentTypeAssessment;
	public final Decomposer<T,E> decomposer;

	public CompositeProvisoryTypeAssessment( Mode mode, Class<T> type, ProvisoryTypeAssessment componentTypeAssessment, Decomposer<T,E> decomposer )
	{
		super( type, true );
		this.mode = mode;
		this.componentTypeAssessment = componentTypeAssessment;
		this.decomposer = decomposer;
	}

	@Override public List<Assessment> children() { return List.of( componentTypeAssessment ); }
}
