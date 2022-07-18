<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AddStudentsToClassList</title>
<script type="text/javascript" src="resources/javascript/admin/add_students_to_class.js"></script>
<%@ include file="../CommonJsp/include_resources.jsp" %>
</head>
<body>

		<table name="classes" border="0" cellpadding="0" cellspacing="0"
			vspace="0" width="439" hspace="0" align="center" class="txtStyle">

			<tbody>
				<tr><td>&nbsp;</td></tr>
				<tr><td colspan="8" style="padding-left:2em"><span class="tabtxt">Teacher Name :</span> ${sectionTeacher}</td></tr>
				<tr><td>&nbsp;</td></tr>
				<tr><td colspan="6" style="padding-left:2em"><h4> Add/Remove Students To/from the class</h4></td></tr>
			
				<tr align="center"><td colspan="3" class='status-message'><label id=returnMessage style="visibility:visible;"></label></td></tr>
				</table>
			<table class="des" border=0><tr><td><div class="Divheads"><table>
				<tr >
						<td  align="center" width="150">Student Name</td>
						<td align="center" width="108">ADD</td>
						<td align="center" width="108">REMOVE</td>
						<td  align="center" width="80"></td>								
				</tr></table></div>
				<div class="DivContents"><table>
							<tr><td><td>&nbsp;</td></tr>
			 	
			 	<%
                   int i = 0;
                 %>
			 	
				<c:forEach items="${allClassStudents}" var="cList">					
					<tr>
						<td align="left" width="150" class="txtStyle">${cList.student.userRegistration.firstName} ${cList.student.userRegistration.lastName}</td>
						<td align="center" width="108"><input type="checkbox" class="checkbox-design" name="studentIds" id="add:<%=i%>" value="${cList.student.studentId}" <c:if test="${cList.status == 'accepted'}"> checked disabled</c:if> onclick="addStudent(this, ${cList.gradeClasses.gradeClassId}, ${cList.gradeLevel.gradeLevelId}, <%=i%>)" /><label for="add:<%=i%>" class="checkbox-label-design"></label></td>
						<td align="center" width="108"><input type="checkbox" class="checkbox-design" name="studentIds" id="remove:<%=i%>" value="${cList.student.studentId}" <c:if test="${cList.status == 'waiting' || cList.status == 'new'}"> disabled </c:if> onclick="removeStudent(this, ${cList.gradeClasses.gradeClassId}, <%=i%>)" /><label for="remove:<%=i%>" class="checkbox-label-design"></label></td>								
						<td  align="center" width="80"><div id='result<%=i%>' class='status-message'></div></td>	
					</tr>
					<tr><td style="padding-bottom: 10px"></td></tr>
					
					<%
						i++;
					%>
					
				</c:forEach></table></div>
				
		</td></tr></table>
</body>
</html>