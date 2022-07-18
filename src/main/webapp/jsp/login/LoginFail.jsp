<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Welcome to The Learning Priority Inc | Login Fail</title>
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/common_special_effects.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" href="resources/css/registration-css.css">
<style type="text/css">
.font-style {
	color: #715700;
	text-shadow: 0 0.1px 1px rgb(201, 197, 197);
}

.lp-tools {
	color: #b000cd;
	font-size: 20px;
	text-shadow: 0 1px 2px rgb(34, 148, 162);
}

.starburst2 {
	display: block;
	width: 2.8em;
	height: 2.8em;
	background: #d2a418;
	-webkit-transform: rotate(-45deg);
	-moz-transform: rotate(-45deg);
	rotation: -45deg;
	position: relative;
	text-align: center;
	text-decoration: none;
	color: white;
	font-size: 17px;
	font-weight: bold;
	font-family: Arial, sans-serif;
	text-shadow: 0 1px 2px rgb(17, 56, 61);
	-moz-transition: -moz-transform 0.3s ease;
	-webkit-transition: -webkit-transform 0.3s ease;
	transition: transform 0.3s ease;
	margin-top: -2em;
}

.starburst2 span {
	display: block;
	width: 2.8em;
	height: 2.8em;
	background: #d2a418;
	-webkit-transform: rotate(22.5deg);
	-moz-transform: rotate(22.5deg);
	rotation: 22.5deg;
}

.starburst2:hover,.starburst2:hover span {
	background: #eeb814;
	color: #fff;
	text-shadow: 0 1px 2px rgb(4, 26, 29);
}

.starburst2:hover {
	-webkit-transform: rotate(315deg);
	-moz-transform: rotate(315deg);
	rotation: 315deg;
}
</style>
</head>
<body>
	<c:if test="${userReg == null}">
		<div align="center" valign="middle" style=''>
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
						style="padding-right: 12em; padding-bottom: 2em"></td>
				</tr>
			</table>
		</div>
	</c:if>
	<div width="100%" align="center">
		<table class="form" width="100%" align="center"
			style="max-width: 550px; padding: 30px; margin-top: -1em;">
			<tr>
				<td width="100%" class=""><h1 style="margin: 0 0 10px;">Login
						Fail</h1></td>
			</tr>
			<tr>
				<td width="100%" valign="middle" align="left"
					style="padding-left: 2em; font-size: 13.8px; color: #000000; font-style: italic; text-shadow: 0 1px 2px rgb(154, 215, 222);">
					<h4 style="text-align: justify; color: #002440;">
						<p>
						<center>
							<p>
								You Entered Invalid Username or Password. Please Click Below
								Button <br /> <br /> to Login Again.
							</p>
						</center>
						</p>

					</h4>
				</td>
			</tr>
			<tr>
				<td><button
						class="stylish-button button--pipaluk button--inverted  button--round-s button--text-thick"
						style="float: right; min-width: 100px; max-width: 100px; background: #00e3ff; text-shadow: 0 1px 1px rgb(128, 128, 128); font-size: 11px;"
						onclick="loadPage('index.htm')">
						<i class="fa fa-home" aria-hidden="true" style="font-size: 18px;"></i>&nbsp;
						Go Home
					</button></td>
			</tr>
		</table>

	</div>
	<script src="resources/javascript/index.js"></script>
</body>
</html>