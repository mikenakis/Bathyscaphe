/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
 */

package io.github.mikenakis.bathyscaphe.internal.mykit.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can be used to exclude certain methods from coverage report.
 * <p>
 * Unfortunately, the coverage runner built into IntellijIdea does not support this annotation.  Feature requests asking for this date since 2014, and I have
 * even submitted one here: <a href="https://youtrack.jetbrains.com/issue/IDEA-292401">https://youtrack.jetbrains.com/issue/IDEA-292401</a>.
 * <p>
 * Luckily, IntellijIdea also comes with the Jacoco coverage runner, which does support such an annotation.
 * <p>
 * Unfortunately, the Jacoco coverage runner operates under the stupendously narrow-minded assumption that the methods that I should want excluded from coverage
 * must be methods that have somehow been generated, and somehow been marked with an annotation that contains the word "Generated" in its name.  (This is an
 * instance of an X-Y solution: there is a problem X, a completely arbitrary and unfounded assumption is made that X must always be due to Y, so a solution is
 * provided for Y.)
 * <p>
 * So, someone here: <a href="https://stackoverflow.com/a/66918619/773113">Stackoverflow: annotation to exclude a method from jacoco report?</a> came up with
 * the idea of creating an annotation called "ExcludeFromJacocoGeneratedReport" which contains the word "Generated" in its name, thus causing Jacoco to honor
 * it, while at the same time conveying the intention of the programmer: "I want this method excluded from code coverage, and it is none of the stupid tool's
 * business to know why I want it excluded".
 *
 * @author michael.gr
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR } )
public @interface ExcludeFromJacocoGeneratedReport
{ }
