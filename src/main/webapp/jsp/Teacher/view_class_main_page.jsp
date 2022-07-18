<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="resources/javascript/TeacherJs/TeacherViewClass.js"></script>
<script type="text/javascript" src="resources/javascript/imageloadfunctions.js"></script>
<c:if test="${divId ne 'RequestForClass'}">
  <script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
</c:if>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.dropdown {
	position: relative;
	display: inline-block;
}
.dropdown .dropdown-menu {
	position: absolute;
	top: 100%;
	display: none;
	margin: 0;
	list-style: none;
	width: 130%;
	padding: 0;
}
.dropdown:hover .dropdown-menu {
	display: block;
}
.dropdown button {
	background: #FF6223;
	color: #FFFFFF;
	border: none;
	margin: 0;
	padding: 0.4em 0.8em;
	font-size: 1em;
}
.dropdown a {
	display: block;
	text-decoration: none;
	background: #CCCCCC;
}
.dropdown a:hover {
	background: #BBBBBB;
}
.hidden {
	display: none;
}
.dropbtn {
	background-color: #4CAF50;
	color: white;
	padding: 16px;
	font-size: 16px;
	border: none;
	cursor: pointer;
}
.dropdown {
	position: relative;
	display: inline-block;
}
.dropdown-content {
	display: none;
	position: absolute;
	background-color: #f9f9f9;
	min-width: 160px;
	box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
}
.dropdown-content a {
	color: black;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
}
.dropdown-content a:hover {
	background-color: #f1f1f1
}
.dropdown:hover .dropdown-content {
	display: block;
}
.dropdown:hover .dropbtn {
	background-color: #3e8e41;
}
</style>
<script type="text/javascript">
	
	$(document).ready(function () {
	 	$('#returnMessage').fadeOut(4000);
	});
	/* $(function() {
	    $( "#dateToUpdate").datepicker({
	    	changeMonth: true,
	        changeYear: true,
	        showAnim : 'clip',
	        maxDate : 0
	    });
	}); */
	
	function togglePage(thisVar, url, name){
		$.ajax({
			type : "GET",
			url : url+".htm",
			success : function(response) {
				$("#contentDiv").empty();
				$("#commonDetailsPage").empty();
				$("#contentDiv").append(response);
				$("#subTitle").html(name);
				$("title").html(name);
				
				//Update main tab class
				var current = document.getElementsByClassName("buttonSelect");
			    current[0].className = current[0].className.replace(" buttonSelect", " button");
			    var activeElement = $(thisVar);
			    activeElement.addClass('buttonSelect');
			    activeElement.removeClass('button');
			    
			  	//Updating Sub menu class 
			    var current = document.getElementsByClassName("selectOnSubButton");
			    if(current.length > 0){
			    	current[0].className = current[0].className.replace(" selectOnSubButton", " selectOffSubButton");
			    }				    
			}
		});
	}
		
	function toggleInnerPage(thisVar, url, name){
		$.ajax({
			type : "GET",
			url : url+".htm",
			success : function(response) {
				$("#contentDiv").empty();
				$("#commonDetailsPage").empty();
				$("#contentDiv").append(response);
				$("#subTitle").html(name);
				$("title").html(name);
				
				//Update main tab class
				var current = document.getElementsByClassName("buttonSelect");
			    current[0].className = current[0].className.replace(" buttonSelect", " button");
			    var activeElement = $("#attendence");
			    activeElement.addClass('buttonSelect');
			    activeElement.removeClass('button');
			    
			    //Updating Sub menu class 
			    var current = document.getElementsByClassName("selectOnSubButton");
			    if(current.length > 0){
			    	current[0].className = current[0].className.replace(" selectOnSubButton", " selectOffSubButton");
			    }			    
			    var activeSubElement = $("#"+url);
			    activeSubElement.addClass('selectOnSubButton');
			    activeSubElement.removeClass('selectOffSubButton');
			}
		});
	}
</script>
<title>Class Roster</title>
</head>
<body>
<div class="container-fluid dboard">
  <c:if test="${gradebook != 'gradebook' }">
    <div class="row">
      <div class="col-md-12 text-right">
        <nav id="primary_nav_wrap">
          <ul class="button-group">
            <li><a href="#" onclick="togglePage(this, 'classRoster', 'Class Roster')" class="btn ${(divId == 'Roster')?'buttonSelect tooLongTitle leftPill':'button tooLongTitle leftPill'}"> Roster </a></li>
            <li> <a href="#" id="attendence" onclick="toggleInnerPage(this, 'takeAttendancePage', 'Attendence')" class="btn ${(divId == 'Attendance')?'buttonSelect tooLongTitle dropdown':'button tooLongTitle dropdown'}">Attendance</a>
              <ul>
                <li><a href="#" id="takeAttendancePage" onclick="toggleInnerPage(this, 'takeAttendancePage', 'Take Attendence')" class="btn ${(page == 'takeAttendance')?'selectOnSubButton':'selectOffSubButton'}" >Take Attendance</a></li>
                <li><a  href="#" id="updateAttendancePage" onclick="toggleInnerPage(this, 'updateAttendancePage', 'Update Attendence')" class="btn ${(page == 'updateAttendance')?'selectOnSubButton':'selectOffSubButton'}" >Update Attendance</a></li>
              </ul>
            </li>
            <li><a href="#" onclick="togglePage(this, 'classRegistration', 'Registration')" class="btn ${(divId == 'Registration')?'buttonSelect':'button tooLongTitle'}"> Registration </a></li>
            <li><a href="#" onclick="togglePage(this, 'showMyClassesTimeTable', 'Show My Classes')" class="btn ${(divId == 'showMyClasses')?'buttonSelect':'button tooLongTitle'}"> Show My Classes </a></li>
            <li><a href="#" onclick="togglePage(this, 'requestForClass', 'Request a Class')" class="btn ${(divId == 'RequestForClass')?'buttonSelect rightPill':'button tooLongTitle rightPill'}"> Request a Class </a></li>
          </ul>
        </nav>
      </div>
    </div>
  </c:if>
  <div class="row">
    <div class="col-md-12">
      <c:if test="${gradebook == 'gradebook' }">
        <div>
          <%@ include file="/jsp/Teacher/view_gradebook_tabs.jsp" %>
        </div>
      </c:if>
    </div>
  </div>
  <div class="row">
    <div class="col-md-12 sub-title title-border">
      <div id="title">Class Roster</div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-12">
      <div id="contentDiv">
        <%@include file="view_class_header_page.jsp"%>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-12">
      <div id="commonDetailsPage"></div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-12">
      <label id="returnMessage" style="color: blue;"><b>${hellowAjax}</b></label>
    </div>
  </div>
</div>
</body>
</html>