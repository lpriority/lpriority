<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script>
$(document).ready(function () {
     $('#returnMessage').fadeOut(3000);
});
</script>
<table><tr><td>
<c:set var="bor" value="0"></c:set>
<c:set var="de" value=""></c:set>
 <c:if test="${fn:length(rtiResults) > 0}">
 <c:set var="bor" value="0"></c:set>
 <c:set var="de" value="des"></c:set>
 </c:if>&nbsp;</td></tr></table>
 <table border="${bor}" class="${de}"><tr><td>
 <c:if test="${fn:length(rtiResults) > 0}">
  <div class="Divheads">
 <table>
 <tr>
        <td width='250' height='0' align='center'>Answers by Correct Response </td>
        <td width='250' height='0' align='center'>Answers by Wrong Response </td>
        <td width='150' height='0' align='center'>Percentage</td>
        <td width='200' height='0' align='center'>Assigned Date</td></tr>
       </table></div>
       <div style="padding: 2px 5px 2px 5px"><table> 
       <tr><td>&nbsp;</td></tr>
                  <c:set var="s" value="0" />  
                   <c:forEach items="${rtiResults}" var="rr">
                                                            
                      <tr><td width='250' height='0' align='center' class='txtStyle'><c:out value="${correctResponses[s]}"><</c:out></td>
                      <td width='250' height='0' align='center' class='txtStyle'><c:out value="${wrongResponses[s]}"></c:out></td>
                      <td width='150' height='0' align='center' class='txtStyle'><c:out value="${rr.percentage}"></c:out></td>
                      <td width='200' height='0' align='center' class='txtStyle'><c:out value="${rr.assignment.dateAssigned}"></c:out></td>
                      </tr>
                       <c:set var="s" value="${s+1}" />
                   </c:forEach>
                    <tr><td height="20"></td></tr></table></div></c:if></td></tr></table>
                   <table><tr><td>                         
                 <c:if test="${fn:length(rtiResults) <= 0}">
                      <tr><td align ='center' colspan='4' id="returnMessage"><font color='blue'>No Diagnostics are Assigned today.</font></td>
                      <td colspan='2'></td></tr>

                 </c:if></td></tr>
        </table>
