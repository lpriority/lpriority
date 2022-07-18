<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/regService.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/Adminjs.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />

</head>
<body>
	 <table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
	     <tr><td  class="lp-menu-tab">
				<%@include file="/jsp/Admin/school_info_tabs.jsp"%>
         </td></tr>
	</table>
<table width="100%">
   <tr><td align="center" colspan="" width="100%"> 
	<table width="100%">
        <tr><td align="center" colspan="" width="100%"> 
           	 <table width="100%" class="title-pad" border="0">
           		 <tr>
                     <td class="sub-title title-border" height="40" align="left" >School Information</td>
                	 </tr>
		     </table>
	   </td></tr></table>
   </td></tr>
   <tr><td align="center" colspan="" width="100%" style="padding-top:1.5em;"> 
   	 <div id="contentDiv">
	   <%@include file="add_school_info.jsp"%>	
	</div>
	</td></tr>
	<tr><td class="status-message" height=50 colSpan=80 align="center" id="returnMessage"></td></tr>
	</table>
</body>
</html>