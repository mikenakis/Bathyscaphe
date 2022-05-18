# mikenakis:bathyscaphe

#### Deep immutability (and coming soon: thread-safety) assessment for Java objects

**_bathyscaphe_** (ˈbæθɪˌskeɪf, -ˌskæf) (noun) A submersible vessel that carries a crew, is able to maneuver independently, and
is capable of reaching great depths.

<p align="center">
<img title="mikenakis:bathyscaphe logo" src="mikenakis-bathyscaphe-logo.svg" width="256"/><br/>
The mikenakis:bathyscaphe logo, a line drawing of <i>bathyscaphe Trieste</i><br/>
by Mike Nakis, based on a drawing found at <a href="https://bertrandpiccard.com/3-generations/jacques-piccard">bertrandpiccard.com</a><br/>
</p>

## Description

mikenakis:bathyscaphe is a java library that can be used to inspect objects at runtime and assert that they are immutable.

Many times we can tell whether an object is mutable or immutable by just looking at its class: some classes are definitely mutable, while some classes are definitely immutable. There exist static analysis tools that can determine this; however, in many cases it is not enough to just look at the class in order to determine whether an object is immutable, and static analysis tools that nonetheless try to do so give assessments that are wrong, or in the best case useless:
1. _Effectively immutable_ classes behave in a perfectly immutable fashion, but under the hood they are strictly-speaking mutable, due to a number of reasons, for example because they perform lazy initialization, or because they contain arrays, which are by definition mutable. The most famous example of such a class is `java.lang.String`.
    - Static analysis tools tend to erroneously classify effectively immutable classes as mutable, which is a false negative. 
    - Note that `java.lang.String` is not so much of a problem, because it can be treated as a special case, but special-casing is a drastic measure which should be used as seldom as possible, and certainly not for every single effectively-immutable class that we write.
2. _Superficially immutable_ classes are classes which are unmodifiable, but they contain members whose immutability they cannot vouch for. The most famous example of classes in this category are the so-called unmodifiable collection classes of java, such as result of invoking `java.util.List.of()`.    
    - Some static analysis tools erroneously report superficially immutable classes as immutable, which is a false positive, as we can easily prove with `List.of( new StringBuilder() )`. 
    - Some static analysis tools erroneously report superficially immutable classes as mutable, which is a false negative, as we can easily prove with `List.of( 1, 2, 3 )`.

So, to the question "is the result of `List.of()` immutable?" the only correct answer is "I don't know". We cannot issue a conclusive immutability assessment just by looking at the class returned by `List.of()`, so we need to assess the immutability of each and every instance of that class at runtime. That's what mikenakis:bathyscaphe does.

For any given class, mikenakis:bathyscaphe can issue three possible assessments:
  - Mutable
  - Immutable
  - Provisory

The first two are straightforward: if the class of an object has been conclusively assessed as mutable or immutable, the object receives the same assessment, and we are done. However, if the class of an object has been assessed as provisory, this means that the object _might_ be immutable, but it needs to be more thoroughly examined. mikenakis:bathyscaphe does all this examination, and finally issues an assessment for an object: the object is either mutable or immutable.  

Bathyscaphe has a very small interface. It exposes only a couple of static methods, it defines a couple of annotations that you can use in your classes, and it defines an interface that your classes may, on a rare occasion, implement. Let's look at those in detail.

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

Note that it is an error to annotate an array with `@InvariableArray` unless the array field itself is either `final` or annotated with `@Invariable`. Also note that it is an error to use any of these annotations on non-private fields.

Sometimes the question of whether an object is mutable or immutable can be so complicated, that only the object itself can tell for sure whether it is mutable or immutable. For example, sometimes we write classes that are 'freezable', meaning that they begin their life as mutable, and at some later moment they are 'frozen', thus becoming immutable from that moment on. For this purpose, bathyscaphe defines the `ImmutabilitySelfAssessable` interface. If your class implements this interface, bathyscaphe will invoke instances of your class to ask them whether they are immutable or not. 

Naturally, when an object that we intended to be immutable is assessed by bathyscaphe as mutable, we would like to have an explanation as to exactly why this assessment was issued, so that we can find where the problem is, and correct it. For this reason, there is a separate module called mikenakis:bathyscaphe-print which can be used to obtain detailed diagnostics from an `ObjectMustBeImmutableException`.

mikenakis:bathyscaphe-print can be used as follows:

	catch( ObjectMustBeImmutableException e )
	{
		AssessmentPrinter.getText( e ).forEach( System.out::println );

If you assert the immutability of `List.of( new ArrayList<>() )`, and you use the above construct to print the text generated from the exception thrown, you will see something like this: (Note: the exact text is subject to change.)

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

## License

See LICENSE.md (https://github.com/mikenakis/Bathyscaphe/blob/master/LICENSE.md)

If you want to do anything with this creative work, contact me.

## Contacting the author

You can find my e-mail address in the form of an image on the right sidebar of my blog, at https://blog.michael.gr

## Coding style

When I write code as part of a team of developers, I use the teams' coding style.  
But when I write code for myself, I use _**my very own™**_ coding style.

More information: [michael.gr - On Coding Style](https://blog.michael.gr/2018/04/on-coding-style.html)

## Poor man's issue and TODO tracking

TODO: reduce the size of the assessment hierarchy by replacing some leaf classes with parameters to their common base class.

TODO: fix some TODOs in the code.

TODO: Add sealed class analysis -- This may allow an otherwise provisory field to be assessed as immutable, if it is of extensible type when that extensible type belongs to a sealed group of which all member-classes have been determined to be immutable.

TODO: add a quick check for records? -- probably won't gain anything because a record may contain mutable members.

TODO: the generic arguments of fields can be discovered using reflection; therefore, it might be possible in many cases to conclusively assess whether a collection field is immutable if the collection is unmodifiable and the element type is immutable.

TODO: possibly use bytecode analysis to determine whether a class mutates a field or an array outside its constructor. This may forgo the need for invariability annotations in some cases. However, this will probably not gain us much, because most fields that are only mutated within constructors are actually declared final. Effectively final fields that are not declared final are usually so because they are in fact mutated outside the constructor. (For example, the cached hashcode in `java.lang.String`.)

TODO: possible bug: how will assessment go if an object has provisory fields and is also iterable?

TODO: add @Pure method annotation and use bytecode analysis to make sure it is truthful. (However, it will not buy us much, because purity does not imply thread-safety: a pure function may attempt to read memory that is concurrently written by another function, with disastrous consequences. What might buy us more is asserting a combination of purity and co-coherence, but I still need to think about that. In any case, this should probably be the subject of another module.)
