/**
 * 
 */
function getTeacherGradeClass() {
		var gradeId = $('#gradeId').val();
		$("#classId").empty(); 
		$("#classId").append(
				$("<option></option>").val('select')
						.html('Select Subject'));
		if(gradeId != "select" && gradeId ){
			$.ajax({
				type : "GET",
				url : "getTeacherClasses.htm",
				data : "gradeId=" + gradeId,
				success : function(response) {
					var teacherClasses = response.teacherClasses;
					$.each(teacherClasses, function(index, value) {					
						$("#classId").append(
								$("<option></option>").val(value.classId).html(
										value.className));
					});
	
				}
			});
		}else{
			$("#csId").empty(); 
			$("#csId").append(
					$("<option></option>").val('select')
							.html('Select Section'));
			$("#assignedDate").empty();
			$("#assignedDate").append(
					$("<option></option>").val('select').html('Select Date'));
			$("#titleId").empty();
			$("#titleId").append(
					$("<option></option>").val('select').html('Select Title'));
			 $("#ReportList").html("");
		}
	}
	
function loadTeacherSections() {
	var gradeId = $('#gradeId').val();
	var classId = $('#classId').val();
	$("#csId").empty(); 
	$("#csId").append(
			$("<option></option>").val('select')
					.html('Select Section'));
	if(gradeId != "select" && classId != "select"){
		$.ajax({
			type : "GET",
			url : "getTeacherSections.htm",
			data : "gradeId=" + gradeId + "&classId=" + classId,
			success : function(response1) {
				var teacherSections = response1.teacherSections;
				
				$.each(teacherSections, function(index, value) {
					$("#csId").append(
						$("<option></option>").val(value.csId).html(
								value.section.section));
				});				
			}
			
		});
	}else{
		$("#assignedDate").empty();
		$("#assignedDate").append(
				$("<option></option>").val('select').html('Select Date'));
		$("#titleId").empty();
		$("#titleId").append(
				$("<option></option>").val('select').html('Select Title'));
		$("#studentId").append(
				$("<option></option>").val('select').html('Select Student'));
		 $("#ReportList").html("");
	}
}

function showReports(){
	var csId = $('#csId').val();
	var assignedDate= $('#assignedDate').val();
	var usedFor=$('#usedFor').val();
	var titleId=$('#titleId').val();
	if(assignedDate != "select" && titleId != "select" && csId != "select"){
		$("#loading-div-background").show();
		$.ajax({  
			url : "getHomeworkReports.htm", 
		    data: "csId="+csId+"&usedFor="+usedFor+"&assignedDate="+assignedDate+"&titleId="+titleId,
		    success: function(response) {
		    	 $("#ReportList").empty(); 
		    	 $("#ReportList").html(response);
		    	 $("#loading-div-background").hide();
		    }
		}); 
	}else{
		 $("#ReportList").html("");
	}
	
}
function showAssignmentTitles(){
	var usedFor = $('#usedFor').val();
	var csId = $('#csId').val();
	var classId = $('#classId').val();
	var assignedDate= $('#assignedDate').val();
	if(assignedDate != 'select' && csId >0 && classId > 0){
		$.ajax({
			type : "GET",
			url : "getAssignmentTitles.htm",
			data : "csId=" + csId + "&usedFor=" + usedFor+"&assignedDate="+assignedDate,
			success : function(response2) {
				var assignmentTitles = response2.assignmentTitles;
				$("#titleId").empty();
				$("#titleId").append(
						$("<option></option>").val('select').html('Select Title'));
				$.each(assignmentTitles, function(index, value) {
					$("#titleId").append(
							$("<option></option>").val(value.assignmentId).html(value.title));
				});
	
			}
		});
	}else{
		alert("Please select the Filters");
	}
}


/*function getTestTitles(){
	var usedFor = $('#usedFor').val();
	var csId = $('#csId').val();
	if(usedFor && csId){
		$.ajax({
			type : "GET",
			url : "getTestTitles.htm",
			data : "csId=" + csId + "&usedFor=" + usedFor,
			success : function(response2) {
				var assignmentTitles = response2.assignmentTitles;
				$("#titleId").empty();
				$("#titleId").append(
						$("<option></option>").val('select').html('Select Title'));
				$.each(assignmentTitles, function(index, value) {
					$("#titleId").append(
							$("<option></option>").val(value.assignmentId).html(value.title));
				});
	
			}
		});
	}
}*/

function loadAssignment(callback) {
	$("#usedFor").empty();
	$("#usedFor").append($("<option></option>").val('select').html('Select Assignment'));
	$("#usedFor").append($("<option></option>").val('assessments').html('Assessments'));
	$("#usedFor").append($("<option></option>").val('homeworks').html('Homeworks'));
	if(callback)
		callback();
}

function loadDatesforRTIResults() {
	var usedFor = $('#usedFor').val();
	var csId = $('#csId').val();
	if(usedFor && csId){
		$.ajax({
			type : "GET",
			url : "getTestDatesforRTIResults.htm",
			data : "csId=" + csId + "&usedFor=" + usedFor,
			success : function(response2) {
				var teacherAssignedDates = response2.teacherAssignedDates;
				$("#assignedDate").empty();
				$("#assignedDate").append(
						$("<option></option>").val('select').html('Select Date'));
				$.each(teacherAssignedDates, function(index, value) {
					$("#assignedDate").append(
							$("<option></option>").val(getDBFormattedDate(value.dateAssigned)).html(getFormattedDate(value.dateAssigned)));
				});
	
			}
		});
	}
}

function checkGroupName(){
	var csId = $('#csId').val();
	var groupName = $('#groupName').val();
	performanceService.verifyGroupName(csId,groupName, {
		callback : verifyGroupNameCallBack
	});
}

function verifyGroupNameCallBack(list) {
	if (list != null) {		
		if(list == true){
			systemMessage("Group name already exists");
			document.getElementById("groupName").value = "";
			$('#groupName').focus();
		}
	} else {
		alert("No data found");
	}
}
function getFluencyTitles(callback){
	var csId = $('#csId').val();
	var classId = $('#classId').val();
	var assignedDate= $('#assignedDate').val();
	if(assignedDate != 'select' && csId >0 && classId > 0){
		$.ajax({
			type : "GET",
			url : "getFluencyTitles.htm",
			data : "csId=" + csId +"&assignedDate="+assignedDate,
			success : function(response2) {
				var assignmentTitles = response2.assignmentTitles;
				$("#titleId").empty();
				$("#titleId").append(
						$("<option></option>").val('select').html('Select Title'));
				$.each(assignmentTitles, function(index, value) {
					$("#titleId").append(
							$("<option></option>").val(value.assignmentId).html(value.title));
				});
				if(callback)
				  callback();
			}
		});
	}else{
		$("#titleId").empty();
		$("#titleId").append(
				$("<option></option>").val('select').html('Select Title'));
	}
}

