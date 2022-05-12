# Assessment
Deep Assessment of object immutability (And coming soon: deep assessment of object thread-safety)
By Mike Nakis (michael.gr)

<p align="center">
<img title="mikenakis-assessment logo" src="mikenakis-assessment-logo.svg" width="256"/><br/>
The mikenakis-assessment logo, a line drawing of <i>bathyscaphe Trieste</i><br/>
</p>

## Description

TODO

## License

For the time being, this creative work is explicitly published under ***No License***.

In the near future this software will be made available under a dual license scheme. You will be able to choose between:
1. A restrictive non-commercial license (Probably GNU AGPL) free of charge.
2. A commercial license for a small fee.

This means that for the time being, I remain the exclusive copyright holder of this creative work, 
and you may not do anything with it other than view its source code and admire it. 
More information here: [michael.gr - Open Source but No License.](https://blog.michael.gr/2018/04/open-source-but-no-license.html)

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
