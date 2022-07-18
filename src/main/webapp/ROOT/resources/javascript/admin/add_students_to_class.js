/**
 * 
 */

$(document).ready(function() {
	$('#returnMessage').fadeOut(4000);
});
function addStudent(thisVal, gClassId, gLevelId, sNo) {

	var studentId = thisVal.value;
	var csId = document.getElementById('csId').value;
	$.ajax({
		url : "AddStudent.htm",
		data : "studentId=" + studentId + "&gClassId=" + gClassId
				+ "&gLevelId=" + gLevelId + "&csId=" + csId,
		success : function(data) {
			systemMessage(data);
			if (data == "Added") {
				document.getElementById('add:' + sNo).disabled = true;
				document.getElementById('remove:' + sNo).disabled = false;
			} else {
				document.getElementById('add:' + sNo).checked = false;
			}
		}
	});

}

function removeStudent(thisVal, gClassId, sNo) {

	var csId = document.getElementById('csId').value;
	var studentId = thisVal.value;
	$.ajax({
		url : "RemoveStudent.htm",
		data : "studentId=" + studentId + "&gClassId=" + gClassId
				+ "&csId=" + csId,// + "&studentObj=" + studentObj,
		success : function(data) {
			systemMessage(data);
			if (data == "Removed") {
				document.getElementById('add:' + sNo).checked = false;
				document.getElementById('add:' + sNo).disabled = false;
				document.getElementById('remove:' + sNo).disabled = true;
				document.getElementById('remove:' + sNo).checked = false;
			} else {
				document.getElementById('remove:' + sNo).checked = false;
			}
		}
	});
}

$(document).ready(function() {
	$("#loading-div-background").css({
		opacity : 0.8
	});
});
function getStudentList() {
	var gradeId = dwr.util.getValue('gradeId');
	var classId = dwr.util.getValue('classId');
	var csId = dwr.util.getValue('csId');
	if (csId != 'select') {
		$("#loading-div-background").show();
		$.ajax({
			type : "GET",
			url : "loadStudentList.htm",
			data : "gradeId=" + gradeId + "&classId=" + classId + "&csId="+ csId,
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

function loadScheduledClasse() {
	clearDiv();
	var gradeId = dwr.util.getValue('gradeId');
	var classId = dwr.util.getValue('classId');
	$.ajax({
		type : "GET",
		url : "getScheduledClassList.htm",
		data : "gradeId=" + gradeId + "&classId=" + classId,
		success : function(response) {
			var scheduledClasses = response.scheduledClasses;
			$("#csId").empty();
			$("#csId")
					.append(
							$("<option></option>").val("select").html(
									'Select Section'));
			$.each(scheduledClasses, function(index, value) {
				$("#csId").append(
						$("<option></option>").val(value.csId)
								.html(value.section.section));
			});
		}
	});
}