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
<script src="resources/javascript/common/js_speech_synthesis.js"></script>
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script src="resources/javascript/contextmenu/jquery.contextMenu.js"></script>
<script src="resources/javascript/contextmenu/jquery.ui.position.js"></script>
<link rel="stylesheet" href="resources/css/contextmenu/jquery.contextMenu.css">
<link href="resources/css/setColor.css" rel="stylesheet" type="text/css" />
<%@ include file="../CommonJsp/include_resources.jsp" %>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css">
<style type="text/css">
.ui-dialog > .ui-widget-content {
	background: white;
}
.ui-widget {
	font-family:Georgia, Times, 'Times New Roman', serif;
	font-size: 1em;
}
.ui-widget-header {
	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #00e3ff), color-stop(1, #00b7d0) );
	border:1px solid #00d8f5;
	text-shadow:0 1px 1px rgb(0, 48, 53);
	margin:-.12em;
}
.ui-widget-overlay {
	background:rgba(81, 139, 146, 0.66);
}
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

$(document).ready(function(){
	
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
	
	//console.debug("inside ready function common_fluency_grade table");
	 $('#contextMenuDiv').on('contextmenu', 'span' ,function(e) {
		 var contextStat=$('#contextStat').val();
		 
		 if(contextStat==1){
   		 e.preventDefault();
   		 window.speechSynthesis.cancel();
	     word = $("#"+this.id).text();
	     wordNum = parseInt(this.id);
	     word = word.replace(/[_\W]+/g, "");
	     $.contextMenu({
	         selector: '.context-menu-one', 
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
	        	            	 if(key == 'unKnownWord'){
	        	 	         		var obj = $("#"+wordNum)[0];
	        	 	         		strikeFluencyText(obj, wordNum, 'unKnownWord','fluency',regradeStat);
	        	 	        	 }else if(key == 'getWord'){
	        	 	         		//document.getElementById("speak_button").value="Play";
	        	 	         		spellIt(word);
	        	 	        	}else if(key == 'skippedWord'){
	        	 	        		var obj = $("#"+wordNum)[0];
	        	 	        		chkErrorExists(obj, wordNum,"skipped");
	        	 	        		  if(regradeStat=="no"){
	        	 	        			checkSelectRange(wordNum);
	        	 	        			var val=document.getElementById("word"+wordNum).value;
	        	 	        			val = val+'@';
	        	 	        			editStrikeFluencyText(obj, wordNum, 'skippedWord','fluency',regradeStat);
	        	 	        		   
	        	 	        		 }else{
	        	 	        			strikeFluencyText(obj, wordNum, 'skippedWord','fluency',regradeStat);
	        	 	        		 }
	        	 	        		 autoDisplayErrorCount("fluency");
	        	 	        			        	 	        		
	        	 	         	}else if(key == 'onlineDictionary'){
	        	 	         		var url ="http://www.thefreedictionary.com/"+word;
	        	 	         		var a = document.createElement("a");
	        	 	         	    a.target = "_blank";
	        	 	         	    a.href = url;
	        	 	         	    a.click();
	        	 	            }else if(key == 'addComment'){
	        	 	            	var obj = $("#"+wordNum)[0];
	        	 	            	chkErrorExists(obj, wordNum,"insert");
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
	        	 	          	   
	        	 	            	var value = $("#comment"+wordNum).val();
	        	 	            	var wordType=1;
		        	 	            removeFluencyAddedWord(wordNum,value,wordType,regradeStat);
	        	 	                autoDisplayErrorCount("fluency");
	        	 	            	
	        	 	            }else if(key == 'addSelfCorrect'){
	        		            	var obj = $("#"+wordNum)[0];
	        		            	chkErrorExists(obj, wordNum,"selfCorrect");
	        		            	$('#'+wordNum).css('color', '#D7D700');
	        		            	if(!document.getElementById("selfCorrect"+wordNum)){
	        			            	 $('#'+wordNum).after("<textarea id='selfCorrect"+wordNum+"' name='textarea"+wordNum+"' style='display:none;font-size: 13px;' onblur='closeSelfCorrect("+wordNum+")' class='box' onkeypress='searchKeyPress1("+wordNum+")'></textarea>");
	        			             }
	        		            	showSelfCorrectBox(wordNum);
	        		            	if(!document.getElementById("errorIcon1"+wordNum))
	        		            	  $('#'+wordNum).after("<div id='errorIcon1"+wordNum+"' class='fa fa-bolt fa-flip-horizontal' aria-hidden='true' style='color:#D7D700;font-size:20px;' onClick='showSelfCorrectBox("+wordNum+")'></div>");
	        		            	
	        		            }else if(key == 'removeSelfCorrect'){
	        		            	var obj = $("#"+wordNum)[0];
	        		            	strikeSelfCorrectText(obj, wordNum, 'addSelfCorrect');
	        		            	$('#selfCorrect'+wordNum).remove();
	        		            	$('#errorIcon1'+wordNum).remove();
	        		            	selfCorrectWordsArray = $.grep(selfCorrectWordsArray, function(value) {
	        		        		    return value.indexOf(wordNum + "$") < 0;
	        		        		});
	        		            	if(regradeStat=="no" || regradeStat=="new"){
	        		            	var wordType=2;
	        		            	obj.style.color='Black';
	        	 	            	removeFluencyAddedWord(wordNum,value,wordType,regradeStat);
	        	 	            	
	        		            	}
	        		            }else if(key == 'addProsody'){
	        		            	  var obj = $("#"+wordNum)[0];
	        		            	  chkErrorExists(obj, wordNum,"prosody");
	        		            	  if(!document.getElementById("prosody"+wordNum)){
		        	 		            	 $('#'+wordNum).after("<textarea id='prosody"+wordNum+"' name='textarea"+wordNum+"' style='display:none;font-size: 13px;' onblur='closeProsody("+wordNum+")' class='box' onkeypress='searchKeyPress2("+wordNum+")'></textarea>");
		        	 		            	 document.getElementById("errors").value = ++document.getElementById("errors").value;
		        	 	                 }
	        		            	  showProsody(wordNum);
	        	 	                 	if(!document.getElementById("errorIcon2"+wordNum))
	        	 	            	  $('#'+wordNum).after("<div id='errorIcon2"+wordNum+"' class='fa fa-hand-paper-o' aria-hidden='true' style='color:#DC7633;font-size:20px;' onClick='showProsody("+wordNum+")'></div>");
	        		            }else if(key == 'removeProsody'){
	        		            	$('#prosody'+wordNum).remove();
	        	 	            	$('#errorIcon2'+wordNum).remove();
	        	 	            	document.getElementById("errors").value = --document.getElementById("errors").value ;
	        	 	            	prosodyArray = $.grep(prosodyArray, function(value) {
	        	 	        		    return value.indexOf(wordNum + "$") < 0;
	        	 	        		});
	        	 	            	var wordType=3;
	        	 	            	removeFluencyAddedWord(wordNum,value,wordType,regradeStat);
	        	 	            	autoDisplayErrorCount("fluency");
	        	 	            	
	        		            } else if(key == 'startPoint'){
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
	        	          			        wordsReadByStudent=count;
	        	          			        k=document.getElementById("errors").value;
	        	          			        var total=count-k;
	        	          			        document.getElementById("correctwords").value=total;
	        	         	         		document.getElementById("lastRange").value = wordNum;
	        	          			        startPointDisabled = false;
	        	        	         		endPointDisabled = true;
	        	        	         		console.log("regradeStat="+regradeStat);
	        	        	         		if(regradeStat=="new" || regradeStat=="yes"){ 
	        	        	         			console.log("aaaaaaaaaa");
	        	        	         		 var assignmentQuestionId=$("#questions").val();
	        	        	         	     var readingTypesId=$("#readingTypesId").val();
	        	        	         	 	 var gradeTypesId=$("#gradeTypesId").val();
	        	        	         	 	 var wordReadCount=document.getElementById("wordsread").value;
	        	        	         	 	 if(k=="")
	        	        	         	 		 k=0;
	        	        	         	 	 $.ajax({
	        	        	         	 		type : "GET",
	        	        	         	 		url : "autoSaveWordCount.htm",
	        	        	         	 		data : "assignmentQuestionId=" +assignmentQuestionId +"&readingTypesId=" + readingTypesId+"&gradeTypesId="+gradeTypesId+"&errRead="+k+"&wordsRead="+wordReadCount+"&totalRead="+total,
	        	        	         	 		success : function(response) {
	        	        	         	 		 }
	        	        	         	 	});	
	        	        	         	 	 

	        	        	         		 }
	        	        	         		if(document.getElementById("endPoint"))
	        	     	         			$('#endPoint').remove();
	        	        	         		   $('#'+wordNum).before("<div id='endPoint' class='fa fa-reply' aria-hidden='true' style='color:#0c7ad1;font-size:20px;position:absolute;z-index:51;margin-top:-0.7em'></div>");
	        	 	         			} 
	        	 	         		}
	        	 	         		
	        	 	         	
	        	 	         	}else if(key == 'startSkip'){
	        	 	         		 document.getElementById("firstSkip").value=wordNum;
	        	 	         		startSkipDisabled = true;
	        	 	         		endSkipDisabled = false;
	        	 	         		if(document.getElementById("startSkip"))
	        	 	         			$('#startSkip').remove();
	        	 	         		if(document.getElementById("endSkip"))
	        	 	         			$('#endSkip').remove();
	        	 	         		  /* $('#'+wordNum).before("<div id='startSkip' class='fas fa-quote-left' aria-hidden='true' style='color:#0c7ad1;font-size:20px;position:absolute;z-index:51;margin-top:-0.7em'></div>"); */  
	        	 	         	}else if(key == 'endSkip'){
	        	 	         		var firstSkip = parseInt(document.getElementById("firstSkip").value);
	        	 	         		var lastSkip = parseInt(wordNum);
	        	 	         		//var j = 0;
	        	 	         		count=0;
	        	 	         		var listSkip=[];
	        	 	         		var listSkipAddress=[];
	        	 	         		if(firstSkip != 0 && lastSkip != 0){
	        	 		         		if(firstSkip > lastSkip){
	        	 			         		document.getElementById("lastSkip").value = 0;
	        	 	         				alert("End Skip should be greater than Start Skip");
	        	 	         			}else{
	        	 	         				 for(var j=firstSkip;j<=lastSkip;j++){
	        	 	         					 var obj = $("#"+j)[0];
	        	 	         					 chkErrorExists(obj, j,"skipped");
	        	 	         					 strikeFText(obj, j, 'skippedWord','fluency');
	        	 	         		    		 document.getElementById(j).style.color = 'blue';
	        	 	         		    		//if(regradeStat=="no" || regradeStat=="new"){
	        	 	         		    			
        		        	 	        			var val=document.getElementById("word"+j).value;
        		        	 	        			val = val+'@';
        		        	 	        			if(regradeStat=="no")
        		        	 	        			checkSelectRange(j);
        		        	 	        			listSkip[count]=val;
        		        	 	        			listSkipAddress[count]=j;
        		        	 	        			count++;
	        	 	         				// }
	        	 	         		    		        		    	  
	        	 	         		    	 }
	        	 	         				//if(regradeStat=="no" || regradeStat=="new"){
	        	 	         				 autoSaveSkippedSentence(listSkip,listSkipAddress,"skippedWord","fluency",regradeStat);//} */
	        	 	         		      }
	        	 		         		 	document.getElementById("lastSkip").value = wordNum; 
											startSkipDisabled = false;
											endSkipDisabled = true;
	        	        	         		if(document.getElementById("endSkip"))
	        	     	         			$('#endSkip').remove();
	        	        	         	  /* $('#'+wordNum).before("<div id='endSkip' class='fas fa-quote-right' aria-hidden='true' style='color:#0c7ad1;font-size:20px;position:absolute;z-index:51;margin-top:-0.7em'></div>"); */ 
	        	 	         			} 
	        	 	         		}else if(key == 'quit'){
	        	 	         		return 'context-menu-icon context-menu-icon-quit';
	        	 	         	}
	        	            },
	        	            items: {}	            	
	        	        };
		        	    var gradePage = document.getElementById("gradePage").value;
		        		if(gradePage == 'student'){
		        			 options.items["unKnownWord"] = {name: "Unknown Word", icon: "fa-question-circle"};
		        			 options.items["getWord"] = {name: "Listen Word", icon: "fa-microphone"};
		        			 options.items["onlineDictionary"] = {name: "Online Dictionary", icon: "fa-search"};
		        			 options.items.step1 = "---------";
		        			 
		        		}else if(gradePage == 'teacher'){
		        			var comment = document.getElementById("comment"+wordNum);
		        			if(!comment)
		        				 options.items["addComment"] = {name: "Added Word", icon: "fa-commenting"};
		        			else
		        				 options.items["removeComment"] = {name: "Remove Added Word", icon: "fa-comment-o"};
		        			
		        			var selfCorrect=document.getElementById("selfCorrect"+wordNum);
		        			if(!selfCorrect)
		        				 options.items["addSelfCorrect"] = {name: "Self Corrected Word", icon: "fa-commenting"};
		        			else
		        				 options.items["removeSelfCorrect"] = {name: "Remove Self Correct", icon: "fa-comment-o"};
		        			
		        			var prosody=document.getElementById("prosody"+wordNum);
		        			if(!prosody)
		        				 options.items["addProsody"] = {name: "Add Prosody", icon: "fa-hand-paper-o"};
		        			else
		        				 options.items["removeProsody"] = {name: "Remove Prosody", icon: "fa-hand-paper-o"};
		        			
		        			options.items["skippedWord"] = {name: "Skipped Word", icon: "fa-scissors"};
		        			options.items.step1 = "---------";
		        		}
		        		
		        		 options.items["startSkip"] = {name: "Skip From", icon: "fa-caret-right", disabled: startSkipDisabled};
	        			 options.items["endSkip"] = {name: "Skip To", icon: "fa-caret-left" , disabled: endSkipDisabled};
	        	 	     options.items["startPoint"] = {name: "Start Point", icon: "fa-caret-right", disabled: startPointDisabled};
	        			 options.items["endPoint"] = {name: "End Point", icon: "fa-caret-left" , disabled: endPointDisabled};
	        			 options.items.step2 = "---------";
	        			 options.items["quit"] = {name: "Quit", icon: "fa-times"};
	        	        return options;
	        	    }
	     });
			}else{
				return false;
			}
	 });
	 
	var benchmarkTestFilePath = document.getElementById("usersFilePath").value;
	var studentRegId= document.getElementById("regId").value;
	var gradePage = document.getElementById("gradePage").value;
	if(gradePage == 'student'){	 
	}
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
		//if(regradeStat=="no"){
			var wordType=1;
			saveFluencyAddedWord(wordNum,value,wordType,regradeStat);
		//}
	}
	else if(!value){
		     	$('#comment'+wordNum).remove();
		     	$('#errorIcon'+wordNum).remove();
		     	document.getElementById("errors").value = --document.getElementById("errors").value ;
		     	addedWordsArray = $.grep(addedWordsArray, function(value) {
		 		    return value.indexOf(wordNum + "$") < 0;
		 		});
		     	autoDisplayErrorCount();
		     	var value = $("#comment"+wordNum).val();
     		    var wordType=1;
	         	removeFluencyAddedWord(wordNum,value,wordType,regradeStat);
	         	
	     }
	else{
		$('#comment'+wordNum).remove();
     	$('#errorIcon'+wordNum).remove();
     	document.getElementById("errors").value = --document.getElementById("errors").value ;
	 }
	autoDisplayErrorCount("fluency");
	
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
function showSelfCorrectBox(wordNum){
	var regradeStat=$('#regradeStatus').val();
	if(regradeStat=="no"){
	checkSelectRange(wordNum);
	}
	 var posX = $('#'+wordNum).position().left, posY = $('#'+wordNum).position().top;
	//$('#'+wordNum).remove();
	    posX = (posX - 40)+"px";
		posY = (posY - 15)+"px";
		$("#selfCorrect"+wordNum).css({left:posX,top:posY});
		$("#selfCorrect"+wordNum).fadeIn(500);
		$("#selfCorrect"+wordNum).focus();
	}
	function closeSelfCorrect(wordNum){
		var regradeStat=$('#regradeStatus').val();
		var value = $("#selfCorrect"+wordNum).val();
		if(value){
			selfCorrectWordsArray = $.grep(selfCorrectWordsArray, function(value) {
			    return value.indexOf(wordNum + "$") < 0;
			});
			selfCorrectWordsArray.push(wordNum + "$"+value);
			$("#selfCorrect"+wordNum).html(value);
			$("#selfCorrect"+wordNum).fadeOut(500);
			//if(regradeStat=="no"){
				var wordType=2;
				saveFluencyAddedWord(wordNum,value,wordType,regradeStat);
			//}
		} else if(!value){
			var obj = $("#"+wordNum)[0];
			$('#selfCorrect'+wordNum).remove();
        	$('#errorIcon1'+wordNum).remove();
        	obj.style.color='Black';
        	obj.style.textDecoration="none";
		   	selfCorrectWordsArray = $.grep(selfCorrectWordsArray, function(value) {
				    return value.indexOf(wordNum + "$") < 0;
		 		});
		    	autoDisplayErrorCount();
		     	//if(regradeStat=="no" ){
		     	var wordType=2;
		     	removeFluencyAddedWord(wordNum,value,wordType,regradeStat);
		     //	}
	} else{
			var obj = $("#"+wordNum)[0];
			$('#selfCorrect'+wordNum).remove();
        	$('#errorIcon1'+wordNum).remove();
        	obj.style.color='Black';
        	obj.style.textDecoration="none";
		}
		autoDisplayErrorCount("fluency");
		
	}
	function searchKeyPress1(wordNum){
		event = window.event;
	    if(event.keyCode == 13){
	    	event.preventDefault();
	    	var value = $("#selfCorrect"+wordNum).val();
	    	if(value){
	    		selfCorrectWordsArray = $.grep(selfCorrectWordsArray, function(value) {
	    		    return value.indexOf(wordNum + "$") < 0;
	    		});
	    		selfCorrectWordsArray.push(wordNum + "$"+value);
	    	}
	    	$("#selfCorrect"+wordNum).html(value);
	    	$("#selfCorrect"+wordNum).fadeOut(500);
	        return false;
	    }
	    return true;
	}
	function showProsody(wordNum){
		var regradeStat=$('#regradeStatus').val();
		if(regradeStat=="no"){
		checkSelectRange(wordNum);
		}
		 var posX = $('#'+wordNum).position().left, posY = $('#'+wordNum).position().top;
		    posX = (posX - 40)+"px";
			posY = (posY - 15)+"px";
			$("#prosody"+wordNum).css({left:posX,top:posY});
			$("#prosody"+wordNum).fadeIn(500);
			$("#prosody"+wordNum).focus();
		}
		function closeProsody(wordNum){
			var regradeStat=$('#regradeStatus').val();
			var value = $("#prosody"+wordNum).val();
			if(value){
				prosodyArray = $.grep(prosodyArray, function(value) {
				    return value.indexOf(wordNum + "$") < 0;
				});
				prosodyArray.push(wordNum + "$"+value);
				$("#prosody"+wordNum).html(value);
				$("#prosody"+wordNum).fadeOut(500);
				//if(regradeStat=="no"){
				var wordType=3;
				saveFluencyAddedWord(wordNum,value,wordType,regradeStat);
				//}
			}
			 else if(!value){
				   	$('#prosody'+wordNum).remove();
				   	$('#errorIcon2'+wordNum).remove();
				   	document.getElementById("errors").value = --document.getElementById("errors").value ;
				    	prosodyArray = $.grep(prosodyArray, function(value) {
						    return value.indexOf(wordNum + "$") < 0;
				 		});
				    	autoDisplayErrorCount();
				     	//if(regradeStat=="no" ){
				     	var wordType=3;
				     	removeFluencyAddedWord(wordNum,value,wordType,regradeStat);
				     //	}
			} 
			else{
				$('#prosody'+wordNum).remove();
	            $('#errorIcon2'+wordNum).remove();
	            document.getElementById("errors").value = --document.getElementById("errors").value ;
			}
			autoDisplayErrorCount("fluency");
			
		}
		function searchKeyPress2(wordNum){
			event = window.event;
		    if(event.keyCode == 13){
		    	event.preventDefault();
		    	var value = $("#prosody"+wordNum).val();
		    	if(value){
		    		prosodyArray = $.grep(prosodyArray, function(value) {
		    		    return value.indexOf(wordNum + "$") < 0;
		    		});
		    		prosodyArray.push(wordNum + "$"+value);
		    	}
		    	$("#prosody"+wordNum).html(value);
		    	$("#prosody"+wordNum).fadeOut(500);
		        return false;
		    }
		    return true;
		}
	function setComments(){
		var comment = $("#commentId option:selected").text();
		if(comment != 'select comment'){
			$('#fluencyComment').val($('#fluencyComment').val()+comment);
			$('#commentId').css({color:'black'})
		}		
	}	
