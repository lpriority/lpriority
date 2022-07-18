/**
 * 
 */
function loadClasses() {
	var gradeId = dwr.util.getValue('gradeId');
	$("#detailsPage").html('');
	var loadClassesCallBack = function(list) {
		if (list != null) {
			dwr.util.removeAllOptions('classId');
			dwr.util.addOptions('classId', ["select"]);
			
			dwr.util.addOptions('classId', list, 'classId', 'className');
			
			dwr.util.removeAllOptions('teacherId');
			dwr.util.addOptions('teacherId', ["select"]);

		}else{
			dwr.util.removeAllOptions('classId');
			dwr.util.addOptions('classId', ["select"]);
			
			dwr.util.removeAllOptions('teacherId');
			dwr.util.addOptions('teacherId', ["select"]);
		}
	}
	if(gradeId != 'select'){
		teacherSchedulerService.getStudentClasses(gradeId, {
			callback : loadClassesCallBack
		});
	}else{
		dwr.util.removeAllOptions('classId');
		dwr.util.addOptions('classId', ["select"]);
		
		dwr.util.removeAllOptions('teacherId');
		dwr.util.addOptions('teacherId', ["select"]);
		return false;
	}
	
}
function loadTeachers() {
	var gradeId = dwr.util.getValue('gradeId');
	var classId = dwr.util.getValue('classId');
	$("#detailsPage").html('');
	var loadTeachersCallBack = function(list) {
		if (list != null) {
			dwr.util.removeAllOptions('teacherId');
			dwr.util.addOptions('teacherId', ["select"]);
			dwr.util.addOptions('teacherId', list,'teacherId', formatter);
		} else {
			dwr.util.removeAllOptions('teacherId');
			dwr.util.addOptions('teacherId', ["select"]);
		}
	}
	 var formatter = function (entry) {
		var fullName = '';
		if(entry.userRegistration.title == null)
			fullName = entry.userRegistration.firstName+ " " +entry.userRegistration.lastName;
		else
			fullName = entry.userRegistration.title + " " +entry.userRegistration.firstName+ " " +entry.userRegistration.lastName;
		return fullName;
	}
	if(classId != 'select'){
		teacherSchedulerService.getTeachers(gradeId,classId, {
			callback : loadTeachersCallBack
		})
	}else{
		dwr.util.removeAllOptions('teacherId');
		dwr.util.addOptions('teacherId', ["select"]);
		return false;
	}
}

function getPlannerData(){
	var gradeId = dwr.util.getValue('gradeId');
	var classId = dwr.util.getValue('classId');
	var teacherId = dwr.util.getValue('teacherId');
	var divId = dwr.util.getValue('divId');
	if(teacherId != "select" && teacherId){
		$("#loading-div-background").show();
		$.ajax({  
			type : "GET",
			url : "getPlannerData.htm", 
		    data: "gradeId="+gradeId+"&classId="+classId+"&teacherId="+teacherId+"&divId="+divId,
		    success : function(data) {
		    	document.open();
			    document.write(data);
			    document.close();
			    $("#loading-div-background").hide();
			}
		}); 
	}else{
		$("#detailsPage").html('');
	}
}

