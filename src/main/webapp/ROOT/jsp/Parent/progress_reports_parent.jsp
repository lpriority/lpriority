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
<title>Progress Reports</title>
<!-- <script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script> -->
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/ParentJs.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
 <input type="hidden" id="tab" name ="tab" value="${tab}" />
<%-- <table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
       <tr><td height=20>&nbsp;</td></tr>
       <tr>
	       <td>
               
           </td>
           <td vAlign=middle align=right colspan="20">
                    <div> 
                    	<%@ include file="/jsp/CommonJsp/view_progress_report_tabs.jsp" %>
                    </div>
           </td>
       </tr>
    </table>
   <table width="100%" border="0" cellspacing="0" cellpadding="0" class="title-pad">
    
    <tr><td class="sub-title title-border" height="40" align="left">Progress Report </td></tr>  
    </table> --%>
   <table width="100%" border="0" cellspacing="0" cellpadding="0" class="title-pad">
	   <tr>
			<td>
			 <table width="100%" height="100%" border=0 align="left" cellPadding=0 cellSpacing=0>
			 <tr><td></td></tr> 
			 <tr><td height="20" colspan="2"></td></tr>     
			  <tr><td align="left"  width="128px" class="label"> 
			   Child&nbsp;&nbsp;
			   	<select name="studentId" class="listmenu" id="studentId" onChange="loadChildClasess()" 	required="required" style="width: 120px;">
					<option value="">Select Child</option>
					<c:forEach items="${students}" var="st">
						<option value="${st.studentId}">${st.userRegistration.title} ${st.userRegistration.firstName} ${st.userRegistration.lastName}</option>
					</c:forEach>
				</select>
			</td> 					
			<td align="left"  width="128px" class="label">  Class &nbsp;
			   	<select id="csId" name="csId" class="listmenu" onchange="loadReportDates()" style="width: 120px;">
					<option value="">Select Subject</option>
				</select>
			</td>
			
			<td align="left"  width="100px" class="label">Date &nbsp;
				<select id="dateId" class="listmenu" onChange="showPrevious()" required="required">
					<option value="">Select Date</option>
				</select>
			</td>
			<td width='300px'>&nbsp;</td>
			<td width='120px'>&nbsp;</td></tr>
			  </table>		
	   </tr>
	   <tr>
			<td height="30" colspan="2">&nbsp;<br><br></td>
	   </tr>
	</table>
	<div id="previousDiv"></div>	   	
</body>
</html>