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
<title>RR Reports</title>
<script type="text/javascript" src="resources/javascript/Student/reading_register_student.js"></script>
<script type="text/javascript" src="resources/javascript/common/reading_register_report.js"></script>
<script>

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
$(function() {
	$("#fromId").datepicker({
		changeMonth : true,
		changeYear : true,
		showAnim : 'clip',
		maxDate : new Date()
	});
});
$(function() {
	$("#toId").datepicker({
		changeMonth : true,
		changeYear : true,
		showAnim : 'clip',
		maxDate : new Date()
	});
});
/* function clear(){
	$('#fromId').val('');
	$('#toId').val('');
} */
</script>

<!--
<style type="text/css">
.ui-accordion > .ui-widget-header {background: #94B8FF;}
.ui-accordion > .ui-widget-content {background: #e6edee;}
.ui-accordion > .ui-accordion-header {font-size: 100%;background:linear-gradient(to bottom,#f7feff 7%, #ffffff 33%, #ffffff 48%, rgb(222, 228, 228) 94%);}
.ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active{border:1px solid #b4c3c5;}
.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {border:1px solid #b5cfd4;}
.ui-state-hover, .ui-widget-content .ui-state-hover, .ui-widget-header .ui-state-hover, .ui-state-focus, .ui-widget-content .ui-state-focus, .ui-widget-header .ui-state-focus{border:1px solid #b5cfd4;}
*:focus {outline:none !important}
</style>
-->
</head>
<body>
<div class="container-fluid dboard">
  <div class="row">
    <div class="col-md-12 text-right">
      <ul class="button-group">
        <li><a href="getRRReview.htm" class="${(divId =='rr_review')?'buttonSelect leftPill':'button tooLongTitle leftPill'}"> Review Activity</a></li>
        <li><a href="getStudentReadingRegister.htm" class="${(divId == 'prr' )?'buttonSelect':'button tooLongTitle'}">Scored Activity</a></li>
        <li><a href="getStudentRRReports.htm" class="${(divId == 'rr_reports' )?'buttonSelect tooLongTitle':'button tooLongTitle'}">RR Report</a></li>
        <li><a href="getTeacherReadingRegister.htm" class="${(divId == 'Reading Register' )?'buttonSelect':'button tooLongTitle'}">Reading Register</a></li>
        <li><a href="bookApproval.htm" class="${(divId =='book_approval')?'buttonSelect rightPill':'button tooLongTitle rightPill'}">Book Approval</a></li>
      </ul>
    </div>
  </div>
  <form:form method="get" name="rrReports" id="rrReports" onsubmit="return validateForm(${userType});">
  <div class="row">
    <div class="col-md-12 sub-title title-border">
      <input type="hidden" id="userType" value="${userType}" />
      <input type="hidden" id="reportsType" name="reportsType" value="" />
      <div id="title">Reading Register Reports</div>
    </div>
  </div>
  <c:if test="${userType eq 3}">
    <div class="row">
      <div class="col-md-3">
        <div class="form-group row">
          <label class="col-sm-3 col-form-label label">Grade</label>
          <div class="col-sm-9">
            <select id="gradeId" name="gradeId" onchange="clear();getStudentsByGradeId('graded');" style="width:120px;" class="listmenu">
              <option value="select">Select Grade</option>
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
            <select id="studentId" name="studentId" style="width: 120px; height: 150px; overflow-y: scroll; color: black;" class="listmenu" multiple="">
              <option value="select"  selected>Select Student</option>
            </select>
          </div>
        </div>
      </div>
    </div>
  </c:if>
  <c:if test="${userType eq 2}">
    <div class="row">
      <div class="col-md-3">
        <div class="form-group row">
          <label class="col-sm-3 col-form-label label">Grade</label>
          <div class="col-sm-9">
            <select id="gradeId" name="gradeId" style="width:120px;" class="listmenu" onChange="clear()">
              <option value="select">Select Grade</option>
              <option value="0"> ALL</option>
              <c:forEach items="${grList}" var="ul">
                <option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
              </c:forEach>
            </select>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="form-group row">
          <label class="col-sm-3 col-form-label label">Start Date</label>
          <div class="col-sm-9">
            <input type="text" style="width: 90px" id="fromId" name="fromId" maxlength="15" readonly required />
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="form-group row">
          <label class="col-sm-3 col-form-label label">End Date</label>
          <div class="col-sm-9">
            <input type="text" style="width: 90px" id="toId" maxlength="15" name="toId" readonly required />
          </div>
        </div>
      </div>
    </div>
  </c:if>
  <div class="row">
    <div class="col-md-12 text-center emptyspace"></div>
  </div>
  <div class="row">
	<div class="col-md-1"></div>
    <div class="col-md-10">
      <div id="StudentReportList">
        <input type="submit" class="button_green" value="Download RR Reports As Excel" onClick="setAction('excel')">
        <input type="submit" class="button_green" value="Download RR Reports As PDF" onClick="setAction('pdf')">
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-12 text-center">
      <div id="rrReportsDetailsDiv"></div>
    </div>
  </div>
</div>
</form:form>
<div id="activityDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;">
  <iframe id='activityIframe' width="98%" height="95%" style="border-radius:1em;"></iframe>
</div>
<!-- <div id="loading-div-background" class="loading-div-background" style="display:none;">
	<div class="loader"></div>
	<div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;">
		<br><br><br><br><br><br><br>Loading....
	</div>
</div> -->
<div id="loading-div-background2" class="loading-div-background"
	style="display: none;">
  <div class="loader"></div>
  <div id="loading-div" class="ui-corner-all loading-div"
		style="color: #103c51; left: 50%; padding-left: 12px; font-size: 17px; margin-top: -8.5em;"> <br>
    <br>
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
