<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Plan Schedule</title>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="planSchedule"  style="visibility:hidden;">
<table width="100%">
	    <tr><td colspan="" width="100%"> 
			 <table width="100%" class="title-pad" border="0">
				<tr>
					<td class="sub-title title-border" height="40" align="left" >Plan Schedule</td>
				</tr>
			</table>
		</td></tr>
		 <tr><td align="left" colspan="" width="100%" valign="top"  class="" style="padding-left:6em;padding-top:1em;"> 
			<table width="60%" border=0 align="left" valign="top"  class="">
				<tr>   
					<td height="30" colspan="2" align="center" valign="middle">
						<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0"  class="label" style="font-size: 12pt ">
						<tr align="right">
							<td width="120">Grade</td>
							<td width="150" align="right" valign="middle">
								<select	id="gradeId" name="gradeId" onchange="loadClasses()" style="width:120px;" class="listmenu">
										<option value="select">select grade</option>
										<c:forEach items="${gradeLt}" var="ul">
													<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
										</c:forEach>
										
								</select>
							</td>
							<td></td>
							<td width="120">Class</td>
							<td width="150" align="right" valign="middle">
							<select id="classId" name="classId" class="listmenu" style="width:120px;" onchange="loadTeachers()" >
							 	<option value="select">select subject</option>
							</select></td>
							<td width="120">Teacher</td>
							<td width="180" align="right" valign="right">
							<select id="teacherId" name="teacherId" class="listmenu" style="width:150px;" onchange="getPlannerData()" >
									<option value="select">Select Teachers</option> 
							</select></td>
							<td>&nbsp;</td>
						</tr>
					    <tr>
		                    <td height="4" colspan="6"></td>
		                </tr>
						
		        	</table>
	        		</td>
				</tr>
			</table>
		 <div id="detailsPage" valign="top">
			<table height="10px" width="40%" valign="top" class="label">
				<tr>
					<td width="100" align="center"  class="label" style="font-size: 12pt ">Start Date</td>
		           	<td width="100"><input name="startDate" type="text" id="startDate" size="10" maxlength="15"  value="${startDate}"  readonly="readonly"/></td>
					<td width="60" align="left"  class="label" style="font-size: 12pt ">End Date </td>
		            <td width="100"><input name="endDate" type="text" id="endDate" size="10" maxlength="15"  value="${endDate}" readonly="readonly"/></td>
		           </tr>
		
				<tr>
			     <input type="hidden" name="gradeIdHidden" id="gradeIdHidden" value="${gradeId}"></input>
			     <input type="hidden" name="classIdHidden" id="classIdHidden" value="${classId}"></input>
			     <input type="hidden" name="teacherIdHidden" id="teacherIdHidden" value="${teacherId}"></input>
			     <table><tr><td>&nbsp;</td></tr></table>
				<table  id="planner" width="auto" align="center" class="des" border=0 style="text-transform: capitalize"><tr><td>
				  <div class="Divheads"><table width="95%">
			           <tr>
			               <th width="100" height="10" align="center" valign="middle"  class="header"><font color="white" size="3"><b>Day&nbsp;</b></font></th>
			               <input type="hidden" name="noOfPeriods" id="noOfPeriods" value="${fn:length(periodsLt)}"></input>
			               <c:forEach items="${periodsLt}" var="period">  
			           		<th width="180" height="10" align="center" valign="middle"  class="header"><font color="white" size="3"><b>${period.periodName}&nbsp;</b></font></th>
			               </c:forEach>
			                 <th width="120" height="10" align="center" valign="middle" class="header" ><font color="white" size="3"><b>Assign</b></font></th>
			           </tr></table></div>
			           <div class="DivContents"><table>
			           <tr>
			             <td width="100" height="30" align="center" valign="middle" class="header" ><font color="white" size="2"></font></td>
			              <c:forEach items="${periodsLt}" var="period">  
			               <td height="10"   valign="middle"  class="header" >
			                     <table width="100%" align="center">
			                         <tr>
			                            <th width="180" align="center"><font color="black" size="2">Section</font></th>
			                         </tr>
			                     </table>
			                 </td>
			               </c:forEach>
			               <th width="150" height="40" align="center" valign="middle" class="header" ></th>
		           </tr>
		          <c:forEach items="${schoolDaysLt}" var="schoolDays">
		            <tr>
			           	<th  class="header" height="30" align="center" valign="middle"><font color="#003e45" size="4">${schoolDays.days.day}</font>
			               <input type="hidden" name="dayId${schoolDays.schoolDaysId}" id="dayId${schoolDays.schoolDaysId}" value="${schoolDays.days.dayId}"></input>
			           	</th>
			         <c:forEach items="${periodsLt}" var="period" varStatus="status"> 
						<input type ="hidden" name="period${schoolDays.schoolDaysId}${status.count}" id="period${schoolDays.schoolDaysId}${period.periodId}" onclick="" value="${period.periodId}"></input>
		             	 <td height="10" align="center" valign="middle" class="header" width="180">
		             	  <c:forEach items="${casLt}" var="cas"> 
		             	   <c:if test="${cas.periods.periodId eq period.periodId && cas.days.dayId eq schoolDays.days.dayId}"> 
		                   		<input type ="hidden" name="casId${schoolDays.schoolDaysId}${status.count}" id="casId${schoolDays.schoolDaysId}${cas.periods.periodId}" value="${cas.casId}" ></input>
		                   </c:if>		
		             	   </c:forEach>
		                      <table width="100%" align="center">
		                         <tr>
		                           <td align="center">
		                           <select  class="listmenu" style="width:120" name="section${schoolDays.schoolDaysId}${status.count}" id="section${schoolDays.schoolDaysId}${period.periodId}"  onChange="checkTeacherAvailability('dayId${schoolDays.schoolDaysId}','period${schoolDays.schoolDaysId}${period.periodId}','section${schoolDays.schoolDaysId}${period.periodId}')">
		                           <option value="select"> Select Section</option>
		                            <c:forEach items="${sectionsLt}" var="section"> 
		                                 <c:forEach items="${casLt}" var="cas" > 
		                                    <c:if test="${cas.classStatus.section.sectionId eq section.sectionId && cas.periods.periodId eq period.periodId && cas.days.dayId eq schoolDays.days.dayId}"> 
		                                          <c:set var="selected" value="true"/>
		                                    </c:if>
		                                 </c:forEach>
		                                 <option value="${section.sectionId}" ${selected == 'true' ? 'selected="selected"' : ''}>
		                                     ${section.section}
		                                  </option>
		                               <c:set var="selected" value="false"/>
		                         </c:forEach>  
		                           </select> 
		                    	</td>    
		                       </tr>
		                      </table>
		                 </td>
		            </c:forEach>
	                 <input type="hidden" name="schoolId" id="schoolId" value="${schoolDays.school.schoolId}"></input>
	                 <td width="180" align="center">
	                    <a href="#"  id="${schoolDays.schoolDaysId}"   name="${schoolDays.schoolDaysId}" onclick="assginSections(this)"  class="fa fa-user" style="color:#008FCF;font-size:30px;box-shadow:0 20px 40px rgb(179, 179, 179);" style="color:#008FCF;"></a>
	                 </td>
		             </tr>
		             <tr><td height=15 colSpan=2><div align="right"></div></td></tr>
		         </c:forEach></table></div>
		      </td></tr></table>  
		</tr>
	</table>
	 </div>
     </td></tr>
       <tr><td height=18 colSpan=2><div align="right"></div></td></tr>
     </table>	 
    </div>
</body>
</html>