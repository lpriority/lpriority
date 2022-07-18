<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%> --%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fluency Reading Results</title>
<script src="resources/javascript/admin/common_dropdown_pull.js"></script>
<script src="resources/javascript/TeacherJs/benchmark_results.js"></script>
<script>
function clearEval(){
	 document.getElementById("showBut").style.visibility="hidden";
}

function getTeachersByYear(thisvar) {
	$.ajax({
		type : "GET",
		url : "getTeachersByYear.htm",
		data : "academicYearId=" + thisvar.value,
		success : function(response) {
			$("#teacherId").empty();
			$("#select_all").prop("checked", false);
			$("#teacherId").append($("<option></option>").val('0').html('Select Teacher'));
			$.each(response.teachers, function(index, value) {
				var teacherName = value.userRegistration.firstName
				+ " "
				+ value.userRegistration.lastName;
				$("#teacherId").append($("<option></option>").val(value.teacherId).html(teacherName));
			});
		}
	});
}

</script>
</head>
<body>
	<%-- <%@ include file="/jsp/assessments/test_results_tab.jsp" %> --%>
	<c:choose>
	 <c:when test="${tab == 'fluencyResults' or tab == 'comprehensionResults'}"> 
	<div class="title-pad" style="padding-top: 1em;">
		<form action="exportFluencyResults.htm" method="post"
			name="fluencyResults">
			<table width="90%">
				<tr>
					<td width="10%" align="center" valign="middle" class="label">Academic Year &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
						</td>
					<td width="10%">
						<select  id="academicYearId" name="academicYearId" class="listmenu" style="overflow-y: visible; height: 30px; width: 150px" required onchange="getTeachersByYear(this)">
							<option value="select" disabled="disabled">Select Academic Year</option>
							<c:forEach items="${acadeYearsLst}" var="aca">
								<option value="${aca.academicYearId}">${aca.academicYear}</option>
							</c:forEach>
						</select> 
					</td>
					<td width="30%" valign="middle" class="label">Teacher
						&nbsp;&nbsp;
						<select id="teacherId" name="teacherId" class="listmenu" multiple="multiple" style="overflow-y: visible; height: 100px;" onclick="selectOption(this, 'teacherId','')" required="required">
							<option value="" disabled="disabled">Select Teacher</option>
							<c:forEach items="${teacherEmails}" var="teacher">
								<option value="${teacher.teacherId}">${teacher.userRegistration.lastName} ${teacher.userRegistration.firstName}</option>
							</c:forEach>
					</select> <input type="checkbox" class="checkbox-design" id="select_all" name="select_all"
						onClick="selectAllOptions(this,'teacherId','')"><label for="select_all" class="checkbox-label-design">Select All</label>
					</td>
					<td width="35%" align="left" valign="middle" class="label">Fluency
						Type &nbsp;&nbsp;<select  required="required"
						id="benchmarkCategoryId" name="benchmarkCategoryId"
						class="listmenu" multiple="multiple"
						style="overflow-y: visible; height: 70px; width: 150px"
						onclick="selectOption(this, 'benchmarkCategoryId','')">
						<option value="" disabled="disabled">Select Fluency Type</option>
							<c:forEach items="${benchmarkTypes}" var="benchmarkType">
								<option value="${benchmarkType.benchmarkCategoryId}">${benchmarkType.benchmarkName}</option>
							</c:forEach>
					</select> <input type="checkbox" class="checkbox-design" id="select_al" name="select_all"
						onClick="selectAllOptions(this, 'benchmarkCategoryId', '')"><label for="select_al" class="checkbox-label-design">Select All</label>
					</td>
					
					
					<!-- <td class="label" width="20">
					<input type="checkbox" class="checkbox-design" id="select_a" name="select_a"
						onClick="selectAllOptions(this,'academicYearId','')"><label for="select_a" class="checkbox-label-design">Select All</label>
					</td> -->
										
				</tr>
				<tr>
					<td colspan="2" width="100%" align="center" valign="top"
						id="StudentReportList"><input type="submit"
						class="button_green" value="Get Fluency Results" onClick></td>
				</tr>
			</table>
		</form>
	</div>
	</c:when>
	<c:otherwise>
	<div style="padding-top: 1em;">
		<form:form name="selfPeerResults" id="selfPeerResults" modelAttribute="assignment" method="get">  
        <table border=0 cellSpacing=0 cellPadding=0 width="100%" align=left height=69>           
			<tr>
				<td style="color: white; font-weight: bold"><input
					type="hidden" name="tab" id="tab" value="${tab}"></td>
				<td height="5">&nbsp;</td>
			</tr>      
            <tr>
                <td  vAlign=top width="100%" colSpan=3 align=middle>
                    <table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0>                        
                        <tr><td vAlign=bottom align=right></td></tr>
                        <tr>
                            <td height="30" colspan="2" align="left" valign="middle"><table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td width="56"><label class="label">Grade</label></td>
                                        <td width="150" >
                                           <select name="gradeId"
												class="listmenu" id="gradeId"
												onChange="getGradeClasses();clearEval();" required="required">
													<option value="select">select grade</option>
													<c:forEach items="${teacherGrades}" var="ul">
														<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
													</c:forEach>
											</select>
                                        </td>
                                        <td width="58" align="left" valign="middle"><label class="label">Class</label></td>
                                        <td width="150" align="left" valign="middle">
                                            <select
											id="classId" name="classId" class="listmenu"
											onChange="getClassSections();clearEval();" required="required">
												<option value="select">select subject</option>
										</select></td>
                                        <td width="58" align='left'><label class="label">Section&nbsp;</label> </td>
                                        <td width="150" align="left" valign="middle" class="text1">
                                        <select id="csId" class="listmenu" name="csId" onChange="getAccuracyDates();clearEval()" required="required">
												<option value="select">select Section</option>
											</select></td>
                                        <td width="56" align="left" valign="middle"><label class="label">Date</label> </td>
                                        <td width="150" align="left" valign="middle" class="header">
                                            <select name="assignedDate" class="listmenu" id="assignedDate" onchange="getAccuracyTitles();clearEval();">
                                                <option value="select">Select Date</option>
                                            </select></td>
                                             <td width="50" align="center" class="label">Title&nbsp;</td>
                                             <td width="150" align="left" valign="middle" class="header" onchange="showDownloadBut()">
                                            <select name="titleId" class="listmenu" id="titleId">
                                                <option value="select">Select Title</option>
                                            </select></td>  
                                        <td  align="left" width="150"  valign="middle" >
                                            <!-- <input type="checkbox" class="checkbox-design" name="checkAssigneds" id="checkAssigneds"  onClick="evaluateBenchmark();clearEval();"/>
                                            <label for="checkAssigneds" class="checkbox-label-design label">Get Results</label> --></td>
                                        <td width="100" align="left" valign="middle">
                                            <input type="hidden" name="usedFor" id="usedFor" value="${usedFor}"></input></td>
                                    </tr>
                                </table></td>
                        </tr>
                        <tr>
                            <td height="2" colspan="2"> </td>
                        </tr>  
                        </table>
                        <table>                      
                         <tr>
                            <td colspan="2" align="center" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                                    <tr>
                                        <td colspan="2" align="center" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" id="showBut" style="visibility: hidden">
                                                <tr><td>&nbsp;</td></tr>
                                                <tr>
                                                    <td> <br><br>
                                                    <input type="submit" class="button_green" value="Download Results" onClick="evaluateBenchmarkResults() "/>
                                                      
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
                                                </tr>
                                            </table>

                                        </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                </table></td>
                        </tr>
                        <tr>
                            <td height="2" colspan="2"></td>
                        </tr>
                        <tr>
                            <td height="30" colspan="2"></td>
                        </tr>
                        <tr>
                            <td height="30" colspan="2"><% String se = "yes";
                                        session.setAttribute("setgroupEvaluateHomework", se);%></td>
                        </tr>                    </table></td>
            </tr>
            <tr><td width="100%" colspan="10" align="center"><label id="returnMessage" style="color: blue;">${hellowAjax}</label></td></tr>
        </table>
       </form:form>
	</div>
	</c:otherwise>
	</c:choose>
</body>
</html>