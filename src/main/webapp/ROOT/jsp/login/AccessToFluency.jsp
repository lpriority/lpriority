<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Welcome to The Learning Priority Inc | Access To Fluency</title>
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_special_effects.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resources/css/registration-css.css">
</head>
<body>
<c:if test="${userReg == null}">
  <div align="center" valign="middle" style=''>
    <table width="100%">
      <tr>
        <td width="37%"  valign="middle" align="right"><span class="logoStyle1">Learning Priority</span></td>
        <td  width="30%"  valign="middle" align="left"><img src="images/common_images/LpLOGO.png" width="90" height="60" style=""></td>
      </tr>
      <tr>
        <td width="100%" valign="middle" align="right" colspan="2" style="padding-right:12em;"><button class="stylish-button button--pipaluk button--inverted  button--round-s button--text-thick" style="float:right;min-width: 100px;max-width: 100px;background: #00e3ff;text-shadow:0 1px 1px rgb(128, 128, 128);font-size: 11px;" onclick="loadPage('index.htm')"><i class="fa fa-home" aria-hidden="true" style="font-size: 18px;"></i>&nbsp; Go Home</button></td>
      </tr>
    </table>
  </div>
</c:if>
<div class="form teacherguide readingregister">
<div class="row">
  <div class="col-sm-12 text-right" style="margin-top:-30px;"> <a class="urllink" href="download.htm?fileName=access to fluencys.pptx" download><i class="fa fa-download" aria-hidden="true" style="font-size: 17px;color:#8e1400;"><span style="font-size: 13px;color:#8e1400;padding:5px;font-weight:bold;">Download</span></i></a> </div>
</div>
<div class="row">
  <div class="col-sm-12 text-center"><span class="mainhead">Access To Fluency</span></div>
</div>
<div class="row">
  <div class="col-sm-12">
    <div class="text-center"><img src="images/Aboutus/Fluencys/Picture1.PNG"></div>
  </div>
  <div class="col-sm-12">
    <div class="text-center"><img src="images/Aboutus/Fluencys/Picture2.PNG"></div>
  </div>
  <div class="col-sm-12">
    <div class="text-center"><img src="images/Aboutus/Fluencys/Picture3.PNG"></div>
  </div>
  <div class="col-sm-12">
    <div class="text-center"><img src="images/Aboutus/Fluencys/Picture4.PNG"></div>
  </div>
  <div class="col-sm-12">
    <div class="text-center"><img src="images/Aboutus/Fluencys/Picture5.PNG"></div>
  </div>
  <div class="col-sm-12">
    <div class="text-center"><img src="images/Aboutus/Fluencys/Picture6.PNG"></div>
  </div>
</div>
<script src="resources/javascript/index.js"></script>
</body>
</html>