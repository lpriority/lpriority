

function getStudentsTestResults(){
	var classId = dwr.util.getValue('classId');
	var gradeName = dwr.util.getValue('gradeName');
	var usedFor = document.getElementById("usedFor").value;
	var studentId = 0;
	if(document.getElementById("studentId") != null){
		studentId = document.getElementById("studentId").value;
	}
	if(classId != 'select' && classId!=""){
		$("#loading-div-background").show();
		$.ajax({
			url : "studentTestGraded.htm",
			data: "classId="+classId+"&usedFor="+usedFor+"&gradeName="+gradeName+"&studentId="+studentId,
			success : function(data) {
				$('#studentsTestResultsDiv').html(data);
				$("#loading-div-background").hide();
			}
		}); 	
	}else{
		alert("Please select all filters");
		return false;
	}
}
var contentArray =  new Array();
var groupIdArray = new Array();
var titleArray = new Array();
var marksArray = new Array();
var phonicContentMap = new Map();
var studentCommentsMap  = new Map();
function getStudentsTestResultsDetails(id,studentAssignmentId,assignmentType,userType,regId,gradedStatus,status,studentId,assignmentId, title, studentName){
	$("#getCompletedTestQuestions").html("");	
	if(status == 'pending'){
			alert("Test not yet submitted !!");
			return false;
		}else if(status == 'submitted'){
			jQuery.curCSS = jQuery.css;
			if(assignmentType == 'Early Literacy Letter' || assignmentType == 'Early Literacy Word'){
				 $("#dialog"+id).dialog({
						overflow: 'auto',
						dialogClass: 'no-close',
					    autoOpen: false,
						position: {my: "center", at: "center", of:window ,within: $("body") },
					    title: 'Early Literacy Student Test Result',
					    draggable: true,
					    width : 1200,
					    resizable : true,
					    modal : true,
					    close: function (ev, ui) {  }
				});
				 if(assignmentType == 'Early Literacy Letter'){
					 $("#dialog"+id).dialog({ height: 350});
				 }else if(assignmentType == 'Early Literacy Word'){
					 $("#dialog"+id).dialog({ height: 550}); 
				 }
				 var getEarlyLiteracyGradeCallBack = function(list){
					 var setType = '';
					 var setsArray = new Array();
					 var setNameArray = new Array();
					 var setIdArray = new Array();
					 for (i=0;i<list.length;i++){
						 setsArray.push(list[i].set);
						 setNameArray.push(list[i].setName);
						 setType = list[i].setType;
						 setIdArray.push(list[i].setId);
					 }
					 var iframe = $('#iframe'+id);
					 iframe.attr('src', "gradeEarlyLiteracyTest.htm?setsArray=" +setsArray+ "&setNameArray="+setNameArray + "&setType="+setType +"&studentAssignmentId="+studentAssignmentId + "&dialogDivId=dialog"+id+"&divId="+assignmentType+"&userType="+userType+"&regId="+regId+"&gradedStatus="+gradedStatus+"&studentId="+studentId+"&assignmentId="+assignmentId+"&page=studentTestResult"+"&assignmentTitle="+title+"&studentName="+studentName + "&setIdArray=" + setIdArray);
				 }
				 
				 earlyLiteracyTestsService.getEarlyLiteracyTests(studentAssignmentId,{
					callback : getEarlyLiteracyGradeCallBack
				 });
			}else if(assignmentType == 'Phonic Skill Test'){
				 var studentName = dwr.util.getValue('studentName');
				 var screenWidth = $( window ).width()-20;
				 var screenHeight = $( window ).height()-10;
			   	 $("#dialog"+id).dialog({
			   		    overflow: 'auto',
						dialogClass: 'no-close',
					    autoOpen: false,
						position: {my: "center", at: "center", of:window ,within: $("body") },
					    title: "Phonic Skill Test Result",
					    draggable: true,
					    width : screenWidth,
					    height : screenHeight,
					    resizable : true,
					    modal : true,
					    close: function (ev, ui) { $("#dialog"+id).html(""); }
				 });
			 
				 var allPhonicGroupsCallBack = function(list) {
					 var questionsArray = new Array();
					 titleArray.length = 0;
					 phonicContentMap.length = 0;
					 len = list.length-1;
					 for (i=0;i<list.length;i++){
						 groupIdArray.push(list[i].groupId);
						 questionsArray.push(list[i].question.trim());
						 titleArray.push(list[i].title);
						 contentArray = list[i].question.trim().split(' ');
						 phonicContentMap[list[i].title] = contentArray;
					 }
					 var testDetailsDivContent = '';
				 	 var studentMarksMap  = new Map();
					 var studentSecuredMarksArray = new Array();
 				     var studentPhonicTestMarksCallBack = function (studentPhonicTestMarksLt){
					 		testDetailsDivContent += "<table width='90%' align='center'  border='0' cellspacing='0' cellpadding='0'><tr><td height=15 colSpan=30></td></tr><tr><td width='262' align='left'><font color='black' size='2'><b><u>Group Name</u></b></font></td><td width='462' align='left'><font color='black' size='2'><b><u>Test Content</u></b></font></td>";
					 		testDetailsDivContent += "<td width='120' align='center'><font color='black' size='2'><b><u>Marks</u></b></font></td></tr><tr><td height=15 colSpan=30></td></tr><tr><td height=3 colSpan=30></td></tr>";
							for (var j = 0; j < studentPhonicTestMarksLt.length; j++) {
								var title = studentPhonicTestMarksLt[j].phonicGroups.title;
								var secmarks = studentPhonicTestMarksLt[j].secMarks;
								studentSecuredMarksArray.push(secmarks);
								var comments = studentPhonicTestMarksLt[j].comments;
								if(comments){
									if(comments.indexOf(':') > -1){
										studentCommentsMap[title] = comments;
									}
								}
								var marksString = studentPhonicTestMarksLt[j].marksString;
								if(marksString != null && marksString){
									if(marksString.indexOf(' ') > -1){
										marksArray = marksString.split(' ');
										studentMarksMap[title] = marksString;
									}
								}
							 }

							if(Object.keys(studentMarksMap).length > 0){
							      for (var i = 0; i < titleArray.length; i++) {
							    	  if(studentMarksMap[titleArray[i]]){
										   var content = phonicContentMap[titleArray[i]];
										   var marks = studentMarksMap[titleArray[i]].split(' ');
										   var contentStr = '';
											  for (var j = 0; j < content.length; j++) {
												  if(marks[j] > 0){
													   contentStr +="<span style='color:blue'>"+content[j]+"&nbsp;&nbsp;</span>";
												   }else{
													   contentStr +="<span style='color:red' id="+i+''+j+" class='simptip-position-top simptip-movable half-arrow simptip-multiline simptip-info'  onMouseOver='showComments(\""+titleArray[i]+"\",\""+i+"\",\""+j+"\")'>"+content[j]+"&nbsp;&nbsp;</span>";
												   }
											   }
											   testDetailsDivContent += "<tr><td width='262' align='left'><font color='663399' size='2'><b>"+titleArray[i]+"</b></font></td><td width='562' align='left'><font size='3'><b>"+contentStr+"</b></font></td>";
										   	   testDetailsDivContent +=	"<td width='120' align='center'><font color='black' size='3'><b>"+studentSecuredMarksArray[i]+"/"+content.length+"</b></font></td></tr><tr><td height=5 colSpan=30></td></tr><tr><td height=10 colSpan=30></td></tr>";
							    	    }
							      }
					          }
							   testDetailsDivContent += "<tr><td height=15 colSpan=30></td></table>";
							   $("#dialog"+id).html(testDetailsDivContent); 
					 	   }
				      	   phonicTestReportsService.getStudentPhonicTestMarksByStudentAssignmentId(studentAssignmentId,{
								callback : studentPhonicTestMarksCallBack
								
							});
				 }
				 assignPhonicSkillTestService.getAssignedStudentPhonicGroups(studentAssignmentId,{
						callback : allPhonicGroupsCallBack
				 });
			 }
			 $("#dialog"+id).css('overflow', 'auto');
			 $("#dialog"+id).dialog("open");
		}
}

