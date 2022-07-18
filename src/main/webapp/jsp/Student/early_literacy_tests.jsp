<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="resources/javascript/Student/early_literacy_tests.js"></script>
<title>Insert title here</title>
</head>
<body>
<input type="hidden" id="studentId" name="studentId" value="${studentId}">
 <table align="left"  width="100%" border=0>
  <tr><td height=100 colSpan=10></td></tr> 
	    <tr><th align="center"><font color="black" size="6"><b> Early Literacy Tests</b></font></th></tr>
	    <tr><td height=70 colSpan=30></td></tr> 
      	<tr><td  valign="middle">
      	    <table width="80%" cellpadding='3' cellspacing='3'>
      	    		<tr  >
						<th align="center"> Assignment Title </th>
						<th align="center"> Due Date </th>
						<th align="center"> Test Type </th>
						<th align="center"> </th>
					</tr>  
					 <tr><td height=15 colSpan=10></td></tr> 
	      	     <c:forEach items="${K1TestMarksLt}" var="K1TestMarks" varStatus="status">
    	        	<tr >
						<td  align="center"> ${K1TestMarks.k1Test.title} </td>
						<td  align="center"> ${K1TestMarks.k1Test.dueDate} </td>
						  <c:choose>
		     			 	<c:when test="${K1TestMarks.k1Test.testType eq 'Y'}">
		     			 		<td  align="center"> Real </td>
		     			 	</c:when>
		     			 	<c:when test="${K1TestMarks.k1Test.testType eq 'N'}">
		     			 		<td  align="center"> Not-Real </td>
		     			 	</c:when>
				     	</c:choose>
				     	<td align="center"> <input type="button" id="${status.count}" name="${status.count}" value="Go For Assignment" onclick="goForAssignment('#dialog${status.count}')" /> </td>
					</tr> 
      			<div id="dialog${status.count}"  style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;display:none;" title=""> <iframe frameborder="0" scrolling="no" width="100%" height="100%" src="goForAssignment.htm?setId=${K1TestMarks.k1Test.setId}&set=${K1TestMarks.k1Test.sets}&setType=${K1TestMarks.k1Test.setType}&k1TestId=${K1TestMarks.k1TestId}&studentId=${studentId}&dialogDivId=dialog${status.count}"></iframe></div> 
				</c:forEach>
      	     </table>
      	 </td></tr>  	
      </table>
    </body>
</html>