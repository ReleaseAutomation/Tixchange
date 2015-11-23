<%@include file="../common/IncludeTop.jsp"%>

<bean:define id="wmiOutput" name="DCOMBean" property="wmiOutput"/>

<table align="left" bgcolor="#008800" border="0" cellspacing="2" cellpadding="2">
<tr><td bgcolor="#FFFF88">
<html:link page="/shop/index.shtml"><b><font color="BLACK" size="2">&lt;&lt; Main Menu</font></b></html:link>
</td></tr>
</table>

<div align="center">
	<h2>DCOM (WMI) Test</h2>
</div>
<p>
	<bean:write name="wmiOutput"/><br>
	<%@include file="../common/IncludeBottom.jsp"%>
</p></p>