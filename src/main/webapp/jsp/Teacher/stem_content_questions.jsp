<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script>
	$(document).ready(function () {
		var selectedStemAreas = document.getElementsByName('selectedStemAreas');
		for(var i=0;i<selectedStemAreas.length; i++)
			stemAreaIdLt.push(parseInt(selectedStemAreas[i].value));
	});

	$(function() {
		  var stemUnitId = parseInt($('#stemUnitId').val());
		  var currentTab = $('.is-active').attr('id');
		   $("input[type=text], textarea").bind("focusin", function() {
			  var currentElement = $(this).attr('id');
			  var updatedVal = $(this).val();
			  synchronizeTab(stemUnitId, currentTab, currentElement, 'Yes', updatedVal, 'focusin');
			});
		    $("input[type=text], textarea").bind("focusout", function() {
		    	var currentElement = $(this).attr('id');
				var updatedVal = $(this).val();
			    synchronizeTab(stemUnitId, currentTab, currentElement, 'Yes', updatedVal, 'focusout');
			});
		    getAllUsersOnContent(stemUnitId, currentTab);
	}); 
</script>
<table style="width: 40%; font-size: 18px; text-align: center;"
	align="center">
	<tr><td width="5%" colspan="2" >
		<c:forEach items="${stemAreasLt}" var="stemArea">
			<input type="hidden" id="stemArea${stemArea.stemAreaId}" value="${stemArea.stemArea }">
			<span class="button_green"
				onclick="addStemArea(${stemArea.stemAreaId},'${stemArea.stemArea}')"
				id="${stemArea.stemAreaId}">${stemArea.stemArea}</span>
		</c:forEach></td>
	</tr>
</table>

