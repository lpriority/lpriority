
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
<title>Add Lessons to Unit</title>
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<link href="resources/javascript/jplayer/skin/blue.monday/css/jplayer.blue.monday.min.css" rel="stylesheet" type="text/css"/>
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/curriculum.js"></script>
<script type="text/javascript" src="/dwr/interface/curriculumService.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script>
	var count = 0;
	$(document).ready(function () {
		$('#returnMessage').fadeOut(4000);
	});
	function clearDiv(){
		$("#lessonDiv").empty();
	}
</script>
<style>
.outer-container {
border: 5px solid purple;
position: relative;
overflow: hidden;
}

.inner-container {
position: absolute;
left: 0;
overflow-x: hidden;
overflow-y: hidden;
}

</style>
</head>
<body>
   <link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
  <div> 
  	<%@ include file="/jsp/curriculum/curriculum_tabs.jsp" %> 
  </div>
   	<input type='hidden'  id='gradeIdHidden' name='gradeIdHidden' value='${gradeIdHidden}' />
   	<input type='hidden'  id='classIdHidden' name='classIdHidden' value='${classIdHidden}' />
    <input type='hidden'  id='unitIdHidden' name='unitIdHidden' value='${unitIdHidden}' />
    <input type='hidden'  id='lessonIdHidden' name='lessonIdHidden' value='${lessonIdHidden}' />
    <input type='hidden'  id='currentTab' name='currentTab' value='${currentTab}' />
    <input type='hidden'  id='regId' name='regId' value="${redId}" />
	 <table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
          <tr>
              <td height="30" align="left" valign="left"  width="100%">
                 	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left" style="padding-left: 8em;">     
                          <tr><td><input type="hidden" id="teacherObj" value="${teacherObj}" /></td></tr>
                          <tr>
                              <td height="20" align="left" class="label" style="width: 12em;">Grade &nbsp;&nbsp;&nbsp;
                                  <select style="width:120px;" name="gradeId" class="listmenu" id="gradeId"  onChange="clearDiv();getGradeClasses();">
                                              <option value="select">select grade</option>
								<c:forEach items="${grList}" var="ul">
								  	<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
								</c:forEach>
                                     </select><c:out value="${ul.gradeId}"></c:out>
                                 </td>
                                 <td height="20" class="label" style="width: 13em;padding-left: 2em;">Subject &nbsp;&nbsp;&nbsp;
									<select style="width:120px;" id="classId" name="classId" class="listmenu"  onChange="clearDiv();getUnits();">
										<option value="select">select subject</option>
									</select>
								</td>
								<td height="20" class="label" style="width: 12em;padding-left: 2em;">Units &nbsp;&nbsp;&nbsp;
									<select style="width:120px;" id="unitId" name="unitId" class="listmenu" onchange=loadLessons()>
										<option value="select">select Unit</option>
									</select>
								</td>
								<td height="20" class="label" style="width: 15em;padding-left: 2em;">
								   <div class="button_green" id="btAdd" name="btAdd" height="52" width="50" onclick="dynamicAdd(-1)" style="display:none;">Add Lessons</div>
								</td>
								<td style="width: 4em;">
									<div id="backBtn" style="display:none;" onclick="backToUnitsTab()">
										<table><tr>
										<td height="20" class="label" style="cursor: hand; cursor: pointer;font-family:Candara, Calibri, Segoe, Segoe UI, Optima, Arial, sans-serif;" align="left">
										<i class="fa fa-arrow-left" aria-hidden="true"></i>
										Back
										</td>
										</tr></table>
									</div>
								</td>
								<td style="width: 10em;"></td>
								<td style="width: 4em;">
									<div id="previousBtn" style="display:none;" onclick="goToAssessmentsTab(-1,'${currentTab}')">
										<table><tr>
										<td height="20" class="label" style="font-family:Candara, Calibri, Segoe, Segoe UI, Optima, Arial, sans-serif;cursor: hand; cursor: pointer;" align="right" >Next
										<i class="fa fa-arrow-right" aria-hidden="true"></i>
										</td>
										</tr></table>
									</div>
								</td>
                          </tr>
                      </table>
            </td></tr><tr><td>&nbsp;</td></tr>
  <!--            <tr>
		<td>
			<div id="videoDiv">
				<a href="#" onClick="openVideoDialog('Common','create_lesson','videoDailog')" class="demoVideoLink"><i class="ion-videocamera" aria-hidden="true" style="font-size:26px;font-weight:bold;color:#e23c00;"></i>&nbsp;video for Create Lesson</a>
		 	</div>
		</td> 
		<td width="80%" colspan="10" align="center"></td>
	   </tr> -->
             <tr>
                <td  id="appenddiv"  colspan="9" align="center" valign="top"  >
                   		<div id="lessonDiv"></div>
                 </td>
               </tr> 
               <tr><td height="20" colspan="2" align="center"><div id="result"></div></td></tr>      
               <tr><td height=5 colSpan=3></td></tr>                             
         </table>
          <tr><td><div id="videoDailog" title="Grade Fluency" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div></td></tr>
      <div id="dialog" style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" class="outer-container"><iframe align='center' id ="iframe" width="886" height="430" class="inner-container" style="border-style: none;"></iframe></div>
</body>
</html>