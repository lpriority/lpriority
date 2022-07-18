<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />
<%@ page import=" java.util.Date"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Student Signup</title>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/regService.js"></script>
<script type="text/javascript" src="resources/javascript/Student/student.js"></script>
<link rel="stylesheet" href="resources/css/registration-css.css">
<link rel="stylesheet" href="resources/css/common_registration.css" >
<style type="text/css">
input, textarea{
  display: inline;
  width: auto;
}

select{
  display: inline;
  width: auto;
  background:rgb(250, 251, 251);
  color:black;
}

.star{
color:red;
}

.content-body{
    width: 80%;
    padding-top:2em;
    margin: 0em 0em 0em 5em;
}

.content-center{
	margin-left: 20em;
    padding-top: 0.5em;
    float: left;
}
</style>
</head>
<body onload="">
	<fmt:formatDate var="year" value="${now}" pattern="yyyy" />
	<!-- Content center body -->
	<div class="content-body">
		<!-- Content center start -->
		<div class="content-center">
			<table width="100%" height="784" border="0" cellpadding="0"
				cellspacing="0">
				<c:if test="${userReg.regId <= 0 }">
				<tr>
					<td height="33" colspan="6" align="left" valign="middle"><img
						src="images/Login/logoBlackUp.gif" width="222" height="21"
						name="logoblack" vspace="0" hspace="7" border="0"
						alt="The Learning Priority" /></td>
				</tr>
				</c:if>
				<tr>
					<td width="20" height="739" rowspan="8"></td>
					<td width="745" colspan="5"></td>
				</tr>
				<tr>
					<td width="35" height="82" rowspan="2"></td>
					<td width="450" height="41" align="right" valign="middle"
						bgcolor=""></td>
					<td width="35" rowspan="2"></td>
				</tr>
				<tr>
					<td height="521" colspan="7" align="center" valign="top" style="max-width:650px;"><table
							width="90%" height="521" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td height="484" align="left" valign="top" class="form"><form:form action="SaveStudentDetails2.htm" method="POST" onSubmit="return above13StudentRegVal2()">
										<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 14px;">
											<tbody>
												<tr>
													<td  valign="middle" width="100%" colspan="3"
														align="center" style="text-shadow: 0 1px 4px rgb(0, 0, 0);padding-top:3em;">
														<h1>Tell us more about yourself</h1>
													</td>
												</tr>
												<tr>
													<td  valign="middle" width="30%" align="right"
														height="19">&nbsp;</td>
													<td valign="middle" width="5%" align="center" height="19">&nbsp;</td>
													<td  valign="middle" width="70%" align="left"
														height="19">&nbsp;</td>
												</tr>
												<tr>
													<td class="subject" valign="middle" width="30%"
														align="right">&nbsp;</td>
													<td valign="middle" width="5%" align="center" ></td>
													<td valign="middle" width="70%"  align="left">&nbsp;</td>
												</tr>

												<tr>
													<td  valign="middle" width="30%" align="right"><span
														class="star">*</span> Gender </td>
													<td valign="middle" width="5%" align="center" >:</td>
													<td  valign="middle" width="70%"
														 align="left"><input class="radio"
														value="male" checked="checked" type="radio" name="gender"
														id="gender" /> <span >Male</span>
														&nbsp;&nbsp; <input class="radio" value="female"
														type="radio" name="gender" id="gender" /> <span
														>Female </span></td>
												</tr>
												<tr>
													<td class="subject" valign="middle" width="30%"
														align="right">&nbsp;</td>
													<td valign="middle" width="5%" align="center" ></td>
													<td valign="middle" width="70%"  align="left">&nbsp;</td>
												</tr>
												<tr>
													<td  valign="middle" width="30%" align="right">
														<span class="star">*</span> Birth Date 
													</td>
													<td valign="middle" width="5%" align="center" >:</td>
													<td valign="middle" width="70%"  align="left">
														<table border="0" cellpadding="0" cellspacing="2">
															<tr>
																<c:choose>
																	<c:when test="${UserType eq 'student below 13' || UserType eq 'admin' }">
																		<td>
																			<select name="year" id="year">
																				<option value="select">Select Year</option>																			
																				<c:forEach begin="4" end="13" var="i">
																					<option>${year-i}</option>																				
																				</c:forEach>
																			</select>
																		</td>
																	</c:when>
																	<c:when test="${UserType eq 'student above 13' || UserType eq 'admin' }">
																		<td>
																			<select name="year" id="year">
																				<option value="select">Select Year</option>
																				<c:forEach begin="13" end="26" var="i">
																					<option>${year-i}</option>																				
																				</c:forEach>
																			</select>
																		</td>
																	</c:when>
																
																</c:choose>
																<td class="space-between-select"><select name="month" id="month">
																		<option value="select">Select Month</option>
																		<option value="01">Jan</option>
																		<option value="02">Feb</option>
																		<option value="03">Mar</option>
																		<option value="04">Apr</option>
																		<option value="05">May</option>
																		<option value="06">Jun</option>
																		<option value="07">Jul</option>
																		<option value="08">Aug</option>
																		<option value="09">Sept</option>
																		<option value="10">Oct</option>
																		<option value="11">Nov</option>
																		<option value="12">Dec</option>
																</select></td>
																<td class="space-between-select">
																<select name="date" id="date" style="">
																		<option value="select">Select Date</option>
																		<c:set var="displayValue"></c:set>
																		<c:forEach begin="0" end="30" varStatus="count">
																			<c:set var="displayValue" value="${count.count}"></c:set>
																			<c:if test="${count.count < 10}">
																				<c:set var="displayValue" value="0${count.count}"></c:set>
																			</c:if>
																			<option id="${displayValue}" value="${count.count}">${displayValue}</option>
																		</c:forEach>
																</select></td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td class="subject" valign="middle" width="30%"
														align="right">&nbsp;</td>
													<td valign="middle" width="5%" align="center" ></td>
													<td valign="middle" width="70%"  align="left">&nbsp;</td>
												</tr>

												<tr>
													<td  valign="top" width="35%" align="right">Current
														Subject Areas </td>
													<td valign="middle" width="5%" align="center" >:</td>
													<td  valign="middle" align="left"  width="70%">
														<table name="personal_interest" width="100%">
															<tbody>
																<c:set value="0" var="i"></c:set>
																<c:forEach items="${classList}" var="cList">
																	<c:if test="${i == 0}">
																		<tr>
																	</c:if>
																	<c:set value="${i+1}" var="i"></c:set>
																		<td width="30%"><input type="checkbox"
																			name="classId" id="classId"
																			value="${cList.studentClass.classId}" /><span >${cList.studentClass.className}</span></td>																		
																	<c:if test="${i == 3}">
																		</tr>
																		<c:set value="0" var="i"></c:set>
																	</c:if>
																</c:forEach>
															</tbody>
														</table>
													</td>
												</tr>
												<tr>
													<td  valign="top" width="30%" align="right">&nbsp;</td>
													<td valign="middle" width="5%" align="center" ></td>
													<td  valign="middle" width="70%" align="left">&nbsp;</td>
												</tr>
												<tr>
													<td  valign="middle" width="30%" align="right">
														Other </td>
													<td valign="middle" width="5%" align="center" >:</td>
													<td  valign="middle" width="70%" align="left"><input
														name="textother" id="textother" size="20" type="text" /></td>
												</tr>
												<tr>
													<td  valign="middle" width="30%" align="right"
														height="8">&nbsp;</td>
													<td valign="middle" width="5%" align="center" ></td>
													<td  valign="middle" width="70%" align="left"
														height="8">&nbsp;</td>
												</tr>
												<tr>
													<td  valign="middle" width="30%" align="right">
														Other </td>
													<td valign="middle" width="5%" align="center" >:</td>
													<td  valign="middle" width="70%" align="left"><input
														name="textother2" id="textother2" size="20" type="text" /></td>
												</tr>
												<tr>
													<td  valign="middle" width="30%" align="right">&nbsp;</td>
													<td valign="middle" width="5%" align="center" ></td>
													<td  valign="middle" width="70%" align="left">&nbsp;</td>
												</tr>
												<tr>
													<td  valign="middle" width="30%" align="right">
														Other </td>
													<td valign="middle" width="5%" align="center" >:</td>
													<td  valign="middle" width="70%" align="left"><input
														name="textother3" id="textother3" size="20" type="text" /></td>
												</tr>
												<tr>
													<td  valign="middle" width="30%" align="right">&nbsp;</td>
													<td valign="middle" width="5%" align="center" ></td>
													<td  valign="middle" width="70%" align="left">&nbsp;</td>
												</tr>
												<tr>
													<td  valign="middle" width="30%" align="right">
														Other </td>
													<td valign="middle" width="5%" align="center" >:</td>
													<td  valign="middle" width="70%" align="left"><input
														name="textother4" id="textother4" size="20" type="text" /></td>
												</tr>
												<tr>
													<td valign="middle" width="30%" align="right"><table
															width="100%" height="100%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td width="40%" align="center" valign="middle"></td>
																<td width="60%" align="left" valign="middle"
																	style="padding-left: 8px"></td>
															</tr>
														</table></td>
													<td valign="middle" width="5%" align="center" ></td>
													<td  valign="middle" width="70%" align="right"><table
															width="100%" height="115" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td width="95%" align="right" valign="middle">
																	<button class="for-back-ward-button-style button-block-inline" >Next</button> </td>
																<td width="5%" align="right" valign="middle"></td>
															</tr>
														</table></td>
												</tr>
											</tbody> 
										</table>
									</form:form></td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td height="47" colspan="5"></td>
				</tr>
				<tr>
					<td colspan="5"></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>