<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Grade Word Work
</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/Student/student_test_results.js"></script>
<script type="text/javascript" src="resources/javascript/Student/rflp_test.js"></script>
<script type="text/javascript" src="/dwr/interface/studentTestResultsService.js"></script>
<style>
.des {
	border: 1px solid black;
	border-collapse: collapse;
	border-color: #d3d3d3;
}

hr {
	border-top: 1px solid #d3d3d3;
}

.ques {
	font-family: "Georgia";
	font-size: 16px;
}

.tit {
	font-family: "Georgia";
	font-size: 15px;
}

.answ {
	font-family: "Georgia";
	font-size: 16px;
	font-weight: bold;
}
</style>
</head>
<body>
	<table style="width: 100%">
		<tr>
			<td style="" align="right"><input type="hidden" id="usedFor"
				name="usedFor" value="${usedFor}" /> <input type="hidden" id="tab"
				name="tab" value="${tab}" /></td>
			<td style="" align="right" valign="bottom" width="100%">
				<div>
					<%@ include file="view_rti_tabs.jsp"%>
				</div>
			</td>
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="title-pad">
		<tr>
			<td class="sub-title title-border" height="40" align="left"
				colspan="10">
				<c:choose>
					<c:when test="${gradeTypesId==2}">   
						Self Word Work Grading <br>
					</c:when>
					<c:otherwise>
						Peer Word Work Grading <br>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		class="title-pad">
		<tr>
			<td height="20" style="width: 100%;" align="center" valign="top"><table
					width="100%" height="20" border="0" cellpadding="0" cellspacing="0"
					style="padding-top: 0.5em">
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td width="35" align="left" valign='top'><label class="label">Class</label></td>
						<td width="90" align="left">
							<select name="csId" class="listmenu" id="csId" onChange="loadRFLPHomeworks(${gradeTypesId})">
								<option>Select Class</option>
								<c:forEach items="${studentClassList}" var="studentClasses">
								<c:set var="itemNums" scope="request" value="${itemNums + 1}" />
									<option value="${studentClasses.classStatus.csId}">
										${studentClasses.gradeClasses.studentClass.className}</option>
								</c:forEach>
							</select>
							
							<script>
									<c:if test='${itemNums >0}' >
									csId.options[1].selected = true;
										$(function () {
										    $("select#csId").change();
										});
									</c:if>	
							</script>
												
							
						</td>
						<td width="35" align="left" valign="top">
							<label class="label"></label>
						</td>
						<td width="150" align="left" valign="bottom" class="header"></td>
						<td width="300">&nbsp;</td>
						<td width="300" align="left" valign="top">
							<font color="blue">
								<%=request.getParameter("msg") == null ? "" : request.getParameter("msg")%>
							</font>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td style="width: 100%" align="center" valign="top">
				<form name="evaluatehomework" action="javascript:void(0)">
					<table style="width: 94%">
						<tr>
							<td></td>
							<td><table id="RflpHomeworks" align="center" width="70%"></table></td>
						</tr>
					</table>
					<table>
						<tr><td><div id="rflpHomeQuestionsList" style="width: 90%"></div></td>
					</table>
					<table>
						<tr>
							<td height="35" align="center" valign="middle" class="tabtxt"></td>
							<td height="35" align="center" valign="middle" class="tabtxt"></td>
							<td height="35" align="center" valign="middle" class="tabtxt"></td>

							<td height="35" align="center" valign="middle" class="tabtxt"></td>
							<td height="35" align="center" valign="middle" class="tabtxt"></td>
							<td height="35" align="center" valign="middle" class="tabtxt"></td>
							<td height="35" align="center" valign="middle" class="tabtxt"></td>
							<td height="35" align="center" valign="middle" class="tabtxt"></td>
						</tr>
						<tr>
							<td height="10" align="center" valign="middle" class="tabtxt"></td>
							<td height="10" align="center" valign="middle" class="tabtxt"></td>
							<td height="10" align="center" valign="middle" class="tabtxt"></td>
							<td height="10" align="center" valign="middle" class="tabtxt"></td>
							<td height="10" align="center" valign="middle" class="tabtxt"></td>
							<td height="10" align="center" valign="middle" class="tabtxt"></td>
							<td height="10" align="center" valign="middle" class="tabtxt"></td>
							<td height="10" align="center" valign="middle" class="tabtxt"></td>
							<td height="10" align="center" valign="middle" class="tabtxt"></td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
		<tr>
			<td height="2" colspan="2"></td>
		</tr>
		<tr>
			<td height="30" colspan="2"></td>
		</tr>
		<tr>
			<td height="30" colspan="2"></td>
		</tr>
	</table>
</body>
</html>