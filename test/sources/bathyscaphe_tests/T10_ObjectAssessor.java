package bathyscaphe_tests;

import mikenakis.bathyscaphe.ImmutabilitySelfAssessable;
import mikenakis.bathyscaphe.ObjectAssessor;
import mikenakis.bathyscaphe.annotations.Invariable;
import mikenakis.bathyscaphe.annotations.InvariableArray;
import mikenakis.bathyscaphe.internal.assessments.ImmutableObjectAssessment;
import mikenakis.bathyscaphe.internal.assessments.MutableObjectAssessment;
import mikenakis.bathyscaphe.internal.assessments.ObjectAssessment;
import mikenakis.bathyscaphe.internal.assessments.mutable.MutableClassMutableObjectAssessment;
import mikenakis.bathyscaphe.internal.assessments.mutable.MutableFieldValueMutableObjectAssessment;
import mikenakis.bathyscaphe.internal.assessments.mutable.MutableSuperclassMutableObjectAssessment;
import mikenakis.bathyscaphe.internal.assessments.mutable.SelfAssessedMutableObjectAssessment;
import mikenakis.bathyscaphe.internal.mykit.MyKit;
import mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.MutableFieldMutableTypeAssessment;
import mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.InterfaceProvisoryTypeAssessment;
import mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisoryFieldProvisoryTypeAssessment;
import mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisorySuperclassProvisoryTypeAssessment;
import mikenakis.bathyscaphe.internal.type.exceptions.AnnotatedInvariableArrayFieldMustBePrivateException;
import mikenakis.bathyscaphe.internal.type.exceptions.AnnotatedInvariableFieldMayNotAlreadyBeInvariableException;
import mikenakis.bathyscaphe.internal.type.exceptions.AnnotatedInvariableFieldMustBePrivateException;
import mikenakis.bathyscaphe.internal.type.exceptions.NonArrayFieldMayNotBeAnnotatedInvariableArrayException;
import mikenakis.bathyscaphe.internal.type.exceptions.PreassessedClassMustNotAlreadyBeImmutableException;
import mikenakis.bathyscaphe.internal.type.exceptions.PreassessedClassMustNotBePreviouslyAssessedException;
import mikenakis.bathyscaphe.internal.type.exceptions.PreassessedTypeMustBeClassException;
import mikenakis.bathyscaphe.internal.type.exceptions.SelfAssessableClassMustNotBeImmutableException;
import mikenakis.bathyscaphe.internal.type.exceptions.VariableFieldMayNotBeAnnotatedInvariableArrayException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test.
 * <p>
 * NOTE: the {@code new Runnable().run()} business is a trick for creating multiple local namespaces within a single java source file.
 */
@SuppressWarnings( { "FieldMayBeFinal", "InstanceVariableMayNotBeInitialized" } )
public class T10_ObjectAssessor
{
	public T10_ObjectAssessor()
	{
		if( !MyKit.areAssertionsEnabled() )
			throw new AssertionError();
	}

	private static ObjectAssessment assess( Object object )
	{
		return Helper.assess( object );
	}

	@Test public void null_is_immutable()
	{
		ObjectAssessment assessment = assess( null );
		assert assessment instanceof ImmutableObjectAssessment;
	}

	@Test public void java_lang_Object_is_immutable()
	{
		Object object = new Object();
		ObjectAssessment assessment = assess( object );
		assert assessment instanceof ImmutableObjectAssessment;
	}

	@Test public void empty_array_is_immutable()
	{
		Object object = new Object[0];
		ObjectAssessment assessment = assess( object );
		assert assessment instanceof ImmutableObjectAssessment;
	}

	@Test public void array_of_immutable_elements_is_mutable()
	{
		Object object = new Integer[] { 1 };
		ObjectAssessment assessment = assess( object );
		assert assessment instanceof MutableObjectAssessment;
	}

