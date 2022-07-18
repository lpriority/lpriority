<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<link href="resources/javascript/jplayer/skin/blue.monday/css/jplayer.blue.monday.min.css" rel="stylesheet" type="text/css"/>
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
<title>Reading Skills Development Dashboard</title>
</head>
<body>
	<c:if test="${userReg.user.userType == 'student above 13' || userReg.user.userType == 'student below 13' }">
		<table width="99.8%" border="0" cellspacing="0" cellpadding="0"
			class="title-pad" style="padding-top: 1em">
			<tr> 
				<td class="sub-title title-border" height="40" align="left">Reading Skills Development Dashboard
				</td>
			</tr>
		</table> 
	</c:if>
	<table>
	<%-- <tr>
					          <td height="2" colspan="2"><br>
					             <div id="videoDiv">
		                          <c:if test="${userReg.user.userType == 'student above 13' || userReg.user.userType == 'student below 13' }">
				                       <a href="#" onClick="openVideoDialog('student','student_access_RSDD','videoDailog')" class="demoVideoLink"><i class="ion-videocamera" aria-hidden="true" style="font-size:26px;font-weight:bold;color:#e23c00;"></i>&nbsp;video for Access Reading Skills Development Dashboard</a>
		 	                     </c:if>
		 	                     </div>
			                  </td>
	   						</tr> --%>
	</table>
	<table style="width: 70%"  class="title-pad">
		<tr> 
			<td align="center">
				<div align="center" style="padding-top: 1em">		 
					 <c:choose>
					 	<c:when test="${fn:length(studentRRDDFiles) eq 0}"><div class="status-message">No Files Available</div></c:when>
						<c:otherwise> 
							<div class="Divheads">List of Student Files</div>
							<div style="padding: 1em"  class="DivContents"></div>							
							<c:forEach items="${studentRRDDFiles}" var="file">
								<div align="left" class="DivContents">  
									<a href="viewPDForImage.htm?filePath=${file.filePath}" target="_blank" >${file.fileName}</a>
									<a href="downloadFile.htm?filePath=${file.filePath}" >Download</a>   
								</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</div>
			</td>
		</tr>
	</table>
	<div id="videoDailog" title="Grade Fluency" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
</body>
</html>