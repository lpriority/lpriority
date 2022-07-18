<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.ui-progressbar {background: white;}
	.ui-progressbar > .ui-widget-header {background: yellow;}
	.ui-progressbar-value {
    font-size: 13px;
    font-weight: normal;
    line-height: 18px;
    font-family : serif;
    color: black;
    text-align: center;
}
</style>
<script type="text/javascript">
	$(function() {
		var count = document.getElementById('count').value;
		for(var i=0;i<count; i++){
			var myPer = document.getElementById('prog'+i).value;
			var intValue = Math.round(myPer);
			$("#progressbar"+i)
			    .progressbar({ value: intValue })
			    .children('.ui-progressbar-value')
			    .html(myPer+ '%')
			    .css("display", "block");
		    $( "#progressbar"+i).height(15);
		}
	});
</script>
</head>
<body>
<div align="center">
<table class="des" align="center" width="70%"><tr><td><div class="Divheads">
	<table style="width: 100%;align:center;" class="txtStyle">
		<tr>
			<td colspan="3" align="center" class="txtStyle"
				style="width: 80%; text-decoration: underline" align="left">
					<label class="headsColor">Exam Item Analysis Report</label></td>
		</tr></table></div><div class="DivContents"><table width="100%" style="align:center;padding-left:70px">
		<tr>
			<td colspan="3" align="left" style="height: 7px;" align="center"></td>
		</tr>
		<tr>
			<td><b>Instructor Name : </b> ${itemAnalysisReports.instructor}</td>
			<td><b>Exam Date : </b> ${itemAnalysisReports.examDate}</td>
			<td><b>Highest Score : </b> ${itemAnalysisReports.highestScore}
				- ${itemAnalysisReports.highestPercentage}%</td>
		</tr>
		<tr>
			<%-- <td><b>Lesson Name : </b> ${itemAnalysisReports.lessonName}</td> --%>
			<td><b>Total Possibles : </b>
				${itemAnalysisReports.totalPossibles}</td>
			<td><b>Lowest Score : </b> ${itemAnalysisReports.lowestScore} -
				${itemAnalysisReports.lowestPercentage}%</td>
		</tr></table></div><div class="DivContents"><table width="100%"><tr>
			<td colspan="3" style="width: 100%">
				<hr>
				<table style="width: 100%" align="left">
					<tr>
						<td colspan="6" style="width: 80%">Correct Answers are shown
							in bold and italics in red color</td>
						<td style="width: 10%">Correct</td>
						<td style="width: 10%">Percent Incorrect</td>
					</tr>
					<tr>
						<td colspan="8">
							<input type="hidden" id="count" value="${fn:length(itemAnalysisReports.questions)}"/>
						<hr></td>

					</tr>
					<c:forEach items="${itemAnalysisReports.questions}" var="quelist"
						varStatus="queCount">
						<tr>
							<td style="width: 13%">Q${queCount.count}</td>
							<td style="width: 13%; <c:if test="${quelist.answer == 'option1'}"> color: red; font-style: italic; font-weight: bold;</c:if>" >  A(${itemAnalysisReports.optionAChoices[queCount.index]}, <fmt:formatNumber
									var="formattedValue" type="number" minFractionDigits="2"
									maxFractionDigits="2"
									value="${itemAnalysisReports.optionAChoices[queCount.index]*100/itemAnalysisReports.questionCounts[queCount.index]}" />
								${formattedValue} %)
							</td>
							<td style="width: 13%; <c:if test="${quelist.answer == 'option2'}"> color: red; font-style: italic; font-weight: bold;</c:if>">B(${itemAnalysisReports.optionBChoices[queCount.index]},
								<fmt:formatNumber var="formattedValue" type="number"
									minFractionDigits="2" maxFractionDigits="2"
									value="${itemAnalysisReports.optionBChoices[queCount.index]*100/itemAnalysisReports.questionCounts[queCount.index]}" />
								${formattedValue}%)
							</td>
							<td style="width: 13%; <c:if test="${quelist.answer == 'option3'}"> color: red; font-style: italic; font-weight: bold;</c:if>">C(${itemAnalysisReports.optionCChoices[queCount.index]},<fmt:formatNumber
									var="formattedValue" type="number" minFractionDigits="2"
									maxFractionDigits="2"
									value="${itemAnalysisReports.optionCChoices[queCount.index]*100/itemAnalysisReports.questionCounts[queCount.index]}" />
								${formattedValue}%)
							</td>
							<td style="width: 13%; <c:if test="${quelist.answer == 'option4'}"> color: red; font-style: italic; font-weight: bold;</c:if>">D(${itemAnalysisReports.optionDChoices[queCount.index]},<fmt:formatNumber
									var="formattedValue" type="number" minFractionDigits="2"
									maxFractionDigits="2"
									value="${itemAnalysisReports.optionDChoices[queCount.index]*100/itemAnalysisReports.questionCounts[queCount.index]}" />
								${formattedValue}%)
							</td>
							<td style="width: 13%; <c:if test="${quelist.answer == 'option5'}"> color: red; font-style: italic; font-weight: bold;</c:if>">E(${itemAnalysisReports.optionEChoices[queCount.index]},
								<fmt:formatNumber var="formattedValue" type="number"
									minFractionDigits="2" maxFractionDigits="2"
									value="${itemAnalysisReports.optionEChoices[queCount.index]*100/itemAnalysisReports.questionCounts[queCount.index]}" />
								${formattedValue}%)
							</td>
							<td style="width: 10%;"><fmt:formatNumber
									var="formattedValue" type="number" minFractionDigits="2"
									maxFractionDigits="2"
									value="${itemAnalysisReports.correctChoices[queCount.index]*100/itemAnalysisReports.questionCounts[queCount.index]}" />
								${formattedValue}%</td>
							<td style="width: 10%"><fmt:formatNumber
									var="formattedValue" type="number" minFractionDigits="2"
									maxFractionDigits="2"
									value="${itemAnalysisReports.wrongChoices[queCount.index]*100/itemAnalysisReports.questionCounts[queCount.index]}" />	
								<input type="hidden" name="prog" id="prog${queCount.index}" value="${formattedValue }" />			
								<div id="progressbar${queCount.index }"></div>
							</td>
						</tr>						
					</c:forEach>
				</table>
				<hr>
				<table>
					<c:if test="${itemAnalysisReports.questions[0].subQuestions.subQuestion != null}">					
						<tr>
							<td>
								<b>Comprehension Passage:</b><br><br>
							</td>
						</tr> 
						<tr>
							<td>
								${itemAnalysisReports.questions[0].subQuestions.subQuestion}<br><br>
							</td>
						</tr>
					</c:if>
				</table>
				<b>Questions:</b><br><br>
				<table style="width: 100%;">
					<c:forEach items="${itemAnalysisReports.questions}" var="question"
						varStatus="queCount">
						<tr>
							<td colspan="10" width="100%">
								Q${queCount.count}: ${question.question}<br>
							</td>
						</tr>
						<tr>
							<c:forEach begin="1" end="${question.numOfOptions}" var="count">
								<td style="width: 10%;">${count}: 										 
									<c:if test="${count == 1 }">
										<c:out value="${question.option1}"> </c:out>
									</c:if>
									<c:if test="${count ==2 }">
										<c:out value="${question.option2}"></c:out>
									</c:if>
									<c:if test="${count ==3 }">
										<c:out value="${question.option3}"></c:out>
									</c:if>
									<c:if test="${count ==4 }">
										<c:out value="${question.option4}"></c:out>
									</c:if>
									<c:if test="${count ==5 }">
										<c:out value="${question.option5}"></c:out>
									</c:if>
								</td>
							</c:forEach>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table></div></td></tr></table>
	</div>
</body>
</html>