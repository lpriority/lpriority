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
		    <tr><td width="100%" class=""><h1 style="margin:0 0 10px;margin-top:-0.3em;margin-bottom:0.6em;">LP Tools</h1></td></tr>
			<tr><td width="100%"  valign="middle" align="left" style="padding-left:2em;font-size:13.8px;color:#000000;text-shadow:0 1px 2px rgb(154, 215, 222);">
				<p style="line-height: 1.38; margin-top: 0pt; margin-bottom: 0pt;text-align:justify;color:#002440;" dir="ltr"><span style="font-style:italic;font-size: 13.8px; color:#0f2d3b; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Learning Priority (LP) builds digital tools to assist learners, families, teachers, schools, and districts maximize learning time spent on educational activities. LP tools are developed to assist in basic literacy development as well as 21st century learning practices and skills. LP works directly with teachers, learners, schools, and districts to design and implement new custom-made tools that direct digital potentials to tap into digital potential for analog tasks. LP tools reach toward innovative 21st century collaboration and knowledge construction unparalleled in analog or face to face methods. </span><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;"><br class="kix-line-break" /></span><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;"><br class="kix-line-break" /></span><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">Current Learning Priority digital tools include:</span></p>
				<p><span style="background-color: transparent; color: black; text-shadow: 0 1px 2px rgb(172, 182, 183);font-size: 17px; font-weight: bold; white-space: pre-wrap;">Literacy Development</span></p>
				<p><span style="background-color: transparent; font-size: 14.6667px; font-weight: bold; white-space: pre-wrap;color:#715700;text-shadow:0 0.1px 1px rgb(201, 197, 197);">&nbsp;&nbsp;&nbsp;&nbsp;Reading Comprehension and Higher Literacy Development</span></p>
				<ul style="font-weight:600;padding-left:3em;font-size:13.6px;text-align:left;color:#002a3d;margin-bottom:-0.2em;">
					<li><p style="line-height: 1.38; margin-top: 0pt; margin-bottom: 0pt;" dir="ltr"><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">Reading dashboard</span></p></li>
					<li><p style="line-height: 1.38; margin-top: 0pt; margin-bottom: 0pt;" dir="ltr"><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">Reading suite</span></p></li>
					<li><p style="line-height: 1.38; margin-top: 0pt; margin-bottom: 0pt;" dir="ltr"><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">Learning indicator</span></p></li>
					<li><p style="line-height: 1.38; margin-top: 0pt; margin-bottom: 0pt;" dir="ltr"><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">Fluency assessments (teacher-scored, self-scored, peer-scored, item analysis of word errors)</span></p></li>
					<li><p style="line-height: 1.38; margin-top: 0pt; margin-bottom: 0pt;" dir="ltr"><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">Fluency passage practice</span></p></li>
					<li><p style="line-height: 1.38; margin-top: 0pt; margin-bottom: 0pt;" dir="ltr"><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">Word error word work</span></p></li>
					<li><p style="line-height: 1.38; margin-top: 0pt; margin-bottom: 0pt;" dir="ltr"><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">Comprehension assessments</span></p></li>
				</ul>
				<p><span style="background-color: transparent; font-size: 14.6667px; font-weight: bold; white-space: pre-wrap;color:#715700;text-shadow:0 0.1px 1px rgb(201, 197, 197);">&nbsp;&nbsp;&nbsp;&nbsp;Early Literacy </span></p>
				<ul style="font-weight:600;padding-left:3em;font-size:13.6px;text-align:left;color:#002a3d;margin-bottom:-0.2em;">
					<li><p style="line-height: 1.38; margin-top: 0pt; margin-bottom: 0pt;" dir="ltr"><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">Letter identification assessments and student work</span></p></li>
					<li><p style="line-height: 1.38; margin-top: 0pt; margin-bottom: 0pt;" dir="ltr"><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">Letter sound identification assessments and student work</span></p></li>
					<li><p style="line-height: 1.38; margin-top: 0pt; margin-bottom: 0pt;" dir="ltr"><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">Sight word identification assessments and student work</span></p></li>
					<li><p style="line-height: 1.38; margin-top: 0pt; margin-bottom: 0pt;" dir="ltr"><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">Basic phonics skills test and student work</span></p></li>
				</ul>
				<p><span style="background-color: transparent; font-size: 14.6667px; font-weight: bold; white-space: pre-wrap;color:#715700;text-shadow:0 0.1px 1px rgb(201, 197, 197);">&nbsp;&nbsp;&nbsp;&nbsp;Mathematics</span></p>
				<ul style="font-weight:600;padding-left:3em;font-size:13.6px;text-align:left;color:#002a3d;margin-bottom:-0.2em;">
					<li><p style="line-height: 1.38; margin-top: 0pt; margin-bottom: 0pt;" dir="ltr"><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">Math assessments (fractions, decimals, percentages)</span></p></li>
					<li><p style="line-height: 1.38; margin-top: 0pt; margin-bottom: 0pt;" dir="ltr"><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">Math dashboard</span></p></li>
					<li><p style="line-height: 1.38; margin-top: 0pt; margin-bottom: 0pt;" dir="ltr"><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">Gear Game</span></p></li>
				</ul>
				<p><span style="background-color: transparent; color: black; text-shadow: 0 1px 2px rgb(172, 182, 183);font-size: 17px; font-weight: bold; white-space: pre-wrap;">21st Century Learning Practices and Skills</span></p>
				<ul style="font-weight:600;padding-left:3em;font-size:13.6px;text-align:left;color:#002a3d;margin-bottom:-0.2em;">
					<li><p style="line-height: 1.38; margin-top: 0pt; margin-bottom: 0pt;" dir="ltr"><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">Performance tasks (individual and group formats)</span></p></li>
					<li><p style="line-height: 1.38; margin-top: 0pt; margin-bottom: 0pt;" dir="ltr"><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">Learning indicator</span></p></li>
					<li><p style="line-height: 1.38; margin-top: 0pt; margin-bottom: 0pt;" dir="ltr"><span style="font-size: 13.8px; color:#002440; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">STEM100 unit/lesson design template</span></p></li>
				</ul>
		</td></tr>
	</table>
</div>
</body>
</html>