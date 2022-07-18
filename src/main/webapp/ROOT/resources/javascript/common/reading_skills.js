/**
 * 
 */

function getAdminRSDashboards(){
	var csId = $("#csId").val();
	if(csId > 0){
		$.ajax({
			type : "GET",
			url : "getAdminRSDashboards.htm",
			data : "csId=" + csId ,
			success : function(response) {
				$("#contentDiv").html(response);
			}
		});
	}
	else{
		alert("Select a section");
		return false;
	}
}

function getParentRSDashboards(){
	var studentId = $("#studentRegId").val();
	if(studentId > 0){
		$.ajax({
			type : "GET",
			url : "getParentRSDashboards.htm",
			data : "studentRegId=" + studentId,
			success : function(response) {
				$("#contentDiv").html(response);
			}
		});
	}
	else{
		alert("Select a student");
		return false;
	}
}