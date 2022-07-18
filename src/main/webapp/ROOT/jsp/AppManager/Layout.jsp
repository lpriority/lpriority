<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true"%>
<link href="resources/css/dashboard/style.css" rel="stylesheet" type="text/css" media="all"/>
<link href="resources/css/dashboard/nav.css" rel="stylesheet" type="text/css" media="all"/>
<!--

<script src="resources/javascript/datatables/jquery.dataTables.min.js"></script>
<script src="resources/javascript/datatables/dataTables.bootstrap4.min.js"></script>
-->
<script src="resources/javascript/datatables/jquery-3.5.1.js"></script>
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>

<script src="resources/javascript/datatables/jquery.dataTables.min.js"></script>
<script src="resources/javascript/datatables/dataTables.bootstrap4.min.js"></script>

<link rel="stylesheet" href="resources/css/dt_table_design.css">
<script>
function startingTime() {
    var today = new Date();
    var hr = today.getHours();
    var min = today.getMinutes();
    var sec = today.getSeconds();
    ap = (hr < 12) ? "<span>AM</span>" : "<span>PM</span>";
    hr = (hr == 0) ? 12 : hr;
    hr = (hr > 12) ? hr - 12 : hr;
    //Add a zero in front of numbers<10
    hr = checkTime(hr);
    min = checkTime(min);
    sec = checkTime(sec);
    document.getElementById("clock").innerHTML = hr + ":" + min + ":" + sec + " " + ap;    
    var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    var days = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
    var curWeekDay = days[today.getDay()];
    var curDay = today.getDate();
    var curMonth = months[today.getMonth()];
    var curYear = today.getFullYear();
    var date = curWeekDay+", "+curDay+" "+curMonth+" "+curYear;
    document.getElementById("clock_date").innerHTML = date;    
    var time = setTimeout(function(){ startingTime() }, 500);
}
function checkTime(i) {
    if (i < 10) {
        i = "0" + i;
    }
    return i;
}
$(document).ready(function(){
	startingTime();
});
</script>
<header>
<input type="hidden" name="userTypeid" value="${userReg.user.userTypeid}">
<c:set var="userName" value="${userReg.title == null ? userReg.title : ''}${userReg.firstName} ${userReg.lastName }">
</c:set>
<input type='hidden'  id='regId' name='regId' value='${userReg.regId}' />
<table width="100%" style="margin-top:-1.6em;">
  <tr>
    <td style="width:25%"><img src="loadSchoolCommonFile.htm?schoolCommonFilePath=${userReg.school.logoLink}" width="170" height="65"><br>
      <span style="text-shadow:0 1px 1px rgba(0, 0, 0, 0.78);color:white;font-size:13px;font-weight:bold;font-family:cursive;letter-spacing: .7px;"><span style="color:black;text-shadow: 0 1px 1px rgba(189, 189, 189, 0.78);">Hello..! </span><span>${userName}</span></span> &nbsp; </td>
    <td align="left" style="width:5%"><c:if test="${!empty fn:trim(previousAuthUser)}">
        <form name="visitteachersdesk"  action="/authSwitchUser.htm" method="post">
          <input type="submit" class="back_button" onclick="removeStoredItem()" value="Back To Admin">
          </input>
        </form>
      </c:if></td>
    <td align="center" style="width:40%" class="celebrat"> ${userReg.school.schoolName } </td>
    <td align="center" style="width:10%"><div class="clockdate-wrapper">
        <div id="clock"></div>
        <div id="clock_date"></div>
      </div></td>
    <td align="right" style="width:20%"><section class="gradient"> <a href="#" onclick="loadPage('logOut.htm')" style="color:white;text-decoration:none;float:right;"><span class="logOutCls"><i class="fa fa-power-off"></i>&nbsp;Log out</span></a> </section></td>
  </tr>
</table>
<c:set var="now" value="<%=new java.util.Date()%>" />
<fmt:formatDate type="date" value="${now}" var="currentDate" pattern="yyyy-MM-dd"/>
</header>
<div class="container-fluid">
  <div id="headerWrap">
    <tiles:insertAttribute name="header" />
    <div class="row">
      <div class="col-md-8"></div>
      <div class="col-md-2" id="nav">
        <div id='content' style="margin-top: -12.5%;">
          <div class="ribbon3"><span>Administration</span></div>
        </div>
      </div>
    </div>
  </div>
</div>