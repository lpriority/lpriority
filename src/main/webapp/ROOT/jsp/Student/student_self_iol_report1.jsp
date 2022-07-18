<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../CommonJsp/include_resources.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>
<link href="resources/css/iol_dial.css" rel="stylesheet" type="text/css" />
<style>
/* body{margin:40px;} */
table.one {
	border-style: solid;
	border-color: #7b8e86;
}
</style>
<script type="text/javascript" src="resources/javascript/common/multi_iol_report.js"></script>
<script type="text/javascript" src="resources/javascript/common/goal_setting_tool.js"></script>
<link href="resources/css/iol_report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/admin/le_rubric.js"></script>
</head>
<body>
	<form name="reportCard">
   <table align="center"><tr><td id="successMsg">
   <input type="hidden" name="tab" id="tab" value="${tab}" />
   </td></tr></table>
		<table align="center" width="65%">
			<tr>
				<td>
				<input type="hidden" name="learningIndicatorId" id="learningIndicatorId" value="${litracyLearnIndValues[0].learningIndicator.learningIndicatorId}"><img
					src="loadSchoolCommonFile.htm?schoolCommonFilePath=${schoolImage}"
					width="200" height="100">
				<td><label class="report-headers">21st Century Learning Indicator</label><label class="report-contents">&nbsp;</label> <br>
				<br> <label class="tabtxts">Student Name
						&nbsp;:&nbsp;&nbsp;</label><label class="report-contentss"><c:out
							value="${studentName}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><label
					class="tabtxts"> Teacher &nbsp;:&nbsp;&nbsp;</label><label
					class="report-contentss">
						${teacher.userRegistration.title}&nbsp;${teacher.userRegistration.firstName}&nbsp;${teacher.userRegistration.lastName}</label><br>
				<br> <label class="tabtxts"> School &nbsp;:&nbsp;&nbsp;</label><label
					class="report-contentss">${teacher.userRegistration.school.schoolName}</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label
					class="tabtxts">Grade&nbsp; :&nbsp;&nbsp;</label><label
					class="report-contentss"><c:out value="${gradeLevel}" /> </label></td>
			</tr>
		</table>
        <table align="center" width="65%"><tr><td class="report-headers" align="right"><div class="button_green" align="right" onClick="getMultiYearIOLReport(${studentId},${teacher.teacherId},'${litracyLearnIndValues[0].learningIndicator.iolReport.classStatus.section.gradeClasses.studentClass.className}')">Multi Year Learning Indicator</div></td></tr></table>
		<table align="center" border="0"  width="65%" style="border-collapse:collapse;border: 1px solid #bec9cc;">
		<tr class="Divheads" style="height:50%"><td class="report-headers" style="color:white" align="center">
		<br>
		THE LEARNING INDICATOR<br><br>
		</td></tr>
		<tr><td colspan="5" style="background: #e9f2f3;">
		<table width="65%" align="center"  >
		<tr><td>&nbsp;</td> </tr></table>
		<table width="65%" align="center" >
		<tr>  
		<c:set var="cou" value="0" />
		 <c:set var="rows" value="5" />
		<c:set var="rowWidth" value="${40/rows}" />
		
		<c:forEach items="${learnInds}" var="lcr">
		<c:set var="legendColor" value="#82CAFA"></c:set>
		<c:if test="${lcr.legendCriteria.legendCriteriaName ne 'Literacies'}">
		<td class="tabtxt" width="${rowWidth}%" align="center">${lcr.legendCriteria.legendCriteriaName}<br>
		<c:choose>
		    <c:when test="${lcr.leScore>=3}">
		       <c:set var="legendColor" value="#83db81"></c:set>
		    </c:when>
		    <c:when test="${lcr.leScore>1.5 && lcr.leScore<3}">
		      <c:set var="legendColor" value="#ffff8c"></c:set>
		    </c:when>
		    <c:when test="${lcr.leScore>=1 && lcr.leScore<1.5}">
		      <c:set var="legendColor" value="#fc7171"></c:set>
		    </c:when>
		      </c:choose>
		<button type="button" id="but${lcr.learningIndicatorId}" class="btn-circle btn-xl"  style="background-color:${legendColor}" onClick="getSubCriteriasByCriteriaId(${lcr.learningIndicatorId},${studentId},'${upStatus}',${trimesterId},${teacher.userRegistration.regId},'part')">
		<div id="set${lcr.learningIndicatorId}"> 
		<c:choose>
		<c:when test="${empty(lcr.leScore) || lcr.leScore eq 0}">0
		</c:when>
		<c:otherwise>
		${lcr.leScore}
		</c:otherwise>
		</c:choose>
		</div></button>
		</td>
		<c:set var="cou" value="${cou+1}" />
		<c:if test="${cou eq 5}">
		</tr><tr><td>&nbsp;</td></tr></table>
		<table width="65%" align="center"><tr>
         <c:set var="rows" value="6" />
		<c:set var="rowWidth" value="${40/rows}"/>
		</c:if>
		
		 </c:if>
		</c:forEach>
			
 		<c:forEach items="${litracyLearnIndValues}" var="lstSub">
 		<td class="tabtxt" width="${rowWidth}%" align="center">${lstSub.legendSubCriteria.legendSubCriteriaName}<br> 
 		<c:choose>
 		    <c:when test="${lstSub.teacherScore.legendId>=3}"> 
 		       <c:set var="legendCol" value="#83db81"></c:set> 
 		    </c:when> 
 		    <c:when test="${lstSub.teacherScore.legendId>1.5 && lstSub.teacherScore.legendId<3}"> 
 		      <c:set var="legendCol" value="#ffff8c"></c:set> 
 		    </c:when> 
 		    <c:when test="${lstSub.teacherScore.legendId>=1 && lstSub.teacherScore.legendId<1.5}"> 
 		      <c:set var="legendCol" value="#fc7171"></c:set>
 		    </c:when> 
 		    <c:otherwise> 
 		     <c:set var="legendCol" value="#82CAFA"></c:set> 
 		    </c:otherwise> 
 		</c:choose> 
 		<button type="button" id="butt${lstSub.learningValuesId}" class="btn-circle btn-xl" style="background-color:${legendCol}" onClick="getSubCriteriaValues(${lstSub.learningValuesId},${studentId},'${upStatus}',${trimesterId},${teacher.userRegistration.regId})" value="0"> 
        <div id="sett${lstSub.learningValuesId}">  
 		<c:choose> 
 		<c:when test="${empty(lstSub.teacherScore.legendId) || lstSub.teacherScore.legendId eq 0}">0 
 		</c:when> 
 		<c:otherwise> 
 		${lstSub.teacherScore.legendId} 
 		</c:otherwise>
 		</c:choose> 
 		</div></button> 
 		</td> 
 	</c:forEach> 
		</tr>
		<tr><td>&nbsp;</td></tr>
       
		</table></td></tr>
		<tr><td style="background: #e9f2f3;" align="center">
		<input type="button" class="goal" value="Goal Setting" onClick="goToGoalSettingTool(${studentId})" /></td></tr>
		<tr><td style="background: #e9f2f3;">&nbsp;</td></tr>
		</table>
		<table align="center"><tr><td id="succMsg"></td></tr></table>
	</form>
	<div id="CriteriaDialog" title="Criteria" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
	<div id="MultiReportsDialog" title="Multi Year Learning Indicator" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
	<div id="goToGoalSettingTool" title="Goal Setting Tool" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>

<table align="center"><tr><td><input type="button" value="Done" class="button_done_round" onClick="submitIOLReportCard(${litracyLearnIndValues[0].learningIndicator.iolReport.iolReportId},${studentId},${csId},${trimesterId})" /></td></tr></table>
<div id="loading-div-background1" class="loading-div-background"
	style="display: none;">
	<div class="loader"></div>
	<div id="loading-div" class="ui-corner-all loading-div"
		style="color: #103c51; left: 50%; padding-left: 12px; font-size: 17px; margin-top: -8.5em;">
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>Loading....
	</div>
</div>
</body>
</html>