

function getPstStudentDetails(){
	var csId = dwr.util.getValue('csId');
	var bpstTypeId = dwr.util.getValue('bpstTypeId');
	var langId = dwr.util.getValue('langId');
	var studentsBySectionCallBack = function(list){
		$('#studentDetailsDiv').html('');
		var studentDetailsDivContent = "<table class='des' border=0 width='28%'><tr><td><div class='Divheads'><table width='100%' visibility='false' align='center'><tr>";
		studentDetailsDivContent +=	"<td width='562' align='center'>Student Name</td></tr></table></div><div class='DivContents'><table>";
		for (var i = 0; i < list.length; i++) {
			studentDetailsDivContent +="<tr><td id='student"+i+"' align='left' style='padding-left:10em;font-size:14px;'><input type='hidden' id='studentId"+i+"' name='studentId"+i+"' value='"+list[i].student.studentId+"'/>"+
			"<font color='black' ><a href='#' style='color:black;' onclick='getPhonicTestReportChart(\""+i+"\")'> "+list[i].student.userRegistration.firstName+' '+list[i].student.userRegistration.lastName+"</a></font></td><td height='25' colspan='2' align='center' valign='middle'>"+
            "<div class='status-message' id='resultDiv"+i+"'></div></td></tr>" +
			"<tr><td><div id='dialog"+i+"' style=\"background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;display:none;\"></div></td></tr>";
		}
		studentDetailsDivContent += "</table></div></td></tr></table>";
		$('#studentDetailsDiv').append(studentDetailsDivContent);
		$("#loading-div-background").hide();
		 setFooterHeight();
	}
	if(langId == 2 && bpstTypeId == 'select'){
		$('#studentDetailsDiv').html('');	
		return false;
	}else{
		if(csId != 'select'){
			 $("#loading-div-background").show();
			phonicTestReportsService.getStudentsByCsId(csId,{
				callback : studentsBySectionCallBack
			});
		}else{
			$('#studentDetailsDiv').html('');	
		}
	}
}

