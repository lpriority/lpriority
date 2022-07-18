<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Set Class Size</title>
<script type="text/javascript"
	src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript"
	src="resources/javascript/admin/add_edit_remove_sl_schl.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<table width="100%">
		<tr>
			<td>
				<table width="100%" class="title-pad">
					<tr>
						<td class="sub-title title-border" height="40" align="left"
							valign="middle">Set Class Size</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="">
				<!-- Content center start --> <form:form
					name="adminSetClassStrength" modelAttribute="school">
					<c:set var="Checked1" value=""></c:set>
					<c:set var="Checked2" value="checked"></c:set>
					<c:set var="Checked3" value=""></c:set>
					<c:set var="Checked4" value="checked"></c:set>

					<br>
					<br>
					<table class="des" border=0 align="center"
						style="padding-top: 0.5em">
						<tr>
							<td width="500">
								<div class="Divheads">
									<table>
										<tr>
											<td class="headsColor">Class Strength</td>
										</tr>
									</table>
								</div>
								<div class="Divcontents" style="padding: 5px 10px 10px 10px;">
									<table width="100%" align="center">
										<tr>
											<td>&nbsp;</td>
										</tr>
										<c:forEach items="${classStrengths}" var="clsStrength">
											<tr>
												<td width="40%" height="30" align="right" valign="middle"
													class="tabtxt">Set Class Size</td>
												<td width="15%" height="30" align="center" valign="middle"
													class="text1">:</td>
												<td height="30" align="left" valign="middle" class="tabtxt">
													<input type="number" min="0" name="classStrength"
													id="classStrength" value="${clsStrength.classStrength}" />
												</td>
											</tr>
											<tr>

												<td height="30" align="right" valign="middle" class="tabtxt">Leveling</td>
												<td height="30" align="center" valign="middle" class="text1">:</td>
												<td height="30" align="left" valign="middle">
													<c:if test="${clsStrength.leveling=='yes'}">
														<c:set var="Checked1" value="checked"></c:set>
														<c:set var="Checked2" value=""></c:set>
													</c:if> <input type="radio" class="radio-design" id="yes" name="Leveling" value="yes"
													${Checked1} />&nbsp;<label for="yes" class="radio-label-design"><span class="tabtxt">Yes</span></label>&nbsp;&nbsp;<input type="radio"  class="radio-design" id="no"
													name="Leveling" value="no" ${Checked2} />&nbsp;<label for="no" class="radio-label-design"><span class="tabtxt">No</span></label>

												</td>
											</tr>
											<tr>
												<td height="40" align="right" valign="middle" class="tabtxt">Gender
													Equity</td>
												<td height="30" align="center" valign="middle" class="text1">:</td>
												<td height="30" align="left" valign="middle" class="">
													<c:if test="${clsStrength.genderEquity=='yes'}">
														<c:set var="Checked3" value="checked"></c:set>
														<c:set var="Checked4" value=""></c:set>
													</c:if> <input type="radio" class="radio-design" id="yes1" name="genderEquity" value="yes"
													${Checked3} />&nbsp;<label for="yes1" class="radio-label-design"><span class="tabtxt">Yes</span></label>&nbsp;&nbsp; <input type="radio" class="radio-design" id="no1"
													name="genderEquity" value="no" ${Checked4} />&nbsp;<label for="no1" class="radio-label-design"><span class="tabtxt">No</span></label>

												</td>
											</tr>
										</c:forEach>
									</table>
									<table align="center" width="100%">
										<tr>
											<td height="35" colspan="4" align="center" valign="middle"><table
													class="" style="width: 85%" align="center">
													<tr>
														<td align="left" valign="middle">
															<div class="button_green" align="right"
																onclick="setClassStrengthDetails()">Submit Changes</div>
														</td>
														<td align="left" valign="middle">&nbsp;</td>
														<td align="left">
															<div class="button_green" align="right"
																onclick="document.adminSetClassStrength.reset();return false;">Clear</div>
														</td>
														<td align="left" valign="middle">&nbsp;</td>
														<td><a href="gotoDashboard.htm"><div
																	class="button_green" align="right">Cancel</div></a></td>
												</table></td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
					</table>

				</form:form>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center" class="space-between"><label
				id="result1" class="status-message"></label></td>
		</tr>
	</table>
</body>
</html>