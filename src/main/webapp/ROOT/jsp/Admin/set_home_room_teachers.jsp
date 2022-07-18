
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="true"%>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
		<c:choose>
        			<c:when test="${empty sectionsList}"><table align="center" width="100%" style="padding-left:11em;"><tr >
							<td width="100%" class="status-message" align="left" colspan="3"><c:out  value="No Data Found"/></td>
						</tr>   </table>     				              			
       				</c:when>        			
        			<c:otherwise>
        			 <table class="des" border=0 align="left" width="50%"><tr><td>
				     <div class="Divheads"><table class="headsColor"><tr><td>
				     <td  width="100" align="center" class="tabtxt" ><div id="subTitle"> Sections </div></td>
							<td  width="50"></td>
							<td  width="108" align="center" class="tabtxt"><div id="subTitle"> Teachers </div></td>							
						</tr></table></div>
    			
    			 <div class="Divcontents"><table>
    			<tr><td  height="10"></td></tr>
				<c:forEach items="${sectionsList}" var="section" varStatus="inc">	
					<input type="hidden" id="size" name="sectionSize" value="${fn:length(sectionsList)}" />									
					<tr class="heading-up">
						<td  width="100" class="txtStyle" align="center"> ${section.section} </td>
						<td  width="70">
							<spring:bind path="homeroomClassesForm.homeroomClassesList[${inc.index}].section.sectionId">
								<form:hidden path="homeroomClassesForm.homeroomClassesList[${inc.index}].section.sectionId" id="sectionId${inc.index}" value="${section.sectionId}" />
							</spring:bind>
							<input type="hidden" id="secTeacherId${inc.index}" value="${section.teacherId}">
							<input type="hidden" id="homeRoomId${inc.index}" value="${section.homeroomId}">
						</td>
						<td  width="108"><form:select path="homeroomClassesForm.homeroomClassesList[${inc.index}].teacher.teacherId" id="teacherId${inc.index}" onchange="checkTeacher(${inc.index});" >
                            		<option value="0" selected>Select Teacher</option>
                            	<c:forEach items="${teachersList}" var="teacher">
                            		<option value="${teacher.teacherId}" <c:if test="${section.teacherId == teacher.teacherId}">selected="selected"</c:if>> 
                            			${teacher.userRegistration.firstName} ${teacher.userRegistration.lastName}
                            		</option>
                            	</c:forEach>
                        	</form:select></td>	
                        <td width="425" height="35" align="center" colspan="5">
                     		<b><div id="resultDiv${inc.index}" class="status-message"></div></b>
                        </td>							
					</tr>
				</c:forEach></table></div></td></tr></table></c:otherwise></c:choose>
				
		