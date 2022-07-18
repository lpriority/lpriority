<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page session="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript">
			function togglePage(thisVar, url, name){
				$.ajax({
					type : "GET",
					url : url+".htm",
					success : function(response) {
						$("#contentDiv").empty();
						$("#commonDetailsPage").empty();
						storeValue('ajaxPath', "/"+url+".htm");
						$("#contentDiv").append(response);
						$("#subTitle").html(name);
						$("title").html(name);
						
						//Update main tab class
						var current = document.getElementsByClassName("buttonSelect");
					    current[0].className = current[0].className.replace(" buttonSelect", " button");
					    var activeElement = $(thisVar);
					    activeElement.addClass('buttonSelect');
					    activeElement.removeClass('button');
					    
					  	//Updating Sub menu class 
					    var current = document.getElementsByClassName("selectOnSubButton");
					    if(current.length > 0){
					    	current[0].className = current[0].className.replace(" selectOnSubButton", " selectOffSubButton");
					    }	
					}
				});
			}
				
			function toggleInnerPage(thisVar, url, name){
				$.ajax({
					type : "GET",
					url : url+".htm",
					success : function(response) {
						$("#contentDiv").empty();
						$("#commonDetailsPage").empty();
						storeValue('ajaxPath', "/"+url+".htm");
						$("#contentDiv").append(response);
						$("#subTitle").html(name);
						$("title").html(name);
						
						//Update main tab class
						var current = document.getElementsByClassName("buttonSelect");
					    current[0].className = current[0].className.replace(" buttonSelect", " button");
					    var activeElement = $("#learningIndicator");
					    activeElement.addClass('buttonSelect');
					    activeElement.removeClass('button');
					    
					    //Updating Sub menu class 
					    var current = document.getElementsByClassName("selectOnSubButton");
					    if(current.length > 0){
					    	current[0].className = current[0].className.replace(" selectOnSubButton", " selectOffSubButton");
					    }			    
					    var activeSubElement = $("#"+url);
					    activeSubElement.addClass('selectOnSubButton');
					    activeSubElement.removeClass('selectOffSubButton');
					}
				});
			}
		</script>
		<title>Grades</title>
	</head>
	<body>	
	<div class="container-fluid mdboard">
		<div class="row">
			<div class="col-md-12 text-right">
				<nav id="primary_nav_wrap">
					<ul class="button-group">
						<li class="sline"><a href="#" onclick="togglePage(this, 'gradeBooks', 'Grades')" class="btn ${(tab == 'grades')?'buttonSelect leftPill tooLongTitle':'button leftPill tooLongTitle'}"> Grades </a></li>
						<li class="sline"><a href="#" onclick="togglePage(this, 'gradeBookUpdateAttendancePage', 'Attendance')" class="btn ${(tab == 'attendance')?'buttonSelect tooLongTitle':'button tooLongTitle'}"> Attendance </a></li>
						<li class="sline"><a href="#" onclick="togglePage(this, 'progressReports', 'Reports')" class="btn ${(tab == 'reports')?'buttonSelect tooLongTitle':'button tooLongTitle'}"> Reports </a></li>
						<li class="sline"><a href="#" onclick="togglePage(this, 'previousReports', 'Previous Reports')" class="btn ${(tab == 'previousReports')?'buttonSelect tooLongTitle':'button tooLongTitle'}"> Previous Reports </a></li>
						<li><a href="#" onclick="togglePage(this, 'itemAnalysisReport', 'Item Analysis Report')" class="btn ${(tab == 'itemAnalysisReport')?'buttonSelect tooLongTitle':'button tooLongTitle'}"> Item Analysis Report </a></li>
						<li class="sline"><a href="#" onclick="togglePage(this, 'compositeChart', 'Composite')" class="btn ${(tab == 'composite')?'buttonSelect tooLongTitle':'button tooLongTitle'}"> Composite </a></li>
						<li>
							<a href="#" id="learningIndicator" onclick="toggleInnerPage(this, 'RIO21stIOLReportCard', 'RIO 21st Century Learning Indicator')" class="btn ${(tab == 'iolReportCard')?'buttonSelect  tooLongTitle':'button tooLongTitle'}"> RIO 21st Century Learning Indicator</a>
							<ul>
								<li><a href="#" id="RIO21stIOLReportCard" onclick="toggleInnerPage(this, 'RIO21stIOLReportCard', 'RIO 21st Century Learning Indicator')" class="btn ${(page == 'iolReportCard')?'selectOnSubButton':'selectOffSubButton'}" >RIO 21st Century Learning <br>Indicator</a></li>
								<li><a href="#" id="teacherGoalSettingExcelDownload" onclick="toggleInnerPage(this, 'teacherGoalSettingExcelDownload', 'Goal Setting Excel Download')" class="btn ${(page == 'teacherGoalSettingExcelDownload')?'selectOnSubButton':'selectOffSubButton'}" >Goal Setting Excel <br>Download</a></li>
							</ul> 
						</li>
						<li class="sline"><a href="#" onclick="togglePage(this, 'ShowLPReportCard', 'Show Reports')" class="btn ${(tab =='showReports')?'buttonSelect rightPill tooLongTitle':'button rightPill tooLongTitle'}"> Show Reports</a></li>								
					</ul>
				</nav>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 sub-title title-border">			
				<c:if test="${tab == 'iolReportCard'}">
					<div id="title">RIO 21st Century Learning Indicator</div>				
				</c:if>
				<c:if test="${tab == 'itemAnalysisReport'}">
					<div id="title">Item Analysis Report</div>				
				</c:if>	
				<c:if test="${tab == 'grades'}">
					<div id="title">Grades</div>				
				</c:if>	
			</div>
		</div>		
	</div>		
	</body>
</html>