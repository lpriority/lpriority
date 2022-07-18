
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<%@ include file="../CommonJsp/include_resources.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Units</title>
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/curriculum.js"></script>
<script type="text/javascript" src="resources/javascript/CommonValidation.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<link href="resources/javascript/jplayer/skin/blue.monday/css/jplayer.blue.monday.min.css" rel="stylesheet" type="text/css"/>
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
<script>
$(document).ready(function () {
 	$('#returnMessage').fadeOut(4000);
});
	
function clearDiv(){
	$("#unitDiv").empty();
	$("#btAdd").hide();
}
</script>
</head>
<body>
   <form:form id="createUnits"  action="createunits.htm" modelAttribute="CreateUnits" method="post">
	<table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
        <input type='hidden'  id='regId' name='regId' value='${regId}' />
                        <tr>
                            <td vAlign=bottom align=right>
                                <div> 
                                	<%@ include file="/jsp/curriculum/curriculum_tabs.jsp" %> 
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td height="30" colspan="2" align="left"  width="100%">
                               <table width="100%" border="0" cellspacing="0" cellpadding="0" align="left"  style="padding-left: 8em;">  
                                     <tr><td><input type="hidden" id="teacherObj" value="${teacherObj}" /></td></tr>
                                     <tr>
                                     	<td height="40" align="left" class="label" style="width: 13em;">Grade &nbsp;&nbsp;&nbsp;
                                             <select name="gradeId" style="width:120px;" class="listmenu" id="gradeId"  onChange="getGradeClasses();clearDiv()">
                                                <option value="select">select grade</option>
												<c:forEach items="${grList}" var="ul">
   												<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
												</c:forEach>
                                            </select><c:out value="${ul.gradeId}"></c:out>
                                        </td>
                                        <td height="40" class="label" style="width: 15em;padding-left: 2em;">Subject &nbsp;&nbsp;&nbsp;
										<select id="classId" style="width:120px;" name="classId" class="listmenu"  onchange="loadUnits()">
												<option value="select">select subject</option>
											</select>
										  </td>
                                        <td height="20" class="label" style="width: 45em;padding-left: 2em;">
										   	<div class="button_green" id="btAdd" name="btAdd" height="52" width="50" onclick="addUnits()" style="display:none;">Add Units</div>
										</td>
										<td>
											<div id="previousBtn" style="display:none;cursor: hand; cursor: pointer;" onclick="goToLessonsTab(-1)">
												<table>
												  <tr align="right">
													<td height="20" class="label" style="font-family:Candara, Calibri, Segoe, Segoe UI, Optima, Arial, sans-serif;" align="right" >Next
														<i class="fa fa-arrow-right" aria-hidden="true"></i>
													</td>
												  </tr></table>
											</div>
										</td>
                                     </tr>
                              	</table>
                                </td></tr>
                           <tr>
		<td>
		<!-- 	<div id="videoDiv">
				<a href="#" onClick="openVideoDialog('Common','create_unit','videoDailog')" class="demoVideoLink"><i class="ion-videocamera" aria-hidden="true" style="font-size:26px;font-weight:bold;color:#e23c00;"></i>&nbsp;video for Create unit</a>
		 	</div> -->
		</td> 
		<td width="80%" colspan="10" align="center"><label id="status"
			style="color: blue;">${hellowAjax}</label></td>
	   </tr>
                                <tr><td height=40 colSpan=3></td></tr> 
                                <tr><td  id="appenddiv"  colspan="5" align="center" valign="top">
                                    <div id="unitDiv" style="display:none;"></div>
                                </td></tr>
                                <tr><td align="center" colspan="9" align="center" valign="top"><div id='resultDiv' align='center'/></td></tr>
           				</table>
           				<tr><td><div id="videoDailog" title="Grade Fluency" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div></td></tr>
                    <div id="dialog"  style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title=""><iframe  id ="iframe" src="" frameborder="0" scrolling="no" width="100%" height="95%" src=""></iframe></div>
      </form:form>
</body>
</html>