<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function showHideLessons(thisvar, uNumber) {
		var lessonContainer = document.getElementById('lessonsDiv'+uNumber);
		if ($(thisvar).html() == "-") {
			lessonContainer.style.display= "none";
			$(thisvar).html("+");
		} else {
			var unitId =  document.getElementById('unitId'+uNumber).value; 
			lessonContainer.style.display= "";
			$(thisvar).html("-");
		}
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
	
	function selectActivity(lessonId, activityNo){
		if(document.getElementById('activitySelected'+lessonId+activityNo).checked){
			document.getElementById('activitySelected'+lessonId+activityNo).checked=false
		}
		else{
			document.getElementById('activitySelected'+lessonId+activityNo).checked=true;
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
	<c:when test="${unitCount!=-1}">
	<table class="des" border=0><tr><td><div class="Divheads"><table><tr><td>Assign Lessons</td></tr></table></div>
		<div class="DivContents"><br>
		<c:forEach var="uNumber" begin="0" end="${unitCount}">
			<div style='padding-bottom: 15px; padding-left: 2cm;font-family:"proxima-nova", "Open Sans","Gill Sans MT","Gill Sans",Corbel,Arial,sans-serif;font-size:14px;font-weight:500; '
				id="unit${uNumber}">
				
				<span id="unitButton${uNumber}"
					onclick="showHideLessons(this, ${uNumber})"
					style="cursor: pointer;">+</span> <font
					color="${createUnits.units[uNumber].userRegistration.user.userTypeid == 2 ? 'blue' : 'black' }">
					${createUnits.units[uNumber].unitName}</font>
				<form:hidden path="createUnits.units[${uNumber}].unitId"
					id="unitId${uNumber}"></form:hidden>
				<!-- ************************* ********************  Lessons divisions ************************  ************************* -->
				<div align="left" id="lessonsDiv${uNumber}" style="display: none;">
					<c:if
						test="${fn:length(createUnits.units[uNumber].lessonsList)  gt 0}">
						<c:forEach var="lNumber" begin="0"
							end="${fn:length(createUnits.units[uNumber].lessonsList) - 1 }">
							<div id="lesson${createUnits.units[uNumber].unitId}${lNumber}"
								style="padding-left: 20px;">
								<span
									id="lessonButton${createUnits.units[uNumber].unitId}${lNumber}"
									style="cursor: pointer;"
									onclick="showHideActivities(this, ${createUnits.units[uNumber].lessonsList[lNumber].lessonId})">+</span>
								<font
									color="${createUnits.units[uNumber].lessonsList[lNumber].userRegistration.user.userTypeid == 2 ? 'blue' : 'black' }">
									${createUnits.units[uNumber].lessonsList[lNumber].lessonName} </font>
								<form:checkbox
									path="createUnits.units[${uNumber}].lessonsList[${lNumber}].lessonSelected"
									id="lessonSelected${createUnits.units[uNumber].unitId}${lNumber}"></form:checkbox>
								<form:hidden
									path="createUnits.units[${uNumber}].lessonsList[${lNumber}].lessonId"
									id="lessonId${createUnits.units[uNumber].unitId}${lNumber}"></form:hidden>
								<!--  ************************* ************************* Activity Divisions  ************************* *************************  -->
								<div style="display: none"
									id="activityDiv${createUnits.units[uNumber].lessonsList[lNumber].lessonId}">
									<c:if
										test="${fn:length(createUnits.units[uNumber].lessonsList[lNumber].activityList)  gt 0}">
										<c:forEach var="aNumber" begin="0"
											end="${fn:length(createUnits.units[uNumber].lessonsList[lNumber].activityList)-1}">
											<div
												id="activity${createUnits.units[uNumber].lessonsList[lNumber].lessonId}${aNumber}"
												style="padding-left: 25px;">
												<font
													color="${createUnits.units[uNumber].lessonsList[lNumber].activityList[aNumber].userRegistration.user.userTypeid == 2 ? 'blue' : 'black' }">
													${createUnits.units[uNumber].lessonsList[lNumber].activityList[aNumber].activityDesc}</font>
												<form:checkbox
													path="createUnits.units[${uNumber}].lessonsList[${lNumber}].activityList[${aNumber}].activitySelected"
													id="activitySelected${createUnits.units[uNumber].lessonsList[lNumber].lessonId}${aNumber}"></form:checkbox>
												<form:hidden
													path="createUnits.units[${uNumber}].lessonsList[${lNumber}].activityList[${aNumber}].activityId"
													id="activity${createUnits.units[uNumber].lessonsList[lNumber].lessonId}${aNumber}"></form:hidden>
											</div>
										</c:forEach>
									</c:if>
								</div>
								<!--  *************************  *************************  Activity Divisions Ends here *************************  *************************  -->
							</div>
						</c:forEach>
					</c:if>
				</div>
				<!--  *************************  *************************  Lessons divisions Ends here *************************  *************************  -->
			</div>
		</c:forEach>
		<div  style="padding-left: 2cm; padding-bottom: 15px" class="tabtxt">
		
			 * Section &nbsp; &nbsp; &nbsp;<select
				name="sectionId" onChange="" id="sectionId" class="listmenu"
				required="required">
				<option value="">select Section</option>
			</select>
		</div>
		<div   style="padding-left: 2cm; padding-bottom: 15px" class="tabtxt">
			* Due Date  &nbsp; &nbsp; &nbsp;<input type="text"
				name="dueDate" id="dueDate" required="required" readonly>
		</div>
		<div  style="padding-left: 2cm">
			<div class="button_green" onclick="assignCurriculum()">Submit Changes</div>
		</div></div></td></tr></table>
	</c:when>
	<c:otherwise>
	No curriculum is created yet
	</c:otherwise>
</c:choose>

