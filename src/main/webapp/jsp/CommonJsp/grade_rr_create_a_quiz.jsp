
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
<input type="hidden" id="bookAppStat" value="${bookApproveStatus}"/>
<input type="hidden" id="bookTitleId" value="${activityScore.readRegMaster.titleId}"/>
<input type="hidden" id="approveStatus" value="${bookApproveStatus}" />
<input type="hidden" id="activityAppStat" value="${activityScore.approveStatus}"/>
<input type="hidden" id="accApproveStatus" value="${activityScore.approveStatus}" />
<c:set var="disStat" value="hidden" />
<c:if test="${bookApproveStatus == 'approved'}">
<c:set var="disStat" value="visible" />
</c:if>
<table width="100%" id="tableDiv">
<form id="addReviewForm" >
 	   <tr>
			<td height="0" colspan="2" align="left" valign="top" class="" style="padding-top:1em;padding-left: 2em">
				<table id="T3" width="100%" id='divtag' align="center">
					<tr>
						<td width="35%" height="20" align="right" valign="middle"><span
							class="tabtxt">Book Title</span> &nbsp;</td>
						<td width="10%" height="20" align="center" valign="middle">:</td>
						<td height="20" align="left" valign="middle"><span class="tabtxt"> 
							${activityScore.readRegMaster.bookTitle}
						</span></td>
					 </tr>
					  <tr>
						<td width="35%" height="20" align="right" valign="middle"><span
							class="tabtxt">Author &nbsp;</span></td>
						<td width="10%" height="20" align="center" valign="middle">:</td>
						<td height="20" align="left" valign="middle"><span
							class="tabtxt">${activityScore.readRegMaster.author}
						</span></td>
					 </tr>
					  <tr>
						<td width="35%" height="20" align="right" valign="middle"><span
							class="tabtxt">Number Of Pages &nbsp;</span></td>
						<td width="10%" height="20" align="center" valign="middle">:</td>
						<td height="20" align="left" valign="middle"><span
							class="tabtxt">${activityScore.readRegMaster.numberOfPages}
						</span></td>
					 </tr>
						<tr>
							<td width="35%" height="10" align="right" valign="middle"></td>
							<td width="10%" height="10" align="center" valign="middle"></td>
							<td height="10" align="left" valign="middle">					
							</td>
						</tr>
						<tr><td colspan="3">
						<ol>
						<c:forEach items="${readRegQuestions }" var="quest" varStatus="cnt">
							 
								<li><p align="justify"
									style="font-family: 'Tangerine', serif;font-size: 22px;">${quest.question}
								</p>
									<ul style="list-style-type:none;font-family: 'Tangerine', serif;font-size: 18px; " >
										<li>A) ${quest.option1}</li>
										<li>B) ${quest.option2}</li>
										<li>C) ${quest.option3}</li>
										<li>D) ${quest.option4}</li>
									</ul>
								</li>
						 </c:forEach>
						 </ol>
						 </td>
						 </tr>
					<c:if test="${showRubric == 'yes'}"> 
						<tr>
							<td width="35%" height="20" align="right" valign="middle"><span
								class="tabtxt">Student's Rubric Score</span> &nbsp;</td>
							<td width="10%" height="20" align="center" valign="middle">:</td>
							<td height="20" align="left" valign="middle"><span class="tabtxt" title="${activityScore.selfScore.description}"> 								
								${activityScore.selfScore.score}									
							</span></td>
						</tr>
						</c:if>
						<c:choose>
				<c:when test="${bookApproveStatus == 'waiting'}">
						<tr id="appStat">
						<td width="35%" height="20" align="right" valign="middle"><span
								class="tabtxt">Approve</span> &nbsp;</td>
							<td width="10%" height="20" align="center" valign="middle">:</td>
							<td height="20" align="left" valign="middle">
							<input type="hidden" name="approveId" id="approveId" value="book" />
								<span class="tabtxt"> 
									<input type="radio" name="bookApprove" value="approved" checked onClick="setBookApproval()">Approve
									<input type="radio" name="bookApprove" value="returned" onClick="setBookApproval()">Return
								</span>
							</td>
						</tr>
						<tr id="spaceId">
						<td>&nbsp;</td>
						</tr>
				</c:when>
				<c:when test="${bookApproveStatus == 'approved' && activityScore.approveStatus=='waiting'}">
				<tr id="appStat">
						<td width="35%" height="20" align="right" valign="middle"><span
								class="tabtxt">Approve</span> &nbsp;</td>
							<td width="10%" height="20" align="center" valign="middle">:</td>
							<td height="20" align="left" valign="middle">
							<input type="hidden" name="approveId" id="approveId" value="activity" />
								<span class="tabtxt"> 
									<input type="radio" name="activityApprove" value="approved" checked onClick="setActivityApproval()">Approve Activity
									<input type="radio" name="activityApprove" value="returned" onClick="setActivityApproval()">Return Activity
								</span>
							</td>
						</tr>
						<tr id="spaceId">
						<td>&nbsp;</td>
						</tr>
				</c:when>
				</c:choose>
						<tr id="finalScore" style="visibility:visible;">
							<td width="35%" height="20" align="right" valign="middle"><span
								class="tabtxt">Final Score</span> &nbsp;</td>
							<td width="10%" height="20" align="center" valign="middle">:</td>
							<td height="20" align="left" valign="middle">
								<span class="tabtxt"> 
									<select id="scoreId" style="width:200px;overflow: auto;" >
									<c:forEach items="${rubrics}" var="rubric">
										<option value="${rubric.readRegRubricId }-${rubric.score }" ${activityScore.readRegRubric.readRegRubricId == rubric.readRegRubricId? 'selected': '' }>${rubric.score } - ${rubric.description}</option>
									</c:forEach>
								</select>
								</span>
							</td>
						</tr>
						 <!-- <tr id="teaComment" style="visibility:hidden;"> -->
						 <tr id="teaComment">
							<td width="35%" height="20" align="right" valign="middle"><span
								class="tabtxt">Teacher Comment</span> &nbsp;</td>
							<td width="10%" height="20" align="center" valign="middle">:</td>
							<td height="20" align="left" valign="middle">
								<span class="tabtxt"> 
									<textarea id="teacherComment" name="teacherComment" style="height: 65px; width: 214px;"></textarea>
								</span>
							</td>
						</tr>	
						<tr>
							<td width="100%" height="23" align="center" colspan="3" >
								<input type="button" class="button_green" value="Submit Changes" style="border:none" onclick="saveScore()">										
							</td>
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