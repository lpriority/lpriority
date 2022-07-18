<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<c:set var="userName" value="${userReg.title == null ? userReg.title : ''}${userReg.firstName} ${userReg.lastName }"></c:set>
   <div class="column_middle_grid1">
        <div class="profile_picture">
       	<table width="100%">
	       	<tr>
	       	   <td width="40%">
	        	  <form name="uploadProfilePicForm" id="uploadProfilePicForm" novalidate> 
					 <div class="show-image">
						<a href="#" class="tooltip" data-tooltip="Click here to view your Profile Picture"><img id="imgDiv" class="imgCls" style="display:none;width: 85px;height: 85px;border-radius: 50%;" onClick="openPreviewImage()"/></a>
						<div id="iconDiv" class="fa fa-user-circle-o" aria-hidden="true" style="display:block;font-size: 80px;text-shadow: 0 1px 4px rgb(140, 156, 158);color:#00cae2;margin-top:5px;margin-bottom:5px;"></div>
					     <a href="#" class="tooltip-left" data-tooltip="Upload"><button id="upload" class="stylish-button button--ujarak button--round-l button--border-thin button--text-thick image-buttons" style="margin-left:10%;"><i class="ion-edit" style="font-size:16px;font-weight:bold;"></i></button></a>
						<input type="file" id="file" name="file" required="required" style="display:none;" onChange="showimagepreview(this)"/>
					    <a href="#" class="tooltip-right" data-tooltip="Remove"><button id="remove" class="stylish-button button--ujarak button--round-l button--border-thin button--text-thick image-buttons" onClick="deleteProfileImage()" style="margin-left:70%;"><i class="fa fa-times" style="font-size:14px;font-weight:bold;"></i></button></a>
				    </div>
				  </form>
			   </td>
	       	   <td width="60%" style="font-family: 'Open Sans Condensed', sans-serif;"> 
	       	      <table width="100%">
	       	        <tr><td align="left" width="100%" height="20">
	       	        <span>Hello..!<br><span style="font-size: 13px;letter-spacing:.2px;color:#525000;margin:.2em;"> ${userName}</span></span>
	       	        </td></tr>
	       	        <tr><td align="left" width="100%">
	       	         <table width="100%">
	        	        <tr>
	        	        	<td align="left" width="16%">
			        	         <div class="txtStyle sonar-wrapper" style="${notificationLength > 0 ? 'margin-top:-2em;margin-left: -1.5em;display:block;': 'display:none;'}">
									<div class="sonar-emitter">
								    <div class="sonar-wave"></div>
								  </div>
								 </div>
							 </td>
						  <td align="left" width="80%" style="margin-top:1.8em;" height="30">
					  	 	 <a href="#" onclick="openNotification()" style="${notificationLength > 0 ? 'margin-top:2em;margin-left:-2em;display:block;text-decoration: underline;': 'display:none;'}">You have <font size="2" color="#008a9a">${notificationLength}</font> message${notificationLength > 1 ? 's':''}</a>
					  	   </td></tr>
				  	  </table>
	       	        </td></tr>
	       	      </table>
	       	   </td>
	       	</tr>
       	</table>
        </div>
    </div>
      <c:choose>
		<c:when test="${userReg.user.userType == 'teacher'}">
			 <div class="middle_ribbon" style="margin-left: -3em;margin-top: .5em;"><span class="middle_text">Teacher's Desktop</span></div>
		</c:when> 
		<c:when test="${userReg.user.userType == 'student above 13' || userReg.user.userType == 'student below 13'}">
			 <div class="middle_ribbon" style="margin-left: -3em;margin-top: .5em;"><span class="middle_text">Student's Desktop</span></div>
		</c:when> 
	</c:choose>
		
         <table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="200" valign="top" style="padding-top: 2em;">
				   <div class="tsc_ribbon_wrap dashboard-center-box tab_fold" style="height:105%;">
			   		 <div class="ribbon-wrap left-edge fork lred"><span>My Classes</span></div>
				  <c:choose>
					<c:when test="${fn:length(classes) gt 0}">
					  <div class="scrollbox scrollbox_delayed flexcroll" tabindex="0">
  						<div class="scrollbox-content"> 
							<table border="0" class="" style="font-size: 12.5px;margin-top:3em;" width="98%">
								<tr class="text">							
									<c:if test="${userRegistration.user.userType eq 'teacher'}"><td><b>Grade</b></td></c:if>
									<c:if test="${userRegistration.user.userType eq 'teacher'}"><td><b>Section </b></td></c:if>
									<td><b>Class</b></td>
									<td><b>Period</b></td>
									<td><b>Start Time</b></td>
									<td><b>End Time</b></td>
									<c:if test="${userRegistration.user.userType ne 'teacher'}"><td><b>Teacher Name</b></td></c:if>
								</tr>
								<c:forEach items="${classes}" var = "class">
									<tr class="txtStyle">
										<c:if test="${userRegistration.user.userType == 'teacher'}"><td>${class.classStatus.section.gradeClasses.grade.masterGrades.gradeName}</td></c:if>
										<c:if test="${userRegistration.user.userType == 'teacher'}">
											<td>
												<a href="getStudentRoster.htm?csId=${class.classStatus.csId}" target="_blank" >${class.classStatus.section.section}</a>
											</td>
										</c:if>
										<td>
											<c:choose>
												<c:when test="${userRegistration.user.userType == 'teacher'}">
													${class.classStatus.section.gradeClasses.studentClass.className}
												</c:when>
												<c:otherwise>
													<a href="javascript:void(0);" onclick="getCompositeChartByStudent(${class.classStatus.csId})">${class.classStatus.section.gradeClasses.studentClass.className}</a>
												</c:otherwise>
											</c:choose>
										</td>
										<td>${class.periods.periodName}</td>
										<td>${class.periods.startTime}</td>
										<td>${class.periods.endTime}</td>
										<c:if test="${userRegistration.user.userType ne 'teacher'}">
											<td>	
												${class.classStatus.teacher.userRegistration.title}
												${class.classStatus.teacher.userRegistration.firstName}${class.classStatus.teacher.userRegistration.lastName}
											</td>
										</c:if>
									</tr>
								</c:forEach>
							</table>
						</div>
					 </div>
					</c:when>
					<c:otherwise>
                     	<div style="font-size:2em;color:#6b6800;padding-top:3em;"> <i class="fa fa-files-o" aria-hidden="true"></i>&nbsp;&nbsp;<span style="color:#000000;text-shadow: 0 1px 1px rgb(204, 204, 204);font-weight: bold;font-size:12px;font-family:cursive;">No Classes are scheduled today.</span></div>
                    </c:otherwise>
		         </c:choose>
				 </div>
			 </td>
		</tr>
        <tr>
		<td align="center" valign="top" height="200" style="padding-top: 2em;">
		  <div class="tsc_ribbon_wrap dashboard-center-box tab_fold" style="height:105%;">
	   		 <div class="ribbon-wrap right-edge fork lblue"><span>Homeworks</span></div>
	   		 <c:choose>
				<c:when test="${fn:length(tests) gt 0}">
				 <div class="scrollbox scrollbox_delayed flexcroll" tabindex="0">
  					<div class="scrollbox-content"> 
					 <table border="0" class="text1" style="font-size: 12.5px;margin-top:3em;" width="100%">
						<tr class="text">
							<td align="center" width="15%"><b>Class</b></td>
							<td align="center" width="30%"><b>Lesson</b></td>
							<td align="center" width="20%"><b>Assignment Type</b></td>
							<td align="center" width="15%"><b>Start Date</b></td>
							<td align="center" width="15%"><b>Due Date</b> </td>
						</tr>			
						<c:forEach items="${tests}" var="test">
							<c:if test="${test.usedFor eq 'homeworks'}">
								<tr  class="txtStyle">
									<td align="center">
										<c:if test="${userRegistration.user.userType eq 'student'}"><a href="goToStudentHomeworksPage.htm">go to homework</a>	</c:if>
										${test.classStatus.section.gradeClasses.studentClass.className}
									</td>
									<td align="center">${not empty test.lesson.lessonName ?test.lesson.lessonName : '-'}</td>
									<td align="center">${test.assignmentType.assignmentType}</td>
									<td align="center">${test.dateAssigned}</td>
									<td align="center">${test.dateDue}</td>
								</tr>
							</c:if>
						</c:forEach>
					 </table>
				   </div>
				</div>
				</c:when>
				<c:otherwise>
                    	<div style="font-size:2em;color:#6b6800;padding-top:3em;"> <i class="ion-ios-home" aria-hidden="true"></i>&nbsp;&nbsp;<span style="color:#000000;text-shadow: 0 1px 1px rgb(204, 204, 204);font-weight: bold;font-size:12px;font-family:cursive;">No Homework Assignments found.</span></div>
                   </c:otherwise>
	         </c:choose>
			</div>
		</td>
		</tr>
		<tr>
		<td align="center" valign="top" height="200" style="padding-top: 2em;">
			<div class="tsc_ribbon_wrap dashboard-center-box tab_fold" style="height:105%;">
			   		 <div class="ribbon-wrap left-edge fork lred"><span>Assessments</span></div>
	   		 	<c:choose>
				  <c:when test="${fn:length(tests) gt 0}">
					<div class="scrollbox scrollbox_delayed flexcroll" tabindex="0">
  						<div class="scrollbox-content"> 
						<table class="text1" width="100%" style="font-size: 12.5px;margin-top:3em;">
							<tr class="text">
								<td align="center" width="15%"><b>Class</b></td>
								<td align="center" width="30%"><b>Lesson</b></td>
								<td align="center" width="20%"><b>Assignment Type</b></td>
								<td align="center" width="15%"><b>Start Date</b></td>
								<td align="center" width="15%"><b>Due Date</b> </td>
							</tr>
							<c:forEach items="${tests}" var="test">
								<c:if test="${test.usedFor eq 'assessments'}">
									<tr  class="txtStyle">
										<td align="center">
											<c:if test="${userRegistration.user.userType eq 'student'}"><a href="goToStudentAssessmentsPage.htm">go to assessment </a>	</c:if>	
											${test.classStatus.section.gradeClasses.studentClass.className}
										</td>
										<td align="center">${not empty test.lesson.lessonName?test.lesson.lessonName : '-'}</td>
										<td align="center">${test.assignmentType.assignmentType}</td>
										<td align="center">${test.dateAssigned}</td>
										<td align="center">${test.dateDue}</td>
										</tr>
									</c:if>
								</c:forEach>
							</table>
						</div>
					 </div>
					</c:when>
					<c:otherwise>
                     	<div style="font-size:2em;color:#6b6800;padding-top:3em;"> <i class="fa fa-clock-o" aria-hidden="true"></i>&nbsp;&nbsp;<span style="color:#000000;text-shadow: 0 1px 1px rgb(204, 204, 204);font-weight: bold;font-size:12px;font-family:cursive;">No Assignments due for today.</span></div>
                    </c:otherwise>
		         </c:choose>
			</div>
		</td>
	 </tr>
	</table>
<div id="compositeChart" title="Composite Chart" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>