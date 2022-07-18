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
/* table, td, th {     */
/*     border: 1px solid #ddd; */
/*     text-align: left; */
/* } */
/* th, td { */
/*     padding: 10px; */
/* } */

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
<body>
	<form name="reportCard">
	
		<table width="65%" align="center" border="1" class="dess" style="border-collapse:collapse;background: #e9f2f3;">
		<tr class="Divheads">
		
 				<td class="" style="width: 150px; height: 30px;color: #0673A7;font-weight: bold;" align="left" >Legend</td> 
 				<c:forEach items="${legends}" var="lg"> 
 					<td style="width: 150px; height: 25px;" id="but0" align="center"><c:out 
 							value="${lg.legendName}"></c:out></td> 
 				</c:forEach> 
 				
 			</tr> 
 			<tr> 
 				<td class="" style="width: 150px; height: 30px;color: #0673A7;font-weight: bold;" align="left" ></td>
 				<c:forEach items="${legends}" var="lg"> 
 					<td style="width: 150px; height: 25px"><input type="button" 
 						name="button" 
 						style="width:200px;height:25px;background-color: ${lg.color};" 
 						value="${lg.legendValue}" /> <input type="hidden" name="changes" 
 						id="changes" /> <input type="hidden" name="changeLegend" 
 						id="changeLegend" value="0" /></td> 

 				</c:forEach> 

 			</tr> 
 		
			<c:set var="m" value="0"></c:set>
             <tr>
					<td width="100%" colspan="5">
							
								<table align="center">
								<tr><td>&nbsp;</td></tr>
								<tr height="50">
								<td class="tabtxt" width="100%" colspan="2" height="20">Legend Sub Criteria</td>
								<td width="150%" height="20"> :</td>	
								<c:choose>
								<c:when test="${upStatus=='show'}">
								<c:set var="fileStat" value="Show Files" />
								</c:when>
								<c:otherwise>
								<c:set var="fileStat" value="Upload" />
								</c:otherwise>
								</c:choose>
										<c:choose>
											<c:when test="${learnIndValues.legend.legendId==4 }">
												<c:set var="legendColor" value="#f3ce5a"></c:set>
											</c:when>
											<c:when test="${learnIndValues.legend.legendId==3}">
												<c:set var="legendColor" value="#83db81"></c:set>
											</c:when>
											<c:when test="${learnIndValues.legend.legendId==2}">
												  <c:set var="legendColor" value="#ffff8c"></c:set>
											</c:when>
											<c:when test="${learnIndValues.legend.legendId==1}">
												 <c:set var="legendColor" value="#fc7171"></c:set>
											</c:when>
											<c:otherwise>
												<c:set var="legendColor" value="#ffffff"></c:set>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${learnIndValues.teacherScore.legendId==4 }">
												<c:set var="teacherColor" value="#f3ce5a"></c:set>
											</c:when>
											<c:when test="${learnIndValues.teacherScore.legendId==3}">
												<c:set var="teacherColor" value="#83db81"></c:set>
											</c:when>
											<c:when test="${learnIndValues.teacherScore.legendId==2}">
												  <c:set var="teacherColor" value="#ffff8c"></c:set>
											</c:when>
											<c:when test="${learnIndValues.teacherScore.legendId==1}">
												 <c:set var="teacherColor" value="#fc7171"></c:set>
											</c:when>
											<c:otherwise>
												<c:set var="teacherColor" value="#ffffff"></c:set>
											</c:otherwise>
										</c:choose>
										<c:choose>
										<c:when test="${learnIndValues.submitStatus==false or learnIndValues.submitStatus==true}">
										<c:set var="editStatus" value="disabled" />
										</c:when>
									    <c:otherwise>
                                         <c:set var="editStatus" value="enabled" />
										</c:otherwise>
										</c:choose>
											<td height="20" style="text-align: center;" class="report-contents" id="sub${m}" width="50%" bgcolor ="${legendColor}" colspan="2"><a href="#" onClick="getRubricBySubcriteriaId(${learnIndValues.legendSubCriteria.legendSubCriteriaId},${gradeId},${m},'${editStatus}','student',${trimesterId},${teacherRegId})">
							               <c:out value="${learnIndValues.legendSubCriteria.legendSubCriteriaName}">${upStatus}</c:out></a>
											
									</td></tr>
									<tr height="60">
									<td class="tabtxt" colspan="2" width="100%" height="30">Student Score</td>
									<td width="20%" height="30"> :</td>	
									<td class="txtStyle" width="150%" height="50%" align="center" colspan="2">

											<input type="hidden" name="subId" id="subId${m}"
												value="${learnIndValues.legendSubCriteria.legendSubCriteriaId}" /><input
												type="hidden" name="learnIndiValueId" id="learnIndiValueId${m}" value="${learnIndValues.learningValuesId}" /> 
												<select style="width: 150%" name="scoreId" id="scoreId${m}" class="listmenu" onchange="setColor(${m});saveStudentScore(${m})" ${editStatus}>
													<option value="select">Select Score</option>
													<c:forEach items="${legends}" var="lgsx">
														<c:if
															test="${learnIndValues.legend.legendId eq lgsx.legendId &&
															learnIndValues.legend.legendValue eq lgsx.legendValue}">
															<c:set var="selectString" value="selected"></c:set>
														</c:if>
														<option value="${lgsx.legendId}"
															${selectString}>${lgsx.legendValue}</option>
														<c:set var="selectString" value=""></c:set>
													</c:forEach>
											</select>
											
							
									</tr>
     								<tr height="60">
										<td class="tabtxt" colspan="2" width="100%" height="20">Student Comment</td>
										<td width="20%" height="20"> :</td>	
											<td class="txtStyle" width="50%" colspan="2" height="20">
												<textarea style="width:150%" name="notes" id="notes${m}" cols="15" rows="3" onblur="saveStudentNotes(${m})" required="required" ${editStatus}>${learnIndValues.notes}</textarea> 
													
													
											</td>
										
									</tr>
									<tr height="60"> <td class="tabtxt" colspan="2" width="100%" height="20">Upload Files</td>
									<td width="20%" height="20"> :</td>	
										<td class="txtStyle" width="${rowWidth}%" colspan="2" height="20">
				     				<input type="hidden" name="subId${m}" id="subId${m}" value="${lgs.legendSubCriteriaId}" /> 
												 <div class="button_green" style="width:50%" onClick="uploadFiles(${learnIndValues.legendSubCriteria.legendSubCriteriaId},${studentId},${learningIndicatorId},${m},'${upStatus}')">${fileStat}</div>
			                         	</td>
							
									</tr>
									<c:if test="${learnIndValues.submitStatus==true}">
									<tr height="50">
									<td class="tabtxt" colspan="2" width="100%" height="20">Teacher Score</td>
									<td width="20%" height="20"> :</td>	
					                 <td style="text-align:center" class="txtStyle" width="150%" colspan="2" height="20">
									<select style="width: 150%;background-color:${teacherColor}" name="teacherScoreId${m}" id="teacherScoreId${m}" class="listmenu" disabled>
									<option value="select">Select Score</option>
									<c:forEach items="${legends}" var="lgsx">
										<c:if test="${learnIndValues.teacherScore.legendId == lgsx.legendId}">
										<c:set var="selected" value="selected">
										</c:set>
										</c:if>
										<option style="text-align: center" value="${lgsx.legendId}"
														${selected}>${lgsx.legendValue}</option>
													<c:set var="selected" value=""></c:set>
												</c:forEach>
												
										</select></td>
																
								</tr>
								<tr height="50">
								<td class="tabtxt" colspan="2" width="100%" height="20">Teacher Comment</td>
								<td width="50%" height="20"> :</td>	
									<td style="text-align: center;" class="txtStyle" width="50%" colspan="2" height="20"><textarea style="width:150%"
												name="teacherNotes${m}" id="teacherNotes${m}" cols="15"
												rows="3" onblur="saveTeacherComment(${m})" readonly>${learnIndValues.teacherComment}</textarea></td>
										
								
								</tr>
								</c:if>
								<tr><td>&nbsp;</td></tr>
								</table>
							</td>
				</tr>
				
				
				
		
		</table>
		<c:if test="${empty learnIndValues.submitStatus}">
		<table align="center">
			<tr>
				<td><input type="hidden" name="count" id="count" value="${m}" /></td>
			</tr>
			<tr>
				<td><input type="hidden" name="studentId" id="studentId"
					value="${studentId }" /> 
<%-- 					<input type="hidden" name="csId" id="csId" value="${csId}" />  --%>
					<div class="button_green" id="btSubmitChanges" name="btSubmitChanges" height="52" width="50" 
					onclick="submitStudentIOLLitracy(${learnIndValues.learningValuesId},${learningIndicatorId},${m})">Submit</div>
				</td>
			</tr>
		</table>
		</c:if>
	</form>
<div id="rubricDialog" align=center style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
	    <div id="LEFileDailog" title="LE Files" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>

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