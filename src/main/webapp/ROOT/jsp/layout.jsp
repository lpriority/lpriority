<!DOCTYPE HTML>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="true"%>
<html>
<head>
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
<link rel="icon" href="images/favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="resources/javascript/bootstrap-3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/javascript/bootstrap-3.3.7/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="resources/css/icons/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/admin_header.css" type="text/css" />
<link rel="stylesheet" href="resources/css/template/multi_level_push_menu.css">
<link rel="stylesheet" href="resources/css/template/common_template.css">
<link rel="stylesheet" href="resources/css/common/tsc_ribbons.css">
<link rel="stylesheet" href="resources/css/icons/foundation-icons/foundation-icons.css">
<link rel="stylesheet" href="resources/css/icons/ionicons/css/ionicons.min.css">
<link rel="stylesheet" href="resources/css/icons/PICOL-font/css/picol.css">
<link rel="stylesheet" href="resources/css/icons/PICOL-font/css/animation.css">
<link rel="stylesheet" href="resources/css/notify/notify.css">
<link rel="stylesheet" href="resources/css/notify/prettify.css">
<link rel="stylesheet" href="resources/css/confirm_dialog/dialog.css">
<link rel="stylesheet" href="resources/css/template/expandcollapse.css">
<link rel="stylesheet" href="resources/css/tooltip.css" />
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/common/jQuery.print.js"></script>
<script src="resources/javascript/bootstrap-3.3.7/js/bootstrap.min.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script> 
<script type="text/javascript" src="resources/javascript/dashboard/moment-2.2.1.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/common/dojo_curriculum.js"></script>
<script type="text/javascript" src="resources/javascript/template/modernizr.min.js"></script>
<script type="text/javascript" src="/resources/javascript/imageloadfunctions.js"></script>
<script src="/resources/javascript/notify/notify.js"></script>
<c:if test="${userReg.regId>0}">  
	<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/common_special_effects.css" rel="stylesheet" type="text/css" />
