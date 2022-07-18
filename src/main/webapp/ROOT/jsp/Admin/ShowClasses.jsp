<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="resources/css/common_design.css" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<table style="width: 100%">
		<tr>
			<td align="center"><form:form modelAttribute="gradeclass">
					<table class="des" border=0>
						<tr>
							<td>
								<div class="Divheads">
									<table width="100%" border="0" cellspacing="0" cellpadding="0"
										align="center" style="padding-bottom: 0.5em">
										<tr>
											<td width="10%" align="left" valign="middle"><span
												class="headsColor">Select Classes</span></td>
										</tr>
									</table>
								</div>
								<div class="DivContents">
									<table>
										<tr>
											<td align="center" class="space-between"><table
													border="0" cellspacing="0" cellpadding="0" width="441">
													<c:set var="i" value="0"></c:set>
													<c:forEach items="${sList}" var="ul" varStatus="status">
														<c:set var="check" value=""></c:set>
														<c:forEach items="${gclassList}" var="gl">
															<c:if test="${ul.classId == gl.classId}">
																<c:set var="check" value="checked"></c:set>
															</c:if>
														</c:forEach>
														<c:if test="${i==0 }">
															<tr>
														</c:if>
														<c:set var="i" value="${i+1 }"></c:set>

														<td width="25" style="padding-bottom: 0.5em"><input
															name="classId" id="classId${status.index}" type="checkbox" class="checkbox-design"
															value="${ul.classId}" ${check} onclick="removeClass(${ul.classId},this)" /><label for="classId${status.index}" class="checkbox-label-design"></label></td>
														<td style="padding-bottom: 1em;" width="150">${ul.className}</td>
														<c:if test="${i==2 }">
															</tr>
															<c:set var="i" value="0"></c:set>
														</c:if>
													</c:forEach>
												</table></td>
										</tr>
									</table>
									<table style="width: 100%;" border="0" cellspacing="0"
										cellpadding="0" align="center">
										<tr>
											<td height="35" align="center" valign="middle">
												<table style="visibility: hidden; font-size: 15px;"
													id="showSubmit">
													<tr>
														<td>&nbsp;</td>
													</tr>
													<tr>
														<td>
															<div class="button_green" align="right"
																onclick="addClasses()">Submit Changes</div>
														</td>
														<td width="3" align="left" valign="middle">&nbsp;</td>
														<td><a href="gotoDashboard.htm"><div
																	class="button_green" align="right">Cancel</div></a></td>
													</tr>
												</table>
											</td>
											<td>&nbsp;</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</td>
		</tr>
		<tr>
			<td colspan="9" align="center" valign="middle"
				style="padding-bottom: 2em"><label id="returnMessage"
				class="status-message"></label></td>
		</tr>
	</table>
	</form:form>
</body>
</html>