
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script src="resources/javascript/my_recorder/recorder.js"></script>
<link rel="stylesheet"
	href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet"
	href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<link rel="stylesheet"
	href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css">
<%@ include file="../CommonJsp/include_resources.jsp"%>
<script type="text/javascript"
	src="resources/javascript/Student/reading_register_student.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add/Edit Quiz Questions</title>
<script>
$(document).ready(function(){
    $(':input:not(:button)').focus(function(index, element) {
    	//console.log($(this)[0].nodeName);
    	$(this).css({"border": ""});
    });
    
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
.round-button{
  width: 40px;
  height: 40px;
  text-decoration: none;
  display: inline-block;
  outline: none;
  cursor: pointer;
  border-style: none;
  color: white;
  background-color: #01D1EB;
  border-radius: 100%; 
  overflow: none;
  text-align: center;
}
.round-done-button{
  width: 40px;
  height: 40px;
  text-decoration: none;
  display: inline-block;
  outline: none;
  cursor: pointer;
  border-style: none;
  color: white;
  background-color: #249636;
  border-radius: 100%; 
  overflow: none;
  text-align: center;
}
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
<input type="hidden" name="rating" id="rating" value="${rating}" />
	<div align="center">
		<c:forEach items="${readRegQuizLt }" var="quest">
		<c:set var="cssStyle" value="round-button"></c:set>			
			<c:if test="${quest.questionNum < questionNum}">
				<c:set var="cssStyle" value="round-done-button"></c:set>
			</c:if>	
			<c:if test="${quest.questionNum == 10 && reQuestion.readRegQuestionsId>0 }">
				<c:set var="cssStyle" value="round-done-button"></c:set>
			</c:if>
			<button onclick='goToQuestion(${quest.questionNum},${page})' ${quest.questionNum <= questionNum ? '': 'disabled'} id="questBut${quest.questionNum}" class="${cssStyle}">${quest.questionNum}</button>
		</c:forEach>
	</div>
	<div id="pageContent" align="center">
		<input type="hidden" name="regId" id="regId" value="${userReg.regId}" />
		<form:form id="createQuestionsForm" modelAttribute="quizQuestion" >
			<table width="100%" style="padding-top: 2em;"  >		
				<tr align="center" style="position: relative;" class="tabtxt">
					<td colspan="3">
						<form:hidden path="readRegMaster.titleId" value="${titleId}" id="titleId"/> 
						<form:hidden path="readRegQuestionsId" />
						<form:hidden path="readRegQuiz.questionNum" id="questionNum" value="${questionNum}" />
						<form:hidden path="grade.gradeId" value="${studentObj.grade.gradeId}" />
						<input type="hidden" var="masGradeId" value="${masGradeId}" />
					</td>
				</tr>
				<tr align="center" style="position: relative;" class="tabtxt">
					<td width="20%" align="right">Question ${questionNum} </td><td width="20%"> :</td><td width="60%" align="left"> <form:textarea
							path="question" 
							id="question"
							style="width:320px;height: 40px;overflow: auto;" maxlength="200"
							></form:textarea></td>
				</tr>
				<tr align="center" style="position: relative;" class="tabtxt">
					<td width="20%" align="right">Option 1 </td><td width="20%"> :</td><td width="60%" align="left"><form:input
							path="option1" 
							id="option1" 
							style="width:100px;height: 15px;overflow: auto;" maxlength="100"
							/></td>
				</tr>
				<tr align="center" style="position: relative;" class="tabtxt">
					<td width="20%" align="right">Option 2 </td><td width="20%"> :</td><td width="60%" align="left"><form:input
							path="option2" 
							id="option2" 
							style="width:100px;height: 15px;overflow: auto;" maxlength="100"
							/></td>
				</tr>
				<tr align="center" style="position: relative;" class="tabtxt">
					<td width="20%" align="right">Option 3 </td><td width="20%"> :</td><td width="60%" align="left"><form:input
							path="option3"
							id="option3"
							style="width:100px;height: 15px;overflow: auto;" maxlength="100"
							/></td>
				</tr>
				<tr align="center" style="position: relative;" class="tabtxt">
					<td width="20%" align="right">Option 4</td><td width="20%"> :</td><td width="60%" align="left"><form:input
							path="option4" 
							id="option4"
							style="width:100px;height: 15px;overflow: auto;" maxlength="100"
							/></td>
				</tr>
				<tr align="center" style="position: relative;" class="tabtxt">
					<td width="20%" align="right">Answer </td><td width="20%"> :</td><td width="60%" align="left">
					<form:select path="answer" id="answer" style="width:100px;">
							<form:option value="" label="select answer" />
							<form:option value="option1" label="option 1" />
							<form:option value="option2" label="option 2" />
							<form:option value="option3" label="option 3" />
							<form:option value="option4" label="option 4" />
						</form:select></td>
				</tr>	
				<c:if test="${(questionNum == 10)  && (masGradeId >=3)}">
				<%-- ${questionNum == 10} and ${masGradeId>=3}"> --%>
					<tr align="center" style="position: relative;" class="tabtxt"  >
						<td width="20%" align="right">Choose a Score  </td><td width="20%"> :</td><td width="60%" align="left">
								<form:select path="rubricScore" id="rubricScore" style="width:180px;overflow: auto;">
									<c:forEach items="${rubrics}" var="rubric">
										<form:option value="${rubric.readRegRubricId }"  selected="${activityScore.selfScore.readRegRubricId == rubric.readRegRubricId? 'selected': '' }">${rubric.score } - ${rubric.description}</form:option>
									</c:forEach>
								</form:select></td>
					</tr>
				</c:if>
				<c:if test="${(questionNum == 10) && (rating eq 0)}">
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
				</c:if>
				<c:if test="${activityScore.approveStatus ne 'approved'}">
				<tr id="submitDiv">
					<td colspan="2" height="10" align="right" valign="middle">
						<div class="button_green" align="right" onclick="saveCreateQuestions(${questionNum},${page})">Submit</div>
					</td>
					<td height="10" align="left" valign="middle"
						style="padding-left: 2em;">
						<div class="button_green" align="right"
							onclick="$('#createQuestionsForm')[0].reset();">Reset</div>
					</td>
				</tr>						
				</c:if>
			</table>
		</form:form>
	</div>	
	<div align="center" class='status-message' id="returnMessage"></div>
	<div id="loading-div-background-c" class="loading-div-background" style="display:none;">
		<div class="loader"></div>
		<div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;">
			<br><br><br><br><br><br><br>Loading....
		</div>
	</div>	
</body>
</html>