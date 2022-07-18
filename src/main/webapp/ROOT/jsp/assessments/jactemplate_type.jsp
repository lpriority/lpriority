<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/curriculum.js"></script>	
<%@ include file="../CommonJsp/include_resources.jsp" %>
<style type="text/css">
	.ui-accordion > .ui-widget-content {background: white;}
	.ui-accordion .ui-accordion-icons{background: rgba(189, 223, 239, 0.28);border: 1px solid #a0daf6;}
	.ui-accordion .ui-accordion-header{font-size:80%;}
	.ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active{border: 1px solid #0057AF;color:#0057AF;}
    .ui-widget input, .ui-widget select, .ui-widget textarea, .ui-widget button{font-size:1em;}
    .ui-widget-content{border: 1px solid #a0daf6;}
    .ui-state-hover, .ui-widget-content .ui-state-hover, .ui-widget-header .ui-state-hover, .ui-state-focus, .ui-widget-content .ui-state-focus, .ui-widget-header .ui-state-focus{border:1px solid #1880C9;color:#1880C9;}
    .titleBtn {
		background: #0057af;
		background-image: -webkit-linear-gradient(top, #0057af, #0057af);
		background-image: -moz-linear-gradient(top, #0057af, #0057af);
		background-image: -ms-linear-gradient(top, #0057af, #0057af);
		background-image: -o-linear-gradient(top, #0057af, #0057af);
		background-image: linear-gradient(to bottom, #0057af, #0057af);
		-webkit-border-radius: 28;
		-moz-border-radius: 28;
		border-radius: 28px;
		font-family: Arial;
		color: #ffffff;
		font-size: 13px;
		padding: 10px 20px 10px 20px;
		text-decoration: none;
	}

	.titleBtn:hover {
		background: #007fff;
		background-image: -webkit-linear-gradient(top, #007fff, #0057af);
		background-image: -moz-linear-gradient(top, #007fff, #0057af);
		background-image: -ms-linear-gradient(top, #007fff, #0057af);
		background-image: -o-linear-gradient(top, #007fff, #0057af);
		background-image: linear-gradient(to bottom, #007fff, #0057af);
		text-decoration: none;
		cursor: hand; cursor: pointer;
	}
	
	 .answerBtn {
		background: #0057af;
		background-image: -webkit-linear-gradient(top, #0057af, #0057af);
		background-image: -moz-linear-gradient(top, #0057af, #0057af);
		background-image: -ms-linear-gradient(top, #0057af, #0057af);
		background-image: -o-linear-gradient(top, #0057af, #0057af);
		background-image: linear-gradient(to bottom, #0057af, #0057af);
		-webkit-border-radius: 28;
		-moz-border-radius: 28;
		border-radius: 28px;
		font-family: Arial;
		color: #ffffff;
		font-size: 4px;
		padding: 5px 10px 5px 10px;
		text-decoration: none;
	}

	.answerBtn:hover {
		background: #007fff;
		background-image: -webkit-linear-gradient(top, #007fff, #0057af);
		background-image: -moz-linear-gradient(top, #007fff, #0057af);
		background-image: -ms-linear-gradient(top, #007fff, #0057af);
		background-image: -o-linear-gradient(top, #007fff, #0057af);
		background-image: linear-gradient(to bottom, #007fff, #0057af);
		text-decoration: none;
		cursor: hand; cursor: pointer;
	}
	.titleTextBox{
    width: 250px;
    height: 25px;
    font-family: 'Segoe UI', Frutiger, 'Frutiger Linotype', 'Dejavu Sans', 'Helvetica Neue', Arial, sans-serif;
    font-size:14px;
    vertical-align:middle;
  }
  .title{
	  border: 0px none;
	  background:rgba(243, 251, 255, 0.28);
  }
</style>
<script type="text/javascript">
	/* $(function () {
		$("#jackTemplateDetails").accordion({
			collapsible : true,
			active : false
		});

	}); */
	$.ui.accordion.prototype._originalKeyDown = $.ui.accordion.prototype._keydown;
	$.ui.accordion.prototype._keydown = function( event ) {
	    var keyCode = $.ui.keyCode;

	    if (event.keyCode == keyCode.SPACE) {
	        return;
	    }
	    // call the original method
	    this._originalKeyDown(event);
	};
   var count = 0;
   var counter = 0;
   var currentDiv = '';
   var currentCounter = 0;
</script>
 <table width="80%">
     <tr><td>
   <form:form id="questionsForm"  action="createAssessments.htm" modelAttribute="questionsList" method="post" enctype="multipart/form-data">
  <table width="100%">
     <input type="hidden" id="usedFor" name="usedFor" value="${usedFor}">
	<input type="hidden" id="tab" name="tab" value="${tab}">
	<input type="hidden" id="lessonId" name="lessonId" value="${lessonId}">
	<input type="hidden" id="assignmentTypeId" name="assignmentTypeId" value="${assignmentTypeId}">
	<input type="hidden" id="assignmentId" name="assignmentId" value="">
	<input type="hidden" id="mode" name="mode" value="${mode}">
    <input type="hidden" name="noOfFiles" id="noOfFiles" />
    <input type="hidden" name="jacFileName" id="jacFileName" value="${jacQuestionFile.filename}"/>
    <input type="hidden" id="questionId" name="questionId" value="${questionId}">
    <input type="hidden" id="question0" name="question0" value="0">
    <input type="hidden" id="id" name ="id" value="" />
    <input type="hidden" id="jacQuestionFileId" name ="jacQuestionFileId" value="${jacQuestionFile.jacQuestionFileId}" />
    <form:hidden path="questions[0].questionId" id="question" name="question" value="${questionId}"/>
    <input type="hidden" id="gradeId" name="gradeId" value="${gradeId}">
     <tr>
		<td width="30%" align="left" colspan="2" style="padding-left: 2em;"><span class="tabtxt">Enter Title Name :</span></td>
	    <td colspan="2" valign="middle" style="" width="10%"><input class="titleTextBox" type="text" id="title" name="title" /></td>
		<td colspan="2" valign="middle" style="" width="50%">&nbsp;&nbsp;<input type="button" class="button_green" id="addTitle" alt="Add Title" height="52" width="50" value="Add Title" name="addTitle" onclick="createTitle()"> 
	   </td>
	</tr>
	 <c:choose>
		  <c:when test="${mode eq 'Create'}"> 
		   <tr height="40" align="left">
				<td width="30%" align="left" colspan="2" style="padding-left: 2em;">
					<span class="tabtxt">Select Question file :</span>
				</td>
				<td  colspan="2" valign="bottom" style="padding-top:1em;" width="10%">
					 &nbsp;&nbsp;<input type="file" id="file" name="files" value="Upload" required="required" /><br><br> 
				</td>
				<td colspan="2" valign="middle" style="" width="50%"></td>
			</tr> 
		  	<tr><td colspan="5">
				<div id="jackTemplateDetails" align="left" style="padding-left: 1cm;width: 120%"></div>
			</td></tr>
		   </c:when>
		   <c:when test="${mode eq 'Edit'}">
		   <tr>
		       	<td width="30%" align="left" colspan="2" style="padding-left: 2em;">
						<span class="tabtxt">Uploaded file :</span>
				</td>
				<td colspan="2" valign="bottom" style="padding-top:1em; style="text-wrap:normal;" width="10%">
				    <div id="filesDiv">
					    <a href="assessmentsFileDownload.htm?questionId=${jacQuestionFile.jacQuestionFileId}&fileName=${jacQuestionFile.filename}&type=JAC_Template">${jacQuestionFile.filename}</a>&nbsp;&nbsp;
					</div>
				</td>
				<td colspan="2" valign="middle" style="" width="50%"><div id="result" ></div></td>
			</tr>
			<tr>	
			    <td width="30%" align="left" colspan="2" style="padding-left: 2em;">
					<span class="tabtxt">Change file :</span>
				</td>
				<td width="30%" align="left" style="padding-top:1em;">
					<input type="file" id="file" name="files" value="Upload"  onchange="filesCount('file','noOfFiles'),updateQuestion(0)" multiple/><br><br> 
				</td>
		   </tr>
		   <tr><td colspan="5">
				<div id="jackTemplateDetails" align="left" style="padding-left: 1cm;width: 120%">
				<c:if test="${jacTemplateSize > 0}">
				<c:forEach varStatus="status" items="${questionsList.jacTemplate}"	var="jacTemplate">
			    <form:hidden path="jacTemplate[${status.index}].jacTemplateId" id="jacTemplateId${status.index}" name="jacTemplateId${status.index}" value="${questionsList.jacTemplate[status.index].jacTemplateId}"/>
				<table width='100%' id='table${status.index}'>
				 <tr>
					<td style='padding-left: 1cm;width:80%;'>
						<div id='titleDiv${status.index}'>
							<h3><input type="text" id='title${status.index}' value="${jacTemplate.titleName}" width="100%" class="title"  style="width:200px;font-family: 'Segoe UI', Frutiger, 'Frutiger Linotype', 'Dejavu Sans', 'Helvetica Neue', Arial, sans-serif;font-size:14px;color:#0057AF;vertical-align:middle;font-weight: 500;margin-top:-0.4em;" onclick="updateTitle(${status.index})" onchange="addOrUpdateJacTemplate(${status.index}),$('#title${status.index}').attr('class', 'title'); return false;" /></h3>
						    <div id="contentDiv${status.index}"	style="min-height:200px;">
						    <table width='100%'><tr><td align='right' width='10%' height='40'>
						    <input type=button class='button_green' id='createAnswer${status.index}'  style="font-size: 0.75em;" value='Add Answers' name='createAnswer${status.index}'  onclick='getDynamicTextBox(${status.index})'/></td></tr>
						    <tr><td width='80%' align='center'>
							    <div id='answersDiv${status.index}' >
							      <input type=hidden id='currentId${status.index}' name='currentId'/><input type=hidden id='presentCounter${status.index}' name='presentCounter${status.index}'/>
							      <c:forEach varStatus="subStatus" items="${questionsList.jacTemplate[status.index].questionsList}"	var="questions">
							   		 <div id='div${status.index}${subStatus.index}' style='height:40px;' class=tabtxt> 
							   		    <form:hidden path="jacTemplate[${status.index}].questionsList[${subStatus.index}].questionId" id="questionId${status.index}${subStatus.index}" name="questionId${status.index}${subStatus.index}" value="${questionsList.jacTemplate[status.index].questionsList[subStatus.index].questionId}"/>
										 <span id='span${status.index}${subStatus.index}'>Answer ${subStatus.count} .</span>&nbsp;&nbsp;
										 <input class='titleTextBox' id='answer${status.index}${subStatus.index}' name='jacTemplate[${status.index}].questionsList[${subStatus.index}].answer' value="${questions.answer}" type='text'  size='25' required='required' pattern='[A-Za-z0-9\s]+' onchange="addOrUpdateAnswer(${status.index},${subStatus.index})"/>
										 &nbsp;&nbsp;
										 <i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;" onclick="removeAnswer(${status.index},${subStatus.index})"></i>
									     <script>
										    $(function () {
										    	var countId = "<c:out value='${status.index}'/>";
										    	var presentCounter = "<c:out value='${subStatus.index}'/>";
										    	document.getElementById('presentCounter'+countId).value = parseInt(presentCounter)+1;
										    	counter = parseInt(presentCounter)+1;
											});
										 </script>
									</div> 
							      </c:forEach>
							    </div>
						    </td></tr>
						   </table></div>
						</div>
				    </td>
				    <td>
				        <i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;" onclick="removeTitle(${status.index})"></i>
					    <input type=hidden id='title${status.index}' name='jacTemplate[${status.index}].titleName' value='${jacTemplate.titleName}'/>
				    </td>
				     <td>
			        	<div id="resultDiv${status.index}" ></div>
			         </td>
			      </tr>
			      </table>
			    <script>
			    $(function () {
			    	var id = "<c:out value='${status.index}'/>";
					$("#titleDiv"+id).accordion({
						collapsible : true,
						active : false
					});
					currentDiv = parseInt(id);
					count = parseInt(id)+1;
				});
			    </script>
			</c:forEach> 
		</c:if>

		</div>
	</td></tr>
   </c:when>
 </c:choose> 
</table>
</form:form>
	 <tr><td height=5 colSpan=3></td></tr>
	  <c:choose>
		  <c:when test="${mode eq 'Create'}">  
			 <tr><td align="center" id="submitDiv" style="display:none;">
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
      </c:when>
       <c:when test="${mode eq 'Edit'}">
       </c:when>
      </c:choose>
      <tr>
        <td width="325" height="25" align="center" colspan="5">
        	<div id="resultDiv" class="status-message" >
       </td>
     </tr>
</table>
<div id="jac-loading-div-background" class="loading-div-background" style="display:none;">
	<div class="loader"></div>
	<div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;">
		<br><br><br><br><br><br><br>Loading....
	</div>
</div>
	