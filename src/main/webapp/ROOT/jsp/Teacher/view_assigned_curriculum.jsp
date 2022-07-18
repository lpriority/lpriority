<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>View Curriculum</title>
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script src="resources/javascript/teacher_popup.js"></script>
<script type="text/javascript">
var lastHeight = $('#body').outerHeight();
$('#body').bind("DOMSubtreeModified",function(){
	currentHeight = $('#body').outerHeight();
	var height = $(document).height();
	var changedHeight = (currentHeight-lastHeight);
	if(lastHeight != currentHeight){
		
		lastHeight = currentHeight;
	}
});
function getAssignedCurriculum(){
			var csId = document.getElementById('csId').value;
			var assignedCurriculumDiv = $(document.getElementById('assignedCurriculumDiv'));
		    if(csId && csId > 0){	
				$(assignedCurriculumDiv).empty();
				$("#loading-div-background").show();
				$.ajax({
					type : "GET",
					url : "getAssignedCurriculum.htm",
					data : "csId=" + csId,
					success : function(response) {
						$(assignedCurriculumDiv).append(response);
						$("#loading-div-background").hide();
					}
				});		
			}else{
				$(assignedCurriculumDiv).empty();
			}
	}
	$(document).ready(function () {
 	 	$('#returnMessage').fadeOut(4000);
 	});
	function clearDiv(){
		var assignedCurriculumDiv = $(document.getElementById('assignedCurriculumDiv'));
		$(assignedCurriculumDiv).empty();
	}
</script>
</head>
<body>
<form name="assignedCurriculum">

<table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
       <tr>
	       <td  height="20" style="color:white;font-weight:bold" >
                <input type="hidden" id="tab" name ="tab" value="${tab}" />
                <input type="hidden" id="subTab" name ="subTab" value="${subTab}" />
                <input type="hidden" id="isGroup" name ="isGroup" value="${isGroup}" />
           </td>
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

          <table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0 class="title-pad">
					
		  			<tr>
							 <td height="30" align="left" valign="left"  width="100%" >
								<table style="">
									<tr>
										<td>
											<label class="label">Grade&nbsp;&nbsp; </label>
											<label> <select name="gradeId"
												class="listmenu" id="gradeId"
												onChange="getGradeClasses();clearDiv()" 
												required="required">
													<option value="select">select grade</option>
													<c:forEach items="${teacherGrades}" var="ul">
														<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
													</c:forEach>
											</select>
										</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td align="left" valign="middle">
											<label class="label">Class &nbsp;</label>
											<select
											 id="classId" name="classId"
											class="listmenu" onChange="getClassSections();clearDiv()"
											required="required">
												<option value="select">select subject</option>
										</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

										<td align="left" valign="middle">
											<label class="label">Section &nbsp;</label>
											<select
												id="csId" class="listmenu"
												 onChange="getAssignedCurriculum()"
												required="required">
												<option value="select">select Section</option>
											</select></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center" valign="top" bgcolor="#cccccc">

							</td>
						</tr>
						<tr>
							<td colspan="2" align="left" valign="top" id="assignedCurriculumDiv">

							</td>
						</tr>
						<tr>
							<td id="appenddiv2" height="30" colspan="7" align="center" valign="middle">
								<font color="blue">
									<label id=returnMessage style="visibility: visible;">${helloAjax}</label>
								</font>
							</td>
						</tr>
					</table>
				</form>
</body>
</html>