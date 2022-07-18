 $(function() {
	 	$.urlParam = function(name){
		    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
		    if (results==null)
		       return null;
		    else
		       return results[1] || 0;
		}
	     var path = $(location).attr('pathname');
	     var ajaxPath = getStoredValue('ajaxPath');
		 var val = getStoredValue('gradeId');
		 var gradeId = setDropdownVal('gradeId', val);
		 /*if(path == '/gotoDashboard.htm'){	
			 if(gradeId){
				$("#loading-div-background2").show();
				getStudentsByGradeId(function(){
					val = getStoredValue('studentRegId');
					var studentRegId = setDropdownVal('studentRegId', val);
					$("#loading-div-background2").hide();
				});	
		 	 }
			 val = getStoredValue('teacheremail');
			 setDropdownVal('teacheremail', val);
				 
		  }else*/ if(path == '/displayClassFiles.htm'){
			  if($.urlParam('fileType') == 'public'){
					 val = getStoredValue('teacherId');
					 var teacherId = setDropdownVal('teacherId', val);
					 if(teacherId && teacherId != 'select'){
						 $("#teacherId").css('color','black');	
						 loadFolders();
					 }
				}
		  }else if(ajaxPath == '/goalSettingExcelDownload.htm') {
			  getAllTeachersByGradeId();
		  }else if(path == '/gradeBook.htm'){
			   if(ajaxPath=='/teacherGoalSettingExcelDownload.htm'){
			 		  downloadGoalSettingReports();
				   }  
		  }else if(path == '/bookApproval.htm'){
				 getBooksToApprove();
		  }else if(path!= '/gotoDashboard.htm'){ 
		 		     getGradeClasses(function(){ 
					 val = getStoredValue('classId');
					var classId = setDropdownVal('classId', val);
					 if(classId){
						if(path == '/assessments.htm' ||  path == '/homeworks.htm'){
							 getUnits(function(){
								 val = getStoredValue('unitId');
								 var unitId = setDropdownVal('unitId', val);
								 if(unitId && unitId != 'select')
									 $("#unitId").css('color','black');	 
								 getLessonByUnitId(function(){
									 val = getStoredValue('lessonId');
									 var lessonId = setDropdownVal('lessonId', val); 
									 if(lessonId && lessonId != 'select')
										 $("#lessonId").css('color','black');	 
									 getAssignmentTypes(function(){
										 val = getStoredValue('assignmentTypeId');
										 assignmentTypeId = setDropdownVal('assignmentTypeId', val); 
										 if(assignmentTypeId && assignmentTypeId != 'select')
											 $("#assignmentTypeId").css('color','black');	 
										  loadQuestions();
										 
									 })
								 });
							 });
						 }else if(path == '/rti.htm'){
							 console.log("sssssssss");
							 getAssignmentTypes(function(){
								 val = getStoredValue('assignmentTypeId');
								 assignmentTypeId = setDropdownVal('assignmentTypeId', val); 
								 if(assignmentTypeId && assignmentTypeId != 'select')
									 $("#assignmentTypeId").css('color','black');	 
								 if(assignmentTypeId==8 || assignmentTypeId==19 || assignmentTypeId==20){ 
								  $('#showUnits').hide();
								  $('#showLessons').hide();
								 loadQuestions();
								 }else{
								 $('#showUnits').show();
								 $('#showLessons').show();
								 getUnits(function(){
									 val = getStoredValue('unitId');
									 var unitId = setDropdownVal('unitId', val);
									 if(unitId && unitId != 'select')
										 $("#unitId").css('color','black');	 
									 getLessonByUnitId(function(){
										 val = getStoredValue('lessonId');
										 var lessonId = setDropdownVal('lessonId', val); 
										 if(lessonId && lessonId != 'select')
											 $("#lessonId").css('color','black');	
										  loadQuestions();
											 
									 })
								 });
								 }
							 });
									 
						 }
						 else if(path == '/curriculumPlan.htm'){
						 loadUnits();
						 }else if(path == '/displayTeacherClassFiles.htm'){
							 getUnits(function(){
								 val = getStoredValue('unitId');
								 var unitId = setDropdownVal('unitId', val);
								 if(unitId && unitId != 'select'){
									 $("#unitId").css('color','black');	
									 displayLessons();
								 }
							 });
						 }else if(path == '/goToAssignLessonsPage.htm'){	 
							 getUnitsByTeacherNAdmin(function(){
								 
							 });
						 }else if(path == '/addLessonstoUnit.htm'){
							 getUnits(function(){
								 val = getStoredValue('unitId');
								 var unitId = setDropdownVal('unitId', val);
								 if(unitId && unitId != 'select'){
									 $("#unitId").css('color','black');	
								     $('#backBtn').show();
									 loadLessons();
								 }
								 var lessonId = getStoredValue('lessonId');
								 if(lessonId)
									$('#previousBtn').show(); 
							 });
						 }else if(path == '/goToAdminRSDD.htm'){
							 getTeachersByClass(function(){
								 val = getStoredValue('teacherId');
								 var teacherId = setDropdownVal('teacherId', val);
								 if(teacherId){
									getSectionsByTeacher(function(){
										val = getStoredValue('csId');
										var csId = setDropdownVal('csId', val);
										getAdminRSDashboards();	 
									 });
								 }else{
									 $("#csId").empty();
									 $("#csId").append($("<option></option>").val('select').html('Select Section')); 
								 }
							 });
						 }else if(path == '/assignGearGameByAdmin.htm' || path == '/reviewMathGameByAdmin.htm' ||path=='/assignRtf.htm'){
							 if(path == '/assignGearGameByAdmin.htm' || path == '/assignRtf.htm'){
								 getClassSections();
							 }else if(path == '/reviewMathGameByAdmin.htm'){
								 getClassSections(function(){
									 val = getStoredValue('csId');
									 var csId = setDropdownVal('csId', val);
								    	getMathAssignedDates(function(){
											val = getStoredValue('assignedDate');
											var studentId = setDropdownVal('assignedDate', val);
											getEltPstAssignmentTitles(function(){
												val = getStoredValue('titleId');
												setDropdownVal('titleId', val);
												showMathGameResults(); 
											});	
										});
								 });
							 }
						 }else{	 
						  getClassSections(function(){
								val = getStoredValue('csId');
								var csId = setDropdownVal('csId', val);
								if(csId){
									if(path == '/assignRti.htm' || path =='/assignAssessments.htm' || path =='/groupPerformanceAssign.htm' || path =='/assignHomeworks.htm' || path =='/gotoCurrentHomework.htm' || path =='/gotoHomeworkManager.htm' ){ 
										getLessonsBycsId(function(){
											val = getStoredValue('lessonId');
											var lessonId = setDropdownVal('lessonId', val);
											if(lessonId && lessonId != 'select'){
												 $("#lessonId").css('color','black');
											if(path =='/gotoCurrentHomework.htm')
												getAssignedHomeworks();
											else if(path =='/gotoHomeworkManager.htm')
												getAssignedHomeworks();
											else
												getAllAssignmentsByAssignmentTypes();
										}
										});
									}else if(path == '/RTIResultsPage.htm'){
										getStudentsBySection(function(){
											val = getStoredValue('studentId');
											var studentId = setDropdownVal('studentId', val);
											if(studentId)
												getAllRTITestResultsByStudent();
										});
									}else if(path == '/showAssignedRtis.htm'){
										showAssignedClasses('showAssignedRTI');
									}else if(path =='/gotoGradeRti.htm' || path =='/gotoGradeAssessments.htm' || path =='/gotoGradeHomeworks.htm' ||  path =='/gotoHomeworkReports.htm'){
										getAssignedDates(csId,function(){
											val = getStoredValue('assignedDate');
											var assignedDate = setDropdownVal('assignedDate', val);
											getAssignmentTitles(function(){
												val = getStoredValue('titleId');
												var titleId = setDropdownVal('titleId', val);
												if(path =='/gotoHomeworkReports.htm')
													showReports();
												else	
													getStudentAsessments(function(){});
											});	
										});
									}else if(path =='/groupGradeAssessments.htm' ){	
										getGroupAssignedDates(function(){
											val = getStoredValue('assignedDate');
											var assignedDate = setDropdownVal('assignedDate', val);
											getGroupAssignmentTitles(function(){
												val = getStoredValue('titleId');
												var titleId = setDropdownVal('titleId', val);
												getStudentAsessments(function(){});
											});	
										});
									}else if(path =='/benchmarkResults.htm'){
										getBenchmarkDates(function(){
											val = getStoredValue('assignedDate');
											var assignedDate = setDropdownVal('assignedDate', val);
											if(assignedDate){
												getFluencyTitles(function(){
													 val = getStoredValue('titleId');
													 var titleId = setDropdownVal('titleId', val);
													 $('#checkAssigneds').attr('checked', true);
													 evaluateBenchmark();
												});
											}
										});
									}else if(path =='/selfAndPeerReviewResults.htm'){
										getAccuracyDates(function(){
											val = getStoredValue('assignedDate');
											var assignedDate = setDropdownVal('assignedDate', val);
											if(assignedDate){
												getAccuracyTitles(function(){
													 val = getStoredValue('titleId');
													 var titleId = setDropdownVal('titleId', val);
													 document.getElementById("showBut").style.visibility="visible";
													 
												});
											}
										});
									} else if(path =='/earlyLiteracyWord.htm' || path =='/assignLetterSet.htm' || path =='/assignWordSet.htm' || path =='/gradeLetterSet.htm' || path =='/gradeWordSet.htm'){
										if(path =='/earlyLiteracyWord.htm'){
											showOrHideCreateWordList(function(){
											});
										}else if(path =='/assignLetterSet.htm' || path =='/assignWordSet.htm'){
											getStudentsBySection(function(){
												val = getStoredValue('studentId');
												var studentId = setDropdownVal('studentId', val);
												setTestType();
											});
										}else{
											getEltAssignedDates(function(){
												val = getStoredValue('assignedDate');
												var assignedDate = setDropdownVal('assignedDate', val);
												getEltPstAssignmentTitles(function(){
													val = getStoredValue('titleId');
													var titleId = setDropdownVal('titleId', val);
													getStudentAsessments(function(){
														getStudentDetailsForGrade();
													});
												});	
											});
										}
									}else if(path =='/phonicSkillTest.htm'){
										getStudentsBySection(function(){
											val = getStoredValue('studentId');
											var studentId = setDropdownVal('studentId', val);
											showTestDiv();
										});
									}else if(path =='/gradePhonicSkill.htm' || path =='/phonicTestSignleReports.htm'){
										getPstAssignedDates(function(){
											val = getStoredValue('assignedDate');
											var studentId = setDropdownVal('assignedDate', val);
											getEltPstAssignmentTitles(function(){
												val = getStoredValue('titleId');
												setDropdownVal('titleId', val);
												if(path =='/gradePhonicSkill.htm')
													getStudentDetailsForGrade();
												else if(path =='/phonicTestSignleReports.htm')
													getPhonicTestReport();
												
											});	
										});
								    }else if(path == '/phonicTestMultipleReports.htm'){
								    	getPstStudentDetails();
								    }else if(path == '/viewAssignedCurriculum.htm'){
								    	getAssignedCurriculum();	
								    }else if(path == '/showAssignedAssessments.htm'){
								    	showAssignedClasses('showAssigned');	
								    }else if(path == '/groupPerformance.htm'){
								    	showGroup();	
								    }else if(path == '/AddStudentsToClass.htm'){
								    	getStudentList();		
								    }else if(path == '/splitStudentsToGroup.htm'){
								    	getPerformanceGroups(function(){
								    		val = getStoredValue('perGroupId');
											var perGroupId = setDropdownVal('perGroupId', val);
											getStudentsByGroupId();
								    	});		
								    }else if(path == '/displayRoster.htm' || path == 'takeAttendancePage.htm' || path == 'updateAttendancePage.htm' || path == 'classRegistration.htm'){
								    	getStudentDetails();
								    }
								}
							 }); 
						 }
					 }
				});
			}
	});
	
 // Start common dropdowns 1.classes 2.sections
	function getGradeClasses(callback) {
		//console.log("inside admin");
		var gradeId = $('#gradeId').val();
		//console.log(gradeId);
		if(gradeId != 'select' && gradeId != undefined){	
			$.ajax({
				type : "GET",
				url : "getTeacherClasses.htm",
				data : "gradeId=" + gradeId,
				success : function(response) {
					$("#classId").empty();
					$("#classId").append(
							$("<option></option>").val('select').html('Select Subject'));
					var teacherClasses = response.teacherClasses;
					$.each(teacherClasses, function(index, value) {
						$("#classId").append(
								$("<option></option>").val(value.classId).html(
										value.className));
					});
					$("#csId").empty();
					$("#csId").append($("<option></option>").val('select').html('Select Section'));
					$("#assignedDate").empty();
					$("#assignedDate").append($("<option></option>").val('select').html('Select Date'));
					$("#titleId").empty();
					$("#titleId").append($("<option></option>").val('select').html('Select Title'));
					$("#studentId").empty();
					$("#studentId").append($("<option></option>").val('select').html('Select Student'));
					$("#unitId").empty();
					$("#unitId").append($("<option></option>").val('select').html('Select Unit'));
					$("#lessonId").empty();
					$("#lessonId").append($("<option></option>").val('select').html('Select Lesson'));
					$("#assignmentTypeId").empty();
					$("#assignmentTypeId").append($("<option></option>").val('select').html('Select AssignmentType'));
					 $("#unitLessonDiv").html('');
					 $('#classId').css('color','gray');
					 $("#csId").css('color','gray');
					 $("#assignedDate").css('color','gray');
					 $('#titleId').css('color','gray');
					 $("#studentId").css('color','gray');
					if(callback)
						callback();
				}
			});
		}else{
			$("#classId").empty();
			$("#classId").append($("<option></option>").val('select').html('Select Subject'));
			$("#csId").empty();
			$("#csId").append($("<option></option>").val('select').html('Select Section'));
			$("#assignedDate").empty();
			$("#assignedDate").append($("<option></option>").val('select').html('Select Date'));
			$("#titleId").empty();
			$("#titleId").append($("<option></option>").val('select').html('Select Title'));
			$("#studentId").empty();
			$("#studentId").append(	$("<option></option>").val('select').html('Select Student'));
			$("#unitId").empty();
			$("#unitId").append($("<option></option>").val('select').html('Select Unit'));
			$("#lessonId").empty();
			$("#lessonId").append($("<option></option>").val('select').html('Select Lesson'));
			$("#assignmentTypeId").empty();
			$("#assignmentTypeId").append($("<option></option>").val('select').html('Select AssignmentType'));
			$("#ReportList").html("");
			$("#unitLessonDiv").html('');
			 $('#classId').css('color','gray');
			 $("#csId").css('color','gray');
			 $("#assignedDate").css('color','gray');
			 $('#titleId').css('color','gray');
			 $("#studentId").css('color','gray');
		}
	}
	function getClassSections(callback) {
		var	gradeId = $('#gradeId').val();
		var	classId = $('#classId').val();
		if(gradeId != 'select' && classId != 'select'){
			$.ajax({
				type : "GET",
				url : "getTeacherSections.htm",
				data : "gradeId=" + gradeId + "&classId=" + classId,
				success : function(response1) {
					$("#csId").empty();
					$("#csId").append($("<option></option>").val('select').html('Select Section'));
					var teacherSections = response1.teacherSections;
					$.each(teacherSections, function(index, value) {
						$("#csId").append($("<option></option>").val(value.csId).html(value.teacher.userRegistration.firstName +" "+value.teacher.userRegistration.lastName  +" - "+value.section.section));
					});
					$("#studentId").empty();
					$("#studentId").append($("<option></option>").val('select').html('Select Student'));
					$("#assignedDate").empty();
					$("#assignedDate").append($("<option></option>").val('select').html('Select Date'));
					$("#titleId").empty();
					$("#titleId").append($("<option></option>").val('select').html('Select Title'));
					$("#csId").css('color','gray');
					$("#assignedDate").css('color','gray');
					$('#titleId').css('color','gray');
					$("#studentId").css('color','gray');
					if(callback)
						callback();
				}
	
			});
		}else{
			$("#csId").empty();
			$("#csId").append(
					$("<option></option>").val('select').html('Select Section'));
			$("#assignedDate").empty();
			$("#assignedDate").append(
					$("<option></option>").val('select').html('Select Date'));
			$("#titleId").empty();
			$("#titleId").append(
					$("<option></option>").val('select').html('Select Title'));
			$("#studentId").empty();
			$("#studentId").append(
					$("<option></option>").val('select').html('Select Student'));
			 $("#csId").css('color','gray');
			 $("#assignedDate").css('color','gray');
			 $('#titleId').css('color','gray');
			 $("#studentId").css('color','gray');
			 $("#ReportList").html("");
		}

	}
 // End common dropdowns 1.classes 2.sections
	
	function getSections(callback) {
		var	gradeId = $('#gradeId').val();
		var	classId = $('#classId').val();
		if(gradeId != 'select' && classId != 'select'){
			$.ajax({
				type : "GET",
				url : "getTeacherSections.htm",
				data : "gradeId=" + gradeId + "&classId=" + classId,
				success : function(response1) {
					$("#csId").empty();
					$("#csId").append($("<option></option>").val('select').html('Select Section'));
					var teacherSections = response1.teacherSections;
					$.each(teacherSections, function(index, value) {
						$("#csId").append($("<option></option>").val(value.csId).html(value.section.section));
					});
					$("#studentId").empty();
					$("#studentId").append($("<option></option>").val('select').html('Select Student'));
					$("#assignedDate").empty();
					$("#assignedDate").append($("<option></option>").val('select').html('Select Date'));
					$("#titleId").empty();
					$("#titleId").append($("<option></option>").val('select').html('Select Title'));
					$("#csId").css('color','gray');
					$("#assignedDate").css('color','gray');
					$('#titleId').css('color','gray');
					$("#studentId").css('color','gray');
					if(callback)
						callback();
				}
	
			});
		}else{
			$("#csId").empty();
			$("#csId").append(
					$("<option></option>").val('select').html('Select Section'));
			$("#assignedDate").empty();
			$("#assignedDate").append(
					$("<option></option>").val('select').html('Select Date'));
			$("#titleId").empty();
			$("#titleId").append(
					$("<option></option>").val('select').html('Select Title'));
			$("#studentId").empty();
			$("#studentId").append(
					$("<option></option>").val('select').html('Select Student'));
			 $("#csId").css('color','gray');
			 $("#assignedDate").css('color','gray');
			 $('#titleId').css('color','gray');
			 $("#studentId").css('color','gray');
			 $("#ReportList").html("");
		}

	}	
	
	function getLessonByUnitId(callback){
		var unitSelected = dwr.util.getValue('unitId');
		var lessonsCallBack = function (list) {
			if (list != null) {
				$("#assignmentTypeId").empty();
				$("#assignmentTypeId").append($("<option></option>").val('select').html('Select AssignmentType'));
				dwr.util.removeAllOptions('lessonId');
				$("#lessonId").append($("<option></option>").val('select').html('Select Lesson'));
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

			}
			if(callback)
				callback();
		}
		if(unitSelected != "" && unitSelected != "select" && unitSelected != "Select Unit"){
			assessmentService.getLessonsByUnitId(unitSelected, {
				callback : lessonsCallBack
			});
		}else{
			$("#assignmentTypeId").empty();
			$("#assignmentTypeId").append($("<option></option>").val('select').html('Select AssignmentType'));
			$("#lessonId").empty();
			$("#lessonId").append($("<option></option>").val('select').html('Select Lesson'));
		}
	}
 // Start teacher assigned lessons
	function getLessonsBycsId(callback) {
        var csId = $('#csId').val();
		if(csId != 'select'){
			var loadTeacherAssignedLessonsCallBack = function(list) {
				if (list != null) {
					dwr.util.removeAllOptions('lessonId');
					$("#lessonId").append($("<option></option>").val('select').html('Select Lesson'));
					var teacherObj = dwr.util.getValue('teacherObj');
					var myselect = document.getElementById('lessonId');		
					for(var i=0;i<list.length;i++){
						var objOption = document.createElement("option");
						var user = list[i].lesson.userRegistration.user.userType;
					    objOption.text = list[i].lesson.lessonName;
					    objOption.value = list[i].lesson.lessonId;
					    if(user =="admin" && teacherObj){
					    	objOption.style.color="blue";
					    }
					    if(user == "teacher" && !teacherObj){
					    	objOption.style.color="blue";
					    }
					    myselect.options.add(objOption);
					}
					if(callback)
						callback();
				} else {
					alert("No data found");
				}
			}
			assignAssessmentsService.getTeacherAssignLessons(csId, {
				callback : loadTeacherAssignedLessonsCallBack
			});
		}
	}