</script>
</head>
<body>
<div class="container-fluid des">
  <div class="row" style="padding-bottom:20px;">
    <div class="col-md-1"> </div>
    <div class="col-md-10">
      <form:form name="StudentBenchmarkForm" modelAttribute="assignmentQuestions">
        <div class="row">
          <div class="col-md-12 tabtxt text-center">
            <input type="hidden" name="regId" value="${studentRegId}" id="regId">
            <input type="hidden" name="usersFilePath" value="${usersFilePath}" id="usersFilePath">
            <font size="3"><b>Fluency Test</b></font></div>
          <div class="col-md-12 tabtxt"> <b>Fluency Passage</br>
            </br>
            </b>
            <input type="hidden" name="questions" id="questions" value="${assignQuestions.assignmentQuestionsId}"/>
            <input type="hidden" name="lessonId" id="lessonId" value="${lessonId}"/>
            <input type="hidden" name="csId" id="csId" value="${csId}"/>
            <input type="hidden" name="studentId" id="studentId" value="${studentId}"/>
            <input type="hidden" name="assignmentTitle" id="assignmentTitle" value="${assignmentTitle}"/>
            <input type="hidden" name="hwAssignmentId" id="hwAssignmentId" value=0 />
            <input type="hidden" name="benchQuestions" id="benchQuestions" value="${benchmarkqList}"/>
            <input type="hidden" name="gradePage" id="gradePage" value="${gradePage}"/>
            <input type="hidden" name="passage" id="passage" value="${passage}"/>
            <input type="hidden" name="regradeStatus" id="regradeStatus" value="${regrade}" />
            <input type="hidden" name="readingTypesId" id="readingTypesId" value="${readingTypesId}" />
            <input type="hidden" name="gradeTypesId" id="gradeTypesId" value="${gradeTypesId}" />
            <input type="hidden" name="wordsRead" id="wordsRead" value="${wordsRead}" />
            <input type="hidden" name="contextStat" id="contextStat" value="1" />
          </div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <div id="contextMenuDiv"  class="context-menu-one btn-neutral" style="text-align:justify">
              <c:set var="i" value="0">
              </c:set>
              <c:forEach items="${benchmarkqList}" var="ql">
                <c:set var="i" value="${i+1}">
                </c:set>
                <c:set var="color" value="#000">
                </c:set>
                <c:set var="textDecoration" value="none">
                </c:set>
                <c:choose>
                  <c:when test="${i<=wordsRead  && (regrade eq 'no' or regrade eq 'new')}">
                    <c:set var="bcolor" value="#9AFEFF">
                    </c:set>
                  </c:when>
                  <c:otherwise>
                    <c:set var="bcolor" value="">
                    </c:set>
                  </c:otherwise>
                </c:choose>
                <c:forEach items="${errorsList}" var="errIndex">
                  <c:if test="${errIndex.errorsAddress == i}">
                    <c:choose>
                      <c:when test="${errIndex.unknownWord=='true'}">
                        <c:set var="color" value="#91a002">
                        </c:set>
                      </c:when>
                      <c:when test="${errIndex.skippedWord=='true'}">
                        <c:set var="color" value="blue">
                        </c:set>
                      </c:when>
                      <c:otherwise>
                        <c:set var="color" value="red">
                        </c:set>
                      </c:otherwise>
                    </c:choose>
                    <c:set var="textDecoration" value="line-through">
                    </c:set>
                  </c:if>
                </c:forEach>
                <c:set var="isAddedWord" value="false" />
                <c:set var="addedWord" value="" />
                <c:set var="isselfCorrectWord" value="false" />
                <c:set var="isProsody" value="false" />
                <c:forEach items="${fluencyAddedWordsLt}" var="fluencyAddedWords">
                  <c:choose>
                    <c:when test="${fluencyAddedWords.wordIndex eq i && fluencyAddedWords.wordType eq 1}">
                      <c:set var="isAddedWord" value="true" />
                      <c:set var="addedWord" value="${fluencyAddedWords.addedWord}" />
                    </c:when>
                    <c:when test="${fluencyAddedWords.wordIndex eq i && fluencyAddedWords.wordType eq 2}">
                      <c:set var="isselfCorrectWord" value="true" />
                      <c:set var="addedWord" value="${fluencyAddedWords.addedWord}" />
                      <c:set var="color" value="#DADA00">
                      </c:set>
                    </c:when>
                    <c:when test="${fluencyAddedWords.wordIndex eq i && fluencyAddedWords.wordType eq 3}">
                      <c:set var="isProsody" value="true" />
                      <c:set var="addedWord" value="${fluencyAddedWords.addedWord}" />
                    </c:when>
                  </c:choose>
                </c:forEach>
                <c:set var="wd" value="${fn:replace(ql,'\\\"','')}">
                </c:set>
                <c:choose>
                  <c:when test="${regrade eq 'yes' or regrade eq 'new'}"> <span style="color:${color}; text-decoration: ${textDecoration};background-color:${bcolor}"  id="${i}" onclick="strikeFluencyText(this,${i},'errorWord','fluency','${regrade}');" class="myelement">
                    <c:out value="${ql}">
                    </c:out>
                    </span> </c:when>
                  <c:otherwise> <span style="color:${color}; text-decoration: ${textDecoration};background-color:${bcolor}"  id="${i}" onclick="editStrikeFluencyText(this,${i},'errorWord','fluency','${regrade}');" class="myelement">
                    <c:out value="${ql}">
                    </c:out>
                    </span> </c:otherwise>
                </c:choose>
                <c:if test="${isAddedWord eq 'true'  && (regrade eq 'no' or regrade eq 'new') }">
                  <div id='errorIcon${i}' class='fa fa-bolt fa-flip-horizontal' aria-hidden='true' style='color:#ff216c;font-size:20px;' onClick='showCommentBox(${i})'></div>
                  <textarea id='comment${i}' name='textarea${i}' style='display:none;font-size: 13px;' onblur='closeComment(${i})' class='box' onkeypress='searchKeyPress(${i})'>${addedWord}</textarea>
                </c:if>
                <c:if test="${isselfCorrectWord eq 'true'  && (regrade eq 'no' or regrade eq 'new')}">
                  <div id='errorIcon1${i}' class='fa fa-bolt fa-flip-horizontal' aria-hidden='true' style='color:#DADA00;font-size:20px;' onClick='showSelfCorrectBox(${i})'></div>
                  <textarea id='selfCorrect${i}' name='textarea${i}' style='display:none;font-size: 13px;' onblur='closeSelfCorrect(${i})' class='box' onkeypress='searchKeyPress1(${i})'>${addedWord}</textarea>
                </c:if>
                <c:if test="${isProsody eq 'true'  && (regrade eq 'no' or regrade eq 'new')}">
                  <div id='errorIcon2${i}' class='fa fa-hand-paper-o' aria-hidden='true' style='color:#DC7633;font-size:20px;' onClick='showProsody(${i})'></div>
                  <textarea id='prosody${i}' name='textarea${i}' style='display:none;font-size: 13px;' onblur='closeProsody(${i})' class='box' onkeypress='searchKeyPress2(${i})'>${addedWord}</textarea>
                </c:if>
                <input type="hidden" id="word${i}" value="${wd}" />
              </c:forEach>
              <input type="hidden" id="firstRange" value=0>
              <input type="hidden" id="lastRange" value=0>
              <input type="hidden" id="firstSkip" value=0>
              <input type="hidden" id="lastSkip" value=0>
            </div>
          </div>
        </div>
        <div class="row" style="padding:8px 0px;">
          <div class="col-md-5 text1 text-right" style="padding-top: 18px;"><b>Answer :</b></div>
          <div class="col-md-7 text1">
            <audio id="fluency" src="loadUserFile.htm?regId=${studentRegId}&usersFilePath=${usersFilePath}" controls></audio>
            <input type='hidden' id='errosString' name='errorsString' />
          </div>
        </div>
        <div class="row">
          <div class="col-md-12 text1 des" id="fluencyTable">
            <c:set var="reg" value="0" />
            <c:if test="${regrade eq 'yes' or regrade eq 'new'}">
              <c:set var="reg" value="1" />
            </c:if>
            <div class="row Divheads">
              <div class="col-md-3">Grade Type</div>
              <div class="col-md-3">Words Read </div>
              <div class="col-md-3">Errors</div>
              <div class="col-md-3">Words Correct </div>
            </div>
            <div class="DivContents tabtxt" id="scoreTable">
              <div class="row" id="sco2" style="padding-top:10px;">
                <div class="col-md-3">
                  <input type="radio" class="radio-design" id="teacher" name="gradeType" onClick="getFluencyScoresByGradeType(${gradeTypesId},${reg},${assignmentTypeId})" checked disabled/>
                  <label for="teacher" class="radio-label-design">Teacher</label>
                </div>
                <div class="col-md-3">
                  <input type='text' id='wordsread' name='wordsread'  value ='${fluencyMarks.wordsRead}' disabled/>
                </div>
                <div class="col-md-3">
                  <input type='text' id='errors' name='errors' value ='${fluencyMarks.countOfErrors}' disabled/>
                </div>
                <div class="col-md-3">
                  <input type='text'  id='correctwords' name='correctwords' value='${correctWords}' disabled/>
                </div>
              </div>
            </div>
            <div style="visibility:hidden" id="colorDis" class="tabtxt">
              <div class="row">
                <div class="col-md-2">
                  <div class='foo teacher'></div>
                  Teacher</div>
                <div class="col-md-2">
                  <div class='foo student'></div>
                  Student</div>
                <div class="col-md-2">
                  <div class='foo peer'></div>
                  Peer</div>
                <div class="col-md-2">
                  <div class='foo common'></div>
                  Common</div>
                <div class="col-md-2">
                  <div class='foo unknown'></div>
                  Unknown Word</div>
                <div class="col-md-2">
                  <div class='foo skipped'></div>
                  Skipped Word</div>
              </div>
              <div class="row">
                <div class="col-md-2">
                  <div class='fa fa-bolt fa-flip-horizontal' aria-hidden='true' style='color:#FF0000;font-size:20px;'></div>
                  &nbsp;Added Word </div>
                <div class="col-md-2">
                  <div class='fa fa-bolt fa-flip-horizontal' aria-hidden='true' style='color:#DADA00;font-size:20px;'></div>
                  &nbsp;Self Corrected Word</div>
                <div class="col-md-2">
                  <div class='fa fa-hand-paper-o' aria-hidden='true' style='color:#DC7633;font-size:20px;'></div>
                  &nbsp;Prosody</div>
              </div>
            </div>
          </div>
        </div>
        <div class="row" style="padding:15px 0px;">
          <div class="col-md-12 text1">
            <c:if test="${regrade eq 'new' or regrade eq 'yes'}"> <img src="images/Common/required.gif">Word work Due Date : &nbsp;&nbsp;
              <input type="text" id="dueDate" name="dueDate" style="width: 120px;" readonly>
            </c:if>
          </div>
          <div class="col-md-12 text1">
            <div class="col-md-4"><font class="tabtxt">Comment:</font> &nbsp;
              <select id="commentId" name="commentId" class="listmenu" style="width:130px;" onchange="setComments()" >
                <option value="select comment">select comments</option>
              </select>
            </div>
            <div class="col-md-8">
              <textarea id='fluencyComment' name='fluencyComment' cols="50" rows="5" onblur="${regrade eq 'no' ? 'autoSaveComments()' : ''}">${fluencyMarks.comment}</textarea>
            </div>
          </div>
        </div>
        <div class="row" id="homeWorkDiv" style="display:none;"> </div>
        <div class="row">
          <div class="col-md-12 text1 text-center" id="benchmark">
            <input type="hidden" id="showFluency" value="no" />
            <input type="hidden" id="showFluency" value="no" />
            <c:if test="${butt == 'yes'}">
              <div class="button_green" onClick='submitFluencyTest(${readingTypesId},${gradeTypesId})'>Submit</div>
            </c:if>
            <div id="shows" class="button_green" onClick="showAllFluencyScores('${regrade}',${assignmentTypeId})" style="width:150px;font-size:1.1em">Show All Scores</div>
            <c:if test="${regrade eq 'new' or regrade eq 'yes'}">
              <div id="retest" class="button_green" onClick="retestFluency(${studentAssignmentId},${readingTypesId},${gradeTypesId},${assignQuestions.assignmentQuestionsId})" style="width:40px;">ReTest</div>
            </c:if>
            <div id="errorTable"></div>
          </div>
        </div>
      </form:form>
    </div>
  </div>
</div>
</body>
</html>
