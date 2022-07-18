<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="resources/css/common_design.css" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<input type="hidden" name="isValid" id="isValid" value="${fn:length(subCriteriaRubricValues)}"/>
	<div align="center">
		<table>
			<tr>
				<td width="700">
					<table class="des" border=0 align="left">
						<tr>
							<td><div class="Divheads">
									<table>

										<tr>
											<!-- <th align="left" width="120px">Select</th> -->
											<th align="left" width="120px">Rubric Score</th>
											<th align="center" width="400px">Rubric Description</th>
										</tr>
									</table>
								</div>
								<div class="DivContents">
									<table>
										<tr>
											<td height="25"></td>
										</tr>
										<c:forEach items="${subCriteriaRubricValues}" var="legList">
											<tr>
												<td align="left" width="120px"><input type="hidden"
													id="chkbox" name="chkbox" value="${legList.legendId}" /></td>
												<td align="center" width="120px"><c:out
														value="${legList.legendValue}"></c:out></td>
												<td align="left" width="400px"><c:out
														value="${legList.legendName}"></c:out></td>

											</tr>
											<tr>
												<td>&nbsp;</td>
											</tr>
										</c:forEach>
									</table>
									<table align="center">
										<tr>
											<td colspan="2">Assign To :&nbsp; <select name="gradeId"
												style="width: 120px;" class="listmenu" id="gradeId">
													<option value="select">select grade</option>
													<c:forEach items="${schoolgrades}" var="ul">
														<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
													</c:forEach>
											</select></td>
										</tr>
										<tr>
											<td colspan="2">&nbsp;</td>
										</tr>
										<tr>
											<td colspan="2"><div class="button_green" align="right"
													onClick="assignRubricValues()">Submit Changes</div></td>
										</tr>
									</table>
								</div></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>