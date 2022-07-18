
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table><tr><td>
<br>
<table>
	<tr>
		<td width="" style="padding-left: 2em;color:black;" class="label">${LP_STEM_TAB} Area :</td>
		<td width="">
			<select id="stemActiviyAreaId" name="stemActiviyAreaId" style="width:120px;" class="listmenu"  onchange="getStemUnitActivities(this);">
				<option value="select">Select Area</option>
			</select>
		</td>
		<td style="padding-left: 2em;">
			<div id="createId" class="button_green" align="center" style="display: none;" onclick="createActivity()">Add Activity</div> 
		</td>
	</tr>
</table></td></tr></table>
<table align="center" width="90%"><tr><td>
<br>
<div id="activitiesDiv"></div>
<div class="active-users-div" id="tab3-active-users-div"></div>
</td></tr></table>

