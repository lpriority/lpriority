<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create sections</title>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
</head>

<body>
<form:form name="CreateSections" modelAttribute="section" method="get">
	<table>
		<tr>

			<td class="2" vAlign=top align=right>
				<table border=0 cellSpacing=0 cellPadding=0 width=206 align=right>
					<tr>
						<td vAlign=bottom width=103><a href="CreateSections.jsp">
								<img border=0 src="images/create-section-green.jpg" width=103
								height=27 />
						</a></td>

						<td vAlign=bottom width=103><a
							onmouseover="MM_swapImage('Imaget','','images/edit-section-green.jpg',1)"
							onmouseout=MM_swapImgRestore() href="EditSections.jsp"><img
								id=Imaget border=0 name=Imaget
								src="images/edit-section-blue.jpg" width=103 height=27 /></a></td>
					</tr>
				</table>
			</td>
		</tr>
			<tr>
				<td height="30" colspan="2" align="left" valign="middle"><table
						width="100%" height="30" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							`
							<td width="30%" class="sub-title" align="center">Create Sections
							</td>
						</tr>
						<tr>
							<td width="30%" align="right" valign="middle" class="header"><font
								color="aeboad" size="2">Grade&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></td>
							<td width="50" align="left" valign="middle"><select
								id="gradeId" name="gradeId" onchange="loadClasses()">
									<option value="select">select grade</option>
									<c:forEach items="${grList}" var="ul">
                                  
										<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>

									</c:forEach>
									
							</select><c:out value="${ul.gradeId}"></c:out>
							</td>
							<td width="58" align="center" valign="middle" class="header"><font
								color="aeboad" size="2">&nbsp;&nbsp;&nbsp;Class&nbsp;&nbsp;</font></td>
							<td width="122" align="right" valign="center">
							<select id="classId" name="classId" class="listmenu" onchange="loadGradeLevels()">
									<option value="select">select subject</option>

									<%-- <c:forEach items="${clList}" var="ul">
										<option value="${ul.classId}">${ul.className}</option>

									</c:forEach> --%>
									

							</select></td>
							<td width="58" align="center" valign="middle" class="header"><font
								color="aeboad" size="2">&nbsp;&nbsp;&nbsp;Group&nbsp;&nbsp;</font></td>
							<td width="80" align="right" valign="right">
							<select name="gradeLevelId" class="listmenu" id="gradeLevelId">
									<option value="select">Select GradeLevel</option>
							</select></td>
							<td>&nbsp;</td>
							<td><font color="blue"> <%-- 	<%
										if (request.getParameter("res") != null) {
											String msg = request.getParameter("res");
											out.println(msg);
										}
									%> --%>
							</font>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
					</table></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>

				<td height="40" align="center" valign="top">Enter Section Name:
					<input type="text" name="sectionName" id="sectionName" value=""></input>
				</td>
			</tr>
			<tr>
				<td align='center' valign='middle'><input type='submit'
					class='submitButton' value='' onClick='submitSection()' /> <input
					type='image' src='images/Clear.gif' onClick='refresh1()' /></td>
			</tr>
		
		<tr>
			<td height="80"></td>
		</tr>
	</table></form:form>
</body>
</html>