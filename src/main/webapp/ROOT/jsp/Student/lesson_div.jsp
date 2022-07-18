<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${tab == 'stem' }" >
	<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
	<%@ include file="../CommonJsp/include_resources.jsp" %>
</c:if>
<c:if test="${tab == 'stem' }" >
	<script type="text/javascript" src="resources/javascript/TeacherJs/stem_curriculum.js"></script>
	<script type="text/javascript" src="resources/javascript/Student/student_stem_curriculum.js"></script>
	<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
	<script type="text/javascript" src="resources/javascript/TeacherJs/curriculum.js"></script>
	<script type="text/javascript" src="resources/javascript/CommonValidation.js"></script>
	<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
	<script type="text/javascript" src="resources/javascript/ParentJs.js"></script>
</c:if>
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
			<c:choose>
			<c:when test="${tab == 'lesson' }">
			<select name="csId" id= "csId" class="listmenu" onchange="getStudentAssignedCurriculum()">
				<option value="select">Select Class</option>
				<c:forEach items="${studentClassList}" var="studentClasses">
					<option value="${studentClasses.classStatus.csId}"> ${studentClasses.gradeClasses.studentClass.className}</option>
				</c:forEach>
			</select>
			</c:when>
			<c:otherwise>
			
			<select name="csId" id= "csId" class="listmenu" onchange="getAssignedStemUnits()">
				<option value="select">Select Class</option>
				<c:forEach items="${studentClassList}" var="studentClasses">
					<option value="${studentClasses.classStatus.csId}"> ${studentClasses.gradeClasses.studentClass.className}</option>
				</c:forEach>
			</select>
			</c:otherwise>
			</c:choose>
		</div>
		<div id="curriculumDiv">
		</div>
		<div id="stemUnitDiv" colspan="5" align="center">
		</div>  
		<div id="dialog"  style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title=""><iframe  id ="iframe" src="" frameborder="0" scrolling="no" width="100%" height="98%" src=""></iframe></div>
	<div id="preMadedialog" style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title=""><iframe  id ="preMadeIframe" src="" frameborder="0" scrolling="no" width="100%" height="98%" src=""></iframe></div>
  
