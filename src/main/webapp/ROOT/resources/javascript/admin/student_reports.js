/**
 * 
 */
/**
 * 
 */
function showHomeworkReports(){
	var gradeId = $('#gradeId').val();
	var classId = $('#classId').val();
	var sectionId = $('#sectionId').val();
	var fromId = $('#fromId').val();
	var teacherId=$('#teacherId').val();
	var assignmentId=$("#titleId").val();
	$("#StudentHomeworkReports").empty(); 
	$("#homeworkQuestions").empty(); 
			
	if(gradeId == "" || gradeId == "select"){
		alert("Please select grade");
		return;
	}
	if(classId == "" || classId == "select"){
		alert("Please select class");
		return;
	}
	if(sectionId == "" || sectionId == "select" || sectionId == "Select Section"){
		alert("Please select section");
		return;
	}
	if(teacherId == "" || teacherId == "select"){
		alert("Please select class");
		return;
	}
	if(fromId == ""){
		alert("Please select From date");
		return;
	}
	if(assignmentId == "" || assignmentId == "select" ){
		var StudentHomeworkReports = $(document.getElementById('StudentHomeworkReports'));
		$(StudentHomeworkReports).empty(); 
	}else{
		$("#loading-div-background").show();
		$.ajax({
			type : "GET",
			url : "getStudentHomeworkReportsList.htm",
			data : "assignmentId="+assignmentId,
			success : function(response) {
				$(StudentHomeworkReports).append(response); 
				 $("#loading-div-background").hide();
			}
		});
		var StudentHomeworkReports = $(document.getElementById('StudentHomeworkReports'));
		$(StudentHomeworkReports).empty();
	}
}

function getStudentTestQuestions(assignmentId,studentAssignmentId,assignmentTypeId,status,testCount,assignmentType){
	$("#homeworkQuestions").html("");
	if(status == "pending"){
		alert("This task not yet submitted");
		return;
	}
	var usedFor=$('#usedFor').val();
	$("#StuAssessQuestionsList").empty();
	var tab=$('#tab').val();	
	$.ajax({
		type : "GET",
		url : "getStudentHomeworkQuestions.htm",
		data : "studentAssignmentId=" + studentAssignmentId + "&usedFor="+usedFor +"&assignmentTypeId="+assignmentTypeId+"&assignmentId="+assignmentId, 
		success : function(response) {
			var dailogContainer = $(document.getElementById('homeworkQuestions'));
				var screenWidth = $( window ).width() - 10;
				var screenHeight = $( window ).height() - 10;
				$('#homeworkQuestions').empty();  
				$(dailogContainer).append(response);
				$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true, dialogClass: 'myTitleClass', open:function () {
					  $(".ui-dialog-titlebar-close").show();
				} });		
				$(dailogContainer).dialog("option", "title", assignmentType);
				$(dailogContainer).scrollTop("0");	
			
		}
	});
			
}

function showReports(){
	if(document.getElementById("checkAssigned").checked == true){
		var gradeId = $('#gradeId').val();
		var classId = $('#classId').val();
		var csId = $('#csId').val();
		var fromId = $('#fromId').val();
		var toId = $('#toId').val();
		var StudentReportList = $(document.getElementById('StudentReportList'));
		$(StudentReportList).empty(); 
		
		if(gradeId == "" || gradeId == "select"){
			alert("Please select grade");
			document.getElementById("checkAssigned").checked = false;
			return;
		}
		if(classId == "" || classId == "select"){
			alert("Please select class");
			document.getElementById("checkAssigned").checked = false;
			return;
		}
		if(csId == "" || csId == "select" || csId == "Select Section"){
			alert("Please select section");
			document.getElementById("checkAssigned").checked = false;
			return;
		}
		if(fromId == ""){
			alert("Please select From date");
			document.getElementById("checkAssigned").checked = false;
			return;
		}
		if(toId == ""){
			alert("Please select To date");
			document.getElementById("checkAssigned").checked = false;
			return;
		}		
		 $("#loading-div-background").show();
		$.ajax({
			type : "GET",
			url : "getStudentReportsList.htm",
			data : "gradeId="+gradeId+"&classId="+classId+"&csId=" + csId+"&fromId="+ fromId+"&toId="+ toId,
			success : function(response) {
				$(StudentReportList).append(response); 
				$("#loading-div-background").hide();
			}
		});
		
	}		
}
function displayStudentCompositeChart(csId, studentId){
	var width = $(window).width();
	var height = $(window).height();
	$.ajax({
		type : "GET",
		url : "getStudentCompositeChart.htm",
		data : "csId=" + csId + "&studentId=" + studentId,
		success : function(response) {
			var dailogContainer = $(document.getElementById('studentCompositeChart'));
			$('#studentCompositeChart').empty();  
			$(dailogContainer).append(response);
			$(dailogContainer).dialog({width: width-20, height: height,open:function () {
				  $(".ui-dialog-titlebar-close").show();
			}
		});					
			$(dailogContainer).scrollTop("0");
		}
	});
}


function getStudentAttendance() {
	if(document.getElementById("getAttendance").checked == true){
		var gradeId = $('#gradeId').val();
		var gradeClassId = $('#gradeClassId').val();
		
		var tab = $('#tab').val();
		var csId= $('#csId').val();
		var startDate = $('#startDate').val();
		var endDate = "";
		var strUrl, data;
		if(tab == 'weekly attendance'){
			endDate = $('#endDate').val();
			if(endDate == ''){
				alert("Please select a value from all the filers");
				document.getElementById("getAttendance").checked = false;
				return false;
			}
			endDate = $.datepicker.formatDate('yy-mm-dd', new Date(endDate));
		}
		if(gradeId == '' || gradeClassId == 'select' || csId == 'select' || startDate == ''){
			alert("Please select a value from all the filers");
			document.getElementById("getAttendance").checked = false;
			return false;
		}
		startDate = $.datepicker.formatDate('yy-mm-dd', new Date(startDate));
		if(tab == 'daily attendance'){
			strUrl = "getDailyAttendance.htm";
			data = "csId="+csId+"&date="+startDate+"&tab="+tab;
		}
		else{
			strUrl = "getWeeklyAttendance.htm";
			data = "csId="+csId+"&startDate="+startDate+"&endDate="+endDate+"&tab="+tab;
		}
		
		var attendanceDiv = $(document.getElementById('attendanceDiv'));
		$(attendanceDiv).empty();
		$.ajax({
			type : "GET",
			url : strUrl,
			data : data,
			success : function(response) {
				$(attendanceDiv).append(response);
			}
		});
	}else{
		var attendanceDiv = $(document.getElementById('attendanceDiv'));
		$(attendanceDiv).empty();
	}
}

function resetData(){
	$("#attendanceDiv").empty();
	document.getElementById("getAttendance").checked = false;
}