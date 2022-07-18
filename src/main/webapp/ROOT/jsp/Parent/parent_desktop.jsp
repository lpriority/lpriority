<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<head>
<title>Parent Desktop</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link href="resources/css/dashboard/style.css" rel="stylesheet" type="text/css" media="all"/>
<link href="resources/css/dashboard/nav.css" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" src="resources/javascript/dashboard/login.js"></script>
<script type="text/javascript" src="resources/javascript/dashboard/Chart.js"></script>
 <script type="text/javascript" src="resources/javascript/dashboard/jquery.easing.js"></script>
 <script type="text/javascript" src="resources/javascript/dashboard/jquery.ulslide.js"></script>
 <!----Start Calender -------->
  <link href="resources/css/dashboard/clndr.css" rel="stylesheet" type="text/css" media="all"/>
  <script type="text/javascript" src="resources/javascript/dashboard/underscore-min.js"></script>
  <script type="text/javascript" src="resources/javascript/dashboard/moment-2.2.1.js"></script>
  <script type="text/javascript" src="resources/javascript/dashboard/clndr.js"></script>
  <script type="text/javascript" src="resources/javascript/dashboard/site.js"></script>
  <script type="text/javascript" src="resources/javascript/dashboard/jquery.marquee.js"></script>
  <!----End Calender -------->
  <script type="text/javascript" src="resources/javascript/common/img_viewer.js"></script>
  <link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src="resources/javascript/ParentJs.js"></script>
  <script type="text/javascript" src="resources/javascript/common/home_page.js"></script>

<style>
.notify{
	width:auto;
	padding:none;
}
.tsc_ribbon_wrap .left-edge.fork{
 top:6px;
}
</style>
</head>
<body>	
	<div class="main">  
	 <div class="wrap">  
	  	<div class="column_middle_stud">
          <%@include file="/jsp/DistrictAdmin/common_homepage_middle.jsp"%>
          <div class="middle_ribbon" style="margin-left: -3em;margin-top: .5em;"><span class="middle_text">Parent's Desktop</span></div>
      	</div>
     </div>
	</div>
   
   <table width="60%" align="center">
      <tr>
      <td width="30%" align="center">
     	<table width="80%" border="0" cellpadding="0" cellspacing="0" align="center">
			<tr>
			<td height="200" valign="top" style="padding-top: 2em;">		
			  <div style="margin-bottom:2em;"></div>
         		 <div class="dashboard-box" style="background:#dee4e6;width:100%;height:220px;">
			        <div class="dashboard-label"><div class="back-side-right"></div><span class="front-side">Fluency Results</span></div>
			   		   <div style="width: 100%;overflow: hidden;height: 180px;font-size:13px;margin-top: 4em;float:left;">
		                 <div>							   
						    <div class="content">
			                   <ul class="ca-menu">
                                 <li>
			                      	<a href="childBenchmarkResults.htm">
			                        	<span class="ca-icon"><i class="fa fa-leanpub"></i></span>
			                        	<div class="ca-content">
			                            <h3 class="ca-sub">Fluency Results&nbsp;</h3>
			                        	</div>
			                      	</a>
			                      </li>				
                               </ul>
			                </div>
		                 </div>                              	                 
				      </div>
				    </div>				
			 </td>							
	         </tr>       		
	     </table>  
      </td> 
      <td width="30%" align="center">
         <table width="80%" border="0" cellpadding="0" cellspacing="0" align="center">
			<tr>
			<td height="200" valign="top" style="padding-top: 2em;">			
			<div style="margin-bottom:2em;"></div>
         	   <div class="dashboard-box" style="background:#dee4e6;width:100%;height:220px;">
			      <div class="dashboard-label"><div class="back-side-right"></div><span class="front-side">Reading Register</span></div>
			   		 <div style="width: 100%;overflow: hidden;height: 180px;font-size:13px;margin-top: 4em;float:left;">
		                <div>							   
							<div class="content">
			                    <ul class="ca-menu">
                                   <li>
			                        <a href="childReadingRegister.htm">
			                            <span class="ca-icon"><i class="fa fa-book"></i></span>
			                            <div class="ca-content">
			                                <h3 class="ca-sub">Reading Register&nbsp;</h3>
			                            </div>
			                         </a>
			                      </li>				
                                </ul>
			                 </div>
		                  </div>                             	                 
				  	   </div>
				  </div>				
				</td>							
		        </tr>       		
	      </table>  
   </td></tr>
   </table>
   <table width="60%" align="center" style="padding-bottom:1em">
   		<tr><td>
   			<c:choose>
				<c:when test="${fn:length(events) gt 0 or fn:length(notificationStatusLt) gt 0}" > 						
  					<div style="width: 99.5%;overflow: hidden;height: 80px;font-size:13px;margin-top: 2em;float:left;">
  						<div class="marquee-vert" style="height:60px;" data-gap="50" data-speed="2000" data-direction="down" pause-on-hover="true" data-duplicated="true">
	 						<marquee behavior="scroll" direction="left" onmouseover="this.stop();" onmouseout="this.start();">
	 						<table width="100%" class="tbl" style="margin-right:.5em;" align="center">
	   							<tr>
	   						      <c:forEach items="${events}" var="event" varStatus="eventCount">									
									<td height="10" width="5%" style="padding-left:2em;padding-top:1em;"><p class="calendar"><em><fmt:formatDate pattern="dd" dateStyle="long" value="${event.announcementDate}"/> <fmt:formatDate pattern="MMM" dateStyle="long" value="${event.announcementDate}"/></em></p></td>
									<td height="10" width="40%" style="padding: 0.5em;" align="left">
										<a href="#" style="font-family:Raleway,Open Sans,sans-serif;" onClick="openEventDialog(${event.eventId})"><span style="padding-left:5px;font-size:13.2px;color:#800000;">${event.eventName}&nbsp;&nbsp;<i class="fa fa-caret-right" aria-hidden="true"></i></span>&nbsp;&nbsp;&nbsp;
											<span class="text" style="padding-left:2px;font-weight:500;font-size:13px;text-shadow: 0 1px 1px rgb(165, 165, 165);">${event.description}</span>
								        </a>
								     </td>									
								  </c:forEach>
								  <c:forEach items="${notificationStatusLt}" var="notificationStatus" varStatus="announceCount">									 
									   <td height="40" valign="middle" style="padding-bottom:0em;padding:.5em;width:5%;text-align:left;color:#002c40;font-size:20px;text-decoration: none;font-weight: 400;"><i class="fa fa-bullhorn" aria-hidden="true"></i></td>
										     <td style="width:90%;text-align:left;padding-bottom01em;" height="20" valign="middle">
												 <a href="#" onClick="openAnnouncementDialog(${notificationStatus.announcements.announcementId},${notificationStatus.notificationStatusId})">
												     <span class="text" style="padding-left:5px;font-size:13.2px;color:#800000;"> ${notificationStatus.announcements.annoncementName}&nbsp;&nbsp;&nbsp;<i class="fa fa-caret-right" aria-hidden="true"></i>
												     <span class="text" style="padding-left:5px;font-weight:500;font-size: 12.2px;text-shadow: 0 1px 1px rgb(138, 138, 138);" >${notificationStatus.announcements.announceDescription}</span></span>&nbsp;&nbsp;&nbsp;&nbsp;
											     </a>											    
											</td>										   
								  </c:forEach>																						 
		         		      </table>
		         		    </marquee> 
	         		      </div>
	         		   </div>	         		  
	         	 </c:when>
         	 </c:choose>
           </tr>
   </table>
</body>
</html>