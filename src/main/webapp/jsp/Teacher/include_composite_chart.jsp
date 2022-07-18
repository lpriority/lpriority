<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
		<script type="text/javascript">
			function sumupGrandTotal(i1, i2){
				console.debug(i1);
			  	var grandTotal=0;
			    var eachOverAllGrade = document.getElementById('overallgrade'+i2).value;
			    document.getElementById('overAllGrade'+i1+i2).value = eachOverAllGrade;
			    var overAllGrades=document.getElementsByName('overAllGrade'+i1);
			    var subTotal=0.0;
			    for(var i=0;i<overAllGrades.length;i++){
			        subTotal=parseFloat(subTotal)+parseFloat(overAllGrades[i].value);
			    }
			    document.getElementById('subTotal'+i1).value=subTotal; 
			    var subTotals=document.getElementsByName('subTotals');
			    for(var j=0;j<subTotals.length;j++){
			        grandTotal=parseFloat(grandTotal)+parseFloat(subTotals[j].value);
			    }
			    document.getElementById('grandTotal').value=grandTotal;
			}
			function checkScore(){
				grandTotal = document.getElementById('grandTotal').value;
				if(grandTotal!=100){
				alert('Grand total should total to 100. Reset your scores');
				return false;
				}
				var form = $("#compositForm");
				   var url = form.attr('action');

				   $.ajax({
				          type: "POST",
				          url: url,
				          data: form.serialize(), // serializes the form's elements.
				          success: function(data){
				       	  console.log(data);
				              alert(data.returnMessage); // show response from the php script.
				          }
				       });
				}
		</script>
		<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<br/>
		<div align="center">
		<form:form action="saveCompositeChartValues.htm" modelAttribute="compositeChartValues" id="compositForm" onsubmit="return checkScore()">
			<c:set var="tCount" value="0"></c:set>
			<c:set var="sTotal" value="0"></c:set>
			<c:set var="gTotal" value="0"></c:set>
			<table class="des"><tr><td><div class="Divheads">
			<table>
				<tr>
					<td style="border: thick;" width="300">Elements Overall grade</td>
					<td  width="200">Types</td>
					<td  width="170"># tests</td>
					<td  width="170">points per</td>
					<td  width="150">% Overall Grade</td>
				</tr></table></div>
				<div class="DivContents"><table>
				<tr><td>&nbsp;</td></tr>
				<c:forEach begin="0" end="${count}" var="cnt">
					<tr>
						<td valign="top"  width="300" class="txtStyle">
							<c:if test="${lastEventName != compositeChartValues.compositecharts[cnt].gradeevents.eventName}">
								<div style="">
									<c:set var="lastEventName" value="${compositeChartValues.compositecharts[cnt].gradeevents.eventName}"></c:set>
									<c:set var="tCount" value="${tCount+1}"></c:set>
									<c:out value="${lastEventName}"></c:out>
								</div>
							</c:if> 
							<form:hidden path="compositecharts[${cnt}].compositeChartId" />
							<form:hidden path="compositecharts[${cnt}].classStatus.csId" /> 
							<form:hidden path="compositecharts[${cnt}].gradeevents.eventId" /> 
							<form:hidden path="compositecharts[${cnt}].assignmentType.assignmentTypeId" />
						</td>
						<td  width="200" class="txtStyle">
							<c:out value="${compositeChartValues.compositecharts[cnt].assignmentType.assignmentType}"></c:out>
						</td>
						<td  width="170" class="txtStyle">
							<form:input type="number" path="compositecharts[${cnt}].nooftests" required="required" />
						</td>
	                    <td  width="170" class="txtStyle">
						<form:input type="number" path="compositecharts[${cnt}].pointspertest" required="required" />
						</td>
						<td  width="150" class="txtStyle">
							<form:input type="number" path="compositecharts[${cnt}].overallgrade" required="required" 
								id="overallgrade${cnt}" onblur="sumupGrandTotal(${tCount},${cnt})" /> 
							<input type="hidden" required="required" name="overAllGrade${tCount}" id="overAllGrade${tCount}${cnt}" 
								value="${compositeChartValues.compositecharts[cnt].overallgrade}" />
							<c:set var="sTotal" value="${sTotal+compositeChartValues.compositecharts[cnt].overallgrade}"></c:set>
						</td>
					</tr>
					<tr>
						<td colspan="5" align="right" class="tabtxt">
							<c:if test="${lastEventName != compositeChartValues.compositecharts[cnt+1].gradeevents.eventName}"> 
								Total Points 
								<input type="number" name="subTotals" id="subTotal${tCount}" required="required" value="${sTotal}" readonly="readonly"/>
								<c:set var="gTotal" value="${gTotal+sTotal}"></c:set>
								<c:set var="sTotal" value="0"></c:set>
								<br>
								<br>
							</c:if>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="5" align="right" class="tabtxt">
						Grand Total <input type="number" name="grandTotal" id="grandTotal" required="required" value="${gTotal}" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td colspan="5" align="center"><br><input type="button" class="button_green" id="btSubmitChanges" name="btSubmitChanges"  height="52" width="50" value="Submit Changes" onclick="checkScore()"/></td>
				</tr>
				
				</table></div></td></tr></table><br>
			
		</form:form>
		</div>
	</body>
</html>