<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<meta charset="UTF-8">
<head>
<link rel="stylesheet" href="resources/css/math_game_2.css">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/math_assessment.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/math_game.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/Fraction.js"></script>
<%@ include file="../CommonJsp/include_resources.jsp" %>
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
		var baseClassValues = {c1_text:1, c2_text:2, c3_text:3};
		var baseColors = {c1_text:"#04e0b5", c2_text:"#04e0b5", c3_text:"#04e0b5"};
		var successColor = "#00bfff"; 
		var failColor = "#ce2029"; 
		var startTime = 0;
		
		function checkCell(id){
			if(startTime == 0){
				startTime = performance.now();				
			}
			var circle = document.getElementById(id);
			var baseClass = getBaseClass();
			var isDisabled = circle.getAttribute('disabled');
			var undoCheck = $(circle).css('backgroundColor');
			undoCheck = hexc(undoCheck);
			if(undoCheck == "#00fac9" || undoCheck == failColor){
				if(baseClass != circle.className){
					if($(circle).parent()[0].style.backgroundColor){
						$(circle).parent().removeAttr("style");
						$(circle).css("background-color", "transparent");
					}
				}else{
					if(isOnlyColored(baseClass)){
						if($(circle).parent()[0].style.backgroundColor){
							$(circle).parent().removeAttr("style");
							$(circle).css("background-color", "transparent");
						}
						enableOthers(baseClass);
					}
				}
				if(undoCheck == failColor){
					$(circle).removeAttr("disabled");
				}
				return false;
			}
			if(baseClass == ""){
				applyColor(circle)
				disableOthers(circle);
			}else if(circle.className == baseClass){
				applyColor(circle)
				disableOthers(circle);
			}else if(baseClassValues[circle.className]>baseClassValues[baseClass]){
				applyColor(circle)
			}else if(baseClassValues[circle.className]<baseClassValues[baseClass]){
				applyColor(circle)
				disableOthers(circle);
				enableOthers(baseClass);
			}
		}
		function hexc(colorval) {
			var hexcValue = "";
			if(colorval){
			    var parts = colorval.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
			    if(parts != null){
			    	delete(parts[0]);
			        for (var i = 1; i <= 3; ++i) {
			            parts[i] = parseInt(parts[i]).toString(16);
			            if (parts[i].length == 1) parts[i] = '0' + parts[i];
			        }
			        hexcValue = '#' + parts.join('');
			    }  
			}
		    return hexcValue;
		}
		function enableOthers(baseClass){
			var elements = document.getElementsByClassName(baseClass);
			for(var i=0;i<elements.length;i++){
				var bgcolor = $(elements[i]).css('backgroundColor');
				bgcolor = hexc(bgcolor);
				if(bgcolor != successColor){
					$(elements[i]).removeAttr("disabled");
				}				
			}
		}
		
		function disableOthers(element){
			var elements = document.getElementsByClassName(element.className);
			for(var i=0;i<elements.length;i++){
				if(elements[i].id != element.id){
					elements[i].setAttribute('disabled', true);
				}
			}
		}
		
		function getBaseClass(){
			var classNames= ["c1_text","c2_text","c3_text"];
			var baseClass = "";
			for(var j=0;j<classNames.length;j++){
				var baseElements = document.getElementsByClassName(classNames[j]);
				var color = "";		
				for(var i=0;i<baseElements.length;i++){
					color = $("#"+baseElements[i].id).css('backgroundColor');
					color = hexc(color);
					if(color != "" && color && color != successColor){
						baseClass = classNames[j];
						break;
					}
					color="";
				}
				if(color != ""){
					break;
				}
			}
			return baseClass;
		}
		
		function applyColor(circle){
			if($(circle).parent()[0].style.backgroundColor){
				$(circle).parent().removeAttr("style");
				$(circle).css("background-color", "transparent");
			}else{
				$("#"+circle.id).css("background-color", baseColors[circle.className]);
				$("#"+circle.id+"_cls").css("background-color", baseColors[circle.className]);
			}
		}
		
		function validateData(){
			var values;
			var baseClass = getBaseClass();			
			var selectedList=[];
			var finalColor = failColor;
			var totalFranction = "";
			var baseFranction = "";
			for(var i=baseClassValues[baseClass];i<=3;i++){
				values = document.getElementsByClassName("c"+i+"_text");
				for(var j=0;j<values.length;j++){					
					var color = $("#"+values[j].id).css('backgroundColor');
					color = hexc(color);
					if(color!="" && color != successColor){
						selectedList.push(values[j]);
						var num = document.getElementById("val:"+values[j].id).value;	
						if(baseClass == values[j].className){				
							baseFranction = num;
							break;
						}else{
							var fraction = Fraction.add(totalFranction, num);
							totalFranction = fraction.numerator+"/"+fraction.denominator;							
						}
					}
				}
			}
			if(selectedList.length > 2){
				if(baseFranction == totalFranction){
					finalColor = successColor;
				}
				for(var i=0;i<selectedList.length;i++){
					$(selectedList[i]).css("background-color", finalColor);
					$("#"+selectedList[i].id+"_cls").css("background-color",finalColor);
					if(finalColor == successColor){
						var ele = document.getElementById(selectedList[i].id);
						ele.setAttribute('disabled', true);	
						ele.style.pointerEvents = "none"; 
					}
				}	
				if(finalColor == successColor){
					for(var i=0;i<selectedList.length;i++){
						$(selectedList[i]).css("background-color", "#d6eaf3");
						$("#"+selectedList[i].id+"_cls").css("background-color","#d6eaf3");	
						$("#"+selectedList[i].id+"_cls").css("border-width","medium");
					}	
					
				}
	   			setTimeout(function(){
	   				for(var i=0;i<selectedList.length;i++){
						$(selectedList[i]).css("background-color", finalColor);
						$("#"+selectedList[i].id+"_cls").css("background-color",finalColor);
						$("#"+selectedList[i].id+"_cls").css("border-width","1px");
					}
	   				var failCount = document.getElementById("failCount").value;
	   				var mathGameScoreId = document.getElementById("mathGameScoreId").value;
	   				var studentAssignmentId = $("#studentAssignmentId").val();
	   				if(finalColor == successColor){
	   					if(checkCompletion()){
	   						playCompletedSound();
	   						var endTime = performance.now();
	   						var millis = endTime - startTime;
	   						var minutes = Math.floor(millis / 60000);
	   						var seconds = ((millis % 60000) / 1000).toFixed(0);
	   						var time = minutes + ":" + (seconds < 10 ? '0' : '') + seconds;
	   						var mathGearId = document.getElementById("mathGearId").value;	
	   						var gameLevelId = document.getElementById("gameLevelId").value;
	   						var noOfAttempts = document.getElementById("noOfAttempts").value;					
	   						noOfAttempts = Number.parseInt(noOfAttempts);
	   						++noOfAttempts;
	   						$("#loading-div-background1").show();
	   						$.ajax({
	   							url : "submitLevel2.htm",
	   							type: 'POST',
	   							data : "time=" + time+"&failCount="+failCount+"&studentAssignmentId="+studentAssignmentId+"&noOfAttempts="+noOfAttempts+"&mathGearId="+mathGearId+"&gameLevelId="+gameLevelId,
	   							success : function(response) {
	   								$("#loading-div-background1").hide();
	   								systemMessage("Congratulations!! You have unlocked the next level");
	   								$('#mathGameDiv').empty();  
	   	 							$("#mathGameDiv").html(response);
	   							}
	   						});
	   						
	   					}
	   					else{
	   						playCorrectSound();
	   					}
	   					enableOthers(baseClass);
	   				}else{
	   					playErrorSound();
	   					failCount = Number.parseInt(failCount);
	   					if(failCount<2){
	   						document.getElementById("failCount").value = failCount+1;
	   					}else{					
	   						resetLevel();			
	   						var mathGearId = document.getElementById("mathGearId").value;	
	   						var noOfAttempts = document.getElementById("noOfAttempts").value;					
	   						noOfAttempts = Number.parseInt(noOfAttempts);
	   						++noOfAttempts;
	   						$("#loading-div-background1").show();
	   						$.ajax({
	   							url : "updateAttempts.htm",
	   							type: 'POST',
	   							data : "noOfAttempts=" + noOfAttempts+"&mathGameScoreId="+mathGameScoreId+"&mathGearId="+mathGearId,
	   							success : function(data) {
	   								document.getElementById("noOfAttempts").value = noOfAttempts;
	   								$("#loading-div-background1").hide();
	   								alert("Reached maximum attempts");
	   							}
	   						});
	   					}
	   				}
		   		}, 800);
			}else{
				systemMessage("Please select valid cells");
			}
		}
		
		function isOnlyColored(className){
			var status = true;
			if(baseClassValues[className]<3){
				var checkColor = "";
				for(var i=baseClassValues[className]+1;i<=4;i++){
					values = document.getElementsByClassName("c"+i+"_text");
					for(var j=0;j<values.length;j++){
						var checkColor = $("#c"+i+"_sector"+j).css('backgroundColor');
						checkColor = hexc(checkColor);
						if(checkColor != "" && checkColor != successColor){
							status = false;
							break;
						}
					}
					if(!status){
						break;
					}
				}
			}else{
				status = false;
			}
			return status;
		}
		
		function resetLevel(){			
			var classNames= ["c1_text","c2_text","c3_text"];
			for(var i=0;i<classNames.length;i++){
				var classElements = document.getElementsByClassName(classNames[i]);
				for(var j=0;j<classElements.length;j++){
					$(classElements[j]).css("background-color", "transparent");
					if($(classElements[j]).parent()[0].style.backgroundColor)
						$(classElements[j]).parent().removeAttr("style");
					$(classElements[j]).removeAttr("disabled");
					$(classElements[j]).css("pointer-events", "auto");
				}				
			}
			document.getElementById("failCount").value = 0;
			startTime = 0;
		}
		
		function checkCompletion(){
			var classNames= ["c1_text","c2_text","c3_text"];
			var status = true;
			for(var i=0;i<classNames.length;i++){
				var classElements = document.getElementsByClassName(classNames[i]);
				for(var j=0;j<classElements.length;j++){
					var checkColor = $("#"+classElements[j].id).css('backgroundColor');
					checkColor = hexc(checkColor);
					if(checkColor != successColor){
						status = false;
						break;
					}
				}
				if(!status){
					break;
				}
			}
			return status;
		}
		$(document).ready(function(){
			   var content ='<div class="circle circle3">';
			   for(j=1;j<=27;j++){
				   content+='<li class="c3_sector'+j+'" id="c3c'+j+'_cls" onclick ="checkCell(\'c3c'+j+'\')"><span class="c3_text" id="c3c'+j+'">1/27<input type="hidden" id="val:c3c'+j+'" name="val:c3_text" value="1/27" /><input type="hidden" id="sub:c3c'+j+'" name="sub:c3_text" value="done"/></span></li>';
			   }
			   content+='<div class="circle circle2">';
			   for(k=1;k<=9;k++){
				   content+='<li class="c2_sector'+k+'" id="c2c'+k+'_cls" onclick ="checkCell(\'c2c'+k+'\')"><span class="c2_text" id="c2c'+k+'">1/9<input type="hidden" id="val:c2c'+k+'" name="val:c2_text" value="1/9" /><input type="hidden" id="sub:c2c'+k+'" name="sub:c2_text" value="done"/></span></li>';
			   }
			   content+='<div class="circle circle1">';
			   for(l=1;l<=3;l++){
				   content+='<li class="c1_sector'+l+'" id="c1c'+l+'_cls" onclick ="checkCell(\'c1c'+l+'\')"><span class="c1_text" id="c1c'+l+'">1/3<input type="hidden" id="val:c1c'+l+'" name="val:c1_text" value="1/3" /><input type="hidden" id="sub:c1c'+l+'" name="sub:c1_text" value="done"/></span></li>';
			   }
			   content+='</div></div></div></div>';
			   $("#container").html(content);
		});		
