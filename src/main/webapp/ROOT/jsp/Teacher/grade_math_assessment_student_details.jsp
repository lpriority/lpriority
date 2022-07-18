<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<c:if test="${loginFrom eq 'teacher'}">
	<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
</c:if>
<html>
<title>Grade Math Assessment</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../CommonJsp/include_resources.jsp" %>
<script src="resources/javascript/TeacherJs/math_assessment.js"></script>
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css">
<style>
.title-txt{
    font-family: Roboto,-apple-system,BlinkMacSystemFont,"Helvetica Neue","Segoe UI","Oxygen","Ubuntu","Cantarell","Open Sans",sans-serif;
    font-weight: 500;
    font-size: 15px;
    text-shadow: 0 1px 1px #a6afaf;
    color:#04345a; 
    white-space: nowrap;
}
.blank-button-cls{
	background:linear-gradient(to bottom,rgb(244, 244, 244) 1%, rgb(255, 255, 255) 48%, rgb(204, 209, 212) 97%, rgb(173, 173, 173) 100%);
	color: black;
	padding:12px;
	font-weight:bold;
	font-size:15px;
	border: 1px solid #b9c1c5;
	text-shadow: 0 1px 2px rgb(195, 201, 203);
	white-space: nowrap;
	min-height:18px;
}
.correct-button-cls{
	background: -webkit-gradient( linear, left top, left bottom, color-stop(0.05,#a8b900), color-stop(1,#427b00) );
	color: white;
	padding:12px;
	font-weight:bold;
	font-size:15px;
	border: 1px solid #b9c1c5;
	text-shadow:0 1px 1px rgb(49, 49, 49);
	white-space: nowrap;
	min-height:18px;
}

.wrong-button-cls{
	background: -webkit-gradient( linear, left top, left bottom, color-stop(0.05,#ff3d00), color-stop(1,#bf1408) );
	color: white;
	padding:12px;
	font-weight:bold;
	font-size:15px;
	border: 1px solid #b9c1c5;
	text-shadow:0 1px 1px rgb(49, 49, 49);
	white-space: nowrap;
	min-height:18px;
}
</style>
</head>
<body>
 <h3 align="center">Student Name : ${sAssignmentStatus.student.userRegistration.firstName } ${sAssignmentStatus.student.userRegistration.lastName }</h3>
<input type="hidden" name="studentAssignmentId" id="studentAssignmentId" value="${studentAssignmentId}" />
<input type="hidden" name="status" id="status" value="${status}" />
<input type="hidden" name="gradedStatus" id="gradedStatus" value="${gradedStatus}" />
<input type="hidden" name="studentRegId" id="studentRegId" value="${studentRegId}" />
 <c:set var="inc" value="0" scope="page" />
 <table width='100%' style='padding-top:2em;'> <tr width='100%'><td>
 <c:if test="${len > 0}">
 <c:forEach begin="0" end="${len-1}" varStatus="loop"> 
 	  <table width='100%' style='' id="table${loop.index}">
	 		 <tr width='100%' style='height:3em;'>
				<td width='10%' align='center' class='title-txt'>Simplified Fraction</td>
				<td width='10%' align='center' class='title-txt'>Word</td>
				<td width='10%' align='center' class='title-txt'>Decimal</td>
				<td width='10%' align='center' class='title-txt'>Percentage</td>
				<td width='10%' align='center' class='title-txt'>Rounded Percentage</td>
				<td width='10%' align='center' class='title-txt'>Eq. Fraction 1</td>
				<td width='10%' align='center' class='title-txt'>Eq. Fraction 2</td>
				<td width='10%' align='center' class='title-txt'>Score</td>
			 </tr>
			<tr width='100%'>
		   <c:set var="cnt" value="0" scope="page" /> 
		   <c:forEach items="${mathQuizQuestionsLts[loop.index]}" var="mathQuizQuestions"  varStatus="count">
				 <td width='10%' align='center'><div name='id${inc}' id='id${inc}'
				  <c:choose>
  				   <c:when test="${mathQuizQuestions.isBlank eq true}">
					    <c:if test="${studentMathAssessMarksLts[loop.index][cnt].mark eq 0}">
					  		 class=wrong-button-cls 
					  		 <c:if test="${loginFrom eq 'teacher'}">
					  		 	onclick=udpateMarkStatus(${inc},${loop.index})
					  		 </c:if>
					   </c:if>
					    <c:if test="${studentMathAssessMarksLts[loop.index][cnt].mark eq 1}">
					  		 class=correct-button-cls 
					  		  <c:if test="${loginFrom eq 'teacher'}">
					  		  	 onclick=udpateMarkStatus(${inc},${loop.index})
					  		  </c:if>
					   </c:if>
				    </c:when>
				   <c:otherwise>
				   		 class=blank-button-cls
				   </c:otherwise>
				   </c:choose>  
				     >
			     <c:choose>
  				    <c:when test="${mathQuizQuestions.isBlank eq true}">
					  <input type=hidden id="studentMathAssessMarksId${inc}" name="studentMathAssessMarksId${inc}"value="${studentMathAssessMarksLts[loop.index][cnt].studentMathAssessMarksId}">
					  <input type=hidden id="mark${inc}" name="mark${inc}"value="${studentMathAssessMarksLts[loop.index][cnt].mark}">
				    </c:when>
			     </c:choose>
			      ${mathQuizQuestions.isBlank eq true ?  studentMathAssessMarksLts[loop.index][cnt].answer  : mathQuizQuestions.actualAnswer}
			      </div></td>
			      <c:if test="${mathQuizQuestions.isBlank eq true}">
			  		 <c:set var="cnt" value="${cnt + 1}" scope="page"/>
			      </c:if>
			    <c:set var="inc" value="${inc + 1}" scope="page"/>

			</c:forEach>
			<script type="text/javascript">
			  $(function () {
			    	var count = "<c:out value='${loop.index}'/>";
			    	getScore(count);
				});
			 </script>
		   <td width='10%' align="center"><span id="score${loop.index}" class="tabtxt" style="font-size:20px;"></span></td>
		  </tr>
	 </table>
    <br><br> 
   </c:forEach>
    </c:if>
   </td></tr>
    <tr><td width="30%" align="left" class="tabtxt" style="padding-left:3em;font-size:20px;">Total Score: &nbsp;&nbsp;&nbsp;&nbsp;<span id="totscore" style=""></span></td></tr>
    <tr><td width="30%" align="left" class="tabtxt" style="padding-left:3em;font-size:20px;">Percentage: &nbsp;&nbsp;&nbsp;&nbsp;<span id="percentage" style="">${sAssignmentStatus.percentage }</span></td></tr>
    <tr><td width="30%" align="left" class="tabtxt" style="padding-left:3em;font-size:20px;">Academic Grade: &nbsp;&nbsp;&nbsp;&nbsp;<span id="academicGrade" style="">${sAssignmentStatus.academicGrade.acedamicGradeName }</span></td></tr>
    <tr><td width="100%" colspan="10" align="center"><div id="result" class="status-message"></div></td></tr>
    </table>
    <script type="text/javascript">
	  $(function () {
	      getTotScore();
	  });
	</script>
</body>
</html>