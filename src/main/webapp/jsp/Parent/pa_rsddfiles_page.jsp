<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Reading Skills Dashboard</title>
<script type="text/javascript" src="resources/javascript/ParentJs.js"></script>
<script type="text/javascript" src="resources/javascript/common/reading_skills.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<link href="resources/javascript/jplayer/skin/blue.monday/css/jplayer.blue.monday.min.css" rel="stylesheet" type="text/css"/>
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
<script type="text/javascript">
	function clearDiv(){
		$("#contentDiv").empty();
	}
	
</script>
</head>
<body>

	 <table width="99.8%" border="0" cellspacing="0" cellpadding="0" class="title-pad" class="heading-up">
                        <tr><td class="sub-title title-border" height="40" align="left">Reading Skills Development Dashboard<br> </td></tr>
                        </table>
	<div>
	<label class="">&nbsp;</label><br />
	</div>
	<div style="padding-left: 3.5cm;">
				
		<div style="padding-left: 0px; padding-top: 5px">
			<label class="label">Child&nbsp;&nbsp;</label>
				<select name="studentRegId" id="studentRegId" class="listmenu" onchange="clearDiv();getParentRSDashboards()">
					<option value="select">Select Child</option>
					<c:forEach items="${children}" var="child">
						<option value="${child.userRegistration.regId}">${child.userRegistration.firstName}${child.userRegistration.lastName}</option>
					</c:forEach>
				</select>
		</div>
	<!-- 	<div id="videoDiv">
		   <a href="#" onClick="openVideoDialog('parent','parent_access_RSDD','videoDailog')" class="demoVideoLink"><i class="ion-videocamera" aria-hidden="true" style="font-size:26px;font-weight:bold;color:#e23c00;"></i>&nbsp;video for Access Reading Skills Development Dashboard</a>
		</div> -->
	<div id="contentDiv">
		</div> 
	</div>
	<div id="videoDailog" title="Grade Fluency" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
</body>

</html>