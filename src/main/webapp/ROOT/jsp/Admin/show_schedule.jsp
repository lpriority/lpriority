<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<title></title>
<style>
	.designTblCls td{
		border:none;
		padding:5px;
	}
	.designTblCls{
		border:1px solid #abc4c9;
		box-shadow:none;
		border-collapse:collapse;
	}
	.designTblCls tr:first-child td{
		border:none;
		color: #001f23;
		text-shadow:0 1px 1px rgb(183, 183, 183);
		background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #00e3ff), color-stop(1, #06b8d1) );
	}
	.designTblCls tr:first-child td:hover{
		background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #00e3ff), color-stop(1, #06b8d1) );
	}
	.mon{width: 120px;height: 40px;background: #393e41;color:white;padding:3px;font-size:11.5px;font-weight:bold;}
	.tue{width: 120px;height: 40px;background: #4CAF50;color:white;padding:3px;font-size:11.5px;font-weight:bold;}
	.wen{width: 120px;height: 40px;background: #FF5722;color:white;padding:3px;font-size:11.5px;font-weight:bold;}
	.thu{width: 120px;height: 40px;background: #2196F3;color:white;padding:3px;font-size:11.5px;font-weight:bold;}
	.fri{width: 120px;height: 40px;background: #3f51b5;color:white;padding:3px;font-size:11.5px;font-weight:bold;}
	.sat{width: 120px;height: 40px;background: #795548;color:white;padding:3px;font-size:11.5px;font-weight:bold;}
	.sun{width: 120px;height: 40px;background: #673AB7;color:white;padding:3px;font-size:11.5px;font-weight:bold;}
</style>
</head>
<body>
 <c:if test="${showBy eq 'teacher'}">
	<c:choose>
	<c:when test="${fn:length(casLt) gt 0}">
			<tr><td>
			   	<table valign="middle" align="center" style="padding-top:0.5em;box-shadow:16px 15px 6px -8px rgb(121, 121, 121);">
					<tr>   
						<td height="5" colspan="2" align="left" valign="middle" style="padding: 6px;border:1px solid #a8e4ee;background:#ebf4f5;"> 
						<table valign="middle" align="right" border="1" class="designTblCls" style="text-transform: capitalize;border-color:#04424a;">
						    <tr></tr>
						 	<c:forEach items="${schoolDaysLt}" var="schoolDays">
					            <tr>
						           	<th align="center" valign="middle" class="header" width="120" style="color:white;font-size:20;background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00E3FF), color-stop(1,#03a1b7) );text-shadow:0 1px 2px rgb(0, 0, 0);"><b> ${schoolDays.days.day}</b></th>
						           	 <c:forEach items="${casLt}" var="cas" > 
				                        <c:if test="${cas.days.dayId eq schoolDays.days.dayId}"> 
				                            <td align="center" width="180px">
				                             <table border="1" align="center">
				                               <tr><td><b>${cas.periods.startTime}-${cas.periods.endTime}</b><br/></td></tr> 
				                               <tr><td align="center">
					                          	  ${cas.periods.grade.masterGrades.gradeName}<br/>
					                          	  ${cas.periods.periodName} <br/>
					                          	  ${cas.classStatus.section.gradeClasses.studentClass.className}<br/> 
					                          	  ${cas.classStatus.section.section}<br/> 
					                          	  <a href="#" onClick="getStudentList(${cas.classStatus.csId},'class')" class="subButtons subButtonsWhite medium" style="text-decoration: none;padding:.1em 1em .2em;">Class Roster</a>
					                          	 </td></tr>
					                          </table>  
					                          </td>
		   	                          </c:if>
				                     </c:forEach>
						        </tr>
						   </c:forEach>
						</table>
					</td></tr>
				</table>
			</td></tr>	
		</c:when>
		<c:otherwise> 
		<tr><td align="center" height="100"><span class="status-message">Teacher not yet scheduled..</span></td></tr>
	</c:otherwise>
	</c:choose>
</c:if>
<c:if test="${showBy eq 'grade'}">
	<table align="center" class="txtStyle">
		   <c:choose>
			     <c:when test="${empty teachersList}">
				     <tr><td colspan="3"> <table border="1" align="center" style="border-collapse: collapse;background:white;">
                            <font color="blue">
                                No Schedule is available
                            </font>
	                  </table></td></tr>
			     </c:when>
				 <c:otherwise>
				  <tr><td colspan="3"> 
				  </td></tr>
				  <tr><td colspan="3"> 
				 <table id="schedulerDiv" width="auto" align="center" class="des" border=0 style="text-transform: capitalize;background:#ebf4f5;max-width:100%;white-space:nowrap;border:1px solid #a0b2b5;box-shadow:16px 15px 6px -8px rgb(121, 121, 121);"><tr><td>
				 <table width="100%">
			           <tr class="Divheads">
			               <th width="180" align="center" height="60" valign="middle"  class="header" style="text-shadow:0 1px 2px rgb(37, 37, 37);color:white;font-size:14.5px;">Teacher Name</th>
			               <input type="hidden" name="noOfPeriods" id="noOfPeriods" value="${fn:length(periodsLt)}"></input>
			               <c:forEach items="${periodLt}" var="period">  
			           		<th width="200" height="60" align="center" valign="middle" class="header" style="text-shadow:0 1px 2px rgb(37, 37, 37);color:white;font-size:14.5px;">${period.periodName} &nbsp;</br> (${period.startTime} - ${period.endTime})&nbsp;</th>
			               </c:forEach>
			           </tr>
                         <c:forEach items="${teachersList}" var="teacher">
                          <tr align="center">
 							<th align="center" height="40" style='font-family:Cambria, Palatino, "Palatino Linotype", "Palatino LT STD", Georgia, serif;padding-left:1em;background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00cde6), color-stop(1,#00aac1) );text-shadow:0 1px 1px rgb(152, 147, 147);color:#001f23;font-size:14.5px;'> ${teacher.userRegistration.firstName} ${teacher.userRegistration.lastName}</th>
 							 <c:forEach items="${periodLt}" var="period">
		                          <c:choose>
				     			 	<c:when test="${period.periodName eq 'Home Room'}">
					     			  	<td style="padding: 6px;border:1px solid #dadada;">
							     			 <c:forEach items="${hrmMap}" var="hrmMap"> 
											    <c:set var="hrmMapKey" value="${teacher.userRegistration.regId}${period.periodId}" /> 
					   								<c:if test="${hrmMap.key eq hrmMapKey}">
				   								      <c:forEach items="${hrmMap.value}" var="hrm">
					   								   ${hrm.value}<br>
					   								   </c:forEach>
									   				</c:if>
				   							 </c:forEach>  
			   							 </td>
									</c:when>
								    <c:otherwise>
									    <td valign="top" align="center" style="padding: 6px;border:1px solid #dadada;">
									  		<c:forEach items="${csMap}" var="csMap"  varStatus="status">
				   								 <c:set var="csKey" value="${teacher.userRegistration.regId}${period.periodId}" />
				   								 <c:if test="${csMap.key eq csKey}">
		   								  			<table width="100%" align="center">
		   								  				<tr><td align="center" height="5">${csMap.value}</td></tr>
						   							 </table>
				   								 </c:if>
			   								</c:forEach>
			   							</td>
								    </c:otherwise>
								   </c:choose> 
						  </c:forEach>
 						  </tr>
						 </c:forEach>
			           </table>
			           </td></tr></table>
	                   </td></tr>
	 			 </c:otherwise>
			 </c:choose>
		</table>
</c:if>
<td>
	<div id="studentDetailsPage"/>
</td>
</body>
</html>