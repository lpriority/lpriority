<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Review Student Gear Game </title>
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
		 	<th width='100' align='center'>S.No</th> 
			<th width='170' align='center'>Student Id</th>
			<th width='250' align='center'>Student Name</th>
			<th width='250' align='center'>Submit Status</th>
			<th width="250" align="center">Levels Completed</th>

		</table>
	</td></tr>
	<tr><td id='rows' width="100%" style="background:white;">
		<c:forEach items="${studentAssignmentStatusLt}" var="studentAssignmentStatus" varStatus="stdCnt">
		   <table align='center'><tr>
		   	 <td width='70' align='center' class='txtStyle'>${stdCnt.count}</td>
		   	 <td width='170' align='center' class='txtStyle'>${studentAssignmentStatus.student.studentId}</td>
		   	 <td width='250' align='center' class='txtStyle'>${studentAssignmentStatus.student.userRegistration.firstName} ${studentAssignmentStatus.student.userRegistration.lastName}</td>
 		     <c:choose>
			  <c:when test="${studentAssignmentStatus.status=='submitted'}">
  				<td width='250' align='center' class='txtStyle'>Yes</td>
  				<td width="250" align="center" class='txtStyle'><fmt:formatDate pattern="dd-MM-yyyy" value="${studentAssignmentStatus.submitdate}" /></td>
			  </c:when>
			  <c:otherwise>
			    <td width='250' align='center' class='txtStyle'>-</td>
			    <td width="250" align="center" class='txtStyle'></td>
			  </c:otherwise>
			 </c:choose>   		    
 		     
		   	</tr></table>
		  <div id="table${stdCnt.count}">		  
				<table width="100%" class="txtStyle" align="center" style="background:white;">
				   <tr>
				  	   <td width="20%" height="30" class="label" align="center" style="border:1px solid #00BCD4;">
					    Gear
					   </td>
					   <td width="20%" height="30" class="label" align="center" style="border:1px solid #00BCD4;">
					    Game Level
					   </td>
					   <td width="20%" height="30" class="label" align="center" style="border:1px solid #00BCD4;">
					    No Of Attempts
					   </td>
					   <td width="20%" height="30" class="label" align="center" style="border:1px solid #00BCD4;">
					    No Of InCorrects
					   </td>
					   <td width="20%" height="30" class="label" align="center" style="border:1px solid #00BCD4;">
					    Time Taken(mm:ss)
					   </td>
				   </tr>
				    <c:forEach items="${studsMathGameScoresLst}" var="studsGameScoresLst" varStatus="status">
					   <c:if test="${studsGameScoresLst.studentAssignmentStatus.studentAssignmentId eq studentAssignmentStatus.studentAssignmentId}">
					   	<tr>
					  	 	<td width="20%" height="20" style="color:black;font-weight: 500;font-size: 14px;" align="center" class="label" class="${stdCnt.count}"><c:out value="${studsGameScoresLst.mathGear.mathGear}" /></td>
					   	   	<td width="20%" style="color:black;font-weight: 500;font-size: 14px;" align="center" class="label" class="${stdCnt.count}"><c:out value="${studsGameScoresLst.gameLevel.level}" /></td>
						  	<td width="20%" style="color:black;font-weight: 500;font-size: 14px;" align="center" class="label" class="${stdCnt.count}">
						  	<c:choose>
						  	<c:when test="${studsGameScoresLst.noOfAttempts >0}">
						  	<c:out value="${studsGameScoresLst.noOfAttempts}" />
						  	</c:when>
						  	<c:otherwise>
						  	-
						  	</c:otherwise>
						  	</c:choose>
						  	</td>
						  	<td width="20%" style="color:black;font-weight: 500;font-size: 14px;" align="center" class="label" class="${stdCnt.count}"> 
						  	<c:choose>
						  	<c:when test="${studsGameScoresLst.noOfInCorrects >0}">
						  	<c:out value="${studsGameScoresLst.noOfInCorrects}" />
						  	</c:when>
						  	<c:otherwise>
						  	-
						  	</c:otherwise>
						  	</c:choose>
						  	</td>
						  	<td width="20%" style="color:black;font-weight: 500;font-size: 14px;" align="center" class="label" class="${stdCnt.count}"><c:out value="${studsGameScoresLst.timeTaken}" /> </td>
						   </tr>
					  </c:if>			   			
		   	 	</c:forEach>
			   </table>
	   	</div>
	</c:forEach>
	</td></tr>
</table>

</body>
</html>