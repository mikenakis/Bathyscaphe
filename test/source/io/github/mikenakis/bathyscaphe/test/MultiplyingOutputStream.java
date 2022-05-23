/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
 */

package io.github.mikenakis.bathyscaphe.test;

import java.io.IOException;
import java.io.OutputStream;

class MultiplyingOutputStream extends OutputStream
{
	private final OutputStream[] outputStreams;

	public MultiplyingOutputStream( OutputStream... outputStreams )
	{
		this.outputStreams = outputStreams;
	}

	@Override public void write( int b ) throws IOException
	{
		for( var outputStream : outputStreams )
			outputStream.write( b );
	}

	@Override public void close() throws IOException
	{
		for( var outputStream : outputStreams )
			outputStream.close();
	}

	@Override public void flush() throws IOException
	{
		for( var outputStream : outputStreams )
			outputStream.flush();
	}
}
