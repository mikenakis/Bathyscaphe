# Contributing to Bathyscaphe

If you would like to contribute to Bathyscaphe, you are more than welcome to do so, but keep in mind that I will need to ask you to either assign the copyright of your contribution to me, or grant me a permissive license on your contribution. Things would otherwise become terribly complicated due to the dual-license scheme of Bathyscaphe. This means that I am going to have to ask you to agree to a **_Contributor License Agreement_** (CLA) which will probably be something like the [MongoDB Contributor Agreement](https://www.mongodb.com/legal/contributor-agreement), but I have yet to draft such a document.

- #### Merge requests
    - Before you start working on something, please come to our [discussions area](https://github.com/mikenakis/Bathyscaphe/discussions) and let us know what you are thinking of doing, so that we can discuss whether it needs to be done, how it should be done, whether someone else is already doing it, etc. 
    - If you do work on something, please be sure to read the author's post [on coding style](https://blog.michael.gr/2018/04/on-coding-style.html).
- #### Legal advice
	- Advice on legal issues would be greatly appreciated, since all this licensing business is terribly complicated to me.
- #### Open-sourcing advice
	- Starting an actual open-source project like Bathyscaphe is uncharted territory to me, so if you are an experienced open-source contributor, your advice and mentorship would be greatly appreciated.
- #### Technical advice
	- If you have given the subject of immutability some thought, then chances are you can discuss Bathyscaphe with me at a technical level. Perhaps you have some suggestion to make, or point out a mistake in my approach. I would be more than happy to discuss via e-mail or video.
- #### Artwork
	- Are your inkscape skills better than mine? Can you improve my SVG drawing of Bathyscaphe Trieste or come up with something different and better? Be my guest!
- #### Configuration
	- There is still a lot of configuration/administrative work do be done on Bathyscaphe, but I am a software engineer, not an operations engineer, (and don't even get me started on the "devops" hoax!) so help in that area would be appreciated. For example:
		- Automatically generating a GitHub release from a GitHub tag.
			- Currently, nothing new appears in mikenakis/Bathyscaphe/releases when I execute the Release-Workflow; instead, a new entry appears in mikenakis/Bathyscaphe/releases/tag. I can manually create a release from a tag, but I would rather not. It is unclear to me how to automate this.
		- Including binaries in a GitHub release.
			- Currently, a release on GitHub (which I can only manually create at the moment) only contains source code in zip and tar.gz format. I would think that in addition to being automatically generated, a release should also contain the jar files, no?
		- Publishing to Maven Central
			- I have already reserved `io.github.mikenakis` on Maven Central, and now I need to deploy there; however, they have a comprehensive set of requirements which includes things that I have never done before, for example, signing code with GPG. I am slowly learning how to do each step, but someone who has done it before could greatly help in this area.
	- The TODO list contains more things that need to be done.
- #### Sponsorship
	- If you would like to fund me to continue developing Bathyscaphe, or if you would like to see a DotNet version of Bathyscaphe sooner rather than later, you can bestow me with large sums of money; that always helps.
