package com.jtixchange.struts.httpmap;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Map to wrap application scope attributes.
 * <p/>
 * Date: Mar 11, 2004 11:21:25 PM
 * 
 * @author Clinton Begin
 */
public class ApplicationMap extends BaseHttpMap {

	private final ServletContext context;

	public ApplicationMap(HttpServletRequest request) {
		this.context = request.getSession().getServletContext();
	}

	@Override
	protected Enumeration<?> getNames() {
		return this.context.getAttributeNames();
	}

	@Override
	protected Object getValue(Object key) {
		return this.context.getAttribute(String.valueOf(key));
	}

	@Override
	protected void putValue(Object key, Object value) {
		this.context.setAttribute(String.valueOf(key), value);
	}

	@Override
	protected void removeValue(Object key) {
		this.context.removeAttribute(String.valueOf(key));
	}

}
