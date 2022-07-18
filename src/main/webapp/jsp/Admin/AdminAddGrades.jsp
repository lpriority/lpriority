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
<title>Add Grades</title>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/admin/add_edit_remove_sl_schl.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<table width="100%">
		<tr>
			<td vAlign=top width="100%" colSpan=3 align=middle>
				<table width="100%" border=0 align="center" cellPadding=0
					cellSpacing=0>
					<tr><td colspan="" width="100%"> 
						 <table width="100%" class="title-pad heading-up" border="0">
							<tr>
								<td class="sub-title title-border" height="40" align="left" >Add Grades</td>
							</tr>
						</table>
					</td></tr>
					<tr>
						<td height=2 colSpan=2></td>
					</tr>
					<tr>
						<td height=0 vAlign=top colSpan=2 align=left>
				   <!-- Content center start -->		  
				   <div class="" style="padding-top:2em;padding-bottom:3em;">
				   <form:form name="adminAddGradesForm" modelAttribute="grade" action="">
				   <table class="des" border=0 align="center"><tr><td><div class="Divheads">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								
									<tr align="left">
										<td width="150" height="30" align="center">Grade</td>
										<td width="100" height="30" align="center">Add</td>
										<td width="100" height="30" align="center">Remove</td>
										<td width="100">&nbsp;</td>
									</tr>
									</table></div><div class="DivContents"><table>
									
									<%
										int i = 0;
									%>
									
									<c:forEach items="${mastergrades}" var="masG">
										<%
										
										    String dis = "";
											String dis1 = "";
											String check = "";
											
									
											i++;
											dis1 = "disabled";
										%>
										<c:forEach items="${schoolgrades}" var="schG">
											
											<c:choose>
												<c:when test="${masG.gradeName==schG.masterGrades.gradeName}">
													
											         <input type='hidden' id="grade:<%=i%>" value=${schG.gradeId} />
													<%
													    dis="disabled";
														dis1 = "";
											            check = "checked";
													%>

												</c:when>
												
											</c:choose>
										</c:forEach>

 										<tr><td height=5 colSpan=80></td></tr>

										<tr align="left">
											
											<td class="txtStyle" width="150" align="center"><c:out
													value="${masG.gradeName}" /></td>

											<td width="100" align="center"><input type="checkbox" class="checkbox-design" name="add:<%=i%>"
												id="add:<%=i%>" value="${masG.masterGradesId}" <%=dis%>
												<%=check%> onclick="addGrade(this,<%=i%>)" /><label for="add:<%=i%>" class="checkbox-label-design"></label>
												
												</td>
											<td width="100" align="center"><input type="checkbox" class="checkbox-design"
												name="remove:<%=i%>" id="remove:<%=i%>"
												value="${masG.masterGradesId}" <%=dis1%>
												onclick="removeGrade(this,<%=i%>)" /><label for="remove:<%=i%>" class="checkbox-label-design"></label></td>
											 <td width="100" align="left">
							                   <div id="resultDiv<%=i%>" class="status-message" ></div>
						                     </td>
										</tr>
										<%
                                                 dis = "";
										        dis1 = "";
											    check = "";
													
										%>

									</c:forEach>
							</table></div>
							</td></tr></table></form:form>
							</div>
						</td>
					</tr>
					
				</table>
			</td>
		</tr>
	</table>

</body>
</html>