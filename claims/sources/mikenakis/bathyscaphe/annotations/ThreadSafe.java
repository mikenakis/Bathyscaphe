package mikenakis.bathyscaphe.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * For fields:
 * <p>
 * Represents a promise that a non-final field will be accessed in a thread-safe way, so that the declaring class can be assessed as thread-safe if it meets all
 * other requirements for thread-safety.
 * <p>
 * For classes and interfaces:
 * <p>
 * Represents a promise that the class or interface will behave in a thread-safe way.
 *
 * @author michael.gr
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( {ElementType.FIELD, ElementType.TYPE} )
public @interface ThreadSafe
{ }
