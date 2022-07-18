<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/admin/add_edit_remove_sl_schl.js"></script>
<title>Insert title here</title>
<script type="text/javascript">
$(document).ready(function () {
	$('#returnMessage').fadeIn();
 	$('#returnMessage').fadeOut(4000);
});


</script>
</head>
<body>
<%@include file="../AppManager/Layout.jsp"%>
<div class="container-fluid">
  <div class="row">
    <div class="col-md-3">
      <%@include file="../AppManager/Menuinfo.jsp"%>
    </div>
    <div class="col-md-9">
      <div class="row">
        <div class="col-md-12 sub-title title-border">
          <div id="title" style="min-height:15px"> Bulk Register Users</div>
          <div style="text-align: right; margin-top:-28px;"> <a href="gotoDashboard.htm" type="button" class="btn btn-sm btn-primary"> Home </a> </div>
        </div>
      </div>
      <div class="col-md-12 text-center" id= "returnMessage" style="color: red"> <font color="blue">
        <label style="visibility:visible;">${status}</label>
        </font> </div>
      <div class="row tblbgcolor">
        <div class="col-md-2"></div>
        <div class="col-md-8"><br>
          <form method="POST" enctype="multipart/form-data" action="upload.htm">
            <div class="col-md-12">
              <div class="form-group">
                <Label class="control-label col-md-4">* Type of user :</label>
                <div class="col-md-6">
                  <div class="row">
                    <div class="col-md-2">
                      <input type="radio" id="teacher" name="userType" value="teacher" required >
                    </div>
                    <div class="col-md-10">
                      <label for="teacher" class="radio-label-design">Teacher</label>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-2">
                      <input type="radio" id="student" name="userType" value="student">
                    </div>
                    <div class="col-md-10">
                      <label for="student" class="radio-label-design">Student</label>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-2">
                      <input type="radio" id="existing" name="userType" value="existing">
                    </div>
                    <div class="col-md-10">
                      <label for="existing" class="radio-label-design">Upload Class Rosters </label>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-md-12" style="padding:15px 0px;">
              <div class="form-group">
                <div class="col-md-4">* File to upload:</div>
                <div class="col-md-6">
                  <input type="file" name="file" id="file" required>
                </div>
              </div>
            </div>
            <div class="col-md-12">
              <div class="col-md-3"></div>
              <div class="col-md-4">
                <button type="submit" class="btn btn-sm btn-primary" onclick="return checkFileExt();">Register the users!</button>
                <br>
                <br>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
