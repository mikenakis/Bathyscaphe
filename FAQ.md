# Frequently Asked Questions (F.A.Q., FAQ)

- #### How large are the Bathyscaphe JARs?
	- The `bathyscaphe-claims` module is microscopic, since it contains no code, only a few definitions.
	- The `bathyscaphe` module qualifies as very small, as its JAR file is of the order of 100 kilobytes.

- #### What is the performance overhead of using Bathyscaphe?
	- Bathyscaphe is _faster than lightning_: the performance overhead of using Bathyscaphe is **_zero_**.
		- That is because performance is only relevant on production environments; bathyscaphe is meant to be used via assertions, which are meant to be disabled on production, therefore Bathyscaphe is not meant to actually do any work on production.
		- On development environments, the speed of Bathyscaphe will depend on what you are assessing:
			- In the best case, when assessing an instance of a class which can be conclusively assessed, Bathyscaphe will do a synchronized map lookup before it determines that the instance is immutable. (How long it will take to determine that the instance is mutable is irrelevant, because if that happens, you are terminating anyway.)
			- In the worst case, when assessing an instance of a class which has been assessed as provisory, Bathyscaphe will use reflection to traverse the entire object graph reachable via provisory fields, while keeping a lock on a synchronized map. The map lock could of course be optimized, but there is no need, because performance is largely irrelevant on development.

- #### Why are the tests in a separate module?
	- Because I have the habit of always placing the tests in a separate module. That's what I do. It's my thing. One day I will write an article explaining why I do this.
	- If you would like to work with Bathyscaphe, do not obtain the sources from maven, because this will give you the sources of each module separately; instead, clone the bathyscaphe repository from GitHub. This is a _monorepo_ which contains all modules in one directory structure, with a parent pom.xml at the root. All you need to do then is point your IDE to the parent pom, and you will have all modules in your IDE.

- #### Why `assert objectMustBeImmutableAssertion( o )` instead of simply `assert !isMutable( o )`?
	- Because `isMutable()` would imply that the method returns either `true` or `false`, while this method works very differently: it never returns `false`; it either returns `true`, or throws an exception.

- #### Why does `objectMustBeImmutableAssertion()` throw an exception instead of returning `false` ?
	- Because the method must produce something more substantial than a boolean, so that you can obtain diagnostics from it. An exception is something substantial, from which you can obtain diagnostics.
	- The alternative would be to have the method somehow produce diagnostic text right before returning `false`, which would then raise other questions, like where to emit that text to. Needless to say, I would have found such behavior mighty annoying.

- #### Why throw an exception containing an assessment instead of returning the assessment?
	- Because if I was to return the assessment then I would have to make the entire assessment hierarchy public, (i.e. move it out of the `internal` package,) and that would severely impede the evolution of Bathyscaphe, since any change to the assessments would break existing code that is making use of Bathyscaphe. The assessment hierarchy might be moved out of the `internal` package a few years down the road, once Bathyscaphe reaches a certain level of maturity.

- #### Why is the method called `objectMustBeImmutableAssertion()` instead of simply `objectMustBeImmutable()`?
	- The suffix `Assertion` indicates that this is an **_assertion method_**. (See glossary.)

- #### Why is the method called `objectMustBeImmutableAssertion()` instead of simply `mustBeImmutableAssertion()`?
	- Because this is an _assertion method_, (see glossary,) whose name must match the name of the exception that it throws, and the exception name could not begin with `MustBe`, it has to begin with `ObjectMustBe`, so the method is named accordingly.

- #### Why is the exception called `ObjectMustBeImmutableException` instead of simply `ObjectIsMutableException`?
	- Because this exception is thrown by the `objectMustBeImmutableAssertion()` method, which is an **_assertion method_**, (see glossary,) and therefore the name of the exception must match the name of the assertion method.

- #### Is it possible to use Bathyscaphe without assertions?
	- Of course, it is possible. You know what else is possible? using bubble-sort instead of quick-sort. The question is not whether it is possible, the question is whether it is intelligent.
