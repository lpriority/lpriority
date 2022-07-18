<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:choose>
		  				<c:when test="${readRegMaster.approved eq 'approved'}">	
 <c:set var="rate" value="0" />	
 <c:set var="page" value="1" />
			        			
			  				<layout-table id="stdReviewTbl_${readRegMaster.titleId}">
			  				 <c:if test="${userReg.user.userType == 'student below 13' || userReg.user.userType == 'student above 13' }"> 
								   <layout-header> 
								       <layout-column width="30%" style="color:black;" align="center" class="label"><div class="fa fa-upload" title="Draw a picture or diagram that explains something important from the book. You can use the online draw tool or draw one on paper and take a photo of it. Upload your picture.&#013;Points Earned : 3" id="btAdd" name="btAdd" height="52" width="50" onclick="openPictureDialog(${readRegMaster.titleId},${page})" style="padding: 0.2px 10px;font-size: 23px;color: #273f4a;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);"></div></layout-column>
								       <layout-column width="28%" style="color:black;" align="center" class="label"><div class="ion-clipboard" title="Look in the Reading Register Library and see if the book you read is in there. See if another student or teacher create a quiz for the book and if they did take the quiz.&#013;Points Earned : 3" id="btAdd" name="btAdd" height="52" width="50" onclick="openTakeQuizDialog(${readRegMaster.titleId})" style="padding: 0.2px 10px;font-size: 23px;color:#88523e;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);"></div></layout-column>
								       <layout-column width="28%" style="color:black;" align="center" class="label"><div class="fa fa-list-ol" title="Write 6 basic questions and 4 more challenging questions for other children to answer. For each question write 4 possible answers. Make sure one answer is correct. Identify which answer is correct.&#013;Points Earned : 4" id="btAdd" name="btAdd" height="52" width="50" onclick="openCreateQuizDialog(${readRegMaster.titleId},${page})" style="padding: 0.2px 10px;font-size: 23px;color:#88523e;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);"></div></layout-column>
								       <layout-column width="30%" style="color:black;" align="center" class="label"><div class="ion-ios-mic" title="Use the retell audio tool and record yourself explaining what the book is about and what you learned.&#013;Points Earned : 2" id="btAdd" name="btAdd" height="52" width="50" onclick="openRetellDialog(${readRegMaster.titleId},${page})" style="padding: 0.2px 10px;font-size: 28px;color:#3F51B5;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);"></div></layout-column>
								       <layout-column width="12%" style="color:black;" align="center" class="label"><div class="ion-chatbox-working" title="Tell what the book is about. Explain what you liked best about the book. Ask a question you have about the book. Answer a question someone else has about the book if there is one.&#013;Points Earned : 5" id="btAdd" name="btAdd" height="52" width="50" onclick="openReviewDialog(${readRegMaster.titleId},${userReivewId},${status},${page})" style="padding: 0.2px 10px;font-size: 28px;color: #5d9c13;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);"></div></layout-column>
								   </layout-header>
								   </c:if>		
								   	 <layout-row > 
								  	  <layout-column width="30%"></layout-column>
								  	  <layout-column width="56%">
								  	  <c:set var="ind" value="0" />					   
						         <c:set var="rateScore" value="0" />
						         <c:set var="reviewCount" value="0" />
								  <c:forEach items="${readRegMaster.readRegReviewLt}" var="readRegReview" varStatus="cnt">
							  	 
								  <c:forEach items="${readRegReview.readRegActivityScoreLt}" var="readRegActivityScore" varStatus="cnt">
									
									
									<%-- <c:out value="${readRegActivityScore.readRegActivity.actitvity}" /> --%>
									<c:set var="studentName" value="Student Name : ${readRegActivityScore.student.userRegistration.firstName} ${readRegActivityScore.student.userRegistration.lastName}"></c:set>
									<fmt:formatDate pattern="MM/dd/yyyy" var="submitDate" value="${readRegMaster.createDate}" />
									<c:set var="titleContent"
										value="Activity : ${readRegActivityScore.readRegActivity.actitvity}
		   					Book Title : ${readRegMaster.bookTitle}
		   				    Author : ${readRegMaster.author}
		   				    SubmitDate : ${submitDate}
		  				    Number of Pages : ${readRegMaster.numberOfPages}
		   				    Points earned : ${readRegActivityScore.pointsEarned} (pages of the book = ${readRegMaster.readRegPageRange.range} x Activity Value = ${readRegActivityScore.readRegActivity.activityValue } x Rubric value = ${readRegActivityScore.readRegRubric.score} )"></c:set>
									<c:if
										test="${readRegActivityScore.readRegActivity.activityId eq 1 && not empty readRegActivityScore.readRegRubric.readRegRubricId && readRegActivityScore.approveStatus eq 'approved'}">
										<c:set var="ind" value="${ind+1}" />
										<div class="inline"
											id="imgDiv11_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}">
											<input type="hidden" id="titCon_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}" value="${titleContent}" />
											<img
												id="imgDiv1_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}"
												class="imgCls" style="display:none;padding: 0.2px 10px;width: 30px;height: 27px;border-radius: 50%;"
												onclick="changeIcon(1,${readRegMaster.titleId},${readRegReview.student.userRegistration.regId},${readRegActivityScore.readRegActivityScoreId})" />
											    <div id="iconDiv1_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}" class="fa fa-user-circle-o" aria-hidden="true" style="display:block;font-size: 30px;text-shadow: 0 1px 4px rgb(202, 202, 202);color:#00cae2;padding: 0.2px 10px;width: 30px;height: 27px;" onclick="changeIcon(1,${readRegMaster.titleId},${readRegReview.student.userRegistration.regId},${readRegActivityScore.readRegActivityScoreId})"></div>
										</div>

									</c:if>

									<c:if test="${readRegActivityScore.readRegActivity.activityId eq 2 && not empty readRegActivityScore.readRegRubric.readRegRubricId && readRegActivityScore.approveStatus eq 'approved'}">
										<c:set var="CreatedQuiz" scope="request" value="true" />
										<c:set var="ind" value="${ind+1}" />
										<div class="inline" id="imgDiv22_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}">
											<input type="hidden" id="titCon_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}" value="${titleContent}" />
											<img id="imgDiv2_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}" class="imgCls" style="display:none;border-radius: 50%;padding: 0.2px 10px;width: 30px;height: 27px;" onclick="changeIcon(2,${readRegMaster.titleId},${readRegReview.student.userRegistration.regId},${readRegActivityScore.readRegActivityScoreId})"/>
									        <div id="iconDiv2_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}" class="fa fa-user-circle-o" aria-hidden="true" style="display:block;font-size: 30px;text-shadow: 0 1px 4px rgb(202, 202, 202);color:#00cae2;padding: 0.2px 10px;width: 30px;height: 27px;" onclick="changeIcon(2,${readRegMaster.titleId},${readRegReview.student.userRegistration.regId},${readRegActivityScore.readRegActivityScoreId})"></div>
									    </div>
									</c:if>
									<c:if test="${readRegActivityScore.readRegActivity.activityId eq 3 && not empty readRegActivityScore.readRegRubric.readRegRubricId && readRegActivityScore.approveStatus eq 'approved'}">
										<c:set var="retold" scope="request" value="true" />
										<c:set var="ind" value="${ind+1}" />
										<div class="inline" id="imgDiv33_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}">
											<input type="hidden" id="titCon_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}" value="${titleContent}" />
											<img id="imgDiv3_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}" class="imgCls" style="display:none;border-radius: 50%;padding: 0.2px 10px;width: 30px;height: 27px;" onclick="changeIcon(3,${readRegMaster.titleId},${readRegReview.student.userRegistration.regId},${readRegActivityScore.readRegActivityScoreId})"/>
									        <div id="iconDiv3_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}" class="fa fa-user-circle-o" aria-hidden="true" style="display:block;font-size: 30px;text-shadow: 0 1px 4px rgb(202, 202, 202);color:#00cae2;padding: 0.2px 10px;width: 30px;height: 27px;" onclick="changeIcon(3,${readRegMaster.titleId},${readRegReview.student.userRegistration.regId},${readRegActivityScore.readRegActivityScoreId})"></div>
									    </div>
									</c:if>
									<c:if test="${readRegActivityScore.readRegActivity.activityId eq 4 && not empty readRegActivityScore.readRegRubric.readRegRubricId && readRegActivityScore.approveStatus eq 'approved'}">
										<c:set var="tookQuiz" scope="request" value="true" />
										<c:set var="ind" value="${ind+1}" />
										<div class="inline" id="imgDiv44_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}">
											<input type="hidden" id="titCon_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}" value="${titleContent}" />
											<img id="imgDiv4_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}" class="imgCls" style="display:none;border-radius: 50%;padding: 0.2px 10px;width: 30px;height: 27px;" onclick="changeIcon(4,${readRegMaster.titleId},${readRegReview.student.userRegistration.regId},${readRegActivityScore.readRegActivityScoreId})"/>
									        <div id="iconDiv4_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}" class="fa fa-user-circle-o" aria-hidden="true" style="display:block;font-size: 30px;text-shadow: 0 1px 4px rgb(202, 202, 202);color:#00cae2;padding: 0.2px 10px;width: 30px;height: 27px;" onclick="changeIcon(4,${readRegMaster.titleId},${readRegReview.student.userRegistration.regId},${readRegActivityScore.readRegActivityScoreId})"></div>
									     </div>
									</c:if>
									<c:if test="${readRegActivityScore.readRegActivity.activityId eq 5 && not empty readRegActivityScore.readRegRubric.readRegRubricId && readRegActivityScore.approveStatus eq 'approved'}">
										<c:set var="uploadedPic" scope="request" value="true" />
										<c:set var="ind" value="${ind+1}" />
										<div class="inline" id="imgDiv55_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}">
											<input type="hidden" id="titCon_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}" value="${titleContent}" />
											<img id="imgDiv5_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}" class="imgCls" style="display:none;border-radius: 50%;padding: 0.2px 10px;width: 30px;height: 27px;" onclick="changeIcon(5,${readRegMaster.titleId},${readRegReview.student.userRegistration.regId},${readRegActivityScore.readRegActivityScoreId})"/>
									         <div id="iconDiv5_${readRegMaster.titleId}_${readRegReview.student.userRegistration.regId}" class="fa fa-user-circle-o" aria-hidden="true" style="display:block;font-size: 30px;text-shadow: 0 1px 4px rgb(202, 202, 202);color:#00cae2;padding: 0.2px 10px;width: 30px;height: 27px;" onclick="changeIcon(5,${readRegMaster.titleId},${readRegReview.student.userRegistration.regId},${readRegActivityScore.readRegActivityScoreId})"></div>
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
								<c:if test="${readRegReview.rating ne 0}" >
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
											  <div class='rating-stars'>
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
											</div>
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
							</c:when>
							</c:choose>
					