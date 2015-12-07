package com.jtixchange.struts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jtixchange.struts.httpmap.ApplicationMap;
import com.jtixchange.struts.httpmap.CookieMap;
import com.jtixchange.struts.httpmap.ParameterMap;
import com.jtixchange.struts.httpmap.RequestMap;
import com.jtixchange.struts.httpmap.SessionMap;

/**
 * The ActionContext class gives simplified, thread-safe access to
 * the request and response, as well as form parameters, request
 * attributes, session attributes, application attributes.  Much
 * of this can be accopmplished without using the Struts or even
 * the Servlet API, therefore isolating your application from
 * presentation framework details.
 * <p/>
 * This class also provides facilities for simpler message and error
 * message handling.  Although not as powerful as that provided by
 * Struts, it is great for simple applications that don't require
 * internationalization or the flexibility of resource bundles.
 * <p/>
 * <i>Note: A more complete error and message handling API will be implemented.</i>
 * <p/>
 * Date: Mar 9, 2004 9:57:39 PM
 * 
 * @author Clinton Begin
 */
public class ActionContext {

	private static final ThreadLocal<ActionContext> localContext = new ThreadLocal<ActionContext>();

	public static ActionContext getActionContext() {
		ActionContext ctx = ActionContext.localContext.get();
		if (ctx == null) {
			ctx = new ActionContext();
			ActionContext.localContext.set(ctx);
		}
		return ctx;
	}
	protected static void initialize(HttpServletRequest request, HttpServletResponse response) {
		final ActionContext ctx = ActionContext.getActionContext();
		ctx.request = request;
		ctx.response = response;
		ctx.cookieMap = null;
		ctx.parameterMap = null;
		ctx.requestMap = null;
		ctx.sessionMap = null;
		ctx.applicationMap = null;
	}

	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map<?, ?> cookieMap;
	private Map<?, ?> parameterMap;
	private Map<Object, Object> requestMap;

	private Map<?, ?> sessionMap;

	private Map<?, ?> applicationMap;

	private ActionContext() {
	}

	public void addSimpleError(String message) {
		@SuppressWarnings("unchecked")
		List<String> errors = (List<String>) this.getRequestMap().get("errors");
		if (errors == null) {
			errors = new ArrayList<String>();
			this.getRequestMap().put("errors", errors);
		}
		errors.add(message);
	}

	public Map<?, ?> getApplicationMap() {
		if (this.applicationMap == null) {
			this.applicationMap = new ApplicationMap(this.request);
		}
		return this.applicationMap;
	}

	public Map<?, ?> getCookieMap() {
		if (this.cookieMap == null) {
			this.cookieMap = new CookieMap(this.request);
		}
		return this.cookieMap;
	}

	public Map<?, ?> getParameterMap() {
		if (this.parameterMap == null) {
			this.parameterMap = new ParameterMap(this.request);
		}
		return this.parameterMap;
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public Map<Object, Object> getRequestMap() {
		if (this.requestMap == null) {
			this.requestMap = new RequestMap(this.request);
		}
		return this.requestMap;
	}

	public HttpServletResponse getResponse() {
		return this.response;
	}

	public Map<?, ?> getSessionMap() {
		if (this.sessionMap == null) {
			this.sessionMap = new SessionMap(this.request);
		}
		return this.sessionMap;
	}

	public boolean isSimpleErrorsExist () {
		final List<?> errors = (List<?>) this.getRequestMap().get("errors");
		return (errors != null) && (errors.size() > 0);
	}

	public void setSimpleMessage(String message) {
		this.getRequestMap().put("message", message);
	}
}