// End teacher assigned lessons

// Start get Students by sections	
	function getStudentsBySection(callback) {
		var csId = $('#csId').val();
		$("#studentId").val('');
		if(csId != 'select'){
				$.ajax({
					type : "GET",
					url : "getStudentsListsByCsId.htm",
					data : "csId=" + csId,
					success : function(response) {
						var studentList = response.studentList;
						$("#studentId").empty();
						$("#studentId").append($("<option></option>").val('select').html('Select Student'));
						$.each(studentList,
							function(index, value) {
								var studentName = value.student.userRegistration.firstName
													+ " "
													+ value.student.userRegistration.lastName;
						$("#studentId").append(
							$("<option></option>").val(value.student.studentId).html(studentName));
								});
						if(callback)
							callback();
					}
				});
			}else{
				$("#createWordDiv").hide();
				$("#studentId").empty();
				$("#studentId").append($("<option></option>").val('select').html('Select Student'));
		   }
	}

// End get Students by sections
	
// Start get Units 
	function getUnits(callback) {
		console.log("get Units");
		var gradeSelected = dwr.util.getValue('gradeId');
		var classSelected = dwr.util.getValue('classId');
		var assignmentTypeId=dwr.util.getValue('assignmentTypeId');
		if(assignmentTypeId==8 || assignmentTypeId==20 || assignmentTypeId==19){
			$('#showUnits').hide();
			$('#showLessons').hide();
			$('#btAdd').show();
			$('#backBtn').show();
		}else{
			$('#showUnits').show();
		    $('#showLessons').show();
		}
		var regId = dwr.util.getValue('childId');
		var childGradeId = dwr.util.getValue('gradeId:'+regId);
		var unitsCallBack = function (list) {
			$("#unitLessonDiv").html('');
			$("#showFilesDiv").html('');
			if (list.length > 0) {
				$("#lessonId").empty();
				$("#lessonId").append($("<option></option>").val('select').html('Select Lesson'));
				/*$("#assignmentTypeId").empty();
				$("#assignmentTypeId").append($("<option></option>").val('select').html('Select AssignmentType'));*/
				dwr.util.removeAllOptions('unitId');
				$("#unitId").append($("<option></option>").val('select').html('Select Unit'));
				dwr.util.addOptions('unitId', list, 'unitId', 'unitName');
			} else {
				dwr.util.removeAllOptions('unitId');
				$("#unitId").append(
						$("<option></option>").val('select').html('Select Unit'));
			}
			if(callback)
				callback();
		}
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
			$("#lessonId").empty();
			$("#lessonId").append($("<option></option>").val('select').html('Select Lesson'));
			$("#assignmentTypeId").empty();
			/*$("#assignmentTypeId").append($("<option></option>").val('select').html('Select AssignmentType'));*/
			dwr.util.removeAllOptions('unitId');
			$("#unitId").append($("<option></option>").val('select').html('Select Unit'));
		}
	}
