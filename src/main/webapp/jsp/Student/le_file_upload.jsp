<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="true"%>
<link
	href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css"
	rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet"
	type="text/css" />
<script>
	
</script>
<div>
	<form name="perFileForm" action="leFileSave.htm" id="perFileForm"
		method="post" enctype="multipart/form-data">
		<table align="center" style="padding: 1cm;visibility:${status}"
			class="tabtxt">
			<tr>
				<td><input type="hidden" name="createdBy" id="createdBy"
					value="${createdBy}" /> <input type="hidden" name="subCriteriaId"
					id="subCriteriaId" value="${subCriteriaId}" />
					 <input type="hidden" name="learningIndicatorId" id="learningIndicatorId" value="${learningIndicatorId}" /> 
					 <input type="hidden" name="learnIndiValueId" id="learnIndiValueId" value="${learnIndiValueId}"> 
					 <input type="hidden" name="index" id="index" value="${index}" /> 
					 <input type="hidden" name="upStatus" id="upStatus" value="${upStatus}">
					Additional Media: <input name="file" id="fileId" type="file"
					onChange="lefileSave(this,${learningIndicatorId})" /> <br>
				<br> 
				</td>
			</tr>
			<tr>
				<td></td>
			</tr>
		</table>
		<table align="center" id="showFiles" border=1 class="dess">
			<tr>
				<td><table class="Divheads">
						<tr>
							<td width="400" align="center">Uploaded File</td>
						</tr>
					</table>
					<table class="DivContents" style="padding-top: 1em">
						<c:set var="count" value="1" scope="page" />
						<c:choose>
							<c:when test="${fn:length(fileList) > 0}">
								<c:forEach items="${fileList}" var="file">
									<tr>
										<td width="400" height="20" class="txtStyle"><c:out
												value="${file.fileName}" /> <input type="hidden"
											id="fileName" name="fileName" value="${file.fileName}" /> <input
											type="hidden" id="filePath" name="filePath"
											value="${file.filePath}" />&nbsp;&nbsp;&nbsp; <a
											href="downloadFile.htm?filePath=${file.filePath}">Download</a>&nbsp;&nbsp;
										</td>
									</tr>
									<c:set var="count" value="${count + 1}" scope="page" />
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td width="400" class="txtStyle">No Files are Uploaded</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table></td>
			</tr>
		</table>
	</form>
</div>
