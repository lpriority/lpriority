 var phonicContentMap = {};	 
 function goForPhonicSkillTest(dialogDivId,studentAssignmentId,id,assignmentId){
	 jQuery.browser = {};
	 (function () {
	     jQuery.browser.msie = false;
	     jQuery.browser.version = 0;
	     if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
	         jQuery.browser.msie = true;
	         jQuery.browser.version = RegExp.$1;
	     }
	 })();
	 var iframe = $('#iframe'+id);
	 iframe.attr('src', "goForPhonicSkillTest.htm?assignmentId="+assignmentId+"&studentAssignmentId="+studentAssignmentId);
	 var dailogContainer = $(document.getElementById(dialogDivId));
	 var screenWidth = $( window ).width() - 40;
	 var screenHeight = $( window ).height() -15;
	 jQuery.curCSS = jQuery.css;
	 $("#"+dialogDivId).dialog({
		overflow : 'auto',
		dialogClass: 'no-close',
	    autoOpen: false,
	    position: { my: "center", at: "center", of: window },
	    title: 'PHONICS SKILLS TEST',
	    draggable: true,
	    width : screenWidth,
	    height : screenHeight,
	    resizable : true,
	    autoResize:true,
	    modal : true,
	    close : function(){},  
	    beforeClose: function(){}
	});
	 $("#"+dialogDivId).dialog( "open" );
 }

 var contentArray =  new Array();
 var groupIdArray = new Array();
 var sectionIdArray = new Array();
 var keyStoreArray = new Array();
 var directionsArray = new Array();
 var isLastValue = false;
 var isRecording = false;
 var contentIndex = 0;
 var len = 0;
 var recordStatus = false;
 var language = '';
 function beginPhonicTest(){
	 var studentAssignmentId = dwr.util.getValue('studentAssignmentId');
	 var usersFilePath = dwr.util.getValue('phonicSkillTestFilePath');
	 var regId = dwr.util.getValue('regId');
	 var allPhonicGroupsCallBack = function(list) {
		 var questionsArray = new Array();
		 var titleArray = new Array();
		 len = list.length-1;
		 for (i=0;i<list.length;i++){
			 language = list[i].phonicSections.language.language;
			 sectionIdArray.push(list[i].phonicSections.phonicSectionId);
			 groupIdArray.push(list[i].groupId);
			 questionsArray.push(list[i].question.trim());
			 titleArray.push(list[i].title);
			 directionsArray.push(list[i].directions);
			 contentArray = list[i].question.trim().split(' ');
			 phonicContentMap[list[i].title] = contentArray;
		 }
	    var keysArray = Object.keys(phonicContentMap);
		var lastSavedGroupIdCallBack = function (groupId){
			if(groupId > 0){
				 for (j=0; j< groupId; j++){
					  keyStoreArray.push(keysArray[j]);
				 }
				 var index = groupIdArray.indexOf(groupId);
				 contentIndex = index;
				getTestContentData(keysArray[contentIndex]);
			}else{
				 contentIndex = 0;
				 groupId = groupIdArray[0];
				 getTestContentData(keysArray[0]);
			}
			checkAudioFileExists(usersFilePath,groupId,regId);
		}
		if(studentAssignmentId){
			gradePhonicSkillTestService.getLastSavedGroupId(studentAssignmentId,{
				callback : lastSavedGroupIdCallBack
			});
		}

	 }
    assignPhonicSkillTestService.getAssignedStudentPhonicGroups(studentAssignmentId,{
		callback : allPhonicGroupsCallBack
	 });
	 $("#takeTest").show();
	 $("#getReady").hide();
	 $("#testContentDiv").html('')
	 $("#headerDiv").append('')
	 $("#submitDiv").html('');	 
}
 
 function getTestContentData(keyVal) {
		for(var key in phonicContentMap){
			if(keyVal == key){
				if(keyStoreArray.length > 0){
					if(keyStoreArray.indexOf(key) == -1){
						keyStoreArray.push(key);
					}
				}else{
					keyStoreArray.push(key);
				}
				 updateContent(key);
				 return;
			}
		}
  }

 function updateContent(key){
	  $("#recordDiv").hide();
	  $("#headerDiv").html('');
	  $("#submitDiv").html('');
	  $("#testContentDiv").html('');
	  var headerDivContent ="<table align='left' width='100%' height='100%'><tr><td width='100%' align='center'colspan='4' class='dialogTitle'>PHONICS SKILLS TEST </td></tr><tr><td width='100%' height='5' colspan='4'></td></tr>"+
	   "<tr><td width='12%' align='left' colspan='1'><h2><font color='#243cc3'>"+key+":</font></h2></td>"+ //<div id='recordDiv' name='recordDiv' class='button_light_green' align='right' onclick=recordAudio()>RECORD</div></td>"+
		"<td  width='7%' align='left' colspan='1'><div id='recordedImg' style='display:none;font-size:2.8em;color:#0a8e0f;margin-left:1px;'><i class='fa fa-thumbs-o-up'></i></div></td><td width='7%' align='left' colspan='1'></td><td width='7%'>" + //<div id='stopDiv' name='stopDiv' class='button_pink' align='right' onclick='stopRecording()'>STOP</div></td><td width='7%'>" +
		"<div id='directionsDiv' style='"+ ((directionsArray[contentIndex] == 'yes') ? 'display:block;' : 'display:none;')+"width:40%;font-size:1.2em;color:##000000;margin-left:2em;font-weight:bold;cursor: pointer; cursor: hand;font-family:\"Lato\", Calibri, Arial, sans-serif;font-weight:bold;color:#3F51B5;'  onClick='playDirections()'>Directions <i class='fa fa-file-audio-o' style='font-size:1.5em;'></i></div></td>" +
		"</tr></table>";
		$("#headerDiv").append(headerDivContent);
		for(i = 0 ; i < phonicContentMap[key].length ; i++){
		   var mapKeyVal = phonicContentMap[key];
		   var contentDivContent = "";
		   if(directionsArray[contentIndex] == 'yes'){
			   if(contentIndex == len){
				   contentDivContent = "<table align='center' cellpadding='1' cellspacing='1' width='100%' height='100%'><tr><th width='30' align='left' colspan='6'><input type='hidden' id='key' value='"+key+"'/><audio id='directionAudio'><source src='loadCommonFile.htm?commonFilePath=phonic_skills_direction_audios/"+language+"/Final.wav' type='audio/wav' /></audio></th></tr>";
			   }else{
				   if(language == 'English')
					   contentDivContent = "<table align='center' cellpadding='1' cellspacing='1' width='100%' height='100%'><tr><th width='30' align='left' colspan='6'><input type='hidden' id='key' value='"+key+"'/><audio id='directionAudio'><source src='loadCommonFile.htm?commonFilePath=phonic_skills_direction_audios/"+language+"/"+groupIdArray[contentIndex]+".wav' type='audio/wav' /></audio></th></tr>";
				   else if(language == 'Spanish')
					   contentDivContent = "<table align='center' cellpadding='1' cellspacing='1' width='100%' height='100%'><tr><th width='30' align='left' colspan='6'><input type='hidden' id='key' value='"+key+"'/><audio id='directionAudio'><source src='loadCommonFile.htm?commonFilePath=phonic_skills_direction_audios/"+language+"/"+sectionIdArray[contentIndex]+".wav' type='audio/wav' /></audio></th></tr>";
			   }
		   }else{
			   contentDivContent = "<table align='center' cellpadding='1' cellspacing='1' width='100%' height='100%'><tr><th width='30' align='left' colspan='6'><input type='hidden' id='key' value='"+key+"'/></th></tr>";
		   }
		   var count = 0;
		   for(j = 0 ; j < mapKeyVal.length ; j++){
			   if(count == 5){
				   contentDivContent +=  "</tr><tr><td></td><th width='250' align='center' style='padding-top: 20px;padding-bottom: 25px;' colspan='2' class='ps-font-size'>"+mapKeyVal[j]+"</th>";
				   count = 0;
			   }else if(j != 0 && count == 0){	
				   contentDivContent +=  "<td></td>";
     		   }else{
				   if(j == 0)
		    		 contentDivContent +=  "<td><div id='recordDiv' name='recordDiv' class='button_green_round' align='right' onclick=audioRecording()>GO</div></td>";
				   if(mapKeyVal[j])
					   contentDivContent +=  "<th width='250' align='center' colspan='2' style='padding-top: 20px;padding-bottom: 25px;' class='ps-font-size'>"+mapKeyVal[j]+"</th>";
			   }
		    	 
			   if(j == mapKeyVal.length-1){
	    		 contentDivContent +=  "<td><div id='stopDiv' name='stopDiv' class='button_pink_round' align='right' onclick='audioStopping()'>STOP</div></td>";
	    		 contentDivContent += "</tr><tr><td height=15 colSpan=30></td></tr><tr><td colspan='20' width='160' align='center'></td></tr></table>";  
			   }
			   count = count + 1;
		   }
		   contentDivContent += "</tr></table>";
		   $("#testContentDiv").append(contentDivContent);
		   var submitDivContent = "<table align='center' cellpadding='6' cellspacing='6' width='100%'> <tr> ";
		   	   submitDivContent +=  
		   		    "<td width='80' align='left' colspan='2'></td>"+ //<div id='previousImg' style='font-size:4em;color:#000102;margin-left:0.5em;cursor: pointer; cursor: hand;' onClick='goForPreviousContent()'><i class='fa fa-chevron-circle-left' aria-hidden='true'></i></div>
			        "<td width='60' align='center' colspan='2'><div id='save' name='save' class='button_green' align='right' onclick=updateTestStatus('save') style='font-size:0.9em;'>Save Changes</div></td>"+
			        "<td  width='60' align='left' colspan='2'><div id='submit' name='submit' class='button_green' onclick=updateTestStatus('submit') style='font-size:0.9em;'>End Test</div></td>" +
			        "<td width='60' align='right' colspan='2'></td>"+ //<div id='nextImg' style='font-size:4em;color:#000102;margin-right:0.5em;cursor: pointer; cursor: hand;' onClick='goForNextContent()'><i class='fa fa-chevron-circle-right' aria-hidden='true'></i></div></td>"+
			        "</tr></table>";
		   	 $("#submitDiv").append(submitDivContent);
		   return;
	    }
 }
 function playDirections(){
    document.getElementById('directionAudio').play();
	return false;
 }
 function updateTestStatus(id){
	 var response = false;
	 var message;
	 if(contentIndex != 0){
		 $("#status").html('');
		 var usersFilePath = dwr.util.getValue('phonicSkillTestFilePath');
		 var regId = dwr.util.getValue('regId');
		 checkAudioFileExists(usersFilePath,groupIdArray[contentIndex],regId);
		 if(id == "save"){
		     document.getElementById("statusVal").value = "saved";
		     message = "Are you sure you want to Save Changes?";
		 }else if (id == "submit"){
			 document.getElementById("statusVal").value = "submitted";
			 message = "Are you sure you want to End the Test?";
    	 }	 
		 if(confirm(message,function(status){
			if(status){
				saveOrSubmitTest(id);
	        }else{
	        	return false;
	        }
		 })); 
	 }
 }
  
 function saveOrSubmitTest(id){
	 if (id == "submit")
		document.getElementById("statusVal").value = "submitted";
		document.getElementById("groupId").value = groupIdArray[contentIndex];
		var phonicSkillTestForm = $("#phonicSkillTestForm").serialize();
      	 $.ajax({
      		 type: "POST",
      	     url:"updateTestStatus.htm",
      	     async: false,
      	     cache: false,
      	     contentType: 'application/x-www-form-urlencoded',
      	     dataType: "json",
      	     data: phonicSkillTestForm,
      	     success: function(response){
      	    	$("#loading-div-background1").hide();
      	    	$("#status").append("<font color='blue' face='courier'>"+response.earlyLiteracyTestsForm.result+"</font>");
      	    	if (id == "submit"){
	      	    	var i = setInterval(function(){ 
		  	       	 clearInterval(i);
		  	         window.parent.location.reload();
		  	       	    }, 3000);
      	    	 }
      	     }
      	 });
 }
 function goForPreviousContent(){
	 var usersFilePath = dwr.util.getValue('phonicSkillTestFilePath');
	 var regId = dwr.util.getValue('regId');
	  if(contentIndex > 0){
		  --contentIndex;
		  $("#testContentDiv").html('');
		  var key = keyStoreArray[contentIndex];
		  updateContent(key);
		  checkAudioFileExists(usersFilePath,groupIdArray[contentIndex],regId);
	  }else{
		  return false;
	  }
}
 
