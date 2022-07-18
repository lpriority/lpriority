<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Assign Math Gear Game</title>
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/admin/gear_game_by_admin.js"></script>
<script src="resources/javascript/admin/common_dropdown_pull.js"></script>
</head>
<script>
function clearDiv() {
	record.length = 0;
	$('#title').val('');
	$('#instructId').val('');
	$('#dueDate').val('');
	$("#subViewDiv").hide();
	$('#select_all').prop('checked', false);
}

$(document).ready(function () {
    $( "#dueDate" ).datepicker({
   		changeMonth: true,
       	changeYear: true,
       	showAnim : 'clip',
       	minDate: 0
    });
});
</script>
<style>
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
.ui-widget-header{background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00e3ff), color-stop(1,#00b7d0) );border:1px solid #00d8f5;text-shadow:0 1px 1px rgb(0, 48, 53);margin:-.12em;}
.ui-widget-overlay{background:rgba(81, 139, 146, 0.66);}
</style>
<body>
 	<input type="hidden" id="usedFor" name="usedFor" value="${usedFor}"/>
 		  <table border=0 cellSpacing=0 cellPadding=0 width="100%" align=center height="100%">  
	  <tr><td>   
	   <table border=0 cellSpacing=0 cellPadding=0 width="100%" align=center height="100%">  
            <tr>
                <td style="color:white;font-weight:bold" >
                	<input type="hidden" name="tab" id="tab" value="${tab}">
                </td>
                <td vAlign=bottom align=right>
		        <div> 
		         	<%@ include file="/jsp/assessments/rti_tabs.jsp" %>
		        </div>
     		  </td>
     		</tr>
       </table>
  </td></tr>
  <tr><td> 
		<!-- Content center box -->	
		<table style="width: 100%;" align="center" border="0" cellspacing="0" cellpadding="0" >
			<tr>
				<td colspan="2" align="left" valign="middle" class="heading-up">
					<table width="100%" border="0" cellpadding="0" class="title-pad" cellspacing="0">
						<tr>
							<td width="70em" valign="middle" class="label" style="font-size: 0.98em" >Grade  </td>
							<td width="150em" valign="middle" >
								<select id="gradeId" name="gradeId" onchange="clearDiv();getGradeClasses();" class="listmenu"  style="width:120px;">
									<option value="select">select grade</option>
									<c:forEach items="${grList}" var="ul">
										<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
									</c:forEach>
								</select>
							</td>
							<td width="70em"  valign="middle" class="space-between-select"><label class="label" style="font-size: 0.98em">Class  </label></td>
							<td width="150em" valign="center">
								<select id="classId" name="classId" class="listmenu" onchange="clearDiv();getClassSections();" style="width:120px;">
									<option value="select">select subject</option>
								</select>
							</td>
							<td width="70em" valign="middle" class="space-between-select label" style="font-size: 0.98em">Section  </td>
							<td width="150em" valign="middle" >
								 <select  valign="middle" name="csId" id="csId" multiple="multiple" style="overflow-y : visible;height:80px;width:180px;" onclick="selectOption(this, 'subViewDiv')"
									required="required">
								</select>
							</td>
							<td class="tabtxt"><input type="checkbox" class="checkbox-design" id="select_all" name="select_all" onClick="selectAllOptions(this, 'csId', 'subViewDiv')"><label for="select_all" class="checkbox-label-design">Select All</label></td></tr>
						</tr>
					</table>
				</td>
			</tr>
	        <tr><td style="width: 100%;" align="center" border="0" cellspacing="0" cellpadding="0">
					<div id="subViewDiv" class="space-between" style="padding-top:0.5em; display: none;">
       					<table width='100%'>
       						<tr>
       							<td align='center'>
       								<table width='32%' class='des' align='center'>
       									<tr>
       										<td align='center'>
       											<table width='100%' class='Divheads'>
       												<tr>
       													<td align='center'> Assign Game </td>
       												</tr>
       											</table>
       										</td>
       									</tr>
       									<tr>
       										<td align='center' width='100%'>
       											<table width='85%' class='DivContents' align='center' >
       												<tr><td style="padding-top: 20px;"></td></tr>
       												<tr><td align="left" class="tabtxt" valign="middle">
       														Title &nbsp;<img src="images/Common/required.gif"></td>
       													<td>:</td> 
       													<td align="right"><input name='title' type='text' id='title' size='20' maxlength='150' class="textBox" onblur="titleValidation();" value=''/>
       													</td>
       												</tr>
       												<tr><td align='left' class="tabtxt" valign="middle">
       														Instructions &nbsp; <img src="images/Common/required.gif"></td>
       													<td>:</td>
       													<td align="right"> <textarea class="textBox" name='instructId' id='instructId' maxlength="100"></textarea>
       													</td>
       												</tr>
       												<tr><td align='left' class="tabtxt" valign="middle">
       														Due Date  &nbsp;<img src="images/Common/required.gif"></td>
       													<td>:</td> 
       													<td align="left" style="padding-left:3.5em;">
       														<input type="text" id="dueDate" name="dueDate" size="15" maxlength="15" value="" required="required" readonly="readonly"/>
       													</td>
       												</tr>
       												<tr>
       													<td style="padding: 15px;" colspan="3" align="center">
       														<div id='assignGame' class='button_green' onclick='assignGameToSections()' style='font-size:12px;width:100px;'>Assign Game</div>
       													</td>
       												</tr>
       											</table>
       										</td>
       									</tr>
       								</table>
       							</td>
       						</tr>
       					</table>
       				</div>
	        </td></tr>
		   </table>
		</td></tr></table>
	</body>
</html>