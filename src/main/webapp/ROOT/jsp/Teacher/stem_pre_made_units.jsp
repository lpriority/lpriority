
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Load Pre-made Units</title>
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script> 
<script type="text/javascript" src="resources/javascript/TeacherJs/stem_curriculum.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/stem_sub_curriculum.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resources/css/icons/font-awesome/css/font-awesome.min.css">
<script src="resources/javascript/notify/notify.js"></script>
<script src="resources/javascript/notify/prettify.js"></script>
<script src="resources/javascript/confirm_dialog/dialog.js"></script>
<script type="text/javascript" src="resources/javascript/template/common_template.js"></script>

<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" href="resources/css/notify/notify.css">
<link rel="stylesheet" href="resources/css/notify/prettify.css">
<link rel="stylesheet" href="resources/css/confirm_dialog/dialog.css">
<style>
.notify-backdrop{
background-color: #e6e6e6;
}
.notify{
	width:auto;
	padding:none;
}
.notify.center{margin-left:-100px;}
.notify > button.close{
   opacity:1;
   color: white;
   text-shadow:0 1px 2px rgb(37, 37, 37);
}
.lnv-mask{
	background:rgba(119, 229, 242, 0.13);
}
.ui-widget-header{background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00e3ff), color-stop(1,#00b7d0) );border:1px solid #00d8f5;text-shadow:0 1px 1px rgb(0, 48, 53);margin:-.12em;}
.ui-widget-overlay{background:rgba(81, 139, 146, 0.66);}
</style>
</head>
<body>
<input type="hidden" id="gradeId" name="gradeId" value="${gradeId}" />
<input type="hidden" id="classId" name="classId" value="${classId}" />
<input type="hidden" id="pathId" name="pathId" value="${pathId}" />
  <table width="85%" align="center">
    <tr><td width="100%" colspan="3" align="center" class="label" style="color:black;">Pre-made Units</td></tr>
   	<tr><td width="100%" valign="top" align="center">
   	<div class="des" style="font-size:13px;overflow-y: auto;overflow-x: hidden;max-height: 350px;min-height: 350px;color:black;margin-right: auto; margin-left: auto; width: 70%;">
   	 <table width="100%" align="center" style='font-size:13px;font-family:"Lato", "PingFang SC", "Microsoft YaHei", sans-serif;'>
   	    <tr class="Divheads"><td width="10%" height="40" align="center">Check</td><td width="40%" align="center">${LP_STEM_TAB} Unit Name</td><td width="40%" align="center">Teacher</td></tr>
        <tr><td height="5"></td>
         <c:forEach items="${stemUnitLt}" var="stemUnit" varStatus="status"> 
           <tr> 
        	 <td width="10%" align="center">
        	    <input id="stemUnitId${status.index}" name="stemUnitId${status.index}" type="hidden" value="${stemUnit.stemUnitId}">  
        	    <input id="stemUnitName${status.index}" name="stemUnitName${status.index}" type="hidden" value="${stemUnit.stemUnitName}">  
        	 	<input id="checkbox${status.index}" name="stemUnitCheckbox" type="checkbox" class="checkbox-design" value="${stemUnit.stemUnitId}">  
               	<label for="checkbox${status.index}" class="checkbox-label-design"></label>
             </td>
	         <td width="60%" align="center" height="20">${stemUnit.stemUnitName}</td>
	         <td width="30%" align="center">${stemUnit.userRegistration.firstName} ${stemUnit.userRegistration.lastName}</td>
	      </tr> 
         </c:forEach>
     </table>
   </div>
  </td></tr>
  <tr><td width="100%" >
	  <table width="61%" align="center">
		  <tr><td width="20%" align="left"><div class="button_green" id="addCopyStemUnits" height="52" width="50" onclick="getCopyOfStemUnits()">Get Copy of ${LP_STEM_TAB} Units</div></td><tr>
		  <tr><td width="40%" align="center"><table width='100%' align='center'><div id="editStemUnitDiv" style="padding:0.5em;background:#eee;font-size:16px;overflow-y: auto;border:1px solid #a4c5c9;overflow-x: hidden;max-height: 200px;min-height: 200px;color:black;margin-right: auto; margin-left: auto; width: 50%;"></div></table></td>
		  </tr>
	  </table>
  </td></table>
 <div id="dialog"  style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title=""><iframe  id ="iframe" src="" frameborder="0" scrolling="no" width="100%" height="99%" src=""></iframe></div>
  </body>
  </html>