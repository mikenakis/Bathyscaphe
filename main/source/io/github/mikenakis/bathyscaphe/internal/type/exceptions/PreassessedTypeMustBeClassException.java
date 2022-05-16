package io.github.mikenakis.bathyscaphe.internal.type.exceptions;

import io.github.mikenakis.bathyscaphe.internal.helpers.Helpers;
import io.github.mikenakis.bathyscaphe.internal.mykit.UncheckedException;

/**
 * Thrown when a preassessment is attempted on a type which is an array or interface.
 * Preassessments may only be made on class types.
 *
 * @author michael.gr
 */
public class PreassessedTypeMustBeClassException extends UncheckedException
{
	public final Class<?> type;

	public PreassessedTypeMustBeClassException( Class<?> type )
	{
		assert !Helpers.isClass( type );
		this.type = type;
	}
}
