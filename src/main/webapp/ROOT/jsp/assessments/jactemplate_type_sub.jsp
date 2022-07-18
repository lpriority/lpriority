<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%int i = 0; %>
<c:forEach var="jCount" items="${jacList}">
<c:set var="count" value="<%=i%>"/>
	<tr>
		<td width="562" align="center">${jCount.titleName}</td>
	</tr>
	<%int j = 1; %>
	<c:forEach var="qNumber" begin="0" end="${jCount.noOfQuestions}">
		<tr>
			<td width="562" align="center"><%=j%>&nbsp;<form:input path="questionsList.jacTemplate[${count}].questionsList[${qNumber}].answer" id="answer${count}${qNumber}" required="required" pattern="[A-Za-z0-9\s]+"/></td>
		</tr>
		<tr>
			<td width="562" align="center">&nbsp;</td>
		</tr>
		<%j++;%>
	</c:forEach>
	
	<tr>
		<td width="562" align="center">&nbsp;</td>
	</tr>
<%i++;%>
</c:forEach>