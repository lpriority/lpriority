<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Welcome to The Learning Priority Inc | About Us</title>
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
	.lp-tools{
		color:#b000cd;
		font-size:20px;
		text-shadow:0 1px 2px rgb(34, 148, 162);
	}
	
	.starburst2 {
	display:block;
	width:2.8em;
	height:2.8em;
	background:#d2a418;
	-webkit-transform:rotate(-45deg);
	-moz-transform:rotate(-45deg);
	rotation:-45deg;
	position:relative;
	text-align:center;
	text-decoration:none;
	color:white;
	font-size:17px;
	font-weight:bold;
	font-family:Arial, sans-serif;
	text-shadow:0 1px 2px rgb(17, 56, 61);
	-moz-transition: -moz-transform 0.3s ease;
	-webkit-transition: -webkit-transform 0.3s ease;
	transition: transform 0.3s ease;
	margin-top: -2em;
}
.starburst2 span {
	display:block;
	width:2.8em;
	height:2.8em;
	background:#d2a418;
	-webkit-transform:rotate(22.5deg);
	-moz-transform:rotate(22.5deg);
	rotation:22.5deg;
}
.starburst2:hover,
.starburst2:hover span {
	background:#eeb814;
	color:#fff;
	text-shadow:0 1px 2px rgb(4, 26, 29);
}
.starburst2:hover {
	-webkit-transform:rotate(315deg);
	-moz-transform:rotate(315deg);
	rotation:315deg;
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
				<td width="100%" valign="middle" align="right" colspan="2" style="padding-right:12em;">
				    <button class="stylish-button button--pipaluk button--inverted  button--round-s button--text-thick" style="float:right;min-width: 100px;max-width: 100px;background: #00e3ff;text-shadow:0 1px 1px rgb(128, 128, 128);font-size: 11px;" onclick="loadPage('index.htm')"><i class="fa fa-home" aria-hidden="true" style="font-size: 18px;"></i>&nbsp; Go Home</button>
				</td>
			</tr>
		</table>
	</div>
</c:if>	
<div width="100%" align="center">
	<table class="form" width="100%" align="center" style="max-width:550px;padding:30px;margin-top:-1em;">
		    <tr><td width="100%" class=""><h1 style="margin:0 0 10px;">About Us</h1></td></tr>
			<tr><td width="100%"  valign="middle" align="left" style="padding-left:2em;font-size:13.8px;color:#000000;font-style:italic;text-shadow:0 1px 2px rgb(154, 215, 222);">
					<h4 style="text-align:justify;color:#002440;"><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Learning Priority was born from the experience and vision of a teacher, coach, leader with years of experience in classrooms and as superintendent of school Districts. From its origin, it developed its design and function through interactions with classroom teachers and experts in a variety of fields.</p>
					<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;LP has developed technological solutions that assist teachers in processes that help them know and connect with learners. LP tools seek to utilize technology to save time that teachers can utilize to make human connections with learners. LP tools also offer learners opportunities to collaborate with each other as well as connecting with their teacher, parents, and peers in ways that amplify the social construction of learning. Learning Priority offers school Districts, teachers, parents, and learners technological solutions that have been developed in close cooperation with real world contexts. LP also offers school Districts the opportunity to develop custom solutions for technology integration.</p> 
					<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;LP helps makes learning the priority in school organizations and cultures. As a technology company, we seek to enable these key ideas related to integrating technology in schools and classrooms;</p>
					</h4>
					</td></tr>
					<tr><td width="37%"  valign="middle" align="right">
				<div>
					<ul style="font-weight:500;padding-left:5em;font-size:13.6px;text-align:left;color:#002a3d;">
					<h4>
					<li>Technology can help teachers <span class="font-style">individualize instruction</span>.</li>
					<li>Technology can <span class="font-style">free up time</span> for teachers to individualize instruction.</li>
					<li><span class="font-style">Human interaction</span> with technology is key.</li>
					<li><span class="font-style">Balancing content and process learning</span> is key</li>
					<li><span class="font-style">Performance tasks</span> are the future tools for developing collaboration and communication</li>
					<li>Technology can <span class="font-style">save money</span> in school systems</li>
					<li>Technology can <span class="font-style">improve school culture</span></li>
					<li>The electronic school can <span class="font-style">extend learning</span> to whenever and wherever students are</li></h4>
					</ul>
			</div>
		</td></tr>
		<tr><td width="50%" align="left"><a href="#" onclick="loadPage('lpTools.htm')" class="starburst2"><span><span><span><span>LP Tools</span></span></span></span></a>
			<!-- <a href="#" onclick="loadPage('lpTools.htm')" style="padding-left:5px;"><i class="fa fa-gift" aria-hidden="true" style="font-size:26px;font-weight:bold;color:#f0d900;"></i>&nbsp;&nbsp;&nbsp;<span class="lp-tools"> LP Tools !!</span></a> -->
		</td>
		<td width="50%" aling="right"><a href="#" onclick="loadPage('lpNews.htm')" class="starburst2"><span><span><span><span> News</span></span></span></span></a>

		</td>
		</tr>
	</table>
	 
</div>
<script src="resources/javascript/index.js"></script>
</body>
</html>