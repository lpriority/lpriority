<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Add/Delete Users</title>
	<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="resources/javascript/Adminjs.js"></script>
    <link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
	<style>
    .tabs {
      position: relative;   
      min-height: 300px;
      clear: both;
      /*margin: 25px 0;*/
    }
    .tab {
      float: right;
    }
    .tab label {
      margin-left: -1px; 
      position: relative;
      left: 1px; 
    }
.content {
	position: absolute;
	top: 28px;
	left: 0;
	right: 0;
	bottom: 0;
	/* padding: 20px; */
}

[type=radio]:checked ~ label {
	background: white;
	z-index: 2;
}

[type=radio]:checked ~ label ~ .content {
	z-index: 1;
}
</style>
<script type="text/javascript">
	$(document).ready(function () {
		document.getElementById("teadis").style.visibility='hidden';
		document.getElementById("pardis").style.visibility='hidden';
		document.getElementById("deldis").style.visibility='hidden';
	    document.getElementById("studis").style.visibility='visible';
	    $('input:radio[name=tab-group-1]')[3].checked = true;
	    
	    $(window).keydown(function(event){
	        if(event.keyCode == 13) {
	          event.preventDefault();
	          return false;
	        }
	      });
	});
</script>
</head>
<body>
	<table width="100%">
	    <tr><td colspan="" width="100%" class="button-group" style="box-shadow:none;"> 
	<div id="page-wrap">
		<div class="tabs">
			<div class="tab">
			<input type="radio" id="tab-4" name="tab-group-1" style="display: none;visibility: hidden;">
				<label for="tab-4" >
					<li><a href="#" onclick="deleteTab()" id="delete" class="button longTitle" style="border-top-right-radius:25px;box-shadow: 0px 5px 5px #888;/* border-bottom-right-radius :90px; */">Delete User</a></li>
				</label>

			<div class="content">
			 <div id="deldis" style="visibility:visible">
			<table width="100%">
			    <tr><td colspan="" width="100%" valign="top"> 
					 <table width="100%" class="title-pad" border="0" style="padding-top: 1em;">
						<tr>
							<td class="sub-title title-border" height="40" align="left" >Delete User</td>
						</tr>
					</table>
				</td></tr>
				<tr><td colspan="" width="100%" style="padding-top:1.5em;" class=""> 	
				<form:form name="adminDeleteUserForm" modelAttribute="user"
					id="register-form">
				<table class="des" border=0 align="center">
				<tr><td><div class="Divheads"><table><tr><td class="headsColor">Delete User</td></tr></table></div>
  				<div class="DivContents">
  				<table width="95%"  style="height:180px;">
  				<tr><td colspan="" width="100%" valign="top" >
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr><td>&nbsp;</td></tr>
						<tr>
							<td width="217" height="80" align="center" valign="middle"
								class="tabtxt"><img src="images/Common/required.gif">&nbsp;Enter Email id</td>
							<td width="40" height="30" align="left" valign="middle" class="text1">:</td>
							<td height="30" width="150" align="left" valign="middle"
								class="tabtxt"><input type="text" name="useremailId"
								id="useremailId"></td>
						</tr>
 						<tr><td height=2 colSpan=80>&nbsp;</td></tr>
					</table>
					</td></tr>
					<tr><td colspan="" width="100%" valign="top">
						<table style="width: 100%" align="center">
								<tbody><tr>
									<td width="65%" align="center" valign="middle">
										<div class="button_green" align="center" onclick="DeleteUser();return false;">Submit Changes</div> 	
									</td>
									<td width="1" align="left" valign="middle"></td>
									<td width="40" align="left" valign="middle">
										<div class="button_green" align="right" onclick="document.adminDeleteUserForm.reset();return false;">Clear</div> 	
									</td>
								</tr>
							</tbody>
						</table>
					</td></tr>
					</table></div></td></tr></table>
					</form:form>
				</td></tr></table>
			</div>
		</div></div>
		<div class="tab">
			<input type="radio" id="tab-3" name="tab-group-1" style="display: none;visibility: hidden;"> 
				<label for="tab-3">
					<li><a href="#"  onclick="parentTab()" id="parent" class="button longTitle" style="box-shadow: 0px 5px 5px #888;"> Add Parent </a></li>
				</label>

			<div class="content">
			<div id="pardis" style="visibility:visible">
			<table width="100%">
			    <tr><td colspan="" width="100%" valign="top"> 
					 <table width="100%" class="title-pad" border="0" style="padding-top: 1em;">
						<tr>
							<td class="sub-title title-border" height="40" align="left" >Add Parent</td>
						</tr>
					</table>
				</td></tr>
				
				<tr><td colspan="" width="100%"  style="padding-top:1.5em;" class=""> 		
 				<form:form name="adminAddParentForm" modelAttribute="user" 
 					id="register-form"> 
 					
