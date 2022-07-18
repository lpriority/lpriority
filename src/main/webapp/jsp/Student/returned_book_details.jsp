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
        	<th width='12%' align='center' data-sort="int">S.No<i class="sort-indicator" aria-hidden="true"></i><span class="sr-only">Sort</span></th> 
			<th width='30%' align='center' data-sort="string">Book Name <i class="sort-indicator" aria-hidden="true"></i><span class="sr-only">Sort</span></th>
			<th width='28%' align='center' data-sort="string">Author <i class="sort-indicator" aria-hidden="true"></i><span class="sr-only">Sort</span></th>
			<th width='30%' align='center' data-sort="int">No.Of Pages <i class="sort-indicator" aria-hidden="true"></i><span class="sr-only">Sort</span></th>
            <th width='10%' align='center'></th>
        </tr>
        </thead> 	  
 	      <c:forEach items="${readRegMasterLt}" var="readRegMaster" varStatus="status">	
 	        <tbody>	
 	          <tr>
	 	    	 <td width='12%' align='center' class='txtStyle'>${status.count}<input type="hidden" name="titleId${status.index}" id="titleId${status.index}" value="${readRegMaster.titleId}" /></td>
			   	 <td width='30%' align='center' class='txtStyle' id="bookTitle${readRegMaster.titleId}">${readRegMaster.bookTitle}</td>
			   	 <td width='28%' align='center' class='txtStyle' id="author${readRegMaster.titleId}">${readRegMaster.author}</td>
			   	 <td width='30%' align='center' class='txtStyle' id="numberOfPages${readRegMaster.titleId}">${readRegMaster.numberOfPages}</td>	
			   	 <td width='10%' align='center' class='txtStyle' id=""><div class="button_green" onClick="reSubmitBook(${readRegMaster.titleId})"> Submit</div></td>	
			   	 <c:set var="userReivewId" scope="request" value="0"/>	
			   	 <c:set var="page" value="1" />
			   	<c:forEach items="${readRegMaster.readRegReviewLt}" var="readRegReview" varStatus="cnt">
					  <c:if test="${readRegReview.student.userRegistration.regId eq userReg.regId}">
				    	<input type="hidden" name="reviewId${status.index}" id="reviewId${status.index}" value="${readRegReview.reviewId}"  /> 
				    	<c:set var="userReivewId" scope="request" value="${readRegReview.reviewId}"/>	
				    </c:if>						    				    
			   	</c:forEach>
			   	<c:if test="${isNew == 'true'}">
		   			<input type="hidden" name="reviewId${status.index}" id="reviewId${status.index}" value="0"  /> 
			    	<c:set var="userReivewId" scope="request" value="0"/>
			    </c:if>
		   	  </tr>
		   	  <tr class="collapse">
		   	   	  	<td colspan="4"> <c:set var="rate" value="0" />	
			      			
			  				<layout-table id="stdReviewTbl_${readRegMaster.titleId}">
								   <layout-header> 
								       <layout-column width="30%" style="color:black;" align="center" class="label"><div class="fa fa-upload" title="Draw a picture or diagram that explains something important from the book. You can use the online draw tool or draw one on paper and take a photo of it. Upload your picture. " id="btAdd" name="btAdd" height="52" width="50" onclick="openPictureDialog(${readRegMaster.titleId},${page})" style="padding: 0.2px 10px;font-size: 23px;color: #273f4a;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);"></div></layout-column>
								       <layout-column width="28%" style="color:black;" align="center" class="label"><div class="ion-clipboard" title="Look in the Reading Register Library and see if the book you read is in there. See if another student or teacher create a quiz for the book and if they did take the quiz." id="btAdd" name="btAdd" height="52" width="50" onclick="openTakeQuizDialog(${readRegMaster.titleId})" style="padding: 0.2px 10px;font-size: 23px;color:#88523e;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);"></div></layout-column>
								       <layout-column width="28%" style="color:black;" align="center" class="label"><div class="fa fa-list-ol" title="Write 6 basic questions and 4 more challenging questions for other children to answer. For each question write 4 possible answers. Make sure one answer is correct. Identify which answer is correct." id="btAdd" name="btAdd" height="52" width="50" onclick="openCreateQuizDialog(${readRegMaster.titleId},${page})" style="padding: 0.2px 10px;font-size: 23px;color:#88523e;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);"></div></layout-column>
								       <layout-column width="30%" style="color:black;" align="center" class="label"><div class="ion-ios-mic" title="Use the retell audio tool and record yourself explaining what the book is about and what you learned." id="btAdd" name="btAdd" height="52" width="50" onclick="openRetellDialog(${readRegMaster.titleId},${page})" style="padding: 0.2px 10px;font-size: 28px;color:#3F51B5;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);"></div></layout-column>
								       <layout-column width="12%" style="color:black;" align="center" class="label"><div class="ion-chatbox-working" title="Tell what the book is about. Explain what you liked best about the book. Ask a question you have about the book. Answer a question someone else has about the book if there is one." id="btAdd" name="btAdd" height="52" width="50" onclick="openReviewDialog(${readRegMaster.titleId},${userReivewId},${status.index},${page})" style="padding: 0.2px 10px;font-size: 28px;color: #5d9c13;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);"></div></layout-column>
								   </layout-header>									   
								   	 <layout-row > 
								  	  <layout-column width="30%"></layout-column>
								  	  <layout-column width="56%">
								  	  <c:set var="ind" value="0" />					   
						         <c:set var="rateScore" value="0" />
						         <c:set var="reviewCount" value="0" />
								  <c:forEach items="${readRegMaster.readRegReviewLt}" var="readRegReview" varStatus="cnt">
							  	 <c:set var="appstat" value="approved" />
								  <c:forEach items="${readRegReview.readRegActivityScoreLt}" var="readRegActivityScore" varStatus="cnt">
									<c:set var="teacherComm" value="Teacher Comment : ${readRegMaster.teacherComment}" />
									<c:if test="${readRegMaster.approved eq 'approved'}">							
									<c:set var="teacherComm" value="Teacher Comment : ${readRegActivityScore.teacherComment}" />
									</c:if>
									<fmt:formatDate pattern="MM/dd/yyyy" var="submitDate" value="${readRegMaster.createDate}" />
									<c:set var="titleContent"
										value="
							Activity : ${readRegActivityScore.readRegActivity.actitvity}
		   					Book Title : ${readRegMaster.bookTitle}
		   				    Author : ${readRegMaster.author}
		   				    SubmitDate : ${submitDate}
		  				    Number of Pages : ${readRegMaster.numberOfPages}
		   				    Points earned : ${readRegActivityScore.pointsEarned} (pages of the book = ${readRegMaster.readRegPageRange.range} x Activity Value = ${readRegActivityScore.readRegActivity.activityValue } x Rubric value = ${readRegActivityScore.readRegRubric.score} )
		   				    Status : ${readRegActivityScore.approveStatus}
		   				    ${teacherComm}">
		   				    
		   				    </c:set>
						    			
									<c:if
										test="${readRegActivityScore.readRegActivity.activityId eq 1 && readRegActivityScore.approveStatus eq 'returned' && readRegActivityScore.student.studentId eq studentId}">
										<c:set var="ind" value="${ind+1}" />
										<div class="inline"
											id="imgDiv11_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}">
											<input type="hidden" id="titCon_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}" value="${titleContent}" />
											<i class="ion-chatbox-working" class="tooltip" style="padding: 0.2px 10px;font-size: 35px;color: #5d9c13;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);" title="${titleContent}" onclick="openReviewDialog(${readRegMaster.titleId},${userReivewId},${status.index},${page})"></i>
										</div>

									</c:if>

									<c:if test="${readRegActivityScore.readRegActivity.activityId eq 2 && readRegActivityScore.approveStatus eq 'returned' && readRegActivityScore.student.studentId eq studentId}">
										<c:set var="CreatedQuiz" scope="request" value="true" />
										<c:set var="ind" value="${ind+1}" />
										<div class="inline" id="imgDiv22_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}">
											<input type="hidden" id="titCon_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}" value="${titleContent}" />
											<i class="fa fa-list-ol" class="tooltip" style="padding: 0.2px 10px;font-size: 30px;color:#88523e;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);" title="${titleContent}" onclick="openCreateQuizDialog(${readRegMaster.titleId},${page})"></i>
									    </div>
									</c:if>
									<c:if test="${readRegActivityScore.readRegActivity.activityId eq 3 && readRegActivityScore.approveStatus eq 'returned' && readRegActivityScore.student.studentId eq studentId}">
										<c:set var="retold" scope="request" value="true" />
										<c:set var="ind" value="${ind+1}" />
										<div class="inline" id="imgDiv33_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}">
											<input type="hidden" id="titCon_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}" value="${titleContent}" />
											<i class="ion-ios-mic" class="tooltip" style="padding: 0.2px 10px;font-size: 35px;color:#3F51B5;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);" title="${titleContent}" onclick="openRetellDialog(${readRegMaster.titleId},${page})"></i>
									    </div>
									</c:if>
									<c:if test="${readRegActivityScore.readRegActivity.activityId eq 4 && readRegActivityScore.approveStatus eq 'returned' && readRegActivityScore.student.studentId eq studentId}">
										<c:set var="tookQuiz" scope="request" value="true" />
										<c:set var="ind" value="${ind+1}" />
										<div class="inline" id="imgDiv44_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}">
											<input type="hidden" id="titCon_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}" value="${titleContent}" />
											<i class="ion-clipboard" class="tooltip" style="padding: 0.2px 10px;font-size: 35px;color: #0078af;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);" title="${titleContent}" onclick="openTakeQuizDialog(${readRegMaster.titleId})"></i>
									     </div>
									</c:if>
									<c:if test="${readRegActivityScore.readRegActivity.activityId eq 5 && readRegActivityScore.approveStatus eq 'returned' && readRegActivityScore.student.studentId eq studentId}">
										<c:set var="uploadedPic" scope="request" value="true" />
										<c:set var="ind" value="${ind+1}" />
										<div class="inline" id="imgDiv55_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}">
											<input type="hidden" id="titCon_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}" value="${titleContent}" />
											<i class="fa fa-upload" class="tooltip" style="padding: 0.2px 10px;font-size: 30px;color: #273f4a;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);" title="${titleContent}" onclick="openPictureDialog(${readRegMaster.titleId},${page})"></i>
									     </div>
										
									</c:if>
                                   	<script>
									    $(function () {
									    	var rating = "<c:out value='${readRegReview.rating}'/>";
									    	var regId = "<c:out value='${readRegReview.student.userRegistration.regId}'/>";
									    	var titleId = "<c:out value='${readRegMaster.titleId}'/>";
									    	var readRegActScoreId="<c:out value='${readRegActivityScore.readRegActivityScoreId}' />";
			                                var activityId="<c:out value='${readRegActivityScore.readRegActivity.activityId}' />";
									    	var picPath = "profile_pic.jpg";
									    	checkUserProfileImage(titleId, regId, picPath,activityId);
									        var bookRating=bookRating+rating;
									    	
									    	/* var audioPath = "Reading_Register//"+titleId+"//Retell.wav";
									    	checkUserProfilePic(titleId, regId, audioPath, "retell_audio");
									    	
									    	setSelectedStarts(titleId, regId, rating); */
										});
									    </script>
									      <c:if test="${ind>0}">
									     <c:if test="${ind%6 eq 0}">
									     <br>
									     </c:if>
									     </c:if>
								</c:forEach>
								<c:if test="${(readRegReview.rating ne 0) or (not empty fn:trim(readRegReview.review))}" >
								<c:set var="rateScore" value="${rateScore + readRegReview.rating}" />
								<c:set var="reviewCount" value="${reviewCount+1}" />
								</c:if>
							</c:forEach>
							
								     </layout-column> 
								<layout-column width="30%">	
								<c:choose>
								<c:when test="${reviewCount eq 0}">
									<c:set var="rate" value="${rateScore}" />	
								</c:when>
								<c:otherwise>
								<c:set var="rate" value="${rateScore/reviewCount}" />	
								</c:otherwise>
								</c:choose>				 									
								</layout-column>
								<layout-column width="12%">
								<div class='rating-widget'>  
								<c:if test="${readRegMaster.approved eq 'returned'}">
								  <p style="text-align:justify">
								   
								   <font color="red">
								   Teacher Comment&nbsp;:&nbsp; </font><c:out value="${readRegMaster.teacherComment}" /></p>
								   </c:if>
								  
								   </div>
											  <%-- <div class='rating-stars'>
											    <ul id='stars_${readRegMaster.titleId}'>
											      <li class='star' data-value='1'>
											        <i class='fa fa-star fa-fw'></i>
											      </li>
											      <li class='star' data-value='2'>
											        <i class='fa fa-star fa-fw'></i>
											      </li>
											      <li class='star' data-value='3'>
											        <i class='fa fa-star fa-fw'></i>
											      </li>
											      <li class='star' data-value='4'>
											        <i class='fa fa-star fa-fw'></i>
											      </li>
											      <li class='star' title='WOW!!!' data-value='5'>
											        <i class='fa fa-star fa-fw'></i>
											      </li>
											    </ul>
											  </div>
											</div> --%>
												<script>
									    $(function () {
									    	var rating = "<c:out value='${rate}'/>";
									    	var regId = "<c:out value='${readRegReview.student.userRegistration.regId}'/>";
									    	var titleId = "<c:out value='${readRegMaster.titleId}'/>";
									    	var reviewCount="<c:out value='${reviewCount}'/>"
									    	setSelectedStars(titleId, rating);
										});
									    </script>
								</layout-column>
								   </layout-row>
							</layout-table>	
						
				   </td>
			  	 </tr>
		   	 </tbody>	
 	        </c:forEach> 	    
 	    </table>
 	      
 	
				
				