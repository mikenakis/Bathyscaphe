# Bathyscaphe Issue Tracking

### Because issues about code should be kept with the code. 


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
