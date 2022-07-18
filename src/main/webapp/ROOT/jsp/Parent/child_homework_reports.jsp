<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Parent Files</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<link rel="stylesheet"
	href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript"
	src="/dwr/interface/parentService.js"></script>
<script type="text/javascript"
	src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript"
	src="/dwr/interface/teacherSchedulerService.js"></script>
<script type="text/javascript"
	src="/dwr/interface/curriculumService.js"></script>
<script type="text/javascript" src="resources/javascript/ParentJs.js"></script>
<script type="text/javascript">
function clearDiv(){
	document.getElementById("checksAssigned").checked = false;
	$("#ChildsReport").empty();
	$("#StuHomeQuestionsList").empty();
}
</script>
</head>
<body>
	<table style="width:100%;">
		<tr>
			<td style="" align="right">
				<input type="hidden" id="usedFor" name="usedFor" value="${usedFor}" />
				<input type="hidden" id="tab" name="tab" value="${tab}" /></td>
			<td style="" align="right" valign="bottom">
				<div>
					<%@ include file="/jsp/Student/view_homework_tabs.jsp" %>
				</div>
			</td>
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="title-pad">
	<tr>
	    <td width="100%" colspan="10" class="sub-title title-border" height="40" align="left">Homework Reports <br></td>
                             <td align="right"></td>
	                         </tr>
	</table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="title-pad" style="padding-top: 0.5em">
				<tr>
					<td width="100%" valign="bottom">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
									<td style="" align="right">&nbsp;</td>
									<td style="" align="right" valign="bottom"></td>
								</tr>
								
							
                       
                                    <tr>
                                        <td style="width:15%;" align="left" valign="middle">
                                        <label style="font-size:3;" class="label">Child&nbsp;&nbsp;</label> <select
										name="studentId" id="studentId" class="listmenu" onChange="clearDiv();loadGradeAndHomeworkDates()" required="required">
											<option value="">Select Child</option>
											<c:forEach items="${students}" var="st">
												<option value="${st.studentId}">${st.userRegistration.title}
													${st.userRegistration.firstName}
													${st.userRegistration.lastName}</option>
											</c:forEach>
									</select></td>
                                        <td style="width:15%;" align="left" valign="middle"><label style="font-size:3;" class="label">Grade&nbsp;&nbsp; </label>
                                        <input name="studentGrade" id="studentGrade" type="text" size="11" readonly/></td>
                                      
                                        <td style="width:15%;" align="left" valign="middle">
                                            <label style="font-size:3;" class="label">Date&nbsp;&nbsp; </label>
                                            <select name="fromDate" id="fromDate" class="listmenu" onchange="clearDiv();">
                                              <option value="0">Select Date</option>
                                            </select></td>
                                        <td width="15%" align="left" valign="middle" class="txtStyle"><input type="checkbox" class="checkbox-design" name="checkbox" id="checksAssigned" onClick="showReport()"/><label for="checksAssigned" class="checkbox-label-design">Show Reports</label></td>
                                        <td width="25%" align="left" valign="middle">&nbsp;</td>
                                        <td width="20%" align="left" valign="middle" class="header">&nbsp;</td>
                                        <td width="30%" align="left" valign="middle"><input type="hidden" id="usedFor" value="${usedFor}" />&nbsp;</td>
                                    </tr>
                                </table>
                        <tr>
                            <td height="2" colspan="2">&nbsp;</td>
                        </tr>
                        <tr>
                            <td style="width:80%;" align="center" valign="top" id="ChildsReport"></td>
                        </tr>
                        <tr>
                            <td height="2" style="width:80%;" align="center">
                             <div id="StuHomeQuestionsList" title="Test Results" style="align:center"></div>
                            </td>
                        </tr>
                        <tr>
                            <td height="30" colspan="2"></td>
                        </tr>
                        <tr>
                            <td height="30" colspan="2" ></td>
                        </tr>
                    </table>
    </body>
  </html>