var phonicContentMap = new Map();
var contentArray =  new Array();
var commentsArray =  new Array();
var groupIdArray = new Array();
var titleArray = [];
var marksArray = new Array();
var studentsMarksMap  = new Map();
var finalMarksArray = new Array();
var graphdata=[];
var colrs = new Array("#00b0ff","#ff9800","#1c286d", "#00bfff","#4b0082","#00FF00","#8000ff","#ff1cff");
var type = "bar";
function getPhonicTestReportChart(id){
	groupIdArray.length = 0;
	titleArray.length = 0;
	graphdata=[];
	var current = 0;
	var studentRegId = 0;
	var userType = '';
	var gradedStatus = '';
	var isSameGroup = true;
	$("#chartdiv").html('');
	$("#testDataDiv").html('');
	$("#testDetailsDiv").html('');
	 var testDataDivContent = "<table width='100%' border='0' cellspacing='0' cellpadding='0'><tr><td align='center' width='100%'><font color='black' size='3'><b><u>Student Test Details</u></b></font></td></tr>" +
	 		"<tr><td height=1 colSpan=30 align='left' style='font-size: 14px;font-weight: bold;color:#152a9e;'><input type='radio' class='radio-design' id='bar' name='chartType' value='bar' onClick='showGraph("+id+")'> <label for='bar' class='radio-label-design'>Bar Chart </label><input type='radio' class='radio-design' id='line' name='chartType' value='line' onClick='showGraph("+id+")'><label for='line' class='radio-label-design'>Line Chart</label></td></tr>";
     var gradName = $("#gradeId :selected").text();
     var teacherName = dwr.util.getValue('teacherName');
	 var langId = dwr.util.getValue('langId');
	 var bpstTypeId = dwr.util.getValue('bpstTypeId');
	 if(bpstTypeId == 'select')
		 bpstTypeId = 0;
	 var allPhonicGroupsCallBack = function(list) {
		if(list.length > 0){ 
			 phonicContentMap.clear();
			 for (i=0;i<list.length;i++){
				 groupIdArray.push(list[i].groupId);
				 titleArray.push(list[i].title);
				 contentArray = list[i].question.trim().split(' ');
				 phonicContentMap[list[i].title] = list[i].question.trim().split(' ');
			 }
		 }
		$("#loading-div-background").hide();
	 } 
	 $("#loading-div-background").show();
	 if(langId == 1){
		 assignPhonicSkillTestService.getAllPhonicGroups(langId,{
				callback : allPhonicGroupsCallBack
		 });
	 }else if(langId == 2){
		 assignPhonicSkillTestService.getAllBpstTypePhonicGroups(bpstTypeId,{
				callback : allPhonicGroupsCallBack
		 });
	 }
	var csId = dwr.util.getValue('csId');
	var studentId = dwr.util.getValue('studentId'+id);
    var assignedDatesArray =  new Array();
    var studentFullName=''
    assignedDatesArray.length = 0;
	var studentPhonicTestMarksCallBack = function(list){
		if(list.length > 0){
			for (var i = 0; i < list.length; i++) {
		     var studentAssignmentId = list[i].studentAssignmentStatus.studentAssignmentId;
			 if(studentAssignmentId){
			     if(!studentFullName)
			    	 studentFullName = list[i].studentAssignmentStatus.student.userRegistration.firstName+' '+list[i].studentAssignmentStatus.student.userRegistration.lastName;	
			     studentId = list[i].studentAssignmentStatus.student.studentId;
			     studentRegId = list[i].studentAssignmentStatus.student.userRegistration.regId;
			     userType = list[i].studentAssignmentStatus.student.userRegistration.user.userType;
			     gradedStatus = list[i].studentAssignmentStatus.gradedStatus;
			     var dateAssigned = getFormattedDate(list[i].studentAssignmentStatus.assignment.dateAssigned);
			     var title = list[i].studentAssignmentStatus.assignment.title;
			      if(assignedDatesArray.indexOf(dateAssigned+' ('+title+')') == -1){
			    	  assignedDatesArray.push(dateAssigned+' ('+title+')');
			    	  testDataDivContent += "<tr><td style='padding-left: 30em;' width='450'><input type='hidden' id='studentName' value='"+studentFullName+"' /><input type='radio' class='radio-design' value ='"+title+"' align='left' id='radio"+i+"' name='radio' onClick='getStudentTestDetails(\""+assignedDatesArray[i]+"\",\""+gradedStatus+"\",\""+list[i].studentAssignmentStatus.studentAssignmentId+"\",\""+list[i].studentAssignmentStatus.assignment.assignmentId+"\",\""+colrs[i]+"\",\""+studentId+"\",\""+studentRegId+"\",\""+userType+"\")' />&nbsp;&nbsp;<label for='radio"+i+"' class='radio-label-design'><font color='"+colrs[i]+"' size='2'>"+assignedDatesArray[i]+"</font></label>&nbsp;&nbsp;&nbsp;</td></tr>";
			      }
				 var studentPhonicTestMarksByStudentAssignmentIdCallBack = function (studentPhonicTestMarksLt){
					 var securedMarksArray = new Array();
					 var tempArray = new Array();
					     tempArray.length = 0;
					     commentsArray.length = 0;
						 contentArray.length = 0;
						 securedMarksArray.length = 0;
					 for (var id = 0; id < studentPhonicTestMarksLt.length; id++){
						 if(bpstTypeId > 0){
							 tempArray.push(studentPhonicTestMarksLt[id].phonicGroups.groupId);
						 }else{
							 tempArray.push(studentPhonicTestMarksLt[id].phonicGroups.groupId); 
						 }
					 }
					 if (tempArray.length === 0) {
						 isSameGroup = false;
					 }
					 for (var j = 0; j < groupIdArray.length; j++) {
						 if(jQuery.inArray(groupIdArray[j], tempArray) >= 0) {
							 if(studentPhonicTestMarksLt[j]){
								 var marksString = studentPhonicTestMarksLt[j].marksString;
								 var secmarks = studentPhonicTestMarksLt[j].secMarks;
								 var title = studentPhonicTestMarksLt[j].phonicGroups.title;
							     securedMarksArray.push(secmarks);
							     if(marksString){
									if(marksString.indexOf(' ') > -1){
										marksArray = marksString.split(' ');
										studentsMarksMap[title] = marksString;
									}
								 }
							 }
							}else{
								securedMarksArray.push(0);
								if(studentPhonicTestMarksLt[j]){
									var marksString = studentPhonicTestMarksLt[j].marksString;
									var secmarks = studentPhonicTestMarksLt[j].secMarks;
									var title = studentPhonicTestMarksLt[j].phonicGroups.title;
									securedMarksArray.push(secmarks);
								     if(marksString){
											if(marksString.indexOf(' ') > -1){
												marksArray = marksString.split(' ');
												studentsMarksMap[title] = marksString;
											}
									 }
								}
							} 

					 }
					 if(list.length > current){
						 if(type == 'bar'){
							 graphdata.push({
					             data: securedMarksArray,
					             name: assignedDatesArray[current],
					             color: colrs[current]
					         })
						 }else if(type == 'line'){
							 graphdata.push({
								 type: type,
					             data: securedMarksArray,
					             name: assignedDatesArray[current],
					             color: colrs[current]
					         }) 
						 }
					  }
					 var text="\tStudent Name: "+studentFullName+"\n\t Grade :"+gradName+"\n\tTeacher Name: "+teacherName;
					      createChart(text);
					      current = current+1;
				 }
			    phonicTestReportsService.getStudentPhonicTestMarksByStudentAssignmentId(studentAssignmentId,{
			    	async: false,
					callback : studentPhonicTestMarksByStudentAssignmentIdCallBack
				});
			}
			}
			if(isSameGroup == true){
				 var screenWidth = $( window ).width();
				 var screenHeight = $( window ).height() -10;
				jQuery.curCSS = jQuery.css;
				$("#dialog"+id).dialog({
						overflow: 'auto',
						dialogClass: 'no-close',
					    autoOpen: false,
						position: {my: "center", at: "center", of:window ,within: $("body") },
					    title: 'Phonic Skill Multiple Test Report',
					    draggable: true,
					    width : screenWidth,
					    height : screenHeight,
					    resizable : true,
					    modal : true,
					    open: function( event, ui ) {showGraph(id);},
					    close: function (ev, ui) { $("#dialog"+id).html(""); } 
					});
				
				var dialogDivContent = "<table width='100%'> <tr><td><div id='chartdiv' style='display:none;width: 100%;background-color: #FFFFFF;' ></div></td></tr>" +
				"<tr><td><div id='testDataDiv' style='display:none;width: 100%;' ></div></td></tr><tr><td><div id='testDetailsDiv' style='display:'';width: 100%;background-color: #FFFFFF;' ></div></td></tr></table>";
				$("#dialog"+id).append(dialogDivContent);
				$("#chartdiv").show();
				testDataDivContent += "<tr><td height=15 colSpan=30></td></table>";
		    	$('#testDataDiv').append(testDataDivContent);
		    	$('#testDataDiv').show();
		    	$("#dialog"+id).dialog("open");
		    	$("input[name=chartType][value=" + type + "]").prop('checked', true);
			}else{
				 systemMessage('No data found');
			}
		}else{
			 systemMessage('No data found');
		}
		}
		if(csId != 'select' && bpstTypeId != 'select' && studentId){
			phonicTestReportsService.getStudentPhonicTestMarks(studentId,csId,langId,bpstTypeId,{
				callback : studentPhonicTestMarksCallBack
			});
		}
}

