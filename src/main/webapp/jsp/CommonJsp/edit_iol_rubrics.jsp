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
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<style>

</style>
<script type="text/javascript">
	function getSubCriterias(){
		var lCriteriaId =  $('#lCriteriaId').val();
		$("#subId").empty();
		$("#subId").append($("<option></option>").val('select').html('Select Sub Criteria'));	
		var subCriteriaContainer = $(document.getElementById('IOLRubricDiv'));
		$(subCriteriaContainer).empty(); 
		if(lCriteriaId != 'select'){			
			$("#loading-div-background").show();
			$.ajax({  
		 		url : "getSubCriteriasByLegend.htm",
		 		type : "POST",
		   		data: "lCriteriaId="+lCriteriaId,
		 		success : function(response) {
		 			var legendSubCriteria = response.legendSubCriteria;
					$.each(legendSubCriteria, function(index, value) {
						$("#subId").append($("<option></option>").val(value.legendSubCriteriaId).html(value.legendSubCriteriaName));
					});
		 			$("#loading-div-background").hide();
		 		}  
			});
		}
	}
	
	function getRubrics(){
		var subCriteriaContainer = $(document.getElementById('IOLRubricDiv'));
		$(subCriteriaContainer).empty(); 
		var subId =$('#subId').val();
		if(subId != "select"){
			$("#loading-div-background").show();
			$.ajax({
				url : "getSubcriteriaRubrics.htm",
				type : "POST",
				data : "subId="+subId,
				success : function(data){
					$(subCriteriaContainer).append(data);
		        	$("#loading-div-background").hide();
				}
			});
		}		
	}
</script>
<title>Edit IOL Rubrics</title>
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
		        	<c:choose>
    					<c:when test="${teacherObj eq null}">
       						<%@ include file="/jsp/CommonJsp/le_rubric_tab.jsp" %>
    					</c:when>    									
    					<c:otherwise>
							<%@ include file="/jsp/Teacher/view_gradebook_tabs.jsp" %>
						</c:otherwise>
					</c:choose>
		         	
		        </div>
     		  </td>
     		</tr>
       </table>
  </td></tr>
  <tr><td>
  	<form method="POST" enctype="multipart/form-data" action="uploadIOLRubricFile.htm" id="uploadIOLRubric">
			<table border=0 cellSpacing=0 cellPadding=0 width="100%" class="title-pad">                         
            	<tr><td height="30" width="100%" align="left" valign="middle">
					<table width="50%" height="30" border="0" cellpadding="0" cellspacing="0" align="left">
						<tr>
							<td height="40" align="left" class="label" style="">
								Legend Criteria:&nbsp;&nbsp;&nbsp;<select name="lCriteriaId" style="width:120px;" class="listmenu" id="lCriteriaId"  onChange="getSubCriterias();">
					            	<option value="select">Select Criteria</option>
									<c:forEach items="${legendCriterias}" var="lc">
										<option value="${lc.legendCriteriaId}">${lc.legendCriteriaName}</option>
									</c:forEach>
							    </select>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<td align="left" class="label" style="">
							    Sub Criteria:&nbsp;&nbsp;&nbsp;<select name="subId" class="listmenu" id="subId" onchange="getRubrics();">
					            	<option value="select">Select Sub Criteria</option>									
							    </select>
							</td>
						</tr>
					</table>					
				</td></tr>
				<tr><td height="20px;">&nbsp;</td></tr>
				<tr>
					<td><div id="IOLRubricDiv"></div></td>
				</tr>
			</table>
		</form>
  </td></tr>
 	</table>
	</body>
</html>