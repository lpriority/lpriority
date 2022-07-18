<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Teacher Report Card</title>
<script type="text/javascript" src="resources/javascript/TeacherJs/teacher_self_evaluation.js"></script>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
</head>
<body>
<div class="container-fluid dboard">
  <div class="row">
    <div class="col-md-12 sub-title title-border">
      <div id="title">Teacher Report Card</div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-3"></div>
    <div class="col-md-3">
      <label class="label">Teacher Name : </label>
      <span class="tabtxt">${TeacherName}</span></div>
    <div class="col-md-3">
        <%
        	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        	String formattedDate = df.format(new Date());
        %>
      <label class="label">Date :</label>
      <span class="tabtxt"><%=formattedDate%></span></div>
  </div>
  <div class="row">
    <div class="col-md-12 text-center emptyspace"></div>
  </div>
</div>
<br />
<table class="des" border=0 width="50%" align="center">
  <tr>
    <td><div class="Divheads">
        <table align="center">
          <tr>
            <td class="headsColor">Teacher Report Card</td>
          </tr>
        </table>
      </div>
      <div class="DivContents">
        <table>
          <c:choose>
            <c:when test="${rcount == 0}">
              <tr>
                <td align="center">No Report Available.</td>
              </tr>
            </c:when>
            <c:otherwise>
              <c:forEach items="${TRList}" var="tp" varStatus="count">
                <tr>
                  <td width="40%" style="padding-top: 1em;" class="tabtxt">${count.count}.&nbsp;
                    <c:out value="${tp.teacherPerformances.performance}" /></td>
                  <td style="padding-left: 10em;" class="tabtxt">:</td>
                  <td style="padding-left: 4em; padding-top: 1em" width="20%" class="tabtxt"><c:out
												value="${tp.choosenOption}" /></td>
                  <td style="padding-top: 1em;" class="tabtxt">-
                    <c:out value="${tp.comments}" /></td>
                  <td></td>
                </tr>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </table>
      </div></td>
  </tr>
</table>
</body>
</html>
