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
<title>${LP_STEM_TAB} Analytics Reports</title>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/admin/student_reports.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
function updateStemArea(thisvar){	
	var areaValue = thisvar.value;
	var stemAnalyticsContainer = $(document.getElementById('stemAnalyticsDiv'));
	$(stemAnalyticsContainer).empty(); 
	if(areaValue != 'select'){
		$("#loading-div-background").show();
		$.ajax({  
			url : "getStemReport.htm", 
	        data: "areaValue=" + areaValue,
	        success : function(data) { 
	        	$(stemAnalyticsContainer).append(data);
	        	$("#loading-div-background").hide();
	        }  
	   	});  
	}
}
</script>
</head>
<body>
<table width="100%">
  <tr>
	<td vAlign=top width="100%" colSpan=3 align=middle>
		<table width="100%" border=0 align="center" cellPadding=0
			cellSpacing=0>
			<tr><td colspan="" width="100%"> 
				 <table width="100%" class="title-pad heading-up" border="0">
					<tr>
						<td class="sub-title title-border" height="40" align="left" >${LP_STEM_TAB} Analytics Reports</td>
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
		                                <td width="220px" valign="middle" class="label" align="left">Report Area &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                                	<select	id="frequencyId" name="frequencyId" class="listmenu" style="width:140px;" onchange="updateStemArea(this)">
												<option value="select">select Area</option>
												<option value="stem">${LP_STEM_TAB} Pair Frequency</option>
												<option value="strands">Strands Frequency</option>
												<option value="strategies">Differentiation Strategies</option>													
											</select>
		                                </td>
		                            </tr>
		                       </table> 
		                    </td>
		                </tr>
		            </table>   
				</div> 	
				<div style="padding-top: 1em;" id="stemAnalyticsDiv"></div>			
		  </td></tr>
	  </table>
	</td>
  </tr>
</table>
<body>
</html>