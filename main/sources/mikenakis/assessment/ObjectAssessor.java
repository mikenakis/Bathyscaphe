package mikenakis.assessment;

import mikenakis.assessment.internal.assessments.mutable.MutableClassMutableObjectAssessment;
import mikenakis.assessment.exceptions.ObjectMustBeImmutableException;
import mikenakis.assessment.internal.assessments.ImmutableObjectAssessment;
import mikenakis.assessment.internal.assessments.MutableObjectAssessment;
import mikenakis.assessment.internal.assessments.ObjectAssessment;
import mikenakis.assessment.internal.assessments.mutable.MutableArrayElementMutableObjectAssessment;
import mikenakis.assessment.internal.assessments.mutable.MutableComponentMutableObjectAssessment;
import mikenakis.assessment.internal.assessments.mutable.MutableFieldValueMutableObjectAssessment;
import mikenakis.assessment.internal.assessments.mutable.MutableSuperclassMutableObjectAssessment;
import mikenakis.assessment.internal.assessments.mutable.NonEmptyArrayMutableObjectAssessment;
import mikenakis.assessment.internal.assessments.mutable.SelfAssessedMutableObjectAssessment;
import mikenakis.assessment.internal.helpers.IterableOnArrayObject;
import mikenakis.assessment.internal.mykit.MyKit;
import mikenakis.assessment.internal.mykit.collections.IdentityLinkedHashSet;
import mikenakis.assessment.internal.type.TypeAssessor;
import mikenakis.assessment.internal.type.assessments.ImmutableTypeAssessment;
import mikenakis.assessment.internal.type.assessments.TypeAssessment;
import mikenakis.assessment.internal.type.assessments.nonimmutable.mutable.ArrayMutableTypeAssessment;
import mikenakis.assessment.internal.type.assessments.nonimmutable.mutable.MutableTypeAssessment;
import mikenakis.assessment.internal.type.assessments.nonimmutable.provisory.ArrayOfProvisoryElementTypeProvisoryTypeAssessment;
import mikenakis.assessment.internal.type.assessments.nonimmutable.provisory.CompositeProvisoryTypeAssessment;
import mikenakis.assessment.internal.type.assessments.nonimmutable.provisory.ExtensibleProvisoryTypeAssessment;
import mikenakis.assessment.internal.type.assessments.nonimmutable.provisory.MultiReasonProvisoryTypeAssessment;
import mikenakis.assessment.internal.type.assessments.nonimmutable.provisory.ProvisoryFieldProvisoryTypeAssessment;
import mikenakis.assessment.internal.type.assessments.nonimmutable.provisory.ProvisorySuperclassProvisoryTypeAssessment;
import mikenakis.assessment.internal.type.assessments.nonimmutable.provisory.ProvisoryTypeAssessment;
import mikenakis.assessment.internal.type.assessments.nonimmutable.provisory.SelfAssessableProvisoryTypeAssessment;
import mikenakis.assessment.internal.type.field.assessments.provisory.ProvisoryFieldTypeProvisoryFieldAssessment;

import java.lang.reflect.Array;
import java.util.Set;

/**
 * Deeply assesses the nature of objects. For now, objects can be of mutable or immutable nature.
 *
 * @author michael.gr
 */
public final class ObjectAssessor
{
	public static final ObjectAssessor instance = new ObjectAssessor();

	private final TypeAssessor typeAssessor = TypeAssessor.create();

	private ObjectAssessor()
	{
	}

	public void addImmutablePreassessment( Class<?> jvmClass )
	{
		typeAssessor.addImmutablePreassessment( jvmClass );
	}

	public boolean mustBeImmutableAssertion( Object object )
	{
		Set<Object> visitedValues = new IdentityLinkedHashSet<>();
		ObjectAssessment assessment = assessRecursively( object, visitedValues );
		if( assessment instanceof MutableObjectAssessment mutableObjectAssessment )
			throw new ObjectMustBeImmutableException( mutableObjectAssessment );
		assert assessment instanceof ImmutableObjectAssessment;
		return true;
	}

	private <T> ObjectAssessment assessRecursively( T object, Set<Object> visitedValues )
	{
		if( object == null )
			return ImmutableObjectAssessment.instance;
		if( visitedValues.contains( object ) )
			return ImmutableObjectAssessment.instance;
		visitedValues.add( object );
		Class<T> declaredClass = MyKit.getClass( object );
		TypeAssessment typeAssessment = typeAssessor.assess( declaredClass );
		return assessRecursively( object, typeAssessment, visitedValues );
	}

	private <T> ObjectAssessment assessRecursively( T object, TypeAssessment typeAssessment, Set<Object> visitedValues )
	{
		return switch( typeAssessment )
			{
				case ImmutableTypeAssessment ignore -> ImmutableObjectAssessment.instance;
				//Class is extensible but otherwise immutable, and object is of this exact class and not of a further derived class, so object is immutable.
				case ExtensibleProvisoryTypeAssessment ignore -> ImmutableObjectAssessment.instance;
				case CompositeProvisoryTypeAssessment<?,?> provisoryCompositeAssessment ->
					assessComposite( object, provisoryCompositeAssessment, visitedValues );
				case SelfAssessableProvisoryTypeAssessment selfAssessableAssessment ->
					assessSelfAssessable( selfAssessableAssessment, (ImmutabilitySelfAssessable)object );
				case MultiReasonProvisoryTypeAssessment multiReasonAssessment -> assessMultiReason( object, multiReasonAssessment, visitedValues );
				case ProvisorySuperclassProvisoryTypeAssessment provisorySuperclassAssessment ->
					assessSuperclass( object, provisorySuperclassAssessment, provisorySuperclassAssessment.superclassAssessment, visitedValues );
				case ProvisoryFieldProvisoryTypeAssessment provisoryFieldAssessment ->
					assessField( object, provisoryFieldAssessment, provisoryFieldAssessment.fieldAssessment, visitedValues );
				case ArrayMutableTypeAssessment arrayAssessment -> assessArray( object, arrayAssessment );
				case MutableTypeAssessment mutableTypeAssessment -> new MutableClassMutableObjectAssessment( object, mutableTypeAssessment );
				default -> throw new AssertionError( typeAssessment );
			};
	}

