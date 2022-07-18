<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add School Days</title>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/admin/add_edit_remove_sl_schl.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<table  width="100%">
		<tr>
			<td vAlign=top width="100%" colSpan=3 align="center">
				<table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
					<tr><td colspan="" width="100%"> 
						 <table width="100%" class="title-pad heading-up" border="0">
							<tr>
								<td class="sub-title title-border" height="40" align="left" >Add School Days</td>
							</tr>
						</table>
					</td></tr>
					<tr><td width="100%" height=0 vAlign=top colSpan=2 align="center">
					<!-- Content center start -->		  
					<div class="content-box space-between" style="">
					<form:form name="adminAddSchoolDaysForm"
									modelAttribute="SchoolDays" action="">
						<table class="des" border=0><tr><td><div class="Divheads">
							<table width="100%" border="0" >
									<tr align="left">
										<td width="10">&nbsp;</td>
										<td width="150">Day of the Week</td>
										<td width="100" align="center">Add</td>
										<td width="100" align="center">Remove</td>
										<td width="100">&nbsp;</td>
									</tr></table></div><div class="DivContents"><table style="text-transform: capitalize;">
									<tr><td>&nbsp;</td></tr>
									<%
										int i = 0;
									%>

									<c:forEach items="${days}" var="masD">
										<%
											String dis = "";
													String dis1 = "";
													String check = "";

													i++;
													dis1 = "disabled";
										%>
										<c:forEach items="${schooldays}" var="schD">

											<c:choose>
												<c:when test="${masD.day==schD.days.day}">

													<input type='hidden' id="day:<%=i%>"
														value=${schD.days.dayId } />
													<%
														dis = "disabled";
																			dis1 = "";
																			check = "checked";
													%>

												</c:when>

											</c:choose>
										</c:forEach>



										<tr align="left">
										<td  width="10">&nbsp;</td>
											<td class="header" width="150" align="center" height="35" style="color:#064E58;"><c:out value="${masD.day}" /></td>
											<td width="100" align="center"><input type="checkbox" class="checkbox-design" name="add:<%=i%>"
												id="add:<%=i%>" value="${masD.dayId}" <%=dis%> <%=check%>
												onclick="addDay(this,<%=i%>)" /><label for="add:<%=i%>" class="checkbox-label-design"></label></td>
											<td width="100" align="center"><input type="checkbox" class="checkbox-design"
												name="remove:<%=i%>" id="remove:<%=i%>"
												value="${masD.dayId}" <%=dis1%>
												onclick="removeDay(this,<%=i%>)" /><label for="remove:<%=i%>" class="checkbox-label-design"></label></td>
											 <td width="100" height="" align="left" colspan="">
							                   <div id="resultDiv<%=i%>" class="status-message"  style="font-size:14px;"></div>
						                     </td>	
										</tr>
										<%
											dis = "";
													dis1 = "";
													check = "";
										%>

									</c:forEach>
								
					</table></div></td></tr></table></form:form>
                  </div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>