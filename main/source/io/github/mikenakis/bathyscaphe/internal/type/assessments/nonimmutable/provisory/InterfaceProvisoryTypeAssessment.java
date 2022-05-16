package io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory;

/**
 * Signifies that a type is provisory because it is an interface.
 */
public final class InterfaceProvisoryTypeAssessment extends ProvisoryTypeAssessment
{
	public InterfaceProvisoryTypeAssessment( Class<?> type )
	{
		super( type );
		assert type.isInterface();
	}
}
