<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Teacher Files</title>        
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<!-- <script src="resources/javascript/jquery/jquery-2.1.4.js"></script> -->
<!-- <script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script> -->
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="/dwr/interface/curriculumService.js"></script>
<script type="text/javascript" src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript" src="/dwr/interface/teacherSchedulerService.js"></script>
<script type="text/javascript" src="resources/javascript/my_files.js"></script>
<script > 
function togglePage(thisVar, url, name, fileType){
	$.ajax({
		type : "GET",
		url : url+".htm",
		data: "fileType="+fileType,
		success : function(response) {
			$("#contentDiv").empty();
			$("#contentDiv").append(response);
			$("#subTitle").html(name);
			var current = document.getElementsByClassName("buttonSelect");
		    current[0].className = current[0].className.replace(" buttonSelect", " button");
		    var activeElement = $(thisVar);
		    activeElement.addClass('buttonSelect');
		    activeElement.removeClass('button');
		    $('title').text(name);
		}
	});
}

</script>
</head>
<body  >		
                
                	<table width="100%" border=0 align="left" cellPadding=0 cellSpacing=0>
                    	<tr>
                            <td vAlign=bottom align=right>
                                <table style="width: 100%" height="29" border="0" cellpadding="0" cellspacing="0" align=right>
                                    <tr>
										<td style="width: 100%" align=right>
							              	<ul class="button-group">
												<li><a href="#" onclick="togglePage(this, 'displayStudentFiles', 'Class Files', 'teacher')"  class="btn ${(fileType == 'teacher')?'buttonSelect leftPill':'button leftPill'}">Teacher Files</a></li>
												<li><a href="#" onclick="togglePage(this, 'displayStudentFiles', 'Private Files', 'studentPrivate')" class="btn ${(fileType == 'studentPrivate')?'buttonSelect':'button'}">Private Files</a></li>
												<li><a href="#" onclick="togglePage(this, 'displayStudentFiles', 'Transfer Files', 'transfer')" class="btn ${(fileType == 'transfer')?'buttonSelect rightPill':'button rightPill'}">Transfer File</a></li>
											</ul>
										</td>		
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        </table>
                        <table style="padding-top:1em;" width="99.8%" border="0" cellspacing="0" cellpadding="0" class="title-pad" >
                        <tr><td id = "subTitle" class="sub-title title-border" height="40" align="left"><c:out  value="${title}"/><br> </td></tr>
                        </table>
                             <div id="contentDiv">
                             <%@include file="student_files.jsp"%>
                             </div>

</body>
</html>