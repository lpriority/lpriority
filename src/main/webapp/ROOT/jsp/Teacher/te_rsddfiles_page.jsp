

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
<title>Reading Skills Development Dashboard</title>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/common/reading_skills.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<link href="resources/javascript/jplayer/skin/blue.monday/css/jplayer.blue.monday.min.css" rel="stylesheet" type="text/css"/>
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>

<script type="text/javascript">
	function clearDiv() {
		$("#contentDiv").empty();
	}
</script>
</head>
<body>
	<table style="padding-left: 8em;width: 99.8%" class="title-pad heading-up">			
         <tr>
           	<td colspan="20" class="sub-title title-border" align="left">
         Reading Skills Development Dashboards
                  </td>
              </tr>
	</table>
	<table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
	
		<tr>
			<td colspan="2" align="left" valign="left" width="100%" class="heading-up">
				<input type="hidden" id="teacherObj" value="${teacherObj}">
				<table align="left" valign="left" width="50%" class="title-pad heading-up heading-up" border=0 cellPadding=0 cellSpacing=0 >
					<tr>
						 <td height="20" align="left" class="label" style="width: 5em;">Grade &nbsp;&nbsp;&nbsp;
							<select name="gradeId" class="listmenu" id="gradeId"
								onChange="clearDiv();getGradeClasses()" required="required">
									<option value="">select grade</option>
									<c:forEach items="${teacherGrades}" var="ul">
										<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
									</c:forEach>
							</select>
						</td>
						<td align="left" class="label" style="width: 5em;padding-left: 2em;">Class &nbsp;&nbsp;&nbsp;
							<select id="classId"
								name="classId" class="listmenu"
								onChange="clearDiv();getClassSections()" required="required">
								<option value="">select subject</option>
						</select>
						</td>
	
						<td align="left" class="label" style="width: 5em;padding-left: 2em;">Section &nbsp;&nbsp;&nbsp;
							<select
								id="csId" class="listmenu"
								onChange="clearDiv();getAdminRSDashboards();"
								required="required"> 
								<option value="">select Section</option>
							</select>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	<!-- 	<tr>
					          <td height="2" colspan="2"><br>
		                          <div id="videoDiv">
				                       <a href="#" onClick="openVideoDialog('teacher','teacher_access_RSDD','videoDailog')" class="demoVideoLink"><i class="ion-videocamera" aria-hidden="true" style="font-size:26px;font-weight:bold;color:#e23c00;"></i>&nbsp;video for Access Reading Skills Development Dashboard</a>
		 	                     </div>
			                  </td>
	   						</tr> -->
		<tr>
			<td id="contentDiv" colspan="7" align="center" valign="middle"></td>
		</tr>
	</table>
	<div id="videoDailog" title="Grade Fluency" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
</body>
</html>