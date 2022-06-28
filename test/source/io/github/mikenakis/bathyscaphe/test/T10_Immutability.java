/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * For licensing information, please see LICENSE.md.
 * You may not use this file except in compliance with the license.
 */

package io.github.mikenakis.bathyscaphe.test;

import io.github.mikenakis.bathyscaphe.Bathyscaphe;
import io.github.mikenakis.bathyscaphe.ImmutabilitySelfAssessable;
import io.github.mikenakis.bathyscaphe.annotations.Invariable;
import io.github.mikenakis.bathyscaphe.annotations.InvariableArray;
import io.github.mikenakis.bathyscaphe.internal.assessments.ImmutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.MutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.ObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.mutable.MutableClassMutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.mutable.MutableFieldValueMutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.mutable.MutableSuperObjectMutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.mutable.SelfAssessedMutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.mykit.MyKit;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.mutable.MutableFieldMutableTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.InterfaceProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisoryFieldProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.assessments.nonimmutable.provisory.ProvisorySuperclassProvisoryTypeAssessment;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.AnnotatedFieldMustBePrivateException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.AnnotatedInvariableFieldMayNotAlreadyBeInvariableException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.NonArrayFieldMayNotBeAnnotatedInvariableArrayException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.PreassessedClassMustNotAlreadyBeImmutableException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.PreassessedClassMustNotBeExtensibleException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.PreassessedClassMustNotBePreviouslyAssessedException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.PreassessedTypeMustBeClassException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.SelfAssessableClassMustNotBeImmutableException;
import io.github.mikenakis.bathyscaphe.internal.type.exceptions.VariableFieldMayNotBeAnnotatedInvariableArrayException;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Test.
 * <p>
 * NOTE: the {@code new Runnable().run()} business is a trick for creating multiple local namespaces within a single java source file.
 *
 * @author michael.gr
 */
@SuppressWarnings( { "FieldMayBeFinal", "InstanceVariableMayNotBeInitialized" } )
public class T10_Immutability
{
	public T10_Immutability()
	{
		if( !MyKit.areAssertionsEnabled() )
			throw new AssertionError();
	}

	@Test public void null_is_immutable()
	{
		Method method = Helper.getCurrentMethod();
		ObjectAssessment assessment = Helper.assess( method, null );
		assert assessment instanceof ImmutableObjectAssessment;
	}

	@Test public void java_lang_Object_is_immutable()
	{
		Method method = Helper.getCurrentMethod();
		Object object = new Object();
		ObjectAssessment assessment = Helper.assess( method, object );
		assert assessment instanceof ImmutableObjectAssessment;
	}

	@Test public void empty_array_is_immutable()
	{
		Method method = Helper.getCurrentMethod();
		Object object = new Object[0];
		ObjectAssessment assessment = Helper.assess( method, object );
		assert assessment instanceof ImmutableObjectAssessment;
	}

	@Test public void array_of_immutable_elements_is_mutable()
	{
		Method method = Helper.getCurrentMethod();
		Object object = new Integer[] { 1 };
		ObjectAssessment assessment = Helper.assess( method, object );
		assert assessment instanceof MutableObjectAssessment;
	}

	@Test public void object_of_immutable_class_with_mutable_static_field_is_immutable()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static final class ImmutableClassWithStaticMutableField
			{
				@SuppressWarnings( "unused" ) private static int staticMutableField = 0;
			}

