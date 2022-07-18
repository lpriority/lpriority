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
<!-- <title>Goal Setting PDF Download</title> -->
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/common/goal_setting_tool.js"></script>
<script src="resources/javascript/teacher_popup.js"></script>
<script src="resources/javascript/Adminjs.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/simptip/simptip.css" />
<script type="text/javascript">
	$(document).ready(function() {
		$('#returnMessage').fadeOut(4000);
		
	});
	
	function goToDiv(i){
		  document.getElementById("sho"+i).scrollIntoView(true);
	}
	
</script>
</head>
<body>
	<div>
		<c:choose>
			<c:when test="${userReg.user.userType == 'admin'}">
			    <script src="resources/javascript/admin/common_dropdown_pull.js"></script>
				<c:set var="getClassFunction" value="getGradeClasses()"/>
			</c:when>
			
		</c:choose>		
	</div>
	<%-- <table border="0" cellspacing="0" cellpadding="0" align="right" class="">
      <tr>
          <td>
            <ul class="button-group">
				<li><a href="#" onclick="togglePage(this, 'goalSettingDownload', 'Goal Setting')" class="btn ${(page == 'goalSettingDownload')?'buttonSelect leftPill':'button leftPill'}"> Goal Setting</a></li>
				<li><a href="#" onclick="togglePage(this, 'goalSettingExcelDownload', 'Goal Setting Excel')" class="btn ${(page =='goalSettingExcelDownload')?'buttonSelect rightPill':'button rightPill'}"> Goal Setting Excel</a></li>
			</ul>
		 </td>	
       </tr>
    </table> --%>
	<form:form name="goalSettingDownload">
	
		 <!-- <table align="left" style="width: 99.8%;" border="0" cellpadding="0" cellspacing="0" class="title-pad">
			<tr>
				<td class="sub-title title-border" height="40" align="left">
					Goal Setting Download
				</td>
			</tr>	
			
	 </table> -->
	 <table>	
			<tr>
				<td colspan="2" align="left" valign="left" width="100%" class="heading-up">
					<table align="left" valign="left" width="100%" style="padding-left: 8em;" border=0 cellPadding=0 cellSpacing=0  class="title-pad heading-up heading-up">
						<tr>
							<td height="20" align="left" class="label" style="width: 12em;">Grade &nbsp;&nbsp;&nbsp;
								<select name="gradeId" class="listmenu" id="gradeId" onChange="getGradeClasses()" required="required">
									<option value="select">select grade</option>
									<c:forEach items="${grades}" var="ul">
										<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
									</c:forEach>
								</select>
							</td>
                            <td width="220px" align="left" valign="middle"  class="label">Class &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                	<select	id="classId" name="classId" class="listmenu" style="width:120px;" onchange="getClassSections()">
									<option value="select">select subject</option>
								</select>
							</td>
							<td width="220px" align="left" valign="middle"  class="label">Teacher &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                	<select style="width:120px;" name="csId" class="listmenu" id="csId">
									<option value="select">Select Teacher</option>
								</select>
							</td>
							<td style="width: 12em;padding-left: 2em;"> <input type="button" class="button_green" value="Get Results" style="border:none"  onclick="getAllStudsGoalReportByReportId(4,1)" />
							</td>				
							<td align="left" valign="middle">
								<input type="hidden" id="usedFor" value="${usedFor}" />
							</td>
						</tr>					
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center" valign="top" id="subViewDiv"></td>
			</tr> 	
		</table>							
	</form:form>
	 <div id="goToGoalSettingTool" title="Goal Setting Tool" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
</body>
</html>