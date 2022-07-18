<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page session="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="container-fluid mdboard">
  <div class="row">
    <div class="col-md-12 text-right">
      <ul class="button-group">
        <li class="sline"><a href="homeworks.htm" class="${(tab == 'createHome')?'buttonSelect leftPill tooLongTitle':'button leftPill tooLongTitle'}"> Homework</a></li>
        <li class="sline"><a href="assignHomeworks.htm" class="${(tab == 'assignHome')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Assign Homework </a></li>
        <li class="sline"><a href="gotoCurrentHomework.htm" class="${(tab == 'currentHome')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Current Homework </a></li>
        <li><a href="gotoHomeworkManager.htm" class="${(tab == 'homeManager')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Homework Manager</a></li>
        <li class="sline"><a href="gotoGradeHomeworks.htm" class="${(tab == 'gradeHome')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Grade Homework </a></li>
        <li class="sline"><a href="gotoHomeworkReports.htm" class="${(tab =='homeReports')?'buttonSelect rightPill tooLongTitle':'button rightPill tooLongTitle'}"> Homework Reports</a></li>
      </ul>
    </div>
  </div>
  <div class="row">
    <div class="col-md-12 sub-title title-border">
      <c:if test="${tab == 'createHome' }">
        <div id="title">Homework</div>
      </c:if>
      <c:if test="${tab == 'editHome' }">
        <div id="title">Edit Homework</div>
      </c:if>
      <c:if test="${tab == 'assignHome' }">
        <div id="title">Assign Homework</div>
      </c:if>
      <c:if test="${tab == 'currentHome' }">
        <div id="title">Current Homework</div>
      </c:if>
      <c:if test="${tab == 'homeManager' }">
        <div id="title">Homework Manager</div>
      </c:if>
      <c:if test="${tab == 'gradeHome' }">
        <div id="title">Grade Homework</div>
      </c:if>
      <c:if test="${tab == 'homeReports' }">
        <div id="title">Homework Reports</div>
      </c:if>
    </div>
  </div>
  <div class="row">
    <div class="col-md-12 text-center emptyspace"></div>
  </div>
</div>
</body>
</html>