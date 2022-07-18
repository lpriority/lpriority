<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="count" scope="session" value="0"/>
 <table width="100%" style="color:black;margin-right: auto; margin-left:8em; width: 80%;border: 1px solid #007094;padding:3em;">
 	 <c:if test="${stemQuestionsLt[0].stemQuestionsType.stemQuesTypeId eq 1}">
		<tr>
			<td width="25%" class="label" style="vertical-align:middle;text-align:right;height:40px;color:black;" > Essential Question &nbsp;&nbsp; :</td>
		  	<td width="60%" height="20" style="vertical-align:middle;padding-left:4em;" class="txtStyle">${stemQuestionsLt[0].stemQuestion}</td>
	  	</tr>
	</c:if>	  
	<tr><td height="30" colspan="2"></td></tr>
	<tr><td  width="25%" class="label" style="vertical-align:top;text-align:right;height:40px;color:black;">Unit Questions &nbsp;&nbsp; :</td><td width="60%" ><table width="100%">
	<c:forEach items="${stemQuestionsLt}" var="stemQuestions" varStatus="status">  	
  	    <c:if test="${stemQuestions.stemQuestionsType.stemQuesTypeId eq 2}">
  	           <c:set var="count" scope="session" value="${count+1}"/>
			  	<tr>
				 	<td height="20" width="60%" style="vertical-align:middle;padding-left:4em;" class="txtStyle">
						${count}. ${stemQuestions.stemQuestion}
				 	</td>
			  	</tr>
		 </c:if>
	 </c:forEach>
	 </table></td></tr>
 </table>