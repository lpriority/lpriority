<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	<c:choose>
	  <c:when test="${divId == 'planSchedule' || divId == 'plannerData'}">
	  	Plan Schedule 
	  </c:when>
	  <c:when test="${divId =='viewTeacherRequests'}">
	   	View Teacher Requests 
	  </c:when>
	</c:choose>
</title>
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/CommonValidation.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript" src="/dwr/interface/teacherSchedulerService.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherScheduler.js"></script>
<script type="text/javascript" src="resources/javascript/admin/teacher_scheduler.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<script>
	$(function() {
	    $( "#startDate" ).datepicker({
	    	changeMonth: true,
	        changeYear: true,
	        showAnim : 'clip'
	    });
	    $( "#endDate" ).datepicker({
	    	changeMonth: true,
	        changeYear: true,
	        showAnim : 'clip'
	    });
	});
</script>
</head>
<body>
	<div class="">
		<table border="0" cellspacing="0" cellpadding="0" align="right">
            <tbody><tr>
             <td>
              	<ul class="button-group">
					<li><a href="teacherScheduler.htm" class="${(divId == 'planSchedule' || divId == 'plannerData')?'buttonSelect leftPill':'button leftPill'}"> Plan Schedule </a></li>
					<li><a href="planHomeRoom.htm" class="${(divId == 'planHomeRoom')?'buttonSelect':'button'}"> Plan Home Rooms </a></li>
					<li><a href="viewTeacherRequest.htm" class="${(divId =='viewTeacherRequests')?'buttonSelect rightPill':'button rightPill'}"> View Requests</a></li>
				</ul>
			</td>					
           </tr></tbody>
          </table>
      </div>
	<table width="100%" border=0 height="auto">	
		<input type="hidden" name="divId" id="divId" value="${divId}"></input>
		<input type="hidden" name="page" id="page" value="${page}"></input>
		<c:choose>
		  <c:when test="${divId == 'planSchedule' || divId == 'plannerData'}">
		       <tr><td>	<%@include file="plan_schedule.jsp"%> </td></tr>
	  
		  </c:when>
		  <c:when test="${divId =='viewTeacherRequests'}">
		   	 <tr><td>	<%@include file="view_teacher_requests.jsp"%> </td></tr>
		   </c:when>
		  </c:choose>
	</table>
</body>
</html>