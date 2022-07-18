<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Welcome to The Learning Priority Inc | LP Tools</title>
	<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/common_special_effects.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="resources/css/registration-css.css"> 
	<style type="text/css">
	.font-style{
	color:#715700;
	text-shadow:0 0.1px 1px rgb(201, 197, 197);
	}
	</style>
</head>
<body>
 <c:if test="${userReg == null}">
<div align="center" valign="middle" style=''>
		<table width="100%">
			<tr><td width="37%"  valign="middle" align="right">
				<span class="logoStyle1">Learning Priority</span>
			</td>
			<td  width="30%"  valign="middle" align="left">
				<img src="images/common_images/LpLOGO.png" width="90" height="60" style="">
			</td></tr>
			<tr>
				<td valign="middle" colspan="2" align="right" style="padding-right:2em;">
				    <table width="20%">
					   <tr><td>
					      <button class="stylish-button button--pipaluk button--inverted  button--round-s button--text-thick" style="float:right;min-width: 100px;max-width: 100px;background: #00e3ff;text-shadow:0 1px 1px rgb(128, 128, 128);font-size: 11px;" onclick="loadPage('aboutUs.htm')"><i class="fa fa-arrow-circle-left" aria-hidden="true" style="font-size: 18px;"></i>&nbsp; Back</button>
					    </td><td>
					   	 <button class="stylish-button button--pipaluk button--inverted  button--round-s button--text-thick" style="float:right;min-width: 100px;max-width: 100px;background: #00e3ff;text-shadow:0 1px 1px rgb(128, 128, 128);font-size: 11px;" onclick="loadPage('index.htm')"><i class="fa fa-home" aria-hidden="true" style="font-size: 18px;"></i>&nbsp; Go Home</button>
					  </td></tr> 
				  </table>
				</td>
			</tr>
		</table>
	</div>
</c:if>	 
<div width="100%" align="center">
	<table class="form" width="100%" align="center" style="max-width:550px;padding:30px;margin-top:-1em;">
		    <tr><td width="100%" class=""><h1 style="margin:0 0 10px;margin-top:-0.3em;margin-bottom:0.6em;">LP News</h1></td></tr>
			<tr>
			<td width="100%"  valign="middle" align="left" style="padding-left:2em;font-size:13.8px;color:#000000;text-shadow:0 1px 2px rgb(154, 215, 222);">
				<ul style="font-weight:600;padding-left:3em;font-size:13.6px;text-align:left;color:#002a3d;margin-bottom:-0.2em;">
				<c:forEach items="${lpNews}" var="lpn">
						<li><p style="line-height: 1.38; margin-top: 0pt; margin-bottom: 0pt;" dir="ltr"><span style="font-size: 20.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;"><a href="${lpn.urlLink }" target="_blank" style="color: #005b84;text-shadow: 0 1px 1px rgb(191, 191, 191);font-size: 15.5px;"> ${lpn.title }</a></span></p></li>
				</c:forEach>
			</ul>
		</td></tr>
	</table>
</div>
</body>
</html>