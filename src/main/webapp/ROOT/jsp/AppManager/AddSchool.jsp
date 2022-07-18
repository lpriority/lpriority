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
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript"
	src="/dwr/interface/regService.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
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
          <div id="title" style="min-height:15px"> Add School</div>
          <div style="text-align: right; margin-top:-28px;"> <a href="displaySchools.htm" type="button" class="btn btn-sm btn-primary"> View Schools </a> </div>
        </div>
      </div>
      <div class="row tblbgcolor">
        <div class="col-md-2"></div>
        <div class="col-md-8"><br>
          <form:form id="schoolForm" modelAttribute='school' method="post" action="saveSchool.htm">
            <div class="col-md-12">
              <div class="form-group fheight">
                <form:label path="schoolName" class="control-label col-md-4">* School Name:</form:label>
                <div class="col-md-6">
                  <form:input path="schoolName" />
                  <font color="red">
                  <form:errors path="schoolName"></form:errors>
                  </font> </div>
              </div>
            </div>
            <div class="col-md-12">
              <div class="form-group fheight">
                <form:label path="schoolAbbr" class="control-label col-md-4">* School Abbr:</form:label>
                <div class="col-md-6">
                  <form:input path="schoolAbbr" />
                  <font color="red">
                  <form:errors path="schoolAbbr"></form:errors>
                  </font> </div>
              </div>
            </div>
            <div class="col-md-12">
              <div class="form-group fheight">
                <form:label path="schoolTypeId" class="control-label col-md-4"> * Type Of School:</form:label>
                <div class="col-md-6">
                  <form:select path="schoolTypeId">
                    <option value="select">Select School Type</option>
                    <c:forEach var="stList" items="${schoolTypeIds}">
                      <option value="${stList.schoolTypeId}"
								<c:if test="${schoolTypeId == stList.schoolTypeId}"> selected="Selected" </c:if>>${stList.schoolTypeName}</option>
                    </c:forEach>
                  </form:select>
                  <font color="red"><form:errors path="schoolTypeId"></form:errors></font> 
				</div>
              </div>
            </div>
            <div class="col-md-12">
              <div class="form-group fheight">
                <form:label path="schoolLevelId" class="control-label col-md-4">* Level Of School:</form:label>
                <div class="col-md-6">
                  <form:select id="schoolLevelId" path="schoolLevelId">
                    <option value="select">Select School Level</option>
                    <c:forEach var="slList" items="${schoolLevelIds}">
                      <option value="${slList.schoolLevelId}"
								<c:if test="${schoolLevelId == slList.schoolLevelId}"> selected="Selected" </c:if>>${slList.schoolLevelName}</option>
                    </c:forEach>
                  </form:select>
                  <font color="red">
                  <form:errors path="schoolLevelId"></form:errors>
                  </font> </div>
              </div>
            </div>
            <div class="col-md-12">
              <div class="form-group fheight">
                <form:label path="countryId" class="control-label col-md-4">* Country:</form:label>
                <div class="col-md-6">
                  <form:select id="countryId" path="countryId"
						onchange="loadStates(); return false;">
                    <option value="select">Select Country</option>
                    <c:forEach var="cList" items="${countryIds}">
                      <option value="${cList.countryId}"
								<c:if test="${countryId == cList.countryId}"> selected="Selected" </c:if>>${cList.country}</option>
                    </c:forEach>
                  </form:select>
                  <font color="red">
                  <form:errors path="countryId"></form:errors>
                  </font> </div>
              </div>
            </div>
            <div class="col-md-12">
              <div class="form-group fheight">
                <form:label path="stateId" class="control-label col-md-4">* State:</form:label>
                <div class="col-md-6">
                  <form:select id="stateId" path="stateId">
                    <option value="select">Select State</option>
                    <c:forEach var="sList" items="${stateIds}">
                      <option value="${sList.stateId}"
								<c:if test="${stateId == sList.stateId}"> selected="Selected" </c:if>>${sList.name}</option>
                    </c:forEach>
                  </form:select>
                  <font color="red">
                  <form:errors path="stateId"></form:errors>
                  </font> </div>
              </div>
            </div>
            <div class="col-md-12">
              <div class="form-group fheight">
                <form:label path="city" class="control-label col-md-4">*  City:</form:label>
                <div class="col-md-6">
                  <form:input path="city" />
                  <font color="red">
                  <form:errors
							path="city"></form:errors>
                  </font> </div>
              </div>
            </div>
            <div class="col-md-12">
              <div class="form-group fheight">
                <form:label path="phoneNumber" class="control-label col-md-4">* Phone Number:</form:label>
                <div class="col-md-6">
                  <form:input path="phoneNumber" />
                  <font color="red">
                  <form:errors path="phoneNumber"></form:errors>
                  </font> </div>
              </div>
            </div>
            <div class="col-md-12">
              <div class="form-group fheight">
                <form:label path="faxNumber" class="control-label col-md-4">* Fax Number:</form:label>
                <div class="col-md-6">
                  <form:input path="faxNumber" />
                  <font color="red">
                  <form:errors path="faxNumber"></form:errors>
                  </font> </div>
              </div>
            </div>
            <div class="col-md-12">
              <div class="form-group fheight">
                <form:label path="noOfStudents" class="control-label col-md-4">* No Of Students:</form:label>
                <div class="col-md-6">
                  <form:input path="noOfStudents" />
                  <font color="red">
                  <form:errors path="noOfStudents"></form:errors>
                  </font> </div>
              </div>
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
