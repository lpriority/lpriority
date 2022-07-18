/**
 * 
 */
function showPermissions(thisVal) {
	enableEditor();
	if(thisVal.checked == false){
		document.getElementById("permId").style.display = "none";
		return;
	}	
	var assignedTaskId = document.getElementById("assignedTaskId").value;
	performanceService.getGroupPerformanceTemp(assignedTaskId, {
		callback : showCallBack
	});
}

function showCallBack(list) {
	if (list != null) {
		$('#permId').show();
		for(var i=0;i<list.length;i++){
			document.getElementById("selfScore"+i).value = list[i].total;	
			if(list[i].permissionStatus == "accepted"){
				document.getElementById("checkId"+i).checked=true;
			}
		}

	} else {
		alert("No data found");
	}
}

function savePermissions(thisVal) {	
	var assignedTaskId = document.getElementById("assignedTaskId").value;
	var pGroupStudentsId = document.getElementById("pGroupStudentId").value;
	var status = null;
	if(thisVal.checked == true){
		status = "accepted";
	}
	performanceService.submitPermission(assignedTaskId, pGroupStudentsId, status, {
		callback : permissionCallBack
	});
}

function permissionCallBack(list) {
	//Nothing here
}

function selectRubric(idName, score, position, totalRub){
	
	if(document.getElementById(idName+position).style.color == 'red'){
		score = 0;
	}
	var rubrics = document.getElementsByName(idName);
	for(var i = 0; i < rubrics.length; i++) {
		rubrics[i].style.color = 'black';	
	}
	if(score > 0){		
		document.getElementById(idName+position).style.color = 'red';
	}
	
	for(var j=1;j<totalRub;j++){
		var dimCount = 1;
		if (document.getElementById("dimension2")) {
			dimCount = 4;
		}
		var totalVal = document.getElementById("total"+j);
		totalVal.value = 0;
		for(var k=1;k<=dimCount;k++){
			var scoreVal = document.getElementById("score"+j).value;	
			if(document.getElementById("dimension"+k+j).style.color == 'red'){
				totalVal.value = parseInt(totalVal.value) + parseInt(scoreVal);
			}
		}
	}
			
	document.getElementById(idName).value = score;
	var dim1=0,dim2=0,dim3=0,dim4= 0;
	dim1 = document.getElementById("dimension1").value;
	
	if (document.getElementById("dimension2")) {
		dim2 = document.getElementById("dimension2").value;
		dim3 = document.getElementById("dimension3").value;
		dim4 = document.getElementById("dimension4").value;
	}
 	document.getElementById("total").value = parseInt(dim1)+parseInt(dim2)+parseInt(dim3)+parseInt(dim4);
 	if(globalTotal != document.getElementById("total").value){
 		var rubricValId = document.getElementById("rubricValuesId").value; 
 		var totalScore = document.getElementById("total").value;
 		var taskId = document.getElementById("assignedTaskId").value;
 		var pGroupStudentId = document.getElementById("pGroupStudentId");
 		if(pGroupStudentId){
 			pGroupStudentId = pGroupStudentId.value;
 			enableEditor();
 			rubricValId =0;
 			document.getElementById("showPerId").checked=false; 
 			document.getElementById("permId").style.display = "none";
 		}else{
 			pGroupStudentId=0;
 		}
 		$.ajax({
			type : "POST",
			url : "autoRubricSave.htm",
			data : "idName=" + idName + "&score="+score +"&totalScore="+totalScore + "&taskId="+ taskId + "&rubricValId="+ rubricValId
					+ "&pGroupStudentId="+ pGroupStudentId,
			success : function(response) {
				document.getElementById("rubricValuesId").value = response.rubricValueId;
			}
		});
 		
 	}
 	globalTotal = document.getElementById("total").value;
	
}

function drawFlowChart(){
	enableEditor();
    var url="https://www.draw.io/";
    //window.open(""+url+"", "", "scrollbars,width=500,height=300");
    $.winOpen({url: 'https://www.draw.io/', height: 600, width: 600 });
}

function printPage(){
	enableEditor();
	window.print();
}

function fileSave(elem) {	
	enableEditor();
	var formObj = document.getElementById("perFileForm");
	var formURL = formObj.action;
	var formData = new FormData(formObj);
	$.ajax({
		url: formURL,
		type: 'POST',
		data: formData,
		mimeType:"multipart/form-data",
		contentType: false,
		cache: false,
		processData:false,
		success: function(data, textStatus, jqXHR){
			var x = document.getElementById("fileId");
			var file = x.files[0];
			document.getElementById("uploaded").innerText= file.name;
		}
	});		
}	

function stopIt(audioContent,playerId){
	stop(audioContent, playerId);
	setTimeout(function(){
	var audio = document.getElementById(audioContent).value;
	var createdBy = document.getElementById("createdBy").value;
	var assignedFileTaskId = document.getElementById("assignedFileTaskId").value;
	var fd = new FormData();
	fd.append('data', audio);
	fd.append('createdBy', createdBy);
	fd.append('assignedFileTaskId', assignedFileTaskId);
	$.ajax({
		type: 'POST',
		url: 'audioSave.htm',
		data: fd,
		processData: false,
		contentType: false
	}).done(function(data) {
	});
	},3000);
}

