<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.lp.model.UserRegistration"%>
<%@ page session="true"%>
  <table width="85%" align="center">
   	<tr><td width="100%" valign="top" align="center">
   	 <c:choose>
		<c:when test="${fn:length(stemUnitLt) gt 0}">
		   	<div class="des" style="font-size:13px;overflow-y: auto;overflow-x: hidden;color:black;margin-right: auto; margin-left: auto; width: 65%;">
		   	 <table width="100%" align="center" style='font-size:13px;font-family:"Lato", "PingFang SC", "Microsoft YaHei", sans-serif;margin-bottom:1em;'>
		   	    <tr class="Divheads"><td width="10%" height="40" align="center">S.No</td><td width="40%" align="center">${LP_STEM_TAB} Unit Name</td><td width="40%" align="center">Created By</td></tr>
		        <tr><td height="5"></td>
		         <c:forEach items="${stemUnitLt}" var="stemUnit" varStatus="status"> 
		         <c:set var="userId" value="${stemUnit.userRegistration.regId}"  scope="request"/>
		         <c:set var="userName" value="${stemUnit.userRegistration.firstName} ${stemUnit.userRegistration.lastName}" scope="request"/>
		        <%
			        UserRegistration userReg = (UserRegistration) session.getAttribute("userReg");
			        long regId = userReg.getRegId();
			        long userId = Long.parseLong(String.valueOf(request.getAttribute("userId")));
			        if(regId == userId){
			        	request.setAttribute("userName", "You");
			        }
		        %>
		           <tr> 
		        	 <td width="10%" align="center">${status.count} 
		        	    <input id="stemUnitId${status.index}" name="stemUnitId${status.index}" type="hidden" value="${stemUnit.stemUnitId}">  
		        	    <input id="stemUnitName${status.index}" name="stemUnitName${status.index}" type="hidden" value="${stemUnit.stemUnitName}">  
		             </td>
			         <td width="60%" align="center" height="20"><a href="#" onclick="javascript:addStemUnit(${stemUnit.stemUnitId});">${stemUnit.stemUnitName}</a></td>
			         <td width="30%" align="center">${userName}</td>
			      </tr> 
		         </c:forEach>
		     </table>
		   </div>
    </c:when>
 </c:choose>
  </td></tr></table>