package mikenakis.bathyscaphe.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents a promise that a certain field which is not declared as {@code final} can be considered as {@code final}, so that the declaring class can be
 * assessed as immutable if it meets all other requirements for immutability.
 * <p>
 * This is essentially a promise that the field will be used in such a way that will not affect the immutability of the declaring class, for example:
 * <p>
 * - the field is written at most once, and it is never read before it is written.
 * <p>
 * - the field is written multiple times, but in a thread-safe way, and the field does not participate in the hashCode computation.
 * <p>
 * For example, in an alternative implementation of class {@link java.lang.String}, the cached hashCode field would be marked with this annotation, thus
 * allowing the class to be assessed as immutable.
 * <p>
 * Note that this annotation only makes sense on fields that are {@code private}.
 * <p>
 * Note that if a field is marked as {@link Invariable}, this only promises shallow immutability; if the type of the field is a reference type, then that type
 * will also be assessed in order to determine whether the field is deeply immutable.
 * <p>
 * Also see jdk.internal.vm.annotation.Stable which gives a similar promise but for slightly different purposes. (Strangely enough, in {@link java.lang.String}
 * even though the character array is marked with jdk.internal.vm.annotation.Stable, the cached hashcode is not; I do not know why.)
 *
 * @author michael.gr
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface Invariable
{ }
