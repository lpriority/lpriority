function getSubCriterias() {
		var criteriaId = $('#criteriaId').val();
		if(criteriaId != "select"){
			$.ajax({
				type : "GET",
				url : "getLegendSubCriterias.htm",
				data : "criteriaId=" + criteriaId,
				success : function(response2) {
					var legendSubCriteriaList = response2.legendSubCriteriaList;
					$("#subCriteriaId").empty(); 
					$("#subCriteriaId").append(
							$("<option></option>").val('select')
									.html('Select SubCriteria'));
					$.each(legendSubCriteriaList, function(index, value) {
						$("#subCriteriaId").append(
								$("<option></option>").val(value.legendSubCriteriaId)
										.html(value.legendSubCriteriaName));
					});
		
				}
			});
		}
	}

function createRubricForSubCriteria(tab){
	 var criteriaId=$('#criteriaId').val();
	 if(criteriaId== '' || criteriaId==null || criteriaId=='select'){
		 alert("Please select the Criteria");
        $('#criteriaId').next().show();
        return false;
     }
	
	 var subCriteriaId=$('#subCriteriaId').val();
	 if(subCriteriaId== '' || subCriteriaId==null || subCriteriaId=='select'){
		 alert("Please select the SubCriteria");
        $('#subCriteriaId').next().show();
        return false;
     }
	 var rubricScore=$('#rubricScore').val();
	 if(rubricScore== '' || rubricScore==null || rubricScore=='select'){
		 alert("Please select the RubricScore");
        $('#rubricScore').next().show();
        return false;
     }
	 var rubricDesc=$('#rubricDesc').val();
	 if(rubricDesc== '' || rubricDesc==null){
		 alert("Please enter the Rubric Description");
        $('#rubricDesc').next().show();
        return false;
     }
	 rubricDesc = encodeURIComponent(rubricDesc);
	 $.ajax({  
		 	type : "POST",
			url : "createLIRubric.htm", 
	        data: "criteriaId=" + criteriaId+"&subCriteriaId="+subCriteriaId+"&rubricScore="+rubricScore+"&rubricDesc="+rubricDesc+"&tab="+tab,
	        success : function(data) { 
	        	systemMessage(data);
	        	$('#criteriaId').val('select');
	            $('#subCriteriaId').empty();
	            $('#subCriteriaId').val('select');
	            $('#rubricScore').val('select');
	            $('#rubricDesc').val('');
	            
	        }  
	    }); 
}

