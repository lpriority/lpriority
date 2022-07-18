function parentRegVal()
{
	var title=$('#parentTitle').val();
	if(title== '' || title==null || title=='select'){
		 alert("Please select the title");
         $('#parentTitle').next().show();
         return false;
      }
	 var firstname=$('#parentfirstname').val();
	 if(firstname== '' || firstname==null){
		 alert("Please enter the firstname");
         $('#parentfirstname').next().show();
         return false;
      }
	 var lastname=$('#parentLastName').val();
	 if(lastname== '' || lastname==null){
		 alert("Please enter the LastName");
         $('#parentLastName').next().show();
         return false;
      }
	 var address1=$('#parentAddress1').val();
	 if(address1== '' || address1==null){
		 alert("Please enter the address1");
         $('#parentAddress1').next().show();
         return false;
      }
	 var address2=$('#parentAddress2').val();
//	  if(address2== '' || address2==null){
//		 alert("Please enter the address2");
//         $('#parentAddress2').next().show();
//         return false;
//      }
	 var countryId=$('#countryId').val();
	  if(countryId== '' || countryId==null || countryId=='select'){
		 alert("Please select the country");
         $('#countryId').next().show();
         return false;
      }
	 var stateId=$('#stateId').val();
	 if(stateId== '' || stateId==null || stateId=='select'){
		 alert("Please select the state");
         $('#stateId').next().show();
         return false;
      }
	 var city=$('#city').val();
	 if(city== '' || city==null){
		 alert("Please enter the city name");
         $('#city').next().show();
         return false;
      }
	 var parentZipcode=$('#parentZipcode').val();
	  if(parentZipcode== '' || parentZipcode==null){
		 alert("Please enter the Zipcode");
         $('#parentZipcode').next().show();
         return false;
      }else if(!$.isNumeric(parentZipcode))
       {
		 alert("please enter the numeric character");
		 $('#parentZipcode').val('');
		 return false;
	   }
	 var pemailId=$('#pemailId').val();
	 if(pemailId== '' || pemailId==null || pemailId=='select'){
		 alert("Please select the emailId");
         $('#pemailId').next().show();
         return false;
      } else if(IsEmail(pemailId)==false){
    	  alert("Please enter valid email address");
          $('#pemailId').show();
          return false;
      }
	 
	 var parentPassword=$('#parentPassword').val();
	  if(parentPassword== '' || parentPassword==null){
		 alert("Please enter the Password");
         $('#parentPassword').next().show();
         return false;
      }
	 var parentConfirmPassword=$('#parentConfirmPassword').val();
	 if(parentConfirmPassword== '' || parentConfirmPassword==null){
		 alert("Please enter the Confirm Password");
         $('#parentConfirmPassword').next().show();
         return false;
      }
	 if(parentPassword != parentConfirmPassword){
		 systemMessage("Confirm Password not match with Password"); 
		 document.getElementById("parentConfirmPassword").value = "";
		 $('#parentConfirmPassword').focus();
		 return false;
	 }
	 var parentsecurityid=$('#parentsecurityid').val();
	 if(parentsecurityid== '' || parentsecurityid==0 || parentsecurityid=='select'){
		 alert("Please select the security question");
         $('#parentsecurityid').next().show();
         return false;
      }
	 var securityanswer=$('#securityanswer').val();
	 if(securityanswer== '' || securityanswer==null){
		 alert("Please enter the security answer");
         $('#securityanswer').next().show();
         return false;
      }
	 var parentqualification=$('#parentqualification').val();
	 if(parentqualification== '' || parentqualification==null){
		 alert("Please enter the parent qualification");
         $('#parentqualification').next().show();
         return false;
      }
	 var parentphoneno=$('#parentphoneno').val();
	 intRegex =  /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
	 if(parentphoneno== '' || parentphoneno==null){
		 alert("Please enter the parentphoneno");
         $('#parentphoneno').next().show();
         return false;
      }else if((parentphoneno.length < 6) || (!intRegex.test(parentphoneno)))
    	  {
    	       alert('Please enter a valid phone number.');
    	       return false;
    	  }
    	  
	 $.ajax({  
			url : "SaveParentDetails.htm", 
	        data: "title=" + title+"&firstname="+firstname+"&lastname="+lastname+"&address1="+address1+"&address2="+address2+"&countryId="+countryId+"&stateId="+stateId+"&city="+city+"&parentZipcode="+parentZipcode+"&pemailId="+pemailId+"&parentPassword="+parentPassword+"&parentConfirmPassword="+
	        parentConfirmPassword+"&parentsecurityid="+parentsecurityid+"&securityanswer="+securityanswer+"&parentqualification="+parentqualification+"&parentphoneno="+parentphoneno,
	        success : function(data) { 
	        	window.location.replace(data);
	        	
	            
	        }  
	    }); 
}
function IsEmail(email) {
    var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if(!regex.test(email)) {
       return false;
    }else{
       return true;
    }
  }

