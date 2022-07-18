<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="resources/javascript/jplayer/css/jplayer.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="resources/javascript/CommonValidation.js"></script>
<script src="resources/javascript/TeacherJs/grade_assessments.js"></script>
<script src="resources/javascript/Student/rflp_test.js"></script>
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<!-- <link rel="stylesheet"
	href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css"> -->
<script src="resources/javascript/contextmenu/jquery.contextMenu.js"></script>
<script src="resources/javascript/contextmenu/jquery.ui.position.js"></script>
<link rel="stylesheet"
	href="resources/css/contextmenu/jquery.contextMenu.css">
<link rel="stylesheet"
	href="resources/css/icons/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="resources/css/icons/foundation-icons/foundation-icons.css">
<link rel="stylesheet"
	href="resources/css/icons/ionicons/css/ionicons.min.css">
<link rel="stylesheet"
	href="resources/css/icons/PICOL-font/css/picol.css">
<link rel="stylesheet"
	href="resources/css/icons/PICOL-font/css/animation.css">
<link href="resources/css/common_buttons.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/setColor.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/TeacherJs/benchmark_results.js"></script>
<style type="text/css">
.ui-dialog>.ui-widget-content {
	background: white;
}

.ui-widget {
	font-family: Georgia, Times, 'Times New Roman', serif;
	font-size: 1em;
}

.destable {
	border-style: solid;
	border-width: 0.5px;
	border: 0.5px solid grey;
	border-collapse: collapse;
	cell-padding: 0px;
	overflow-x: scroll;
	text-decoration:none;
}

.aaa {
	border-style: solid;
	border-width: 0.5px;
	border-bottom-color: #AEB6BF;
	border-right-color: #AEB6BF;
}
 .tdBor { 
     border: 1px solid grey; */
} 

</style>
<script type="text/javascript">
$(document).ready(function(){	
	var errorsListSize = document.getElementById('errorsListSize').value;
	var studentListSize = document.getElementById('studentListSize').value;	
	for(var index1 = 0 ; index1<errorsListSize; index1++){
		var ttlValue = 0;
		for(var index2 = 0 ; index2<studentListSize; index2++){
			ttlValue = ttlValue +  parseInt(document.getElementById('score'+index1+index2).value);
		}
		document.getElementById('ttl'+index1).append(ttlValue);		
	}
});
</script>
</head>
<body>
	<input type="hidden" name="errorsListSize" id="errorsListSize"
		value="${fn:length(totErrors)}">
	<input type="hidden" name="studentListSize" id="studentListSize"
		value="${fn:length(studentList)}">
	<div align="center" class="sub-title">Fluency Error Words/Item
		analysis</div>
	<br>
	<table align="center">
		<c:set var="i" value="1" />
		<c:forEach items="${errorTypes}" var="et">
			<c:set var="stat" value=""></c:set>
			<tr>
				<c:if test="${type==i}">
					<c:set var="stat" value="checked"></c:set>
				</c:if>
				<td><input type="checkbox" id="err${i}"
					onClick="getAllStudentFluencyResults(${assignmentId},${i})" ${stat} /><font
					color="${et.errorColor}">${et.errorType}</font></td>
			</tr>
			<c:set var="i" value="${i+1}"></c:set>
		</c:forEach>
	</table>
	<c:choose>
		<c:when test="${fn:length(totErrors) eq 0}">
			<div style="padding-top: 3em" align="center">There is no
				data.</div>
		</c:when>
		<c:otherwise>
			<br>
			<table align="center" class="destable">
				<form:form name="StudentBenchmarkForm"
					modelAttribute="assignmentQuestions">

					<tr>
						<td class="tdBor"><table height="100%" class="destable" style="overflow-x: scroll;">
								<tr style="height: 50px">
									<td class="hl" bgColor="#CCCC00">StudentName</td>
								</tr>
								<c:forEach items="${studentList}" var="sl">
									<tr class="aaa" style="height: 50px">
										<td class="tdBor"><c:out
												value="${sl.student.userRegistration.firstName}"></c:out>&nbsp;<c:out
												value="${sl.student.userRegistration.lastName}"></c:out></td>
									</tr>
								</c:forEach>
								<tr style="height: 50px">
									<td class="tdBor">Total</td>
								</tr>
							</table></td>
				
					<td><table  height="100%" valign="top" style="overflow-x: scroll;" class="destable">
							<tr class="" style="height: 50px">
								<c:set var="i" value="0"></c:set>
								<c:if test="${type eq 1 || type eq 2 || type eq 5}">
									<c:forEach items="${totErrors}" var="ql" varStatus="index">
										<td align="center" style="padding-left:1em;padding-right:1em;" bgColor="#CCCC00" class="tdBor"><c:out value="${ql.errorWord}"></c:out></td>
									</c:forEach>
								</c:if>
								<c:if test="${type eq 4 || type eq 3}">
									<c:forEach items="${totErrors}" var="ql" varStatus="index">
										<td align="center" style="padding-left:1em;padding-right:1em;" bgColor="#CCCC00" class="tdBor"><c:out value="${ql.addedWord}"></c:out></td>
									</c:forEach>
								</c:if>
							</tr>

							<c:set var="n" value="0"></c:set>

							<c:forEach var="err" items="${errorsList}" varStatus="index1">
								<tr class="aaa" style="height: 50px;">
									<c:forEach var="co" items="${err}" varStatus="index2">
										<c:choose>
											<c:when test="${co==2}">
												<c:set var="color" value="#0000FF"></c:set>
												<c:set var="lett" value="X"></c:set>
												<c:set var="score" value="1"></c:set>
											</c:when>
											<c:when test="${co==1}">
												<c:set var="color" value="#FF0000"></c:set>
												<c:set var="lett" value="X"></c:set>
												<c:set var="score" value="1"></c:set>
											</c:when>
											<c:when test="${co==3}">
												<c:set var="color" value="#008000"></c:set>
												<c:set var="lett" value="X"></c:set>
												<c:set var="score" value="1"></c:set>
											</c:when>
											<c:when test="${co==4}">
												<c:set var="color" value="#FF00FF"></c:set>
												<c:set var="lett" value="X"></c:set>
												<c:set var="score" value="1"></c:set>
											</c:when>
											<c:when test="${co==5}">
												<c:set var="color" value="#642EFE"></c:set>
												<c:set var="lett" value="X"></c:set>
												<c:set var="score" value="1"></c:set>
											</c:when>
											<c:otherwise>
												<c:set var="color" value="#000000"></c:set>
												<c:set var="lett" value=""></c:set>
												<c:set var="score" value="0"></c:set>
											</c:otherwise>
										</c:choose>
										<td align="center" valign="middle" class="tdBor"><font color="${color}"><c:out
													value="${lett}"></c:out></font> <input type="hidden" name="score"
											id="score${index2.index}${index1.index}" value="${score}">
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
							<c:forEach items="${totErrors}" var="te" varStatus="index">
								<td align="center" valign="middle"
									style="height: 50px;" class="tdBor">
									<div id="ttl${index.index}"></div>
								</td>
							</c:forEach>
						</table></td>
					</tr>
				</form:form>
			</table>
		</c:otherwise>
	</c:choose>
</body>
</html>