function getRubricValues(){
	
	var criteriaId=$('#criteriaId').val();
	
	 if(criteriaId== '' || criteriaId==null || criteriaId=='select'){
		 alert("Please select the Criteria");
       $('#criteriaId').next().show();
       return false;
    }
	 var subCriteriaId=$('#subCriteriaId').val();
	 if(subCriteriaId== '' || subCriteriaId==null || subCriteriaId=='select'){
		 alert("Please select the SubCriteria");
       $('#subCriteriaId').next().show();
       return false;
    }
	 $.ajax({  
			url : "getRubricValuesBySubCriteriaId.htm", 
	        data: "criteriaId=" + criteriaId+"&subCriteriaId="+subCriteriaId,
	        success : function(data) { 
	        	
	        	$("#showRubric").fadeIn();
	        	$("#showRubric").html(data);
	        	            
	        }  
	    }); 
}
function assignRubricValues(){
	if(document.getElementById('isValid').value == 4){
		var subCriteriaId=$('#subCriteriaId').val();
		 if(subCriteriaId== '' || subCriteriaId==null || subCriteriaId=='select'){
			 alert("Please select the SubCriteria");
	      $('#subCriteriaId').next().show();
	      return false;
	   }
		var legendIds = new Array();
		var subRubrics = document.getElementsByName('chkbox');
		for(var i = 0; i < subRubrics.length; i++){
		  //if(subRubrics[i].checked){ 
			  	legendIds.push(subRubrics[i].value);
		  // }
	   }
		var gradeId=$('#gradeId').val();
		if(gradeId== '' || gradeId==null || gradeId=='select'){
			 alert("Please select the grade");
	     $('#gradeId').next().show();
	     return false;
	  }
	  $.ajax({  
			url : "assignLIRubricToGrade.htm", 
	        data: "subCriteriaId="+subCriteriaId+"&legendIds="+legendIds+"&gradeId="+gradeId,
	        success : function(response) { 
	        	    systemMessage(response);
	        	    $('#criteriaId').val('select');
		            $('#subCriteriaId').empty();
		            $('#subCriteriaId').val('select');
		           
	        }  
	  }); 
	}
	else{
		alert("Cannot be assigned. Create the complete rubric to proceed");
		return false;
	}
	
}
function getRubricBySubcriteriaId(subCriteriaId,gradeId,indx,editStatus,type,trimesterId,teacherRegId){
	$.ajax({
		type : "POST",				
		url : "getRubricByGradeId.htm",
		data : "subCriteriaId="+subCriteriaId+"&gradeId="+gradeId+"&indx="+indx+"&editStatus="+editStatus+"&type="+type+"&trimesterId="+trimesterId+"&teacherRegId="+teacherRegId,
		async: true,
		success : function(response) {
			var screenWidth = $( window ).width() - 10;
			var screenHeight = $( window ).height() - 10;
			$('#rubricDialog').empty();  
			$("#rubricDialog").html(response);
			$("#rubricDialog").dialog({width: screenWidth, height: screenHeight,modal: true,
				open:function () {
					$(".ui-dialog-titlebar-close").show();
				},
				close: function(event, ui){
					$(this).empty();  
					$('#rubricDialog').dialog('destroy');	
			    }
			});		
			$("#rubricDialog").dialog("option", "title", "Rubric");
			$("#rubricDialog").scrollTop("0");
			 //$("#loading-div-background").hide();
		}
	});
}


function uploadFiles(subCriteriaId,studentId,learningIndicatorId,ts,stat){
	var learnIndiValueId = document.getElementById('learnIndiValueId'+ts).value;
	$("#loading-div-background2").show();
	$.ajax({
			type : "POST",
			url : "getLEFileBySubCriteriaId.htm",
			data : "subCriteriaId="+subCriteriaId+"&studentId="+studentId+"&learningIndicatorId="+learningIndicatorId+"&learnIndiValueId="+learnIndiValueId+"&index="+ts+"&stat="+stat,
			success : function(response) {
				var dailogContainer = $(document.getElementById('LEFileDailog'));
				var screenWidth = $( window ).width() - 220;
				var screenHeight = $( window ).height() - 220;
				$('#LEFileDailog').empty();  
				$(dailogContainer).append(response);
				$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
					  $(".ui-dialog-titlebar-close").show();
				},close: function (ev, ui) { 
					$(this).dialog('close');  
					$(this).empty()
				},
				dialogClass: 'myTitleClass'
				});		
				$(dailogContainer).dialog("option", "title", "Learning Indicator Files");
				$(dailogContainer).scrollTop("0");	
				$("#loading-div-background2").hide();
			}
		});
}

