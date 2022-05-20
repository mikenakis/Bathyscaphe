# Bathyscaphe

#### Deep immutability (and coming soon: thread-safety) assessment for Java objects

<p align="center">
<img title="mikenakis:bathyscaphe logo" src="mikenakis-bathyscaphe-logo.svg" width="256"/><br/>
The mikenakis:bathyscaphe logo, a line drawing of <i>bathyscaphe Trieste</i><br/>
by Mike Nakis, based on a drawing found at <a href="https://bertrandpiccard.com/3-generations/jacques-piccard">bertrandpiccard.com</a><br/>
</p>

## Description

Bathyscaphe is a java library that can be used to inspect objects at runtime and assert that they are immutable.

For an article explaining what problem it solves, why it is even a problem, how it works, a glossary of terms, etc. see the corresponding post in my blog: https://blog.michael.gr/2022/05/bathyscaphe.html

## Reference

One method exposed by bathyscaphe is:

	public static objectMustBeImmutableAssertion( Object object );

If the object is immutable, then the method will return `true`; otherwise, the method will throw `ObjectMustBeImmutableException`. Thus, the method is suitable for using in `assert` statements, as follows:

	assert Bathyscaphe.objectMustBeImmutableAssertion( myObject );  

Note that such an assertion will never fail, because bathyscaphe will never return `false`; instead, bathyscaphe may throw its own exception, which is not an `AssertionError`. The benefit of invoking bathyscaphe from within `assert` statements is that we can disable bathyscaphe by disabling assertions.

The other method exposed by bathyscaphe is:

	public static addImmutablePreassessment( Class<?> jvmClass );

This method adds a pre-assessment for a class, otherwise known as an assessment override.  You can use this method to tell bathyscaphe that even though a particular class would be assessed as mutable if it was to be examined by bathyscaphe, you know that it behaves in an effectively immutable way, so you are instructing bathyscaphe to consider that class as immutable. Bathyscaphe invokes this method internally for a few classes, for example `java.lang.String`.  

The `addImmutablePreassessment()` method is primarily for use with classes whose source code you cannot modify. For classes that are under your control, it is best to avoid using this method, and to annotate their effectively immutable fields instead, thus allowing bathyscaphe to assess their immutability in all other respects. Bathyscaphe defines a couple of annotations that you can use for this purpose:

	@Invariable

This annotation tells bathyscaphe that even though a certain field is declared as non-final, you are promising that it will behave as if it was final. Bathyscaphe will still examine the field type, and if necessary the actual value of the field at runtime, to determine whether it is actually immutable or not.

In a hypothetical re-implementation of `java.lang.String`, the lazily computed hashCode field would be marked as `@Invariable` to instruct bathyscaphe to ignore the fact that it is not `final`. 

	@InvariableArray

This annotation instructs bathyscaphe to consider the array pointed by an array field as being effectively immutable, despite the fact that arrays are by definition mutable. Bathyscaphe will still examine the array element type, and if necessary the actual value of each array element at runtime, to determine whether it is immutable or not.  

In a hypothetical re-implementation of `java.lang.String`, the array of characters would be marked as `@InvariableArray` to instruct bathyscaphe to ignore the fact that it is an array, since arrays are by definition mutable.    

Note that it is an error to annotate an array with `@InvariableArray` unless the array field itself is either `final` or annotated with `@Invariable`. Also note that it is an error to use any of these annotations on non-private fields. Bathyscaphe never ignores erroneously used annotations; whenever mistakes of that kind are encountered, it throws an appropriate exception.

Sometimes the question of whether an object is mutable or immutable can be so complicated, that only the object itself can answer the question for sure. For example, sometimes we write classes that are 'freezable', meaning that they begin their life as mutable, and at some moment they are 'frozen', thus becoming immutable from that moment on. In order to cover these cases, Bathyscaphe defines the `ImmutabilitySelfAssessable` interface. If your class implements this interface, bathyscaphe will invoke instances of your class to ask them whether they are immutable or not.

### Diagnostics for Troubleshooting

Naturally, when an object that we intended to be immutable is assessed by bathyscaphe as mutable, we would like to have an explanation as to exactly why this assessment was issued, so that we can find where the problem is, and fix it. For this reason, there is a separate module called bathyscaphe-print which can create detailed human-readable diagnostics from an `ObjectMustBeImmutableException`.

