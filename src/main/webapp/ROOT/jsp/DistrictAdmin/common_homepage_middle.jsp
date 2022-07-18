<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<c:set var="userName" value="${userReg.title == null ? userReg.title : ''}${userReg.firstName} ${userReg.lastName }"></c:set>
   <div class="column_middle_grid1">
        <div class="profile_picture">
       	<table width="100%">
	       	<tr>
	       	   <td width="40%">
	        	  <form name="uploadProfilePicForm" id="uploadProfilePicForm" novalidate> 
					 <div class="show-image">
						<a href="#" class="tooltip" data-tooltip="Click here to view your Profile Picture"><img id="imgDiv" class="imgCls" style="display:none;width: 85px;height: 85px;border-radius: 50%;" onClick="openPreviewImage()"/></a>
						<div id="iconDiv" class="fa fa-user-circle-o" aria-hidden="true" style="display:block;font-size: 80px;text-shadow: 0 1px 4px rgb(140, 156, 158);color:#00cae2;margin-top:5px;margin-bottom:5px;"></div>
					     <a href="#" class="tooltip-left" data-tooltip="Upload"><button id="upload" class="stylish-button button--ujarak button--round-l button--border-thin button--text-thick image-buttons" style="margin-left:10%;"><i class="ion-edit" style="font-size:16px;font-weight:bold;"></i></button></a>
						<input type="file" id="file" name="file" required="required" style="display:none;" onChange="showimagepreview(this)"/>
					    <a href="#" class="tooltip-right" data-tooltip="Remove"><button id="remove" class="stylish-button button--ujarak button--round-l button--border-thin button--text-thick image-buttons" onClick="deleteProfileImage()" style="margin-left:70%;"><i class="fa fa-times" style="font-size:14px;font-weight:bold;"></i></button></a>
				    </div>
				  </form>
			   </td>
	       	   <td width="60%" style="font-family: 'Open Sans Condensed', sans-serif;"> 
	       	      <table width="100%">
	       	        <tr><td align="left" width="100%" height="20">
	       	        <span>Hello..!<br><span style="font-size: 13px;letter-spacing:.2px;color:#525000;margin:.2em;"> ${userName}</span></span>
	       	        </td></tr>
	       	        <tr><td align="left" width="100%">
	       	         <table width="100%">
	        	        <tr>
	        	        	<td align="left" width="16%">
			        	         <div class="txtStyle sonar-wrapper" style="${notificationLength > 0 ? 'margin-top:-2em;margin-left: -1.5em;display:block;': 'display:none;'}">
									<div class="sonar-emitter">
								    <div class="sonar-wave"></div>
								  </div>
								 </div>
							 </td>
						  <td align="left" width="80%" style="margin-top:1.8em;" height="30">
					  	 	 <a href="#" onclick="openNotification()" style="${notificationLength > 0 ? 'margin-top:2em;margin-left:-2em;display:block;text-decoration: underline;': 'display:none;'}">You have <font size="2" color="#008a9a">${notificationLength}</font> message${notificationLength > 1 ? 's':''}</a>
					  	   </td></tr>
				  	  </table>
	       	        </td></tr>
	       	      </table>
	       	   </td>
	       	</tr>
       	</table>
        </div>
    </div>
     <c:choose>
		<c:when test="${userReg.user.userType == 'teacher'}">
			 <div class="middle_ribbon" style="margin-left: -3em;margin-top: .5em;"><span class="middle_text">Teacher's Desktop</span></div>
		</c:when> 
		<c:when test="${userReg.user.userType == 'student above 13' || userReg.user.userType == 'student below 13'}">
			 <div class="middle_ribbon" style="margin-left: -3em;margin-top: .5em;"><span class="middle_text">Student's Desktop</span></div>
		</c:when> 
	</c:choose>
     
		 
	
		
        