function getStudentsBenchmarkAllResults(){
	var classId = dwr.util.getValue('classId');
	var usedFor = document.getElementById("usedFor").value;
	$('#studentsBenchmarkTestResultsDiv').html("");
	$('#StuBenchmarkQuestionsList').html("");
	if(classId != 'select'){
		$("#loading-div-background").show();
		$.ajax({
			url : "getStudentBenchmarkResults.htm",
			data: "classId="+classId +"&usedFor="+usedFor,
			success : function(data) {
				$('#studentsBenchmarkTestResultsDiv').html(data);
				$("#loading-div-background").hide();
			}
		}); 	
	}else{
		return false;
	}
}
function getStudentsBenchmarkTestResults(){
	var classId = dwr.util.getValue('classId');
	var usedFor = document.getElementById("usedFor").value;
	var assignmentTypeId =  document.getElementById("assignmentTypeId").value;
	$('#studentsBenchmarkTestResultsDiv').html("");
	$('#StuBenchmarkQuestionsList').html("");
	if(classId != 'select'){
		$("#loading-div-background").show();
		$.ajax({
			url : "getStudentBenchmarkResults.htm",
			data: "classId="+classId + "&usedFor="+usedFor+"&assignmentTypeId="+assignmentTypeId,
			success : function(data) {
				$('#studentsBenchmarkTestResultsDiv').html(data);
				$("#loading-div-background").hide();
			}
		}); 	
	}else{
		return false;
	}
}

