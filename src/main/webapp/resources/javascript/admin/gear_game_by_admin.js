function titleValidation(){
	var csIds = $('#csId').val();
	var title = $('#title').val();
	var usedFor = $('#usedFor').val();
	if(csIds){
		if(csIds.length > 0){
			if(title != ""){
				$.ajax({
					type : "GET",
					url : "validateTitles.htm",
					data : "csIds=" + csIds + "&title="+title+ "&usedFor="+usedFor,
					success : function(response) {
						if(!response.status){							
							systemMessage("This Title already existed");	
							document.getElementById("title").value = "";
							$('#title').focus();
						}
					}
				});
			}		
		}else{
			alert("Please select Sections");	
			return false;
		}
	}else{
		alert("Please select Sections");	
		return false;
	}
}

function assignGameToSections(){
	var gradeId=$('#dueDate').val();
	var classId=$('#classId').val();
	var dueDate = $('#dueDate').val(); 
	var title = $('#title').val(); 
	var instructId = $('#instructId').val();
	var csIds = $('#csId').val();
	if(!csIds){
	   alert("Please Select Sections !!");
	   return false;
   	}else if(title && dueDate && instructId ){
		$.ajax({
			url : "assignGameByAdmin.htm",
			type: 'POST',
			data: "dueDate="+dueDate+"&title="+title+"&csIds="+csIds+"&instructId="+instructId,
			success : function(data){
		      $('#dueDate').val(''); 
	    	  $('#title').val(''); 
	    	  $('#instructId').val('');
	    	  $('#select_all').prop('checked', false);
	    	  getClassSections();
	    	  systemMessage(data);
			}
		});
	}else{
		alert("Please fill all mandatory fields");
	}	
}

function showMathGameResults(){
	var assignmentId = $('#titleId').val();
	var assignmentTypeId = window.parent.document.getElementById('assignmentTypeId').value;
	if(!assignmentTypeId){
		assignmentTypeId = dwr.util.getValue('assignmentTypeId');}
	if(assignmentId != 'select'){
		$("#loading-div-background").show();
		$.ajax({
			url : "getMathGameResults.htm",
			data: "assignmentId="+assignmentId+"&assignmentTypeId="+assignmentTypeId,
			success : function(data){
				$("#loading-div-background").hide();
				$("#studentMathGameDetailsPage").html(data); 
			}
		});
	}else{
		$("#studentMathGameDetailsPage").html(""); 
	}
}

function getMathAssignedDates(callback){
	var csId = $('#csId').val();
    if(csId !=  'select'){
		var usedFor = $('#usedFor').val();
		var page = dwr.util.getValue('page');
		$.ajax({
			type : "GET",
			url : "getMathAssignedDates.htm",
			data : "csId=" + csId + "&usedFor=" + usedFor+ "&page=" + page,
			success : function(response2) {
				var teacherAssignedDates = response2.teacherAssignedDates;
				$("#assignedDate").empty();
				$("#assignedDate").append(
						$("<option></option>").val('select').html('select date'));
				$.each(teacherAssignedDates, function(index, value) {
					$("#assignedDate").append(
							$("<option></option>").val(getDBFormattedDate(value.dateAssigned)).html(
									getFormattedDate(value.dateAssigned)));
				});
				if(callback)
				  callback();
			}
		});

	}else{
		$("#assignedDate").empty();
		$("#assignedDate").append(
				$("<option></option>").val('select').html('select'));
		$("#titleId").empty();
		$("#titleId").append(
				$("<option></option>").val('select').html('select'));
		$('#studentDetailsPage').html('');
	}
}