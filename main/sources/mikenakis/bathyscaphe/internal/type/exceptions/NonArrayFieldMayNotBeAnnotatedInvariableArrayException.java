package mikenakis.bathyscaphe.internal.type.exceptions;

import mikenakis.bathyscaphe.annotations.InvariableArray;
import mikenakis.bathyscaphe.internal.mykit.UncheckedException;

import java.lang.reflect.Field;

/**
 * Thrown if a non-array field is annotated with the @{@link InvariableArray} annotation.
 * The @{@link InvariableArray} annotation is only valid for array fields.
 *
 * @author michael.gr
 */
public class NonArrayFieldMayNotBeAnnotatedInvariableArrayException extends UncheckedException
{
	public final Field field;

	public NonArrayFieldMayNotBeAnnotatedInvariableArrayException( Field field )
	{
		this.field = field;
	}
}
