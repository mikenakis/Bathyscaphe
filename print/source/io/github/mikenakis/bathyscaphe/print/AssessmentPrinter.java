package io.github.mikenakis.bathyscaphe.print;

import io.github.mikenakis.bathyscaphe.annotations.Invariable;
import io.github.mikenakis.bathyscaphe.annotations.InvariableArray;
import io.github.mikenakis.bathyscaphe.internal.assessments.Assessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.ImmutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.MutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.ObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.mutable.MutableSuperclassMutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.mutable.MutableArrayElementMutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.mutable.MutableComponentMutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.mutable.MutableFieldValueMutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.mutable.NonEmptyArrayMutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.mutable.MutableClassMutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.mutable.SelfAssessedMutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.mykit.MyKit;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.ImmutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.TypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.ArrayMutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.ArrayOfMutableElementTypeMutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.MultiReasonMutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.MutableFieldMutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.MutableSuperclassMutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.MutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ArrayOfProvisoryElementTypeProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.CompositeProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ExtensibleProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.InterfaceProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.MultiReasonProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisoryFieldProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisorySuperclassProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.SelfAssessableProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.FieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.mutable.ArrayMutableFieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.mutable.MutableFieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.mutable.MutableFieldTypeMutableFieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.mutable.VariableMutableFieldAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.field.assessments.provisory.ProvisoryFieldTypeProvisoryFieldAssessment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates human-readable text for assessments.
 *
 * @author michael.gr
 */
public final class AssessmentPrinter
{
	public static List<String> getObjectAssessmentTextTree( ObjectAssessment assessment )
	{
		List<String> lines = new ArrayList<>();
		TextTree.tree( assessment, Assessment::children, AssessmentPrinter::getAssessmentText, s -> lines.add( s ) );
		return lines;
	}

	private static String getAssessmentText( Assessment unknownAssessment )
	{
		AssessmentPrinter assessmentPrinter = new AssessmentPrinter();
		switch( unknownAssessment )
		{
			case FieldAssessment assessment -> assessmentPrinter.getFieldAssessmentText( assessment );
			case TypeAssessment assessment -> assessmentPrinter.getTypeAssessmentText( assessment );
			case ObjectAssessment assessment -> assessmentPrinter.getObjectAssessmentText( assessment );
			//DoNotCover
			default -> throw new AssertionError( unknownAssessment );
		}
		assessmentPrinter.append( ". (" + unknownAssessment.getClass().getSimpleName() + ")" );
		return assessmentPrinter.stringBuilder.toString();
	}

	private final StringBuilder stringBuilder = new StringBuilder();

	private AssessmentPrinter()
	{
	}

	private void append( String text )
	{
		stringBuilder.append( text );
	}

	private void getFieldAssessmentText( FieldAssessment fieldAssessment )
	{
		switch( fieldAssessment )
		{
			case MutableFieldAssessment assessment -> getMutableFieldAssessmentText( assessment );
			case ProvisoryFieldTypeProvisoryFieldAssessment assessment -> getProvisoryFieldAssessmentText( assessment );
			//DoNotCover
			default -> throw new AssertionError( fieldAssessment );
		}
	}

	private void getMutableFieldAssessmentText( MutableFieldAssessment mutableFieldAssessment )
	{
		append( "field " + fieldName( mutableFieldAssessment.field ) + " is mutable" );
		switch( mutableFieldAssessment )
		{
			case ArrayMutableFieldAssessment ignored -> append( " because is an array and it has not been annotated with @" + InvariableArray.class.getSimpleName() );
			case MutableFieldTypeMutableFieldAssessment assessment -> append( " because it is of mutable type " + className( assessment.fieldTypeAssessment.type ) );
			case VariableMutableFieldAssessment ignored -> append( " because it is not final and it has not been annotated with @" + Invariable.class.getSimpleName() );
			//DoNotCover
			default -> throw new AssertionError( mutableFieldAssessment );
		}
	}

	private void getProvisoryFieldAssessmentText( ProvisoryFieldTypeProvisoryFieldAssessment provisoryFieldAssessment )
	{
		append( "field " + fieldName( provisoryFieldAssessment.field ) + " is provisory because it is of provisory type " + className( provisoryFieldAssessment.field.getType() ) );
	}

	private void getTypeAssessmentText( TypeAssessment typeAssessment )
	{
		switch( typeAssessment )
		{
			case MutableTypeAssessment assessment -> getMutableTypeAssessmentText( assessment );
			case ProvisoryTypeAssessment assessment -> getProvisoryTypeAssessmentText( assessment );
			case ImmutableTypeAssessment ignore -> append( "immutable" );
			//DoNotCover
			default -> throw new AssertionError( typeAssessment );
		}
	}

