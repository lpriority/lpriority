<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<meta charset="UTF-8">
<head>
<title>Game Level 3</title>
<%@ include file="../CommonJsp/include_resources.jsp" %>
<link rel="stylesheet" href="resources/css/math_game_4.css">
<script type="text/javascript" src="resources/javascript/TeacherJs/math_assessment.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/math_game.js"></script>
<link rel="stylesheet" href="resources/css/common/colorPick.css">
<script src="resources/javascript/common/colorPick.js"></script>
<script src="resources/javascript/common/jQuery.print.min.js"></script>
<link rel="stylesheet" href="resources/css/tooltip.css" />
<script>
	   $(function() {
	    $( document ).tooltip({
	      position: {
	        my: "right bottom-20",
	        at: "right top",
	        using: function( position, feedback ) {
	          $( this ).css( position );
	          $( "<div>" )
	            .addClass( "arrow" )
	            .addClass( feedback.vertical )
	            .addClass( feedback.horizontal )
	            .appendTo( this );
	        }
	      }
	    });
	   });
$(document).ready(function(){
	  var content ='<div class="circle circle3">';
	   for(j=1;j<=36;j++){
		   content+='<li class="c3_sector'+j+'" id="c3c'+j+'_cls" onclick="checkColorPicker()"><span class="c3_text" id="c3c'+j+'">1/36</span></li>';
	   }
	   content+='<div class="circle circle2">';
	   for(k=1;k<=12;k++){
		   content+='<li class="c2_sector'+k+'" id="c2c'+k+'_cls" onclick="checkColorPicker()"><span class="c2_text" id="c2c'+k+'">1/12</span></li>';
	   }
	   content+='<div class="circle circle1">';
	   for(l=1;l<=4;l++){
		   content+='<li class="c1_sector'+l+'" id="c1c'+l+'_cls" onclick="checkColorPicker()"><span class="c1_text" id="c1c'+l+'">1/4</span></li>';
	   }
	   content+='</div></div></div></div>';
	   $("#container").html(content);   
	   for(j=1;j<=36;j++){
		   $(".c3_sector"+j).colorPick({
			   'initialColor' : '',
				'onColorSelected': function() {
					this.element.css({'backgroundColor': this.color, 'color': this.color});
					fillColorCallback(this.element[0].id);
				}
		   });
	   }
	   
	   for(k=1;k<=12;k++){
		   $(".c2_sector"+k).colorPick({
			   'initialColor' : '',
				'onColorSelected': function() {
					this.element.css({'backgroundColor': this.color, 'color': this.color});
					fillColorCallback(this.element[0].id);
				}
		   });
	   }
	   
	   for(l=1;l<=4;l++){
		   $(".c1_sector"+l).colorPick({
			   'initialColor' : '',
				'onColorSelected': function() {
					this.element.css({'backgroundColor': this.color, 'color': this.color});
					fillColorCallback(this.element[0].id);
				}
		   });
	   }
});
var colorPickArray = [];	
function checkColorPicker(){
	colorPickArray = $('.colorPicker').map(function() {
	  return this;
	}).get();
	if(colorPickArray.length > 0){
		$("div.colorPicker:first").remove();
	 }	
}

var c1Array = new Array();
var c2Array = new Array();
var c3Array = new Array();
var c1 = 1;
var c2 = 1;
var c3 = 1;
var startTime = performance.now();
function fillColorCallback(cId){	
	if(cId){
		if (cId.match("^c1")) {
			if ($.inArray(cId, c1Array) == -1){
				 c1Array.push(cId);
				 if(c1Array.length == 4){
					 playCorrectSound(); 
				 }
				 c1 = c1+1;
			}
		}else if(cId.match("^c2")){
			if ($.inArray(cId, c2Array) == -1){
				 c2Array.push(cId);
				 if(c2Array.length == 12){
					 playCorrectSound(); 
				 }
				 c2 = c2+1;
			}
		}else if(cId.match("^c3")){
			if ($.inArray(cId, c3Array) == -1){
				 c3Array.push(cId);
				 if(c3Array.length == 36){
					 playCorrectSound(); 
				 }
				c3 = c3+1;
			}
		}
		if(c1Array.length == 4 && 
		   c2Array.length == 12 &&
		   c3Array.length == 36){
			playCompletedSound();
			$("#printDiv").show();
			$("#nextTest").show();
		}
	}
}

