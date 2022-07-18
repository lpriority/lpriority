<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Class Files</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<!-- <script src="resources/javascript/jquery/jquery-2.1.4.js"></script> -->
<!-- <script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script> -->
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="/dwr/interface/curriculumService.js"></script>
<script type="text/javascript" src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript" src="/dwr/interface/teacherSchedulerService.js"></script>
<script type="text/javascript" src="resources/javascript/my_files.js"></script>
<script > 
function togglePage(thisVar, url, name, fileType){
	$("#tf-loading-div-background").show();
	$.ajax({
		type : "GET",
		url : url+".htm",
		data: "fileType="+fileType,
		success : function(response) {
			storeValue('ajaxPath', "/"+url+".htm");
			storeValue('fileType', fileType);
			$("#contentDiv").empty();
			$("#contentDiv").append(response);
			$("#subTitle").html(name);
			var current = document.getElementsByClassName("buttonSelect");
		    current[0].className = current[0].className.replace(" buttonSelect", " button");
		    var activeElement = $(thisVar);
		    activeElement.addClass('buttonSelect');
		    activeElement.removeClass('button');
		    $('title').text(name);
	  		$("#tf-loading-div-background").hide();
		}
	});
}

function togglePage_tchr(thisVar, url, name, fileType, page){
	$("#tf-loading-div-background").show();
	$.ajax({
		type : "GET",
		url : url+".htm",
		data: "fileType="+fileType+"&page="+page,
		success : function(response) {
			storeValue('ajaxPath', "/"+url+".htm");
			storeValue('fileType', fileType);
			$("#contentDiv").empty();
			$("#contentDiv").append(response);
			$("#subTitle").html(name);
			var current = document.getElementsByClassName("buttonSelect");
		    current[0].className = current[0].className.replace(" buttonSelect", " button");
		    var activeElement = $(thisVar);
		    activeElement.addClass('buttonSelect');
		    activeElement.removeClass('button');
		    $('title').text(name);
	  		$("#tf-loading-div-background").hide();
		}
	});
}

</script>
</head>
<body  >
<div class="container-fluid dboard">
  <c:if test="${page ne 'uploadFile'}">
    <div class="row">
      <div class="col-md-12 text-right">
        <ul class="button-group">
          <li><a href="#" onclick="togglePage_tchr(this, 'displayTeacherFiles', 'Class Files', 'public', 'public')"  class="btn ${(fileType == 'public')?'buttonSelect tooLongTitle leftPill':'button tooLongTitle leftPill'}">Class Files</a></li>
          <li><a href="#" onclick="togglePage(this, 'displayTeacherFiles', 'Private Files', 'private')" class="btn ${(fileType == 'private')?'buttonSelect':'button tooLongTitle'}">Private Files</a></li>
          <li><a href="#" onclick="togglePage(this, 'loadTeacherAdminFiles', 'Admin Files', 'public')" class="btn ${(fileType == 'admin')?'buttonSelect':'button tooLongTitle'}">Admin File</a></li>
          <li><a href="#" onclick="togglePage(this, 'displayTeacherStudentFiles', 'Student Files', 'student')" class="btn ${(fileType == 'student')?'buttonSelect tooLongTitle rightPill':'button tooLongTitle rightPill'}">Student File</a></li>
        </ul>
      </div>
    </div>
  </c:if>
  <c:if test="${page ne 'uploadFile'}">
    <div class="row">
      <div class="col-md-12 sub-title title-border">
        <div id="title">${title}</div>
      </div>
    </div>
  </c:if>
  <div class="row">
    <div class="col-md-12 text-center emptyspace"></div>
  </div>
  <div class="row">
    <div class="col-md-12">
      <div id="contentDiv">
        <%@include file="teacher_files.jsp"%>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-12">
      <div id="tf-loading-div-background" class="loading-div-background" style="display:none;">
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
    </div>
  </div>
</div>
</body>
</html>