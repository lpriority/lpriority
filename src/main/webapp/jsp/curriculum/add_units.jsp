<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ include file="../CommonJsp/include_resources.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>        
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title>Create Unit</title>
    </head>
    <body> 
           <table width="50%" border=0 align="center" cellPadding=0 cellSpacing=0>
           <tr>
                    <td height="0" colspan="2" align="center" valign="top">
                        <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
                        		<tr><td height=30 colSpan=3></td></tr> 
                            	<tr>
                                    <td width="342" align="left"><span class="tabtxt">Unit Name :</span></td>
                                </tr>
                                <tr><td height=10 colSpan=3></td></tr> 
                                <tr>    
                                    <td width="342" align="left">
                                        <span class="tabtxt">
                                            <input id="unitName" name = "unitName" type="text"/>
                                            <input id="gradeId" name = "gradeId" value="${gradeId}" type="hidden"/>
                                            <input id="classId" name = "classId" value="${classId}" type="hidden"/> 
                                        </span>                                                  
                                       
                                    </td>
                                </tr>
                                <tr><td height=30 colSpan=3></td></tr> 
                                <tr>
                                    <td align="center">
                                    
                                        <table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:16px;">
                                            <tr>
                                                <td width="30%" height="10" align="left">
                                                    <div class="button_green" align="right" onclick="creatUnit()">Create</div>
                                                </td>
                                                <td width="30%" align="center"></td>
                                                <td width="30%" align="left" valign="middle" height="10">
                                                   <div class="button_green" align="right" onclick="$('#unitName').val(''); return false;">Clear</div>
                                                </td>
                                            </tr>
                                        </table></td>
                                </tr>
                                <tr><td height=10 colSpan=3></td></tr> 
                                 <tr>
				                     <td width="325" height="5" align="center" colspan="5">
				                     	<div id="resultDiv1" align="center" class="status-message"></div>
				                     </td>
				             	</tr>
                        </table>
                    </td>
                </tr>
          </table>

</body>
</html>
