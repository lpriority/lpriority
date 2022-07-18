<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Math Analysis Student Details </title>
</head>
<body>
	<table width="80%" class="des" align="center">
		<tr class="Divheads">
		   <td width="40%" height="40" align="center">Fraction : ${mathQuiz.fraction}</td>
		   <td width="60%" align="center">Math Quiz Blank Questions</td>
		</tr>
		<tr>
		   <td colspan="2">
			   <table width="100%" class="label" align="center" style="color:black;">
				   <tr>
					   <td width="40%" height="40" align="center" style="border:1px solid #00BCD4;">
					    <table width="100%">
							<tr>
								<td width="10%" align="center">S.No</td>
								<td width="30%" align="center">Student Id</td>
								<td width="60%" align="center">Student Name</td>
							</tr>
						</table>	
					   </td>
					   <td width="60%" align="center" style="border:1px solid #00BCD4;">
					     <table width="100%"><tr>
					    <c:forEach items="${mathQuizQuestionsLt}" var="mathQuizQuestions">
						    <c:if test="${mathQuizQuestions.isBlank eq true}"> 
						 	<td width="20%" align="center">${mathQuizQuestions.mathConversionTypes.conversionType}</td>
						 	</c:if>
						 </c:forEach>
						 </tr></table>
					   </td>
				   </tr>
			   </table>
		   </td>
		</tr>
 <c:forEach items="${studentAssignmentStatusLt}" var="studentAssignmentStatus" varStatus="status"> 
 		<tr><td colspan="2">
			   <table width="100%" class="txtStyle" align="center">
				   <tr>
					   <td width="40%" height="30" align="center" style="border:1px solid #00BCD4;">
					    <table width="100%">
							<tr>
								<td width="10%" align="center">${status.count}</td>
								<td width="30%" align="center">${studentAssignmentStatus.student.studentId}</td>
								<td width="60%" align="center">${studentAssignmentStatus.student.userRegistration.firstName} ${studentAssignmentStatus.student.userRegistration.lastName}</td>
							</tr>
						</table>	
					   </td>
					   <td width="60%" align="center" style="border:1px solid #00BCD4;">
					     <table width="100%" id="resultDiv"><tr>
					    <c:forEach items="${studentMathAssessMarksLts}" var="studentMathAssessMarks">
						    <c:if test="${studentMathAssessMarks.studentAssignmentStatus.student.studentId eq studentAssignmentStatus.student.studentId}">
						         <c:set var="conType" value="${fn:replace(studentMathAssessMarks.mathQuizQuestions.mathConversionTypes.conversionType,' ', '')}"/> 
						         <c:if test="${studentMathAssessMarks.mark eq 0}"> 
							 		<td width="20%" align="center" class="${conType}0"><i class="fa fa-times" style="font-size: 20px;color:#CD0000;"></i></td>
							 	 </c:if>
							 	 <c:if test="${studentMathAssessMarks.mark eq 1}"> 
							 		<td width="20%" align="center" class="${conType}1"><i class="fa fa-check" style="font-size: 20px;color:#427b00;"></i></td>
							 	 </c:if>
						 	</c:if>
						 </c:forEach>
						 </tr></table> 
					   </td>
				   </tr>
			   </table>
		   </td>
	</tr>
 </c:forEach> 
 <tr><td colspan="2">
  <table width="100%" class="txtStyle" align="center">
				   <tr>
					   <td width="40%" height="30" class="label" align="center" style="border:1px solid #00BCD4;">
					    Total Summary
					   </td>
					   <td width="60%" align="center" style="border:1px solid #00BCD4;">
					     <table width="100%"><tr>
					     <c:forEach items="${mathQuizQuestionsLt}" var="mathQuizQuestions">
						   <c:if test="${mathQuizQuestions.isBlank eq true}"> 
							    <c:set var="conversionType" value="${fn:replace(mathQuizQuestions.mathConversionTypes.conversionType,' ', '')}"/>
							    <td width="20%" align="center"><span style="font-size: 14px;color:#427b00;">Correct : </span><span id="${conversionType}1"></span>&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 14px;color:#CD0000;">Wrong : </span><span id="${conversionType}0"></span></td>
							 	 <script>
							 	 var conversionType = '${mathQuizQuestions.mathConversionTypes.conversionType}';
							 	     conversionType = conversionType.replace(/\s/g, "");
								 var wrongCount = $('.'+conversionType+'0').length;
								 var correctCount = $('.'+conversionType+'1').length;
								 $("#"+conversionType+"0").html(wrongCount); 
								 $("#"+conversionType+"1").html(correctCount); 
								 </script>
						 	</c:if>
						 </c:forEach>
						 </tr></table> 
					   </td>
				   </tr>
			   </table>

 </td></tr>
</table>
</body>
</html>