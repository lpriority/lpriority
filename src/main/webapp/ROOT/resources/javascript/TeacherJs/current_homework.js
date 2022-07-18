/**
 * 
 */

function getAssignedHomeworks(){
	var gradeId = $('#gradeId').val();
	var classId = $('#classId').val();
	var csId = $('#csId').val();
	var lessonId= $('#lessonId').val();
	var stat=$('#stat').val();
	$("#CurrentHomeWorks").empty();
	$('#HomeWorkQuestions').empty();
	if(gradeId != 'select' && classId != 'select' && csId != 'select' && lessonId != 'select'){
		 $("#loading-div-background").show();
		$.ajax({  
			url : "getTeacherCurrentHomeworks.htm", 
		    data: "gradeId="+gradeId+"&classId="+classId+"&csId="+csId+"&lessonId="+lessonId+"&stat="+stat,
		    success: function(response) {
		    	 $("#CurrentHomeWorks").empty(); 
		    	 $("#CurrentHomeWorks").html(response);
		    	 $("#loading-div-background").hide();
		    }
		}); 
	}
	
}
function getHomeworkQuestions(assignmentId,assignmentTypeId,index){
	 $("#HomeWorkQuestions").empty(); 
	 $("#loading-div-background").show();
	$.ajax({  
		url : "getQuestionsByAssignmentId.htm", 
	    data: "assignmentId="+assignmentId+"&assignmentTypeId="+assignmentTypeId,
	    success: function(response) {
	    	  	 var checks=document.getElementsByName('checkAssigned');
	    	    var a=0;
	    	    for(;a<checks.length;a++)
	    	    {
	    	        if(index!=a){
	    	            document.getElementById('checkAssigned'+a).checked=false;
	    	        }
	    	    }	    	
	    	$("#HomeWorkQuestions").html(response);
	    	 $("#loading-div-background").hide();
	    }
	}); 	
}
