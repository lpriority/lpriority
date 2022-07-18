<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>        
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title>Create Unit</title>	
    </head>
    <script>
    var count = 0;
    $(document).ready(function() {
    	addActivities(); 
    	});
    </script>
    <body> 
     <table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
     <tr><td width="80%">
    <form:form id="createLesson"  action="addlessons.htm" modelAttribute="lesson" target="lesson" method="post">
           <table width="90%" border=0 align="center" cellPadding=0 cellSpacing=0>
                <tr>
                    <td height="0" colspan="4" align="center" valign="top">
                        <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0"  colspan="4" >
                            <tr>
                                <td width="342" align="center" valign="middle">&nbsp;</td>
                                <td width="419" align="center" valign="middle">&nbsp;</td>
                                <td width="365" align="center" valign="middle">&nbsp;</td>
                                <td></td>
                            </tr>
                            <tr>
                                    <td width="390" align="left"><span class="tabtxt">Lesson Name :</span></td>
                                    <td width="342" align="left">
                                        <span class="tabtxt"><form:input id="lessonName" path="lessonName" readonly="${(mode eq 'create')?'false':'true'}" disabled="${(mode eq 'create')?'false':'true'}" />  </span>  
                                            <input id="gradeId" name = "gradeId" value="${gradeId}" type="hidden"/>
                                            <input id="classId" name = "classId" value="${classId}" type="hidden"/> 
                                            <input id="mode" name = "mode" value="${mode}" type="hidden"/> 
                                            <form:hidden id="unitId" path="unit.unitId" required="required"  value="${unitId}"/> 
                                            <form:hidden id="lessonId" path="lessonId" required="required" value="${lessonId}"/> 
                                            <input id="activitiesSize" name="activitiesSize" value="${activitiesSize}" type="hidden"/> 
                                    </td>
                                    <td width="342" align="left"><span class="tabtxt">Lesson Objective/Outcome :</span></td>
                                    <td width="342" align="left">
                                        <span class="tabtxt">
										<form:textarea  id="lessonDesc" path="lessonDesc" readonly="${(mode eq 'create')?'false':'true'}" disabled="${(mode eq 'create')?'false':'true'}"/> 
                                        </span>                                                  
                                    </td>
                                </tr>
                                <tr><td height="20" colspan="2" ></td></tr>
                                <tr>
                                	<td colspan="1" ><span class="tabtxt">Lesson Activities : </span></td>
                                 	<td colspan="1" valign="top" style="font-size:16px;"> 
                                 		<div class="button_green" align="Add Activity" onclick="addActivities()" id="addActivity" name="addActivity">Add Activity</div>
									</td>
								</tr>
                        </table>
                    </td>
                </tr>
                <tr><td height="10" colspan="2" ></td></tr>
          </table>
          <table width="100%" align="center">
            <tr><td colspan="4" align="center"><div id="activities" align="center" style="overflow-y: auto ;border-style: groove;border-width: 1px;overflow-x: hidden ;max-height: 200px;min-height: 200px; width: 90%;"><table width="100%" >
                     <tr><td>
                     <c:choose>
						<c:when test="${activitiesSize > 0}">
						 <c:forEach var="i" begin="0" end="${activitiesSize-1}" step="1" varStatus="loop">
								 <div id="div${i}"> 
						           <table><tr>
						           <td><form:hidden id="activityId${i}" path="activityList[${i}].activityId"/> 
								      <span id='span${i}' style="padding-top:0.2em;" class="tabtxt">Activity  ${i+1} :</span></td>
								  <td><form:textarea id="${i}" rows="3" cols="60" path="activityList[${i}].activityDesc" onchange="${(mode eq 'edit')?'updateActivity(this)':''}"/> </td>
								  <td><i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;" onclick="removeActivity(${i})"></i></td>
								  <td id="result${i}"></td>
						  </tr></table>
								 </div>
						 </c:forEach>
						</c:when>
						<c:otherwise>
						</c:otherwise>
						</c:choose>
                     </td> 
                     </tr>
                    <tr><td height=30 colSpan=4><div id="activitiesDiv"></div></td></tr> 
                 </table></div></td></tr>
          </table>
</form:form>
</td></tr>
</table>
<table width="100%">
<c:choose>
 <c:when test="${mode eq 'create'}"> 
  <tr>
      <td align="center">
           <table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:16px;" id="submitDiv">
               <tr><td height="10" colspan="2" ></td></tr>
               <tr>
                   <td width="45%" height="25" align="right">
                       <div class="button_green" align="right" onclick="createLesson()">Submit Changes</div>
                   </td>
                   <td width="10%" align="center"></td>
                   <td width="50%" align="left" valign="middle">
                        <div class="button_green" align="right"  onclick="$('#lessonName').val(''); $('#lessonDesc').val('');return false;">Clear</div>
                   </td>
               </tr>
           </table>
       </td>
   </tr>
</c:when> 
<c:otherwise>
<tr><td align="left" height="15"><font color="#8cb4cd">* Changes will apply automatically</font> </td></tr>
</c:otherwise>
</c:choose>
  <tr>
      <td width="325" height="15" align="center" colspan="5">
      		<div id="resultDiv" class="status-message"></div>
      </td>
  </tr>
  </table>
</body>
</html>