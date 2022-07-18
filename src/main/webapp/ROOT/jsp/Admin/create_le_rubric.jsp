<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Create Rubric</title>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/admin/le_rubric.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
	       		<tr>
                    <td vAlign=bottom align=right>
                       <div>
                       	<%@ include file="/jsp/CommonJsp/le_rubric_tab.jsp" %>
                      </div>
                    </td>
                </tr>
     </table>
<table width="100%" >
<tr><td class="heading-up"  style="padding-top:2em;">	
<!-- Content center start -->		  

		<form:form name="adminCreateRubric" modelAttribute="Criteria" onsubmit="return false;"><br>
		<table class="des" border=0 align="center" style="padding-top:2em;width:30%;"><tr><td><div class="Divheads">
		<table><tr><td class="headsColor">
		<c:choose>
		<c:when test="${tab eq 'createLeRubric'}">
		Create Rubric
		</c:when>
		<c:otherwise>
		Edit Rubric
		</c:otherwise>
		</c:choose>
		</td></tr></table></div>
		<div class="DivContents">
			<table style="width: 90%;" align="center" class="space-between">
			 <tr>
							<td width="50" height="35" align="left" valign="top">&nbsp;</td>
							  		<td width="250" align="left" valign="middle"
									class="tabtxt">Select Criteria</td>
								<td width="100" align="left" valign="middle"
									class="text1">:</td>
								<td width="100" align="left" valign="middle"
									class="tabtxt"><select id="criteriaId" name="criteriaId" class="listmenu" onchange="getSubCriterias()">
									<option value="select">Select</option>
									<c:forEach items="${legendCriteriaLst}" var="lc">
										 <option value="${lc.legendCriteriaId}">${lc.legendCriteriaName}</option>
									</c:forEach>
								</select></td>
							</tr>
							<tr>
							
							<td width="50" height="35" align="left" valign="top">&nbsp;</td>
							  		<td width="250" align="left" valign="middle"
									class="tabtxt">Select SubCriteria</td>
								<td width="100" align="left" valign="middle"
									class="text1">:</td>
								<td width="100" align="left" valign="middle"
									class="tabtxt"><select id="subCriteriaId" name="subCriteriaId" class="listmenu" onChange="clearRubricValues()">
														<option value="select">select SubCriteria</option>
													</select></td>
							</tr>
							<tr>
							
							<td width="50" height="35" align="left" valign="top">&nbsp;</td>
							  		<td width="250" align="left" valign="middle"
									class="tabtxt">Select Rubric Score</td>
								<td width="100" align="left" valign="middle"
									class="text1">:</td>
								<td width="100" align="left" valign="middle"
									class="tabtxt"><select id="rubricScore" name="rubricScore" class="listmenu" onChange="getRubricDesc('${tab}')">
														<option value="select">select RubricScore</option>
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
													</select></td>
							</tr>
	                           <c:choose>
		                  <c:when test="${tab eq 'createLeRubric'}">
		                  <tr>
	                            <td width="50" height="35" align="left" valign="top">&nbsp;</td>
								<td width="250" align="left" valign="middle"
									class="tabtxt">Enter Rubric Description</td>
								<td width="100" align="left" valign="middle"
									class="text1">:</td>
								<td width="100" align="left" valign="middle"
									class="tabtxt"><textarea id="rubricDesc" name="rubricDesc" style="overflow-y : visible;"></textarea>
	                             </td>
	                          </tr>
		                  </c:when>
		                  <c:otherwise>
		                      <tr>
	                            <td width="50" height="35" align="left" valign="top">&nbsp;</td>
								<td width="250" align="left" valign="middle"
									class="tabtxt">Edit Rubric Description</td>
								<td width="100" align="left" valign="middle"
									class="text1">:</td>
								<td width="100" align="left" valign="middle"
									class="tabtxt"><textarea id="rubricDesc" name="rubricDesc" style="overflow-y : visible;"></textarea>
	                             </td>
	                          </tr>
		                  </c:otherwise>
		                   </c:choose>   
	                         
									<tr>
										<td colspan="4" align="center" class="space-between"><table
										align="center">
	                                        <tr>
	                                        	<td align="left" valign="middle">
	                                            	<div class="button_green" align="right" onclick="createRubricForSubCriteria('${tab}')">Submit Changes</div> 
	                                            </td>
	                                            <td  align="left" valign="middle">
	                                           		<div class="button_green" align="right" onclick="document.adminCreateRubric.reset();return false;">Clear</div> 
	                                            </td> 
	                                            <td>
	                                           		<a href="gotoDashboard.htm"><div class="button_green" align="right">Cancel</div></a>
	                                           </td>  
	                                        </tr>
	                                    </table></td>
	                            </tr>
	                            </table></div></td></tr></table>
	                            
	</form:form>
</td></tr>
<tr><td colspan="4" class="space-between" align="center"><label id="result" class="status-message"></label></td></tr>
</table>	
</body>
</html>