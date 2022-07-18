<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.min.css" rel="stylesheet">
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/CommonValidation.js"></script>
<script type="text/javascript" src="resources/javascript/common/my_profile.js"></script>
<script src="resources/javascript/imageloadfunctions.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<script src="/resources/javascript/notify/notify.js"></script>
<script type="text/javascript">
function togglePage(thisVar, url, name){
	$.ajax({
		type : "GET",
		url : url+".htm",
		success : function(response) {
			$("#contentDiv").empty();
			$("#contentDiv").append(response);
			$("#subTitle").html(name);
			var current = document.getElementsByClassName("buttonSelect");
		    current[0].className = current[0].className.replace(" buttonSelect", " button");
		    var activeElement = $(thisVar);
		    activeElement.addClass('buttonSelect');
		    activeElement.removeClass('button');
		    $('title').text(name);
		}
	});
}

function resetForm(formId){
	$("#"+formId)[0].reset();
}

</script>
<title>Personal Information</title>
</head>
<body>
<div class="container-fluid dboard">
  <div class="row">
    <div class="col-md-12 text-right">
      <nav id="primary_nav_wrap">
        <c:choose>
          <c:when test="${page != 'update school sports'}">
            <ul id="myDiv" class="button-group">
              <li><a href="#" onClick="togglePage(this, 'personalInfo', 'Personal Information')" class="btn ${(page =='Personal Info')?'buttonSelect leftPill tooLongTitle':'button leftPill tooLongTitle'}"> Personal Info </a></li>
              <li><a href="#" onClick="togglePage(this, 'changeUserName', 'Change User Name')" class="btn ${(page == 'Change User Name')?'buttonSelect tooLongTitle tooLongTitle':'button tooLongTitle tooLongTitle'}"> Change User Name </a></li>
              <li><a href="#" onClick="togglePage(this, 'changePassword', 'Change Password')" class="btn ${(page == 'Change Password')?'buttonSelect tooLongTitle':'button tooLongTitle'}"> Change Password</a></li>
              <c:if test="${userRegistration.user.userTypeid ne '4' }">
                <li><a href="#" onClick="togglePage(this, 'schoolInfo', 'School Information')" class="btn ${(page=='School Information')?'buttonSelect tooLongTitle':'button tooLongTitle'}"> School Information </a></li>
                <li><a href="#" onClick="togglePage(this, 'personalInterest', 'Personal Interest')" class="btn ${(page == 'Personal Interest')?'buttonSelect tooLongTitle':'button tooLongTitle'}"> Personal Interest </a></li>
              </c:if>
              <c:if test="${userRegistration.user.userTypeid eq '4'}">
                <li><a href="#" onClick="togglePage(this, 'personalInterest', 'Personal Interest')" class="btn ${(page == 'Personal Interest')?'buttonSelect rightPill':'button rightPill'}"> Personal Interest </a></li>
              </c:if>
              <c:if test="${userRegistration.user.userTypeid eq '2'}">
                <li><a href="#" onClick="togglePage(this, 'homePage', 'Home Page')" class="btn ${(page=='Home Page')?'buttonSelect rightPill tooLongTitle':'button rightPill tooLongTitle'}">Home Page</a></li>
              </c:if>
              <c:if test="${userRegistration.user.userTypeid eq '3'}">
                <li><a href="#" onClick="togglePage(this, 'homePage', 'Home Page')" class="btn ${(page=='Home Page')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Home Page</a></li>
              </c:if>
              <c:if test="${userRegistration.user.userTypeid eq '3' || userRegistration.user.userTypeid eq '5' || userRegistration.user.userTypeid eq '6'}">
                <li><a href="#" onClick="togglePage(this, 'educationalInfo', 'Change Educational Information')" class="btn ${(page == 'Educational Info')?'buttonSelect rightPill tooLongTitle':'button rightPill tooLongTitle'}"> Educational Info </a></li>
              </c:if>
            </ul>
          </c:when>
          <c:otherwise>
            <div class="lp-menu-tab">
              <%@include file="/jsp/Admin/school_info_tabs.jsp"%>
            </div>
          </c:otherwise>
        </c:choose>
      </nav>
    </div>
  </div>
  <c:if test="${page eq 'Personal Info'}">
    <div class="row">
      <div class="col-md-12 sub-title title-border">
        <div id="title">Personal Information </div>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <div id="contentDiv">
          <%@include file="personal_info.jsp"%>
        </div>
      </div>
    </div>
  </c:if>
</div>
</body>
</html>