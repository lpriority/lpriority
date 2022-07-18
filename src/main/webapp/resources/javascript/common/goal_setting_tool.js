function goToGoalSettingTool(studentId){
		  $("#goToGoalSettingTool").html("");
		  $("#loading-div-background1").show();
		  var tab=$('#tab').val();
		  
		   	$.ajax({
				type : "POST",
				url : "goToGoalSettingTool.htm",
				data : "studentId=" + studentId+"&tab="+tab,
				success : function(response) {
					
					var reportContainer = $(document.getElementById('goToGoalSettingTool'));
					var screenWidth = $( window ).width() - 10;
					var screenHeight = $( window ).height() - 10;
					$(reportContainer).empty(); 
					$(reportContainer).append(response);
					$(reportContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
						  $(".ui-dialog-titlebar-close").show();
					},
					close: function( event, ui ) {  
						 $(this).dialog('destroy');
						 $("#goToGoalSettingTool").html("");
					}
					});		
					$(reportContainer).dialog("option", "title", "Goal Setting Tool");
					$(reportContainer).scrollTop("0");
					$("#loading-div-background1").hide();
				}
			});
		
	}

function goToDashBoard(id,studentId){
	setActive(id);
	$.ajax({
		type : "POST",
		url : "goToGoalSettingTool.htm",
		data : "studentId=" + studentId+"&tab="+tab,
		success : function(response) {
			$("#contentPage").html(response);
		}
	});
}
function goToGoalReadingPage(caasppTypesId,id,studentId,starCaasppTypeId){
	setActive(id);
	 $("#contentPage").html("");
	  $("#loading-div-background-gs-main").show();
	   	$.ajax({
			type : "POST",
			url : "goToGoalReadingPage.htm",
			data : "caasppTypesId=" + caasppTypesId+"&studentId="+studentId+"&starCaasppTypeId="+starCaasppTypeId,
			success : function(response) {
				 $("#contentPage").html(response);
				 $("#loading-div-background-gs-main").hide();
			
			}
		});
}
function goToGoalCAASPPTestPrepPage(id,studentId){
	var dateobj= new Date() ;
	var month = dateobj.getMonth() + 1;
	
//	if(month==5 || month==6){
	  setActive(id); 
	  $("#contentPage").html("");
	  $("#loading-div-background-gs-main").show();
	   	$.ajax({
			type : "POST",
			url : "goToGoalCAASPPTestPage.htm",
			data : "studentId=" + studentId,
			success : function(response) {
				 $("#contentPage").html(response);
				 $("#loading-div-background-gs-main").hide();
			
			}
		});
	//}else{
	//	systemMessage("You Can't Access this page");
	//}
}
function autoSaveStrategies(i,trimesterId,starTypeId,studentId){
	 var type="";
	var gradeId=document.getElementById("gradeId").value;
	var strategyId = document.getElementById("strategyId"+i).value;
	if(strategyId>0){
					
			$.ajax({  
				url : "autoSaveStudStrategies.htm",
				type : "POST",
	 		    data: "strategyId=" + strategyId+"&gradeId="+gradeId+"&goalCount="+(i+1)+"&studentId="+studentId+"&trimesterId="+trimesterId+"&caasppTypesId="+starTypeId,
				success : function(data) { 
				$("#loading-div-background").hide();
	   		}  
			}); 	
	}else{
		systemMessage("Select Strategy Required");
	}
		
}
function goToGoalStrategies(starCaasppTypesId,id){
	setActive(id);  
	$("#contentPage").html("");
	  $("#loading-div-background-gs-main").show();
	   	$.ajax({
			type : "POST",
			url : "goToGoalStrategies.htm",
			data : "starCaasppTypesId=" + starCaasppTypesId,
			success : function(response) {
				 $("#contentPage").html(response);
				 $("#loading-div-background-gs-main").hide();
			
			}
		});
}


