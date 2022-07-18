<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<!-- <link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" /> -->
<style>
.title
 {
 font-family: "Georgia";
   font-size : 15px;
   font-weight : bold;
   font-color :blue;
 }
 .answers
 {
 font-family: "Georgia";
   font-size : 15px;
   }
</style>
<script type="text/javascript">
	
	function getJacAnswers(s) {		
		var assignmentQuestionId = $("#qid"+s).val();
		var originalAnswer = $("#originalAnswer"+s).val();
		var answer=$("#answer"+s).val();
		var condition=/^([a-zA-Z0-9]+(\s|\.|\')?)+$/.test(answer);
		if(condition==false){
			systemMessage("requires alphanumeric characters");
			document.getElementById("answer"+s).value="";
			document.getElementById("answer"+s).focus;
			return false;
		}
		$.ajax({
			type : "GET",
			url : "saveJacAnswer.htm",
			data : "assignmentQuestionId=" +assignmentQuestionId +"&originalAnswer="+originalAnswer +"&answer=" + answer,
			success : function(response) {
				//alert(response);
			}
		});
	}
	function validateForm(formId,loopCnt) {
		var allFilled = true;
		var formData = new FormData(document.querySelector(formId));
			 for(i= 0;i < loopCnt; i++){
				var ele = document.getElementById("answer"+i);
				if(ele.type == "text"){
					if (ele.value === '')
			            allFilled = false;
				}
			 }
		    if(allFilled){
				return true;
		    }else{
			    alert("Please answer all the questions");
			    return false;
			}
	}
	function submitJacTemplate(noOfQuestions)
	{
 		var isValid = validateForm('studentassignmentform',noOfQuestions);
		 if(isValid){ 
			var studentAssignmentId=$("#studentAssignmentId").val();
			var tab = $('#tab').val();
			$.ajax({
				type : "GET",
				url : "submitJacTemplateTest.htm",
				data : "studentAssignmentId="+studentAssignmentId+"&tab="+tab,
				success : function(response) {
					$('#benchmarkDailog').dialog('close');
					$('#returnMessage').fadeIn();
					$("#returnMessage").html(response);
					$('#returnMessage').fadeOut(3000);
					
					var usedFor = $("#usedFor").val();
					if(usedFor == 'homeworks'){
						getTests(document.getElementById("csId")); 
					}else{
						location.reload();
					}
				}
			});
		 }
	}
	function refresh()
	{
		window.close();
		window.opener.location.reload();
	}
</script>
</head>
<body>
	<table align="center" id="shows" style="visibility: hidden;"><tr>
		<td><div id="msg" style="color:#0000FF"></div></td></tr>
	<!-- 	<tr><td><input type='button' value="close window" onClick='refresh()' /></td></tr> -->
	</table>
<div id="jactemplate" style="visibility: visible;">
	<form:form name="studentassignmentform" modelAttribute="studentAssignmentStatus">
		<table border=0 align="center" width="50%" class="des"><tr><td>
			<div style="background: -webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00E3FF), color-stop(1,#0696aa) ); padding: 1em;font-size: 15px; align: center" >
              <table width="60%">
              <tr>
				<td class="title"><font color="blue">Question File :<a href="downloadFile.htm?filePath=${jacQuestionFilePath}"><c:out value="${jacTitles[0].jacQuestionFile.filename}"></c:out></a></font>
				</td>
			
               <td align="right"><br>
               <input type="hidden" id="studentAssignmentId" value="${studentAssignmentId}"/>
		        <input type="hidden"  id="assignmentTypeId" value="${assignmentTypeId}" />
		        <input type="hidden" value="${usedFor}" id="usedFor" />
               </td> 
             </tr>
       </table>
       </div>
       
         	<c:set var="s" value="0" />
			<c:forEach items="${jacTitles}" var="jtl">
			 <div style="background-color: #c7e4e8; padding: 0.5em; margin: 0;"><font color="000000"><table>
			<tr>
				<td>
												
				<input type="hidden" name="titleid" id='titleid' value="${jtl.jacTemplateId}" /> 
					<b><label class="title"><c:out value="${jtl.titleName}"></c:out></label></b>
				</td>
			</tr></table></font></div><br>
			<c:forEach var="n" begin="0" end="${jtl.noOfQuestions-1}" >
			<div style="padding-left: 5em">
			<table>
			<tr>
				<td class="answers">
				   <input type="hidden" name="qid" id="qid${s}" value="${testQuestions[s].assignmentQuestionsId}" />
					<input type="hidden" name="originalAnswer" id="originalAnswer${s}" value="${testQuestions[s].questions.answer}" />                                                       
					<input type='text' required="required" name='answer' value='${testQuestions[s].answer}' id='answer${s}' size='30' onblur='getJacAnswers(${s})' /><br>
					
				</td>

			</tr>
			<c:set var="s" value="${s+1}" />
			</table></div>
			</c:forEach><br>
			</c:forEach>
			
			</td></tr></table><table align=center>
			<tr>

				<td height='20' align='center' valign='middle' class='tabtxt'><table
						style="">
						<tr>
							<td colspan='3' width='71' height='2' align='left'
								valign='middle'></td>
						</tr>
						<c:if test="${userReg.user.userType != 'parent'}">
						<tr>
							<td width='50%' align='right' valign='middle'></td>
							<td width='0' align='left' valign='middle'>							
								<input type="submit" class="button_green" id="btSubmitChanges" name="btSubmitChanges" height="52" width="50" value="Submit Changes"
								 onclick="submitJacTemplate(${s}); return false;"/>	
							</td>
						</tr>
						</c:if>
					</table></td>
			</tr>
		</table>		
	</form:form>
	</div>
</body>
</html>