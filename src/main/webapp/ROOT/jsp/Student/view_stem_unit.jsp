<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script> 
<script type="text/javascript" src="resources/javascript/TeacherJs/stem_curriculum.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/curriculum.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>

<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resources/css/icons/font-awesome/css/font-awesome.min.css">
<link href="resources/css/responsive_tabs/tabs_style.css" rel="stylesheet" type="text/css" />
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" href="resources/css/notify/notify.css">
<link rel="stylesheet" href="resources/css/notify/prettify.css">
<link rel="stylesheet" href="resources/css/confirm_dialog/dialog.css">

<script src="resources/javascript/notify/notify.js"></script>
<script src="resources/javascript/notify/prettify.js"></script>
<script src="resources/javascript/confirm_dialog/dialog.js"></script>
<script type="text/javascript" src="resources/javascript/template/common_template.js"></script>
<style>
.notify-backdrop{
background-color: #e6e6e6;
}
.notify{
	width:auto;
	padding:none;
}
.notify.center{margin-left:-100px;}
.notify > button.close{
   opacity:1;
   color: white;
   text-shadow:0 1px 2px rgb(37, 37, 37);
}
.lnv-mask{
	background:rgba(119, 229, 242, 0.13);
}
.ui-widget-header{background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00e3ff), color-stop(1,#00b7d0) );border:1px solid #00d8f5;text-shadow:0 1px 1px rgb(0, 48, 53);margin:-.12em;}
.ui-widget-overlay{background:rgba(81, 139, 146, 0.66);}
</style>
</head>
 <body>
  <input type="hidden" id="stemUnitId" name="stemUnitId" value="${stemUnit.stemUnitId}" />
  <input type="hidden" id="mode" name="mode" value="${mode}" />
  <input type="hidden" id="unitStemStrandsId" name="unitStemStrandsId" value="" />
  <input type="hidden" id="mainStrandsId" name="mainStrandsId" value="" />
  <input type="hidden" id="additionalStrandsId" name="additionalStrandsId" value="" />
  <div class="o-container">
    <div class="o-section">
      <div id="tabs" class="c-tabs">
        <div class="c-tabs-nav">
          <a id="tab0" href="#" class="c-tabs-nav__link is-active">
            <i class="fa fa-th"></i>
            <span>${LP_STEM_TAB} Unit</span>
          </a>
          <a id="tab1" href="#" class="c-tabs-nav__link">
            <i class="fa fa-question-circle"></i>
            <span>Essential/Unit Questions</span>
          </a>
          <a id="tab2" href="#" class="c-tabs-nav__link">
            <i class="fa fa-th-list"></i>
            <span>Content Questions</span>
          </a>
          <a id="tab3" href="#" class="c-tabs-nav__link">
            <i class="fa fa-th-list"></i>
            <span>Activites</span>
          </a>
        </div>
        
      <!-- Start Unit Div -->
        <div class="c-tab is-active" style="padding-top:3em;">
          <div class="c-tab__content">
	      	<jsp:include page="show_stem_unit.jsp" />
          </div>
        </div>
      <!-- End Unit Div -->
      <!-- Start Essenitial Question Div  -->
        <div class="c-tab"  style="padding-top:3em;">
          <div class="c-tab__content">
          	<jsp:include page="show_stem_essential_question.jsp" />
          </div>
        </div>
      <!-- End Essenitial Question Div  -->
      <!-- Start Select STEM Area  -->
        <div class="c-tab">
          <div class="c-tab__content">
          	<jsp:include page="show_stem_content_questions.jsp" />
          </div>
        </div>
      <!-- End Select STEM Area  -->
       <!-- Start Select STEM Activites  -->
        <div class="c-tab">
          <div class="c-tab__content">
          	<jsp:include page="show_stem_activities.jsp" />
          </div>
        </div>
      <!-- End Select STEM Activites  -->
       </div>
    </div>
    <div class="o-section">
      <div id="github-icons"></div>
    </div>
  </div>
<script type="text/javascript" src="resources/javascript/responsive_tabs/tabs_script.js"></script>
<script>
  var stemTabs = tabs({
    el: '#tabs',
    tabNavigationLinks: '.c-tabs-nav__link',
    tabContentContainers: '.c-tab'
  });
  stemTabs.init();
</script>
<div id="loading-div-background" class="loading-div-background"
	style="display: none;">
	<div class="loader"></div>
	<div id="loading-div" class="ui-corner-all loading-div"
		style="color: #103c51; left: 50%; padding-left: 12px; font-size: 16px;">
		<br><br><br>
		<br>Loading....
	</div>
</div>
<div id="dialog"  style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title=""><iframe  id ="iframe" src="" frameborder="0" scrolling="no" width="100%" height="95%" src=""></iframe></div>
</body>
</html>