// End get Units
	
	function getUnitsByTeacherNAdmin(callback) {
		var gradeId = $('#gradeId').val();
		var classId = $('#classId').val();
		var unitContainer = $(document.getElementById('unitDiv'));
		$(unitContainer).empty();
		if(gradeId > 0 && classId > 0){
			$("#loading-div-background").show();
			$.ajax({
				type : "GET",
				url : "getUnitsByTeacherNAdmin.htm",
				data : "gradeId=" + gradeId + "&classId=" + classId,
				success : function(response) {
					alert(response);
					$(unitContainer).append(response);
					$.ajax({
						type : "GET",
						url : "getTeacherSections.htm",
						data : "gradeId=" + gradeId + "&classId=" + classId,
						success : function(response1) {
							var teacherSections = response1.teacherSections;
							$.each(teacherSections, function(index, value) {
								$("#sectionId").append($("<option></option>").val(value.csId).html(value.section.section));
							});
							if(callback)
								callback();
						}
					});
					 $("#loading-div-background").hide();
				}
			});
		}
	}	
// Start get Assignment Type 	
	function getAssignmentTypes(callback){
		var lessonId = dwr.util.getValue('lessonId');	
		var usedFor = dwr.util.getValue('usedFor');
		var assessmentForCallBack = function(list) {
			if (list != null) {
				dwr.util.removeAllOptions('assignmentTypeId');
				$("#assignmentTypeId").append(
						$("<option></option>").val('select').html('Select AssignmentType'));
				dwr.util.addOptions('assignmentTypeId', list, 'assignmentTypeId', 'assignmentType');
			}
			if(callback)
				callback();
		}	
		if(lessonId != "" && lessonId != "Select Lesson" && lessonId != "select"){
			assessmentService.getAssignments(usedFor, {
				callback : assessmentForCallBack
			});
		}else{
			dwr.util.removeAllOptions('assignmentTypeId');
			$("#assignmentTypeId").append($("<option></option>").val('select').html('Select AssignmentType'));
		}
	}
