
/*function getAllStudentsRRReportsByGradeId(){
	var gradeId = $('#gradeId').val();
	var schoolId = $('#schoolId').val();
	if(gradeId > 0 && schoolId > 0){
	$.ajax({
		type : "GET",
		url : "rrReportsMainPage.htm",
		data : "gradeId="+gradeId+"&schoolId="+schoolId,
		 success : function(data) {
			 
			$('#rrReportsDetailsDiv').html("");
			$('#rrReportsDetailsDiv').html(data);
		}
	});	
  }
}*/
function rrReportsDownloadToExcel(gradeId,studentId){
	$.ajax({
		type : "GET",
		url : "getStudentRReports.htm",
		data : "gradeId="+gradeId+"&studentId="+studentId,
		 success : function(data) {
			$('#rrReportsDetailsDiv').html("");
			$('#rrReportsDetailsDiv').html(data);
		}
	});	
}

function validateForm(userType){
	if(userType==3){
	var gradeId = $('#gradeId').val();
	var studentId = $('#studentId').val();
	if(gradeId > 0 && studentId!='select'){
		return true;
	}else{
		systemMessage("Please select All the Filters");
		return false;
	}
}
else{
	
	var gradeId = $('#gradeId').val();
	var fromId= $('#fromId').val();
	var toId= $('#toId').val();

	if(gradeId!='select' && fromId!="" && toId!=""){
		return true;
	}else{
		systemMessage("Please Fill All the Fields");
		return false;
	}
}
}
function setAction(type){
	if(type=="excel"){
	document.getElementById('rrReports').action = 'exportStudentRRReports.htm';
	document.getElementById('reportsType').value="excel";
	}
	else{
	document.getElementById('rrReports').action = 'exportStudentRRReports.htm';
	document.getElementById('reportsType').value="pdf";
	}
}
function validateDownload(userType){
 if(userType==1){
		var districtId = $('#districtId').val();
		if(districtId > 0 && districtId!='select'){
			return true;
		}else{
			systemMessage("Please select district");
			return false;
		}
	}	
}

function setURL(type){
	
		if(type=="excel"){
			document.getElementById('rrReports').action = 'exportDistrictWiseRRReports.htm';
			document.getElementById('reportsType').value="excel";
			}
			else{
			document.getElementById('rrReports').action = 'exportDistrictWiseRRReports.htm';
			document.getElementById('reportsType').value="pdf";
			}
	
	
}