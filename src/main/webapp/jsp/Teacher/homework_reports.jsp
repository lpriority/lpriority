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
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Homework Reports </title>
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>

<script src="resources/javascript/teacher_popup.js"></script>
<script src="resources/javascript/TeacherJs/current_homework.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#returnMessage').fadeOut(4000);
	});
	function clearDiv(){
		$("#ReportList").empty();
	}
</script>
    </head>

    <body>
       <form:form name="homeworkReports" modelAttribute="assignment">
        <table border=0 cellSpacing=0 cellPadding=0 width="100%" align=center height=69>
            

            <tr>
                <%if (session.getAttribute("check1") != null) {%>
                <td width="30%" align="right" valign="middle" class="Admin"><a href="../Admin/AdminDesktop.jsp" class="Admin">Admin Desktop</a></td>
                <%}%>

            </tr>
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

                    <table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0 class="title-pad">
                        <tr>
                            <td height="30" colspan="2" align="left" valign="middle"><table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td width="6%" align="left" valign="middle" ><label class="label">Grade</label></td>
                                        <td width="12%" align="left" valign="middle">
                                        <select name="gradeId"
												class="listmenu" id="gradeId"
												onChange="getGradeClasses();clearDiv()" required="required">
													<option value="select">select grade</option>
													<c:forEach items="${teacherGrades}" var="ul">
														<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
													</c:forEach>
											</select>
                                        </td>
                                        <td width="6%" align="left" valign="middle" ><label class="label">Class</label></td>
                                        <td width="12%" align="left" valign="middle" >
                                              <select
											id="classId" name="classId" class="listmenu"
											onChange="getClassSections();clearDiv()" required="required">
												<option value="">select subject</option>
										</select></td>

                                        <td width="6%" align="left" valign="middle" ><label class="label">Section</label></td>
                                        <td width="12%" align="left" valign="middle" >
                                            <form:select
												id="csId" path="classStatus.csId" class="listmenu"
												onChange="getAssignedDates();clearDiv()" required="required">
												<option value="">select Section</option>
											</form:select></td>
                                     <td width="6%" align="left" valign="middle" ><label class="label">Date</label></td>
                                        <td width="12%" align="left" valign="middle" class="header">
                                            <select name="assignedDate" class="listmenu" id="assignedDate" onChange="getAssignmentTitles();clearDiv()">
                                                <option value="select">Select Date</option>
                                            </select></td>
                                           <td width="6%" align="left" valign="middle" ><label class="label">Title</label></td>
                                        <td width="12%" align="left" valign="middle" class="header">
                                            <select name="titleId" class="listmenu" id="titleId" onChange="clearDiv();showReports()">
                                                <option value="select">Select Title</option>
                                            </select></td>
                                        <td width="34" align="center" valign="middle"></td>

                                        <td width="139" align="left" valign="middle"><font color="aeboad" size="3"></font></td>
                                         <td width="136" align="left" valign="middle" class="header">&nbsp;</td>
                                        <td width="66" align="left" valign="middle"><input type="hidden" id="usedFor" value="${usedFor}" /></td>
                                    </tr>
                                </table></td>
                        </tr>
                        <tr>
                            <td height="10" colspan="2">&nbsp;</td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center" valign="top">
                                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td style="width:100%;" id="ReportList" colspan="10" ></td>
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