	@Test public void object_of_immutable_class_with_mutable_static_field_is_immutable()
	{
		new Runnable()
		{
			static final class ImmutableClassWithStaticMutableField
			{
				@SuppressWarnings( "unused" ) private static int staticMutableField = 0;
			}

			@Override public void run()
			{
				ObjectAssessment assessment = assess( new ImmutableClassWithStaticMutableField() );
				assert assessment instanceof ImmutableObjectAssessment;
			}
		}.run();
	}

	@Test public void immutable_object_with_mutable_super_is_mutable()
	{
		new Runnable()
		{
			static class Superclass
			{
				@SuppressWarnings( "unused" ) final Object provisoryFieldAssessedAsMutable = new StringBuilder();
			}

			static final class Derived extends Superclass
			{ }

			@Override public void run()
			{
				var object = new Derived();
				ObjectAssessment assessment = assess( object );
				var mutableObjectAssessment = (MutableObjectAssessment)assessment;
				assert mutableObjectAssessment.object() == object;
				assert mutableObjectAssessment.typeAssessment() instanceof ProvisorySuperclassProvisoryTypeAssessment;
				var mutableSuperclassMutableObjectAssessment = (MutableSuperclassMutableObjectAssessment)mutableObjectAssessment;
				assert mutableSuperclassMutableObjectAssessment.mutableSuperObjectAssessment.object() == object;
				assert mutableSuperclassMutableObjectAssessment.mutableSuperObjectAssessment.typeAssessment() instanceof ProvisoryFieldProvisoryTypeAssessment;
				var mutableFieldValueMutableObjectAssessment = (MutableFieldValueMutableObjectAssessment)mutableSuperclassMutableObjectAssessment.mutableSuperObjectAssessment;
				assert mutableFieldValueMutableObjectAssessment.fieldValueAssessment instanceof MutableClassMutableObjectAssessment;
				assert mutableFieldValueMutableObjectAssessment.fieldValueAssessment.object() == object.provisoryFieldAssessedAsMutable;
			}
		}.run();
	}

	@Test public void circularly_self_referencing_immutable_object_is_immutable()
	{
		new Runnable()
		{
			static final class SelfReferencingProvisoryClass
			{
				@SuppressWarnings( "unused" ) private final Object selfReference = this;
			}

			@Override public void run()
			{
				assert assess( new SelfReferencingProvisoryClass() ) instanceof ImmutableObjectAssessment;
			}
		}.run();
	}

	@Test public void circularly_self_referencing_object_extending_circularly_self_referencing_immutable_object_is_immutable()
	{
		new Runnable()
		{
			static class SelfReferencingProvisoryClass
			{
				@SuppressWarnings( "unused" ) private final Object selfReference = this;
			}

			static final class ClassExtendingSelfReferencingProvisoryClass extends SelfReferencingProvisoryClass
			{
				@SuppressWarnings( "unused" ) private final Object selfReference = this;
			}

			@Override public void run()
			{
				assert assess( new ClassExtendingSelfReferencingProvisoryClass() ) instanceof ImmutableObjectAssessment;
			}
		}.run();
	}

	@Test public void object_with_invariable_field_of_interface_type_with_immutable_value_is_immutable()
	{
		new Runnable()
		{
			static final class ClassWithInvariableFieldOfInterfaceTypeWithValueOfImmutableType
			{
				@SuppressWarnings( "unused" ) private final Comparable<String> invariableFieldOfInterfaceType = "";
			}

			@Override public void run()
			{
				assert assess( new ClassWithInvariableFieldOfInterfaceTypeWithValueOfImmutableType() ) instanceof ImmutableObjectAssessment;
			}
		}.run();
	}

