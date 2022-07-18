<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 

<html>
<style>
.tab{
  border-width: thin;
  border-style: none;
  border-color: grey;
  border: 2px solid grey;
     
 }
</style>
<body>
<table align="center">
	<tr>
		<td width="100%" style="padding-left: 2em;" class="label" height="15" valign="bottom">Differentiation Strategies </td>
	</tr>
</table>
<div id="stemStrategiesDiv">
<c:set var="count" value="0" /> 
<c:set var="coun" value="0" /> 
<table align="center" class="tab" border=1 style="background-color:white">
	 <tr>
		<c:forEach items="${stemStrategiesLt}" var="stStg">
			<c:set var="coun" value="${coun+1}" /> 
			<c:set var="count" value="${count+1}" /> 
			<c:set var="col" value="" />
			<c:forEach items="${unitStemStratgLt}" var="unStg">
				<c:if test="${stStg.stemStrategiesId eq unStg.stemStrategies.stemStrategiesId}">
					<c:set var="col" value="yellow" />
				</c:if>
			</c:forEach>
			 <td width='200' align='center' style='padding-right: 1.6em;' class="stemTblText" onClick="setStrategColor(${coun},${stemUnitId})">
			    <label id="tdStrateg${coun}" style="background-color:${col};align:left"> ${stStg.strategiesDesc}</label>
			    <input type="checkbox" name="unitStrateg" id="unitStrateg${coun}" value="${stStg.stemStrategiesId}" style="display:none">
			</td>	
			  <c:if test="${count>=3}">
			  <c:set var="count" value="0" /> 
		</tr>
		<tr>
			 </c:if>						  						
			</c:forEach>
        </tr>

</table>
<div class="active-users-div" id="tab5-active-users-div"></div>
</div>
</body>
</html>