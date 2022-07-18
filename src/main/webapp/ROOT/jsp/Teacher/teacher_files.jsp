<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Teacher Files</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/my_files.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<c:if test="${userReg.user.userType == 'teacher'}">
			<c:choose>
				<c:when test="${page == 'uploadFile' }">
					<%@ include file="/jsp/curriculum/curriculum_tabs.jsp"%>
				</c:when>
				<c:otherwise>
				 <%-- <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
					<tbody><tr>
					<td valign="bottom" align="right">
		              	<ul class="button-group">
							<li><a href="displayTeacherClassFiles.htm?fileType=public&page=public" class="${(fileType == 'public')?'buttonSelect leftPill':'button leftPill'}"> Class Files </a></li>
							<li><a href="displayTeacherClassFiles.htm?fileType=private" class="${(fileType == 'private')?'buttonSelect':'button'}"> Private Files </a></li>
							<li><a href="loadTeacherAdminFiles.htm?fileType=public" class="${(fileType == 'admin')?'buttonSelect':'button'}"> Admin Files </a></li>
							<li><a href="displayTeacherStudentFiles.htm?fileType=student" class="${(fileType == 'student')?'buttonSelect rightPill':'button rightPill'}"> Student Files </a></li>
						</ul>
					</td></tr>
					</tbody></table> --%>
					</td>
				</tr>
					</tbody></table></td></tr>
                   </tbody></table> 
				</c:otherwise>
			</c:choose>
		</c:if>
	<input type="hidden" id="page" name="page" value="${page}" />
	<table width="100%">
	<%-- <c:if test="${page ne 'uploadFile'}">
	    <tr><td colspan="" width="100%"> 
			 <table width="100%" class="title-pad" border="0">
				<tr>				
					<td class="sub-title title-border" height="40" align="left" >${title}</td>					
				</tr>
			</table>
		</td></tr></c:if> --%>
		<tr>
			<td height="30" colspan="2" align="left" valign="middle" class="title-pad heading-up">
				<table id="dropdownTable" width="100%" height="30" border="0" cellpadding="0" cellspacing="0" class="label"  style="font-size: 12pt;">
					<c:if test="${fileType ne 'admin'}">
						<tr>
							<td><input type="hidden" id="teacherObj"
								value="${teacherObj}" /></td>
						</tr>
						<form name="createFolder" action="displayCreateFolder.htm"
							method="post">
							<input type="hidden" id="teacherId" name="teacherId"
								value="${teacherId}" />
							<td width="18%" align="left" valign="middle">Grade &nbsp;&nbsp;&nbsp;
								 <select name="gradeId" class="listmenu" id="gradeId" style="width:120px;" onChange="getGradeClasses()">
										<option value=select>select grade</option>
										<c:forEach items="${grList}" var="ul">
											<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
										</c:forEach>
								</select>
							</td>
							<c:if test="${(fileType eq 'public')}">
								<td width="18%" align="left" valign="center">Class  &nbsp;&nbsp;&nbsp; 
									<select	id="classId" name="classId" class="listmenu" style="width:120px;" onchange="getUnits();">
											<option value=select>select class</option>
									</select>
								</td>
							</c:if>
							<c:if test="${(fileType eq 'private')}">
								<td width="18%" align="left" valign="center">Class &nbsp;&nbsp;&nbsp; 
									<select id="classId" name="classId" class="listmenu" style="width:120px;" onchange="loadTeacherFolders();">
											<option value=select>select subject</option>
									</select>
								</td>
							</c:if>
							<input type="hidden" id="fileType" name="fileType" value="${fileType}" />
						</form>
						<c:if test="${fileType eq 'public'}">
							<td width="18%" align="left" valign="center">Unit &nbsp;&nbsp;&nbsp; 
								<select	id="unitId" name="unitId" class="listmenu" style="width:120px;"	onchange="displayLessons();">
										<option value="select">select unit</option>
								</select>
							</td>
						</c:if>
						<c:if test="${fileType eq 'student'}">
							<td width="18%" align="left" valign="center">Class &nbsp;&nbsp;&nbsp;
							 <select
								id="classId" name="classId" class="listmenu" style="width:120px;"
								onchange="getClassSections()">
									<option value=select>select class</option>
							</select></td>
							<td width="18%" align="left" valign="center">Section  &nbsp;&nbsp;&nbsp; 
							<select
								id="csId" name="csId" class="listmenu" style="width:120px;"
								onchange="getStudentsBySection();">
									<option value=select>select section</option>
							</select></td>
							<td width="18%" align="left" valign="center">Student &nbsp;&nbsp;&nbsp; 
							<select
								id="studentId" name="studentId" class="listmenu" style="width:120px;"
								onchange="loadStudentFolders();">
									<option value=select>select student</option>
							</select></td>
						</c:if>

						<td width="53%" align="right" valign="middle"><c:if
								test="${fileType ne 'public'}">
								<div id="createFolderDiv" style="display: none;">
								<div class="button_green" align="right" onclick="teacherCreateFolderDialog()">Create Folder</div>		
								</div>
							</c:if></td>
						<td>
							<div id="dialog" style="display: none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title="">
								<iframe id="iframe" src="" frameborder="0" scrolling="no"
									width="100%" height="95%" src=""></iframe>
							</div>
						</td>
					</tr>
				</c:if>
			</table>
		 </td>
		</tr>
		<tr>
			<td height="0" colspan="2" align="center" valign="top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					id="LessonContent"  class="title-pad heading-up">
					<tr>
						<td><div id="unitLessonDiv"></div></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>