<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function showHideLessons(thisvar, uNumber) {
		var lessonContainer = document.getElementById('lessonsDiv'+uNumber);
		var divClass = $("#"+thisvar.id).attr("class");
		if (divClass == "fa fa-plus-square") {
			lessonContainer.style.display= "";
			$("#"+thisvar.id).removeClass('fa fa-plus-square').addClass('fa fa-minus-square'); 
		} else{
			var unitId =  document.getElementById('unitId'+uNumber).value; 
			lessonContainer.style.display= "none";
			$("#"+thisvar.id).removeClass('fa fa fa-minus-square').addClass('fa fa-plus-square'); 
		}
		setFooterHeight();
	}
	
	function showHideActivities(thisvar, lessonId) {
		var activityContainer = document.getElementById('activityDiv'+lessonId);
		if ($(thisvar).html() == "-") {
			$(thisvar).html("+");
			activityContainer.style.display = "none";
		} else {
			$(thisvar).html("-");
			activityContainer.style.display="";
		}
	}
	
	function selectLesson(unitId, lessonNo){
		if(document.getElementById('lessonSelected'+unitId+lessonNo).checked){
			document.getElementById('lessonSelected'+unitId+lessonNo).checked=false
		}
		else{
			document.getElementById('lessonSelected'+unitId+lessonNo).checked=true;
		}
	}
	
	
	$(function() {
	    $( "#dueDate" ).datepicker({
	    	changeMonth: true,
	        changeYear: true,
	        showAnim : 'clip',
	        minDate: 0
	    });
	});
</script>
<c:choose>
	<c:when test="${stemUnitCount!=-1}">
	<table class="des" border=0><tr><td><div class="Divheads"><table><tr><td>Assign Lessons</td></tr></table></div>
		<div class="DivContents"><table><br>
		<c:forEach var="uNumber" begin="0" end="${stemUnitCount}">
			<div style='padding-bottom: 15px; padding-left: 2cm;'
				id="unit${uNumber}">
				
				<font color="${stemcurriculum.stemUnits[uNumber].userRegistration.user.userTypeid == 2 ? 'blue' : 'black' }" class="txtStyle" style="font-size:14.4px;">
						${stemcurriculum.stemUnits[uNumber].stemUnitName}
						<%-- <input type="checkbox" name="stemUnitId" id="stemUnitId${uNumber}" value="${stemcurriculum.stemUnits[uNumber].stemUnitId}"></input> --%> 
						<input type="checkbox" id="stemUnitId${uNumber}" name="stemUnitId" value="${stemcurriculum.stemUnits[uNumber].stemUnitId}"  class="checkbox-design">  
               					<label for="stemUnitId${uNumber}" class="checkbox-label-design"></label>
					</font>
				   <form:hidden path="stemcurriculum.stemUnits[${uNumber}].stemUnitId"
					id="unitId${uNumber}"></form:hidden>
							
			</div>
		</c:forEach>
		<div style="padding-left: 2cm; padding-bottom: 15px" class="tabtxt">
		
			 * Section &nbsp; &nbsp; &nbsp;<select
				name="sectionId" onChange="" id="sectionId" class="listmenu"
				required="required">
				<option value="">select Section</option>
			</select>
		</div>
		<div   style="padding-left: 2cm; padding-bottom: 15px" class="tabtxt">
			* Due Date  &nbsp; &nbsp; &nbsp;<input type="text"
				name="dueDate" id="dueDate" required="required">
		</div>
		<div  style="padding-left: 2cm">
			<div class="button_green" onclick="assignStemCurriculum()">Submit Changes</div>
		</div></table></div></td></tr></table>
	</c:when>
	<c:otherwise>
	No curriculum is created yet
	</c:otherwise>
</c:choose>

