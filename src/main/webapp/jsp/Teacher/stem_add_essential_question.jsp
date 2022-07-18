<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script>
$(function() {
	  var essUnitQueLen = parseInt($('#essUnitQueLen').val());
	  if(essUnitQueLen > 0){
		  //loadEssUnitQuestions();
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
		    synchronizeTab(stemUnitId, currentTab, '', 'Yes', '', 'focusin');
	  }
});
</script>
<input type="hidden" id="essUnitQueLen" name="essUnitQueLen" value="${fn:length(stemEssUnitQues)}" />
<table width="100%">
<tr><td width="100%">
	<table id="essUnitTbl" width="100%" style="color:black;margin-right: auto; margin-left: auto; width: 800px;">
	<c:choose>
	  <c:when test="${fn:length(stemEssUnitQues) > 0}">
		 <c:forEach items="${stemEssUnitQues}" var="stemQuestion">
			 <c:if test="${stemQuestion.stemQuestionsType.stemQuesTypeId eq 1}">
				  <tr>
					  <td width="25%" class="label" style="vertical-align:middle;text-align:left;height:40px;color:black;" > Essential Question &nbsp;&nbsp; :</td>
					  <td width="60%" valign="middle" ><textarea rows="3" cols="60" id="essQuestion" name="essQuestion" onblur="autoSaveEssenUnitQues('',1)">${stemQuestion.stemQuestion}</textarea></td>
					  <td><input type="hidden" id="essQuestionId" name="essQuestionId" value="${stemQuestion.stemQuestionId}" /></td>
				   </tr>  
			</c:if>
		 </c:forEach>
		 <tr><td class="label" height="25" style="vertical-align:middle;text-align:left;height:40px;color:black;font-weight:bold;"> Unit Questions &nbsp;&nbsp; :</td><td colspan="2" align="right"><span class="button_green" onclick="addEssenitialQuestion('${mode}')" id="addUnitQue">Add Unit Question</span></td></tr>
		 <c:forEach items="${stemEssUnitQues}" var="stemQuestion" varStatus="status">
			 <c:if test="${stemQuestion.stemQuestionsType.stemQuesTypeId eq 2}">
				 <tr id="tr${status.index}">
					 <td width="15%" class="label"><input type="hidden" id="unitQueId${status.index}" name="unitQueId${status.index}" value="${stemQuestion.stemQuestionId}" /></td>
					  <td width="60%" valign="middle" ><textarea class="unitQueCls" rows="3" cols="60" id="unitQues${status.index}" name="unitQues${status.index}" onblur="autoSaveEssenUnitQues(${status.index},2)">${stemQuestion.stemQuestion}</textarea>&nbsp;&nbsp;<i class="fa fa-times" style="cursor: hand; cursor: pointer; font-size: 20px; color: #CD0000; vertical-align: middle;" onclick="removeEssQuestion(${status.index})"></i></td>
				  </tr>  
			</c:if>
		 </c:forEach>
		  <c:if test="${fn:length(stemEssUnitQues) < 3}">
		    <c:forEach begin="0" end="${3-(fn:length(stemEssUnitQues))}" varStatus="loop">
		  	    <c:set var="count" value="${fn:length(stemEssUnitQues) + loop.index}"/>
			      <tr id="tr${count}">
					 <td width="15%" class="label"><input type="hidden" id="unitQueId${count}" name="unitQueId${count}" value="0" /></td>
					 <td width="60%" valign="middle" ><textarea class="unitQueCls" rows="3" cols="60" id="unitQues${count}" name="unitQues${count}" onblur="autoSaveEssenUnitQues(${count},2)">${stemQuestion.stemQuestion}</textarea>&nbsp;&nbsp;<i class="fa fa-times" style="cursor: hand; cursor: pointer; font-size: 20px; color: #CD0000; vertical-align: middle;" onclick="removeEssQuestion(${count})"></i></td>
				   	 <td></td>
				  </tr> 
			</c:forEach>
	   	  </c:if>
	  </c:when>
	  <c:otherwise>
	   	    <tr>
			  <td width="25%" class="label" style="vertical-align:middle;text-align:left;height:40px;color:black;" > Essential Question &nbsp;&nbsp; :</td>
			  <td width="60%" valign="middle" ><textarea rows="3" cols="60" id="essQuestion" name="essQuestion" onblur=""></textarea></td>
			  <td><input type="hidden" id="essQuestionId" name="essQuestionId" value="0" /></td>
		    </tr>  
		    <tr><td height="25" class="label" style="vertical-align:middle;text-align:left;height:40px;color:black;font-weight:bold;"> Unit Questions &nbsp;&nbsp; :</td><td colspan="2" align="right"><span class="button_green" onclick="addEssenitialQuestion('${mode}')" id="addUnitQue">Add Unit Question</span></td></tr>
	  		<c:forEach begin="0" end="2" varStatus="loop">
			  <tr id="tr${loop.index}">
				 <td width="15%" class="label"><input type="hidden" id="unitQueId${loop.index}" name="unitQueId${loop.index}" value="0" /></td>
				 <td width="60%" valign="middle" ><textarea class="unitQueCls" rows="3" cols="60" id="unitQues${loop.index}" name="unitQues${loop.index}" onblur=""></textarea>&nbsp;&nbsp;<i class="fa fa-times" style="cursor: hand; cursor: pointer; font-size: 20px; color: #CD0000; vertical-align: middle;" onclick="removeEssQuestion(${loop.index})"></i></td>
			   	 <td></td>
			   </tr> 
			</c:forEach>
	  </c:otherwise>
	</c:choose>
	</table>