// End get Assignment Type 
// Start get all Literacy Development test results by student	
	function getAllRTITestResultsByStudent(){
		var csId = $('#csId').val();
		var usedFor = document.getElementById("usedFor").value;
		var	studentId = document.getElementById("studentId").value;
		if(csId != 'select' && studentId != 'select'){
			$("#loading-div-background").show();
			$.ajax({
				url : "studentRTITestGraded.htm",
				data: "csId="+csId+"&usedFor="+usedFor+"&studentId="+studentId,
				success : function(data) {
					$('#resultsDiv').html(data);
					$("#loading-div-background").hide();
				}
			}); 	
		}else{
			$('#resultsDiv').html('');
			return false;
		}
	}	
// End get all Literacy Development test results by student
	
function getAllAssignmentsByAssignmentTypes() {
		var usedFor = $('#usedFor').val();
		var isGroup = $('#isGroup').val();	
		var	lessonId = $('#lessonId').val();
		$("#questionList").empty();
		if(lessonId == ''){
			document.getElementById('assignasssessments').style.visibility = "hidden";
			document.getElementById('reTestDiv').style.display = 'none';
			return false;
		}
		var csId = $('#csId').val();
		if(csId != 'select' && lessonId != 'select'){
			$("#loading-div-background").show();
			$.ajax({
						type : "GET",
						url : "getStudentsList.htm",
						data : "csId=" + csId + "&usedFor=" + usedFor,
						success : function(response) {
							var studentList = response.studentList;
							var rtiList = response.rtiList;
							var perGroupList = response.perGroupList;
							$("#studentId").empty();
							$.each(studentList,
								function(index, value) {
									var studentName = value.student.userRegistration.firstName
														+ " "
														+ value.student.userRegistration.lastName;
									$("#studentId").append(
										$("<option></option>").val(
												value.student.studentId).html(studentName));
											});
							$("#rtiGroupId").empty();
							$.each(rtiList, function(index, value) {
								$("#rtiGroupId").append(
										$("<option></option>")
												.val(value.rtiGroupId).html(
														value.rtiGroupName));
							});
							$("#perGroupId").empty();
							$.each(perGroupList, function(index, value) {
								$("#perGroupId").append(
										$("<option></option>")
												.val(value.performanceGroupId).html(value.groupName));
							});
							$.ajax({
										type : "GET",
										url : "getAssignmentTypes.htm",
										data : "csId=" + csId + "&usedFor="
												+ usedFor,
										success : function(response1) {
											var assignmentTypes = response1.assignmentTypes;
											$("#assignmentType").empty();
											$("#assignmentType")
													.append(
															$("<option></option>").val('0').html('Select AssignmentType'));
											$.each(assignmentTypes,
												function(index, value) {
																if(isGroup=="true" && value.assignmentTypeId==13){
																	$("#assignmentType").append(
																			$("<option></option>").val(value.assignmentTypeId).html(value.assignmentType));
																}
																if(isGroup!="true"){
																$("#assignmentType").append(
																	$("<option></option>").val(value.assignmentTypeId).html(value.assignmentType));
																}
												});
											if (usedFor == 'rti') {
												document.getElementById('getrtigroups').style.visibility = "visible";
											}
											document.getElementById('assignasssessments').style.visibility = "visible";
											if (usedFor == 'rti' || isGroup == "true") {
												document.getElementById("reTestDiv").style.display = 'none';
											} else {
												document.getElementById("reTestDiv").style.display = '';
											}
											$("#loading-div-background").hide();
										}
									});
						}
				});
		}else{
			document.getElementById('assignasssessments').style.visibility = "hidden";
		}
		
	}	

