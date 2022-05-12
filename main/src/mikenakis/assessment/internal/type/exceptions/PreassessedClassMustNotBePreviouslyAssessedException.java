package mikenakis.assessment.internal.type.exceptions;

import mikenakis.assessment.internal.type.assessments.TypeAssessment;
import mikenakis.assessment.internal.mykit.UncheckedException;

/**
 * Thrown when an attempt is made to preassess a class, but there has already been a request to assess this class, and the answer that was given was that the
 * class is mutable. The order of operations should be changed so as to avoid this inconsistency.
 *
 * @author michael.gr
 */
public class PreassessedClassMustNotBePreviouslyAssessedException extends UncheckedException
{
	public final TypeAssessment previousTypeAssessment;

	public PreassessedClassMustNotBePreviouslyAssessedException( TypeAssessment previousTypeAssessment )
	{
		this.previousTypeAssessment = previousTypeAssessment;
	}
}
