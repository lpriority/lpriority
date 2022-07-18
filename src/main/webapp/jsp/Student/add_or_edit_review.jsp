
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
<title>Add/Edit Review</title>
<script>
$(document).ready(function(){	  
	  /* 1. Visualizing things on Hover - See next part for action on click */
	  $('#stars li').on('mouseover', function(){
	    var onStar = parseInt($(this).data('value'), 10); // The star currently mouse on
	   
	    // Now highlight all the stars that's not after the current hovered star
	    $(this).parent().children('li.star').each(function(e){
	      if (e < onStar) {
	        $(this).addClass('hover');
	      }
	      else {
	        $(this).removeClass('hover');
	      }
	    });
	    
	  }).on('mouseout', function(){
	    $(this).parent().children('li.star').each(function(e){
	      $(this).removeClass('hover');
	    });
	  }); 
	  
	  /* 2. Action to perform on click */
	  $('#stars li').on('click', function(){
	    var onStar = parseInt($(this).data('value'), 10); // The star currently selected
	    var stars = $(this).parent().children('li.star');
	   
	    for (i = 0; i < stars.length; i++) {
	      $(stars[i]).removeClass('selected');
	    }
	    
	    for (i = 0; i < onStar; i++) {
	      $(stars[i]).addClass('selected');
	    }
	    
	    // JUST RESPONSE (Not needed)
	    var ratingValue = parseInt($('#stars li.selected').last().data('value'), 10);
	    var msg = "";
	    if(ratingValue){
	    	if(ratingValue > 0){
	    		$('#rating').val(ratingValue);
	    	}
	    }    
	  });
	  var rating = parseInt($('#rating').val());	
		setSelectedStarts(rating)
	});
	
function removeSelectedStars(){
	 $('#stars li.selected').each(function (i) {
	       $(this).removeClass('selected');
	 });
}
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
<body>
<input type="hidden" name="userType" id="userType" value="${userReg.user.userType}" />
<input type="hidden" name="regId" id="regId" value="${userReg.regId}" />
<input type="hidden" name="titleId" id="titleId" value="${titleId}" />
<input type="hidden" name="operation" id="operation" value="${mode}" />
<table width="100%" id="tableDiv">
<form:form id="addReviewForm" modelAttribute="readRegReview" method="post" action="saveOrUpdateReview.htm">
 	   <tr>
			<td height="0" colspan="2" align="left" valign="top" class="" style="padding-top:1em;">
				<table id="T3" width="100%" id='divtag' align="center">
						<tr>
							<td width="25%" height="10" align="right" valign="middle"></td>
							<td width="10%" height="10" align="center" valign="middle"></td>
							<td height="10" align="left" valign="middle">
							 <form:hidden path="reviewId" value="${reviewId}" />
							 <form:hidden path="rating" value="${rating}" />
							 <form:hidden path="grade.gradeId" value="${studentObj.grade.gradeId}" />
							 <form:hidden path="readRegMaster.titleId" value="${titleId}" />							
							</td>
						</tr>
						<tr>
							<td width="25%" height="20" align="right" valign="middle"><span
								class="tabtxt">Write Review &nbsp;<img src="images/Common/required.gif"></span></td>
							<td width="10%" height="20" align="center" valign="middle">:</td>
							<td height="20" align="left" valign="middle"><span
								class="tabtxt"> <form:textarea path="review" style="width:280px;height: 100px;overflow: auto;pointer-events:${activityScore.approveStatus eq 'approved'? 'none' : 'auto' }" onblur="saveOrUpdateReview(${index},${page},false)"></form:textarea><font color="red">
										<form:errors path="review"></form:errors>
								</font>
							</span></td>
						 </tr>
						 <c:if test="${showRubric == 'yes'}">
							  <tr>
								<td width="25%" height="20" align="right" valign="middle"><span
									class="tabtxt">Choose a Score</span> &nbsp;<img src="images/Common/required.gif"></td>
								<td width="10%" height="20" align="center" valign="middle">:</td>
								<td height="20" align="left" valign="middle"><span class="tabtxt"> 
									<form:select path="rubricScore" style="width:280px;overflow: auto; pointer-events:${activityScore.approveStatus eq 'approved'? 'none' : 'auto' }" >
										<c:forEach items="${rubrics}" var="rubric">
											<form:option value="${rubric.readRegRubricId }" selected="${activityScore.selfScore.readRegRubricId == rubric.readRegRubricId? 'selected': '' }">${rubric.score } - ${rubric.description}</form:option>
										</c:forEach>
									</form:select>
									<font color="red">
										<form:errors path="rubricScore"></form:errors>
									</font>
								</span></td>
							 </tr>
						</c:if>
						<tr>
						<td width="25%" height="20" align="right" valign="middle"><span	class="tabtxt">Rate &nbsp;<img src="images/Common/required.gif"></span></td>
						<td width="10%" height="20" align="center" valign="middle">:</td>
						<td height="20" align="left" valign="middle">							
								<div class='rating-widget'>  
								  <!-- Rating Stars Box -->
								  <div class='rating-stars' style="pointer-events:${not empty activityScore.pointsEarned? 'none' : 'auto' }">
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
						<c:if test="${activityScore.approveStatus ne 'approved'}">
							<tr>
								<td width="100%" height="23" align="center" colspan="3" style="padding-top:1em;">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td width="8%" align="center" valign="middle">
													<input type="button" class="button_green" value="Submit Changes" style="border:none" onclick="saveOrUpdateReview(${index},${page},true)">
												</td>
											</tr>
											<tr><td>&nbsp;</td></tr>
									</table>
								</td>
							</tr>
						</c:if>
				</table>
</td></tr></form:form>
</table>
</body>
</html>