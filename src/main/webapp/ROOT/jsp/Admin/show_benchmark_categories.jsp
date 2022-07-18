<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
 
 <form id='benchmarkCutOffs' name='benchmarkCutOffs'>
 <table class="des" border=0><tr><td><div class="Divheads">
 <table>
   <tr>
     <td width='130' class="headsColor"><input type='hidden' name='operation' id='operation' value='save'> Category</td>
     <td width='160' class="headsColor">Fluency Cut Off</td>
     <td width='150' class="headsColor">Retell Cut Off</td>
   </tr></table></div><div class="DivContents"><table><tr><td>&nbsp;</td></tr>
   <c:set var="s" value="0" />
     <c:forEach items="${benchmarkCategoriesList}" var="bc">
         <tr>
         <td width='150' class="tabtxt"><input type='hidden' name='categoryId' id="categoryId${s}" value='${bc.benchmarkCategoryId}'>
           <c:out value="${bc.benchmarkCategory}"></c:out></td>
         <td width='150'><input type='number' name='fluencyCutOff' min="1" max="200" id='fluencyCutOff${s}' value="${benchmarkCutOffMarksList[s].fluencyCutOff}" size='10'></td>
         <td width='160'><input type='number' name='retellCutOff'  min="0" max="4" id='retellCutOff${s}' value="${benchmarkCutOffMarksList[s].retellCutOff}" size='10'></td>
        </tr>
         <c:set var="s" value="${s+1}" />
     </c:forEach></table>
    
    
    <table> 
    <tr><td>&nbsp;</td></tr>
   <tr> <td width="100%" colspan='4' align="center"> 
   <table align="center"> <tr>
    <td width='100'>&nbsp;</td>
   <td  width='50%' align='center' valign='middle'><input type="submit" class="button_green" style="border:none;"  onclick="saveBenchmarkCuttoffPoints();return false;"></td>
   <td width='150' align='left' valign='middle'><div class="button_green" align="right" onclick="document.benchmarkCutOffs.reset();return false;">Clear</div></td>
  </tr></table>
</td></tr>
</table></div></td></tr></table>
</form>


 