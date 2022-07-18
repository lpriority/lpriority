
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page session="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<title>Insert title here</title>
<script type="text/javascript">
	function getStudentGradeClasses(yearId){
		var academicYear = $('#academicYear').val();
		var academicUserType = $('#academicUserType').val();
		if(academicUserType == "parent"){
			if(academicYear != 'select'){	
				$.ajax({
					type : "POST",
					url : "updateChildYear.htm",
					data : "academicYear=" + academicYear,
					success : function(response) {
						$("#csId").empty();
						$("#csId").append(
							$("<option></option>").val('select').html('Select Class'));
						document.getElementById("studentId").value = "";
					}
				});
			}		
		}else{
			$("#StuAssessQuestionsList").empty();
			$("#studentsBenchmarkTestResultsDiv").empty();
			$("#StuBenchmarkQuestionsList").empty();
			$("#studentsTestResultsDiv").empty();
			var studentContainer = $(document.getElementById('studentDiv'));
			$(studentContainer).html(""); 
			if(academicYear != 'select'){	
				$.ajax({
					type : "POST",
					url : "getStudentGradeClasses.htm",
					data : "academicYear=" + academicYear,
					success : function(response) {
						if(response.grade.masterGrades != null){
							document.getElementById("gradeName").value = response.grade.masterGrades.gradeName;
						}else{
							document.getElementById("gradeName").value = "";
						}
						var gradeClasses = response.gradeClasses;
						var academicTab = $('#academicTab').val();
						if(academicTab == "progressMonitoring"){
							$("#csId").empty();
							$("#csId").append(
								$("<option></option>").val('select').html('Select Class'));
							$.each(gradeClasses, function(index, value) {
								var classStatusId = "";
								if(value.classStatus > 0){
									classStatusId = value.classStatus;
								}else{
									classStatusId = value.classStatus.csId;
								}
								$("#csId").append(
										$("<option></option>").val(classStatusId).html(
												value.gradeClasses.studentClass.className));
							});
						}else{
							$("#classId").empty();
							$("#classId").append(
								$("<option></option>").val('select').html('Select Class'));
							$.each(gradeClasses, function(index, value) {
								$("#classId").append(
										$("<option></option>").val(value.gradeClasses.studentClass.classId).html(
												value.gradeClasses.studentClass.className));
							});
						}
					}
				});
			}
		}
	}
	