function getAssignedDates(csId,callback) {
	var usedFor = $('#usedFor').val();
	if(!csId)
		 csId = $('#csId').val();
	if(usedFor && csId != "select"){
		$.ajax({
			type : "GET",
			url : "getTeacherAssignedDates.htm",
			data : "csId=" + csId + "&usedFor=" + usedFor,
			success : function(response2) {
				var teacherAssignedDates = response2.teacherAssignedDates;
				$("#assignedDate").empty();
				$("#assignedDate").append(
						$("<option></option>").val('select').html('Select Date'));
				$.each(teacherAssignedDates, function(index, value) {
					$("#assignedDate").append(
							$("<option></option>").val(value.dateAssigned).html(
									getFormattedDate(value.dateAssigned)));
				});
				if(callback)
					callback();
			}
		});
	}else{
		$("#assignedDate").empty();
		$("#assignedDate").append(
				$("<option></option>").val('select').html('Select Date'));
		$("#titleId").empty();
		$("#titleId").append(
				$("<option></option>").val('select').html('Select Title'));
		 $("#ReportList").html("");
	}
}

function getAssignmentTitles(callback){
	var usedFor = $('#usedFor').val();
	var	classId = $('#classId').val();
    var csId = $('#csId').val();
	var	assignedDate = $('#assignedDate').val();
	if(assignedDate != 'select' && csId != 'select' && classId != 'select'){
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
				if(callback)
					callback();
			}
		});
	}else{
		$("#titleId").empty();
		$("#titleId").append(
				$("<option></option>").val('select').html('Select Title'));
		/*alert("Please select the Filters");*/
	}
}