function assginSections(thisvar){
	    var arr_periods = new Array(10);
	    var arr_selectedperiods;
	    var gradeId=document.getElementById("gradeId").value;
	    var classId=document.getElementById("classId").value;
	    var teacherId=document.getElementById("teacherId").value;
	    var schoolId=document.getElementById("schoolId").value;
	    var startDate=document.getElementById("startDate").value;
	    var endDate=document.getElementById("endDate").value;
	    
	    var index=parseInt(thisvar.id);
	    var dayId = window.document.getElementById('dayId'+index).value;
	    var sectionId='0';
	    var sectionIds='0';
	    var casId='0';
	    var arr_casIds='0';
	    var sectionCnt = 1;
	    var dayCheckCnt = 1;
	    var noOfPeriods=parseInt(document.getElementById('noOfPeriods').value)+1;
	    var j=0;
	    var temp=0;
	    for(var i=1;i<noOfPeriods;i++)
	    {
	    	if(document.getElementsByName('period'+index+''+i)){
		           if(j==0){
		                arr_selectedperiods=document.getElementsByName('period'+index+''+i)[0].value;
		                if(document.getElementsByName('section'+index+''+i)[0].value != 'select'){
		                    sectionIds=document.getElementsByName('section'+index+''+i)[0].value;
		                }else{
		                	dayCheckCnt = dayCheckCnt+1;
		                	if(document.getElementsByName('casId'+index+''+i).length == 0)
		                		sectionCnt = sectionCnt+1;
		                }
		                j=j+1;
		            }else{
		            	 arr_selectedperiods=arr_selectedperiods +'^'+ document.getElementsByName('period'+index+''+i)[0].value;
		                if(document.getElementsByName('section'+index+''+i)[0].value != 'select'){
		                    sectionIds=sectionIds+'^'+document.getElementsByName('section'+index+''+i)[0].value;
		                }else{
		                	 sectionIds=sectionIds+'^'+sectionId;
		                	 dayCheckCnt = dayCheckCnt+1;
		                	 if(document.getElementsByName('casId'+index+''+i).length == 0)
		                		 sectionCnt = sectionCnt+1;
		                }
		            }
		        
		        if(document.getElementsByName('casId'+index+''+i).length > 0){
	    			if(temp==0){
		    			 arr_casIds = document.getElementsByName('casId'+index+''+i)[0].value;
		    			 temp=temp+1;
		    		 }else{
		    			 arr_casIds = arr_casIds +'^'+ document.getElementsByName('casId'+index+''+i)[0].value; 
		    		 }
	    		}else{
	    			if(temp==0){
	    				arr_casIds=casId;
	    				temp=temp+1;
	    			}else{
	    				arr_casIds=arr_casIds+'^'+casId;
	    			}
	    		}
	    	}
	    }
	    if(sectionCnt == noOfPeriods){
	    	alert("Please select a section");
	    	return false;
	    }
	    if(startDate=='Start Date'||startDate==''){
	    	systemMessage('Please Select Start Date');
	        document.getElementById("startDate").focus();
	        thisvar.checked=false;
	        return false;
	    }
	    if(endDate=='End Date' || endDate==''){
	    	systemMessage('Please Select End Date');
	        document.getElementById("endDate").focus();
	        thisvar.checked=false;
	        return false;
	    }
	    if(Date.parse(endDate) <= Date.parse(startDate)){
	        alert("Statrt Date should not greater than End Date");
	        return false;
	    }
	    var insertCallback = function(success) {
	    	if(success){
    		   if(dayCheckCnt == noOfPeriods)
    		     systemMessage("Updated Successfully !!")
    		   else
    			 systemMessage("Assigned Successfully !!");
	    	}
	    }
    	teacherSchedulerService.planSchedule(gradeId,classId,teacherId,dayId,schoolId,startDate,endDate,arr_selectedperiods,sectionIds,arr_casIds,{
 			callback : insertCallback
 		});
 }

function setSchoolId() {
	var element = document.getElementById("schoolIdDropdown").value;
	document.getElementById("schoolId").value = element;

}
function getStudentList(uniqueId,type){
	$("#detailsPage").html('');
	$.ajax({  
		type : "POST",
		url : "getStudentList.htm", 
	    data: "uniqueId="+uniqueId+"&type="+type,
	    success : function(response) {
	    	$("#studentDetailsPage").html(response);
	    	smoothScroll(document.getElementById('studentDetailsPage'))
		}
	});
}
function checkTeacherAvailability(dId,pId,secId){
	 	var gradeId=document.getElementById("gradeId").value;
	    var classId=document.getElementById("classId").value;
	    var teacherId=document.getElementById("teacherId").value;
		var dayId = document.getElementById(dId).value;
		var periodId = document.getElementById(pId).value;
		
		var teacherAvailabilityCallBack = function(isAssignable) {
			if(isAssignable){
				checkSectionAvailabilityforTeacher(secId);
			}else{
				alert("Teacher not Available !!");
				document.getElementById(secId).value = 'select';
				return false;
			}
		};
		teacherSchedulerService.checkTeacherAvailability(gradeId,classId,teacherId,dayId,periodId, {
			callback : teacherAvailabilityCallBack
		});
}

