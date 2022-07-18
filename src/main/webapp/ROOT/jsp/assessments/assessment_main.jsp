
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html>
<head>
<link rel="icon" type="image/ico" href="images/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Assessments</title>
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/voice_recorder.js"></script>
<script type="text/javascript" src="resources/javascript/VoiceRecorder/recorder.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/curriculum.js"></script>	
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/javascript/jplayer/skin/blue.monday/css/jplayer.blue.monday.min.css" rel="stylesheet" type="text/css"/>
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
<script>
	$(document).ready(function () {
	 	$('#returnMessage').fadeOut(4000);
	});
	function helloAjax(){
		var questionContainer = $(document.getElementById('questionDiv'));
		var answerContainer = $(document.getElementById('answerDiv'));
		$(questionContainer).empty();  
    	$(answerContainer).empty(); 
    	$('#backBtn').hide();
    	$('#btAdd').hide();
	}	
</script>
</head>
<body>
	<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
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
<form:form id="questionsForm"  action="updateAssessments.htm" modelAttribute="questionsList" method="post" enctype="multipart/form-data">
	       <table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0>	       		
                        <tr><td>
                        	<input type="hidden" id="teacherObj" name ="teacherObj" value="${teacherObj}" />
                            <input type="hidden" id="usedFor" name="usedFor" value="${usedFor}" />
                            <input type="hidden" id="tab" name ="tab" value="${tab}" />
                            <input type="hidden" id="jacFileName" name="jacFileName" value="">
                        </td></tr>
                        <tr>
                            <td height="30" align="left" width="100%"  class="heading-up">
                            	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left" class="title-pad heading-up heading-up"> 
                                                    <tr>
                                                        <td height="20" width="210px" valign="middle" class="label">Grade &nbsp;&nbsp;&nbsp;
                                                            <select  name="gradeId" class="listmenu" id="gradeId" onChange="helloAjax();getGradeClasses()">
                                                               <option value="select">Select Grade</option>
																	<c:forEach items="${grList}" var="ul">
			                											<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
																	</c:forEach>
                                                            </select><c:out value="${ul.gradeId}"></c:out>
                                                        </td>
                                                        <td height="20" width="210px" valign="middle" class="label" >Class &nbsp;&nbsp;&nbsp;
															<select  id="classId" name="classId" class="listmenu" onchange="helloAjax();getAssignmentTypes()">
																<option value="select">Select Subject</option>
															</select>
														</td>	
														<td height="20" width="270px" valign="middle" class="label">Assignment &nbsp;&nbsp;&nbsp;
															<select  id="assignmentTypeId" name="assignmentTypeId" class="listmenu" onchange="helloAjax();getUnits()">
																<option value="select">Assignment Type</option>
															</select>
														</td>
														
														 <td  height="20"width="210px" valign="middle" class="label" id="showUnits">Unit &nbsp;&nbsp;&nbsp;
															<select  id="unitId" name="unitId" class="listmenu" onchange="helloAjax();getLessonByUnitId()">
																<option value="select">Select Unit</option>
															</select>
														</td>													
														<td  height="20" width="230px" valign="middle" class="label" id="showLessons">Lesson &nbsp;&nbsp;&nbsp;
															<select  id="lessonId" name="lessonId" class="listmenu" onchange="helloAjax();loadQuestions()">
																<option value="select">Select Lesson</option>
															</select>
														</td> 
														
				                                         <td height="20" class="label" width="150px" align="left">
														   <div class="button_green" id="btAdd" name="btAdd" height="52" width="50" onclick="showQuestions(-1)" style="display:none;padding:0.2px 10px;"> Add Assessments</div>
														</td>
														<td align="center" style="width: 4em;">
															<div id="backBtn" style="display:none;cursor: hand; cursor: pointer;" onclick="backToLessonsTab()">
																<table><tr>
																<td height="20" class="label" style="width: 12em;font-family:Candara, Calibri, Segoe, Segoe UI, Optima, Arial, sans-serif;" align="left">
																<i class="fa fa-arrow-left" aria-hidden="true"></i> Back
																</td>
																</tr></table>
															</div>
														</td>
                                                    </tr>
                                                   <%--  <c:if test="${usedFor == 'rti'}">
	                                                    <tr><td colspan="5"><br>
	                                                    <a href="#" onClick="openVideoDialog('teacher','create_fluency','videoDailog')" class="demoVideoLink" style="padding-left:5px;"><i class="ion-videocamera" aria-hidden="true" style="font-size:26px;font-weight:bold;color:#e23c00;"></i>&nbsp;video for Create Fluency</a>
	                                                    <a href="#" onClick="openVideoDialog('teacher','assign_fluency','videoDailog1')" class="demoVideoLink"><i class="ion-videocamera" aria-hidden="true" style="font-size:26px;font-weight:bold;color:#e23c00;"></i>&nbsp;video for Assign Fluency</a>
			 	                                       </td>
	                                                   </tr>
                                                   </c:if> --%>
                                                    <tr>
                                                    	<td colspan="5">&nbsp;</td>
                                                    	<td id="optionId" width="" height="20" style="visibility:hidden;padding-left: 2em;" class="label" align="left">
                                                    		No.of Options :
                                                        	<input type="text" name="noOfOptions" id="noOfOptions" onclick="helloAjax();" size="5" maxlength="2" />                                                            
                                                        </td>                                                         
                                                    </tr>
                                             	</table>
                                   		</td></tr>
                                        <tr>
                                            <td  id="appenddiv"  colspan="9" align="left" valign="top" class="txtStyle">
                                            	<div id="main">
                                            		<div id="questionDiv"></div>
                                            		<div id="answerDiv"></div>
                                            	</div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td  id="appenddiv1"  colspan="9" align="left" valign="top" class="txtStyle"><div></div></td>                                            
                                        </tr>
                                        <tr><td height=10 colSpan=3></td></tr> 
                    </table>
          </form:form>
           <div id="videoDailog" title="Create Fluency" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
           <div id="videoDailog1" title="Assign Fluency" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
	 <div id="dialog"  style="display:none;overflow-y:auto; overflow-x:hidden;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title=""><iframe  id ="iframe" src="" width="100%" height="96%" src=""  style=""><%@ include file="../CommonJsp/include_resources.jsp" %></iframe></div>
</body>
</html>