//function getStudentBenchmarkQuestions(studentAssignmentId,assignmentId,assignmentTypeId,gradingTypesId){
//	$('#BenchmarkQuestionsList').html("");
//	$.ajax({
//		url : "getStudentBenchmarkQuestions.htm",
//		data: "&studentAssignmentId="+studentAssignmentId+"&assignmentId="+assignmentId+"&assignmentTypeId="+assignmentTypeId+"&gradingTypesId="+gradingTypesId,
//		success : function(data) {
//			$('#BenchmarkQuestionsList').html(data);
//		}
//	}); 	
//}
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
	$('#EvaluateHomework').html("");
	$("#StuHomeQuestionsList").empty();
	$("#loading-div-background").show();
	$.ajax({
		url : "getHomeworkReportsByDate.htm",
		data: "csId="+csId + "&usedFor="+usedFor+"&assignedDate="+assignedDate,
		success : function(data) {
			$('#EvaluateHomework').html(data);
			$("#loading-div-background").hide();
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
	$("#loading-div-background").show();
		$.ajax({
			type : "GET",
			url : "getStudentHomeworkQuestions.htm",
			data : "studentAssignmentId=" + studentAssignmentId + "&usedFor="+usedFor +"&assignmentTypeId="+assignmentTypeId+"&assignmentId="+assignmentId, 
			success : function(response) {
				var dailogContainer = $(document.getElementById('StuHomeQuestionsList'));
				var screenWidth = $( window ).width() - 10;
				var screenHeight = $( window ).height() - 10;
				$('#StuHomeQuestionsList').empty();  
				$(dailogContainer).append(response);
				$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
					  $(".ui-dialog-titlebar-close").show();
				},close : function(event, ui) {
					$(this).empty();
				},
				dialogClass: 'myTitleClass'
				});	
				if(assignmentTypeId==14){
					$(dailogContainer).dialog("option", "title", "Spelling");
				}else{
				$(dailogContainer).dialog("option", "title", "Test Results");}
				$(dailogContainer).scrollTop("0");	
				$("#loading-div-background").hide();
				
			}
		});
}
function showComments(title,i,j){
	var comments = studentCommentsMap[title];
	var commentsArray = comments.split(':');
	 $("#"+i+''+j).attr("data-tooltip",commentsArray[j]);
}
var coun=0;
var clicks = 0;
var errorsAddress = new Array();
var errorsArray = new Array();
var errosComments=new Array();
var wordsReadByStudent;
var view = false;
//function openChildWindow(assignmentQuestionId,studentAssignmentId,benchmarkId,readingTypesId){
//	 var classId = dwr.util.getValue('classId'); 
//	// var csId = document.getElementById("csId").value;
//	 var marks = 0;
//	
//		  marks = document.getElementById("marks:"+readingTypesId).value;
//		  
//		  if(marks=="")
//			  marks=0;
//		  
//		 if(marks > 0){
//			  
//			  
//				    view = true;
//				     $.ajax({
//	 		       		type : "GET",
//	 		       		url : "getStudentsBenchmarkTest.htm",
//	 		       		data : "assignmentQuestionId="+assignmentQuestionId+"&studentAssignmentId="+studentAssignmentId+"&benchmarkId="+benchmarkId+"&readingTypesId="+readingTypesId+"&marks="+marks+"&regrade=no"+"&butt=no&classId="+classId,
//	 		       		success : function(response) {
//	 		       			var dailogContainer = $(document.getElementById('gradeBenchmark'));
//	 		       			var screenWidth = $( window ).width() - 10;
//	 		       			var screenHeight = $( window ).height() - 10;
//	 		       			$('#gradeBenchmark').empty();  
//	 		       			$(dailogContainer).append(response);
//	 		       			$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true,
//	 		       			 open: function(event, ui) {
//	 		       				  $(".ui-dialog-titlebar-close").show();
//	 		       			},
//	 		       		   close: function(event, ui) {
//	 		       			$("#gradeBenchmark").empty();  
//	 		       		    $("#ui-datepicker-div").remove();
//			 		       		errorsArray.length = 0;
//								errorsAddress.length = 0;
//	 		       			} 
//	 		       			});		
//	 		       			$(dailogContainer).dialog("option", "title", "Benchmark Test");
//	 		       			$(dailogContainer).scrollTop("0");
//		 		       		$( "#dueDate").datepicker({
//		 		       		  	changeMonth: true,
//		 		       		      changeYear: true,
//		 		       		      showAnim : 'clip',
//		 		       		      minDate : 0
//		 		       		 });
//		 		       	document.getElementById("errorTable").style.visibility = "visible";
//	 		       		}
//	 		       	});
//			   }
//	     
//	    	 
//	    }
function getBenchmarkTests(gradingTypesId,assignmentTypeId){
	var classId = dwr.util.getValue('classId');
	var usedFor = document.getElementById("usedFor").value;
	$('#BenchmarkTestResultsDiv').html("");
	$('#BenchmarkQuestionsList').html("");
	if(classId != 'select'){
		$("#loading-div-background").show();
		$.ajax({
			url : "getStudentBenchmarkTests.htm",
			data: "classId="+classId + "&usedFor="+usedFor+"&gradingTypesId="+gradingTypesId+"&assignmentTypeId="+assignmentTypeId,
			success : function(data) {
				$('#BenchmarkTestResultsDiv').html(data);
				$("#loading-div-background").hide();
			}
		}); 	
	}else{
		return false;
	}
}

