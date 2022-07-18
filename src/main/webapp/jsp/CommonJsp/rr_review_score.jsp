
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
<script>
$(document).ready(function(){	 
	  var rating = parseInt($('#rating').val());	
		setSelectedStarts(rating)
	});	

function setSelectedStarts(val){
	$('#stars li').each(function (i) {
		  if(i < val)
	       $(this).addClass('selected');
	 });
}


</script>
<style>
/* Rating Star Widgets Style */
.rating-stars ul {
  list-style-type:none;
  padding:0;
  text-shadow:0 1px 2px rgb(154, 154, 154);
  -moz-user-select:none;
  -webkit-user-select:none;
}
.rating-stars ul > li.star {
  display:inline-block;
  
}
/* Idle State of the stars */
.rating-stars ul > li.star > i.fa {
  font-size:1.7em; /* Change the size of the stars */
  color:#ccc; /* Color on idle state */
}
/* Hover state of the stars */
.rating-stars ul > li.star.hover > i.fa {
  color:#97e3ec;
}
/* Selected state of the stars */
.rating-stars ul > li.star.selected > i.fa {
  color:#00c5de;
}
</style>
</head>
<body oncontextmenu="return false" onkeydown="if ((arguments[0] || window.event).ctrlKey) return false">
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
						 <tr>
							<td width="35%" height="20" align="right" valign="middle"><span
								class="tabtxt">Review &nbsp;</span></td>
							<td width="10%" height="20" align="center" valign="middle">:</td>
							<td height="20" align="left" valign="middle"><span
								class="tabtxt"> ${readRegReview.review}
							</span></td>
						 </tr>
						<tr>
							<td width="35%" height="20" align="right" valign="middle"><span	class="tabtxt">Rate &nbsp;</span></td>
							<td width="10%" height="20" align="center" valign="middle">:</td>
							<td height="20" align="left" valign="middle">							
								<div class='rating-widget'>  
								  <!-- Rating Stars Box -->
								  <div class='rating-stars' style="pointer-events:none">
								    <ul id='stars'>
								      <li class='star' title='Poor' data-value='1'>
								        <i class='fa fa-star fa-fw'></i>
								      </li>
								      <li class='star' title='Fair' data-value='2'>
								        <i class='fa fa-star fa-fw'></i>
								      </li>
								      <li class='star' title='Good' data-value='3'>
								        <i class='fa fa-star fa-fw'></i>
								      </li>
								      <li class='star' title='Excellent' data-value='4'>
								        <i class='fa fa-star fa-fw'></i>
								      </li>
								      <li class='star' title='WOW!!!' data-value='5'>
								        <i class='fa fa-star fa-fw'></i>
								      </li>
								    </ul>
								  </div>
								</div>
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
						<c:if test="${not empty activityScore.teacherComment}">
						<tr>
							<td width="35%" height="20" align="right" valign="middle"><span
								class="tabtxt">Teacher Comment</span> &nbsp;</td>
							<td width="10%" height="20" align="center" valign="middle">:</td>
							<td height="20" align="left" valign="middle"><span class="tabtxt" > 								
								${activityScore.teacherComment}									
							</span></td>
						</tr>
						</c:if>
						<c:if test= "${userReg.user.userType == 'teacher' || userReg.user.userType == 'admin' }">
						<tr>
							<td width="100%" height="23" align="center" colspan="3" ><br>
								<input type="button" class="button_green" value="Return" style="border:none" onclick="returnGradedActivity(${activityScore.readRegActivityScoreId})">										
							</td>
						</tr>
						</c:if>
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