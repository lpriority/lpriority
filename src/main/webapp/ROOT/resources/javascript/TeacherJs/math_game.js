function beginGame(){
	var studentAssignmentId = $("#studentAssignmentId").val();
	var gameLevelId = $("#gameLevelId").val();	
	var mathGearId = $("#mathGearId").val();	
	gameLevelId = Number.parseInt(gameLevelId);
	$("#loading-div-background1").show();
	if(gameLevelId > 0 && mathGearId > 0){		
		$.ajax({
			type : "GET",				
			url : "gameLevel.htm",
			data : "studentAssignmentId="+studentAssignmentId+"&mathGearId="+mathGearId+"&gameLevelId="+gameLevelId,
			async: true,
			success : function(response) {
				$("#loading-div-background1").hide();
				$('#mathGameDiv').empty();  
				$("#mathGameDiv").html(response);
			}
		});
	}else{	
		submitGearGameTestCallback = function(response){
			if(response){
				var testCount = document.getElementById("mathGameAssignRow").value;
				$('#row'+testCount).remove();
				$("#loading-div-background1").hide();
				$(".ui-dialog-titlebar-close").trigger('click'); 
				$('#mathGameDiv').empty();
			}
		}
		mathGameService.submitGearGameTest(studentAssignmentId,{
 			callback : submitGearGameTestCallback
 		});		
	}	
}
function playCompletedSound(){
	var audioElement = document.createElement('audio');
    audioElement.setAttribute('src', 'loadCommonFile.htm?commonFilePath=buzzer_sounds/complete.wav');
    audioElement.setAttribute('autoplay', 'autoplay');
    audioElement.load()
    $.get();
    audioElement.addEventListener("load", function() {
    audioElement.play();
    }, true);
}
function playCorrectSound(){
	var audioElement = document.createElement('audio');
    audioElement.setAttribute('src', 'loadCommonFile.htm?commonFilePath=buzzer_sounds/green.wav');
    audioElement.setAttribute('autoplay', 'autoplay');
    audioElement.load()
    $.get();
    audioElement.addEventListener("load", function() {
    audioElement.play();
    }, true);
}
function playErrorSound(){
	var audioElement = document.createElement('audio');
    audioElement.setAttribute('src', 'loadCommonFile.htm?commonFilePath=buzzer_sounds/red.wav');
    audioElement.setAttribute('autoplay', 'autoplay');
    audioElement.load()
    $.get();
    audioElement.addEventListener("load", function() {
    audioElement.play();
    }, true);
}