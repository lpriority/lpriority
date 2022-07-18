<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
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
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/admin/add_edit_remove_sl_schl.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/iol_rubrics.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />

<style>
	.ui-widget{
		font-size:1.2em;
	}
</style>
<script type="text/javascript">
	
      function assignIOLRubric(){
    	var userRegId = $("input[name='rubricType']:checked").val();
    	var gradeId =$('#gradeId').val();
		if(gradeId=="select"){
			alert("Please select a grade");
			return false;
		}
		var trimesterId =$('#trimesterId').val();
		if(trimesterId=="select"){
			alert("Please select a trimester");
			return false;
		}
		if(userRegId){
			$("#loading-div-background").show();
			$.ajax({
				url : "assignIOLRubricToGrade.htm",
				type : "POST",
				data : "userRegId="+userRegId+"&gradeId="+gradeId+"&trimesterId="+trimesterId,
				success : function(data){
					//data.status=false;
					alert(data.status);
					$('#gradeId').val("select");
					$('#trimesterId').val("select");
		        	$("#loading-div-background").hide();
				}
			});
		}else{
			alert("Please select a rubric");
			return false;
		}		
	}
</script>
<title>Assign IOL Rubrics</title>
</head>
 <body>
	  <table border=0 cellSpacing=0 cellPadding=0 width="100%" align=center height="100%">  
	  <tr><td>   
	   <table border=0 cellSpacing=0 cellPadding=0 width="100%" align=center height="100%">  
            <tr>
                <td style="color:white;font-weight:bold" >
                	<input type="hidden" name="tab" id="tab" value="${tab}">
                </td>
                <td vAlign=bottom align=right>
		        <div> 
		  			<%@ include file="/jsp/Teacher/view_gradebook_tabs.jsp" %>
			         	
		        </div>
     		  </td>
     		</tr>
       </table>
  </td></tr>
  	</table>
  	<table><tr><td>&nbsp;</td></tr></table>
  	<table width="30%" align="center" class="des">
 <tr><td>
  	<form method="POST" id="assignIOLRubric">
			
			<div class="Divheads"><table><tr><td>Assign Rubric</td></tr>                     
            	</table></div>
            	<div class="DivContents">
            	<table align="center" class="txtStyle">
            	<tr><td><br>Select Rubric</td></tr>
            	<tr><td>&nbsp;</td></tr>
            	<tr><td><input type="radio" name="rubricType" value="0" />
            			<a style="text-decoration: none;color: blue;font-size: 15px" onClick="return getRubricByUser(0)"
            			 onmouseover="this.style.color = 'red';" onmouseout = "this.style.color = 'blue';">Default</a>
            		</td></tr>
            	<tr><td><input type="radio" name="rubricType" value="${adminRegId}"/>
            			<a style="text-decoration: none;color: blue;font-size: 15px" onClick="return getRubricByUser(${adminRegId})"
            			onmouseover="this.style.color = 'red';" onmouseout = "this.style.color = 'blue';">School</a>
            		</td></tr>
            	<%-- <tr><td><input type="radio" name="rubricType" value="${teacherRegId}" />
            			<a style="text-decoration: none;color: blue;font-size: 15px" onClick="return getRubricByUser(${teacherRegId})"
            			onmouseover="this.style.color = 'red';" onmouseout = "this.style.color = 'blue';">Self</a>
            		</td></tr> --%>
            	<tr><td>&nbsp;</td></tr>
            	<tr><td>Assign To&nbsp;:&nbsp;&nbsp;Grade&nbsp;&nbsp;&nbsp;
            	<select name="gradeId" class="listmenu" id="gradeId" onChange="getGradeClasses();" required="required">
							<option value="select">select grade</option>
							<c:forEach items="${grList}" var="ul">
								<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
							</c:forEach>
					</select>
			</td></tr>
			
			<tr><td>Trimester&nbsp;:&nbsp;&nbsp;&nbsp;
            	<select name="trimesterId" class="listmenu" id="trimesterId" required="required">
							<option value="select">select Trimester</option>
							<c:forEach items="${trimesterList}" var="tl">
								<option value="${tl.trimesterId}">${tl.trimesterName}</option>
							</c:forEach>
					</select>
			</td></tr>
			<tr><td align="center"><table align="center"><tr><td><br>
			<div class="button_green" id="btSubmitChanges" name="btSubmitChanges" height="52" width="50" 
					onclick="assignIOLRubric()">Submit</div><td></tr></table></td></tr>
			</table></div>
	</form>
	</td></tr>
	<tr><td>
		<div id="legendCriteriaDailog"  style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title="">
  			<div id="legendCriteriaDiv"></div>
  		</div>
	</td></tr>
			</table>
	</body>
</html>