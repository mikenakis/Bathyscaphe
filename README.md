# Bathyscaphe

#### Deep immutability and thread-safety assessment for Java objects

<p align="center">
<img title="Bathyscaphe Logo" src="bathyscaphe-logo.svg" width="256"/><br/>
The Bathyscaphe logo, a line drawing of <b><i>bathyscaphe Trieste</i></b><br/>
based on art found at <a href="https://bertrandpiccard.com/3-generations/jacques-piccard">bertrandpiccard.com</a><br/>
</p>

[![CI-Workflow status badge     ](https://img.shields.io/github/workflow/status/mikenakis/Bathyscaphe/CI-Workflow?label=CI-Workflow&logo=github)](https://github.com/mikenakis/Bathyscaphe/actions/workflows/ci.yml)
[![Release-Workflow status badge](https://img.shields.io/github/workflow/status/mikenakis/Bathyscaphe/Release-Workflow?label=Release-Workflow&logo=github)](https://github.com/mikenakis/Bathyscaphe/actions/workflows/release.yml)
[![IntelliJ IDEA badge          ](https://img.shields.io/badge/built_with-IntelliJ_IDEA-blue?logo=intellijIdea&logoColor=pink&color=pink)](#;)
[![Number of files badge        ](https://img.shields.io/github/search/mikenakis/Bathyscaphe/java?label=files&logo=files&logoColor=yellow)](#;)
[![Repository Size badge        ](https://img.shields.io/github/languages/count/mikenakis/Bathyscaphe)](#;)
[![Language badge               ](https://img.shields.io/github/languages/top/mikenakis/Bathyscaphe)](#;)
[![Code size badge              ](https://img.shields.io/github/languages/code-size/mikenakis/Bathyscaphe)](#;)
[![Repo size badge              ](https://img.shields.io/github/repo-size/mikenakis/Bathyscaphe)](#;)
[![File size badge              ](https://img.shields.io/github/size/mikenakis/Bathyscaphe/README.md?label=size+of+this+README.md)](#;)
[![GitHub discussions badge     ](https://img.shields.io/badge/chat-on_github-4fb999?logo=googlechat)](https://github.com/mikenakis/Bathyscaphe/discussions)
[![Latest release badge         ](https://img.shields.io/github/v/release/mikenakis/Bathyscaphe?label=latest+release&include_prereleases&sort=semver)](#;)
[![Website badge                ](https://img.shields.io/website?down_color=lightgrey&down_message=offline&up_color=limegreen&up_message=online&url=https%3A%2F%2Fblog.michael.gr)](https://blog.michael.gr/2022/05/bathyscaphe.html)
[![Contributors badge           ](https://img.shields.io/github/contributors/mikenakis/Bathyscaphe)](https://github.com/mikenakis/Bathyscaphe/graphs/contributors)
[![Commit activity badge        ](https://img.shields.io/github/commit-activity/y/mikenakis/Bathyscaphe)](https://github.com/mikenakis/Bathyscaphe/graphs/commit-activity)
[![Last commit badge            ](https://img.shields.io/github/last-commit/mikenakis/Bathyscaphe)](#;)
[![Last Release Date badge      ](https://img.shields.io/github/release-date/mikenakis/Bathyscaphe?label=last+release)](#;)
[![Last Pre-Release Date badge  ](https://img.shields.io/github/release-date-pre/mikenakis/Bathyscaphe?label=last+pre-release)](#;) 
[![License badge                ](https://img.shields.io/badge/license-dual-red?logo=)](https://github.com/mikenakis/Bathyscaphe/blob/master/LICENSE.md)
[![GitHub pulse badge           ](https://img.shields.io/badge/%E2%80%8B-pulse-blue?logo=github)](https://github.com/mikenakis/Bathyscaphe/pulse)
[![GitHub dependencies badge    ](https://img.shields.io/badge/%E2%80%8B-dependencies-blue?logo=github)](https://github.com/mikenakis/Bathyscaphe/network/dependencies)
[![GitHub traffic badge         ](https://img.shields.io/badge/%E2%80%8B-traffic-blue?logo=github)](https://github.com/mikenakis/Bathyscaphe/graphs/traffic)

`SPDX-FileCopyrightText: © 2022, Michael Belivanakis, a.k.a. MikeNakis, michael.gr`<br/>
`SPDX-License-Identifier: AGPL-3.0-only OR BATCL-1.0`

<!--- TODO: enable this once we have releases on maven central: 
      Maven Central badge /maven-central/v/:groupId/:artifactId?versionPrefix=29&versionSuffix=-android --->

<!--- TODO: enable these when they become non-zero -->
<!--- 
[![GitHub issues badge       ](https://img.shields.io/github/issues/mikenakis/Bathyscaphe)](https://github.com/mikenakis/Bathyscaphe/issues)
[![GitHub issues badge       ](https://img.shields.io/github/issues-raw/mikenakis/Bathyscaphe)](https://github.com/mikenakis/Bathyscaphe/issues)
[![GitHub closed issues badge](https://img.shields.io/github/issues-closed/mikenakis/Bathyscaphe)](https://github.com/mikenakis/Bathyscaphe/issues)
[![GitHub closed issues badge](https://img.shields.io/github/issues-closed-raw/mikenakis/Bathyscaphe)](https://github.com/mikenakis/Bathyscaphe/issues) 
[![GitHub all releases badge](https://img.shields.io/github/downloads/mikenakis/Bathyscaphe/total)]() 
![GitHub Discussions badge](https://img.shields.io/github/discussions/mikenakis/Bathyscaphe) --->

<!--- I am not sure what these are supposed to do --->
<!--- <img src="https://img.shields.io/github/checks-status/mikenakis/Bathyscaphe/master?label=master&logo=github" /> --->
<!--- <img src="https://img.shields.io/github/workflow/status/mikenakis/Bathyscaphe/CI-Workflow?event=push&label=CI&logo=github" /> --->
<!--- TODO: display jar file size --->
<!--- <img src="https://img.badgesize.io/mikenakis/Bathyscaphe/:filepath[?compression=gzip|brotli][&label=string][&max=string][&softmax=string]" /> --->

<!--- TODO: none of these work, and the documentation is absolutely horrid. --->
<!--- ![Stack Exchange questions badge](https://img.shields.io/stackexchange/stackoverflow/t/java)
![Stack Exchange monthly questions badge](https://img.shields.io/stackexchange/stackoverflow/qm/java)
![Stack Exchange reputation badge](https://img.shields.io/stackexchange/stackoverflow/r/773113?order=desc&sort=reputation&site=stackoverflow&?cacheSeconds=1) --->

<!--- TODO: for complicated stuff, see this post on how to generate README.md from a template: https://stackoverflow.com/a/69750410/773113 --->

<!--- GitHub fails to detect the license, so it always shows "not identifiable by github" ---> 
<!--- [![GitHub license badge](https://img.shields.io/github/license/mikenakis/Bathyscaphe)]() --->

<!--- Another example repository with dual license: https://github.com/evencart/evencart/blob/dev/LICENSE.txt --->
<!--- A repository with a couple of commercial license examples: https://github.com/evencart/evencart/blob/dev/LICENSE.txt
      (also with an example github yml that converts docx to pdf and commits back to the repository) --->

## Table of contents

- [Description](#description)
- [Highlights](#highlights)
- [How it works](#how-it-works)
- [How to use](#usage)
    - [Asserting immutability](#usage-asserting-immutability)
        - [The objectMustBeImmutableAssertion() method](#usage-asserting-immutability-method)
    - [Adding pre-assessments](#usage-adding-pre-assessments)
        - [The addImmutablePreassessment() method](#usage-adding-pre-assessments-method)
    - [Annotating fields](#usage-annotating-fields)
        - [The @Invariable annotation](#usage-annotating-fields-invariable)
        - [The @InvariableArray annotation](#usage-annotating-fields-invariable-array)
    - [Self-assessment](#usage-self-assessment)
        - [The ImmutabilitySelfAssessable interface](#usage-self-assessment-interface)
    - [Obtaining diagnostics](#usage-obtaining-diagnostics)
        - [The explain() method](#usage-obtaining-diagnostics-method)
- [Status (Maturity) of the project](#maturity)
- [Dependencies](#dependencies)
- [Requirements](#requirements)
- [Installation](#installation)
- [Copyright & License](#license)
- [Contacting the author](#contact)
- [Glossary](#glossary)
- [Contributing](#contributing)
- [Code of Conduct](#code-of-conduct)
- [Sponsoring](#sponsoring)
- [Coding style](#coding-style)
- [Frequently Asked Questions](#faq)
- [Feedback](#feedback)
- [Poor man's issue and TODO tracking](#issues)

## <a name="description">&ZeroWidthSpace;</a>Description

Bathyscaphe is an open-source java library that you can use to inspect objects at runtime and assert that they are immutable.

This document contains reference material about Bathyscaphe, assuming that you already understand what problem it solves, why it is a problem, why everyone has this problem, why it needs fixing, and why other tools fail to fix it. If not, please start by reading this article which introduces Bathyscaphe: [michael.gr - Bathyscaphe](https://blog.michael.gr/2022/05/bathyscaphe.html)

## <a name="highlights">&ZeroWidthSpace;</a>Highlights

- It works, as opposed to static analysis tools, which do not work. For example, it will assess `List.of( new StringBuilder() )` as mutable, but `List.of( 1 )` as immutable.
- It is lightning fast: the `assert` keyword ensures **_zero performance penalty on production_**.
- It is very small: The `Bathyscaphe` JAR is about 100 kilobytes. The `BathyscapheClaims` JAR is a couple of kilobytes.
- It has no dependencies outside of the core Java Runtime Environment.
- It is easy to use: Just `assert Bathyscaphe.objectMustBeImmutableAssertion( myObject );` and if it fails, it yields extensive diagnostics in human-readable form explaining precisely why this happened. 
- It is easy to integrate: Just add a maven-central dependency. (Coming soon.)
- The annotations module, which will be used by most code out there, comes with a free-of-charge and very permissive license (MIT).
- The actual assessment module comes with a choice of either a free-of-charge copyleft license (AGPL), or an inexpensive commercial license. 

## <a name="how-it-works">&ZeroWidthSpace;</a>How it works

Bathyscaphe consists of two parts: 

1. **Bathyscaphe**
   - Repository: https://github.com/mikenakis/Bathyscaphe
   - Contains the immutability assessment library.
   - Few software systems are likely to invoke this library, and then only from a few places, where immutability needs to be ascertained. For example, a custom `HashMap` class might contain a call to bathyscaphe, to assert that keys added to it are immutable.
2. **BathyscapheClaims**
   - Repository: https://github.com/mikenakis/BathyscapheClaims
   - Contains annotations, interfaces, etc. that you can add to your classes to guide assessment. 
   - Most client code is expected to make use of only this module of Bathyscaphe.
	
When assessing whether an object is immutable or not, Bathyscaphe begins by looking at the class of the object, and issues one of the following assessments:

1. Mutable (Conclusive)
1. Immutable (Conclusive)
1. Provisory (Inconclusive)

The first two are straightforward: if a class is conclusively assessed as mutable or immutable, then each instance of that class receives the same assessment, and we are done; however, if the class receives a provisory assessment, then Bathyscaphe proceeds to examine the contents of the object.

For example, if a class looks immutable in all aspects except that it declares a final field of interface type, Bathyscaphe will recursively assess the immutability of the object referenced by that field.

Note that this yields consistently accurate assessments in cases where static analysis tools fail, because they only examine classes, so when a class contains a field which _might_ be mutable, (such as an interface, or any non-final type,) they have no option but to err on the side of caution and assess the containing class as mutable.

## <a name="usage">&ZeroWidthSpace;</a>How to use

### <a name="usage-asserting-immutability">&ZeroWidthSpace;</a>Asserting immutability

- #### <a name="usage-asserting-immutability-method">&ZeroWidthSpace;</a>The `objectMustBeImmutableAssertion()` method

  The main thing you are likely to do with Bathyscaphe is this:

      assert Bathyscaphe.objectMustBeImmutableAssertion( myObject );  

  If `myObject` is immutable, this will succeed; otherwise, an `ObjectMustBeImmutableException` will be thrown.

  Note that the assertion statement itself will never fail, because `objectMustBeImmutableAssertion()` never returns `false`; It either returns `true`, or it throws `ObjectMustBeImmutableException`. The benefit of using the `assert` keyword is that the method will not be invoked unless assertions are enabled, which is how Bathyscaphe can boast zero performance overhead on production.

### <a name="usage-adding-pre-assessments">&ZeroWidthSpace;</a>Adding pre-assessments

- #### <a name="usage-adding-pre-assessments-method">&ZeroWidthSpace;</a>The `addImmutablePreassessment()` method

  Suppose that we have a class which is _effectively immutable_, meaning that it behaves immutably, but under the hood it is strictly speaking mutable, either because it is making use of lazy initialization, or simply because it contains an array. (Arrays in Java are mutable by nature.) If Bathyscaphe was to assess the immutability of this class, it would find it to be mutable; however, we know that the class behaves immutably, so we want to instruct Bathyscaphe to skip assessment and consider it as immutable. This is accomplished by adding what is known as a _pre-assessment_ or _assessment override_, as follows:

      Bathyscaphe.addImmutablePreassessment( EffectivelyImmutableClass.class );

  One famous effectively immutable class is `java.lang.String`, which contains both an array of characters and a lazily initialized hash-code field. Bathyscaphe has a built-in pre-assessment for `java.lang.String` and a few other well-known effectively immutable classes of the JDK.

Pre-assessment should be used only on classes whose source code we have no control over, such as classes found in the JDK or in third-party libraries. For classes that we write and can thus modify, see next section.

### <a name="usage-annotating-fields">&ZeroWidthSpace;</a>Annotating fields

If you write an effectively immutable class, you should use the annotations found in the `bathyscaphe-claims` module to annotate each effectively immutable field of that class, thus allowing Bathyscaphe to assess the immutability of the remaining fields and issue an assessment for your class as a whole.

- #### <a name="usage-annotating-fields-invariable">&ZeroWidthSpace;</a>The `@Invariable` annotation

  Suppose that we have a non-final field in an otherwise immutable class. The presence of such a field would normally cause Bathyscaphe to assess the declaring class as mutable; however, we know that this particular field will behave as if it was final, so we would like to tell Bathyscaphe to consider it as final. This is accomplished as follows:

      @Invariable private int myLazilyInitializedHashCode;

  Thus, if the class meets all other requirements for immutability, Bathyscaphe will assess the class as immutable.

- #### <a name="usage-annotating-fields-invariable-array">&ZeroWidthSpace;</a>The `@InvariableArray` annotation

  Suppose that we have a field which is final, but it is of array type. Arrays are by definition mutable in Java, so the presence of this field would normally cause Bathyscaphe to assess the declaring class as mutable; however, we know that this particular field will behave as if it was immutable, so we would like to tell Bathyscaphe to refrain from assessing that field, and consider it as immutable. This is accomplished as follows:

      @InvariableArray private final byte[] mySha256Hash;

  Thus, if the class meets all other requirements for immutability, Bathyscaphe will assess the class as immutable.

Note that `@Invariable` and `@InvariableArray` can be combined.

Also note that it is illegal to use either of these annotations on non-private fields, because a class cannot give any promises about fields that may be mutated by other classes.

Also note that with these annotations we are only promising **_shallow immutability_**; Bathyscaphe will still perform all the checks necessary in order to ascertain **_deep immutability_**. So, for example, if the field was of type `Foo` instead of `int`, or if the array field was an array of `Foo` instead of an array of `byte`, then Bathyscaphe would recursively assess the immutability of `Foo` as part of assessing the immutability of the field.

### <a name="usage-self-assessment">&ZeroWidthSpace;</a>Self-assessment

- #### <a name="usage-self-assessment-interface">&ZeroWidthSpace;</a>The `ImmutabilitySelfAssessable` interface

  Sometimes, the question whether an object is mutable or immutable can be so complicated, that only the object itself can answer the question for sure. (For an example, see **_freezable class_** in the glossary.) In order to accommodate such cases, the bathyscaphe-claims module defines the `ImmutabilitySelfAssessable` interface. If your class implements this interface, bathyscaphe will be invoking instances of your class, asking them whether they are immutable or not. Here is an example:

      public class MyFreezableClass implements ImmutabilitySelfAssessable
      {
          private int counter; //obviously mutable 
          private boolean frozen;
          public void mutate() { assert !frozen; mutable++; }
          public void freeze() { assert !frozen; frozen = true; }
          @Override public boolean isImmutable() { return frozen; }
      }

### <a name="usage-obtaining-diagnostics">&ZeroWidthSpace;</a>Obtaining diagnostics

- #### <a name="usage-obtaining-diagnostics-method">&ZeroWidthSpace;</a>The `explain()` method

  Suppose that there is a certain object which we intended to be immutable, but Bathyscaphe finds it to be mutable. We would like to know exactly why Bathyscaphe issues this assessment, so that we can locate the problem and fix it. Here is how:

      Object myObject = List.of( new StringBuilder() );
      try
      {
          assert Bathyscaphe.objectMustBeImmutableAssertion( myObject ); 
      }
      catch( ObjectMustBeImmutableException e ) 
      {
          Bathyscaphe.explain( e ).forEach( System.out::println );
      }

  The above code will emit to the standard output a detailed human-readable diagnostic message explaining exactly why the assessment was issued. The text will look something like this: (Note: the exact text is subject to change.)

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

## <a name="maturity">&ZeroWidthSpace;</a>Status (maturity) of the project

The _**Technology Readiness Level**_ (TRL) so-to-speak of Bathyscaphe currently is **_5: Technology validated in lab_**.
- The library works, it appears to be problem-free, and it produces very good results; however, the only environment in which it is currently being put into use is the author's hobby projects, which is about as good as laboratory use.
- There is at least one major (but optional) feature pending to be implemented: thread-safety assessment.
- There is at least one major task pending to be done: publish on maven-central.  
- Since the project is still young, new releases are likely to contain breaking changes. (The major version number will always be incremented to indicate so.) 
             
## <a name="dependencies">&ZeroWidthSpace;</a>Dependencies
                                                          
- The `bathyscaphe-test` module necessarily depends on JUnit.

- The `bathyscaphe` and `bathyscaphe-claims` modules do not depend on anything outside the Java Runtime Environment.
  - Let me repeat this: Bathyscaphe. Has. No. Dependencies. It depends on nothing. When you include the Bathyscaphe JARs in a project, you are including those JARs and nothing else.

## <a name="requirements">&ZeroWidthSpace;</a>Requirements

- Module `bathyscaphe-claims`:
  - Requires java 8 to compile. 
  - It could probably compile on older java versions, but I have not tried it.
  - It will almost certainly run on even older JREs, but I have not tried it.
- Module `bathyscape`:
  - Requires java 17 to compile, and it actually makes use of java 17 features.
  - I will be upgrading to java 18 as soon as I find the chance. 
  - I do not care if that is too avant-garde for some people; by the time Bathyscaphe becomes widely adopted, Java 18 will be old.
  - It might run on older JREs, but I have not tried it.
  - It will almost certainly run on older JREs if I specify an older \<target\> to the java-compiler-plugin, but I have not tried that either.
  - I have not tried these things because this kind of experimentation has very low priority at the moment.  

## <a name="installation">&ZeroWidthSpace;</a>Installation

- In the near future, Bathyscaphe artifacts will be released to maven central, so you will not have to specify an artifact repository; however, for the time being, Bathyscaphe artifacts are not being released to maven central, and the situation is as follows:
	- Although the artifacts are being built on GitHub, and GitHub does store the artifacts, GitHub makes it difficult to use it as a maven artifact repository, or in any case I do not yet understand how to do that.
	- There appears to be a solution involving some jitpack.io, but I could not get it to work.
	- So, while I am figuring all this out, you can simply clone Bathyscaphe into your project, so that it builds along with your project.

## <a name="license">&ZeroWidthSpace;</a>Copyright & License
 
Bathyscaphe is copyright © 2022, Michael Belivanakis, a.k.a. MikeNakis, michael.gr

You may not use this library except in compliance with the license.

For information regarding licensing Bathyscaphe, please see [LICENSE.md](LICENSE.md)

## <a name="contact">&ZeroWidthSpace;</a>Contacting the author

The author's e-mail address can be found on the sidebar of his blog: https://blog.michael.gr.

## <a name="glossary">&ZeroWidthSpace;</a>Glossary
                       
- See [GLOSSARY.md](GLOSSARY.md)

## <a name="contributing">&ZeroWidthSpace;</a>Contributing
                        
- See [CONTRIBUTING.md](CONTRIBUTING.md)

## <a name="code-of-conduct">&ZeroWidthSpace;</a>Code of Conduct
                        
- See [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md)

## <a name="sponsoring">&ZeroWidthSpace;</a>Sponsoring

- If you would like to fund me to continue developing Bathyscaphe, or if you would like to see a DotNet version of Bathyscaphe sooner rather than later, you can bestow me with large sums of money; that always helps.

- Sponsoring link: https://paypal.me/mikenakis

## <a name="coding-style">&ZeroWidthSpace;</a>Coding style

- When I write code as part of a team of developers, I use the teams' coding style, but when I write code for myself, I use _**My Very Own™**_ coding style.
- As a result, Bathyscaphe uses My Very Own™ Coding Style.
- More information: [michael.gr - My Very Own™ Coding Style](https://blog.michael.gr/2018/04/on-coding-style.html)

## <a name="faq">&ZeroWidthSpace;</a>Frequently Asked Questions (F.A.Q., FAQ)
              
- See [FAQ.md](FAQ.md)

## <a name="feedback">&ZeroWidthSpace;</a>Feedback

- Please visit our [discussions area](https://github.com/mikenakis/Bathyscaphe/discussions) to leave feedback, criticism, praise, feature requests, bug reports, haikus, whatever.  

## <a name="issues">&ZeroWidthSpace;</a>Poor man's issue and TODO tracking

TODO: promote Bathyscaphe

- Make more extensive use of GitHub Pages. See https://github.com/showcases/github-pages-examples
- Add a "Who What Where When Why How"
- Create a very short "what is bathyscaphe" video
- Create a very short "how to use bathyscaphe" video
- Create a short animated gif
- Promote on Stack Overflow, Reddit, Hacker News, Quora, Twitter, DZone, Lobste.rs. Search for questions that are asking for a tool that does what Bathyscaphe does, or post such a question and perhaps then answer it.
- Promote to the local JUG. (Contact Freek about this)
- Emphasize the possibility of collaboration between Bathyscaphe for dynamic analysis and MutabilityDetector for static analysis.
- Start maintaining a change log. Add an entry to the log each time:
	- a bug is fixed.
	- a feature is added.
	- a breaking API change is made.
- Research whether there should be a tag for each release, or whether maven's snapshot scheme renders tags unnecessary. If there should be tags, then add their creation to the release workflow.
- Enable issue tracking on GitHub.
- Look for people to take over the project from me as soon as I start feeling that I would like to move on with my other stuff.

TODO: check out opencollective.

- See opencollective.com
- For example: https://opencollective.com/shields

TODO: add thread-safety assessment. A class is thread-safe if:

- It has been annotated with @ThreadSafe.
- It consists of fields that are either immutable, or invariable and in turn of a thread-safe class, or annotated as @ThreadSafe.

TODO: possibly introduce an `@Immutable` annotation.

- Look for it by simple name, thus honoring it regardless of package.
- Treat any class annotated as such as immutable without analyzing it. The idea behind this is that if the developer already has a static analysis tool, then that tool can make sure that classes marked as `@Immutable` are in fact immutable, so that Bathyscaphe does not have to repeat the checks.
- Be sure to include big disclaimers that the use of the `@Immutable` annotation bypasses Bathyscaphe, so it should only be used if the developer already has other means of statically ascertaining immutability.

TODO: reduce the size of the assessment hierarchy

- Replace some leaf classes with parameters to their common base class.

TODO: fix some TODOs in the code.

TODO: Add sealed class analysis

- This may allow an otherwise provisory field to be assessed as immutable, if it is of extensible type when that extensible type belongs to a sealed group of which all member-classes have been determined to be immutable.

TODO: Look into generic field arguments

- The actual types of generic arguments of fields can be discovered using reflection; therefore, it might be possible in some cases to conclusively assess a collection field as immutable if the field is invariable, the collection is unchangeable, and the element type of the collection is immutable.

TODO: possible bug: how will assessment go if an object has provisory fields and is also iterable?

TODO: handle multi-dimensional invariable arrays.

- the @InvariableArray annotation might benefit from an integer parameter indicating the number of dimensions for which invariability is promised, so that we can declare an invariable array of invariable arrays, etc.

TODO: Enable "Sonatype Lift" on GitHub.

- See https://links.sonatype.com/products/lift/github-integration

TODO: prevent the creation of package `bathyscaphe-parent`

- Currently, `bathyscaphe-parent` is included in the list of packages on GitHub, but it is unnecessary and it represents noise.

TODO: publish to maven central. (s01.oss.sonatype.org)

- For deployment instructions, see https://central.sonatype.org/publish/publish-guide/#deployment
- Also see:
	- https://zteater.medium.com/automate-releases-to-maven-central-using-github-actions-a2bf1748b103

TODO: Finalize the "installation" section of this README.md.

TODO: add a README.md badge with JAR file sizes

- use `badgesize.io`
	- For the how-to, see https://github.com/ngryman/badge-size
	- For an example README.md, see https://github.com/twbs/bootstrap/edit/main/README.md

TODO: add a README.md badge with stats about the tests.

- Unfortunately, by looking at this page: https://shields.io/category/test-results this is not possible.
- However, shields.io supports creating a shield from a custom json endpoint, and a custom json endpoint can be just a static json file created by the build process, so if the build process could somehow generate a json file with stats about the tests, we should be able to generate a badge.

TODO: do something about ZoneId.systemDefault(), Clock.systemUTC(), java.util.KeyValueHolder, etc.

- Add pre-assessments by name?

TODO: Try cloudsmith.io/cloudsmith.com
  - 1st very annoying thing: the "sign-up" link is HIDDEN on their front page.
  - 2nd very annoying thing: they ask for my phone number; I leave it blank and click sign-up; an error message begins to show telling me that the phone number is required; and before I have had time to fully read it, the page is immediately replaced with a welcome page.
  - 3rd very annoying thing: I went through the process of creating what they call a repository; then they showed me the management page of that repository; then I clicked on one of the links on that page; then they took me to a page-not-found page; then the repository disappeared. The "repositories" page is empty and suggests to create a new repository. Then later I refreshed the page and the new repository appeared.
  - Other than that, it seems like I can create a maven repository there. 
  - Next step will be to actually make use of it if something bad happens to repsy.

<strike>TODO:</strike> DONE: Try finding some other service to use as an artifact repository.

- packagecloud.io
  - Will not use, because:
    - Although it supposedly offers a free plan, when you try to sign up it only offers an option which costs $150 per month, and has a "start trial" button. I do not want to start any kind of fucking trial, I want the fucking free plan. 
    - It requires my phone upon signup, and offers no option to fuck-off and skip that part.
- cloudrepo.io
  - Will not use, because:
    - It does not appear to have a free plan.
- cloudsmith.io
  - Give it a try.
- jitpack.io
  - Will not use, because:
    - It does not appear to have a free plan. 
    - Their cheapest plan is $12/month, which I will not give to a company that I never heard of before.
- jfrog.com
  - Will not use, because:
    - I have tried using this in the past, and my experience with it was beyond horrible.

<strike>TODO:</strike> CANNOT-DO: use "GitHub Packages" as an artifact repository.

- At least for snapshots.
- Can't do: It appears to be a deliberate decision of GitHub to prevent this from happening.
- GitHub does not support a concept of an artifact repository; instead, it keeps the artifacts of each project completely separate from artifacts of other projects.
  - You could try using a separate artifact repository for each project, but it would not work either, because GitHub does not allow public access to github-packages; instead, it requires authentication by access token.
    - You could try creating an access token with read-only access to your github-packages and distribute it for public use, but GitHub searches for things that look like access tokens, and if it finds one exposed then it automagically revokes it, which means that sooner or later someone will expose that token, and GitHub will immediately revoke it.
      - Rumor has it that the undocumented GitHub feature which automagically revokes personal access tokens goes by the name of GitGuardian, and allegedly there exists some undocumented way of disabling it, but that is just way too much hassle.  
- The closest anyone has ever gotten to achieve this is https://github.com/TobseF/HelloMaven; note that YOUR_AUTH_TOKEN must be placed in settings.xml.
- In this post: https://github.community/t/how-to-allow-unauthorised-read-access-to-github-packages-maven-repository/115517/3 they admit that it does not work, and they say that they plan to fix it in the future, and that was 2 years ago. 

<strike>TODO:</strike> DONE: drop gitter in favor of github discussions

- See https://docs.github.com/en/discussions/quickstart

<strike>TODO:</strike> DONE: Make the bathyscaphe-claims module completely separate from bathyscaphe

- so as to reduce confusion with respect to licensing

<strike>TODO:</strike> DONE: add a table of contents to README.md

<strike>TODO:</strike> DONE: merge bathyscaphe and bathyscaphe-print

<strike>TODO:</strike> DONE: add a google alert for bathyscaphe.

<strike>TODO:</strike> DONE: add a GitHub actions workflow for making bathyscaphe releases.

<strike>TODO:</strike> WILL-NOT-DO: add a quick check for records

-- No, actually, this will not buy us anything, because a record may contain mutable members. Come to think of it, if records allow mutable members, then what is the point in records?

<strike>TODO:</strike> WILL-NOT-DO: use bytecode analysis to determine whether a class mutates a field outside its constructor. 

- This may alleviate the need for invariability annotations in some cases. 
- No, actually, this will gain us very little, because fields that are only mutated within constructors are usually declared as final anyway; it is bad practice to not declare them as final. Fields that are not declared final are typically so because they are in fact mutated outside the constructor. (For example, the cached hashcode in `java.lang.String`.) The only thing that this would buy us is detection of invariable array fields without the need to annotate them with `@InvariableArray`, but this is a marginal benefit. (Who uses arrays anyway?)

<strike>TODO:</strike> WILL-NOT-DO: add @Pure method annotation

- and use bytecode analysis to make sure it is truthful. Then, assess interfaces as immutable if they consist of nothing but pure methods. 
- No, actually, this will buy us nothing, because purity essentially is unmodifiability, not immutability. Furthermore, purity does not even imply thread-safety: a pure function may attempt to read memory that is concurrently written by another function, with disastrous consequences. What might buy us something is asserting a combination of purity and co-coherence, but I still need to think about that, and in any case, it should probably be the subject of some other module.
