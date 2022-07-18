<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Goal Setting Excel Download</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/common/goal_setting_tool.js"></script>
<script src="resources/javascript/teacher_popup.js"></script>
<script src="resources/javascript/Adminjs.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/simptip/simptip.css" />
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/teacherService.js"></script>
<script src="resources/javascript/LineChart/jszip.min.js"></script>
<script src="resources/javascript/LineChart/pako_deflate.min.js"></script>
<link rel="stylesheet" href="resources/css/LineChart/kendo.material.min.css" />
<link rel="stylesheet" href="resources/css/LineChart/kendo.dataviz.min.css" />
<script src="resources/javascript/LineChart/kendo.all.min.js"></script>


<script type="text/javascript">
	$(document).ready(function() {
		$('#returnMessage').fadeOut(4000);
	});
	
	function getAllTeachersByGradeId(){
		$("#loading-div-background1").show();
		var gradeId = $('#gradeId').val();
		var schoolId = $('#schoolId').val();
		
		if(gradeId!='select'){
			var teacherId = $('#teacherId').val();
			var teacherName = $('#teacherName').val();
			var gradeName = $("#gradeId option:selected").text();
			if(teacherId > 0){
				var content = 	"<table border=0 width='35%' algin='center'>"+
								"<tr><td width='100%'><span style='font-size:24px;color:green;' class='button_white' onclick='goalSettingDownloadToExcelTest("+teacherId+",\""+teacherName+"\")'><span style='color:black;text-shadow: 0 1px 1px rgba(189, 189, 189, 0.78);font-size: 13px;font-weight: bold;'>"+gradeName+" Grade Data &nbsp;&nbsp;</span><i class='fa fa-file-excel-o'></i></span></td></tr>"+
								"</table>";
				$('#goalSettingTeachersDetailsDiv').html(content);
			}else{
				if(gradeId > 0 && schoolId > 0){
					$.ajax({
						type : "GET",
						url : "goalSettingTeachersDetails.htm",
						data : "gradeId="+gradeId+"&schoolId="+schoolId,
						 success : function(data) {
							$('#goalSettingTeachersDetailsDiv').html("");
							$('#goalSettingTeachersDetailsDiv').html(data);
						}
					});	
				}else{
					$('#goalSettingTeachersDetailsDiv').html("");
				}
			}
			$("#loading-div-background1").hide();
		}else{
			$('#goalSettingTeachersDetailsDiv').html("");
			$("#loading-div-background1").hide();
		}
	}
	function goToDiv(i){
		  document.getElementById("sho"+i).scrollIntoView(true);
	}
</script>
</head>
<body>
<input type="hidden" id="schoolId" name="schoolId" value="${schoolId}" />
<input type="hidden" id="teacherId" name="teacherId" value="${teacherId}" />
<input type="hidden" id="teacherName" name="teacherName" value="${teacherName}" />
	<div>
		<c:choose>
			<c:when test="${userReg.user.userType == 'admin'}">
			    <script src="resources/javascript/admin/common_dropdown_pull.js"></script>
				<c:set var="getClassFunction" value="getGradeClasses()"/>
			</c:when>
			
		</c:choose>		
	</div>
		<%-- <table border="0" cellspacing="0" cellpadding="0" align="right" class="">
       <tbody><tr>
          <td>
            <ul class="button-group">
				<li><a href="goalSettingDownload.htm" class="${(page == 'goalSettingDownload')?'buttonSelect leftPill':'button leftPill'}"> Goal Setting</a></li>
				<li><a href="goalSettingExcelDownload.htm" class="${(page =='goalSettingExcelDownload')?'buttonSelect rightPill':'button rightPill'}"> Goal Setting Excel</a></li>
			</ul>
		 </td>	
       </tr></tbody>
    </table>
		 <table align="left" style="width: 99.8%;" border="0" cellpadding="0" cellspacing="0" class="title-pad">
			<tr>
				<td class="sub-title title-border" height="40" align="left">
					Goal Setting Excel Download
				</td>
			</tr>	
			
	 </table> --%>
	 <table width='100%' align='center'>	
			<tr>
				<td colspan="2" align="left" valign="left" width="100%" class="heading-up">
					<table align="left" valign="left" width="100%" style="padding-left: 8em;" border=0 cellPadding=0 cellSpacing=0  class="title-pad heading-up heading-up">
						<tr>
							<td height="20" align="left" class="label" style="width: 12em;">Grade &nbsp;&nbsp;&nbsp;
								<select name="gradeId" class="listmenu" id="gradeId" onChange="getAllTeachersByGradeId()" required="required">
									<option value="select">select grade</option>
									<c:forEach items="${grades}" var="ul">
										<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
									</c:forEach>
								</select>
							</td>						
							<td style="width: 12em;padding-left: 2em;">
							</td>				
							<td align="right" valign="middle" style="padding-right:6em;">
								<input type="hidden" id="usedFor" value="${usedFor}" />
								
							</td>
						</tr>
						<tr>
							<td height="10" colspan="15"></td>
						</tr>				
					</table>
				</td>
			</tr>
			<tr>
				<td height="2" colspan="2"></td>
			</tr>
			<tr>
				<td colspan="2" align="center" valign="top" id="subViewDiv"></td>
			</tr>
			<tr><td>&nbsp;</td></tr></table>
							 <table width=100% align=left>
							<tr>
							<tr>
<!-- 							<td align="left" style="padding-left: 7em;"><div id="studentsTestResultsDiv" style="align:center;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div></td></tr> -->
							</table>
	 <div id="goalSettingTeachersDetailsDiv" class="center_of_div" style="width: 100%;"></div>			
	 <div id="goToGoalSettingTool" title="Goal Setting Tool" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>

<div id="loading-div-background1" class="loading-div-background"
	style="display: none;">
	<div class="loader"></div>
	<div id="loading-div" class="ui-corner-all loading-div"
		style="color: #103c51; left: 50%; padding-left: 12px; font-size: 17px; margin-top: -8.5em;">
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>Loading....
	</div>
</div>
</body>
</html>