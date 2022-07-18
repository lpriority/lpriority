
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Classes</title>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/admin/add_edit_remove_sl_schl.js"></script>
<script type="text/javascript" src="resources/javascript/Adminjs.js"></script>
</head>
<body>
	<table width="100%" class="title-pad" style="padding-top: 0.5em">
		<tr>
			<td class="sub-title title-border"  height="40" align="left" valign="middle">Add
				Classes</td>
		</tr>
	</table>
	<!-- Content center body -->
	<form:form name="addclasses">
		<table style="width: 100%;"border="0" cellspacing="0" cellpadding="0" class="title-pad heading-up">
			<tr>
				<td height="30" valign="middle" align="left" class="label" style="font-size:15;">
					Grade  &nbsp;&nbsp;&nbsp;&nbsp; 
					<select id="gradeId" name="gradeId" onchange="getClasses()" class="listmenu">
						<option value="">Select</option>
						<c:forEach items="${sgList}" var="ul">
							<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
						</c:forEach>
				</select>
				</td>
			</tr>
		</table>
		<table style="width: 100%;"border="0" cellspacing="0" cellpadding="0" align="center">
			<tr>
				<!-- Content center start -->
				<td height="0" valign="top" align="center"  class="txtStyle"><span id="showclasses"></span></td>
			</tr>			
		</table>
	</form:form>
</body>
</html>