</td></tr>
<c:choose>
<c:when test="${fn:length(stemEssUnitQues) == 0}">
<tr><td colspan="3">
	<table width="90%" align="center" style="padding-top: 50px;" id="tabsubmitDiv">
       	<tr>
      		<td width="35%" height="10" align="right" valign="middle"> 
           		<div class="button_green" align="right" onclick="saveEssentialQues()">Submit Changes</div> 
          	</td>
            <td width="30%" height="10" align="left" valign="middle" style="padding-left: 2em;">
            	<div class="button_green" align="right" onclick="$('#essenitialQueForm')[0].reset(); return false;">Clear</div>
           	</td>
     	</tr>
   </table>
 </td></tr>
 </c:when>
 </c:choose>
 </table>
	<%-- <c:if test="${mode eq 'create'}">
		<input type="hidden" id="questionNum" name="questionNum" value=4>
		<c:set var="noOfQues" value="4" />
	</c:if>
	
	<c:if test="${mode eq 'edit'}">
		<input type="hidden" id="questionNum" name="questionNum" value="${listSize}">
		<c:set var="noOfQues" value="${listSize}" />
		<input type="hidden" id="stemUnitId" value="${stemUnitId}" />
	</c:if>
  	<table id="essentialId" width="100%" style="color:black;margin-right: auto; margin-left: auto; width: 800px;">
  		<c:forEach var="qNumber" begin="0" end="${noOfQues-1}" varStatus="qStatus">
  			<c:if test="${qStatus.index eq 0}">
				<tr id="essRow${qStatus.count}">
					<td width="25%" class="label" style="vertical-align:middle;text-align:left;height:40px;color:black;" > Essential Question &nbsp;&nbsp; :</td>
				  	<td width="60%" style="vertical-align:middle;">				  		
				  		<form:textarea path="stemQuestionList[${qStatus.index}].stemQuestion" id="essQues" name="essQues"  class="${qStatus.index}" rows="3" cols="60" value="" onblur="autoSaveEssenUnitQues(this,1)"/>
				  		<form:hidden path="stemQuestionList[${qStatus.index}].stemQuestionId" id="essQuesId" value=""/>
				  		<form:hidden path="stemQuestionList[${qStatus.index}].stemQuestionsType.stemQuesTypeId" id="stemTypeId${qStatus.index}" value="1"/>
				  		<form:hidden path="stemQuestionList[${qStatus.index}].stemUnit.stemUnitId" id="unitId${qStatus.index}" value="${stemUnitId}"/>
				  	</td>
			  	</tr>
		  	</c:if>
		  	<c:if test="${qStatus.index eq 1}">
			  	<tr id="essRow${qStatus.count}">
					<td width="15%" class="label" style="vertical-align:middle;text-align:left;height:60px;color:black;" > Unit Questions &nbsp;&nbsp; :</td>
				  	<td width="60%" style="vertical-align:middle;">
				  		<form:textarea path="stemQuestionList[${qStatus.index}].stemQuestion" id="unitQues${qStatus.index}" name="unitQues${qStatus.index}"  class="${qStatus.index}" rows="3" cols="60" value="" onblur="autoSaveEssenUnitQues(this,2)"/>
				  		<form:hidden path="stemQuestionList[${qStatus.index}].stemQuestionId" id="unitQuesId${qStatus.index}" value=""/>
				  		<form:hidden path="stemQuestionList[${qStatus.index}].stemQuestionsType.stemQuesTypeId" id="stemTypeId${qStatus.index}" value="2"/>
				  		<form:hidden path="stemQuestionList[${qStatus.index}].stemUnit.stemUnitId" id="unitId${qStatus.index}" value="${stemUnitId}"/>
				  	</td>
			  		<td height="10" align="right" valign="middle"> 
		          		<div class="button_green" align="right" onclick="addEssenitialQuestion('${mode}')">Add</div>           	
		         	</td>
		         	<td height="10" align="right" valign="middle"> 
		          		<div class="button_green" align="right" onclick="removeEssQuestion()">Remove</div>
		         	</td>
			  	</tr>
		  	</c:if>
		  	<c:if test="${qStatus.index ge 2}">		  	
			  	<tr id="essRow${qStatus.count}">
					<td width="15%" class="label" style="vertical-align:middle;text-align:left;height:40px;color:black;" ></td>
				 	<td width="60%" style="vertical-align:middle;">
				 		<form:textarea path="stemQuestionList[${qStatus.index}].stemQuestion" id="unitQues${qStatus.index}" name="unitQues${qStatus.index}"  class="${qStatus.index}" rows="3" cols="60" value="" onblur="autoSaveEssenUnitQues(this,2)"/>
				 		<form:hidden path="stemQuestionList[${qStatus.index}].stemQuestionId" id="unitQuesId${qStatus.index}" value=""/>
				 		<form:hidden path="stemQuestionList[${qStatus.index}].stemQuestionsType.stemQuesTypeId" id="stemTypeId${qStatus.index}" value="2"/>
				 		<form:hidden path="stemQuestionList[${qStatus.index}].stemUnit.stemUnitId" id="unitId${qStatus.index}" value="${stemUnitId}"/>
				 	</td>
			  	</tr>
		  	</c:if>
	  	</c:forEach>
  	</table> --%>
  	<%-- <c:if test="${mode=='create'}">
  	<table width="90%" align="center" style="padding-top: 50px;" id="tabsubmitDiv">
  
      	<tr>
      		<td width="35%" height="10" align="right" valign="middle"> 
           		<div class="button_green" align="right" onclick="saveEssentialQues()">Submit Changes</div> 
          	</td>
            <td width="30%" height="10" align="left" valign="middle" style="padding-left: 2em;">
            	<div class="button_green" align="right" onclick="$('#essenitialQueForm')[0].reset(); return false;">Clear</div>
           	</td>
     	</tr>
	</table>
	</c:if>
</form> --%>
 <div class="active-users-div text" id="tab1-active-users-div"></div>