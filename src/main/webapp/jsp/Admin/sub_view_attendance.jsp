<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script type="text/javascript">
 $(function() {
    $( "#startDate" ).datepicker({
		changeMonth: true,
        changeYear: true,
        showAnim : 'clip'
    }); 
  $( "#endDate" ).datepicker({
    	changeMonth: true,
        changeYear: true,
        showAnim : 'clip'
    }); 
 }); 
</script>
<table width="100%">
		<tr>
			<td  vAlign=top width="100%" colSpan=3 align=middle>
			<input type="hidden" id="tab" name="tab" value="${tab}">
		<table width="100%" align="left" style="padding-top:1em;" class="title-pad">			
			<c:if test="${tab != 'school attendance' }">
				<tr>
					<td colspan="2" align="left" >
						<table>
							<tr>
								<td width="60" class="label">Grade  </td>
								<td width="120"><label> 
								<select name="gradeId" class="listmenu" id="gradeId" style="width:30px;" onChange="getGradeClasses();resetData();" required="required" >
									<option value="select">select grade</option>
									<c:forEach items="${grList}" var="ul">
										<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
									</c:forEach>
									</select>
								</label></td>
								<td width="60" class="label space-between-select">Class  </td>
								<td width="120" align="right" valign="middle">
									<select id="classId" name="classId" class="listmenu" style="width: 120px;" onchange="getClassSections();resetData();" >
										<option value="select">select subject</option>
								</select>
								</td>
								<td  width="60" class="label space-between-select">Section  </td>
								<td  width="120" align="right" valign="right">
									<select id="csId" name="csId" class="listmenu"
									style="width: 120px;" onchange="resetData()" >
										<option value="select">Select Section</option>
								</select>
								</td>
								<c:choose>
									<c:when test="${tab == 'daily attendance' }">
										<td class="label space-between-select">Select Date </td>
									</c:when>
									<c:otherwise>
										<td class="label space-between-select"> Select Date Range  </td>
									</c:otherwise>
								</c:choose>
								<td class="space-between-select">
									<input type="text" style="width: 3cm" id="startDate" onchange="resetData()" readonly="readonly"
									  ></td>
								<c:if test="${tab == 'weekly attendance' }">
									<td class="space-between-select">
										<input type="text" style="width: 3cm" id="endDate" onchange="resetData()" readonly="readonly"
										></td>
								</c:if>
								<td class="label space-between-select">Show Attendance</td>
								<td><input type="checkbox" class="checkbox-design"
									id="getAttendance" onclick="getStudentAttendance()"><label for="getAttendance" class="checkbox-label-design"></label></td>
							</tr>
						</table>
					</td>
				</tr>
			</c:if>
			
		    <c:if test="${tab == 'school attendance' }">
				<tr>
					<td style="padding-bottom: 1cm;" colspan="2" align="center">
							<table style="width: 60%" class="des" border=0>
								<tr><td colspan="4">
									<table class="Divheads" width="100%">
										<tr>
											<td width="10%" style="padding-left:2em;"></td>
											<td width="10%" align="center">Today</td>										
											<td width="10%" align="center">Prior Day</td>
											<td width="10%" align="center">2 Days Prior</td>
										</tr>
									</table>
								</td>	
								</tr>
								<tr>
									<td width="10%" height="30"></td>
									<td width="10%" class="tabtxt" align="center">${today}</td>										
									<td width="10%" class="tabtxt" align="center">${priorDay}</td>
									<td width="10%" class="tabtxt" align="center">${twoDaysPrior}</td>
								</tr>
								<tr>
									<td class="tabtxt" style="padding-left:2em;">School Enrollment</td>
									<td class="txtStyle" align="center">${attendanceChart.totalEnrollment}</td>
									<td class="txtStyle"align="center" >${attendanceChart.totalEnrollment}</td>
									<td class="txtStyle" align="center">${attendanceChart.totalEnrollment}</td>										 
								</tr>
								<tr>
									<td class="tabtxt" style="padding-left:2em;">Male Enrollment</td>
									<td class="txtStyle" align="center">${attendanceChart.maleEnrollment}</td>
									<td class="txtStyle"align="center">${attendanceChart.maleEnrollment}</td>
									<td class="txtStyle" align="center">${attendanceChart.maleEnrollment}</td>										 
								</tr>
								<tr>
									<td class="tabtxt" style="padding-left:2em;">Female Enrollment</td>
									<td class="txtStyle" align="center">${attendanceChart.femaleEnrollment}</td>
									<td class="txtStyle" align="center">${attendanceChart.femaleEnrollment}</td>
									<td class="txtStyle" align="center">${attendanceChart.femaleEnrollment}</td>										 
								</tr>
								<c:forEach items="${attendanceChart.schoolAttendanceList}" var ="attendance">
									<tr>
										<td class="tabtxt" style="padding-left:2em;">${attendance.status}</td>
										<td class="txtStyle" align="center">${attendance.todayCount}</td>
										<td class="txtStyle" align="center">${attendance.priorDayCount}</td>
										<td class="txtStyle" align="center">${attendance.twoDaysPrior}</td>
									</tr>
								</c:forEach>
							</table>
						
				</td>
			</tr>
			</c:if>
			<tr>
				<td>
					<div align="center" id="attendanceDiv" ></div>
				</td>
			</tr>
		</table>
		</td></tr></table>