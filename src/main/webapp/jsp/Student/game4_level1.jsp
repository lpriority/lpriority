<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<meta charset="UTF-8">
<head>
<link rel="stylesheet" href="resources/css/math_game_4.css">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/math_assessment.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/math_game.js"></script>
<%@ include file="../CommonJsp/include_resources.jsp" %>
<link rel="stylesheet" href="resources/css/tooltip.css" />
<script>
  $( function() {
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
var c1Array = new Array();
var c2Array = new Array();
var c3Array = new Array();
var c1 = 1;
var c2 = 1;
var c3 = 1;
var startTime = 0;
var endTime=0;
function checkFractionsss(fract){
	var celCount=0,n=0;
	id=fract.id;	
	n=document.getElementById("pos:"+id).value;
	n=parseInt(n);
	var my_text=prompt('Enter text here');
	if(my_text){
		document.getElementById(id+":").innerHTML =my_text;
		var originalFraction=document.getElementById("ans:"+id).value;
	   	var texts=my_text.trim();
	   	if(texts != ""){
	   		++n;
	   		document.getElementById("pos:"+id).value=n;
	   	}	       	
	   	if(originalFraction==texts){
	   		$("#"+id).css("background-color","#00bfff");
	   		var className = document.getElementById(id).getAttribute("name");
	   		var status = checkCircleFilled(className);
	   		if(!status){
	   			var els=document.getElementsByName(className);
		   		for (var i=0;i<els.length;i++) {
		   			els[i].style.backgroundColor = "#d6eaf3";
		   			els[i].style.borderWidth  = "medium";
		   		}
	   			
	   			setTimeout(function(){
	   				for (var i=0;i<els.length;i++) {
			   			els[i].style.backgroundColor = "#00bfff";
			   			els[i].style.borderWidth  = "1px";
			   		}
		   		}, 800);	
	   		} 
	   		document.getElementById(id).style.pointerEvents = "none"; 
			celCount=document.getElementById("noOfCellCompleted").value;
			document.getElementById("noOfCellCompleted").value=parseInt(celCount)+1;
			var totalCells = parseInt(document.getElementById("totalCells").value)-1;
			if(parseInt(celCount) == totalCells){
				playCompletedSound();
				var endTime = performance.now();
				var millis = endTime - startTime;
				var minutes = Math.floor(millis / 60000);
				var seconds = ((millis % 60000) / 1000).toFixed(0);
				var time = minutes + ":" + (seconds < 10 ? '0' : '') + seconds;
				var noOfAttempts=parseInt(document.getElementById("noOfAttempts").value);
				++noOfAttempts;
				var noOfInCorrects=document.getElementById("noOfInCorrects").value;
			    var studentAssignmentId=document.getElementById("studentAssignmentId").value;
			    var mathGearId = document.getElementById("mathGearId").value;	
			    var gameLevelId = document.getElementById("gameLevelId").value;	
			    $("#loading-div-background1").show();
		     	$.ajax({
 					type : "POST",
 					url : "submitLevel1.htm",
 					data : "studentAssignmentId="+studentAssignmentId+"&noOfAttempts="+noOfAttempts+"&noOfInCorrects="+noOfInCorrects+"&timeTaken="+time+"&mathGearId="+mathGearId+"&gameLevelId="+gameLevelId,
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
	   	}else if(n>2){
	   		resetLevel(id);
	   	}else{
	   		playErrorSound();
	   		$("#"+id).css("background-color","#ce2029");
	       	var levlCount=document.getElementById("noOfInCorrects").value;
	       	levlCount=parseInt(levlCount)+1;
	       	document.getElementById("noOfInCorrects").value=levlCount;
	   	}
    }
	
}
function resetLevel(id){
	var node = document.getElementById(id);
	node.setAttribute('disabled', true);
	var noOfAttempts=parseInt(document.getElementById("noOfAttempts").value);
	++noOfAttempts;
	var mathGameScoreId = document.getElementById("mathGameScoreId").value;	
	var mathGearId = document.getElementById("mathGearId").value;	
	var classNames= ["c1_text","c2_text","c3_text"];
	for(var i=0;i<classNames.length;i++){
		var classElements = document.getElementsByClassName(classNames[i]);
		for(var j=0;j<classElements.length;j++){
			if($(classElements[j]).parent()[0].style.backgroundColor)
				$(classElements[j]).parent().removeAttr("style");
			$(classElements[j]).html('');
			document.getElementById("noOfInCorrects").value="0";
			document.getElementById("noOfCellCompleted").value="0";
			$(classElements[j]).css("pointer-events", "auto");
			$(".pos").val('');
			$('.pos').val('0');
		}				
	}
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
	startTime = 0;
}

function checkCircleFilled(className){
	var classElements = document.getElementsByName(className);
	var circleFill = false;
	for(var i=0;i<classElements.length;i++){
		var bgcolor = $(classElements[i]).css('backgroundColor');
		bgcolor = hexc(bgcolor);
		if(bgcolor != "#00bfff"){
			circleFill = true;
			break;
		}
	}	
	return circleFill;
}

function hexc(colorval) {
	var hexcValue = "";
    var parts = colorval.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
    if(parts != null){
    	delete(parts[0]);
        for (var i = 1; i <= 3; ++i) {
            parts[i] = parseInt(parts[i]).toString(16);
            if (parts[i].length == 1) parts[i] = '0' + parts[i];
        }
        hexcValue = '#' + parts.join('');
    }    
    return hexcValue;
}
</script>
<style>
#container {
   position: fixed;
   left: 0;
   width: 98%;
   height: 10%;
   z-index: 1000;
   top:-15%;
}
</style>
</head>
<body>
    <input type="hidden" id="studentAssignmentId" value="${mathGameScores.studentAssignmentStatus.studentAssignmentId}" /> 
	<input type="hidden" id="mathGameScoreId" value="${mathGameScores.mathGameScoreId}"/>
	<input type="hidden" id="noOfAttempts" value="${mathGameScores.noOfAttempts}"/>
	<input type="hidden" id="mathGearId" value="${mathGameScores.mathGear.mathGearId}"/>
	<input type="hidden" id="gameLevelId" value="${mathGameScores.gameLevel.gameLevelId}"/>
	<input type="hidden" id="noOfCellCompleted" value="0">
	<input type="hidden" id="noOfInCorrects" value="0">
	<input type="hidden" id="totalCells" value="52">
<table width="100%">
<tr>
	<td width="10%" align="left"><div style="font-size:14px;font-weight: bold;color: #ea4612;" class="fa fa-hand-o-right label" aria-hidden="true" title="Look at the concentric circles. Type in the appropriate fraction for each cell. When you complete every cell correctly you are done with level one.">&nbsp;Directions</div></td>
	<td width="90%" align="left"><div class="gear-title">Gear ${mathGameScores.mathGear.mathGearId}: Level ${mathGameScores.gameLevel.gameLevelId}</div></td>
</tr>
<tr><td width="100%" align="center" colspan="2">
  <div id="container">
			<div class="circle circle3">
			  <c:forEach var="index" begin="1" end="36"><input type="hidden" class="pos" id="pos:c3c${index}" value="0" /><input type="hidden" id="ans:c3c${index}" value="1/36" />
				  <li class="c3_sector${index}" id="c3c${index}" name="c3c" onClick="checkFractionsss(this)" >
				      <span id="c3c${index}:" class="c3_text"></span>
				  </li>
			  </c:forEach>
			   <div class="circle circle2">
				  <c:forEach var="index" begin="1" end="12"><input type="hidden" class="pos" id="pos:c2c${index}" value="0" /><input type="hidden" id="ans:c2c${index}" value="1/12" />
					  <li class="c2_sector${index}" id="c2c${index}" name="c2c" onClick="checkFractionsss(this)" >
					      <span id="c2c${index}:" class="c2_text"></span>
					  </li>
				  </c:forEach>
				    <div class="circle circle1">
						<c:forEach var="index" begin="1" end="4"><input type="hidden" class="pos" id="pos:c1c${index}" value="0" /><input type="hidden" id="ans:c1c${index}" value="1/4" />
						  <li class="c1_sector${index}" id="c1c${index}" name="c1c" onClick="checkFractionsss(this)" >
						      <span id="c1c${index}:" class="c1_text"></span>
						  </li>
					   </c:forEach>
				    </div>
		      </div>
		   </div>
	</div>
</td></tr>
</table>

	<div id="loading-div-background1" class="loading-div-background" style="display:none;">
		<div class="loader"></div>
		<div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;">
			<br><br><br><br><br><br><br>Loading....
		</div>
	</div>
</body>
</html>