<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Literacy Development Results</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<!-- <link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">-->
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/common/goal_setting_tool.js"></script>
<script src="resources/javascript/teacher_popup.js"></script>
<script src="resources/javascript/Adminjs.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/simptip/simptip.css" />
<script type="text/javascript" src="resources/javascript/ParentJs.js"></script> 

</head>
<body>
<input type="hidden" id="tab" name ="tab" value="${tab}" />
<%-- <table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
       <tr><td height=20>&nbsp;</td></tr>
       <tr>
	       <td>
                
           </td>
           <td vAlign=middle align=right colspan="20">
                    <div> 
                    	<%@ include file="/jsp/CommonJsp/view_progress_report_tabs.jsp" %>
                    </div>
           </td>
       </tr>
    </table>
   <table width="100%" border="0" cellspacing="0" cellpadding="0" class="title-pad">
    
    <tr><td class="sub-title title-border" height="40" align="left">Goal Reports </td></tr>  
    </table> --%>
   <table width="100%" border="0" cellspacing="0" cellpadding="0" class="title-pad">
	   <tr>
			<td>
			 <table width="100%" height="100%" border=0 align="left" cellPadding=0 cellSpacing=0>
			 <tr><td></td></tr> 
			 <tr><td height="20" colspan="2"></td></tr>     
			  <tr><td align="left"  width="128px" class="label"> 
			   Child&nbsp;&nbsp;
			   	<select name="studentId" class="listmenu" id="studentId" onChange="getChildGoalReportsMainPage(1)" required="required" style="width: 120px;">
					<option value="0">Select Child</option>
					<c:forEach items="${students}" var="st">
						<option value="${st.studentId}">${st.userRegistration.title} ${st.userRegistration.firstName} ${st.userRegistration.lastName}</option>
					</c:forEach>
				</select>
			</td> 					
			<td align="left"  width="128px" class="label"> 
			</td>
			
			<td align="left"  width="100px" class="label">
			</td>
			<td width='300px'>&nbsp;</td>
			<td width='120px'>&nbsp;</td></tr>
			  </table>		
	   </tr>
 
	   <tr>
			<td height="30" colspan="2">&nbsp;<br><br></td>
	   </tr>
	</table>
	<div id="goToGoalReports" title="Goal Setting Tool" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>  
	<div id="loading-div-background1" class="loading-div-background"
	style="display: none;">
	<div class="loader"></div>
	<div id="loading-div" class="ui-corner-all loading-div"
		style="color: #103c51; left: 50%; padding-left: 12px; font-size: 17px; margin-top: -8.5em;">
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>Loading....
	</div>
</div> 
	
</body>
</html>