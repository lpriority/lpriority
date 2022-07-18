<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="true"%>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/my_files.js"></script>
<title>Files</title>
<script type="text/javascript">
function fileToggle(id) { 
    var divObject = document.getElementById('divid:' + id);
    var folderName = document.getElementById("folderName"+id).value;
    var folderSize = 0;
    if($("#table"+id+" > tbody").children().length > 0)
    	folderSize = $("#table"+id+" > tbody").children().length;
    if(divObject){
	     if (divObject.style.display == "") {
	         divObject.style.display = "none";
	         document.getElementById('anchorid:' + id).innerHTML = "<i class='fa fa-folder arrow' aria-hidden='true'></i><span id='folderId"+id+"' class='folderStyle'> "+folderName+" </span><span class='fileCountStyle'>&nbsp;&nbsp;("+folderSize+") </span>";
	     } else { 
	        divObject.style.display = "";
	        document.getElementById('anchorid:' + id).innerHTML = "<i class='fa fa-folder-open arrow' aria-hidden='true'></i><span id='folderId"+id+"' class='folderStyle'> "+folderName+" </span><span class='fileCountStyle'>&nbsp;&nbsp;("+folderSize+") </span>";
	     }
    }
}
</script>
<style>
 .ss{ 
  	padding-left:15em; 
 } 
 .arrow{
	 font-size:20px;
	 color:#09b7cd;
	 padding: 2px;
	 text-shadow:0 1px 3px rgb(216, 223, 224);
 }
 .box {
	position:absolute;
	width:100px;
	height:40px;
	background:#ffffff;
	z-index:51;
	padding:5px;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border-radius: 2px;
	-moz-box-shadow:0px 0px 5px 1px #BCE2E7;
	-webkit-box-shadow:0px 0px 5px 1px #BCE2E7;
	box-shadow:0px 0px 5px 1px #BCE2E7;
	display:none;
	resize: none;
	border:solid 1px #C6C6C6;
	color:black;
 }
 .fileCountStyle{ 
  	color: #cb7900;
  	font-size:14px;
  	font-weight:bold;
 } 
 
