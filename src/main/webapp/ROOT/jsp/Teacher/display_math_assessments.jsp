<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page session="true"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Create Math Assessments</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<script src="resources/javascript/TeacherJs/math_assessment.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/javascript/jplayer/skin/blue.monday/css/jplayer.blue.monday.min.css" rel="stylesheet" type="text/css"/>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript">
function clearDiv(){
	$('#quizQuestionsDiv').html("");
}
</script>
<style>

</style>
</head>
<body>
	<div>
		<%@ include file="/jsp/assessments/rti_tabs.jsp" %>
	</div>
	<input type="hidden" name="page" id="page" value="${page}" />
	<input type="hidden" name="setType" id="setType" value="${setType}" />
	<input type="hidden" name="teacherId" id="teacherId" value="${teacherId}" />
	<input type="hidden" name="teacherName" id="teacherName" value="${teacherName}" />
	<input type="hidden" name="letterCount" id="letterCount"/>
	<input type="hidden" name="usedFor" id="usedFor" value="rti" />  
	<table width='100%'><tr><td align='center'>
	<table width="70%" height="30" border="0" cellpadding="0" cellspacing="0" align="left" class="title-pad">
		<tr class="label">
			<td width="60" align="left">Grade</td>
			<td width="150" align="left" valign="right">
				<select	id="gradeId" name="gradeId" class="listmenu" onchange="clearDiv();getGradeClasses();" style="width:120px;">
						<option value="select">select</option>
			 		<c:forEach items="${teacherGrades}" var="ul">
						<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
					</c:forEach> 
				</select>
			</td>
			<td width="60" align="left">Class</td>
			<td width="150" align="left" valign="middle">
				<select id="classId" name="classId" class="listmenu" style="width:120px;" onchange="clearDiv();getClassSections();" >
				 	<option value="select">select</option>
				</select>
			</td>
			<td width="80" align="left">Section</td>
			<td width="150" align="left" valign="right">
				<select id="csId" name="csId" class="listmenu" onChange="clearDiv();getQuizQuestions()">
					<option value="select">select Section</option>
				</select>
			</td>
		   <td height=10 width="250" align="right"><div class="button_green" align="Create Question" onclick="openQuizQuestionDialog(-1)" id="createQuestionBtn" name="createQuestionBtn" style="display:none;">Create Quiz Questions</div></td>
		 </tr>
		 </table></td></tr>
		 <tr><td width="100%" colspan="10" align="center" style="padding-top:2em;"><div id="quizQuestionsDiv"></div></td></tr>
	</table>
	<div id="createQuizQuestionsDialog"  style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title=""><iframe  id ="iframe" src="" frameborder="0" scrolling="no" width="100%" height="95%" src=""></iframe></div>
</body>
