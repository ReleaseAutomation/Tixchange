package com.jtixchange.presentation;

import org.jinterop.dcom.common.JISystem;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JIClsid;
import org.jinterop.dcom.core.JIComServer;
import org.jinterop.dcom.core.JISession;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIVariant;
import org.jinterop.dcom.impls.JIObjectFactory;
import org.jinterop.dcom.impls.automation.IJIDispatch;
import org.jinterop.dcom.impls.automation.IJIEnumVariant;

import com.jtixchange.struts.BaseBean;

public class DCOMBean extends BaseBean {

	/* Constants */

	private static final long serialVersionUID = 1L;
	private static final String newLine = "\n";
	private static final String[] WMIArgs = {"localhost", "Administrator", "CAdemo123"};
	
	private String wmiOutput = "";
	private JIComServer comStub = null;
	private IJIComObject comObject = null;
	private IJIDispatch dispatch = null;
	private JISession session = null;
	
	/* Public Methods */
	
	public DCOMBean() throws Exception {
		JISystem.setInBuiltLogHandler(true);
		// JISystem.getLogger().setLevel(Level.FINEST);
		JISystem.setAutoRegisteration(true);
	}
	
	public String testDCOM() throws Exception {
		this.performOp("localhost", WMIArgs);
		this.killme();
		return "success";
	}

	public String getWmiOutput() {
		return this.wmiOutput;
	}

	public void setWmiOutput(String wmiOutput) {
		this.wmiOutput = wmiOutput;
	}
	

	private void killme() throws Exception {
		JISession.destroySession(this.session);
	}

	public void performOp (String address, String[] args) throws Exception {
		StringBuilder opOutput = new StringBuilder("");
		
		this.session = JISession.createSession(args[0], args[1], args[2]);

		this.comStub = new JIComServer(JIClsid.valueOf("76a64158-cb41-11d1-8b02-00600806d9b6"), address, this.session);
		final IJIComObject unknown = this.comStub.createInstance();
		this.comObject = unknown.queryInterface("76A6415B-CB41-11d1-8B02-00600806D9B6");

		this.dispatch = ((IJIDispatch)JIObjectFactory.narrowObject(this.comObject.queryInterface("00020400-0000-0000-c000-000000000046")));

		JIVariant[] results = this.dispatch.callMethodA("ConnectServer", new Object[] { JIVariant.OPTIONAL_PARAM(), new JIString("ROOT\\CIMV2"), JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM(),
		JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM(), new Integer(0), JIVariant.OPTIONAL_PARAM() });

		final IJIDispatch wbemServices_dispatch = (IJIDispatch)JIObjectFactory.narrowObject(results[0].getObjectAsComObject());
		results = wbemServices_dispatch.callMethodA("ExecQuery", new Object[] { new JIString("select * from Win32_OperatingSystem where Primary=True"), JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM() });
		final IJIDispatch wbemObjectSet_dispatch = (IJIDispatch)JIObjectFactory.narrowObject(results[0].getObjectAsComObject());
		final JIVariant variant = wbemObjectSet_dispatch.get("_NewEnum");
		final IJIComObject object2 = variant.getObjectAsComObject();

		final IJIEnumVariant enumVARIANT = (IJIEnumVariant)JIObjectFactory.narrowObject(object2.queryInterface("00020404-0000-0000-C000-000000000046"));

		final JIVariant Count = wbemObjectSet_dispatch.get("Count");
		final int count = Count.getObjectAsInt();
		for (int i = 0; i < count; i++)
		{
			final Object[] values = enumVARIANT.next(1);
			final JIArray array = (JIArray)values[0];
			final Object[] arrayObj = (Object[])array.getArrayInstance();
			for (final Object element : arrayObj) {
				final IJIDispatch wbemObject_dispatch = (IJIDispatch)JIObjectFactory.narrowObject(((JIVariant)element).getObjectAsComObject());
				final JIVariant variant2 = wbemObject_dispatch.callMethodA("GetObjectText_", new Object[] { new Integer(1) })[0];
				opOutput.append(variant2.getObjectAsString().getString() + newLine);
				opOutput.append("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + newLine);
			}
		}
		
		setWmiOutput(opOutput.toString());
	}
}