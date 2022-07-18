function loadClasses() {
	var gradeId = dwr.util.getValue('gradeId');
	var teacherObj = dwr.util.getValue('teacherObj');
	var regId = dwr.util.getValue('childId');
	var childGradeId = dwr.util.getValue('gradeId:'+regId);
	if(!gradeId || gradeId == 'select' || gradeId == 0){
		$("#lessonDiv").html('');
    	$("#unitLessonDiv").html('');
    	$("#showFilesDiv").html('');
		dwr.util.removeAllOptions('classId');
		$("#classId").append(
				$("<option></option>").val(0).html('Select Subject'));
		dwr.util.removeAllOptions('unitId');
		$("#unitId").append(
				$("<option></option>").val(0).html('Select Unit'));
		$("#unitLessonDiv").html('');
		dwr.util.removeAllOptions('sectionId');
		$("#sectionId").append($("<option></option>").val(0).html('Select Section'));
		$("#csId").empty();
		$("#csId").append($("<option></option>").val('').html('Select Section'));
		dwr.util.removeAllOptions('studentId');
		$("#studentId").append($("<option></option>").val(0).html('Select Student'));
		if(document.getElementById("createFolderDiv"))
			document.getElementById("createFolderDiv").style.display = 'none';
		return;
	}
	if(teacherObj){
		if(document.getElementById("createFolderDiv"))
			document.getElementById("createFolderDiv").style.display = 'none';
		//teacher flow
		curriculumService.getAssingedTeacherClasses(gradeId, {
			callback : stateCallBack
		});
	}else if(gradeId ){
		//admin flow
		adminService1.getStudentClasses(gradeId, {
			callback : stateCallBack
		});
	}else if(childGradeId) {		
		//var studentGradeId = parentService.getStudentGradeIdByRegId(regId);
		adminService1.getStudentClasses(childGradeId, {
			callback : stateCallBack
		});
	}
}
function stateCallBack(list) {
	if (list != null) {
		$("#lessonDiv").html('');
    	$("#unitLessonDiv").html('');
    	$("#showFilesDiv").html('');
		dwr.util.removeAllOptions('unitId');
		$("#unitId").append(
				$("<option></option>").val(0).html('Select Unit'));
		$("#unitLessonDiv").html('');
		dwr.util.removeAllOptions('sectionId');
		$("#sectionId").append($("<option></option>").val(0).html('Select Section'));
		dwr.util.removeAllOptions('studentId');
		$("#studentId").append($("<option></option>").val(0).html('Select Student'));
		dwr.util.removeAllOptions('classId');
		$("#classId").append(
				$("<option></option>").val(0).html('Select Subject'));
		dwr.util.addOptions('classId', list, 'classId', 'className');

	} else {
		alert("No data found");
	}
}

function loadSections() {
	var gradeSelected = dwr.util.getValue('gradeId');
	var classSelected = dwr.util.getValue('classId');
	var sectionCallBack = function (list) {
		if (list != null) {
			dwr.util.removeAllOptions('sectionId');
			$("#sectionId").append($("<option></option>").val(0).html('Select Section'));
			dwr.util.addOptions('sectionId', list, 'sectionId', 'section');

		} else {
			alert("No data found");
		}
	}
	if(classSelected &&  classSelected > 0) {
		adminService1.getAllSections(gradeSelected,classSelected, {
			callback : sectionCallBack
		});
	}else{
		$("#unitLessonDiv").html('');
		dwr.util.removeAllOptions('sectionId');
		$("#sectionId").append($("<option></option>").val(0).html('Select Section'));
		dwr.util.removeAllOptions('studentId');
		$("#studentId").append($("<option></option>").val(0).html('Select Student'));
		return false;
	}	
}