function getFluencyReadingQuestions(assignmentId,studentAssignmentId,assignmentTypeId){
	$('#StuBenchmarkQuestionsList').html("");
	if(assignmentTypeId == 8){
		$("#loading-div-background").show();
		$.ajax({
				url : "getStudentBenchmarkQuestions.htm",
				data: "studentAssignmentId="+studentAssignmentId + "&assignmentId="+assignmentId+"&assignmentTypeId="+assignmentTypeId,
				success : function(data) {
					$('#StuBenchmarkQuestionsList').html(data);
					$("#loading-div-background").hide();
				}
			}); 	
		}
		else{
			$("#loading-div-background").show();
			$.ajax({
				url : "getStudentBenchmarkQuestions.htm",
				data: "studentAssignmentId="+studentAssignmentId + "&assignmentId="+assignmentId+"&assignmentTypeId="+assignmentTypeId,
				success : function(data) {
					$('#StuBenchmarkQuestionsList').html(data);
					$("#loading-div-background").hide();
				}
			}); 	
		}
}
function getStudentBenchmarkQuestions(assignmentId,studentAssignmentId,assignmentTypeId,gradingTypesId){
	$('#BenchmarkQuestionsList').html("");
	$("#loading-div-background").show();
	$.ajax({
		url : "getBenchmarkQuestsByGradingTypesId.htm",
		data: "studentAssignmentId="+studentAssignmentId + "&assignmentId="+assignmentId+"&assignmentTypeId="+assignmentTypeId+"&gradingTypesId="+gradingTypesId,
		success : function(data) {
			$('#BenchmarkQuestionsList').html(data);
			$("#loading-div-background").hide();
		}
	}); 	
}

