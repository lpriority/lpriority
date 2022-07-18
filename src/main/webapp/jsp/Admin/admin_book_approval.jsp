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
<script src="resources/javascript/admin/common_dropdown_pull.js"></script>
<script type="text/javascript" src="resources/javascript/gs_sortable.js"></script>
<link rel="stylesheet" href="resources/css/tooltip.css" />
<script>
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
<style>
#bookName {
	width: 250px;
	height: 25px;
}
</style>
</head>
<body>
<input type="hidden" name="user" id="user" value="${userReg.user.userType}" />
<div class="container-fluid dboard">
  <div class="row">
    <div class="col-md-12 text-right">
      <ul class="button-group">
        <li><a href="getRRReview.htm" class="${(divId =='rr_review')?'buttonSelect leftPill':'button tooLongTitle leftPill'}"> Review Activity</a></li>
        <li><a href="getStudentReadingRegister.htm" class="${(divId == 'prr' )?'buttonSelect':'button tooLongTitle'}">Scored Activity</a></li>
        <li><a href="getStudentRRReports.htm" class="${(divId == 'rr_reports' )?'buttonSelect':'button tooLongTitle'}">RR Report</a></li>
        <li><a href="getTeacherReadingRegister.htm" class="${(divId == 'Reading Register' )?'buttonSelect':'button tooLongTitle'}">Reading Register</a></li>
        <li><a href="bookApproval.htm" class="${(divId =='book_approval')?'buttonSelect tooLongTitle rightPill':'button rightPill'}">Book Approval</a></li>
      </ul>
    </div>
  </div>
  <div class="row">
    <div class="col-md-12 sub-title title-border">
      <div id="title">Book Approval</div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-3">
      <div class="form-group row">
        <label class="col-sm-3 col-form-label label">Grade</label>
        <div class="col-sm-9">
          <select id="gradeId" name="gradeId" style="width:120px;" class="listmenu" onchange="clear(); getBooksToApprove();">
            <option value="select">Select Grade</option>
            <c:forEach items="${grList}" var="ul">
              <option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
            </c:forEach>
          </select>
        </div>
      </div>
    </div>
    <div class="col-md-4">
      <div class="form-group row">
        <label class="col-sm-3 col-form-label label">Academic Year</label>
        <div class="col-sm-9">  
          <select id="academicYear" onchange="clear(); getBooksToApprove();" name="academicYear" style="width:128px;" class="listmenu">
            <option value="select">select year</option>
            <c:forEach items="${academicYears}" var="academicYear" varStatus="count">
              <option value="${academicYear.academicYearId }" 
				 	<c:if test="${academicYear.isCurrentYear == 'YES'}"> selected="Selected" </c:if> >${academicYear.academicYear}</option>
            </c:forEach>
          </select>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-12 text-center emptyspace"></div>
  </div>
  <div class="row">
    <div class="col-md-12 text-center">
      <div id="contentDiv"></div>
    </div>
  </div>
</div>
<div id="addBookDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;">
  <iframe id='bookIframe' width="98%" height="95%" style="border-radius:1em;"></iframe>
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
</body>
</html>
