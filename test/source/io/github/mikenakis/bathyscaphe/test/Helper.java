/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
 */

package io.github.mikenakis.bathyscaphe.test;

import io.github.mikenakis.bathyscaphe.Bathyscaphe;
import io.github.mikenakis.bathyscaphe.ObjectMustBeImmutableException;
import io.github.mikenakis.bathyscaphe.internal.assessments.ImmutableObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.assessments.ObjectAssessment;
import io.github.mikenakis.bathyscaphe.print.AssessmentPrinter;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Various static helper methods.
 *
 * @author michael.gr
 */
final class Helper
{
	private Helper()
	{
	}

	static ObjectAssessment assess( Object object, PrintStream printStream )
	{
		printStream.print( "assessment for " + AssessmentPrinter.objectName( object ) + ":\n" );
		ObjectAssessment assessment;
		try
		{
			assert Bathyscaphe.objectMustBeImmutableAssertion( object );
			assessment = ImmutableObjectAssessment.instance;
		}
		catch( ObjectMustBeImmutableException exception )
		{
			assessment = exception.mutableObjectAssessment;
		}
		catch( Throwable throwable )
		{
			printStream.print( "    " + throwable.getClass().getName() + " : " + throwable.getMessage() + "\n" );
			throw throwable;
		}
		AssessmentPrinter.getText( assessment ).forEach( s -> printStream.print( "    " + s + "\n" ) );
		return assessment;
	}

	static void createEmptyPrint( Class<?> testClass )
	{
		Path path = getPrintPath( testClass );
		try
		{
			Files.writeString( path, "" );
		}
		catch( IOException e )
		{
			throw new RuntimeException( e );
		}
	}

	private static Path getPrintPath( Class<?> testClass )
	{
		var workingDirectory = MyTestKit.getWorkingDirectory();
		Path printsPath = workingDirectory.resolve( "prints" );
		return printsPath.resolve( testClass.getSimpleName() + ".txt" );
	}

	static PrintStream getPrintStream( Class<?> testClass )
	{
		Path path = getPrintPath( testClass );
		OutputStream outputStream;
		try
		{
			outputStream = Files.newOutputStream( path, StandardOpenOption.APPEND );
		}
		catch( IOException e )
		{
			throw new RuntimeException( e );
		}
		outputStream = new BufferedOutputStream( outputStream, 1024 * 1024 );
		outputStream = new MultiplyingOutputStream( outputStream, new NonClosingOutputStream( System.out ) );
		return new PrintStream( outputStream, true, StandardCharsets.UTF_8 );
	}
}
