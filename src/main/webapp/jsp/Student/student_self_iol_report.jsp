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
</style>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/admin/le_rubric.js"></script>
<link href="resources/css/iol_report.css" rel="stylesheet"
	type="text/css" />
<script>

        function setColor(se){
        	   var td = document.getElementById('sub'+se);
        	   var colValue=document.getElementById("scoreId"+se).value;
        	   if(colValue==4)
            	   td.style.backgroundColor="#fee9ab";
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
     		$.ajax({  
         		url : "saveStudentNotes.htm",
         		type : "POST",
           		data: "learnIndiValueId=" + learnIndiValueId + "&studentNotes=" + encodeURIComponent(studentNotes),
         		success : function(data) { 
            	}  
        	});            	
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
        function submitReportCard()
        {
        	$("#loading-div-background").show();
        	console.debug("hi");
         	var learningIndicatorId = document.getElementById("learningIndicatorId").value;
		   	var i=0;
     		var notes=document.getElementsByName("notes");
     		var legends=document.getElementsByName("scoreId");
     		var flag=false;
              	for(i=0;i<legends.length;i++){
              		if (notes[i].value == '' || notes[i].value == null) {
              			alert("Please fill all the comment");
             	    	flag=true;
             	    	return false;
             	    }
              		if (legends[i].value == 'select' || legends[i].value == null) {
             	    	alert("Please select legend value for all the categories");
             	    	flag=true;
             	    	return false;
             	    }
              	}
         	if(!flag){
	        	$.ajax({  
	         		url : "SubmitIOLReportCard.htm",
	         		type : "POST",
	           		data: "learningIndicatorId=" + learningIndicatorId,
	         		success : function(data) { 
	         			$("#rioReportContainer").html("<br><br><table align='center'><tr><td><font color=blue>Report Created Successfully </font></td></tr></table>");
	         			$(window).scrollTop(0);
	         			$("#loading-div-background").hide();
	             		
	         		}  
	        	});
         	}
        }
       </script>
</head>
<body >
	<form name="reportCard">

		<table align="center" width="65%" style='font-family: "Open Sans", Arial, sans-serif;border-collapse: collapse;'>
			<tr>
				<td><input type="hidden" name="learningIndicatorId"
					id="learningIndicatorId" value="${learnIndValues[0].learningIndicator.learningIndicatorId}"> <img
					src="loadSchoolCommonFile.htm?schoolCommonFilePath=${schoolImage}"
					width="200" height="100">
				<td><label class="report-headers">21st Century Indicator
						of Learning</label><label class="report-contents">&nbsp;(formerly
						known as Report Card)</label> <br>
				<br> <label class="tabtxts">Student Name
						&nbsp;:&nbsp;&nbsp;</label><label class="report-contentss"><c:out
							value="${studentName}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><label
					class="tabtxts"> Teacher &nbsp;:&nbsp;&nbsp;</label><label
					class="report-contentss">
						${teacher.userRegistration.title}&nbsp;${teacher.userRegistration.firstName}&nbsp;${teacher.userRegistration.lastName}</label><br>
				<br> <label class="tabtxts"> School &nbsp;:&nbsp;&nbsp;</label><label
					class="report-contentss">${teacher.userRegistration.school.schoolName}</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label
					class="tabtxts">Grade&nbsp; :&nbsp;&nbsp;</label><label
					class="report-contentss"><c:out value="${gradeLevel}" /> </label></td>
			</tr>
		</table>
		<table align="center" border="0" class="" width="65%">
			<tr>
				<td class="" style="width: 150px; height: 30px;color: #0673A7;font-weight: bold;" align="left">Legend</td>
				<c:forEach items="${legends}" var="lg">
					<td style="width: 150px; height: 25px;" id="but0"><c:out
							value="${lg.legendName}"></c:out></td>
				</c:forEach>
			</tr>
			<tr>
				<td>
				
				</td>
				<c:forEach items="${legends}" var="lg">
					<td style="width: 150px; height: 25px"><input type="button"
						name="button"
						style="width:200px;height:25px;background-color: ${lg.color};"
						value="${lg.legendValue}" /> <input type="hidden" name="changes"
						id="changes" /> <input type="hidden" name="changeLegend"
						id="changeLegend" value="0" /></td>

				</c:forEach>

			</tr>

		</table>
		<table width="65%" align="center" border="1" class="dess" style="border-collapse:collapse;">
			<c:set var="m" value="0"></c:set>
			<c:set var="s" value="0"></c:set>
			<c:set var="se" value="0"></c:set>
			<c:set var="co" value="0"></c:set>
			<c:set var="livId" value="0"></c:set>
			<c:set var="cmtId" value="0"></c:set>
			<c:set var="colorId" value="0"></c:set>
			<c:set var="rows" value="0"></c:set>
			<c:set var="rowWidth" value="0"></c:set>
			<c:set var="rowheight" value="0"></c:set>
			<c:set var="z" value="0"></c:set>
			<c:set var="stat" value="upload"></c:set>
			<c:set var="tc" value="0"></c:set>
			<c:set var="ts" value="0"></c:set>
 			

			<c:forEach items="${listLegCriteria}" var="listlgcr">

				<c:set var="rows" value="${fn:length(listlgcr.legendSubCriteria)}" />
 			    <c:set var="rowWidth" value="${40/rows}"></c:set> 
<%-- 			    <c:set var="rowWidth"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${40/rows}" /></c:set> --%>
						

				<tr>
					<td width="65%"><div align="center" class="Divheads">
							<label onclick="goToDiv(0)" style="cursor: pointer;"> <c:out
									value="${listlgcr.legendCriteriaName}"></c:out></label>

							</div>
							
								<table border="1" class="dess">
									<tr>
									<c:forEach items="${listlgcr.legendSubCriteria}" var="lgs">
										<c:choose>
											<c:when test="${learnIndValues[colorId].teacherScore.legendId==4 }">
												<c:set var="legendColor" value="#f1c232"></c:set>
											</c:when>
											<c:when test="${learnIndValues[colorId].teacherScore.legendId==3}">
												<c:set var="legendColor" value="#0FC30C"></c:set>
											</c:when>
											<c:when test="${learnIndValues[colorId].teacherScore.legendId==2}">
												  <c:set var="legendColor" value="#ffff00"></c:set>
											</c:when>
											<c:when test="${learnIndValues[colorId].teacherScore.legendId==1}">
												 <c:set var="legendColor" value="#ff0000"></c:set>
											</c:when>
											<c:otherwise>
												<c:set var="legendColor" value="#ffffff"></c:set>
											</c:otherwise>
										</c:choose>
											<td style="text-align: center;" class="report-contents" id="sub${m}" width="${rowWidth}%" bgcolor ="${legendColor}"><a href="#" onClick="getRubricBySubcriteriaId(${lgs.legendSubCriteriaId},${gradeId},${m})"><c:out
													value="${lgs.legendSubCriteriaName}"></c:out></a><input
												type="hidden" name="legend" id="legend${m}" value="0" /></td>
											<c:set var="m" value="${m+1}"></c:set>
											<c:set var="legendColor" value=""></c:set>
											<c:set var="colorId" value="${colorId+1}"></c:set>
										</c:forEach>

									</tr>
									<tr>


										<c:forEach items="${listlgcr.legendSubCriteria}" var="lgs" varStatus="index">
											<td class="txtStyle" width="${rowWidth}%" align="center">
											<input
												type="hidden" name="subId" id="subId${se}"
												value="${lgs.legendSubCriteriaId}" /><input
												type="hidden" name="learnIndiValueId" id="learnIndiValueId${livId}"
												value="${learnIndValues[livId].learningValuesId}" /> 
												<select style="width: 100%" name="scoreId" id="scoreId${se}" class="listmenu" onchange="setColor(${se});saveStudentScore(${se})">
													<option value="select">Select Score</option>
													<c:forEach items="${legends}" var="lgsx">
														<c:if
															test="${learnIndValues[se].legendSubCriteria.legendSubCriteriaId eq lgs.legendSubCriteriaId && 
															learnIndValues[livId].legend.legendId eq lgsx.legendId &&
															learnIndValues[livId].legend.legendValue eq lgsx.legendValue}">
															<c:set var="selectString" value="selected"></c:set>
														</c:if>
														<option value="${lgsx.legendId}"
															${selectString}>${lgsx.legendValue}</option>
														<c:set var="selectString" value=""></c:set>
													</c:forEach>
											</select> <c:set var="se" value="${se+1}"></c:set>
											<c:set var="livId" value="${livId+1}"></c:set></td>

										</c:forEach>
									</tr>


									<tr>
										<c:forEach items="${listlgcr.legendSubCriteria}" var="lgs">
											<td class="txtStyle" width="${rowWidth}%"><input
												type="hidden" name="subId${s}" id="subId${s}"
												value="${lgs.legendSubCriteriaId}" /> 
												<textarea style="width:98%;border: none" name="notes" id="notes${s}" cols="15" rows="3" onblur="saveStudentNotes(${s})" required="required">${learnIndValues[cmtId].notes}</textarea> 
													<c:set var="s" value="${s+1}"></c:set>
													<c:set var="cmtId" value="${cmtId+1}"></c:set>
											</td>
										</c:forEach>
									</tr>
									<tr>
										<c:forEach items="${listlgcr.legendSubCriteria}" var="lgs">
											<td class="txtStyle" width="${rowWidth}%"><input
												type="hidden" name="subId${s}" id="subId${s}"
												value="${lgs.legendSubCriteriaId}" /> 
												 <div class="button_green" onClick="uploadFiles(${lgs.legendSubCriteriaId},${studentId},${learnIndValues[0].learningIndicator.learningIndicatorId},${z},'${upStatus}')">upload</div>
												<c:set var="z" value="${z+1}"></c:set> 
