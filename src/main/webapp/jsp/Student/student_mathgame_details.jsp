<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Gear Game Details </title>

</head>
<body><table><tr><td><br>&nbsp;<br></td></tr></table>
	<table border=0 class='des' width="60%" align="center">
	<tr><td class='Divheads' width="100%">
		<table align='center' width="100%">
		 <tr>
		 			  <td width="20%" height="30" align="center">
					    Game Level
					   </td>
					   <td width="20%" height="30" align="center">
					    Game Level
					   </td>
					   <td width="20%" height="30" align="center">
					    No Of Attempts
					   </td>
					   <td width="20%" height="30" align="center">
					    No Of InCorrects
					   </td>
					   <td width="20%" height="30" align="center">
					    Time Taken(mm:ss)
					   </td>

				   </tr>
		</table>
	</td></tr>
	<tr><td class="divContents" width="100%"><table align='center' width="100%">
				    <c:forEach items="${studsMathGameScoresLst}" var="studsGameScoresLst" varStatus="status">
			   	
			   	<tr>
			   		<td width="20%" style="color:black;" align="center" class="label" ><c:out value="${studsGameScoresLst.mathGear.mathGear}" /></td>
				   	<td width="20%" style="color:black;" align="center" class="label" ><c:out value="${studsGameScoresLst.gameLevel.level}" /></td>
				  	<td width="20%" style="color:black;" align="center" class="label">
				  	<c:choose>
				  	<c:when test="${studsGameScoresLst.noOfAttempts >0}">
				  	<c:out value="${studsGameScoresLst.noOfAttempts}" />
				  	</c:when>
				  	<c:otherwise>
				  	-
				  	</c:otherwise>
				  	</c:choose>
				  	</td>
				  	<td width="20%" style="color:black;" align="center" class="label">
				  		<c:choose>
				  	<c:when test="${studsGameScoresLst.noOfInCorrects >0}">
				  	<c:out value="${studsGameScoresLst.noOfInCorrects}" />
				  	</c:when>
				  	<c:otherwise>
				  	-
				  	</c:otherwise>
				  	</c:choose>
				  	 </td>
				  	<td width="20%" style="color:black;" align="center" class="label"><c:out value="${studsGameScoresLst.timeTaken}" /> </td>
				   </tr>
			   
			   			
		   	 	</c:forEach>
			   </table>
	 
	
	</td></tr>
</table>

</body>
</html>