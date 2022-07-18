<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Student Signup</title>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/regService.js"></script>
<link rel="stylesheet" href="resources/css/normalize.css">
<link rel="stylesheet" href="resources/css/registration-css.css">
<link rel="stylesheet" href="resources/css/common_registration.css" >
<style type="text/css">
input[type="text"],input[type="email"],input[type="password"]{
	width:95%;
}
</style>
</head>
<body>
	<c:if test="${userReg.regId <= 0 }"><div align="center" style="padding-top: 20px">
		<img src="images/Login/logoBlackUp.gif" width="222" height="21"
			name="logoblack" vspace="0" hspace="7" border="0"
			alt="The Learning Priority" />
	</div>
	</c:if>
	<div class="form" style="max-width:550px;">
		<ul class="tab-group">
			<li class="tab active"><a href="#signup">Sign Up</a></li>
			<li class="tab"><a href="#lp" style="font-size:16px;padding:8px;">with Learning Priority</a></li>
		</ul>

		<div class="tab-content">
			<div id="signup">
				<h1>Student Sign Up Form</h1>

				<form:form name="StudentReg1" modelAttribute="studentUserReg" method="POST" action="SaveStudentDetails1.htm">
					<div class="top-row">
						<div class="field-wrap">
							<label> First Name<span class="req">*</span>
							</label>
							<form:input path="firstName" required="required"
								autocomplete="off"/>
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
					<div class="top-row">
						<div class="field-wrap">
							<form:select id="gradeId" path="gradeId" required="required">
								<option value="">select grade</option>
								<c:forEach items="${schoolgrades}" var="ul">	
									<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>	
								</c:forEach>	
							</form:select>
						</div>
						<c:choose>
							<c:when test="${userReg.regId > 0 }">
								<div class="field-wrap">
									<form:select path="emailId" required="required">
										<form:option value="">Select Student</form:option>
										<c:forEach var="tList" items="${studentlist}">
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
					</div>
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
					<div align="right">
						 <button class="for-back-ward-button-style button-block-inline">Next</button> 
					</div>		
				</form:form>
			</div>
			<div id="blank">
			</div>
		</div>
		<!-- tab-content -->
	</div>
	<!-- /form -->
	<script src="resources/javascript/jquery/jquery.min.js"></script>
	<script src="resources/javascript/index.js"></script>
</body>
</html>