function loadChildClasess() {
	var studentId = $('#studentId').val();
	$("#csId").empty();
	$("#csId").append(
			$("<option></option>").val('').html(
					'Select Subject'));
	if(studentId != "select" && studentId != ''){		
		$.ajax({
				type : "GET",
				url : "loadGradeClasess.htm",
				data : "studentId=" + studentId,
				success : function(response) {					
					var clasess = response.classMap;
					$.each(clasess, function(index, value) {
						$("#csId").append(
								$("<option></option>").val(index).html(
										value));
					});
				}
			});
	}else{
		return false;
	}
}

function loadClasess() {
	var studentId = $('#studentId').val();
	$("#classId").empty();
	$("#classId").append(
			$("<option></option>").val('').html(
					'Select Subject'));
	if(studentId != "select" && studentId != ''){		
		$.ajax({
				type : "GET",
				url : "loadClasess.htm",
				data : "studentId=" + studentId,
				success : function(response) {	
					document.getElementById("gradeName").value = response.gradeName;
					var clasess = response.classMap;
					$.each(clasess, function(index, value) {
						$("#classId").append(
								$("<option></option>").val(index).html(
										value));
					});
				}
			});
	}else{
		document.getElementById("gradeName").value = "";
		return false;
	}
}
function getChildsTestResults()
{
	var studentId=$('#studentId').val();
	var classId = $("#csId").val();
    var usedFor = document.getElementById("usedFor").value;
    $('#StuCompleteAssessment').html("");
    $("#StuAssessmentQuestionsList").empty();
	if(classId != 'select' && classId != ""){
		$("#loading-div-background").show();
		$.ajax({
			url : "childTestGraded.htm",
			data: "classId="+classId + "&usedFor="+usedFor+"&studentId="+studentId,
			success : function(data) {
				$('#StuCompleteAssessment').html(data);
				$("#loading-div-background").hide();
			}
		});
	}else{
		return false;
	}
}
function getStudentsTestResultsDetails(id,studentAssignmentId,assignmentType,userType,regId,gradedStatus,status,studentId,assignmentId){
	if(status == 'pending'){
		alert("Test not yet submitted !!");
		return false;
	}else if(status == 'submitted'){
		jQuery.curCSS = jQuery.css;
		if(assignmentType == 'Early Literacy Letter'){
			 $("#dialog"+id).dialog({
					overflow: 'auto',
					dialogClass: 'no-close',
				    autoOpen: false,
					position: {my: "center", at: "center", of:window ,within: $("body") },
				    title: 'Early Literacy Student Test Result',
				    draggable: true,
				    width : 1200,
				    height : 350,
				    resizable : true,
				    modal : false
				});
	   	}else if(assignmentType == 'Early Literacy Word'){
	   	 $("#dialog"+id).dialog({
				overflow: 'auto',
				dialogClass: 'no-close',
			    autoOpen: false,
				position: {my: "center", at: "center", of:window ,within: $("body") },
			    title: 'Early Literacy Student Test Result',
			    draggable: true,
			    width : 1200,
			    height : 550,
			    resizable : true,
			    modal : false
			});
	   	}
		 $("#dialog"+id).css('overflow', 'inherit');
		 $("#dialog"+id).dialog("open");
		 var getEarlyLiteracyGradeCallBack = function(list){
			 var setType = '';
			 var setsArray = new Array();
			 var setNameArray = new Array();
			 for (i=0;i<list.length;i++){
				 setsArray.push(list[i].set);
				 setNameArray.push(list[i].setName);
				 setType = list[i].setType;
			 }
			 var iframe = $('#iframe'+id);
			 iframe.attr('src', "gradeEarlyLiteracyTest.htm?setsArray=" +setsArray+ "&setNameArray="+setNameArray + "&setType="+setType +"&studentAssignmentId="+studentAssignmentId + "&dialogDivId=dialog"+id+"&divId="+assignmentType+"&userType="+userType+"&regId="+regId+"&gradedStatus="+gradedStatus+"&studentId="+studentId+"&assignmentId="+assignmentId+"&page=studentTestResult");
		 }
		 
		 earlyLiteracyTestsService.getEarlyLiteracyTests(studentAssignmentId,{
			callback : getEarlyLiteracyGradeCallBack
		 });
	}
}

