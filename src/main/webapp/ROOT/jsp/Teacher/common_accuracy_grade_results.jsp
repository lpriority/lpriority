<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
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
<link href="resources/css/setColor.css" rel="stylesheet" type="text/css" />
<%@ include file="../CommonJsp/include_resources.jsp" %>
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
var startSkipDisabled=false;
var endSkipDisabled=true;
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
		var contextStat=$('#contextStat').val();
		if(contextStat==1){
		 e.preventDefault();
  		 window.speechSynthesis.cancel();
	     word = $("#"+this.id).text();
	     wordNum = parseInt(this.id);
	     word = word.replace(/[_\W]+/g, "");
	     $.contextMenu({
	         selector: '.context-menu-nine', 
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
	        	            	 if(key == 'skippedWord'){
	        	            		var obj = $("#"+wordNum)[0];
	        	            		chkErrExists(obj, wordNum,"skipped");
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
	        	            		 if(regradeStat=="no")
	        	 	         			editStrikeFluencyText(obj, wordNum, 'unKnownWord','accuracy');
	        	 	         		 else
	        	 	         			strikeFText(obj, wordNum, 'unKnownWord','accuracy');
	        	 	         		//autoSaveErrorWord(val,wordNum,key);
	        	 	        	 }
	        	            	 else if(key == 'addComment'){
	        	            		var obj = $("#"+wordNum)[0];
	        	            		chkErrExists(obj, wordNum,"insert");
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
	        	 	            	    removeFluencyAddedWord(wordNum,value,1);
	        	 	            	}
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
	        	 	         		    				 document.getElementById(j).style.color = '#91a002';
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
	        	          			        var k=document.getElementById("errors").value;
	        	          			        if(k=="")
	        	          			        	k=0;
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
	        	 	         		
	        	 	         	
	        	 	         	} else if(key == 'startSkip'){
	        	 	         		
	        	 	         		document.getElementById("firstSkip").value=wordNum;
	        	 	         		startSkipDisabled = true;
	        	 	         		endSkipDisabled = false;
	        	 	         		if(document.getElementById("startSkip"))
	        	 	         			$('#startSkip').remove();
	        	 	         		if(document.getElementById("endSkip"))
	        	 	         			$('#endSkip').remove();
	        	 	         		/* $('#'+wordNum).before("<div id='startSkip' class='fa fa-share' aria-hidden='true' style='color:#0c7ad1;font-size:20px;position:absolute;z-index:51;margin-top:-0.7em'></div>"); */
	        	 	         	}else if(key == 'endSkip'){
	        	 	         		var firstSkip = parseInt(document.getElementById("firstSkip").value);
	        	 	         		var lastSkip = parseInt(wordNum);
	        	 	         		var j = 0;
	        	 	         		count=0;
	        	 	         		var listSkip=[];
	        	 	         		var listSkipAddress=[];
	        	 	         		if(firstSkip != 0 && lastSkip != 0){
	        	 		         		if(firstSkip > lastSkip){
	        	 			         		document.getElementById("lastSkip").value = 0;
	        	 	         				alert("End Skip should be grater than Start Skip");
	        	 	         			}else{
	        	 	         				for(j=firstSkip;j<=lastSkip;j++){
	        	 	         					 var obj = $("#"+j)[0];
	        	 	         					 chkErrExists(obj, j,"skipped");
	        	 	         					 strikeFText(obj, j, 'skippedWord','accuracy');
	        	 	         		    		 document.getElementById(j).style.color = 'blue';
	        	 	         		    		if(regradeStat=="no"){
       		        	 	        			var val=document.getElementById("word"+j).value;
       		        	 	        			val = val+'@';
       		        	 	        			checkSelectRange(j);
       		        	 	        			listSkip[count]=val;
       		        	 	        			listSkipAddress[count]=j;
       		        	 	        			count++;
       		        	 	        	     }
	        	 	         		    		        		    	  
	        	 	         		       }
	        	 	         				 if(regradeStat=="no")
	        	 	         				 autoSaveSkippedSentence(listSkip,listSkipAddress,"skippedWord","accuracy",regradeStat);
	        	 	         				 else
	        	 	         				 autoDisplayErrorCount("accuracy");	 
	        	 	         				 
	        	 	         				 
	        	 	         		      }
	        	 		         		  if(regradeStat=="no"){
	        	 		         		    var percent=document.getElementById("accuracyPer").value;
	        	 		         		    document.getElementById("accuracyMarks:"+assignmentQuestionId+":"+readingTypesId).value=percent;
	        	 		         		  }
	        	         	         		document.getElementById("lastSkip").value = wordNum; 
											startSkipDisabled = false;
											endSkipDisabled = true;
	        	        	         		if(document.getElementById("endSkip"))
	        	     	         			$('#endSkip').remove();
	        	        	         		  /*  $('#'+wordNum).before("<div id='endSkip' class='fa fa-reply' aria-hidden='true' style='color:#0c7ad1;font-size:20px;position:absolute;z-index:51;margin-top:-0.7em'></div>"); */
	        	 	         			} 
	        	 	         		}
	        	 	         	/* }else if(key == 'quit'){
	        	 	         		return 'context-menu-icon context-menu-icon-quit';
	        	 	         	} */
	        	            },
	        	            items: {}	            	
	        	        };
		        	         		
		        			var comment = document.getElementById("comment"+wordNum);
		        			if(!comment)
		        				 options.items["addComment"] = {name: "Insert Word", icon: "fa-commenting"};
		        			else
		        				 options.items["removeComment"] = {name: "Remove Insert Word", icon: "fa-comment-o"};
		        			
		        			
		        			options.items["skippedWord"] = {name: "Omit Word", icon: "fa-scissors"};
	        			    options.items.step1 = "---------";
		        		
	        	 	     options.items["startPoint"] = {name: "Start Point", icon: "fa-caret-right", disabled: startPointDisabled};
	        			 options.items["endPoint"] = {name: "End Point", icon: "fa-caret-left" , disabled: endPointDisabled};
	        			 options.items["startSkip"] = {name: "Skip From", icon: "fa-caret-right", disabled: startSkipDisabled};
	        			 options.items["endSkip"] = {name: "Skip To", icon: "fa-caret-left" , disabled: endSkipDisabled};
	        			 options.items.step2 = "---------";
	        			 options.items["quit"] = {name: "Quit", icon: "fa-times"};
	        	        return options;
	        	    }
	     });
		}else{
			return false;
		}
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
	var regradeStat=$('#regradeStatus').val();
	var value = $("#comment"+wordNum).val();
	if(value){
		addedWordsArray = $.grep(addedWordsArray, function(value) {
		    return value.indexOf(wordNum + "$") < 0;
		});
		addedWordsArray.push(wordNum + "$"+value);
		$("#comment"+wordNum).html(value);
		$("#comment"+wordNum).fadeOut(500);
		if(regradeStat=="no")
			saveFluencyAddedWord(wordNum,value,1);
		
	}else if(!value){
     	$('#comment'+wordNum).remove();
     	$('#errorIcon'+wordNum).remove();
     	document.getElementById("errors").value = --document.getElementById("errors").value ;
     	addedWordsArray = $.grep(addedWordsArray, function(value) {
 		    return value.indexOf(wordNum + "$") < 0;
 		});
     	autoDisplayErrorCount();
     	if(regradeStat=="no"){
    		var value = $("#comment"+wordNum).val();
		    var wordType=1;
     	removeFluencyAddedWord(wordNum,value,wordType);
 	}     	
 }else{
		$('#comment'+wordNum).remove();
     	$('#errorIcon'+wordNum).remove();
     	document.getElementById("errors").value = --document.getElementById("errors").value ;
  	}
	autoDisplayErrorCount("accuracy");
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
                  <input type="hidden" name="audioDuration" id="audioDuration" value="" />
                  </c:if>
                  <input type="hidden" name="contextStat" id="contextStat" value="1" />
                  </td></tr>
                 <tr><td width="100%">
					<div id="contextMenuDiv"  class="context-menu-nine btn btn-neutral" style="text-align:justify">
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
					     <span style="color:${color}; text-decoration: ${textDecoration};background-color:${bcolor}"  id="${i}" onclick="strikeText(this,${i},'accuracy','errorWord');" class="myelement"><c:out value="${ql}"></c:out>
					     </span>
					     </c:when>
					     <c:otherwise>
					      <span style="color:${color}; text-decoration: ${textDecoration};background-color:${bcolor}"  id="${i}" onclick="editBenchmarkstrikeText(this,${i},'accuracy','${regrade}');" class="myelement"><c:out value="${ql}"></c:out>
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
                           <input type="hidden" id="firstSkip" value=0>
                           <input type="hidden" id="lastSkip" value=0>
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
                                <tr><td class="text1"><table align="center" class="des" id="accuracyTable"><tr><td>
                              <c:set var="reg" value="0" />
                             <c:if test="${regrade eq 'yes'}">
                              <c:set var="reg" value="1" />
                             </c:if>
                             <table class="Divheads" width="100%">
                             <tr id="sco1"><td width="180" align="left">GradeType</td><td width="180" align="left">Words Read</td><td width="180" align="left">Errors</td>
                             <td width="130"> Words Correct</td><td width="190" align="center">Accuracy Score</td><td width="190"> WCPM(Note* : Play audio to get WCPM Score)</td></tr></table>
                             <br>
                             <table class="DivContents tabtxt" id="scoreTable">
                             <tr id="sco2"><td width="150"><input type="radio" class="radio-design" id="teacher" name="gradeType" onClick="getFluencyScoresByGradeType(${gradeTypesId},${reg},${assignmentTypeId})" checked disabled/><label for="teacher" class="radio-label-design">Teacher</label></td>
                             <td width="170"><input type='text' id='wordsread' name='wordsread'  value ='${fluencyMarks.wordsRead}' disabled/></td>
                             <td width="170"><input type='text' id='errors' name='errors' value ='${fluencyMarks.countOfErrors}' disabled/></td>
                             <td width="195" align="center"><input type='text'  id='correctwords' name='correctwords' value='${correctWords}' disabled/></td>
                             <td width="150" align="center"><input type="text" id="accuracyPer" name="accuracyPer" value="${fluencyMarks.accuracyScore}" disabled/></td>
                              <td width="190" align="center"><input type="text" id="wcpm" name='wcpm' value='${fluencyMarks.wcpm}'  disabled/></td>                    
                             </tr>
                             </table>
                             <table style="visibility:hidden" id="colorDis" class="tabtxt">
                             <tr><td>&nbsp;</td></tr>
                             <tr><td width="100">
                             <div class='foo teacher'></div>Teacher</td><td width="120"><div class='foo student'></div>Student</td>
                             <td width="120"><div class='foo peer'></div>Peer</td><td width="120"><div class='foo common'></div>Common</td>
                             <td width="120"> <div class='foo skipped'></div>Omit Word</td><td width="120"><div class='fa fa-bolt fa-flip-horizontal' aria-hidden='true' style='color:#FF0000;font-size:20px;'></div>Insert Word</td>
                            <td width="140"> <div class='foo unknown'></div>&nbsp;&nbsp;Unknown Word</td></tr>
                            <!--  <tr><td width="120"><div class='fa fa-bolt fa-flip-horizontal' aria-hidden='true' style='color:#FF0000;font-size:20px;'></div>&nbsp;Insert Word</td> 
                             </tr> -->
                             </table>
                          
                       	</td></tr>
                       	</table><br><br>
                       	<table align="center"><%-- <tr><td><c:if test="${regrade eq 'yes'}"> 
                             	<img src="images/Common/required.gif">Word work Due Date : &nbsp;&nbsp; 
                                  <input type="text" id="dueDate" name="dueDate" style="width: 120px;" readonly="readonly">
                          </c:if>  </td></tr> --%>
                          <tr><td><div align="center" class="tabtxt">Comment:</font> &nbsp;
                            <select id="commentId" name="commentId" class="listmenu" style="width:130px;" onchange="setComments('${regrade}')" >
							 	<option value="select comment">select comments</option>
							</select>
							<textarea id='fluencyComment' name='fluencyComment' cols="50" rows="5" onblur="${regrade eq 'no' ? 'autoSaveComments()' : ''}">${fluencyMarks.comment}</textarea></div></td></tr>
                          </table>
                          </td></tr>  
                              </table></td>
                              <td width="40%" height="100%" valign="top" id="accErrTab" class="tabtxt"><table border=1 id="errorTable" class="des" style="visibility:visible"><tr><td><div class="Divheads"><table><tr><td width="200">Error Word</td><td width="200">Teacher Comment</td></tr></table></div>
                                    <div class="DivContents"><table id="dataTable">
                                   <c:set var="j" value="0"></c:set>
                                   <c:set var="n" value="0"></c:set>
                                   <c:forEach items="${benchmarkqList}" var="qlw">
                                       <c:set var="j" value="${j+1}"></c:set> 
                                      <c:forEach items="${errorsList}" var="errx">
                                       <c:if test="${errx.errorsAddress == j}">
                                        <c:if test="${errx.skippedWord ne 'true'}">
                                         <c:set var="color" value="red"></c:set>
                                        <tr id="addedError${errx.errorsAddress}"><td width="200">
                                            <c:out value="${errx.errorWord}" /></td>
                                           <td> <textarea name="errComment" id="errs:${n}" onkeypress="return checkLength(${errx.errorsAddress},${n},event)" onKeyDown="return setLength(${errx.errorsAddress},${n},event)" onblur="autoSaveComment(${n},${errx.errorsAddress})">${errx.comments}</textarea>
                                        	</td><td width="50"><div id="rem:${n}"></div></td></tr>
                                        	 <c:set var="n" value="${n+1}"></c:set>  
                                       </c:if> 
                                       </c:if>
									</c:forEach>
             	            </c:forEach>    
                                     </table></div></td></tr>                                            
                                  </table></td><td>&nbsp;</td></tr>
                                </table></td></tr>
                                 <tr><td>&nbsp;</td></tr>
                       			<tr><td width='151' align='center' valign='middle' id="benchmark">
         				  <table width='114' border='0' cellspacing='0' cellpadding='0'>
	                           <tr>
	                           <c:if test="${regrade eq 'yes'}">
	                           <td width='71' align='center' valign='middle'>
	                           <c:if test="${butt == 'yes'}">
	                             <div class="button_green" onClick='submitAccuracyTest(${readingTypesId},${gradeTypesId})'>Submit</div>
	                          </c:if>
	                           </td>
	                           </c:if>
	                           <td width='3' align='left' valign='middle'>
	                           <div id="showsAcc" class="button_green" onClick="showAllFluencyScores('${regrade}',${assignmentTypeId})" style="width:93px;">Show All Scores</div>
	                           <input type="hidden" id="showFluency" value="no" />
	                           </td>
	                           <td width='3' align='left' valign='middle'>
	                            <c:if test="${butt != 'yes'}"> 
	                         	<div class="button_green" onClick='saveWCPM(${assignQuestions.assignmentQuestionsId},${readingTypesId},${gradeTypesId})'>Submit</div>
	                           </c:if>
	                           </td>
	                            <td width='3' align='left' valign='middle'>
	                            <c:if test="${regrade eq 'new' or regrade eq 'yes'}"> 
	                           <div id="retest" class="button_green" onClick="retestFluency(${assignQuestions.studentAssignmentStatus.studentAssignmentId},${readingTypesId},${gradeTypesId},${assignQuestions.assignmentQuestionsId})" style="width:40px;">ReTest</div>
	                           </c:if>
	                           <input type="hidden" id="showFluency" value="no" />
	                           </td>
	                           <td width='40' align='left' valign='middle' id="errorTable"></td>
	                           </tr>
                           </table></td></tr>
                  				 </form:form> 
                        </table>
                        </body>
                        </html>