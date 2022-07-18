<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html>
<head>

<script type="text/javascript">
$(function() {
    $( "#promotStartDate" ).datepicker({
    	changeMonth: true,
        changeYear: true,
        showAnim : 'clip',
        minDate: 0
    });
});
$(function() {
    $( "#promotEndDate" ).datepicker({
    	changeMonth: true,
        changeYear: true,
        showAnim : 'clip',
        minDate: 0
    });
});
function updateCalendar(thisvar){
	var start = new Date(thisvar.value);
	var end = new Date();
	var diff = new Date(start - end);
	var days = Math.ceil(diff/1000/60/60/24);		
	$( "#promotEndDate" ).datepicker( "option", "minDate",  days);
	 
}
</script>
</head>
<body>
 <form:form name="adminSchoolInfoForm" modelAttribute="School" >  
	   <table class="des" border=0 align="center" width="33%">
	   <tr><td align="left" width="500"> 
  	   <div class="Divheads" >
  	   <table><tr><td class="headsColor" align="left">School Information</td></tr></table></div>
  	   <div class="DivContents">
  	    
        	<table width="100%" border="0" cellspacing="0" cellpadding="0" valign="top" align="center" >
				<tr><td>&nbsp;</td></tr>
				<tr>
										<td width="35%" height="35" align="right" valign="middle"
											class="tabtxt"><form:label path="schoolName"><img src="images/Common/required.gif"> School Name</form:label></td>
										<td  width="20%" height="35" align="center" valign="middle"
											class="text1">:</td>
										<td  height="35" align="left" valign="middle"
											class="tabtxt"><form:input path="schoolName"
												id="schoolName" class="textBox1" /></td>
									</tr>
									<tr>
										<td  height="35" align="right" valign="middle"
											class="tabtxt"><form:label path="schoolAbbr"><img src="images/Common/required.gif"> School Abbreviation</form:label></td>
										<td  height="35" align="center" valign="middle"
											class="text1">:</td>
										<td  height="35" align="left" valign="middle"
											class="tabtxt"><form:input path="schoolAbbr"
												id="schoolAbbr" class="textBox1"/></td>
									</tr>
									<tr>
										
										<td  height="35" align="right" valign="middle"
											class="tabtxt" ><img src="images/Common/required.gif"> Country</td>
										<td  height="35" align="center" valign="middle"
											class="text1">:</td>
										<td height="35" colspan="6" align="left" valign="middle"
											class="tabtxt"><font size="2"
											face="Arial, Helvetica, sans-serif"> <form:select  class="listmenu"
													id="countryId" path="countryId"
													onchange="loadStates(); return false;">
													<option value="select">Select Country</option>
													<c:forEach var="cList" items="${countryIds}">
														<option value="${cList.countryId}"
															<c:if test="${countryId == cList.countryId}"> selected="Selected" </c:if>>${cList.country}</option>
													</c:forEach>
												</form:select>
										</font></td>
									</tr>
									<tr>
										
										<td  height="35" align="right" valign="middle"
											class="tabtxt"><img src="images/Common/required.gif"> State</td>
										<td  height="35" align="center" valign="middle"
											class="text1">:</td>
										<td height="35" colspan="6" align="left" valign="middle"
											class="tabtxt"><font size="2"
											face="Arial, Helvetica, sans-serif"> <form:select class="listmenu"
													id="stateId" path="stateId">
													<option value="select">Select State</option>
													<c:forEach var="sList" items="${stateIds}">
														<option value="${sList.stateId}"
															<c:if test="${stateId == sList.stateId}"> selected="Selected" </c:if>>${sList.name}</option>
													</c:forEach>
												</form:select>
										</font></td>
									</tr>
									<tr>
										
										<td  height="35" align="right" valign="middle"
											class="tabtxt"><form:label path="city"><img src="images/Common/required.gif"> City</form:label></td>
										<td  height="35" align="center" valign="middle"
											class="text1">:</td>
										<td  height="35" align="left" valign="middle"
											class=""><form:input path="city" id="city" class="textBox1"/></td>
									</tr>


									<tr>
										<td width="47" align="left" valign="top"></td>
										<td colspan="8" align="right" valign="middle" class="tabtxt"></td>
									</tr>
									<tr>
										
										<td  height="35" align="right" valign="middle"
											class="tabtxt"><img src="images/Common/required.gif"> Type Of School</td>
										<td  height="35" align="center" valign="middle"
											class="text1">:</td>
										<td height="35" colspan="6" align="left" valign="middle"
											class="tabtxt"><form:select path="schoolTypeId" class="listmenu"
												id="schoolTypeId">
												<option value="select">Select School Type</option>
												<c:forEach var="stList" items="${schoolTypeIds}">
													<option value="${stList.schoolTypeId}"
														<c:if test="${schoolTypeId == stList.schoolTypeId}"> selected="Selected" </c:if>>${stList.schoolTypeName}</option>
												</c:forEach>
											</form:select></td>
									</tr>
									<tr>
										<td width="47" align="left" valign="top"></td>
										<td colspan="8" align="right" valign="middle" class="tabtxt"></td>
									</tr>
									<tr>
										
										<td  height="35" align="right" valign="middle"
											class="tabtxt"><img src="images/Common/required.gif">
											Level Of School</td>
										<td  height="35" align="center" valign="middle"
											class="text1">:</td>
										<td height="35" align="left" valign="middle" class="tabtxt"><form:select class="listmenu"
												id="schoolLevelId" path="schoolLevelId">
												<option value="select">Select School Level</option>
												<c:forEach var="slList" items="${schoolLevelIds}">
													<option value="${slList.schoolLevelId}"
														<c:if test="${schoolLevelId == slList.schoolLevelId}"> selected="Selected" </c:if>>${slList.schoolLevelName}</option>
												</c:forEach>
											</form:select></td>
									</tr>
									<tr>
										<td width="47" align="left" valign="top"></td>
										<td colspan="8" align="right" valign="middle" class="tabtxt"></td>
									</tr>
									<tr>
										
										<td  height="35" align="right" valign="middle"
											class="tabtxt"><form:label path="noOfStudents"><img src="images/Common/required.gif"> Number of Students</form:label></td>
										<td  height="35" align="center" valign="middle"
											class="text1">:</td>
										<td  height="35" align="left" valign="middle"
											class="tabtxt"><form:input path="noOfStudents" 
												id="noOfStudents" class="textBox1" /></td>
									</tr>
									<tr>
										
										<td  height="35" align="right" valign="middle"
											class="tabtxt"><form:label path="phoneNumber"><img src="images/Common/required.gif"> Phone Number</form:label></td>
										<td  height="35" align="center" valign="middle"
											class="text1">:</td>
										<td  height="35" align="left" valign="middle"
											class="tabtxt"><form:input path="phoneNumber" 
												id="phoneNumber" class="textBox1" /></td>
									</tr>
									<tr>
										
										<td  height="35" align="right" valign="middle"
											class="tabtxt"><form:label path="faxNumber"><img src="images/Common/required.gif"> Fax Number</form:label></td>
										<td  height="35" align="center" valign="middle"
											class="text1">:</td>
										<td  height="35" align="left" valign="middle"
											class="textBox"><form:input path="faxNumber" 
												id="faxNumber" class="textBox1"/></td>
									</tr>
									<tr>

										
										<td  height="35" align="right" valign="middle"
											class="tabtxt"><form:label path="promotStartDate" >Promotion Start Date</form:label></td>
										<td  height="35" align="center" valign="middle"
											class="text1">:</td>
										<td  height="35" align="left" valign="middle"
											class="tabtxt"><form:input path="promotStartDate"
												id="promotStartDate" value="${startDate}" readonly="true" onchange="updateCalendar(this)"/></td>

									</tr>
									<tr>

										
										<td  height="35" align="right" valign="middle"
											class="tabtxt"><form:label path="promotEndDate">Promotion End Date</form:label></td>
										<td  height="35" align="center" valign="middle"
											class="text1">:</td>
										<td  height="35" align="left" valign="middle"
											class="tabtxt"><form:input path="promotEndDate"
												id="promotEndDate" value="${endDate}"  readonly="true"/></td>

									</tr>
									<tr>
										
										<td  height="35" align="right" valign="middle"
											class="tabtxt">District</td>
										<td  height="35" align="center" valign="middle"
											class="text1">:</td>
										<td height="35" colspan="6" align="left" valign="middle"
											class="tabtxt"><form:select path="districtId"
												id="districtId" class="listmenu">
												<option value="">Select District</option>
												<c:forEach var="dList" items="${districtIds}">
													<option value="${dList.districtId}"
														<c:if test="${districtId == dList.districtId}"> selected="Selected" </c:if>>${dList.districtName}</option>
												</c:forEach>
											</form:select></td>
									</tr></table>
									
									<table align="center">
									<tr>
										<td><form:hidden path="schoolId" id="schoolId" /></td>
									</tr>
							    <tr>
								<td height="35" colspan="9" align="center" valign="middle"><table
										style="width: 100%" >
											<tr>
												<td width="50%" align="left" valign="middle">
													<div class="button_green" align="right" onclick="AddSchoolInfo()">Submit Changes</div> 
												</td>
												<td width="100" align="left" valign="middle">
													<div class="button_green" align="right" onclick="document.adminSchoolInfoForm.reset();return false;">Clear</div> 
												</td>
												<td width="100" align="left" valign="middle">
													<a href="gotoDashboard.htm"><div class="button_green" align="right">Cancel</div></a>
												</td>
											</tr>
										</table></td>
								</tr>
							</table>
				</td></tr></div>
</table>		
	</form:form>
</body>
</html>