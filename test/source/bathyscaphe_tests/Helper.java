package bathyscaphe_tests;

import io.github.mikenakis.bathyscaphe.ObjectAssessor;
import io.github.mikenakis.bathyscaphe.exceptions.ObjectMustBeImmutableException;
import io.github.mikenakis.bathyscaphe.internal.assessments.ImmutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.ObjectAssessment;
import io.github.mikenakis.bathyscaphe.print.AssessmentPrinter;

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
