<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Fluency Reading Results</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script src="resources/javascript/teacher_popup.js"></script>
<script src="resources/javascript/TeacherJs/benchmark_results.js"></script>
<script type="text/javascript" src="resources/javascript/gs_sortable.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(document).ready(function () {
 	$('#returnMessage').fadeOut(4000);
});

 </script>  
 <style type="text/css">
        table.demo
        {
            font-size: 18px;
            border: 1px solid black;
            height:100%;
        }

        table.demo th
        {
            font-weight: bold;
            background-color: #C7DC00;
            padding: 2px 5px 2px 5px;
            font-size: 16px;
        }

        table.demo td
        {
            font-size: 16px;
            padding: 2px 5px 2px 5px;
            text-align: center;
            height:1.5em;
            background: white;
            /*	border-top: 1px dotted #C0C0FF;*/
        }

        tr.row1
        {
            background-color: #FFFFFF;
        }

        tr.row2
        {
            background-color: #E0E0FF;
        }

        tr.hl
        {
            color: #808080;
            font-weight: bold;
        }
  </style>
      <script type="text/javascript">
         function clearEval(){
        	  document.getElementById("checkAssigneds").checked = false;
        	  $("#benchmarkResultsTable").html("");
          }           
      </script>
    </head>
    <body >
	<table border=0 cellSpacing=0 cellPadding=0 width="100%" align=right height=69>
            <tr>				
				<td align=right>
					<div>
						<%@ include file="/jsp/assessments/rti_tabs.jsp"%>
					</div>
				</td>
			</tr>   
	</table>
    <form:form name="rtiResults" modelAttribute="assignment">
        <table border=0 cellSpacing=0 cellPadding=0 width="80%" align=center height=69>           
			<tr>
				<td style="color: white; font-weight: bold"><input
					type="hidden" name="tab" id="tab" value="${tab}"></td>
				<td height="5">&nbsp;</td>
			</tr>      
            <tr>
                <td  vAlign=top width="100%" colSpan=3 align=middle>
                    <table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0>                        
                        <tr><td vAlign=bottom align=right></td></tr>
                        <tr>
                            <td height="30" colspan="2" align="left" valign="middle"><table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td width="56"><label class="label">Grade</label></td>
                                        <td width="150" >
                                           <select name="gradeId"
												class="listmenu" id="gradeId"
												onChange="getGradeClasses();clearEval();" required="required">
													<option value="select">select grade</option>
													<c:forEach items="${teacherGrades}" var="ul">
														<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
													</c:forEach>
											</select>
                                        </td>
                                        <td width="58" align="left" valign="middle"><label class="label">Class</label></td>
                                        <td width="150" align="left" valign="middle">
                                            <select
											id="classId" name="classId" class="listmenu"
											onChange="getClassSections();clearEval();" required="required">
												<option value="select">select subject</option>
										</select></td>
                                        <td width="58" align='left'><label class="label">Section&nbsp;</label> </td>
                                        <td width="150" align="left" valign="middle" class="text1"><form:select
												id="csId" path="classStatus.csId" class="listmenu"
												onChange="getBenchmarkDates();clearEval()" required="required">
												<option value="select">select Section</option>
											</form:select></td>
                                        <td width="56" align="left" valign="middle"><label class="label">Date</label> </td>
                                        <td width="150" align="left" valign="middle" class="header">
                                            <select name="assignedDate" class="listmenu" id="assignedDate" onchange="getFluencyTitles();clearEval();">
                                                <option value="select">Select Date</option>
                                            </select></td>
                                             <td width="50" align="center" class="label">Title&nbsp;</td>
                                             <td width="150" align="left" valign="middle" class="header" onchange="clearEval();">
                                            <select name="titleId" class="listmenu" id="titleId">
                                                <option value="select">Select Title</option>
                                            </select></td>  
                                        <td  align="left" width="150"  valign="middle" >
                                            <input type="checkbox" class="checkbox-design" name="checkAssigneds" id="checkAssigneds"  onClick="evaluateBenchmark();clearEval();"/>
                                            <label for="checkAssigneds" class="checkbox-label-design label">Get Results</label></td>
                                        <td width="100" align="left" valign="middle">
                                            <input type="hidden" name="usedFor" id="usedFor" value="${usedFor}"></input></td>
                                    </tr>
                                </table></td>
                        </tr>
                        <tr>
                            <td height="2" colspan="2"> </td>
                        </tr>                        
                        <tr>
                            <td colspan="2" align="center" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td colspan="2" align="center" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr><td>&nbsp;</td></tr>
                                                <tr>
                                                    <td> 
                                                        <div id="printablediv">
                                                            <table id="benchmarkResultsTable"  width="100%" border="0" cellpadding="0" cellspacing="0" border="0">
                                                            </table>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
                                                </tr>
                                            </table>

                                        </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                </table></td>
                        </tr>
                        <tr>
                            <td height="2" colspan="2"></td>
                        </tr>
                        <tr>
                            <td height="30" colspan="2"></td>
                        </tr>
                        <tr>
                            <td height="30" colspan="2"><% String se = "yes";
                                        session.setAttribute("setgroupEvaluateHomework", se);%></td>
                        </tr>                    </table></td>
            </tr>
            <tr><td width="100%" colspan="10" align="center"><label id="returnMessage" style="color: blue;">${hellowAjax}</label></td></tr>
        </table>
       </form:form>
    </body>  
</html>