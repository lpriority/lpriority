
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
<title>Edit Lessons</title>
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
		var gradeId = document.getElementById("gradeId").value;
		var lessonContainer = $(document.getElementById('lessonDiv'));
    	$(lessonContainer).empty();
		if(subjectId=="select"){
			alert("Please select a subject");
			return;
		}
		if(unitId == "select" || unitId == "Select Unit"){
			return;
		}
		if(unitId > 0 && subjectId > 0 && gradeId > 0)
        $.ajax({  
			url : "editLessonsView.htm", 
			data: "unitId=" + unitId, 
	       	success : function(data) { 
	       		$(lessonContainer).append(data);
	       	}  
	    }); 
	}

	function clearDiv(){
		$("#unitId").empty();
		$("#unitId").append(
				$("<option></option>").val('').html('Select Unit'));
		$("#lessonDiv").empty();
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
<form:form id="editLessons"  action="updateLessons.htm" modelAttribute="editLessons" method="post">
	<table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
                        <tr>
                            <td height="30" colspan="2" align="left" valign="left"  width="100%">
                            	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left" style="padding-left: 8em;"> 
                                                    <tr><td><input type="hidden" id="teacherObj" value="${teacherObj}" /></td></tr>
                                                    <tr>
                                                        <td height="20" align="left" class="label" style="width: 12em;">Grade &nbsp;&nbsp;&nbsp;
                                                            <select name="gradeId" class="listmenu" id="gradeId"  onChange="clearDiv();loadClasses();">
                                                               <option value="select">select grade</option>
																	<c:forEach items="${grList}" var="ul">
			                											<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
																	</c:forEach>
                                                            </select><c:out value="${ul.gradeId}"></c:out>
                                                        </td>
                                                        <td height="20" class="label" style="width: 13em;padding-left: 2em;">Subject &nbsp;&nbsp;&nbsp;
															<select id="classId" name="classId" class="listmenu" onChange="clearDiv();loadUnitName();">
																<option value="select">select subject</option>
															</select>
														</td>
														<td height="20" class="label" style="padding-left: 2em;">Units &nbsp;&nbsp;&nbsp;
															<select id="unitId" name="unitId" class="listmenu" onChange="changeMe()">
																<option value="select">select Unit</option>
															</select>
														</td>
                                                    </tr>
                                             	</table>
                                    </td></tr>
                                    <tr>
                                         <td  id="appenddiv"  colspan="9" align="center" valign="top"  >
                                            	<div id="main">
                                            		<div id="lessonDiv"></div>
                                            	
                                            	</div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="appenddiv2" height="30" colspan="5" align="center" valign="middle">
												<font color="blue"><label id=returnMessage style="visibility:visible;">${helloAjax}</label></font>
                                            </td>
                                      	</tr>
                    </table>
            </form:form>
</body>
</html>