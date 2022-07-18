function getFluencyAndAccuracyQuestions(assignmentId,studentAssignmentId,assignmentTypeId){
		$('#StuBenchmarkQuestionsList').html("");
		$('#getCompletedTestQuestions').html("");
	if(assignmentTypeId == 8){
		$.ajax({
				url : "getStudentBenchmarkQuestions.htm",
				data: "studentAssignmentId="+studentAssignmentId + "&assignmentId="+assignmentId+"&assignmentTypeId="+assignmentTypeId,
				success : function(data) {
					$('#getCompletedTestQuestions').html(data);
					
				}
			}); 	
		}
		else{	
			$.ajax({
				//url : "getAccuracyTestResults.htm",
				url: "getStudentBenchmarkQuestions.htm",
				data: "studentAssignmentId="+studentAssignmentId + "&assignmentId="+assignmentId+"&assignmentTypeId="+assignmentTypeId,
				success : function(data) {
					$('#getCompletedTestQuestions').html(data);
				
				}
			}); 	
		}
}