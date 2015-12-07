package com.jtixchange.struts.httpmap;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * Map to wrap form parameters.
 * <p/>
 * Date: Mar 11, 2004 10:35:52 PM
 * 
 * @author Clinton Begin
 */
public class ParameterMap extends BaseHttpMap {

	private final HttpServletRequest request;

	public ParameterMap(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	protected Enumeration<?> getNames() {
		return this.request.getParameterNames();
	}

	@Override
	protected Object getValue(Object key) {
		return this.request.getParameter(String.valueOf(key));
	}

	protected Object[] getValues(Object key) {
		return this.request.getParameterValues(String.valueOf(key));
	}

	@Override
	protected void putValue(Object key, Object value) {
		throw new UnsupportedOperationException("Cannot put value to ParameterMap.");
	}

	@Override
	protected void removeValue(Object key) {
		throw new UnsupportedOperationException("Cannot remove value from ParameterMap.");
	}

}