function autoSaveStudOwnStrategies(studentId,gradeId,i){
	//studOwnStrategyDesc
	var studOwnStrategyDesc=$('#studOwnStrategyDesc'+i).val();
	 if(studOwnStrategyDesc!=""){
  		$.ajax({  
			url : "autoSaveStudOwnStrategies.htm",
			type : "POST",
 		    data: "studentId=" + studentId+"&gradeId="+gradeId+"&studOwnStrategyDesc="+studOwnStrategyDesc+"&goalCount="+i,
			success : function(data) { 
			$("#loading-div-background").hide();
   		}  
		}); 	
}
		
}
function getStarStrategies(starCaasppTypesId,index,trimesterId,studentId){
	var gradeId=document.getElementById("gradeId").value;
	$("#starStrategy").html("");
	$.ajax({
		type : "POST",
		url : "getStarStrategies.htm",
		data : "starCaasppTypesId=" + starCaasppTypesId+"&studentId="+studentId+"&gradeId="+gradeId+"&trimesterId="+trimesterId,
		success : function(response) {
			 $("#starStrategy").html(response);
			 $("#loading-div-background1").hide();
		
		}
	});
}
function getBoyReport(studentId,id){
	setActive(id);  
	$("#contentPage").html("");
	var tab=$("#tab").val();
	 $("#loading-div-background-gs-main").show();
	$.ajax({
		type : "POST",
		url : "printBoyReport.htm",
		data : "studentId=" + studentId+"&tab="+tab,
		success : function(response) {
			 $("#loading-div-background-gs-main").hide();
			$("#contentPage").html(response);
			 checkParentSignExists('BOY_Report');
			
		
		}
	});
}
function exportToPDF(){
	$("#loading-div-background1").show();
	 $("#loading-div-background-gs-main").show();
	var report="goalReport";
     kendo.drawing.drawDOM($("#printBoy"),{
    	 paperSize: "auto",
        /* margin: { left: "0cm", top: "0cm", right: "0cm", bottom: "0cm" },*/
         landscape: false,
         multiPage: true,
         forcePageBreak: ".new-page"
     }).then(function(group) {
         return kendo.drawing.exportPDF(group,{      	 
         });
     }).done(function(data) {  
    	 $("#loading-div-background1").hide();
    	 $("#loading-div-background-gs-main").hide();
         kendo.saveAs({
             dataURI: data,
             fileName: report
         });         
     });    
}
//kendo.drawing.drawDOM($("#temp-container"), {
//    paperSize: "A4",

//    margin: { left: "0cm", top: "1cm", right: "0cm", bottom: "1cm" }
//}).then(function (group) {
//    // Render the result as a PDF file
//    return kendo.drawing.exportPDF(group);
//}).done(function (data) {
//    // Save the PDF file
//    kendo.saveAs({
//        dataURI: data,
//        fileName: "HR-Dashboard.pdf",
//        proxyURL: "http://demos.telerik.com/kendo-ui/service/export"
//    });
//});
function printDiv(divID) {
	// $("#"+divID).addClass("printable");
    // window.print();
//	$(document).attr("title", "Goal Report");	
//	$("#"+divID).print({
//       globalStyles: true,
//       mediaPrint: true,
//       iframe: true,
//       noPrintSelector: ".avoid-this"
// });
//	chart1.print();
//	$("#"+divID).print({
//        globalStyles : true,
//        mediaPrint : true,
//        iframe : true,
//        prepend : "A jQuery Tutorial <br/>",
//        append : "<br/>The printing plug-in!"
//    });
//	 var mode = 'iframe'; //popup
//     var close = mode == "popup";
//     var options = { mode : mode, popClose : close};
//     $("#"+divID).printArea(options);
	$('#chartContainer1').html($(this)[0].innerHTML).printArea();
} 

