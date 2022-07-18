<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resources/css/notify/notify.css">
<link rel="stylesheet" href="resources/css/notify/prettify.css">
<link rel="stylesheet" href="resources/css/confirm_dialog/dialog.css"> 
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />

<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>  
<script src="resources/javascript/notify/notify.js"></script>
<script src="resources/javascript/notify/prettify.js"></script>
<script src="resources/javascript/confirm_dialog/dialog.js"></script>
<script type="text/javascript" src="resources/javascript/template/common_template.js"></script>
<script type="text/javascript" src="resources/javascript/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="resources/javascript/tinymce/jscripts/tiny_mce/plugins/asciimath/js/ASCIIMathMLwFallback.js"></script>
<script type="text/javascript" src="resources/javascript/voice_recorder.js"></script>
<script src="resources/javascript/VoiceRecorder/recorder.js"></script>
<script type="text/javascript" src="resources/javascript/performance_common.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
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
.ui-state-default a, .ui-state-default a:link, .ui-state-default a:visited{color:#1880C9;font-weight:bold;}
.ScrollStyle{
   max-height: 250px;
   overflow-y: scroll;
}

#divWorkMain{
	min-height: 400px;
	max-height: 400px;
	overflow-y: auto;
}

#divEvidence{
   height: 318px;
   overflow:auto;
}

#divAdditional{
   height: 138px;
   overflow:auto;
}

#divSelfAssessment{
	height: 492px;
	overflow:auto;
}

.notify-backdrop{
background-color: #e6e6e6;
}
.notify{
	width:auto;
	padding:none;
}
.notify.center{margin-left:-100px;}
.notify > button.close{
   opacity:1;
   color: white;
   text-shadow:0 1px 2px rgb(37, 37, 37);
}
.lnv-mask{
	background:rgba(119, 229, 242, 0.13);
}
.ui-widget-header{background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00E3FF), color-stop(1,#03b7cf) );border:1px solid #02b8d0;text-shadow:0 1px 1px rgb(0, 48, 53);margin:-.12em;}
</style>
<script type="text/javascript">
var AMTcgiloc = "http://www.imathas.com/cgi-bin/mimetex.cgi";
var editors = [];
var globalTotal = 0;
var globalevi = 1;
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
	theme_advanced_buttons1 : "fontselect,fontsizeselect,formatselect,bold,italic,underline,strikethrough,separator,sub,sup,separator,cut,copy,paste,undo,redo",
    theme_advanced_buttons2 : "justifyleft,justifycenter,justifyright,justifyfull,separator,numlist,bullist,outdent,indent,separator,forecolor,backcolor,separator,hr,link,unlink,image,table,code,separator",
	disk_cache : true,
	debug : false,
	image_advtab: true,
	theme_advanced_buttons3 : "",
    theme_advanced_fonts : "Arial=arial,helvetica,sans-serif,Courier New=courier new,courier,monospace,Georgia=georgia,times new roman,times,serif,Tahoma=tahoma,arial,helvetica,sans-serif,Times=times new roman,times,serif,Verdana=verdana,arial,helvetica,sans-serif",
    theme_advanced_toolbar_location : "top",
    theme_advanced_toolbar_align : "left",
    theme_advanced_statusbar_location : "bottom",
    file_browser_callback : 'myFileBrowser',
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
	theme_advanced_buttons1 : "asciimath,asciimathcharmap,sub,sup,separator,fontsizeselect,bold,italic,underline,strikethrough,separator,cut,copy,paste,undo,redo,separator,outdent,indent,separator,forecolor,backcolor,separator,table,separator",
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
	theme_advanced_buttons1 : "image",
    disk_cache : true,
	debug : false,
    theme_advanced_fonts : "Arial=arial,helvetica,sans-serif,Courier New=courier new,courier,monospace,Georgia=georgia,times new roman,times,serif,Tahoma=tahoma,arial,helvetica,sans-serif,Times=times new roman,times,serif,Verdana=verdana,arial,helvetica,sans-serif",
    theme_advanced_toolbar_location : "top",
    theme_advanced_toolbar_align : "left",
    theme_advanced_statusbar_location : "bottom",
    theme_advanced_path : false,
    statusbar : false,
    init_instance_callback  : "updateField",
    file_browser_callback : 'myFileBrowser',
    content_css : "resources/javascript/tinymce/jscripts/tiny_mce/css/content.css"
});

var winGlobal;
var fieldGlobal;
var createdBy = 0;
var taskId = 0;
function myFileBrowser(field_name, url, type, win) {	
	
	//alert("Field_Name: " + field_name + "nURL: " + url + "nType: " + type + "nWin: " + win); // debug/testing
	  // opening dialog
	 winGlobal = win;
	 fieldGlobal = field_name;
     var input = $(document.createElement('input'));
     input.attr("type", "file");
     input.attr('name',"file");   
     input.attr('onchange',"tinyfileSave(this)"); 
     input.trigger('click');
     return false; 
}

