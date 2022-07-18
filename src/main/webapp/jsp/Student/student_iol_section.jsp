<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>
<style>
table.one {
	border-style: solid;
	border-color: #7b8e86;
}
.submit_button{
	-moz-box-shadow: 0px 10px 14px -7px #276873;
	-webkit-box-shadow: 0px 10px 14px -7px #107181;
	box-shadow: 0px 10px 14px -7px #276873;
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #599bb3), color-stop(1, #408c99));
	background:-moz-linear-gradient(top, #599bb3 5%, #408c99 100%);
	background:-webkit-linear-gradient(top, #599bb3 5%, #408c99 100%);
	background:-o-linear-gradient(top, #599bb3 5%, #408c99 100%);
	background:-ms-linear-gradient(top, #599bb3 5%, #408c99 100%);
	background: linear-gradient(to bottom,rgb(255, 255, 255) 1%, rgb(255, 255, 255) 48%, rgb(188, 190, 191) 97%, rgb(146, 146, 146) 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#599bb3', endColorstr='#408c99',GradientType=0);
	background-color:#599bb3;
	-moz-border-radius:8px;
	-webkit-border-radius:8px;
	border-radius:8px;
	display:inline-block;
	cursor:pointer;
	color: black;
	font-family:Arial;
	font-size:13px;
	font-weight:bold;
	padding:12px 29px;
	text-decoration:none;
	text-shadow: 0 1px 2px rgb(183, 183, 183);
	/* transition:all .1s cubic-bezier(0, 0, 0, 1.35);
  	-webkit-filter: blur(0); */
	-webkit-transition: all .2s ease-out .1s;
	-moz-transition: all .2s ease-out;
	-o-transition: all .2s ease-out .1s;
	transition:all .1s cubic-bezier(0, 0, 0, 1.35);
	-webkit-filter: blur(0);
}
.header-content{
border-color:#D3D3D3;font-family: Roboto,-apple-system,BlinkMacSystemFont,"Helvetica Neue","Segoe UI","Oxygen","Ubuntu","Cantarell","Open Sans",sans-serif;font-weight: 500;border-collapse:collapse;
}
</style>
<!-- <script src="resources/javascript/jquery/jquery-2.1.4.js"></script> -->
<%@ include file="../CommonJsp/include_resources.jsp" %>
<script type="text/javascript" src="resources/javascript/admin/le_rubric.js"></script>
<link href="resources/css/iol_report.css" rel="stylesheet"
	type="text/css" />
<script>

        function setColor(se){
        	   var td = document.getElementById('sub'+se);
        	   var colValue=document.getElementById("scoreId"+se).value;
        	   if(colValue==4)
            	   td.style.backgroundColor="#f3ce5a";
            	  else if(colValue==3)
            		  td.style.backgroundColor="#83db81";
            	  else if(colValue==2)
            		  td.style.backgroundColor="#ffff8c";  
            	  else if(colValue==1)
             		  td.style.backgroundColor="#fc7171";   
             	  else 
             		 td.style.backgroundColor="#FFFFFF";
        	}
        function goToDiv(i){
      	  document.getElementById("but"+i).scrollIntoView(true);
      	}
        
        function saveStudentNotes(ts)
        {       
        	var learnIndiValueId = document.getElementById('learnIndiValueId'+ts).value;
       		var studentNotes=document.getElementById("notes"+ts).value; 
       		var le=studentNotes.length;
       		if(le<=1000){
     		$.ajax({  
         		url : "saveStudentNotes.htm",
         		type : "POST",
           		data: "learnIndiValueId=" + learnIndiValueId + "&studentNotes=" + encodeURIComponent(studentNotes),
         		success : function(data) { 
            	}  
        	}); 
       		}else{
       			systemMessage('The field cannot contain more than 1000 characters!');
      			document.getElementById("notes"+ts).focus();
     	    	flag=true;
     	    	return false;
       		}
     	}       
        
        function saveStudentScore(ts)
        {       
        	var learnIndiValueId = document.getElementById("learnIndiValueId"+ts).value;
        	var legend=document.getElementById("scoreId"+ts).value;  
       		$.ajax({  
       			url : "saveStudentSelfScore.htm",
       			type : "POST",
         			data: "learnIndiValueId=" + learnIndiValueId + "&legend=" + legend ,
       			success : function(data) { 
           		}  
       		}); 
     	}
        
       </script>
</head>
<body >
	<form name="reportCard">

<!-- 		<table align="center" width="65%" style='font-family: "Open Sans", Arial, sans-serif;border-collapse: collapse;'> -->
<!-- 			<tr> -->
<!-- 				<td><input type="hidden" name="learningIndicatorId" -->
<%-- 					id="learningIndicatorId" value="${learnIndValues[0].learningIndicator.learningIndicatorId}"> <img --%>
<%-- 					src="loadSchoolCommonFile.htm?schoolCommonFilePath=${schoolImage}" --%>
<!-- 					width="200" height="100"> -->
<!-- 				<td><label class="report-headers">21st Century Indicator -->
<!-- 						of Learning</label><label class="report-contents">&nbsp;(formerly -->
<!-- 						known as Report Card)</label> <br> -->
<!-- 				<br> <label class="tabtxts">Student Name -->
<%-- 						&nbsp;:&nbsp;&nbsp;</label><label class="report-contentss"><c:out --%>
<%-- 							value="${studentName}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><label --%>
<!-- 					class="tabtxts"> Teacher &nbsp;:&nbsp;&nbsp;</label><label -->
<!-- 					class="report-contentss"> -->
<%-- 						${teacher.userRegistration.title}&nbsp;${teacher.userRegistration.firstName}&nbsp;${teacher.userRegistration.lastName}</label><br> --%>
<!-- 				<br> <label class="tabtxts"> School &nbsp;:&nbsp;&nbsp;</label><label -->
<%-- 					class="report-contentss">${teacher.userRegistration.school.schoolName}</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; --%>
<!-- 					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label -->
<!-- 					class="tabtxts">Grade&nbsp; :&nbsp;&nbsp;</label><label -->
<%-- 					class="report-contentss"><c:out value="${gradeLevel}" /> </label></td> --%>
<!-- 			</tr> -->
<!-- 		</table> -->
      <table align="center" border="1" class="header-content" width="75%" style=""> 
 			<tr> 
 				<td class="" style="width: 150px; height: 30px;color: #0673A7;font-weight: bold;" align="left">Legend</td> 
 				<c:forEach items="${legends}" var="lg"> 
 					<td style="width: 150px; height: 25px;" id="but0" align="center"><c:out 
 							value="${lg.legendName}"></c:out></td> 
 				</c:forEach> 
 			</tr> 
 			<tr> 
 				<td> 
			
 				</td> 
 				<c:forEach items="${legends}" var="lg"> 
 					<td style="width: 150px; height: 25px" align="center"><input type="button" 
 						name="button" 
 						style="width:200px;height:25px;background-color: ${lg.color};" 
 						value="${lg.legendValue}" /> <input type="hidden" name="changes" 
 						id="changes" /> <input type="hidden" name="changeLegend" 
 						id="changeLegend" value="0" /></td> 

 				</c:forEach> 

 			</tr> 

 		</table>
		<table width="75%" align="center" border="1" class="dess" style="border-collapse:collapse;">
			<c:set var="m" value="0"></c:set>
			<c:set var="s" value="0"></c:set>
			<c:set var="se" value="0"></c:set>
			<c:set var="co" value="0"></c:set>
			<c:set var="livId" value="0"></c:set>
			<c:set var="cmtId" value="0"></c:set>
			<c:set var="colorId" value="0"></c:set>
			<c:set var="colId" value="0"></c:set>
			<c:set var="rows" value="0"></c:set>
			<c:set var="rowWidth" value="0"></c:set>
			<c:set var="rowheight" value="0"></c:set>
			<c:set var="z" value="0"></c:set>
			<c:set var="stat" value="upload"></c:set>
			<c:set var="tc" value="0"></c:set>
			<c:set var="ts" value="0"></c:set>
 				<c:choose>
								<c:when test="${upStatus=='show'}">
								<c:set var="fileStat" value="Show Files" />
								</c:when>
								<c:otherwise>
								<c:set var="fileStat" value="Upload" />
								</c:otherwise>
								</c:choose>

			

				<c:set var="rows" value="${fn:length(legendSubCriterias)}" />
 			    <c:set var="rowWidth" value="${40/rows}"></c:set> 
<%-- 			    <c:set var="rowWidth"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${40/rows}" /></c:set> --%>
						

				<tr>
					<td width="65%" align="center"><div align="center" class="Divheads" >
					<input type="hidden" name="learningIndicatorId" id="learningIndicatorId" value="${learningIndicatorId}">
					<input type="hidden" name="legendCriteriaId" id="legendCriteriaId" value="${legendCriteriaId}">		
							<label onclick="goToDiv(0)" style="cursor: pointer;"> <c:out
									value="${legendCriteriaName}"></c:out></label>

							</div>
							
								<table border="1" class="dess">
									<tr>
									<c:forEach items="${legendSubCriterias}" var="lgs">
										<c:choose>
											<c:when test="${learnIndValues[colorId].legend.legendId==4 }">
												<c:set var="legendColor" value="#f3ce5a"></c:set>
											</c:when>
											<c:when test="${learnIndValues[colorId].legend.legendId==3}">
												<c:set var="legendColor" value="#83db81"></c:set>
											</c:when>
											<c:when test="${learnIndValues[colorId].legend.legendId==2}">
												  <c:set var="legendColor" value="#ffff8c"></c:set>
											</c:when>
											<c:when test="${learnIndValues[colorId].legend.legendId==1}">
												 <c:set var="legendColor" value="#fc7171"></c:set>
											</c:when>
											<c:otherwise>
												<c:set var="legendColor" value="#ffffff"></c:set>
											</c:otherwise>
										</c:choose>
										<c:choose>
										<c:when test="${secStatus==false or secStatus==true}">
										<c:set var="editStatus" value="disabled" />
										</c:when>
									    <c:otherwise>
                                         <c:set var="editStatus" value="enabled" />
										</c:otherwise>
										</c:choose>
										
										<td style="text-align: center;" class="report-contents" id="sub${m}" width="${rowWidth}%" bgcolor ="${legendColor}"><a href="#" onClick="getRubricBySubcriteriaId(${lgs.legendSubCriteriaId},${gradeId},${m},'${editStatus}','student',${trimesterId},${teacherRegId})"><c:out
													value="${lgs.legendSubCriteriaName}"></c:out></a><input
												type="hidden" name="legend" id="legend${m}" value="0" /></td>
											<c:set var="m" value="${m+1}"></c:set>
											<c:set var="legendColor" value=""></c:set>
											<c:set var="colorId" value="${colorId+1}"></c:set>
										</c:forEach>

									</tr>
									<tr>


										<c:forEach items="${legendSubCriterias}" var="lgs" varStatus="index">
											<td class="txtStyle" width="${rowWidth}%" align="center">
											<input
												type="hidden" name="subId" id="subId${se}"
												value="${lgs.legendSubCriteriaId}" /><input
												type="hidden" name="learnIndiValueId" id="learnIndiValueId${livId}"
												value="${learnIndValues[livId].learningValuesId}" /> 
												<select style="width: 100%" name="scoreId" id="scoreId${se}" class="listmenu" onchange="setColor(${se});saveStudentScore(${se})" ${editStatus}>
													<option value="select">Select Score</option>
													<c:forEach items="${legends}" var="lgsx">
														<c:if
															test="${learnIndValues[se].legend.legendId eq lgsx.legendId}">
															<c:set var="selectString" value="selected"></c:set>
														</c:if>
														<option value="${lgsx.legendId}" ${selectString}>${lgsx.legendValue}</option>
														<c:set var="selectString" value=""></c:set>
													</c:forEach>
											</select> <c:set var="se" value="${se+1}"></c:set>
											<c:set var="livId" value="${livId+1}"></c:set></td>

										</c:forEach>
									</tr>


									<tr>
										<c:forEach items="${legendSubCriterias}" var="lgs">
											<td class="txtStyle" width="${rowWidth}%"><input
												type="hidden" name="subId${s}" id="subId${s}"
												value="${lgs.legendSubCriteriaId}" /> 
												<textarea style="width:98%;border: none" name="notes" id="notes${s}" cols="15" rows="3" onblur="saveStudentNotes(${s})" required="required" ${editStatus}>${learnIndValues[cmtId].notes}</textarea> 
													<c:set var="s" value="${s+1}"></c:set>
													<c:set var="cmtId" value="${cmtId+1}"></c:set>
											</td>
										</c:forEach>
									</tr>
									<tr>
										<c:forEach items="${legendSubCriterias}" var="lgs">
											<td class="txtStyle" width="${rowWidth}%" align="center">
											<input type="hidden" name="subId${s}" id="subId${s}" value="${lgs.legendSubCriteriaId}" /> 
							     		 <div class="button_green" style="width:60%" onClick="uploadFiles(${lgs.legendSubCriteriaId},${studentId},${learningIndicatorId},${z},'${upStatus}')">${fileStat}</div>
												<c:set var="z" value="${z+1}"></c:set> 
<%--  													<c:set var="cmtId" value="${cmtId+1}"></c:set>  --%>
											</td>
										</c:forEach>
									</tr>
									<c:if test="${secStatus==true}">
									<tr>
									<c:forEach items="${legendSubCriterias}" var="lgs">
									<c:choose>
											<c:when test="${learnIndValues[colId].teacherScore.legendId==4 }">
												<c:set var="teacherColor" value="#f3ce5a"></c:set>
											</c:when>
											<c:when test="${learnIndValues[colId].teacherScore.legendId==3}">
												<c:set var="teacherColor" value="#83db81"></c:set>
											</c:when>
											<c:when test="${learnIndValues[colId].teacherScore.legendId==2}">
												  <c:set var="teacherColor" value="#ffff8c"></c:set>
											</c:when>
											<c:when test="${learnIndValues[colId].teacherScore.legendId==1}">
												 <c:set var="teacherColor" value="#fc7171"></c:set>
											</c:when>
											<c:otherwise>
												<c:set var="teacherColor" value="#ffffff"></c:set>
											</c:otherwise>
									</c:choose>	
									 <input type="hidden" name="subId${ts}" id="subId${ts}" value="${lgs.legendSubCriteriaId}" />
									 <input type="hidden" name="learnIndiValueId" id="learnIndiValueId${ts}" value="${learnIndValues[ts].learningValuesId}" /> 
										<input type="hidden" name="learnValId${ts}" id="learnValId${ts}" value="${learnIndValues[ts].learningValuesId}" />
										<td style="text-align: center;" class="txtStyle" width="${rowWidth}%"><select style="width: 100%;background-color:${teacherColor}"
											name="scoreId${ts}" id="scoreId${ts}" class="listmenu" disabled>
											
												<option value="select">Select Score</option>
												
												<c:forEach items="${legends}" var="lgsx">
													<c:if
														test="${learnIndValues[ts].teacherScore.legendId == lgsx.legendId}">
														<c:set var="selected" value="selected">
														</c:set>
													</c:if>
													<option style="text-align: center" value="${lgsx.legendId}"
														${selected}>${lgsx.legendValue}</option>
													<c:set var="selected" value=""></c:set>
												</c:forEach>
												
										</select></td>
										<c:set var="ts" value="${ts+1}"></c:set>
										<c:set var="colId" value="${colId+1}"></c:set>
									</c:forEach>
								</tr>
							
								<tr>
									<c:forEach items="${legendSubCriterias}" var="lgs">
									 	<input type="hidden" name="subId${tc}" id="subId${tc}"
											value="${lgs.legendSubCriteriaId}" />
										<td style="text-align: center;" class="txtStyle"
											width="${rowWidth}%">
									<textarea style="width:98%;border: none" name="teacherNotes${tc}" id="teacherNotes${tc}" cols="15" rows="3" onblur="saveTeacherComment(${tc})" disabled>${learnIndValues[tc].teacherComment}</textarea></td>
										<c:set var="tc" value="${tc+1}"></c:set>
									</c:forEach>
								</tr>
								</c:if>
								</table>
							</td>
				</tr>
				<tr>
					<td><c:set var="co" value="${co+1}"></c:set></td>
				</tr>
				<c:set var="rowWidth" value="0"></c:set>
				
		
		</table>
		<c:if test="${empty secStatus}">
		<table align="center">
			<tr>
				<td><input type="hidden" name="count" id="count" value="${m}" /></td>
			</tr>
			<tr>
				<td><input type="hidden" name="studentId" id="studentId"
					value="${studentId }" /> <input type="hidden" name="csId"
					id="csId" value="${csId}" /> <div
					class="submit_button" id="btSubmitChanges" name="btSubmitChanges" height="52" width="50" 
					onclick="return submitStudentIOLSection(${learningIndicatorId})">Submit</div>${secStatus}
				</td>
			</tr>
		</table>
		</c:if>
		<div id="rubricDialog" align=center style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
	    <div id="LEFileDailog" title="LE Files" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
	</form>
	<div id="loading-div-background2" class="loading-div-background"
	style="display: none;">
	<div class="loader"></div>
	<div id="loading-div" class="ui-corner-all loading-div"
		style="color: #103c51; left: 50%; padding-left: 12px; font-size: 17px; margin-top: -8.5em;">
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>Loading....
	</div>
</div>
</body>
</html>