function getTrimesterReport(studentId,trimesterId,id){
	setActive(id);  
	$("#contentPage").html("");
	var tab=$("#tab").val();
	$("#loading-div-background-gs-main").show();
	$.ajax({
		type : "POST",
		url : "printTrimesterReport.htm",
		data : "studentId=" + studentId+"&trimesterId="+trimesterId+"&tab="+tab,
		success : function(response) {
			 $("#loading-div-background-gs-main").hide();
			  $("#contentPage").html(response);
			 checkParentSignExists('Trimester_Report');
			 	document.getElementById('chkbox1').checked = true;
			 	document.getElementById('chkbox2').checked = true;
			 	showGraph(1);
			 	showGraph(2);
		
		}
	});
}
function showGraph(type){
	var x;
		if(document.getElementById('chkbox'+type).checked) {
			    var gtype="";
		        if(type==1)
		        	gtype="STAR Reading Scores";
		        else
		        	gtype="STAR Math Scores";					        
  				var len=$('#len'+type).val();
  				if(len>0){
					
					$('#chartContainer'+type).empty(); 
					var score=[];
					if(type==1)
 					 x = document.getElementsByName("rscore");
					else
					 x = document.getElementsByName("mscore");	
				
					var m;
					
					for (m = 0; m < x.length; m++) {
	                  score[m]=	Number(x[m].value);	
	                  
	     			}
						
				    var goal=[],smax;
				    goal[0]=Number(score[0]);
				    
				    goal[3]=Number($('#rgolSc'+type).val());
				    var gsc=$('#range'+type).val();
				    if(gsc>goal[3])
			    		smax=gsc;
			    	else
			    		smax=goal[3]+1;
				    
				      
					var name=["boy", "tri1", "tri2","eoy"];
										
					var starScores = [];
					var goals = [];
					fLen = name.length;
					for (var i = 0; i < fLen; i++) {
					    	var xname,goalScores=null;					
							var fScore =score[i];	
							
							//var gScore =goal[i];
							xname=name[i];
							if(fScore!=0){
							goalScores=score[i];
							}
							
							
							var color = "#0000ff";
							var color1="#ff0000";
							var size = 20;
							starScores.push({ 
								x:i,
								y: goalScores,
								markerType: "circle", 
								markerColor: color,
								markerBorderColor : "#0000ff", 
								markerSize: size,
								markerBorderThickness: 2,
								label:xname
								 
						    });
										
					}
					for(var k=0;k<4;k++){
						color1="#ff0000";
						size = 16;
						if(k==0 || k==3){							
							var yname=name[k];
							var gScore =goal[k];
							goals.push({ 
								x:k,
								y: gScore,
								markerType: "circle", 
								markerColor: color1,
								markerBorderColor : "#ff0000", 
								markerSize: size,
								markerBorderThickness: 2,
								label:yname 
						    });
						}
					}
					

					CanvasJS.addColorSet("greenShades",
									                [
							                        "#0000ff",
									                "#ff0000",
									                "#ff69b4",
									                "#2E8B57",
									                "#3CB371",
									                "#90EE90"                
									                ]);
					
					var chart = new CanvasJS.Chart("chartContainer"+type,{
						colorSet: "greenShades",
						toolTip:{   
							content: "{label}<b>:</b> {y}"      
						},
						width:700,
						height:400,
						axisX:{  
						    labelAngle: -30
						},
						axisY: {
							minimum: 0,
						    maximum: smax,
						    interval: 0.5,
						    gridThickness: 0.5,
						    includeNull: true,
						 },
						data: [
	                       	{        
								type: "line",
								 connectNullData:true,
							     nullDataLineDashType:"solid",
								name:gtype,
								showInLegend: true,
								legendMarkerType: "square",
							    dataPoints: starScores,
							    
							},
	 						{       
	 							type: "line",
	 							connectNullData:true,
 	 							nullDataLineDashType:"solid",
	  							name:"Goal",
	  							showInLegend: true,
	  							legendMarkerType: "square",
	  							dataPoints: goals,
	  							
	  						}
	  						
							
						]
					});
					$("#chartContainer"+type).css("height", "400");
					chart.render();
					//chart.print();
					
					
			}else{
				$("#chartContainer"+type).html("<center><font color='red'>No Datas Available</font></center>");
			}
	}else{
		$('#chartContainer'+type).html("");
 		$("#chartContainer"+type).css("height", "");
	}
 	}
