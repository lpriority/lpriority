
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script src="resources/javascript/my_recorder/recorder.js"></script>
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css">
<%@ include file="../CommonJsp/include_resources.jsp" %>
<script type="text/javascript" src="resources/javascript/Student/reading_register_student.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script>
$(document).ready(function(){
	 var usersFilePath = $('#usersFilePath').val();
	 var regId = $('#studentRegId').val();
	 checkRRFileExists(usersFilePath, "retellAudio");	
});
</script>
</head>
<body>
<input type="hidden" name="userType" id="userType" value="${activityScore.student.userRegistration.user.userType}" />
<input type="hidden" name="studentRegId" id="studentRegId" value="${activityScore.student.userRegistration.regId}" />
<input type="hidden" name="usersFilePath" id="usersFilePath" value="${usersFilePath}" />
<input type="hidden" id="pageRange" value="${activityScore.readRegMaster.readRegPageRange.range}" />
<input type="hidden" id="activityValue" value="${activityScore.readRegActivity.activityValue}" />
<input type="hidden" id="readRegActivityScoreId" value="${activityScore.readRegActivityScoreId }"/>


<table width="100%" id="tableDiv" style="padding-top:2em;">
<tr><td align="center"><div id="contentDiv" ><audio id="retellAudio" src="" controls="" preload="metadata" controls controlsList="nodownload"><source src="" type="audio/wav"></audio></div></td></tr> 
<tr><td height="30"></td></tr>
<tr>
	<td align="center">
		<table>
				 <c:if test="${showRubric == 'yes'}"> 
				<tr>
					<td width="35%" height="20" align="right" valign="middle"><span
						class="tabtxt">Student's Rubric Score</span> &nbsp;</td>
					<td width="10%" height="20" align="center" valign="middle">:</td>
					<td height="20" align="left" valign="middle"><span class="tabtxt" title="${activityScore.selfScore.description}"> 								
						${activityScore.selfScore.score}									
					</span></td>
				</tr>
				 </c:if>
				<tr>
					<td width="35%" height="20" align="right" valign="middle"><span
						class="tabtxt">Teacher Rubric Score</span> &nbsp;</td>
					<td width="10%" height="20" align="center" valign="middle">:</td>
					<td height="20" align="left" valign="middle"><span class="tabtxt" title="${activityScore.readRegRubric.description}"> 								
						${activityScore.readRegRubric.score}									
					</span></td>
				</tr>
				<tr>
					<td width="35%" height="20" align="right" valign="middle"><span
						class="tabtxt">Points Earned</span> &nbsp;</td>
					<td width="10%" height="20" align="center" valign="middle">:</td>
					<td height="20" align="left" valign="middle"><span class="tabtxt" > 								
						${activityScore.pointsEarned} (pages of the book = ${activityScore.readRegMaster.readRegPageRange.range} x Activity Value = ${activityScore.readRegActivity.activityValue } x Rubric value = ${activityScore.readRegRubric.score} )	 								
					</span></td>
				</tr>
				<c:if test="${not empty activityScore.teacherComment}">
				<tr>
							<td width="35%" height="20" align="right" valign="middle"><span
								class="tabtxt">Teacher Comment</span> &nbsp;</td>
							<td width="10%" height="20" align="center" valign="middle">:</td>
							<td height="20" align="left" valign="middle"><span class="tabtxt" > 								
								${activityScore.teacherComment}									
							</span></td>
						</tr>
				</c:if>
				<c:if test= "${userReg.regId != activityScore.student.userRegistration.regId}">
				<tr>
							<td width="100%" height="23" align="center" colspan="3" ><br>
								<input type="button" class="button_green" value="Return" style="border:none" onclick="returnGradedActivity(${activityScore.readRegActivityScoreId})">										
							</td>
						</tr>
				</c:if>		
				
		</table>
	</td>
</tr>
<tr><td align="center" class="blink-text"><span id="status">${status }</span></td></tr>
</table>
<div id="loading-div-background" class="loading-div-background" style="display:none;">
	<div class="loader"></div>
	<div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;">
		<br><br><br><br><br><br><br>Loading....
	</div>
</div>	
</body>
</html>