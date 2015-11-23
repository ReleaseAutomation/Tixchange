package com.jtixchange.struts.httpmap;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Map to wrap session scope attributes.
 * <p/>
 * Date: Mar 11, 2004 10:35:42 PM
 * 
 * @author Clinton Begin
 */
public class SessionMap extends BaseHttpMap {

	private final HttpSession session;

	public SessionMap(HttpServletRequest request) {
		this.session = request.getSession();
	}

	@Override
	protected Enumeration<?> getNames() {
		return this.session.getAttributeNames();
	}

	@Override
	protected Object getValue(Object key) {
		return this.session.getAttribute(String.valueOf(key));
	}

	@Override
	protected void putValue(Object key, Object value) {
		this.session.setAttribute(String.valueOf(key), value);
	}

	@Override
	protected void removeValue(Object key) {
		this.session.removeAttribute(String.valueOf(key));
	}

}