function printGoalReminder(studentId){
	$("#contentPage").html("");
	$.ajax({
		type : "POST",
		url : "printGoalReminder.htm",
		data : "studentId=" + studentId,
		success : function(response) {
			 $("#contentPage").html(response);
			 $("#loading-div-background1").hide();
		
		}
	});
}
function gotoIdeasList(){
	$("#contentPage").html("");
	  $("#loading-div-background1").show();
	   	$.ajax({
			type : "POST",
			url : "goToSampleIdeas.htm",
			success : function(response) {
				 $("#contentPage").html(response);
				 $("#loading-div-background1").hide();
			
			}
		});
}
function getAllStudsGoalReportByReportId(trimesterId,id) {
	setActive(id);
	var gradeId=$("#gradeId").val();
	var classId=$("#classId").val();
	var csId=$('#csId').val();
	if (gradeId != 'select' && classId != 'select' && csId != 'select') {		
		$("#loading-div-background1").show();
		$("#loading-div-background").show();
		$.ajax({
			type : "POST",
			url : "getAllStudsGoalReportByReportId.htm",
			data : "gradeId="+gradeId+"&trimesterId="+trimesterId+"&classId="+classId+"&csId="+csId,
			success : function(response) {
				var reportContainer = $(document.getElementById('goToGoalSettingTool'));
				var screenWidth = $( window ).width() - 10;
				var screenHeight = $( window ).height() - 10;
				$(reportContainer).empty(); 
				$(reportContainer).append(response);
				$(reportContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
					  $(".ui-dialog-titlebar-close").show();
				},
				close: function( event, ui ) { 
					$(reportContainer).empty(); 
					$(this).dialog('destroy');		
				}
				});		
				$(reportContainer).dialog("option", "title", "Goal Setting Tool");
				$(reportContainer).scrollTop("0");
				$("#loading-div-background1").hide();
				$("#loading-div-background").hide();
			}
		});
	}
	else{
		alert('please select all the filters');
		return false;
	}
}
function getAllStudsTrimesterReports(trimesterId,id) {

	setActive(id);
	var gradeId=$("#gradeId").val();
	var classId=$("#classId").val();
	var csId=$('#csId').val();
	if (gradeId != 'select') {
		
		
		$("#loading-div-background1").show();
		$.ajax({
			type : "POST",
			url : "getAllStudsTrimesterReports.htm",
			data : "gradeId="+gradeId+"&trimesterId="+trimesterId+"&classId="+classId+"&csId="+csId,
			success : function(response) {
				 $("#contentPage").html(response);
				    var x = document.getElementsByName("chkbox1");
				 	var y = document.getElementsByName("chkbox2");
				 	for(var i=0;i<x.length;i++){
				 	document.getElementById('chkbox1:'+i).checked = true;
				 	document.getElementById('chkbox2:'+i).checked = true;
				 	showGraphs(i,1);
				 	showGraphs(i,2);
				 	}
				$("#loading-div-background1").hide();
			}
		});
	}
}
function getAllStudsGoalReminderReports(trimesterId,id) {
	
	setActive(id);
	var gradeId=$("#gradeId").val();
	var classId=$("#classId").val();
	var csId=$('#csId').val();
	if (gradeId != 'select') {
		
		
		$("#loading-div-background1").show();
		$.ajax({
			type : "POST",
			url : "masterPrintGoalReminder.htm",
			data : "gradeId="+gradeId+"&trimesterId="+trimesterId+"&classId="+classId+"&csId="+csId,
			success : function(response) {
				 $("#contentPage").html(response);
				$("#loading-div-background1").hide();
			}
		});
	}
}

function getTrimesterList() {
	var gradeId=$("#gradeId").val();
	$("#reportId").empty();
	$("#reportId").append(
			$("<option></option>").val('select').html('Select Report'));
	if(gradeId != "" && gradeId != "select"){
		$.ajax({
			type : "POST",
			url : "loadTrimesterList.htm",
			data : "gradeId="+ gradeId,
			success : function(response) {
				var reports = response.reportsMap;	
				$.each(reports,
					function(index, value) {
						$("#reportId").append(
							$("<option></option>").val(index).html(value));
				});
			}
		});
	}
}

function setActive(id){
	for(var i=1;i<=10;i++){
	 $("#"+i).removeClass("active");
	} 
	$("#"+id).attr('class', 'active');
}

