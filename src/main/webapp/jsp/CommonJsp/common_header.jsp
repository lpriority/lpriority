<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true"%>
<link href="resources/css/dashboard/style.css" rel="stylesheet" type="text/css" media="all"/>
<link href="resources/css/dashboard/nav.css" rel="stylesheet" type="text/css" media="all"/>	

 <!----Start Notifications -------->
 <link rel="stylesheet" type="text/css" href="resources/css/notifications/ns-default.css">
 <link rel="stylesheet" type="text/css" href="resources/css/notifications/ns-style-attached.css">
 <script src="resources/javascript/notifications/classie.js"></script>
 <script src="resources/javascript/notifications/notificationFx.js"></script>
 <!----End Notifications -------->
 
 <script type="text/javascript" src="resources/javascript/admin/announcements.js"></script>
 <script type="text/javascript" src="resources/javascript/admin/events.js"></script>
 <script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/announcementsNEventsService.js"></script>
<style>
header{
  margin: 0; padding:1.5em .5em .2em; color: #efefef;
  overflow:hidden;
  position: relative;
}

header::after{
  content:"";
  position:absolute;
  top:0;
  left:0;
  height: 100%;
  width:100%;
  transform: skewY(-1deg);
  background: -webkit-gradient(linear, left top, left bottom, from(#07e4ff), to(#00d7f1));
  transform-origin: bottom left;
  z-index: -1;
}

header::before{
  content:"";
  position:absolute;
  top:0;
  left:0;
  height: 100%;
  width:100%;
  transform: skewY(3deg);
  background:#01d9f3;
  transform-origin: bottom right;
  z-index: -1;
}

.badge {
   position:relative;
}
.badge[data-badge]:after {
   content:attr(data-badge);
   position:absolute;
   top:-11px;
   right:-10px;
   font-size:.6em;
   background:-webkit-gradient(linear, left top, left bottom, from(#e9ff14), to(#d4cd00));
   color:black;
   width:16px;height:16px;
   text-align:center;
   line-height:16px;
   border-radius:50%;
   box-shadow:0 0 1px #2b2f00;
   padding:1.5px;
   text-shadow:0 1px 1px rgba(148, 148, 148, 0.78);
   font-weight:bold;
}
.clockdate-wrapper {
    background-color: transparent;
    padding:8px;
    max-width:100%;
    width:100%;
    text-align:center;
    border-radius:5px;
    margin:0 auto;
	text-shadow:0 1px 1px rgb(0, 154, 173);
}
#clock{
    background-color:transparent;
    font-family: 'Open Sans', sans-serif;
    font-size:15px;
    text-shadow:0 1px 1px rgb(150, 150, 150);
    color:white;
}
#clock span {
	color: black;
	text-shadow: 0 1px 1px rgb(123, 123, 123);
	font-size:10px;
	position:relative;
	top:-12px;
	left:-3px;
}
#clock_date {
    letter-spacing:1px;
    font-size:10.4px;
    font-family:arial,sans-serif;
    color: black;
}
.notification_title{
	text-decoration:underline;
	color:#427b00;
	font-size: 14px;
}

.ui-dialog { z-index: 900 !important ;}
</style>
<script>
var notiArray = [];	
function openNotification(){
	  notiArray = $('.ns-box').map(function() { return this;}).get();
      var notfiContent = $(".notificationCls").siblings('div:hidden')[0].innerHTML;
	  var notification = new NotificationFx({
			message : notfiContent,
			layout : 'attached',
			effect : 'bouncyflip',
			type : 'warning', // notice, warning or error
			onClose : function() {}
		});
	  
	   if(notiArray.length > 0)
		  notification.dismiss();
		notification.show();
}


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
	<c:set var="userName" value="${userReg.title == null ? userReg.title : ''}${userReg.firstName} ${userReg.lastName }"></c:set>
	<input type='hidden'  id='regId' name='regId' value='${userReg.regId}' />
	<table width="100%" style="margin-top:-1.6em;">
		<tr>
			<td style="width:25%">
				<img src="loadSchoolCommonFile.htm?schoolCommonFilePath=${userReg.school.logoLink}" width="170" height="65"><br>
				<span style="text-shadow:0 1px 1px rgba(0, 0, 0, 0.78);color:white;font-size:13px;font-weight:bold;font-family:cursive;letter-spacing: .7px;"><span style="color:black;text-shadow: 0 1px 1px rgba(189, 189, 189, 0.78);">Hello..! </span><span>${userName}</span></span> &nbsp;
				<c:if test="${notificationLength > 0 }">
					<a href="#" onclick="openNotification()" class="badge" data-badge="${notificationLength}"><i class="fa fa-bell" aria-hidden="true" style="font-size:18px;color:#ECECEC;text-shadow: 0 1px 2px rgb(13, 54, 60);"></i></a> &nbsp;&nbsp;
				 </c:if>
				<c:if test="${userReg.user.userType ne 'admin' }">
				  <a href="#" class="tooltip-right" data-tooltip="Archived notifications" onclick="getAllArchivedNotifications()"><i class="fa fa-archive" aria-hidden="true" style="font-size:18.5px;color:#5a5803;text-shadow:0 1px 1px rgba(189, 189, 189, 0.78);"></i></a><br>
			   </c:if>
			</td>
			<td align="left" style="width:5%">
				<c:if test="${!empty fn:trim(previousAuthUser)}">
			       <form name="visitteachersdesk"  action="/authSwitchUser.htm" method="post">
			      		<input type="submit" class="back_button" onclick="removeStoredItem()" value="Back To Admin"></input>
			       </form>
		        </c:if>  
			</td>
	        <td align="center" style="width:40%" class="celebrat">
			<%-- 	<%=schoolName%>			 --%>	${userReg.school.schoolName }
			</td>
			<td align="center" style="width:10%">
				  <div class="clockdate-wrapper">
				    <div id="clock"></div>
				    <div id="clock_date"></div>
				  </div>
			</td>
			<td align="right" style="width:20%">
				<section class="gradient">
	          			<a href="#" onclick="loadPage('logOut.htm')" style="color:white;text-decoration:none;float:right;"><span class="logOutCls"><i class="fa fa-power-off"></i>&nbsp;Log out</span></a>
			    </section>
			</td>
		</tr>
	</table>

			<c:set var="now" value="<%=new java.util.Date()%>" />
			<fmt:formatDate type="date" value="${now}" var="currentDate" pattern="yyyy-MM-dd"/>	
				<c:choose>
					<c:when test="${userReg.user.userType == 'admin' }">
						<!-- Admin Desktop messages -->
						<div id="notificationDiv" style="display:none;">												
							<c:choose>
								<c:when test="${fn:length(teacherRequests) gt 0}">
									<i class="fa fa-angle-double-right" style="color: #01b3ca;"></i>&nbsp;&nbsp;<a href="viewTeacherRequest.htm" class="notification_title"><b>Request to join class </b></a><br>
									<span style='font-family:-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";font-size: 13px;'>
									<c:forEach items="${teacherRequests}" var="tlist" varStatus="status">
										<div style="padding-top:1em;">
											${status.count}. <b>Teacher Name :</b> ${tlist.teacher.userRegistration.title} ${tlist.teacher.userRegistration.firstName} ${tlist.teacher.userRegistration.lastName}
										</div>
										<div>
										   &nbsp;&nbsp;&nbsp;&nbsp; <b>Grade :</b> ${tlist.grade.masterGrades.gradeName}
										</div>
										<div>
										   &nbsp;&nbsp;&nbsp;&nbsp; <b>Class :</b> ${tlist.studentClass.className}
										</div>
									</c:forEach>
									</span>
								</c:when>
								<c:when test="${promoStartDate <= currentDate  and currentDate <= promoEndDate}">
									<div>
										<i class="fa fa-angle-double-right" style="color: #01b3ca;"></i>&nbsp;&nbsp;<a href="promoteGrade.htm">Here it is promotion</a>
									</div>	
								</c:when>	
								<c:otherwise>
									There are no messages 
								</c:otherwise>
							</c:choose>
																		
							<c:if test="${promoStartDate <= currentDate  and currentDate <= promoEndDate}">
																				
							</c:if>
						</div>
					</c:when>
					<c:when test="${userReg.user.userType == 'teacher'}">
						<!-- Teacher Desktop messages -->
						<div id="notificationDiv" style="display:none;">	
							<c:if test="${fn:length(studentRequests) gt 0}">
								<a href="classRegistration.htm">Accept Request</a><br/>
							</c:if>
							<c:forEach items="${studentRequests}" var="slist">
								<div>
									Student Name : ${slist.student.userRegistration.firstName} ${slist.student.userRegistration.lastName} 
								</div>
								<div>
									Grade : ${slist.gradeClasses.grade.masterGrades.gradeName}
								</div>
								<div>
									Class : ${slist.gradeClasses.studentClass.className}
								</div>
							</c:forEach>
							<c:forEach items="${parentComments}" var="parentComment">
								<div>
									<i class="fa fa-angle-double-right" style="color: #01b3ca;"></i>&nbsp;&nbsp;<a href="previousReports.htm">${parentComment.student.userRegistration.firstName}${parentComment.student.userRegistration.lastName}'s parent commented on Progress card</a> 
								</div>
							</c:forEach>
							
							<c:if test="${fn:length(testToBeGraded) gt 0}">
								<i class="fa fa-angle-double-right" style="color: #01b3ca;"></i>&nbsp;&nbsp;<a href="gotoGradeRti.htm" class="notification_title"><b>New tests submitted for grading</b></a><br/>
							</c:if>
							<c:if test="${fn:length(notificationStatusLt) gt 0}">
								<c:forEach items="${notificationStatusLt}" var="notificationStatus" varStatus="status">
									<i class="fa fa-angle-double-right" style="color: #01b3ca;"></i>&nbsp;&nbsp;<a href="#" class="notification_title" onclick="openAnnouncementDialog(${notificationStatus.announcements.announcementId},${notificationStatus.notificationStatusId})"><b>${notificationStatus.announcements.annoncementName}</b></a><br> 
								</c:forEach>	
							</c:if>
							<c:if test="${fn:length(studentRequests) eq 0 && fn:length(parentComments) eq 0 && fn:length(testToBeGraded) eq 0 && fn:length(notificationStatusLt) eq 0}">
								There are no messages 
							</c:if>
						</div>
					</c:when>
					<c:when test="${userReg.user.userType == 'student above 13' || userReg.user.userType == 'student below 13' }">
						<!-- Student Desktop messages -->
						<c:set var="check" value="0"></c:set>
						<div id="notificationDiv" style="display:none;">	
							<c:forEach items="${teacherResponses}" var="teacherResponse">
								<div>
									 Request to join class
								</div>
								<div>
									Class : ${teacherResponse.gradeClasses.studentClass.className} 
								</div>
								<div>
									Status : ${teacherResponse.status} 
								</div>
							</c:forEach>
							<c:if test="${rtiTests eq 'true'}"> 
								<div>
									<i class="fa fa-angle-double-right" style="color: #01b3ca;"></i>&nbsp;&nbsp;<a href="goToStudentRTIPage.htm" class="notification_title"><b> You have been assigned Literacy Development tests</b></a>
								</div>
							</c:if>	
							<c:if test="${promoEndDate < currentDate && userReg.user.userType == 'student above 13' 
									&& studentObj.gradeStatus == 'promoted'}">
								<c:set var="check" value="1"></c:set>
								<div style="padding-top: 10px;padding-bottom: 10px;">
									<i class="fa fa-angle-double-right" style="color: #01b3ca;"></i>&nbsp;&nbsp;<a href="educationalInfo.htm" style='text-decoration:underline;color:#427b00;'><b>
											You have Promoted To Next Grade. 
												Please Select subjects for Promoted Grade
										</b>
									</a>
								</div>	
							</c:if>	
							<c:if test="${rejectedBooks > 0}"> 
								<div>
									<i class="fa fa-angle-double-right" style="color: #01b3ca;"></i>&nbsp;&nbsp;<a href="returnedBooks.htm" class="notification_title"><b> Books have been returned by teacher</b></a>
								</div>
							</c:if>	
							<c:if test="${fn:length(notificationStatusLt) gt 0}">
								<c:forEach items="${notificationStatusLt}" var="notificationStatus" varStatus="status">
									<i class="fa fa-angle-double-right" style="color: #01b3ca;"></i>&nbsp;&nbsp;<a href="#" class="notification_title" onclick="openAnnouncementDialog(${notificationStatus.announcements.announcementId},${notificationStatus.notificationStatusId})"><b>${notificationStatus.announcements.annoncementName}</b></a><br> 
								</c:forEach>	
							</c:if>										
							<c:if test="${fn:length(teacherResponses) eq 0 && rtiTests ne 'true' && fn:length(notificationStatusLt) eq 0 && rejectedBooks eq 0 }">
								<c:if test="${check == 0}">
									There are no messages 
								</c:if> 
							</c:if>
						</div>
					</c:when>		
					<c:when test="${userReg.user.userType == 'parent'}">
						<!-- Parent Desktop messages -->
						<div id="notificationDiv"  style="padding-bottom: 10px;display:none;">
							<c:forEach items="${teacherResponsesforClassRequests}" var="teacherResponses">
								<div>Child Name : ${teacherResponses.student.userRegistration.firstName} ${teacherResponses.student.userRegistration.lastName} </div>
								<div>Class : ${teacherResponses.gradeClasses.studentClass.className}</div>
								<div>Status : ${teacherResponses.status} </div>
							</c:forEach>						
							<c:forEach items="${attendanceList}" var="attendance">
								<div> 	
									<font color="black"> ${attendance.student.userRegistration.firstName} ${attendance.student.userRegistration.lastName}
								 	is ${attendance.status} for ${attendance.classStatus.section.gradeClasses.studentClass.className}</font>
								 </div>
							</c:forEach>
							<c:forEach items="${children}" var="child">
								<c:if test="${promoEndDate < currentDate && child.gradeStatus == 'promoted'}">													
									<div style="padding-top: 10px;padding-bottom: 10px;"> 	
										<i class="fa fa-angle-double-right" style="color: #01b3ca;"></i>&nbsp;&nbsp;<a href="studentEducationalInfo.htm?stdRegId=${child.userRegistration.regId}">
											<font color="red">
												Your child ${child.userRegistration.firstName} ${child.userRegistration.lastName} Promoted To Next Grade. 
													Please Select subjects for Promoted Grade
											</font>
										</a>
									</div>
								</c:if>
							</c:forEach>
							<c:if test="${fn:length(notificationStatusLt) gt 0}">
								<c:forEach items="${notificationStatusLt}" var="notificationStatus" varStatus="status">
									<i class="fa fa-angle-double-right" style="color: #01b3ca;"></i>&nbsp;&nbsp;<a href="#" class="notification_title" onclick="openAnnouncementDialog(${notificationStatus.announcements.announcementId},${notificationStatus.notificationStatusId})"><b>${notificationStatus.announcements.annoncementName}</b></a><br> 
								</c:forEach>	
							</c:if>
							<c:if test="${fn:length(teacherResponsesforClassRequests) eq 0 && fn:length(attendanceList) eq 0 && fn:length(notificationStatusLt) eq 0}">
								There are no messages 
							</c:if>
						</div>
					</c:when>								
					<c:otherwise>
						There are no messages 
					</c:otherwise>
				</c:choose>	
			<span class="notificationCls"></span>	
			
</header>

<div id="announcementDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"><iframe id='announcementIframe' width="98%" height="98%" style="border-radius:1em;"></iframe></div>
<div id="eventDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"><iframe id='eventIframe' width="98%" height="95%" style="border-radius:1em;"></iframe></div>
<div id="archivedNotifDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
