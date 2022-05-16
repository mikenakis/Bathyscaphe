package mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory;

import mikenakis.bathyscaphe.internal.type.Decomposer;
import mikenakis.bathyscaphe.internal.type.assessments.TypeAssessment;

/**
 * Signifies that a class is provisory because it is composite, so its components will need to be assessed.
 * <p>
 * This is used for classes that are not iterable but contain elements, i.e. implementations of {@link java.util.Map}, which is conceptually a collection of
 * entries, but does not extend {@link java.util.Collection<java.util.Map.Entry>} and instead offers an {@link java.util.Map#entrySet()} method.
 * <p>
 * If the mode is {@link TypeAssessment.Mode#Assessed} then the class has been assessed to be immutable in all other respects.
 * <p>
 * If the mode is {@link TypeAssessment.Mode#Preassessed} then this assessment overrules an assessment that would normally come out as mutable, and promises
 * that this class will behave as immutable, with the caveat that it is iterable.
 */
public final class CompositeProvisoryTypeAssessment<T, E> extends ProvisoryTypeAssessment
{
	public final Mode mode;
	public final Decomposer<T,E> decomposer;

	public CompositeProvisoryTypeAssessment( Mode mode, Class<T> type, Decomposer<T,E> decomposer )
	{
		super( type );
		this.mode = mode;
		this.decomposer = decomposer;
	}
}
