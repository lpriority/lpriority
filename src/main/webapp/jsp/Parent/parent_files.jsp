<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Parent Files</title>        
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="/dwr/interface/parentService.js"></script>
<script type="text/javascript" src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript" src="/dwr/interface/teacherSchedulerService.js"></script>
<script type="text/javascript" src="/dwr/interface/curriculumService.js"></script>
<script type="text/javascript" src="resources/javascript/my_files.js"></script>
<script>
function loadStudentClasses() {
	var studentId = $('#studentId').val();
	$("#classId").empty();
	$("#classId").append(
			$("<option></option>").val('').html(
					'Select Subject'));
	if(studentId != "select" && studentId != ''){		
		$.ajax({
				type : "GET",
				url : "loadClasess.htm",
				data : "studentId=" + studentId,
				success : function(response) {					
					var clasess = response.classMap;
					$.each(clasess, function(index, value) {
						$("#classId").append(
								$("<option></option>").val(index).html(
										value));
					});
				}
			});
	}else{
		$("#showFilesDiv").html('');
		return false;
	}
}
function displayLesson(){
	var studentId=document.getElementById("studentId").value;
	var unitId = document.getElementById('unitId').value;
	var fileType = document.getElementById('fileType').value;
	var gradeId = document.getElementById('gradeId:'+studentId).value;
	var classId = document.getElementById('classId').value;
	var page = "";
	if(unitId == 'select' || unitId == 'Select Unit' || unitId == 0){
		$("#showFilesDiv").html('');
		return false;
	}else{
		if(document.getElementById('page'))
			page = document.getElementById('page').value;
		 $("#loading-div-background").show();
		$.ajax({
			url : "loadLessons.htm", 
		    data : "unitId="+unitId+"&fileType="+fileType+"&gradeId="+gradeId+"&classId="+classId+"&page="+page,
		    success : function(response) {
		       	$("#showFilesDiv").html(response);
		    	$("#loading-div-background").hide();
			}  
		}); 	
	}
}
</script>
</head>

<body>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="title-pad">
      <tr><td width="184" class="sub-title title-border" height="40" align="left"><c:out  value="${title}"/></td></tr>
     </table>
	  <input id="fileType" name = "fileType" value="${fileType}" type="hidden"/>	
        <table border=0 cellSpacing=0 cellPadding=0 width="100%" align=center height=69>
            <tr>
                <td  vAlign=top width="100%" colSpan=3 align=middle>

                    <table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0 class="title-pad">
                       
                        
                        <tr align="center"><td colspan="3"><font color="red" size="4px" ><label  id=returnMessage ></label></font></td></tr>
                      
                      
                        <tr><td height="60" colspan="2" align="left" valign="middle" width="100%">
                                <table id="dropdownTable" width="70%" height="30" border="0" cellpadding="0" cellspacing="0">                                	
                                    <tr>                                        	
                                       	<form name="createFolder" action="displayCreateFolder.htm" method="post">
											<td width="160" align="left" valign="center">
											   <label style="font-size:3;" class="label">Child&nbsp;&nbsp;&nbsp;</label>
												<select id="studentId" name="studentId" class="listmenu" onchange="loadStudentClasses()" >
													<option value="select">Select Child</option>
													<c:forEach items="${studentLst}" var="children">															
		                								<option value="${children.studentId}">${children.userRegistration.title} ${children.userRegistration.firstName} ${children.userRegistration.lastName}</option>
		                							</c:forEach>												
												</select>
												<c:forEach items="${studentLst}" var="child">															
		                								<input type="hidden" id="gradeId:${child.studentId}" name="childId" value="${child.grade.gradeId}" />
													</c:forEach>
											</td>        									
       										<td width="160" align="left" valign="center">    
       										<font size='3'><label style="font-size:3;" class="label">Class&nbsp;&nbsp;&nbsp;</label></font>                                   		
       											<select id="classId" name="classId" class="listmenu"  onchange="loadUnitName()">
													<option value=0>select class</option>														
												</select> 
											</td>
											<td width="160" align="left" valign="center">
											<font size='3'><label style="font-size:3;" class="label">Unit&nbsp;&nbsp;&nbsp;</label></font>
												<select id="unitId" name="unitId" class="listmenu" onchange="displayLesson();" >
													<option value="select">select Unit</option>
												</select>
											</td>  		
										<td width="100" align="left" valign="center">&nbsp;</td>
											<td width="100" align="left" valign="center">&nbsp;<input type="hidden" id="fileType" name="fileType" value="${fileType}" /> </td>								                                        
											                                       	
                                       	</form>                      		
                            		</tr>                            	
                        </table></td>
                </tr>
                <tr>
                    <td height="2" colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td height="0" colspan="2" align="center" width="100%">
                        <table width="80%" border="0" cellpadding="0" cellspacing="0" id="LessonContent">
                        <tr><td><div id="showFilesDiv"></div></td></tr>
                        
                        </table></td>
    </tr>
</table></td></tr></table>
</body>
</html>