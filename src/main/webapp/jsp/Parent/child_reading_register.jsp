<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Scored Activity</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/Student/reading_register_student.js"></script>
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="resources/css/tooltip.css" />
<script>
$(function() {
    $( "#rows" ).accordion({
      collapsible: true,
      active: false,
      activate:function(event, ui ){
  		setFooterHeight();
    }
    });    
});
$( function() {
  $( document ).tooltip({
    position: {
      my: "center bottom-20",
      at: "center top",
      using: function( position, feedback ) {
        $( this ).css( position );
        $( "<div>" )
          .addClass( "arrow" )
          .addClass( feedback.vertical )
          .addClass( feedback.horizontal )
          .appendTo( this );
      }
    }
  });
});

</script>
<style type="text/css">
.ui-accordion > .ui-widget-header {background: #94B8FF;}
.ui-accordion > .ui-widget-content {background: #e6edee;}
.ui-accordion > .ui-accordion-header {font-size: 100%;background:linear-gradient(to bottom,#f7feff 7%, #ffffff 33%, #ffffff 48%, rgb(222, 228, 228) 94%);}
.ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active{border:1px solid #b4c3c5;}
.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {border:1px solid #b5cfd4;}
.ui-state-hover, .ui-widget-content .ui-state-hover, .ui-widget-header .ui-state-hover, .ui-state-focus, .ui-widget-content .ui-state-focus, .ui-widget-header .ui-state-focus{border:1px solid #b5cfd4;}
*:focus {outline:none !important}
</style>

</head>
<body>
<table width="100%">
	<tr>
		<td colspan="" width="100%"> 
			 <table width="100%" class="title-pad" border="0">
				<tr>
					<td class="sub-title title-border" height="40" align="left" ><div id="title">Child Reading Register</div></td>
				</tr>
			</table>
		</td>
	</tr>
	 <tr>
		<td style="padding-left: 7em" align="left" width="100%">
		 <label style="padding-right: 1em" class="label">Child </label>
		           <select id="studentId" onchange="parentSortActivities()">
		              <option value="select"  selected>Select Child</option> 
						<c:forEach items="${childs}" var="child">
						<option value="${child.studentId}">${child.userRegistration.firstName} ${child.userRegistration.lastName}</option>
					</c:forEach>
			</select>
			&nbsp;&nbsp;&nbsp;
			<label style="padding-right: 1em" class="label">Sort By </label>
				<select id="sortBy" onchange="parentSortActivities()">
				<option value="createDate" selected="selected">Date</option>
				<option value="readRegActivity.activityId">Activity</option>
				<option value="pointsEarned">Points earned</option>
			</select>
			
		</td>
	</tr> 
	 <tr>
		<td style="padding-left: 7em; padding-top: 1em" id="subContentDiv" align="center">
			
		</td>
	</tr>	
</table>
<div id="reviewDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"><iframe id='reviewIframe' width="98%" height="95%" style="border-radius:1em;"></iframe></div>
<div id="retellDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"><iframe id='retellIframe' width="98%" height="95%" style="border-radius:1em;"></iframe></div>
<div id="pictureDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"><iframe id='pictureIframe' width="98%" height="95%" style="border-radius:1em;"></iframe></div>
<div id="createQuizDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"><iframe id='createQuizIframe' width="98%" height="95%" style="border-radius:1em;"></iframe></div>
</body>
</html>
