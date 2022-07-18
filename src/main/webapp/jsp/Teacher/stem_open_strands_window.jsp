
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
<title>Apply Strands</title>
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script> 
<script type="text/javascript" src="resources/javascript/TeacherJs/stem_curriculum.js"></script>

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
<script>
$(document).ready(function () {	
	if($("#selectedStemGradeStrandId").val() != -1)
	getStrandconcepts();
});
</script> 
<body>
 <input type="hidden" id="stemAreaId" name="stemAreaId" value="${stemAreaId}" /> 
<input type="hidden" id="unitStemAreaId" name="unitStemAreaId" value="${unitStemAreaId}" />
<input type="hidden" id="stemArea" name="stemArea" value="${stemArea}" />
<input type="hidden" id="gradeId" name="gradeId" value="${gradeId}" />
<input type="hidden" id="selectedStemGradeStrandId" name="stemGradeStrandId" value="${stemGradeStrandId}" />
<form action="" id="contentQueForm">
  <table width="100%" style="color:black;padding-top:1em;">
   <tr>
    <td width="6%" style="padding-left: 2em;" class="label">Select Grade :</td>
    <td width="20%"><select id="masterGradeId" name="masterGradeId" style="width:120px;" class="listmenu"  onchange="getStrands();">
		<option value="select">Select Grade</option>
		<c:forEach items="${grades}" var="grade">			
			<option value="${grade.masterGradesId}" <c:if test="${masterGradeId == grade.masterGradesId}">selected="selected"</c:if>>${grade.gradeName}</option>
		</c:forEach>
	</select>
	</td>
    </tr>
    <tr>
    <td width="6%" style="padding-left: 2em;" class="label">${stemArea} Strands :</td>
    <td width="20%"><select id="stemGradeStrandId" name="stemGradeStrandId" style="width:120px;" class="listmenu"  onchange="getStrandconcepts();">
		<option value="select">Select Strand</option>
		<c:forEach items="${stemGradeStrands}" var="stemGradeStrand">
			
			<option value="${stemGradeStrand.stemGradeStrandId}" <c:if test="${stemGradeStrandId == stemGradeStrand.stemGradeStrandId}">selected="selected"</c:if>>${stemGradeStrand.stemStrandTitle }</option>
		</c:forEach>
	</select>
	</td>
    </tr>
    <tr><td colspan="2" align="center">
    	<div id="strandConceptsDiv" style='overflow-y: auto;border-style: groove;border-width: 1px;overflow-x: hidden;max-height: 330px;min-height: 330px; width:100%;'></div>
   	</td></tr>
    <tr><td colspan="2" height="60" align="center">
	   	<div id="submitDiv" style="display:none;">
		   <table width="80%" align="center">
				<tr>
	               <td width="35%" height="10" align="right" valign="middle"> 
	              	<div class="button_green" align="right" onclick="saveStemAreaStrands()">Submit Changes</div> 
	               </td>
	               <td width="30%" height="10" align="left" valign="middle" style="padding-left: 2em;">
	               	 <div class="button_green" align="right" onclick=" $('input:checkbox').removeAttr('checked');">Clear</div>
	               </td>
	           	</tr>
	        </table>
		</div>
		<div id="doneDiv" style="display:none;">
		   <div id="closeDiv" align="center" onclick="window.parent.jQuery('#dialog').dialog('close');" class="button_green">Done with ${stemArea}</div>
		</div>
   </td></tr>
  </table>
</form>
</body>
</head>
</html>