<%--  													<c:set var="cmtId" value="${cmtId+1}"></c:set>  --%>
											</td>
										</c:forEach>
									</tr>
									<tr>
									<c:forEach items="${listlgcr.legendSubCriteria}" var="lgs">
									 	<input type="hidden" name="subId${ts}" id="subId${ts}"
											value="${lgs.legendSubCriteriaId}" />
											<input type="hidden" name="learnIndiValueId" id="learnIndiValueId${ts}"
												value="${learnIndValues[ts].learningValuesId}" /> 
										<input type="hidden" name="learnValId${ts}"
											id="learnValId${ts}"
											value="${learnIndValues[ts].learningValuesId}" />
										<td style="text-align: center;" class="txtStyle"
											width="${rowWidth}%"><select style="width: 100%"
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
									</c:forEach>
								</tr>
							
								<tr>
									<c:forEach items="${listlgcr.legendSubCriteria}" var="lgs">
									 	<input type="hidden" name="subId${tc}" id="subId${tc}"
											value="${lgs.legendSubCriteriaId}" />
										<td style="text-align: center;" class="txtStyle"
											width="${rowWidth}%"><textarea style="width:98%;border: none"
												name="teacherNotes${tc}" id="teacherNotes${tc}" cols="15"
												rows="3" onblur="saveTeacherComment(${tc})" disabled>${learnIndValues[tc].teacherComment}</textarea></td>
										<c:set var="tc" value="${tc+1}"></c:set>
									</c:forEach>
								</tr>
								</table>
							</td>
				</tr>
				<tr>
					<td><c:set var="co" value="${co+1}"></c:set></td>
				</tr>
				<c:set var="rowWidth" value="0"></c:set>
				
			</c:forEach>
		</table>
		<table align="center">
			<tr>
				<td><input type="hidden" name="count" id="count" value="${m}" /></td>
			</tr>
			<tr>
				<td><input type="hidden" name="studentId" id="studentId"
					value="${studentId }" /> <input type="hidden" name="csId"
					id="csId" value="${csId}" /> <input type="button"
					class="button_green" id="btSubmitChanges" name="btSubmitChanges" height="52" width="50" value="Submit Changes"
					onclick="return submitReportCard()" />
				</td>
			</tr>
		</table>
		<div id="rubricDialog" align=center style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
	    <div id="LEFileDailog" title="LE Files" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
	</form>
</body>
</html>