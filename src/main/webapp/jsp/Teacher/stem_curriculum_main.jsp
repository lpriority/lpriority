
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<%@ include file="../CommonJsp/include_resources.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${LP_STEM_TAB} Unit</title>
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/curriculum.js"></script>
<script type="text/javascript" src="resources/javascript/CommonValidation.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/stem_curriculum.js"></script>
<script>
$(document).ready(function () {
 	$('#returnMessage').fadeOut(4000);
 	/* $('#dialog').on('dialogclose', function(event) {
 	     alert('closed');
 	 }); */

 	$( ".selector" ).on( "dialogclose", function( event, ui ) { alert('pagehide');} );

});
function clearDiv(){
	$("#unitDiv").empty();
	$("#addStemUnit").hide();
	$("#preMadeStemUnit").hide();
	$("#classId").val('select');
	$("#pathId").empty(); 
	$("#pathId").append(
			$("<option></option>").val('select')
					.html('Select Path'));
	$("#pathId").val('select');
}
</script>
</head>
<body>
   <form:form id="createUnits"  action="createunits.htm" modelAttribute="CreateUnits" method="post">
	<table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
        <input type='hidden'  id='regId' name='regId' value='${regId}' />
              <tr>
                   <td vAlign=bottom align=right>
                       <div> 
                       	<%@ include file="/jsp/curriculum/curriculum_tabs.jsp" %> 
                       </div>
                   </td>
               </tr>
               <tr>
                  <td height="30" colspan="2" align="left"  width="100%">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" align="left"  style="padding-left: 8em;">  
                            <tr><td><input type="hidden" id="teacherObj" value="${teacherObj}" /></td></tr>
                            <tr>
                             <td height="40" align="left" class="label" style="width: 14em;">Grade &nbsp;&nbsp;&nbsp;
                                <select name="gradeId" style="width:120px;" class="listmenu" id="gradeId"  onChange="getGradeClasses();clearDiv()">
                                   <option value="select">select grade</option>
							<c:forEach items="${grList}" var="ul">
								<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
							</c:forEach>
		                                </select><c:out value="${ul.gradeId}"></c:out>
		                             </td>
		                             <td height="40" class="label" style="width: 15em;padding-left: 2em;">Subject &nbsp;&nbsp;&nbsp;
							<select id="classId" style="width:120px;" name="classId" class="listmenu"  onchange="loadPaths();">
								<option value="select">select subject</option>
							</select>
					 	 </td>
					  	<td height="40" class="label" style="width: 15em;padding-left: 2em;">Path &nbsp;&nbsp;&nbsp;
							<select id="pathId" style="width:120px;" name="pathId" class="listmenu" onchange="loadStemContent()" >
								<option value="select">select Path</option>
							</select>
					 	 </td>
		                 <td height="20" class="label" style="width: 45em;padding-left: 2em;">
						   	<div class="button_green" id="addStemUnit" height="52" width="50" onclick="addStemUnit(0)" style="display:none;">Create ${LP_STEM_TAB} Unit</div>
						   	<div class="button_green" id="preMadeStemUnit" height="52" width="50" onclick="loadPremadeStemUnits(0)" style="display:none;">Prepare ${LP_STEM_TAB} Unit</div>
						   	<div class="button_green" id="adoptedStemUnit" height="52" width="50" onclick="addStemUnit(0)" style="display:none;">Adopted ${LP_STEM_TAB} Unit</div>
						</td>
		             </table>
		             <table width="90%" height="90%" border=0 align="center" cellPadding="0" cellSpacing="0">
		                <tr>
			            <td  style="margin-left:90%;margin-right:0px;margin-top:4px;height:2em;width:10em;font-size: 1em;padding: 0.2px 25px;" align="right">
	  	               <div class="button_green" align="right" id="ipalId" style="display: none;" onclick="getIpalResources('childWindow')">iPal Resources</div> 
			           </td> 
		              </tr>
		            </table>
              </td>
             </tr>
             <tr>
				<td></td> 
				<td width="80%" colspan="10" align="center"></td>
	  		</tr>
	        <tr><td height=40 colSpan=3></td></tr> 
	        <tr><td  id="appenddiv"  colspan="5" align="center" valign="top">
	           <div id="pathDiv" style="display:none;"></div>
	        </td></tr>
	        <tr><td align="center" colspan="9" align="center" valign="top"><div id='resultDiv' align='center'/></td></tr>
			<tr><td height=40 colSpan=3></td></tr> 
	         <tr><td colspan="5" align="center" valign="top">
	             <div id="stemUnitDiv" style="display:none;"></div>
	         </td>
	        </tr>
	</table>
	<div id="videoDailog" title="Grade Fluency" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div></td></tr>
	<div id="dialog"  style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title=""><iframe  id ="iframe" src="" frameborder="0" scrolling="no" width="100%" height="98%" src=""></iframe></div>
	<div id="preMadedialog" style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title=""><iframe  id ="preMadeIframe" src="" frameborder="0" scrolling="no" width="100%" height="98%" src=""></iframe></div>
   </form:form>
 
   <div id="iPalDailog"  style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title="">
  		<div id ="iPalDiv"></div>
  	</div>
</body>
</html>