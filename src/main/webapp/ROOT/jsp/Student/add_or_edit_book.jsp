
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
 <link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
 <link rel="stylesheet" href="resources/css/tooltip.css" />
<%--<script type="text/javascript" src="resources/javascript/Student/reading_register_student.js"></script>
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="resources/javascript/common/img_viewer.js"></script>
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css">
<%@ include file="../CommonJsp/include_resources.jsp" %> --%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add/Edit a Book</title>
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
	  console.log(rating);
		setSelectedStarts(rating)
		
		$( document ).tooltip({
		    position: {
		      my: "center bottom-20",
		      at: "center top",
		      using: function( position, feedback ) {
		        $( this ).css( position );
		        $( "<div>" )
		          .addClass( "arrow" )
		          .addClass( feedback.vertical )
		          .addClass( feedback.horizontal )
		          .appendTo( this );
		      }
		    }
		  });
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
.QuizDailog{
 width:700px;
 height:392px;
}
</style>
</head>
<body>
<input type="hidden" name="userType" id="userType" value="${userReg.user.userType}" />
<input type="hidden" name="operation" id="operation" value="${mode}" />
<input type="hidden" name="user" id="user" value="${user}" />
<input type="hidden" name="actCount" id="actCount" value="0" />
<input type="hidden" name="bookTitId" id="bookTitId" value="0" />
 <table width="100%" id="tableDiv">
<form:form id="addBookForm" modelAttribute="readRegMaster" method="post" action="saveOrUpdateBook.htm">
	<%-- 	  --%>
		
		  <tr>
			<td height="0" colspan="2" align="left" valign="top" class="" style="padding-top:1em;">
				<table id="T3" width="100%" id='divtag' align="center">
						<tr>
							<td width="25%" height="10" align="right" valign="middle"></td>
							<td width="10%" height="10" align="center" valign="middle"></td>
							<td height="10" align="left" valign="middle">
							 <form:hidden path="titleId" value="${titleId}" />
							 <%-- <form:hidden path="grade.gradeId" value="${studentObj.grade.gradeId}" /> --%>
							 <form:hidden path="grade.gradeId" value="${gradeId}" />
							 <form:hidden path="mode" value="${mode}" />
							 <form:hidden path="rating" value="${rating}" />
							 <form:hidden path="approved" value="${approved}" />
							 <form:hidden path="createDate" />
							 <form:hidden path="userRegistration.regId" value="${studentRegId}" />							 
							</td>
						</tr>
						<tr>
							<td width="25%" height="20" align="right" valign="middle"><span
								class="tabtxt"><img src="images/Common/required.gif"> Book Title</span></td>
							<td width="10%" height="20" align="center" valign="middle">:</td>
							<td height="20" align="left" valign="middle"><span
								class="tabtxt"> <form:input path="bookTitle" id="bookTitle" style="width:250px;height:18px;" maxlength="90" onblur="checkBookExists(${masterGradesId})"/><font color="red">
										<form:errors path="bookTitle"></form:errors>
								</font>
							</span></td>
						 </tr>
						 <tr>
							<td width="25%" height="10" align="right" valign="middle"></td>
							<td width="10%" height="10" align="center" valign="middle"></td>
							<td height="10" align="left" valign="middle"></td>
						</tr>
						<tr>
							<td width="25%" height="20" align="right" valign="top"><span
								class="tabtxt"><img src="images/Common/required.gif"> Author</span></td>
							<td width="10%" height="20" align="center" valign="top">:</td>
							<td height="20" align="left" valign="middle"><span
								class="tabtxt"><form:input
										path="author" style="width:250px;height:18px;" maxlength="45"/><font
									color="red"> <form:errors path="author"></form:errors>
								</font> </span>
							</td>
						</tr>
						<tr>
							<td width="25%" height="10" align="right" valign="middle"></td>
							<td width="10%" height="10" align="center" valign="middle"></td>
							<td height="10" align="left" valign="middle"></td>
						</tr>
						<tr>
							<td width="25%" height="20" align="right" valign="middle"><span
								class="tabtxt"><img src="images/Common/required.gif"> No.of Pages</span></td>
							<td width="10%" height="20" align="center" valign="middle">:</td>
							<td height="20" align="left" valign="middle">
							<form:input	path="numberOfPages" type="number" required="required" min="0" max="999999" size="5" maxlength="5"/><font
								color="red"> <form:errors path="numberOfPages"></form:errors>
							</font>
							</td>
						</tr>
						<c:if test="${user == 'student'}">
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
						<!-- <tr>
							<td width="25%" height="10" align="right" valign="middle"></td>
							<td width="10%" height="10" align="center" valign="middle"></td>
							<td height="10" align="left" valign="middle"></td>
						</tr> -->
						<tr>
							<td width="100%" height="23" align="center" colspan="3" style="padding-top:1em;">
								<table width="60%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="20%" align="right" valign="middle">
											<c:choose>
											<c:when test="${mode eq 'create' }">
												<input type="button" class="button_green" value="Add Book" style="border:none" onclick="saveOrUpdateBook()">
											</c:when>
											<c:when test="${mode eq 'edit' }">
												<input type="button" class="button_green" value="Edit Book" style="border:none" onclick="saveOrUpdateBook()">
											</c:when>
											</c:choose>
											</td>
											<td width="20%" align="center" valign="middle">
										 <!-- <div class="button_green" onclick="$('#addBookForm')[0].reset();">Cancel</div> </td> -->
										 <input type="button" id="closeBut" class="button_green" value="Close" style="border:none;visibility:visible" onClick="closeDailog()"> </td>
										</tr>
										<!-- <tr><td>&nbsp;</td></tr> -->
										
										
								</table>
							</td>
						</tr>
						<tr><td>&nbsp;<br></td></tr>
						</table>
						
						<table id="viewActivity" width="100%" align="center">
						</table>
				
</td></tr></form:form>
</table>
</body>
<div id="reviewDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"><iframe id='reviewIframe' width="98%" height="95%" style="border-radius:1em;"></iframe></div>
<div id="retellDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"><iframe id='retellIframe' width="98%" height="95%" style="border-radius:1em;"></iframe></div>
<div id="pictureDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"><iframe id='pictureIframe' width="98%" height="95%" style="border-radius:1em;"></iframe></div>
<div id="createQuizDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" class="QuizDailog"><iframe id='createQuizIframe' width="98%" height="95%" style="border-radius:1em;"></iframe></div>
</html>