<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@include file="Layout.jsp"%>
<div class="container-fluid">
  <div class="row">
    <div class="col-md-3">
      <%@include file="Menuinfo.jsp"%>
    </div>
    <div class="col-md-9">
      <div class="row">
        <div class="col-md-12 sub-title title-border">
          <div id="title" style="min-height:15px"> Add School Administrator</div>
          <div style="text-align: right; margin-top:-28px;"> <a href="displayUserRegistrations.htm" type="button" class="btn btn-sm btn-primary"> View School Administrators </a> </div>
        </div>
      </div>
      <div class="row tblbgcolor">
        <div class="col-md-2"></div>
        <div class="col-md-8"><br>
          <form:form id="userRegistrationForm" modelAttribute="userRegistration"
		method="post" action="saveUserRegistration.htm">
            <div class="col-md-12 text-center">
              <c:set var="disabled" value="false">
              </c:set>
              <c:if test="${userRegistration.status != 'new' && userRegistration.status != null}">
                <c:set var="disabled" value="true">
                </c:set>
                <label style="color: red"><h5>You can't edit Registered users</h5></label>
                <br>
                <br>
              </c:if>
            </div>
            <div class="col-md-12">
              <div class="form-group fheight">
                <form:label path="schoolId" class="control-label col-md-4">* Schools</form:label>
                <div class="col-md-6">
                  <form:select path="schoolId" required="required" disabled="${disabled}" >
                    <option value="">Select School</option>
                    <c:forEach var="sList" items="${schoolList}">
                      <option value="${sList.schoolId}"
								<c:if test="${schoolId eq sList.schoolId}"> selected="Selected" </c:if>>${sList.schoolName}</option>
                    </c:forEach>
                  </form:select>
                  <font color="red">
                  <form:errors path="schoolId"></form:errors>
                  </font> </div>
              </div>
            </div>
            <div class="col-md-12">
              <div class="form-group fheight">
                <form:label path="emailId" class="control-label col-md-4">* Email Id:</form:label>
                <div class="col-md-6">
                  <form:input path="emailId"  pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" required="required" 
						title="Enter valid Email Id" disabled="${disabled}"/>
                  <font color="red">
                  <form:errors path="emailId"></form:errors>
                  </font> </div>
              </div>
            </div>
            <div class="col-md-12">
              <div class="form-group fheight">
                <form:label path="firstName" class="control-label col-md-4">* First Name:</form:label>
                <div class="col-md-6">
                  <form:input path="firstName" pattern="[A-Za-z\s]+" required="required" 
					title="Should not Contain special charecters and numbers" disabled="${disabled}"/>
                  <font color="red">
                  <form:errors path="firstName"></form:errors>
                  </font> </div>
              </div>
            </div>
            <div class="col-md-12">
              <div class="form-group fheight">
                <form:label path="lastName" class="control-label col-md-4">* Last Name:</form:label>
                <div class="col-md-6">
                  <form:input path="lastName" pattern="[A-Za-z\s]+" required="required"  
					title="Should not Contain special charecters and numbers" disabled="${disabled}"/>
                  <font color="red">
                  <form:errors path="lastName"></form:errors>
                  </font> </div>
              </div>
            </div>
            <div class="col-md-12 text-center">
              <form:hidden path="regId" />
            </div>
            <div class="col-md-12">
              <div class="col-md-3"></div>
              <div class="col-md-4">
                <button type="submit" class="btn btn-sm btn-primary">SUBMIT CHANGES</button>
                <br>
                <br>
              </div>
            </div>
          </form:form>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
