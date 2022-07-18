//Set the number of minutes you need
var timerFlag = true;
var maxTime = 59;
var currentSeconds = 0;
var currentMinutes = 0;
var isRecorded = false;
var isRecording = false;

function record(questionCount, passageType,passageCount) {
	if(!isRecording) {
		$('#startAudio').val("true");
		isRecorded = true;
		isRecording = true;
		$('#accuracyRecording'+passageCount).addClass('blink-text');
		$("#recordAccuracy" + questionCount).prop('disabled', true);
		recordAudio();
	} else {
		alert('Recording is already in progress');
		return false;
	}	
}
function stop(questionCount, passageType, passageCount) { 
	isRecording = false;
	if(isRecorded){
		isRecorded = false;
		$('#accuracyRecording'+passageCount).removeClass('blink-text');
		$("#loading-div-background1").show();
		$("#audioDiv").show();
		playBackAudio(function(url){
			$("#accId").attr("src", url);
		    $("#accId")[0].play();
		});
		stopRecording(function(base64){ 
		 var assignmentQuestionIdVal = document.getElementById('assignmentQuestionId'+ questionCount).value;
			document.getElementById('assignmentQuestionId').value = assignmentQuestionIdVal;
			document.getElementById('passageType').value = passageType;
			document.getElementById('audioData').value = base64;
			$('#stopAudio').val("true");
		    var formData = new FormData(document.getElementById('accuracyUploadForm'));
		      $.ajax({
		        url: "saveAccurcayFiles.htm",
		        type: 'POST',
				data: formData,
				mimeType:"multipart/form-data",
				contentType: false,
				cache: false,
				processData:false,
		        success: function(url) {
		        	$("#loading-div-background1").hide();
		        }
		      });
		});
	}
	else{
		alert("Please click on record button");
		return false;
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
function submitTest() {
	var studentAssignmentId = document.getElementById('studentAssignmentId').value;
	var testCount = document.getElementById('testCount').value;
	var isStartPress=$('#startAudio').val();
	var isStopPress=$('#stopAudio').val();
	console.log("start="+isStartPress+"stop="+isStopPress);	
  if(isStartPress=="true" && isStopPress=="true"){
	$.ajax({
		type : "POST",
		url : "submitBenchmark.htm",
		data : "studentAssignmentId=" + studentAssignmentId,
		success : function(response) {
			systemMessage("Test submitted successfully !!");
			setTimeout(function() {
				$('#row'+testCount).remove();
				$('#autoGradeTest').dialog('close');
			}, 3000);
		}
	});
	}else if(isStartPress=="true" && isStopPress=="false" ){
		systemMessage("Please stop the recording before submit the test");
		return false;
	}else{
		systemMessage("Please record audio");
		return false;
	}
}
function beginAccuracyTest(){
	 document.getElementById('accuracyTestDiv').style.visibility = "visible";
	 document.getElementById('accuracyTestReady').style.display = "none";	 
	 document.getElementById('accuracy0').style.display = 'block';
	 document.getElementById('submit0').style.display = 'block';
}
function nextPassage(questionCount){
	$("#audioDiv").hide();
	document.getElementById("accuracy" + questionCount).style.display = "none";
	document.getElementById("submit" + questionCount).style.display = "none";
	questionCount++;
	if (document.getElementById("accuracy" + questionCount) != null) {
		document.getElementById("accuracy" + questionCount).style.display = "";
		document.getElementById("submit" + questionCount).style.display = "";
	}
}