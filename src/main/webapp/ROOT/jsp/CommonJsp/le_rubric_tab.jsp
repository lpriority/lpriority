
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page session="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<table align="right" cellspacing="0" cellpadding="0">
	
	<tr>
             <td>
              	<ul class="button-group">
					<%--<li><a href="goToCreateLIRubric.htm" class="${(tab == 'createLeRubric')?'buttonSelect leftPill':'button leftPill'}"> Create Rubric </a></li>
					<%-- <li><a href="goToEditLIRubric.htm" class="${(tab == 'editLeRubric')?'buttonSelect':'button'}"> Edit Rubric </a></li>
					<li><a href="goToAssignLIRubric.htm" class="${(tab =='assignLeRubric')?'buttonSelect':'button'}">Assign Rubric</a></li> --%>
					<li><a href="uploadIOLRubrics.htm" class="${(page =='uploadIOLRubrics')?'buttonSelect leftPill':'button leftPill'}"> Upload IOL Rubric </a></li>
					<li><a href="editIOLRubrics.htm" class="${(page =='editIOLRubrics')?'buttonSelect rightPill longTitle':'button rightPill longTitle'}">Edit IOL Rubric</a></li>
				</ul>
			</td>					
           </tr>
	</table>
	<table  align="left" class="title-pad" style="width: 99.8%;" border="0" cellpadding="0" cellspacing="0" >
		<tr>
			<%--<c:if test="${tab == 'createLeRubric' }">
				<td class="sub-title title-border"  height="40" align="left" valign="middle">
					Create Rubric
				</td>
			</c:if>
			<c:if test="${tab == 'editLeRubric' }">
				<td class="sub-title title-border"  height="40" align="left" valign="middle">
					Edit Rubric
				</td>
			</c:if>
			<c:if test="${tab == 'assignLeRubric' }">
				<td class="sub-title title-border"  height="40" align="left" valign="middle">
					Assign Rubric
				</td>
			</c:if> --%>
			<c:if test="${tab == 'createLeRubric' }">
				<td class="sub-title title-border"  height="40" align="left" valign="middle">
					Create Rubric
				</td>
			</c:if>
			<c:if test="${page =='uploadIOLRubrics'}">
				<td class="sub-title title-border"  height="40" align="left" valign="middle">
					Upload IOL Rubrics
				</td>
			</c:if>
			<c:if test="${page =='editIOLRubrics'}">
				<td class="sub-title title-border"  height="40" align="left" valign="middle">
					Edit IOL Rubrics
				</td>
			</c:if>
		</tr>
	</table>
</body>
</html>