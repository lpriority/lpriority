<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<script src="resources/javascript/admin/common_dropdown_pull.js"></script>
<script type="text/javascript" src="resources/javascript/admin/announcements.js"></script>
<script type="text/javascript" src="resources/javascript/admin/events.js"></script>
<script type="text/javascript">


$(document).ready(function () {
	 $('#returnMessage').fadeOut(3000);
	 $(document).on('change',"#schoolId", function(){
    });
});


function transferStudents(callback) {
	
	var sel = document.getElementById("sGradeId"); 
	  var gradeId = sel.options[sel.selectedIndex].value; 
	  var gradeName = sel.options[sel.selectedIndex].text;
	  console.log(gradeId, gradeName);
	
    var obj =	document.getElementById("newschoolId");
    var schoolId = obj.value;
    var regId = $('#studentRegId').val();
    var classId = 1346;
    var csId = 1442;
    
	if(regId > 0 && schoolId >0 && gradeId > 0){
		$("#loading-div-background").show();
		$.ajax({
			type : "GET",
			url : "transferStudents.htm",
			data : "schoolId=" + schoolId + "&regId=" + regId + "&gradeId=" + gradeId + 
			"&classId=" + classId + "&csId="+ csId+
			"&gradeName=" +gradeName,
			success : function(response) {
				alert(response);
			if( response=="Student Transfered")
				{
				var x = document.getElementById("studentRegId");
				x.remove(x.selectedIndex);
				}
				$("#subViewDiv").html(response);
				$("#loading-div-background").hide();
				if(callback)
					 callback();
			}
		});
	}else{
		alert("Please fill all the filters");
	}
}


function getGradesBySchoolId(callback){
	
	  var obj =	document.getElementById("schoolId");
	  if(obj){
		  var schoolId = obj.value;
		  console.log("hi "+schoolId);
		  if(schoolId  != 'select'){
			  clear();
				$("#loading-div-background").show();
				$.ajax({
					type : "GET",
					url : "getSchoolGrades.htm",
					data : "schoolId=" + schoolId,
					success : function(response) {
						$("#sGradeId").empty();
						$("#sGradeId").append($("<option></option>").
								val('select').html(
								'Select Grade'));
						$.each(response, function(index, value) {
							$("#sGradeId").append($("<option></option>").
								val(value.gradeId).html(
								value.masterGrades.gradeName));
						});
						$("#loading-div-background").hide();
						if(callback)
						 callback();
					}
				}); 
		  }

		}
	}

function getStudentsBySGradeId(callback){
	  var obj =	document.getElementById("sGradeId");
	  if(obj){
		  var gradeId = obj.value;
		  if(gradeId  != 'select'){
			  clear();
				$("#loading-div-background").show();
				$.ajax({
					type : "GET",
					url : "getStudentsByGrade.htm",
					data : "gradeId=" + gradeId,
					success : function(response) {
						var students = response.students;
						$("#studentRegId").empty();
						$("#studentRegId").append($("<option></option>").
								val('select').html(
								'Select Student'));
						$.each(students, function(index, value) {
							$("#studentRegId").append($("<option></option>").
								val(value.userRegistration.regId).html(
								value.userRegistration.emailId));
						});
						$("#loading-div-background").hide();
						if(callback)
						 callback();
					}
				}); 
		  }

		}
	}


function clear(){
	var gradeId = $('#sGradeId').val();
	console.log("grade id "+gradeId);
	//if(gradeId == 'select'){
		$("#studentRegId").empty();
		$("#studentRegId").append($("<option></option>").val('select').html('Select Student'));

	//}
	var schoolId = $('#schoolId').val();
	console.log("school id "+schoolId);
	if(schoolId == 'select'){
		$("#sGradeId").empty();
		$("#sGradeId").append($("<option></option>").val('select').html('Select Grade'));
		$("#studentRegId").empty();
		$("#studentRegId").append($("<option></option>").val('select').html('Select Student'));

	}
}


</script>
</head>
<body>
<%@include file="Layout.jsp"%>
<div class="container-fluid">
  <div class="row">
    <div class="col-md-3">
      <%@include file="Menuinfo.jsp"%>
    </div>
    <div class="col-md-9">
      <div class="row">
        <div class="col-md-12 sub-title title-border">
          <div id="title" style="min-height:15px"> Student Transfer </div>
          <div style="text-align: right; margin-top:-28px;"> <a href="gotoDashboard.htm" type="button" class="btn btn-sm btn-primary"> Home </a> </div>
        </div>
      </div>
      <div class="col-md-12 text-center" id= "returnMessage" style="color: red"> <font color="blue">
        <label id=returnMessage style="visibility: visible;">${status}</label>
        </font> </div>
      <div class="row tblbgcolor">
        <div class="col-md-2"></div>
        <div class="col-md-8">
          <div class="col-md-12" style="padding-top:15px;padding-bottom:15px;">
            <div class="form-group">
              <div class="col-md-4">From School Name</div>
              <div class="col-md-6">
                <select name="schoolId" id="schoolId" onChange="getGradesBySchoolId();" required="required" class="desktop_select" style="border:1px solid #97ced6;color:white;">
                  <option value="select" selected>Select School</option>
                  <c:forEach items="${sList}" var="sl">
                    <option value='${sl.schoolId}'>${sl.schoolName} 
                  </c:forEach>
                </select>
              </div>
            </div>
          </div>
          <div class="col-md-12" style="padding-bottom:15px;">
            <div class="form-group">
              <div class="col-md-4">To School Name </div>
              <div class="col-md-6">
                <select name="newschoolId" id="newschoolId" required="required" class="desktop_select" style="border:1px solid #97ced6;color:white;">
                  <option value="select" selected>Select School</option>
                  <c:forEach items="${sList}" var="sl">
                    <option value='${sl.schoolId}'>${sl.schoolName} 
                  </c:forEach>
                </select>
              </div>
            </div>
          </div>
          <div class="col-md-12" style="padding-bottom:15px;">
            <div class="form-group">
              <div class="col-md-4">Grade</div>
              <div class="col-md-6">
                <select name="sGradeId" id="sGradeId" onChange="getStudentsBySGradeId();" required="required" class="desktop_select" style="border:1px solid #97ced6;color:white;">
                  <option value="select" selected>Select Grade</option>
                </select>
              </div>
            </div>
          </div>
          <div class="col-md-12" style="padding-bottom:15px;">
            <div class="form-group">
              <div class="col-md-4">Students</div>
              <div class="col-md-6">
                <select name="j_username" required="required" id="studentRegId" class="desktop_select" style="border:1px solid #97ced6;color:white;">
                  <option value="select" selected>Select Student</option>
                </select>
              </div>
            </div>
          </div>
          <div class="col-md-12">
            <div class="col-md-5"></div>
            <div class="col-md-4">
              <button type="submit" class="btn btn-sm btn-primary" onclick="transferStudents();return false;">Transfer</button>
              <br>
              <br>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
