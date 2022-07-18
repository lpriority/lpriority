//Set the number of minutes you need
var timerFlag = true;
var maxTime = 60;
var currentSeconds = 0;
var currentMinutes = 0;
var isRecordStopped = true;
var stopRetellTimer = false;
var isRecording = false;

function record(questionCount, passageType) {
	if(!isRecording) {
		isRecordStopped = true;
		isRecording = true;
		//timerFlag =  true;
		// ask for permission and start recording
		clearTime(passageType);		
		recordAudio();	
		if(passageType == 'retell'){
			stopRetellTimer = false;
		}
		
		if (passageType == 'fluency') {	
			document.getElementById('passage' + questionCount).style.display = "";
			document.getElementById('directions'+ questionCount).style.display = "none";
			fluencyDecrement(questionCount);	
			setTimeout(function() {
				stop(questionCount, passageType);
			},61000);
		}
		else if(passageType == 'retell'){
			retellDecrement(questionCount);
			setTimeout(function() {
				if(!stopRetellTimer)
					stop(questionCount, passageType);
			},61000);
		}	
	} else {
		alert('Recording is already in progress');
		return false;
	}
	
}

function stopCheckCall(questionCount, passageType){
	if (isRecording){
		isRecordStopped = true;
		stop(questionCount, passageType);	
	}else {
		alert('Recording is not in progress');
		return false;
	}
	
}
function stop(questionCount, passageType) {
	isRecording = false;
	if(passageType == 'retell'){
		stopRetellTimer = true;
	}
	if(isRecordStopped){
		isRecordStopped = false;
		var audio = $("#"+passageType+questionCount);
		var assignmentQuestionIdVal = document.getElementById('assignmentQuestionId'+ questionCount).value;		
	    $("#loading-div-background1").show();
	    playBackAudio(function(url){
			$("#accId").attr("src", url);
		    $("#accId")[0].play();
		});
	    stopRecording(function(base64){ 
			document.getElementById("audioData").value=base64;
		    document.getElementById('assignmentQuestionId').value= assignmentQuestionIdVal;
			document.getElementById('passageType').value= passageType;		
			  var formBean = document.getElementById('benchmarkUploadForm');
		      var formData = new FormData(formBean);
		      var recTime = 0;
		      $.ajax({
		        url: "saveBenchmarkFiles.htm",
		        type: 'POST',
		        data: formData,
		        contentType: false,
				cache: false,
				processData:false,
		        mimeType:"multipart/form-data",
		        success: function(response) {
		        	var parsedData = JSON.parse(response);
		        	var currentTime = $("#accId")[0].currentTime;
		        	recTime = 60- Math.round(maxTime) - Math.round(currentTime);
		        	//console.debug("maxTime: "+maxTime+" currentSeconds: "+currentSeconds+"recTime :"+recTime);
		        	setTimeout(function() {
		        		$("#accId")[0].pause();
				    	$("#loading-div-background1").hide();
			            if (passageType == 'fluency') {
							document.getElementById("passage"+ questionCount).style.display = "none";
							document.getElementById("retellDiv"+ questionCount).style.display = "";
						}else{
							if(parsedData.status == true)
								showDirections(passageType,	questionCount);
							else
								systemMessage("The test not submitted !!","error");	
						}
				    }, recTime*1000);
		        }
		      });
			});
	}    	
}
function ConvertFormToJSON(form){
    var array = jQuery(form).serializeArray();
    var json = {};
    jQuery.each(array, function() {
        json[this.name] = this.value || '';
    });
    return json;
}
function showPassage(passageType, questionCount) {
	document.getElementById("directions" + questionCount).style.display = "none";
	document.getElementById("passage" + questionCount).style.display = "";
	setTimeout(function() {
		record(questionCount, passageType);
	}, 1000);
}
function showDirections(passageType, questionCount) {
	var testCount = document.getElementById('testCount').value;
	document.getElementById("retellDiv" + questionCount).style.display = "none";
	questionCount++;
	if (document.getElementById("directions" + questionCount) != null) {
		document.getElementById("directions" + questionCount).style.display = "";
	} else {
	   var studentAssignmentId = document.getElementById('studentAssignmentId').value;
	   document.getElementById('stdAssignmentId').value = studentAssignmentId;
	   var formBean = document.getElementById('benchmarkUploadForm');
	   var formData = new FormData(formBean);
		$.ajax({
			type : "POST",
			url : "submitBenchmark.htm",
			data: formData,
	        contentType: false,
			cache: false,
			processData:false,
	        mimeType:"multipart/form-data",
			success : function(response) {
				var parsedData = JSON.parse(response);
				if(parsedData.status == true){
					systemMessage("Test submitted successfully !!");
					setTimeout(function() {
						$('#row'+testCount).remove();
						$('#benchmarkDailog').dialog('close');
						window.location.reload();
					}, 3000);
				}else{
					systemMessage("The test not submitted !!","error");	
					setTimeout(function() {
						$('#benchmarkDailog').dialog('close');
						window.location.reload();
					}, 3000);
				}				
			}
		});
	}
}

function fluencyDecrement(count) {
	if(timerFlag){
		currentMinutes = Math.floor(maxTime / 60);
		currentSeconds = maxTime % 60;
	    if(currentSeconds <= 9) currentSeconds = "0" + currentSeconds;
	    document.getElementById("fluencyMinute"+count).innerHTML = currentMinutes + ":" + currentSeconds; //Set the element id you need the time put into.
	    if(maxTime > 0){ 
	    	setTimeout('fluencyDecrement('+count+')',1000);
	        maxTime--;
		    timerFlag =  true;
	    }else{
	    	timerFlag =  false;
	    }
	}
}
function clearTime(passageType){	
	 //Set the number of minutes you need
	timerFlag = true;
   if (passageType == 'fluency') {
	    maxTime =  60;
    }else if(passageType == 'retell'){
    	maxTime = 60;
    }
	currentSeconds = 0;
	currentMinutes = 0;
}

function retellDecrement(count) {
	if(!stopRetellTimer){
		if(timerFlag){
			currentMinutes = Math.floor(maxTime / 60);
			currentSeconds = maxTime % 60;
		    if(currentSeconds <= 9) currentSeconds = "0" + currentSeconds;
		    document.getElementById("retellMinute"+count).innerHTML = currentMinutes + ":" + currentSeconds; 
		     if(maxTime > 0){ 
		    	setTimeout('retellDecrement('+count+')',1000);
		         maxTime--;
			    timerFlag =  true;
		    }else{
		    	timerFlag =  false;
		    }
		}
	}
}
function beginBenchmarkTest(){
	 document.getElementById('benchmarkTestDiv').style.visibility = "visible";
	 document.getElementById('benchmarkTestReady').style.display = "none";	 
}