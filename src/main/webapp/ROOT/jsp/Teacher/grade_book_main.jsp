

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
		<link href="resources/css/style.css" rel="stylesheet" type="text/css" />	
		<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
		<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
		
		<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
		<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
		<script type="text/javascript" src="resources/javascript/teacher_popup.js"></script>
		<!-- <script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script> -->
		
		<script src="/resources/javascript/template/common_template.js"></script>	
		<script type="text/javascript">
			var protocol = location.protocol;
		 	var slashes = protocol.concat("//");
		 	var host = slashes.concat(window.location.host);
		 	if(typeof window.history.pushState == 'function') {
			 	window.history.pushState({}, "Hide", host+"/gradeBook.htm#");
		 	}
		</script>	
	</head>
	<body>
		<table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
			<tr>
				<td style="color:white;font-weight:bold" >
					<input type="hidden" id="tab" name ="tab" value="${tab}" />
				</td>
				<td vAlign=bottom align=right>
					<div> 
						<%@ include file="/jsp/Teacher/view_gradebook_tabs.jsp" %>
					</div>
				</td>
			</tr>
		</table>
		<table width="100%">
			<tr>
				<td align="center" colspan="" width="100%" style="padding-top: 0em;"> 
		   	 		<div id="contentDiv">
		   	 			<c:if test="${tab == 'iolReportCard'}">
		   	 				<%@include file="indicator_learning_report_card.jsp"%>
		   	 			</c:if>
		   	 			<c:if test="${tab == 'itemAnalysisReport'}">
		   	 				<%@include file="item_analysis_report.jsp"%>
		   	 			</c:if>
		   	 			<c:if test="${tab == 'grades'}">
		   	 				<%@include file="grade_book_main_sub.jsp"%>
		   	 			</c:if>			   					
					</div>
				</td>
			</tr>
			<tr><td class="content-box" style="margin:0em 0em 0em 10em;" > 
		   		<div id="commonDetailsPage"></div>
        	</td></tr>
			<tr>
				<td class="status-message" height=50 colSpan=80 align="center" id="returnMessage"></td>
			</tr>
		</table>
	</body>
</html>