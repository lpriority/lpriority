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
<title>RR Reports</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/Student/reading_register_student.js"></script>
<script type="text/javascript" src="resources/javascript/common/reading_register_report.js"></script>
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="resources/css/tooltip.css" />

<script>
/*
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
*/
</script>
<style type="text/css">
.ui-accordion > .ui-widget-header {
	background: #94B8FF;
}
.ui-accordion > .ui-widget-content {
	background: #e6edee;
}
.ui-accordion > .ui-accordion-header {
	font-size: 100%;
	background:linear-gradient(to bottom, #f7feff 7%, #ffffff 33%, #ffffff 48%, rgb(222, 228, 228) 94%);
}
.ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active {
	border:1px solid #b4c3c5;
}
.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {
	border:1px solid #b5cfd4;
}
.ui-state-hover, .ui-widget-content .ui-state-hover, .ui-widget-header .ui-state-hover, .ui-state-focus, .ui-widget-content .ui-state-focus, .ui-widget-header .ui-state-focus {
	border:1px solid #b5cfd4;
}
*:focus {
	outline:none !important
}
</style>
</head>
<body>
<%@include file="Layout.jsp"%>
<div class="container-fluid">
  <div class="row">
    <div class="col-md-3">
      <%@include file="Menuinfo.jsp"%>
    </div>
    <div class="col-md-9">
      <div class="row">
        <div class="col-md-12 sub-title title-border">
          <div id="title" style="min-height:15px"> Bulk Register Users</div>
          <div style="text-align: right; margin-top:-28px;"> <a href="gotoDashboard.htm" type="button" class="btn btn-sm btn-primary"> Home </a> </div>
        </div>
      </div>
      <div class="col-md-12 text-center" id= "returnMessage" style="color: red"> <font color="blue">
        <label style="visibility:visible;">${status}</label>
        </font> </div>
      <div class="row tblbgcolor">
        <div class="col-md-2"></div>
        <div class="col-md-8"><br>
          <form:form method="get" name="rrReports" id="rrReports" onsubmit="return validateDownload(${userTypeId});">
            <input type="hidden" id="reportsType" name="reportsType" value="" />
            <div class="col-md-12" style="padding-top:15px;padding-bottom:15px;">
              <div class="form-group">
                <div class="col-md-4">District</div>
                <div class="col-md-5">
                  <select id="districtId" name="districtId" required>
                    <option value="select">select district</option>
                    <c:forEach items="${districtLst}" var="dl">
                      <option value="${dl.districtId}">${dl.districtName}</option>
                    </c:forEach>
                  </select>
                </div>
              </div>
            </div>
            <div class="col-md-12">
              <div class="col-md-4"></div>
              <div class="col-md-5">
                <button type="submit" class="btn btn-sm btn-primary" onClick="setURL('excel')">Download RR Reports As Excel</button>
                <br>
                <br>
              </div>
            </div>
            <div class="col-md-12">
              <div class="col-md-4"></div>
              <div class="col-md-5">
                <button type="submit" class="btn btn-sm btn-primary" onClick="setURL('pdf')">Download RR Reports As PDF</button>
                <br>
                <br>
              </div>
            </div>
          </form:form>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <div id="activityDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;">
            <iframe id='activityIframe' width="98%" height="95%" style="border-radius:1em;"></iframe>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <div id="loading-div-background2" class="loading-div-background"
	style="display: none;">
            <div class="loader"></div>
            <div id="loading-div" class="ui-corner-all loading-div"
		style="color: #103c51; left: 50%; padding-left: 12px; font-size: 17px; margin-top: -8.5em;"> <br>
              <br>
              <br>
              <br>
              <br>
              <br>
              <br>
              <br>
              Loading.... </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
