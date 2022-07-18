<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Parent Last Seen</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/teacher_popup.js"></script>
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<style type="text/css">
#atten, #module {
	border: 0px solid black;
}
</style>
<script type="text/javascript">


function loadStudentList() {
	var csId = $('#csId').val();
	var gradeId = $('#gradeId').val();
	var classId = $('#classId').val();
	var parentContainer = $(document.getElementById('parentLastDiv'));
	$(parentContainer).empty(); 
	if(csId > 0 && classId >0 && gradeId > 0){
		$("#loading-div-background").show();
		$.ajax({
			type : "GET",
			url : "getParentLastSeen.htm",
			data : "csId="+ csId,
			success : function(response) {
				$(parentContainer).append(response);
				$("#loading-div-background").hide();
			}
		});
	}else{
		alert("Please fill all the filters");
	}
}

function clearDiv(){
	$('#parentLastDiv').empty();
}
</script>
</head>
<body>
<div class="container-fluid dboard">
  <div class="row">
    <div class="col-md-12 sub-title title-border">
      <div id="title">Parent Last Seen</div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-3">
      <div class="form-group row">
        <label class="col-sm-3 col-form-label label">Grade</label>
        <div class="col-sm-9">
          <select name="gradeId" class="listmenu" id="gradeId" onChange="getGradeClasses();clearDiv();" 
					required="required">
            <option value="">select grade</option>
            <c:forEach items="${grList}" var="ul">
              <option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
            </c:forEach>
          </select>
        </div>
      </div>
    </div>
    <div class="col-md-3">
      <div class="form-group row">
        <label class="col-sm-3 col-form-label label">Class</label>
        <div class="col-sm-9">
          <select id="classId" name="classId" class="listmenu" onChange="getClassSections();clearDiv();"
											required="required">
            <option value="">select subject</option>
          </select>
        </div>
      </div>
    </div>
    <div class="col-md-3">
      <div class="form-group row">
        <label class="col-sm-3 col-form-label label">Section</label>
        <div class="col-sm-9">
          <select id="csId" class="listmenu" onChange="loadStudentList()" required="required">
            <option value="">select Section</option>
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
      <div id="parentLastDiv" class="text-center"></div>
    </div>
  </div>
</div>
</body>
</html>