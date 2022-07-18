

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<script type="text/javascript" src="resources/javascript/canvasjs/canvasjs.min.js"></script>
<script type="text/javascript" src="resources/javascript/admin/le_rubric.js"></script>

	
<style type="text/css">
/* .ui-dialog>.ui-widget-header {
	background: #94B8FF;
} */

.ui-dialog>.ui-widget-content {
	background: white;
}
.golLink{
 color:blue;
}
</style>
</head>
<body>
<div align="center">
<input type="hidden" id="tab" name="tab" value="${tab}" />
	<c:set var="bor" value="0"></c:set>
	<c:set var="de" value=""></c:set>
	<c:if test="${fn:length(reportDates) > 0}">
		<c:set var="de" value="des"></c:set>
		<c:set var="bor" value="0"></c:set>
	</c:if>
	<table border="${bor}" class="${de}" align="center">
		<tr>
			<td width="400"><c:if
					test="${fn:length(reportDates) > 0}">
					<div
						class="Divheads">
						<table>
							<tr>
								<td align='center' class="headsColor">REPORTS</font></td>
							</tr>
						</table>
					</div>
				
				 <div style="padding: 2px 5px 2px 5px"><table >
		
			 <c:set var="i" value="0"/>
			 <c:forEach items="${reportDates}" var="dList">
						 <c:set var="i" value="${i+1}"/>
                   <tr> <td style="padding-top:2em"><font color='black'> 
						&nbsp;&nbsp;<a href="#" class="golLink"
							onClick="getStudentReportCard(${dList.iolReportId},${studentId},'${dList.reportDate}','${stat}',${csId})">${dList.reportDate}</a>
						</font></td></tr>

					</c:forEach>
	</table></div></c:if></td></tr></table>
	<table align="center">
	
	<c:if test="${fn:length(reportDates) <= 0}">
											<tr>
												<td align="center" colspan="10">&nbsp;
												</td>												
											</tr>
											<tr>
												<td align="center" colspan="10" id="returnMessage" class="status-message">
													No Reports.
												</td>												
											</tr>
										</c:if>
	</table>
	<div id="monitor" style="display: none;">
		<div id="rioReportsContainer" style="height: 400px; width: 100%;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
		<br>

	</div>

</div>
</body>
</html>