function lefileSave(elem,learningIndicatorId) {	
	
	var formObj = document.getElementById("perFileForm");
	var formURL = formObj.action;
	var formData = new FormData(formObj);
	var subCriteriaId=document.getElementById("subCriteriaId").value;
    var studentId=document.getElementById("createdBy").value;
    var learnIndiValueId=document.getElementById("learnIndiValueId").value;
    var index=document.getElementById("index").value;
    var stat=document.getElementById("upStatus").value;
   	$.ajax({
		url: formURL,
		type: 'POST',
		data: formData,
		mimeType:"multipart/form-data",
		contentType: false,
		cache: false,
		processData:false,
		success: function(data, textStatus, jqXHR){
			alert("File uploaded Successfully");
			//$('#LEFileDailog').dialog('close');	
			
			if ($("#LEFileDailog").hasClass("ui-dialog-content")){
				$('#LEFileDailog').dialog('close');
				
			}
//			
//		$.ajax({
//				type : "POST",
//				url : "getLEFileBySubCriteriaId.htm",
//				data : "subCriteriaId="+subCriteriaId+"&studentId="+studentId+"&learningIndicatorId="+learningIndicatorId+"&learnIndiValueId="+learnIndiValueId+"&index="+index+"&stat="+stat,
//				success : function(response) {
//					var dailogContainer = $(document.getElementById('LEFileDailog'));
//					var screenWidth = $( window ).width() - 10;
//					var screenHeight = $( window ).height() - 10;
//					$('#LEFileDailog').empty();  
//					$(dailogContainer).append(response);
//					$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
//						  $(".ui-dialog-titlebar-close").show();
//					},close: function (ev, ui) { 
//						$(this).dialog('close');  
//						 	
//					} });		
//					$(dailogContainer).dialog("option", "title", "LE Files");
//					$(dailogContainer).scrollTop("0");	
//					//$("#loading-div-background").hide();
//				}
//			});
		}
	});		
}
function getRubricDesc(tab){
	var criteriaId=$('#criteriaId').val();
	 if(criteriaId== '' || criteriaId==null || criteriaId=='select'){
		 alert("Please select the Criteria");
        $('#criteriaId').next().show();
        return false;
     }
	 var subCriteriaId=$('#subCriteriaId').val();
	 if(subCriteriaId== '' || subCriteriaId==null || subCriteriaId=='select'){
		 alert("Please select the SubCriteria");
        $('#subCriteriaId').next().show();
        return false;
     }
	 var rubricScore=$('#rubricScore').val();
	 if(rubricScore== '' || rubricScore==null || rubricScore=='select'){
		 alert("Please select the RubricScore");
        $('#rubricScore').next().show();
        return false;
     }
	 $.ajax({  
		url : "getRubricDesc.htm", 
		type: 'POST',
		data: "criteriaId=" + criteriaId+"&subCriteriaId="+subCriteriaId+"&rubricScore="+rubricScore,
        success : function(data) {
	    	if(tab=='createLeRubric'){
	    		if((data.rubricDesc!="" && data.rubricDesc!=null)){
	        		alert("rebric already created");
	        		$('#rubricDesc').val("");
	        		$('#rubricDesc').prop("disabled", true);
	        	}
	    		else{
	    			$('#rubricDesc').prop("disabled", false);
	    		}
	    	}
	    	else{
	        	if((data.rubricDesc=="" || data.rubricDesc==null)){
	        		alert("no data found");
	        	  $('#rubricDesc').prop("disabled", true);}
	        	else{
	        		 $('#rubricDesc').prop("disabled", false);
	        	   	 $("#rubricDesc").val(data.rubricDesc);
	        	}
	    	}
        }  
	});
}
function setLegendColor(se,colValue,type){
	 var td = document.getElementById('sub'+se);
	  if(colValue==4)
	   td.style.backgroundColor="#f3ce5a";
	  else if(colValue==3)
		  td.style.backgroundColor="#83db81";
	  else if(colValue==2)
		  td.style.backgroundColor="#ffff8c";  
	  else if(colValue==1)
		  td.style.backgroundColor="#fc7171";   
	  else 
		 td.style.backgroundColor="#FFFFFF";
	  if(type=="student")
	     $("#scoreId"+se).val(colValue);
	  else
		  $("#teacherScoreId"+se).val(colValue);
	  //jQuery('#rubricDialog').dialog('close');
		if ($("#rubricDialog").hasClass("ui-dialog-content")){
			$('#rubricDialog').dialog('close');	
			}
}
function saveRubricScore(ts,legend,type)
{       
	var learnIndiValueId = document.getElementById("learnIndiValueId"+ts).value;
		//var legend=document.getElementById("scoreId"+ts).value; 
	if(type=="student"){
	$.ajax({  
			url : "saveStudentSelfScore.htm",
			type : "POST",
 			data: "learnIndiValueId=" + learnIndiValueId + "&legend=" + legend,
			success : function(data) { 
   		}  
		}); 
	}else{
		$.ajax({  
			url : "saveTeacherScore.htm",
			type : "POST",
 		data: "learnValId=" + learnIndiValueId+ "&legend=" + legend,
			success : function(data) { 
			$("#loading-div-background").hide();
   		}  
		}); 	
	}
	}

   
