/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
 */

package io.github.mikenakis.bathyscaphe.test;

import io.github.mikenakis.bathyscaphe.internal.ObjectAssessor;
import io.github.mikenakis.bathyscaphe.internal.assessments.ObjectAssessment;
import io.github.mikenakis.bathyscaphe.internal.diagnostic.AssessmentPrinter;
import io.github.mikenakis.debug.Debug;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

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

	static ObjectAssessment assess( Method method, Object object )
	{
		try( PrintStream printStream = getPrintStream( method ) )
		{
			return assess( object, printStream );
		}
	}

	private static ObjectAssessment assess( Object object, PrintStream printStream )
	{
		printStream.print( "assessment for " + AssessmentPrinter.objectName( object ) + ":\n" );
		ObjectAssessment assessment;
		try
		{
			assessment = Debug.boundary( () -> ObjectAssessor.instance.assess( object ) );
		}
		catch( Throwable throwable )
		{
			printStream.print( "    " + throwable.getClass().getName() + " : " + throwable.getMessage() + "\n" );
			throw throwable;
		}
		AssessmentPrinter.getText( assessment ).forEach( s -> printStream.print( "    " + s + "\n" ) );
		return assessment;
	}

	private static PrintStream getPrintStream( Path path )
	{
		OutputStream outputStream;
		try
		{
			outputStream = Files.newOutputStream( path );
		}
		catch( IOException e )
		{
			throw new RuntimeException( e );
		}
		outputStream = new BufferedOutputStream( outputStream, 8192 );
		outputStream = new MultiplyingOutputStream( outputStream, new NonClosingOutputStream( System.out ) );
		return new PrintStream( outputStream, true, StandardCharsets.UTF_8 );
	}

	private static PrintStream getPrintStream( Method method )
	{
		Path printsPath = MyTestKit.getWorkingDirectory().resolve( "prints" );
		assert printsPath.toFile().isDirectory();
		Path pathForTestClass = printsPath.resolve( method.getDeclaringClass().getSimpleName() );
		//noinspection ResultOfMethodCallIgnored
		pathForTestClass.toFile().mkdir();
		Path pathForTestMethod = pathForTestClass.resolve( method.getName() + ".print" );
		return getPrintStream( pathForTestMethod );
	}

	static Method getCurrentMethod()
	{
		return getCurrentMethod( 1 );
	}

	static Method getCurrentMethod( int numberOfFramesToSkip )
	{
		//also see MethodHandles.lookup().lookupClass().getEnclosingMethod()
		StackWalker.StackFrame x = StackWalker.getInstance( StackWalker.Option.RETAIN_CLASS_REFERENCE ).walk( s -> s.skip( 1 + numberOfFramesToSkip ).findFirst() ).orElseThrow();
		Class<?> jvmClass = x.getDeclaringClass();
		try
		{
			return jvmClass.getDeclaredMethod( x.getMethodName(), x.getMethodType().parameterArray() );
		}
		catch( NoSuchMethodException e )
		{
			throw new RuntimeException( e );
		}
	}
}