	@Test public void object_with_invariable_field_of_interface_type_with_mutable_value_is_mutable()
	{
		new Runnable()
		{
			static final class ClassWithInvariableFieldOfInterfaceTypeWithMutableValue
			{
				final List<String> mutableField = new ArrayList<>();
			}

			@Override public void run()
			{
				var object = new ClassWithInvariableFieldOfInterfaceTypeWithMutableValue();
				ObjectAssessment assessment = assess( object );
				assert assessment instanceof MutableFieldValueMutableObjectAssessment;
				MutableFieldValueMutableObjectAssessment mutableFieldValueAssessment = (MutableFieldValueMutableObjectAssessment)assessment;
				assert mutableFieldValueAssessment.object == object;
				assert mutableFieldValueAssessment.fieldValueAssessment.object() == object.mutableField;
				assert mutableFieldValueAssessment.fieldValueAssessment.typeAssessment().type == ArrayList.class;
				assert mutableFieldValueAssessment.fieldValueAssessment instanceof MutableClassMutableObjectAssessment;
				assert mutableFieldValueAssessment.provisoryFieldAssessment.field.getName().equals( "mutableField" );
				assert mutableFieldValueAssessment.provisoryFieldAssessment.fieldTypeAssessment.type == List.class;
				assert mutableFieldValueAssessment.provisoryFieldAssessment.fieldTypeAssessment instanceof InterfaceProvisoryTypeAssessment;
			}
		}.run();
	}

	@Test public void positively_self_assessing_object_is_immutable()
	{
		new Runnable()
		{
			static final class ProvisorySelfAssessableClassWhichSelfAssessesPositively implements ImmutabilitySelfAssessable
			{
				@SuppressWarnings( "unused" ) private final Object provisoryMember = null;
				@Override public boolean isImmutable() { return true; }
			}

			@Override public void run()
			{
				assert assess( new ProvisorySelfAssessableClassWhichSelfAssessesPositively() ) instanceof ImmutableObjectAssessment;
			}
		}.run();
	}

	@Test public void negatively_self_assessing_object_is_mutable()
	{
		new Runnable()
		{
			static final class ProvisorySelfAssessableClassWhichSelfAssessesNegatively implements ImmutabilitySelfAssessable
			{
				@SuppressWarnings( "unused" ) private final Object provisoryMember = null;
				@Override public boolean isImmutable() { return false; }
			}

			@Override public void run()
			{
				var object = new ProvisorySelfAssessableClassWhichSelfAssessesNegatively();
				ObjectAssessment assessment = assess( object );
				MutableObjectAssessment mutableObjectAssessment = (MutableObjectAssessment)assessment;
				assert mutableObjectAssessment.object() == object;
				assert mutableObjectAssessment.typeAssessment().type == ProvisorySelfAssessableClassWhichSelfAssessesNegatively.class;
				assert mutableObjectAssessment instanceof SelfAssessedMutableObjectAssessment;
			}
		}.run();
	}

	@Test public void object_with_array_field_is_mutable()
	{
		new Runnable()
		{
			static final class ClassWithArrayField
			{
				@SuppressWarnings( "unused" ) public final int[] array = null;
			}

			@Override public void run()
			{
				var assessment = assess( new ClassWithArrayField() );
				MutableClassMutableObjectAssessment mutableClassMutableObjectAssessment = (MutableClassMutableObjectAssessment)assessment;
				assert mutableClassMutableObjectAssessment.typeAssessment instanceof MutableFieldMutableTypeAssessment;
			}
		}.run();
	}

	@Test public void object_with_invariable_array_field_of_circular_reference_element_type_is_immutable()
	{
		new Runnable()
		{
			static final class ClassWithInvariableArrayFieldOfCircularReferenceElementType
			{
				@SuppressWarnings( "unused" ) @InvariableArray private final ClassWithInvariableArrayFieldOfCircularReferenceElementType[] array = null;
			}

			@Override public void run()
			{
				assert ObjectAssessor.instance.mustBeImmutableAssertion( new ClassWithInvariableArrayFieldOfCircularReferenceElementType() );
			}
		}.run();
	}

	@Test public void object_with_invariable_array_of_provisory_element_type_with_mutable_elements_is_mutable()
	{
		new Runnable()
		{
			static class ClassWithInvariableArrayOfProvisoryType
			{
				@SuppressWarnings( "unused" ) @InvariableArray private final Object[] arrayField = { new StringBuilder() };
			}

			@Override public void run()
			{
				Object object = new ClassWithInvariableArrayOfProvisoryType();
				ObjectAssessment assessment = assess( object );
				assert assessment instanceof MutableObjectAssessment;
			}
		}.run();
	}

