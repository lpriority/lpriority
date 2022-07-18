<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
	<c:choose>
		<c:when test="${tab == 'currentHome' }">Current Homeworks</c:when>
		<c:otherwise>Homework Manager</c:otherwise>
	</c:choose>
</title>
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<script src="resources/javascript/teacher_popup.js"></script>
<script src="resources/javascript/TeacherJs/current_homework.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#returnMessage').fadeOut(4000);
	});
	function clearDiv(){
		$("#lessonId").empty(); 
		$("#lessonId").append(
				$("<option></option>").val('select')
						.html('Select Lesson'));
		$('#HomeWorkQuestions').empty();
		$("#CurrentHomeWorks").empty();
	}
</script>
</head>
    <body>
 <form:form name="currentHomework" modelAttribute="assignment">

        <table border=0 cellSpacing=0 cellPadding=0 width="100%" align=center height=69>     
            <tr>
                <td  vAlign=top width="100%" colSpan=3 align=middle>
					<table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
                        <tr>
                            <td vAlign=bottom align=right>
                                 <div> 
                                	<%@ include file="/jsp/assessments/homework_tabs.jsp" %> 
                                </div>
                            </td>
                        </tr>
                    </table>
					
                    <table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0 class="title-pad heading-up heading-up">
                       
                        <tr>
                            <td height="30" colspan="2" align="left" valign="middle">
                                <table width="90%" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                       
                                        <td width="80" ><label class="label">Grade</label> </td>
                                        <td width="160" >
                                           <select name="gradeId"
												class="listmenu" id="gradeId"
												onChange="clearDiv();getGradeClasses();" required="required">
													<option value="select">select grade</option>
													<c:forEach items="${teacherGrades}" var="ul">
														<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
													</c:forEach>
											</select>

                                        </td>
                                        <td width="80" align="left" valign="middle" ><label class="label">Class</label></td>
                                        <td width="160" align="left" valign="middle" >
                                            <select
											id="classId" name="classId" class="listmenu"
											onChange="clearDiv();getClassSections();" required="required">
												<option value="select">select subject</option>
										</select></td>

                                        <td width="80" align="left" valign="middle" ><label class="label">Section&nbsp;&nbsp;</label></td>
                                        <td width="160" align="left" valign="middle" >
                                           <form:select
												id="csId" path="classStatus.csId" class="listmenu"
												onChange="clearDiv();getHomeworkLessonsBycsId();" required="required">
												<option value="select">select Section</option>
											</form:select></td>
									
                                        <td width="80" align="left" valign="middle"><label class="label">Lesson&nbsp;&nbsp;</label>  </td>
                                        <td width="160" align="left" valign="middle" class="text1"><label>
                                               <form:select
												class="listmenu" id="lessonId" path="lesson.lessonId"
												onChange="getAssignedHomeworks();"
												required="required">
												<option value="select">Select Lesson</option>
											</form:select>
                                            </label></td>
                                        <td width="257" align="left" valign="middle"><input type="hidden" id="stat" value="${tab}"/></td>
                                        <td width="45" align="left" valign="middle"></td>
                                    </tr>
                                </table></td>
                        </tr>
                        <tr>
                            <td height="2" colspan="2"></td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center" valign="top">

                                <table width="100%" height="142" border="0" cellpadding="0" cellspacing="0">

                                    <tr>
                                        <td>&nbsp; </td><br>
                                    </tr>
                                    <tr>
                                        <td colspan="8" >
                                        	<div id="CurrentHomeWorks" ></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="90%" align="center" style="padding-left:8em">
                                        	<div id="HomeWorkQuestions"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td  height="10" align="center" valign="middle" class="tabtxt"></td>
                                        <td  height="10" align="center" valign="middle" class="tabtxt"></td>
                                        <td  height="10" align="center" valign="middle" class="tabtxt"></td>
                                        <td height="10" align="center" valign="middle" class="tabtxt"></td>
                                        <td height="10" align="center" valign="middle" class="tabtxt"></td>
                                        <td height="10" align="center" valign="middle" class="tabtxt"></td>
                                        <td  colspan="2" height="10" align="center" valign="middle" class="tabtxt"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table></td>
            </tr>
            <tr><td width="100%" colspan="10" align="center"><label id="returnMessage" style="color: blue;">${helloAjax}</label></td></tr>
        </table>
       </form:form>
    </body>
</html>
