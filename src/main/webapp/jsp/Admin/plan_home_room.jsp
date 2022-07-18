
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Plan Home Room</title>

<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript" src="/dwr/interface/teacherService.js"></script>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/Adminjs.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherScheduler.js"></script>
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
</head>
<body>	
	<table border="0" cellspacing="0" cellpadding="0" align="right" class="">
       <tbody><tr>
          <td>
            <ul class="button-group">
			<li><a href="teacherScheduler.htm" class="${(divId == 'planSchedule' || divId == 'plannerData')?'buttonSelect leftPill':'button leftPill'}"> Plan Schedule </a></li>
			<li><a href="planHomeRoom.htm" class="${(divId == 'planHomeRoom')?'buttonSelect':'button'}"> Plan Home Rooms </a></li>
			<li><a href="viewTeacherRequest.htm" class="${(divId =='viewTeacherRequests')?'buttonSelect rightPill':'button rightPill'}"> View Requests</a></li>
			</ul>
		 </td>	
       </tr></tbody>
    </table>
 	<table width="100%">
 	    <tr><td>
 	    	<table width="100%" class="title-pad" border="0">
 				<tr><td class="sub-title title-border" height="40" align="left" >Plan Home Rooms</td></tr>  
 				<tr><td height=5 colSpan=5><div align="right"></div></td></tr>   
             </table>
         </td></tr>    
		 <form:form modelAttribute="homeroomClassesForm" name="homeRoomTeacher" method="post" action="saveHomeRoomTeacher.htm"> 
		 <tr><td  valign="top" width="100%" colSpan=3 align=middle>    
             <table width="100%" border="0" class="title-pad" valign="top">
            	<tr>
                   	<td height="30" align="left" valign="top" class="label"><font size="3">Grade </font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                       	<form:select path="gradeId" id="gradeId" onChange="getGradeLevelTeachers(this);" style="width:120px;" class="listmenu">
                           	<option value="grade" selected>select grade</option>
                           	<c:forEach items="${schoolgrades}" var="grList"><option value="${grList.gradeId}">${grList.masterGrades.gradeName}</option></c:forEach>
                       	</form:select>
                   	</td>
                	</tr>    
               </table>
          </td></tr> 
          <tr><td>  
			<table width="100%" border=0>
			    <input type="hidden" name="divId" id="divId" value="${divId}"></input>
				<tr><td class="space-between common-center">	  
					<div>
			             <div id="gradeLevelTeachers" style=""></div> 
			           </div> 
			    </td></tr>
		   </table>
  		</td></tr>  
       </form:form>
  </table>   
</body>
</html>


