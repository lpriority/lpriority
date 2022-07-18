<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<script type="text/javascript" src="resources/javascript/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="resources/javascript/tinymce/jscripts/tiny_mce/plugins/asciimath/js/ASCIIMathMLwFallback.js"></script>
<script type="text/javascript" src="resources/javascript/voice_recorder.js"></script>
<script src="resources/javascript/VoiceRecorder/recorder.js"></script>
<script type="text/javascript" src="resources/javascript/performance_common.js"></script>
<script type="text/javascript" src="/dwr/interface/performanceService.js"></script>
<style type="text/css">
.ui-dialog > .ui-widget-content {background: white;}
.ui-widget {font-size:0.9em;font-family:'Segoe UI', Frutiger, 'Frutiger Linotype', 'Dejavu Sans', 'Helvetica Neue', Arial, sans-serif;}

.ui-accordion > .ui-widget-content {background: transparent;}
.ui-accordion .ui-accordion-icons{background: rgb(235, 244, 251);border: 1px solid #a0daf6;color:#1880C9;font-weight:bold;}
.ui-accordion .ui-accordion-header{font-size:100%;outline:0;}
.ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active{border: 1px solid #0057AF;color:#0057AF;}
.ui-state-active a, .ui-state-active a:link, .ui-state-active a:visited,.ui-state-hover a, .ui-state-hover a:hover, .ui-state-hover a:link, .ui-state-hover a:visited, .ui-state-focus a, .ui-state-focus a:hover, .ui-state-focus a:link, .ui-state-focus a:visited{color:#0057AF;}
.ui-widget input, .ui-widget select, .ui-widget textarea, .ui-widget button{font-size:1em;}
.ui-widget-content{border: 1px solid #a0daf6;}
.ui-state-hover, .ui-widget-content .ui-state-hover, .ui-widget-header .ui-state-hover, .ui-state-focus, .ui-widget-content .ui-state-focus, .ui-widget-header .ui-state-focus{border:1px solid #1880C9;color:#1880C9;}
.ui-widget-header{background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00E3FF), color-stop(1,#03b7cf) );border:1px solid #02b8d0;text-shadow:0 1px 1px rgb(0, 48, 53);margin:-.12em;}
.ui-state-default a, .ui-state-default a:link, .ui-state-default a:visited{color:#1880C9;font-weight:bold;}
</style>
<script type="text/javascript">
var AMTcgiloc = "http://www.imathas.com/cgi-bin/mimetex.cgi";
var editors = [];
var globalTotal = 0;

$(function() {
    $( "#selfAssessment" ).accordion({
      collapsible: true,
      active: false
    });
    $( "#additional" ).accordion({
        collapsible: true,
        active: false
    });
    $( "#workMain" ).accordion({
    	collapsible: true
    });
    $( "#teacherAssessment" ).accordion({
    	collapsible: true,
    	active: false
    });
    $( "#teacherComment" ).accordion({
    	collapsible: true,
    	active: false
    });
    $( "#chat" ).accordion({
    	collapsible: true,
    	active: false
    });
    $( "#supportevidence" ).accordion({
        collapsible: true,
        active: false
    });
    
    $('.ui-accordion').click(function() {
    	$(this).find('.panel').show();
    	$(this).siblings().find('.panel').hide();

    });
});

tinyMCE.init({
    //mode : "textareas",
    mode : "specific_textareas",
    auto_focus : "writing",
    editor_selector : "mceEditor",
	plugins : 'inlinepopups,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,insertdatetime,preview,media,searchreplace,print,contextmenu',
	themes : 'simple,advanced',
	theme_advanced_buttons1 : "",
	disk_cache : true,
	debug : false,
	image_advtab: true,
	theme_advanced_buttons3 : "",
    theme_advanced_fonts : "Arial=arial,helvetica,sans-serif,Courier New=courier new,courier,monospace,Georgia=georgia,times new roman,times,serif,Tahoma=tahoma,arial,helvetica,sans-serif,Times=times new roman,times,serif,Verdana=verdana,arial,helvetica,sans-serif",
    theme_advanced_toolbar_location : "top",
    theme_advanced_toolbar_align : "left",
    theme_advanced_statusbar_location : "bottom",
    statusbar : false,
    theme_advanced_path : false,
    init_instance_callback  : "updateField",		
    content_css : "resources/javascript/tinymce/jscripts/tiny_mce/css/content.css"
});

tinyMCE.init({
    //mode : "textareas",
    mode : "specific_textareas",
    editor_selector : "mceEditor2",
	plugins : 'asciimath,asciisvg,inlinepopups,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,insertdatetime,preview,media,searchreplace,print,contextmenu',
	themes : 'simple,advanced',
	theme_advanced_buttons1 : "",
	disk_cache : true,
	debug : false,
    theme_advanced_fonts : "Arial=arial,helvetica,sans-serif,Courier New=courier new,courier,monospace,Georgia=georgia,times new roman,times,serif,Tahoma=tahoma,arial,helvetica,sans-serif,Times=times new roman,times,serif,Verdana=verdana,arial,helvetica,sans-serif",
    theme_advanced_toolbar_location : "top",
    theme_advanced_toolbar_align : "left",
    theme_advanced_statusbar_location : "bottom",
    theme_advanced_path : false,
    statusbar : false,
    init_instance_callback  : "updateField",
    content_css : "resources/javascript/tinymce/jscripts/tiny_mce/css/content.css"
});

tinyMCE.init({
    //mode : "textareas",
    mode : "specific_textareas",
    editor_selector : "mceEditor3",
	plugins : 'advimage,inlinepopups,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,insertdatetime,preview,media,searchreplace,print,contextmenu',
	themes : 'simple,advanced',	
	theme_advanced_buttons1 : "",
    disk_cache : true,
	debug : false,
    theme_advanced_fonts : "Arial=arial,helvetica,sans-serif,Courier New=courier new,courier,monospace,Georgia=georgia,times new roman,times,serif,Tahoma=tahoma,arial,helvetica,sans-serif,Times=times new roman,times,serif,Verdana=verdana,arial,helvetica,sans-serif",
    theme_advanced_toolbar_location : "top",
    theme_advanced_toolbar_align : "left",
    theme_advanced_statusbar_location : "bottom",
    theme_advanced_path : false,
    statusbar : false,
    init_instance_callback  : "updateField",
    content_css : "resources/javascript/tinymce/jscripts/tiny_mce/css/content.css"
});

function updateField(inst){
	
	var b=inst.getBody();
    $(b).focus();
   
   	$(b).blur();
   	
   	var text =document.getElementById(inst.id+"Id").value;
   	inst.setContent(text);
   	editors[inst.id] = text;
   	inst.getBody().setAttribute('contenteditable', false);
}


$( document ).ready(function() {	
	var dimCount = 1;
	if (document.getElementById("sdimension2")) {
		dimCount = 4;
	}
	var totalRub = $("#totalRub1").val();
	var position = 0;
	
	for(var i=1;i<=dimCount;i++){
		var dimention =  $("#sdimension"+i).val();// document.getElementById("sdimension"+i).value;	
		if(dimention > 0){
			position = totalRub-dimention;
			document.getElementById("sdimension"+i+position).style.color = 'red';
		}	
		//for teacher rubric
		dimention =  $("#dimension"+i).val(); //document.getElementById("dimension"+i).value;	
		if(dimention > 0){
			position = totalRub-dimention;
			document.getElementById("dimension"+i+position).style.color = 'red';
		}
	}
	
	for(var j=1;j<totalRub;j++){		
		var sTotalVal =  $("#stotal"+j).val();//document.getElementById("stotal"+j);
		sTotalVal = 0;
		for(var k=1;k<=dimCount;k++){
			var sScoreVal = $("#sscore"+j).val(); //document.getElementById("sscore"+j).value;	
			if(document.getElementById("sdimension"+k+j).style.color == 'red'){
				sTotalVal.value = parseInt(sTotalVal.value) + parseInt(sScoreVal);
			}
		}
		//for teacher rubric
		var totalVal = $("#total"+j).val();// document.getElementById("total"+j);
		totalVal = 0;
		for(var k=1;k<=dimCount;k++){
			var scoreVal =  $("#score"+j).val(); //document.getElementById("score"+j).value;	
			if( $("#dimension"+k+j).css("color") == 'red'){
				totalVal.value = parseInt(totalVal) + parseInt(scoreVal);
			}
		}
	}
	globalTotal = $("#total").val(); //document.getElementById("total").value;	
});

window.addEventListener("beforeunload", function (e) {
	  
	var confirmationMessage = "";
//     (e || window.event).returnValue = confirmationMessage;     
// 	  return confirmationMessage;                                
});
	
function playAudio(){
	var path = document.getElementById("audioPath").value;
	$.ajax({
		type : "GET",
		url : "playAudio.htm",
		data : "filePath="+path,
		success : function(response) {
			//document.getElementById("rubricValuesId").value = response.rubricValueId;
		}
	});
}


function checkParecentage(){
	var percent=$('#percentage').val();
	if(percent>100){
	    alert("The percentage must be between 0 and 100");
	    $('#percentage').val(100);
	}  
}
	
function checkTeacherComment(){
	var teaComment=$('#comment').val();
	le=teaComment.length;
	if(le>500){
		systemMessage("You have exceeded the more than 500 characters");
		 $("#comment").focus();
		 return false;
		
	  }
	}  
</script>
<div id="clsPerformance">

	<table style="border: 1px solid black;" width="100%" id="msg">		
		<tr>
			<td width="50%"><input type="hidden" id="assPTaskId" name="assPTaskId" value="${assignedPtasks.assignedTaskId}" />
			<input type="hidden" id="assignmentId" name="assignemntId" value="${assignmentId}" />
				<b>Performance Task Name:</b> ${assignedPtasks.performanceTask.ptName}
			</td>
			<td rowspan="3" width="50%" style="border: 1px solid black;">
				<b><label style="padding-left: 7cm">Upload Direction Files:</label></b><br>
				<div style="overflow:auto; max-height: 70px">
					<c:forEach var="ptf" items="${filesLP}">					
						<a href="downloadFile.htm?filePath=${ptf.filePath}"><font color="blue">${ptf.fileName}</font></a><br>					
					</c:forEach>
				</div>
				
			</td>
		</tr>
		<tr>
			<td>
				<b>Subject Area :</b> ${assignedPtasks.performanceTask.ptSubjectArea}
			</td>
		</tr>
		<tr>
			<td>
				<b>Directions :</b> ${assignedPtasks.performanceTask.ptDirections}
			</td>
		</tr>
	</table>
	<div id="workMain" style="overflow: hidden;">
	<h3>
	<a>Main Work Area</a></h3> 
 		<div id="divWorkMain" class="panel">
 			<table style="border: 1px solid black;" width="100%">
 				<tr>
					<td colspan="4" align="center" style="border: 1px solid black;">		
						<audio id="playerId" style="width: 3cm;" controls="" preload="auto">
						  <source src="loadDirectUserFile.htm?usersFilePath=${audioFiles.filePath}" type="audio/wav">
						</audio><br>	
						<input type="hidden" id="audioPath" value="${audioFiles.filePath}"/>					
						<input type="hidden" id="audioContent"/>
							
					</td>	
				</tr>
 			</table>
			<table style="border: 1px solid black;" width="100%">
				<tr style="border: 1px solid black;">
					<td style="border: 1px solid black;" width="50%" align="center" rowspan="2">
						<label>Writing Area </label>
						<textarea id="writing" class="mceEditor" rows="38" cols="80" style="width: 100%" /></textarea>				
					</td>			
					<td style="border: 1px solid black;" align="center" width="50%">
						<label>Image Workspace: </label>
						<textarea id="uploadarea" class="mceEditor3" rows="18" cols="80" style="width: 100%"></textarea>				
					</td>
				</tr>
				<tr style="border: 1px solid black;">
					<td style="border: 1px solid black;" align="center">
						<label>Calculation Area </label>
						<textarea id="calculations" class="mceEditor2" rows="18" cols="80" style="width: 100%" 
							></textarea>							
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="supportevidence">
		<h3>Support Evidence</h3> 
 		<div id="divEvidence" class="panel">
 			<form name="perEvidenceForm" action="" id="perEvidenceForm" style="min-height: 300px;max-height: 300px;overflow-y: auto;"> 
 				
 				<table align="center" style="padding: 1cm">
 					
					<tr><td>
					<c:set var="coun" value="0" />
                            <div id="addEvi"> 
                            <c:forEach items="${assignedPtasks.studentPtaskEvidence}" var="evid">
                            <c:set var="coun" value="${coun+1}" />
                             Evidence&nbsp;<c:out value="${coun}" />&nbsp;:&nbsp;<textarea name="evidence" id="evidence:${coun}" readonly><c:out value="${evid.evidenceDesc}" /></textarea><br><br>
                            </c:forEach>
                            </div> 
					</td></tr>
 				</table> 
 			</form> 
		</div>
	</div>
	<div id="additional">
		<h3>Additional Media</h3> 
 		<div id="divAdditional" class="panel"> 
			<form name="perFileForm" action="fileAutoSave.htm" id="perFileForm" method="post" enctype="multipart/form-data">
				<table align="center" style="padding: 1cm">
					<tr>
						<td>
							<input type="hidden" name="assignedFileTaskId" id="assignedFileTaskId" value="${assignedPtasks.assignedTaskId}"/>
							Uploaded file:
								<a href="downloadFile.htm?filePath=${fileLP.filePath}"><font color="blue">${fileLP.fileName}</font></a>							
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<c:if test="${tab == 'gradeGroup'}">
		<div id="chat">
			<h3>Chat Content</h3> 
 			<div>
				${assignedPtasks.chatcontents}
			</div>
		</div>
	</c:if>
	
	<form:form id="performanceTestForm" modelAttribute="assignedPtasks" method="POST">
		<form:hidden path="assignedTaskId" id="assignedTaskId"/>
		<form:hidden path="studentAssignmentStatus.studentAssignmentId" id="studentAssignmentId"/>
		<form:hidden path="performanceTask.questionId" id="questionId"/>
		<form:hidden path="rubricValues.rubricValuesId" id="rubricValuesId"/>	
		<form:hidden path="writing" id="writingId"/>	
		<form:hidden path="uploadarea" id="uploadareaId"/>
		<form:hidden path="calculations" id="calculationsId"/>	
		<input type="hidden" id="tab" name="tab" value="${tab}"/>
		<input type="hidden" id="totalRub1" name="totalRub1" value="${fn:length(rubrics)}"/>
		<div id="selfAssessment">
		<h3>Student Self Assessment Score</h3> 
 			<div id="divSelfAssessment" class="panel"> 
				<table border="1" align="center" width="20%">
					<c:set var="dec" value="0"/>
					<c:set var="totalRub" value="${fn:length(rubrics)}"/>		
					<c:forEach var="rubric" items="${rubrics}">
					<c:if test="${dec == 0}">		
					<tr>
						<td width="1%">
							<input id="sscore${dec}" required="required" value="${rubric.score}" 
								size="3" style="border:none; background-color:transparent; text-align:center;"  readonly/>	
						</td>
						<td >
							<textarea  id="sdimension1${dec}" required="required" cols="25" rows="5" 
								style="border:none; text-align:center; background-color:transparent;" readonly>${rubric.dimension1}</textarea>
						</td>
						<c:if test="${rubric.rubricTypes.rubricTypeId == 3 || rubric.rubricTypes.rubricTypeId == 4}">
							<td>
								<textarea  id="sdimension2${dec}" required="required" cols="25" rows="5" 
									style="border:none; text-align:center; background-color:transparent;" readonly>${rubric.dimension2}</textarea>
							</td>
							<td>
								<textarea  id="sdimension3${dec}" required="required" cols="25" rows="5" 
									style="border:none; text-align:center; background-color:transparent;" readonly>${rubric.dimension3}</textarea>
							</td>
							<td>
								<textarea  id="sdimension4${dec}" required="required" cols="25" rows="5" 
									style="border:none; text-align:center; background-color:transparent;" readonly>${rubric.dimension4}</textarea>
							</td>			
						</c:if>
						<td width="1%">
							<input id="stotal${dec}" required="required"  value="Total" 
								size="3" style="border:none; background-color:transparent; text-align:center;" readonly/>	
						</td>
					</tr>	
					</c:if>
					<c:if test="${dec ne 0}">
					<tr>
						<td width="1%">
							<input id="sscore${dec}" name="srScore" required="required" value="${rubric.score}" 
								size="2" style="border:none; background-color:transparent; text-align:center;"  readonly/>	
						</td>
						<td>
							<textarea id="sdimension1${dec}" name="sdimension1" required="required" cols="25" rows="5" 
								readonly style="border:none; text-align:center; background-color:transparent;">${rubric.dimension1}</textarea>
						</td>
						<c:if test="${rubric.rubricTypes.rubricTypeId == 3 || rubric.rubricTypes.rubricTypeId == 4}">
							<td>
								<textarea  id="sdimension2${dec}" name="sdimension2" required="required" cols="25" rows="5" 
									readonly style="border:none; text-align:center; background-color:transparent;">${rubric.dimension2}</textarea>
							</td>
							<td>
								<textarea  id="sdimension3${dec}" name="sdimension3" required="required" cols="25" rows="5" 
									readonly style="border:none; text-align:center; background-color:transparent;">${rubric.dimension3}</textarea>
							</td>
							<td>
								<textarea id="sdimension4${dec}" name="sdimension4" required="required" cols="25" rows="5" 
									readonly style="border:none; text-align:center; background-color:transparent;">${rubric.dimension4}</textarea>
							</td>			
						</c:if>
						<td width="1%">
							<input id="stotal${dec}" required="required"	size="2" style="border:none; background-color:transparent; text-align:center;" 
								readonly value="0"/>	
						</td>
					</tr>	
					</c:if>
					<c:set var="dec" value="${dec+1}"/>
			     </c:forEach>
			     <tr>
						<td width="1%">
							<input id="sscore" required="required" value="Total"  
								size="3" style="border:none; background-color:transparent; text-align:center;"  readonly/>	
						</td>
						<td>
							<input type="text" id="sdimension1" value="${studentRubric.dimension1Value}" 
								style="border:none; text-align:center; background-color:transparent;" readonly required="required"/>
						</td>
						<c:if test="${rubrics[0].rubricTypes.rubricTypeId == 3 || rubrics[0].rubricTypes.rubricTypeId == 4}">
							<td>
								<input type="text" id="sdimension2" value="${studentRubric.dimension2Value}" 
									style="border:none; text-align:center; background-color:transparent;" readonly required="required" />
							</td>
							<td>
								<input type="text" id="sdimension3" value="${studentRubric.dimension3Value}" 
									style="border:none; text-align:center; background-color:transparent;" readonly  required="required"/>
							</td>
							<td>
								<input type="text" id="sdimension4" value="${studentRubric.dimension4Value}" 
									style="border:none; text-align:center; background-color:transparent;" readonly required="required" />
							</td>			
						</c:if>
						<td width="1%"> 
							<input type="text" id="stotal" value="${studentRubric.totalScore}"   
								size="2" style="border:none; background-color:transparent; text-align:center;" readonly required="required" />
						</td> 
					</tr>			     
				</table>
		  </div>
		</div>		
		<div id="teacherAssessment">
		<h3>Teacher Assessment Score</h3> 
 			<div id="divTeacherAssessment" class="panel">
				<table border="1" align="center" width="20%">
					<c:set var="dec" value="0"/>
					<c:set var="totalRub" value="${fn:length(rubrics)}"/>		
					<c:forEach var="rubric" items="${rubrics}">
					<c:if test="${dec == 0}">		
					<tr>
						<td width="1%">
							<input id="score${dec}" required="required" value="${rubric.score}" 
								size="3" style="border:none; background-color:transparent; text-align:center;"  readonly/>	
						</td>
						<td >
							<textarea  id="dimension1${dec}" required="required" cols="25" rows="5" 
								style="border:none; text-align:center; background-color:transparent;" readonly>${rubric.dimension1}</textarea>
						</td>
						<c:if test="${rubric.rubricTypes.rubricTypeId == 3 || rubric.rubricTypes.rubricTypeId == 4}">
							<td>
								<textarea  id="dimension2${dec}" required="required" cols="25" rows="5" 
									style="border:none; text-align:center; background-color:transparent;" readonly>${rubric.dimension2}</textarea>
							</td>
							<td>
								<textarea  id="dimension3${dec}" required="required" cols="25" rows="5" 
									style="border:none; text-align:center; background-color:transparent;" readonly>${rubric.dimension3}</textarea>
							</td>
							<td>
								<textarea  id="dimension4${dec}" required="required" cols="25" rows="5" 
									style="border:none; text-align:center; background-color:transparent;" readonly>${rubric.dimension4}</textarea>
							</td>			
						</c:if>
						<td width="1%">
							<input id="total${dec}" required="required"  value="Total" 
								size="3" style="border:none; background-color:transparent; text-align:center;" readonly/>	
						</td>
					</tr>	
					</c:if>
					<c:if test="${dec ne 0}">
					<tr>
						<td width="1%">
							<input id="score${dec}" name="rScore" required="required" value="${rubric.score}" 
								size="2" style="border:none; background-color:transparent; text-align:center;"  readonly/>	
						</td>
						<td>
							<textarea id="dimension1${dec}" name="dimension1" required="required" cols="25" rows="5" 
								<c:if test="${teacherObj ne null}"> onclick="selectRubric('dimension1',${rubric.score},${dec},${totalRub})" </c:if> 
								readonly style="border:none; text-align:center; background-color:transparent;">${rubric.dimension1}</textarea>
						</td>
						<c:if test="${rubric.rubricTypes.rubricTypeId == 3 || rubric.rubricTypes.rubricTypeId == 4}">
							<td>
								<textarea  id="dimension2${dec}" name="dimension2" required="required" cols="25" rows="5" 
									<c:if test="${teacherObj ne null}"> onclick="selectRubric('dimension2',${rubric.score},${dec},${totalRub})" </c:if>
									readonly style="border:none; text-align:center; background-color:transparent;">${rubric.dimension2}</textarea>
							</td>
							<td>
								<textarea  id="dimension3${dec}" name="dimension3" required="required" cols="25" rows="5" 
									<c:if test="${teacherObj ne null}"> onclick="selectRubric('dimension3',${rubric.score},${dec},${totalRub})" </c:if> 
									readonly style="border:none; text-align:center; background-color:transparent;">${rubric.dimension3}</textarea>
							</td>
							<td>
								<textarea id="dimension4${dec}" name="dimension4" required="required" cols="25" rows="5" 
									<c:if test="${teacherObj ne null}"> onclick="selectRubric('dimension4',${rubric.score},${dec},${totalRub})" </c:if> 
									readonly style="border:none; text-align:center; background-color:transparent;">${rubric.dimension4}</textarea>
							</td>			
						</c:if>
						<td width="1%">
							<input id="total${dec}" required="required" size="2" style="border:none; background-color:transparent; text-align:center;" 
								readonly value="0"/>	
						</td>
					</tr>	
					</c:if>
					<c:set var="dec" value="${dec+1}"/>
			     </c:forEach>
			     <tr>
						<td width="1%">
							<input id="score" required="required" value="Total"  
								size="3" style="border:none; background-color:transparent; text-align:center;"  readonly/>	
						</td>
						<td>
							<form:input path="rubricValues.dimension1Value" id="dimension1" value="${assignedPtasks.rubricValues.dimension1Value}" 
								style="border:none; text-align:center; background-color:transparent;" readonly="true" required="required"/>
						</td>
						<c:if test="${rubrics[0].rubricTypes.rubricTypeId == 3 || rubrics[0].rubricTypes.rubricTypeId == 4}">
							<td>
								<form:input path="rubricValues.dimension2Value" id="dimension2" value="${assignedPtasks.rubricValues.dimension2Value}" 
									style="border:none; text-align:center; background-color:transparent;" readonly="true" required="required" />
							</td>
							<td>
								<form:input path="rubricValues.dimension3Value" id="dimension3" value="${assignedPtasks.rubricValues.dimension3Value}" 
									style="border:none; text-align:center; background-color:transparent;" readonly="true"  required="required"/>
							</td>
							<td>
								<form:input path="rubricValues.dimension4Value" id="dimension4" value="${assignedPtasks.rubricValues.dimension4Value}" 
									style="border:none; text-align:center; background-color:transparent;" readonly="true" required="required" />
							</td>			
						</c:if>
						<td width="1%"> 
							<form:input path="rubricValues.totalScore" id="total" value="${assignedPtasks.rubricValues.totalScore}"   
								size="2" style="border:none; background-color:transparent; text-align:center;" readonly="true" required="required" />
							<input type="hidden" id="totalRub" value="${totalRub}">	
						</td> 
					</tr>	
			     
				</table>
			</div>
		</div>
		
		<div id="teacherComment">
			<h3>Teacher Comment and Percentage</h3>
			<div id="divTeacherComment" class="panel">
				<table align="center" style="padding: 1cm">
					<tr>
						<td align="right">
							Comment:&nbsp;
						</td>
						<td>
							<c:choose>
                       			<c:when test="${teacherObj ne null}">
                          			<form:textarea path="teacherComments" id="comment" required="required" cols="40" rows="2" onblur="checkTeacherComment()" maxlength="500"/>	<br>
                        		</c:when>
                       			<c:otherwise>
                        			 <textarea name="comment" id="comment" required="required" cols="40" readonly 
                        			 	rows="2">${assignedPtasks.teacherComments}</textarea>	<br>
                       			</c:otherwise>
                     		</c:choose>
									
						</td>
					</tr>
					<tr>
						<td align="right">
							Percentage:&nbsp;
						</td>
						<td>
							<c:choose>
                       			<c:when test="${teacherObj ne null}">
                          			<form:input path="studentAssignmentStatus.percentage" id="percentage" type="number" 
                          				step="0.01" required="required" onblur="checkParecentage()"/>&nbsp;%
                        		</c:when>
                       			<c:otherwise>
                        			 <textarea name="percentage" id="percentage" required="required" readonly cols="40" 
                        			 	rows="2">${assignedPtasks.studentAssignmentStatus.percentage}</textarea>&nbsp;%	
                       			</c:otherwise>
                     		</c:choose>
								
						</td>
					</tr>
				</table>
			</div>
		</div>
		
		<c:if test="${teacherObj ne null && tab != 'gradeBook'}">
		<table align="center" style="padding: 1cm">
			<tr>
				<td align="center">
				<input type="hidden" id="ruType" name="ruType" value="${rubrics[0].rubricTypes.rubricTypeId}" />
					<input type="button" onClick="checkBeforeSubmit()" class="button_green" id="btCreate" name="btCreate" height="52" width="50" value="Submit Changes" /><br>
					<font color="aeboad" size="4">Note: Please submit after finishing the grading.</font>
			</td>
			</tr>
		</table>
		</c:if>

	</form:form>
</div>
<!-- </body> -->
<!-- </html> -->