	@Test public void object_with_invariable_array_of_provisory_element_type_with_immutable_elements_is_immutable()
	{
		new Runnable()
		{
			static class ClassWithInvariableArrayOfProvisoryType
			{
				@SuppressWarnings( "unused" ) @InvariableArray private final Object[] arrayField = { 1 };
			}

			@Override public void run()
			{
				Object object = new ClassWithInvariableArrayOfProvisoryType();
				ObjectAssessment assessment = assess( object );
				assert assessment instanceof ImmutableObjectAssessment;
			}
		}.run();
	}

	@Test public void object_with_array_of_array_is_mutable()
	{
		//TODO
	}

	// TODO: revise the purposefulness of allowing this. Perhaps we should not allow it.
	@Test public void invariable_array_annotation_on_array_field_of_mutable_element_type_is_ignored()
	{
		new Runnable()
		{
			static final class ClassWithInvariableArrayInvariableFieldOfMutableElementType
			{
				@SuppressWarnings( "unused" ) @InvariableArray private final ArrayList<Integer>[] arrayOfMutableElements = null;
			}

			@Override public void run()
			{
				var assessment = assess( new ClassWithInvariableArrayInvariableFieldOfMutableElementType() );
				assert assessment instanceof MutableObjectAssessment;
			}
		}.run();
	}

	@Test public void invariable_annotation_on_public_field_is_caught()
	{
		new Runnable()
		{
			static class ClassWithPublicFieldAnnotatedInvariable
			{
				@SuppressWarnings( "unused" ) @Invariable public Integer publicFieldAnnotatedInvariable;
			}

			@Override public void run()
			{
				var exception = MyTestKit.expect( AnnotatedInvariableFieldMustBePrivateException.class, () -> //
					assess( new ClassWithPublicFieldAnnotatedInvariable() ) );
				assert exception.field.getName().equals( "publicFieldAnnotatedInvariable" );
			}
		}.run();
	}

	@Test public void invariable_annotation_on_protected_field_is_caught()
	{
		new Runnable()
		{
			static class ClassWithProtectedFieldAnnotatedInvariable
			{
				@SuppressWarnings( "unused" ) @Invariable protected Integer protectedFieldAnnotatedInvariable;
			}

			@Override public void run()
			{
				var exception = MyTestKit.expect( AnnotatedInvariableFieldMustBePrivateException.class, () -> //
					assess( new ClassWithProtectedFieldAnnotatedInvariable() ) );
				assert exception.field.getName().equals( "protectedFieldAnnotatedInvariable" );
			}
		}.run();
	}

	@Test public void invariable_annotation_on_package_private_field_is_caught()
	{
		new Runnable()
		{
			static class ClassWithPublicFieldAnnotatedInvariable
			{
				@SuppressWarnings( "unused" ) @Invariable Integer packagePrivateFieldAnnotatedInvariable;
			}

			@Override public void run()
			{
				var exception = MyTestKit.expect( AnnotatedInvariableFieldMustBePrivateException.class, () -> //
					assess( new ClassWithPublicFieldAnnotatedInvariable() ) );
				assert exception.field.getName().equals( "packagePrivateFieldAnnotatedInvariable" );
			}
		}.run();
	}

	@Test public void invariable_annotation_on_already_invariable_field_is_caught()
	{
		new Runnable()
		{
			static class ClassWithInvariableFieldAnnotatedInvariable
			{
				@SuppressWarnings( "unused" ) @Invariable private final Integer invariableFieldAnnotatedInvariable = Integer.MAX_VALUE;
			}

			@Override public void run()
			{
				var exception = MyTestKit.expect( AnnotatedInvariableFieldMayNotAlreadyBeInvariableException.class, () -> //
					assess( new ClassWithInvariableFieldAnnotatedInvariable() ) );
				assert exception.field.getName().equals( "invariableFieldAnnotatedInvariable" );
			}
		}.run();
	}

