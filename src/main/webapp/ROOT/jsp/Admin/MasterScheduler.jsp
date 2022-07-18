<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Master Schedule</title>
<script type="text/javascript" src="resources/javascript/TeacherScheduler.js"></script>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
</head>
<body>
<div class="container-fluid dboard">
  <div class="row">
    <div class="col-md-12 sub-title title-border">
      <div id="title">Master Scheduler</div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-2">
      <input type="radio" id="grades" name="options" value="grades" checked onclick="showDropdown()" class="radio-design">
      <label for="grades" class="radio-label-design">View by Grades</label>
    </div>
    <div class="col-md-2">
      <input type="radio" id="teachers" name="options" value="teachers" onclick="showDropdown()" class="radio-design">
      <label for="teachers" class="radio-label-design"> View by Teacher</label>
      </td>
    </div>
    <div class="col-md-3">
      <div id="gradeDiv" style="display:block;"><span class="label">Grade</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <select id="gradeId" name="gradeId" onchange="getScheduleByGrade()" class="listmenu" style="width:120px;">
          <option value="select">Select</option>
          <c:forEach items="${schoolgrades}" var="schoolgrade">
            <option value="${schoolgrade.gradeId}">${schoolgrade.masterGrades.gradeName}</option>
          </c:forEach>
        </select>
      </div>
      <div id="teacherDiv" style="display:none;"><span class="label">Teacher</span>&nbsp;&nbsp;&nbsp;
        <select id="teacherId" name="teacherId" onchange="getScheduleByTeacher()" class="listmenu" style="width:120px;">
          <option value="select">Select</option>
          <c:forEach items="${teachersLt}" var="teacher">
            <option value="${teacher.teacherId}">${teacher.userRegistration.firstName} ${teacher.userRegistration.lastName}</option>
          </c:forEach>
        </select>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-12 text-center emptyspace"></div>
  </div>
  <br>
  <div class="row">
    <div class="col-md-12">
      <div id="schedulerDiv"></div>
    </div>
  </div>
</div>
</body>
</html>