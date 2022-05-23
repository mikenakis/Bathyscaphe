/*
 * Copyright (c) 2022 Michael Belivanakis a.k.a. MikeNakis, michael.gr
 *
 * Licensed under a dual-license scheme; see LICENSE.md for details.
 * You may not use this file except in compliance with one of the licenses.
 */

package io.github.mikenakis.bathyscaphe.test;

import java.io.IOException;
import java.io.OutputStream;

class NonClosingOutputStream extends OutputStream
{
	private final OutputStream outputStream;

	public NonClosingOutputStream( OutputStream outputStream )
	{
		this.outputStream = outputStream;
	}

	@Override public void write( int b ) throws IOException
	{
		outputStream.write( b );
	}

	@Override public void close()
	{
		//do not close.
	}

	@Override public void flush() throws IOException
	{
		outputStream.flush();
	}
}
