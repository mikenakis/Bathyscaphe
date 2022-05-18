package io.github.mikenakis.bathyscaphe;

import io.github.mikenakis.bathyscaphe.internal.ObjectAssessor;
import io.github.mikenakis.bathyscaphe.internal.assessments.ImmutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.MutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.ObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.mykit.collections.IdentityLinkedHashSet;

import java.util.Set;

/**
 * Deeply assesses the nature of objects.
 *
 * @author michael.gr
 */
public final class Bathyscaphe
{
	public static boolean objectMustBeImmutableAssertion( Object object )
	{
		Set<Object> visitedValues = new IdentityLinkedHashSet<>();
		ObjectAssessment assessment = ObjectAssessor.instance.assessRecursively( object, visitedValues );
		if( assessment instanceof MutableObjectAssessment mutableObjectAssessment )
			throw new ObjectMustBeImmutableException( mutableObjectAssessment );
		assert assessment instanceof ImmutableObjectAssessment;
		return true;
	}

	public static void addImmutablePreassessment( Class<?> jvmClass )
	{
		ObjectAssessor.instance.addImmutablePreassessment( jvmClass );
	}
}
