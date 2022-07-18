<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form:form modelAttribute="employee" method="POST">
  <div id="tabContent" class="tab-content">
    <div id="basic-details" class="tab-pane fade in active">
      <fieldset>
        <t:input path="firstNames" label="First Name(s)"/>
        <t:input path="surname" label ="Surname or Contractor" required="true"/>
        <t:input path="internalId" label="Person ID"/>
        <t:textarea path="address" /> 
        <t:input path="email" label="Email Address"/>
        <t:input path="phone" label="Phone Number"/>
        <t:enumSelect path="gender" required="true" emptyOption="${null==employee.gender ? 'true' : 'false'}" />
        <t:input path="dateOfBirth" label="Date of Birth" placeholder="dd/mm/yyyy"/>
      </fieldset>
    </div><!-- basic-details end -->
    <div id="job-details" class="tab-pane fade">
      <fieldset>                           
        <t:input path="jobTitle" label="Job Title"/>
        <t:enumRadio path="proprietorStatus" label="Proprietor"/>
        
      </fieldset>
    </div><!-- job-details end -->
   
  </div> <!-- tab content -->
  <div class="form-actions">
    <div class="pull-right">
      <a class="btn">Cancel</a>
      <button class="btn btn-primary"><i class="icon-user icon-white"></i> Save Person</button>
    </div>
  </div>
</form:form>       
</body>
</html>