function goForNextContent(){
	var usersFilePath = dwr.util.getValue('phonicSkillTestFilePath');
	var regId = dwr.util.getValue('regId');
	var keysArray = Object.keys(phonicContentMap);
	  if(contentIndex < len){
		  ++contentIndex;
		  $("#testContentDiv").html('');
		  if(JSON.stringify(keyStoreArray) === JSON.stringify(keysArray) ){
			  var key = keyStoreArray[contentIndex];
			  updateContent(key);
			  checkAudioFileExists(usersFilePath,groupIdArray[contentIndex],regId);
		  }else{
			  getTestContentData(keysArray[contentIndex]);
			  checkAudioFileExists(usersFilePath,groupIdArray[contentIndex],regId);
		  }
	  }else{
		  return false;
	  }
} 
 function audioRecording(){
	  if(isRecording == false){	
		  $("#status").html('');
	    	var directionAudio = $("#directionAudio")[0];
	    	if(directionAudio){
				if (directionAudio.duration > 0 && !directionAudio.paused)
					  document.getElementById('directionAudio').pause();
	    	}
		  if(recordStatus){
			  var response;
			  var message;
			   if(contentIndex == groupIdArray.length-1)
				   message = "You reached the last group. Are you sure for re-recording?";	 
			   else
				   message = "Already Recorded. Are you sure for re-recording?";
			   if(confirm(message,function(status){
					if(status){
						$("#status").attr("class", "recording ");
				    	recordAudio(function(){			    		
				    		$("#status").append("<font face='courier'>Recording....</font>");
					    	isRecording = true;
				    	});
				    	isRecording = true;
					}else{
						return false;
					}
			   })); 
		  }else{
			  $("#status").attr("class", "recording");
			  recordAudio(function(){			    		
		    		$("#status").append("<font face='courier'>Recording....</font>");
			    	isRecording = true;
		    	});
			  isRecording = true;
		  }
	  }else{
		  alert("Recording already started. Click on Stop button to proceed further");  
		  return false;
	  }
 } 
 
