package mikenakis.bathyscaphe.internal.type.field;

import mikenakis.bathyscaphe.annotations.Invariable;
import mikenakis.bathyscaphe.annotations.InvariableArray;
import mikenakis.bathyscaphe.internal.type.TypeAssessor;
import mikenakis.bathyscaphe.internal.type.assessments.ImmutableTypeAssessment;
import mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.ArrayOfMutableElementTypeMutableTypeAssessment;
import mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.MutableTypeAssessment;
import mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ArrayOfProvisoryElementTypeProvisoryTypeAssessment;
import mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisoryTypeAssessment;
import mikenakis.bathyscaphe.internal.type.assessments.TypeAssessment;
import mikenakis.bathyscaphe.internal.type.assessments.UnderAssessmentTypeAssessment;
import mikenakis.bathyscaphe.internal.type.exceptions.AnnotatedInvariableArrayFieldMustBePrivateException;
import mikenakis.bathyscaphe.internal.type.exceptions.AnnotatedInvariableFieldMayNotAlreadyBeInvariableException;
import mikenakis.bathyscaphe.internal.type.exceptions.AnnotatedInvariableFieldMustBePrivateException;
import mikenakis.bathyscaphe.internal.type.exceptions.NonArrayFieldMayNotBeAnnotatedInvariableArrayException;
import mikenakis.bathyscaphe.internal.type.exceptions.VariableFieldMayNotBeAnnotatedInvariableArrayException;
import mikenakis.bathyscaphe.internal.type.field.assessments.FieldAssessment;
import mikenakis.bathyscaphe.internal.type.field.assessments.ImmutableFieldAssessment;
import mikenakis.bathyscaphe.internal.type.field.assessments.UnderAssessmentFieldAssessment;
import mikenakis.bathyscaphe.internal.type.field.assessments.mutable.ArrayMutableFieldAssessment;
import mikenakis.bathyscaphe.internal.type.field.assessments.mutable.MutableFieldTypeMutableFieldAssessment;
import mikenakis.bathyscaphe.internal.type.field.assessments.mutable.VariableMutableFieldAssessment;
import mikenakis.bathyscaphe.internal.type.field.assessments.provisory.ProvisoryFieldTypeProvisoryFieldAssessment;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Deeply assesses the nature of files. DO NOT USE; FOR INTERNAL USE ONLY.
 * <p>
 * For now, fields can be of mutable, immutable, or provisory nature.
 * <p>
 * Provisory means that the field cannot be assessed as mutable or immutable by just looking at the field, so the value of the field will need to be assessed.
 */
public class FieldAssessor
{
	private final TypeAssessor typeAssessor;

	/**
	 * Constructor. DO NOT USE; FOR INTERNAL USE ONLY.
	 */
	public FieldAssessor( TypeAssessor typeAssessor )
	{
		this.typeAssessor = typeAssessor;
	}

	public FieldAssessment assessField( Field field )
	{
		assert !Modifier.isStatic( field.getModifiers() );
		boolean isInvariableField = isInvariableField( field );
		Class<?> fieldType = field.getType();
		boolean isArray = fieldType.isArray();
		boolean isInvariableArray = field.isAnnotationPresent( InvariableArray.class );
		if( !isArray && isInvariableArray )
			throw new NonArrayFieldMayNotBeAnnotatedInvariableArrayException( field );
		else if( !isInvariableField && isInvariableArray )
			throw new VariableFieldMayNotBeAnnotatedInvariableArrayException( field );
		else if( !isInvariableField )
			return new VariableMutableFieldAssessment( field );
		else if( isArray && !isInvariableArray )
			return new ArrayMutableFieldAssessment( field );
		else if( isArray )
		{
			assert isArray && isInvariableArray;
			if( !Modifier.isPrivate( field.getModifiers() ) )
				throw new AnnotatedInvariableArrayFieldMustBePrivateException( field );
			TypeAssessment arrayElementTypeAssessment = typeAssessor.assess( fieldType.getComponentType() );
			return switch( arrayElementTypeAssessment )
				{
					case UnderAssessmentTypeAssessment ignore -> UnderAssessmentFieldAssessment.instance;
					case ProvisoryTypeAssessment provisoryTypeAssessment ->
						new ProvisoryFieldTypeProvisoryFieldAssessment( field, new ArrayOfProvisoryElementTypeProvisoryTypeAssessment( fieldType, provisoryTypeAssessment ) );
					case ImmutableTypeAssessment ignore -> ImmutableFieldAssessment.instance;
					case MutableTypeAssessment mutableTypeAssessment ->
						new MutableFieldTypeMutableFieldAssessment( field, new ArrayOfMutableElementTypeMutableTypeAssessment( fieldType, mutableTypeAssessment ) );
					//DoNotCover
					default -> throw new AssertionError( arrayElementTypeAssessment );
				};
		}
		TypeAssessment fieldTypeAssessment = typeAssessor.assess( fieldType );
		return switch( fieldTypeAssessment )
			{
				case UnderAssessmentTypeAssessment ignore -> UnderAssessmentFieldAssessment.instance;
				case ProvisoryTypeAssessment provisoryTypeAssessment -> new ProvisoryFieldTypeProvisoryFieldAssessment( field, provisoryTypeAssessment );
				case ImmutableTypeAssessment ignore -> ImmutableFieldAssessment.instance;
				case MutableTypeAssessment mutableTypeAssessment -> new MutableFieldTypeMutableFieldAssessment( field, mutableTypeAssessment );
				//DoNotCover
				default -> throw new AssertionError( fieldTypeAssessment );
			};
	}

	public static boolean isInvariableField( Field field )
	{
		int fieldModifiers = field.getModifiers();
		assert !Modifier.isStatic( fieldModifiers );
		boolean isAnnotatedInvariable = field.isAnnotationPresent( Invariable.class );
		boolean isPrivate = Modifier.isPrivate( fieldModifiers );
		if( isAnnotatedInvariable && !isPrivate )
			throw new AnnotatedInvariableFieldMustBePrivateException( field );
		boolean isDeclaredInvariable = Modifier.isFinal( fieldModifiers );
		if( isDeclaredInvariable && isAnnotatedInvariable )
			throw new AnnotatedInvariableFieldMayNotAlreadyBeInvariableException( field );
		return isDeclaredInvariable || isAnnotatedInvariable;
	}
}