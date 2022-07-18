<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table width="20%" border="1" align="center">
	<tbody>
			<tr>
			<td width="1%">
				<form:input path="questionsList.questions[${qNumber}].rubrics[0].score" id="score0" required="required" value="Score" size="2" style="border:none; background-color:transparent; text-align:center" readonly="true"/>			
			</td>
			<td>
				<form:input path="questionsList.questions[${qNumber}].rubrics[0].dimension1" id="dimension1" required="required" value="dimension1" size="25" style="border:none; text-align:center"  onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}"/>
			</td>
			<c:if test="${rubType == 3 || rubType == 4}">
				<td>
					<form:input path="questionsList.questions[${qNumber}].rubrics[0].dimension2" id="dimension2" required="required" value="dimension2" size="25" style="border:none; text-align:center"  onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}"/>
				<td>
					<form:input path="questionsList.questions[${qNumber}].rubrics[0].dimension3" id="dimension3" required="required" value="dimension3" size="25" style="border:none; text-align:center"  onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}"/>
				</td>
				<td>
					<form:input path="questionsList.questions[${qNumber}].rubrics[0].dimension4" id="dimension4" required="required" value="dimension4" size="25" style="border:none; text-align:center"  onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}"/>
				</td>			
			</c:if>
		</tr>
		<c:set var="dec" value="0"/>
		<c:forEach var="rScore" begin="1" end="${rubScore}">
		<tr>
			<td width="1%">
				<input form="questionsForm" name="questions[${qNumber}].rubrics[${rScore}].score" id="score${rScore}" required="required" value="${rubScore-dec}" size="2" style="border:none; background-color:transparent; text-align:center;"  readonly="true"/>	
			</td>
			<td>
				<textarea form="questionsForm" name="questions[${qNumber}].rubrics[${rScore}].dimension1" id="dimension1${rScore}" required="required" cols="25" rows="5" style="border:none"  onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}"/>
			</td>
			<c:if test="${rubType == 3 || rubType == 4}">
				<td>
					<textarea form="questionsForm" name="questions[${qNumber}].rubrics[${rScore}].dimension2" id="dimension2${rScore}" required="required" cols="25" rows="5" style="border:none"  onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}"/>
				</td>
				<td>
					<textarea form="questionsForm" name="questions[${qNumber}].rubrics[${rScore}].dimension3" id="dimension3${rScore}" required="required" cols="25" rows="5" style="border:none"  onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}"/>
				</td>
				<td>
					<textarea  form="questionsForm" name="questions[${qNumber}].rubrics[${rScore}].dimension4" id="dimension4${rScore}" required="required" cols="25" rows="5" style="border:none"  onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}"/>
				</td>			
			</c:if>
		</tr>	
		<c:set var="dec" value="${dec+1}"/>
     </c:forEach>
     </tbody>
</table>
	
	