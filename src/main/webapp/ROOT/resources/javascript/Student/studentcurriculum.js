/**
 * 
 */
	function getStudentAssignedCurriculum(){
		var csId = document.getElementById('csId').value;
		var assignedCurriculumDiv = $(document.getElementById('curriculumDiv'));
		$(assignedCurriculumDiv).empty();
		if(csId && csId != "select"){
			$("#loading-div-background").show();
			$.ajax({
				type : "GET",
				url : "getAssignedCurriculum.htm",
				data : "csId=" + csId,
				success : function(response) {
					$(assignedCurriculumDiv).append(response);
					$("#loading-div-background").hide();
				}
			}); 	
		}
	}
	function playBench(thisVar){
		var audio = new Audio(thisVar.value);
		audio.play();
	}