<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page session="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript">
		function togglePage(thisVar, url, name){
			$.ajax({
				type : "GET",
				url : url+".htm",
				success : function(response) {
					$("#viewAttendence").empty();
					$("#viewAttendence").append(response);
					$("#subTitle").html(name);
					var current = document.getElementsByClassName("buttonSelect");
				    current[0].className = current[0].className.replace(" buttonSelect", " button");
				    var activeElement = $(thisVar);
				    activeElement.addClass('buttonSelect');
				    activeElement.removeClass('button');
				    $('title').text(name);
				}
			});
		}
	</script>
	<title>View Daily Attendance</title>
</head>
<body>
	
	<table border="0" cellspacing="0" cellpadding="0" align="right" class="">
         <tbody><tr>
	         <td>
	          	<ul class="button-group">
				<li><a href="#" onclick="togglePage(this, 'viewDailyAttend', 'View Daily Attendance')"  class="btn ${(tab == 'daily attendance')?'buttonSelect leftPill tooLongTitle':'button leftPill tooLongTitle'}"> View Daily Attendence</a></li>
				<li><a href="#" onclick="togglePage(this, 'viewWeeklyAttendance', 'View Weekly Attendance')"  class="btn ${(tab == 'weekly attendance')?'buttonSelect tooLongTitle':'button tooLongTitle'}"> View Weekly Attendance</a></li>
				<li><a href="#" onclick="togglePage(this, 'viewSchoolAttendance', 'View School Attendance')"  class="btn ${(tab == 'school attendance')?'buttonSelect rightPill tooLongTitle':'button rightPill tooLongTitle'}">School Attendance</a></li>
				</ul>
			</td>
          </tr></tbody>
    </table>
	<table width="100%" class="title-pad" border="0">
		<tr>
			<td class="sub-title title-border" height="40" align="left" id="subTitle">View Daily Attendance</td>
		</tr>
	</table>
</body>
</html>