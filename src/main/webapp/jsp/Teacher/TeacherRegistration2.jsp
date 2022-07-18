<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Teacher Signup</title>
<link rel="stylesheet" href="resources/css/common_registration.css" >
<script type="text/javascript">
	var count = -1;
	function getClassesPost(i) {
		count++;
		var c = i.value;
		var gradeId;
		var gradsplt = c.split(":");
		gradeId = gradsplt[0];
		var check = (jQuery(i).is(":checked"));
		if (check) {
			$.ajax({
				type : "GET",
				url : "getClasses.htm",
				data : "gradeId=" + gradeId + "&count=" + count,
				success : function(response) {
					$("#" + gradeId).html(response);
				}
			}).done(function() { 
				setFooterHeight();
			});
		} else {
			$("#" + gradeId).empty();
		}
	}

	function submitchanges() {
		var gradeIds = [];
		$("input[name*='gradeIds']").each(function() {
			// Get all checked checboxes in an array
			if (jQuery(this).is(":checked")) {
				gradeIds.push($(this).val());
			}
		});
		var classesLengths = [];
		var classIds = [];
		if (gradeIds.length == 0) {
			alert('Please select a grade');
			return false;
		}
		for (var i = 0; i < gradeIds.length; i++) {
			
			
			var c = gradeIds[i];
			var gradeId;
			var gradsplt = c.split(":");
			gradeId = gradsplt[0];
			
			classIds[i] = new Array();
			$("input[name*='classId" + gradeId + "']").each(function() {
				// Get all checked checboxes in an array
				if (jQuery(this).is(":checked")) {
					classIds[i].push($(this).val());
				}
			});
			classesLengths.push(classIds[i].length);
		}

		for (var i = 0; i < gradeIds.length; i++) {

			if (classIds[i].length == 0) {
				alert('please select a class for grade ' + gradeIds[i]);
				return false;
			}
		}
		$.ajax({
			url : "teacherRegVal2.htm",
			type : "POST",
			data : "classIds=" + encodeURIComponent(classIds) + "&gradeIds=" + encodeURIComponent(gradeIds)
					+ "&classesLengths=" + classesLengths,
			success : function(data) {				
				//alert(data);
				window.location.replace(data);
			}
		});
	}
</script>
</head>
<body>
	<table width="550px" border="0" cellpadding="0" align="center"
		cellspacing="0">
		<c:if test="${userReg.regId <= 0 }">
			<tr>
				<td height="33" colspan="6" align="left" valign="middle"><img
					src="images/Login/logoBlackUp.gif" width="222" height="21"
					name="logoblack" vspace="0" hspace="7" border="0"
					alt="The Learning Priority" /></td>
			</tr>
		</c:if>
		<tr>
			<td colspan="6" align="left" valign="top"><table
					width="100%" border="0" cellpadding="0" cellspacing="0" style="max-width:550px;font-size: 21px;" class="form">
					<tr>
						<td align="center" valign="top">
								<table name="content" border="0" cellpadding="0" cellspacing="0"
									vspace="0" width="622" hspace="0">
									<tbody>
										<tr>
											<td align="right" valign="middle" width="175">&nbsp;</td>
											<td align="center" valign="middle" width="8">&nbsp;</td>
											<td colspan="3" align="left" valign="middle" width="439"></td>
										</tr>
										<tr>
											<td align="right" valign="middle" width="175">&nbsp;</td>
											<td align="center" valign="middle" width="8">&nbsp;</td>
											<td colspan="3" align="left" valign="middle" width="439">&nbsp;</td>
										</tr>
										<tr>
											<td align="right" valign="middle" width="175">&nbsp;</td>
											<td align="center" valign="middle" width="8">&nbsp;</td>
											<td class="" align="left" valign="middle" style="font-size:13px;color:#006471;"
												width="325">You can select more multiple grade levels
												and subjects. Your profile will be customized accordingly.</td>
											<td align="left" valign="middle" width="114">&nbsp;</td>
										</tr>
										<tr>
											<td align="right" valign="middle" width="175">&nbsp;</td>
											<td align="center" valign="middle" width="8">&nbsp;</td>
											<td colspan="3" align="left" valign="middle" width="439">&nbsp;</td>
										</tr>
										<tr>
											<td align="left" valign="top" width="175" class="text" style="font-size: 13px;">
												* Grade Level:</td>
											<td align="center" valign="middle" width="8">&nbsp;</td>
											<td colspan="3" align="left" valign="middle" width="439">
												<input type="hidden" name="count" id="count" />
												<table name="scholar_interest" border="0" cellpadding="0"
													cellspacing="0" vspace="0" width="439" hspace="0">
													<tbody>
														<c:set var="count" value="0" > </c:set>
														<c:forEach items="${grList}" var="gList">
															<c:if test="${count==0})">
															<tr>
															</c:if>
																<c:set var="count" value="${count+1}"></c:set>
																<td width="25" class="" style="color:black;font-size:14px;"><input type="checkbox"
																	name="gradeIds"
																	value="${gList.gradeId}:${gList.masterGrades.gradeName}"
																	onclick="getClassesPost(this)" />${gList.masterGrades.gradeName}</td>
																<c:if test="${count==3 }">
															</tr>
															<c:set var="count" value="0" > </c:set>
															</c:if>
														</c:forEach>
														<tr>
															<td colspan="3">&nbsp;</td>
														</tr>

													</tbody>
													<tbody>
														<c:forEach items="${grList}" var="gList">
															<tr>
																<td colspan="3" style="word-wrap: break-word;"
																	id="${gList.gradeId}"></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</td>
										</tr>
																		
										
										<tr>
											<td colspan="3" align="right" valign="middle" width="439">
												<table width="100%" height="115" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td width="100%" align="right" valign="middle">
															<button class="for-back-ward-button-style button-block-inline" onclick="submitchanges()" >Next</button>	
														</td>
													</tr>
												</table>
											</td>
										</tr>	
									</tbody>
								</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>