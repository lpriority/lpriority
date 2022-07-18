<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title><c:choose><c:when test="${(tab == 'accuracySelfReview')}">Accuracy Self Review</c:when><c:otherwise>Accuracy Peer Review</c:otherwise> </c:choose> </title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="resources/javascript/Student/student_test_results.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/early_literacy.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/studentTestResultsService.js"></script>
<script type="text/javascript" src="/dwr/interface/earlyLiteracyTestsService.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<link href="resources/javascript/jplayer/skin/blue.monday/css/jplayer.blue.monday.min.css" rel="stylesheet" type="text/css"/>
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
</head>
    <body>
    	<table style="width:100%">
							<tr>
								<td style="" align="right">
									<input type="hidden" id="usedFor" name="usedFor" value="${usedFor}" />
									<input type="hidden" id="tab" name="tab" value="${tab}" /></td>
								<td style="" align="right" valign="bottom">
									<div>
										
												<%@ include file="view_rti_tabs.jsp"%>
											

									</div>
								</td>
							</tr>
		</table>
		<table width="99.8%" border="0" cellspacing="0" cellpadding="0" class="title-pad">
		<tr>
             <td colspan="6" class="sub-title title-border" height="40" align="left">
             <c:choose>
             <c:when test="${gradingTypesId==2}">Accuracy Self Assessment</c:when>
              <c:when test="${gradingTypesId==3}">Accuracy Peer Review</c:when>
             </c:choose>
              <br> </td>
        </tr>
        </table>
        <table width='100%' class=""><tr>
                            <td height="30" colspan="2" align="left"><table class="title-pad" width="100%" border="0" cellpadding="0" cellspacing="0" style="padding-top: 0.5em">
                            		<tr>
                                        <td colspan="6" class="perinfohead"></td>
                                    </tr>
                                    <tr><td>&nbsp;</td></tr>
                                    <tr>
                                    	
                                      <!--    <td align="left" width="150"><label class="label">Grade </label>&nbsp;&nbsp;
                                        	<input type="text"  style="width:100px;" name="gradeName" id="gradeName" value ="${grade.masterGrades.gradeName}" disabled /></td>
                                        -->
                                        <td width="150" align="left" valign="middle">
                                            <label class="label">Class </label>&nbsp;&nbsp;
                                            
                                            <select	id="classId" name="classId" class="listmenu" onchange="getBenchmarkTests(${gradingTypesId},${assignmentTypeId})" style="width:120px;">
												<option value="select">select class</option>
										 		<c:forEach items="${GradeClasses}" var="ul">
										 			<c:set var="itemNums" scope="request" value="${itemNums + 1}" />
													<option value="${ul.gradeClasses.studentClass.classId}">${grade.masterGrades.gradeName}_${ul.gradeClasses.studentClass.className}</option>
												</c:forEach> 
											</select></td>
											
											<script>
													<c:if test='${itemNums >0}' >
													classId.options[1].selected = true;
														$(function () {
														    $("select#classId").change();
														});
													</c:if>	
											</script>
												
											
                                        <td width="400" align="left" valign="middle">&nbsp;</td>
                                        <td width="200" align="left" valign="middle">&nbsp;</td>
                                    </tr>
                                </table></td>
                        </tr></table>
                     <%--    <tr>
							<td height="2" colspan="2">
		     
			<div id="videoDiv">
			 <c:choose>
             <c:when test="${gradingTypesId==2}">
				<a href="#" onClick="openVideoDialog('student','self_fluency_grading','videoDailog')" class="demoVideoLink"><i class="ion-videocamera" aria-hidden="true" style="font-size:26px;font-weight:bold;color:#e23c00;"></i>&nbsp;video for Accuracy Self Assessment</a>
		 	</c:when>
		 	 <c:when test="${gradingTypesId==3}">
		 		<a href="#" onClick="openVideoDialog('student','peer_fluency_grading','videoDailog')" class="demoVideoLink"><i class="ion-videocamera" aria-hidden="true" style="font-size:26px;font-weight:bold;color:#e23c00;"></i>&nbsp;video for Accuracy Peer Review</a>
		 	</c:when>
		 	</c:choose>
		 	</div>
			</td>
	   
						</tr> --%>
        <table style="width:80%" align="center">
				<tr>
					<td style="width:100%" colspan="5" align="center" valign="top">
					<table style="width:100%">
							
							<tr>
								<td style="" align="right"></td>
								<td style="" align="right" valign="bottom"></td>
							</tr>
                        
                        <tr>
                            <td width='100%'></td>
                        </tr>
                        <tr><td width='100%'>&nbsp;</td></tr>
                        <tr>
                          
                            <td width='100%' height="0" align="center" valign="top" id="BenchmarkTestResultsDiv" >
                            </td>
                        </tr>
                       
                    </table></td>
            </tr></table>
            <table width='80%' align='center'>
             <tr>
               
                 <td width='80%' height="0" align="center" valign="top" id="BenchmarkQuestionsList" >
              </td>
                        </tr>
                         
            </table>
            <div id="videoDailog" title="Grade Accuracy" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
    </body>
</html>