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
<title>Student Files</title>        
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<!-- <script src="resources/javascript/jquery/jquery-2.1.4.js"></script> -->
<!-- <script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script> -->
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="/dwr/interface/curriculumService.js"></script>
<script type="text/javascript" src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript" src="/dwr/interface/teacherSchedulerService.js"></script>
<script type="text/javascript" src="resources/javascript/my_files.js"></script>
<script > 
$(function() {	
	var gradeId =parseInt(document.getElementById('gradeIdHidden').value);	
	var classId =parseInt( document.getElementById('classIdHidden').value);	
	 var stateCallBack= function(list) {
		if (list != null) {
			$('#gradeId').val(gradeId);
			dwr.util.removeAllOptions('classId');
			$("#classId").append(
					$("<option></option>").val(0).html('Select Subject'));
			dwr.util.addOptions('classId', list, 'classId', 'className');
			$('select[name="classId"]').val(document.getElementById("classIdHidden").value); 
			loadStudentFolders();
		} else {
			alert("No data found");
		}
	}
	if(gradeId > 0 && classId > 0 ){
		adminService1.getStudentClasses(gradeId, {
			callback : stateCallBack
		});
	}
});

</script>
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
</head>
<body  >	
	
		<input type="hidden" id="studentId"	name="studentId" value="${studentId}" />
	    <input type="hidden" id="gradeIdHidden" name="gradeIdHidden" value="${gradeId}" />	
	    <input type="hidden" id="classIdHidden" name="classIdHidden" value="${classId}" />			
                
                	<%-- <table width="100%" border=0 align="left" cellPadding=0 cellSpacing=0>
                    	<tr>
                            <td vAlign=bottom align=right>
                                <table style="width: 100%" height="29" border="0" cellpadding="0" cellspacing="0" align=right>
                                    <tr>
										<td style="width: 100%" align=right>
							              	<ul class="button-group">
												<li><a href="displayStudentClassFiles.htm?fileType=teacher" class="${(fileType == 'teacher')?'buttonSelect leftPill':'button leftPill'}">Teacher Files</a></li>
												<li><a href="displayStudentClassFiles.htm?fileType=studentPrivate" class="${(fileType == 'studentPrivate')?'buttonSelect':'button'}">Private Files</a></li>
												<li><a href="displayStudentClassFiles.htm?fileType=transfer" class="${(fileType == 'transfer')?'buttonSelect rightPill':'button rightPill'}">Transfer File</a></li>
											</ul>
										</td>		
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        </table>
                        <table style="padding-top:1em;" width="99.8%" border="0" cellspacing="0" cellpadding="0" class="title-pad" >
                        <tr><td class="sub-title title-border" height="40" align="left"><c:out  value="${title}"/><br> </td></tr>
                        </table> --%>
                    <table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0 class="title-pad">                    	
                        <tr align="center"><td colspan="3"><font color="red" size="4px" ><label  id=returnMessage ></label></font></td></tr>
                        <tr >
                         <tr><td height="20" colspan="2"></td></tr>
                        <tr>
                            <td height="30" colspan="2" align="left" valign="middle" >
                                <table id="dropdownTable" width="100%" height="30" border="0" cellpadding="0" cellspacing="0">                                	
                                    <tr >
                                         <form name="createFolder" action="displayCreateFolder.htm" method="post">
                                        	<td width="15%" align="left" valign="middle"> <label class="label">Grade  </label> &nbsp;&nbsp;&nbsp;                                                                                      
                                            	<input type="text" size="10" disabled="disabled" id="gradeName" name="gradeName" value="${gradeName}" />
                                            	<input type="hidden" name="gradeId" id="gradeId" value="${gradeId}" />
                                            	<input type="hidden" id="teacherId"	name="teacherId" value="" />
                                        	</td> 
                                          <c:if test="${(fileType eq 'teacher')}">   
                                          	<td width="15%" align="left" valign="middle"> 
                                       			<label class="label">Class</label> &nbsp;&nbsp;&nbsp;                                          		
       											<select id="classId" name="classId" class="listmenu" onchange="loadUnitName();">
													<option value=0>select class</option>
													<c:forEach items="${studentClassList}" var="class">
		                								<option value="${class.classId}">${class.className}</option>
													</c:forEach>
												</select> 
											</td>
											<td width="15%" align="left" valign="middle">
											<label class="label">Unit</label>  &nbsp;&nbsp;&nbsp;   
											<select id="unitId" name="unitId" class="listmenu" onchange="displayLessons()" >
													<option value="select">select Unit</option>
												</select>
											</td>
                                          </c:if>
                                          <c:if test="${(fileType eq 'studentPrivate') || (fileType eq 'transfer')}">   
                                         		<td width="15%" align="left" valign="middle">  
												<label class="label">Class</label> &nbsp;&nbsp;&nbsp;                                        		
       											 <select id="classId" name="classId" class="listmenu"  onchange="loadStudentFolders();">
													<option value=0>select class</option>
													<c:forEach items="${studentClassList}" var="class">
		                								<option value="${class.classId}" ${class.classId == classId ? 'selected="selected"' : ''}>${class.className}</option>
													</c:forEach>
												</select> 
											</td>
                                          </c:if>
                    					  <input type="hidden" id="fileType" name="fileType" value="${fileType}" /> 	
                                       	 <td width="59%" align="right">
                                         	<c:if test="${fileType eq 'studentPrivate' || fileType eq 'transfer'}">
	                                         	<div id="createFolderDiv" style="display: none;">
													<div class="button_green" id="btCreate" name="btCreate" height="52" width="50" onclick="studentCreateFolderDialog()">Create Folder</div>
												</div>
											</c:if>
                                		 </td>
                                		 <td>
											<div id="dialog"  style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title=""> 
												<iframe  id ="iframe" src="" frameborder="0" scrolling="no" width="100%" height="95%" src=""></iframe>
											</div>
										 </td>  
										 </form>  
                                		                                		
                            		</tr>                            	
                        </table></td>
                </tr>
                <tr>
                    <td height="2" colspan="2"></td>
                </tr>
                <tr>
                    <td height="0" colspan="2" align="left" valign="top">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" id="LessonContent">
                        <tr><td>&nbsp;</td></tr>
                        <tr><td><div id="showFilesDiv"></div></td></tr>
                        
                        </table>
                    </td>
			    </tr>
</table>
</body>
</html>