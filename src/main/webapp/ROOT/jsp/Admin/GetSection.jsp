<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<c:if test="${fn:length(secList)>0}">
	<table class="des" border=0 id="togglePage"><tr><td><div class="Divheads">
		<table>
			<tr>
				<td width="20" height='30' align='center' valign='middle' class='header'></td>
				<td width='220' height='30' align='center' valign='middle'
					class='headsColor'>Section
						Name</td>
				<td width='80' height='30' align='center' valign='middle'
					class='headsColor'>Edit
				</td>
				<td width='100' height='30' align='center' valign='middle' class='headsColor'>Delete
				</td>
				
	
			</tr></table></div><div class="DivContents" style="overflow: auto;"><table>
	        <tr><td>&nbsp;</td></tr>
			
			<c:forEach items="${secList}" var="sec" varStatus="i">
	             <tr class='tabtxt'><td width='30' height='30' align='center' valign='middle' class='header' ></td>
	             <td width='200' height='30' align='center' valign='middle' class='header' >
	             <input type='text' name="sectionName" id="sectionName${i.count}" value="${sec.section}" />
	             </td>
	             <td width='100' height='30' align='center' valign='middle' class='header'>
	             <input type='checkbox' class="checkbox-design" name="edit${i.count}" id="edit${i.count}" value="${sec.sectionId}" onClick='updateSection(${i.count})'/><label for="edit${i.count}" class="checkbox-label-design"></label>
	             </td><td>&nbsp;</td>
	             <td width='100' height='30' align='center' valign='middle' class='header'>
	             <input type='checkbox' class="checkbox-design" id="del${i.count}" name="del${i.count}" value="${sec.sectionId}" onClick='DeleteCheckSection(${i.count})'/><label for="del${i.count}" class="checkbox-label-design"></label></td>
	          </tr>
			</c:forEach>	
		</table></div></td></tr></table>
	</c:if>
	<c:if test="${fn:length(secList)==0}">
		<table>
			<tr>
				<td width='100%' height='30' align='center'>
					<span class="status-message">No Sections Created</span>
				</td>
			</tr>
		</table>
	</c:if>
</body>
</html>