function getStudentAsessments(callback){
	var gradeId=$('#gradeId').val();
	var classId=$('#classId').val();
	var csId=$('#csId').val();
	var assignedDate=$('#assignedDate').val();
	assignedDate = new Date(assignedDate);
	var assignmentId=$('#titleId').val();
	var tab = $('#tab').val();	
	$("#Evaluate").empty(); 
	$("#StuAssessQuestionsList").empty(); 
	if(assignmentId > 0 && gradeId >0 && classId >0 && csId >0 && assignedDate != 'Invalid Date'){
		
		if(tab == "gradeGroup"){
			$("#loading-div-background").show();
			$.ajax({  
				url : "getGroupAssessmentTests.htm", 
			    data: "assignmentId="+assignmentId,
			    success: function(response) {
			    	 $("#Evaluate").html(response);
			    	 $("#loading-div-background").hide();
			    }
			}); 
		}else{
			
			$("#loading-div-background").show();
			$.ajax({  
				url : "getStudentAssessmentTests.htm", 
			    data: "assignmentId="+assignmentId,
			    success: function(response) {
			    	 $("#Evaluate").html(response);
			    	 $("#loading-div-background").hide();
			    }
			}); 
		}
		if(callback)
			callback();
	}
}

function getBenchmarkDates(callback) {
	var usedFor = $('#usedFor').val();
	var csId = $('#csId').val();
	if(csId != 'select'){
		$.ajax({
			type : "GET",
			url : "getBenchmarkDates.htm",
			data : "csId=" + csId + "&usedFor=" + usedFor,
			success : function(response2) {
				var teacherAssignedDates = response2.teacherAssignedDates;
				$("#assignedDate").empty();
				$("#assignedDate").append(
						$("<option></option>").val('select').html('Select Date'));
				$.each(teacherAssignedDates, function(index, value) {
					$("#assignedDate").append(
							$("<option></option>").val(value.dateAssigned).html(
									getFormattedDate(value.dateAssigned)));
				});
				if(callback)
					callback();
			}
		});
	}else{
		$("#assignedDate").empty();
		$("#assignedDate").append(
				$("<option></option>").val('select').html('Select Date'));
		$("#titleId").empty();
		$("#titleId").append(
				$("<option></option>").val('select').html('Select Title'));
	}
}