function checkSectionAvailabilityforTeacher(secId){
	
	var sectionId=document.getElementById(secId).value;
	var teacherId=document.getElementById("teacherId").value;
	var sectionAvailabilityforTeacherCallBack = function(isSectionAvailable) {
		if(isSectionAvailable){
			return true;
		}else{
			alert("Section not Available !!");
			document.getElementById(secId).value = 'select';
			return false;
		}
	};
	if(sectionId != 'select'){
	teacherSchedulerService.checkSectionAvailabilityforTeacher(sectionId,teacherId, {
		callback : sectionAvailabilityforTeacherCallBack
	})
	}else{
		return false;
	}
}
function getGradeLevelTeachers(option){
	var gradeId = option.value;	
	$("#gradeLevelTeachers").empty();
	if(gradeId == "grade"){
		return false;
	}else{
		 $("#loading-div-background").show();
		$.ajax({  
			type : "GET",
			url : "getGradeLevelTeachers.htm", 
		    data: "gradeId="+gradeId,
		    success : function(response) {	    
		    	$("#gradeLevelTeachers").html(response);
		    	 $("#loading-div-background").hide();
			}  
		}); 	
	}
}

function emailBodyController(studentEmailId,status){
	if(studentEmailId){
		$("#ComposeMail").show();
		$("#Success").hide();
		$("#fail").hide();
		
	}else{
		if(status == 'true'){
			$("#Success").show();
			$("#fail").hide();
			$("#ComposeMail").hide();
		}else{
			$("#fail").show();
			$("#Success").hide();
			$("#ComposeMail").hide();
		}
	}
}
function setAction(obj){
	var res = obj.id.split("-");
	if(res[1] == "accepted"){
		$("#"+res[0]+"-declined").prop('checked', false); 
	}else{
		$("#"+res[0]+"-accepted").prop('checked', false); 
	}
	
	var teacherName = dwr.util.getValue(res[0]);
	var gradeId = dwr.util.getValue("gradeId"+res[0]);
	var classId = dwr.util.getValue("classId"+res[0]);
	var teacherId = dwr.util.getValue("teacherId"+res[0]);
	if(!obj.checked){
		res[1] = "waiting";
	}
	if(res[1] == 'accepted'){
	$.ajax({
		url : "acceptTeacherRequest.htm",
		data: "gradeId="+gradeId+"&classId="+classId+"&teacherId="+teacherId+"&status="+res[1]+"&teacherName="+teacherName,
		success : function(data) {
			document.open();
		    document.write(data);
		    document.close();
		}
	});
	}else{
	if(confirm("Are you sure to decline the request?",function(status){
	 	  if(status){
			
			  $.ajax({
				  url : "declineTeacherRequest.htm",
				  data: "gradeId="+gradeId+"&classId="+classId+"&teacherId="+teacherId+"&status="+res[1]+"&teacherName="+teacherName,
				  success : function(data) {
					  alert(data);
					  location.reload();
				  	}
			  	});
		 }else{
			$("#"+res[0]+"-declined").prop('checked', false); 
			return false;
		 }
	}));
}
}

function showDropdown(){
	 var radio =  $('input[name=options]:checked').val(); 
	 $("#schedulerDiv").html("")
	 if(radio == 'grades'){
	   $('#gradeDiv').show();  
	   $('#teacherDiv').hide(); 
	   dwr.util.setValue("gradeId", "select");
	}else{
		$('#teacherDiv').show();  
		$('#gradeDiv').hide(); 
		dwr.util.setValue("teacherId", "select");
	}
}

function getScheduleByGrade(){
	var gradeId=document.getElementById("gradeId").value;
	if(gradeId != 'select'){
		$("#loading-div-background").show();
		$.ajax({
			url : "showScheduleByGrade.htm",
			data: "gradeId="+gradeId,
			success : function(data) {
				$("#schedulerDiv").html(data);
				$("#loading-div-background").hide();
			}
		});
	}else{
		$("#schedulerDiv").html("");
	}
}

function getScheduleByTeacher(){
var teacherId=document.getElementById("teacherId").value;
	if(teacherId != 'select'){
		$("#loading-div-background").show();
		$.ajax({
			url : "showScheduleByTeacher.htm",
			data: "teacherId="+teacherId,
			success : function(data) {
				$("#schedulerDiv").html(data);
				$("#loading-div-background").hide();
			}
		});
	}else{
		$("#schedulerDiv").html("");
	}
}