function showGraph(id){
	var graphType = $('input[name=chartType]:checked').val();
	if(graphType == 'line')
		type = graphType;
	else if(graphType == 'bar')
		type = graphType;
	getPhonicTestReportChart(id);
}

function createChart(text){
  $("#chartdiv").kendoChart({
	    title: {
	          text: text
	     },
	    legend: {
	          position: "bottom"
	    },
	    series: graphdata,
	    valueAxes: [{
            name: "temp",
            min: 0,
            max: 15
        }],
	    categoryAxis:{
	        categories: titleArray,
            justified: true
	    },
	    tooltip: {
            visible: true,
            format: "{0}",
            template: "#= category #: #= value #"
        }
     });
}
var wrongContentArray = new Array();
function getStudentTestDetails(dateAssigned, gradedStatus, studentAssignmentId, assignmentId, color, studentId, regId, userType){
	 $("#testDetailsDiv").html('');
	 wrongContentArray.length = 0;
	 var studentMarksMap  = new Map();
	 var studentSecuredMarksArray = new Array();
	 	var studentPhonicTestMarksCallBack = function (studentPhonicTestMarksLt){
	 		titleArray.length = 0;
			for (var j = 0; j < studentPhonicTestMarksLt.length; j++) {
				var marksString = studentPhonicTestMarksLt[j].marksString;
				var title = studentPhonicTestMarksLt[j].phonicGroups.title;
				var secmarks = studentPhonicTestMarksLt[j].secMarks;
				studentSecuredMarksArray.push(secmarks);
				titleArray.push(title);
				if(marksString){
					if(marksString.indexOf(' ') > -1){
						marksArray = marksString.split(' ');
						studentMarksMap[title] = marksString;
					}
				}
			 }
			 var testDetailsDivContent = "<table width='90%' align='center'  border='0' cellspacing='0' cellpadding='0'><tr><td colspan='3' align='left'><font color='"+color+"' size='3'><b>"+dateAssigned+" :</font></td>"+
			  "<td width='262' align='left'><font color='black' size='2'></td><td width='462'height='30' align='center'><iframe id='ifmcontentstoprint' style='height: 0px; width: 0px; position: absolute;'></iframe><div onclick='printDiv(\"multiple\")' style='cursor: pointer; cursor: hand;font-weight:bold;text-shadow:0 2px 2px rgb(0, 0, 0);'><span style='font-size:45px;color:#1A7BC9;' class='fi-print'></span>&nbsp;<span style='font-size:22px;color:#1A7BC9;'>Print</span></div></td></tr>"+
			  "<tr><td height=15 colSpan=30></td></tr><tr><td><table colspan='10' width='140%'> <tr><td width='80' align='center'><font color='black' size='2'><b><u>S.No</u></b></font></td><td width='262' align='left'><font color='black' size='2'><b><u>Group Name</u></b></font></td><td width='462' align='left'><font color='black' size='2'><b><u>Test Content</u></b></font></td>";
			  if(gradedStatus == 'graded' || gradedStatus == 'live graded'){
				  testDetailsDivContent += "<td width='80' align='center'><font color='black' size='2'><b><u>Audio</u></b></font></td>";
			   }
			      testDetailsDivContent += "<td width='120' align='center'><font color='black' size='2'><b><u>Marks</u></b></font></td></tr><tr><td height=10 colSpan=30></td></tr>";
			     if(Object.keys(studentMarksMap).length > 0){
			      for (var i = 0; i < titleArray.length; i++) {
				   var content = phonicContentMap[titleArray[i]];
				   var groupId = groupIdArray[i];
				   var marks = "";
				   if(studentMarksMap[titleArray[i]]){
				       marks = studentMarksMap[titleArray[i]].split(' ');
				   }
				   
				   if(marks){ 
				   var contentStr = '';
					  for (var j = 0; j < content.length; j++) {
						  if(marks[j] > 0){
							   contentStr +="<span style='color:blue'>"+content[j]+"&nbsp;&nbsp;</span>";
						   }else{
							   contentStr +="<span style='color:red'>"+content[j]+"&nbsp;&nbsp;</span>";
							   wrongContentArray.push(content[j]);
						   }
					   }
						   testDetailsDivContent += "<tr><td width='80' align='center'><font color='663399' size='2'><b>"+(i+1)+"."+"</b></font></td><td width='262' align='left'><font color='663399' size='2'><b>"+titleArray[i]+"</b></font></td><td width='462' align='left'><font size='3'><b>"+contentStr+"</b></font></td>";
					   if(gradedStatus == 'graded' || gradedStatus == 'live graded'){
						   testDetailsDivContent +=	"<td width='80' align='center'><input id=image"+groupId+" type=image src='images/Teacher/audioOver.gif' onclick='playPhonicTestAudio(\""+groupId+"\",\""+assignmentId+"\",\""+studentId+"\",\""+regId+"\",\""+userType+"\")' value=''></td>";
					   }
					   	   testDetailsDivContent +=	"<td width='120' align='center'><font color='black' size='3'><b>"+studentSecuredMarksArray[i]+"/"+content.length+"</b></font></td></tr>";
				   }
				  
			      }
	          }
			   testDetailsDivContent += "<tr><td height=10 colSpan=30><div id='printdiv' style='display: none;'></td></tr></table></td></tr></table>";
			   $('#testDetailsDiv').append(testDetailsDivContent);
	    	   $('#testDetailsDiv').show();
	    	   smoothScroll(document.getElementById('testDetailsDiv'));
	 	   }
       	   phonicTestReportsService.getStudentPhonicTestMarksByStudentAssignmentId(studentAssignmentId,{
				callback : studentPhonicTestMarksCallBack
				
			});
}
function playPhonicTestAudio(groupId,assignmentId,studentId,regId,userType){
	 if(studentId && regId && userType && assignmentId && groupId){
			$.ajax({  
				type : "GET",
				url : "playPhonicTestAudio.htm", 
			    data: "studentId="+studentId+"&regId="+regId+"&userType="+userType+"&assignmentId="+assignmentId+"&groupId="+groupId,
			    success : function(audioFilePath) {
			     if(audioFilePath){	
			    	 document.getElementById('image'+groupId).value = "loadDirectUserFile.htm?usersFilePath="+audioFilePath;
			  		 var audio = new Audio(document.getElementById('image'+groupId).value);
					 audio.play();
			     }else{
			    	 alert("Audio File not Existed !!");
			     }
			    }
			}); 
		}
}
function printDiv(page) {
	    var teacherName = dwr.util.getValue('teacherName');
	    var studentName = dwr.util.getValue('studentName');
	    var  assignmentTitle = $('input[name=radio]:radio:checked').val();
	    var gradeName = '';
	    if(assignmentTitle){
	    	 gradeName = $("#gradeId :selected").text();
	    }else{
	    	 assignmentTitle = window.parent.document.getElementById('titleId').options[window.parent.document.getElementById('titleId').selectedIndex].text; 
			 gradeName = window.parent.document.getElementById('gradeId').options[window.parent.document.getElementById('gradeId').selectedIndex].text; 
	    }
	  
	   var printDiv = "<table width='100%' align='center' cellpadding='2' cellspacing='8' border='0'><tr><td colspan='2' height='38'></td></tr><tr><td height='246'><table width='100%' height='246' align='center' cellpadding='2' cellspacing='8' border='0'><tr style='padding-left: 1em; font-size: 18px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><td> Student Name : <b>"+studentName+"</b></td><td> Teacher Name : <b>"+teacherName+"</b></td></tr>"+
	    			  "<tr style='padding-left: 1em; font-size: 18px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><td> Grade : <b>"+gradeName+"</b></td><td> Title : <b>"+assignmentTitle+"</b></td></tr><tr><td colspan='2' height='30'></td></tr><tr><td colspan='2'><table width='100%' align='center' cellpadding='2' cellspacing='8' border='1'><tr>";    
	    var count = 1;
	    var className = '';
	    for (var i = 0; i < wrongContentArray.length; i++) {
			var contentLen = wrongContentArray[i].length;
		if(count < 2){
			if(contentLen < 4){
				printDiv += "<td width='50%' align='center' height='246' style='font-size: 140px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><font color=black><b>"+wrongContentArray[i]+"</b></font></td>";
			}else if(contentLen < 6){
				printDiv += "<td width='50%' align='center' height='246' style='font-size: 110px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><font color=black><b>"+wrongContentArray[i]+"</b></font></td>";
			}else if(contentLen < 9){
				printDiv += "<td width='50%' align='center' height='246' style='font-size: 75px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><font color=black><b>"+wrongContentArray[i]+"</b></font></td>";
			}else if(contentLen < 12){
				printDiv += "<td width='50%' align='center' height='246' style='font-size: 50px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><font color=black><b>"+wrongContentArray[i]+"</b></font></td>";
			}else if(contentLen < 15){
				printDiv += "<td width='50%' align='center' height='246' style='font-size: 45px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><font color=black><b>"+wrongContentArray[i]+"</b></font></td>";
			}else{
				printDiv += "<td width='50%' align='center' height='246' style='font-size: 30px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><font color=black><b>"+wrongContentArray[i]+"</b></font></td>";
			}
		}else{
			if(contentLen < 4){
				printDiv += "<td width='50%' align='center' height='246' style='font-size: 140px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><font color=black><b>"+wrongContentArray[i]+"</b></font></td></tr>";
			}else if(contentLen < 6){
				printDiv += "<td width='50%' align='center' height='246' style='font-size: 110px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><font color=black><b>"+wrongContentArray[i]+"</b></font></td></tr>";
			}else if(contentLen < 9){
				printDiv += "<td width='50%' align='center' height='246' style='font-size: 75px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><font color=black><b>"+wrongContentArray[i]+"</b></font></td></tr>";
			}else if(contentLen < 12){
				printDiv += "<td width='50%' align='center' height='246' style='font-size: 50px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><font color=black><b>"+wrongContentArray[i]+"</b></font></td></tr>";
			}else if(contentLen < 15){
				printDiv += "<td width='50%' align='center' height='246' style='font-size: 45px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><font color=black><b>"+wrongContentArray[i]+"</b></font></td></tr>";
			}else{
				printDiv += "<td width='50%' align='center' height='246' style='font-size: 35px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><font color=black><b>"+wrongContentArray[i]+"</b></font></td></tr>";
			}
			count = 0;
		}
		count = count+1;
	    }
		printDiv += "</table></td></tr></table>";
		$("#printdiv").html(printDiv);
		$("#printdiv").print({
		       globalStyles: false,
		       mediaPrint: false,
		       iframe: true,
		       noPrintSelector: ".avoid-this"
		  }); 
		
		/*$("#printdiv").print({
             globalStyles: false,
             mediaPrint: false,
             iframe: true,
             noPrintSelector: ".avoid-this"
        });	*/
}

