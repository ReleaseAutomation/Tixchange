package com.jtixchange.struts.httpmap;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Map to wrap cookie names and values (READ ONLY).
 * <p/>
 * Date: Mar 11, 2004 11:31:35 PM
 * 
 * @author Clinton Begin
 */
public class CookieMap extends BaseHttpMap {

	/**
	 * Cookie Enumerator Class
	 */
	private class CookieEnumerator implements Enumeration<Object> {

		private int i = 0;

		private final Cookie[] cookieArray;

		public CookieEnumerator(Cookie[] cookies) {
			this.cookieArray = cookies;
		}

		public synchronized boolean hasMoreElements() {
			return this.cookieArray.length > this.i;
		}

		public synchronized Object nextElement() {
			final Object element = this.cookieArray[this.i];
			this.i++;
			return element;
		}

	}

	private final Cookie[] cookies;

	public CookieMap(HttpServletRequest request) {
		this.cookies = request.getCookies();
	}

	@Override
	protected Enumeration<?> getNames() {
		return new CookieEnumerator(this.cookies);
	}

	@Override
	protected Object getValue(Object key) {
		for (final Cookie cookie : this.cookies) {
			if (key.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}

	@Override
	protected void putValue(Object key, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void removeValue(Object key) {
		throw new UnsupportedOperationException();
	}

}
