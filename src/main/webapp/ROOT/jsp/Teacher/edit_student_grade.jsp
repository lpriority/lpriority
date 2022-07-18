<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style type="text/css">
.ui-dialog > .ui-widget-content {background: white;}
</style>
<script type="text/javascript">

	function showQuestion(sasId,count,assignmentId,assignmentTypeId,assignmentType,usedFor){
		var totalCount = $('#totalCount').val();
		var div = document.getElementById("detailsDiv"+count);
		if(div.style.display != "none"){
			$('#detailsDiv'+count).hide();
			return;
		}
		for(var i=0; i<totalCount;i++){
			$('#detailsDiv'+i).hide();
		}
		if(assignmentType != "Performance Task" && assignmentType != "Reading Fluency Learning Practice"){
			$.ajax({
				type : "GET",
				url : "getAssignmentQuestion.htm",
				data : "sasId=" + sasId+"&assignmentId="+assignmentId+"&assignmentTypeId="+assignmentTypeId+"&usedFor="+usedFor,
				success : function(response) {
					var dailogContainer = $(document.getElementById('studentGrades'));
					var screenWidth = $( window ).width() - 100;
					var screenHeight = $( window ).height() - 40;
					$('#studentGrades').empty();  
					$(dailogContainer).append(response);
					$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () 
					{
						  $(".ui-dialog-titlebar-close").show();
					},close : function(event, ui) {
						$(this).empty();
						$('#studentGrades').dialog('destroy');
						
					},
					dialogClass: 'myTitleClass'
				});		
					$(dailogContainer).dialog("option", "title", "Test Results");
					$(dailogContainer).scrollTop("0");	
					$("#loading-div-background").hide();
								
					/* $('#detailsDiv'+count).show();
					var detailContainer = $(document.getElementById('detailsDiv'+count));
			    	$(detailContainer).empty(); 			    	
					$(detailContainer).append(response); */

				}
			});
		}
		else if(assignmentType == "Performance Task"){
			$("#loading-div-background").show();
			$.ajax({
				type : "GET",
				url : "gradePerformanceTest.htm",
				data : "studentAssignmentId=" + sasId + "&assignmentTypeId=0&tab=gradeBook",
				success : function(response) {
					var dailogContainer = $(document.getElementById('performanceDailog'));
					var screenWidth = $( window ).width() - 175;
					var screenHeight = $( window ).height() - 175;
					$('#performanceDailog').empty();  
					$(dailogContainer).append(response);
					$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () 
					{
						  $(".ui-dialog-titlebar-close").show();
					},close : function(event, ui) {
						$(this).empty();
						$('#performanceDailog').dialog('destroy');
						
					},
					dialogClass: 'myTitleClass'
				});		
					$(dailogContainer).dialog("option", "title", "Performance Test");
					$(dailogContainer).scrollTop("0");	
					$("#loading-div-background").hide();
				}
			});
		}else if(assignmentType == "Reading Fluency Learning Practice"){
			var studentId = $('#studentId'+count).val();
			$.ajax({
				type : "GET",
				url : "getRFLPQuestions.htm",
				data : "studentAssignmentId=" + sasId + "&usedFor="+usedFor +"&assignmentTypeId="+assignmentTypeId+"&assignmentId="+assignmentId+"&studentId="+studentId, 
				//+ "&tab="+ tab,
				success : function(response) {
					$('#detailsDiv'+count).show();
					var detailContainer = $(document.getElementById('detailsDiv'+count));
			    	$(detailContainer).empty(); 			    	
					$(detailContainer).append(response);
					
				}
			});
		}
	
	}
	function checkParecentage(count){
		var percent=$('#percent'+count).val();
		if(percent>100){
		    alert("The percentage must be between 0 and 100");
		    $('#percent'+count).val(100);
		}  
	}
</script>
	<form name="editGradeForm" action="updateStudentGrades.htm" id="editGradeForm" method="post" enctype="multipart/form-data">
		<table name="classes" border="0" cellpadding="3" cellspacing="3" vspace="0" width="100%" hspace="0" align="center">
			<c:choose>
				<c:when test="${fn:length(studentTestList) > 0}">
					<tr >
						<th colspan="8" align="left" width="80%">Title</th>
						<th align="center" width="20%">Percentage</th>								
					</tr>
					<tr><td>
						<input type="hidden" id="totalCount" name="totalCount" value="${fn:length(studentTestList)}">
						<input type="hidden" id="usedFor" name="usedFor" value="${usedFor}">
					</td></tr>
					<c:set var="count" value="0"></c:set>
					<c:forEach items="${studentTestList}" var="sTest">					
						<tr >
							<td colspan="8" align="left" width="80%" style="padding-bottom: 5px;" onclick=""> 
										<a href="#" onclick="showQuestion(${sTest.studentAssignmentId},${count},${sTest.assignment.assignmentId},${sTest.assignment.assignmentType.assignmentTypeId},'${sTest.assignment.assignmentType.assignmentType}','${usedFor}')">
											<font color="blue">${sTest.assignment.title}</font>
										</a> 
									<input type="hidden" id="sasId${count}" name="sasId${count}" value="${sTest.studentAssignmentId}">
									<input type="hidden" id="studentId${count}" name="studentId${count}" value="${sTest.student.studentId}">	
							</td>					
							<td align="center" width="20%">
								<input id="percent${count}" name="percent${count}" value="${sTest.percentage}" onblur="checkParecentage(${count})" />
							</td>
						</tr>
				
							<tr>
								<td><div id="detailsDiv${count}" style="display: none;padding: 10px;">
								</div></td>
							</tr>
						<c:set var="count" value="${count+1}"></c:set>
					</c:forEach>
					<tr>
                    	<td colspan="9" align="center" style="padding-top: 2cm;">
                        	<input type="submit" class="button_green" id="btSubmitChanges" name="btSubmitChanges" height="52" width="50" value="Submit Changes" onclick="return updateGrades()">
                        </td>
                   	</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td align="center" colspan="10"><font color="blue">
							No Assignments are Graded.</font>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
	</form>
	<div id="performanceDailog"></div>
	<div id="studentGrades"></div>
</body>
</html>