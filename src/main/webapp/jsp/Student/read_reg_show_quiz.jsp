
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
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
<form:form id="takeQuizForm" modelAttribute="quizQuestionsList" method="POST" action="takeQuizQuestions.htm">
<input type="hidden" name="titleId" id="titleId" value="${titleId}" />
<input type="hidden" name="rating" id="rating" value="${rating}" />

<table width="100%" id="tableDiv" style="padding-top:1.5em;">
<c:choose>
 <c:when test="${fn:length(readRegAnswersLt) == 0}">
 	<c:forEach items="${numArray}" var="random" varStatus="status"> 
 		<tr><td width="75%"><b class="label">Question ${status.count}:</b>&nbsp;&nbsp;&nbsp;<p class='tabtxt' align="justify">${quizQuestionsList.readRegQuestionsLt[random].question}</p></td></tr>
		<tr><td class="txtStyle" style="padding-left:1em;height:2.5em;">
		<table width="80%" readonly="readonly"><tr>
		<td width="5%">
		<form:hidden path="readRegAnswersLt[${status.index}].readRegQuestions.readRegQuestionsId" value="${quizQuestionsList.readRegQuestionsLt[random].readRegQuestionsId}"/>
		<form:hidden path="readRegAnswersLt[${status.index}].readRegQuestions.answer" value="${quizQuestionsList.readRegQuestionsLt[random].answer}"/>
		<form:hidden path="readRegAnswersLt[${status.index}].readRegQuestions.readRegMaster.titleId" value="${quizQuestionsList.readRegQuestionsLt[random].readRegMaster.titleId}"/>		
		</td>
		<tr><td width="50%"><form:radiobutton path="readRegAnswersLt[${status.index}].answer" data-count="${status.index}" value="option1" />&nbsp; ${quizQuestionsList.readRegQuestionsLt[random].option1}</td></tr> 
		<tr><td width="50%"><form:radiobutton path="readRegAnswersLt[${status.index}].answer" data-count="${status.index}" value="option2" />&nbsp; ${quizQuestionsList.readRegQuestionsLt[random].option2}</td></tr> 
		<tr><td width="50%"><form:radiobutton path="readRegAnswersLt[${status.index}].answer" data-count="${status.index}" value="option3" />&nbsp; ${quizQuestionsList.readRegQuestionsLt[random].option3}</td></tr> 
		<tr><td width="50%"><form:radiobutton path="readRegAnswersLt[${status.index}].answer" data-count="${status.index}" value="option4" />&nbsp; ${quizQuestionsList.readRegQuestionsLt[random].option4}</td></tr> 
		<tr><td colspan="5" height="10"><div id="correctAnswer${status.index}" style="color:green;display: none">Correct Answer : ${quizQuestionsList.readRegQuestionsLt[random].answer}</div></td></tr>
		</table>
		</td>
		</tr>
		
	</c:forEach>
	</table>
	<table>
	<c:if test="${rating eq 0}">
				 <tr>
						<td height="20" align="left" valign="middle"><span	class="tabtxt">Rate &nbsp;<img src="images/Common/required.gif"></span></td>
						<td height="20" align="left" valign="middle">:</td>
						<td height="20" align="left" valign="middle">							
								<div class='rating-widget'>  
								  <!-- Rating Stars Box -->
								  <div class='rating-stars' style="pointer-events:auto">
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
	<tr id="submitDiv"><td colspan="7">
	<table width="100%"><tr>
         <td width="35%" height="10" align="right" valign="middle"> 
        	<div class="button_green" align="right" onclick="submitQuizAnswers()">Submit</div> 
         </td>
         <td width="30%" height="10" align="left" valign="middle" style="padding-left: 2em;">
         	 <div class="button_green" align="right" onclick="$('#takeQuizForm')[0].reset();">Reset</div> 
         </td></tr>
   </table>
   </td></tr>
 </c:when>
 <c:when test="${fn:length(readRegAnswersLt) > 0}">
 <c:set var="count" value="0" />
	<c:forEach items="${readRegAnswersLt}" var="readRegAnswer" varStatus="status">
		<tr><td><b class="label">Question ${status.count}:</b>&nbsp;&nbsp;&nbsp;<span class='tabtxt'>${readRegAnswer.readRegQuestions.question}</span></td></tr>
		<tr><td class="txtStyle" style="padding-left:6.5em;height:2.5em;">
		<c:choose>
		    <c:when test="${readRegAnswer.answer == readRegAnswer.readRegQuestions.answer}">
		    	<c:set var="evalute" value="style='color:green;'"/>
		    	<c:set var="count" value="${count+1}" />
		    </c:when>    
		    <c:otherwise>
		    	<c:set var="evalute" value="style='color:red;'"/>
		    </c:otherwise>
		</c:choose>
		<table width="80%" readonly="readonly">
		  <tr>
			<td width="5%">
			<form:hidden path="readRegAnswersLt[${status.index}].readRegQuestions.readRegQuestionsId" value="${readRegAnswer.readRegQuestions.readRegQuestionsId}"/>
			<form:hidden path="readRegAnswersLt[${status.index}].readRegQuestions.answer" />
			</td>
			<tr><td width="25%" ${readRegAnswer.answer=='option1' ? evalute : ''} ><form:radiobutton disabled="true" path="readRegAnswersLt[${status.index}].answer" data-count="${status.index}" value="option1" checked="${readRegAnswer.answer=='option1' ? 'checked' : ''}" />&nbsp; ${readRegAnswer.readRegQuestions.option1}</td></tr> 
			<tr><td width="25%" ${readRegAnswer.answer=='option2' ? evalute : ''} ><form:radiobutton disabled="true" path="readRegAnswersLt[${status.index}].answer" data-count="${status.index}" value="option2" checked="${readRegAnswer.answer=='option2' ? 'checked' : ''}" />&nbsp; ${readRegAnswer.readRegQuestions.option2}</td></tr> 
			<tr><td width="25%" ${readRegAnswer.answer=='option3' ? evalute : ''} ><form:radiobutton disabled="true" path="readRegAnswersLt[${status.index}].answer" data-count="${status.index}" value="option3" checked="${readRegAnswer.answer=='option3' ? 'checked' : ''}" />&nbsp; ${readRegAnswer.readRegQuestions.option3}</td></tr> 
			<tr><td width="25%" ${readRegAnswer.answer=='option4' ? evalute : ''} ><form:radiobutton disabled="true" path="readRegAnswersLt[${status.index}].answer" data-count="${status.index}" value="option4" checked="${readRegAnswer.answer=='option4' ? 'checked' : ''}" />&nbsp; ${readRegAnswer.readRegQuestions.option4}</td></tr> 			
		  </tr>
		  <c:if test="${readRegAnswer.answer != readRegAnswer.readRegQuestions.answer }">
		  <tr><td colspan="5" height="10"><div style="color:green;">Correct Answer : ${readRegAnswer.readRegQuestions.answer}</div></td></tr>
		  </c:if>
		  </table>
		</td></tr>
		<tr><td height="10"></td></tr>
	</c:forEach> 
   <tr><td height="10">
		<table width="50%"><tr><td class="tabtxt" width="20%"><font color="green">Corrects:</font> &nbsp;&nbsp;${count}</td><td class="tabtxt"  width="20%"><font color="red">Wrongs:</font> &nbsp;&nbsp;${5-count}</td></tr></table>
   </tr></td>
</c:when>
</c:choose>
</table>
</form:form>
<div align="left" id="showResultDiv" style="padding-left:1em;font-weight:bold;"></div>
</body>
</html>