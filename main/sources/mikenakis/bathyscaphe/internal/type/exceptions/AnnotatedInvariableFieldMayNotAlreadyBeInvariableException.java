package mikenakis.bathyscaphe.internal.type.exceptions;

import mikenakis.bathyscaphe.annotations.Invariable;
import mikenakis.bathyscaphe.internal.mykit.UncheckedException;

import java.lang.reflect.Field;

/**
 * Thrown when a {@code final} field is annotated with @{@link Invariable}.
 * A {@code final} final need not, and should not, be annotated with @{@link Invariable}.
 *
 * @author michael.gr
 */
public class AnnotatedInvariableFieldMayNotAlreadyBeInvariableException extends UncheckedException
{
	public final Field field;

	public AnnotatedInvariableFieldMayNotAlreadyBeInvariableException( Field field )
	{
		this.field = field;
	}
}