<table width="100%">
	<tr>
		<td width="38%" class="label" align="right"><input type="hidden"
			id="totStemArea" name="totStemArea" value=0>Selected ${LP_STEM_TAB}
			Areas &nbsp;<span class="tabtxt"
			style="color: blue; font-weight: bold;">(Click on button to
				Apply Strands)</span> :</td>
		<td width="62%" align="left"><div id="selectedStemAreasDiv">
				<c:forEach items="${stemUnit.uniStemAreasLt}"
					var="addedStemArea" varStatus="index">
					<c:set var="divId" value="${fn:replace(addedStemArea.stemAreas.stemArea,' ', '')}Div"></c:set>
					<c:set var="contentQueDivId"
						value="${fn:replace(addedStemArea.stemAreas.stemArea, ' ', '' )}ContentMainDiv"></c:set>
					
					<span id="${divId}"> 
						 <input
						type="hidden"
						id="unitStemAreasId${addedStemArea.stemAreas.stemAreaId}"
						name="unitStemAreasIds"
						 value="${addedStemArea.unitStemAreasId}"> <span
						id="button${addedStemArea.stemAreas.stemAreaId}"
						class="subButtons subButtonsGreen medium"
						onClick="openStrandsWindow(${addedStemArea.unitStemAreasId},${addedStemArea.stemAreas.stemAreaId },'${addedStemArea.stemAreas.stemArea}',-1)">${addedStemArea.stemAreas.stemArea}</span>
						<i class="fa fa-times" aria-hidden="true"
						style="cursor: hand; cursor: pointer; font-size: 20px; color: #CD0000; vertical-align: middle;"
						onclick="removeStemArea('${divId}','${contentQueDivId}',${addedStemArea.stemAreas.stemAreaId},${index.index })"></i></span> &nbsp;&nbsp;&nbsp;
						<input type="hidden" name="selectedStemAreas" value="${addedStemArea.stemAreas.stemAreaId}" id="${addedStemArea.stemAreas.stemAreaId}">
			 	</c:forEach>
			</div></td>
	</tr>
	<tr>
		<td align="center" colspan="2" style="padding-top: 1em;">
			<div id="contentQuestionsDiv"
				style="min-height: 260px;max-height: 260px;overflow: auto;border: 1px solid #7ab4bb;width: 80%;background: #e1e9ea;">
				<c:forEach items="${stemUnit.uniStemAreasLt}"
					var="unitStemArea" varStatus="index">
					<c:set var="unitStemAreaWithoutSpaces" value="${fn:replace(unitStemArea.stemAreas.stemArea, ' ', '')}"></c:set>
					<table width="85%" id="${divId}" style="padding-bottom: 1em;"
						align="center">
							<tr>
									<td class="tabtxt" width="60%" style="color: #000000;"><span
										style="color: blue; font-weight: bold;">${unitStemArea.stemAreas.stemArea }
									</span> Selected Standards:</td>
								</tr>
							<c:forEach items="${unitStemArea.unitStemStrandsLt}" var="unitStemStrand"> 
								
								<tr id="rmUnitStandardId${unitStemStrand.unitStemStrandsId}">
									<td colspan="2" width="100%"
										id="conceptsDiv${unitStemArea.stemAreas.stemAreaId}">
										<i class="fa fa-times"
												style="cursor: hand; cursor: pointer; font-size: 20px; color: #CD0000; vertical-align: middle;"
												onclick="removeUnitStandards(${unitStemStrand.unitStemStrandsId})"></i> 
												<a href="#" onclick="openStrandsWindow(${unitStemArea.unitStemAreasId},${unitStemArea.stemAreas.stemAreaId},'${unitStemArea.stemAreas.stemArea}',${unitStemStrand.stemGradeStrands.stemGradeStrandId})">${unitStemStrand.stemGradeStrands.stemStrandTitle }</a>&nbsp; 
										<c:forEach
											items="${unitStemStrand.unitStemConceptsLt}"
											var="unitStemConcept" varStatus="count">
										<a href="#" title="${unitStemConcept.stemStrandConcepts.stemConceptDesc}" >
											<c:choose>
												<c:when test="${not empty unitStemConcept.stemStrandConcepts.stemConcept}">
													${unitStemConcept.stemStrandConcepts.stemConcept}
												</c:when>
												<c:otherwise>
													Concept ${count.count}
												</c:otherwise>
											</c:choose> 
											
										</a>
									</c:forEach>
		
									</td>
								</tr>
							</c:forEach>
							<tr>
									<td class="tabtxt" width="60%" style="color: #000000;"> Content Questions:</td>
									<td width="40%" align="right"><span class=button_green
										onclick="addContentQuestions(${unitStemArea.stemAreas.stemAreaId} ,'${unitStemAreaWithoutSpaces}')">
											Add Content Question</span></td>
							</tr>
							<tr>
								<td colspan="2" width="100%"><input type="hidden" id=""
									value=0><input type="hidden"
									id="stemArea${unitStemArea.stemAreas.stemArea}"
									value="${unitStemArea.stemAreas.stemAreaId}">
									<input type='hidden' id="${unitStemAreaWithoutSpaces}QuesSize" value="${fn:length(unitStemArea.unitStemContentQuestionsLt) }">
									<div id="${unitStemAreaWithoutSpaces}QuestionsDiv" class="questionDiv">
										<c:forEach items="${unitStemArea.unitStemContentQuestionsLt }" var="stemQuestion" varStatus="status"> 
											<span
												id="${unitStemAreaWithoutSpaces}Div${status.index}" style="margin-left: 2em;"><textarea
													rows=2 cols=100
													id="${unitStemAreaWithoutSpaces}${status.index}"
													name="${status.index}"
													style="margin-bottom: 5px;margin-top:.2em;"  onblur="updateContentQuestions('${unitStemAreaWithoutSpaces}',${unitStemArea.stemAreas.stemAreaId},${status.index})">${stemQuestion.contentQuestion}</textarea>
												&nbsp;&nbsp;<i class="fa fa-times"
												style="cursor: hand; cursor: pointer; font-size: 20px; color: #CD0000; vertical-align: middle;"
												onclick="removeContentQuestion(${unitStemArea.stemAreas.stemAreaId}, '${unitStemAreaWithoutSpaces}',${status.index})"></i>
												<input type="hidden"
												id="${unitStemArea.stemAreas.stemAreaId}:${status.index}"
												name="${unitStemAreaWithoutSpaces}:${status.index}"
												value="${stemQuestion.unitStemContentQuesId }" /><br></span>
										</c:forEach>
								  </div>
							</td>
						 </tr>						
					</table>
				</c:forEach>
			</div>
		</td>
	</tr>
</table>