function getBenchmarkTest(readingTypesId,response){			
	
   	var dailogContainer = $(document.getElementById('gradeBenchmark'));
	var screenWidth = $( window ).width() - 10;
	var screenHeight = $( window ).height() - 10;
	$(dailogContainer).append(response);
	$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true,
   open: function(event, ui) {
		  $(".ui-dialog-titlebar-close").show();
	},
   close: function(event, ui) {
	     
	errorsArray.length = 0;
    errorsAddress.length = 0;
	$("#gradeBenchmark").empty();  
    $("#ui-datepicker-div").remove();
    // $.contextMenu( 'destroy' );
     $(dailogContainer).dialog('destroy');	
    
	},
	dialogClass: 'myTitleClass'
	});	
	if(readingTypesId==1)
	$(dailogContainer).dialog("option", "title", "Accuracy Assessment");
  	else
	$(dailogContainer).dialog("option", "title", "Fluency Test");	
   
	
	$(dailogContainer).scrollTop("0");
	$("#dueDate").datepicker({
	   changeMonth: true,
       changeYear: true,
       showAnim : 'clip',
       minDate : 0
	 });

}
function openStudentWindow(assignmentQuestionId,studentAssignmentId,benchmarkId,readingTypesId,gradingTypesId,langId){
	  var classId = document.getElementById("classId").value;
	  var marks = 0;
	
		  marks = document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value;
		  
		  if(marks=="")
			  marks=-1;
		 		  
		 if(marks >=0){
			 if(confirm("Would you like to regrade this test?",function(status){
				if(status){
					$("#loading-div-background").show();
				   errorsAddress.length= 0;
				  // errorsArray.length= 0;
				   view = false;
			    	$.ajax({
	 		       		type : "GET",
	 		       		url : "getStudentsBenchmarkTest.htm",
	 		       		data : "assignmentQuestionId="+assignmentQuestionId+"&studentAssignmentId="+studentAssignmentId+"&benchmarkId="+benchmarkId +"&readingTypesId="+readingTypesId+"&marks="+marks+"&regrade=yes"+"&butt=yes&classId="+classId+"&gradingTypesId="+gradingTypesId+"&langId="+langId,
	 		       		success : function(response) {
	 		       		$("#loading-div-background").hide();
		 		       			document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value = "";
		 		       			if(readingTypesId==2){
		 		       			  document.getElementById("accuracyMarks:"+assignmentQuestionId+":"+readingTypesId).value="";
		 		       		     /* document.getElementById("correctwords").value = "";
		 		       		      document.getElementById("errors").value = "";
		 		       		      document.getElementById("wordsread").value = ""; 
		 		       	          document.getElementById("fluencyComment").value = ""; */
	 		       		       }
		 		       		    getBenchmarkTest(readingTypesId,response);
				 		       	document.getElementById("errorTable").style.visibility = "visible";
				 		        if(readingTypesId==3)
		 		       	        $("#retellComment").empty();
		 		       	
	 		       		}
	 		       	});
			   }else{
				   
				    view = true;
				    $("#loading-div-background").show();
				     $.ajax({
	 		       		type : "GET",
	 		       		url : "getStudentsBenchmarkTest.htm",
	 		       		data : "assignmentQuestionId="+assignmentQuestionId+"&studentAssignmentId="+studentAssignmentId+"&benchmarkId="+benchmarkId+"&readingTypesId="+readingTypesId+"&marks="+marks+"&regrade=no"+"&butt=no&classId="+classId+"&gradingTypesId="+gradingTypesId+"&langId="+langId,
	 		       		success : function(response) {
	 		       		$("#loading-div-background").hide();
	 		       	    getBenchmarkTest(readingTypesId,response);
			       	   	document.getElementById("errorTable").style.visibility = "visible";
		 		        $('#correctwords').prop('disabled', true);
		 		        $('#errors').prop('disabled', true);
		 		        $('#wordsread').prop('disabled', true);
	 		       		}
				    
	 		       	});
			   }
		  })); 
	     }else{
	    	
	    	    view = false;
	    	    $("#loading-div-background").show();
	        	$.ajax({
	       		type : "GET",
	       		url : "getStudentsBenchmarkTest.htm",
	       		data : "assignmentQuestionId="+assignmentQuestionId+"&studentAssignmentId="+studentAssignmentId+"&benchmarkId="+benchmarkId+"&readingTypesId="+readingTypesId+"&marks="+marks+"&regrade=yes"+"&butt=yes&classId="+classId+"&gradingTypesId="+gradingTypesId+"&langId="+langId,
	       		success : function(response) {
	       			//console.log(response);
	       			$("#loading-div-background").hide();
	       			getBenchmarkTest(readingTypesId,response);
	 		             document.getElementById("errorTable").style.visibility = "hidden";
	 		       	     if(readingTypesId==2){
	 		       	    	document.getElementById("correctwords").value = "";
		       	            document.getElementById("errors").value = "";
		       	            document.getElementById("wordsread").value = "";
	 		       	     }
		       	          if(readingTypesId==3)
	 		       	        $("#retellComment").empty();
		       	         
		       		}
	       	});
	    }
}
function getStudentRFLPTestQuestions(assignmentId,studentAssignmentId,assignmentTypeId,status,testCount,studentId){

	$("#rflpHomeQuestionsList").empty();
	if(status == "pending"){
		alert("This task not yet submitted");
		return;
	}
	var lessonId=0;
	var usedFor=$('#usedFor').val();
	var tab = $('#tab').val();
		$("#loading-div-background").show();
		$.ajax({
			type : "GET",
			url : "getStudentTestQuestions.htm",
			data : "studentAssignmentId=" + studentAssignmentId + "&usedFor="+usedFor +"&assignmentTypeId="+assignmentTypeId+"&assignmentId="+assignmentId+"&lessonId="+lessonId+"&studentId="+studentId, 
			//+ "&tab="+ tab,
			success : function(response) {
				var dailogContainer = $(document.getElementById('rflpHomeQuestionsList'));
				var screenWidth = $( window ).width() - 10;
				var screenHeight = $( window ).height() - 10;
				$('#rflpHomeQuestionsList').empty();  
				$(dailogContainer).append(response);
				$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
					  $(".ui-dialog-titlebar-close").show();
				}});	
				$(dailogContainer).dialog("option", "title", "Grade Tests");
				$(dailogContainer).scrollTop("0");		
				$("#loading-div-background").hide();
				
			}
		});
			
}
function submitGrading(studentAssignmentId,gradeTypesId,assignmentTypeId){
	var assQuesLt=document.getElementsByName("assignmentQuesList");
	var len=assQuesLt.length;
	var count=0;
	for(var i=1;i<=len;i++)
	{
	var assignQuesId=document.getElementById("assignQuesList:"+i).value;
	var fluScore=document.getElementById("marks:"+assignQuesId+":"+2).value;
    var retellScore=document.getElementById("marks:"+assignQuesId+":"+3).value;
    if((fluScore!="" && fluScore>=0) && retellScore!=""){
    count=count+1;	
    }
	}
	if(count==len){
		$.ajax({
			url : "GradeSelfAndPeerBenchmark.htm",
			data : "studentAssignmentId=" + studentAssignmentId+"&gradeTypesId="+gradeTypesId, 
			success : function(response) {
				systemMessage(response);
				$("#BenchmarkQuestionsList").html("");
				getBenchmarkTests(gradeTypesId,assignmentTypeId);
			}
		});
	}else{
		systemMessage("Please Fill all the fields");
		return false;
	}
	}
