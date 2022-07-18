<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="resources/javascript/jplayer/css/jplayer.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/CommonValidation.js"></script>
<script src="resources/javascript/TeacherJs/grade_assessments.js"></script>
<script src="resources/javascript/Student/self_grading.js"></script>
<script src="resources/javascript/common/js_speech_synthesis.js"></script>
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script src="resources/javascript/contextmenu/jquery.contextMenu.js"></script>
<script src="resources/javascript/contextmenu/jquery.ui.position.js"></script>
<link rel="stylesheet" href="resources/css/contextmenu/jquery.contextMenu.css">
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
 <style type="text/css">
	.ui-dialog > .ui-widget-content {background: white;}
	.ui-widget {font-family:Georgia, Times, 'Times New Roman', serif;font-size: 1em;}
	.table_border {
		border-radius: 2px;
		border: 1px solid #029ae3;
		padding: 20px; 
		width: 500px;
		height: 110px; 
		background:#eeeeee;
	}
	.box {
		position:absolute;
		width:100px;
		height:40px;
		background:#ffffff;
		z-index:51;
		padding:5px;
		-webkit-border-radius: 2px;
		-moz-border-radius: 2px;
		border-radius: 2px;
		-moz-box-shadow:0px 0px 5px 1px #BCE2E7;
		-webkit-box-shadow:0px 0px 5px 1px #BCE2E7;
		box-shadow:0px 0px 5px 1px #BCE2E7;
		display:none;
		resize: none;
		border:solid 1px #C6C6C6;
		color:#ff0057;
		}