function autoSave(id,text,chat){

	if(id == ""){ //editors[id] == text || 
		return;
	}
	var taskId = document.getElementById("assignedTaskId").value;
	var pGroupStudentId = document.getElementById("pGroupStudentId");
	if(pGroupStudentId){
		pGroupStudentId = pGroupStudentId.value;
	}else{
		pGroupStudentId=0;
	}
	editors[id] = text;
	var fd = new FormData();
	fd.append('id', id);
	fd.append('data', text);
	fd.append('taskId', taskId);
	fd.append('perGroupStudentId', pGroupStudentId);
	fd.append('chat', chat);
	$.ajax({
		type: 'POST',
		url: 'autoSave.htm',
		data: fd,
		processData: false,
		contentType: false
	}).done(function(data) {
	});
}

function enableEditor(){
	tinyMCE.get("writing").getBody().setAttribute('contenteditable', true);
	tinyMCE.get("uploadarea").getBody().setAttribute('contenteditable', true);
	tinyMCE.get("calculations").getBody().setAttribute('contenteditable', true);
}

function refreshPage(){
	var sAssignmentId = document.getElementById("studentAssignmentId").value;
	var assignmentTypeId = 13;
	var createdBy = document.getElementById("createdBy").value;
	$.ajax({
		type : "GET",
		url : "getGroupPerformanceTest.htm",
		data : "studentAssignmentId=" + sAssignmentId + "&assignmentTypeId="+assignmentTypeId + "&createdBy="+createdBy,
		success : function(response) {
			var dailogContainer = $(document.getElementById('performanceDailog'));
			var screenWidth = $( window ).width() - 10;
			var screenHeight = $( window ).height() - 10;
			$('#performanceDailog').empty();  
			$(dailogContainer).append(response);
//			$(dailogContainer).dialog({width: screenWidth, height: screenHeight,open:function () {
//				  $(".ui-dialog-titlebar-close").show();
//			} });		
			$(dailogContainer).dialog("option", "title", "Group Performance Test");
			$(dailogContainer).scrollTop("0");				
		}
	});
}

function beforeSubmit(){
	if(!checkSubmit()){
		return false;
	}
	var checkPerm = document.getElementsByName('checkPerm');
	var selfScore = document.getElementsByName('selfScore');
	var score = 0;
	if(selfScore.length > 0){
		score = selfScore[0].value;
	}
	for(var i=0;i<checkPerm.length;i++){
		if(checkPerm[i].checked == false){
			alert("Please check submit permissions");
			return false;
	    }
		if(score != selfScore[i].value){
			alert("Please check self scores should be equal");
			return false;
	    }
		
	}
	return true;
}

function checkSubmit(){
	var ruType=document.getElementById("ruType").value;
	var total = document.getElementById('total').value;
	
	if(total>0){
		if(ruType==3 || ruType==4){
			var dimension1=document.getElementById("dimension1").value;
			var dimension2=document.getElementById("dimension2").value;
			var dimension3=document.getElementById("dimension3").value;
			var dimension4=document.getElementById("dimension4").value;	
		
			if(dimension1>0 && dimension2>0 && dimension3>0 && dimension4>0){
				return true;
			}
			else
			{
				alert("Please select atleast one score for all criteria");
				return false;
			}
	 }else{
		 return true;
	 }
   }
	else{
		alert("Please fill self assessment score");
		return false;
	}
}
function addStudentsToGroup(){
	var minStudents=$('#pgMin').val();
	var maxStudents=$('#pgMax').val();
	systemMessage("min="+minStudents);
	systemMessage("max="+maxStudents);
	var checkedStudentIds = [];
	$('input[name=studentAdd]:checked').map(function() {
		checkedStudentIds.push($(this).val());
		systemMessage("value="+$(this).val());
	});
	systemMessage("length="+checkedStudentIds.length);
	var studentCount=checkedStudentIds.length;
	if(studentCount>=minStudents && studentCount<=maxStudents){		
		systemMessage("Add/Remove Student Succesfully");
	}else if(studentCount<minStudents){
		systemMessage("Please add Minimum "+minStudents+" students");
		return false;
	}else{
		systemMessage("The Maximum students of this group is "+maxStudents);
		return false;
		
	}

}
function checkBeforeSubmit(){
	var ruType=document.getElementById("ruType").value;
	var dimension1=0,dimension2=0,dimension3=0,dimension4=0;
	var total = document.getElementById('total').value;
	var percentage = document.getElementById('percentage').value;
	var comment = document.getElementById('comment').value;
	
	if(comment == ''){
		alert("Please enter a comment");
		 return false;
	}
	if(percentage == ''){
		alert("Please enter percentage score");
		 return false;
	}
	if(total>0){
	 if(ruType==3 || ruType==4)
     {   
		var dimension1=document.getElementById("dimension1").value;
		var dimension2=document.getElementById("dimension2").value;
		var dimension3=document.getElementById("dimension3").value;
		var dimension4=document.getElementById("dimension4").value;	
		if(dimension1>0 && dimension2>0 && dimension3>0 && dimension4>0){
		}else{
			 alert("Please select atleast one score for all criteria");
			 return false;
		}
     }
	}
	else{
		alert("Please fill assessment score");
		return false;
	}
	var le=comment.length;
	  if(le>500){
		  systemMessage("You have exceeded the more than 500 characters ");
	  	 $("#comment").focus();
	       return false;
	
      }
	  var assignTaskId=document.getElementById("assPTaskId").value;
	   $.ajax({  
			url : "gradePerformanceTasks.htm",
			type: "GET",
			data : "percentage="+percentage+"&teacherComment="+comment+"&teacherScore="+total+"&assignPTaskId="+assignTaskId,
			success : function(response) { 
				    $('#clsPerformance').html("");
					$("#clsPerformance").html("<table align='center' width='100%' height='100%'><tr><td width='100%' align='center' style='padding-top:10em'><font color=blue>"+response+"</font></td></tr></table>");
					return false;
									
				}
			});
}
	