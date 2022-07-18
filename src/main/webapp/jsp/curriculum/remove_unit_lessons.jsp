
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
<title>Remove Units & Lessons</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
    src="resources/javascript/jquery/jquery-2.1.4.js"></script>

<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript"
	src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript"
	src="/dwr/interface/AddStudentsToClassService.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript"
	src="/dwr/interface/curriculumService.js"></script>


<script>

	$(document).ready(function () {
	 	$('#returnMessage').fadeOut(4000);
	});
	

	function changeMe(){
		
		var subjectId = document.getElementById("classId").value;
		var unitId = document.getElementById("unitId").value;
		var lessonContainer = $(document.getElementById('lessonDiv'));
    	$(lessonContainer).empty();
		if(subjectId=="select"){
			alert("Please select a subject");
			return;
		}
        $.ajax({  
			url : "editLessonsView.htm", 
			data: "unitId=" + unitId, 
	       	success : function(data) { 
	       		$(lessonContainer).append(data);
	       	}  
	    }); 
	}
	
	function toggleRadio(thisVal){
		document.getElementById("removeForm").reset();
		thisVal.checked=true;
		if(thisVal.id == 'uId'){
			$('#lesId').hide();
			//document.getElementById("lesId").style.display = "none";			
		}else{
			$('#lesId').show();
		}		
	}
	
	function deleteThis(){
		var unit = document.getElementById("uId").checked;
		var lesson = document.getElementById("lId").checked;
		var unitId = document.getElementById("unitId").value;
		var lessonId = document.getElementById("lessonId").value;
		var subjectId = document.getElementById("classId").value;
		var gradeId = document.getElementById("gradeId").value;
		
		if(unit){
			lessonId = 0;	
		}
		if(gradeId != "select" && subjectId != "select" && unitId != "Select Unit" && lessonId >= 0){
			$.ajax({  
				url : "removeUnitLesson.htm", 
				data: "unitId=" + unitId+"&lessonId=" + lessonId, 
			    success : function(data) { 
			    	systemMessage(data.helloAjax);
			    	document.getElementById("removeForm").reset();
			    	$('#lesId').hide();
			    }  
			}); 
		}else{
			alert("Please fill the filters");
		}
		return false;
	}
</script>
</head>
<body>
	<table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
						<tr>
                            <td vAlign=bottom align=right>
                                <div> 
                                	<%@ include file="/jsp/curriculum/curriculum_tabs.jsp" %> 
                                </div>
                            </td>
                        </tr>
    </table>
	<form id="removeForm">
	<table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
                        <tr>
                            <td height="30" colspan="2" align="left" valign="left"  width="100%">
                               <table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left: 8em;">    
                                                    <tr><td><input type="hidden" id="teacherObj" value="${teacherObj}" /></td></tr>
                                                    <tr>
                                                    	<td colspan="4" align="left" class="txtStyle" style="padding-bottom: 2em;">
                                                    		Remove :
                                                    		<input type="radio" class="radio-design" id="uId" name="option" checked="checked" onclick="toggleRadio(this)"/><label for="uId" class="radio-label-design">Units</label>&nbsp;&nbsp;
                                                    		<input type="radio" class="radio-design" id="lId" name="option" onclick="toggleRadio(this)"/><label for="lId" class="radio-label-design">Lessons</label> 
                                                    	</td>
                                                    </tr>
                                                    <tr>
                                                        <td height="20" align="left" class="label" style="width: 12em;">Grade &nbsp;&nbsp;&nbsp;
                                                            <select name="gradeId" class="listmenu" id="gradeId"  onChange="loadClasses()">
                                                               <option value="select">select grade</option>
																	<c:forEach items="${grList}" var="ul">
			                											<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
																	</c:forEach>
                                                            </select>
                                                        </td>
                                                        <td height="20" class="label" style="width: 12em;padding-left: 2em;">Subject &nbsp;&nbsp;&nbsp;<select id="classId" name="classId" class="listmenu" onChange="loadUnitName()">
																<option value="select">select subject</option>
															</select>
														</td>
														<td height="20" class="label" style="width: 12em;padding-left: 2em;">Units &nbsp;&nbsp;&nbsp;<select id="unitId" name="unitId" class="listmenu" onChange="loadLessonNamesByUser()">
																<option value="select">select Unit</option>
															</select>
														</td>
														<td height="20" style="padding-left: 2em;" class="label">
																<div id="lesId" style="display: none;">
																Lessons :<select id="lessonId" name="lessonId" class="listmenu" onChange="">
																<option value="select">Select Lesson</option>
															</select></div>
														</td>
                                                    </tr>
                                             	</table>
                                  </td></tr>
                           <tr>
                                            <td id="appenddiv2" height="30" colspan="5" align="center" valign="middle">
												<font color="blue"><label id=returnMessage style="visibility:visible;">${helloAjax}</label></font>
                                            </td>
                                      	</tr>
                                      	<tr>
											<td align="center" colspan="3">
												<img src="images/Common/submitChangesUp.gif" width="100" onclick="return deleteThis();" height="20">  
											</td>
										</tr> 
                    </table>
         	  </form>
	</body>
</html>