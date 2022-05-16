package io.github.mikenakis.bathyscaphe.internal.helpers;

import java.lang.reflect.Modifier;

public class Helpers
{
	public static boolean isClass( Class<?> type )
	{
		if( type.isInterface() )
			return false;
		if( type.isArray() )
			return false;
		if( type.isAnnotation() )
			return false;
		return true;
	}

	public static boolean isExtensible( Class<?> type )
	{
		return !Modifier.isFinal( type.getModifiers() );
	}
}
