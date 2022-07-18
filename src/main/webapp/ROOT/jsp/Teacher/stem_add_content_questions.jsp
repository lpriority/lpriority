<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<style>
.questionDiv{
	background:#e2eaf0;
	overflow-y: auto ;
	padding:.6em;
	border-style: groove;
	border-width: 1px;
	overflow-x: hidden ;
	max-height: 120px;
	min-height: 120px; 
	width: 85%;
}
</style>
<form action="" id="contentQueForm">
<input type='hidden'  id='mainQueLen' name='mainQueLen' value=0 />
<input type='hidden'  id='additionalQueLen' name='additionalQueLen' value=0 />
  <table width="100%" style="color:black;margin-right: auto; margin-left: auto;" id="testing">
    <tr align="center">
	   <td colspan="22" height="40" class="label">
		  <input type="radio" class="radio-design" id="main"  name="stemAreasType" value="main" checked onclick="getStemAreas()"><label for="main" class="radio-label-design">Main ${LP_STEM_TAB} Areas</label> &nbsp;&nbsp;&nbsp;
		  <input type="radio" class="radio-design" id="additional" name="stemAreasType" value="additional" onclick="getStemAreas()"><label for="additional" class="radio-label-design">Additional ${LP_STEM_TAB} Areas</label>
	  </td>
	</tr>
    <tr><td> 
    	<div id="stemAreasDiv"></div>
	</td></tr>
	<tr><td colspan="2" height="60" valign="middle" style="margin-bottom:5px;">
	   <table width="80%" align="center" id="tab3submitDiv" style="display:none;">
			<tr>
                <td width="35%" height="10" align="right" valign="middle"> 
               	<div class="button_green" align="right" onclick="saveStemContentQuestions()">Submit Changes</div> 
                </td>
                <td width="30%" height="10" align="left" valign="middle" style="padding-left: 2em;">
                	 <div class="button_green" align="right" onclick="$('#contentQueForm')[0].reset(); return false;">Clear</div>
                </td>
           	</tr>
       </table>
	</td></tr>
  </table>
</form>
<div class="active-users-div text" id="tab2-active-users-div"></div>