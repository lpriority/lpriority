<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <table width="100%" style="color:black;margin-right: auto;margin-left:18em;width: 60%;border: 1px solid #007094;padding:3em;">
	  <tr>
		  <td width="15%" class="label" style="vertical-align:middle;text-align:right;height:60px;color:black;" > Unit Name &nbsp;&nbsp; :</td>
		  <td width="60%" style="vertical-align:middle;padding-left:4em;" class="txtStyle">${stemUnit.stemUnitName}</td>
	  </tr>
	  <tr>
		 <td width="25%" class="label" style="vertical-align:middle;text-align:right;height:40px;color:black;" > Unit Description &nbsp;&nbsp; :</td>
		 <td width="60%" style="vertical-align:middle;padding-left:4em;overflow: auto;height: 100px" class="txtStyle">${stemUnit.stemUnitDesc}</td>
	  </tr>
   <c:if test="${stemUnit.urlLinks ne ''}"> 
	  <tr>
		 <td width="15%" class="label" style="vertical-align:middle;text-align:right;height:40px;color:black;" > URL Links &nbsp;&nbsp; :</td>
		 <td width="60%" style="vertical-align:middle;padding-left:4em;" class="txtStyle">${stemUnit.urlLinks}</td>
	  </tr>
   </c:if>
	  <tr><td colspan="2" height="40"></td></tr>
  </table>