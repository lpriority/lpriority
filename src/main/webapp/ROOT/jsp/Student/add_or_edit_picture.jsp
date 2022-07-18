<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en" dir="ltr" >
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<title>Camera and Video Control with HTML5 Example</title>
<meta name="description" content="Access the desktop camera and video using HTML, JavaScript, and Canvas.  The camera may be controlled using HTML5 and getUserMedia." />
<style type=text/css id=main-styles></style>
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script src="resources/javascript/my_recorder/recorder.js"></script>
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css">
<%@ include file="../CommonJsp/include_resources.jsp" %>
<script type="text/javascript" src="resources/javascript/Student/reading_register_student.js"></script>
</head>
<script>
$(document).ready(function(){
	 var usersFilePath = $('#usersFilePath').val();
	 var regId = $('#regId').val();
	 checkFileExists(usersFilePath, regId, "preview");
	 
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
<body>
<c:if test="${activityScore.approveStatus ne 'approved' }">
	<div align="center"> <video id="video" width="200" height="240" autoplay></video></div>
	<button id="snap">Capture Photo</button>
	<canvas id="canvas" width="640" height="480" hidden="true"></canvas>
	<input type="hidden" id="isCaptured" value="false">
	<!-- <button name="upload" onclick="UploadCapturedImage()">Upload</button>
	 -->
	 
 </c:if>
<input type="hidden" name="userType" id="userType" value="${userReg.user.userType}" />
<input type="hidden" name="regId" id="regId" value="${userReg.regId}" />
<input type="hidden" name="usersFilePath" id="usersFilePath" value="${usersFilePath}" />
<input type="hidden" name="operation" id="operation" value="${mode}" />
<input type="hidden" name="studentGradeId" id="studentGradeId" value="${studentObj.grade.masterGrades.masterGradesId}" />
<form id="uploadPictureForm" name="uploadPictureForm" action="uploadRRPicture.htm" method="POST"> 	
<input type="hidden" name="titleId" id="titleId" value="${titleId}" />
<input type="hidden" name="rating" id="rating" value="${rating}" />
<table width="100%" id="tableDiv" style="padding-top:2em;">
<tr style="position:relative;">
	<td width="20%" height="20" align=center valign="middle"><span class="tabtxt">Upload &nbsp;<img src="images/Common/required.gif"></span></td>
	<td width="10%"><input type="file" name="file" id="file" required="required" onchange="uploadRRPicture('${mode}')" ${activityScore.approveStatus eq 'approved'? 'disabled' : '' }/></td> 	
</tr>
<tr><td height="30" align="center" colspan="2"></td></tr>
<tr><td align="center" colspan="2"><div id="contentDiv" style="display:none;"><img id="preview" src="" width="130" height="130"></div></td></tr>
<tr >
	<td align="center" colspan="2">
		<table>
			<c:if test="${showRubric == 'yes'}">		
				<tr>
					<td width="25%" height="20" align="right" valign="middle"><span
						class="tabtxt">Choose a Score</span> &nbsp;<img src="images/Common/required.gif"></td>
					<td width="10%" height="20" align="center" valign="middle">:</td>
					<td height="20" align="left" valign="middle"><span class="tabtxt"> 
						<select id="rubricScore" style="width:180px;overflow: auto;" ${activityScore.approveStatus eq 'approved'? 'disabled' : '' }>
							<c:forEach items="${rubrics}" var="rubric">
								<option value="${rubric.readRegRubricId}" ${activityScore.selfScore.readRegRubricId == rubric.readRegRubricId?'selected': '' }>${rubric.score } - ${rubric.description}</option>
							</c:forEach>
						</select>
					</span></td>
				 </tr>	
				</c:if>	
				 <c:if test="${rating eq 0}">
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
			 	<%-- <c:if test="${empty activityScore.pointsEarned}"> --%>
			 	<c:if test="${activityScore.approveStatus ne 'approved'}">
				 	<tr>
						<td width="100%" height="23" align="center" colspan="3" style="padding-top:1em;">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="8%" align="center" valign="middle" colspan="2">
											<input type="button" class="button_green" value="Submit Changes" style="border:none" onclick="validateImageFileType();saveOrUpdatePicture('${mode}',${page});">
										</td>
									</tr>
									<tr><td>&nbsp;</td></tr>
							</table>
						</td>
					</tr>
				</c:if>			
		</table>
	</td>
</tr>
</table>
<div id="rr-pic-loading-div-background" class="loading-div-background" style="display:none;">
	<div class="loader"></div>
	<div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;">
		<br><br><br><br><br><br><br>Loading....
	</div>
</div>

</form>
<script>

// Put event listeners into place
window.addEventListener("DOMContentLoaded", function() {
// Grab elements, create settings, etc.
    var canvas = document.getElementById('canvas');
    var context = canvas.getContext('2d');
    var video = document.getElementById('video');
    var mediaConfig =  { video: true };
    var errBack = function(e) {
    console.log('An error has occurred!', e)
    };

// Put video listeners into place
	if(navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
	    navigator.mediaDevices.getUserMedia(mediaConfig).then(function(stream) {
			//video.src = window.URL.createObjectURL(stream);
			video.srcObject = stream;
			video.play();
        });
    }

    /* Legacy code below! */
    else if(navigator.getUserMedia) { // Standard
		navigator.getUserMedia(mediaConfig, function(stream) {
			video.src = stream;
			video.play();
		}, errBack);
	} else if(navigator.webkitGetUserMedia) { // WebKit-prefixed
		navigator.webkitGetUserMedia(mediaConfig, function(stream){
			video.src = window.webkitURL.createObjectURL(stream);
			video.play();
		}, errBack);
	} else if(navigator.mozGetUserMedia) { // Mozilla-prefixed
		navigator.mozGetUserMedia(mediaConfig, function(stream){
			video.src = window.URL.createObjectURL(stream);
			video.play();
		}, errBack);
	}

	// Trigger photo take
	document.getElementById('snap').addEventListener('click', function() {
		context.drawImage(video, 0, 0, 640, 480);
		UploadCapturedImage();
	});
}, false);



function UploadCapturedImage(){
	var titleId= document.getElementById("titleId").value;
	  var c = document.getElementById("canvas");
	//  var ctx = c.getContext("2d"); 
	 // var img = document.getElementById("tst");
	  // ctx.drawImage(img, 20, 20);
	  var dataURL = c.toDataURL("image/png");
	 
	  
	  var formData = new FormData();
		formData.append('dataURL', dataURL);
		formData.append('titleId', titleId);
		 console.log(formData);
		 $("#rr-pic-loading-div-background").show();
		$.ajax({
			url: "uploadRRPictureData.htm",
		    type: 'POST',
		    data: formData,
		    contentType: false,
		    processData: false,
		    success: function(data) {
		    	//var parsedData = JSON.parse(data);
				systemMessage(data.status);	
				var regId = $('#regId').val();	
				$("#isCaptured").val(true);
				console.log(regId);
				var usersFilePath = data.usersFilePath;
				console.log(usersFilePath);
				checkFileExists(usersFilePath, regId, "preview");
		    	$('#status').fadeIn().html("<font face='courier' color='red'><b>Uploaded !!</b></font>");
		    	setTimeout(function() {
					$('#status').fadeOut("slow");
				}, 500 );
		    	$("#rr-pic-loading-div-background").hide();
		    }
		});
	  return true;
	}
</script>
</div>
</main>

</body>
</html>