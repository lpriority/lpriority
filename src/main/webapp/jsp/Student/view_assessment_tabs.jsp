<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page session="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<title>Insert title here</title>
</head>
<body>
	<table width="100%" height="29" border="0" align="right" cellpadding="0"
		cellspacing="0">
		<tr>
		<td width="100%" align="right">
			     <ul class="button-group">
					<c:choose>	
						<c:when test="${userReg.user.userType =='parent'}">
							<li><a href="childAssessments.htm" class="${(tab == 'view assessment tests')?'buttonSelect leftPill tooLongTitle ':'button leftPill tooLongTitle'}">View Assessments</a></li>
							<li><a href="childsAssessmentCompleted.htm" class="${(tab == 'viewAssessmentCompleted')?'buttonSelect rightPill tooLongTitle':'button rightPill tooLongTitle'}"> Assessments Completed </a></li>
						</c:when>
						<c:otherwise>
							<li><a href="goToStudentAssessmentsPage.htm" class="${(tab == 'view assessment tests')?'buttonSelect leftPill tooLongTitle ':'button leftPill tooLongTitle'}">View Assessments</a></li>
							<li><a href="assessmentsCompleted.htm" class="${(tab == 'viewAssessmentCompleted')?'buttonSelect tooLongTitle':'button tooLongTitle'}"> Assessments Completed </a></li>
							<li><a href="perGroupAssessmentsPage.htm" class="${(tab == 'viewGroupPerformance')?'buttonSelect rightPill tooLongTitle':'button rightPill tooLongTitle'}"> Student Group Work Area</a></li>
						</c:otherwise>
					</c:choose>	
				</ul>
			</td>
		</tr>
	</table>
</body>
</html>