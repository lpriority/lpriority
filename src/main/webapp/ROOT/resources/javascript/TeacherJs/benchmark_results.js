function clears(){
	 $("#Evaluate").empty(); 
	 $("#StuAssessQuestionsList").empty(); 
	 
}
function evaluateBenchmark(){
	var checkbox = $('#checkAssigneds').is(':checked');
	if(checkbox){
		$("#benchmarkResultsTable").html("");
		var usedFor = $('#usedFor').val();
		var csId = $('#csId').val();
		var dateAssigned=$("#assignedDate").val();
		var gradeId = $('#gradeId').val();
		var classId = $('#classId').val();
		var assignmentId=$('#titleId').val();
		if(gradeId > 0 && classId > 0 && csId > 0 && dateAssigned != "" && dateAssigned != "select" && assignmentId != 'select'){
			$("#loading-div-background").show();
		   	$.ajax({
				type : "GET",
				url : "showBenchmarkResults.htm",
				data : "csId=" + csId + "&usedFor=" + usedFor+"&dateAssigned="+dateAssigned+"&titleId="+assignmentId,
				success : function(response2) {
				  $("#benchmarkResultsTable").html("");
				  $("#loading-div-background").hide();
			      $("#benchmarkResultsTable").html(response2);	
			      var TSort_Data = new Array ('sortTable', 's', 's', 'i', 'i', 'i', 's', 'f', 'i');
	              tsRegister();
	              tsSetTable('sortTable');
	              tsInit();		
	        	if(checkbox)
	              $("#checkAssigneds").prop("checked", true);
	        	else
	        	  $("#checkAssigneds").prop("checked", false);
	             }
			});
		}else{
			alert("Please fill the filters");
		}
	}
}

function exportToPDF(){
 	 var dateAssigned=$("#assignedDate").val();
      kendo.drawing.drawDOM($("#contentDiv"))
      .then(function(group) {
          return kendo.drawing.exportPDF(group, {
              paperSize: "auto",
              margin: { left: "1cm", top: "1cm", right: "1cm", bottom: "1cm" }
          });
      })
      .done(function(data) {
          kendo.saveAs({
              dataURI: data,
              fileName: dateAssigned
          });
      });
}

function printDiv(divID) {
	$("#"+divID).print({
        globalStyles: false,
        mediaPrint: false,
        iframe: true,
        noPrintSelector: ".avoid-this"
   });	
} 
function getAllStudentFluencyResults(assignmentId,type){
	$("#loading-div-background1").show();
	$.ajax({
			type : "GET",
			url : "AllStudentFluencyErrorWordList.htm",
			data : "assignmentId="+assignmentId+"&type="+type,
			success : function(response) {
				var screenWidth = $( window ).width() - 10;
				var screenHeight = $( window ).height() - 10;
				$('#fluencyDailog').empty();  
				$("#fluencyDailog").html(response);
				$("#fluencyDailog").dialog({width: screenWidth, height: screenHeight,modal: true,title:"Fluency Error Words/Item Analysis",
					open:function () {
						$(".ui-dialog-titlebar-close").show();
					},
					close: function(event, ui){
						$(this).empty();  
				    }
				});		
				$("#loading-div-background1").hide();
				$("#fluencyDailog").dialog("option", "title", "Fluency Error Words/Item Analysis");
				$("#fluencyDailog").scrollTop("0");
				document.getElementById("chk"+type).checked=true;
				
			}
		});
	}
	  	
function evaluateBenchmarkResults(){
	
		var usedFor = $('#usedFor').val();
		var csId = $('#csId').val();
		var dateAssigned=$("#assignedDate").val();
		var gradeId = $('#gradeId').val();
		var classId = $('#classId').val();
		var assignmentId=$('#titleId').val();
		
		if(gradeId != "select" && classId != "select" && csId != "select" && dateAssigned != "" && dateAssigned != "select" && assignmentId != 'select'){
			document.getElementById('selfPeerResults').action = 'exportSelfAndPeerReports.htm';
		}else{
			alert("Please fill the filters");
			return false;
		}
	
}
function showDownloadBut(){
	var usedFor = $('#usedFor').val();
	var csId = $('#csId').val();
	var dateAssigned=$("#assignedDate").val();
	var gradeId = $('#gradeId').val();
	var classId = $('#classId').val();
	var assignmentId=$('#titleId').val();
	
	if(gradeId != "select" && classId != "select" && csId != "select" && dateAssigned != "" && dateAssigned != "select" && assignmentId != 'select'){
	   	document.getElementById("showBut").style.visibility="visible";
	}else{
		document.getElementById("showBut").style.visibility="hidden";
	}
}
