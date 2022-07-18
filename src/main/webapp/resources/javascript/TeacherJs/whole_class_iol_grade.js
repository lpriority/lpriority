/*function getSubCriteriasByCriteriaId(){
	console.log("welcome");
	var gradeId=$('#gradeId').val();
	var criteriaId=$('#legendCriteriaId').val();
	
	//$("#loading-div-background1").show();
	 $.ajax({  
			url : "getSubCriteriasByCriteriaId.htm", 
			type: 'POST',
			data: "gradeId="+gradeId+"&legendCriteriaId="+criteriaId,
	        success : function(response) {
	        	var subcriterias = response.legendSubCriterias;
	        	var len=subcriterias.length;
	        	var wid=90/len;
				var str="<table width='90%' border='1' align='center' height='40px'><tr>";
	        	$.each(subcriterias, function(index, value) {					
					str+="<td width='"+wid+"' align='center'>"+value.legendSubCriteriaName+"</td>";
							
				});
	        	str+="</tr></table>";
	        	$("#subcriterias").html(str);
	        	
			}
	      
});
}*/
function getSubCriteriasForWholeClass(){
	var csId=$('#csId').val();
	var gradeId=$('#gradeId').val();
	var trimesterId=$('#trimesterId').val();
	var criteriaId=$('#legendCriteriaId').val();
	
	$("#loading-div-background1").show();
	if (gradeId!='select' && csId != 'select' && trimesterId != 'select' && criteriaId!='select') {
	 $.ajax({  
			url : "getWholeClassLIValuesByCriteria.htm", 
			type: 'POST',
			data: "trimesterId="+trimesterId+"&gradeId="+gradeId+"&csId="+csId+"&criteriaId="+criteriaId,
			success : function(response) {
				$("#subcriterias").html(response);
			}
	        	
	      
      });
	}else {
		$("#subcriterias").html("");
		SystemMessage("Please Select All Filters");
		return false;
	}

}

function getRubricsBySubcriteriaId(subCriteriaId,gradeId,trimesterId,teacherRegId){
	$.ajax({
		type : "POST",				
		url : "getRubricsBySubcriteriaId.htm",
		data : "subCriteriaId="+subCriteriaId+"&gradeId="+gradeId+"&trimesterId="+trimesterId+"&teacherRegId="+teacherRegId,
		async: true,
		success : function(response) {
			var screenWidth = $( window ).width() - 10;
			var screenHeight = $( window ).height() - 10;
			$('#rubricsDialog').empty();  
			$("#rubricsDialog").html(response);
			$("#rubricsDialog").dialog({width: screenWidth, height: screenHeight,modal: true,
				open:function () {
					$(".ui-dialog-titlebar-close").show();
				},
				close: function(event, ui){
					$(this).empty();  
					$('#rubricsDialog').dialog('destroy');	
			    }
			});		
			$("#rubricsDialog").dialog("option", "title", "Rubric");
			$("#rubricsDialog").scrollTop("0");
			 //$("#loading-div-background").hide();
		}
	});
}
function showEvidenceFiles(subCriteriaId,studentId,learningIndicatorId,ts,stat){
	var learnIndiValueId = document.getElementById('learnIndicatorValueId:'+studentId+":"+ts).value;
	$("#loading-div-background5").show();
	$.ajax({
			type : "POST",
			url : "getLEFileBySubCriteriaId.htm",
			data : "subCriteriaId="+subCriteriaId+"&studentId="+studentId+"&learningIndicatorId="+learningIndicatorId+"&learnIndiValueId="+learnIndiValueId+"&index="+ts+"&stat="+stat,
			success : function(response) {
				var dailogContainer = $(document.getElementById('EvidenceFileDailog'));
				var screenWidth = $( window ).width() - 220;
				var screenHeight = $( window ).height() - 220;
				$('#EvidenceFileDailog').empty();  
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
				$("#loading-div-background5").hide();
			}
		});
}
function getCriteriaReportsByStudent(learningIndicatorId,studentId,upStatus,trimesterId,teacherRegId,gradeType){
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
					$('#CriteriaDialog').dialog('destroy');
					$('#LEFileDailog').dialog('destroy');
				},
				dialogClass: 'myTitleClass'
				});		
				$(dailogContainer).dialog("option", "title", "Learning Indicator");
				$(dailogContainer).scrollTop("0");	
				$("#loading-div-background1").hide();
			}
	      
});
}