function clearRubricValues(){
	$('#rubricScore').val("select");
	$("#rubricDesc").val("");
}
//remove csId
function getSubCriteriasByCriteriaId(learningIndicatorId,studentId,upStatus,trimesterId,teacherRegId,gradeType){
	$("#loading-div-background1").show();
	 $.ajax({  
			url : "getLearningIndicatorValuesByCriteria.htm", 
			type: 'POST',
			data: "learningIndicatorId=" + learningIndicatorId+"&studentId="+studentId+"&upStatus="+upStatus+"&trimesterId="+trimesterId+"&teacherRegId="+teacherRegId+"&gradeType="+gradeType,
	        success : function(response) {
	        	var dailogContainer = $(document.getElementById('CriteriaDialog'));
				var screenWidth = $( window ).width() - 150;
				var screenHeight = $( window ).height() - 150;
				$('#CriteriaDialog').empty();  
				$(dailogContainer).append(response);
				$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
					  $(".ui-dialog-titlebar-close").show();
				},close: function (ev, ui) { 
					$(this).empty();  
					if ($("#CriteriaDialog").hasClass("ui-dialog-content")){
						$('#CriteriaDialog').dialog('destroy');
					  }
				  if ($("#LEFileDailog").hasClass("ui-dialog-content")){
					$('#LEFileDailog').dialog('destroy');
				  }
					/*$('#CriteriaDialog').dialog('destroy');
					$('#LEFileDailog').dialog('destroy');*/
				},
				dialogClass: 'myTitleClass'
				});		
				$(dailogContainer).dialog("option", "title", "Learning Indicator");
				$(dailogContainer).scrollTop("0");	
				$("#loading-div-background1").hide();
			}
	      
});
}
function submitStudentIOLSection(learningIndicatorId)
{
     	var i=0;
		var notes=document.getElementsByName("notes");
		var legends=document.getElementsByName("scoreId");
		var flag=false;
		
      	for(i=0;i<legends.length;i++){
      		if (notes[i].value == '' || notes[i].value == null) {
      			alert("Please fill all the comment");
     	    	flag=true;
     	    	return false;
     	    }
            if((notes[i].value).length > 1000)
    		{  
    	       systemMessage("The field cannot contain more than 1000 characters!");
    	       document.getElementsByName("notes"+i).focus();
     	       flag=true;
     	       return false;
      			
    		}
      		if (legends[i].value == 'select' || legends[i].value == null) {
     	    	alert("Please select legend value for all the categories");
     	    	flag=true;
     	    	return false;
     	    }
      	}
 	if(!flag){
    	$.ajax({  
     		url : "submitStudentIOLSection.htm",
     		type : "POST",
       		data: "learningIndicatorId=" + learningIndicatorId,
     		success : function(data) {
     			$('#CriteriaDialog').dialog('close');	
     			if(data.leScore>=3)
     				document.getElementById("but"+learningIndicatorId).style.backgroundColor="#83db81";
     			else if(data.leScore>=1.5 && data.leScore<3)
     				document.getElementById("but"+learningIndicatorId).style.backgroundColor="#ffff8c";
     			else if(data.leScore>=1 && data.leScore<1.5)
     				document.getElementById("but"+learningIndicatorId).style.backgroundColor="#fc7171";  
     			else 
     				document.getElementById("but"+learningIndicatorId).style.backgroundColor="#82CAFA";
     			document.getElementById("set"+learningIndicatorId).innerHTML = data.leScore;
         		
     		}  
    	});
 	}
}