function tinyfileSave(thisVal){
	var tinyForm = document.createElement("form");
    tinyForm.setAttribute('method',"post");
    tinyForm.setAttribute('action',"tinynceFileSave.htm");
    tinyForm.setAttribute('name',"tinynceForm");
    tinyForm.setAttribute('enctype',"multipart/form-data");
    
    if(createdBy == 0){
    	createdBy = document.getElementById("createdByTiny");
    }
    if(taskId == 0){
    	taskId = document.getElementById("tinyAssignedFileTaskId");
    }
    	
	$(thisVal).appendTo(tinyForm);
    $(createdBy).appendTo(tinyForm);
    $(taskId).appendTo(tinyForm);
    
	var formURL = tinyForm.action;
	var formData = new FormData(tinyForm);
    
	$.ajax({
		url: formURL,
		type: 'POST',
		data: formData,
		mimeType:"multipart/form-data",
		contentType: false,
		cache: false,
		processData:false,
		success: function(response){
			var obj = JSON.parse(response);
			winGlobal.document.forms[0].elements[fieldGlobal].value = obj.imageURL;
			tinyForm.reset();
		}
	});	
}

function updateField(inst){
	var b=inst.getBody();
    $(b).focus();
   
   	$(b).blur(function() {  
   		var text = tinyMCE.get(inst.id).getContent();
   	    document.getElementById(inst.id+"Id").value = text; 
   	 	autoSave(inst.id,text,false);
     });
   	var text =document.getElementById(inst.id+"Id").value;
   	inst.setContent(text);
   	editors[inst.id] = text;
}


$( document ).ready(function() {	
	var dimCount = 1;
	if (document.getElementById("dimension2")) {
		dimCount = 4;
	}
	var totalRub = document.getElementById("totalRub").value;
	var position = 0;
	
	for(var i=1;i<=dimCount;i++){
		var dimention = document.getElementById("dimension"+i).value;	
		if(dimention > 0){
			position = totalRub-dimention;
			document.getElementById("dimension"+i+position).style.color = 'red';
		}		
	}
	
	for(var j=1;j<totalRub;j++){		
		var totalVal = document.getElementById("total"+j);
		totalVal.value = 0;
		for(var k=1;k<=dimCount;k++){
			var scoreVal = document.getElementById("score"+j).value;	
			if(document.getElementById("dimension"+k+j).style.color == 'red'){
				totalVal.value = parseInt(totalVal.value) + parseInt(scoreVal);
			}
		}
	}
	globalTotal = document.getElementById("total").value;		
});

$('#performanceDailog').on('dialogclose', function(event) {
	var tId = tinyMCE.activeEditor.id;
    var text = tinyMCE.activeEditor.getContent();	
	autoSave(tId,text,"login");
});
	

function addEvidence(assignedPtaskId){
	 var count=document.getElementById("count").value;
	 count++;
	 document.getElementById("count").value=count;
	var str = "Evidence&nbsp;"+count+"&nbsp;:&nbsp;<textarea name='evidence' id='evidence:"+count+"' onblur='saveEvidence("+count+","+assignedPtaskId+")' autofocus></textarea><br><br>";
	str+="<input type='hidden' id='evidenceId:"+count+"' value='0'>";
	$(addEvi).append(str);
    document.getElementById("evidence:"+count).focus();
   	
}

function saveEvidence(ths,assignedPtaskId){
 var evidence=document.getElementById("evidence:"+ths).value;
 var evidenceId=document.getElementById("evidenceId:"+ths).value;
 var ptaskGroupStudentId=0;
 $.ajax({  
		url : "saveStudentEvidence.htm",
		type: "POST",
		data: "assignedPtaskId="+assignedPtaskId+"&evidenceId="+evidenceId+"&evidence="+evidence+"&ptaskGroupStudentId="+ptaskGroupStudentId,
		success : function(response) { 
			document.getElementById("evidenceId"+ths).value=response.evidenceId;
		}
 });
}

</script>