function getPhonicTestReport(){
	var assignmentId = window.parent.document.getElementById( 'titleId' ).value;
	if(!assignmentId)
		assignmentId = dwr.util.getValue('titleId');
	if(assignmentId != 'select'){
		 $("#loading-div-background").show();
		 $.ajax({  
				type : "GET",
				url : "getAllStudentsAssessmentTest.htm", 
			    data: "assignmentId="+assignmentId,
			    success : function(response) {
			    	 $("#loading-div-background").hide();
				     $("#studentDetailsPage").html(response);
				     $("#contentDiv").show();
				 	 $("#downloadDiv").show();
			    }
			}); 
	}else{
	   	$('#studentDetailsPage').html('');
	   	$("#downloadDiv").hide();
		return false;
	}
}

function getMarksInDetails(id,studentAssignmentId,assignmentId,studentFullName,dateAssigned,studentId,stdRegId,userType,gradedStatus,testTitle){
	var teacherName = dwr.util.getValue('teacherName');
	var langId = dwr.util.getValue('langId');
	var bpstTypeId = dwr.util.getValue('bpstTypeId');
	var screenWidth = $( window ).width();
	var screenHeight = $( window ).height() -10;
	jQuery.curCSS = jQuery.css;
	$("#dialog"+id).dialog({
			overflow: 'auto',
			dialogClass: 'no-close',
		    autoOpen: false,
			position: {my: "center", at: "center", of:window ,within: $("body") },
		    title: 'Phonic Skill Single Test Report',
		    draggable: true,
		    width : screenWidth,
		    height : screenHeight,
		    resizable : true,
		    modal : true,
		    close: function (ev, ui) { $("#dialog"+id).html(""); } 
		});
	groupIdArray.length = 0;
	titleArray.length = 0;
	graphdata=[];
	var dateAssigned = getFormattedDate(dateAssigned)+' ('+testTitle+')';
	var current = 0;
	$("#chartdiv").html('');
	$("#testDataDiv").html('');
	$("#testDetailsDiv").html('');
	 var allPhonicGroupsCallBack = function(list) {
		 phonicContentMap.clear();
		 for (i=0;i<list.length;i++){
			 groupIdArray.push(list[i].groupId);
			 titleArray.push(list[i].title);
			 contentArray = list[i].question.trim().split(' ');
			 phonicContentMap[list[i].title] = list[i].question.trim().split(' ');
		 }
		 $("#loading-div-background").hide();
	 } 
	 $("#loading-div-background").show();
	 if(langId == 1){
		 assignPhonicSkillTestService.getAllPhonicGroups(langId,{
				callback : allPhonicGroupsCallBack
		 });
	 }else if(langId == 2){
		 bpstTypeId = dwr.util.getValue('bpstTypeId');
		 assignPhonicSkillTestService.getAllBpstTypePhonicGroups(bpstTypeId,{
				callback : allPhonicGroupsCallBack
		 });
	 }
	 var testDataDivContent = "<table width='100%' border='0' cellspacing='0' cellpadding='0'><tr><td align='center' width='100%'><font color='black' size='3'><b><u>Student Test Details</u></b></font></td></tr><tr><td height=15 colSpan=30></td></tr>";
	 var studentPhonicTestMarksByStudentAssignmentIdCallBack = function (studentPhonicTestMarksLt){
	 var securedMarksArray = new Array();
	     commentsArray.length = 0;
		 contentArray.length = 0;
		 securedMarksArray.length = 0;
		 for (var j = 0; j < studentPhonicTestMarksLt.length; j++) {
			var marksString = studentPhonicTestMarksLt[j].marksString;
			var secmarks = studentPhonicTestMarksLt[j].secMarks;
			var title = studentPhonicTestMarksLt[j].phonicGroups.title;
			securedMarksArray.push(secmarks);
			if(marksString){
				if(marksString.indexOf(' ') > -1){
					marksArray = marksString.split(' ');
					studentsMarksMap[title] = marksString;
				}
			}
		 }
		 var text="Student Name: &nbsp;"+studentFullName+"\nTeacher Name: &nbsp; "+teacherName+"\nTest Title: &nbsp;&nbsp;"+testTitle;
		 graphdata.push({
             data: securedMarksArray,
             name: dateAssigned,
             color: "#00bfff"
         })
	      createChart(text);
	      getStudentTestDetails(dateAssigned, gradedStatus,studentAssignmentId,assignmentId,"00bfff",studentId,stdRegId,userType);
	 }
    phonicTestReportsService.getStudentPhonicTestMarksByStudentAssignmentId(studentAssignmentId,{
		callback : studentPhonicTestMarksByStudentAssignmentIdCallBack
	});
	var dialogDivContent = "<table width='100%'> <tr><td><div id='chartdiv' style='display:none;width: 100%;background-color: #FFFFFF;' ></div></td></tr>" +
	"<tr><td><input type='hidden' id='studentName' name='studentName' value='"+studentFullName+"' /><div id='testDataDiv' style='display:none;width: 100%;background-color: #FFFFFF;' ></div></td></tr><tr><td><div id='testDetailsDiv' style='display:'';width: 100%;background-color: #FFFFFF;' ></div></td></tr></table>";
	$("#dialog"+id).append(dialogDivContent);
	$("#chartdiv").show();
	testDataDivContent += "<tr><td height=15 colSpan=30></td></table>";
	$('#testDataDiv').append(testDataDivContent);
	$('#testDataDiv').show();
	$("#dialog"+id).dialog("open");
}

function getBpstGroups(){
  var langId = dwr.util.getValue('langId');
  $("#bpstTypeId").empty();
	$("#bpstTypeId").append($("<option></option>").val('select').html('Select Section'));
  if(langId == 1){
	  $('#bpstDiv').hide();
	  getPstStudentDetails();
  }else if(langId == 2){
	  var getBpstTypesCallBack =  function(list){
		    $('#bpstDiv').show();
		    $("#bpstTypeId").empty();
			$("#bpstTypeId").append($("<option></option>").val('select').html('Select Section'));
			$.each(list, function(index, value) {
				$("#bpstTypeId").append($("<option></option>").val(value.bpstTypeId).html(value.bpstType));
			});
	  }
	  phonicTestReportsService.getBpstTypes({
		  callback : getBpstTypesCallBack
	  })  
  }else if(langId == 'select'){
	  $('#bpstDiv').hide();
	  $("#bpstTypeId").empty();
  }
}