function getSubCriteriaValues(learningValuesId,studentId,upStatus,trimesterId,teacherRegId){
	$("#loading-div-background1").show();
	$.ajax({  
 		url : "getLearningIndicatorValuesBySubcriteria.htm",
 		type : "POST",
   		data: "learningValuesId=" + learningValuesId+"&studentId="+studentId+"&upStatus="+upStatus+"&trimesterId="+trimesterId+"&teacherRegId="+teacherRegId,
 		success : function(response) {
 			var dailogContainer = $(document.getElementById('CriteriaDialog'));
			var screenWidth = $( window ).width() - 100;
			var screenHeight = $( window ).height() - 10;
			$('#CriteriaDialog').empty();  
			$(dailogContainer).append(response);
			$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
				  $(".ui-dialog-titlebar-close").show();
			},close: function (ev, ui) { 
				$(this).empty();
				$('#LEFileDailog').dialog('close'); 
				$('#LEFileDailog').dialog('destroy');
				$('#CriteriaDialog').dialog('destroy');
				
			},
			dialogClass: 'myTitleClass'
			});		
			$(dailogContainer).dialog("option", "title", "Learning Indicator");
			$(dailogContainer).scrollTop("0");	
			$("#loading-div-background1").hide();
		}
	
	});
}
function submitStudentIOLLitracy(learningValuesId,learningIndicatorId,ts){
	
	var studentNotes=document.getElementById("notes"+ts).value; 
	var legend=document.getElementById("scoreId"+ts).value; 
	var flag=false;
  
  		if (studentNotes == '' || studentNotes== null) {
  			alert("Please fill all the comment");
 	    	flag=true;
 	    	return false;
 	    }
  		if(studentNotes.length > 1000)
  			{
  			systemMessage('The field cannot contain more than 1000 characters');
  			document.getElementById("notes"+ts).focus();
 	    	flag=true;
 	    	return false;
  			}
  		if (legend == 'select' || legend == null) {
 	    	alert("Please select legend value");
 	    	flag=true;
 	    	return false;
 	    }
  	
	if(!flag){
	$.ajax({  
 		url : "submitStudentIOLLitracy.htm",
 		type : "POST",
   		data: "learningIndicatorId=" + learningIndicatorId+"&learningValuesId="+learningValuesId,
 		success : function(data) {
 			$('#CriteriaDialog').dialog('close');	
 			if(legend>=3)
 				document.getElementById("butt"+learningValuesId).style.backgroundColor="#83db81";
 			else if(legend>=1.5 && legend<3)
 				 document.getElementById("butt"+learningValuesId).style.backgroundColor="#ffff8c";
 			else if(legend>=1 && legend<1.5)
 				 document.getElementById("butt"+learningValuesId).style.backgroundColor="#fc7171";  
 			else 
 			document.getElementById("butt"+learningValuesId).style.backgroundColor="#82CAFA";
 			document.getElementById("sett"+learningValuesId).innerHTML = legend;
     		
 		}  
	});
	}
}

