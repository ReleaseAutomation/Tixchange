package com.jtixchange.struts.httpmap;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * Map to wrap request scope attributes.
 * <p/>
 * Date: Mar 11, 2004 10:35:34 PM
 * 
 * @author Clinton Begin
 */
public class RequestMap extends BaseHttpMap {

	private final HttpServletRequest request;

	public RequestMap(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	protected Enumeration<?> getNames() {
		return this.request.getAttributeNames();
	}

	@Override
	protected Object getValue(Object key) {
		return this.request.getAttribute(String.valueOf(key));
	}

	@Override
	protected void putValue(Object key, Object value) {
		this.request.setAttribute(String.valueOf(key), value);
	}

	@Override
	protected void removeValue(Object key) {
		this.request.removeAttribute(String.valueOf(key));
	}

}
