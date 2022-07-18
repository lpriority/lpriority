function getPstAssignedDates(callback){
	var csId = $('#csId').val();
    if(csId !=  'select'){
		var usedFor = $('#usedFor').val();
		var page = dwr.util.getValue('page');
		$.ajax({
			type : "GET",
			url : "getAssignedDates.htm",
			data : "csId=" + csId + "&usedFor=" + usedFor+ "&page=" + page,
			success : function(response2) {
				var teacherAssignedDates = response2.teacherAssignedDates;
				$("#assignedDate").empty();
				$("#assignedDate").append(
						$("<option></option>").val('select').html('select date'));
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
				$("<option></option>").val('select').html('select'));
		$("#titleId").empty();
		$("#titleId").append(
				$("<option></option>").val('select').html('select'));
		$('#studentDetailsPage').html('');
	}
}

function showAssignmentTitles(){
	var usedFor = $('#usedFor').val();
	var csId = $('#csId').val();
	var assignedDate= $('#assignedDate').val();
	var page = dwr.util.getValue('page');
	if(assignedDate != 'select'){
		$.ajax({
			type : "GET",
			url : "getAssignmentsTitles.htm",
			data : "csId=" + csId + "&usedFor=" + usedFor+"&assignedDate="+assignedDate+ "&page=" + page,
			success : function(response2) {
				var assignmentTitles = response2.assignmentTitles;
				$("#title").empty();
				$("#title").append(
						$("<option></option>").val('select').html('select title'));
				$.each(assignmentTitles, function(index, value) {
					$("#title").append($("<option></option>").val(value.assignmentId).html(value.title));
				});
			}
		});
	}else{
	   	$('#studentDetailsPage').html('');
		$("#title").empty();
		$("#title").append($("<option></option>").val('select').html('select title'));
	}
}

function getStudentDetailsForGrade(){
	var assignmentId = window.parent.document.getElementById( 'titleId' ).value;
	if(!assignmentId)
		assignmentId = dwr.util.getValue('titleId');
	
	var studentDetailsForGradeCallBack = function (list){
	var str = "<table class='des' border=0><tr><td><div class='Divheads'><table align='center' cellpadding='5' cellspacing='5'> <tr> " +
		"<th width='106' align='center'>Select </th>" +
		"<th width='106' align='center'>Student Id</th>" +
		"<th width='150' align='center'>Student Name</th>" +
		"<th width='120' align='center'>Test Status</th>" +
		"<th width='150' align='center'>Graded Status</th></table></div><div class='DivContents'><table><tr><td>&nbsp;</td></tr>";

	for (i=0;i<list.length;i++){
		var lastSavedSet = list[i].lastSavedSet;
		if(!lastSavedSet)
		  lastSavedSet = 0;
		str +=  "<tr><td width='106' align='center' height='25'> <input type='radio' class='radio-design' id='radio"+i+"' name='radio' value='"+list[i].studentAssignmentId+"' onClick=\"getStudentsTestDetails(this,'"+i+"','"+list[i].student.userRegistration.user.userType+"','"+list[i].student.userRegistration.regId+"','"+list[i].gradedStatus+"','"+list[i].status+"','"+list[i].student.studentId+"','"+list[i].assignment.assignmentId+"','"+lastSavedSet+"')\" /><label for='radio"+i+"' class='radio-label-design'></label></td>" +
				"<td  width='130' align='center' class='txtStyle'>"+list[i].student.studentId+"</td> " +
				"<td width='150' align='center' class='txtStyle'> "+list[i].student.userRegistration.firstName+" "+list[i].student.userRegistration.lastName+"</td> " +
				"<td th width='150' align='center' class='txtStyle'>"+list[i].status+"</td>" +
				"<td width='150' align='center' class='txtStyle'>"+list[i].gradedStatus+"</td>";

		str += "<td><div id='dialog"+i+"'  style=\"background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;display:none;\" title=''> "+
			   "<iframe id='iframe"+i+"' frameborder='0' scrolling='no' width='100%' height='98%' src=''></iframe></div></td>";
		str += "</tr> ";
	 }
		str += "<tr><td height=15 colSpan=30></td></tr><tr><td colspan='20' width='160' align='center'><div id='lpSystemRubricDiv' align='right'/></td></tr></table></div></td></tr></table>";
   		$('#studentDetailsPage').html(str);
   		$("#loading-div-background").hide();
	   	 var isInIFrame = (window.location != window.parent.location) ? true : false;
		 if(!isInIFrame)
			 setFooterHeight();
	}
	if(assignmentId != 'select'){
		 $("#loading-div-background").show();
		gradeAssessmentsService.getStudentAssessmentTests(assignmentId,{
			callback : studentDetailsForGradeCallBack
		});
	}else{
	   	$('#studentDetailsPage').html('');
		return false;
	}
}

function getStudentsTestDetails(obj,id,userType,regId,gradedStatus,status,studentId,assignmentId,lastSavedSet){
	 var screenWidth = $( window ).width() - 150;
	 var screenHeight = $( window ).height() -10;
			jQuery.curCSS = jQuery.css;
				 $("#dialog"+id).dialog({
						overflow: 'auto',
						dialogClass: 'no-close',
					    autoOpen: false,
						position: {my: "center", at: "center", of:window ,within: $("body") },
					    title: 'Phonic Skill Test Grading',
					    draggable: true,
					    width : screenWidth,
					    height : screenHeight,
					    resizable : true,
					    modal : true,
					    close : function(){
					    	$('#studentDetailsPage').html('');
					    	getStudentDetailsForGrade();
			              }  
					});
			 var iframe = $('#iframe'+id);
			     iframe.attr('src', "insertStudentsTestDetails.htm?studentAssignmentId="+obj.value +"&userType="+userType+"&regId="+regId+"&studentId="+studentId+"&assignmentId="+assignmentId+"&status="+status+"&gradedStatus="+gradedStatus+"&page=Phonic Skill Test&lastSavedSet="+lastSavedSet);
			if(status == 'pending'){
				if(confirm("Do you want to go for Live grade ?",function(status){
					if(status){
						 $("#dialog"+id).dialog('option', 'height', screenHeight-120);
		            	  $("#dialog"+id).dialog("open");
		            	  return true;
					}
				})); 
	        }else{
	        	 $("#dialog"+id).dialog("open");
	        }
}
var contentArray =  new Array();
var groupIdArray = new Array();
var keyStoreArray = new Array();
var commentsArray = new Array();
var marksArray = new Array();
var existedSecuredMarks = 0;
var isLastValue = false;
var isRecording = false;
var contentIndex = 0;
var phonicContentMap = new Map();
var len = -1;

function insertStudentsTestDetails(){
	var studentAssignmentId = dwr.util.getValue('studentAssignmentId');
	var lastSavedSetId = dwr.util.getValue('lastSavedSet');
	 var allPhonicGroupsCallBack = function(list) {
		 var questionsArray = new Array();
		 var titleArray = new Array();
		 var setsLen = list.length;
		 for (i=0;i<setsLen;i++){
 			if(list[i].groupId == lastSavedSetId){
			   var phonicSkillTestFilePath = dwr.util.getValue('phonicSkillTestFilePath')+'/'+lastSavedSetId+'/'+lastSavedSetId+'.wav';
			   setAudioPathToPlayer(phonicSkillTestFilePath,list[i].title,function(status){
				   if(status == 'true'){
				     groupIdArray.push(list[i].groupId);
					 questionsArray.push(list[i].question.trim());
					 titleArray.push(list[i].title);
					 contentArray = list[i].question.trim().split(' ');
					 phonicContentMap[list[i].title] = contentArray;
				   }
			   });
			  break;
		   }else{
			 groupIdArray.push(list[i].groupId);
			 questionsArray.push(list[i].question.trim());
			 titleArray.push(list[i].title);
			 contentArray = list[i].question.trim().split(' ');
			 phonicContentMap[list[i].title] = contentArray;
		   }
		 }
		 var lastSet = list[setsLen-1].groupId;
		 if(lastSet == lastSavedSetId)
			 len = Object.keys(phonicContentMap).length; 
		 else
			 len = Object.keys(phonicContentMap).length-1; 

		 var keysArray = Object.keys(phonicContentMap);
		 getTestContentData(keysArray[0]);
	 }
	 assignPhonicSkillTestService.getAssignedStudentPhonicGroups(studentAssignmentId,{
			callback : allPhonicGroupsCallBack
	 });
}

function getTestContentData(keyVal) {
	for(var key in phonicContentMap)
	 {
		if(keyVal == key){
			if(keyStoreArray.length > 0){
				if(keyStoreArray.indexOf(key) == -1){
					keyStoreArray.push(key);
				}
			}else{
				keyStoreArray.push(key);
			}
			 getAlreadyGradedContent(key);
			 return;
		}
	 }
}

function updateContent(key){
	for(i = 0 ; i < phonicContentMap[key].length ; i++){
	   var mapKeyVal = phonicContentMap[key];
	   var contentDivContent = "<table width='100%' align='center' cellpadding='1' cellspacing='1' ><tr><td class='groupNameCls' width='100%' align='left'><u><font color='#243cc3' size='4'>"+key+": </font></u> </td></tr>";
	   contentDivContent += "<tr><td width='100%'><table align='center' cellpadding='1' cellspacing='1' width='100%' height='100%'><tr>";
	   var count = 0;
	   for(j = 0 ; j < mapKeyVal.length ; j++){
		   if(count == 5){
			   if(marksArray[j] == 0){
				   contentDivContent +=  "</tr><tr><th width='20%' align='center' style='padding-top: 20px;padding-bottom: 25px;' colspan='2'><input type='hidden' id='marks"+j+"' id='marks"+j+"' value='0'/><span id='word"+j+"' class='red-content' onclick='openCommentWindow("+j+")'>"+mapKeyVal[j]+"</span><textarea id='comment"+j+"' name='textarea"+j+"' style='display:none;font-size: 13px;' onblur='closeComment("+j+")' class='box' onkeypress='searchKeyPress("+j+")'>"+commentsArray[j]+"</textarea></th>";
			   }else{
				   contentDivContent +=  "</tr><tr><th width='20%' align='center' style='padding-top: 20px;padding-bottom: 25px;' colspan='2'><input type='hidden' id='marks"+j+"' id='marks"+j+"' value='1'/><span id='word"+j+"' class='blue-content' onclick='openCommentWindow("+j+")'>"+mapKeyVal[j]+"</span></th>";
			   }
			   count = 0;
 		   }else{
 			  if(marksArray[j] == 0){
				   contentDivContent +=  "<th width='20%' align='center' colspan='2' style='padding-top: 20px;padding-bottom: 25px;'><input type='hidden' id='marks"+j+"' id='marks"+j+"' value='0'/><span id='word"+j+"' class='red-content' onclick='openCommentWindow("+j+")'>"+mapKeyVal[j]+"</span><textarea id='comment"+j+"' name='textarea"+j+"' style='display:none;font-size: 13px;' onblur='closeComment("+j+")' class='box' onkeypress='searchKeyPress("+j+")'>"+commentsArray[j]+"</textarea></th>";
			   }else{
				   contentDivContent +=  "<th width='20%' align='center' colspan='2' style='padding-top: 20px;padding-bottom: 25px;'><input type='hidden' id='marks"+j+"' id='marks"+j+"' value='1'/><span id='word"+j+"' class='blue-content' onclick='openCommentWindow("+j+")'>"+mapKeyVal[j]+"</span></th>";
			   }
		  }
	    	 
		   if(j == mapKeyVal.length-1){
    		 contentDivContent += "</tr></table>";  
		   }
		   count = count + 1;
	   }
	   contentDivContent += "</td></tr></table></tr></table>";
	   $("#testContentDiv").append(contentDivContent);
	   
	   var marksAndSubmitDivContent = "<table hieght='100%' width='100%' align='center' cellpadding='1' cellspacing='1' ><tr><td align='left'>" +
 		"<div align='center'><b> Max. Marks: &nbsp;</b><input type='text' name='maxmarks' readonly id='maxmarks' value='"+mapKeyVal.length+"'></div><br> " +
 		"<div align='center'><b> Sec. Marks: &nbsp;</b><input type='text' name='secmarks' readonly id='secmarks' value='"+mapKeyVal.length+"'></div></td>" +
 		"<td align='right'>"+
      "<div align='center'><div class='button_green' id='btSubmitChanges' name='btSubmitChanges' style='width:90px;font-size:12px;'" +
      " onClick='submitGradePhonicTest(\""+mapKeyVal.length+"\",\""+groupIdArray[contentIndex]+"\")'>Submit Changes</div></div></td>" +
      "</tr><table>";
      $("#marksAndSubmitDiv").append(marksAndSubmitDivContent);
       if(existedSecuredMarks > 0){
    	   dwr.util.setValue('secmarks',existedSecuredMarks);
    	   existedSecuredMarks = 0;
       }
	   var phonicSkillTestFilePath = dwr.util.getValue('phonicSkillTestFilePath')+'/'+groupIdArray[contentIndex]+'/'+groupIdArray[contentIndex]+'.wav';
	   var status = dwr.util.getValue('status');
	   var gradedStatus = dwr.util.getValue('gradedStatus');
	   if(status == 'submitted' && gradedStatus != 'live graded'){
	   	   $("#audioDiv").css({visibility: "visible"});
	   	   $("#audioDiv").append(getPlayerContent());
		   setAudioPathToPlayer(phonicSkillTestFilePath,key,function(status){
			   if(status == 'false'){alert("Audio File Not Existed");}
		   });
	   }
	   var controllerDivContent = "<table align='center' cellpadding='1' cellspacing='1'><tr>"+
	   							  "<td width='650' align='left' class='explore-bars' style='cursor: pointer;'><div class='ion-arrow-left-b' onClick='goForPreviousContent()'></div></td>"+
	   							  "<td width='650' align='center' style='padding: 15px;'></td>"+
	   							  "<td width='650' align='right' class='explore-bars' style='cursor: pointer;'><div class='ion-arrow-right-b' onClick='goForNextContent()'></div></td>";
	   	   controllerDivContent +="</tr></table>";
	   $("#controllerDiv").append(controllerDivContent);
	   
	   marksArray.length = 0;
	   commentsArray.length = 0;
	   return;
    }
}

function openCommentWindow(wordNum){
	 if(!document.getElementById("comment"+wordNum)){
		 $('#word'+wordNum).after("<textarea id='comment"+wordNum+"' name='textarea"+wordNum+"' style='display:none;font-size: 13px;' onblur='closeComment("+wordNum+")' class='box' onkeypress='searchKeyPress("+wordNum+")'></textarea>");
	 }
	showCommentBox(wordNum);
	var marks = dwr.util.getValue('marks'+wordNum);
	if(marks == 1){
	    $('#word'+wordNum).removeClass('blue-content').addClass('red-content');
	}
}

function showCommentBox(wordNum){
	var posX = $('#word'+wordNum).position().left, posY = $('#word'+wordNum).position().top;
	    posX = (posX - 10)+"px";
		posY = (posY - 65)+"px";
		$("#comment"+wordNum).css({left:posX,top:posY});
		$("#comment"+wordNum).fadeIn(500);
		$("#comment"+wordNum).focus();
}

function closeComment(wordNum){
	commentsArray = $.grep(commentsArray, function(value) {
	    return value.indexOf(wordNum + "$") < 0;
	});
	var secmarks = dwr.util.getValue('secmarks');
	var marks = dwr.util.getValue('marks'+wordNum);
	var value = $("#comment"+wordNum).val();
	if(value){
		commentsArray.push(wordNum + "$"+value);
		dwr.util.setValue('marks'+wordNum,0);
		if(marks == 1){
			dwr.util.setValue('secmarks',--secmarks)
		}
		$("#comment"+wordNum).html(value);
		$("#comment"+wordNum).fadeOut(500);
	}else{
		dwr.util.setValue('marks'+wordNum,1);
		if(marks == 0){
			dwr.util.setValue('secmarks',++secmarks)
		}
		$('#word'+wordNum).removeClass('red-content').addClass('blue-content');
		$("#comment"+wordNum).remove();
	}
}
function searchKeyPress(wordNum){
	event = window.event;
    if(event.keyCode == 13){
    	event.preventDefault();
		commentsArray = $.grep(commentsArray, function(value) {
		    return value.indexOf(wordNum + "$") < 0;
		});
		var secmarks = dwr.util.getValue('secmarks');
		var marks = dwr.util.getValue('marks'+wordNum);
    	var value = $("#comment"+wordNum).val();
    	if(value){
    		commentsArray.push(wordNum + "$"+value);
    		dwr.util.setValue('marks'+wordNum,0);
    		if(marks == 1){
    			dwr.util.setValue('secmarks',--secmarks)
    		}
    		$("#comment"+wordNum).html(value);
        	$("#comment"+wordNum).fadeOut(500);
    	}else{
    		dwr.util.setValue('marks'+wordNum,1);
    		if(marks == 0){
    			dwr.util.setValue('secmarks',++secmarks);
    		}
    		$('#word'+wordNum).removeClass('red-content').addClass('blue-content');
    		$("#comment"+wordNum).remove();
    	}
    	
        return false;
    }
    return true;
}

function getCommentwindow(id,keyLength){
	var marks = dwr.util.getValue('marks'+id);
	var secmarks = dwr.util.getValue('secmarks');
	if(marks == 1){
		$("#comments"+id).css({visibility: "visible"});
		dwr.util.setValue('marks'+id,0)
		dwr.util.setValue('secmarks',--secmarks)
		if(keyLength > 5){
			$('#contentId'+id).removeClass('bigContentBlueFont').addClass('bigContentRedFont');
		}else{
			$('#contentId'+id).removeClass('smallContentBlueFont').addClass('smallContentRedFont');
		}
	}else if(marks == 0){
		$("#comments"+id).css({visibility: "hidden"});
		dwr.util.setValue('marks'+id,1)
		dwr.util.setValue('secmarks',++secmarks)
		if(keyLength > 5){
			$('#contentId'+id).removeClass('bigContentRedFont').addClass('bigContentBlueFont');
		}else{
			$('#contentId'+id).removeClass('smallContentRedFont').addClass('smallContentBlueFont');
		}
	}
	$("#comments"+id).focus();
}

function setAudioPathToPlayer(phonicSkillTestFilePath,groupName,callback){
	var studentRegId = dwr.util.getValue('studentRegId');
	var param = "usersFilePath="+phonicSkillTestFilePath+"&regId="+studentRegId;
	 $("#loading-div-background1").show();
	
	 $.ajax({
		 type: "GET",
	     url:"checkFileExists.htm",
	     data: param,
	     success: function(status){
		      $("#loading-div-background1").hide();
		      if(status == 'Exists'){
		    	  var path = "loadUserFile.htm?usersFilePath="+phonicSkillTestFilePath+"&regId="+studentRegId;
		    	  $("#player").attr("src", path);
		    	  if(callback)
		    		callback("true");
		      }else{
		    	  if(callback)
			    	callback("false");
		      }
	     }
	 });
}
function checkAudioFileExists(param) {
	 $("#loading-div-background1").show();
	 $.ajax({
		 type: "GET",
	     url:"checkFileExists.htm",
	     data: param,
	     success: function(status){
	      $("#loading-div-background1").hide();
	      if(status == 'Exists'){
	    	return status;
	      }else{
	    	return status;
	      }
	     }
	 });
}
function goForPreviousContent(){
	  if(contentIndex > 0){
		  --contentIndex;
		  $("#testContentDiv").html('');
		  $("#audioDiv").html('');
		  $("#marksAndSubmitDiv").html('');
		  $("#controllerDiv").html('');
		  $("#commentsDiv").html('');
		  var key = keyStoreArray[contentIndex];
		  getAlreadyGradedContent(key)
	  }else{
		  systemMessage("First Set");
		  return false;
	  }
}
function goForNextContent(){
	var keysArray = Object.keys(phonicContentMap);
	  if(contentIndex < len){
		  ++contentIndex;
		  $("#testContentDiv").html('');
		  $("#audioDiv").html('');
		  $("#marksAndSubmitDiv").html('');
		  $("#controllerDiv").html('');
		  $("#commentsDiv").html('');
		  if(JSON.stringify(keyStoreArray) === JSON.stringify(keysArray) ){
			  var key = keyStoreArray[contentIndex];
			  getAlreadyGradedContent(key);
		  }else{
			  getTestContentData(keysArray[contentIndex]);
		  }
	  }else{
		  systemMessage("Last Set");
		  return false;
	  }
}
function getAlreadyGradedContent(key){
	var studentAssignmentId = dwr.util.getValue('studentAssignmentId');
	var groupId = groupIdArray[contentIndex];
	var studentPhonicTestMarksByGroupIdCallBack = function (studentPhonicTestMarks){
		var comments = studentPhonicTestMarks.comments; 
		var marksString = studentPhonicTestMarks.marksString;
		    existedSecuredMarks = studentPhonicTestMarks.secMarks;
		if(comments){
			if(comments.indexOf(':') > -1){
				commentsArray = comments.split(':');
			}
		}
		if(marksString){
			if(marksString.indexOf(' ') > -1){
				marksArray = marksString.split(' ');
			}
		}
		updateContent(key);
	}
	if(studentAssignmentId && groupId){
		gradePhonicSkillTestService.getStudentPhonicTestMarksByGroupId(studentAssignmentId,groupId,{
			callback : studentPhonicTestMarksByGroupIdCallBack
		});
	}
	 
}

function getPlayerContent(){
	  var audioDivContent = "<table align='center' hieght='100%' width='23%' border=0>"+
      "<tr><td align='center' style='background:#6fb8d7;'><audio src='' id='player' controls style='margin-top: 0.2em;'></audio>"+
   "</td></tr>";
	  
	  /*<div id='jquery_jplayer_1' class='jp-jplayer'></div>"+
		"<div id='jp_container_1' class='jp-audio' role='application' aria-label='media player'>"+
		"<div class='jp-type-single'>"+
			"<div class='jp-gui jp-interface'>"+
				"<div class='jp-controls'>"+
					"<button class='jp-play' role='button' tabindex='0'>play</button>"+
					"<button class='jp-stop' role='button' tabindex='0'>stop</button>"+
				"</div><div class='jp-progress'><div class='jp-seek-bar'>"+
						"<div class='jp-play-bar'></div></div></div>"+
				"<div class='jp-volume-controls'>"+
					"<button class='jp-mute' role='button' tabindex='0'>mute</button>"+
					"<button class='jp-volume-max' role='button' tabindex='0'>max volume</button>"+
					"<div class='jp-volume-bar'>"+
						"<div class='jp-volume-bar-value'></div>"+
					"</div>"+
				"</div>"+
				"<div class='jp-time-holder'>"+
					"<div class='jp-current-time' role='timer' aria-label='time'>&nbsp;</div>"+
					"<div class='jp-duration' role='timer' aria-label='duration'>&nbsp;</div>"+
					"<div class='jp-toggles'>"+
						"<button class='jp-repeat' role='button' tabindex='0'>repeat</button>"+
					"</div>"+
				"</div>"+
			"</div>"+
			"<div class='jp-details'>"+
				"<div class='jp-title' aria-label='title'>&nbsp;</div>"+
			"</div>"+
	"</div>"+
"</div>*/
return audioDivContent;
}

function submitGradePhonicTest(totalMarks,groupId){
	var secmarks = dwr.util.getValue('secmarks');
	var studentAssignmentId = dwr.util.getValue('studentAssignmentId');
	var lastSavedSetId = dwr.util.getValue('lastSavedSet');
	var commentStr='';
	var marksStr='';
	for(i = 0 ; i < totalMarks ; i++){
		var marks = dwr.util.getValue('marks'+i);
		marksStr += marks+' ';
		var comment = dwr.util.getValue('comment'+i);
		if(marks == 0 && !comment){
			systemMessage("Please write a comment for wrong answer");
			$("#comments"+i).focus();
			return false;
		}
		if(i < totalMarks-1)
		 commentStr += comment+':';
		else
		 commentStr += comment;	
	}
	if(studentAssignmentId && groupId && secmarks && totalMarks){
		$.ajax({
			url : "submitStudentPhonicTestMarks.htm",
			data: "studentAssignmentId="+studentAssignmentId+"&groupId="+groupId+"&lastSavedSetId="+lastSavedSetId+"&secmarks="+secmarks+"&totalMarks="+totalMarks+"&commentStr="+commentStr+"&marksStr="+marksStr,
			success : function(data){
				goForNextContent();
				 systemMessage(data);
			}
		});
	}
}