<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page session="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet"
	type="text/css" />
</head>
<body>
<div class="container-fluid mdboard">
  <div class="row">
    <div class="col-md-12 text-right">
      <nav id="primary_nav_wrap">
        <ul class="button-group">
          <c:if test="${teacherObj != null }">
            <li class="sline"><a href="stem.htm" class="${(tab == 'stem')?'buttonSelect leftPill tooLongTitle':'button leftPill tooLongTitle'}">${LP_STEM_TAB}</a>
              <ul>
                <li><a href="stem.htm" class="${(page == 'stem')?'stemOnSubButton':'stemSubButton'}" style="float: right; width: 130px;">${LP_STEM_TAB}</a></li>
                <li><a href="goToAssignStemUnitsPage.htm" class="${(page == 'assignStemUnits')?'stemOnSubButton':'stemSubButton'}" style="float: right; width: 130px;">Assign ${LP_STEM_TAB} Units</a></li>
              </ul>
            </li>
            <li class="sline"><a href="curriculumPlan.htm" class="${(tab == 'curriculum')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Curriculum </a></li>
            <li class="sline"><a href="displayTeacherClassFiles.htm?uploadLessons=yes&fileType=public&page=uploadFile" class="${(tab == 'uploadFile')?'buttonSelect tooLongTitle':'button tooLongTitle'}"> Upload Files </a></li>
            <li class="sline"><a href="goToAssignLessonsPage.htm" class="${(tab == 'assignLessons')?'buttonSelect tooLongTitle':'button tooLongTitle'}"> Assign Lessons </a></li>
            <li><a href="assignAssessments.htm" class="${(tab == 'assignAssessments')?'buttonSelect tooLongTitle':'button tooLongTitle'}"> Assign Assessments</a></li>
            <li class="sline"><a href="viewAssignedCurriculum.htm" class="${(tab == 'viewCurriculum')?'buttonSelect tooLongTitle':'button tooLongTitle'}">View Curriculum </a></li>
            <li><a href="showAssignedAssessments.htm" class="${(tab == 'showAssigned')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Show Assigned Assessments</a></li>
            <li class="sline"><a href="gotoGradeAssessments.htm" class="${(tab == 'gradeAssessments')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Grade Assessments</a></li>
            <li><a href="groupPerformance.htm" class="${(tab == 'groupPerformance')?'buttonSelect rightPill tooLongTitle':'button rightPill tooLongTitle'}">Group Performance Task</a>
              <ul style="left: -6em;">
                <li><a href="groupPerformance.htm" class="${(subTab == 'createPerformanceGroup')?'selectOnSubButton':'selectOffSubButton'}">Create Performance Task Group</a></li>
                <li><a href="splitStudentsToGroup.htm" class="${(subTab == 'splittingStudents')?'selectOnSubButton':'selectOffSubButton'}">Splitting Students Into Groups</a></li>
                <li><a href="groupPerformanceAssign.htm" class="${(subTab == 'assigningPerformanceGroup')?'selectOnSubButton':'selectOffSubButton'}">Assigning Group Performance Task</a></li>
                <li><a href="groupGradeAssessments.htm" class="${(subTab == 'gradePerformanceGroup')?'selectOnSubButton':'selectOffSubButton'}">Grade Group Performance Task</a></li>
              </ul>
            </li>
          </c:if>
        </ul>
      </nav>
    </div>
    <div class="row">
      <div class="col-md-12 sub-title title-border">
        <c:if test="${tab == 'stem' }">
          <div id="title">
            <c:choose>
              <c:when test="${page == 'stem'}"> ${LP_STEM_TAB} Curriculum </c:when>
              <c:when test="${page == 'assignStemUnits'}"> Assign ${LP_STEM_TAB} Units </c:when>
            </c:choose>
          </div>
        </c:if>
        <c:if test="${tab == 'curriculum' }">
          <div id="title">Curriculum Planner</div>
        </c:if>
        <c:if test="${tab == 'remove' }">
          <div id="title">Remove Unit/Lesson</div>
        </c:if>
        <c:if test="${tab == 'createHome' }">
          <div id="title">Homework</div>
        </c:if>
        <c:if test="${tab == 'editHome' }">
          <div id="title">Homework</div>
        </c:if>
        <c:if test="${tab == 'uploadFile' }">
          <div id="title">Upload Files</div>
        </c:if>
        <c:if test="${tab == 'assignLessons' }">
          <div id="title">Assign Lessons</div>
        </c:if>
        <c:if test="${tab == 'assignAssessments' }">
          <div id="title">Assign Assessments</div>
        </c:if>
        <c:if test="${tab == 'viewCurriculum' }">
          <div id="title">View Curriculum</div>
        </c:if>
        <c:if test="${tab == 'showAssigned' }">
          <div id="title">Assigned Assessments</div>
        </c:if>
        <c:if test="${tab == 'gradeAssessments' }">
          <div id="title">Grade Assessments</div>
        </c:if>
        <c:if test="${tab == 'gradeGroup' }">
          <div id="title">Grade Group Assessments</div>
        </c:if>
        <c:if test="${tab == 'groupPerformance' && subTab == 'createPerformanceGroup' }">
          <div id="title">Group Performance Task</div>
        </c:if>
        <c:if test="${tab == 'groupPerformance' && subTab == 'splittingStudents' }">
          <div id="title">Split Students into Groups</div>
        </c:if>
        <c:if test="${tab == 'groupPerformance' && subTab == 'assigningPerformanceGroup' }">
          <div id="title">Assign Group Performance Task</div>
        </c:if>
        <c:if test="${tab == 'groupPerformance' && subTab == 'gradePerformanceGroup' }">
          <div id="title">Grade Group Performance Task</div>
        </c:if>
      </div>
    </div>
	<div class="row">
		<div class="col-md-12 text-center emptyspace"></div>
	</div>
  </div>
</div>
<table width="100%" border=0 align="right" cellPadding=0 cellSpacing=0>
  <c:if test="${tab == 'edit'}">
    <tr>
      <td colspan="2" align="left" valign="left" width="100%"
					style="padding-left: 8em; padding-bottom: 2em"><table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="link-title">
          <tr>
            <td style="color: white; font-weight: bold" width="162"><c:choose>
                <c:when test="${subTab == 'editUnits'}"> <a href="editCurriculum.htm"><font color="green">Edit
                  Units</font></a> </c:when>
                <c:otherwise> <a href="editCurriculum.htm">Edit Units</a> </c:otherwise>
              </c:choose></td>
            <td style="color: white; font-weight: bold" width="162"><c:choose>
                <c:when test="${subTab == 'editLessons'}"> <a href="editLessons.htm"><font color="green">Edit
                  Lessons</font></a> </c:when>
                <c:otherwise> <a href="editLessons.htm">Edit Lessons</a> </c:otherwise>
              </c:choose></td>
            <td style="color: white; font-weight: bold" width="162"><c:choose>
                <c:when test="${subTab == 'editActivity'}"> <a href="editActivity.htm"><font color="green">Edit
                  Activity</font></a> </c:when>
                <c:otherwise> <a href="editActivity.htm">Edit Activity</a> </c:otherwise>
              </c:choose></td>
            <td style="color: white; font-weight: bold" width="162"><c:choose>
                <c:when test="${subTab == 'editAssessments'}"> <a href="editAssessments.htm"><font color="green">Edit
                  Assessments</font></a> </c:when>
                <c:otherwise> <a href="editAssessments.htm">Edit Assessments</a> </c:otherwise>
              </c:choose></td>
          </tr>
        </table></td>
    </tr>
  </c:if>
</table>
</body>
</html>