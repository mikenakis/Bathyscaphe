# Bathyscaphe Glossary

<sup>Note: some of the glossary terms (i.e. **_variable_** / **_invariable_**, **_extensible_** / **_inextensible_**) are introduced in order to mitigate the ambiguities caused by Java's unfortunate decision to reuse certain language keywords (i.e. `final`) to mean entirely different things in different situations. (i.e. a `final` class vs. a `final` field.)</sup>

- **_Assertion Method_** - a pattern of my own devise, for writing comprehensive assertions.
	- An assertion method is identified by the suffix `Assertion` in its name.
	- An assertion method is only meant to be invoked from within an `assert` statement.
	- To prevent misuse, an assertion method should generally avoid returning `false` to indicate failure; instead, an assertion method should either return `true`, or throw an exception.
	- For clarity, if an assertion method checks one thing and throws one exception, then the exception should have the same name as the assertion method, only suffixed with `Exception` instead of `Assertion`.
		- From this it follows that exceptions should have assertive names, describing what is expected, such as `PointerMustBeNonNullException`, instead of declarative names, describing postmortem findings, such as `NullPointerException`. One day I will write an article explaining this in greater detail.


- **_Assessment_** - the result of examining an object or a class to determine whether it is immutable or not. Bathyscaphe contains a hierarchy of assessments, which is divided into a few distinct sub-hierarchies: one for type assessments, one for object assessments, one for field assessments, and one for field value assessments. These hierarchies all share a common ancestor for the sole purpose of constructing assessment trees, where the children of an assessment are the reasons due to which the assessment was issued. Also see **_Type assessment_**, **_Object assessment_**.


- **_Bathyscaphe_** - (/ˈbæθɪskeɪf/ or /ˈbæθɪskæf/) (noun) a free-diving, self-propelled, deep-sea submersible with a crew cabin. Being yellow is not a strict requirement. See [Wikipedia - Bathyscaphe](https://en.wikipedia.org/wiki/Bathyscaphe)


- **_Conclusive Assessment_** - an assessment which classified a type as definitely mutable or definitely immutable. This means that instances of this type can receive the same assessment without any further inspection of their contents. Also see opposite: **_Inconclusive assessment_**. 


- **_Deep Immutability_** - the immutability of an entire object graph reachable from a certain object, as opposed to the immutability of only that object. It is among the fundamental premises of Bathyscaphe that this is the only type of immutability that really matters. Also see opposite: **_Superficial Immutability_**.


- **_Effectively Immutable_** - classes that behave in an immutable fashion, but under the hood are strictly speaking mutable, due to various reasons, for example because they perform lazy initialization, or because they contain arrays. (Arrays in Java are mutable by nature.) The most famous example of such a class is `java.lang.String`. Note that this definition differs from the one given in the book _Java Concurrency In Practice_, section 3.5.4 _Effectively Immutable Objects_, which is not really about objects, but rather about **_treatment_** of objects: the book talks about situations where mutable objects (for example `java.util.Date`) are being passed around between threads, but the threads refrain from mutating them, so all is good. This sounds like catastrophe waiting to happen, and Bathyscaphe exists precisely in order to prevent programmers from doing things like that.


- **_Extensible class_** - a class that may be sub-classed (extended.) Corresponds to the absence of the language keyword `final` in the class definition. Also see opposite: **_Inextensible Class_**.


- **_Freezable class_** - a class which begins its life as mutable, so that it can undergo complex initialization, and is at some moment instructed to freeze in-place, thus becoming immutable from that moment on. For more information see the relevant appendix in the introductory blog post: [michael.gr - Bathyscaphe](https://blog.michael.gr/2022/05/bathyscaphe.html)


- **_Inconclusive assessment_** - See **_Provisory assessment_**.
             

- **_Inextensible Class_** - a class that may not be sub-classed (extended.) Corresponds to the presence of the language keyword `final` in the class definition. Also see opposite: **_Extensible Class_**.


- **_Invariable Field_** - a field that cannot be mutated. Corresponds to the presence of the language keyword `final` in the field definition. Note that invariability here refers only to the field itself, and is entirely without regards to whether the object referenced by the field is immutable or not. Also see opposite: **_Variable Field_**.


- **_Object Assessment_** - represents the result of examining an instance of a class (an object) to determine whether it is immutable or not. One of the fundamental premises of Bathyscaphe is that we must assess objects for immutability because quite often the assessment of types is inconclusive. Bathyscaphe has one assessment to express that an object is immutable, but an entire hierarchy of assessments for all the different ways in which an object can be mutable, so that it can provide diagnostics as to precisely why an object was assessed as mutable. Also see **_Assessment_**, **_Type Assessment_**.


- **_Provisory Assessment_** - a type assessment which did not result in a conclusive **_mutable_** or **_immutable_** result. This means that each instance of that type _may and may not_ be immutable; each instance will have to be individually examined in order to issue a final assessment for that instance. Most provisory type assessments contain extra information about precisely which members of each instance will require further examination.    
  

- **_Shallow Immutability_** - see **_Superficial Immutability_**


- **_Superficial Immutability_** - refers to the immutability of a single object, without regards to the immutability of objects that it references. It is among the fundamental premises of Bathyscaphe that this type of immutability is largely irrelevant. Also see opposite: **_Deep Immutability_**.


- **_Type Assessment_** - represents the result of examining a class to determine whether it is immutable or not. One of the fundamental premises of Bathyscaphe is that type assessment is quite often inconclusive, in which case we must go one step further and assess the immutability of instances of that class. (Objects.) Bathyscaphe has a single assessment to express that a class is immutable, a hierarchy of assessments to represent all the different ways in which a class may be mutable, and another hierarchy of so-called **_provisory_** assessments to represent all the different ways in which a type eludes assessment, necessitating the further assessment of instances of that type. The information contained in type assessments provides explanations as to why that particular assessment was issued. Furthermore, the information contained in provisory assessments is used by Bathyscaphe as a guide in assessing the immutability of instances. Also see **_Assessment_**, **_Object Assessment_**.


- **_Variable Field_** - a field that is free to mutate. Corresponds to the absence of the language keyword `final` in the field definition. Also see opposite: **_Invariable Field_**.