</style>
<script type="text/javascript">
var word;
var wordNum;
var startPointDisabled=false;
var endPointDisabled=true;
var audioDuration;
$(document).ready(function(){
	audioDuration = undefined;
	var audio = $("#accuracy")[0];
	var getDuration = function (audio, next) {
	    audio.addEventListener("durationchange", function (e) {
	        if (this.duration!=Infinity) {
	           var duration = this.duration
	           //audio.remove();
	           next(duration);
	        };
	    }, false);  
	};

	getDuration (audio, function (duration) {
		audioDuration = duration;
		var wcpm = 0;
		var count = document.getElementById("wordsread").value;
		if(count != '') {
			if(audioDuration > 0) {
				wcpm = Math.round(parseInt(count) * 60 / audioDuration) ;
			} else {
				wcpm = Math.round(parseInt(count) * 60) ;
			}
			document.getElementById("wcpm").value = wcpm;
			$("#audioDuration").val(audioDuration);
		}
		
	});
	
	var getFluencyCommentsCallBack = function(list){
		if (list != null) {
			dwr.util.removeAllOptions('commentId');
			dwr.util.addOptions('commentId', ["select comment"]);
			dwr.util.addOptions('commentId', list,'comment');
		} else {
			dwr.util.removeAllOptions('commentId');
			dwr.util.addOptions('commentId', ["select comment"]);
		}
	}
	gradeAssessmentsService.getFluencyComments({
		callback : getFluencyCommentsCallBack
	});
	
	 $('#contextMenuDiv').on('contextmenu', 'span' ,function(e) {
  		 e.preventDefault();
  		 window.speechSynthesis.cancel();
	     word = $("#"+this.id).text();
	     wordNum = parseInt(this.id);
	    // word = word.replace(/[_\W]+/g, "");
	     $.contextMenu({
	         selector: '.context-menu-student-accuracy', 
	         animation: {duration: 5, show: 'fadeIn', hide: 'fadeOut'},
	         autoHide: true,
	         trigger:'right',
	         build: function ($trigger, e) { 
	        	var options = {
	        	           callback: function(key, options) {
	        	        	   var assignmentQuestionId=$("#questions").val();
	        	        	     var readingTypesId=$("#readingTypesId").val();
	        	        	 	 var gradeTypesId=$("#gradeTypesId").val();
	        	        	 	 var regradeStat=$('#regradeStatus').val();
	        	        	 	 var langId=$('#langId').val();
	        	            	 if(key == 'skippedWord'){
	        	 	        		 var obj = $("#"+wordNum)[0];
	        	 	        		 strikeFText(obj, wordNum, 'skippedWord','accuracy');
	        	 	        		 if(regradeStat=="no"){
	        	 	        			checkSelectRange(wordNum);
	        	 	        			var val=document.getElementById("word"+wordNum).value;
	        	 	        			val = val+'@';
	        	 	        			autoSaveErrorWord(val,wordNum,key,regradeStat);
	        	 	        		 }
	        	 	        		 autoDisplayErrorCount("accuracy");
	        	 	        		 
	        	 	         	}else if(key == 'unKnownWord'){
	        	            		var obj = $("#"+wordNum)[0];
	        	            		chkErrExists(obj, wordNum,"unKnownWord");
	        	            		 if(regradeStat=="no")
	        	 	         			editStrikeFluencyText(obj, wordNum, 'unKnownWord','accuracy',regradeStat);
	        	 	         		 else
	        	 	         			strikeFText(obj, wordNum, 'unKnownWord','accuracy');
	        	 	         		
	        	            		 autoDisplayErrorCount("accuracy");
	        	 	        	 }else if(key == 'getWord'){
	        	 	         		//document.getElementById("speak_button").value="Play";
	        	 	         		// word = word.replace(/[_\W]+/g, "");
	        	 	         		spellIt(word);
	        	 	        	}else if(key == 'addComment'){
	        	 	                if(!document.getElementById("comment"+wordNum)){
	        	 		            	 $('#'+wordNum).after("<textarea id='comment"+wordNum+"' name='textarea"+wordNum+"' style='display:none;font-size: 13px;' onblur='closeComment("+wordNum+")' class='box' onkeypress='searchKeyPress("+wordNum+")'></textarea>");
	        	 		            	 document.getElementById("errors").value = ++document.getElementById("errors").value;
	        	 	                 }
	        	 	            	showCommentBox(wordNum);
	        	 	            	if(!document.getElementById("errorIcon"+wordNum))
	        	 	            	  $('#'+wordNum).after("<div id='errorIcon"+wordNum+"' class='fa fa-bolt fa-flip-horizontal' aria-hidden='true' style='color:#ff216c;font-size:20px;' onClick='showCommentBox("+wordNum+")'></div>");
	        	 	            }else if(key == 'removeComment'){
	        	 	            	      	 	            	
	        	 	            	$('#comment'+wordNum).remove();
	        	 	            	$('#errorIcon'+wordNum).remove();
	        	 	            	document.getElementById("errors").value = --document.getElementById("errors").value ;
	        	 	            	addedWordsArray = $.grep(addedWordsArray, function(value) {
	        	 	        		    return value.indexOf(wordNum + "$") < 0;
	        	 	        		});
	        	 	            	autoDisplayErrorCount("accuracy");
	        	 	            	if(regradeStat=="no"){
	        	 	            		var value = $("#comment"+wordNum).val();
	        	 	            	    removeFluencyAddedWord(wordNum,value,1)
	        	 	            	}
	        	 	            }else if(key == 'onlineDictionary'){
	        	 	            	var content=jQuery.trim(word);
	        	 	            	content= content.toString().replace(/[`~!@#$%^&*()_|+\-=?;:",.<>\{\}\[\]\\\/]/gi, '');
	        	 	            	if(content.charAt(0) === '"' || content.charAt(0) === '“' || content.charAt(0)==="\'" || content.charAt(0)==="\‘"){
	        	 	         			content= content.slice(1);
	        	 	         		}
	        	 	         		if(content.charAt(content.length-1) === '"' || content.charAt(content.length-1) === '”' || content.charAt(content.length-1)==="\'" || content.charAt(content.length-1)==="\’"){
	        	 	         			content=content.slice(0, content.length-1);
	        	 	         		}
	        	 	         		var wor=content.replace(/[\u2018\u2019\u201A]/g, "\'");
	        	 	         		
	        	 	             	if(langId==2){
	        	 	         		  	var url="http://es.thefreedictionary.com/"+encodeURIComponent(wor);
	        	 	         		}
	        	 	         		else
	        	 	         		var url ="http://en.thefreedictionary.com/"+escape(wor);
	        	 	         		var a = document.createElement("a");
	        	 	         	    a.target = "_blank";
	        	 	         	    a.href =url;
	        	 	         	    a.click();
	        	 	            }
	        	 	            else if(key == 'startPoint'){
	        	 	         		var previousFirstRange = parseInt(document.getElementById("firstRange").value);
	        	 	         		if(previousFirstRange == 0){
	        	 	         			  document.getElementById("firstRange").value=wordNum;
	        	 	         		 }else{
	        	 	         			var j = 0;
	        	 		         		count=0;
	        	 	         			var lastRange =  parseInt(document.getElementById("lastRange").value);
	        	 		         		if(previousFirstRange != 0 && lastRange != 0){
	        	 			         		if(previousFirstRange < lastRange){
	        	 		         				 for(j=previousFirstRange;j<=lastRange;j++){
	        	 		         		    		 document.getElementById(j).style.background = 'none';
	        	 		         		    	      for(var m=0;m<errorsAddress.length;m++){
	        	 		         		    		  if(errorsAddress[m]==j){
	        	 		         		    	    	 document.getElementById(j).style.color = 'none';
	        	 		         		    	    	document.getElementById(j).style.textDecoration="line-through";
	        	 		         		    		  } 
	        	 		         		    	 }
	        	 		         		    	     count++;
	        	 		         		    	 }
	        	 		         			} 
	        	 		         		}
	        	 		         		 document.getElementById("firstRange").value = wordNum;
	        	 		         		 document.getElementById("lastRange").value = "";
	        	 		         		 document.getElementById("correctwords").value=0;
	        	 		         		 document.getElementById("wordsread").value=0;
	        	 		         		 document.getElementById("accuracyPer").value=0;
	        	 		         		 
	        	 	         		}
	        	 	         		startPointDisabled = true;
	        	 	         		endPointDisabled = false;
	        	 	         		if(document.getElementById("startPoint"))
	        	 	         			$('#startPoint').remove();
	        	 	         		if(document.getElementById("endPoint"))
	        	 	         			$('#endPoint').remove();
	        	 	         		$('#'+wordNum).before("<div id='startPoint' class='fa fa-share' aria-hidden='true' style='color:#0c7ad1;font-size:20px;position:absolute;z-index:51;margin-top:-0.7em'></div>");
	        	 	         	}else if(key == 'endPoint'){
	        	 	         		var firstRange = parseInt(document.getElementById("firstRange").value);
	        	 	         		var lastRange = parseInt(wordNum);
	        	 	         		var j = 0;
	        	 	         		count=0;
	        	 	         		if(firstRange != 0 && lastRange != 0){
	        	 		         		if(firstRange > lastRange){
	        	 			         		document.getElementById("lastRange").value = 0;
	        	 	         				alert("End point should be grater than end point");
	        	 	         			}else{
	        	 	         				 for(j=firstRange;j<=lastRange;j++){
	        	 	         		    		 document.getElementById(j).style.background = 'rgba(0, 176, 255, 0.21)';
	        	 	         		    	     for(var m=0;m<errorsAddress.length;m++){
	        	 	         		    		  if(errorsAddress[m]==j){
	        	 	         		    			  if(errorsArray[m].endsWith("$"))
	        	 	         		    				 document.getElementById(j).style.color = 'green';
	        	 	         		    			  else if(errorsArray[m].endsWith("@"))
	        	 	         		    				 document.getElementById(j).style.color = 'blue';
	        	 	         		    			  else
	        	 	         		    	    	 	document.getElementById(j).style.color = 'red';
	        	 	         		    	    	document.getElementById(j).style.textDecoration="line-through";
	        	 	         		    		  }
	        	 	         		    	 }
	        	 	         		    	     count++;
	        	 	         		    	 }
	        	 	         				document.getElementById("wordsread").value = count;
	        	 	         				if (audioDuration != undefined && audioDuration != Infinity) {
	        	 	         					var wcpm = 0;
	        	 	         					if(audioDuration > 0 ) {
	        	 	         						wcpm = Math.round(parseInt(count) * 60 / audioDuration) ;
	        	 	         					} else {
	        	 	         						wcpm = Math.round(parseInt(count) * 60) ;
	        	 	         					}
	        	 	         					document.getElementById("wcpm").value = wcpm;
	        	 		        	 	    }
	        	          			        wordsReadByStudent=count;
	        	          			        k=document.getElementById("errors").value;
	        	          			        var total=count-k;
	        	          			        document.getElementById("correctwords").value=total;
	        	         	         		document.getElementById("lastRange").value = wordNum;
											 var s=parseInt(total) + parseInt(k);
											 var accPercentage = parseInt(total * 100) /parseInt(s);
											document.getElementById("accuracyPer").value=accPercentage.toFixed(2);
	        	          			       startPointDisabled = false;
	        	        	         		   endPointDisabled = true;
	        	        	         		if(document.getElementById("endPoint"))
	        	     	         			$('#endPoint').remove();
	        	        	         		   $('#'+wordNum).before("<div id='endPoint' class='fa fa-reply' aria-hidden='true' style='color:#0c7ad1;font-size:20px;position:absolute;z-index:51;margin-top:-0.7em'></div>");
	        	 	         			} 
	        	 	         		}
	        	 	         		
	        	 	         	
	        	 	         	}else if(key == 'quit'){
	        	 	         		return 'context-menu-icon context-menu-icon-quit';
	        	 	         	}
	        	            },
	        	            items: {}	            	
	        	        };
		        	         		
	        		 	options.items["unKnownWord"] = {name: "Unknown Word", icon: "fa-question-circle"};
	        		 	if(langId.value==1){
	        		 		options.items["getWord"] = {name: "Listen Word", icon: "fa-microphone"};
	        		 	}
    			 		options.items["onlineDictionary"] = {name: "Online Dictionary", icon: "fa-search"};
    			 		options.items.step1 = "---------";
		        		
	        	 	     options.items["startPoint"] = {name: "Start Point", icon: "fa-caret-right", disabled: startPointDisabled};
	        			 options.items["endPoint"] = {name: "End Point", icon: "fa-caret-left" , disabled: endPointDisabled};
	        			 options.items.step2 = "---------";
	        			 options.items["quit"] = {name: "Quit", icon: "fa-times"};
	        	        return options;
	        	    }
	     });
	 });	 
// 	var benchmarkTestFilePath = document.getElementById("usersFilePath").value;
// 	var studentRegId= document.getElementById("regId").value;
// 	var gradePage = document.getElementById("gradePage").value;
	
});
function showCommentBox(wordNum){
	var regradeStat=$('#regradeStatus').val();
	if(regradeStat=="no"){
	 checkSelectRange(wordNum);
	}
 var posX = $('#'+wordNum).position().left, posY = $('#'+wordNum).position().top;
    posX = (posX - 40)+"px";
	posY = (posY - 15)+"px";
	$("#comment"+wordNum).css({left:posX,top:posY});
	$("#comment"+wordNum).fadeIn(500);
	$("#comment"+wordNum).focus();
	
}
function closeComment(wordNum){
	var value = $("#comment"+wordNum).val();
	if(value){
		addedWordsArray = $.grep(addedWordsArray, function(value) {
		    return value.indexOf(wordNum + "$") < 0;
		});
		addedWordsArray.push(wordNum + "$"+value);
	}
	autoDisplayErrorCount("accuracy");
	$("#comment"+wordNum).html(value);
	$("#comment"+wordNum).fadeOut(500);
	var regradeStat=$('#regradeStatus').val();
	if(regradeStat=="no"){
	saveFluencyAddedWord(wordNum,value,1);
	
	}
}
function searchKeyPress(wordNum){
	event = window.event;
    if(event.keyCode == 13){
    	event.preventDefault();
    	var value = $("#comment"+wordNum).val();
    	if(value){
    		addedWordsArray = $.grep(addedWordsArray, function(value) {
    		    return value.indexOf(wordNum + "$") < 0;
    		});
    		addedWordsArray.push(wordNum + "$"+value);
    	}
    	$("#comment"+wordNum).html(value);
    	$("#comment"+wordNum).fadeOut(500);
        return false;
    }
    return true;
}
function setComments(regrade){
	var comment = $("#commentId option:selected").text();
	if(comment != 'select comment'){
		$('#fluencyComment').val($('#fluencyComment').val()+comment);
		$('#commentId').css({color:'black'})
	}
if(regrade=='no')
	autoSaveComments();
}	
	
</script>
</head>
<table width="90%" align="center" border=0 class="des"> <form:form name="StudentBenchmarkForm" modelAttribute="assignmentQuestions">
             <tr>
                <td height='20' align='center' valign='middle' class='tabtxt'>
               <input type="hidden" name="regId" value="${studentRegId}" id="regId"><input type="hidden" name="usersFilePath" value="${usersFilePath}" id="usersFilePath"> 
               <font size="3"><b>Accuracy Test</b></font></td></tr>
               <tr><td width='100%' height='25' align='left' class="tabtxt"><font size="2"><b>Accuracy Passage </b></font>
                  <input type="hidden" name="questions" id="questions" value="${assignQuestions.assignmentQuestionsId}"/>
                  <input type="hidden" name="lessonId" id="lessonId" value="${lessonId}"/>
                  <input type="hidden" name="csId" id="csId" value="${csId}"/>
                  <input type="hidden" name="studentId" id="studentId" value="${studentId}"/>
                  <input type="hidden" name="langId" id="langId" value="${langId}"/>
                  <input type="hidden" name="assignmentTitle" id="assignmentTitle" value="${assignmentTitle}"/>
                  <input type="hidden" name="hwAssignmentId" id="hwAssignmentId" value=0 />
                   <input type="hidden" name="regradeStatus" id="regradeStatus" value="${regrade}" />
                   <c:if test="${regrade eq 'no'}">
                  <input type="hidden" name="readingTypesId" id="readingTypesId" value="${readingTypesId}" />
                  <input type="hidden" name="gradeTypesId" id="gradeTypesId" value="${gradeTypesId}" />
                  <input type="hidden" name="wordsRead" id="wordsRead" value="${wordsRead}" />
                   <input type="hidden" name="audioDuration" id="audioDuration" value="" />
                   </c:if>
                  </td></tr>
                 <tr><td width="100%">
					<div id="contextMenuDiv"  class="context-menu-student-accuracy btn btn-neutral" style="text-align:justify">
                           <c:set var="i" value="0"></c:set>
                           <c:forEach items="${benchmarkqList}" var="ql">
                 				<c:set var="i" value="${i+1}"></c:set>
                            <c:set var="color" value="#000"></c:set>
                            <c:set var="textDecoration" value="none"></c:set>
                            <c:choose>
                                  <c:when test="${i<=wordsRead  && regrade eq 'no'}">
                                      <c:set var="bcolor" value="#9AFEFF"></c:set>
                                  </c:when>
                                  <c:otherwise>
									       <c:set var="bcolor" value=""></c:set>
									    </c:otherwise>
                              </c:choose>    
                           <c:forEach items="${errorsList}" var="errIndex">
                                <c:if test="${errIndex.errorsAddress == i}">
                                  <c:choose>
									    <c:when test="${errIndex.unknownWord=='true'}">
									       <c:set var="color" value="green"></c:set>
									    </c:when>
									    <c:when test="${errIndex.skippedWord=='true'}">
									       <c:set var="color" value="blue"></c:set>
									       
									    </c:when>     
									    <c:otherwise>
									       <c:set var="color" value="red"></c:set>
									    </c:otherwise>
									</c:choose>
                                  <c:set var="textDecoration" value="line-through"></c:set>
                                </c:if> 
						   </c:forEach>
						   <c:set var="isAddedWord" value="false" />
								<c:set var="addedWord" value="" />
								 <c:forEach items="${fluencyAddedWordsLt}" var="fluencyAddedWords">
								  <c:if test="${fluencyAddedWords.wordIndex eq i && fluencyAddedWords.wordType eq 1}">
					                   <c:set var="isAddedWord" value="true" />
									    <c:set var="addedWord" value="${fluencyAddedWords.addedWord}" />
									  </c:if>
							 
								 </c:forEach>
						 <c:set var="wd" value="${fn:replace(ql,'\\\"','')}"></c:set>  
						 <c:choose>
						 <c:when test="${regrade eq 'yes'}"> 
					     <span style="color:${color}; text-decoration: ${textDecoration};background-color:${bcolor}"  id="${i}" onclick="strikeText(this,${i},'accuracy');"><c:out value="${ql}"></c:out>
					     </span>
					     </c:when>
					     <c:otherwise>
					       <span style="color:${color}; text-decoration: ${textDecoration};background-color:${bcolor}"  id="${i}" onclick="editBenchmarkstrikeText(this,${i},'accuracy','${regrade}');"><c:out value="${ql}"></c:out>
					     </span>
					     </c:otherwise>
					     </c:choose>
					     
					      <c:if test="${isAddedWord eq 'true' && regrade eq 'no'}">
									<div id='errorIcon${i}' class='fa fa-bolt fa-flip-horizontal' aria-hidden='true' style='color:#ff216c;font-size:20px;' onClick='showCommentBox(${i})'></div>
									<textarea id='comment${i}' name='textarea${i}' style='display:none;font-size: 13px;' onblur='closeComment(${i})' class='box' onkeypress='searchKeyPress(${i})'>${addedWord}</textarea>
								</c:if>
                          <input type="hidden" id="word${i}" value="${wd}">
                          </c:forEach>
                           <input type="hidden" id="firstRange" value=0>
                           <input type="hidden" id="lastRange" value=0>
					 </div>
                   </td>
                     </tr>
                     <tr><td>&nbsp;<br><br></td></tr>
                      <tr><td width='100%' height='100' align='left' valign='middle' class='text1' >
                      <table width='100%' border=0><tr><td width="70%" valign="top" height="100%">
                      <table>
                      <tr><td>
                                     Answer :   
									<audio id="accuracy"
										src="loadUserFile.htm?regId=${studentRegId}&usersFilePath=${usersFilePath}" controls></audio>
									<input type='hidden' id='errosString' name='errorsString' />
								 </td></tr>
								 
						<tr><td><br><br></td></tr>
                                 <tr><td class="tabtxt">
                                 	Words Read&nbsp;&nbsp;<input type='text' id='wordsread' name='wordsread'  value ="${regrade eq 'no' ? fluencyMarks.wordsRead : 0}" maxlength='12' size='12' disabled/> &nbsp;&nbsp;&nbsp;&nbsp;
			                       Errors&nbsp;  &nbsp;
			                       <input type='text' id='errors' name='errors' value ="${regrade eq 'no' ? fluencyMarks.countOfErrors : 0}" maxlength='12' size='12' disabled/> &nbsp;&nbsp;&nbsp;&nbsp;
			                       Words Correct&nbsp;&nbsp;  
			                      <input type='text'  id='correctwords' name='correctwords' value="${regrade eq 'no' ? correctWords : 0}" maxlength='12' size='12' disabled/>&nbsp;&nbsp; &nbsp;&nbsp; 
                                  Accuracy Score&nbsp;&nbsp;  
			                      <input type='text'  id='accuracyPer' name='accuracyPer' value="${regrade eq 'no' ? fluencyMarks.accuracyScore : 0}"  maxlength='12' size='12' disabled/>&nbsp;&nbsp; &nbsp;&nbsp; 
			                       WCPM&nbsp;&nbsp;  
			                      <input type='text'  id='wcpm' name='wcpm' value="${regrade eq 'no' ? fluencyMarks.wcpm : 0}"  maxlength='12' size='12' disabled/>
                                 </td>

                                 </tr>
                                 <c:if test="${gradeTypesId==2}">
                                 <tr><td align="center"> <br><br><div align="center" class="tabtxt">
                                  <c:if test="${regrade eq 'yes'}">
                             		<img src="images/Common/required.gif">Word work Due Date : &nbsp;&nbsp; 
                                  	<input type="text" id="dueDate" name="dueDate" style="width: 120px;" readonly="readonly">
                          		 </c:if></div></td></tr>
                          		</c:if>
                          		 <tr><td><div align="center" class="tabtxt">Comment:</font> &nbsp;
                            <select id="commentId" name="commentId" class="listmenu" style="width:130px;" onchange="setComments('${regrade}')" >
							 	<option value="select comment">select comments</option>
							</select>
							<textarea id='fluencyComment' name='fluencyComment' cols="50" rows="5" onblur="${regrade eq 'no' ? 'autoSaveComments()' : ''}">${fluencyMarks.comment}</textarea></div></td></tr>
                                 <%-- <tr><td><br><br><div align="center" class="tabtxt">Comment: <textarea id='fluencyComment' name='fluencyComment'  cols="50" rows="5" onblur="${regrade eq 'no' ? 'autoSaveComments()' : ''}">${fluencyMarks.comment}</textarea></div></td></tr> --%>
                              </table></td>
                              <td width="40%" height="100%" valign="top" class="tabtxt"><table border=1 id="errorTable" class="des" style="visibility:visible"><tr><td><div class="Divheads"><table><tr><td width="200">Error Word</td><td width="200">Comment</td></tr></table></div>
                                    <div class="DivContents"><table id="dataTable">
                                   <c:set var="j" value="0"></c:set>
                                    <c:set var="n" value="0"></c:set>
                                   <c:forEach items="${benchmarkqList}" var="qlw">
                                       <c:set var="j" value="${j+1}"></c:set> 
                                      <c:forEach items="${errorsList}" var="errx">
                                       <c:if test="${errx.errorsAddress == j}">
                                        <c:if test="${errx.skippedWord ne 'true' and errx.unknownWord ne 'true'}">
                                         <c:set var="color" value="red"></c:set>
                                        <tr id="addedError${errx.errorsAddress}"><td width="200">
                                            <c:out value="${errx.errorWord}" /></td><td width="200"><textarea name="errComment" id="errs:${n}" onkeypress="return checkLength(${errx.errorsAddress},${n},event)" onKeyDown="return setLength(${errx.errorsAddress},${n},event)" onblur="autoSaveComment(${n},${errx.errorsAddress})">${errx.comments}</textarea></td>
                                            <td width="50"><div id="rem:${n}"></div></td>
                                            <c:set var="n" value="${n+1}"></c:set>
                                            </tr>
                                       </c:if> 
                                       </c:if>
									</c:forEach>
                                 </c:forEach>    
                                     </table></div></td></tr>                                            
                                  </table></td><td>&nbsp;</td></tr>
                                </table></td></tr>
                                <tr><td>&nbsp;</td></tr>
                    			 <tr><td width='151' align='center' valign='middle' id="benchmark">
                                  <table width='100%' border='0' cellspacing='0' cellpadding='0'>
                                      <tr><c:choose>
                                       <c:when test="${regrade eq 'yes'}">
                                      <td width='71' align='center' valign='middle'>
                                      <c:if test="${butt == 'yes' }">
                                     	 <input type='submit' value='Submit'  class="button_green" onClick='gradeSelfandPeerAccuracyTest(${studentAssignmentId},${readingTypesId},${gradeTypesId})' />
                                     </c:if>
                                      </td>
                                     </c:when>
                                      <c:otherwise>
                                      <td width='71' align='center' valign='middle'>
                                        <input type='submit' value='Submit'  class="button_green" onClick='saveWCPM(${assignQuestions.assignmentQuestionsId},${readingTypesId},${gradeTypesId})' />
                                      </td>
                                      </c:otherwise>
                                      </c:choose>
                                      
                                        <!--  <td width='3' align='left' valign='middle'>&nbsp;</td>
                                          <td width='40' align='left' valign='middle'></td> -->
                                           <td width='40' align='right' valign='middle'>
                                       <font color="red">Note* : Play audio to get WCPM Score</font>
                                      </td>
                                      </tr></table></td></tr>
                  				 </form:form> 
                        </table>
                        </body>
                        </html>