function loadStudents() {	
	var csId = dwr.util.getValue('csId');	
	if(csId > 0){
	adminService1.getStudentsBySection(csId, {
		callback : studentCallBack
	});
	}else{
		$("#unitLessonDiv").html('');
		dwr.util.removeAllOptions('studentId');
		$("#studentId").append($("<option></option>").val(0).html('Select Student'));
	}
}
function studentCallBack(list) {
	if (list != null) {
		dwr.util.removeAllOptions('studentId');
		$("#studentId").append($("<option></option>").val(0).html('Select Student'));
		var studentSelect = document.getElementById('studentId');		
		for(var i=0;i<list.length;i++){
			var studentOption = document.createElement("option");			
			studentOption.text = list[i].userRegistration.firstName+" "+list[i].userRegistration.lastName;
			studentOption.value = list[i].studentId;		    
		    studentSelect.options.add(studentOption);
		}
	} else {
		alert("No data found");
	}
}

function loadGradeLevels() {
	var selected = dwr.util.getValue('classId');
	if(selected == 'select'){
		return;
	}
	adminService1.getStudentGradeLevels(selected, {
		callback : classCallBack
	});
}
function classCallBack(list) {
	if (list != null) {
		dwr.util.removeAllOptions('gradeLevelId');
		dwr.util.addOptions(gradeLevelId, ["select"]);
		dwr.util.addOptions('gradeLevelId', list, 'gradeLevelId', 'gradeLevelName');

	} else {
		alert("No data found");
	}
}
function submitSection()
{
	var gradeId=$('#gradeId').val();
	 if(gradeId== '' || gradeId==null || gradeId=='select'){
		 alert("Please select the gradeName");
         $('#gradeId').next().show();
         return false;
      }
	 var classId=$('#classId').val();
	 if(classId== '' || classId==null || classId=='select'){
		 alert("Please select the className");
         $('#classId').next().show();
         return false;
      }
	 var gradeLevelId=$('#gradeLevelId').val();
	 if(gradeLevelId== '' || gradeLevelId==null || gradeLevelId=='select'){
		 alert("Please select the GradeLevel");
         $('#gradeLevelId').next().show();
         return false;
      }
	 var sectionName=$('#sectionName').val();
	 if(sectionName== '' || sectionName==null){
		 alert("Please enter the section name");
         $('#sectionName').next().show();
         return false;
      }
	 $.ajax({  
			url : "addSections.htm", 
	        data: "gradeId=" + gradeId+"&classId="+classId+"&gradeLevelId="+gradeLevelId+"&sectionName="+sectionName,
	        success : function(data) { 
	        	alert(""+data);
	        	$("#result").html(data);  
	            $('#gradeId').val('select');
	            $('#classId').val('select');
	            $('#gradeLevelId').val('select');
	            $('#sectionName').val('');
	            
	        }  
	    }); 
}
function getGrades()
{	
	document.getElementById("EditSections").reset(); 
	$.ajax({  
		url : "EditSections.htm", 
	    data: "option=EditSection",
	    success : function(data) { 
	    	document.getElementById("edit").src = "images/Admin/edit-section-green.jpg";   
	    	document.getElementById("create").src = "images/Admin/create-section-blue.jpg"; 
	    }
	}); 	
}
//function getSection()
//{
//	var gradeId=$('#gradeIds').val();
//	 if(gradeId== '' || gradeId==null || gradeId=='select'){
//		 alert("Please select the gradeName");
//        $('#gradeIds').next().show();
//        return false;
//     }
//	 var classId=$('#classIds').val();
//	 if(classId== '' || classId==null || classId=='select'){
//		 alert("Please select the className");
//        $('#classIds').next().show();
//        return false;
//     }
//$.ajax({  
//	url : "GetSections.htm", 
//    data: "gradeId="+gradeId+"&classId="+classId,
//    success: function(response) {
//    	alert(response);
//        $("#getsection").html(response);
//    }
//}); 	
//}
function loadSubjects()
{
	var selected = dwr.util.getValue('gradeIds');
	if(selected == 'select'){
		return;
	}
	adminService1.getStudentClasses(selected, {
		callback : subjectCallBack
	});
}
function subjectCallBack(list) {
	if (list != null) {
		dwr.util.removeAllOptions('classIds');
		dwr.util.addOptions(classIds, ["select"]);
		dwr.util.addOptions('classIds', list, 'classId', 'className');

	} else {
		alert("No data found");
	}
}
function loadUnitName() {
	var gradeSelected = dwr.util.getValue('gradeId');
	var classSelected = dwr.util.getValue('classId');
	var studentId = dwr.util.getValue('studentId');
	var childGradeId = dwr.util.getValue('gradeId:'+studentId);
	if(classSelected > 0){
		if(childGradeId){
			curriculumService.getUnitsByGradeIdClassId(childGradeId,classSelected, {
				callback : unitsCallBack
			});
		}else{
			curriculumService.getUnitsByGradeIdClassId(gradeSelected,classSelected, {
				callback : unitsCallBack
			});
		}	
	}else{
		$("#lessonDiv").html("");
		$("#showFilesDiv").html("");
		$("#unitLessonDiv").html('');
		dwr.util.removeAllOptions('unitId');
		$("#unitId").append(
				$("<option></option>").val(0).html('Select Unit'));
	}
}
function unitsCallBack(list) {
	$("#unitLessonDiv").html('');
	$("#showFilesDiv").html('');
	if (list.length > 0) {
		dwr.util.removeAllOptions('unitId');
		$("#unitId").append(
				$("<option></option>").val(0).html('Select Unit'));
		dwr.util.addOptions('unitId', list, 'unitId', 'unitName');
	} else {
		dwr.util.removeAllOptions('unitId');
		$("#unitId").append(
				$("<option></option>").val(0).html('Select Unit'));
		alert("No data found");
	}
}

