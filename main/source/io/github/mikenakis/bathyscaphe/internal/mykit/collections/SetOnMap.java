package io.github.mikenakis.bathyscaphe.internal.mykit.collections;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;

public class SetOnMap<T> extends AbstractSet<T>
{
	private final Map<T,T> map;

	public SetOnMap( Map<T,T> map )
	{
		this.map = map;
	}

	@Override public boolean contains( Object element )
	{
		//noinspection SuspiciousMethodCalls
		return map.containsKey( element );
	}

	@Deprecated @Override public boolean add( T element )
	{
		return map.put( element, element ) == null;
	}

	@Deprecated @Override public boolean remove( Object element )
	{
		return map.remove( element ) != null;
	}

	@Override public Iterator<T> iterator()
	{
		return map.keySet().iterator();
	}

	@Override public int size()
	{
		return map.size();
	}
}
