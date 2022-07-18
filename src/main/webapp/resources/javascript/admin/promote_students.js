/**
 * 
 */
$(document).ready(function () {
	 $('#returnMessage').fadeOut(3000);
	 $("#loading-div-background").css({ opacity: 0.8 });
});
	function loadGradeStudents() {
		clearDiv();
		var gradeId = dwr.util.getValue('gradeId');
		if(gradeId > 0){	
			$("#loading-div-background").show();
			$.ajax({
				type : "GET",
				url : "getGradedStudentList.htm",
				data : "gradeId=" + gradeId,
				success : function(response) {
					$("#subViewDiv").html(response);
					$("#loading-div-background").hide();
				}
			});	
		}
	}
	function clearDiv() {
		var subViewDiv = $(document.getElementById('subViewDiv'));
		$(subViewDiv).empty();
	}
	
	function validationCheck() {
		var gradeId = dwr.util.getValue('gradeId');
		var promoteId = dwr.util.getValue('promoteId');
		if(promoteId == ""){
			systemMessage("Please Select Promote Grade");
			document.getElementById("promoteId").focus();
			return false;
		}
		if(gradeId == promoteId){
			systemMessage("Grade and Promote Grade should not be equal");
			document.getElementById("promoteId").focus();
			return false;
		}
		var students = document.getElementsByName("studentId");
		var checkStatus = false;
		for(var i = 0; i < students.length; i++){
			if(students[i].checked == true){
				checkStatus = true;
			}
	    }	
		if(!checkStatus){
			alert("Please select atleast one student to promote.");
			return false;
		}
		if(confirm("Conform to Promote Student Grade?",function(status){
			if(status){
				$("#loading-div-background").show();
				return true;
		    } else {
		        return false;
		    }
		})); 
	}
	
	function checkAllStudents(){
		var students = document.getElementsByName("studentId");
		for(var i = 0; i < students.length; i++){
			students[i].checked = true;
	    }
	}