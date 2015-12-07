package com.jtixchange.struts.httpmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p/>
 * Date: Mar 11, 2004 10:39:51 PM
 * 
 * @author Clinton Begin
 */
public abstract class BaseHttpMap implements Map<Object, Object> {

	public void clear() {
		final Iterator<?> i = this.keySet().iterator();
		while (i.hasNext()) {
			this.removeValue(i.next());
		}
	}

	public boolean containsKey(Object key) {
		return this.keySet().contains(key);
	}

	public boolean containsValue(Object value) {
		return this.values().contains(value);
	}

	public Set<java.util.Map.Entry<Object, Object>> entrySet() {
		final Set<java.util.Map.Entry<Object, Object>> newSet = null;
		return newSet;
	}

	public Object get(Object key) {
		return this.getValue(key);
	}

	protected abstract Enumeration<?> getNames();

	protected abstract Object getValue(Object key);

	public boolean isEmpty() {
		return this.keySet().size() == 0;
	}

	public Set<Object> keySet() {
		final Set<Object> keySet = new HashSet<Object>();
		final Enumeration<?> enumer = this.getNames();
		while (enumer.hasMoreElements()) {
			keySet.add(enumer.nextElement());
		}
		return keySet;
	}

	public Object put(Object key, Object value) {
		final Object old = this.getValue(key);
		this.putValue(key, value);
		return old;
	}

	public void putAll(Map<?, ?> map) {
		final Iterator<?> i = map.keySet().iterator();
		while (i.hasNext()) {
			final Object key = i.next();
			this.putValue(key, map.get(key));
		}
	}

	protected abstract void putValue(Object key, Object value);


	public Object remove(Object key) {
		final Object old = this.getValue(key);
		this.removeValue(key);
		return old;
	}

	protected abstract void removeValue(Object key);

	public int size() {
		return this.keySet().size();
	}

	public Collection<Object> values() {
		final List<Object> list = new ArrayList<Object>();
		final Enumeration<?> enumer = this.getNames();
		while (enumer.hasMoreElements()) {
			list.add(this.getValue(enumer.nextElement()));
		}
		return list;
	}

}
