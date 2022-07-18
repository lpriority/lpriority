<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table align="center">
		<tr>
			<td width="562" align="center">&nbsp;</td>
		</tr>
	</table>
	<table align="center">

 
	<%int i = 1; %>
	<c:forEach var="jacTemp" items="${questionsList.jacTemplate}" varStatus="outerCount">
		<tr height="30">																
			<td width="300" align="left">
				<b>Title: &nbsp;${jacTemp.titleName}</b>
			</td>	
		</tr>	
		<%int j = 1; %>
		<c:forEach var="ques" items="${jacTemp.questionsList}" varStatus="innerCount">
			<tr>
				<td width="562" align="center"><%=j%>:&nbsp;<form:input path="questionsList.jacTemplate[${outerCount.index}].questionsList[${innerCount.index}].answer" id="answer${outerCount.index}${innerCount.index}" required="required" /></td>
			</tr>
			<tr>
				<td width="562" align="center">&nbsp;
					<form:hidden path="questionsList.jacTemplate[${outerCount.index}].questionsList[${innerCount.index}].questionId"/>
					<form:hidden path="questionsList.jacTemplate[${outerCount.index}].questionsList[${innerCount.index}].assignmentType.assignmentTypeId"/>
					<form:hidden path="questionsList.jacTemplate[${outerCount.index}].questionsList[${innerCount.index}].lesson.lessonId"/>
					<form:hidden path="questionsList.jacTemplate[${outerCount.index}].questionsList[${innerCount.index}].usedFor"/>
					<form:hidden path="questionsList.jacTemplate[${outerCount.index}].questionsList[${innerCount.index}].createdBy"/>
					<form:hidden path="questionsList.jacTemplate[${outerCount.index}].questionsList[${innerCount.index}].jacTemplate.jacTemplateId"/>
				</td>
			</tr>
			<%j++;%>
		</c:forEach>	
	<%i++;%>
	</c:forEach> 
	<tr>
		<td align="center" style="padding-bottom: 20px;">
			<input type="file" name="file" value="Upload" style="display: none;"/><br><br> 
			<input type="image" onclick="return jacUpdate()" src="images/Common/submitChangesUp.gif"/> 
		</td>
	</tr>
</table> 