</script>
</head>
<body>
	<table width="100%" height="29" border="0" align="right" cellpadding="0"  style="padding-top:1em;" cellspacing="0">
		<tr>
		   <td align="right">
		    <nav id="primary_nav_wrap">
             <ul class="button-group">
				<c:choose>	
					<c:when test="${userReg.user.userType =='parent'}">
						<li><a href="childRtiTestResults.htm" class="${(tab == 'rti test results')?'buttonSelect leftPill tooLongTitle ':'button leftPill tooLongTitle'}">Literacy Development Test Results </a></li>
						<li><a href="childRtiResults.htm" class="${(tab == 'rtiResults')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Literacy Development Results</a></li>
						<li><a href="childBenchmarkResults.htm" class="${(tab == 'FluencyReading Results')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Reading Results</a>
						   	<ul>
						      	<li><a href="childBenchmarkResults.htm" class="${(page == 'FluencyReading Results')?'selectOnSubButton':'selectOffSubButton'}" >Fluency Reading Results</a></li>
						        <li><a href="childAccuracyResults.htm" class="${(page == 'Accuracy Reading Results')?'selectOnSubButton':'selectOffSubButton'}" >Accuracy Reading Results</a></li>
					        </ul>
					   </li>
						<li><a href="viewProgressMonitor.htm" class="${(tab == 'progressMonitoring')?'buttonSelect rightPill tooLongTitle':'button rightPill tooLongTitle'}">Progress Monitoring </a></li>
					</c:when>
					<c:otherwise>
						<li><a href="goToStudentRTIPage.htm" class="${(tab == 'view rti tests')?'buttonSelect leftPill tooLongTitle ':'button leftPill tooLongTitle'}">View Literacy Development Tests</a></li>
					   <li><a href="studentTestResults.htm" class="${(tab == 'rti test results' || page == 'FluencyReading Results' || page == 'Accuracy Reading Results' || tab == 'progressMonitoring')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Results </a>
					       <ul>
					           <li><a href="studentTestResults.htm" class="${(tab == 'rti test results')?'selectOnSubButton':'selectOffSubButton'}" >Literracy Development Results </a>					           
					           <li><a href="studentBenchmarkResults.htm" class="${(page == 'FluencyReading Results')?'selectOnSubButton':'selectOffSubButton'}" >Reading Results</a></li>
					           <li><a href="viewProgressMonitor.htm" class="${(page == 'progressMonitoring')?'selectOnSubButton':'selectOffSubButton'}" >Progress Monitoring</a></li>
					      </ul>
					   </li>
					   <%-- <li><a href="studentBenchmarkResults.htm" class="${(tab == 'FluencyReading Results')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Reading Results</a>
						   	<ul>
						      	<li><a href="studentBenchmarkResults.htm" class="${(page == 'FluencyReading Results')?'selectOnSubButton':'selectOffSubButton'}" >Fluency Reading Results</a></li>
						        <li><a href="studentAccuracyResults.htm" class="${(page == 'Accuracy Reading Results')?'selectOnSubButton':'selectOffSubButton'}" >Accuracy Reading Results</a></li>
					        </ul>
					   </li>
					   <li><a href="viewProgressMonitor.htm" class="${(tab == 'progressMonitoring')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Progress Monitoring </a></li> --%>
					   <li><a href="studentSelfGrading.htm" class="${(tab == 'benchmarkSelfReview' || tab == 'benchmarkPeerReview')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Grading </a>
					       <ul>
					            <li><a href="studentSelfGrading.htm" class="${(tab == 'benchmarkSelfReview')?'selectOnSubButton':'selectOffSubButton'}">Fluency Self Review </a>
					           <li><a href="studentPeerReview.htm" class="${(page == 'benchmarkPeerReview')?'selectOnSubButton':'selectOffSubButton'}">Fluency Peer Review </a></li>
					           <li><a href="studentAccuracyResults.htm" class="${(page == 'Accuracy Reading Results')?'selectOnSubButton':'selectOffSubButton'}" >Grade Accuracy </a></li>
					       </ul>
					   </li>
					  <%--  <li><a href="studentPeerReview.htm" class="${(tab == 'benchmarkPeerReview')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Fluency Peer Review </a></li> --%>

					   <li>
							<a href="goToWordworkPage.htm" class="${(tab == 'ViewWordWorkTests')?'buttonSelect tooLongTitle':'button rightPill tooLongTitle'}">Word work </a>
							<ul>
							<li><a href="goToWordworkPage.htm" class="${(tab == 'ViewWordWorkTests')?'selectOnSubButton':'selectOffSubButton'}">Access Word Work </a>
							<li><a href="goToStudentRFLPGradingPage.htm" class="${(page == 'rflpSelf')?'selectOnSubButton':'selectOffSubButton'}" >Self Assess Word Work </a></li>
							<li><a href="rflpPeerGrading.htm" class="${(page == 'rflpPeer')?'selectOnSubButton':'selectOffSubButton'}" >Peer Review Word Work </a></li>
						    </ul>
					   </li>
					   
					     <li>
							<a href="accuracySelfGrading.htm" class="${(tab == 'accuracySelfReview')?'buttonSelect tooLongTitle':'button rightPill tooLongTitle'}"> Grade Accuracy</a>
							<ul>
						      	<li><a href="accuracySelfGrading.htm" class="${(page == 'accuracySelfReview')?'selectOnSubButton3':'selectOffSubButton3'}" >Self</a></li>
						        <li><a href="accuracyPeerGrading.htm" class="${(page == 'accuracyPeerReview')?'selectOnSubButton3':'selectOffSubButton3'}" >Peer</a></li>
					        </ul>
						  </li>
					   
					</c:otherwise>
				</c:choose>	
			</ul>
			</nav>
		   </td>		
		</tr>
	</table>
	<c:if test="${tab == 'FluencyReading Results' || tab =='progressMonitoring' || tab == 'rti test results'}">
	 <table>
	<tr>
		<td>
			<select onchange="getStudentGradeClasses(this)" id="academicYear">
		  	<c:forEach items="${academicYears}" var="academicYear">
		  		<option value="${academicYear.academicYearId }" <c:if test="${academicYear.academicYearId  == selectedYearId}"> selected="Selected" </c:if>>${academicYear.academicYear}</option>
		  	</c:forEach>
		  	</select>
		  	<input type="hidden" id="academicTab" value="${tab}">
		  	<input type="hidden" id="academicUserType" value="${userReg.user.userType}">		  	
	  	</td>
	 	</tr>  	
	 </table>
  </c:if>
</body>
</html>