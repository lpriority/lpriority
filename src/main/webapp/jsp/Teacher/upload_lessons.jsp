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
<title>Upload Lessons</title>        
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
                   
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="/dwr/interface/curriculumService.js"></script>
<script type="text/javascript" src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript" src="/dwr/interface/AddStudentsToClassService.js"></script>
<script type="text/javascript" src="resources/javascript/my_files.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	 $('#returnMessage').fadeOut(3000);
});
</script>
</head>
<body  >
	<input type="text" id="fileType" name="fileType" value="${fileType}" ></input>
<form:form id="createUnits"  action="addlessons.htm" modelAttribute="CreateUnits" method="post">
	<table width="90%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
                        <tr><td height=20 colSpan=2><div align="right"></div></td></tr>
                        <tr>
                            <td  height="20" style="color:white;font-weight:bold" >
                            </td>
                            <td vAlign=bottom align=right>
                                <div> 
                                	<%@ include file="/jsp/curriculum/curriculum_tabs.jsp" %> 
                                </div>
                            </td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td height="30" colspan="2" align="left" valign="left"  width="100%">
                               
                                    <table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">

                                        <tr>
                                            <td height="30" colspan="5" align="left" valign="center">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">                                                    
                                                    <tr><td colspan="6" > &nbsp;</td></tr>
                                                    <tr><td><input type="hidden" id="teacherObj" value="${teacherObj}" /></td></tr>
                                                    <tr>
                                                        <td width="5%" height="20" align="center"><font color="aeboad" size="3">Grade</font>
                                                            <select name="gradeId" class="listmenu" id="gradeId"  onChange="loadClasses()">
                                                               <option value="select">select grade</option>
																	<c:forEach items="${grList}" var="ul">
			                											<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
																	</c:forEach>
                                                            </select><c:out value="${ul.gradeId}"></c:out>
                                                        </td>
                                                        <td width="5%" height="20"><font color="aeboad" size="3">Subject</font>

															<select id="classId" name="classId" class="listmenu"  onChange="loadUnitName()">
																<option value="select">select subject</option>
															</select>
														</td>
														<td width="5%" height="20"><font color="aeboad" size="3">Units</font>

															<select id="unitId" name="unitId" class="listmenu" onchange="displayLessons();" >
																<option value="select">select Unit</option>
															</select>
														</td>
                                                    </tr>
                                             	</table>
                                             </td>

                                        </tr>
                                         <tr>
						                    <td height="2" colspan="2"></td>
						                </tr>
						                <tr>
						                    <td height="0" colspan="2" align="center" valign="top">
						                        <table width="100%" border="0" cellpadding="0" cellspacing="0" id="LessonContent">
						                        <tr><td><div id="showFilesDiv"></div></td></tr>
						                        
						                        </table>
						                    </td>
									    </tr>
                                        <tr>
                                            <td  id="appenddiv1"  colspan="9" align="left" valign="top"  ><div></div></td>
                                        </tr>


                                    </table></td></tr>
                        <tr>
                            <td height="2" colspan="3"></td>
                        </tr>
                        <tr>
                            <td height="30" colspan="2">&nbsp;</td>
                        </tr>
                        <tr>
                            <td height="30" colspan="2">&nbsp;</td>
                        </tr>
                        <tr>                        	
                        </tr>
                    </table>
            </form:form>
	<div id="subViewDiv"></div>
</body>
</html>