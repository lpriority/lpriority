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
   		 <div class="menu-box" style="margin: 158px 18px;">
   		 	 <h4></h4>
  		 	   <div class="content">
			     <ul class="ca-menu">
                      <li>
			                        <a href="studentReadingRegister.htm">
			                            <span class="ca-icon"><i class="fa fa-book"></i></span>
			                            <div class="ca-content">
			                                <h3 class="ca-sub">Reading Register&nbsp;(${rrTtlPtsEarned })</h3>
			                            </div>
			                        </a>
			                    </li>				
                   </ul>
				   </div>
	    		 </div>
 				
			       
 		</div> 