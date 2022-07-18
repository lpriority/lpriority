<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta charset="UTF-8">
<title>Teacher Signup</title>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/regService.js"></script>
<link rel="stylesheet" href="resources/css/normalize.css">
<link rel="stylesheet" href="resources/css/registration-css.css">
<link rel="stylesheet" href="resources/css/common_registration.css" >
<script type="text/javascript" src="resources/javascript/imageloadfunctions.js"></script>
<style type="text/css">
input[type="text"],input[type="email"],input[type="password"]{
	width:95%;
}
</style>
</head>
<body>
	<c:if test="${userReg.regId <= 0 }">
		<div align="center" style="padding-top: 20px">
			<img src="images/Login/logoBlackUp.gif" width="222" height="21"
				name="logoblack" vspace="0" hspace="7" border="0"
				alt="The Learning Priority" />
		</div>
	</c:if>
	<div class="form" style="max-width:550px;"> 
		<div class="tab-content">
			<div id="teacherSignUpForm">
				<h1>Teacher Sign Up Form</h1>

				<form:form name="persInfo" modelAttribute="teacherReg" action="teacherRegVal1.htm"
					method="POST">
					<div class="top-row">
						<div class="field-wrap">
							<form:select path="title"
								required="required" style="width:70%;">
								<form:option value="" selected="selected"> Select Title </form:option>
								<form:option value="Mr">Mr.</form:option>
								<form:option value="Mrs">Mrs.</form:option>
								<form:option value="Ms">Ms.</form:option>
								<form:option value="Dr">Dr.</form:option>
							</form:select>
							<font color="red"><form:errors path="title" /></font>
						</div>
						<div class="field-wrap">
							<label> First Name<span class="req">*</span>
							</label>
							<form:input path="firstName" required="required"
								autocomplete="off" />
						</div>

						<div class="field-wrap">
							<label> Last Name<span class="req">*</span>
							</label>
							<form:input path="lastName" required="required" 
								autocomplete="off" />
						</div>
					</div>
					<div class="top-row">
						<div class="field-wrap">
							<label> Home Address 1<span class="req">*</span>
							</label>
							<form:input path="address1" required="required" autocomplete="off" />
						</div>

						<div class="field-wrap">
							<label> Home Address 2 </label>
							<form:input path="address2"	autocomplete="off" /> 
						</div>
					</div>
					<div class="top-row">
						<div class="field-wrap">
							<form:select path="countryId"
								onChange="loadStates();" required="required">
								<form:option value="">Select Country</form:option>
								<c:forEach var="cList" items="${countryIds}">
									<option value="${cList.countryId}"
										<c:if test="${countryId == cList.countryId}"> selected="Selected" </c:if>>${cList.country}
									</option>
								</c:forEach>
							</form:select>
						</div>
						<div class="field-wrap">
							<form:select path="stateId" required="required">
								<form:option value="">Select State</form:option>
								<c:forEach var="sList" items="${stateIds}">
									<option value="${sList.stateId}"
										<c:if test="${stateId == sList.stateId}"> selected="Selected" </c:if>>${sList.name}
									</option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="top-row">
						<div class="field-wrap">
							<label> City<span class="req">*</span>
							</label>
							<form:input path="city" required="required" autocomplete="off" />
						</div>
						<div class="field-wrap">
							<label> Zipcode<span class="req">*</span>
							</label>
							<form:input path="zipcode" pattern="([0-9]{5})" maxlength="5" required="required" autocomplete="off"  />
						</div>
					</div>
					<c:choose>
						<c:when test="${userReg.regId > 0 }">
							<div class="field-wrap">
								<form:select path="emailId" required="required" style="width:45%;">
									<form:option value="">Select Teacher</form:option>
									<c:forEach var="tList" items="${teacherMails}">
										<option value="${tList.emailId}"
											<c:if test="${emailId == tList.emailId}"> selected="Selected" </c:if>>${fn:trim(tList.emailId)}
										</option>
									</c:forEach>
								</form:select>
							</div>
						</c:when>
						<c:otherwise>
							<div class="field-wrap">
								<form:input path="emailId" readonly="true"   />
							</div>
						</c:otherwise>
					</c:choose>
					<div class="top-row">
						<div class="field-wrap">
							<label> Create Password<span class="req">*</span>
							</label>
							<form:password path="password" required="required" autocomplete="off" />
						</div>
						<div class="field-wrap">
							<label> Re-Enter Password<span class="req">*</span>
							</label> 
							<form:password path="confirmPassword" 
								required="required" autocomplete="off" onblur="check(this)" />
						</div>
					</div>
					<div class="top-row">
						<div class="field-wrap">
							<form:select
								path="securityQuestionId" id="parentsecurityid" required="required">
								<option value="">Select</option>

								<c:forEach items="${securityQuestions}" var="qlist">

									<option value="${qlist.securityQuestionId}">${qlist.question}</option>

								</c:forEach>
							</form:select>
						</div>
						<div class="field-wrap">
							<label> Answer<span class="req">*</span>
							</label>
							<form:input path="answer" id="answer" required="required"/>
						</div>
					</div>
					<div class="top-row">
						<div class="field-wrap">
							<label> Qualification<span class="req">*</span>
							</label>
							<form:input path="qualification" required="required" 
								 autocomplete="off" />
						</div>
						<div class="field-wrap">
							<label> Phone Number<span class="req">*</span>
							</label>
							<form:input path="phonenumber" required="required"
								pattern="[0-9]+" maxlength="10" autocomplete="off" />
						</div>
					</div>
					<div align="right">
						 <button class="for-back-ward-button-style button-block-inline">Next</button>
					</div>		
				</form:form>

			</div>

			<div id="gradeLevelInfo">
				

			</div>

		</div>
		<!-- tab-content -->

	</div>
	<!-- /form -->
	<script src="resources/javascript/jquery/jquery.min.js"></script>
	<script src="resources/javascript/index.js"></script>
</body>
</html>