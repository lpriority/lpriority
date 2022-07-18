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
<script>
  $( "#rows" ).accordion({
      collapsible: true,
      active: false,
      activate:function(event, ui ){
  		setFooterHeight();
    }
    });
</script>
</head>
<body>
	<table border=0 class='des' width="60%">
	<tr><td class='Divheads' width="100%">
		<table align='center' width="100%">
		 <tr>
		 	<th width='40' align='center'>S.No</th> 
			<th width='60' align='center'>Student Id</th>
			<th width='120' align='center'>Student Name</th>
			<th width='60' align='center'>Percentage</th>
			<th width='60' align='center'>Academic Grade</th>
		</table>
	</td></tr>
	<tr><td id='rows' width="100%" style="background:white;">
		<c:forEach items="${studentAssignmentStatusLt}" var="studentAssignmentStatus" varStatus="stdCnt">
		   <table align='center'><tr>
		   	 <td width='50' align='center' class='txtStyle'>${stdCnt.count}</td>
		   	 <td width='170' align='center' class='txtStyle'>${studentAssignmentStatus.student.studentId}</td>
		   	 <td width='250' align='center' class='txtStyle'>${studentAssignmentStatus.student.userRegistration.firstName} ${studentAssignmentStatus.student.userRegistration.lastName}</td>
		   	 <td width='200' align='center' class='txtStyle'>${studentAssignmentStatus.percentage}</td>
		   	 <td width='140' align='center' class='txtStyle'>${studentAssignmentStatus.academicGrade.acedamicGradeName}</td>
		   	</tr></table>
		  <div id="table${stdCnt.count}">
		   <c:forEach items="${mathQuizLt}" var="mathQuiz" varStatus="count">
		   	<table align='center' width="100%" style="font-size: 12px;border:1px solid #00BCD4;border-collapse:collapse;background:white;" border="1">
			   	<tr>
				   	<td width="10%" style="color:black;" align="center" class="label">
				   	${mathQuiz.fraction}
				   	</td>
			   		<td width="90%" style="background:white;">
			   			<table align='center' width="100%">
			   				<tr><td style="background:#00d9f5;border-bottom: 1pt solid #0097aa;"><table align='center' width="100%" class="label" style="color:black;"><tr>
							   	<c:forEach items="${mathQuizQuestionsLts}" var="mathQuizQuestionsLt">
							   	 	<c:forEach items="${mathQuizQuestionsLt}" var="mathQuizQuestions">
							   	 	  <c:if test="${mathQuiz.mathQuizId eq mathQuizQuestions.mathQuiz.mathQuizId}"> 
									    <c:if test="${mathQuizQuestions.isBlank eq true}"> 
									 	<td width="25%" align="center" style="font-weight:500;">${mathQuizQuestions.mathConversionTypes.conversionType}</td>
									 	</c:if>
									 </c:if>	
									</c:forEach>	
								</c:forEach>
							</tr></table>
							</td></tr>
							<tr><td><table align='center' width="100%"><tr>
							<c:forEach items="${studentMathAssessMarksLts}" var="studentMathAssessMarksLt" varStatus="status">
						   		<c:forEach items="${studentMathAssessMarksLt}" var="studentMathAssessMarks" varStatus="status">
						   			<c:if test="${studentMathAssessMarks.studentAssignmentStatus.student.studentId eq studentAssignmentStatus.student.studentId && studentMathAssessMarks.mathQuizQuestions.mathQuiz.mathQuizId eq mathQuiz.mathQuizId}"> 
							   		<c:if test="${studentMathAssessMarks.mathQuizQuestions.isBlank eq true}"> 
							   		 	<c:if test="${studentMathAssessMarks.mark eq 0}"> 
									 		<td width="25%" align="center" class="${stdCnt.count}0" style="font-size: 12px;font-weight:bold;color:#CD0000;">${studentMathAssessMarks.answer} </td>
									 	 </c:if>
									 	 <c:if test="${studentMathAssessMarks.mark eq 1}"> 
									 		<td width="25%" align="center" class="${stdCnt.count}1"  style="font-size: 12px;font-weight:bold;color:#427b00;">${studentMathAssessMarks.answer}</td>
									 	 </c:if>
							   		</c:if>
						   		</c:if>
					   		 </c:forEach>
					      </c:forEach>
			   			</tr></table>
			   		</td>
		   		</tr>
		   	</table></td></tr>
		   	</table>
		   	</c:forEach>
				<table width="100%" class="txtStyle" align="center" style="background:white;">
				   <tr>
					   <td width="10%" height="30" class="label" align="center" style="border:1px solid #00BCD4;">
					    Result
					   </td>
					   <td width="90%" align="center" style="border:1px solid #00BCD4;">
					     <table width="100%" align="center">
						     <tr>
						      <td width="20%" align="center"  style="font-size: 14px;font-weight:bold;"><span style="color:#427b00;">Correct : </span><span id="${stdCnt.count}1"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:#CD0000;">Wrong : </span><span id="${stdCnt.count}0"></span></td>
						       <script type="text/javascript">
								 	 var count = '${stdCnt.count}';
									 var wrongCount = $('#table'+count).find('.'+count+'0').length;
									 var correctCount = $('#table'+count).find('.'+count+'1').length;
									 $("#"+count+"0").html(wrongCount); 
									 $("#"+count+"1").html(correctCount); 
							 </script>
					   </tr></table> 
					  </td>
				   </tr>
			   </table>
	   	</div>
	</c:forEach>
	</td></tr>
</table>

</body>
</html>