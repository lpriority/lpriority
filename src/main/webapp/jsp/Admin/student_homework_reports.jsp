<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Homework Reports</title>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript"
	src="resources/javascript/admin/student_reports.js"></script>
<script src="resources/javascript/admin/common_dropdown_pull.js"></script>
<script type="text/javascript">
	$(function() {
		$("#fromId").datepicker({
			changeMonth : true,
			changeYear : true,
			showAnim : 'clip',
			maxDate : new Date()
		});
	});
	function clears() {
		$("#title").empty();
		$("#title").append($("<option></option>").val('').html('Select Title'));
		$("#StudentHomeworkReports").empty();
		$("#homeworkQuestions").empty();

	}
</script>
</head>
<body>
	<table width="100%">
		<tr>
			<td vAlign=top width="100%" colSpan=3 align=middle>
				<table width="100%" border=0 align="center" cellPadding=0
					cellSpacing=0>
					<tr>
						<td colspan="" width="100%">
							<table width="99.8%" class="title-pad heading-up" border="0">
								<tr>
									<td class="sub-title title-border" height="40" align="left">Student
										Homework Reports</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td height=0 vAlign=top colSpan=2 align=left>
							<div class="title-pad" style="padding-top: 1em;">
								<table width="100%" border=0 align="center" cellPadding=0
									cellSpacing=0>
									<tr>
										<td height="30" colspan="2" align="left" valign="middle">
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0" class="label" style="font-size: 12pt">
												<tr>
													<td width="74" align="left" valign="middle">Grade</td>
													<td width="160" align="left" valign="middle"><select
														id="gradeId" name="gradeId" style="width: 120px;"
														class="listmenu" onchange="getGradeClasses();clears()">
															<option value="select">select grade</option>
															<c:forEach items="${grList}" var="ul">
																<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
															</c:forEach>
													</select></td>

													<td width="74" align="left" valign="middle">Class</td>
													<td width="160" align="left" valign="middle"><select
														id="classId" name="classId" class="listmenu"
														style="width: 120px;"
														onchange="getClassSections();clears()">
															<option value="select">select subject</option>
													</select></td>
													<td width="74" align="left" valign="middle">Section</td>

													<td width="160" align="left" valign="middle"><select
														name="csId" class="listmenu" id="csId"
														style="width: 120px;" onchange="clears()">
															<option value="select">Select Section</option>
													</select></td>

													<td width="74" align="left" valign="middle">Date</td>
													<td width="140" align="left" valign="middle" class="header">
														<input type="text" style="width: 90px" id="fromId"
														maxlength="15" onChange="showAssignmentTitles();"
														readonly="readonly" />
													</td>

													<td width="74" align="left" valign="middle">Title</td>
													<td width="160" align="left" valign="middle" class="header">
														<select name="titleId" class="listmenu" id="titleId"
														style="width: 120px;" onChange="showHomeworkReports()">
															<option value="select">Select Title</option>
													</select>
													</td>

													<td width="136" align="left" valign="middle" class="header">
													</td>
													<td width="66" align="left" valign="middle"><input
														type="hidden" id="usedFor" value="${usedFor}" /></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td height="2" colspan="2">&nbsp;<br></td>
									</tr>
									<tr>
										<td colspan="2" align="center" valign="top"
											id="StudentHomeworkReports"></td>
									</tr>
									<tr>
										<td colspan="2" align="center" valign="top">
											<div id="homeworkQuestions">
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>