</c:if>
<link href="resources/css/common_footer.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resources/css/custom.css" />
<style type="text/css">
.fa-spin {animation: fa-spin 1s linear infinite;}
.ui-widget-overlay{background:rgba(7, 77, 86, 0.13);}
.ui-widget {font-family:'Big Caslon', 'Book Antiqua', 'Palatino Linotype', Georgia, serif;font-size: .8em;}
.notify.center{margin-left:-100px}
.notify > button.close{opacity:1;color: white;text-shadow:0 1px 2px rgb(37, 37, 37);}
.notify.bottom{bottom: 60px;}
.bg{pointer-events: auto; background : #f7f5f5 url('images/Common/bg.png') repeat fixed top center; pointer-events: auto;}
.logoStyle1{
	color: black;
	font-size: 23px;
	letter-spacing:0.1px;
	font-weight: 600; 
	font-family: 'Ubuntu','Liberation Sans','Open Sans','sans-serif';
	text-shadow: -2px 2px 2px rgba(175, 175, 175, 0.79);
}
.myTitleClass .ui-dialog-titlebar {
	background: -webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00e3ff), color-stop(1,#0ea8bd) );
}
.logoStyle2{
	color:#000000;
	font-size:16px;
	font-weight:bold;
	text-shadow:0 1px 1px #a2a2a2;
}
.footer{
	font-family: Candara, Calibri, Segoe, Segoe UI, Optima, Arial, sans-serif;
	font-size: 12px;
	font-weight: bold;
}

input:-webkit-autofill {
    -webkit-box-shadow: 0 0 0px 1000px white inset;
}

.iconBorder{
    border-radius: 100%;
    transition-duration: .9s;
    padding: 1px 2px 0 1px;
    color: white;
    background-color: #0d4d6d;
    width: 32px;
    height:28px;
    text-align: center;
}

.menu-toggle{
   position: relative;
    top: 0;
    left: 0;
    z-index: 1;
    width: 100px;
    height: 40px;
    margin: 0em -.2em;
    background: -webkit-gradient(linear, left top, left bottom, from(#16e5ff), to(#06c7de));
    /* border-radius: 0 0.12em 0.12em 0; */
    cursor: pointer;
    -webkit-transition: box-shadow 0.4s ease;
    transition: box-shadow 0.4s ease;
    -webkit-backface-visibility: hidden;
    backface-visibility: hidden;
    display: -webkit-box;
    display: -ms-flexbox;
    /* display: flex; */
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    font-size: 13.5px;
    font-weight: bold;
    font-family: cursive;
    color: #000000;
    letter-spacing:1.5px;
    text-shadow: 0 1px 1px rgb(169, 169, 169);
    box-shadow:-8px 12px 30px -10px rgb(13, 122, 132);
    border-top-right-radius:.5em;
    border-bottom-right-radius:.5em;
    
}
.menu-wrapper {
  width: 30%;
  height: 65%;
  position: relative;
  cursor: pointer;
}
.menu-wrapper.on .one {
  -moz-transform: rotate(45deg) translate(8px, 3px);
  -ms-transform: rotate(45deg) translate(8px, 3px);
  -webkit-transform: rotate(45deg) translate(8px, 3px);
  transform: rotate(45deg) translate(8px, 3px);
}
.menu-wrapper.on .two {
  opacity: 0;
}
.menu-wrapper.on .three {
  -moz-transform: rotate(-45deg) translate(2px, -8px);
  -ms-transform: rotate(-45deg) translate(2px, -8px);
  -webkit-transform: rotate(-45deg) translate(2px, -8px);
  transform: rotate(-45deg) translate(2px, -8px);
  background:#00151f;
}

.one,
.two,
.three {
  width: 90%;
  height: 2px;
  background: #867800;
  margin: 5px auto;
  backface-visibility: hidden;
  -moz-transition-duration: 0.3s;
  -o-transition-duration: 0.3s;
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
}
.one{
margin-left:-.2em;
}
.two{
background: rgba(0, 0, 0, 0.74);
}
.three{
margin-right:-.2em;
}

/* Step 1: Build the Animation */
@-webkit-keyframes aniload {
  from {opacity:0.4}
  to   {opacity:1}
}

@-moz-keyframes aniload {
  from {opacity:0.4}
  to   {opacity:1}
}

@-ms-keyframes aniload {
  from {opacity:0.4}
  to   {opacity:1}
}

@-o-keyframes aniload {
  from {opacity:0.4}
  to   {opacity:1}
}

@keyframes aniload {
  from {opacity:0.4}
  to   {opacity:1}
}

/* Step 2: Call the Animation */
#mainBody {
  -webkit-animation:aniload .6s;
  -moz-animation:aniload .6s;
  -ms-animation:aniload .6s;
  -o-animation:aniload .6s;
  animation:aniload .6s;
}
</style>
<script type="text/javascript">
var record = [];
var selectOption = function (target, divId) {
	if(target.selectedIndex != 0){
		if(record[target.selectedIndex])
			$('#select_all').prop('checked', false);	
	    record[target.selectedIndex] = !record[target.selectedIndex];
	}    
	rebuildOptions(target.id);
    if(record.length > 0)
       $("#"+divId).show();
}
var rebuildOptions = function (id) {
    var itemsList = document.getElementById(id);
    for (var i = 0; i < record.length; i++) {
        itemsList.options[i].selected = record[i];
    }
}
var selectAllOptions = function (target, id, divId) {
    var itemsList = document.getElementById(id);
    for (var i = 1; i < itemsList.options.length; i++) {
        itemsList.options[i].selected = target.checked ? 1 : 0;
        record[i] = itemsList.options[i].selected;
    }    
    if(itemsList.options.length == 1 ){
    	systemMessage("Please select an option" );
    	return false;
    }
    else if(itemsList.options.length > 1 && target.checked){
    	$("#"+divId).show();
        $("#"+divId).css('visibility', 'visible');
    }
    else{
        $("#"+divId).hide();
    }
}
</script>
</head>
<body id="mainBody" class="claro bg">
    <c:if test="${userReg != null}">
		<c:choose>
			<c:when test="${userReg.user.userType == 'admin'}">
				<script type="text/javascript" src="resources/javascript/menu/admin_menus.js"></script>
			</c:when> 
			<c:when test="${userReg.user.userType == 'teacher'}">
				<script type="text/javascript" src="resources/javascript/menu/teacher_menus.js"></script> 
			</c:when> 
			<c:when test="${userReg.user.userType == 'student above 13' || userReg.user.userType == 'student below 13'}">
				<script type="text/javascript" src="resources/javascript/menu/student_menus.js"></script> 
			</c:when> 
			<c:when test="${userReg.user.userType == 'parent'}">
				<script type="text/javascript" src="resources/javascript/menu/parent_menus.js"></script> 
			</c:when>
		</c:choose>
     </c:if>
      <div id="pushobj">
         <c:if test="${userReg != null}">
          <c:if test="${userReg.status eq 'active'}">
         	<c:if test="${userReg.user.userType != 'appmanager'}">
				<div id="headerWrap">
					<tiles:insertAttribute name="header" />
					<table width="100%" style="">
					  <tr>
						 <td>
							  <div class="menu-toggle">
								   <div class="menu-wrapper">
	 							        <div class="one"></div>
									    <div class="two"></div>
									    <div class="three"></div>
								   </div> 
								   &nbsp;Menu
							  </div>
						  </td>
						  <td width="20%" align="right" id="nav" >
								<c:choose>
									<c:when test="${page eq 'adminDesktop'}">
									  	<div id='content' style="margin-top: -12.5%;">
									 	 	<div class="ribbon3"><span>Admin Panel</span></div>
										</div>
									 </c:when>  
									<c:when test="${page eq 'teacherDesktop'}">
										<div id='content' style="margin-top: -12.5%;">
									 	 	<div class="ribbon3"><span>Teacher Panel</span></div>
										</div>
									</c:when> 
									<c:when test="${page eq 'studentDesktop'}">
									    <div id='content' style="margin-top: -12.5%;">
									 	 	<div class="ribbon3"><span>Student Panel</span></div>
										</div>
									</c:when> 
									<c:when test="${page eq 'parentDesktop'}">
									    <div id='content' style="margin-top: -12.5%;">
									 	 	<div class="ribbon3"><span>Parent Panel</span></div>
										</div>
									</c:when>      									
									<c:otherwise>
									<div style="margin-top: -1em;margin-right: 1em;">
											<a href="#" onclick="loadPage('gotoDashboard.htm')" class="go_to_desktop"><span>Go to Desktop</span></a>
										</div>
										<c:choose>
											<c:when test="${userReg.user.userType == 'admin'}">
												<div id='content' style="margin-top: -14.1%;">
											 	 	<div class="ribbon3"><span>Admin Panel</span></div>
												</div>
											</c:when> 
											<c:when test="${userReg.user.userType == 'teacher'}">
												<div id='content' style="margin-top: -14.1%">
											 	 	<div class="ribbon3"><span>Teacher Panel</span></div>
												</div> 
											</c:when> 
											<c:when test="${userReg.user.userType == 'student above 13' || userReg.user.userType == 'student below 13'}">
												<div id='content' style="margin-top: -14.1%">
											 	 	<div class="ribbon3"><span>Student Panel</span></div>
												</div>
											</c:when> 
											<c:when test="${userReg.user.userType == 'parent'}">
												<div id='content' style="margin-top: -14.1%">
											 	 	<div class="ribbon3"><span>Parent Panel</span></div>
												</div>
											</c:when>
										</c:choose>
									</c:otherwise>	    	
								</c:choose>	
							</td>
						</tr>
					</table> 
				</div>	 
			</c:if>
		</c:if>
	 </c:if>	
      <div id="bodyWrap" class="">
        <div id="body" class="bodyCls">
   		 <tiles:insertAttribute name="body" />
   		 </div>   		 
   	  </div>
    </div>
	  <c:if test="${userReg != null }">
		  <div id="menuWrap" >
		  	<div id="menu"></div>
		  </div> 
	  </c:if>

<div id="footer" class="footer"><tiles:insertAttribute name="footer" /> </div>
<c:choose>	  
	<c:when test="${userReg.user.userType ne 'appmanager'}">	  
		<script src="resources/javascript/template/jquery.multilevelpushmenu.min.js"></script>
		<script type="text/javascript" src="resources/javascript/template/expandcollapse.js"></script>
	</c:when>
</c:choose>
  
<script src="resources/javascript/notify/notify.js"></script>
<script src="resources/javascript/notify/prettify.js"></script>
<script type="text/javascript" src="resources/javascript/template/common_template.js"></script>
<script src="resources/javascript/confirm_dialog/dialog.js"></script>
 <style type="text/css">
 	.ui-widget-header{background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00E3FF), color-stop(1,#03b7cf) );border:1px solid #36b3c4;text-shadow:0 1px 1px rgb(0, 48, 53);margin:-.12em;}
 	.ui-widget-overlay{background:rgba(81, 139, 146, 0.66);}
 </style>
<script>
  var arrMenu;
  var currentBrowserHeight;
  var userReg = '${userReg}';
  $(document).ready(function(){
	  if(top != self) {
	  	   if (window.addEventListener)
		  	 	window.addEventListener("load", changeCSS, false);
		   else if (window.attachEvent)
		   		window.attachEvent("onload", changeCSS);
		   else window.onload = createIframe;
	  }
	  var path = $(location).attr('pathname');
	  if(path == '/index.htm'){
		  $('#headerWrap').css('display', 'none');
		  $('#menuWrap').css('display', 'none');
	  }
	  if(getStoredValue("itemName")){
      	var temp = $('#menu').multilevelpushmenu('finditemsbyname', getStoredValue("itemName"));
	      if(temp){
		      $(temp[0]).addClass('cmn-t-pulse');
		      $(temp[0]).css('background', '-webkit-gradient(linear, 0% 0%, 0% 100%, color-stop(0.05, rgb(0, 169, 222)), to(rgb(0, 136, 198)))');
	      }
	  }
	  storeValue('ajaxPath', "");
  });
  function changeCSS() {
	   $("#headerWrap").hide();
	   $("#menuWrap").hide();
	   $("#footer").hide();
	}
	var toggle = [expand,collapse];
   	$("#toggleMenu").click(function(){
   		toggle.reverse()[1]();
   		console.log("inside toggle");
   	});
   	
	$('.listmenu').click(function(e) {
	    e.stopPropagation();
    });
	
   	($('#body, #nav')).click(function(){
  		if(expanded){
  			var obj = $(".menu-wrapper");
  		    $(obj).toggleClass("on");
   			toggle.reverse()[1]();
   			isBack = false;
    	} 
  		else if(isBack){
    		var obj = $(".menu-wrapper");
  		    $(obj).toggleClass("on");
    		toggle.reverse()[1]();
    		isBack = false;
    	} 
   	});

	 $('.ui-accordion,#body').bind("DOMSubtreeModified",function(){
		 if($(this).parent().find('.form').length > 0){
		 }else{
			setFooterHeight();
		 }
	}); 
	 
	$('select').on('change', function() {
	   if(this.value){
		   if(this.value != 'select')
			  storeValue(this.id, this.value +"_"+ this.options[this.selectedIndex].text);
	    }
		if(userReg)
		  setDropdownColor();
	});

function setFooterHeight(){
}

function setDropdownColor() {
	var path = $(location).attr('pathname');
	if(path =='/earlyLiteracyWord.htm' || path =='/assignLetterSet.htm' || path =='/assignWordSet.htm' || path =='/gradeLetterSet.htm' || path =='/gradeWordSet.htm'){
		$('select').each(function(index){ 
			if($(this).val() != 'select')
		  		$('#'+this.id).css('color','black');
		});
	}else{
		$('select').each(function(index){  
	   		if($(this)[0].selectedIndex == 0)
	   			$('#'+$(this)[0].id).css('color','gray');
	   		else
	   			$('#'+$(this)[0].id).css('color','black');
		});
	}
}
	function ajaxPageLoad(){		
		$('select').on('change', function() {		
		   if(this.value){		
			   if(this.value != 'select')		
				  storeValue(this.id, this.value +"_"+ this.options[this.selectedIndex].text);		
		    }		
			if(userReg)		
			  setDropdownColor();		
		});		
	}		
					
	function gotoIolReport(){		
		storeValue('ajaxPath', "/RIO21stIOLReportCard.htm");		
	}
	
	function gotoItenAnalysisReport(){		
		storeValue('ajaxPath', "/itemAnalysisReport.htm");		
	}
	
$(document).ajaxComplete(function(){
	setFooterHeight();
	if(userReg)
	  setDropdownColor();
});
$(window).bind("load", function() {
	if(userReg)
	  setDropdownColor();
 });
 
$(".menu-toggle").on('click', function() {
	var obj = $(".menu-wrapper");
	  $(obj).toggleClass("on");
	  toggle.reverse()[1](); 
	  isBack = false;
	});	
	
 </script>
</body>
</html>