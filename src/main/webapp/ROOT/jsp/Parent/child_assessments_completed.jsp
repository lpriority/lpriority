<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Parent Files</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript"
	src="/dwr/interface/parentService.js"></script>
<script type="text/javascript"
	src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript"
	src="/dwr/interface/teacherSchedulerService.js"></script>
<script type="text/javascript"
	src="/dwr/interface/curriculumService.js"></script>
<script type="text/javascript" src="resources/javascript/ParentJs.js"></script>
<script type="text/javascript">
function clearDiv(){
	$("#csId").empty();
	$("#csId").append(
			$("<option></option>").val('').html('Select Subject'));
	$("#StuCompleteAssessment").empty();
	$("#StuAssessmentQuestionsList").empty();
}
</script>
</head>
<body>
	<table style="width: 100%;">
		<tr>
			<td style="" align="right">
				<input type="hidden" id="usedFor" name="usedFor" value="${usedFor}" />
				<input type="hidden" id="tab" name="tab" value="${tab}" /></td>
			<td style="" align="right" valign="bottom">
				<div>
					<%@ include file="/jsp/Student/view_assessment_tabs.jsp" %>
				</div>
			</td>
		</tr>
		
	</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="title-pad">
	 <tr><td width="300" class="sub-title title-border" height="40" align="left">Assessments Completed</td></tr>
	 </table>
       <table width="100%" border="0" cellspacing="0" cellpadding="0" class="title-pad">
		
					<tr>
						<td style="width: 100%"><table style="width: 100%">
								<tr>

									<td style="" align="left"></td>
								</tr>

								<tr>
									<td>&nbsp;</td>
								</tr>

		<tr>
			<td>
				<table width="100%" height="30" border="0" cellpadding="0"
					cellspacing="0">
								<tr>
								
									<td width="112" align="left" valign="middle">
									<label style="font-size:3;" class="label">&nbsp;Child&nbsp;&nbsp;&nbsp;</label><select
										name="studentId" id="studentId" class="listmenu" onChange="clearDiv();loadChildClasess()" required="required">
											<option value="">Select Child</option>
											<c:forEach items="${students}" var="st">
												<option value="${st.studentId}">${st.userRegistration.title}
													${st.userRegistration.firstName}
													${st.userRegistration.lastName}</option>
											</c:forEach>
									</select></td>
									<td width="200" align="left" valign="middle">
									<label style="font-size:3;" class="label">Class &nbsp;&nbsp;</label>
									<select id="csId" name="csId" class="listmenu" onchange="getChildsTestResults()"
										style="width: 120px;">
											<option value="">Select Subject</option>
									</select></td>
									<td width="400" align="left" valign="middle">&nbsp;</td>
									<td width="200" align="left" valign="middle">&nbsp;</td>
								</tr>
							</table><input type='hidden' id='usedFor' value='${usedFor}' /></td>
					</tr>
					<tr>
						<td height="30" colspan="2" align="left"><table width="100%"
								height="30" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="50" align="left" valign="middle" class="perinfohead">&nbsp;&nbsp;&nbsp;</td>
									<td width="350" align="left" valign="middle" class="tabtxt">
										<%
											
											if (request.getParameter("status2") != null) {
												out.println("<font color=blue>Assignment successfully submitted</font>");
											}
										%>
									</td>
									<td width="50" align="left" valign="middle" class="perinfohead">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

									<td width="403" align="left" valign="middle"></td>
									<td width="400" align="left" valign="middle">
										
									</td>
								</tr>
							</table></td>
					</tr>
					<tr>
						<td height="2" colspan="2"></td>
					</tr>
					
						<tr>
						   <td height="0" colspan="2" align="center" valign="top">
								<table width="80%" height="43" border="0" cellpadding="0"
									cellspacing="0" id="StuCompleteAssessment" align=center>

								</table>
								
							</td>
						</tr>
					
					<tr>
						<td height="2" colspan="2"></td>
					</tr>
					<tr>
						<td height="30" colspan="2"></td>
					</tr>
					<tr>
						<td height="30" colspan="2"></td>
					</tr>
				</table></td>
		</tr>
	</table>
	<table width="100%" height="25"><div id="getCompletedTestQuestions" title="Test Results" style="align:center;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>

								</table></form>
</body>
</html>