function loadLessonNames(){
	var unitSelected = dwr.util.getValue('unitId');
	if(unitSelected != "" && unitSelected != "select" && unitSelected != "Select Unit"){
		assessmentService.getLessonsByUnitId(unitSelected, {
			callback : lessonsCallBack
		});
	}else{
		$("#lessonId").empty();
		$("#lessonId").append(
				$("<option></option>").val('').html('Select Lesson'));
	}
}

function lessonsCallBack(list) {
	if (list != null) {		
		dwr.util.removeAllOptions('lessonId');
		dwr.util.addOptions(lessonId, ["Select Lesson"]);
		var teacherObj = dwr.util.getValue('teacherObj');
		var myselect = document.getElementById('lessonId');		
		for(var i=0;i<list.length;i++){
			var objOption = document.createElement("option");
			var user = list[i].userRegistration.user.userType;
		    objOption.text = list[i].lessonName;
		    objOption.value = list[i].lessonId;
		    if(user =="admin" && teacherObj){
		    	objOption.style.color="blue";
		    }
		    if(user == "teacher" && !teacherObj){
		    	objOption.style.color="blue";
		    }
		    myselect.options.add(objOption);
		}

	} else {
		alert("No data found");
	}
}

function loadAssignments(){
	var lessonId = dwr.util.getValue('lessonId');	
	var usedFor = dwr.util.getValue('usedFor');
	if(lessonId != "" && lessonId != "Select Lesson" && lessonId != "select"){
		assessmentService.getAssignments(usedFor, {
			callback : assessmentForCallBack
		});
	}
}

function assessmentForCallBack(list) {
	if (list != null) {
		dwr.util.removeAllOptions('assignmentTypeId');
		dwr.util.addOptions(assignmentTypeId, ["Assignment Type"]);
		dwr.util.addOptions('assignmentTypeId', list, 'assignmentTypeId', 'assignmentType');

	} else {
		alert("No data found");
	}
}

