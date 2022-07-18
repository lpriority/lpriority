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
<title>Teacher Files</title>        
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="/dwr/interface/curriculumService.js"></script>
<script type="text/javascript" src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript" src="/dwr/interface/AddStudentsToClassService.js"></script>
<script type="text/javascript" src="resources/javascript/my_files.js"></script>
<script > 
$(function() {	
	var gradeId = document.getElementById('gradeId');	
	if(gradeId != null ){
		if(gradeId.value){
			loadFolders(gradeId);
		}
	}else{
		loadFolders();
	}
});
</script>
</head>
<body>
        <table border=0 cellSpacing=0 cellPadding=0 width="100%" align=center height=69>
            <tr>
                <td  vAlign=top width="100%" colSpan=3 align=middle>

                    <table width="70%" border=0 align="center" cellPadding=0 cellSpacing=0>
                        <tr>
                            <td height=65 colSpan=2><div align="right"></div></td></tr>
                        <tr>
                            <td height=30 align=right></td>
                            <td vAlign=bottom align=right>
								<c:if test="${userReg.user.userType == 'teacher'}">
	                                <table width="206" height="29" border="0" cellpadding="0" cellspacing="0">
	                                    <tr>
	
	                                        <td vAlign=bottom width=103>
	                                        	<c:choose>
	    									   		<c:when test="${fileType == 'public'}">
	       												<a href="displayTeacherClassFiles.htm?fileType=public"><img alt="" border=0 src="images/Teacher/CF.gif" width=103 height=27 /></a>
	    											</c:when>
											    	<c:otherwise>
											    		<a href="displayTeacherClassFiles.htm?fileType=public"><img alt="" border=0 src="images/Teacher/CF1.gif" width=103 height=27 /></a>
											    	</c:otherwise>
											    </c:choose> 
	                                        </td>
	                                        <td vAlign=bottom width=103>
	                                        	<c:choose>
	    									   		<c:when test="${fileType == 'private'}">
	       												<a href="displayTeacherClassFiles.htm?fileType=private"><img alt="" border=0 src="images/Teacher/PF.gif" width=103 height=27 /></a>
	    											</c:when>
											    	<c:otherwise>
											    		<a href="displayTeacherClassFiles.htm?fileType=private"><img alt="" border=0 src="images/Teacher/PF1.gif" width=103 height=27 /></a>
											    	</c:otherwise>
											    </c:choose> 
	                                        </td>
	                                        <td vAlign=bottom width=103>
	                                        	<c:choose>
	    									   		<c:when test="${fileType == 'admin'}">
	       												<a href="javascript:loadTeacherAdminFiles();"><img alt="" border=0 src="images/Teacher/AF.gif" width=103 height=27 /></a>
	    											</c:when>
											    	<c:otherwise>
											    		<a href="javascript:loadTeacherAdminFiles();"><img alt="" border=0 src="images/Teacher/AF1.gif" width=103 height=27 /></a>
											    	</c:otherwise>
											    </c:choose>
	                                        </td>
	                                        <td vAlign=bottom width=103>
	                                        	<c:choose>
	    									   		<c:when test="${fileType == 'student'}">
	       												<a href="displayTeacherStudentFiles.htm?fileType=student"><img alt="" border=0 src="images/Teacher/StudentGreen.jpg" width=103 height=27 /></a>
	    											</c:when>
											    	<c:otherwise>
											    		<a href="displayTeacherStudentFiles.htm?fileType=student"><img alt="" border=0 src="images/Teacher/StudentBlue.jpg" width=103 height=27 /></a>
											    	</c:otherwise>
											    </c:choose>
	                                        </td>
	                                    </tr>
	                                </table>
                                </c:if>
                            </td>
                        </tr>
                        <tr align="center"><td colspan="3"><font color="red" size="4px" ><label  id=returnMessage ></label></font></td></tr>
                        <tr >
                        <tr><td width="184" class="perinfohead"><font color="aeboad" size="3" id="title"><c:out  value="${title}"/></font> </td></tr>
                            <td height="30" colspan="2" align="left" valign="middle" >
                                <table id="dropdownTable" width="100%" height="30" border="0" cellpadding="0" cellspacing="0">                                	
                                    <tr >                                        
                                        <c:if test="${fileType ne 'admin'}">
                                        	<tr><td><input type="hidden" id="teacherObj" value="${teacherObj}" /></td></tr>
                                        	<form name="createFolder" action="displayCreateFolder.htm" method="post">
                                        	<td width="112" align="center" valign="middle">                                                                                          
                                               <select name="gradeId" class="listmenu" id="gradeId"  onChange="loadClasses()">
                                                               <option value="0">select grade</option>
																	<c:forEach items="${grList}" var="ul">
			                											<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
																	</c:forEach>
                                                            </select>
                                        	</td>
                                        	<td width="250" align="center" valign="center">
                                       		<c:if test="${(fileType eq 'public')}">   
        										<select id="classId" name="classId" class="listmenu"  onchange="loadUnitName();">
													<option value="0">select class</option>
												</select>   				              			
       										</c:if>        			
        									<c:if test="${(fileType eq 'private')}">
        										<select id="classId" name="classId" class="listmenu"  onchange="loadFolders();">
													<option value="0">select subject</option>
												</select>
        									</c:if>
    											                                        
											<input type="hidden" id="fileType" name="fileType" value="${fileType}" />
                                        	</td>
                                        	</form>
                                        	<c:if test="${fileType eq 'public'}">
                                       			<td width="112" align="center" valign="center">
													<select id="unitId" name="unitId" class="listmenu" onchange="loadUnitsLessons();" >
														<option value="select">select unit</option>
													</select>
												</td>
											</c:if>
											<c:if test="${fileType eq 'student'}">
											<td width="112" align="center" valign="center">
													<select id="classId" name="classId" class="listmenu"  onchange="loadSections();">
														<option value="0">select class</option>
													</select>  
												</td>
                                       			<td width="112" align="center" valign="center">
													<select id="sectionId" name="sectionId" class="listmenu" onchange="loadStudents();" >
														<option value="0">select section</option>
													</select>
												</td>
												<td width="112" align="center" valign="center">
													<select id="studentId" name="studentId" class="listmenu"  >
														<option value="0">select student</option>
													</select>
												</td>
											</c:if>
										
                                        	<td width="500" align="center" valign="center">
                                         	<c:if test="${fileType eq 'private'}">
                                         		<a onclick="newFolder();" href="#"> <img alt=""
												src="images/Common/CFO.gif" width="82" height="22" border="0" />
												</a>
											</c:if>
                                		</td>                                		
                            		</tr>
                            	</c:if>
                        </table></td>
                </tr>
                <tr>
                    <td height="2" colspan="2"></td>
                </tr>
                <tr>
                    <td height="0" colspan="2" align="center" valign="top">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" id="LessonContent">
                        <tr><td><div id="unitLessonDiv"></div></td></tr>
                        
                        </table></td>
    </tr>
</table>
</body>
</html>