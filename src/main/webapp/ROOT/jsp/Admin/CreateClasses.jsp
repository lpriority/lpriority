<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Classes</title>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/admin/add_edit_remove_sl_schl.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%" >
<tr><td>
<table width="100%"  class="title-pad" style="padding-top: 0.5em">
		<tr>
			<td class="sub-title title-border" height="40" align="left">Create Classes</td>
		</tr>
	</table>
</td></tr>
<tr><td class="heading-up"  style="padding-top:2em;">	
<!-- Content center start -->		  

		<form:form name="adminCreateClasses" modelAttribute="studentclass" onsubmit="return false;"><br>
		<table class="des" border=0 align="center" style="padding-top:2em;width:30%;"><tr><td><div class="Divheads"><table><tr><td class="headsColor">Create Class</td></tr></table></div>
		<div class="DivContents">
			<table style="width: 90%;" align="center" class="space-between">
	                                <tr>
	                                    <td  align="left" valign="top"></td>
	                                    <td align="center" valign="middle" class="tabtxt">Enter New Class Name  </td>
	                                    <td align="center" valign="middle" class="text1">:</td>
	                                    <td align="right" valign="middle" class="tabtxt">
	                                       <input type="text" name="classname" id="classname" />
	                                
	                                    </td></tr>
									<tr>
										<td colspan="4" align="center" class="space-between"><table
										align="center">
	                                        <tr>
	                                        	<td align="left" valign="middle">
	                                            	<div class="button_green" align="right" onclick="CreateClasses()">Submit Changes</div> 
	                                            </td>
	                                            <td  align="left" valign="middle">
	                                           		<div class="button_green" align="right" onclick="document.adminCreateClasses.reset();return false;">Clear</div> 
	                                            </td> 
	                                            <td>
	                                           		<a href="gotoDashboard.htm"><div class="button_green" align="right">Cancel</div></a>
	                                           </td>  
	                                        </tr>
	                                    </table></td>
	                            </tr>
	                            </table></div></td></tr></table>
	                            
	</form:form>
</td></tr>
<tr><td colspan="4" class="space-between" align="center"><label id="result1" class="status-message"></label></td></tr>
</table>	
</body>
</html>