function checkTeacher(id){
	var gradeId = document.getElementById("gradeId").value; 
	var teacher = document.getElementById("teacherId"+id).value;
	var sectionId = document.getElementById("sectionId"+id).value;
	var homeRoomId = document.getElementById("homeRoomId"+id).value;
	if(!homeRoomId || homeRoomId < 0){
		homeRoomId = 0;
	}
	
	 if(sectionId>0){
			$.ajax({
				type : "GET",
				url : "checkHomeRoomTeacher.htm",
				data : "teacher=" + teacher + "&sectionId=" + sectionId + "&homeRoomId=" + homeRoomId+ "&gradeId=" + gradeId,
				success : function(data) {
					if(data.status == "Teacher already assigned !!"){
						systemMessage(data);
					}else{
						systemMessage(data);
					}
				}
			});
		}
}
function getSections() {
	var gradeId = $('#gradeId').val();
	var classId = $('#classId').val();
	if(gradeId>0 && classId>0){
		$.ajax({
			type : "GET",
			url : "getTeacherSections.htm",
			data : "gradeId=" + gradeId + "&classId=" + classId,
			success : function(response1) {
				var teacherSections = response1.teacherSections;
				$("#csId").empty();
				$("#csId").append(
						$("<option></option>").val(0).html('Select Section'));
				$.each(teacherSections, function(index, value) {
					$("#csId").append(
							$("<option></option>").val(value.csId).html(
									value.section.section));
				});
			}
	
		});
	}
	else{
		return false;
	}
}
function loadTeachers() {
	var gradeId = $('#gradeId').val();
	var classId = $('#classId').val();
	var sectionId = $('#sectionId').val();
	$.ajax({
		type : "GET",
		url : "getSectionTeachers.htm",
		data : "gradeId=" + gradeId + "&classId=" + classId + "&sectionId="
				+ sectionId,
		success : function(response2) {
			var sectionTeachers = response2.sectionTeachers;
			$("#teacherId").empty(); 
			$("#teacherId").append(
					$("<option></option>").val('select')
							.html('Select Teacher'));
			$.each(sectionTeachers, function(index, value) {
				$("#teacherId").append(
						$("<option></option>").val(value.teacher.userRegistration.regId)
								.html(value.teacher.userRegistration.firstName+" "+value.teacher.userRegistration.lastName));
			});

		}
	});
}
function showAssignmentTitles(){
	var usedFor = $('#usedFor').val();
	var csId = $('#csId').val();
	var fromId = $('#fromId').val();
	//var teacherId=$('#teacherId').val();
	$("#titleId").empty();
	$("#titleId").append($("<option></option>").val('select').html('Select Title'));
	$("#StudentHomeworkReports").empty(); 
	$("#homeworkQuestions").empty(); 
	if(csId != 'select'){
		$.ajax({
			type : "GET",
			url : "getHomeworkTitles.htm",
			data : "csId=" + csId + "&usedFor=" + usedFor+"&fromId="+fromId,
			success : function(response2) {
				var homeworkTitles = response2.homeworkTitles;
				$.each(homeworkTitles, function(index, value) {
					$("#titleId").append($("<option></option>").val(value.assignmentId).html(value.title));
				});
	
			}
		});
	}
}

function loadLessonNamesByUser(){
	var unitSelected = dwr.util.getValue('unitId');
	curriculumService.getLessonsByUnitIdUserId(unitSelected, {
		callback : lessonsCallBack
	});
}

function loadGradeClassLessons() {
	var gradeSelected = dwr.util.getValue('gradeId');
	var classSelected = dwr.util.getValue('classId');
	if(classSelected > 0){
		curriculumService.getLessonsByGradeIdClassId(gradeSelected,classSelected, {
			callback : gradeClassLessonCallBack
		});
	}
}
function gradeClassLessonCallBack(list) {
	if (list != null) {
		$("#activityTable").show();
		dwr.util.removeAllOptions('lessonId');
		$("#lessonId").empty(); 
		$("#lessonId").append(
				$("<option></option>").val('')
						.html('Select Lesson'));
		var teacherObj = dwr.util.getValue('teacherObj');
		var myselect = document.getElementById('lessonId');		
		for(var i=0;i<list.length;i++){
			var objOption = document.createElement("option");
			var user = list[i].userRegistration.user.userType;
		    objOption.text = list[i].lessonName;
		    objOption.value = list[i].lessonId;
		    if(user =="admin" && teacherObj){
		    	objOption.style.color="blue";
		    }
		    if(user == "teacher" && !teacherObj){
		    	objOption.style.color="blue";
		    }
		    myselect.options.add(objOption);
		}

	} else {
		alert("No data found");
	}
}

function createTab(){
	$("#getsection").empty();
	document.getElementById("CreateSections").reset(); 
	document.getElementById("edit").src = "images/Admin/edit-section-blue.jpg";   
	document.getElementById("create").src = "images/Admin/create-section-green.jpg"; 
}