a{ text-decoration: none; }
 
 .my-tooltip {
    background-color:#000;
    border:1px solid #fff;
    padding:10px 15px;
    width:200px;
    display:none;
    color:#fff;
    text-align:left;
    font-size:12px;
 
    /* outline radius for mozilla/firefox only */
    -moz-box-shadow:0 0 10px #000;
    -webkit-box-shadow:0 0 10px #000;
}
</style> 
<table border=0 cellSpacing=0 cellPadding=0 width="100%" align="center">
	<c:choose>
		<c:when	test="${userReg.user.userType == 'teacher' && (title == 'Admin Files' || title =='Upload Private Files')}">
			
			<tr>
				<td  vAlign=top colSpan=3 align=middle>
					<%-- <table width="100%" border=0 align="center" cellPadding=0
						cellSpacing=0><tr>
						<td valign="bottom" align="right">
		              	<ul class="button-group">
							<li><a href="displayTeacherClassFiles.htm?fileType=public&page=public" class="${(fileType == 'public')?'buttonSelect leftPill':'button leftPill'}"> Class Files </a></li>
							<li><a href="displayTeacherClassFiles.htm?fileType=private" class="${(fileType == 'private')?'buttonSelect':'button'}"> Private Files </a></li>
							<li><a href="loadTeacherAdminFiles.htm?fileType=public" class="${(fileType == 'admin')?'buttonSelect':'button'}"> Admin Files </a></li>
							<li><a href="displayTeacherStudentFiles.htm?fileType=student" class="${(fileType == 'student')?'buttonSelect rightPill':'button rightPill'}"> Student Files </a></li>
						</ul>
					</td>	
						</tr>
					</table></td></tr>
                   <table width="100%" border="0" cellspacing="0" cellpadding="0" class="title-pad" style="padding-top: 1em">
                        <tr>
                            <td class="sub-title title-border" height="40" align="left">Admin Files</td>
                        </tr>
                   </table>	 --%>		
				<tr><td>
				<br><br>
					<c:set var="ss" value="center"></c:set> 			  
				</c:when>
				<c:otherwise>
				<c:set var="ss" value="left"></c:set> 
                 <br>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when
					test="${(userReg.user.userType eq 'teacher') && (fileType eq 'public') 
						  || (userReg.user.userType eq 'parent') && (fileType eq 'studentFiles')}">
							<table width="78%" height="10%" align="center" class="des" border=0><tr><td>
								<div class="Divheads"><table width="100%">
		               				<tr>
									<td width="330"  align="left" class="headsColor"
										style="vertical-align: top;"><b>Lessons Names</b></td>
									<td width="200" align="center" class="headsColor"
										style="vertical-align: top;"><b>Date Created</b></td>
								</c:when>
								<c:otherwise>
 							    	<c:choose>
										<c:when test="${((userReg.user.userType eq 'teacher' && fileType eq 'admin')||(userReg.user.userType eq 'teacher' && fileType eq 'student')	|| ((userReg.user.userType eq 'student above 13' || userReg.user.userType eq 'student below 13') && (fileType eq 'teacher')) || (userReg.user.userType eq 'admin' && page eq 'teacherLessonContent') )}">
											<table width="42%" height="10%" align="center" class="des" border=0 style="margin-top:2em;"><tr><td>
											<div class="Divheads">
											<table width="100%">
					               				<tr>
												<td width="50%" align="left" class="headsColor"
													style="vertical-align: top;"><b>Folders/Files</b></td>
												<td width="50%" align="center" class="headsColor"
													style="vertical-align: top;"><b>Date Created</b></td>
										</c:when>
										<c:otherwise>
										 <table width="78%" height="10%" align="center" class="des" border=0><tr><td>
										 <div class="Divheads">
											<table width="100%">
					               				<tr>
												<td width="300" align="left" class="headsColor"
													style="vertical-align: top;"><b>Folders/Files</b></td>
												<td width="300" align="center" class="headsColor"
													style="vertical-align: top;"><b>Date Created</b></td>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
								</c:choose>

							<c:choose>
								<c:when
								test="${(userReg.user.userType eq 'admin' && page ne 'teacherLessonContent') || 
			 							(userReg.user.userType eq 'teacher' && (fileType eq 'private' || fileType eq 'public')) ||
										((userReg.user.userType eq 'student above 13' || userReg.user.userType eq 'student below 13') && (fileType eq 'studentPrivate' || fileType eq 'transfer'))}">
								<td width="350" align="center" class="headsColor"
									style="vertical-align: top;"><b>Add File</b></td>
							</c:when>
								<c:otherwise>
									<td width="350" align="center" class="tabtxt"
									style="vertical-align: top;"></td>
									<td width="100" align="center" class="tabtxt"
										style="vertical-align: top;"></td>
								</c:otherwise>
							</c:choose>
						</tr></table></div>
						<div class="DivContents"><table width="100%"><br>
					    <c:set var="folderCount" value="1" scope="page" />
						<c:forEach items="${foldersList}" var="folder" varStatus="status">
							<tr id="row${status.count}">
								<td width="300" height="20" align="left" style="vertical-align: top;">
								  <c:choose>
									    <c:when test="${fn:length(folder.filesList) gt 0}">
											<a id="anchorid:${status.count}" href="javascript:fileToggle(${status.count})">
											 	<i class="fa fa-folder arrow" aria-hidden="true"></i>
											 	<span class="folderStyle" id="folderId${status.count}">${folder.foldername}</span><span class="fileCountStyle">&nbsp;&nbsp;(${fn:length(folder.filesList)})</span>
											</a>
										</c:when>
										<c:otherwise>
											<a id="anchorid:${status.count}" href="javascript:fileToggle(${status.count})">
											 	<i class="fa fa-folder arrow" aria-hidden="true"></i>
											 	<span class="folderStyle" id="folderId${status.count}">${folder.foldername}</span><span class="fileCountStyle">&nbsp;&nbsp;(${fn:length(folder.filesList)})</span>
											</a>										
										</c:otherwise>
									</c:choose> 
									<c:if test="${(userReg.user.userType eq 'admin' && page ne 'teacherLessonContent') || 
			 							(userReg.user.userType eq 'teacher' && (fileType eq 'private' || fileType eq 'public')) ||
										((userReg.user.userType eq 'student above 13' || userReg.user.userType eq 'student below 13') && (fileType eq 'studentPrivate' || fileType eq 'transfer'))}">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fi-pencil" id="editIcon${status.count}" aria-hidden="true" onclick="openEditBox('folderId','${status.count}','editIcon')" style="cursor: hand; cursor: pointer;font-size: 20px;font-weight:bold;color:#185C64;"></i> &nbsp;&nbsp;&nbsp;<i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 20px;color:#CD0000;" onclick="deleteFolder('${status.count}')"></i>
									</c:if>
									<div id="divid:${folderCount}" style="display: none">
										<c:set var="folderCount" value="${folderCount + 1}"
											scope="page" />
										<table style="padding-left: 2em" class="txtStyle" id="table${status.count}">
											<c:forEach items="${folder.filesList}" var="file" varStatus="count">
												<tr id="tr${status.count}${count.count}" style="font-size:12.5px;">
													<td height="20" class="txtStyle"><c:out value="${file.fileName}" />
														<c:if test="${((userReg.user.userType eq 'teacher') && (fileType eq 'admin' || fileType eq 'student'))}">
															<input type="hidden" id="id" name="id" value="${status.count}" />
															<input type="hidden" id="folderName${status.count}" name="folderName${status.count}" value="${folder.foldername}" /> 
															<input type="hidden" id="folderPath${status.count}" name="folderPath${status.count}" value="${folder.folderpath}" />
														</c:if>
													</td>
													<td>&nbsp;&nbsp;</td>
													<td width="231" height="20"><a href="javascript:downloadFile('${file.fileName}','${status.count}')">Download</a>
														<c:if
															test="${(fileType ne 'admin') && (fileType ne 'teacher') && (fileType ne 'studentFiles') && (fileType ne 'student') }">
															&nbsp;&nbsp;
															<a
																href="javascript:deleteFile('${count.count}','${file.fileName}','${status.count}',0,0)">Delete</a>
														</c:if></td>
												</tr>
											</c:forEach>
										</table>
									</div></td>

								<td width="300" height="20" align="center"
									style="vertical-align: top;" class="txtStyle"><fmt:formatDate
										type="both" dateStyle="short" timeStyle="short"
										value="${folder.createddate}"/><br /> <font color=red
									size="3px"></font></td>

								<c:if test="${user eq 'teacher'}">
									<input type="hidden" id="unitId" name="unitId"
										value="${unitId}" />
									<input name="fileType" value="${fileType}" type="hidden" />
								</c:if>
								<c:if test="${(userReg.user.userType eq 'admin')}">
								<td width="400" height="20" align="center" class="tabtxt"
									style="vertical-align: top;">
										<form name="fileupload" id="fileupload"
											action="uploadFile.htm" enctype="multipart/form-data"
											method="post">
											
											<input type="hidden" name="formname" value="fileupload" /> 
											<input type="hidden" id="id" name="id" value="${status.count}" />
											<input type="hidden" id="folderName${status.count}" name="folderName${status.count}" value="${folder.foldername}" /> 
											<input type="hidden" id="folderPath${status.count}" name="folderPath${status.count}" value="${folder.folderpath}" />
												 <input type="hidden"
												id="downloadFilePath" name="downloadFilePath" value="" /> <input
												id="teacherId" name="teacherId" value="${teacherId}"
												type="hidden" /> <input name="gradeId" id="gradeId"
												value="${gradeId}" type="hidden" /> <input name="classId"
												id="classId" value="${classId}" type="hidden" /> <input
												name="fileType" id="fileType" value="${fileType}"
												type="hidden" /> <input type="file" name="file[]"
												id="file:${folderCount-1}" multiple="multiple"
												onchange="fileValidate('${folderCount-1}')"></input>
												<input id="button:${folderCount-1}" type="submit" disabled="disabled" class="button_green" style="border:none;">
										</form>
										</td>
									</c:if> <c:if test="${(userReg.user.userType eq 'teacher' && (fileType eq 'private' || fileType eq 'public'))}">
									<td width="350" height="20" align="center" class="tabtxt"
									style="vertical-align: top;">
										<form name="uploadTeacherFiles" id="fileupload"
											action="uploadTeacherFiles.htm" enctype="multipart/form-data"
											method="post">
											<input type="hidden" name="formname" value="fileupload" />
											<input type="hidden" id="id" name="id" value="${status.count}" />
											<input type="hidden" id="folderName${status.count}" name="folderName${status.count}" value="${folder.foldername}" /> 
											<input type="hidden" id="folderPath${status.count}" name="folderPath${status.count}" value="${folder.folderpath}" />
												 <input type="hidden"
												id="downloadFilePath" name="downloadFilePath" value="" /> <input
												id="teacherId" name="teacherId" value="${teacherId}"
												type="hidden" /> <input name="gradeId" id="gradeId"
												value="${gradeId}" type="hidden" /> <input name="classId"
												id="classId" value="${classId}" type="hidden" /> <input
												name="fileType" id="fileType" value="${fileType}"
												type="hidden" /> <input type="file" name="file[]"
												id="file:${folderCount-1}" multiple="multiple"
												onchange="fileValidate('${folderCount-1}')"></input> 
												<input id="button:${folderCount-1}" type="submit" disabled="disabled" class="button_green" style="border:none;">
										</form>
									</td>	
									</c:if> <c:if
										test="${((userReg.user.userType eq 'student above 13' || userReg.user.userType eq 'student below 13') && (fileType eq 'studentPrivate' || fileType eq 'transfer'))}">
										<td width="350" height="20" align="center" class="tabtxt" style="vertical-align: top;">
										<form name="uploadStudentFiles" id="fileupload"
											action="uploadStudentFiles.htm" enctype="multipart/form-data"
											method="post">
											<input type="hidden" name="formname" value="fileupload" /> 
											<input type="hidden" id="id" name="id" value="${status.count}" />
											<input type="hidden" id="folderName${status.count}" name="folderName${status.count}" value="${folder.foldername}" /> 
											<input type="hidden" id="folderPath${status.count}" name="folderPath${status.count}" value="${folder.folderpath}" />
												<input type="hidden"
												id="downloadFilePath" name="downloadFilePath" value="" /> <input
												id="teacherId" name="teacherId" value="${teacherId}"
												type="hidden" /> <input name="gradeId" id="gradeId"
												value="${gradeId}" type="hidden" /> <input name="classId"
												id="classId" value="${classId}" type="hidden" /> <input
												name="fileType" id="fileType" value="${fileType}"
												type="hidden" /> <input type="file" name="file[]"
												id="file:${folderCount-1}" multiple="multiple"
												onchange="fileValidate('${folderCount-1}')"></input> 
												<input type="submit" id="button:${folderCount-1}" disabled="disabled" class="button_green" style="border:none;">
										</form>
										</td>	
									</c:if>
							</tr>							
						</c:forEach>
						<c:set var="lessonCount" value="1" scope="page" />
						<c:forEach items="${lessonsList}" var="lesson" varStatus="status">
							<tr>
								<td align="left"  height="35" width="350">
								<c:forEach items="${foldersListMap}" var="foldersList" varStatus="loop">
										<c:if test="${foldersList.key eq lesson.lessonId}">
											<c:if test="${fn:length(foldersList.value) gt 0}">
												<input type="hidden" id="folderName${status.count}" name="folderName${status.count}" value="${lesson.lessonName}" />
												<a id="anchorid:${status.count}" class="plus"
													href="javascript:fileToggle(${status.count})"><i class="fa fa-folder arrow" aria-hidden="true"></i>
												<span class="folderStyle">${lesson.lessonName}</span><span class="fileCountStyle"> (${fn:length(foldersList.value)})</span></a>
												<div id="divid:${status.count}" style="display: none">
													<c:set var="folderCount" scope="page" />
													<table style="padding-left: 3em;" id="table${status.count}">
														<c:forEach items="${foldersList.value}" var="folder" varStatus="count">
															<tr id="tr${status.count}${count.count}" style="font-size:12.5px;">
																<td>${folder.name}</td>
																<td height="20"><c:out value="${file.fileName}" />
																	<input type="hidden" id="fileName" name="fileName"
																	value="${folder.name}" /> <input type=hidden
																	id="filePath" name="filePath" value="${folder.path}" />
																	<input type="hidden" id="lessonId" name="lessonId"
																	value="${lesson.lessonId}" /> <input type="hidden"
																	id="unitId" name="unitId" value="${unitId}" /> <input
																	type="hidden"
																	id="folderPath${status.count}"
																	name="folderPath${status.count}"
																	value="${folder.parentFile}" /></td>
																<td width="231" height="20"><a
																	href="javascript:downloadFile('${folder.name}','${status.count}')">Download</a>
																	<c:if test="${fileType eq 'public'}">
																		<a href="javascript:deleteFile('${count.count}','${folder.name}','${status.count}','${unitId}','${lesson.lessonId}')">Delete</a>
																	</c:if>
																</td>
															</tr>
														</c:forEach>
													</table>
												</div>
											</c:if>
											<c:if test="${fn:length(foldersList.value) ==  0}">
												<a id="anchorid:${status.count}" class="plus"
													href="javascript:fileToggle(${status.count})"><i class="fa fa-folder arrow" aria-hidden="true"></i>
												<span class="folderStyle">${lesson.lessonName}</span><span class='fileCountStyle'> (${fn:length(foldersList.value)})</span></a>
											</c:if>
										</c:if>
									</c:forEach></td>
								<td width="100"  height="35" align="center" class="txtStyle"><c:out value="${lesson.createDate}" /></td>
								<c:if test="${(userReg.user.userType eq 'teacher' && fileType eq 'public')}">
								<td width="350"  height="35" align="center" class="tabtxt" style="vertical-align: top;">
										<form name="uploadTeacherLessonFile" id="fileupload"
											action="uploadTeacherLessonFiles.htm"
											enctype="multipart/form-data" method="post">
											<input type="hidden" name="formname" value="fileupload" /> <input
												type="hidden" name="lessonName" value="${lesson.lessonName}" />
											<input type="hidden" id="lessonId" name="lessonId"
												value="${lesson.lessonId}" /> 
											<input type="hidden" id="id" name="id" value="${status.count}" />
											<input type="hidden" id="folderName${status.count}" name="folderName${status.count}" value="${folder.name}" /> 
											<input type="hidden" id="folderPath${status.count}" name="folderPath${status.count}" value="${folder.absolutePath}" />
												<input id="teacherId"
												name="teacherId" value="${teacherId}" type="hidden" /> <input
												type="hidden" id="unitId" name="unitId" value="${unitId}" />
											<input type="hidden" name="page" id="page" value="${page}" />
											<input name="gradeId" id="gradeId" value="${gradeId}"
												type="hidden" /> <input name="classId" id="classId"
												value="${classId}" type="hidden" /> <input name="fileType"
												id="fileType" value="teacher" type="hidden" /> <input
												type="file" name="file[]" id="file:${status.count}"
												multiple="multiple"
												onchange="fileValidate('${status.count}')"></input>
												<input  id="button:${status.count}"  type="submit" disabled="disabled" class="button_green" style="border:none;">
										</form>
									</td></c:if>
							</tr>
							<c:set var="folderCount" value="${lessonCount + 1}" scope="page" />
						</c:forEach>
						<tr><td height="20" colspan="7" align="center" class="tabtxt" style="">
						<c:if test="${(fn:length(lessonsList)== 0) && (fn:length(foldersList)==0)}">
						<div class="status-message">
						<c:out value="No Folders/Files are created"></c:out>
				       </div></c:if></td></tr>
					</table></div></td></tr>
					</table>
					<c:choose>
						<c:when
							test="${userReg.user.userType == 'teacher' && (title == 'Admin Files' || title =='Upload Private Files')}">
							</body>
						</c:when>
					</c:choose>
