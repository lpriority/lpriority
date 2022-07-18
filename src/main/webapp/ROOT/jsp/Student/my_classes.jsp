<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>IOL Reports</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="resources/javascript/canvasjs/canvasjs.min.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/common/home_page.js"></script>
<style type="text/css">

.ui-dialog > .ui-widget-content {background: white;}

.linkSty{
font-color:blue;
font-size:10;
}
</style>
 </head>
<body>

<table width="100%" border="0" class="title-pad" style="padding-bottom:3em;">
<tr><td colspan="3" class="sub-title title-border" height="40" align="left">
       		My Classes<br>
       		<input type="hidden" id="studentId" value="${student.studentId}"/>
       </td></tr></table>
 <br>
         <table><tr><td width="600" style="padding-left:37em">
        <c:if test="${fn:length(classes) > 0}">
       
	   <table width="100%" border='0' class="des" align="center"><tr><td><div class="Divheads"><table align="center"><tr><td align='center' class="headsColor">My Classes</td></tr></table></div>
                    <br>
                     <div class="DivContents"><table>
                     <tr class="text">							
									<td width="23%"><b>Class</b></td>
									<td width="20%"><b>Period</b></td>
									<td width="20%"><b>Start Time</b></td>
									<td width="20%"><b>End Time</b></td>
									<td width="20%"><b>Teacher Name</b></td>
					</tr>
                    <c:forEach items="${classes}" var = "class"> 
                    <tr><td align='center' class="tabtxt"><font color="blue">
                     <c:set var="i" value="0"/>
                     <tr class="txtStyle">
										
										<td width="23%">
										  <a href="javascript:void(0);" onclick="getCompositeChartByStudent(${class.classStatus.csId})">${class.classStatus.section.gradeClasses.studentClass.className}</a>
												
										</td>
										<td width="20%">${class.periods.periodName}</td>
										<td width="20%">${class.periods.startTime}</td>
										<td width="20%">${class.periods.endTime}</td>
										<td width="20%">	
												${class.classStatus.teacher.userRegistration.title}
												${class.classStatus.teacher.userRegistration.firstName}${class.classStatus.teacher.userRegistration.lastName}
										</td>
						
						</tr>
									</font>
                                         
                    </td></tr>
                    </c:forEach>
                    
                    
          </table></div></td></tr></table></c:if>
          <c:if test="${fn:length(classes) <= 0}">
                     <div style="font-size:2em;color:#6b6800;padding-top:3em;"> <i class="fa fa-files-o" aria-hidden="true"></i>&nbsp;&nbsp;<span style="color:#000000;text-shadow: 0 1px 1px rgb(204, 204, 204);font-weight: bold;font-size:12px;font-family:cursive;">No Classes are scheduled today.</span></div>
           </c:if>
          </td></tr>
                  
          </table>
           <div id="compositeChart" title="Composite Chart" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
          
                  
		
                     
		<%-- <table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="200" valign="top" style="padding-top: 2em;">
				   <div style="height:105%;">
			   		 <div><span>My Classes</span></div>
				  <c:choose>
					<c:when test="${fn:length(classes) gt 0}">
					  <div>
  						<div> 
							<table border="0" class="" style="font-size: 12.5px;margin-top:3em;" width="98%">
								<tr class="text">							
									<c:if test="${userRegistration.user.userType eq 'teacher'}"><td><b>Grade</b></td></c:if>
									<c:if test="${userRegistration.user.userType eq 'teacher'}"><td><b>Section </b></td></c:if>
									<td><b>Class</b></td>
									<td><b>Period</b></td>
									<td><b>Start Time</b></td>
									<td><b>End Time</b></td>
									<c:if test="${userRegistration.user.userType ne 'teacher'}"><td><b>Teacher Name</b></td></c:if>
								</tr>
								<c:forEach items="${classes}" var = "class">
									<tr class="txtStyle">
										<c:if test="${userRegistration.user.userType == 'teacher'}"><td>${class.classStatus.section.gradeClasses.grade.masterGrades.gradeName}</td></c:if>
										<c:if test="${userRegistration.user.userType == 'teacher'}">
											<td>
												<a href="getStudentRoster.htm?csId=${class.classStatus.csId}" target="_blank" >${class.classStatus.section.section}</a>
											</td>
										</c:if>
										<td>
											<c:choose>
												<c:when test="${userRegistration.user.userType == 'teacher'}">
													${class.classStatus.section.gradeClasses.studentClass.className}
												</c:when>
												<c:otherwise>
													<a href="javascript:void(0);" onclick="getCompositeChartByStudent(${class.classStatus.csId})">${class.classStatus.section.gradeClasses.studentClass.className}</a>
												</c:otherwise>
											</c:choose>
										</td>
										<td>${class.periods.periodName}</td>
										<td>${class.periods.startTime}</td>
										<td>${class.periods.endTime}</td>
										<c:if test="${userRegistration.user.userType ne 'teacher'}">
											<td>	
												${class.classStatus.teacher.userRegistration.title}
												${class.classStatus.teacher.userRegistration.firstName}${class.classStatus.teacher.userRegistration.lastName}
											</td>
										</c:if>
									</tr>
								</c:forEach>
							</table>
						</div>
					 </div>
					</c:when>
					<c:otherwise>
                     	<div style="font-size:2em;color:#6b6800;padding-top:3em;"> <i class="fa fa-files-o" aria-hidden="true"></i>&nbsp;&nbsp;<span style="color:#000000;text-shadow: 0 1px 1px rgb(204, 204, 204);font-weight: bold;font-size:12px;font-family:cursive;">No Classes are scheduled today.</span></div>
                    </c:otherwise>
		         </c:choose>
				 </div>
			 </td>
		</tr>
		</table> --%>
	   	
</body>
</html>