<!-- 				Content center start	 -->
				<table class="des" border=0 align="center">
				<tr><td><div class="Divheads"><table><tr><td class="headsColor">Add Parent</td></tr></table></div>		  
  				<div class="DivContents">
  				<table width="95%" style="height:180px;">
  				<tr><td colspan="" width="100%" valign="top" > 
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							
								<td width="217" height="30" align="right" valign="middle"
									class="tabtxt"><img src="images/Common/required.gif"> Enter Student's Email id</td>
								<td width="40" height="30" align="center" valign="middle" class="text1">:</td>
								<td height="30" colspan="6" align="left" valign="middle"
									class="tabtxt"><input type="text" name="studentemailId"
									id="studentemailId"></td>
							</tr>
						
						<tr><br>
							<td width="217" height="80" align="right" valign="middle"
								class="tabtxt"><img src="images/Common/required.gif"> Enter Parent Email id</td>
							<td width="40" height="30" align="center" valign="middle" class="text1">:</td>
							<td height="30" colspan="6" align="left" valign="middle"
								class="tabtxt"><input type="text" name="parent2emailId"
								id="parent2emailId"></td>
						</tr>
					</table>
					</td></tr>
					<tr><td colspan="" width="100%" valign="top">
						<table style="width: 100%" align="center">
									<tbody><tr><br>
										<td width="65%" align="center" valign="middle">
											<div class="button_green" align="center" onclick="adminAddParent();return false;">Submit Changes</div> 			
										</td>
										<td width="1" align="left" valign="middle"></td>
										<td width="40" align="left" valign="middle">
											<div class="button_green" align="right" onclick="document.adminAddParentForm.reset();return false;">Clear</div> 	
										</td>
									</tr>
								</tbody>
						</table>
					</td></tr>
					</table></div></td></tr></table>
 				</form:form> 
			</td></tr></table>
			</div>
		</div></div>
	

		<div class="tab">
			<input type="radio" id="tab-2" name="tab-group-1" style="display: none;visibility: hidden;"> 
				<label for="tab-2">
					<li><a href="#"  onclick="teacherTab()" id="teacher" class="button longTitle" style="box-shadow: 0px 5px 5px #888;"> Add Teacher </a></li>
				</label>

			<div class="content">
			<div id="teadis" style="visibility:visible">
			<table width="100%">
			    <tr><td colspan="" width="100%" valign="top"> 
					 <table width="100%" class="title-pad" border="0" style="padding-top: 1em;">
						<tr>
							<td class="sub-title title-border" height="40" align="left" >Add Teacher</td>
						</tr>
					</table>
				</td></tr>
				<tr><td colspan="" width="100%"  style="padding-top:1.5em;" class=""> 		
				<form:form name="adminAddTeacherForm" modelAttribute="user" method="GET"
					id="register-form">
				<!-- Content center start -->	
				<table class="des" border=0 align="center">
				<tr><td><div class="Divheads"><table><tr><td class="headsColor">Add Teacher</td></tr></table></div>		  
  				<div class="DivContents">
  				<table width="95%" style="height:180px;">
  				<tr><td colspan="" width="100%" valign="top" > 
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
 						<tr><br>
							<td width="217" height="80" align="right" valign="middle"
								class="tabtxt"><img src="images/Common/required.gif"> Enter Teacher's Email id</td>
							<td width="40" height="30" align="center" valign="middle" class="text1">:</td>
							<td height="30" colspan="6" align="left" valign="middle"
								class="tabtxt"><input type="text" name="emailId"
								id="emailId"></td>
						</tr>
					</table>
					</td></tr>
					<tr><td colspan="" width="100%" valign="top">
						<table style="width: 100%" align="center">
									<tbody><tr><br>
										<td width="65%" align="center" valign="middle">
											<div class="button_green" align="center" onclick="adminAddTeacherVal();return false;">Submit Changes</div> 			
										</td>
										<td width="1" align="left" valign="middle"></td>
										<td width="40" align="left" valign="middle">
											<div class="button_green" align="right" onclick="document.adminAddTeacherForm.reset();return false;">Clear</div> 	
										</td>
									</tr>
								</tbody>
						</table>
					</td></tr>
					</table></div></td></tr></table>
				</form:form>
			</td></tr></table>
			</div>
		</div></div>
		<div class="tab">
			<input type="radio" id="tab-1" name="tab-group-1" style="display:none;visibility: hidden;"> 
				<label for="tab-1">
					<li><a href="#" onclick="studentTab()" id="student" class="buttonSelect longTitle" style="border-top-left-radius:25px;box-shadow: 0px 5px 5px #888;/* border-bottom-left-radius :90px */">Add Student</a></li>
				</label>
				<div class="content" align="center">
				 <div id="studis" style="visibility:visible">
				<table width="100%">
			    <tr><td colspan="" width="100%" valign="top"> 
					 <table width="100%" class="title-pad" border="0" style="padding-top: 1em;">
						<tr>
							<td class="sub-title title-border" height="40" align="left" >Add Student</td>
						</tr>
					</table>
				</td></tr>
				 <tr><td colspan="" width="100%" style="padding-top:1.5em;" class=""> 		
					<form:form name="adminAddUserForm" modelAttribute="user">
				    <!-- Content center start -->	
				    <table class="des" border=0 align="center">
				<tr><td><div class="Divheads"><table><tr><td class="headsColor">Add Student</td></tr></table></div>
				<div class="DivContents">	
	  				<table width="95%">
	  				<tr><td colspan="" width="100%" valign="top" > 
						<table width="100%" border="0" cellspacing="0" cellpadding="0"><br>
							<tr>
								
								<td width="217" height="30" align="right" valign="middle"
									class="tabtxt"><img src="images/Common/required.gif"> Enter Parent's Email id</td>
								<td width="40" height="30" align="center" valign="middle" class="text1">:</td>
								<td height="30" colspan="6" align="left" valign="middle"
									class="tabtxt"><input type="text" name="parentemailId"
									id="parentemailId"></td>
							</tr>
							<tr>
								<td height="10" colspan="9" align="left" valign="top"></td>
							</tr>
							<tr>
							
								<td width="217" height="30" align="right" valign="middle"
									class="tabtxt"><img src="images/Common/required.gif"> Enter Student's Email id</td>
								<td width="40" height="30" align="center" valign="middle" class="text1">:</td>
								<td height="30" colspan="6" align="left" valign="middle"
									class="tabtxt"><input type="text" name="stdemailId"
									id="stdemailId"></td>
							</tr>
							<tr>
								<td height="10" colspan="9" align="left" valign="top"></td>
							</tr>
							<tr>
								
								<td width="217" height="30" align="right" valign="middle"
									class="tabtxt"><img src="images/Common/required.gif"> Student age below 13 ?</td>
								<td width="40" height="30" align="center" valign="middle" class="text1">:</td>
								<td height="30" align="left" valign="middle"><table
										style="width: 100%">
										<tr>
											<td width="50%" align="left" valign="middle" class="tabtxt">Yes
												<input type="radio" name="studentage" id="studentage"
												checked value="below" />
											</td>
											<td width="50%" align="left" valign="middle" class="tabtxt">No<input
												type="radio" name="studentage" id="studentage" value="above" /></td>
										</tr>
									</table></td>
							</tr>
						</table>
						</td></tr>
						<tr><td colspan="" width="100%" valign="top"  style="display:block;">
							<table style="width: 100%" align="center" id="stdTbl">
										<tbody><tr><br>
											<td width="65%" align="center" valign="middle">
												<div class="button_green" align="center" onclick="adminAddUser();return false;">Submit Changes</div> 					
											</td>
											<td width="1" align="left" valign="middle"></td>
											<td width="40" align="left" valign="middle" >
												<div class="button_green" align="right" onclick="document.adminAddUserForm.reset();return false;">Clear</div> 	
											</td>
										</tr>
									</tbody>
							</table>
						</td></tr>
						</table></div></td></tr></table>
						</form:form>
				</td></tr></table>
				</div>
			</div>
	</div>
	
	</div>
	</td></tr>
	<tr><td align="center" valign="bottom" colspan="4" height="150" class="status-message">
		<div id="result1" ></div><div id="result2" ></div><div id="result3" ></div>
	</td></tr> 
	</table>
</body>
</html>
