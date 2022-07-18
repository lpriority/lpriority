<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="java.util.*,java.text.*" %>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Learning Priority|Teacher Reports</title>
<script type="text/javascript" src="resources/javascript/TeacherJs/teacher_self_evaluation.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table class="des" border=0 width="50%">
<tr><td>
<div class="Divheads"><table align="center">
   <tr><td class="headsColor">Self Evaluation</td></tr></table></div>
   <div class="DivContents"><table>
                     <c:choose> 
                       <c:when test="${rcount == 0}">
                         <tr>
                         <td align="center">No Report Available.</td>
                        </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${TRList}" var="tp" varStatus="count">
                             <tr>
	                             <td width="40%" style="padding-top:1em;" class="tabtxt">${count.count}.&nbsp;<c:out value="${tp.teacherPerformances.performance}" /></td>
	                             <td style="padding-left: 10em;">:</td>
	                             <td style="padding-left: 4em;padding-top:1em" width="20%"><c:out value="${tp.choosenOption}" /></td>
	                             <td style="padding-top:1em;">- <c:out value="${tp.comments}" /></td>
	                             <td></td>  
                             </tr>
                        </c:forEach>
                     </c:otherwise>
                    </c:choose>
                   </table></div></td></tr></table>
</body>
</html>