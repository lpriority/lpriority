<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<!-- <link rel="stylesheet" -->
<!-- 	href="resources/javascript/jquery/jquery-ui/jquery-ui.css"> -->
<!-- <link rel="stylesheet" -->
<!-- 	href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css"> -->
<!-- <script src="resources/javascript/jquery/jquery-2.1.4.js"></script> -->
<!-- <script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script> -->
<script type="text/javascript"
	src="resources/javascript/canvasjs/canvasjs.min.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet"
	type="text/css" />
<style>
.desTable {
	border: 1px solid grey;
	border-collapse: collapse;
	height: 8%;
	min-height: 8%;
}
.tdSty{
  padding-left:1em;
  padding-right:1em;
}
</style>
</head>
<body>
	<br>
	<center>
		<label class="sub-title">Multi Year Learning Indicator</label>
	</center>
	<br>
	<c:set var="wid" value="${fn:length(grList)}" />
	<c:set var="widt" value="${100/wid}" />
	<table width="40%" class="desTable" align="center" border=1
		style="height: 60%; min-height: 60%;">
		<tr>
			<td class="iolReport" style="height: 6%; min-height: 6%;">Student
				Name</td>
			<td class="txtStyle1"><c:out value="${studentName}"></c:out></td>
		</tr>
		<tr>
			<td class="iolReport" style="height: 6%; min-height: 6%;">&nbsp;</td>
			<td bgcolor="#CA8403" class="mulReport">Grades 3-8 Learning
				Indicator</td>
		</tr>
		<tr>
			<td class="iolReport">&nbsp;</td>
			<td class="iolReport">English Language Arts (ELA)</td>
		</tr>
		<tr>
			<td class="iolReport">Grade Level</td>
			<td>
				<table width="100%" style="height: 100%; min-height: 100%;"
					class="desTable" border="1">
					<tr>
						<c:forEach items="${grList}" var="gList">
							<td class="txtStyle1 tdSty" width="${widt}" style="white-space:nowrap;">
 							<label>${gList.masterGrades.masterGradesId}</label> 
							</td>
						</c:forEach>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="txtStyle1">CAASPP Level</td>
			<td><table width="100%" style="height: 100%; min-height: 100%;"
					class="desTable" border="1">
					<tr>
						<c:forEach items="${grList}" var="gList">
							<c:set var="i" value="0" />
							<c:forEach items="${caaSppELAScores}" var="ela">
								<c:if
									test="${ela.grade.masterGrades.masterGradesId eq gList.masterGrades.masterGradesId}">
									<fmt:parseNumber var="elaScore" type="number" value="${ela.caasppScore}" />
									<c:choose>
										<c:when test="${elaScore>=3}">
											<c:set var="bgcolor" value="lime" />

										</c:when>
										<c:when test="${elaScore>=1.5 && elaScore<3}">
											<c:set var="bgcolor" value="yellow" />
										</c:when>
										<c:when test="${elaScore>=1 && elaScore<1.5}">
											<c:set var="bgcolor" value="red" />
										</c:when>
									</c:choose>
									<td bgcolor="${bgcolor}" class="txtStyle1 tdSty" width="${widt}" style="white-space:nowrap;">
	                                    <label>${ela.caasppScore}</label> 
										 <c:set var="i" value="1" />
									</td>
								</c:if>
							</c:forEach>
							<c:if test="${i eq 0}">
								<td class="txtStyle1 tdSty" width="${widt}" style="white-space:nowrap;">
                                 <label>-</label> 
								</td>
							</c:if>
						</c:forEach>
					</tr>
				</table></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="iolReport">Mathematics</td>
		</tr>
		<tr>
			<td class="txtStyle1">CAASPP Level</td>
			<td><table width="100%" style="height: 100%; min-height: 100%;"
					class="desTable" border="1">
					<tr>
						<c:forEach items="${grList}" var="gList">
							<c:set var="i" value="0" />
							<c:forEach items="${caaSppMathScores}" var="mat">
								<c:if
									test="${mat.grade.masterGrades.masterGradesId eq gList.masterGrades.masterGradesId}">
									<fmt:parseNumber var="mathScore" type="number"
										value="${mat.caasppScore}" />
									<c:choose>
										<c:when test="${mathScore>=3}">
											<c:set var="bgcolor" value="lime" />

										</c:when>
										<c:when test="${mathScore>=1.5 && mathScore<3}">
											<c:set var="bgcolor" value="yellow" />
										</c:when>
										<c:when test="${mathScore>=1 && mathScore<1.5}">
											<c:set var="bgcolor" value="red" />
										</c:when>
									</c:choose>
									<td bgcolor="${bgcolor}" class="txtStyle1 tdSty" width="${widt}" style="white-space:nowrap;">
 										<label>${mat.caasppScore}</label> 
										<c:set var="i" value="1" />
									</td>
								</c:if>
							</c:forEach>
							<c:if test="${i eq 0}">
								<td class="txtStyle1 tdSty" width="${widt}" style="white-space:nowrap;">
 								<label>-</label> 
								</td>
							</c:if>
							<%--   <td width="${widt}">&nbsp;</td> --%>
						</c:forEach>
					</tr>
				</table></td>
		</tr>
		<tr>
			<td class="iolReport">5Cs</td>
			<td class="iolReport">21st Century Practices</td>
		</tr>
		<c:forEach items="${criteriaScores}" var="mapItem">
			<c:if test="${mapItem.key ne 'Literacies'}">
				<tr>
					<td class="iolReport">${mapItem.key}</td>
					<td><table width="100%"
							style="height: 100%; min-height: 100%;" class="desTable"
							border="1">
							<tr>
								<c:forEach items="${grList}" var="gList">
									<c:set var="i" value="0" />
									<c:forEach items="${mapItem.value}" var="learnInd">
										<c:if
											test="${learnInd.iolReport.classStatus.section.gradeClasses.grade.masterGrades.masterGradesId eq gList.masterGrades.masterGradesId}">
											<fmt:parseNumber var="lscore" type="number"
												value="${learnInd.leScore}" />
											<c:choose>
												<c:when test="${lscore>=3}">
													<c:set var="bgcolor" value="lime" />

												</c:when>
												<c:when test="${lscore>=1.5 && lscore<3}">
													<c:set var="bgcolor" value="yellow" />
												</c:when>
												<c:when test="${lscore>=1 && lscore<1.5}">
													<c:set var="bgcolor" value="red" />
												</c:when>
											</c:choose>
											<td bgcolor="${bgcolor}" class="txtStyle1 tdSty" width="${widt}">
                                                    <label>${learnInd.leScore}</label>
												<c:set var="i" value="1" />
											</td>
										</c:if>
									</c:forEach>
									<c:if test="${i eq 0}">
										<td width="${widt}" class="txtStyle1 tdSty">
                                       <label>-</label>
										</td>
									</c:if>

								</c:forEach>
							</tr>
						</table></td>
				</tr>
			</c:if>
		</c:forEach>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td class="iolReport">Legend</td>
			<td><table width="100%" style="height: 100%; min-height: 100%;"
					class="desTable" border="1">
					<tr>
						<c:forEach items="${multiYearLegends}" var="legList">
							<td bgcolor="${legList.mulYrLegendColor}" class="txtStyle1"
								width="25%"><c:out value="${legList.mulYrLegendId}"></c:out></td>
						</c:forEach>
					</tr>
				</table></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><table width="100%" style="height: 100%; min-height: 100%;"
					class="desTable" border="1">
					<tr>
						<c:forEach items="${multiYearLegends}" var="legList">
							<td bgcolor="${legList.mulYrLegendColor}" class="txtStyle1"
								width="25%"><c:out value="${legList.mulYrLegendName}"></c:out></td>
						</c:forEach>
					</tr>
				</table></td>
		</tr>
	</table>
</body>
</html>