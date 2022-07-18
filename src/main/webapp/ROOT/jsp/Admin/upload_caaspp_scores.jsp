<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/admin/add_edit_remove_sl_schl.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

	$(document).ready(function () {
		 $('#returnMessage').fadeOut(3000);
	});
</script>
<title>Upload CAASPP Scores</title>
</head>
<body>
	<table style="width:100%">
		<tr><td>
			<table width="100%">
		        <tr><td align="center" colspan="" width="100%"> 
						<table width="100%">
					        <tr><td align="center" colspan="" width="100%"> 
					           	 <table width="100%" class="title-pad" border="0">
					           		 <tr>
				                    	 <td class="sub-title title-border" height="40" align="left" >Upload CAASPP Scores</td>
				                	 </tr>
							     </table>
						   </td></tr></table>
			   </td></tr>
			    <tr><td align="center" colspan="" width="100%" style="padding-top:1.5em;"> 
					   <table class="des" border=0 align="center"><tr><td><div class="Divheads"><table><tr><td class="headsColor">Upload CAASPP Scores</td></tr></table></div>
						   <div class="DivContents">
						   <table>
						   	   <tr><td align="center"> 
							<table  style="width: 100%" align="center" class="space-between">	
							
								<tr>
									<td align="left" class="tabtxt"><form id="uploadCAASPPScoresForm" name="uploadCAASPPScoresForm" action="addCAASPPScores.htm"> 	
										
											 <img src="images/Common/required.gif">Select a file to upload &nbsp;&nbsp;:&nbsp;&nbsp; <input type="file" name="file" id="file" required="required" />
											<br><br><div align="center"><div class="button_green" onclick="uploadCAASPPScores()">Upload</div></div>
									</form>	
									</td>
								</tr>
							</table>
						  </td></tr></table></div></td></tr>
				   		</table>	
		    </td></tr>
		    <tr><td class="status-message" height=50 colSpan=80 align="center" id="returnMessage">${status}</td></tr>
		   </table>
   </td></tr>
   </table>
</body>
</html>