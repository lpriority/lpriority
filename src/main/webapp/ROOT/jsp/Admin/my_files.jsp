<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin My Files</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript" src="/dwr/interface/teacherService.js"></script>
<script type="text/javascript" src="resources/javascript/my_files.js"></script>
<script type="text/javascript" src="resources/javascript/CommonValidation.js"></script>
<script type="text/javascript" src="resources/javascript/imageloadfunctions.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/admin/common_dropdown_pull.js"></script>
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

<body>
	<div class="">
		<table width="100%" align="right" border=0 cellSpacing=0 cellPadding=0 width=206>
			<tr><td>
			<table width="100%" align="right" border="0" cellspacing="0" cellpadding="0">
			       <tbody><tr>
			          <td width="100%" align="right">
			            <ul class="button-group">
						<li><a href="#" onclick="togglePage(this, 'displayAdminClassFiles', 'Class Files', 'public')"  class="btn ${(fileType == 'public')?'buttonSelect leftPill':'button leftPill'}"> Class Files </a></li>
						<li><a href="#" onclick="togglePage(this, 'displayAdminClassFiles', 'Private Files', 'private')"  class="btn ${(fileType == 'private')?'buttonSelect rightPill':'button rightPill'}"> Private Files </a></li>
						</ul>
					 </td>	
			       </tr></tbody>
			    </table>
		</td></tr>
	</table>
	</div>
	 <tr>
		<td vAlign=top width="100%" colSpan=3 align=middle class="title-pad heading-up sub-title" height="40" align="left">
		<div style="height:auto;padding-left:8em;">    
			<table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0 >
				<tr><td>
						<table width="100%" style="padding-top:1em;">
                      		 <tbody><tr>
                                <td class="sub-title title-border" height="35" align="left" id="subTitle">${title}</td>
                           	 </tr>
						</tbody></table>
				</td></tr>
				
                <tr><td align="center" class="content-box"> 
                             <div class="center_of_div" id="contentDiv">
                             <%@include file="my_files_sub.jsp"%>
                             </div>
                            </td></tr>
			</table>
			</div>
		</td>
	</tr> 
	<tr><td width="100%" colspan="10" align="center"><label id="returnMessage" style="color: blue;">${helloAjax}</label></td></tr></table>
	<div id="dialog"  style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title=""> 
		<iframe  id ="iframe" src="" frameborder="0" scrolling="no" width="100%" height="95%" src=""></iframe>
	</div>
</body>
</html>