function getEltPstAssignmentTitles(callback){
	var usedFor = $('#usedFor').val();
	var csId = $('#csId').val();
	var assignedDate= $('#assignedDate').val();
	var page = dwr.util.getValue('page');
	var divId = dwr.util.getValue('divId');
	if(assignedDate != 'select'){
	$.ajax({
		type : "GET",
		url : "getAssignmentsTitles.htm",
		data : "csId=" + csId + "&usedFor=" + usedFor+"&assignedDate="+assignedDate+ "&page=" + page,
		success : function(response2) {
			var assignmentTitles = response2.assignmentTitles;
			$("#titleId").empty();
			$("#titleId").append(
					$("<option></option>").val('select').html('select'));
			$.each(assignmentTitles, function(index, value) {
				$("#titleId").append(
						$("<option></option>").val(value.assignmentId).html(value.title));
			});
			if(divId == 'gradeLetterSet'){
		   		$('#GradeLetterSetDetailsPage').html('');
		   	}else if(divId == 'gradeWordSet'){
		   		$('#GradeWordSetDetailsPage').html('');
		   	}
			 if(callback)
				callback();
		}
	});
	}else{
		if(divId == 'gradeLetterSet'){
	   		$('#GradeLetterSetDetailsPage').html('');
	   	}else if(divId == 'gradeWordSet'){
	   		$('#GradeWordSetDetailsPage').html('');
	   	}
		$("#titleId").empty();
		$("#titleId").append(
				$("<option></option>").val('select').html('select'));
		$("#titleId").css('color','gray');
	}
}