function getChildsBenchmarkTestResults(assignmentTypeId){
	var studentId=$('#studentId').val();
	var classId = $("#csId").val();
    var usedFor = document.getElementById("usedFor").value;
    $('#studentsBenchmarkTestResultsDiv').html("");
	if(classId != 'select' && classId != ""){
		$("#loading-div-background").show();
		$.ajax({
			url : "getChildBenchmarkTests.htm",
			data: "classId="+classId + "&usedFor="+usedFor+"&studentId="+studentId+"&assignmentTypeId="+assignmentTypeId,
			success : function(data) {
				$('#studentsBenchmarkTestResultsDiv').html(data);
				$("#loading-div-background").hide();
			}
		});
	}else{
		return false;
	}
}
function getStudentBenchmarkQuestions(assignmentId,studentAssignmentId,assignmentTypeId){
$.ajax({
	url : "getStudentBenchmarkQuestions.htm",
	data: "studentAssignmentId="+studentAssignmentId + "&assignmentId="+assignmentId+"&assignmentTypeId="+assignmentTypeId,
	success : function(data) {
		$('#StuBenchmarkQuestionsList').html(data);
	}
});
}
function playRetellAudio(){

var path = document.getElementById("audioPath").value;

	$.ajax({
	type : "GET",
	url : "playAudio.htm",
	data : "filePath="+path,
	success : function(response) {
				}
});
}
function getHomeworkReportsByDate(){
var usedFor = $('#usedFor').val();
var csId = $('#csId').val();
var assignedDate= document.getElementById("assignedDate").value;
$.ajax({
	url : "getHomeworkReportsByDate.htm",
	data: "csId="+csId + "&usedFor="+usedFor+"&assignedDate="+assignedDate,
	success : function(data) {
		$('#EvaluateHomework').html(data);
	}
});
}
function getStudentTestQuestions(assignmentId,studentAssignmentId,assignmentTypeId,status,testCount){
if(status == "pending"){
	alert("This task not yet submitted");
	return;
}
var usedFor=$('#usedFor').val();
$("#StuHomeQuestionsList").empty();
var tab=$('#tab').val();

	$.ajax({
		type : "GET",
		url : "getStudentHomeworkQuestions.htm",
		data : "studentAssignmentId=" + studentAssignmentId + "&usedFor="+usedFor +"&assignmentTypeId="+assignmentTypeId+"&assignmentId="+assignmentId, 
		//+ "&tab="+ tab,
		success : function(response) {
			//smoothScroll($("#StuHomeQuestionsList").html(response));
			var dailogContainer = $(document.getElementById('StuHomeQuestionsList'));
			var screenWidth = $( window ).width() - 10;
			var screenHeight = $( window ).height() - 10;
			$('#StuHomeQuestionsList').empty();  
			$(dailogContainer).append(response);
			$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
				  $(".ui-dialog-titlebar-close").show();
			},
			dialogClass: 'myTitleClass'
			});	
			if(assignmentTypeId==14){
				$(dailogContainer).dialog("option", "title", "Spelling");
			}else{
			$(dailogContainer).dialog("option", "title", "Test Results");}
			$(dailogContainer).scrollTop("0");
			
			
			
		}
	});
		
}
function getStudentTestResult(assignmentId,studentAssignmentId,assignmentTypeId,status,testCount){
	if(status == "pending"){
		alert("This task not yet submitted");
		return;
	}
	var usedFor=$('#usedFor').val();
	$("#getCompletedTestQuestions").empty();
	var tab=$('#tab').val();
	if(assignmentTypeId == 13){
		$("#loading-div-background").show();
		$.ajax({
			type : "GET",				
			url : "gradePerformanceTest.htm",
			data : "studentAssignmentId=" + studentAssignmentId + "&assignmentTypeId="+assignmentTypeId + "&tab="+tab,
			async: true,
			success : function(response) {
				var dailogContainer = $(document.getElementById('performanceDailog'));
				var screenWidth = $( window ).width() - 10;
				var screenHeight = $( window ).height() - 10;
				$('#performanceDailog').empty();  
				$(dailogContainer).append(response);
				$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true,
					open:function () {
						$(".ui-dialog-titlebar-close").show();
					},
					close: function(event, ui){
						$(this).empty();  
						getChildsTestResults();
						
				    },
				    dialogClass: 'myTitleClass'
				});		
				$(dailogContainer).dialog("option", "title", "Performance Test Result");
				$(dailogContainer).scrollTop("0");	
				$("#loading-div-background").hide();
			}
		});
	}else{
				
		$.ajax({
			type : "GET",	
			url : "getCompletedTestQuestions.htm",
			data : "studentAssignmentId=" + studentAssignmentId + "&usedFor="+usedFor +"&assignmentTypeId="+assignmentTypeId+"&assignmentId="+assignmentId+"&tab="+ tab,
			success : function(response) {
			//smoothScroll($("#StuAssessmentQuestionsList").html(response));
				var dailogContainer = $(document.getElementById('getCompletedTestQuestions'));
				var screenWidth = $( window ).width() - 10;
				var screenHeight = $( window ).height() - 10;
				$('#getCompletedTestQuestions').empty();  
				$(dailogContainer).append(response);
				$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
					  $(".ui-dialog-titlebar-close").show();
				},
				dialogClass: 'myTitleClass'
				});	
				if(assignmentTypeId==14){
					$(dailogContainer).dialog("option", "title", "Spelling");
				}else{
				$(dailogContainer).dialog("option", "title", "Test Results");}
				$(dailogContainer).scrollTop("0");	
		
			}
		});
	}		
}
function loadGradeAndHomeworkDates(){
	var studentId=$('#studentId').val();
	var usedFor = document.getElementById("usedFor").value;
	$("#fromDate").empty(); 
	$("#fromDate").append(
			$("<option></option>").val('')
					.html('Select Date'));
	if(studentId != "select" & studentId != ""){
		$.ajax({
			type : "GET",	
			url : "getStudentGradeAndHomeworkDates.htm",
			data : "studentId=" + studentId + "&usedFor="+usedFor,
			success : function(response) {
				var homeworkDates = response.homeworkDates;
				var grade=response.grade.masterGrades.gradeName;
				document.getElementById("studentGrade").value=grade;
				$.each(homeworkDates, function(index, value) {
					$("#fromDate").append(
							$("<option></option>").val(getDBFormattedDate(value.assignment.dateAssigned)).html(
									getFormattedDate(value.assignment.dateAssigned)));
				    });
				
				
			}
		});
	}else{
		$('#ChildsReport').html("");
		document.getElementById("studentGrade").value="";
		document.getElementById("checksAssigned").checked = false;
		return false;
	}
}

