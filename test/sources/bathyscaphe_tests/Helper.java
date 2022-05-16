package bathyscaphe_tests;

import mikenakis.bathyscaphe.ObjectAssessor;
import mikenakis.bathyscaphe.exceptions.ObjectMustBeImmutableException;
import mikenakis.bathyscaphe.internal.assessments.ImmutableObjectAssessment;
import mikenakis.bathyscaphe.internal.assessments.ObjectAssessment;
import mikenakis.bathyscaphe.print.AssessmentPrinter;

final class Helper
{
	private Helper()
	{
	}

	static ObjectAssessment assess( Object object )
	{
		System.out.println( "assessment for object " + AssessmentPrinter.stringFromObjectIdentity( object ) + ":" );
		ObjectAssessment assessment;
		try
		{
			assert ObjectAssessor.instance.mustBeImmutableAssertion( object );
			assessment = ImmutableObjectAssessment.instance;
		}
		catch( ObjectMustBeImmutableException exception )
		{
			assessment = exception.mutableObjectAssessment;
		}
		catch( Throwable throwable )
		{
			System.out.println( "    " + throwable.getClass().getName() + " : " + throwable.getMessage() );
			throw throwable;
		}
		AssessmentPrinter.getObjectAssessmentTextTree( assessment ).forEach( s -> System.out.println( "    " + s ) );
		return assessment;
	}
}
