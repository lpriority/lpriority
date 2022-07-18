<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><c:choose>
	    	<c:when test="${userType == 'admin'}">
	    		Teacher Report Card
	    	</c:when>    									
	    	<c:when test="${userType == 'teacher'}">
	    		Self Evaluation
	    	</c:when>     	
		</c:choose>		</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/TeacherJs/teacher_self_evaluation.js"></script>
<script type="text/javascript"
	src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	 $('#returnMessage').fadeOut(0);
});
</script>
</head>
<body>
<div align="center">
	<form:form name="adminTeacherReportForm" id="adminTeacherReportForm" modelAttribute="TeacherPerformances">
		<table style="padding-left: 8em;width: 99.8%" class="title-pad heading-up">
			<c:choose> 
	    		<c:when test="${userType eq 'admin'}">
	            	<tr>
	                	<td colspan="20" class="sub-title title-border" align="left">
	                    	Teacher Report Card
	                    </td>
	                </tr>
	                <tr>
	                    <td align="left" colspan="2" height=30 width=100% align=left class="label heading-up">
	                            Teacher &nbsp;&nbsp;&nbsp;
	                    	<select id="teacherId" name="teacherId" onchange="loadClasses()" class="listmenu" style="width:140px;">
								<option value="select">select Teacher</option>
								<c:forEach items="${TeacherList}" var="tl">
									<option value="${tl.teacher.userRegistration.regId}">${tl.teacher.userRegistration.title}&nbsp;${tl.teacher.userRegistration.firstName}&nbsp;${tl.teacher.userRegistration.lastName}</option>
								</c:forEach>
							</select>
	                   </td>
	                </tr>
	                </c:when>
	                <c:otherwise>
	                	<tr>
	                    	<td colspan="20"  class="sub-title title-border" height="40" align="left">
				       			Teacher Self Evaluation
				       		</td>
	                    </tr>
	               </c:otherwise>
	    	</c:choose> 
		</table>
	    <br>
	    <table width="100%"><tr><td align="center">
   		<table width="50%" align="center" class="des" border=0><tr>
                        
                        <tr>                            
                            <td> <c:set var="user" value="${userType}" />
                          <div class="Divheads"><table>
                            <tr>
                                <td height="30" align="center" valign="middle">Titles</td>
                                <td height="30" colspan="6" align="center" valign="middle" style="padding-left: 18em;">
                                    Choose One </td>
                                <td style="padding-left: 4em;" align="center">Comments </td>

                            </tr></table></div><div class="DivContents"><table><br>
                            <c:forEach items="${TPList}" var="tp" varStatus="count">
                    <tr>
                        <td  height="30" align="left" valign="middle" class="txtStyle" style="width: 20em;">
                        	${count.count}.&nbsp;<c:out value="${tp.performance}" /></td>
                        <td height="30" colspan="6" align="center" valign="middle" style="padding-left: 2em;">
                            <input type="hidden" name="performances" id="perid${count.count}" value="${tp.id}"></input>
                            <select name="selects" id="select${count.count}">
                                <option value="satisfactory">Satisfactory</option>
                                <option value="unsatisfactory">UnSatisfactory</option>
                            </select> </td>
                        <td style="padding-left: 2em;"><textarea rows="1" id="exp${count.count}" name="expressions"></textarea> </td>

                    </tr></c:forEach></table>
                            <table align="center">
                            <tr>
                                <td height="20" align="left" valign="middle"></td>
                                <td height="20" colspan="10" align="left" valign="middle">
                                	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="">
                                       <tr><td>&nbsp;</td></tr>
                                        <tr>
                                            <td width="80%" align="center" valign="middle">
                                            <div class="button_green"
																	align="right"
																	onclick="return adminCreateTeacherReports(${fn:length(TPList)},'${user}');">Submit
																	Changes</div>
															</td>
                                            <td width="3">&nbsp;&nbsp;&nbsp;</td>
                                            <td width="40" align="left" valign="middle">
                                           		<div class="button_green" align="right" onclick="document.adminTeacherReportForm.reset();return false;">Clear</div> 
                                            </td>
                                        </tr>
                                    </table></td>
                            </tr>
                    </table>
                    </div></td></tr></table>
                    </td></tr>
                     <tr>
                        <td height=45 colSpan="10" align="center" style="">
                    	<label id=returnMessage class="status-message"></label></td>
                      </tr>
                     </table>
                    </form:form>
                    </div>
    </body>
</html>