	private static ObjectAssessment assessArray( Object arrayObject, ArrayMutableTypeAssessment mutableArrayAssessment )
	{
		if( Array.getLength( arrayObject ) == 0 )
			return ImmutableObjectAssessment.instance;
		return new NonEmptyArrayMutableObjectAssessment( arrayObject, mutableArrayAssessment );
	}

	private <T, E> ObjectAssessment assessComposite( T compositeObject, CompositeProvisoryTypeAssessment<?,?> wildcardTypeAssessment, Set<Object> visitedValues )
	{
		@SuppressWarnings( "unchecked" ) CompositeProvisoryTypeAssessment<T,E> typeAssessment = (CompositeProvisoryTypeAssessment<T,E>)wildcardTypeAssessment;
		Iterable<E> iterableObject = typeAssessment.decomposer.decompose( compositeObject );
		int index = 0;
		for( var element : iterableObject )
		{
			ObjectAssessment elementAssessment = assessRecursively( element, visitedValues );
			if( elementAssessment instanceof MutableObjectAssessment mutableObjectAssessment )
				return new MutableComponentMutableObjectAssessment<>( compositeObject, typeAssessment, index, mutableObjectAssessment );
			assert elementAssessment instanceof ImmutableObjectAssessment;
			index++;
		}
		return ImmutableObjectAssessment.instance;
	}

	private static ObjectAssessment assessSelfAssessable( SelfAssessableProvisoryTypeAssessment typeAssessment, ImmutabilitySelfAssessable selfAssessableObject )
	{
		if( selfAssessableObject.isImmutable() )
			return ImmutableObjectAssessment.instance;
		return new SelfAssessedMutableObjectAssessment( typeAssessment, selfAssessableObject );
	}

	private ObjectAssessment assessMultiReason( Object object, MultiReasonProvisoryTypeAssessment multiReasonProvisoryTypeAssessment, Set<Object> visitedValues )
	{
		for( ProvisoryTypeAssessment provisoryReason : multiReasonProvisoryTypeAssessment.provisoryReasons )
		{
			ObjectAssessment objectAssessment = switch( provisoryReason )
				{
					case ProvisorySuperclassProvisoryTypeAssessment provisorySuperclassAssessment ->
						assessSuperclass( object, multiReasonProvisoryTypeAssessment, provisorySuperclassAssessment.superclassAssessment, visitedValues );
					case ProvisoryFieldProvisoryTypeAssessment provisoryFieldAssessment ->
						assessField( object, multiReasonProvisoryTypeAssessment, provisoryFieldAssessment.fieldAssessment, visitedValues );
					default -> throw new AssertionError( provisoryReason );
				};
			if( !(objectAssessment instanceof ImmutableObjectAssessment) )
				return objectAssessment;
		}
		return ImmutableObjectAssessment.instance;
	}

	private ObjectAssessment assessSuperclass( Object object, ProvisoryTypeAssessment provisoryTypeAssessment, ProvisoryTypeAssessment superTypeAssessment, Set<Object> visitedValues )
	{
		ObjectAssessment superObjectAssessment = assessRecursively( object, superTypeAssessment, visitedValues );
		if( superObjectAssessment instanceof MutableObjectAssessment mutableSuperObjectAssessment )
			return new MutableSuperclassMutableObjectAssessment( object, provisoryTypeAssessment, mutableSuperObjectAssessment );
		assert superObjectAssessment instanceof ImmutableObjectAssessment;
		return superObjectAssessment;
	}

	private ObjectAssessment assessField( Object object, ProvisoryTypeAssessment provisoryTypeAssessment, ProvisoryFieldTypeProvisoryFieldAssessment provisoryFieldAssessment, //
		Set<Object> visitedValues )
	{
		Object fieldValue = MyKit.getFieldValue( object, provisoryFieldAssessment.field );
		ObjectAssessment fieldValueAssessment = switch( provisoryFieldAssessment.fieldTypeAssessment )
		{
			case ArrayOfProvisoryElementTypeProvisoryTypeAssessment assessment -> assessInvariableArrayField( fieldValue, assessment, visitedValues );
			default -> assessRecursively( fieldValue, visitedValues );
		};
		if( fieldValueAssessment instanceof MutableObjectAssessment mutableFieldValueAssessment )
			return new MutableFieldValueMutableObjectAssessment( object, provisoryTypeAssessment, provisoryFieldAssessment, mutableFieldValueAssessment );
		assert fieldValueAssessment instanceof ImmutableObjectAssessment;
		return ImmutableObjectAssessment.instance;
	}

	private ObjectAssessment assessInvariableArrayField( Object array, ProvisoryTypeAssessment arrayTypeAssessment, Set<Object> visitedValues )
	{
		int index = 0;
		for( Object element : new IterableOnArrayObject( array ) )
		{
			ObjectAssessment elementAssessment = assessRecursively( element, visitedValues );
			if( elementAssessment instanceof MutableObjectAssessment mutableElementAssessment )
				return new MutableArrayElementMutableObjectAssessment( array, arrayTypeAssessment, index, mutableElementAssessment );
			index++;
		}
		return ImmutableObjectAssessment.instance;
	}
}