	private void getMutableTypeAssessmentText( MutableTypeAssessment mutableTypeAssessment )
	{
		append( "class " + className( mutableTypeAssessment.type ) + " is mutable" );
		switch( mutableTypeAssessment )
		{
			case ArrayMutableTypeAssessment ignore -> append( " because it is an array class" );
			case MultiReasonMutableTypeAssessment ignore -> append( " due to multiple reasons" );
			case MutableFieldMutableTypeAssessment assessment -> append( " because field " + fieldName( assessment.fieldAssessment.field ) + " is mutable" );
			case MutableSuperclassMutableTypeAssessment assessment -> append( " because it extends mutable class " + className( assessment.superclassAssessment.type ) );
			case ArrayOfMutableElementTypeMutableTypeAssessment ignore -> append( " because it is an array of mutable element type" );
			//DoNotCover
			default -> throw new AssertionError( mutableTypeAssessment );
		}
	}

	private void getProvisoryTypeAssessmentText( ProvisoryTypeAssessment provisoryTypeAssessment )
	{
		append( "class " + className( provisoryTypeAssessment.type ) + " is provisory" );
		switch( provisoryTypeAssessment )
		{
			case CompositeProvisoryTypeAssessment<?,?> assessment -> append( " because it " + modeName( assessment.mode ) + " composite" );
			case ExtensibleProvisoryTypeAssessment assessment -> append( " because it " + modeName( assessment.mode ) + " an extensible class" );
			case InterfaceProvisoryTypeAssessment ignore -> append( " because it is an interface" );
			case MultiReasonProvisoryTypeAssessment ignore -> append( " due to multiple reasons" );
			case ProvisorySuperclassProvisoryTypeAssessment assessment -> append( " because it extends provisory type " + className( assessment.superclassAssessment.type ) );
			case ProvisoryFieldProvisoryTypeAssessment assessment -> append( " because field " + fieldName( assessment.fieldAssessment.field ) + " is provisory" );
			case ArrayOfProvisoryElementTypeProvisoryTypeAssessment ignore -> append( " because it is an array of provisory element type" );
			case SelfAssessableProvisoryTypeAssessment ignore -> append( " because instances of this type are self-assessable" );
			//DoNotCover
			default -> throw new AssertionError( provisoryTypeAssessment );
		}
	}

	private void getObjectAssessmentText( ObjectAssessment objectAssessment )
	{
		switch( objectAssessment )
		{
			case MutableObjectAssessment assessment -> getMutableObjectAssessmentText( assessment );
			case ImmutableObjectAssessment ignore -> append( "immutable" );
			//DoNotCover
			default -> throw new AssertionError( objectAssessment );
		}
	}

	private void getMutableObjectAssessmentText( MutableObjectAssessment mutableObjectAssessment )
	{
		append( "object " + stringFromObjectIdentity( mutableObjectAssessment.object() ) + " is mutable" );
		switch( mutableObjectAssessment )
		{
			case MutableSuperclassMutableObjectAssessment ignore -> append( " because its superclass is mutable" );
			case MutableArrayElementMutableObjectAssessment assessment -> append( " because it is an invariable array, and element " + stringFromObjectIdentity( assessment.elementAssessment.object() ) + " at index " + assessment.elementIndex + " is mutable" );
			case MutableComponentMutableObjectAssessment<?,?> assessment -> append( " because it is a composite, and element " + stringFromObjectIdentity( assessment.elementAssessment.object() ) + " at index " + assessment.elementIndex + " is mutable" );
			case MutableFieldValueMutableObjectAssessment assessment -> append( " because it is of provisory type " + className( assessment.typeAssessment().type ) + " and field " + fieldName( assessment.provisoryFieldAssessment.field ) + " has mutable value." );
			case NonEmptyArrayMutableObjectAssessment ignore -> append( " because it is a non-empty array" );
			case MutableClassMutableObjectAssessment ignore -> append( " because it is of a mutable class" );
			case SelfAssessedMutableObjectAssessment ignore -> append( " because it assessed itself as mutable" );
			//DoNotCover
			default -> throw new AssertionError( mutableObjectAssessment );
		}
	}

	private static String modeName( TypeAssessment.Mode typeAssessmentMode )
	{
		return switch( typeAssessmentMode )
			{
				case Assessed -> "is";
				case Preassessed -> "is preassessed as";
				case PreassessedByDefault -> "is preassessed by default as";
			};
	}

	private static String className( Class<?> jvmClass )
	{
		return "'" + MyKit.getClassName( jvmClass ) + "'";
	}

	private static String fieldName( @SuppressWarnings( "TypeMayBeWeakened" ) Field field )
	{
		return "'" + field.getName() + "'";
	}

	public static String stringFromObjectIdentity( Object object )
	{
		assert !(object instanceof Class<?>);
		return MyKit.identityString( object );
	}
}
