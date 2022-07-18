<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="true"%>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/my_files.js"></script>

<form name="fileupload" id="fileupload" action="uploadTeacherFiles.htm" enctype="multipart/form-data" method="post">
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
	<tr><td width="5%" height="20" align="center" style="vertical-align: top;"><td></tr>
	<tr>
		
		<td width="50%" height="20" align="center" class="tabtxt" style="vertical-align: top;">
			Lessons Names</td>
		<td width="50%" height="20" align="center" class="tabtxt" style="vertical-align: top;">
				AddFile</td>
		
	</tr>	
	<tr><td width="253px" height="20" align="center" style="vertical-align: top;"><td></tr>
	<c:set var="lessonCount" value="1" scope="page" />
	<input type="hidden" name="totalLessons" value="${fn:length(lessonsList)}" />
	<c:forEach items="${lessonsList}" var="lesson" varStatus="status">	
	<tr><td width="253" height="10" align="center" style="vertical-align: top;"><td></tr>
	<tr  >
		<td width="50%" height="20" align="center" class="tabtxt" style="vertical-align: top;"  >					
			<c:out  value="${lesson.lessonName}"/>
			<input type="hidden" name="lessonName:${lessonCount}" value="${lesson.lessonName}" />
			<input type="hidden" name="lessonId${status.index}" value="${lesson.lessonId}" />
			<input type="hidden" name="unitId${status.index}" value="${unitId}" />
		</td>		
		<td width="50%" height="20" align="center" class="tabtxt" style="vertical-align: top;">
            <input type="file" name="file[]" id="file" multiple="multiple" ></input>
		</td>		
	</tr>
	<c:set var="folderCount" value="${lessonCount + 1}" scope="page"/>
	</c:forEach>
	<tr>
		<td colspan="2" align="center" valign="center"><input type="submit"  value="Submit"></td>
	</tr>	
</table>
</form>
