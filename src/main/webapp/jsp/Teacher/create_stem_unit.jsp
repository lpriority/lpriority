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
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
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
<script type="text/javascript" src="resources/javascript/common/img_viewer.js"></script>
<script src="resources/javascript/common/jQuery.print.min.js"></script>

<script src="resources/javascript/LineChart/jszip.min.js"></script>
<script src="resources/javascript/LineChart/pako_deflate.min.js"></script>
<link rel="stylesheet" href="resources/css/LineChart/kendo.material.min.css" />
<link rel="stylesheet" href="resources/css/LineChart/kendo.dataviz.min.css" />
<link rel="stylesheet" href="resources/css/tooltip.css" />

<script src="resources/javascript/LineChart/kendo.all.min.js"></script>	
<script>
  $( function() {
	  getUnitContent();
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
    
    $(".c-tabs-nav__link").click(function(){
  	  var stemUnitId = parseInt($('#stemUnitId').val());
  	  var currentTab = $(this)[0].id;
  	  synchronizeTab(stemUnitId, currentTab, '', 'Yes', '', 'focusin');
    });
 });
</script>
<style type="text/css">
.active-users-div{
	 position: absolute;
	 bottom: 10px;
	 left: 5em;
	 width:auto;
}
.border-text{
    font-size: 10px;
    vertical-align: bottom;
    margin-top: -0.5em;
    padding-left: 4px;
    padding-right: 4px;
    background:#2fe8ff;
}
.blinking-text {
  	opacity: 0;
  	animation: blinking 1s linear infinite;
  	position: relative;
}

@keyframes blinking {
  from,
  49.9% {
    opacity: 0;
  }
  50%,
  to {
    opacity: 1;
  }
}
</style>
</head>
 <body>
  <input type="hidden" id="stemUnitId" name="stemUnitId" value="${stemUnitId}" />
  <input type="hidden" id="mode" name="mode" value="${mode}" />
  <input type="hidden" id="unitStemStrandsId" name="unitStemStrandsId" value="" />
  <input type="hidden" id="mainStrandsId" name="mainStrandsId" value="" />
  <input type="hidden" id="additionalStrandsId" name="additionalStrandsId" value="" />
  <div class="o-container">
    <div class="o-section">
      <div id="tabs" class="c-tabs">
        <div class="c-tabs-nav">
          <a id="tab0" href="#" class="c-tabs-nav__link is-active" onclick="getUnitContent()">
            <i class="fa fa-th"></i>
            <span>${LP_STEM_TAB} Unit</span>
          </a>
          <a id="tab1" href="#" class="c-tabs-nav__link" onclick="essUnitQues()"  
          	title="Essential Questions are discussion causing questions with no specific answer.  
          	Example: How do humans influence society? Why code? Why is that there? 
          	
          	Unit Questions are focused attention on the significant objectives of the unit. 
          	Example: How do the structure of organisms support their survival? Do new Technologies always lead to progress?">
            <i class="fa fa-question-circle"></i>
            <span>Essential/Unit Questions</span>
          </a>
          <a id="tab2" href="#" class="c-tabs-nav__link" onclick="getStemAreas()" 
          	title="Content questions have specific answers.  
           	Example: How can you find the surface area of a shape? How does the transfer of energy drive the motion of matter?">
            <i class="fa fa-th-list"></i>
            <span>Content Questions</span>
          </a>
         <a id="tab3" href="#" class="c-tabs-nav__link" onclick="getUnitStemArea()"  title="What scenarios will you develop to provide rich learning opportunities to help students meet the learning goals? How can you involve students in problem-solving investigations or other meaningful tasks that will help answer the Curriculum-Framing Questions and establish connections to life outside the classroom and address real-world concerns?">
            <i class="fa fa-calendar"></i>
            <span>Activities</span>
          </a>
          <a id="tab4" href="#" class="c-tabs-nav__link" onclick="stemFiveC()">
            <i class="fa fa-cog"></i>
            <span>5C's</span>
          </a>
          <a id="tab5" href="#" class="c-tabs-nav__link" onclick="getUnitStemStrategies()">
            <i class="fa fa-lightbulb-o"></i>
            <span>Differentiation Strategies</span>
          </a>
          <a id="tab6" href="#" class="c-tabs-nav__link" onclick="stemAssessments()">
            <i class="fa fa-list-alt"></i>
            <span>Formative Assessments</span>
          </a>
        </div>
        
      <!-- Start Unit Div -->
        <div class="c-tab is-active" style="padding-top:1em;">
          <div class="c-tab__content">
          	 <div id="getUnitContent"></div>
	      	<%-- <jsp:include page="stem_add_unit.jsp" /> --%>
          </div>
        </div>
      <!-- End Unit Div -->
      <!-- Start Essenitial Question Div  -->
        <div class="c-tab"  style="padding-top:3em;">
          <div class="c-tab__content">
          	<div id="stemEssDiv"></div>
			<%-- <jsp:include page="stem_add_essential_question.jsp" /> --%>
          </div>
        </div>
      <!-- End Essenitial Question Div  -->
      <!-- Start Select STEM Area  -->
        <div class="c-tab">
          <div class="c-tab__content">
          	<jsp:include page="stem_add_content_questions.jsp" />
          </div>
        </div>
      <!-- End Select STEM Area  -->
      <!-- Start Activities Div  -->
        <div class="c-tab">
          <div class="c-tab__content">
         	 <jsp:include page="stem_add_activities.jsp" />
         </div>
        </div>
      <!-- End STEM Activities Area  -->
       <!-- Start FIveC Div  -->  
        <div class="c-tab">
          <div class="c-tab__content">
          	<div id="stemFiveCDiv"></div>
          </div>
        </div>
      <!-- End FiveC Area  -->
        <div class="c-tab">
        	<div class="c-tab__content">
          		<div id="stemStrategiesDiv"></div>
         	</div>
       	</div>      
      <!-- Start Assessments Div  -->
      	<div class="c-tab">
          <div class="c-tab__content">
            <div style="padding-left: 4em;padding-right: 4em;padding-top: 1rem;padding-bottom: 1rem;">
          	<div id="stemAssessmentDiv" style="min-height: 430px;max-height: 430px;overflow: auto;border: 1px solid #8bc0c9;width: 100%;background: #95ccd6;"></div>
          	</div>
          </div>
        </div>
      <!-- End Assessments Area  -->
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
<div id="dialog"  style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title=""><iframe  id ="iframe" src="" frameborder="0" scrolling="no" width="100%" height="98%" src=""></iframe></div>
</body>
</html>
