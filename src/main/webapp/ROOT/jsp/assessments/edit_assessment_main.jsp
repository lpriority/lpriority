
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit Assessments</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<link href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.min.css" rel="stylesheet">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>

<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript"
	src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript"
	src="/dwr/interface/AddStudentsToClassService.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript"
	src="/dwr/interface/curriculumService.js"></script>
<script type="text/javascript"
	src="/dwr/interface/assessmentService.js"></script>
<script type="text/javascript" src="resources/javascript/voice_recorder.js"></script>
<script src="resources/javascript/VoiceRecorder/recorder.js"></script>
	
<script>

	$(document).ready(function () {
	 	$('#returnMessage').fadeOut(5000);
	});
	

	function showQuestions(){
		
		var subjectId = document.getElementById("classId").value;
		var usedFor = document.getElementById("usedFor").value;
		var lessonId = document.getElementById("lessonId").value;
		var assignmentTypeId = document.getElementById("assignmentTypeId").value;
		var questionContainer = $(document.getElementById('questionDiv'));
		var answerContainer = $(document.getElementById('answerDiv'));
		var unitId = document.getElementById("unitId").value;
		var gradeId = document.getElementById("gradeId").value;
    	$(questionContainer).empty();  
    	$(answerContainer).empty();  
		
		if(assignmentTypeId =="select" || assignmentTypeId == "Assignment Type"){
			alert("Please select assignment type");
			return;
		}
		
		
    	// ADD TEXTBOX.
    	if(subjectId > 0 && unitId > 0){
    		$(questionContainer).append('<table width="100%" border="0" cellspacing="5" cellpadding="0">');
        	$(questionContainer).append('<tr><td width="562" align="center" colspan="4" style="padding-bottom: 2em;"><font color="blue" size="4">Edit your questions</font> </td></tr>');
	    	$.ajax({  
				url : "editAssessmentTypeView.htm", 
				data: "assignmentTypeId=" + assignmentTypeId + "&lessonId=" + lessonId + "&usedFor=" + usedFor+"&gradeId="+gradeId, 
	        	success : function(data) { 
	        		$(questionContainer).append(data);        		
	        	}  
	    	}); 
    	}else{
    		alert("Please select the filters");
			return;		
    	}
	}
	
	function helloAjax(){
		$("#assignmentTypeId").empty();
		$("#assignmentTypeId").append(
				$("<option></option>").val('').html('Assignment Type'));
		var questionContainer = $(document.getElementById('questionDiv'));
		var answerContainer = $(document.getElementById('answerDiv'));
		$(questionContainer).empty();  
    	$(answerContainer).empty();
	}	
	
	function playAudio(id){
		var path = document.getElementById(id).value; 
		if(path == ""){
			alert("No data found");
		}
		$.ajax({  
			url : "playAudio.htm", 
			data: "filePath=" + path, 
        	success : function(data) { 
        	}  
    	}); 		
	}
	
	function editQuestion(jacFileId,pos,length){
		var jacContainer = $(document.getElementById('jacDiv'+pos));
		for(var i=0;i<length;i++){
			if(i!=pos){
				$("#jacDiv"+i).hide();
			}else{
				$("#jacDiv"+i).show();
			}
		}
		$(jacContainer).empty();  
		$.ajax({  
			url : "getJacQuestions.htm", 
			data: "jacFileId=" + jacFileId, 
        	success : function(response) { 
        		$(jacContainer).append(response);			
        	}  
    	}); 
	}
	
	function jacUpdate() {	
		var formObj = document.getElementById("updateAssessments");
		var formURL = "updateJac.htm";
		var formData = new FormData(formObj);
		$.ajax({
			url: formURL,
			type: 'POST',
			data: formData,
			mimeType:"multipart/form-data",
			contentType: false,
			cache: false,
			processData:false,
			success: function(response,helloAjax){
				var length = document.getElementById("totalLength").value;
				for(var i=0;i<length;i++){
					$("#jacDiv"+i).hide();
				}
				if(helloAjax == "success"){
					systemMessage("Successfully Updated");
				}else{
					systemMessage("Failed to Updated");
				}
			}
		});	
		return false;
	}	
    
