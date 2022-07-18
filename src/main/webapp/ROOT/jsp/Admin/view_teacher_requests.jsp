<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<%@ page import="com.lp.utils.WebKeys"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Teacher Requests</title>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="viewTeacherRequests" style="visibility:hidden;">
<table width="100%">
	 <tr><td colspan="" width="100%"> 
       <table width="100%" class="title-pad heading-up" border="0">
		<tr>
			<td class="sub-title title-border" height="40" align="left" > View Teacher Requests</td>
		</tr>
		</table>
	 </td></tr>	</table><br><br>
	 <table border=0 class="des content-box" align="center" style="width:60%;margin-left:25em;" >
	 <tr><td> 
	 <div class="Divheads">
	    <table>
		     <tr>
	              <th width="160" align="center">Grade</th>
	              <th width="150" align="center">Class</th>
			      <th width="200" align="center">Teacher</th>
			      <th width="350" align="center">Reply Action</th>
		      </tr></table></div>
		      <div class="DivContents">
		     <table align="center" width="100%" class="txtStyle">
		      <tr><td colspan="4"  align="center">&nbsp;</td></tr>
		 <c:choose>
			 <c:when test="${fn:length(teacherRequestLt) > 0}">
		   	  <c:forEach items="${teacherRequestLt}" var="teacherSubjects" varStatus="loop">  
		      <tr>
		      	<td  width="160" align="center"> ${teacherSubjects.grade.masterGrades.gradeName} </td>
		        <td  width="160" align="center"> ${teacherSubjects.studentClass.className} </td>
		        <td  width="200" align="center"> 
		        	<c:if test="${teacherSubjects.teacher.userRegistration.title != null && teacherSubjects.teacher.userRegistration.title!=''}">
		        		${teacherSubjects.teacher.userRegistration.title}&nbsp;.
		        	</c:if>
		        	
		        	${teacherSubjects.teacher.userRegistration.firstName}&nbsp;
		        	${teacherSubjects.teacher.userRegistration.lastName}
		        </td>
		      <input type="hidden" id ="${teacherSubjects.teacherSubjectId}" name="${teacherSubjects.teacherSubjectId}" value="${teacherSubjects.teacher.userRegistration.title} ${teacherSubjects.teacher.userRegistration.firstName} ${teacherSubjects.teacher.userRegistration.lastName}">
		      <input type="hidden" name="gradeId${teacherSubjects.teacherSubjectId}" id="gradeId${teacherSubjects.teacherSubjectId}" value="${teacherSubjects.grade.gradeId}"></input>
		      <input type="hidden" name="classId${teacherSubjects.teacherSubjectId}" id="classId${teacherSubjects.teacherSubjectId}" value="${teacherSubjects.studentClass.classId}"></input>
		      <input type="hidden" name="teacherId${teacherSubjects.teacherSubjectId}" id="teacherId${teacherSubjects.teacherSubjectId}" value="${teacherSubjects.teacher.teacherId}"></input> 
		      	
		       	<td align='center' valign='middle' width="350">
					<input type='checkbox' class="checkbox-design" id='${teacherSubjects.teacherSubjectId}-<%=WebKeys.ACCEPTED%>' name='${teacherSubjects.teacherSubjectId}-<%=WebKeys.ACCEPTED%>' onClick="setAction(this)"/><label for="${teacherSubjects.teacherSubjectId}-<%=WebKeys.ACCEPTED%>" class="checkbox-label-design">Accept</label>&nbsp;&nbsp;
				
				
					<input type='checkbox' class="checkbox-design" id ='${teacherSubjects.teacherSubjectId}-<%=WebKeys.DECLINED%>' name='${teacherSubjects.teacherSubjectId}-<%=WebKeys.DECLINED%>' onClick="setAction(this)"/><label for="${teacherSubjects.teacherSubjectId}-<%=WebKeys.DECLINED%>" class="checkbox-label-design">Decline</label>
				</td>
		      </tr>
		    </c:forEach>
		    </c:when>
		    <c:otherwise>
		     	<tr><td colspan="4"  align="center" class="status-message">No request found</td></tr>
		    </c:otherwise>
		   </c:choose> 
		    </table></div></td></tr></table>
	 <table>
	<tr><td height=5 colSpan=2><div align="right"></div></td></tr>
	</table>
 </div> 
</body>
</html>