<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<script type="text/javascript" src="resources/javascript/admin/student_reports.js"></script>
<script src="resources/javascript/Adminjs.js"></script>
<script src="resources/javascript/admin/common_dropdown_pull.js"></script>
</head>
<body>	
		<table align="right" width="100%">
			<tr>
				<td colspan="10" width="80%" align="right">
					<div align="right">
						<%@include file="/jsp/Admin/attendance_tabs.jsp"%>
					</div>
				</td>
			</tr>
		</table>
		<br><br><br>
		<div align="right" id= "viewAttendence">
			<%@include file="/jsp/Admin/sub_view_attendance.jsp"%>
		</div>
</body>
</html>