  <%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
.inline { 
    display: inline-block; 
   
    }
   .circlebase{
    border-radius:50%;
    background:yellow;
    border:3px;
    }

</style>
<script>
function setSelectedStars(titleId, val){
	$('#stars_'+titleId+'  li').each(function (i) {
		  if(i < val)
	       $(this).addClass('selected');
	 });
}

</script>
  <table class="jAccordionTable des" width="85%" data-page-size="6" data-page-navigation=".pagination" data-limit-navigation="5">
        <thead class="Divheads" >
        <tr height="40"> 
        	<th width='12%' align='center' onclick="sortByColumn('createDate', '${sortBy}', '${sortingOrder}', ${pageId})">Date Created <i class="sort-indicator" aria-hidden="true"></i><span class="sr-only">Sort</span></th> 
			<th width='30%' align='center' onclick="sortByColumn('bookTitle', '${sortBy}', '${sortingOrder}', ${pageId})">Book Name <i class="sort-indicator" aria-hidden="true"></i><span class="sr-only">Sort</span></th>
			<th width='28%' align='center' onclick="sortByColumn('author', '${sortBy}', '${sortingOrder}', ${pageId})">Author <i class="sort-indicator" aria-hidden="true"></i><span class="sr-only">Sort</span></th>
			<th width='30%' align='center' onclick="sortByColumn('numberOfPages', '${sortBy}', '${sortingOrder}', ${pageId})">No.Of Pages <i class="sort-indicator" aria-hidden="true"></i><span class="sr-only">Sort</span></th>
        </tr>
        </thead> 	  
 	      <c:forEach items="${readRegMasterLt}" var="readRegMaster" varStatus="status" >	
 	        <tbody>	
 	          <tr onclick="getActivities(${readRegMaster.titleId},${status.index} )">
	 	    	 <td width='12%' align='center' class='txtStyle'>	<fmt:formatDate pattern="MM/dd/yyyy" var="createdDate" value="${readRegMaster.createDate}" />${createdDate}<input type="hidden" name="titleId${status.index}" id="titleId${status.index}" value="${readRegMaster.titleId}" /></td>
			   	 <td width='30%' align='center' class='txtStyle' id="bookTitle${readRegMaster.titleId}">${readRegMaster.bookTitle}</td>
			   	 <td width='28%' align='center' class='txtStyle' id="author${readRegMaster.titleId}">${readRegMaster.author}</td>
			   	 <td width='30%' align='center' class='txtStyle' id="numberOfPages${readRegMaster.titleId}">${readRegMaster.numberOfPages}</td>	
			   	 <c:set var="userReivewId" scope="request" value="0"/>				   	
		   	  </tr>
		   	  <tr class="collapse">
		   	  	<td colspan="4"> <div id="details:${readRegMaster.titleId}"></div></td>
		   	  </tr>	  	 
		   	 </tbody>	
 	        </c:forEach> 	    
 	    </table>
 	    <div id="rr-loading-div-background" class="loading-div-background" style="display:none;">
			<div class="loader"></div>
			<div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;">
				<br><br><br><br><br><br><br>Loading....
			</div>
		</div>
 	   
 	
				
				