<!-- </head> -->
<!-- <body> -->


	<table style="border: 1px solid black;background:rgb(235, 244, 251);" width="100%">		
		<tr>
			<td width="50%">
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
	<div id="workMain">
	<h3>
	<a>Main Work Area</a></h3> 
 		<div id="divWorkMain" class="panel">
 			<table style="border: 1px solid black;" width="100%">
 				<tr>
					<td colspan="4" align="center" style="border: 1px solid black;">
					   <div class="button_green" align="right" onclick="printPage()" style="color:white;font-size: 13.5px;text-shadow: 0 1px 2px rgb(23, 47, 59);margin:10px 40px;">Print</div>
					   <a  class="button_green" target="_blank" href="https://www.draw.io/" style="color:white;font-size: 13.5px;text-shadow: 0 1px 2px rgb(23, 47, 59);margin:10px 40px;">Draw</a>
					   <div class="button_green" align="right" onclick="record()" style="color:white;font-size: 13.5px;text-shadow: 0 1px 2px rgb(23, 47, 59);margin:10px 40px;">Record</div>&nbsp;
					   <div class="button_green" align="right" onclick="stopIt('audioContent', 'playerId')" style="color:white;font-size: 13.5px;text-shadow: 0 1px 2px rgb(23, 47, 59);margin:10px 40px;">Stop</div>
						<audio id="playerId" style="width: 3cm;vertical-align:middle;" type="audio/wav"
							src="loadDirectUserFile.htm?usersFilePath=${audioFiles.filePath}" controls></audio>
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
 		<table align="right"><tr>
 						<td align="center"> 
							<input type="hidden" name="assignedTaskId" id="assignedTaskId" value="${assignedPtasks.assignedTaskId}"/>
							<div id="supportEvid" onClick="addEvidence(${assignedPtasks.assignedTaskId})" class='button_green' style="font-size: 0.9em;">Add Support Evidence</div> <br><br>
                            
					</td> 
					</tr> </table><br>
 			<form name="perEvidenceForm" action="" id="perEvidenceForm" method="post" style="min-height: 300px;max-height: 300px;overflow-y: auto;"> 
 				
 				<table align="center" style="padding: 1cm">
 					
					<tr><td>
					<c:set var="coun" value="0" />
                            <div id="addEvi"> 
                            <c:forEach items="${assignedPtasks.studentPtaskEvidence}" var="evid">
                            <c:set var="coun" value="${coun+1}" />
                             Evidence&nbsp;<c:out value="${coun}" />&nbsp;:&nbsp;<textarea name="evidence" id="evidence:${coun}" onblur="saveEvidence(${coun},${assignedPtasks.assignedTaskId})"><c:out value="${evid.evidenceDesc}" /></textarea><br><br>
                              <input type="hidden" id="evidenceId:${coun}" value="${evid.studentPtaskEvidenceId}" />
                            </c:forEach>
                            <input type="hidden" name="count" id="count" value="${coun}"/>
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
							<input type="hidden" name="tinyAssignedFileTaskId" id="tinyAssignedFileTaskId" value="${assignedPtasks.assignedTaskId}"/>
							<input type="hidden" name="createdBy" id="createdBy" value="${createdBy}"/>
							<input type="hidden" name="createdByTiny" id="createdByTiny" value="${createdBy}"/>
							Additional Media: <input name="file" id="fileId" onchange="fileSave(this)" type="file"/> <br><br>
							Uploaded file:<label id="uploaded" style="color: blue;">${assignedPtasks.filepath}</label>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
<form:form id="performanceTestForm" modelAttribute="assignedPtasks" action="submitPerformanceTest.htm" method="POST">
		<div id="selfAssessment">
		<h3>Student Self Assessment Score</h3> 
 		<div id="divSelfAssessment" class="panel"> 
		<form:hidden path="assignedTaskId" id="assignedTaskId"/>
		<form:hidden path="studentAssignmentStatus.studentAssignmentId" id="studentAssignmentId"/>
		<form:hidden path="performanceTask.questionId" id="questionId"/>
		<form:hidden path="rubricValues.rubricValuesId" id="rubricValuesId"/>	
		<form:hidden path="writing" id="writingId"/>	
		<form:hidden path="uploadarea" id="uploadareaId"/>
		<form:hidden path="calculations" id="calculationsId"/>	
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
								onclick="selectRubric('dimension1',${rubric.score},${dec},${totalRub})" readonly
								style="border:none; text-align:center; background-color:transparent;">${rubric.dimension1}</textarea>
						</td>
						<c:if test="${rubric.rubricTypes.rubricTypeId == 3 || rubric.rubricTypes.rubricTypeId == 4}">
							<td>
								<textarea  id="dimension2${dec}" name="dimension2" required="required" cols="25" rows="5" 
									onclick="selectRubric('dimension2',${rubric.score},${dec},${totalRub})" readonly
									style="border:none; text-align:center; background-color:transparent;">${rubric.dimension2}</textarea>
							</td>
							<td>
								<textarea  id="dimension3${dec}" name="dimension3" required="required" cols="25" rows="5" 
									onclick="selectRubric('dimension3',${rubric.score},${dec},${totalRub})" readonly
									style="border:none; text-align:center; background-color:transparent;">${rubric.dimension3}</textarea>
							</td>
							<td>
								<textarea id="dimension4${dec}" name="dimension4" required="required" cols="25" rows="5" 
									onclick="selectRubric('dimension4',${rubric.score},${dec},${totalRub})" readonly
									style="border:none; text-align:center; background-color:transparent;">${rubric.dimension4}</textarea>
							</td>			
						</c:if>
						<td width="1%">
							<input id="total${dec}" required="required"	size="2" style="border:none; background-color:transparent; text-align:center;" 
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
	</div></div>
	<c:if test="${userReg.user.userType != 'parent'}">
		<table align="center" style="padding: 1cm">
			<tr>
				<td align="center">
				<input type="hidden" name="ruType" id="ruType" value="${rubrics[0].rubricTypes.rubricTypeId}" />
					<input type="submit" onClick="return checkSubmit()"  class="button_green" id="btSubmitChanges" name="btSubmitChanges" height="52" width="50"><br>
					<font color="aeboad" size="3">Note: Please submit after finishing the assignment.</font>				
				</td>
			</tr>
		</table>
	</c:if>
</form:form>
<!-- </body> -->
<!-- </html> -->