<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/curriculum.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<%@ include file="../CommonJsp/include_resources.jsp" %>
 <table><tr><td>
    <form:form id="questionsForm"  action="createAssessments.htm" modelAttribute="questionsList" method="post" enctype="multipart/form-data">
		<input type="hidden" id="usedFor" name="usedFor" value="${usedFor}">
		<input type="hidden" id="tab" name="tab" value="${tab}">
		<input type="hidden" id="lessonId" name="lessonId" value="${lessonId}">
		<input type="hidden" id="assignmentTypeId" name="assignmentTypeId" value="${assignmentTypeId}">
		<input type="hidden" id="assignmentId" name="assignmentId" value="">
		<input type="hidden" id="mode" name="mode" value="${mode}">
	    <input type="hidden" name="noOfFiles" id="noOfFiles" />
	    <input type="hidden" id="questionId" name="questionId" value="${questionId}">
	    <input type="hidden" id="id" name ="id" value="" />
	    <form:hidden path="questions[0].questionId" id="question" name="question" value="${questionId}"/>
	    <input type="hidden" id="gradeId" name="gradeId" value="${gradeId}">
	<table with="100%"> <tr><td>
		<table with="80%" align="center">
	    <tr height="30" align="left">
			<td width="30%" align="right">
				<span class="tabtxt">PerformanceTask Name :	</span>		
			</td>
			<td width="562" align="left" style="padding-left: 1em;">
				<form:textarea path="questions[0].ptName" id="question0" required="required"  cols="60" rows="2" onkeyup="enforceMaxLength(this,'100')" onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}"/>
			</td>
		</tr>
		<tr height="30" align="left">
			<td width="562" align="right">
				<span class="tabtxt">Subject Area :</span>
			</td>
			<td width="562" align="left" style="padding-left: 1em;">
				<form:textarea path="questions[0].ptSubjectArea" id="ptSubjectArea" required="required" cols="60" rows="2"   onkeyup="enforceMaxLength(this,'100')" onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}"/>
			</td>
		</tr>
		<tr height="30" align="left">
			<td width="562" align="right">
				<span class="tabtxt">	Directions/Prompt :</span>
			</td>
			<td width="562" align="left" style="padding-left: 1em;">
				<form:textarea path="questions[0].ptDirections" id="ptDirections" required="required" cols="60" rows="2"   onkeyup="enforceMaxLength(this,'500')" onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}" />
			</td>
		</tr>
		 <c:choose>
		  <c:when test="${mode eq 'Create'}">  
		    <tr height="30" align="left">
				<td width="562" align="right">
					<span class="tabtxt"> Choose Multiple files :</span>
				</td>
				<td width="562" align="left" style="padding-left: 1em;padding-top:1em;">
					<input type="file" id="file" name="files" value="Upload"  onchange="filesCount('file','noOfFiles')" multiple/><br><br> 
				</td>
			</tr>
		   </c:when>
		   <c:when test="${mode eq 'Edit'}">
		   <tr height="30">
				<td width="562" align="right">
						<span class="tabtxt"> Uploaded files :</span>
				</td>
				<td width="562" align="left" style="padding-left: 1em;" style="text-wrap:normal;">
				    <div id="filesDiv">
						<c:choose>
							<c:when test="${fn:length(questionsList.questions[0].ptaskFiles)>0}">
							   <c:set var="index" value="0" />
								<c:forEach var="ptFiles" begin="0" end="${fn:length(questionsList.questions[0].ptaskFiles)-1}" varStatus="status">	
								<c:if test="${questionsList.questions[0].ptaskFiles[ptFiles].filename ne ''}">
								    <c:set var="index" value="${index+1}" />
									<lable id="file${questionsList.questions[0].ptaskFiles[ptFiles].fileId}" >			
										&nbsp;&nbsp;${index}.&nbsp;<a href="assessmentsFileDownload.htm?questionId=${questionsList.questions[0].questionId}&fileName=${questionsList.questions[0].ptaskFiles[ptFiles].filename}&type=Performance_Tests">${questionsList.questions[0].ptaskFiles[ptFiles].filename}</a>&nbsp;&nbsp;
									 <i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;" onclick="deletePerformancefile(${questionsList.questions[0].ptaskFiles[ptFiles].fileId},${questionsList.questions[0].questionId})"></i>
									</lable>
								</c:if>	
								</c:forEach>
							</c:when>
							<c:otherwise>
								<lable><b>No Files</b></lable>
							</c:otherwise>
						</c:choose>
					</div>
				</td>
				
			</tr> 
			<tr>
			    <td width="562" align="right">
					<span class="tabtxt"> Choose Multiple files :</span>
				</td>
				<td width="562" align="left" style="padding-left: 1em;padding-top:1em;">
					<input type="file" id="file" name="files" value="Upload"  onchange="filesCount('file','noOfFiles'),updateQuestion(0)" multiple/><br><br> 
				</td>
			</tr>
		   </c:when>
		 </c:choose> 
		<tr height="30" align="left">
			<td width="562" align="right">
				<span class="tabtxt"> Select Type Of Rubric : </span>
			</td>
			<td width="562" align="left" style="padding-left: 1em;">
				<form:select path="questions[0].rubrics[0].rubricTypes.rubricTypeId" required="required" 
					id="rubricTypeId" onchange="">
					<option value="">Select Option</option>
						<c:forEach items="${rubricType}" var="rt">
				        	<option value="${rt.rubricTypeId}" ${rt.rubricTypeId == questionsList.questions[0].rubrics[0].rubricTypes.rubricTypeId ? 'selected="selected"' : ''}>${rt.rubricType}</option>
						</c:forEach>
				</form:select>			
			</td>			
		</tr>
		<tr height="30" align="left">
			<td width="562" align="right">
				<span class="tabtxt"> Scoring Rubric :</span>
			</td>
			<td width="562" align="left" style="padding-left: 1em;">
				<form:select path="questions[0].rubrics[0].rubricScalings.rubricScalingId" required="required" id="rubricScalingId" onchange="showRubric(0)">
					<option value="">Select Option</option>
					<c:forEach items="${rubricScalings}" var="rs">
				        	<option value="${rs.rubricScalingId}" ${rs.rubricScalingId == questionsList.questions[0].rubrics[0].rubricScalings.rubricScalingId ? 'selected="selected"' : ''}>${rs.rubricScaling}</option>
					</c:forEach>		
				</form:select>	
			</td>
		</tr>
		</table></td></tr>	
		<tr height="30" align="left">
		<c:choose>
		  <c:when test="${mode eq 'Create'}">  
			<td width="562" colspan="2" align="center">
				<div align="center" id="rubric" style="overflow-y: auto ;border-style: groove;border-width: 1px;overflow-x: hidden ;max-height: 260px;min-height: 260px; width: 90%;" onblur=""></div>
			</td>
			</c:when>
		 <c:when test="${mode eq 'Edit'}">
			<td width="562" colspan="2" align="center">
				<div align="center" id="rubric" style="overflow-y: auto ;border-style: groove;border-width: 1px;overflow-x: hidden ;max-height: 260px;min-height: 260px; width: 90%;">
				<table border="1" align="center" width="20%">
							<c:if test="${fn:length(questionsList.questions[0].rubrics)>0}">
								<c:forEach var="rubricPos" begin="0" end="${fn:length(questionsList.questions[0].rubrics)-1}">
									<tr>
										<td width="1%">
											<form:input path="questions[0].rubrics[${rubricPos}].score" id="score${rubricPos}" 
												required="required" size="2" style="border:none; background-color:transparent; text-align:center;"  readonly="true"/>
										</td>
									<c:set var="rubType" value="${questionsList.questions[0].rubrics[rubricPos].rubricTypes.rubricTypeId}"></c:set>
									<c:choose>
		 							 <c:when test="${rubricPos eq 0}">  	
										<td>
											<form:input path="questions[0].rubrics[${rubricPos}].dimension1" id="dimension1${rubricPos}"  onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}"
												required="required" cols="25" rows="5" style="border:none;text-align:center;"  />	
											<form:hidden path="questions[0].rubrics[${rubricPos}].rubricId"/>
											<form:hidden path="questions[0].rubrics[${rubricPos}].questions.questionId"/>
											<form:hidden path="questions[0].rubrics[${rubricPos}].createdBy"/>
											<form:hidden path="questions[0].rubrics[${rubricPos}].rubricTypes.rubricTypeId"/>
											<form:hidden path="questions[0].rubrics[${rubricPos}].rubricScalings.rubricScalingId"/>
										</td>
										<c:if test="${rubType == 3 || rubType == 4}">
											<td>
												<form:input path="questions[0].rubrics[${rubricPos}].dimension2" 
													id="dimension2${rubricPos}" required="required" cols="25" rows="5" style="border:none;text-align:center;"  onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}"/>
											</td>
											<td>
												<form:input path="questions[0].rubrics[${rubricPos}].dimension3" 
													id="dimension3${rubricPos}" required="required" cols="25" rows="5" style="border:none;text-align:center;"  onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}"/>
											</td>
											<td>
												<form:input path="questions[0].rubrics[${rubricPos}].dimension4" 
													id="dimension4${rubricPos}" required="required" cols="25" rows="5" style="border:none;text-align:center;"  onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}"/>
											</td>			
										</c:if>	
										</c:when>
										<c:otherwise>
										<td>
											<form:textarea path="questions[0].rubrics[${rubricPos}].dimension1" id="dimension1${rubricPos}"  onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}"
												required="required" cols="25" rows="5" style="border:none"/>	
											<form:hidden path="questions[0].rubrics[${rubricPos}].rubricId"/>
											<form:hidden path="questions[0].rubrics[${rubricPos}].questions.questionId"/>
											<form:hidden path="questions[0].rubrics[${rubricPos}].createdBy"/>
											<form:hidden path="questions[0].rubrics[${rubricPos}].rubricTypes.rubricTypeId"/>
											<form:hidden path="questions[0].rubrics[${rubricPos}].rubricScalings.rubricScalingId"/>
										</td>
										<c:if test="${rubType == 3 || rubType == 4}">
											<td>
												<form:textarea path="questions[0].rubrics[${rubricPos}].dimension2" 
													id="dimension2${rubricPos}" required="required" cols="25" rows="5" style="border:none"  onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}"/>
											</td>
											<td>
												<form:textarea path="questions[0].rubrics[${rubricPos}].dimension3" 
													id="dimension3${rubricPos}" required="required" cols="25" rows="5" style="border:none"  onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}"/>
											</td>
											<td>
												<form:textarea path="questions[0].rubrics[${rubricPos}].dimension4" 
													id="dimension4${rubricPos}" required="required" cols="25" rows="5" style="border:none"  onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}"/>
											</td>
											</c:if>	
										</c:otherwise>	
										</c:choose>		
									</tr>	
								</c:forEach>
							</c:if>
						</table>
				</div>
			</td>
		</c:when>
		</c:choose>
		</tr>
	   </table>
   </form:form>
  </td></tr> 
   <c:if test="${mode eq 'Create'}">  
   <tr><td height=5 colSpan=3></td></tr> 
   <tr><td align="center">
             <table width="100%" border="0" cellspacing="1" cellpadding="0">
                 <tr>
                     <td colspan="4" width="50%" height="25" align="right">
                          <div class="button_green" id="btCreate" name="btAdd" height="52" width="50" onclick="createQuestions()" >Create</div>
                     </td>
                     <td width="5%" align="center"></td>
                     <td colspan="4" width="50%" align="left" valign="middle">
                         <div class="button_green"  onclick="$('#questionsForm')[0].reset(); return false;">Clear</div>
                     </td>
                 </tr>
             </table></td>
      </tr>
      </c:if>
      <tr>
           <td width="325" align="center" colspan="5" height="25" >
           	<div id="resultDiv" class="status-message" ></div><div id="resultDiv0" class="status-message" ></div>
           </td>
     	</tr>
	</table>
