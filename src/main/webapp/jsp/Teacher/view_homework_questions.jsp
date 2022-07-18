<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="mcqCount" value="0"> </c:set>
<table style="width: 60%;" class="des" border=0><tr><td width="500"><div class="Divheads">
<table><tr><td>Questions</td></tr></table></div>
<div class="DivContents"><table>
	<c:forEach items="${hquestionList}" var="ql" varStatus="count">
		<c:if
			test="${assignmentTypeId == 1 ||assignmentTypeId == 2 ||assignmentTypeId == 3 ||assignmentTypeId == 4 ||assignmentTypeId == 5 ||assignmentTypeId == 6 }">
			<tr>
				<td colspan="5" height="20" align="center" valign="middle"
					class="tabtxt">
					<table style="width: 100%">
						<tr>
							<td width="100%" height="30" align="left">
								<b>${count.index+1}. <c:out value="${ql.questions.question}"></c:out></b></td>
						</tr>
					</table>
				</td>
			</tr>
		</c:if>
		<c:if test="${assignmentTypeId==3}">
			<tr>
				<td colspan="10" width="100%" style="padding-left: 1cm;">
					<table style="width: 100%;">
						<c:forEach var="var" begin="0" end="${ql.questions.numOfOptions-1}" varStatus="count"> 			
							<tr>
								<td><input type='checkbox' class="checkbox-design" id="options" name='options' value='' disabled /><label for="options" class="checkbox-label-design"></label></td>
								<td><c:out value="${mcqOptions[mcqCount]}"></c:out></td>													
							</tr>
							<c:set var="mcqCount" value="${mcqCount+1}"> </c:set>
						</c:forEach>
					</table>
				</td>
			</tr>
		</c:if>
		<c:if test="${assignmentTypeId==5}">
			<tr>
				<td colspan="5" style="padding-left: 1cm;">
					<table style="width: 100%">
						<tr>
							<td width='50%' height='30' align='left'>
								<input type='checkbox' class="checkbox-design" id='True' name='answers' value='True' disabled /><label for="True" class="checkbox-label-design">True</label>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;								
								<input type='checkbox' class="checkbox-design" id='False' name='answers' value='False' disabled /><label for="False" class="checkbox-label-design">False</label></td>							
						</tr>
					</table>
				</td>
			</tr>
		</c:if>
		<c:if test="${assignmentTypeId==14}">
			<tr>
				<td>Question File :&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="downloadFile.htm?filePath=${jacQuestionFilePath}"><c:out value="${ql.filename}"></c:out></a>
				</td>
			</tr>
		</c:if>
	</c:forEach></table></div>
	
		
			<c:if test="${fn:length(hquestionList) <= 0}">
              <tr>
			   	<td align='center' valign='middle' class='status-message'>No
					Questions are assigned for this homework</td>
			</c:if>
			

</td></tr></table>