<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>        
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title>Create Folder</title>
        <script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
		<%@ include file="../CommonJsp/include_resources.jsp" %>
        <script type="text/javascript" src="resources/javascript/my_files.js"></script>
    </head>
    <body> 
       
    <tr>
        <td  vAlign=top width="100%" colSpan=3 align=middle>
            <table width="50%" border=0 align="center" cellPadding=0 cellSpacing=0>
                <tr>
                    <td height=12 colSpan=2></td></tr>
                <tr>
                    <td height="0" colspan="2" align="center" valign="top">
                        <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td width="342" align="center" valign="middle">&nbsp;</td>
                                <td width="419" align="center" valign="middle">&nbsp;</td>
                                <td width="365" align="center" valign="middle">&nbsp;</td>
                            </tr>
                                <tr>
                                    <td width="342" align="left"><span class="tabtxt">New Folder Name :</span></td>
                                </tr>
                                <tr><td height=10 colSpan=3></td></tr> 
                                <tr>    
                                    <td width="342" align="left">
                                        <span class="tabtxt">
                                            <input id="folderName" name = "folderName" type="text" style="width: 200px;height:20px;"/>
                                            <input id="teacherId" name = "teacherId" value="${teacherId}" type="hidden"/>
                                            <input id="fileType" name = "fileType" value="${fileType}" type="hidden"/>
                                            <input id="gradeId" name = "gradeId" value="${gradeId}" type="hidden"/>
                                            <input id="classId" name = "classId" value="${classId}" type="hidden"/>
                                            <input type="hidden" id="usersFolderPath" name="usersFolderPath" value="${usersFolderPath}" /> 
                                        </span>                                                  
                                       
                                    </td>
                                </tr>
                                <tr><td height=30 colSpan=3></td></tr> 
                                <tr>
                                    <td align="center">
                                    
                                        <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                            <tr>
                                                <td width="70" height="25" align="left">
                                                  <div class="button_green" align="right" onclick="creatFolder()">Create</div>
                                                </td>
                                                <td width="25" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                                <td width="147" align="left" valign="middle">
													 <div class="button_green" align="right" onclick="$('#folderName').val(''); return false;">Clear</div>
                                                </td>

                                            </tr>
                                        </table></td>
                                </tr>
                                <tr><td height=10 colSpan=3></td></tr> 
                                 <tr>
				                     <td width="325" height="35" align="center" colspan="5">
				                     		<div id="resultDiv" class="status-message" ></div>
				                     </td>
				             	</tr>
                            <tr>
                                <td width="365" align="center" valign="middle" ></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td height="2" colspan="2" ></td>
                </tr>
                <tr>
                    <td height="30" colspan="2"></td>
                </tr>
                <tr>
                    <td height="30" colspan="2"></td>
                </tr>
            </table>
          <%--   </form> --%>
        </td>
    </tr>   
</body>
</html>