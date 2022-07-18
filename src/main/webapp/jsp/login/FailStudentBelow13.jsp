<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Student Signup Failed</title>
<link rel="stylesheet" href="resources/css/normalize.css">
<link rel="stylesheet" href="resources/css/registration-css.css">
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/common_special_effects.css" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<c:if test="${userReg == null}">
		<div align="center" valign="middle" style='padding-top: 1.5em;'>
			<table width="100%">
				<tr>
					<td width="37%" valign="middle" align="right"><span
						class="logoStyle1">Learning Priority</span></td>
					<td width="30%" valign="middle" align="left"><img
						src="images/common_images/LpLOGO.png" width="90" height="60"
						style=""></td>
				</tr>
				<tr>
					<td width="100%" valign="middle" align="right" colspan="2"
						style="padding-right: 12em;">
						<button
							class="stylish-button button--pipaluk button--inverted  button--round-s button--text-thick"
							style="float: right; min-width: 100px; max-width: 100px; background: #00e3ff; text-shadow: 0 1px 1px rgb(128, 128, 128); font-size: 11px;"
							onclick="loadPage('index.htm')">
							<i class="fa fa-home" aria-hidden="true" style="font-size: 18px;"></i>&nbsp;
							Go Home
						</button>
					</td>
				</tr>
			</table>
		</div>
	</c:if>
	<div class="form" style="margin-top: -1em;">
		<div id="ftui">
			<h1>Email Verification</h1>
			<div>
				<span>
					<p class="font-style">
						Parent is not yet registered with the application. 
						Your parent must register first before you can register.
					</p>
				</span>
			</div>
			<div>
				<span width="100%" height="20" align="center" valign="middle"><a
					href="index.htm" class="button_green">Home Page</a></span>
			</div>
		</div>
		<div id="blank"></div>
	</div>
	<script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
	<script src="resources/javascript/index.js"></script>
</body>
</html>