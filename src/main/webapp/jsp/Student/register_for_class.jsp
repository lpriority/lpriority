<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<%@ include file="../CommonJsp/include_resources.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript" src="/dwr/interface/studentService.js"></script>
<script type="text/javascript" src="/dwr/interface/teacherViewClassService.js"></script>
<script type="text/javascript" src="/dwr/interface/teacherSchedulerService.js"></script>
<script type="text/javascript" src="resources/javascript/Student/student.js"></script>

<title>Request a Class</title>
<style>
.btn {
	background: #4E0C90;
	background-image: -webkit-linear-gradient(top, #4E0C90, #4E0C90);
	background-image: -moz-linear-gradient(top, #4E0C90, #4E0C90);
	background-image: -ms-linear-gradient(top, #4E0C90, #4E0C90);
	background-image: -o-linear-gradient(top, #4E0C90, #4E0C90);
	background-image: linear-gradient(to bottom, #4E0C90, #4E0C90);
	-webkit-border-radius: 28;
	-moz-border-radius: 28;
	border-radius: 28px;
	font-family: Arial;
	color: #ffffff;
	font-size: 13px;
	padding: 10px 20px 10px 20px;
	text-decoration: none;
}

.btn:hover {
	background: #9054cc;
	background-image: -webkit-linear-gradient(top, #9054cc, #8d46d4);
	background-image: -moz-linear-gradient(top, #9054cc, #8d46d4);
	background-image: -ms-linear-gradient(top, #9054cc, #8d46d4);
	background-image: -o-linear-gradient(top, #9054cc, #8d46d4);
	background-image: linear-gradient(to bottom, #9054cc, #8d46d4);
	text-decoration: none;
}
</style>
<script>
	$(document)
			.ready(
					function() {
						var divId = document.getElementById("divId").value;
						if (divId == 'header') {
							$("#header").show();
							$("#details").hide();
							loadClasses();
						} else if (divId == 'details') {
							$("#header").show();
							$("#details").show();
							var loadClassesCallBack = function(list) {
								if (list != null) {
									dwr.util.removeAllOptions('classId');
									dwr.util
											.addOptions('classId', [ "select" ]);
									dwr.util.addOptions('classId', list,
											'classId', 'className');
									$('select[name="classId"]')
											.val(
													document
															.getElementById("classIdHidden").value)
								}
							}
							teacherSchedulerService.getStudentClasses(document
									.getElementById("gradeId").value, {
								callback : loadClassesCallBack
							});
							var formatter = function(entry) {
								var fullName = entry.userRegistration.title
										+ " "
										+ entry.userRegistration.firstName
										+ " " + entry.userRegistration.lastName;
								return fullName;
							}
							var loadTeachersCallBack = function(list) {
								if (list != null) {
									dwr.util.removeAllOptions('teacherId');
									dwr.util.addOptions('teacherId',
											[ "select" ]);
									dwr.util.addOptions('teacherId', list,
											'teacherId', formatter);
									$('select[name="teacherId"]')
											.val(
													document
															.getElementById("teacherIdHidden").value)
								}
							}
							teacherSchedulerService.getTeachers(document
									.getElementById("gradeId").value, document
									.getElementById("classIdHidden").value, {
								callback : loadTeachersCallBack
							})
							var section = function(list) {
								return list.classStatus.section.section;
							}
							var sectionId = function(list) {
								return list.classStatus.section.sectionId;
							}
							var teacherSectionsCallBack = function(list) {
								dwr.util.removeAllOptions('sectionId');
								dwr.util.addOptions('sectionId', [ "select" ]);
								if (list != null) {

									dwr.util.addOptions('sectionId', list,
											sectionId, section);
									$('select[name="sectionId"]')
											.val(
													document
															.getElementById("sectionIdHidden").value)
								} else {
									alert("No data found");
								}
								var a = new Array();
								$("#sectionId").children("option").each(
										function(x) {
											duplicate = false;
											b = a[x] = $(this).val();
											for (i = 0; i < a.length - 1; i++) {
												if (b == a[i])
													duplicate = true;
											}
											if (duplicate)
												$(this).remove();
										})
							}
							var studentId = dwr.util.getValue('studentId');
							var gradeClassId = dwr.util
									.getValue('gradeClassId');
							if (teacherId != 'select') {
								studentService
										.getSectionsForRegistration(
												document
														.getElementById("teacherIdHidden").value,
												studentId,
												gradeClassId,
												{
													callback : teacherSectionsCallBack
												});
							}
						}
					});
</script>
</head>
<body>

	<input type="hidden" id="gradeId" name="gradeId" value="${gradeId}">
	<input type="hidden" id="classIdHidden" name="classIdHidden"
		value="${classId}">
	<input type="hidden" id="teacherIdHidden" name="teacherIdHidden"
		value="${teacherId}">
	<input type="hidden" id="sectionIdHidden" name="sectionIdHidden"
		value="${sectionId}">
	<input type="hidden" id="csId" name="csId" value="${csId}">
	<input type="hidden" id="gradeLevelId" name="gradeLevelId"
		value="${gradeLevelId}">
	<input type="hidden" id="gradeClassId" name="gradeClassId"
		value="${gradeClassId}">
	<input type="hidden" id="studentId" name="studentId"
		value="${studentObj.studentId }">
	<input type="hidden" id="divId" name="divId" value="${divId}">

	<table width="99.8%" border="0" cellspacing="0" cellpadding="0"
		class="title-pad" style="padding-top: 1em">
		<tr>


			<td class="sub-title title-border" height="40" align="left">Available
				Classes at <font color="#0057af" size="4">${schoolName}</font>
			</td>
		</tr>
	</table>
	<table style="width: 100%" align="center" class="title-pad">
		
					
						
								<tr>

									<td style="" align="left"></td>
								</tr>

								<tr>
									<td>&nbsp;</td>
								</tr>


								<tr>
									<td>
										<table width="100%" height="30" border="0" cellpadding="0"
											cellspacing="0" align='left'>
											<tr align="left">
												<th width="50" align='left'><label class="label">Class</label></th>
												<td width="70" align="left" valign="middle"><select
													id="classId" name="classId" class="listmenu"
													style="width: 120px;" onchange="loadTeachers()">
														<option value="select">select subject</option>
												</select></td>
												<th width="50"><label class="label">Teacher</label></th>
												<td width="70" align="left" valign="right"><select
													id="teacherId" name="teacherId" class="listmenu"
													style="width: 120px;" onchange="getTeacherSections()">
														<option value="select">Select Teachers</option>
												</select></td>
												<th width="50"><font color="black" size="3"><label
														class="label">Section</label></font></th>
												<td width="70" align="left" valign="right"><select
													id="sectionId" name="sectionId" class="listmenu"
													style="width: 120px;" onchange="getDetailsOfSections()">
														<option value="select">Select Section</option>
												</select></td>
												<td width='300'>&nbsp;</td>
												<td width='120'>&nbsp;</td>
											</tr>
											<tr>
												<td height="4" colspan="6"></td>
											</tr>

										</table>
									</td>
								</tr>
								<tr>
									<td height=45 colSpan=4><div align="right"></div></td>
								</tr>
								<tr>
									<td>
										<div id="details">
											<table width="100%" height="30" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td align="center" width="60" height="60">
														<table width="459" border="1" class="header" style="background:#E3EDEF;"
															cellpadding="1" cellspacing="1">
															<tbody class="designTblCls">
																<tr>
																</tr>
																<c:forEach items="${schoolDaysLt}" var="schoolDays"
																	varStatus="status">
																	<tr>
																		<th width="50" height="40" align="center" style="color:white;font-size:20;background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00E3FF), color-stop(1,#007B8C) )"><font
																			size="4"><b>${schoolDays.days.day}</b></font></th>
																		<c:forEach items="${casLt}" var="cas">
																			<c:if
																				test="${cas.days.dayId eq schoolDays.days.dayId && cas.classStatus.section.gradeClasses.studentClass.classId eq classId && cas.classStatus.section.sectionId eq sectionId}">
																				<td align="center" width=60">
																					<table border="1">
																						<tr>
																							<td align="center" width=60"><b>${cas.periods.startTime}-${cas.periods.endTime}</b><br /></td>
																						</tr>
																						<tr>
																							<td align="center" width=60">
																								${cas.periods.periodName} <br />
																								${cas.periods.grade.masterGrades.gradeName}<br />
																							</td>
																						</tr>
																					</table>
																				</td>
																			</c:if>
																		</c:forEach>
																	</tr>
																</c:forEach>
															</tbody>
														</table>
													</td>
												</tr>
												<tr>
													<td height=45 colSpan=4><div align="right"></div></td>
												</tr>
												<tr>
													<td width="35%" height="55" align="center" valign="middle">
														<input type="button" class="button_green" id="sendRequest"
														alt="sendRequest" height="52" width="50"
														value="Send Request" name="sendRequest"
														onclick="setStatusForClassRegistration(this)"> <input
														type="button" class="button_green" id="cancelRequest"
														alt="cancelRequest" height="48" width="125"
														name="cancelRequest" value="Cancel Request"
														onclick="setStatusForClassRegistration(this)">
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							
				</table>
		
	
	
</body>

</html>