function gradeSudentIOLSection(learningIndicatorId,gradeType,studentId)
{
	var legValues=[],teaComments=[];
	var notes=document.getElementsByName("teacherNotes");
	var legends=document.getElementsByName("teacherScoreId");
	var flag=false;
	var count=legends.length;
	
	for(var i=0;i<legends.length;i++){
		legValues[i]=legends[i].value;
		teaComments[i]=notes[i].value;
		if (notes[i].value == '' || notes[i].value == null) {
			alert("Please fill all the comment");
			flag=true;
			return false;
		}
	   if((notes[i].value).length > 1000)
 		 {  
	     systemMessage("The field cannot contain more than 1000 characters!");
 	     document.getElementsByName("teacherNotes"+i).focus();
  	    	flag=true;
  	    	return false;
   			
 		}
		if (legends[i].value == 'select' || legends[i].value == null) {
			alert("Please select legend value for all the categories");
			flag=true;
			return false;
		}
	}
	
	
 	if(!flag){
 		$.ajax({  
     		url : "gradeStudentIOLSection.htm",
     		type : "POST",
       		data: "learningIndicatorId=" + learningIndicatorId,
     		success : function(data) {
     			//jQuery('#CriteriaDialog').dialog('close');
     			$('#CriteriaDialog').dialog('close');
     			if(gradeType=="part"){
     			if(data.leScore>=3)
     				document.getElementById("but"+learningIndicatorId).style.backgroundColor="#83db81";
     			else if(data.leScore>=1.5 && data.leScore<3)
     				 document.getElementById("but"+learningIndicatorId).style.backgroundColor="#ffff8c";
     			else if(data.leScore>=1 && data.leScore<1.5)
     				 document.getElementById("but"+learningIndicatorId).style.backgroundColor="#fc7171";  
     			else 
     			     document.getElementById("but"+learningIndicatorId).style.backgroundColor="#82CAFA";
     			
     			     document.getElementById("set"+learningIndicatorId).innerHTML = data.leScore;
     			}else{
     				for(var s=0;s<count;s++){
     					document.getElementById("tscore:"+studentId+":"+s).value=legValues[s];
     					document.getElementById("tcomment:"+studentId+":"+s).innerHTML=teaComments[s];
     				}
     			}
         		
     		}  
    	});
 	}
}
function gradeStudentIOLLitracy(learningValuesId,learningIndicatorId,ts){	
	var teacherNotes=document.getElementById("teacherNotes"+ts).value; 
	var legend=document.getElementById("teacherScoreId"+ts).value; 
	var flag=false;
  
  		if (teacherNotes == '' || teacherNotes== null) {
  			alert("Please fill all the comment");
 	    	flag=true;
 	    	return false;
 	    }
  		if(teacherNotes.length > 1000)
  			{
  			systemMessage('The field cannot contain more than 1000 characters!');
  			document.getElementById("teacherNotes"+ts).focus();
 	    	flag=true;
 	    	return false;
  			}
  		if (legend == 'select' || legend == null) {
 	    	alert("Please select teacher score");
 	    	flag=true;
 	    	return false;
 	    }
	if(!flag){
	$.ajax({  
 		url : "gradeStudentIOLLitracy.htm",
 		type : "POST",
   		data: "learningIndicatorId=" + learningIndicatorId+"&learningValuesId="+learningValuesId,
 		success : function(data) {
 			$('#CriteriaDialog').dialog('close');	
 			if(legend>=3)
 				document.getElementById("butt"+learningValuesId).style.backgroundColor="#83db81";
 			else if(legend>=1.5 && legend<3)
 				 document.getElementById("butt"+learningValuesId).style.backgroundColor="#ffff8c";
 			else if(legend>=1 && legend<1.5)
 				 document.getElementById("butt"+learningValuesId).style.backgroundColor="#fc7171";  
 			else 
 			document.getElementById("butt"+learningValuesId).style.backgroundColor="#82CAFA";
 			document.getElementById("sett"+learningValuesId).innerHTML = legend;
     		
 		}  
	});
	}
}
function submitIOLReportCard(iolReportId,studentId,csId,trimesterId)
{
	$.ajax({  
 		url : "checkIOLStatus.htm",
 		type : "POST",
   		data: "&iolReportId="+iolReportId,
 		success : function(data) { 
 			if(data.iolStatus==true){
 				$.ajax({  
 			 		url : "StudentSubmitIOLReportCard.htm",
 			 		type : "POST",
 			   		data: "&iolReportId="+iolReportId+"&studentId="+studentId+"&csId="+csId+"&trimesterId="+trimesterId,
 			 		success : function(data) { 
 			 			$("#monitor").html("<br><br><table align='center'><tr><td><font color=blue>Report Submitted Successfully </font></td></tr></table>");
 						window.scroll(0);
 								     		
 			 		}  
 				});
 			}else{
 				alert("Please Submit All Dials");
 				return false;
 			}
 		}  
	}); 

}
function gradeStudentIOLReportCard(iolReportId){
	$.ajax({  
 		url : "checkIOLGradeStatus.htm",
 		type : "POST",
   		data: "&iolReportId="+iolReportId,
 		success : function(data) { 
 			if(data.iolStatus==true){
   	                  $.ajax({  
 		               url : "TeacherSubmitIOLReportCard.htm",
 		               type : "POST",
   		               data: "&iolReportId="+iolReportId,
 		               success : function(data) { 
 		               $("#rioReportsContainer").html("<br><br><table align='center'><tr><td><font color=blue>Report Submitted Successfully </font></td></tr></table>");
 			           window.scroll(0);
 			
 		              }  
	               });
 			}else{
 				alert("Please Submit All Dials");
 				return false;
 			}
 			}	
 
	});
}
function getStudentIOLReportDates(viewStatus,studId) {
	var studentId;
	var gradeId= $('#gradeId').val();
	var classId= $('#classId').val();
	var csId = $('#csId').val();
	if(studId=="student"){
	studentId = $('#studentId').val();}
	else{
	studentId=studId;
	$('#studentId').val(studId);	
	}
	var tab=$('#tab').val();
	var reportsContainer = $(document.getElementById('reportDatesDiv'));
	$(reportsContainer).empty();
	if (gradeId!='select' && classId!='select' && csId != 'select' && studentId != 'select') {
		$("#loading-div-background").show();
		if(studentId!=0){
		  $('#showTrimester').hide();
		  $.ajax({
			type : "GET",
			url : "getStudentIOLReportDates.htm",
			data : "csId=" + csId + "&studentId=" + studentId+ "&stat="+viewStatus+"&tab="+tab,
			success : function(response) {
				$(reportsContainer).append(response);
				$("#loading-div-background").hide();
			}
		});
		}else{
			$('#showTrimester').show();
			loadTrimesters();
		}
	}else 
		SystemMessage("Please Select All Filters");
	}
