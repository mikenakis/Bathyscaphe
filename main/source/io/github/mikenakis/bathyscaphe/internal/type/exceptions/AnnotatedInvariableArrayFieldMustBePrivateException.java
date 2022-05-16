package io.github.mikenakis.bathyscaphe.internal.type.exceptions;

import io.github.mikenakis.bathyscaphe.annotations.InvariableArray;
import io.github.mikenakis.bathyscaphe.internal.mykit.UncheckedException;

import java.lang.reflect.Field;

/**
 * Thrown when a non-private field is annotated with @{@link InvariableArray}.
 * A class is not in a position of making any guarantees about the invariability of a field if the field is not private.
 *
 * @author michael.gr
 */
public class AnnotatedInvariableArrayFieldMustBePrivateException extends UncheckedException
{
	public final Field field;

	public AnnotatedInvariableArrayFieldMustBePrivateException( Field field )
	{
		this.field = field;
	}
}
