<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<table width="100%" align='center' cellpadding='5' cellspacing='5' class="des" border=0 style="border:1px solid #d4e5e8;font-size: 13px;font-family: 'Segoe UI', Frutiger, 'Frutiger Linotype', 'Dejavu Sans', 'Helvetica Neue', Arial, sans-serif;"> 
<tr><td>
	<h3 align="center" > <font color="red">LEARNING PRIORITY SYSTEM RUBRIC</font></h3>
	<table width="100%"><tr>
                <th align="center" width="10%" align="center"><font size="4"> <u> Level</u></font></th>
                <th  width="15%" align="center"><font size="4"><u> Academic Grade </u></font></th>
                <th  width="15%"  align="center"><font size="4"><u> Numerical Percentage Score</u></font></th> 
                <th  width="40%"  align="center" ><font size="4"><u> Performance Descriptions for Academic Standards</u></font></th>
            </tr>
            <tr><td height=6 colSpan=10></td></tr>
           <c:forEach items="${academicPerformanceLt}" var="academicPerformance"  varStatus="status">
            <tr>
                <td  align="center" width="10%" >${academicPerformance.academicLevel}</td>
                <td  align="center" width="10%" >${academicPerformance.academicGrade}</td>
                <c:forEach items="${numPerGradeMap}" var="numPerGrade"  varStatus="status">
                 <c:if test="${numPerGrade.key eq academicPerformance.academicId}">
                  <td  align="center" width="30%" >${numPerGrade.value}</td>
                 </c:if>
                </c:forEach>
                <td  align="left" width="10%" >${academicPerformance.academicDescription}</td>
                  <tr><td height=6 colSpan=10></td></tr>
            </tr>
            </c:forEach>
          </table>
	</td>
    </tr>
  </table>