function showReport(){
	var usedFor = $('#usedFor').val();
	var studentId = $('#studentId').val();
	var assignedDate= document.getElementById("fromDate").value;
	$("#ChildsReport").empty();
	$("#StuHomeQuestionsList").empty();
	var check = document.getElementById("checksAssigned").checked;	
	if(check){
		if(studentId > 0 && assignedDate != ''){
			$.ajax({
				url : "getChildHomeworkReportsByDate.htm",
				data: "studentId="+studentId + "&usedFor="+usedFor+"&assignedDate="+assignedDate,
				success : function(data) {	
					$('#ChildsReport').html(data);
				}
			});
		}else{
			alert("Please fill all the filters");
			document.getElementById("checksAssigned").checked = false;
			return false;
		}
	}	
}

function getChildTestQuestions(assignmentId,studentAssignmentId,assignmentTypeId,status,testCount){
	if(status == "pending"){
		alert("This task not yet submitted");
		return;
	}
	var usedFor=$('#usedFor').val();
	$("#StuHomeQuestionsList").empty();
	var tab=$('#tab').val();
	
		$.ajax({
			type : "GET",
			url : "getStudentHomeworkQuestions.htm",
			data : "studentAssignmentId=" + studentAssignmentId + "&usedFor="+usedFor +"&assignmentTypeId="+assignmentTypeId+"&assignmentId="+assignmentId, 
			//+ "&tab="+ tab,
			success : function(response) {

				smoothScroll($("#StuHomeQuestionsList").html(response));
				
			}
		});
			
}



