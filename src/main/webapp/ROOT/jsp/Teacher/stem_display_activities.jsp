<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
.uploadButton{
    padding: 10px;
    display: inline;
    border-radius: 2px;
    font-family: "Arial";
    border: 0;
    margin: 0 10px;
    background: green;
    font-size: 15px;
    line-height: 15px;
    color: white;
    width: auto;
    height: auto;
    box-sizing: content-box;
}
</style>
<script type="text/javascript">
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
<form:form id="stemActivitiesForm"  action="saveStemActivities.htm" modelAttribute="stemUnitActList" method="post" enctype="multipart/form-data">
	
	<input type="hidden" id="noOfActQues" value="${noOfActQues}"/>
	<c:set var="display" value="none"></c:set>
	<c:if test="${noOfActQues gt 0}">
		<c:set var="display" value="display"></c:set>
	</c:if>
	<div style="display:${display};" id="activityDiv">
		<div style="max-height: 18em;overflow: auto;">
		<table width="90%" id="activitiesTable">		
			<tr>
				<th width="30%" class="label" align="center" style="color:black;">
					Description
				</th>
				<th width="30%" class="label" align="center" style="color:black;">
					Link
				</th>
				<th width="40%" class="label" align="center" style="color:black;">
					Upload File
				</th>
				<th width="20%" class="label" align="center" style="color:black;">
					&nbsp;
				</th>
			</tr>
			<c:if test="${noOfActQues gt 0}">
				<c:forEach var="qNumber" begin="0" end="${noOfActQues-1}">
					<tr>
						<td width="" align="center">
							<form:hidden path="stemUnitAct[${qNumber}].stemActivityId" id="actId${qNumber}" value=""/>
							<form:textarea rows="4" cols="40" path="stemUnitAct[${qNumber}].activityDesc" id="desc${qNumber}"  style="overflow-y: auto; overflow-x: hidden;" value=""></form:textarea>							
						</td>
						<td width="" align="center">
							<form:textarea rows="4" cols="40" path="stemUnitAct[${qNumber}].activityLink" id="link${qNumber}" style="overflow-y: auto; overflow-x: hidden;" value=""></form:textarea>
						</td>
						<td width="" align="center">
							<label class="file-upload">
								<c:set var="fileNameDesc" value="${stemUnitActList.stemUnitAct[qNumber].fileName}" />
								<c:if test="${stemUnitActList.stemUnitAct[qNumber].fileName eq '' or stemUnitActList.stemUnitAct[qNumber].fileName eq null}">
									<c:set var="fileNameDesc" value="Upload FIle" />
								</c:if> 
								<span id="fName${qNumber}" class="uploadButton">${fileNameDesc}</span>
								<input type="file" id="mFile${qNumber}" name="files" style="display: none;" onchange="getFileData(this,fName${qNumber});">						
							</label>  
							<form:hidden path="stemUnitAct[${qNumber}].fileName" id="stemFileName${qNumber}" value=""/>  
						</td>
						<td width="" align="center">
							<div class="button_green" align="right" onclick="viewSharedActivities(${qNumber})">Shared</div>
							<form:hidden path="stemUnitAct[${qNumber}].referActivityId" id="referId${qNumber}" value=""/>
						</td>
					</tr>
				</c:forEach>
			</c:if>		
		</table>  
		</div>
		<table width="90%" align="center" style="padding-top: 50px;">
			<tr>
				<td width="30%" height="10" align="right" valign="middle" style="padding-left: 2em;">
		    	   	<div class="button_green" align="right" onclick="window.open('http://www.google.com')">Google Search</div>
		      	</td>
		    	<td width="35%" height="10" align="center" valign="middle"> 
		        	<div class="button_green" align="center" onclick="saveStemAct()">Save Activities</div> 
		        </td>	        
		    </tr>
		</table>	
	</div>
	<c:if test="${noOfActQues eq 0}">
		<div id="noActDiv">
			<label class="label">No Activities to display </label> 
		</div>
	</c:if>
  	<div id="sharedDialog"  style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title="">
  		<div id ="shareDiv"></div>
  	</div>
</form:form>