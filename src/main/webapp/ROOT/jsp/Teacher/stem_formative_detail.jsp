<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<table width="100%">	
		<tr>
			<td class = "label">Title :</td>
			<td class = "">${fAssessment.title}</td>
		</tr>
		<tr>
			<td class = "label">Description :</td>
			<td class = "">${fAssessment.description}</td>
		</tr>
		<tr>
			<td class = "label">Keywords :</td>
			<td class = "">
				<c:forEach var="key" items="${keywords}" varStatus="status">
					${key.keyword}
					<c:if test="${fn:length(keywords) != status.count}">
						,
					</c:if>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td class = "label">Instructions :</td>
			<td class = "">${fAssessment.instructions}</td>
		</tr>
		<tr>
			<td class = "label">Type of Assessment :</td>
			<td class = "">${fAssessment.assignmentType.assignmentType}</td>
		</tr>				
	</table>
	<table border="1">
		<c:choose>	
				<c:when test="${fn:length(formativeAssessmentRubrics) gt 0}">
					<c:set var="count" value="0"></c:set>
					<tr>
						<th></th>
						<c:forEach begin="0" end="3" var="index">
							<th>${formativeAssessmentRubrics[index].score}</th>
						</c:forEach>
						<c:forEach items="${fColumnHeaders}" var="fcHeader"> 
							<th>${fcHeader.columnHeaders.columnName}</th>
						</c:forEach>
					</tr>					
					<c:forEach items="${fAssessmentCategories}" var="category"> 
						<tr>
							<td>${category.category}</td>
							<c:forEach begin="0" end="3" varStatus="index"> 								
								<td>${formativeAssessmentRubrics[count].description} <input type="checkbox" disabled="disabled"></td>
								<c:set var="count" value="${count+1}"></c:set>
							</c:forEach>
							<c:forEach items="${fColumnHeaders}" var="fcHeader"> 
								<td></td>
							</c:forEach>
						</tr>														
					</c:forEach>					
				</c:when>
				<c:otherwise>
					<tr>
						<th></th>
						<c:forEach items="${fColumnHeaders}" var="fcHeader"> 
							<th>${fcHeader.columnHeaders.columnName}</th>
						</c:forEach>
					</tr>
					<c:forEach items="${fAssessmentCategories}" var="category">
						<tr>
							<td>${category.category}</td>
							<c:forEach items="${fColumnHeaders}" var="fcHeader"> 
								<td align="center">
									<input type="${fcHeader.columnHeaders.dataType}" disabled="disabled"/>
								</td>
							</c:forEach>							
						</tr>
					</c:forEach>
				</c:otherwise>			
		</c:choose>		
	</table> 