<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="resources/javascript/canvasjs/canvasjs.min.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/whole_class_iol_grade.js"></script>
	
<style type="text/css">
.ui-dialog>.ui-widget-content {
	background: white;
}
.golLink{
 color:blue;
}
select {
  text-align: center;
  text-align-last: center;
    
 }
option {
  text-align: left;
  font-size:14px; 
  font-weight:500;
 }
 .desTable {
	border: 1px solid grey;
	border-collapse: collapse;
	height: 28%;
	min-height: 28%;
	table-layout: fixed;
	
}
 .tdSty{
  padding-left:1em;
  padding-right:1em;
} 
 .tdwid{
width: inherit;
}   
td textarea,input {
    width: 100%;
    height: 100%;
     
}
button {
    display: block;
    width: 100%;
} 
textarea{
    border: 2px;
    width: 100%;
    -webkit-box-sizing: border-box; /* <=iOS4, <= Android  2.3 */
       -moz-box-sizing: border-box; /* FF1+ */
            box-sizing: border-box; /* Chrome, IE8, Opera, Safari 5.1*/
}
.za{
    border: 2px;
    width: 100%;
    -webkit-box-sizing: border-box; /* <=iOS4, <= Android  2.3 */
       -moz-box-sizing: border-box; /* FF1+ */
            box-sizing: border-box; /* Chrome, IE8, Opera, Safari 5.1*/
}
.label{
	color:rgba(0, 91, 101, 0.92);
    font-family: Cambria, Palatino, "Palatino Linotype", "Palatino LT STD", Georgia, serif;
    font-size:16px; 
    font-weight:600;
    text-shadow: 0 1px 1px rgba(0,0,0,.2);
    vertical-align: middle;
    text-align:center;
}
.tilte{
	color: #041b2d;
    font-family: Cambria, Palatino, "Palatino Linotype", "Palatino LT STD", Georgia, serif;
    font-size:16px; 
    font-weight:600;
    text-shadow: 0 1px 1px rgba(0,0,0,.2);
    vertical-align: middle;
    text-align:center;
    background: radial-gradient(ellipse farthest-side at top,#ebf4f5 20%,#dde8ea 100%);
}
a label {
    cursor: pointer;
}
.pointer {cursor: pointer;}
</style>
</head>
<body>
<c:set var="colorId" value="0"></c:set>
<c:set var="se" value="0"></c:set>
<c:set var="len" value="${fn:length(legendSubCriterias)}" />
<c:set var="wid" value="${90/len}" />
	<%-- <table width='90%' border='1' align='right' height='40px'>
		<tr>
			<c:forEach items="${legendSubCriterias}" var="sc">
				<td width='${wid}' align='center'>${sc.legendSubCriteriaName}</td>
			</c:forEach>
		</tr>
	</table> --%>
	<table width='100%' align='center' border=1 height='40px'  style="border-collapse: collapse;border: 1px solid grey;background-color:#ffffff;background: radial-gradient(ellipse farthest-side at top,#ebf4f5 20%,#dde8ea 100%);";>
	<tr><td height="40" class="tilte tdwid">Student Name</td>
			<c:forEach items="${legendSubCriterias}" var="sc">
				<td width='${wid}' align='center' class="tilte tdwid"><a href="#" onClick="getRubricsBySubcriteriaId(${sc.legendSubCriteriaId},${gradeId},${trimesterId},${teacherRegId})">${sc.legendSubCriteriaName}</a></td>
			</c:forEach>
		</tr>
	<c:forEach items="${studentList}" var="st">
	<c:set var="s" value="stud:${st.student.studentId}" />
	<tr>
	<td width="10%" class="label tdwid"><a href="#" onClick="getCriteriaReportsByStudent(${learningIndicatorIds[s]},${st.student.studentId},'show',${trimesterId},${teacherRegId},'whole')">${st.student.userRegistration.firstName}&nbsp;${st.student.userRegistration.lastName}</a></td>
	<c:choose>
		<c:when test="${fn:length(learnIndValues[s]) gt 0}">
		 <c:set var="z" value="0" />
		<c:forEach var="secCategories" items="${learnIndValues[s]}">
         <input type="hidden" id="learnIndicatorValueId:${st.student.studentId}:${z}" value="${secCategories.learningValuesId}" /> 
							<c:choose>
								<c:when test="${secCategories.legend.legendId==4 }">
									<c:set var="legendColor" value="#f3ce5a"></c:set>
								</c:when>
								<c:when test="${secCategories.legend.legendId==3}">
									<c:set var="legendColor" value="#83db81"></c:set>
								</c:when>
								<c:when test="${secCategories.legend.legendId==2}">
									<c:set var="legendColor" value="#ffff8c"></c:set>
								</c:when>
								<c:when test="${secCategories.legend.legendId==1}">
									<c:set var="legendColor" value="#fc7171"></c:set>
								</c:when>
								<c:otherwise>
									<c:set var="legendColor" value="#72A4D2"></c:set>
								</c:otherwise>
							</c:choose>


							<td width='${wid}' align='center' class="tdwid">
			<table width="100%"  border="0" style="border-collapse: collapse"><tr>
				<td height="20" width="40%">
				<input type="label" id="score" class="pointer" name="rScore" value="${secCategories.legend.legendValue}" size="15" style="height: 36px;background-color:${legendColor}; text-align:center;" onClick="showEvidenceFiles(${secCategories.legendSubCriteria.legendSubCriteriaId},${st.student.studentId},${secCategories.learningIndicator.learningIndicatorId},${z},'show')" readonly/>	
					
				</td><td height="20" align='center' width="60%" class="block tdwid">
				    <textarea cols="25" rows="2" style="text-align:center;background-color:#ffffff" readOnly>${secCategories.notes}</textarea>
				</td></tr>

									<c:choose>
										<c:when
											test="${secCategories.teacherScore.legendId==4 }">
											<c:set var="teacherColor" value="#f3ce5a"></c:set>
										</c:when>
										<c:when
											test="${secCategories.teacherScore.legendId==3}">
											<c:set var="teacherColor" value="#83db81"></c:set>
										</c:when>
										<c:when
											test="${secCategories.teacherScore.legendId==2}">
											<c:set var="teacherColor" value="#ffff8c"></c:set>
										</c:when>
										<c:when
											test="${secCategories.teacherScore.legendId==1}">
											<c:set var="teacherColor" value="#fc7171"></c:set>
										</c:when>
										<c:otherwise>
											<c:set var="teacherColor" value="#72A4D2"></c:set>
										</c:otherwise>
									</c:choose>
									<tr><td width="40%" height="20"><input type="label" id="tscore:${st.student.studentId}:${z}" class="pointer" name="tScore:${st.student.studentId}" value="${secCategories.teacherScore.legendValue}" size="4" style="height: 36px;background-color:${legendColor}; text-align:center;" onClick="showEvidenceFiles(${secCategories.legendSubCriteria.legendSubCriteriaId},${st.student.studentId},${secCategories.learningIndicator.learningIndicatorId},${z},'show')" readonly/></td>
			 <td width="60%" align='center' class="block tdwid"><textarea cols="25" rows="2" id="tcomment:${st.student.studentId}:${z}"	style="text-align:center;background-color:#ffffff" readOnly>${secCategories.teacherComment}</textarea></td></tr>
			</table></td>
			<%-- <td width="${wid/2}" class="txtStyle1 tdSty" style="white-space:nowrap;">${secCategories.legend.legendValue}</td><td class="txtStyle1 tdSty" width="${wid/2}" style="white-space:nowrap;">${secCategories.notes}</td> --%>
			
			
			
		<c:set var="z" value="${z+1}" />		
		</c:forEach>
		</c:when>
		<c:otherwise>
		<c:forEach items="${legendSubCriterias}" var="sc">
		<td width='${wid}' align='center' class="tdwid">
			<table width="100%"  border="0" style="border-collapse: collapse">
			<%-- <tr><td width="40%" height="20" class="tdwid"><input type="label" id="score" class="pointer" name="rScore" value="${secCategories.teacherScore.legendValue}" size="4" style="height: 36px;background-color:${legendColor}; text-align:center;" onClick="showEvidenceFiles(${secCategories.legendSubCriteria.legendSubCriteriaId},${st.student.studentId},${secCategories.learningIndicator.learningIndicatorId},${z},'show')" readonly/></td>
			 <td width="60%" align='center' class="block tdwid"><textarea cols="25" rows="2" 
								style="text-align:center;background-color:#ffffff" readOnly>${secCategories.teacherComment}</textarea></td></tr> --%>
			
			 <tr>
				<td height="20" width="40%">
				<input type="label" id="score" class="pointer" name="rScore" value="-" size="4" style="height: 36px;background-color:#72A4D2 ; text-align:center;"  readonly/>	
					
				</td><td height="20" align='center' width="60%" class="block tdwid">
				    <textarea cols="25" rows="2" style="text-align:center;" readOnly>&nbsp;</textarea>
				</td></tr> 
			  <tr><td width="20%" height="20"><input type="label" id="score" class="pointer" name="rScore" value="-" size="4" style="height: 36px;background-color:#72A4D2; text-align:center;" onClick="" readonly/></td>
			 <td width="80%" align='center' class="block tdwid"><textarea cols="25" rows="2" 
								style="text-align:center;background-color:#ffffff" readOnly>&nbsp;</textarea></td></tr> 
			</table></td>
		</c:forEach>
		</c:otherwise>
		</c:choose>
		</tr>
		
		</c:forEach>
		
	</table>
	<div id="rubricsDialog" align=center style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
	<div id="EvidenceFileDailog" title="LE Files" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
	<div id="CriteriaDialog" title="LearningIndicator" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
	<div id="loading-div-background5" class="loading-div-background"
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