function goalSettingDownloadToExcelTest(teacherId, teacherName){
	var gradeId = $('#gradeId').val();
	$("#loading-div-background").show();
	if(gradeId > 0){
		$.ajax({  
			type : "GET",
			url : "downloadAllToExcel.htm", 
		    data: "gradeId="+gradeId+"&teacherId="+teacherId,
		    success : function(data) {
		    	$("#loading-div-background").hide();
		    	 var rows = [
		   	              {
		   		          cells: [
		   		            { value: "Teacher Name", fontSize: 12, bold: true, textAlign: "center",  verticalAlign: "center"},
		   		            { value: "Student Name" ,fontSize: 12, bold: true, textAlign: "center",  verticalAlign: "center"},
		   		            { value: "Grade Level", fontSize: 12 , bold: true, textAlign: "center",  verticalAlign: "center"},
		   		            
		   		            { value: "STAR Reading BOY", fontSize: 12 , bold: true, textAlign: "center",  verticalAlign: "center"},
		   		            {},
		   		            { value: "STAR Reading Tri 1", fontSize: 12, bold: true, textAlign: "center",  verticalAlign: "center"},
		   		            {},
		   		            { value: "STAR Reading Tri 2", fontSize: 12, bold: true, textAlign: "center",  verticalAlign: "center"},
		   		            {},
		   		            { value: "STAR Reading Tri 3", fontSize: 12, bold: true, textAlign: "center",  verticalAlign: "center"},	
		   		            {},
		   		            
		   		            { value: "STAR Math BOY", fontSize: 12, bold: true, textAlign: "center",  verticalAlign: "center" },
		   		            {},
		   		            { value: "STAR Math Tri 1", fontSize: 12, bold: true, textAlign: "center",  verticalAlign: "center"},
		   		            {},
		   		            { value: "STAR Math Tri 2", fontSize: 12, bold: true, textAlign: "center",  verticalAlign: "center"},
		   		            {},
		   		            { value: "STAR Math Tri 3", fontSize: 12, bold: true, textAlign: "center",  verticalAlign: "center"},
		   		            {},
		   		            
		   		            { value: "CAASPP Reading", fontSize: 12, bold: true, textAlign: "center",  verticalAlign: "center"},
		   		            { value: "CAASPP Math", fontSize: 12, bold: true, textAlign: "center",  verticalAlign: "center"},
		   		            
		   		          ]
		   	     		 },
		   	     		{
		   				    cells: [
		   				      {},
		   				      {},
		   				      {},
		   				      { value:"Score", fontSize: 10, bold: true, textAlign: "center",  verticalAlign: "center"},
		   				      { value:"strategies", fontSize: 10, bold: true, textAlign: "center",  verticalAlign: "center"},
		   				      { value:"Score", fontSize: 10, bold: true, textAlign: "center",  verticalAlign: "center"},
		   				      { value:"strategies", fontSize: 10, bold: true, textAlign: "center",  verticalAlign: "center"},
		   				      { value:"Score", fontSize: 10, bold: true, textAlign: "center",  verticalAlign: "center"},
		   				      { value:"strategies", fontSize: 10, bold: true, textAlign: "center",  verticalAlign: "center"},
		   				      { value:"Score", fontSize: 10, bold: true, textAlign: "center",  verticalAlign: "center"},
		   				      { value:"strategies", fontSize: 10, bold: true, textAlign: "center",  verticalAlign: "center"},
		   				      { value:"Score", fontSize: 10, bold: true, textAlign: "center",  verticalAlign: "center"},
		   				      { value:"strategies", fontSize: 10, bold: true, textAlign: "center",  verticalAlign: "center"},
		   				      { value:"Score", fontSize: 10, bold: true, textAlign: "center",  verticalAlign: "center"},
		   				      { value:"strategies", fontSize: 10, bold: true, textAlign: "center",  verticalAlign: "center"},
		   				      { value:"Score", fontSize: 10, bold: true, textAlign: "center",  verticalAlign: "center"},
		   				      { value:"strategies", fontSize: 10, bold: true, textAlign: "center",  verticalAlign: "center"},
		   				      { value:"Score", fontSize: 10, bold: true, textAlign: "center",  verticalAlign: "center"},
		   				      { value:"strategies", fontSize: 10, bold: true, textAlign: "center",  verticalAlign: "center"},
		   				      {},
		   				      {}
		   				    ]
		   				 }		   	      
		   	      ];
		    	 if(data.studentsLt.length > 0){
		    		 for (var i = 0; i < data.studentsLt.length; i++){
			        	var starScores = data.studentsLt[i];
			        	var studentId = starScores.student.studentId;
			        	rows.push({
			 	            cells: [	 	              
			 	                {value: starScores.teacher.userRegistration.firstName+" "+starScores.teacher.userRegistration.lastName},
								{value: starScores.student.userRegistration.firstName+" "+starScores.student.userRegistration.lastName},
								{value: starScores.grade.masterGrades.gradeName},
			 	           	 ]
			 	          });		 	          
			 	          if(data.starScoresMap[studentId].length > 0){			 	        	
				 	        	 var tempMap = data.starScoresMap[studentId][0];
				 	        	 var readMap = new Object();
				 	        	 readMap["@4"] = tempMap["@4"]; readMap["@1"] = tempMap["@1"]; readMap["@2"] = tempMap["@2"]; readMap["@3"] = tempMap["@3"];
				 	        	 $.each(readMap, function(readMapKey, readMapValue) {
				 	        		if(readMapValue.length > 0){
				 	        			if(rows[i+2]){
					 	        			rows[i+2].cells.push({value: readMapValue});
					 	        			if(data.starStrategiesMap[studentId]){
					 	        				var straReadMap = data.starStrategiesMap[studentId][0];
						 	        			if(Object.keys(straReadMap).length > 0){					 	        				
						 	        				 $.each(straReadMap, function(straReadKey, straReadValue) {
						 	        					 if(straReadKey == readMapKey){
						 	        						 if(straReadValue){
						 	        							rows[i+2].cells.push({value: straReadValue}); 
						 	        						 }else{
						 	        							rows[i+2].cells.push({value: ""}); 
						 	        						 }
						 	        					 }
						 	        				 });
						 	        			}else{
			 	        							rows[i+2].cells.push({value: ""}); 
			 	        						 }
					 	        			}else{
		 	        							rows[i+2].cells.push({value: ""}); 
		 	        						 }
				 	        			}
				 	        		}else{
				 	        			if(rows[i+2]){
					 	        			rows[i+2].cells.push({value: ""}); 	
					 	        			if(data.starStrategiesMap[studentId]){
					 	        				var straReadMap = data.starStrategiesMap[studentId][0];
					 	        				if(Object.keys(straReadMap).length > 0){
						 	        				 $.each(straReadMap, function(straReadKey, straReadValue) {
						 	        					 if(straReadKey == readMapKey){
						 	        						 if(straReadValue){
						 	        							rows[i+2].cells.push({value: straReadValue}); 
						 	        						 }else{
						 	        							rows[i+2].cells.push({value: ""}); 
						 	        						 }
						 	        					 }
						 	        				 });
						 	        			}else{
			 	        							rows[i+2].cells.push({value: ""}); 
			 	        						 }
					 	        			}else{
		 	        							rows[i+2].cells.push({value: ""}); 
		 	        						 }
					 	        		 }
				 	        		}
				 				 });
				 	        	
				 	        	 var tempMap = data.starScoresMap[studentId][1];
				 	        	 var mathMap = new Object();
				 	        	 mathMap["@4"] = tempMap["@4"]; mathMap["@1"] = tempMap["@1"]; mathMap["@2"] = tempMap["@2"]; mathMap["@3"] = tempMap["@3"];
				 	        	 $.each(mathMap, function(mathMapkey, mathMapValue) {
				 	        		if(mathMapValue.length > 0){
				 	        			if(rows[i+2]){
					 	        			rows[i+2].cells.push({value: mathMapValue});
					 	        			if(data.starStrategiesMap[studentId]){
						 	        			var straMathMap = data.starStrategiesMap[studentId][1];
						 	        			if(Object.keys(straMathMap).length > 0){
						 	        				$.each(straMathMap, function(straMathKey, straMathValue) {
						 	        					 if(straMathKey == mathMapkey){
						 	        						 if(straMathValue){
						 	        							rows[i+2].cells.push({value: straMathValue}); 
						 	        						 }else{
						 	        							rows[i+2].cells.push({value: ""}); 
						 	        						 }
						 	        					 }
						 	        				 });
						 	        			}else{
						 	        				rows[i+2].cells.push({value: ""}); 
						 	        			}
					 	        			}else{
		 	        							rows[i+2].cells.push({value: ""}); 
		 	        						}
				 	        		  }
				 	        		}else{
				 	        			if(rows[i+2]){
					 	        			rows[i+2].cells.push({value: ""}); 
					 	        			if(data.starStrategiesMap[studentId]){
						 	        			var straMathMap = data.starStrategiesMap[studentId][1];
						 	        			if(Object.keys(straMathMap).length > 0){
						 	        				$.each(straMathMap, function(straMathKey, straMathValue) {
						 	        					 if(straMathKey == mathMapkey){
						 	        						 if(straMathValue){
						 	        							rows[i+2].cells.push({value: straMathValue}); 
						 	        						 }else{
						 	        							rows[i+2].cells.push({value: ""}); 
						 	        						 }
						 	        					 }
						 	        				 });
						 	        			}else{
						 	        				rows[i+2].cells.push({value: ""}); 
						 	        			}
					 	        			}else{
		 	        							rows[i+2].cells.push({value: ""}); 
		 	        						}
				 	        			}
				 	        		}
					 			 }); 	 	        	         
			 	          }   
				 	     if(data.CAASPPScoresMap[studentId].length > 0){				 	    	  
				 	    	var CAASPPScores = data.CAASPPScoresMap[studentId];
						    if(CAASPPScores[0].caasppScore > 0)
						    	rows[i+2].cells.push({value: CAASPPScores[0].caasppScore}); 	
						    else
						    	rows[i+2].cells.push({value: ""}); 
						    
						    
						    if(CAASPPScores[1].caasppScore > 0)
						    	rows[i+2].cells.push({value: CAASPPScores[1].caasppScore}); 	
						    else
						    	rows[i+2].cells.push({value: ""}); 						    
				 	    } 
			        }			        	 
			        var gradeName = $("#gradeId :selected").text();
			    	var title ='Goal_Setting_'+gradeName+"_"+teacherName;
					var max = 0;
			    	var workbook = new kendo.ooxml.Workbook({
						 sheets: [{
							 columns: [
								        { width: 250 },
								        { width: 200 },
								        { width: 120 },
								        { width: 100 },
								        { width: 100 },
								        { width: 100 },
								        { width: 100 },
								        { width: 100 },
								        { width: 100 },
								        { width: 100 },
								        { width: 100 },
								        { width: 100 },
								        { width: 100 },
								        { width: 100 },
								        { width: 100 },
								        { width: 100 },
								        { width: 100 },
								        { width: 100 },
								        { width: 100 },
								        { width: 150 },
								        { width: 150 }
								      ],
								     	title: "Goal Setting Report",
								     	sortable: true,
							            pageable: true,
							            groupable: true,
							            filterable: true,
							            columnMenu: true,
							            reorderable: true,
							            resizable: true,
							            mergedCells: ["A1:A2",
							                          "B1:B2",
							                          "C1:C2",
							                          "D1:E1",
							                          "F1:G1",
							                          "H1:I1",
							                          "J1:K1",
							                          "L1:M1",
							                          "N1:O1",
							                          "P1:Q1",
							                          "R1:S1",
							                          "T1:T2",
							                          "U1:U2"									                          
							                        ],
								      rows: rows
						 }]
			    	});
			    	kendo.saveAs({
					    dataURI: workbook.toDataURL(),
					    fileName: title+".xlsx"
					});  
		    	 }else{
		    		 systemMessage("Data not found !!");
		    	 }			        
			}
		}); 
   }
}

function getChildGoalReportsMainPage(id){
	
	setActive(id);  
	var studentId=$("#studentId").val();
	var tab=$("#tab").val();
	$("#loading-div-background").show();
	
	if(studentId>0){
	$.ajax({
		type : "POST",
		url : "printBoyReport.htm",
		data : "studentId=" + studentId+"&tab="+tab,
		success : function(response) {
			var reportContainer = $(document.getElementById('goToGoalReports'));
			var screenWidth = $( window ).width() - 10;
			var screenHeight = $( window ).height() - 10;
			$(reportContainer).empty(); 
			$(reportContainer).append(response);
			$(reportContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
				  $(".ui-dialog-titlebar-close").show();
			},
			close: function( event, ui ) { 
				$('#studentId').val('0');
				var reportContainer = $(document.getElementById('goToGoalReports'));
				$(reportContainer).empty();
				$(".ui-dialog").remove();
			}
			});		
			$(reportContainer).dialog("option", "title", "Goal Setting Tool");
			$(reportContainer).scrollTop("0");
			checkParentSignExists('BOY_Report');
			$("#loading-div-background").hide();
		
		}
	});
	}
}
