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
<title>Legend Upload</title>
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
          <div id="title" style="min-height:15px"> Star Scores </div>
          <div style="text-align: right; margin-top:-28px;"> <a href="gotoDashboard.htm" type="button" class="btn btn-sm btn-primary"> Home </a> </div>
        </div>
      </div>
      <div class="row tblbgcolor">
        <div class="col-md-2"></div>
        <div class="col-md-8">
          <form method="POST" enctype="multipart/form-data" action="starUploadFile.htm" id="StarUploadForm">
            <div class="col-md-12" style="padding-top:15px;padding-bottom:15px;">
              <div class="form-group">
                <div class="col-md-4">Trimester</div>
                <div class="col-md-6">
                  <select name="trimesterId" style="width:120px;" class="listmenu" id="trimesterId">
                    <option value="select">Select Trimester</option>
                    <c:forEach items="${trimesters}" var="tm">
                      <option value="${tm.trimesterId}">${tm.trimesterName}</option>
                    </c:forEach>
                  </select>
                </div>
              </div>
            </div>
            <div class="col-md-12" style="padding-bottom:15px;">
              <div class="form-group">
                <div class="col-md-4">CAASPP Types</div>
                <div class="col-md-6">
                  <select name="caasppId" style="width:120px;" class="listmenu" id="caasppId">
                    <option disabled="disabled">Select CAASPP Types</option>
                    <c:forEach items="${cAASPPTypes}" var="ct">
                      <option value="${ct.caasppTypesId}">${ct.caasppType}</option>
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
                <button type="submit" class="btn btn-sm btn-primary" onclick="return checkStarXLSFileExt();">Upload Star Score</button>
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