	// TODO: revise the purposefulness of this. Perhaps we should throw an exception, because doing such a thing cannot possibly lead to any good.
	@Test public void invariable_annotation_on_mutable_field_is_ignored()
	{
		new Runnable()
		{
			static class ClassWithMutableFieldAnnotatedInvariable
			{
				@SuppressWarnings( "unused" ) @Invariable private ArrayList<Integer> mutableFieldAnnotatedInvariable = null;
			}

			@Override public void run()
			{
				var assessment = assess( new ClassWithMutableFieldAnnotatedInvariable() );
				assert assessment instanceof MutableObjectAssessment;
			}
		}.run();
	}

	@Test public void object_with_invariable_array_invariable_field_of_immutable_element_type_is_immutable()
	{
		new Runnable()
		{
			static final class ClassWithInvariableArrayInvariableFieldOfImmutableElementType
			{
				@SuppressWarnings( "unused" ) @Invariable @InvariableArray private int[] array = null;
			}

			@Override public void run()
			{
				var assessment = assess( new ClassWithInvariableArrayInvariableFieldOfImmutableElementType() );
				assert assessment instanceof ImmutableObjectAssessment;
			}
		}.run();
	}

	@Test public void invariable_array_annotation_on_non_private_field_is_caught()
	{
		new Runnable()
		{
			static class ClassWithPublicFieldAnnotatedInvariableArray
			{
				@SuppressWarnings( "unused" ) @InvariableArray public final int[] publicFieldAnnotatedInvariableArray = null;
			}

			static class ClassWithProtectedFieldAnnotatedInvariableArray
			{
				@SuppressWarnings( "unused" ) @InvariableArray protected final int[] protectedFieldAnnotatedInvariableArray = null;
			}

			static class ClassWithPackagePrivateFieldAnnotatedInvariableArray
			{
				@SuppressWarnings( "unused" ) @InvariableArray final int[] packagePrivateFieldAnnotatedInvariableArray = null;
			}

			@Override public void run()
			{
				MyTestKit.expect( AnnotatedInvariableArrayFieldMustBePrivateException.class, () -> //
					assess( new ClassWithPublicFieldAnnotatedInvariableArray() ) );
				MyTestKit.expect( AnnotatedInvariableArrayFieldMustBePrivateException.class, () -> //
					assess( new ClassWithProtectedFieldAnnotatedInvariableArray() ) );
				MyTestKit.expect( AnnotatedInvariableArrayFieldMustBePrivateException.class, () -> //
					assess( new ClassWithPackagePrivateFieldAnnotatedInvariableArray() ) );
			}
		}.run();
	}

	@Test public void invariable_array_annotation_on_non_array_field_is_caught()
	{
		new Runnable()
		{
			static class ClassWithNonArrayField
			{
				@SuppressWarnings( "unused" ) @InvariableArray private Object nonArrayField = null;
			}

			@Override public void run()
			{
				var exception = MyTestKit.expect( NonArrayFieldMayNotBeAnnotatedInvariableArrayException.class, () -> //
					assess( new ClassWithNonArrayField() ) );
				assert exception.field.getName().equals( "nonArrayField" );
			}
		}.run();
	}

	@Test public void invariable_array_annotation_on_variable_field_is_caught()
	{
		new Runnable()
		{
			static class ClassWithVariableFieldAnnotatedAsInvariableArray
			{
				@SuppressWarnings( "unused" ) @InvariableArray private Object[] variableField = null;
			}

			@Override public void run()
			{
				var exception = MyTestKit.expect( VariableFieldMayNotBeAnnotatedInvariableArrayException.class, () -> //
					assess( new ClassWithVariableFieldAnnotatedAsInvariableArray() ) );
				assert exception.field.getName().equals( "variableField" );
			}
		}.run();
	}

