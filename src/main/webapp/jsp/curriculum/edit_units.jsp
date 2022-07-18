<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${isError == false}">
<table>
	<c:forEach var="uNumber" begin="0" end="${noOfUnits}" varStatus="status">

		<tr>

			<td width="562" height="30" align="center" class="txtStyle">${status.count}&nbsp;.&nbsp;
				<form:input path="editUnits.units[${uNumber}].unitName" id="unitName${uNumber}"
					pattern="[A-Za-z]+[a-zA-Z0-9\s-'.,&@:?!(){}$#]+" 
					title="Only Numbers and Special characters not allowed" required="true" height="20" />
				<i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;" onclick="deleteFile(${uNumber})"></i>
				<form:hidden path="editUnits.units[${uNumber}].unitId"/>
				<form:hidden path="editUnits.units[${uNumber}].unitNo"/>
				<form:hidden path="editUnits.units[${uNumber}].gradeClasses.gradeClassId"/>
				<form:hidden path="editUnits.units[${uNumber}].userRegistration.regId"/>
			</td>
		</tr>
	</c:forEach>
	<tr>
		<td align="center">
			<input type="image" src="images/Common/submitChangesUp.gif" id="Submit"/>
		</td>
	</tr>
</table>
</c:if>
<c:if test="${isError == true}">
	<label>Units Not Yet Created</label>
</c:if>