function loadGradeClasess() {
	
	var studentId = $('#studentId').val();
	document.getElementById("gradeName").value="";
	$("#csId").empty();
	$("#csId").append(
			$("<option></option>").val('').html('Select Subject'));
	if(studentId != "" && studentId != "select"){
		$.ajax({
			type : "GET",
			url : "loadGradeClasess.htm",
			data : "studentId="+ studentId,
			success : function(response) {
				document.getElementById("gradeName").value=response.grade.masterGrades.gradeName;				
				var clasess = response.classMap;	
				$.each(clasess,
					function(index, value) {
						$("#csId").append(
							$("<option></option>").val(index).html(value));
				});
			}
		});
	}
}
function getChildRTIResults(){
	var studentId = $('#studentId').val();
	var classId=$('#csId').val();
	var usedFor=$('#usedFor').val();
	$("#StudentResults").html("")
	if(classId != ""){
		$.ajax({
			type : "GET",
			url : "getChildRtiResults.htm",
			data : "studentId="+ studentId+"&classId="+classId+"&usedFor="+usedFor,
			success : function(response) {
				smoothScroll($("#StudentResults").html(response));
			}
		});
	}else{
		return false;
	}
}

function getChildIOLReportDates(){
	
	var studentId = $('#studentId').val();
	var tab=$('#tab').val();
	var reportsContainer = $(document.getElementById('reportDatesDiv'));
	$(reportsContainer).empty(); 
    if(studentId>0){
    	$("#loading-div-background1").show();
	$.ajax({
			type : "GET",
			url : "getChildIOLReportDates.htm",
			data : "&studentId="+studentId+"&tab="+tab,
			success : function(response) {
				$("#loading-div-background1").hide();
				$(reportsContainer).append(response);
			}
	    });
    }
	}

function getChildClasses(thisvar){
	if(thisvar.value != 'select'){
		$.ajax({
			type : "GET",
			url : "getChildClasses.htm",
			data : "studentId=" + thisvar.value,
			success : function(childClasses) {
				$.ajax({
					type : "GET",
					url : "getChildHomeworks.htm",
					data : "studentId=" + thisvar.value,
					success : function(childHomeworks) {
						$.ajax({
							type : "GET",
							url : "getChildAssessments.htm",
							data : "studentId=" + thisvar.value,
							success : function(childAssessments) {
								$("#classesDiv").html("");
								$("#homeworksDiv").html("");
								$("#assessmentsDiv").html("");
								$("#classesDiv").html(childClasses);
								$("#homeworksDiv").html(childHomeworks);
								$("#assessmentsDiv").html(childAssessments);
							}
						});
					}
				});
			}
		});
	}else{
		$("#classesDiv").html("");
		$("#homeworksDiv").html("");
		$("#assessmentsDiv").html("");
		return false;
	}
}
function getChildCompositeChart(thisvar){
	var studentId = $('#child').val();
	var width = $(window).width();;
	var height = $(window).height();;
	$.ajax({
		type : "GET",
		url : "getStudentCompositeChart.htm",
		data : "csId=" + thisvar + "&studentId="+studentId,
		success : function(response) {
			var dailogContainer = $(document.getElementById('compositeChart'));
			$('#compositeChart').empty();  
			$(dailogContainer).append(response);
			$(dailogContainer).dialog({width: width-20, height: height,open:function () {
				  $(".ui-dialog-titlebar-close").show();
			}
		});					
			$(dailogContainer).scrollTop("0");
		}
	});
}

function loadReportDates(){
	var csId = $('#csId').val();
	var studentId = $('#studentId').val();
	var previousContainer = $(document.getElementById('previousDiv'));
	$(previousContainer).empty(); 
	$("#dateId").empty();
	$("#dateId").append(
			$("<option></option>").val('').html('Select Date'));
	if(csId == ""){
		alert("Please select Class");
	}
	if(csId && csId != "select"){
		$.ajax({
			type : "GET",
			url : "getReportDates.htm",
			data : "csId="+ csId+"&studentId="+studentId,
			success : function(response) {
				if(response.size == 0){
					alert("No Reports for this Class");
				}else{
					var reports = response.reports;				
					$.each(reports,
						function(index, value) {
							var d = new Date(value.releaseDate);
							var dateFormat = d.getMonth()+1+"/"+d.getDate()+"/"+d.getFullYear();
							$("#dateId").append(
								$("<option></option>").val(value.reportId).html(dateFormat));
					});				
				}
				
			}
		});
	}
}

function showPrevious(){
	var reportId = $('#dateId').val();
	var previousContainer = $(document.getElementById('previousDiv'));
	$(previousContainer).empty();  
	if(reportId > 0){
		$.ajax({
			type : "GET",
			url : "getPreviousReports.htm",
			data : "reportId="+ reportId,
			success : function(response) {
				$(previousContainer).append(response);
			}
		});
	}
}