</script>
<style type="text/css">
#container {
   position: fixed;
   left: 0;
   width: 98%;
   height: 10%;
   z-index: 1000;
   top:-10%;
}
</style>
</head>
<body>
<table width="100%">
<tr>
	<td width="10%" align="left"><div style="font-size:14px;font-weight: bold;color: #ea4612;" class="fa fa-hand-o-right label" aria-hidden="true" title="Click on a cell with a fraction, then choose two or more fractions in cells that are equivalent to the first fraction chosen. Then press done to see if you are correct. When you complete all the cells correctly you have completed level two.">&nbsp;Directions</div></td>
	<td width="90%" align="left">
		<table width="100%"> 
		  <tr>
			  <td width="50%" align="left"><div class="gear-title">Gear ${mathGameScores.mathGear.mathGearId}: Level ${mathGameScores.gameLevel.gameLevelId}</div></td>
			  <td width="50%" align="right"><div class="button_green" height="52" width="50" onclick="return validateData();" style="margin-right:12em;">Done</div></td>
		  </tr>
		</table>
	</td>
</tr>
<tr><td width="100%" align="center" colspan="2"><div id="container"></div></td></tr>
</table>
	<input type="hidden" id="failCount" value="0" />
	<input type="hidden" id="studentAssignmentId" value="${mathGameScores.studentAssignmentStatus.studentAssignmentId}" /> 
	<input type="hidden" id="mathGameScoreId" value="${mathGameScores.mathGameScoreId}"/>
	<input type="hidden" id="noOfAttempts" value="${mathGameScores.noOfAttempts}"/>
	<input type="hidden" id="mathGearId" value="${mathGameScores.mathGear.mathGearId}"/>
	<input type="hidden" id="gameLevelId" value="${mathGameScores.gameLevel.gameLevelId}"/>
	<div id="loading-div-background1" class="loading-div-background" style="display:none;">
		<div class="loader"></div>
		<div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;">
			<br><br><br><br><br><br><br>Loading....
		</div>
	</div>
</body>
</html>