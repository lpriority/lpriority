<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page session="true"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>
	<c:choose>
		<c:when test="${divId eq 'assignLetterSet'}">Assign Letter Sets</c:when>
		<c:when test="${divId eq 'assignWordSet'}">Assign Word Sets</c:when>		
		<c:when test="${divId eq 'gradeLetterSet'}">Grade Letter Sets</c:when>
		<c:when test="${divId eq 'gradeWordSet'}">Grade Word Sets</c:when>
		<c:when test="${divId eq 'CreateLetter'}">Create Letter Sets</c:when>
		<c:when test="${divId eq 'CreateWord'}">Create Word Sets</c:when>
		
	</c:choose> 
</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="resources/javascript/CommonValidation.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/early_literacy.js"></script>
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<link href="resources/javascript/jplayer/skin/blue.monday/css/jplayer.blue.monday.min.css" rel="stylesheet" type="text/css"/>
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript">
function showOrHide(){
	var divId = document.getElementById("divId").value;
	 $("#headerPage").css('display', 'none');
	if(divId == 'CreateLetter'){
		$("#CreateLetterHeader").append($("#headerPage").html());	
		$("#CreateLetter").css('display', 'block');
		$("#CreateWord").css('display', 'none');
		$("#AssignLetterSet").css('display', 'none');
		$("#AssignWordSet").css('display', 'none');
		$("#GradeWordSet").css('display', 'none');
		$("#GradeLetterSet").css('display', 'none');
		
	}else if(divId == 'CreateWord'){
		$("#CreateWordHeader").append($("#headerPage").html());	
		$("#CreateWord").css('display', 'block');
		$("#CreateLetter").css('display', 'none');
		$("#AssignLetterSet").css('display', 'none');
		$("#AssignWordSet").css('display', 'none');
		$("#GradeWordSet").css('display', 'none');
		$("#GradeLetterSet").css('display', 'none');
	}
	else if(divId == 'assignLetterSet'){
		$("#AssignLetterSetHeader").append($("#headerPage").html());	
		$("#AssignLetterSet").css('display', 'block');
		$("#CreateLetter").css('display', 'none');
		$("#CreateWord").css('display', 'none');
		$("#AssignWordSet").css('display', 'none');
		$("#GradeWordSet").css('display', 'none');
		$("#GradeLetterSet").css('display', 'none');
	}
	else if(divId == 'assignWordSet'){
		$("#AssignWordSetHeader").append($("#headerPage").html());	
		$("#AssignWordSet").css('display', 'block');
		$("#AssignLetterSet").css('display', 'none');
		$("#CreateLetter").css('display', 'none');
		$("#CreateWord").css('display', 'none');
		$("#GradeWordSet").css('display', 'none');
		$("#GradeLetterSet").css('display', 'none');
	}
	else if(divId == 'gradeLetterSet'){
		$("#GradeLetterSetHeader").append($("#headerPage").html());	
		$("#GradeLetterSet").css('display', 'block');
		$("#GradeWordSet").css('display', 'none');
		$("#AssignWordSet").css('display', 'none');
		$("#AssignLetterSet").css('display', 'none');
		$("#CreateLetter").css('display', 'none');
		$("#CreateWord").css('display', 'none');
		
	}
	else if(divId == 'gradeWordSet'){
		$("#GradeWordSetHeader").append($("#headerPage").html());
		$("#GradeWordSet").css('display', 'block');
		$("#GradeLetterSet").css('display', 'none');
		$("#AssignWordSet").css('display', 'none');
		$("#AssignLetterSet").css('display', 'none');
		$("#CreateLetter").css('display', 'none');
		$("#CreateWord").css('display', 'none');
		
	}
}
function datepick() {
   $( "#dueDate").datepicker({
   	   changeMonth: true,
       changeYear: true,
       showAnim : 'clip',
       minDate : 0
   });
	$( "#startDate").datepicker({
		changeMonth: true,
	    changeYear: true,
	    showAnim : 'clip',
	    minDate : 0
	});
}
$(document).ready(function() {
	$('#returnMessage').fadeOut(4000);
	showOrHide();
	 $('select').on('change', function() {
	   if(this.value){
		   if(this.value != 'select'){
			  storeValue(this.id, this.value +"_"+ this.options[this.selectedIndex].text);
			  $('#'+$(this)[0].id).css('color','black');
		   }else{
			   $('#'+$(this)[0].id).css('color','gray');
		   }
	   }
	    var divId = dwr.util.getValue('divId');
		if(divId){
			if(divId == 'gradeLetterSet'){
		   		$('#GradeLetterSetDetailsPage').html('');
		   	}else if(divId == 'gradeWordSet'){
		   		$('#GradeWordSetDetailsPage').html('');
		   	}else if(divId == 'CreateWord'){
		   		var csId = $('#csId').val();
		   		if(csId == 'select')
		   			$('#createWordDiv').hide();
		   	}else if(divId=='assignLetterSet'){
		   		$('#AssignLetterSetDetailsPage').html("");
		   		$( "#select_all").prop('checked', false);
		   	}
		   	else if(divId=='assignWordSet'){
		   		$('#AssignWordSetDetailsPage').html("");
		   		$( "#select_all").prop('checked', false);
		   	}
		}
	}); 
});
</script>
<style>
.submitButton {
	width: auto;
	color:white;
	background-color: #0080FF;
	moz-border-radius: 15px;
	-webkit-border-radius: 15px;
	border: 5px solid #0080FF;
	padding: 5px;
	font-weight: 900;
	text-align: center;
	vertical-align: middle;
}
.wordsWrap{
	background-color:#cccccc;
	border:1px solid #000000;
	text-align:center;
	border-width:1px 1px 1px 1px;
	font-size:16px;
	font-family:Arial, Helvetica, sans-serif;
	font-weight:bold;
	color:#000000;
}
.wordsWrapSelect{
	background-color:white;
	border:1px solid #000000;
	text-align:center;
	border-width:1px 1px 1px 1px;
	font-size:16px;
	font-family:Arial, Helvetica, sans-serif;
	font-weight:bold;
	color:black;
}
.assignTest{
	color: white;
	height:35px;
	width: 100px;
	font-size:13px;
	background-color: rgb(44, 44, 201);
	moz-border-radius: 15px;
	-webkit-border-radius: 15px;
}
</style>
</head>
<body>
	<div>
		<%@ include file="/jsp/assessments/rti_tabs.jsp" %>
	</div>
	<c:if test="${divId eq 'gradeLetterSet' || divId eq 'gradeWordSet'}">
		<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" /> 
	</c:if>
	<input type="hidden" name="divId" id="divId" value="${divId}" />
	<input type="hidden" name="page" id="page" value="${page}" />
	<input type="hidden" name="setType" id="setType" value="${setType}" />
	<input type="hidden" name="teacherId" id="teacherId" value="${teacherId}" />
	<input type="hidden" name="teacherName" id="teacherName" value="${teacherName}" />
	<input type="hidden" name="letterCount" id="letterCount"/>
	<input type="hidden" name="usedFor" id="usedFor" value="rti" />  
	<table width="100%" border=0 align="left">
     <tr><td>
	     <div id="CreateLetter">
			<table align="center" width="100%">
				<tr><td>
					<div id="CreateLetterHeader"></div>
				</td></tr>
				<tr><td>
					 <div id="CreateLetterDetailsPage"></div>
				</td></tr>
			</table>
		</div>
		<div id="CreateWord">
			<table align="center" width="100%">
				<tr><td>
					<div id="CreateWordHeader"></div>
				</td></tr>
				<tr><td>
					 <div id="CreateWordDetailsPage"></div>
				</td></tr>
			</table>
		</div>
		<div id="AssignLetterSet">
			<table align="center"  width="100%">
				<tr><td>
					<div id="AssignLetterSetHeader"></div>
				</td></tr>
				<tr><td height=20 colSpan=30>&nbsp;</td></tr>
				<tr><td>
					 <div id="AssignLetterSetDetailsPage" style="left-padding: 5em;"></div>
				</td></tr>
				<tr><td align="center">
					 <div id="AssignLetterSetResult" class="status-message"></div>
				</td></tr>
			</table>
		</div>
		<div id="AssignWordSet">
			<table align="center"  width="100%">
				<tr><td>
					<div id="AssignWordSetHeader"></div>
				</td></tr>
		<tr>
		<td>
			<!-- <div id="videoDiv">
				<a href="#" onClick="openVideoDialog('teacher','assign_earlyLitracy_wordset','videoDailog')" class="demoVideoLink"><i class="ion-videocamera" aria-hidden="true" style="font-size:26px;font-weight:bold;color:#e23c00;"></i>&nbsp;video for Assign Word Set</a>
		 	</div> -->
           <label id="status"
			style="color: blue;">${hellowAjax}</label></td>
	</tr> 
				<tr><td height=30 colSpan=30 align="center" id="assignTypeDiv" style="display:none;"><span class="label"><b>Automatic Tests Assign : </b></span> <input type="checkbox" class="checkbox-design" id="teacher" name="assignType" onClick="getTestSets($(this).is(':checked'))" /><label for="teacher" class="checkbox-label-design"></label></td></tr>
				<tr><td>
					 <div id="AssignWordSetDetailsPage"></div>
				</td></tr>
				<tr><td align="center">
					 <div id="AssignWordSetResult" style="display:none" class="status-message"></div>
				</td></tr>
			</table>
		</div>
		<div id="GradeLetterSet">
			<table align="center"  width="100%">
				<tr><td>
					<div id="GradeLetterSetHeader"></div>
				</td></tr>
				<tr><td height=20 colSpan=30>&nbsp;</td></tr>
				<tr><td style="padding-left:18em;padding-top:1em;">
					 <div id="GradeLetterSetDetailsPage"></div>
				</td></tr>
				<tr><td align="center">
					 <div id="GradeLetterSetResult" style="display:none" class="status-message"></div>
				</td></tr>
			</table>
		</div>
		<div id="GradeWordSet">
			<table align="center"  width="100%">
				<tr><td>
					<div id="GradeWordSetHeader"></div>
				</td></tr>
				<tr><td style="padding-left:18em;padding-top:2em;">
					 <div id="GradeWordSetDetailsPage"></div>
				</td></tr>
				<tr><td align="center">
					 <div id="GradeWordSetResult" style="display:none" class="status-message"></div>
				</td></tr>
			</table>
		</div></td></tr>
		<tr><td width="100%" colspan="10" align="center"><label id="returnMessage" style="color: blue;">${helloAjax}</label></td></tr>
		</table>
	    <div id="headerPage">
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0" align="left" class="title-pad">
					 <c:if test="${divId eq 'gradeWordSet'}">
						<tr align="center">
						  <td colspan="22" height="40" class="label"><input type="radio" id="system" class="radio-design" name="eltTests" value="system" checked onclick="getTests()"><label for="system" class="radio-label-design">System Generated Tests</label> &nbsp;&nbsp;&nbsp;<input type="radio" class="radio-design" id="autoAssign" name="eltTests" value="autoAssign" onclick="getTests()"><label for="autoAssign" class="radio-label-design">Auto assigned Tests</label></td>
						</tr>
					 </c:if>
						<tr class="label">
							<td width="60" align="left">Grade</td>
							<td width="150" align="left" valign="right">
								<select	id="gradeId" name="gradeId" class="listmenu" onchange="getGradeClasses();clears(this);" style="width:120px;">
										<option value="select">select</option>
							 		<c:forEach items="${teacherGrades}" var="ul">
										<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
									</c:forEach> 
								</select>
							</td>
							<td></td>
							<td width="60" align="left">Class</td>
							<td width="150" align="left" valign="middle">
							<select id="classId" name="classId" class="listmenu" style="width:120px;" onchange="getClassSections();clears(this);" >
							 	<option value="select">select</option>
							</select></td>
							<td width="80" align="left">Section</td>
							<td width="150" align="left" valign="right">
								<select id="csId" name="csId" class="listmenu" onChange="clears(this);getStudentsBySection();getEltAssignedDates();getAutoAssignmentTitles();getWordLists();">
									<option value="select">select Section</option>
								</select>
							</td>
						   <td height=10 colSpan=10></td>
						<c:choose>
							<c:when test="${divId eq 'CreateLetter'}">
				 				<td width="180">No Of Upper Case Sets</td>
		                         <td width="13" align="left">
		                             <input type="text" name="noOfUCSets" class="listmenu" id="noOfUCSets" value="0" style="width:50px;">
		                         </td>
		                         <td height=10 colSpan=20></td>
		                         <td width="180">No Of Lower Case Sets</td>
		                         <td width="93" align="left">
		                             <input type="text" name="noOfLCSets" class="listmenu" id="noOfLCSets" value="0" style="width:50px;">
		                         </td>
	                             <td width="180" align="center">
	                             <input type="button" class="" onclick="generateSets()" value="Generate" ></td>
	                            </tr>
	                            <tr><td height=40 colSpan=8></td></tr> 
	                            <tr align="center">
	                              <td height="10" colspan="10" id="appendDiv"></td>
	                            </tr>
	                            <tr>
									<td  width="580"  align="right" style="position:absolute;"><font color="blue"><b> <div id='result'></div></b></td>
								</tr>
							</c:when>
							<c:when test="${divId eq 'CreateWord'}">
							 <tr height="100%">
								<td align="center" colspan="15">
									<table width='100%'>
										<tr><td align='right'><div class="button_green" align="create word list" onclick="openWordListDialog('create',-1)" id="addWordList" name="addWordList" style='display:none;'>Create Word Lists</div></td></tr>
										<tr><td><div id="displaySetsDiv" style=''></div></td></tr>
									</table>
								</td>	
			                 </tr> 
			                 <tr><td height=30 colSpan=90></td></tr>
							 <tr>
								<td align="center" colspan="12"><div id="result" class="status-message"></div></td>
							 </tr>
							</c:when>
							<c:when test="${divId eq 'assignLetterSet' || divId eq 'assignWordSet'}">
							  <td width="90">Students</td>
							  <td width="160"><select name="studentId" id="studentId" multiple="multiple" required="required" style="width: 150px;height:60px; overflow-y : visible" onChange="setTestType()" onClick="selectOption(this,'');setTestType()" >
							  <option value="select">select student</option>
							  </select></td>
 							   <td class="label">&nbsp;&nbsp; <input type="checkbox" class="checkbox-design" id="select_all" name="select_all" onClick="selectAllOptions(this,'studentId', '');setTestType()"><label for="select_all" class="checkbox-label-design">Select All</label>
						       </td>
 							  <td width="120">
<!--  							  Real Test ? -->
 							  </td>  
							</tr>
							 </c:when>
						   <c:when test="${divId eq 'gradeLetterSet' || divId eq 'gradeWordSet'}">
							  <td width="220" align="left" id="dateDiv">&nbsp;&nbsp;&nbsp; Date &nbsp;&nbsp;&nbsp;&nbsp; 
							   <select id="assignedDate" name="assignedDate" class="listmenu" style="width:120px;" onchange="getEltPstAssignmentTitles()" >
							 		<option value="select">select date</option>
								</select>
							  </td>
							 <td width="120">Test Title </td>
							 <td>
							 	 <select id="titleId" name="titleId" class="listmenu" style="width:120px;" onchange="getStudentDetailsForGrade()">
							 		<option value="select" >select title</option>
								</select>
							 </td>
							</tr>
					   </c:when>
					</c:choose>
			</table>
		</div>
		<div id="videoDailog" title="Grade Fluency" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
		<div id="createWordListdialog" title="Word List" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;display:none;" ><iframe id='iframe' width="95%" height="95%"></iframe></div>
</body>