function goForNextGearTest(){
	var mathGameScoreId = document.getElementById("mathGameScoreId").value;
	var mathGearId = document.getElementById("mathGearId").value;
	var gameLevelId = document.getElementById("gameLevelId").value;
	var studentAssignmentId = document.getElementById("studentAssignmentId").value;
	var endTime = performance.now();
	var millis = endTime - startTime;
	var minutes = Math.floor(millis / 60000);
	var seconds = ((millis % 60000) / 1000).toFixed(0);
	var time = minutes + ":" + (seconds < 10 ? '0' : '') + seconds;
	$.ajax({
		url : "submitLevel3.htm",
		type: 'POST',
		data : "time=" + time+"&mathGameScoreId="+mathGameScoreId+"&studentAssignmentId="+studentAssignmentId+"&mathGearId="+mathGearId+"&gameLevelId="+gameLevelId,
		success : function(response) {
				var testCount = document.getElementById("mathGameAssignRow").value;
				$('#row'+testCount).remove();
				systemMessage("Congratulations!! You have Completed test successfully !!");
				$('#mathGameDiv').empty();  
				$("#mathGameDiv").html(response);		
		}
	});	
}

var deg = 0;
function slowSpin(){
	var circle = document.getElementsByClassName('circle3');
	circle[0].style.WebkitAnimationName = "slow"; // Code for Chrome, Safari, and Opera
	circle[0].style.animationName = "slow";
}
function medSpin(){
	var circle = document.getElementsByClassName('circle3');
	circle[0].style.WebkitAnimationName = "medium"; // Code for Chrome, Safari, and Opera
	circle[0].style.animationName = "medium";
}
function fastSpin(){
	var circle = document.getElementsByClassName('circle3');
	circle[0].style.WebkitAnimationName = "fast"; // Code for Chrome, Safari, and Opera
	circle[0].style.animationName = "fast";
}
function stopSpin(){
	var circle = document.getElementsByClassName('circle3');
	circle[0].style.WebkitAnimationName = "stop"; // Code for Chrome, Safari, and Opera
	circle[0].style.animationName = "stop";
}
</script>
<style>
#container {
   position: fixed;
   left: 0;
   width: 98%;
   height: 10%;
   z-index: 900;
   top:-10%;
}
.circle{
 background:#ffffff;
}
.circle4 li{
background-color: #d5e0e4;
}
.circle4 li:hover {
  background-color: #d6eaf3;
}
.circle3 li{
background-color: #dce4e6;
}
.circle3 li:hover {
  background-color:#d6eaf3;
}
.circle2 li{
background-color: #d5e0e4;
}
.circle2 li:hover {
  background-color: #d6eaf3;
}
.circle1 li{
background-color: #cdd7dc;
}
.circle1 li:hover {
  background-color: #d6eaf3;
}
body{
  -webkit-print-color-adjust:exact;
}
</style>
</head>
<body>
    <input type="hidden" id="studentAssignmentId" value="${mathGameScores.studentAssignmentStatus.studentAssignmentId}" /> 
	<input type="hidden" id="mathGameScoreId" value="${mathGameScores.mathGameScoreId}"/>
	<input type="hidden" id="mathGearId" value="${mathGameScores.mathGear.mathGearId}"/>
	<input type="hidden" id="gameLevelId" value="${mathGameScores.gameLevel.gameLevelId}"/>
	<table width="100%">
		<tr>
			<td width="100%" align="left" colspan="2">
			<div class="button_green"  onclick="stopSpin()">Stop</div>
			<div class="button_green"  onclick="slowSpin()">Slow</div>
			<div class="button_green"  onclick="medSpin()">Medium</div>
			<div class="button_green"  onclick="fastSpin()">Fast</div>
			<span id="printDiv" style="padding-left: 1em;float: right;display:none;">Print<span class="button_white" style="font-size:24px;color:black;" onclick="printThisDiv('container')"><i class="fa fa-print"></i></span></span>
			<div class="button_green"  id="nextTest" onclick="goForNextGearTest()" style="padding-left: 1em;float: right;display:none;">Submit Level3</div>
			</td>
		</tr>
		<tr>
			<td width="10%" align="left"><div style="font-size:14px;font-weight: bold;color: #ea4612;" class="fa fa-hand-o-right label" aria-hidden="true" title="Choose colors to color each cell of the concentric circles in a symmetrical pattern. When you have colored all the cells, try spinning the &quot;gear&quot; at different speeds and see how your eye and brain mix colors. Do some colors mix, some not? Which circles blend colors? Are there other things you observe?">&nbsp;Directions</div></td>
			<td width="90%" align="left"><div class="gear-title">Gear ${mathGameScores.mathGear.mathGearId}: Level ${mathGameScores.gameLevel.gameLevelId}</div></td>
		</tr>
		<tr><td width="100%" align="center" colspan="2"><div id="container"></div></td></tr>
	</table>
<div id="loading-div-background1" class="loading-div-background" style="display:none;background:rgb(142, 228, 238);">
   <div class="loader"></div>
   <div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;">
		<br><br><br><br><br><br><br>Please wait....
   </div>
</div>
</body>
</html>