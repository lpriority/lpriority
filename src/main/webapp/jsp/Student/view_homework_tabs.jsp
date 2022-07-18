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
			<td width="100%" valign="bottom" align="right">
				<nav id="primary_nav_wrap">
			     <ul class="button-group">
					<c:choose>	
						<c:when test="${userReg.user.userType =='parent'}">
							<li><a href="getChildCurrentHomeworks.htm" class="${(tab == 'current homeworks')?'buttonSelect leftPill tooLongTitle ':'button leftPill tooLongTitle'}">Current Homework </a></li>
							<li><a href="childHomeworkReports.htm" class="${(tab == 'homeReports')?'buttonSelect rightPill tooLongTitle':'button rightPill tooLongTitle'}"> Homework Report </a></li>
						</c:when>
						<c:otherwise>
							<li><a href="goToStudentHomeworksPage.htm" class="${(tab == 'current homeworks')?'buttonSelect  leftPill tooLongTitle ':'button leftPill tooLongTitle'}">Current Homework </a></li>
							<li><a href="goToStudentDueHomeworksPage.htm" class="${(tab == 'due homeworks')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Homework Past Due</a></li>
							<li><a href="studentEvaluateHomeworks.htm" class="${(tab == 'homeReports')?'buttonSelect rightPill tooLongTitle':'button rightPill tooLongTitle'}"> Homework Report </a></li>
						</c:otherwise>
					</c:choose>	
				</ul>
				</nav>
			</td>
		</tr>
	</table>
</body>
</html>