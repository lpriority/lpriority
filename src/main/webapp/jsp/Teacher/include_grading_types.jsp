<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link
	href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css"
	rel="stylesheet" type="text/css" />
<script>
	function goToDiv(i) {
		document.getElementById("sho" + i).scrollIntoView(true);
	}
</script>
<script src="resources/javascript/TeacherJs/grade_assessments.js"></script>
</head>
<body>
	<c:set var="queCount" value="0"></c:set>
	<c:set var="co" value="1"></c:set>
	<c:set var="quedisplayCount" value="1"></c:set>
	<table>
		<c:if test="${userTypeId==3}">
			<c:if
				test="${(gradeTypesId==2 || gradeTypesId==3) && !empty gradeStatus}">
				<tr>
					<td width="500"><label class="tabtxt"> <font
							color="blue">Graded Status&nbsp;:&nbsp;</font>${gradeStatus}<br>
							<c:if test="${gradeTypesId==3}">
								<font color="blue">Peer Review By</font>&nbsp;:&nbsp;${peerReviewBy}<br>
								<font color="blue">Peer Submit Date</font>&nbsp;:&nbsp;${peerSubmitDate}<br>
							</c:if>
					</label><br></td>
				</tr>
			</c:if>
		</c:if>
	</table>
	<c:choose>
		<c:when
			test="${fn:length(benchQuestions[0].fluencyMarks) gt 0 && assignmentTypeId==8}">
			<table>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td width='100' height='25' align='left'><b>Test</b></td>
					<td><b>WCPM</b></td>
					<td><b>Accuracy</b></td>
				</tr>
				<c:set var="marks" value="0"></c:set>
				<c:set var="coun" value="0"></c:set>
				<c:set var="Type" value=""></c:set>
				<c:set var="co" value="0" />
				<c:forEach items="${benchQuestions}" var="questionList"
					varStatus="count">
					<c:forEach items="${questionList.fluencyMarks}" var="typesList">
						<input type="hidden" id="marks" name="marks"
							value="${typesList.marks}" />
						<c:if test="${queCount%2==0}">
							<c:set var="co" value="${co+1}"></c:set>
						</c:if>
						<c:set var="yes" value="0"></c:set>
						<c:if test="${empty typesList.marks}">
							<c:set var="marks" value="0"></c:set>
						</c:if>
						<c:if test="${not empty typesList.marks}">
							<c:set var="marks" value="${typesList.marks}"></c:set>
						</c:if>
						<tr>
							<td width='100' height='25' align='left'><a
								href='javascript:openFluecyResultWindow(${questionList.assignmentQuestionsId},${questionList.studentAssignmentStatus.studentAssignmentId},${questionList.studentAssignmentStatus.assignment.benchmarkCategories.benchmarkCategoryId},${typesList.readingTypes.readingTypesId},${gradeTypesId},${userTypeId})'>
									<c:if test="${typesList.readingTypes.readingTypesId==3}">
										<c:set var="Type" value="Score"></c:set>
									</c:if> <c:out value="${typesList.readingTypes.readingTypes}" />&nbsp;
									<c:out value="${co}" />
									
							</a></td>
							<c:choose>
												<c:when test="${typesList.readingTypes.readingTypesId ne 3}">
													<td width='100' height='25' align='left'><input
														type='text'
														id='marks:${questionList.assignmentQuestionsId}:${typesList.readingTypes.readingTypesId}'
														name='marks' value='${typesList.marks}' maxlength='3'
														size='3' disabled /></td>
												</c:when>
												<c:otherwise>
													<td width='100' height='25' align='left'><input
														type='text'
														id='marks:${questionList.assignmentQuestionsId}:${typesList.readingTypes.readingTypesId}'
														name='marks' value='${typesList.qualityOfResponse.qorId}' maxlength='3'
														size='3' disabled /></td>
												</c:otherwise>
											</c:choose>
											<c:if test="${typesList.readingTypes.readingTypesId==2}">
												<td width='100' height='25' align='left'><input
													type='text'
													id='accuracyMarks:${questionList.assignmentQuestionsId}:${typesList.readingTypes.readingTypesId}'
													name='accuracyMarks' value='${typesList.accuracyScore}'
													maxlength='4' size='4' disabled />
													<input type="hidden" name="assignmentQuesList" id="assignQuesList:${co}" value="${questionList.assignmentQuestionsId}" />
													</td>
											</c:if>
										</tr>

										<c:set var="queCount" value="${queCount+1}"></c:set>
									</c:forEach>
				</c:forEach>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<c:if test="${gradeTypesId==1}">
					<tr>
						<td align='left'>Notes:<textarea id='teacherNotes'
								name='teacherNotes'><c:out value="${teacherComment}"></c:out></textarea></td>
						<td>&nbsp;</td>
					</tr>
				</c:if>
				<div id="gradeBenchmark" style="align: center"></div>
				<tr>
					<td width='100' height='25' align='center'></td>
					<td width='100' align='center'></td>
				</tr>
			</table>
		</c:when>
		<c:when
			test="${fn:length(benchQuestions[0].fluencyMarks) gt 0 && assignmentTypeId==20}">
			<table>
				<tr>
						<td width='80' height='25' align='left' class="tabtxt"><b>Test</b></td>
						<td width='120' height='25' align='left' class="tabtxt"><b>Accuracy
						Score</b></td>
						<td width='100' height='25' align='left' class="tabtxt"><b>Accuracy</b></td>
				</tr>
				<c:set var="marks" value="0"></c:set>
							<c:set var="coun" value="0"></c:set>
							<c:forEach items="${benchQuestions}" var="questionList"
								varStatus="count">
								<c:forEach items="${questionList.fluencyMarks}" var="typesList">
									<input type="hidden" id="marks" name="marks"
										value="${typesList.marks}" />
									<c:if test="${count.count%2==0}">
										<c:set var="co" value="${co+1}"></c:set>
									</c:if>
									<c:set var="yes" value="0"></c:set>
									<c:if test="${empty typesList.marks}">
										<c:set var="marks" value="0"></c:set>
									</c:if>
									<c:if test="${not empty typesList.marks}">
										<c:set var="marks" value="${typesList.marks}"></c:set>
									</c:if>
									<tr>
										<td width='100' height='25' align='left'>
										<a href='javascript:openAccuracyWindow(${questionList.assignmentQuestionsId},${questionList.studentAssignmentStatus.studentAssignmentId},4,${typesList.readingTypes.readingTypesId},${typesList.gradingTypes.gradingTypesId},${userTypeId})'>
												<c:out value="${typesList.readingTypes.readingTypes}" />
										</a></td>
										<td width='100' height='25' align='left'><input
											type='text'
											id='marks:${questionList.assignmentQuestionsId}:${typesList.readingTypes.readingTypesId}'
											name='marks' value='${typesList.marks}' maxlength='3'
											size='3' disabled /></td>

										<td width='100' height='25' align='left'><input
											type='text'
											id='accuracyMarks:${questionList.assignmentQuestionsId}:${typesList.readingTypes.readingTypesId}'
											name='accuracyMarks' value='${typesList.accuracyScore}'
											maxlength='4' size='4' disabled /></td>

									</tr>

									<c:set var="queCount" value="${queCount+1}"></c:set>
								</c:forEach>
							</c:forEach>

							<%-- <c:if test="${gradeTypesId==1}">
								<tr>
									<td align='left'>Notes:<textarea id='teacherNotes'
											name='teacherNotes'><c:out
												value="${teacherComment}"></c:out></textarea></td>
									<td>&nbsp;</td>
								</tr>
							</c:if> --%>
							<tr>
				<td width='100' height='25' align='center'></td>
				<td width='100' align='center'></td>
			</tr>
						</table>
			<div id="gradeBenchmark"
				style="background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center; align: center"></div>
			

		</c:when>
		<c:otherwise>Not assigned for this grading</c:otherwise>
	</c:choose>

</body>
</html>