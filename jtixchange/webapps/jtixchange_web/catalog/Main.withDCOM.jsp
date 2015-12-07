<%@include file="../common/IncludeTop.jsp"%>

<table border="0" cellspacing="2" cellpadding="0" width="100%">
  <tr>
    <td valign="top" width="100%">

      <table align="left" border="0" cellspacing="0" width="80%">
        <tbody>
        <tr>
          <td valign="top">

            <!-- SIDEBAR -->

        <table bgcolor="#5A8C29" border="0" cellspacing="2" cellpadding="0" width="100%"><tr><td>
            <table bgcolor="#FFFF88" border="0" cellspacing="0" cellpadding="5" width="200">
              <tbody>
      <tr>
      <td>
      <logic:present name="accountBean" scope="session">
        <logic:equal name="accountBean" property="authenticated" scope="session" value="true" >
              <b><i><font size="2" color="BLACK">Welcome <bean:write name="accountBean" property="account.firstName" />!</font></i></b>
        </logic:equal>
      </logic:present>
        &nbsp;
      </td>
      </tr>
              <tr>
                <td>
                <html:link page="/shop/viewCategory.shtml?categoryId=CONCERTS">
                <img border="0" src="../images/concerts_icon.gif" /></html:link>
                <br><font size="2"><i>Live Music</i></font>
                </td>
              </tr>
              <tr>
                <td>
                <html:link page="/shop/viewCategory.shtml?categoryId=CONFERENCES">
                <img border="0" src="../images/conferences_icon.gif" /></html:link>
                <br><font size="2"><i>Technical Conferences</i></font>
               </td>
              </tr>
              <tr>
                <td>
                <html:link page="/shop/viewCategory.shtml?categoryId=FESTIVALS">
                <img border="0" src="../images/festivals_icon.gif" /></html:link>
                <br><font size="2"><i>Multi-Day Concerts</i></font>
                </td>
              </tr>
              <tr>
                <td>
                <html:link page="/shop/viewCategory.shtml?categoryId=SPORTS">
                <img border="0" src="../images/sports_icon.gif" /></html:link>
                <br><font size="2"><i>Sporting Events</i></font>
                </td>
              </tr>
              <tr>
                <td>
                <html:link page="/shop/viewCategory.shtml?categoryId=THEATER">
                <img border="0" src="../images/theater_icon.gif" /></html:link>
                <br><font size="2"><i>Broadway, Musicals</i></font>
                </td>
              </tr>
 			  <tr>
                <td align="left">
                <html:link page="/shop/testDCOM.shtml">
           			<font size="4" face="verdana,arial,sans-serif"><i>Test DCOM</i></font>
                </html:link>
                </td>
              </tr>
              </tbody>
             </table>
         </td></tr></table>

           </td>
          <td align="center" bgcolor="white" height="300" width="100%">

          <!-- MAIN IMAGE -->

          <img border="0" height="350" width="350" src="../images/splash.jpg" align="center" />
          </td></tr></tbody></table></td></tr>

        </table>

<%@include file="../common/IncludeBanner.jsp"%>
<%@include file="../common/IncludeBottom.jsp"%>

