<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="resources/javascript/jplayer/skin/blue.monday/css/jplayer.blue.monday.min.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">

<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<style>
.jp-audio, .jp-audio-stream, .jp-video{
border:0.5px solid #b9c2c4;
}
</style>
<script>
var play = true;
	$(function() {
		$("#datepicker").datepicker();
		//loadDemo();
		 $('#jquery_jplayer_1').on('click', function(){
			 if(play){
				 play = false; 
				 $('#jquery_jplayer_1').jPlayer("play");
			 }else{
				 play = true; 
				 $('#jquery_jplayer_1').jPlayer("pause");
			 }
		  });
		 
		  $('.marquee-vert').marquee({
			    speed: 5000,
				gap: 50,
				delayBeforeStart: 0,
				direction: 'down',
				duplicated: true,
				pauseOnHover: true
			});
	});
  
	function loadDemo(){
		$("#loadId").hide();
		$('.jp-gui').css('pointer-events', 'auto');
		$('.jp-interface').not(':first').remove();
		 var page = document.getElementById("page").value;
		 var pageVar = "";
		 if(page == 'adminDesktop')	 
			 pageVar = "admin";
		 else if(page == 'teacherDesktop')
			 pageVar = "teacher";
		 else if(page == 'studentDesktop')	
			 pageVar = "student";
		 else if(page == 'parentDesktop')	
			 pageVar = "parent";
		$("#loadId").hide();
      	$("#jquery_jplayer_1").jPlayer({
	        ready: function () {
	          $(this).jPlayer("setMedia", {
	            title: "Dashboard Demo",
	            m4v: "loadDemoVideoFile.htm?demoVideoFilePath=LP_Tutorials/"+pageVar+"/desktop.mp4",
	            poster: "images/common_images/watch-demo.png"
	          });
	        },
	        cssSelectorAncestor: "#jp_container_1",
	        swfPath: "resources/javascript/jplayer/jquery.jplayer.swf",
	        supplied: "m4v",        
	        wmode: "window",
			useStateClassSkin: true,
			//autoBlur: false,
			smoothPlayBar: true,
			keyEnabled: true,
			remainingDuration: true,
			toggleDuration: true,
			preload: 'auto',
			size: {width: "320px", height: "200px"},
            error: function (event) {
           }
	      });
	    }
 </script>
<input type="hidden" name="page" id="page" value="${page}" />
     <div class="column_left">	          
   		 <div class="menu-box" style="margin: 8px 8px;">
   		 	 <h4>Menu Box</h4>
  		 	   <div class="content">
			     <ul class="ca-menu">
                    <li>
                        <a href="personalInformation.htm">
                            <span class="ca-icon"><i class="ion-person-add"></i></span>
                            <div class="ca-content">
                                <h3 class="ca-sub">My Profile</h3>
                            </div>
                        </a>
                    </li>
                   <c:choose>
				   <c:when test="${userRegistration.user.userType eq 'parent'}">
                    <li>
                        <a href="childReadingRegister.htm">
                            <span class="ca-icon"><i class="fa fa-book"></i></span>
                            <div class="ca-content">
                                <h3 class="ca-sub">Reading Register</h3>
                            </div>
                        </a>
                    </li>
                     </c:when>
                   
						  <c:when test="${userRegistration.user.userType eq 'admin'}">
		                    <li>
		                        <a href="goToAddAnnouncementsPage.htm">
		                            <span class="ca-icon"><i class="fa fa-bullhorn"></i></span>
		                            <div class="ca-content">
		                                <h3 class="ca-sub">Notifications</h3>
		                            </div>
		                        </a>
		                    </li>
		                    <li>
		                        <a href="goToAddEventsPage.htm">
		                            <span class="ca-icon"><i class="fa fa-calendar"></i></span>
		                            <div class="ca-content">
		                                <h3 class="ca-sub">Events</h3>
		                            </div>
		                        </a>
		                    </li>
		                    <li>
                         <a href="getRRReview.htm">
                            <span class="ca-icon"><i class="fa fa-book"></i></span>
                            <div class="ca-content">
                                <h3 class="ca-sub">Reading Register</h3>
                            </div>
                         </a>
                          </li>
						  </c:when>
						  <c:when test="${userRegistration.user.userType eq 'teacher'}">
							<li>
								
		                        <a href="gradeBook.htm?page=iolReportCard" onclick="gotoIolReport()">
		                            <span class="ca-icon"><i class="fa fa-line-chart"></i></span>
		                            <div class="ca-content">
		                                <h3 class="ca-sub">Learning Indicator</h3>
		                            </div>
		                        </a>
		                    </li>
		                      <li>
		                        <a href="stem.htm">
		                            <span class="ca-icon"><i class="fa fa-puzzle-piece"></i></span>
		                            <div class="ca-content">
		                                <h3 class="ca-sub">${LP_TAB_STEM100}</h3>
		                            </div>
		                        </a>
		                    </li>
		                     <li>
                         <a href="getRRReview.htm">
                            <span class="ca-icon"><i class="fa fa-book"></i></span>
                            <div class="ca-content">
                                <h3 class="ca-sub">Reading Register</h3>
                            </div>
                         </a>
                          </li>
				                    
						  </c:when>
						  <c:when test="${userRegistration.user.userType eq 'student above 13' || userRegistration.user.userType eq 'student below 13'}">
							   <li>
		                          <a href="StudentSelfIOLReportCard.htm">
		                            <span class="ca-icon"><i class="fa fa-line-chart"></i></span>
		                            <div class="ca-content">
		                                <h3 class="ca-sub">Learning Indicator</h3>
		                            </div>
		                          </a>
			                   </li>
			                   <li>
			                        <a href="StudentSTEMCurriculum.htm">
			                            <span class="ca-icon"><i class="fa fa-puzzle-piece"></i></span>
			                            <div class="ca-content">
			                                <h3 class="ca-sub">${LP_TAB_STEM100}</h3>
			                            </div>
			                        </a>
			                    </li>
			                    <li>
			                        <a href="studentReadingRegister.htm">
			                            <span class="ca-icon"><i class="fa fa-book"></i></span>
			                            <div class="ca-content">
			                                <h3 class="ca-sub">Reading Register&nbsp;(${rrTtlPtsEarned })</h3>
			                            </div>
			                        </a>
			                    </li>					                   
						  </c:when>
						  <c:otherwise>
							
						  </c:otherwise>
					</c:choose>
                   </ul>
				   </div>
	    		 </div>
 				 <div class="column_right_grid calender">
                      <div class="cal1"> </div>
				  </div>
			     <div class="dashboard-box" class="text1" style="margin-top:2em;">
			    	<div class="ribbon-right"><span>News</span></div> 
			     	<table width="100%" align="center" class="menu">
						<c:forEach items="${lpNews}" var="news">					
							<tr><td width="100%" height="10" align="left" style='padding-bottom:.5em;padding-left: 3em;text-shadow: none;'><a href="${news.urlLink }" target="_blank" style="color: #005b84;text-shadow: 0 1px 1px rgb(191, 191, 191);font-size: 13.5px;"> ${news.title }</a></td></tr>
						</c:forEach>
					</table>
			       </div><div style="margin-bottom:2em;"></div>
    
 		</div> 