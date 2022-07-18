<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 

 <table  width="100%" align=center  style="padding-left: 2em;">  
                                	  	 	<tr><td width="2" height="2"></td></tr>
               <c:forEach items="${PhonicSectionsLt}" var="phonicSections" varStatus="status">
	               <tr> <td class='header' width="490px" style="color:#004b54;text-shadow:0 1px 3px rgb(189, 189, 189);font-size:16px;">${phonicSections.name} :</td></tr>
	               	<tr><td width="3" height="3"></td></tr>
	               	<c:forEach items="${bpstGroupsLt}" var="pg" varStatus="loop" >
	         		    <c:if test="${pg.phonicGroups.phonicSections.phonicSectionId eq phonicSections.phonicSectionId}">
	             		    <input type="hidden" id="groupId${loop.count}" name="groupId${loop.count}"" value="${pg.phonicGroups.groupId}"/>
	           		  	 	<tr><td width="100%" class="" ><span class='folderStyle'>${pg.phonicGroups.title}  :</span><span class='txtStyle'> ${pg.phonicGroups.question}</span></td></tr>
	           		  	 	<tr><td width="2" height="2"></td></tr>
	      				</c:if>
	           	    </c:forEach>
	          		 <tr><td width="5" height="5"></td></tr>
	       		  </c:forEach>
	 </table>