function openChildAccuracyWindow(assignmentQuestionId,studentAssignmentId,benchmarkId,readingTypesId,gradeTypesId){
	
	//var lessonId = document.getElementById("lessonId").value;
	  var classId = document.getElementById("classId").value;
	 // var studentId = document.getElementById("studentId").value;
	  var marks = 0;
	  marks = document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value;
	  if(marks=="")
		  marks=-1;
	 
	if(marks >=0){
				 
		 if(confirm("Would you like to regrade this test?",function(status){
			 
				if(status){
				   errorsAddress.length= 0;
				   view = false;
				   $("#loading-div-background").show();
			    	$.ajax({
	 		       		type : "GET",
	 		       		url : "getStudentAccuracyTest.htm",
	 		       		data : "assignmentQuestionId="+assignmentQuestionId+"&studentAssignmentId="+studentAssignmentId+"&benchmarkId="+benchmarkId +"&readingTypesId="+readingTypesId+"&marks="+marks+"&regrade=yes"+"&butt=yes&gradeTypesId="+gradeTypesId+"&classId="+classId,
	 		       		success : function(response) {
	 		       	   		$("#loading-div-background").hide();
	 		       			document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value = "";
	 		       			document.getElementById("accuracyMarks:"+assignmentQuestionId+":"+readingTypesId).value ="";
	 		       		   getBenchmarkTest(readingTypesId,response);
		 		       	   document.getElementById("errorTable").style.visibility = "hidden";
	 		       	       document.getElementById("correctwords").value = 0;
	 		       	       document.getElementById("errors").value = 0;
	 		       	       document.getElementById("wordsread").value = 0;
	 		       	       document.getElementById("fluencyComment").value = "";
	 		       	       document.getElementById("wcpm").value = "";
	 		       		}
	 		       	});
			   }else{
				  console.log(assignmentQuestionId+","+studentAssignmentId+","+benchmarkId+","+readingTypesId+","+marks+","+gradeTypesId+","+classId);
				    view = true;
				    $("#loading-div-background").show();
				     $.ajax({
	 		       		type : "GET",
	 		       		url : "getStudentAccuracyTest.htm",
	 		       		data : "assignmentQuestionId="+assignmentQuestionId+"&studentAssignmentId="+studentAssignmentId+"&benchmarkId="+benchmarkId+"&readingTypesId="+readingTypesId+"&marks="+marks+"&regrade=no"+"&butt=no&gradeTypesId="+gradeTypesId+"&classId="+classId,
	 		       		success : function(response) {
	 		       		 $("#loading-div-background").hide();
	 		       		getBenchmarkTest(readingTypesId,response);
		 		       	document.getElementById("errorTable").style.visibility = "visible";
		 		        $('#correctwords').prop('disabled', true);
		 		        $('#errors').prop('disabled', true);
		 		        $('#wordsread').prop('disabled', true);
	 		       		}
	 		       	});
			   }
		   }));
	     }else{
	    	 
	    	    view = false;
	    	    $("#loading-div-background").show();
	        	$.ajax({
	       		type : "GET",
	       		url : "getStudentAccuracyTest.htm",
	       		data : "assignmentQuestionId="+assignmentQuestionId+"&studentAssignmentId="+studentAssignmentId+"&benchmarkId="+benchmarkId+"&readingTypesId="+readingTypesId+"&marks="+marks+"&regrade=yes"+"&butt=yes&gradeTypesId="+gradeTypesId+"&classId="+classId,
	       		success : function(response) {
	       			
	       			$("#loading-div-background").hide();
	       		    getBenchmarkTest(readingTypesId,response);
	 		       	  document.getElementById("errorTable").style.visibility = "hidden";
	 		         
 		       		}
	       	});
	    }
		 
		  
}
function getBenchmarkTest(readingTypesId,response){			
	
   	var dailogContainer = $(document.getElementById('gradeBenchmark'));
	var screenWidth = $( window ).width() - 10;
	var screenHeight = $( window ).height() - 10;
	$(dailogContainer).append(response);
	$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true,
   open: function(event, ui) {
		  $(".ui-dialog-titlebar-close").show();
	},
   close: function(event, ui) {
	errorsArray.length = 0;
errorsAddress.length = 0;
	$("#gradeBenchmark").empty();  
    $("#ui-datepicker-div").remove();
    // $.contextMenu( 'destroy' );
     $(dailogContainer).dialog('destroy');	
    
	},
	dialogClass: 'myTitleClass'
	});	
	if(readingTypesId==1)
	$(dailogContainer).dialog("option", "title", "Accuracy Assessment");
  	else
	$(dailogContainer).dialog("option", "title", "Fluency Test");	
   
	
	$(dailogContainer).scrollTop("0");
	$("#dueDate").datepicker({
	   changeMonth: true,
       changeYear: true,
       showAnim : 'clip',
       minDate : 0
	 });

}
function gradeSelfandPeerAccuracyTest(studentAssignmentId,readingTypesId,gradeTypesId){
	
	if(confirm("Do you want to submit?",function(status){
		if(status){
	var addedWordStr="";
	var assignmentQuestionId=$("#questions").val();
	var wordsRead=$("#wordsread").val();
	var wcpm=$("#wcpm").val();
	if(wordsRead == 0){
		alert("Please Select Read Content");
    	return false;
    } 
	if(wcpm == 0){
		alert("Please Play Audio to get WCPM score");
    	return false;
    } 
	var errors=$("#errors").val();
	var correctWords=$("#correctwords").val();
	var errorIdsStr="";
	var errorsStr="";
	var assignmentTitle = $("#assignmentTitle").val();
	var csId = $("#csId").val();
	var studentId = $("#studentId").val();
	var dueDate=""; 
	if(gradeTypesId==2){
	dueDate= $("#dueDate").val();
	//var assignmentTitle = $("#assignmentTitle").val();
	if(errorsAddress.length>0 && !dueDate){
    	alert("Please Select Due Date");
    	return false;
    } 
	}
	 for(var i=0;i<errorsAddress.length;i++){
	    	if(errorIdsStr == ""){
	    		errorIdsStr = errorsAddress[i]+ ":";
	    	}else{
	    		if(errorsAddress[i]){
		    		if(i == errorsAddress.length-1)
		    			errorIdsStr = errorIdsStr+errorsAddress[i];
		    		else
		    			errorIdsStr = errorIdsStr+errorsAddress[i]+ ":" ;
	    		}
	    	}
	    }
	 for(var i=0;i<errorsArray.length;i++){
	    	if(errorsStr == ""){
	    		errorsArray[i].replace("'","\\\\'");
	    		errorsStr = errorsArray[i]+ ":";
	    	}else{
	    		if(errorsArray[i]){
		    		if(i == errorsArray.length-1)
		    			errorsStr = errorsStr+errorsArray[i];
		    		else
		    			errorsStr = errorsStr+errorsArray[i]+":";
	    		}
	    	}
	    }
	 
	 for(var i=0;i<addedWordsArray.length;i++){
	    	
	    	if(addedWordStr == ""){
	    		addedWordStr = addedWordsArray[i]+ ":";
	    	}else{
	    		if(addedWordsArray[i]){
		    		if(i == addedWordsArray.length-1)
		    			addedWordStr = addedWordStr+addedWordsArray[i];
		    		else
		    			addedWordStr = addedWordStr+addedWordsArray[i]+":";
	    		}
	    	}
	    }
   if(errors==""){
       errors=0;
    }
   var errorWordsComments = $("[name=errComments]");
   var errorComments = [];
	
   var pattern = new RegExp(/[%&]/);
	
	for (var i = 0; i < errorWordsComments.length; i++) {
		var comt=errorWordsComments[i].value;
		if (!comt.replace(/\s/g, '').length) {
			alert("Please enter a comment");
		    return false;
		}
		
	  errorComments.push(errorWordsComments[i].value);
	}
	 var comment=window.document.getElementById("fluencyComment").value; 
	   if (!comment.replace(/\s/g, '').length) {
			alert("Please enter a comment");
		    return false;
		}
	   if (pattern.test(comment)) {
			 alert("Special Characters Not Allowed in Comments");
			 $("#fluencyComment").focus();
			 return false;
		}	
	   var le=comment.length;
	   if(le>500){
	  	 alert("You have exceeded the more than 500 characters ");
	  	 $("#fluencyComment").focus();
	       return false;
	   } 
	   if(correctWords<0){
		  	alert("Words Read should not less than errors");
		  	return false;
		}
	   var hwAssignmentId=$("#hwAssignmentId").val();
	    
    $.ajax({
		type : "POST",
		url : "gradeSelfandPeerAccuracyTest.htm",
		data : "assignmentQuestionId="+assignmentQuestionId+"&readingTypesId="+readingTypesId+"&wordsRead="+wordsRead+"&correctWords="+correctWords+"&errors="+errors+"&errorIdsStr="+errorIdsStr+"&errorComments="+errorComments+"&errorsStr="+errorsStr+"&gradeTypesId="+gradeTypesId+"&addedWordStr="+addedWordStr
		+"&comment="+comment+"&dueDate="+dueDate+"&assignmentTitle="+assignmentTitle+"&studentId="+studentId+"&hwAssignmentId="+hwAssignmentId+"&csId="+csId+"&wcpm="+wcpm,
		success : function(data) {
			 document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value=correctWords;
			 var per=document.getElementById("accuracyPer").value;
			 if(data.percentageAcquired!=undefined)
			 document.getElementById("accuracyMarks:"+assignmentQuestionId+":"+readingTypesId).value=data.percentageAcquired;
			 else
		     document.getElementById("accuracyMarks:"+assignmentQuestionId+":"+readingTypesId).value=per;	 
			 window.parent.$('.ui-dialog-content:visible').dialog('close');
			$("#gradeBenchmark").empty();  
			errorsAddress.length = 0;
			
		}
    });
	}else{
		return false;
	}}));
}

