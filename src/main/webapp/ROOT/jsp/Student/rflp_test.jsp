<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<meta charset="UTF-8">
<script src="resources/javascript/my_recorder/recorder.js"></script>
<script type="text/javascript" src="resources/javascript/Student/rflp_test.js"></script>
<script src="resources/javascript/common/js_speech_synthesis.js"></script>
<link rel="stylesheet" href="resources/css/icons/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/icons/ionicons/css/ionicons.min.css">
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<style>
.buttonCls{
	width: 150px;
	height: 35px;
	font-size:17px;
	color:red;
	font-weight:bold;
	background:-webkit-gradient(linear, left top, left bottom, from(#fff), to(#F0F0F0));
}
</style>
<meta name="msapplication-TileImage" content="/mstile-144x144.png">
<meta name="msapplication-TileColor" content="#333333">
<meta name="theme-color" content="#ffffff">
<script type="text/javascript">
$(document).ready(function () {
	 $("#loading-div-background1").css({ opacity: 0.8 });
});
function goToDictionary(langId,id){
	var word = $("#but"+id).val();
	console.log("asdjkhasdhasd"+word);
	/* var endString = word .replace(/’/g, "'");
	var b = word.replace(/\’/g, "\'");
	console.log("escape(word)="+word.replace(/\’/g, "\'")); */
	var wor=word.replace(/[\u2018\u2019\u201A]/g, "\'");
	var meanWord=wor.replace(/!+$/, '');
	if(langId==1)
	var url ="http://en.thefreedictionary.com/"+escape(meanWord);
	else
	var url ="http://es.thefreedictionary.com/"+encodeURIComponent(word);
	var a = document.createElement("a");
	a.target = "_blank";
	a.href = url;
	a.click();
}
</script>
</head>
<body>
 	<form:form id="questionsForm" modelAttribute="rflpTest"	action="submitRflpTest.htm" method="POST"> 
 	   <span id="flashcontent"></span>
	   <form:hidden id="operation" path="operation" value="" />
	   <form:hidden id="action" path="action" value="testRflp" />
	   <input type="hidden" id="studentAssignmentId" name="studentAssignmentId" value="${studentAssignmentId}" />
	   <input type="hidden" id="usersFilePath" name="usersFilePath" value="${usersFilePath}" />
	   <input type="hidden" id="totLen" name="totLen" value="${fn:length(rflpPracticeLt)}" />	  
	   <input type="hidden" id="langId" name="langId" value="${langId}" />
	   <input type="hidden" id="index" name="index" value=-1 />
	   <table border=0 align="center" width="100%"  style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;">
		    <tr><td height="10" style="font-size:11.5px;" align="left" width="30%"><font color='#00186D'>* Click on Error word to hear <b>correct pronunciation </b></font></td><td height="10" style="font-size:11.5px;" align="left"><font color='#00186D'>* Click on Search icon to get the <b>online help</b></font></td></tr>
		    <tr><td height="10" style="font-size:11.5px;" align="left" width="30%"><font color='#00186D'>* <font color='green'><b>Green</b></font> Colored words are <b>unknown words</b></font></td><td height="10" style="font-size:11.5px;" align="left"><font color='#00186D'>* <font color='blue'><b>Blue</b></font> Colored words are <b>skipped words</b></font></td></tr>
			<tr>
				<td width="100%" align="center" colspan="2" style="padding-top:1em;"> 
				<table border=0 align="center" width="95%" class="des">
		   		<tr><td>
					<div class="Divheads">
						<table width="100%" style="align: center;font-size:15px;">
							<tr>
								<td width="11%"><b>Word #</b></td>
								<td width="25%"><b>Error Word</b></td>
								<td width="35%" style="padding-left: 2em;"><b>Student authored sentences</b></td>
								<td width="18%"><b>Record Audio</b></td>
								<td width="5%"></td>
								<td width="10%"></td>
								</tr>
						</table>
					</div>
				</td></tr>
				<tr><td height="60">
					<div style="align: center" class="DivContents">
					<c:set var="cou" value="1" />
						<table width="100%"  style="font-size:13.5px;">
						   <c:forEach items="${rflpPracticeLt}" var="rflpPractice" varStatus="status">
						   	  <c:if test="${not empty rflpPractice.errorWord}">
						       <form:hidden id="rflpPracticeId${status.index}" path="rflpPractice[${status.index}].rflpPracticeId" value="${rflpPractice.rflpPracticeId}" required="required" />
						       <form:hidden id="rflpTestId${status.index}" path="rflpTestId" value="${rflpPractice.rflpTest.rflpTestId}" required="required"/>
						       <form:hidden id="audioData${status.index}"  path="rflpPractice[${status.index}].audioData" required="required" />
						       <c:choose>
								    <c:when test="${rflpPractice.unknownWord=='true'}">
								       <c:set var="color" value="#91a002"></c:set>
								    </c:when>
								    <c:when test="${rflpPractice.skippedWord=='true'}">
								       <c:set var="color" value="blue"></c:set>
								    </c:when>     
								    <c:otherwise>
								       <c:set var="color" value="black"></c:set>
								    </c:otherwise>
								</c:choose>
							   <tr>
							    <td width="8%" height="40">${rflpPractice.wordNum}</td>
								<td width="25%"><input type="button" id="but${cou}" height="15" onclick="spellIt(this.value)" value="${rflpPractice.errorWord}" style="width:150px;border-radius:0.5em;color:${color};" class="subButtons subButtonsWhite medium" />
								<i class="fa fa-search fa-rotate-90" style="color:#008FCF;text-decoration:none;font-size:26px;" onClick="goToDictionary(${langId},${cou})"></i>
								<%-- &nbsp;&nbsp;<a href="${langId == 1 ? 'http://en.thefreedictionary.com/':'http://es.thefreedictionary.com/'}${rflpPractice.errorWord}" target="_blank" class="fa fa-search fa-rotate-90" style="color:#008FCF;text-decoration:none;font-size:26px;"></a> --%></td>
								<td width="35%"><form:input id="studentSentence${status.index}" path="rflpPractice[${status.index}].studentSentence" value="${rflpPractice.studentSentence}" style="width: 325px;font-size:11.5px;height:25px;" onblur="autoSaveRflpTest(${status.index},'save')" /></td>
								<td width="20%"><div id="recordImg" name="recordImg" class="button_light_green" align="right" onclick="recordErrorWord('audioData','audioVoice.wav',${status.index})">RECORD</div>&nbsp;&nbsp;<div id="stopImg" name="stopImg" class="button_pink" align="right" onclick="stopErrorWord('audioData',${status.index})">STOP</div></td>
								<td width="2%">
								<span id="span${status.index}" style="display:none;font-size: 200%;color:green;text-decoration:none;font-size:26px;" class="fa fa-check"></span>
								<script>
							    $(function () {
							    	var index = "<c:out value='${status.index}'/>";
							    	var rflpTestId = "<c:out value='${rflpPractice.rflpTest.rflpTestId}'/>";
							    	var rflpPracticeId = "<c:out value='${rflpPractice.rflpPracticeId}'/>";
							    	var usersFilePath = $('#usersFilePath').val();
							    	index = parseInt(index);
							    	rflpTestId = parseInt(rflpTestId);
							    	rflpPracticeId = parseInt(rflpPracticeId);
							    	$.ajax({  
							    		url : "checkAudioExisted.htm", 
							    		data: "rflpTestId="+rflpTestId+"&rflpPracticeId="+rflpPracticeId+"&usersFilePath="+usersFilePath,
							    	    success : function(response) {
							    	    	if(response.isExisted == "true"){
							    	    		 $('#span'+index).show();
							    	    	}
							    		}
							    	}); 
								});
							</script>
								
								</td>
								<td width="10%" align="center" style="font-size:11px;"><font color="blue"><b><div id="resultDiv${status.index}"></div></b></font></td>
								</tr>
							   </c:if>
							   <c:set var="cou" value="${cou+1}" />
						   </c:forEach>
						</table>
					</div>
				</td></tr>
				<tr><td>
				<table align="center" width="100%" style="font-size: 16px;">
				 <tr>
					<td width="50%" align="right">
						<div class="button_green" align="right" onclick="formSubmit('submit',0,'TestSubmit')">Submit</div>
					</td>
					<td  width="3%"></td>
					<td  width="10%">
						<div class="button_green" align="right" onclick="$('#questionsForm')[0].reset();return false;">Clear</div>
					</td>
					<td></td>
				</tr>
			 </table>
			</td></tr> 
			</table>
			</td></tr>
			<tr><td height="60"><font color="blue" size="2"><b><div id="resultDiv" align="center" style="font-size:12px;"></div></b></font></td></tr>
		</table> 
	 </form:form>
	<div id="loading-div-background1" class="loading-div-background" style="display:none;">
		<div class="loader"></div>
		<div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;">
			<br><br><br><br><br><br><br>Loading....
		</div>
	</div>
</body>
</html>