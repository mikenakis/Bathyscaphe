package mikenakis.bathyscaphe.internal.type.assessments.nonimmutable;

import mikenakis.bathyscaphe.internal.type.assessments.TypeAssessment;

/**
 * Signifies that a class is mutable.
 */
public abstract class NonImmutableTypeAssessment extends TypeAssessment
{
	public final Class<?> type;

	protected NonImmutableTypeAssessment( Class<?> type ) { this.type = type; }
}