function getPerformanceGroups(callback){
	var csId = $('#csId').val();
	var withStudents = "NO";
	var performanceGroupForCallBack = function (list) {
		if (list != null) {
			dwr.util.removeAllOptions('perGroupId');
			dwr.util.addOptions(perGroupId, ["Select Group"]);
			dwr.util.addOptions('perGroupId', list, 'performanceGroupId', 'groupName');
		}
		 if(callback)
				callback();
	}
	if(csId!= 'select'){
		performanceService.getPerformanceGroups(csId,withStudents, {
			callback : performanceGroupForCallBack
		});
	}
}

function getStudentsByGradeId(callback){
	  var obj =	document.getElementById("gradeId");
	  if(obj){
		  var gradeId = obj.value;
		  if(gradeId  != 'select'){
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
								val(value.userRegistration.emailId).html(
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
function getAccuracyDates(callback) {
	var usedFor = $('#usedFor').val();
	var csId = $('#csId').val();
	if(csId != 'select'){
		$.ajax({
			type : "GET",
			url : "getAccuracyDates.htm",
			data : "csId=" + csId + "&usedFor=" + usedFor,
			success : function(response2) {
				var teacherAssignedDates = response2.teacherAssignedDates;
				$("#assignedDate").empty();
				$("#assignedDate").append(
						$("<option></option>").val('select').html('Select Date'));
				$.each(teacherAssignedDates, function(index, value) {
					$("#assignedDate").append(
							$("<option></option>").val(getDBFormattedDate(value.dateAssigned)).html(
									getFormattedDate(value.dateAssigned)));
				});
				if(callback)
					callback();
			}
		});
	}else{
		$("#assignedDate").empty();
		$("#assignedDate").append(
				$("<option></option>").val('select').html('Select Date'));
		$("#titleId").empty();
		$("#titleId").append(
				$("<option></option>").val('select').html('Select Title'));
	}
}
function getAccuracyTitles(callback){
	var csId = $('#csId').val();
	var classId = $('#classId').val();
	var assignedDate= $('#assignedDate').val();
	if(assignedDate != 'select' && csId >0 && classId > 0){
		$.ajax({
			type : "GET",
			url : "getAccuracyTitles.htm",
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
	

