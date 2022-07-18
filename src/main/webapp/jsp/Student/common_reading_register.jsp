<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Reading Register</title>
</head>
<script>
function togglePage(thisVar,url, name){
	$("#common-loading-div-background").show();
	$.ajax({
		type : "GET",
		url : url+".htm",
		success : function(response) { 
 		 	$("#contentDiv").empty();
			$("#contentDiv").append(response);
			$("#tab_title").html(name);
			$("title").html(name);
			var current = document.getElementsByClassName("buttonSelect");
		    current[0].className = current[0].className.replace(" buttonSelect", " button");
		    var activeElement = $(thisVar);
		    activeElement[0].className = activeElement[0].className.replace(" button"," buttonSelect");
		    $("#common-loading-div-background").hide();
	 	}
	}); 
}
</script>
<body>
<div class="container-fluid dboard">
  <c:choose>
    <c:when test="${userReg.user.userType == 'admin' || userReg.user.userType == 'teacher' }">
      <div class="row">
        <div class="col-md-12 text-right">
          <ul class="button-group">
            <li><a href="getRRReview.htm" class="${(divId =='rr_review')?'buttonSelect leftPill':'button tooLongTitle leftPill'}"> Review Activity</a></li>
            <li><a href="getStudentReadingRegister.htm" class="${(divId == 'prr' )?'buttonSelect':'button tooLongTitle'}">Scored Activity</a></li>
            <li><a href="getStudentRRReports.htm" class="${(divId == 'rr_reports' )?'buttonSelect':'button tooLongTitle'}">RR Report</a></li>
            <li><a href="getTeacherReadingRegister.htm" class="${(divId == 'Reading Register' )?'buttonSelect tooLongTitle':'button tooLongTitle'}">Reading Register</a></li>
            <li><a href="bookApproval.htm" class="${(divId =='book_approval')?'buttonSelect rightPill':'button tooLongTitle rightPill'}">Book Approval</a></li>
          </ul>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12 sub-title title-border">
          <div id="title">Reading Register </div>
        </div>
      </div>
    </c:when>
    <c:otherwise>
      <div class="row">
        <div class="col-md-12 text-right">
          <ul class="button-group">
            <li><a href="#" onclick = "togglePage(this,'studentReading','Reading Register')" class="btn ${(divId == 'Reading Register' )?'buttonSelect tooLongTitle leftPill':'button tooLongTitle'}">Reading Register</a></li>
            <li><a href="#" onclick = "togglePage(this,'returnedBooks','Returned Books')" class="btn ${(divId =='Returned Books')?'buttonSelect':'button tooLongTitle'}">Returned Books</a></li>
            <li><a href="#" onclick = "togglePage(this,'personalReadingRegister','Scored Activity')" class="btn ${(divId =='Personal Reading Register')?'buttonSelect rightPill':'button tooLongTitle rightPill'}">Scored Activity</a></li>
          </ul>
        </div>
      </div>
      <div class="row">
        <div id="tab_title" class="col-md-12 sub-title title-border">
          <c:if test="${divId == 'Returned Books'}">
            <div id="title">Returned Books</div>
          </c:if>
          <c:if test="${divId != 'Returned Books'}">
            <div id="title">View Reading Register</div>
          </c:if>
        </div>
      </div>
    </c:otherwise>
  </c:choose>
  <div class="row">
    <div class="col-md-12 text-center">
      <div id="contentDiv">
        <c:if test="${divId == 'Returned Books'}">
          <%@include file="../Student/returned_books.jsp"%>
        </c:if>
        <c:if test="${divId != 'Returned Books'}">
          <%@include file="../Student/reading_register_view.jsp"%>
        </c:if>
      </div>
    </div>
  </div>
</div>
<div id="common-loading-div-background" class="loading-div-background" style="display:none;">
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