function saveWCPM(assignmentQuestionId,readingTypeId,gradeTypesId){
	
	if(confirm("Do you want to submit?",function(status){
		if(status){
	var addedWordStr="";
	var assignmentQuestionId=$("#questions").val();
	var wordsRead=$("#wordsread").val();
	var wcpm=$("#wcpm").val();
	if(wordsRead == 0){
		alert("Please Select Read Content");
    	return false;
    } 
	if(wcpm == 0){
		alert("Please Play Audio to get WCPM score");
    	return false;
    } 
    $.ajax({
		type : "GET",
		url : "saveWCPM.htm",
		data : "assignmentQuestionId="+assignmentQuestionId+"&wcpm="+wcpm+"&gradeTypesId="+gradeTypesId+"&readingTypesId="+readingTypeId,
		success : function(data) {	 
			 window.parent.$('.ui-dialog-content:visible').dialog('close');
			$("#gradeBenchmark").empty();  
		}
    });
	}else{
		return false;
	}}));
}
function submitAccuracySelfAndPeerGrading(studentAssignmentId,gradeTypesId,assignmentTypeId){
	var assQuesLt=document.getElementsByName("assignmentQuesList");
	var len=assQuesLt.length;
	var count=0;
	for(var i=1;i<=len;i++)
	{
	var assignQuesId=document.getElementById("assignQuesList:"+i).value;
	var accScore=document.getElementById("accuracyMarks:"+assignQuesId+":"+1).value;
    if(accScore!="" && accScore>=0){
    count=count+1;	
    }
	}
	if(count==len){
		$.ajax({
			url : "GradeSelfAndPeerBenchmark.htm",
			data : "studentAssignmentId=" + studentAssignmentId+"&gradeTypesId="+gradeTypesId, 
			success : function(response) {
				systemMessage(response);
				$("#BenchmarkQuestionsList").html("");
				getBenchmarkTests(gradeTypesId,assignmentTypeId);
			}
		});
	}else{
		systemMessage("Please Fill all the fields");
		return false;
	}
	}

