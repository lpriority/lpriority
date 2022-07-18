<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/admin/add_edit_remove_sl_schl.js"></script>
<title>Upload Stem Strands</title>
<script type="text/javascript">

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
          <div id="title" style="min-height:15px"> Upload ${LP_STEM_TAB} Strands </div>
          <div style="text-align: right; margin-top:-28px;"> <a href="gotoDashboard.htm" type="button" class="btn btn-sm btn-primary"> Home </a> </div>
        </div>
      </div>
      <div class="col-md-12 text-center" id= "returnMessage" style="color: red"> <font color="blue">
        <label style="visibility:visible;">${status}</label>
        </font> </div>
      <div class="row tblbgcolor">
        <div class="col-md-2"></div>
        <div class="col-md-8">
          <form method="POST" enctype="multipart/form-data" action="uploadStrandsFile.htm" id="strandsUploadForm">
            <div class="col-md-12" style="padding-bottom:15px;">
              <div class="form-group">
                <div class="col-md-4">${LP_STEM_TAB} Area</div>
                <div class="col-md-6">
                  <select name="areaId" style="width:120px;" class="listmenu" id="areaId"  onChange="">
                    <option value="select">Select ${LP_STEM_TAB} Area</option>
                    <c:forEach items="${stemAreas}" var="sa">
                      <option value="${sa.stemAreaId}">${sa.stemArea}</option>
                    </c:forEach>
                  </select>
                </div>
              </div>
            </div>
            <div class="col-md-12" style="padding-bottom:15px;">
              <div class="form-group">
                <div class="col-md-4">Master Grades</div>
                <div class="col-md-6">
                  <select name="masterId" style="width:120px;" class="listmenu" id="masterId">
                    <option value="select">Select Grade</option>
                    <c:forEach items="${masterGrades}" var="mg">
                      <option value="${mg.masterGradesId}">${mg.gradeName}</option>
                    </c:forEach>
                  </select>
                </div>
              </div>
            </div>
            <div class="col-md-12" style="padding-bottom:15px;">
              <div class="form-group">
                <div class="col-md-4">State</div>
                <div class="col-md-6">
                  <select name="stateId" style="width:120px;" class="listmenu" id="stateId">
                    <option value="select">Select State</option>
                    <c:forEach items="${schoolStates}" var="ss">
                      <option value="${ss.states.stateId}">${ss.states.name}</option>
                    </c:forEach>
                  </select>
                </div>
              </div>
            </div>
            <div class="col-md-12" style="padding-bottom:15px;">
              <div class="form-group">
                <div class="col-md-4">File to upload:</div>
                <div class="col-md-6">
                  <input type="file" name="file" id="file" required>
                </div>
              </div>
            </div>
            <div class="col-md-12">
              <div class="col-md-3"></div>
              <div class="col-md-4">
                <button type="submit" class="btn btn-sm btn-primary" onclick="return checkXLSFileExt();">Upload ${LP_STEM_TAB} Strands</button>
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