<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script src="/resources/javascript/common/my_profile.js"></script>

<!-- <script src="/resources/javascript/common/notify.js"></script> -->

<div class="" style="">
<form name="personal_interest">
<c:choose>
<c:when test="${typeOfInterest == 'sports'}">
 <table class="des" border=0 align="center" width="45%">
  	   		
 </c:when>
 <c:otherwise>
 <table width="90%" border=0 class="des" align="center">
 </c:otherwise>
 
 </c:choose>
           		 <tr><td>
               <c:forEach items="${InterestLt}" var="interest" varStatus="status">
               <div class="Divheads"><table>
               	<tr><td height=3 valign="top" width="350" >
              	<font color="white"> <b>${status.count}. ${interest.interest} </b></font>
             	</td></tr></table></div>
                <div class="DivContents">       
               	 <table width="100%" align="center"><br>
          			<c:set var="count" value="0" scope="page"/>
    		    	<c:set var="flag" value="true" />
               		 <c:forEach items="${subInterestLt}" var="subInterest" varStatus="loop" >
                		    <c:if test="${subInterest.interest.interestId eq interest.interestId}"> 
               		    		<c:set var="count" value="${count+1}"  scope="page"/>
	               		    	<c:if test="${flag eq true}">
									<c:set var="flag" value="false" />
									<tr>
							  	</c:if>
               					<td width="130" align="left" valign="middle" class="txtStyle" style="padding-bottom: 10px">
               					 <c:forEach items="${userInterestsLt}" var="userInterests" >
                                   <c:if test="${userInterests.subInterest.subInterestId eq subInterest.subInterestId}"> 
                                         <c:set var="selected" value="true"/>
                                   </c:if>
		                        </c:forEach>
               					<input id="checkbox${loop.count}" name="userInterest" value="${subInterest.interest.interestId}-${subInterest.subInterestId}" type=checkbox class='checkbox-design'  ${selected == 'true' ? 'checked' : ''} />  
               					<label for='checkbox${loop.count}' class='checkbox-label-design'>${subInterest.subInterest}</label><br/></td>
               					<c:set var="selected" value="false"/>
               					<c:choose>
									<c:when test="${count == '3'}">
										 <c:set var="count" value="0"/>
										 <c:set var="flag" value="true" />
										 </tr>										 
								  	 </c:when>
							 </c:choose>
               				</c:if>
               				
               		 </c:forEach>
               		</tr>
               	</table></div>
              	 
           <table class="DivContents">
              	<tr><td height=16 colSpan=3></td></tr> 
              		 <tr>
           <td><table width="40%" border="0" cellspacing="0" cellpadding="0" >
          <tr >
               <td width="123" height="35" align="left" valign="middle"></td>
               <td width="125" align="right" valign="middle"><span class="txtStyle">Other</span></td>
               <td width="10" height="35" align="center" valign="middle">&nbsp;:</td>
               <td  width="123"  height="35" align="left" valign="middle"> &nbsp;&nbsp;</td>
               <td width="140" height="35" align="left" valign="middle">
                   <input type="text" name="otherAreasOfInterest1"  value=""/></td>
               <td height="35" align="left" valign="middle"></td>
               <td style="padding-left: 20px" height="35" align="right" valign="middle"><span class="txtStyle">Other</span></td>
               <td height="35" align="center" valign="middle">:</td>
               <td height="35" align="left" valign="middle"><input type="text" name="otherAreasOfInterest2" value="" /></td>
               <td height="35" align="left" valign="middle"></td>
           </tr>
           
           <tr>
               <td width="123" height="35" align="left" valign="middle"></td>
               <td width="125" align="right" valign="middle"><span class="txtStyle">Other</span></td>
               <td width="10" height="35" align="center" valign="middle">&nbsp;:</td>
               <td height="35" align="left" valign="middle">&nbsp;&nbsp;</td>
               <td width="140" height="35" align="left" valign="middle"><input type="text" name="otherAreasOfInterest3" value=""/></td>
               <td height="35" align="left" valign="middle"></td>
               <td height="35" align="right" valign="middle"><span class="txtStyle">Other</span></td>
               <td height="35" align="center" valign="middle">&nbsp;:&nbsp;&nbsp;</td>
               <td height="35" align="left" valign="middle"><input type="text" name="otherAreasOfInterest4" value=""/></td>
               <td height="35" align="left" valign="middle"></td>
           </tr>
           <tr>
               <td width="123" height="35" align="left" valign="middle"></td>
               <td width="125" align="right" valign="middle"><span class="txtStyle">Other</span></td>
               <td width="10" height="35" align="center" valign="middle">&nbsp;:</td>
               <td height="35" align="left" valign="middle">&nbsp;&nbsp;</td>
               <td width="140" height="35" align="left" valign="middle"><input type="text" name="otherAreasOfInterest5" value=""/></td>
               <td height="35" align="left" valign="middle"></td>
               <td height="35" align="right" valign="middle"></td>
               <td height="35" align="center" valign="middle"></td>
               <td height="35" align="left" valign="middle"></td>
               <td height="35" align="left" valign="middle"></td>
           </tr>
		 </table></td>	
           </tr>
           <tr><td height=10 colSpan=3></td></tr> 
      </table> </c:forEach>
      <div class="DivContents">
      <table align="center" width="100%">
   <c:if test="${userTypeId ne 6}"> 
	    <tr><td height="10" >
	    	<table width="100%">
	    	<tr>
               <td width="30%" height="10" align="right" valign="middle"> 
              		 <div class="button_green" align="right" onclick="updatePersonalInterest()">Submit Changes</div> 
               </td>
               <td width="30%" height="10" align="left" valign="middle"  style="padding-left: 2em;">
               	 <input type="reset" class="button_green" value="Clear" > 
               </td>
               </tr>
               </table>
        </td></tr>
   </c:if>
</table>
</form></div>
      	</td>
   </tr></table></div>
   