</script>

</head>

<body>
	<table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
	       		<tr>
                            <td vAlign=bottom align=right>
                                <div> 
                                	<c:choose>
    									<c:when test="${usedFor == 'rti'}">
       										<%@ include file="/jsp/assessments/rti_tabs.jsp" %>
    									</c:when>    									
    									<c:when test="${usedFor == 'homeworks' && teacherObj != null }">
       										<%@ include file="/jsp/assessments/homework_tabs.jsp" %>
    									</c:when>
										<c:otherwise>
											<%@ include file="/jsp/curriculum/curriculum_tabs.jsp" %>
										</c:otherwise>
									</c:choose>
                                	 
                                </div>
                            </td>
                        </tr>
     </table>
<form:form id="updateAssessments"  action="updateAssessments.htm" modelAttribute="questionsList" method="post" enctype="multipart/form-data">
	       <table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
                        <tr>
                            <td height="30" colspan="2" align="left" valign="left"  width="100%">
                               <table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left: 8em;">
                                                    <tr>
                                                    	<td>
                                                    		<input type="hidden" id="teacherObj" name ="teacherObj" value="${teacherObj}" />
                                                    		<input type="hidden" id="usedFor" name="usedFor" value="${usedFor}" />
                                                    		<input type="hidden" id="tab" name ="tab" value="${tab}" />
                                                    	</td>
                                                    </tr>
                                                    <tr>
                                                        <td height="20" align="left" class="label" style="width: 12em;">Grade &nbsp;&nbsp;&nbsp;
                                                            <select name="gradeId" class="listmenu" id="gradeId" onChange="loadClasses();helloAjax()">
                                                               <option value="select">Select Grade</option>
																	<c:forEach items="${grList}" var="ul">
			                											<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
																	</c:forEach>
                                                            </select><c:out value="${ul.gradeId}"></c:out>
                                                        </td>
                                                        <td height="20" class="label" style="width: 12em;padding-left: 2em;">Class &nbsp;&nbsp;&nbsp;
															<select id="classId" name="classId" class="listmenu" onchange="loadUnitName();helloAjax()">
																<option value="select">Select Subject</option>
															</select>
														</td>	
														<td  height="20" class="label" style="width: 12em;padding-left: 2em;">Unit &nbsp;&nbsp;&nbsp;
															<select id="unitId" name="unitId" class="listmenu" onchange="loadLessonNames();helloAjax()">
																<option value="select">Select Unit</option>
															</select>
														</td>													
														<td  height="20" class="label" style="width: 12em;padding-left: 2em;">Lesson &nbsp;&nbsp;&nbsp;
															<select id="lessonId" name="lessonId" class="listmenu" onchange="helloAjax();loadAssignments()">
																<option value="select">Select Lesson</option>
															</select>
														</td>
														<td height="20" class="label" style="padding-left: 2em;">Assignment &nbsp;&nbsp;&nbsp;
															<select id="assignmentTypeId" name="assignmentTypeId" class="listmenu" onchange="showQuestions()">
																<option value="select">Assignment Type</option>
															</select>
														</td>
                                                    </tr>                                                    
                                             	</table>
                                   </td></tr>
                                    <tr>
											
                                            <td id="appenddiv2" height="30" colspan="5" align="center" valign="middle" style="padding-bottom: 7px; padding-top: 7px">
												<font color="blue"><label id=returnMessage style="visibility:visible;">${helloAjax}</label></font>
                                            </td>
                                      	</tr>
                                                                                        
                                        <tr>
                                            <td  id="appenddiv"  colspan="9" align="center" valign="top" class="txtStyle">
                                            	<div id="main">
                                            		<div id="questionDiv"></div>
                                            		<div id="answerDiv"></div>
                                            	</div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td  id="appenddiv1"  colspan="9" align="left" valign="top" class="txtStyle"><div></div></td>                                            
                                        </tr>
                    </table>
          </form:form>
	<div id="subViewDiv" class="txtStyle"></div>
</body>
</html>