<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Students To Class</title>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/admin/add_students_to_class.js"></script>
<script src="resources/javascript/admin/common_dropdown_pull.js"></script>
</head>
<body>
	<table width="100%"  class="title-pad" style="padding-top: 0.5em">
		<tr>
			<td class="sub-title title-border" height="40" align="left" valign="middle">Add Students To Class</td>
		</tr>
	</table>
	<form:form name="AddStudentsToClass" modelAttribute="StudentsToClass" method="get">
		<!-- Content center box -->	
		<table style="width: 100%;" align="center" border="0" cellspacing="0" cellpadding="0" >
			<tr>
				<td colspan="2" align="left" valign="middle" class="heading-up">
					<table width="100%" border="0" cellpadding="0" class="title-pad" cellspacing="0">
						<tr>
							<td width="70em" valign="middle" class="label" style="font-size: 0.98em" >Grade  </td>
							<td width="150em" valign="middle" >
								<select id="gradeId" name="gradeId" onchange="clearDiv();getGradeClasses();" class="listmenu"  style="width:120px;">
									<option value="select">select grade</option>
									<c:forEach items="${grList}" var="ul">
										<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
									</c:forEach>
								</select>
							</td>
							<td width="70em"  valign="middle" class="space-between-select"><label class="label" style="font-size: 0.98em">Class  </label></td>
							<td width="150em" valign="center">
								<select id="classId" name="classId" class="listmenu" onchange="clearDiv();getClassSections()" style="width:120px;">
									<option value="select">select subject</option>
								</select>
							</td>
							<td width="70em" valign="middle" class="space-between-select label" style="font-size: 0.98em">Section  </td>
							<td>
								<select name="csId" class="listmenu" id="csId" onchange="clearDiv();getStudentList();" style="width:120px;">
									<option value="select">Select Section</option>
								</select>
							</td>
						</tr>
					</table>
				</td>
			</tr>
	        <tr><td style="width: 100%;" align="center" border="0" cellspacing="0" cellpadding="0">
					<div id="subViewDiv" class="space-between"></div>
	        </td></tr>
		</table>
	</form:form>
	</body>
</html>