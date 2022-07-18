<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<head>
<script type="text/javascript" src="resources/javascript/TeacherJs/math_game.js"></script>
<link rel="stylesheet" href="resources/javascript/bootstrap-3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/tooltip.css" />
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/mathGameService.js"></script>
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
  } );
</script>
<script>
</script>
</head>
<body>
	<c:set var="class1" value="progress-bar-info active"></c:set>
	<c:set var="class2" value="progress-bar-info active"></c:set>
	<c:set var="class3" value="progress-bar-info active"></c:set>
	<c:if test="${mathGameScores.gameLevel.gameLevelId > 1}"><c:set var="class1" value="progress-bar-success"></c:set></c:if>
	<c:if test="${mathGameScores.gameLevel.gameLevelId > 2}"><c:set var="class2" value="progress-bar-success"></c:set></c:if>
	<c:if test="${mathGameScores.gameLevel.gameLevelId > 3}"><c:set var="class3" value="progress-bar-success"></c:set></c:if>
	<c:if test = "${mathGameScores.mathGear.mathGearId > 0}">
		<div class="gear-title" style="margin-left: 8em;text-align: left;margin-top: 0.5em;">Gear ${mathGameScores.mathGear.mathGearId}</div>
  	</c:if>
  	<div class="progress" style="width:80%;height: 25px;">
	    <div class="progress-bar ${class1} progress-bar-striped"  role="progressbar" aria-valuenow="33" aria-valuemin="0" aria-valuemax="100" style="width:33%" title="Look at the concentric circles. Type in the appropriate fraction for each cell. When you complete every cell correctly you are done with level one.">
	       		<font color="white" size="3">Level 1</font>
    	</div>
    	<div class="progress-bar ${class2} progress-bar-striped" role="progressbar" aria-valuenow="33" aria-valuemin="0" aria-valuemax="100" style="width:33%" title="Click on a cell with a fraction, then choose two or more fractions in cells that are equivalent to the first fraction chosen. Then press done to see if you are correct. When you complete all the cells correctly you have completed level two.">
       		<font color="white" size="3">Level 2</font>
    	</div>
    	<div class=" progress-bar ${class3} progress-bar-striped" role="progressbar" aria-valuenow="34" aria-valuemin="0" aria-valuemax="100" style="width:34%" title="Choose colors to color each cell of the concentric circles in a symmetrical pattern. When you have colored all the cells, try spinning the &quot;gear&quot; at different speeds and see how your eye and brain mix colors. Do some colors mix, some not? Which circles blend colors? Are there other things you observe?">
      		<font color="white" size="3">Level 3</font>
    	</div>
  	</div>
	<div id="accuracyTestReady" style="text-align: center; display: block;padding-top:13em;">
		<input type="hidden" id="studentAssignmentId" value="${studentAssignmentId}"/>
		<input type="hidden" id="gameLevelId" value="${mathGameScores.gameLevel.gameLevelId}"/>
		<input type="hidden" id="mathGearId" value="${mathGameScores.mathGear.mathGearId}"/>
		<c:choose>
			<c:when test="${mathGameScores.gameLevel.gameLevelId == 1}">
				<div id="begin" name="begin" class="button_blue_round" align="right" onclick="beginGame()">Let's Begin</div>
			</c:when>
			<c:when test="${mathGameScores.gameLevel.gameLevelId == 2}">
				<div id="begin" name="begin" class="button_blue_round" align="right" onclick="beginGame()">Begin Level 2</div>
			</c:when>
			<c:when test="${mathGameScores.gameLevel.gameLevelId == 3}">
				<div id="begin" name="begin" class="button_blue_round" align="right" onclick="beginGame()">Begin Level 3</div>
			</c:when>
			<c:otherwise>
				<div id="begin" name="begin" class="button_blue_round" align="right" onclick="beginGame()">Finished</div>
			</c:otherwise>
		</c:choose>
		
	</div>
	<div id="loading-div-background1" class="loading-div-background" style="display:none;">
		<div class="loader"></div>
		<div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;">
			<br><br><br><br><br><br><br>Loading....
		</div>
	</div>
</body>
</html>


