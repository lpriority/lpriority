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
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="/dwr/interface/parentService.js"></script>
<script type="text/javascript" src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript" src="/dwr/interface/teacherSchedulerService.js"></script>
<script type="text/javascript" src="/dwr/interface/curriculumService.js"></script>
<script type="text/javascript" src="resources/javascript/ParentJs.js"></script>
<script type="text/javascript" src="resources/javascript/Student/student_test_results.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/simptip/simptip.css" />
<script>
function clearDiv(){
	$('#studentsTestResultsDiv').html("");
	$('#getCompletedTestQuestions').html("");
}
</script>
</head>
<body>
	<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
	<table style="width: 100%;">
		<tr>
			<td style="" align="right">
				<input type="hidden" id="usedFor" name="usedFor" value="${usedFor}" />
				<input type="hidden" id="tab" name="tab" value="${tab}" /></td>
			<td style="" align="right" valign="bottom">
				<div>
					<%@ include file="/jsp/Student/view_rti_tabs.jsp" %>
	      		</div>
			</td>
		</tr>							
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="title-pad">
	<tr>
      <td width="300" class="sub-title title-border" height="40" align="left">Literacy Development Results</td>
    </tr>
     </table>
        <input type="hidden" id="gradeName" name="gradeName" value="" />
        <table align="center" style="width: 100%;" class="title-pad" >
           <tr>
                <td height="30" colspan="2" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0" style="padding-top: 1em">
                       <tr>
                          <td width="112" align="left" valign="middle"><label style="font-size:3;" class="label">Child&nbsp;</label>
		                   <select name="studentId" id="studentId" class="listmenu" onChange="clearDiv();loadClasess()" required="required">
							<option value="">Select Child</option>
							<c:forEach items="${students}" var="st">
								<option value="${st.studentId}">${st.userRegistration.title}
									${st.userRegistration.firstName}
									${st.userRegistration.lastName}</option>
								</c:forEach>
							</select></td>
                    <td width="112" align="left" valign="middle"><label style="font-size:3;" class="label">Class &nbsp;</label>
                        <select name="classId" id="classId" class="listmenu" onchange="clearDiv();getStudentsTestResults()" >
                            <option value="select" selected>SelectSubject</option>
                            
                        </select></td>
                    <td width="400" align="left" valign="middle"><input type="hidden" id="usedFor" value="${usedFor}" /></td>
                </tr>
            	</table></td>
              </tr>
              <tr><td height="10"></td></tr>
              <tr><td> 
                <table width=100% align=left>
				<tr>
				<td align="left" style="padding-left: 7em;"><div id="studentsTestResultsDiv"></div></td></tr></table>
			 </td></tr>
             <tr>
	            <td align="left" style="padding-left: 10em;">
	            <div id="getCompletedTestQuestions" title="Test Results" style="align:center;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div></td>
		    </tr>
       </table>
    </body>
</html>