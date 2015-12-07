package com.jtixchange.service;

import java.util.Arrays;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import com.jtixchange.domain.Account;
// import com.jtixchange.struts.BeanActionException;

public class AccountServiceClient {

	/* Constants */

	private static AccountServiceClient instance = null;
	private final static int MAX_TOTAL_CONNECTIONS = 100;
	private final static int MAX_CONNECTIONS_PER_HOST = 50;
	private final static int CONNECTION_TIMEOUT = 20000;
	
	protected static MultiThreadedHttpConnectionManager httpConnectionManager;
	protected static HttpConnectionManagerParams httpParams = new HttpConnectionManagerParams();
	protected static HttpClient httpClient;
	
	private final EndpointReference targetEPR;
	private final QName opGetAccount = new QName("http://service.jtixchange.com", "getAccount");
	private final QName opGetAuthAccount = new QName("http://service.jtixchange.com", "getAuthAccount");
	private final QName opGetAuthAccountWild = new QName("http://service.jtixchange.com", "getAuthAccountWild");
	private final QName opInsAccount = new QName("http://service.jtixchange.com", "insertAccount");
	private final QName opUpdAccount = new QName("http://service.jtixchange.com", "updateAccount");
	private final QName opListUsernames = new QName("http://service.jtixchange.com", "getUsernameList");

	static {
		httpConnectionManager = new MultiThreadedHttpConnectionManager();
		httpParams.setDefaultMaxConnectionsPerHost(AccountServiceClient.MAX_CONNECTIONS_PER_HOST);
		httpParams.setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, AccountServiceClient.MAX_CONNECTIONS_PER_HOST);
		httpParams.setMaxTotalConnections(AccountServiceClient.MAX_TOTAL_CONNECTIONS);
		httpConnectionManager.setParams(httpParams);
		httpClient = new HttpClient(httpConnectionManager);
	}

	/* CONSTRUCTOR */

	public AccountServiceClient() {
	
		// Get Host, Port & Path for Web Service from java properties
		String wsHost, wsPort, wsPath;
		
		try {
			wsHost = System.getProperty("jtixchange.ws.host");
		} catch (NullPointerException npe) {
			wsHost = "localhost";
		}
		
		try {
			wsPort = System.getProperty("jtixchange.ws.port");
		} catch (NullPointerException npe) {
			wsPort = "8080";
		}
		
		try {
			// FOR NEW TECH USE CASES ONLY
			// wsPath = "jtixchange_services_new_tech";
			wsPath = System.getProperty("jtixchange.ws.path");
		} catch (NullPointerException npe) {
			wsPath = "jtixchange_services";
		}
		
		String targetEPRURL = "http://"+ wsHost + ":" + wsPort + "/" + wsPath + "/services/AccountService";
		targetEPR = new EndpointReference(targetEPRURL);
	}
	
	/* UTILITY METHODS */
	
	public static AccountServiceClient getInstance() {
		if(instance == null) {
			 instance = new AccountServiceClient();
		}
		return instance;
	}
	
	private RPCServiceClient newServiceClient(String thisAction) throws AxisFault {
		
		RPCServiceClient serviceClient = new RPCServiceClient();
		
		final Options options = serviceClient.getOptions();
		options.setTo(targetEPR);
		
		serviceClient.getOptions().setProperty(HTTPConstants.REUSE_HTTP_CLIENT, Constants.VALUE_TRUE);
		serviceClient.getOptions().setProperty(HTTPConstants.CACHED_HTTP_CLIENT, httpClient); 
		serviceClient.getOptions().setProperty(HTTPConstants.AUTO_RELEASE_CONNECTION, Constants.VALUE_TRUE);
		serviceClient.getOptions().setAction(thisAction);
		
		return serviceClient;
	}

	private void cleanupClient(RPCServiceClient serviceClient) throws AxisFault {
		if(serviceClient != null) {
			serviceClient.cleanupTransport();
		}
		httpConnectionManager.closeIdleConnections(CONNECTION_TIMEOUT);
	}
	
	/* ACCOUNT SERVICE METHODS */
	
	public Account getAccount(String username) throws AxisFault {
		RPCServiceClient accountClient = newServiceClient("urn:getAccount");
		final Object[] opGetAccountArgs = new Object[] { username };
		@SuppressWarnings("rawtypes")
		final Class[] returnTypes = new Class[] { Account.class };
		final Object[] response = accountClient.invokeBlocking(opGetAccount, opGetAccountArgs, returnTypes);
		cleanupClient(accountClient);
		return (Account) response[0];
	}

	public Account getAuthAccount(String username, String password) throws AxisFault {
		RPCServiceClient accountClient = newServiceClient("urn:getAuthAccount");
		final Object[] opGetAuthAccountArgs = new Object[] { username, password };
		@SuppressWarnings("rawtypes")
		final Class[] returnTypes = new Class[] { Account.class };
		final Object[] response = accountClient.invokeBlocking(opGetAuthAccount, opGetAuthAccountArgs, returnTypes);
		cleanupClient(accountClient);
		return (Account) response[0];
	}

	public Account getAuthAccountWild(String username, String password) throws AxisFault {
		RPCServiceClient accountClient = newServiceClient("urn:getAuthAccountWild");
		final Object[] opGetAuthAccountArgs = new Object[] { username, password };
		@SuppressWarnings("rawtypes")
		final Class[] returnTypes = new Class[] { Account.class };
		final Object[] response = accountClient.invokeBlocking(opGetAuthAccountWild, opGetAuthAccountArgs, returnTypes);
		cleanupClient(accountClient);
		return (Account) response[0];
	}

	public List<Object> getUsernameList() throws AxisFault {
		RPCServiceClient accountClient = newServiceClient("urn:getUsernameList");
		@SuppressWarnings("rawtypes")
		final Class[] returnTypes = new Class[] { List.class };
		final List<Object> returnUsernameList = Arrays.asList(accountClient.invokeBlocking(opListUsernames, null, returnTypes));
		cleanupClient(accountClient);
		return returnUsernameList;
	}

	public void insertAccount(Account account) throws AxisFault {
		RPCServiceClient accountClient = newServiceClient("urn:insertAccount");
		final Object[] opInsAccountArgs = new Object[] { account };
		accountClient.invokeRobust(opInsAccount, opInsAccountArgs);
		cleanupClient(accountClient);
	}

	public void updateAccount(Account account) throws AxisFault {
		RPCServiceClient accountClient = newServiceClient("urn:updateAccount");
		final Object[] opUpdAccountArgs = new Object[] { account };
		accountClient.invokeRobust(opUpdAccount, opUpdAccountArgs);
		cleanupClient(accountClient);
	}
}
