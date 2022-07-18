<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/regService.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />

<!-- <script src="/resources/javascript/notify/notify.js"></script> -->

</head>
<body>
 <form:form id="personalInfo" modelAttribute="userRegistration" action="updatePersonalInfo.htm" method="GET">
<table class="des" align="center" width="90%"><tr><td width="540" align="center"><div class="Divheads">
<table><tr><td class="headsColor"><font color="white">Personal Information</font></td></tr></table>
</div>
<div class="DivContents">
  	<table width="100%" align="center" valign="middle"><tr><td>&nbsp;</td></tr>
  	                            <c:set var="address" value="${fn:split(userRegistration.address.address, '##@##')}" />
                                    <tr>
                                        <td width="123" height="35" align="right" valign="middle"></td>
                                        <td width="125" height="30" align="right" valign="middle" class="tabtxt"><img src="images/Common/required.gif"> Title</td>
                                        <td width="400" height="30" align="center" valign="middle" class="text1">:</td>
                                        <td width="853" height="30" align="left" valign="middle" class="tabtxt">
                                            <select path="title" name="title" id="title" required="required" class="listmenu">
                                                <option value="select">select</option>
                                                <option value="Mr" ${'Mr' eq userRegistration.title ? 'selected="selected"' : ''}>Mr.</option>
                                                <option value="Mrs" ${'Mrs' eq userRegistration.title ? 'selected="selected"' : ''}>Mrs.</option>
                                                <option value="Ms" ${'Ms' eq userRegistration.title ? 'selected="selected"' : ''}>Ms.</option>
                                                <option value="Dr" ${'Dr' eq userRegistration.title ? 'selected="selected"' : ''}>Dr.</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="123" height="35" align="right" valign="middle"></td>
                                        <td width="225" height="30" align="right" valign="middle" class="tabtxt"><img src="images/Common/required.gif"> First Name </td>
                                        <td width="10" height="30" align="center" valign="middle" class="">:</td>
                                        <td width="853" height="30" align="left" valign="middle">
											<input class="textBox"class="textBox" path="firstName" id="firstName" name="firstName" type="text" value="${userRegistration.firstName}" required="required" onblur="checkForNonAlphabates('firstName')" />
										</td>
                                    </tr>
                                    <tr>
                                        <td width="123" height="35" align="right" valign="middle"></td>
                                        <td width="225" height="30" align="right" valign="middle" class="tabtxt"><img src="images/Common/required.gif"> Last Name </td>
                                        <td width="10" height="30" align="center" valign="middle" class="text1">:</td>
                                        <td width="853" height="30" align="left" valign="middle" class="tabtxt">
											<input class="textBox"path="lastName" id="lastName" name="lastName" type="text" value="${userRegistration.lastName}" required="required" onblur="checkForNonAlphabates('lastName')" />
										</td>
                                    </tr>
                                    <tr>
                                        <td width="123" height="35" align="right" valign="middle"></td>
                                        <td width="325" height="30" align="right" valign="middle" class="tabtxt"><img src="images/Common/required.gif"> Home Address1 </td>
                                        <td width="10" height="30" align="center" valign="middle" class="text1">:</td>
                                        <td width="853" height="30" align="left" valign="middle" class="tabtxt">
											<input class="textBox"class="textBox"path="address1" id="address1" name="address1" type="text" value="${address[0]}" maxlength="30" required="required"  />
										</td>
                                    </tr>
                                    <tr>
                                        <td width="123" height="35" align="right" valign="middle"></td>
                                        <td width="325" height="30" align="right" valign="middle" class="tabtxt"> Home Address2 </td>
                                        <td width="10" height="30" align="center" valign="middle" class="text1">:</td>
                                        <td width="853" height="30" align="left" valign="middle" class="tabtxt">
                                      	  <input class="textBox"class="textBox"path="address2" id="address2" name="address2" type="text" value="${address[1]}" maxlength="30"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="123" height="35" align="right" valign="middle"></td>
                                        <td width="125" height="30" align="right" valign="middle" class="tabtxt"><img src="images/Common/required.gif">Country</td>
                                        <td width="10" height="30" align="center" valign="middle" class="text1">:</td>
                                        <td width="853" height="30" align="left" valign="middle"><span class="tabtxt">
                                                <select path="address.states.country.countryId" id="countryId" name="countryId" required="required"  onChange="loadStates()" class="listmenu">
                                                   <option value="select">select</option>
                                                   <c:forEach items="${countryLt}" var="country">
													 <option value="${country.countryId}"  ${country.countryId eq userRegistration.address.states.country.countryId ? 'selected="selected"' : ''}>${country.country}</option>
													</c:forEach> 
                                                </select>
                                            </span></td>
                                    </tr>
                                    <tr>
                                        <td width="123" height="35" align="right" valign="middle"></td>
                                        <td width="125" height="30" align="right" valign="middle" class="tabtxt"><img src="images/Common/required.gif"> State</td>
                                        <td width="10" height="30" align="center" valign="middle" class="text1">:</td>
                                        <td width="853" height="30" align="left" valign="middle"><span class="tabtxt">
                                               <select path="userRegistration.address.states.stateId" id="stateId" name="stateId" required="required"  onChange="" class="listmenu">
                                                   <option value="select">select</option>
                                                   <c:forEach items="${statesLt}" var="state">
													 <option value="${state.stateId}"  ${state.stateId eq userRegistration.address.states.stateId ? 'selected="selected"' : ''}>${state.name}</option>
													</c:forEach> 
                                                </select>
                                            </span></td>
                                    </tr>
                                    <tr>
                                        <td width="123" height="35" align="right" valign="middle"></td>
                                        <td width="125" height="30" align="right" valign="middle" class="tabtxt"><img src="images/Common/required.gif"> City</td>
                                        <td width="10" height="30" align="center" valign="middle" class="text1">:</td>
                                        <td width="853" height="30" align="left" valign="middle" class="tabtxt">
                                       	 <input class="textBox"class="textBox"path="address.city" id="city" name="city" type="text" required="required"  value="${userRegistration.address.city}" />
                                        </td>
                                    </tr>

                                    <tr>
                                        <td width="123" height="35" align="right" valign="middle"></td>
                                        <td width="125" height="30" align="right" valign="middle" class="tabtxt"><img src="images/Common/required.gif"> Zip Code </td>
                                        <td width="10" height="30" align="center" valign="middle" class="text1">:</td>
                                        <td width="1053" height="30" align="left" valign="middle"><span class="tabtxt">
                                                <input class="textBox"class="textBox"path="address.zipcode" id="zipcode"  name="zipcode" type="text" required="required"  value="${userRegistration.address.zipcode}" maxlength="5"/>
                                            </span></td>
                                    </tr>
                                    <tr>
                                        <td width="123" height="35" align="right" valign="middle"></td>
                                        <td width="1853" height="30" align="right" valign="middle" class="tabtxt"><img src="images/Common/required.gif"> Your Email Address </td>
                                        <td width="10" height="30" align="center" valign="middle" class="text1">:</td>
                                        <td width="853" height="30" align="left" valign="middle"><span class="tabtxt">
                                                <input class="textBox"class="textBox"path="emailId"  id="emailId"  name="emailId" required="required"  style="background-color: #F5F5F5;" type="text" value="${userRegistration.emailId}" readonly="readonly"/>
                                            </span></td>
                                    </tr>
                                    <tr>
                                        <td width="123" height="35" align="right" valign="middle"></td>
                                        <td width="225" height="30" align="right" valign="middle" class="tabtxt"><img src="images/Common/required.gif"> Phone Number </td>
                                        <td width="10" height="30" align="center" valign="middle" class="text1">:</td>
                                        <td width="853" height="30" align="left" valign="middle"><span class="tabtxt">
                                                <input class="textBox"class="textBox"path="phonenumber" id="phonenumber" name="phonenumber" type="text" value="${userRegistration.phonenumber}" onblur="checkPhoneNumber('phonenumber')" />
                                            </span></td>
                                    </tr>
                            </table></div></td></tr>
                             <tr><td height="10" ><table width="100%"><tr>
	                                    <td width="35%" height="10" align="right" valign="middle"> 
	                                   		 <div class="button_green" align="right" onclick="updatePersonalInfo()">Submit Changes</div> 
	                                    </td>
	                                    <td width="30%" height="10" align="left" valign="middle" style="padding-left: 2em;">
	                                    	 <a href="personalInformation.htm" class="button_green">Clear</a> 
	                                    </td>
	                                    </tr></table>
	                                </td></tr>
	                          <tr><td height=10 colSpan=30></td></tr>
                            </table>
  </form:form>
</body>
</html>