
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page session="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
function togglePage(thisVar,url, name){
	$.ajax({
		type : "GET",
		url : url+".htm",
		success : function(response) { 
 		 	$("#lessonDiv").empty();
			$("#lessonDiv").append(response);
			$("#tab_title").html(name);
			var current = document.getElementsByClassName("buttonSelect");
		    current[0].className = current[0].className.replace(" buttonSelect", " button");
		    var activeElement = $(thisVar);
		    activeElement[0].className = activeElement[0].className.replace(" button"," buttonSelect");
	 	}
	}); 
}
</script>
</head>

<body>
	<table align="right" cellspacing="0" cellpadding="0">
	
	<tr>
             <td>
				 <ul class="button-group">
					<c:choose>
			     		<c:when test="${userReg.user.userType == 'parent'}">
			     			<li><a href="#" onclick = "togglePage(this, 'ParentChildCurriculum', 'Lessons Curriculum')" class="btn ${(tab == 'lesson')?'buttonSelect leftPill longTitle':'button leftPill longTitle'}">Lessons Curriculum</a></li>
							<li><a href="#" onclick = "togglePage(this, 'childSTEMCurriculum', '${LP_STEM_TAB} Curriculum')" class="btn ${(tab =='stem')?'buttonSelect rightPill longTitle':'button rightPill longTitle'}"> ${LP_STEM_TAB} Curriculum </a></li>
			     		</c:when>
			     		<c:otherwise>
			     			<li><a href="#" onclick = "togglePage(this, 'lessonsCurriculum', 'Lessons Curriculum')"  class="btn ${(tab == 'lesson')?'buttonSelect leftPill longTitle':'button leftPill longTitle'}">Lessons Curriculum</a></li>
							<li><a href="#" onclick = "togglePage(this, 'StudentSTEMCurriculumDiv', '${LP_STEM_TAB} Curriculum')" class="btn ${(tab =='stem')?'buttonSelect rightPill longTitle':'button rightPill longTitle'}">${LP_STEM_TAB} Curriculum </a></li>
			     		</c:otherwise>
			     	</c:choose>
				</ul>
			</td>					
           </tr>
	</table>
	<table  align="left" class="title-pad" style="width: 99.8%;" border="0" cellpadding="0" cellspacing="0" >
		<tr>
			<c:if test="${tab == 'lesson' }">
				<td id = tab_title class="sub-title title-border"  height="40" align="left" valign="middle">
					Lessons Curriculum
				</td>
			</c:if>
			<c:if test="${tab == 'stem' }">
				<td id = tab_title class="sub-title title-border"  height="40" align="left" valign="middle">
					${LP_STEM_TAB} Curriculum
				</td>
			</c:if>			
		</tr>
	</table>
</body>
</html>