function audioStopping(){
	if(isRecording == true){
		$("#loading-div-background1").show();	
		$("#status").html('');
		if(contentIndex != groupIdArray.length-1){
			$("#status").attr("class", "stopped");
			$("#status").append("<font face='courier'>Stopped...</font>");
		}
		isRecording = false;
		var keys = Object.keys(phonicContentMap);
		if(JSON.stringify(keyStoreArray) === JSON.stringify(keys) ){
			isLastValue = true;
		}
		dwr.util.setValue('lastValue',isLastValue);
		 stopRecording(function(base64){ 
			document.getElementById("groupId").value = groupIdArray[contentIndex];			
			var groupId = document.getElementById('groupId').value;
			var assignmentId = document.getElementById('assignmentId').value;
			var formData = new FormData();
			formData.append('audioData', base64);
			formData.append("groupId",groupId);
			formData.append("assignmentId",assignmentId);
			     
			$.ajax({
				url: "recordPhonicTest.htm",
			    type: 'POST',
			    data: formData,
			    contentType: false,
			    processData: false,
			    success: function(url) {
			    	if(contentIndex == groupIdArray.length-1){
			    		$('#recordDiv').css('pointer-events', 'none');
			    		$('#stopDiv').css('pointer-events', 'none');
			    		saveOrSubmitTest('submit');
					}else{
				    	if(len == contentIndex){
				    		var usersFilePath = dwr.util.getValue('phonicSkillTestFilePath');
			      	    	var regId = dwr.util.getValue('regId');
			      	    	checkAudioFileExists(usersFilePath,groupIdArray[contentIndex],regId);
			      	    }
		      	    	goForNextContent();
					}
		      	    $("#loading-div-background1").hide();
			    }
			});
		});
	 }else{
		 alert("Recording not yet started !!");
		 return false;
	 }
  }
 
 function checkAudioFileExists(usersFilePath,groupId,regId) {
	 var grpId = 0;
	 if(groupId==0)
		grpId = groupId+1;
	 else
		 grpId = groupId;
	 
	 $("#loading-div-background1").show();
	 var param = "usersFilePath="+usersFilePath+"\\"+grpId+"\\"+grpId+".wav"+" &regId="+regId;
  	 $.ajax({
  		 type: "GET",
  	     url:"checkFileExists.htm",
  	     data: param,
  	     success: function(status){
  	      $("#loading-div-background1").hide();
	  	      if(status == 'Exists'){
	  	    	  $("[id^=recordedImg]").show();
	  	    	  recordStatus = true;
	  	      }else{
	  	    	  $("[id^=recordedImg]").hide();
	  	    	  recordStatus = false; 
	  	      }
  	     }
  	 });
}