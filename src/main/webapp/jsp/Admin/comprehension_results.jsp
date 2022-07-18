<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Comprehension Results Download</title>
</head>
<body>
	<%-- <%@ include file="/jsp/assessments/test_results_tab.jsp" %> --%>
	<div class="title-pad" style="padding-top: 1em;">
		<form action="exportComprehensionResults.htm" method="post"
			name="fluencyResults">
			<table width="60%">
				<tr>
				   <td width="10%" align="center" valign="middle" class="label">Academic Year &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
						</td>
					<td width="10%">
						<select  id="compAcademicYearId" name="compAcademicYearId" class="listmenu" style="overflow-y: visible; height: 30px; width: 150px" required onchange="getTeachersByYear(this)">
							<option value="select" disabled="disabled">Select Academic Year</option>
							<c:forEach items="${acadeYearsLst}" var="aca">
								<option value="${aca.academicYearId}">${aca.academicYear}</option>
							</c:forEach>
						</select> 
					</td>
					<td width="5px" valign="middle" class="label">Teacher
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <select id="teacherId"
						name="teacherId" class="listmenu" multiple="multiple"
						style="overflow-y: visible; height: 100px;"
						onclick="selectOption(this, 'teacherId', '')" required="required">
							<option value="" disabled="disabled">Select Teacher</option>
							<c:forEach items="${teacherEmails}" var="teacher">
								<option value="${teacher.teacherId}">${teacher.userRegistration.lastName} ${teacher.userRegistration.firstName}
									</option>
							</c:forEach>
					</select> <input type="checkbox" class="checkbox-design" id="select_all" name="select_all"
						onClick="selectAllOptions(this,'teacherId','')"><label for="select_all" class="checkbox-label-design">Select All</label>
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%" align="center" valign="top"
						id="StudentReportList"><input type="submit"
						class="button_green" value="Get Comprehension Results"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>