function getTeachersByClass(callback){
	var gradeId = document.getElementById('gradeId').value;
	var classId = document.getElementById('classId').value;
	if(gradeId != 'select' && classId != 'select'){
		$.ajax({			
			url : "getScheduledClassesByGradeNClass.htm", 
		    data: "gradeId="+gradeId+"&classId="+classId,
		    success : function(response) {
		    	$("#teacherId").empty(); 
				$("#teacherId").append($("<option></option>").val('select').html('Select Teacher'));
				$.each(response.scheduledClasses, function(index, value) {
					$("#teacherId").append(
						$("<option></option>").val(value.teacher.teacherId).html(value.teacher.userRegistration.firstName +" "+ value.teacher.userRegistration.lastName));
				});	
				if(callback)
					callback();
		    }
		}); 	
	}
}


function convertChar(count){
	var swapCodes   = new Array(8216, 8217, 8220, 8221, 8226); // dec codes from char at
	var swapStrings = new Array("'",  "'",  "\"",  "\"",  "*"); 
	
    var output = document.getElementById("question"+count).value;
    for (var i = 0; i < swapCodes.length; i++) {
        var swapper = new RegExp("\\u" + swapCodes[i].toString(16), "g"); // hex codes
        output = output.replace(swapper, swapStrings[i]);
    }
    document.getElementById("question"+count).value = output;
}


function getSectionsByTeacher(callback) {
	var gradeId = $('#gradeId').val();
	var classId = $('#classId').val();
	var teacherId = $('#teacherId').val();
	$("#csId").empty();
	$("#csId").append($("<option></option>").val("select").html('Select Section'));
	if(gradeId != 'select' && classId != 'select' && teacherId != 'select'){
		$.ajax({
			type : "GET",
			url : "getScheduledClassListByTeacher.htm",
			data : "gradeId=" + gradeId + "&classId=" + classId + "&teacherId=" + teacherId,
			success : function(response) {
				var scheduledClasses = response.scheduledClasses;				
				$.each(scheduledClasses, function(index, value) {
					$("#csId").append($("<option></option>").val(value.csId).html(value.section.section));
				});
				if(callback)
					callback();
			}
		});
	}
}

function loadClassesWithoutHomeroom() {
	var gradeId = $("#gradeId").val();
	$("#classId").empty();
	$("#classId").append(
			$("<option></option>").val('').html('Select Subject'));
	if(gradeId > 0){
		$.ajax({
			type : "GET",
			url : "getClassesWithOutHomeRoom.htm",
			data : "gradeId=" + gradeId,
			success : function(response) {
				var scheduledClasses = response.classes;
				
				$.each(scheduledClasses, function(index, value) {
					$("#classId").append(
							$("<option></option>").val(value.classId).html(
									value.className));
				});
			}
		});
	}
}
function loadScheduledClassesByTeacher() {
	var gradeId = $('#gradeId').val();
	var classId = $('#classId').val();
	var teacherId = $('#teacherId').val();
	$("#csId").empty();
	$("#csId").append(
			$("<option></option>").val("select").html(
					'Select Section'));
	if(gradeId > 0 && classId > 0 && teacherId > 0){
		$.ajax({
			type : "GET",
			url : "getScheduledClassListByTeacher.htm",
			data : "gradeId=" + gradeId + "&classId=" + classId + "&teacherId=" + teacherId,
			success : function(response) {
				var scheduledClasses = response.scheduledClasses;				
				$.each(scheduledClasses, function(index, value) {
					$("#csId").append(
							$("<option></option>").val(value.csId).html(
									value.section.section));
				});
			}
		});
	}
}
function getScheduledClassesByTeacher() {
	var gradeId = $('#gradeId').val();
	var classId = $('#classId').val();
	var teacherId = $('#teacherId').val();
	$("#csId").empty();
	$("#csId").append(
			$("<option></option>").val("select").html(
					'Select Section'));
	if(gradeId > 0 && classId > 0 && teacherId > 0){
		$.ajax({
			type : "GET",
			url : "getScheduledClassListByTeacher.htm",
			data : "gradeId=" + gradeId + "&classId=" + classId + "&teacherId=" + teacherId,
			success : function(response) {
				var scheduledClasses = response.scheduledClasses;				
				$.each(scheduledClasses, function(index, value) {
					$("#csId").append(
							$("<option></option>").val(value.csId).html(
									value.section.section));
				});
			}
		});
	}
}