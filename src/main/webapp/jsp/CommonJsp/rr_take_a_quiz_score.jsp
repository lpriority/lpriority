
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script> 
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css">
<%@ include file="../CommonJsp/include_resources.jsp" %>
<script type="text/javascript" src="resources/javascript/Student/reading_register_student.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
#div_top_hypers {
    background-color:#eeeeee;
    display:inline;      
}
#ul_top_hypers {
    display: flex;
    justify-content:space-around;
    list-style-type:none;
}
</style>
</head>
<body>
<input type="hidden" id="rating" value="${readRegReview.rating}" />
<input type="hidden" id="pageRange" value="${activityScore.readRegMaster.readRegPageRange.range}" />
<input type="hidden" id="activityValue" value="${activityScore.readRegActivity.activityValue}" />
<input type="hidden" id="readRegActivityScoreId" value="${activityScore.readRegActivityScoreId }"/>
<table width="100%" id="tableDiv">
<form id="addReviewForm" >
 	   <tr>
			<td height="0" colspan="2" align="left" valign="top" class="" style="padding-top:1em;padding-left: 2em">
				<table id="T3" width="100%" id='divtag' align="center">
						<tr>
							<td width="35%" height="10" align="right" valign="middle"></td>
							<td width="10%" height="10" align="center" valign="middle"></td>
							<td height="10" align="left" valign="middle">					
							</td>
						</tr>
						<tr><td colspan="3">
						<ol>
						<c:forEach items="${readRegAnswers }" var="answer" varStatus="cnt">
							 
								<li><p align="justify"
										style=" font-family: 'Tangerine', serif;font-size: 22px; color: ">${answer.readRegQuestions.question}
										
									</p>
									<ul style="list-style-type:none;font-family: 'Tangerine', serif;font-size: 18px; " >
										<li style="color:${answer.answer == 'option1' && answer.readRegQuestions.answer == answer.answer? 'green':''}; color: ${answer.answer == 'option1' && answer.readRegQuestions.answer ne answer.answer? 'red':''}" >A) ${answer.readRegQuestions.option1}</li>
										<li style="color:${answer.answer == 'option2' && answer.readRegQuestions.answer == answer.answer? 'green':''}; color: ${answer.answer == 'option2' && answer.readRegQuestions.answer ne answer.answer? 'red':''}">B) ${answer.readRegQuestions.option2}</li>
										<li style="color:${answer.answer == 'option3' && answer.readRegQuestions.answer == answer.answer? 'green':''}; color: ${answer.answer == 'option3' && answer.readRegQuestions.answer ne answer.answer? 'red':''}">C) ${answer.readRegQuestions.option3}</li>
										<li style="color:${answer.answer == 'option4' && answer.readRegQuestions.answer == answer.answer? 'green':''}; color: ${answer.answer == 'option4' && answer.readRegQuestions.answer ne answer.answer? 'red':''}">D) ${answer.readRegQuestions.option4}</li>
									</ul>
									<c:if test="${answer.readRegQuestions.answer ne answer.answer}">
										<span style="color:green"> Correct Answer : ${answer.readRegQuestions.answer }</span> 
									</c:if>
								</li>
						 </c:forEach>
						 </ol>
						 </td>
						 </tr>
						<tr>
							<td width="35%" height="20" align="right" valign="middle"><span
								class="tabtxt">Teacher Rubric Score</span> &nbsp;</td>
							<td width="10%" height="20" align="center" valign="middle">:</td>
							<td height="20" align="left" valign="middle"><span class="tabtxt" title="${activityScore.readRegRubric.description}"> 								
								${activityScore.readRegRubric.score}									
							</span></td>
						</tr>
						<tr>
							<td width="35%" height="20" align="right" valign="middle"><span
								class="tabtxt">Points Earned</span> &nbsp;</td>
							<td width="10%" height="20" align="center" valign="middle">:</td>
							<td height="20" align="left" valign="middle"><span class="tabtxt" > 								
								${activityScore.pointsEarned} (pages of the book = ${activityScore.readRegMaster.readRegPageRange.range} x Activity Value = ${activityScore.readRegActivity.activityValue } x Rubric value = ${activityScore.readRegRubric.score} )									
							</span></td>
						</tr>
				</table>
</td></tr></form>
</table>
<div id="loading-div-background" class="loading-div-background" style="display:none;">
	<div class="loader"></div>
	<div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;">
		<br><br><br><br><br><br><br>Loading....
	</div>
</div>
</body>
</html>