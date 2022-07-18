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
<script type="text/javascript"
	src="resources/javascript/admin/le_rubric.js"></script>
<style>
.col {
	border: 1px solid black;
	border-collapse: collapse;
	padding: 2px;
	text-align: center;
}

.pad {
	padding-left: 10px;
	padding-right: 10px;
}
</style>
</head>
<body>
	<div align="center">
		<c:choose>
			<c:when test="${fn:length(leRubrics) eq 0  }">
				No rubrics available
			</c:when>
			<c:otherwise>
				<table>
					<tr>
						<td width="700">
							<table class="des" border=0 align="left" class="col">
								<tr>
									<td>
										<div class="DivContents" class="col">
											<table>
											<c:forEach items="${leRubrics}" var="leR">
												<tr>
												<c:choose>
												<c:when test="${editStatus eq 'enabled'}">
													<td align="center" width="120px" class="pad" style="padding-bottom: 10px;color: white;background-color: #02BED6;" 
														onmouseover="this.style.background='#1179A3'" onmouseout="this.style.background='#02BED6'" 
														onClick="setLegendColor(${indx},${leR.legendValue},'${type}');saveRubricScore(${indx},${leR.legendValue},'${type}')"><font
														color="blue">
															<a href="#" onClick="setLegendColor(${indx},${leR.legendValue},'${type}');saveRubricScore(${indx},${leR.legendValue},'${type}')">${leR.legendValue}</a>
														 
														 </font>
													</td>	
													</c:when>
													<c:otherwise>
													<td align="center" width="120px" class="pad" style="padding-bottom: 10px;color: white;background-color: #02BED6;" 
														onmouseover="this.style.background='#1179A3'" onmouseout="this.style.background='#02BED6'"><font color="blue">
															${leR.legendValue}
													 </font>
													</td>	
													</c:otherwise>
													</c:choose>
																									
													<td align="left" valign="top" width="500px" class="pad" style="padding-bottom: 10px;color: white;background-color: #02BED6;" 
														onmouseover="this.style.background='#1179A3'" onmouseout="this.style.background='#02BED6'"><c:out value="${leR.legendName}" /></td>													
												</tr>
												</c:forEach>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>