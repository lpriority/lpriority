<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Goal Setting </title> 
</head>
<script>
$(document).ready(function() {
	storeValue('ajaxPath', "");	
});
function togglePage(thisVar,url, name){
	$("#common-loading-div-background").show();	
	storeValue('ajaxPath', "/"+url+".htm");
	$.ajax({
		type : "GET",
		url : url+".htm",
		success : function(response) { 
			$("#contentDiv").empty();	
			$("#contentDiv").append(response);
			$("#tab_title").html(name);
			$("title").html(name);
			var current = document.getElementsByClassName("buttonSelect");
		    current[0].className = current[0].className.replace(" buttonSelect", " button");
		    var activeElement = $(thisVar);
		    activeElement[0].className = activeElement[0].className.replace(" button"," buttonSelect");
		    $("#common-loading-div-background").hide();
	 	}
	}); 
}
</script>
<body>
	<table border="0" cellspacing="0" cellpadding="0" align="right" class="">
      <tr>
          <td>
            <ul class="button-group">
				<li><a href="#" onclick="togglePage(this, 'goalSetting', 'Goal Setting')" class="btn ${(page == 'goalSettingDownload')?'buttonSelect leftPill':'button leftPill'}"> Goal Setting</a></li>
				<li><a href="#" onclick="togglePage(this, 'goalSettingExcelDownload', 'Goal Setting Excel')" class="btn ${(page =='goalSettingExcelDownload')?'buttonSelect rightPill':'button rightPill'}"> Goal Setting Excel</a></li>
			</ul>
		 </td>	
       </tr>
    </table>
    <table align="left" style="width: 99.8%;" border="0" cellpadding="0" cellspacing="0" class="title-pad">
			<tr>
				<td id ="tab_title" class="sub-title title-border" height="40" align="left">
					Goal Setting
				</td>
			</tr>	
			
	 </table>
	 <div id = "contentDiv">				
		   	 		<%@include file="goal_setting_download.jsp"%>
			</div>
			
</body>
</html>