<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Goal Setting Analytics Reports</title>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/admin/student_reports.js"></script>
<script type="text/javascript">
function getGoalSettingsReport(thisvar){	
	var caasppType = thisvar.value;
	var goalSettingsContainer = $(document.getElementById('goalSettingsAnalyticsDiv'));
	$(goalSettingsContainer).empty();
	if(caasppType != 'select'){
		$("#loading-div-background").show();
		$.ajax({  
			url : "getGoalSettingsReport.htm", 
	        data: "caasppType=" + caasppType,
	        success : function(data) { 
	        	$(goalSettingsContainer).append(data);
	        	$("#loading-div-background").hide();
	        }  
	   	});  
	}
}
</script>
</head>
<body>
${studentsLt}
<table width="100%">
  <tr>
	<td vAlign=top width="100%" colSpan=3 align=middle>
		<table width="100%" border=0 align="center" cellPadding=0
			cellSpacing=0>
			<tr><td colspan="" width="100%"> 
				 <table width="100%" class="title-pad heading-up" border="0">
					<tr>
						<td class="sub-title title-border" height="40" align="left" >Goal Settings Analytics Reports</td>
					</tr>
				</table>
			</td></tr>
			<tr><td height=0 vAlign=top colSpan=2 align=left>
				   <div class="title-pad" style="padding-top: 1em;">
					 <table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
		                <tr>
		                    <td align="left" width="100%" valign="middle">
		                    	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		                            <tr>
		                                <td width="220px" valign="middle" class="label" align="left">CAASPP Type &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                                	<select	id="caasppType" name="caasppType" class="listmenu" style="width:140px;" onchange="getGoalSettingsReport(this)">
												<option value="select">Select caaspp type</option>
												<option value="Star Reading">Star Reading</option>
												<option value="Star Math">Star Math</option>										
											</select>
		                                </td>
		                            </tr>
		                       </table> 
		                    </td>
		                </tr>
		            </table>   
				</div> 	
				<div style="padding-top: 1em;" id="goalSettingsAnalyticsDiv"></div>			
		  </td></tr>
	  </table>
	</td>
  </tr>
</table>
<body>
</html>