	@Test public void immutable_preassessment_on_already_immutable_class_is_caught()
	{
		new Runnable()
		{
			static final class AlreadyImmutableClass
			{ }

			@Override public void run()
			{
				var exception = MyTestKit.expect( PreassessedClassMustNotAlreadyBeImmutableException.class, () -> //
					ObjectAssessor.instance.addImmutablePreassessment( AlreadyImmutableClass.class ) );
				assert exception.jvmClass == AlreadyImmutableClass.class;
			}
		}.run();
	}

	@Test public void immutable_preassessment_on_interface_is_caught()
	{
		new Runnable()
		{
			interface Interface
			{ }

			@Override public void run()
			{
				var exception = MyTestKit.expect( PreassessedTypeMustBeClassException.class, () -> //
					ObjectAssessor.instance.addImmutablePreassessment( Interface.class ) );
				assert exception.type == Interface.class;
			}
		}.run();
	}

	@Test public void immutable_preassessment_on_array_is_caught()
	{
		Class<?> arrayClass = int[].class;
		var exception = MyTestKit.expect( PreassessedTypeMustBeClassException.class, () -> //
			ObjectAssessor.instance.addImmutablePreassessment( arrayClass ) );
		assert exception.type == arrayClass;
	}

	@Test public void immutable_preassessment_on_previously_assessed_immutable_class_is_caught()
	{
		new Runnable()
		{
			static final class ImmutableClass
			{ }

			@Override public void run()
			{
				assert ObjectAssessor.instance.mustBeImmutableAssertion( new ImmutableClass() );
				MyTestKit.expect( PreassessedClassMustNotBePreviouslyAssessedException.class, () -> //
					ObjectAssessor.instance.addImmutablePreassessment( ImmutableClass.class ) );
			}
		}.run();
	}

	@Test public void immutable_preassessment_on_previously_assessed_mutable_class_is_caught()
	{
		new Runnable()
		{
			static class ClassWithMutableField
			{
				@SuppressWarnings( "unused" ) private final ArrayList<String> mutableField = null;
			}

			@Override public void run()
			{
				assert assess( new ClassWithMutableField() ) instanceof MutableObjectAssessment;
				MyTestKit.expect( PreassessedClassMustNotBePreviouslyAssessedException.class, () -> //
					ObjectAssessor.instance.addImmutablePreassessment( ClassWithMutableField.class ) );
			}
		}.run();
	}

	@Test public void object_of_mutable_class_preassessed_as_immutable_is_immutable()
	{
		new Runnable()
		{
			static final class ProvisoryClass
			{
				@SuppressWarnings( "unused" ) private final List<Object> provisoryField = new ArrayList<>();
			}

			@Override public void run()
			{
				ObjectAssessor.instance.addImmutablePreassessment( ProvisoryClass.class );
				assert ObjectAssessor.instance.mustBeImmutableAssertion( new ProvisoryClass() );
			}
		}.run();
	}

	@Test public void self_assessable_class_that_is_already_immutable_is_caught()
	{
		new Runnable()
		{
			static final class ImmutableSelfAssessableClass implements ImmutabilitySelfAssessable
			{
				@Override public boolean isImmutable() { throw new AssertionError(); /* we do not expect this to be invoked. */ }
			}

			@Override public void run()
			{
				var exception = MyTestKit.expect( SelfAssessableClassMustNotBeImmutableException.class, () -> //
					assess( new ImmutableSelfAssessableClass() ) );
				assert exception.jvmClass == ImmutableSelfAssessableClass.class;
			}
		}.run();
	}

	@Test public void immutable_object_with_supertype_field_is_immutable()
	{
		new Runnable()
		{
			static class SuperClass
			{
				@SuppressWarnings( "unused" ) public final DerivedClass derivedClassField = null;
			}

			static final class DerivedClass extends SuperClass
			{ }

			@Override public void run()
			{
				assert ObjectAssessor.instance.mustBeImmutableAssertion( new SuperClass() );
			}
		}.run();
	}
}
