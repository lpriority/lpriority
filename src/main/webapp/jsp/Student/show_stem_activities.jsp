<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<script type="text/javascript">
var lastHeight = $('#body').outerHeight();
	$('#body').bind("DOMSubtreeModified",function(){
		currentHeight = $('#body').outerHeight();
		var height = $(document).height();
		var changedHeight = (currentHeight-lastHeight);
		if(lastHeight != currentHeight){
			
			lastHeight = currentHeight;
		}
	});
</script>
<style type="text/css">
	
	.ui-dialog > .ui-widget-content {background: white;}
	.ui-widget {font-size:0.9em;font-family:'Segoe UI', Frutiger, 'Frutiger Linotype', 'Dejavu Sans', 'Helvetica Neue', Arial, sans-serif;}
	
	.ui-accordion > .ui-widget-content {background: #f4f9fd;height:350px;}
	.ui-accordion .ui-accordion-icons{background: rgb(235, 244, 251);height:35px;border: 1px solid #a0daf6;}
	.ui-accordion .ui-accordion-header{font-size:100%;}
	.ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active{border: 1px solid #0057AF;color:#0057AF;}
	.ui-state-active a, .ui-state-active a:link, .ui-state-active a:visited,.ui-state-hover a, .ui-state-hover a:hover, .ui-state-hover a:link, .ui-state-hover a:visited, .ui-state-focus a, .ui-state-focus a:hover, .ui-state-focus a:link, .ui-state-focus a:visited{color:#0057AF;}
	.ui-widget input, .ui-widget select, .ui-widget textarea, .ui-widget button{font-size:1em;}
	.ui-widget-content{border: 1px solid #a0daf6;}
	.ui-state-hover, .ui-widget-content .ui-state-hover, .ui-widget-header .ui-state-hover, .ui-state-focus, .ui-widget-content .ui-state-focus, .ui-widget-header .ui-state-focus{border:1px solid #1880C9;color:#1880C9;}
</style>
<script type="text/javascript">
	$(function() {
		$("#stemAreaDetails").accordion({
			collapsible : true,
			active : false
		});
	});
</script>
<div id="stemAreaDetails" align="left" style="padding-left: 2cm; padding-top: 1.5cm; width:95%">	
 
  <table width="100%" style="">
   <tr>
	   <td height="40" width="30%" class="label" align="left" style="padding-left: 2em;vertical-align:top;color:black;">${LP_STEM_TAB} Activities : </td>
	   <td height="60" width="100%" align="left" style="margin-bottom:5px;">
		   <table width="100%" align="left">
		     <c:forEach items="${stemActivityLt}" var="stemActivity" varStatus="status">  
				<tr>
	                <td width="100%" height="10" align="left" valign="top" style="padding-left: 2em;">
		                 <table width="100%" align="left"> 
		                 <c:if test="${stemActivity.activityDesc ne ''}"> 
							<tr>
				                <td width="30%" height="10" align="left" valign="middle"> 
				               		${status.count}. Description : ${stemActivity.activityDesc}
				                </td>
				           	</tr>
				         </c:if> 
				         <c:if test="${stemActivity.activityLink ne ''}"> 
				           	<tr>
				                <td width="30%" height="10" align="left" valign="middle"> 
				               		 Links : ${stemActivity.activityLink}
				                </td>
				           	</tr>
				        </c:if> 
				        <c:if test="${stemActivity.fileName ne ''}"> 
				           	<tr>
				                <td width="30%" height="10" align="left" valign="middle"> 
				               		FileName : <a href='downloadFile.htm?filePath=${uploadFilePath}/stem_files/${stemActivity.stemActivityId}/${stemActivity.fileName}' style="color:blue;text-decoration: underline;cursor: pointer;">${stemActivity.fileName}</a>
				                </td>
				           	</tr>
				        </c:if>    	
				        </table>
	                </td>
	           	</tr> 
	           	</c:forEach>
	       </table>
	  </td>
   </tr>
  </table>
  </div>