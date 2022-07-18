<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css">
<script type="text/javascript" src="resources/javascript/canvasjs/canvasjs.min.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.ui-dialog > .ui-widget-content {background: white;}
</style>

</head>
<body>
    <div align="center">
    	<table class="des" border=0 align="center"><tr><td>
			<div class="Divheads">
				<table>
					<tr>
						<th  align="left" width="108">Error Word</th>
						<th  align="center" width="180">Count</th>	
						<th  align="left" width="108">Error Word</th>
						<th  align="center" width="200">Count</th>							
					</tr>
				</table>
			</div>
			<div class="DivContents">
				<table>
					<tr><td>&nbsp;</td></tr>
					<c:if test="${fn:length(errorWordsList) eq 0}">
						<tr><td  width="600" align="center"><label class="label">No data Found</label></td></tr>						
					</c:if>
					<c:forEach items="${errorWordsList}" var="cList" varStatus="status">					
						<c:if test="${status.index % 2 eq 0}">
							<tr>								
						</c:if>
							<td  align="left" width="150" style="padding-bottom: 15px;font-size:13px;" class='txtStyle'>
								${cList.key}
							</td>					
							<td  align="center" width="150" style="padding-bottom: 15px;">
								${cList.value}
							</td>
						<c:if test="${status.index % 2 eq 1}">
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div></td></tr>
		</table>		
	</div>
</body>
</html>