function loadTrimesters(){
	$("#trimesterId").empty(); 
	$("#trimesterId").append(
			$("<option></option>").val('select')
					.html('Select Trimester'));
	$.ajax({
		type : "GET",
		url : "getTrimesters.htm",
		success : function(response) {
			var trimesters = response.trimesters;
			$.each(trimesters, function(index, value) {	
				if(value.trimesterId!=4){
				$("#trimesterId").append(
						$("<option></option>").val(value.trimesterId).html(
								value.trimesterName));
				}
			});
			$("#loading-div-background").hide();
   }
	});
}

function getWholeClassIOLReports(viewStatus){
	var gradeId= $('#gradeId').val();
	var classId= $('#classId').val();
	var csId = $('#csId').val();
	var studentId = $('#studentId').val();
	var tab=$('#tab').val();
	var trimesterId=$('#trimesterId').val();
	var reportsContainer = $(document.getElementById('reportDatesDiv'));
	$(reportsContainer).empty();
	if (gradeId!='select' && classId!='select' && csId != 'select' && studentId != 'select' && trimesterId!='select') {
		$("#loading-div-background").show();
		  $.ajax({
			type : "GET",
			url : "getStudentIOLReportDates.htm",
			data : "csId=" + csId + "&studentId=" + studentId+ "&stat="+viewStatus+"&tab="+tab,
			success : function(response) {
				$(reportsContainer).append(response);
				$("#loading-div-background").hide();
			}
		});
		
	}else 
		SystemMessage("Please Select All Filters");
	    return false;
	}


function getStudentReportCard(iolReportId,studentId,reportDate,stat,csId){
	$("#loading-div-background").show();
	var tab=$('#tab').val();
	$.ajax({
		type : "GET",
		url : "createStudentIOLReportCard.htm",
		data : "studentId=" + studentId+"&iolReportId="+iolReportId+"&reportDate="+reportDate+"&stat="+stat+"&csId="+csId+"&tab="+tab,
		success : function(response) {
				var reportContainer = $(document.getElementById('rioReportsContainer'));
				var screenWidth = $( window ).width() - 75;
				var screenHeight = $( window ).height() - 75;
				$('#rioReportsContainer').empty(); 
				$(reportContainer).append(response);
				$(reportContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
					  $(".ui-dialog-titlebar-close").show();
				},
				close: function( event, ui ) { 
					  $(this).dialog('destroy');
					  if ($("#CriteriaDialog").hasClass("ui-dialog-content")){
							$('#CriteriaDialog').dialog('destroy');
						  }
					  if ($("#LEFileDailog").hasClass("ui-dialog-content")){
						$('#LEFileDailog').dialog('destroy');
					  }
				 },
				 dialogClass: 'myTitleClass'
				});		
				$(reportContainer).dialog("option", "title", "Learning Indicator");
				$(reportContainer).scrollTop("0");
				$("#loading-div-background").hide();

		}});
}

