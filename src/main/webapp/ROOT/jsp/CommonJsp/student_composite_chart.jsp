<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		 <style type="text/css">
		 	.ui-widget-header{background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00E3FF), color-stop(1,#03b7cf) );border:1px solid #36b3c4;text-shadow:0 1px 1px rgb(0, 48, 53);margin:-.12em;}
		 	.ui-widget-overlay{background:rgba(81, 139, 146, 0.66);}
		 </style>
	</head>
	
	<body>
		<form:form action="saveStudentCompositeChartValues.htm" modelAttribute="compositeChartValues" >
			<input type="hidden" name="csId" value="${csId}"/>
			<input type="hidden" name="studentId" value="${studentId}"/>
			<c:set var="tCount" value="0"></c:set>
			<c:set var="sTotal" value="0"></c:set>
			<c:set var="gTotal" value="0"></c:set>
			<div align="center">
				<table border="1" style="width:95%;border-collapse: collapse;font-size: 14px;font-family: 'Ubuntu','Liberation Sans','Open Sans','sans-serif';text-align: center;">
					<tr>
						<th>% Overall Grade</th>
						<th>Elements Overall grade</th>
						<th style="width:15%;">Types(%, #tests, pointsPer)</th>
						<th>Date of Test</th>
						<th>Date of Submission</th>
						<th>Percentage acquired</th>
						<th>Points Accumulated</th>
						<th>Academic Grade</th>
					</tr>
					<c:forEach begin="0" end="${count}" var="cnt">
						<tr>
							<td width="10%"></td>
							<td width="10%" valign="top">
								<c:if test="${lastEventName != compositeChartValues.compositecharts[cnt].gradeevents.eventName}">
									<div>
										<c:set var="lastEventName" value="${compositeChartValues.compositecharts[cnt].gradeevents.eventName}"></c:set>
										<c:set var="tCount" value="${tCount+1}"></c:set>
										<c:out value="${lastEventName}"></c:out>
									</div>
								</c:if> 
							</td>
							<td width="10%">
								<c:out value="${compositeChartValues.compositecharts[cnt].assignmentType.assignmentType}"></c:out>
								<c:out value="(${compositeChartValues.compositecharts[cnt].nooftests},${compositeChartValues.compositecharts[cnt].pointspertest},
									${compositeChartValues.compositecharts[cnt].overallgrade})"></c:out>
								<input type="hidden" required="required" name="overAllGrade${tCount}" id="overAllGrade${tCount}${cnt}" 
									value="${compositeChartValues.compositecharts[cnt].overallgrade}" />
							</td>
							<td colspan="5" width="70%">
								<c:choose>
									<c:when test="${'classworks' == compositeChartValues.compositecharts[cnt].gradeevents.eventName &&
										 'Lesson work' == compositeChartValues.compositecharts[cnt].assignmentType.assignmentType}">
										 <table width="100%">
											<c:forEach items="${assignedLessons}" var="assignedLesson" varStatus="lessonCount">
												<tr>
												 	<td width="20%"><fmt:formatDate pattern="MM/dd/yyyy" value="${assignedLesson.assignedDate}"/></td>
													<td width="20%"><c:out value="${assignedLesson.lesson.lessonName}"/></td>
													<td width="20%" align="right">
														<input type="number" name="lessonScores" id="lessonScore${lessonCount.index}" maxlength="2" size="10" value="${assignedLesson.score}"/>
														<input type="hidden" name="assignLessonIds" id="assignLessonId${lessonCount.index}" value="${assignedLesson.assignId}"/>
													</td>
													<td width="20%" align="right">
														${assignedLesson.score*compositeChartValues.compositecharts[cnt].pointspertest/100}
														<c:set var="sTotal" value="${sTotal+(assignedLesson.score*compositeChartValues.compositecharts[cnt].pointspertest/100)}"></c:set> 
													</td>												
													<td width="20%">${academicGradeName}</td>
												 </tr>							
											</c:forEach>
										</table>
									</c:when>
									<c:when test="${'classworks' == compositeChartValues.compositecharts[cnt].gradeevents.eventName &&
										 'Activity work' == compositeChartValues.compositecharts[cnt].assignmentType.assignmentType}">
										 <table width="100%">
											<c:forEach items="${assignedActivities}" var="assignedActivity" varStatus="activityCount">
												<tr>
												 	<td width="20%"><fmt:formatDate pattern="MM/dd/yyyy" value="${assignedActivity.assignedDate}"/></td>
													<td width="20%"><c:out value="${assignedActivity.activity.activityDesc}"/></td>
													<td width="20%" align="right">
														<input type="number" name="activityScores" id="activityScore${activityCount.index}" value="${assignedActivity.score}">
														<input type="hidden" name="assignActivityIds" id="assignActivityId${activityCount.index}" value="${assignedActivity.assignActivityId}"/>
													</td>
													<td width="20%" align="right">
														${assignedActivity.score*compositeChartValues.compositecharts[cnt].pointspertest/100}
														<c:set var="sTotal" value="${sTotal+(assignedActivity.score*compositeChartValues.compositecharts[cnt].pointspertest/100)}"></c:set> 
													</td>
													<td width="20%">${academicGradeName}</td>
												 </tr>							
											</c:forEach>
										</table>
									</c:when>
									<c:when test="${'classworks' == compositeChartValues.compositecharts[cnt].gradeevents.eventName &&
									 'Participation' == compositeChartValues.compositecharts[cnt].assignmentType.assignmentType}">
										 <fmt:formatNumber var="pScore" type="number" minFractionDigits="2"  maxFractionDigits="2" value="${studentAttendance.statusCount/studentAttendance.totalCount*100}"/>
										 <fmt:formatNumber var="pPoints" type="number" maxFractionDigits="2" value="${(pScore*compositeChartValues.compositecharts[cnt].pointspertest)/100}"/> 
											 <table width="100%">
												<tr>
												 	<td width="20%"></td>
													<td width="20%"></td>
													<td width="20%" align="right"><input type="number" name="participationScore" id="participationScore" value="${pScore}"/></td>
													<td width="20%" align="right">${pPoints}
														<c:set var="sTotal" value="${sTotal+pPoints}"></c:set>
													</td>
													<td width="20%"></td>
												 </tr>	
											</table>
									</c:when>
									<c:when test="${'classworks' == compositeChartValues.compositecharts[cnt].gradeevents.eventName &&
									 'project work' == compositeChartValues.compositecharts[cnt].assignmentType.assignmentType}">
									 <fmt:formatNumber var="pjPoints" type="number" minFractionDigits="2"  maxFractionDigits="2" value="${projectScores.score*compositeChartValues.compositecharts[cnt].pointspertest/100}"/>
										 <table width="100%">
											<tr>
											 	<td width="20%"></td>
												<td width="20%"></td>
												<td width="20%" align="right"><input type="number" name="projectScore" id="projectScore" value="${projectScores.score}"/></td>
												<td width="20%" align="right">
													${pjPoints}
													<c:set var="sTotal" value="${sTotal+pjPoints}"></c:set>
												</td>
												<td width="20%">${projectScores.academicGrades.acedamicGradeName}</td>
											 </tr>	
										</table>
									</c:when>
									<c:otherwise>
										<table style="width:100%">
											<c:forEach items="${studentTestResults}" var="studentTests">
												<c:if test="${studentTests.assignment.usedFor == compositeChartValues.compositecharts[cnt].gradeevents.eventName &&
												 studentTests.assignment.assignmentType.assignmentType == compositeChartValues.compositecharts[cnt].assignmentType.assignmentType}">
													 <tr>
													 	<td width="20%"><fmt:formatDate pattern="MM/dd/yyyy" value="${studentTests.assignment.dateAssigned}"/></td>
														<td width="20%"><fmt:formatDate pattern="MM/dd/yyyy" value="${studentTests.submitdate}"/></td>
														<td width="20%" align="center">${studentTests.percentage}</td>
														<td width="20%" align="center">
															${studentTests.percentage*compositeChartValues.compositecharts[cnt].pointspertest/100}
															<c:set var="sTotal" value="${sTotal+(studentTests.percentage*compositeChartValues.compositecharts[cnt].pointspertest/100)}"></c:set>
														</td>
														<td width="20%" align="center">${studentTests.academicGrade.acedamicGradeName}</td>
													 </tr>
												 </c:if>							
											</c:forEach>
										</table>								
									</c:otherwise>
								</c:choose>
							</td>						
						</tr>
				 		<tr>
							<td colspan="7" align="right">
								<c:if test="${lastEventName != compositeChartValues.compositecharts[cnt+1].gradeevents.eventName}"> 
									Total Points 
									<fmt:formatNumber var="sTtl" type="number" minFractionDigits="2"  maxFractionDigits="2" value="${sTotal}"/>
									<c:out value="${sTtl}"></c:out>								
									<input type="hidden" name="subTotals" id="subTotal${tCount}" required="required" value="${sTtl}" />
									<c:set var="gTotal" value="${gTotal+sTtl}"></c:set>
									<c:set var="sTotal" value="0"></c:set>
									<br>
									<br>
								</c:if>
							</td>
							<td></td>
						</tr> 
					</c:forEach>
					<tr>
						<td colspan="7" align="right">
							Grand Total  <input type="hidden" name="grandTotal" id="grandTotal" required="required" value="${gTotal}" /> 
							<c:out value="${gTotal}"></c:out>
						</td>
						<td></td>
					</tr>
					<c:if test="${userReg.user.userType == 'teacher' }">
						<tr>
							<td colspan="8" align="center"><input type="submit" value="submit" /></td>
						</tr>
					</c:if>
				</table>
			</div>
		</form:form>
	 	<c:if test="${count eq 0}">
			<div align="center" style="text-decoration:blink; color: red; font-size: medium" >
                Teacher not yet designed composite chart for this class
            </div>
		</c:if> 
	</body>
</html>