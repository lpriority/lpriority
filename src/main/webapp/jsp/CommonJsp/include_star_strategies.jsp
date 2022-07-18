<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="disStat" value="" />
            <c:if test="${userType=='teacher' || userType=='parent'}">
            <c:set var="disStat" value="disabled" />
            </c:if>
			<tr><td colspan="2" class="alert-info">  
  					<h5 class="text-center"><a href="#" onClick="goToGoalStrategies(${starCaasppTypesId},${id})">Strategies to achieve goals - ${gtype}</a></h5></td></tr>  
  					 <c:forEach var = "i" begin = "0" end = "2"> 
           					<tr><td class="col-md-1"><c:out value = "${i+1}"/></td>  
 								<td>  
  								<select name="strategyId${i}" class="listStrateg" id="strategyId${i}" onChange="autoSaveStrategies(${i},${trimesterId},${starCaasppTypesId},${student.studentId})" required="required" ${disStat}>  
 											<option value="select" selected disabled>select Strategies</option> 
  													<c:forEach items="${listGoalStrategies}" var="ls">  
  													<c:set var="stat" value="" />   
    													 <c:if test="${listStudGoalStrategies[i].goalStrategies.goalStrategiesId eq ls.goalStrategiesId}">   
    													   <c:set var="stat" value="selected" />     
     													 </c:if>     
   														<option value="${ls.goalStrategiesId}" ${stat}>${ls.goalStrategiesDesc}</option>  
													</c:forEach>   
  								</select> 
  								</td></tr>  
							 
     				 </c:forEach>  
  					<tr><td></td>  
  					<td></td></tr> 

