package mikenakis.assessment.internal.type.exceptions;

import mikenakis.assessment.annotations.InvariableArray;
import mikenakis.assessment.internal.mykit.UncheckedException;

import java.lang.reflect.Field;

/**
 * Thrown if a variable field is annotated with the @{@link InvariableArray} annotation.
 * The @{@link InvariableArray} annotation is only valid for invariable fields.
 *
 * @author michael.gr
 */
public class VariableFieldMayNotBeAnnotatedInvariableArrayException extends UncheckedException
{
	public final Field field;

	public VariableFieldMayNotBeAnnotatedInvariableArrayException( Field field )
	{
		this.field = field;
	}
}