bathyscaphe-print can be used as follows:

	catch( ObjectMustBeImmutableException e )
	{
		AssessmentPrinter.getText( e ).forEach( System.out::println );

If you use the above construct to print the text generated from the exception thrown as a result of attempting to assert the immutability of the expression `List.of( new ArrayList<>() )`, you will see something like this: (Note: the exact text is subject to change.)

    ■ instance of 'java.util.ImmutableCollections.List12' is mutable because index 0 contains mutable instance of 'java.lang.StringBuilder'. (MutableComponentMutableObjectAssessment)
    ├─■ type 'java.util.ImmutableCollections.List12' is provisory because it is preassessed by default as a composite class. (CompositeProvisoryTypeAssessment)
    └─■ instance of 'java.lang.StringBuilder' is mutable because it is of a mutable class. (MutableClassMutableObjectAssessment)
      └─■ class 'java.lang.StringBuilder' is mutable because it extends mutable class 'java.lang.AbstractStringBuilder'. (MutableSuperclassMutableTypeAssessment)
        └─■ class 'java.lang.AbstractStringBuilder' is mutable due to multiple reasons. (MultiReasonMutableTypeAssessment)
          ├─■ class 'java.lang.AbstractStringBuilder' is mutable because field 'value' is mutable. (MutableFieldMutableTypeAssessment)
          │ └─■ field 'value' is mutable because it is not final, and it has not been annotated with @Invariable. (VariableMutableFieldAssessment)
          ├─■ class 'java.lang.AbstractStringBuilder' is mutable because field 'coder' is mutable. (MutableFieldMutableTypeAssessment)
          │ └─■ field 'coder' is mutable because it is not final, and it has not been annotated with @Invariable. (VariableMutableFieldAssessment)
          └─■ class 'java.lang.AbstractStringBuilder' is mutable because field 'count' is mutable. (MutableFieldMutableTypeAssessment)
            └─■ field 'count' is mutable because it is not final, and it has not been annotated with @Invariable. (VariableMutableFieldAssessment)

## License and Copyright
                       
All modules that comprise Bathyscaphe are Copyright © 2022, Michael Belivanakis.

The bathyscaphe-claims module is published under the Apache License v2.0. 

A copy of the APACHE-2.0 license is included with Bathyscaphe, and it can also be found on the Apache website, for example here: https://www.apache.org/licenses/LICENSE-2.0

The rest of the modules are published under a dual-license scheme. You can choose among the following:
  - The GNU Affero General Public License (GNU AGPL) v.3
  - The Bathyscaphe Alternative Terms Commercial License (BATCL) v.1

If you use the software without taking any action regarding licensing, then the license which applies by default is the GNU AGPL v.3 licence.

### The GNU Affero General Public License (GNU AGPL) v.3

Bathyscaphe is free software; you can redistribute it and/or modify it under the terms of the GNU Affero General Public License (AGPL) v.3 as published by the Free Software Foundation.

A copy of the GNU AGPL v.3 is included with Bathyscape, and it can also be found on the GNU website, for example here: https://www.gnu.org/licenses/agpl-3.0.en.html

### The Bathyscaphe Alternative Terms Commercial License (BATCL) v.1

Non-free versions of Bathyscaphe are available under terms different from those of the GNU AGPL, in that they do not require you to publish the source code of everything that you create that makes use of Bathyscaphe. For these alternative terms you must purchase a commercial license from the author. 

Bathyscaphe includes a copy of the BATCL v.1, and you can also find it on the Bathyscaphe website, for example here: (TODO)

The commercial license is issued for a specific version of the software. If you wish to use another version, you need a new license. This is necessary in order to fund the continuous development of the software.

### Disclaimer

This software is distributed WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the full text of the license for more details.

### Governing Law

These licenses and any dispute arising out of or in connection with these licenses, shall be governed by and construed in accordance with the laws of The Netherlands.

### Violations    

If you have good reasons to believe that an organization or individual is using this software in ways which are not compliant with the GNU AGPL v.3, while at the same time they have not purchased a commercial license for the particular version of the software that they are using, please contact the author.

## Contacting the author

The author's e-mail address can be found on the sidebar of his blog: https://blog.michael.gr.

## Coding style

When I write code as part of a team of developers, I use the teams' coding style.  
But when I write code for myself, I use _**my very own™**_ coding style.

More information: [michael.gr - On Coding Style](https://blog.michael.gr/2018/04/on-coding-style.html)

## Poor man's issue and TODO tracking

TODO: possibly rename 'claims' to 'promises'.

TODO: reduce the size of the assessment hierarchy by replacing some leaf classes with parameters to their common base class.

TODO: fix some TODOs in the code.

TODO: Add sealed class analysis -- This may allow an otherwise provisory field to be assessed as immutable, if it is of extensible type when that extensible type belongs to a sealed group of which all member-classes have been determined to be immutable.

TODO: add a quick check for records? -- probably won't gain anything because a record may contain mutable members.

TODO: the generic arguments of fields can be discovered using reflection; therefore, it might be possible in many cases to conclusively assess whether a collection field is immutable if the collection is unmodifiable and the element type is immutable.

TODO: possibly use bytecode analysis to determine whether a class mutates a field or an array outside its constructor. This may forgo the need for invariability annotations in some cases. However, this will probably not gain us much, because most fields that are only mutated within constructors are actually declared final. Effectively final fields that are not declared final are usually so because they are in fact mutated outside the constructor. (For example, the cached hashcode in `java.lang.String`.)

TODO: possible bug: how will assessment go if an object has provisory fields and is also iterable?

TODO: add @Pure method annotation and use bytecode analysis to make sure it is truthful. (However, it will not buy us much, because purity does not imply thread-safety: a pure function may attempt to read memory that is concurrently written by another function, with disastrous consequences. What might buy us more is asserting a combination of purity and co-coherence, but I still need to think about that. In any case, this should probably be the subject of another module.)
