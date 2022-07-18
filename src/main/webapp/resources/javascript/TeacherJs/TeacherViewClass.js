
function getStudentDetails(){
	var divId = document.getElementById("divId").value;
	var gradeId = dwr.util.getValue('gradeId');
	var classId = dwr.util.getValue('classId');
	var csId = dwr.util.getValue('csId');
	var page=document.getElementById("page").value;
	var dateToUpdate = dwr.util.getValue("dateToUpdate");
    if(csId != "select"){	
	    if((page == "updateAttendance" || page == "takeAttendance" ) && dateToUpdate==""){
	    	$("#loading-div-background").show();
	    	if(csId != 'select' && gradeId !='select' && classId!='select'){
		    	$.ajax({  
		    		url : "getStudentDetails.htm", 
		    		data: "divId="+divId+"&gradeId="+gradeId+"&classId="+classId+"&csId="+csId+"&page="+page+"&dateToUpdate="+dateToUpdate,
		    	    success : function(response) {
		    	    	var currentTime = new Date();
			    		var month = currentTime.getMonth()+1;if(month<10)month='0'+month;
			    		var day = currentTime.getDate();if(day<10)day='0'+day;
			    		var year = currentTime.getFullYear();
		
			    		var date = month +"/"+day+"/"+year;
			    		$("#dateToUpdate").val(date);
			    		$("#commonDetailsPage").html(response);
			    		$("#loading-div-background").hide();
		    		}
		    	}); 
	    	}
	    	else{
	    		alert('Please select all the filters');
	    		$("#loading-div-background").hide();
	    		return false;
	    	}
	    }else if((page == "updateAttendance" || page == "takeAttendance" ) && dateToUpdate){
	    	$("#loading-div-background").show();
	    	$.ajax({  
	    		url : "getStudentDetails.htm", 
	    		data: "divId="+divId+"&gradeId="+gradeId+"&classId="+classId+"&csId="+csId+"&page="+page+"&dateToUpdate="+dateToUpdate,
	    	    success : function(response) {
		    		$("#dateToUpdate").val(dateToUpdate);
		    		$("#commonDetailsPage").html(response);
		    		$("#loading-div-background").hide();
	    		}
	    	}); 
	    }else{
	    	$("#loading-div-background").show();
	    	$.ajax({  
	    		url : "getStudentDetails.htm", 
	    		data: "divId="+divId+"&gradeId="+gradeId+"&classId="+classId+"&csId="+csId+"&page="+page+"&dateToUpdate="+dateToUpdate,
	    	    success : function(response) {
	    	    	if(divId == 'Roster' || page == 'takeAttendance' || divId == 'Registration'){
	    	    		$("#commonDetailsPage").html(response);	    	    	
	    	    	}
	    	    	$("#loading-div-background").hide();
	    		}
	    	}); 
	    }
    }else{
    	if(divId == 'Roster' || divId == "Attendance" || divId == 'Registration'){
    		$("#commonDetailsPage").html("");
    	}
    	return false;
    }
}
function showOrHide(){
	var divId = document.getElementById("divId").value;
	 $("#dateToUpdateDiv").hide();
	 $("#headerPage").hide();
	 $("#submit").hide();
	if(divId == 'Roster'){
		$("#RosterHeader").append($("#headerPage").html());	
		 $("#Roster").show();
		 $("#Attendance").hide();
		 $("#Registration").hide();
		 $("#RequestForClass").hide();
		 $("#showMyClasses").hide();
	}else if(divId == 'Attendance'){
		var page = document.getElementById("page").value;
		if(page){
			$("#appendDate").append($("#dateToUpdateDiv").html());
			$("#AttendanceHeader").append($("#headerPage").html());	
			/*if(page=='updateAttendance'){
				$("#appendDate").append($("#dateToUpdateDiv").html());
				$("#AttendanceHeader").append($("#headerPage").html());	
				
			}else{
				$("#AttendanceHeader").append($("#headerPage").html());	
			}*/
			$("#Roster").hide();
			$("#Registration").hide();
			$("#RequestForClass").hide();
			$("#showMyClasses").hide();
		}
	}else if(divId == 'Registration'){
		$("#RegistrationHeader").append($("#headerPage").html());	
		$("#Registration").show();
		$("#Roster").hide();
		$("#Attendance").hide();
		$("#RequestForClass").hide();
		$("#showMyClasses").hide();
	}else if(divId == 'RequestForClass'){
		$("#RequestForClassHeader").append($("#headerPage").html());
		$("#RequestForClass").show();
		$("#Registration").hide();
		$("#Roster").hide();
		$("#Attendance").hide();
		$("#showMyClasses").hide();
	}else if(divId == 'showMyClasses'){
		$("#RequestForClass").hide();
		$("#Registration").hide();
		$("#Roster").hide();
		$("#Attendance").hide();
		$("#showMyClasses").show();
	}
}
function saveAttendance(countId){
	var csId = dwr.util.getValue('csId');
	var page = dwr.util.getValue('page');
	var status = new Array();
	var stdIds = new Array();
	if(page=='takeAttendance' && countId == ""){
		$("#loading-div-takeAttendance").show();
		var noOfStudents = dwr.util.getValue('noOfStudents');
		for (i = 1; i <= noOfStudents; i++) {
			var value = dwr.util.getValue('student'+i);
			var res = value.split("-");
			status.push(res[0]);
			stdIds.push(res[1]);
		}
		var savable = false;
		var date = dwr.util.getValue('dateToUpdate');
		
		$.ajax({
			url : "saveAttendance.htm",
			data: "status="+status+"&studentId="+stdIds+"&csId="+csId+"&date="+date+"&page="+page+"&savable="+savable,
			success : function(data) {
				$("#resultDiv").html('');
				if(data == "Yes"){
					if(confirm("Attendance already taken..Do you want to save again?",function(status1){
						if(status1){
							savable = true;
							$.ajax({
								url : "saveAttendance.htm",
								data: "status="+status+"&studentId="+stdIds+"&csId="+csId+"&date="+date+"&page="+page+"&savable="+savable,
								success : function(data){
									 systemMessage('Saved Successfully !!');
								}
							});
						}
					}));
				}else{	
					systemMessage('Saved Successfully !!');
				}
			}
		});
		$("#loading-div-takeAttendance").hide();
	}else if(page=='updateAttendance'){
		//$("#loading-div-takeAttendance").show();
		var savable = true;
		var date = dwr.util.getValue('dateToUpdate');
		var value = dwr.util.getValue("student"+countId);
		var res = value.split("-");
		status.push(res[0]);
		stdIds.push(res[1]);
		
		$.ajax({
			url : "saveAttendance.htm",
			data: "status="+status+"&studentId="+stdIds+"&csId="+csId+"&date="+date+"&page="+page+"&savable="+savable,
			success : function(data) {
				$("#loading-div-takeAttendance").hide();
				systemMessage(data);
			}
		});
		//$("#loading-div-takeAttendance").hide();
	}else{
	return false;	
	}
	
}
function refreshAttendance(){
	var divId = dwr.util.getValue("divId");
	dwr.util.setValue('gradeId','select');
	dwr.util.removeAllOptions('classId');
	dwr.util.addOptions('classId', ["select"]);
	dwr.util.removeAllOptions('sectionId');
	dwr.util.addOptions('sectionId', ["select"]);  	
	dwr.util.setValue('dateToUpdate','');
	$("#AttendanceDetailsPage").html('');
}
function check(obj){
	var res = obj.id.split("-");
	var response;
	if(res[1] == "accepted"){
		$("#"+res[0]+"-declined").prop('checked', false); 
		if(confirm("Are you Sure? You want to accept the request?",function(status){
			if(status){
				setAction(obj);
			}else{
			 $("#"+res[0]+"-accepted").prop('checked', false); 
			  return false;
			}
		})); 
	}else{
		$("#"+res[0]+"-accepted").prop('checked', false); 
		if(confirm("Are you Sure? You want to decline the request?",function(status){
			if(status){
			setAction(obj);
			}else{
				$("#"+res[0]+"-declined").prop('checked', false); 
				  return false;
			}
		})); 
	}
}
function setAction(obj){
	var gradeClassId = dwr.util.getValue('gradeClassId');
	var gradeLevelId = dwr.util.getValue('gradeLevelId');
	var csId = dwr.util.getValue('csId');
	var res = obj.id.split("-");
	var studentName = dwr.util.getValue(res[0]);
	if(!obj.checked){
		res[1] = "waiting";
	}
	$.ajax({
		url : "setStudentAction.htm",
		data: "studentId="+res[0]+"&status="+res[1]+"&studentName="+studentName+"&gradeClassId="+gradeClassId+"&gradeLevelId="+gradeLevelId+"&csId="+csId,
		success : function(data) {
			getStudentDetails();
			alert(data);
		}
	});
	
}
function sendRequestForAClass(){
	var gradeId = dwr.util.getValue('gradeId');
	var classId = dwr.util.getValue('classId');
	if((gradeId == "select") || (classId == "select")){
		alert("Please select the values");
		return false;
	}else{
		$.ajax({
			url : "sendRequestForAClass.htm",
			data: "gradeId="+gradeId+"&classId="+classId,
			success : function(data) {
				alert(data);
				dwr.util.setValue('classId',classId);
			}
		});
	}
}
function refreshRequest(){
	$("#submitDiv").html('');
	dwr.util.setValue('gradeId','select');
	dwr.util.removeAllOptions('classId');
	dwr.util.addOptions('classId', ["select"]);
}
