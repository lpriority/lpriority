<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Promote Student Grade</title>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript" src="/dwr/interface/AddStudentsToClassService.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/admin/promote_students.js"></script>
</head>

<body>
	<table width="100%"  class="title-pad" style="padding-top: 0.5em">
		<tr>
			<td class="sub-title title-border" height="40" align="left" valign="middle">Promote Students</td>
		</tr>
	</table>
	<form:form name="promoteStudents" modelAttribute="promoteStudents" method="get" action="promoteStudentGrade.htm">
		<!-- Content center box -->	
		<table style="width: 100%;" align="center" border="0" cellspacing="0" cellpadding="0" >
			<tr>
				<td colspan="2" align="left" valign="middle" class="space-between">
					<table width="100%" border="0" cellpadding="0" class="title-pad" cellspacing="0">
						<tr>
							<td width="70em" valign="middle" class="label" style="font-size: 0.98em" >Grade  </td>
							<td width="150em" valign="middle" >
								<select id="gradeId" name="gradeId" onchange="loadGradeStudents();clearDiv();" class="listmenu"  style="width:120px;">
									<option value="select">Select Grade</option>
									<c:forEach items="${schoolgrades}" var="ul">
										<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
									</c:forEach>
								</select>
							</td>
							<td width="150em"  valign="middle" class="space-between-select"><label class="label" style="font-size: 0.98em">Promote Grade</label></td>
							<td valign="center">
								<select id="promoteId" name="promoteId" class="listmenu" onchange="checkPromoteGrade()" style="width:120px;">
									<option value="">Select Promote</option>
									<c:forEach items="${schoolgrades}" var="ul">
										<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
									</c:forEach>
									<option value="promote">Final Promotion</option>
								</select>
							</td>							
						</tr>
					</table>
				</td>
			</tr>
	        <tr><td style="width: 100%;" align="center" border="0" cellspacing="0" cellpadding="0">
					<div id="subViewDiv" class="space-between"></div>
	        </td></tr>
			<tr align="center"><td colspan="3" style="padding-top: 2em;">
				<label id="returnMessage" style="color: blue;"> ${promoteStatus}</label>&nbsp;
			</td></tr>
		</table>
	</form:form>
</body>
</html>