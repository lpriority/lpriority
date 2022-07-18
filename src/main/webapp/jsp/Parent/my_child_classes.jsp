<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript" src="/dwr/interface/studentService.js"></script>
<script type="text/javascript" src="/dwr/interface/teacherViewClassService.js"></script>
<script type="text/javascript" src="/dwr/interface/teacherSchedulerService.js"></script>	
<script src="resources/javascript/Adminjs.js"></script>

<title>Show Child Classes</title>
<script>
	function getDetailsOfSections() {
		var csId = $('#csId').val();
		var teacherId = $('#teacherId').val();
		var gradeClassId = $('#gradeClassId').val();
		var studentId = $('#studentId').val();
		$('#details').empty();
		if(studentId){
			if(studentId == "select"){
				alert("Please select a student");
				return false;
			}
		}
		
		if(csId > 0 && gradeClassId > 0 && teacherId > 0){
			$.ajax({
				type : "GET",
				url : "getClassDetails.htm",
				data : "csId=" + csId + "&teacherId=" + teacherId,
				success : function(response) {
					$('#details').append(response);
				}
			});
		}else{
			alert("Please fill the filters");
		}
	}

	function setStatusForClassRegistration() {
		var csId = $('#csId').val();
		var studentId = $('#studentId').val();
		var gradeClassId = $('#gradeClassId').val();
		var teacherId = $('#teacherId').val();
		$.ajax({
			type : "GET",
			url : "sendClassRequest.htm",
			data : "studentId=" + studentId + "&csId=" + csId + "&gradeClassId=" + gradeClassId +"&teacherId="+teacherId,
			success : function(response) {
				alert(response);
			}
		});
	}
	
	function clearDiv(){
		$('#details').empty();
		$("#csId").empty();
		$("#csId").append(
				$("<option></option>").val('').html('Select Section'));
	}
</script>
</head>
<body>
	<c:if test="${userReg.user.userType ne 'parent' }">
		<input type="hidden" id="gradeId" name="gradeId" value="${studentObj.grade.gradeId}">
		<input type="hidden" id="studentId" name="studentId"
			value="${studentObj.studentId}">
	</c:if>
	<input type="hidden" id="divId" name="divId" value="${divId}">
	<input type="hidden" id="userType" value="${userReg.user.userType}">
	<table width="99.5%" border="0" cellspacing="0" cellpadding="0" class="title-pad" style="padding-top: 1em">
	  <tr>
	
		
			<td width="15%" class="sub-title title-border" height="40" align="left">Available Classes at <font
					color="#0057af" size="4">${userReg.school.schoolName}</font></td>
		</tr>
	</table>
	<table style="width: 100%" align="center" class="title-pad">
		
					<tr>
						<td style="width: 100%"><table style="width: 100%" class="heading-up">
								<tr>

									<td style="" align="left"></td>
								</tr>

								

		<tr>
			<td>
				<table width="100%" height="30" border="0" cellpadding="0"
					cellspacing="0">
					
					<tr align="left">
						<c:if test="${userReg.user.userType == 'parent' }">
							<th width="50" align='left'><label class="label">Child</label></th>
							<td width="100" align="left" valign="left"><c:forEach
									items="${children}" var="child">
									<input type="hidden" id="gradeId${child.studentId}"
										value="${child.grade.gradeId}">
								</c:forEach> <select id="studentId" name="studentId" class="listmenu"
								style="width: 120px;" onchange="getClassesNoHomeroom(this);clearDiv();">
									<option value="select">Select Child</option>
									<c:forEach items="${children}" var="child1">
										<option value="${child1.studentId}">${child1.userRegistration.firstName}${child1.userRegistration.lastName}</option>
									</c:forEach>
							</select></td>
						</c:if>
						<th width="50" align='left'><label class="label">Class</label></th>
						<td width="100" align="left" valign="middle"><select
							id="gradeClassId" name="gradeClassId" class="listmenu"
							style="width: 120px;" onchange="getAvailableTeachers();clearDiv();">
								<option value="select">select subject</option>
						</select></td>
						<th width="60" aling='left'><label class="label">Teacher</label></th>
						<td width="110" align="left" valign="middle"><select
							id="teacherId" name="teacherId" class="listmenu"
							style="width: 120px;" onchange="getAvailableTeacherSections()">
								<option value="select">Select Teachers</option>
						</select></td>
						<th width="60" align='left'><label class="label">Section</label></th>
						<td width="100" align="left" valign="right"><select
							id="csId" name="csId" class="listmenu" style="width: 120px;"
							onchange="getDetailsOfSections()">
								<option value="select">Select Section</option>
						</select></td>
						<td  width='200'>&nbsp;</td>
						<td  width='200'>&nbsp;</td>
						
					</tr>
					<tr>
						<td height="4" colspan="6"></td>
					</tr>

				</table>
			</td>
		</tr>
		<tr>
			<td height=45 colSpan=4><div align="right"></div></td>
		</tr>
		<tr>
			<td>
				<div id="details"></div>
			</td>
		</tr>
		
	</table></td></tr>
		</table>
</body>
</html>