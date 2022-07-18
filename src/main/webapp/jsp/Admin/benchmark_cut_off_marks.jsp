<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fluency Cut Off Marks</title>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/admin/add_edit_remove_sl_schl.js"></script>
</head>
<body>
	<table style="padding-left: 8em;width: 100%"  class="heading-up heading-up">
       	<tr>
           	<td class="sub-title title-border" height="40" align="left" 
           		colspan="10">
               		Set Fluency Cut Off Marks
            </td>
        </tr>
        <tr>
        	<td height=10 align=left style="padding-top: 1.5em;width: 12em;" class="label">
        		Grade  &nbsp;&nbsp;&nbsp;
				<select id="gradeId" name="gradeId" onchange="getBenchmarkCategories()" class="listmenu">
					<option value="select">select grade</option>
						<c:forEach items="${grList}" var="ul">
							<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
						</c:forEach>
				</select>	
        	</td>
        </tr>
	</table>	
	<div>					
	<table>
		<tr>
			<td  vAlign=top width="100%" colSpan=3 align=middle class="">
				<table width="80%" border=0 align="center" cellPadding=0
					cellSpacing=0>	
					<tr>
						<td height="0" colspan="2" align="left" valign="top" width="50%">
							<table width="50%" border="0" cellspacing="0" cellpadding="0" align="center">
								<tr>
									<td width="64" height="30" align="center" valign="top"></td>
									<td></td>
								</tr>
								<tr>
									<td height="0" colspan="9" align="center" valign="top" id="td1"></td>
								</tr>
								<tr>
									<td height="0" colspan="9" align="center" valign="top" id="td2"></td>
								</tr>
								<tr>
									<td height="0" colspan="9" align="center" valign="top">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td width="64">&nbsp;</td>
												<td width="185" align="right" valign="top"><span
													class="tabtxt"></span></td>
												<td width="10" align="center" valign="top" class="text1"></td>
												<td width="621"><table border="0" cellspacing="0"
														cellpadding="0" width="441">
													</table></td>
											</tr>


										</table>
									</td>
								</tr>


								<tr>
									<td height="10" colspan="9" align="left" valign="top"></td>
								</tr>
								<tr>
									<td width="64" height="35" align="left" valign="middle"></td>
									<td width="10" height="35" align="right" valign="middle"></td>
									<td width="10" height="35" align="left" valign="middle"></td>
									<td height="35" width="621" align="left" valign="middle"><table
											width="190" border="0" cellspacing="0" cellpadding="0">
										</table></td>
								</tr>
								<tr>
									<td width="64" height="25" align="left" valign="middle">&nbsp;</td>
									<td height="25" align="right" valign="middle">&nbsp;</td>
									<td height="25" width="10" align="left" valign="middle">&nbsp;</td>
									<td height="25" width="621" align="left" valign="middle">&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</div>
</body>
</html>


