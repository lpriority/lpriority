<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reading Dashboards</title>
<!-- <script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/adminService1.js"></script> -->
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/common/reading_skills.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<link href="resources/javascript/jplayer/skin/blue.monday/css/jplayer.blue.monday.min.css" rel="stylesheet" type="text/css"/>
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
<script src="resources/javascript/admin/common_dropdown_pull.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#returnMessage').fadeOut(3000);
		$("#loading-div-background").css({
			opacity : 0.8
		});
	});
	function clears() {
		$("#contentDiv").empty();
	}
</script>
</head>
<body>
	<table width="100%">
		<tr>
			<td vAlign=top width="100%" colSpan=3 align=middle>
				<table width="100%" border=0 align="center" cellPadding=0
					cellSpacing=0>
					<tr>
						<td colspan="" width="100%">
							<table width="99.8%" class="title-pad heading-up" border="0">
								<tr>
									<td class="sub-title title-border" height="40" align="left">Reading
										Skills Development Dashboards</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td height=0 vAlign=top colSpan=2 align=left>
							<div class="title-pad" style="padding-top: 1em;">
								<table width="100%" border=0 align="center" cellPadding=0
									cellSpacing=0>
									<tr>
										<td height="30" colspan="2" align="left" valign="middle">
											<table width="70%" border="0" cellpadding="0"
												cellspacing="0" class="label" style="font-size: 12pt">
												<tr>
													<td width="58" align="left" valign="middle">Grade</td>
													<td width="160" align="left" valign="middle"><select
														id="gradeId" name="gradeId" style="width: 120px;"
														class="listmenu"
														onchange="getGradeClasses();clears()">
															<option value="select">select grade</option>
															<c:forEach items="${schoolGrades}" var="ul">
																<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
															</c:forEach>
													</select></td>

													<td width="58" align="left" valign="middle"><label
														class="tabtxt">Class</label></td>
													<td width="160" align="left" valign="middle"><select
														id="classId" name="classId" class="listmenu"
														style="width: 120px;" onchange="getTeachersByClass();clears()">
															<option value="select">select subject</option>
													</select></td>
													<td width="74" align="left" valign="middle">Teacher</td>
													<td width="160" align="left" valign="middle"><select
														name="teacherId" style="width: 120px;" class="listmenu"
														id="teacherId"
														onchange="getSectionsByTeacher();clears()">
															<option value="select">Select Teacher</option>
													</select></td>
													<td width="74" align="left" valign="middle">Section</td>

													<td width="160" align="left" valign="middle"><select
														name="csId" class="listmenu" id="csId"
														style="width: 120px;" onchange="clears();getAdminRSDashboards()">
															<option value="select">Select Section</option>
													</select></td>
												</tr>
											</table>
										</td>
									</tr>
		     				<!-- <tr>
					          <td height="2" colspan="2"><br>
		                          <div id="videoDiv">
				                       <a href="#" onClick="openVideoDialog('admin','admin_reading_skills_dashboard','videoDailog')" class="demoVideoLink"><i class="ion-videocamera" aria-hidden="true" style="font-size:26px;font-weight:bold;color:#e23c00;"></i>&nbsp;video for Access Reading Skills Development Dashboard</a>
		 	                     </div>
			                  </td>
	   						</tr> -->
									<tr>
										<td height="2" colspan="2">&nbsp;<br></td>
									</tr>
									<tr>
										<td colspan="2" align="center" valign="top"
											id="contentDiv"></td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<div id="videoDailog" title="Grade Fluency" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
</body>
</html>