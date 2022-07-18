<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Scored Activity</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/Student/reading_register_student.js"></script>
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="resources/css/tooltip.css" />
<script>
  function getGradedActivites(){
  	var gradeId = $("#gradeId").val();
  	var sortBy = $("#sortBy").val();
  	var studentId = $("#studentId").val();
  
  	if(studentId != "select" && gradeId!="select" && sortBy!="select")
	{
  		$("#loading-div-background").show();
		$.ajax({
			type : "POST",
			url : "sortActivities.htm",
			data : "studentId=" + studentId+"&sortBy="+sortBy,
			success : function(response) {
				$("#contentDiv").empty();
				$("#contentDiv").append(response);
				$("#loading-div-background").hide();
			}
		});
	}
  	else if(gradeId != "select" && studentId=="select" && sortBy!="select"){
  		$("#loading-div-background1").show();
		$.ajax({
			type : "POST",
			data : "gradeId=" + gradeId+"&sortBy="+sortBy,
			url : "getGradedActivities.htm",
			success : function(response) {
				$("#contentDiv").empty();
				$("#contentDiv").append(response);
				$("#loading-div-background1").hide();
			}
		});
	}
  	else{
  		systemMessage("ddddd");
		alert("Please select the grade");
		$("#contentDiv").empty();
		return false;
	}
  }  
  $(function() {
	    $( "#rows" ).accordion({
	      collapsible: true,
	      active: false,
	      activate:function(event, ui ){
	  		setFooterHeight();
	    }
	    });    
	});
$( function() {
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


</script>
<style type="text/css">
.ui-accordion > .ui-widget-header {
	background: #94B8FF;
}
.ui-accordion > .ui-widget-content {
	background: #e6edee;
}
.ui-accordion > .ui-accordion-header {
	font-size: 100%;
	background:linear-gradient(to bottom, #f7feff 7%, #ffffff 33%, #ffffff 48%, rgb(222, 228, 228) 94%);
}
.ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active {
	border:1px solid #b4c3c5;
}
.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {
	border:1px solid #b5cfd4;
}
.ui-state-hover, .ui-widget-content .ui-state-hover, .ui-widget-header .ui-state-hover, .ui-state-focus, .ui-widget-content .ui-state-focus, .ui-widget-header .ui-state-focus {
	border:1px solid #b5cfd4;
}
*:focus {
	outline:none !important
}
</style>
</head>
<body>
<div class="container-fluid dboard">
  <div class="row">
    <div class="col-md-12 text-right">
      <ul class="button-group">
        <li><a href="getRRReview.htm" class="${(divId =='rr_review')?'buttonSelect leftPill':'button tooLongTitle leftPill'}"> Review Activity</a></li>
        <li><a href="getStudentReadingRegister.htm" class="${(divId == 'prr' )?'buttonSelect tooLongTitle':'button tooLongTitle'}">Scored Activity</a></li>
        <li><a href="getStudentRRReports.htm" class="${(divId == 'rr_reports' )?'buttonSelect':'button tooLongTitle'}">RR Report</a></li>
        <li><a href="getTeacherReadingRegister.htm" class="${(divId == 'Reading Register' )?'buttonSelect':'button tooLongTitle'}">Reading Register</a></li>
        <li><a href="bookApproval.htm" class="${(divId =='book_approval')?'buttonSelect rightPill':'button tooLongTitle rightPill'}">Book Approval</a></li>
      </ul>
    </div>
  </div>
  <div class="row">
    <div class="col-md-12 sub-title title-border">
      <div id="title">Scored Activity </div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-3">
      <div class="form-group row">
        <label class="col-sm-3 col-form-label label">Grade</label>
        <div class="col-sm-9">
          <select id="gradeId" name="gradeId" onchange="clear();getStudentsByGradeId('graded');getGradedActivites();" style="width:120px;" class="listmenu">
            <option value="select">select grade</option>
            <c:forEach items="${grList}" var="ul">
              <option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
            </c:forEach>
          </select>
        </div>
      </div>
    </div>
    <div class="col-md-3">
      <div class="form-group row">
        <label class="col-sm-3 col-form-label label">Student</label>
        <div class="col-sm-9">
          <select id="studentId" onchange="getGradedActivites();" style="width:120px;" class="listmenu">
            <option value="select"  selected>Select Student</option>
          </select>
        </div>
      </div>
    </div>
    <div class="col-md-3">
      <div class="form-group row">
        <label class="col-sm-3 col-form-label label">Sort By</label>
        <div class="col-sm-9">
          <select id="sortBy" onchange="getGradedActivites()">
            <option value="createDate" selected="selected">Date</option>
            <option value="readRegActivity.activityId">Activity</option>
            <option value="pointsEarned">Points earned</option>
          </select>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-12 text-center emptyspace"></div>
  </div>
  <div class="row">
    <div class="col-md-12">
      <div id="contentDiv"></div>
    </div>
  </div>
</div>
<div id="reviewDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;">
  <iframe id='reviewIframe' width="98%" height="95%" style="border-radius:1em;"></iframe>
</div>
<div id="retellDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;">
  <iframe id='retellIframe' width="98%" height="95%" style="border-radius:1em;"></iframe>
</div>
<div id="pictureDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;">
  <iframe id='pictureIframe' width="98%" height="95%" style="border-radius:1em;"></iframe>
</div>
<div id="createQuizDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;">
  <iframe id='createQuizIframe' width="98%" height="95%" style="border-radius:1em;"></iframe>
</div>
<div id="loading-div-background" class="loading-div-background" style="display:none;">
  <div class="loader"></div>
  <div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;"> <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    Loading.... </div>
</div>
<div id="loading-div-background1" class="loading-div-background" style="display:none;">
  <div class="loader"></div>
  <div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;"> <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    Loading.... </div>
</div>
</body>
</html>
