<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add News</title>
<script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/jquery.validation/1.15.0/jquery.validate.min.js"></script>
<script src="https://cdn.jsdelivr.net/jquery.validation/1.15.0/additional-methods.min.js"></script>
<script type="text/javascript">
function validate() {
    var url = document.getElementById("urlLink").value;
    var pattern = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;
    if (!pattern.test(url)) {   
        alert("Please enter valid URL!");
        return false;
    } 
}
</script>
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
          <div id="title" style="min-height:15px"> Add/Edit News </div>
          <div style="text-align: right; margin-top:-28px;"> <a href="goToLPNewsPage.htm" type="button" class="btn btn-sm btn-primary"> View LP News </a> </div>
        </div>
      </div>
      <div class="row tblbgcolor">
        <div class="col-md-2"></div>
        <div class="col-md-8"><br>
          <form:form id="lpNewsObj" modelAttribute="lpNewsObj"
			method="post" action="saveLPNews.htm" onsubmit="return validate()">
            <div class="col-md-12 text-center">
              <form:hidden path="lpNewsId" />
              <form:hidden path="status" />
            </div>
            <div class="col-md-12">
              <div class="form-group fheight">
                <form:label path="title" class="control-label col-md-4">* Title:</form:label>
                <div class="col-md-6">
                  <form:input path="title" id="title" required="required"/>
                </div>
              </div>
            </div>
            <div class="col-md-12">
              <div class="form-group fheight">
                <form:label path="urlLink"  class="control-label col-md-4">* URL Link:</form:label>
                <div class="col-md-6">
                  <form:input path="urlLink" id="urlLink" required="required" />
                </div>
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