			@Override public void run()
			{
				var object = new ImmutableClassWithStaticMutableField();
				ObjectAssessment assessment = Helper.assess( method, object );
				assert assessment instanceof ImmutableObjectAssessment;
			}
		}.run();
	}

	@Test public void mutable_object_is_mutable()
	{
		Method method = Helper.getCurrentMethod();
		var object = new StringBuilder();
		ObjectAssessment assessment = Helper.assess( method, object );
		var mutableObjectAssessment = (MutableObjectAssessment)assessment;
		assert mutableObjectAssessment.object() == object;
	}

	@Test public void object_with_mutable_field_is_mutable()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static class ObjectWithMutableField
			{
				@SuppressWarnings( "unused" ) final Object provisoryFieldWithMutableValue = new StringBuilder();
			}

			@Override public void run()
			{
				var object = new ObjectWithMutableField();
				ObjectAssessment assessment = Helper.assess( method, object );
				var mutableObjectAssessment = (MutableObjectAssessment)assessment;
				assert mutableObjectAssessment.object() == object;
			}
		}.run();
	}

	@Test public void immutable_object_with_mutable_super_is_mutable()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static class Superclass
			{
				@SuppressWarnings( "unused" ) final Object provisoryFieldWithMutableValue = new StringBuilder();
			}

			static final class Derived extends Superclass
			{ }

			@Override public void run()
			{
				var object = new Derived();
				ObjectAssessment assessment = Helper.assess( method, object );
				var mutableObjectAssessment = (MutableObjectAssessment)assessment;
				assert mutableObjectAssessment.object() == object;
				assert mutableObjectAssessment.typeAssessment() instanceof ProvisorySuperclassProvisoryTypeAssessment;
				var mutableSuperclassMutableObjectAssessment = (MutableSuperObjectMutableObjectAssessment)mutableObjectAssessment;
				assert mutableSuperclassMutableObjectAssessment.mutableSuperObjectAssessment.object() == object;
				assert mutableSuperclassMutableObjectAssessment.mutableSuperObjectAssessment.typeAssessment() instanceof ProvisoryFieldProvisoryTypeAssessment;
				var mutableFieldValueMutableObjectAssessment = (MutableFieldValueMutableObjectAssessment)mutableSuperclassMutableObjectAssessment.mutableSuperObjectAssessment;
				assert mutableFieldValueMutableObjectAssessment.fieldValueAssessment instanceof MutableClassMutableObjectAssessment;
				assert mutableFieldValueMutableObjectAssessment.fieldValueAssessment.object() == object.provisoryFieldWithMutableValue;
			}
		}.run();
	}

	@Test public void circularly_self_referencing_immutable_object_is_immutable()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static final class SelfReferencingProvisoryClass
			{
				@SuppressWarnings( "unused" ) private final Object selfReference = this;
			}

			@Override public void run()
			{
				var object = new SelfReferencingProvisoryClass();
				var assessment = Helper.assess( method, object );
				assert assessment instanceof ImmutableObjectAssessment;
			}
		}.run();
	}

	@Test public void circularly_self_referencing_object_extending_circularly_self_referencing_immutable_object_is_immutable()
	{
		Method method = Helper.getCurrentMethod();
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
				var object = new ClassExtendingSelfReferencingProvisoryClass();
				var assessment = Helper.assess( method, object );
				assert assessment instanceof ImmutableObjectAssessment;
			}
		}.run();
	}

	@Test public void object_with_invariable_field_of_interface_type_with_immutable_value_is_immutable()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static final class ClassWithInvariableFieldOfInterfaceTypeWithValueOfImmutableType
			{
				@SuppressWarnings( "unused" ) private final Comparable<String> invariableFieldOfInterfaceType = "";
			}

			@Override public void run()
			{
				var object = new ClassWithInvariableFieldOfInterfaceTypeWithValueOfImmutableType();
				var assessment = Helper.assess( method, object );
				assert assessment instanceof ImmutableObjectAssessment;
			}
		}.run();
	}

	@Test public void object_with_invariable_field_of_interface_type_with_mutable_value_is_mutable()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static final class ClassWithInvariableFieldOfInterfaceTypeWithMutableValue
			{
				final List<String> mutableField = new ArrayList<>();
			}

			@Override public void run()
			{
				var object = new ClassWithInvariableFieldOfInterfaceTypeWithMutableValue();
				ObjectAssessment assessment = Helper.assess( method, object );
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
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static final class ProvisorySelfAssessableClassWhichSelfAssessesPositively implements ImmutabilitySelfAssessable
			{
				@SuppressWarnings( "unused" ) private final Object provisoryMember = null;
				@Override public boolean isImmutable() { return true; }
			}

			@Override public void run()
			{
				var object = new ProvisorySelfAssessableClassWhichSelfAssessesPositively();
				ObjectAssessment assessment = Helper.assess( method, object );
				assert assessment instanceof ImmutableObjectAssessment;
			}
		}.run();
	}

	@Test public void negatively_self_assessing_object_is_mutable()
	{
		Method method = Helper.getCurrentMethod();
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
				ObjectAssessment assessment = Helper.assess( method, object );
				MutableObjectAssessment mutableObjectAssessment = (MutableObjectAssessment)assessment;
				assert mutableObjectAssessment.object() == object;
				assert mutableObjectAssessment.typeAssessment().type == ProvisorySelfAssessableClassWhichSelfAssessesNegatively.class;
				assert mutableObjectAssessment instanceof SelfAssessedMutableObjectAssessment;
			}
		}.run();
	}

	@Test public void object_with_array_field_is_mutable()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static final class ClassWithArrayField
			{
				@SuppressWarnings( "unused" ) public final int[] array = null;
			}

			@Override public void run()
			{
				var object = new ClassWithArrayField();
				var assessment = Helper.assess( method, object );
				MutableClassMutableObjectAssessment mutableClassMutableObjectAssessment = (MutableClassMutableObjectAssessment)assessment;
				assert mutableClassMutableObjectAssessment.typeAssessment instanceof MutableFieldMutableTypeAssessment;
			}
		}.run();
	}

	@Test public void object_with_invariable_array_field_of_circular_reference_element_type_is_immutable()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static final class ClassWithInvariableArrayFieldOfCircularReferenceElementType
			{
				@SuppressWarnings( "unused" ) @InvariableArray private final ClassWithInvariableArrayFieldOfCircularReferenceElementType[] array = null;
			}

			@Override public void run()
			{
				var object = new ClassWithInvariableArrayFieldOfCircularReferenceElementType();
				var assessment = Helper.assess( method, object );
				assert assessment instanceof ImmutableObjectAssessment;
			}
		}.run();
	}

	@Test public void object_with_invariable_array_of_provisory_element_type_with_mutable_elements_is_mutable()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static class ClassWithInvariableArrayOfProvisoryType
			{
				@SuppressWarnings( "unused" ) @InvariableArray private final Object[] arrayField = { new StringBuilder() };
			}

			@Override public void run()
			{
				Object object = new ClassWithInvariableArrayOfProvisoryType();
				ObjectAssessment assessment = Helper.assess( method, object );
				assert assessment instanceof MutableObjectAssessment;
			}
		}.run();
	}

	@Test public void object_with_invariable_array_of_provisory_element_type_with_immutable_elements_is_immutable()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static class ClassWithInvariableArrayOfProvisoryType
			{
				@SuppressWarnings( "unused" ) @InvariableArray private final Object[] arrayField = { 1 };
			}

			@Override public void run()
			{
				Object object = new ClassWithInvariableArrayOfProvisoryType();
				ObjectAssessment assessment = Helper.assess( method, object );
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
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static final class ClassWithInvariableArrayInvariableFieldOfMutableElementType
			{
				@SuppressWarnings( "unused" ) @InvariableArray private final ArrayList<Integer>[] arrayOfMutableElements = null;
			}

			@Override public void run()
			{
				var object = new ClassWithInvariableArrayInvariableFieldOfMutableElementType();
				var assessment = Helper.assess( method, object );
				assert assessment instanceof MutableObjectAssessment;
			}
		}.run();
	}

	@Test public void invariable_annotation_on_public_field_is_caught()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static class ClassWithPublicAnnotatedField
			{
				@SuppressWarnings( "unused" ) @Invariable public Integer publicAnnotatedField;
			}

			@Override public void run()
			{
				var object = new ClassWithPublicAnnotatedField();
				var exception = MyTestKit.expect( AnnotatedFieldMustBePrivateException.class, () -> Helper.assess( method, object ) );
				assert exception.field.getName().equals( "publicAnnotatedField" );
			}
		}.run();
	}

	@Test public void invariable_annotation_on_protected_field_is_caught()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static class ClassWithProtectedAnnotatedField
			{
				@SuppressWarnings( "unused" ) @Invariable protected Integer protectedAnnotatedField;
			}

			@Override public void run()
			{
				var object = new ClassWithProtectedAnnotatedField();
				var exception = MyTestKit.expect( AnnotatedFieldMustBePrivateException.class, () -> Helper.assess( method, object ) );
				assert exception.field.getName().equals( "protectedAnnotatedField" );
			}
		}.run();
	}

	@Test public void invariable_annotation_on_package_private_field_is_caught()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static class ClassWithPublicAnnotatedField
			{
				@SuppressWarnings( "unused" ) @Invariable Integer packagePrivateAnnotatedField;
			}

			@Override public void run()
			{
				var object = new ClassWithPublicAnnotatedField();
				var exception = MyTestKit.expect( AnnotatedFieldMustBePrivateException.class, () -> Helper.assess( method, object ) );
				assert exception.field.getName().equals( "packagePrivateAnnotatedField" );
			}
		}.run();
	}

	@Test public void invariable_annotation_on_already_invariable_field_is_caught()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static class ClassWithInvariableAnnotatedField
			{
				@SuppressWarnings( "unused" ) @Invariable private final Integer invariableAnnotatedField = Integer.MAX_VALUE;
			}

			@Override public void run()
			{
				var object = new ClassWithInvariableAnnotatedField();
				var exception = MyTestKit.expect( AnnotatedInvariableFieldMayNotAlreadyBeInvariableException.class, () -> Helper.assess( method, object ) );
				assert exception.field.getName().equals( "invariableAnnotatedField" );
			}
		}.run();
	}

	// TODO: revise the purposefulness of this. Perhaps we should throw an exception, because doing such a thing cannot possibly lead to any good.
	@Test public void invariable_annotation_on_mutable_field_is_ignored()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static class ClassWithMutableAnnotatedField
			{
				@SuppressWarnings( "unused" ) @Invariable private ArrayList<Integer> mutableAnnotatedField = null;
			}

			@Override public void run()
			{
				var object = new ClassWithMutableAnnotatedField();
				var assessment = Helper.assess( method, object );
				assert assessment instanceof MutableObjectAssessment;
			}
		}.run();
	}

	@Test public void object_with_invariable_array_invariable_field_of_immutable_element_type_is_immutable()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static final class ClassWithInvariableArrayInvariableFieldOfImmutableElementType
			{
				@SuppressWarnings( "unused" ) @Invariable @InvariableArray private int[] array = null;
			}

			@Override public void run()
			{
				var object = new ClassWithInvariableArrayInvariableFieldOfImmutableElementType();
				var assessment = Helper.assess( method, object );
				assert assessment instanceof ImmutableObjectAssessment;
			}
		}.run();
	}

	@Test public void invariable_array_annotation_on_public_field_is_caught()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static class ClassWithPublicAnnotatedFieldArray
			{
				@SuppressWarnings( "unused" ) @InvariableArray public final int[] publicAnnotatedFieldArray = null;
			}

			@Override public void run()
			{
				var object = new ClassWithPublicAnnotatedFieldArray();
				MyTestKit.expect( AnnotatedFieldMustBePrivateException.class, () -> Helper.assess( method, object ) );
			}
		}.run();
	}

	@Test public void invariable_array_annotation_on_protected_field_is_caught()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static class ClassWithProtectedAnnotatedFieldArray
			{
				@SuppressWarnings( "unused" ) @InvariableArray protected final int[] protectedAnnotatedFieldArray = null;
			}

			@Override public void run()
			{
				var object = new ClassWithProtectedAnnotatedFieldArray();
				MyTestKit.expect( AnnotatedFieldMustBePrivateException.class, () -> Helper.assess( method, object ) );
			}
		}.run();
	}

	@Test public void invariable_array_annotation_on_package_private_field_is_caught()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static class ClassWithPackagePrivateAnnotatedFieldArray
			{
				@SuppressWarnings( "unused" ) @InvariableArray final int[] packagePrivateAnnotatedFieldArray = null;
			}

			@Override public void run()
			{
				var object = new ClassWithPackagePrivateAnnotatedFieldArray();
				MyTestKit.expect( AnnotatedFieldMustBePrivateException.class, () -> Helper.assess( method, object ) );
			}
		}.run();
	}

	@Test public void invariable_array_annotation_on_non_array_field_is_caught()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static class ClassWithNonArrayField
			{
				@SuppressWarnings( "unused" ) @InvariableArray private Object nonArrayField = null;
			}

			@Override public void run()
			{
				var object = new ClassWithNonArrayField();
				var exception = MyTestKit.expect( NonArrayFieldMayNotBeAnnotatedInvariableArrayException.class, () -> Helper.assess( method, object ) );
				assert exception.field.getName().equals( "nonArrayField" );
			}
		}.run();
	}

	@Test public void invariable_array_annotation_on_variable_field_is_caught()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static class ClassWithVariableFieldAnnotatedAsArray
			{
				@SuppressWarnings( "unused" ) @InvariableArray private Object[] variableField = null;
			}

			@Override public void run()
			{
				var object = new ClassWithVariableFieldAnnotatedAsArray();
				var exception = MyTestKit.expect( VariableFieldMayNotBeAnnotatedInvariableArrayException.class, () -> Helper.assess( method, object ) );
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
					Bathyscaphe.addImmutablePreassessment( AlreadyImmutableClass.class ) );
				assert exception.jvmClass == AlreadyImmutableClass.class;
			}
		}.run();
	}

	@Test public void immutable_preassessment_on_interface_is_caught()
	{
		new Runnable()
		{
			interface SomeInterface
			{ }

			@Override public void run()
			{
				var exception = MyTestKit.expect( PreassessedTypeMustBeClassException.class, () -> //
					Bathyscaphe.addImmutablePreassessment( SomeInterface.class ) );
				assert exception.type == SomeInterface.class;
			}
		}.run();
	}

	@Test public void immutable_preassessment_on_extensible_class_is_caught()
	{
		new Runnable()
		{
			static class ExtensibleClass
			{ }

			@Override public void run()
			{
				var exception = MyTestKit.expect( PreassessedClassMustNotBeExtensibleException.class, () -> //
					Bathyscaphe.addImmutablePreassessment( ExtensibleClass.class ) );
				assert exception.jvmClass == ExtensibleClass.class;
			}
		}.run();
	}

	@Test public void immutable_preassessment_on_array_is_caught()
	{
		Class<?> arrayClass = int[].class;
		var exception = MyTestKit.expect( PreassessedTypeMustBeClassException.class, () -> //
			Bathyscaphe.addImmutablePreassessment( arrayClass ) );
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
				assert Bathyscaphe.objectMustBeImmutableAssertion( new ImmutableClass() );
				MyTestKit.expect( PreassessedClassMustNotBePreviouslyAssessedException.class, () -> //
					Bathyscaphe.addImmutablePreassessment( ImmutableClass.class ) );
			}
		}.run();
	}

	@Test public void immutable_preassessment_on_previously_assessed_mutable_class_is_caught()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static class ClassWithMutableField
			{
				@SuppressWarnings( "unused" ) private final ArrayList<String> mutableField = null;
			}

			@Override public void run()
			{
				var object = new ClassWithMutableField();
				ObjectAssessment assessment = Helper.assess( method, object );
				assert assessment instanceof MutableObjectAssessment;
				MyTestKit.expect( PreassessedClassMustNotBePreviouslyAssessedException.class, () -> //
					Bathyscaphe.addImmutablePreassessment( ClassWithMutableField.class ) );
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
				Bathyscaphe.addImmutablePreassessment( ProvisoryClass.class );
				assert Bathyscaphe.objectMustBeImmutableAssertion( new ProvisoryClass() );
			}
		}.run();
	}

	@Test public void self_assessable_class_that_is_already_immutable_is_caught()
	{
		Method method = Helper.getCurrentMethod();
		new Runnable()
		{
			static final class ImmutableSelfAssessableClass implements ImmutabilitySelfAssessable
			{
				@Override public boolean isImmutable() { throw new AssertionError(); /* we do not expect this to be invoked. */ }
			}

			@Override public void run()
			{
				var object = new ImmutableSelfAssessableClass();
				var exception = MyTestKit.expect( SelfAssessableClassMustNotBeImmutableException.class, () -> Helper.assess( method, object ) );
				assert exception.jvmClass == ImmutableSelfAssessableClass.class;
			}
		}.run();
	}

	@Test public void immutable_object_with_supertype_field_is_immutable()
	{
		Method method = Helper.getCurrentMethod();
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
				var object = new SuperClass();
				var assessment = Helper.assess( method, object );
				assert assessment instanceof ImmutableObjectAssessment;
			}
		}.run();
	}
}
