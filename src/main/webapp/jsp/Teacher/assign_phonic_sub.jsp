<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<script type="text/javascript" src="resources/javascript/TeacherJs/assign_phonic_skill_test.js"></script>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script>
$("#checkAll").click(function () {
    $('input:checkbox').not(this).prop('checked', this.checked);
});
</script>


<table align="center" width="60%"><tr><td class="des"><div class="Divheads"><table width="100%" align="center"><tr><td align="center">Assign Phonic Skill Test</td></tr></table></div>
                            <div class="DivContents"><table  width="100%">
                            <tr><td height="10" colspan="2" align="center" valign="middle">
                          		  <tr><td width="50%" height="5"></td><td width="50%" height="5"></td></tr>
                                <table  width="100%" align=center  style="padding-left: 2em;">  
                                	  	 	<tr><td width="2" height="2"></td></tr>
                                	 <c:if test="${langId==1}">
                                	  <input type="hidden" id="groupsLength" name="groupsLength" value="${fn:length(phonicGroupsLt)}"/>
	                               <c:forEach items="${PhonicSectionsLt}" var="phonicSections" varStatus="status">
	                                   <tr> <td class='header' width="490px" style="color:#004b54;text-shadow:0 1px 3px rgb(189, 189, 189);">${phonicSections.name} :</td></tr>
	                                	<tr><td width="3" height="3"></td></tr>
	                                	<c:forEach items="${phonicGroupsLt}" var="phonicGroups" varStatus="loop" >
				                		    <c:if test="${phonicGroups.phonicSections.phonicSectionId eq phonicSections.phonicSectionId}">
					                		    <input type="hidden" id="groupId${loop.count}" name="groupId${loop.count}"" value="${phonicGroups.groupId}"/>
					               		  	 	<tr><td width="100%" class="" ><span class='folderStyle'>${phonicGroups.title}  :</span><span class='txtStyle'> ${phonicGroups.question}</span></td></tr>
					               		  	 	<tr><td width="2" height="2"></td></tr>
				               				</c:if>
					               	    </c:forEach>
					               		 <tr><td width="5" height="5"></td></tr>
					               		  </c:forEach>
					               		  </c:if>
					               		  <c:if test="${langId==2}">
					               		  <c:forEach items="${bpstTypesList}" var="bp">
	                                       <tr> <td class='' width="490px"   style="color:#004b54;text-shadow:0 1px 3px rgb(189, 189, 189);font-size:18px;"><input type="radio" class="radio-design"  name="bpsttype" id="bpst${bp.bpstTypeId}" value="${bp.bpstTypeId}" onClick="getPhonicGroupsByBpstType(${bp.bpstTypeId})"><label for="bpst${bp.bpstTypeId}" class="radio-label-design label">${bp.bpstType}&nbsp;${bp.desc}</label></td></tr>
	                                	   <tr><td width="3" height="3"></td></tr>
	                                	   <tr><td><div id="bpstGroup${bp.bpstTypeId}"></div></td></tr>
	                                	</c:forEach>
					               		  </c:if>
					               		   <tr><td><table width="100%" style="font-size: 14px;"><tr>
	                                            <th align="left"width="50%">
	                                               Due Date : &nbsp;&nbsp; <input type="text" id="dueDate" name="dueDate" readonly="readonly">
	                                            </th>
	                                            <th align="left" width="50%">
	                                               Title : &nbsp;&nbsp; <input name='titleId' type='text' id='titleId' size='20' maxlength='15' onblur="checkTitleById();" value=''/>
	                                            </th>
	                                        </tr></table></td></tr>
	                                        <tr><td width="10" height="20"></td></tr>
	                                        <tr>
	                                             <td align="center"><div id="assignPhonicSkillsTest" name="assignPhonicSkillsTest" class="button_green" onclick="assignPhonicSkillsTest(${langId})">Assign Phonic Skills Test</div></td>
	                                        </tr>
	                                 </table>                                 
                                </td> 
                            </tr>
 			 </table></div></td></tr>
 			 
 			 </table>

