<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<html>
<head>
<script type="text/javascript" src="resources/javascript/common/goal_setting_tool.js"></script>
<script src="resources/javascript/LineChart/jszip.min.js"></script>
<script src="resources/javascript/LineChart/pako_deflate.min.js"></script>
<link rel="stylesheet" href="resources/css/LineChart/kendo.material.min.css" />
<link rel="stylesheet" href="resources/css/LineChart/kendo.dataviz.min.css" />
<script src="resources/javascript/LineChart/kendo.all.min.js"></script>
<script type="text/javascript">
</script>
<style type="text/css">
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
</style>
</head>
<body>
<table border=0 width='35%'>
	<tr><td width='100%'><span style="font-size:24px;color:green;" class="button_white" onclick="goalSettingDownloadToExcelTest(0,'ALL')"><span style="color:black;text-shadow: 0 1px 1px rgba(189, 189, 189, 0.78);font-size: 13px;font-weight: bold;">Download All Teachers Data &nbsp;&nbsp;</span><i class="fa fa-file-excel-o"></i></span></td></tr>
	<tr><td width='100%' class='des'>
	  <div class='Divheads'>
		<table align='center' width='100%'>
			<tr> 
			    <th width='20%' align='center'>S.No</th>
				<th width='60%' align='center'>Teacher</th>
				<th width='20%' align='center'>Download</th>
			</tr>
		</table>
	  </div>
	  <div class='DivContents'>
	  			<table width='100%'><tr><td height='10'></td></tr>
				  <c:forEach items="${teacherLt}" var="teacher" varStatus="status">  
						<tr class='txtStyle'><td width='20%' align='center' height='20'> ${status.count} </td>			            
				    		<td width='60%' align='center'>${teacher.userRegistration.firstName} ${teacher.userRegistration.lastName}</td>
				    		<td width='20%' align='center'><span style='font-size:18px;color:green;' class='button_white' onclick='goalSettingDownloadToExcelTest(${teacher.teacherId},"${teacher.userRegistration.firstName} ${teacher.userRegistration.lastName}")'><i class='fa fa-file-excel-o'></i></span> </td>
							</td></tr>
				   </c:forEach>
				  <tr><td height='10'></td></tr>
				</table>
		</div>
	</td></tr></table>		
</body>
</html>
