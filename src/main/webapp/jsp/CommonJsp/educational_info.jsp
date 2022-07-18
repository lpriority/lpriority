<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Educational Info</title>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<style>
.submitButton{
	color: white;
	height:30px; 
	width:85px; 
	font-size:12px;
	background-color: #663399;
	font-weight: 900;
	moz-border-radius: 15px;
	-webkit-border-radius: 15px;
	font-family: Gill Sans, Verdana;
}
.addEducationInfoButton{
	color: white;
	height:35px; 
	width:240px; 
	font-size:15px;
	background-color: #663399;
	font-weight: 900;
	font-family: Gill Sans, Verdana;
}
</style>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/dwr/interface/myProfileService.js"></script>
</head>
<body>
  <input type="hidden" id="userTypeId" name="userTypeId" value="${userTypeId}" />
  <input type="hidden" id="userId" name="userId" value="${userId}" />
  <input type="hidden" id="stdRegId" name="stdRegId" value="${stdRegId}" />  
<!-- <table width="100%">
	    <tr><td colspan="" width="100%"> 
			 <table width="100%" class="title-pad heading-up" border="0">
				<tr>
					<td class="sub-title title-border heading-up" height="40" align="left" >Change Educational Information</td>
				</tr>
			</table>
		</td></tr></table> -->
		<br>
		<table class="des" border=0>
	    <tr><td> 
  <c:choose>
    <c:when test="${not empty gradesLt}">
         <c:if test="${userTypeId eq '3'}">
           	<div class="Divheads"> 
           	   <table width='84%'>
           		 <tr>
            	 	<th height=3 valign="top" width=90 colspan="1" align='center' >Grade Name</th>
            	 	<th width="100" align='center' >Class Name</th> 
            	 	<th width='165' align='center' style="padding-right: 20px;" >Grade Level</th> 
	           	 	<th width='125' align='left' >Per Day </th> 
            	 	<th width='140' align='left' >Per Week  </th>
            	 </tr>
            	</table></div>
           	</c:if>
           	<c:if test="${userTypeId eq '5' || userTypeId eq '6'}"> 
           	<div class="Divheads" style="height:1.5em;"> 
	           	<table width='60%' align='left' border="0"  cellpadding='0' cellspacing='0'>
	           		 <tr>
	            	 	<th height=3 valign="top" width=90 colspan="1" align='center'  >Grade Name</th>
	            	 	<th width="120" align='center' >Class Name</th> 
	            	 	<th width='150' align='center' style="padding-right: 20px;" >Grade Level</th> 
	           	 	 </tr>
	           	 </table></div>
           	</c:if>
          <div class="DivContents"><table>
           <tr><td height=26 colSpan=1></td></tr> 
          
           <tr><td>
           	<c:if test="${userTypeId eq '5' || userTypeId eq '6'}"> 
               	 <table table width="145%" border="0" cellspacing="0" cellpadding="0" >
             </c:if> 
             	<c:if test="${userTypeId eq '3'}"> 
               	 <table table width="120%" border="0" cellspacing="0" cellpadding="0" >
             </c:if>  	 	 
               	 	 <c:forEach items="${gradesLt}" var="grade" varStatus="cnt">
          			  <tr id="table${cnt.count}" height="20" colspan="30">
          			  <td height=3 valign="top" width=80 colspan="6" style="" class="header" ><font color="black">${grade.masterGrades.gradeName}</font> </td>
          			  <td>
          			    <c:forEach items="${classesMap}" var="classesMap"  varStatus="status">
          			     <c:choose>
    						<c:when test="${userTypeId eq '3'}">
    							 <table width="81%" border="0" cellspacing="0" cellpadding="0" >
          			      	        <c:if test="${classesMap.key eq grade.gradeId}">
	          			      	    <c:forEach items="${classesMap.value}" var="gradeClass"  varStatus="loop">
	          			      	    <tr id="${loop.count}" style=""> 
   									      <td width="180" valign="top" style="padding-left: 10px;" class='txtStyle'> ${gradeClass.studentClass.className} </td>
										  <td width="200" valign="top" align='left'>
											<select	id="gradeLevelId${grade.gradeId}${loop.count}" name="gradeLevelId${grade.gradeId}${loop.count}" onchange=""  style='width:130px;' class='listmenu'>
												<option value="select">Select Grade Level</option>
												<c:forEach items="${gradeLevelLt}" var="gl"  varStatus="count">	
													 <c:forEach items="${classLt}" var="class"> 
													  <c:if test="${userTypeId eq '3'}"> 
						                                    <c:if test="${class.grade.gradeId eq grade.gradeId && class.studentClass.classId eq gradeClass.studentClass.classId}"> 
						                                          <c:set var="selected" value="${class.gradeLevel.gradeLevelId}" />
						                                          <c:set var="noSectionsPerDay" value="${class.noSectionsPerDay}" />
						                                          <c:set var="noSectionsPerWeek" value="${class.noSectionsPerWeek}" />
						                                          <c:set var="uniqueId" value="${class.teacherSubjectId}" />
						                                          <c:set var="gradeClassId" value="" />
						                                    </c:if>
					                                    </c:if>
					                                 <c:if test="${userTypeId eq '5' || userTypeId eq '6'}"> 
					                                        <c:if test="${class.gradeClasses.grade.gradeId eq grade.gradeId && class.gradeClasses.gradeClassId eq gradeClass.gradeClassId}"> 
						                                          <c:set var="selected" value="${class.gradeLevel.gradeLevelId}"/>
						                                           <c:set var="uniqueId" value="${class.section.sectionId}" />
						                                           <c:set var="gradeClassId" value="${class.section.gradeClasses.gradeClassId}" />
						                                    </c:if>
					                                  </c:if> 
		                                			 </c:forEach>
													<option value="${gl.gradeLevelId}" ${selected == gl.gradeLevelId ? 'selected="selected"' : ''} >${gl.gradeLevelName}</option>
													<c:set var="selected" value=""/>	
												</c:forEach> 
												<input type="hidden" id="uniqueId${grade.gradeId}${loop.count}" name="uniqueId${grade.gradeId}${loop.count}" value="${uniqueId}">
												<input type="hidden" id="gradeClassId${grade.gradeId}${loop.count}" name="gradeClassId${grade.gradeId}${loop.count}" value="${gradeClassId}">	
											</select>
										 </td>
										 <td width="180" valign="top" align='left'><input type="text" id="noSectionsPerDay${grade.gradeId}${loop.count}"  name="noSectionsPerDay${grade.gradeId}${loop.count}" size="8" value="${noSectionsPerDay}" onBlur="numVal('noSectionsPerDay${grade.gradeId}${loop.count}')"></td>
									     <td width="120" valign="top" align='left'><input type="text" id="noSectionsPerWeek${grade.gradeId}${loop.count}" name="noSectionsPerWeek${grade.gradeId}${loop.count}" size="8" value="${noSectionsPerWeek}" onBlur="numVal('noSectionsPerWeek${grade.gradeId}${loop.count}'),perDayAndWeekValidation('noSectionsPerDay${grade.gradeId}${loop.count}','noSectionsPerWeek${grade.gradeId}${loop.count}')"></td>
		  							 	 <td  width="80" valign="top"align='left'><input type="submit" class="button_green" id="update${grade.gradeId}${loop.count}"  name="update${grade.gradeId}${loop.count}" value="Update" onclick="updateGradeClass('${grade.gradeId}','${loop.count}')"></td>
		  							 	 <td  width="15"></td>
										 <td  width="80" valign="top" align='left'><input type="submit" class="button_green" id="delete${grade.gradeId}${loop.count}" name="delete${grade.gradeId}${loop.count}" value="Delete" onclick="deleteGradeClass('${grade.gradeId}','${loop.count}','${cnt.count}')" ></td>
									</tr> 
									<tr><td height=16 colSpan=3></td></tr> 
									</c:forEach>
          			      	   </c:if>
          			      	 </table>
    					  </c:when>
    					  <c:when test="${userTypeId eq '5' || userTypeId eq '6'|| userTypeId eq '4'}">
	    						<table width="60%" border="0" cellspacing="0" cellpadding="0" >
	          			      	        <c:if test="${classesMap.key eq grade.gradeId}">
		          			      	    <c:forEach items="${classesMap.value}" var="gradeClass"  varStatus="loop">
		          			      	    <tr id="${loop.count}" style=""> 
	   									      <td width="180" valign="top" style="padding-left: 10px;" class='txtStyle'> ${gradeClass.studentClass.className} </td>
											  <td width="200" valign="top" align='left'>
												<select	id="gradeLevelId${grade.gradeId}${loop.count}" name="gradeLevelId${grade.gradeId}${loop.count}" onchange=""  style='width:130px;' class='listmenu'>
													<option value="select">Select Grade Level</option>
													<c:forEach items="${gradeLevelLt}" var="gl"  varStatus="count">	
														 <c:forEach items="${classLt}" var="class"> 
														  <c:if test="${userTypeId eq '3'}"> 
							                                    <c:if test="${class.grade.gradeId eq grade.gradeId && class.studentClass.classId eq gradeClass.studentClass.classId}"> 
							                                          <c:set var="selected" value="${class.gradeLevel.gradeLevelId}" />
							                                          <c:set var="noSectionsPerDay" value="${class.noSectionsPerDay}" />
							                                          <c:set var="noSectionsPerWeek" value="${class.noSectionsPerWeek}" />
							                                          <c:set var="uniqueId" value="${class.teacherSubjectId}" />
							                                          <c:set var="gradeClassId" value="" />
							                                    </c:if>
						                                    </c:if>
						                                 <c:if test="${userTypeId eq '5' || userTypeId eq '6' || userTypeId eq '4'}"> 
						                                        <c:if test="${class.gradeClasses.grade.gradeId eq grade.gradeId && class.gradeClasses.gradeClassId eq gradeClass.gradeClassId}"> 
							                                          <c:set var="selected" value="${class.gradeLevel.gradeLevelId}"/>
							                                           <c:set var="uniqueId" value="${class.section.sectionId}" />
							                                           <c:set var="gradeClassId" value="${class.section.gradeClasses.gradeClassId}" />
							                                    </c:if>
						                                  </c:if> 
			                                			 </c:forEach>
														<option value="${gl.gradeLevelId}" ${selected == gl.gradeLevelId ? 'selected="selected"' : ''} >${gl.gradeLevelName}</option>
														<c:set var="selected" value=""/>	
													</c:forEach> 
													<input type="hidden" id="uniqueId${grade.gradeId}${loop.count}" name="uniqueId${grade.gradeId}${loop.count}" value="${uniqueId}">
													<input type="hidden" id="gradeClassId${grade.gradeId}${loop.count}" name="gradeClassId${grade.gradeId}${loop.count}" value="${gradeClassId}">	
												</select>
											 </td>
										<c:if test="${userTypeId eq '5' || userTypeId eq '4'}"> 
			  							 	<td  width="80" valign="top"align='left'><input type="submit" class="button_green" id="update${grade.gradeId}${loop.count}"  name="update${grade.gradeId}${loop.count}" value="Update" onclick="updateGradeClass('${grade.gradeId}','${loop.count}')"></td>
			  							 	<td  width="15"></td>
											<td  width="80" valign="top" align='left'><input type="submit" class="button_green" id="delete${grade.gradeId}${loop.count}" name="delete${grade.gradeId}${loop.count}" value="Delete" onclick="deleteGradeClass('${grade.gradeId}','${loop.count}')" ></td>
										</c:if>
										<c:if test="${userTypeId eq '6'}"> 
			  							 	<td  width="80" valign="top"align='left'></td>
			  							 	<td  width="15"></td>
											<td  width="80" valign="top" align='left'></td>
										</c:if>
										</tr> 
										<tr><td height=16 colSpan=3></td></tr> 
										</c:forEach>
	          			      	   </c:if>
	          			      	 </table>
    						</c:when>
    					</c:choose>
          			    </c:forEach>
          			   </td>
           			  </tr>
               	  </c:forEach>
               	</table>
            </td></tr></table></div>
   </c:when>
</c:choose>
<div class="DivContents"><table>
    	<c:if test="${userTypeId ne '6'}"> 
    	 <tr><td width="600" align="center"  class="heading-up"> <input  width="250"  type="button" class="button_green" value="Add Educational Info" onclick="addEducationalInformation()" style='font-size:1em;'/></td>
		  <td>
		  	<div id="dialog"  style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title=''> 
				 <iframe id='iframe' frameborder='0' scrolling='yes' width='100%' height='95%' src='' style="min-height:582px;"></iframe>
			</div>
		  </td></tr>
	</c:if></table></div>
   </td></tr> </table> 
   
</body>
</html>