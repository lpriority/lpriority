
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
<title>${LP_STEM_TAB} Curriculum</title>
<script type="text/javascript" src="resources/javascript/TeacherJs/stem_curriculum.js"></script>
<script type="text/javascript" src="resources/javascript/Student/student_stem_curriculum.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/curriculum.js"></script>
<script type="text/javascript" src="resources/javascript/CommonValidation.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/ParentJs.js"></script>

<script>
$(document).ready(function () {
 	$('#returnMessage').fadeOut(4000);
});
function clearDiv(){
	$("#unitDiv").empty();
	$("#addStemUnit").hide();
	$("#preMadeStemUnit").hide();
	$('#stemUnitDiv').html("");
}
</script>
</head>
<body>

   <form:form id="createUnits"  action="createunits.htm" modelAttribute="CreateUnits" method="post">
   <input type='hidden'  id='regId' name='regId' value='${regId}' />
   <input type='hidden'  id='gradeId' name='gradeId' value='${grade.gradeId}' />
	<%-- <table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
        
			 <tr>
                  <td height="30" colspan="2" align="left"  width="100%" class="heading-up">
                      <table width="${(userReg.user.userType == 'parent')?'55%':'40%'}" border="0" cellspacing="0" cellpadding="0" align="left"  style="padding-left: 8em;">  
                          <tr><td><input type="hidden" id="teacherObj" value="${teacherObj}" /></td></tr>
                          <tr>
                          	<c:if test="${userReg.user.userType == 'parent'}">
								<td height="40" align="left" class="label" style="width: 14em;">
								<label class="label">Child&nbsp;&nbsp;</label>
									<select name="studentId" id="studentId" class="listmenu" onchange="clearDiv();loadGradeClasess()">
										<option value="select">Select Child</option>
										<c:forEach items="${childs}" var="child">
											<option value="${child.studentId}">${child.userRegistration.firstName}${child.userRegistration.lastName}</option>
										</c:forEach>
									</select>&nbsp;&nbsp;&nbsp;</td>
								</c:if>
                            <td height="40" align="left" class="label" style="width: 13em;">Grade &nbsp;&nbsp;&nbsp;
                           	  <input type="text" style="width:100px;" name="gradeName" value="${grade.masterGrades.gradeName}" disabled="disabled" id="gradeName">
                            </td>
                            <td height="40" class="label" style="width: 14em;padding-left: 2em;">Class &nbsp;&nbsp;&nbsp;
								<select name="csId" id= "csId" class="listmenu" onchange="clearDiv();getAssignedStemUnits();">
									<option value="select">Select Class</option>
									<c:forEach items="${studentClassList}" var="studentClasses">
										<option value="${studentClasses.classStatus.csId}"> ${studentClasses.gradeClasses.studentClass.className}</option>
									</c:forEach>
								</select>
			 	         	</td>
	             	     </tr>
             	    </table>
               </td>
             </tr>
	        <tr><td height=40 colSpan=3></td></tr> 
	        <tr><td colspan="5" align="center" valign="top">
	             <div id="stemUnitDiv"></div>
	         	</td>
	        </tr>
	</table> --%>
	
	<div style="padding-left: 0px; padding-top: 5px">
			<c:if test="${userReg.user.userType == 'parent'}">
			<label class="label">Child&nbsp;&nbsp;</label>
				<select name="studentId" id="studentId" class="listmenu" onchange="clearDiv();loadGradeClasess()">
					<option value="select">Select Child</option>
					<c:forEach items="${childs}" var="child">
						<option value="${child.studentId}">${child.userRegistration.firstName}${child.userRegistration.lastName}</option>
					</c:forEach>
				</select>&nbsp;&nbsp;&nbsp;
			</c:if><label class="label">Grade&nbsp;&nbsp;</label>
			<input type="text" style="width:100px;" name="gradeName" value="${grade.masterGrades.gradeName}" disabled="disabled" id="gradeName">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label class="label">Class&nbsp;&nbsp;</label>
			<select name="csId" id= "csId" class="listmenu" onchange="getAssignedStemUnits()">
				<option value="select">Select Class</option>
				<c:forEach items="${studentClassList}" var="studentClasses">
					<option value="${studentClasses.classStatus.csId}"> ${studentClasses.gradeClasses.studentClass.className}</option>
				</c:forEach>
			</select>
		</div>
		<div id="stemUnitDiv" colspan="5" align="center">
		</div> 
	<div id="loading-div-background1" class="loading-div-background" style="display:none;">
		<div class="loader"></div>
		<div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;">
			<br><br><br><br><br><br><br>Loading....
		</div>
	</div>
	<div id="dialog"  style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title=""><iframe  id ="iframe" src="" frameborder="0" scrolling="no" width="100%" height="98%" src=""></iframe></div>
	<div id="preMadedialog" style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title=""><iframe  id ="preMadeIframe" src="" frameborder="0" scrolling="no" width="100%" height="98%" src=""></iframe></div>
   </form:form>
</body>
</html>