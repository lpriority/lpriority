<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%-- <form:form command="securityQuestion" method="post" action="">  --%>
<form:form>
<table>  
<tr><td><c:if test="${not empty lists}">
 
		<ul>
			<c:forEach var="listValue" items="${lists}">
				<li>${listValue.question}</li>
			</c:forEach>
		</ul>
 
	</c:if>
	

</td></tr>
<tr><td>
<select id="userType" name="userType">
    <option value="--select--" selected></option>
    <c:forEach items="${userlists}" var="title">
<%--         <c:when test="${title.titleId== titleId}"> --%>
            <option value="${title.userType}">${title.userType}</option>
<%--         </c:when> --%>
<%--         <c:otherwise> --%>
<%--             <option value="${title.titleId}" >${title.titleName}</option> --%>
<%--         </c:otherwise> --%>
    </c:forEach>
</select>

<%-- <form:select path="question" multiple="true"> --%>
<%--                     <form:options items="${lists.question}"/> --%>
<%--                 </form:select> --%>
</td></tr>



</table>
</form:form> 
</body>
</html>

  