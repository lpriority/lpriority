<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script> 
<script type="text/javascript" src="resources/javascript/CommonValidation.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/early_literacy.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<%@ include file="../CommonJsp/include_resources.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script>
$(document).ready(function(){
	var k1Set = $('#k1Set').val();
	wordsArray.length = 0; 
	wordsArray = k1Set.split(" ");
	wordsArray = jQuery.grep(wordsArray, function(n){ return (n); });
	var str = "";
	for( var cnt= 0; cnt < wordsArray.length; cnt++ ){
		str+= "<div style='height:32px;padding:0.3em;' id="+cnt+"><input type='button' class='subButtons subButtonsWhite medium' id=word_"+cnt+" onClick='editWord(this)' value="+wordsArray[cnt]+" style='font-size:14px;border-radius:0.5em;width:auto;font-family:Roboto,-apple-system,BlinkMacSystemFont,Helvetica Neue,Segoe UI,Oxygen,Ubuntu,Cantarell,Open Sans,sans-serif;'/>";
		str+= "&nbsp&nbsp<i class='fa fa-times' id=remove_"+cnt+" aria-hidden='true' style='cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;' onclick='removeWord(this)'></i></br></br></div>";
	}
	$("#appendDiv").append(str);
	$("#appendDiv").show();
});
</script>
</head>
<body>
    <input type="hidden" id="setId" name="setId" value="${setId}" />
    <input type="hidden" id="k1Set" name="k1Set" value="${k1Set}" />
    <input type="hidden" id="mode" name="mode" value="${mode}" />
    <input type="hidden" id="csId" name="csId" value="${csId}" />
    <input type="hidden" id="divId" name="divId" value="${divId}" />
	<table width="90%" height="30" border="0" cellpadding="0" cellspacing="0" align="center" id="createWordDiv" style="">
		<input type="hidden" id="updatedWordId" name="updatedWordId" />
		<tr><td height=60 colSpan=30></td></tr>
		<tr align="center" width="60%">
			<td align="right" width="30%"> <span class="title-style">Set Name   : </span><input type="text" id="setName" style='color: black;font-size:14px;' value="${setName}" onblur="${(mode eq 'edit')?'createTestSets();':''}"></td>
			<td align="right" width="60%"><span class="title-style">Enter a Word </span><span class="tabtxt" style="color:#ec3900;"><b>(Press ENTER to add a word)</b></span>:&nbsp;&nbsp;</b><input type="text" id="words" style='color: black;height:30px;width: 180px;font-weight: 500;font-size:16px;' onkeydown="if (event.keyCode == 13){ ${(mode eq 'edit')?'collectWords(this);createTestSets();':'collectWords(this);'}}" onblur="" ></td>
		</tr>
		<tr>			
			<td align="left" width="100%" colspan='2'>
				<table border="0"  width="50%" align="center" >
					<tr><td align="center" ><div id="appendDiv" style="background:#e2eaee;font-size:14px;overflow-y: auto;border-style: groove;border-width: 1px;overflow-x: hidden;max-height: 20em;min-height: 20em;width: 35em;border:0.5px solid rgb(195, 201, 204);margin-top:3em;"/></td></tr>
				</table>
			</td>
		</tr>
		<tr>
			<td height=10 valign="middle" colspan="2">
				<table width="100%" id="buttonsDiv" style="display:none;" align="center"><tbody><tr>
                   <td width="30%" height="10" align="right" valign="middle"> 
                  		 <div class="button_green" align="right" onclick="createTestSets()">Create Set</div> 
                   </td>
                   <td width="30%" height="10" align="left" valign="middle" style="padding-left: 2em;">
                  		 <div class="button_green" align="right" onclick="clearWords()">Clear</div> 
                   </td>
                   </tr></tbody>
               </table>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<div id="resultDiv" class="status-message" style="font-size:16px;"/>
			</td>
		</tr>
	</table>
</body>
</html>