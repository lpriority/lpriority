
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Assign Rubric</title>
<script type="text/javascript"
	src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript"
	src="resources/javascript/admin/le_rubric.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet"
	type="text/css" />

<script>
	$(document).ready(function() {
		$('#returnMessage').fadeOut(4000);
	});
</script>
</head>
<body>
	<table width="100%" height="100%" border=0 align="center" cellPadding=0
		cellSpacing=0>
		<tr>
			<td vAlign=bottom align=right>
				<div>
					<%@ include file="/jsp/CommonJsp/le_rubric_tab.jsp"%>
				</div>
			</td>
		</tr>
	</table>
	<form:form id="createRubric">
		<table width="100%" height="100%" border=0 align="center"
			cellPadding=0 cellSpacing=0>
			<tr>
				<td>&nbsp;<br></td>
			</tr>
			<tr>
				<td height="30" colspan="2" align="left" width="100%">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						align="left" style="padding-left: 8em;">
						<tr>
							<td><input type="hidden" id="teacherObj"
								value="${teacherObj}" /></td>
						</tr>
						<tr>
							<td height="40" align="left" class="label" style="width: 14em;">Criteria
								&nbsp;&nbsp;&nbsp; <select id="criteriaId" name="criteriaId"
								class="listmenu" onChange="getSubCriterias()">
									<option value="select">Select</option>
									<c:forEach items="${legendCriteriaLst}" var="lc">
										<option value="${lc.legendCriteriaId}">${lc.legendCriteriaName}</option>
									</c:forEach>
							</select>
							</td>
							<td height="40" class="label"
								style="width: 30em; padding-left: 2em;">SubCriteria
								&nbsp;&nbsp;&nbsp; <select id="subCriteriaId"
								name="subCriteriaId" class="listmenu"
								onChange="getRubricValues()">
									<option value="select">select SubCriteria</option>
							</select>
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>

			</tr>
			<tr>
				<td height=40 colSpan=3></td>
			</tr>
			<tr>
				<td id="showRubric" colspan="5" align="center" valign="top"></td>
			</tr>
			<tr>
				<td colspan="4" class="space-between" align="center"><label
					id="result" class="status-message"></label></td>
			</tr>
		</table>
		<tr>
			<td></td>
		</tr>

	</form:form>
</body>
</html>





