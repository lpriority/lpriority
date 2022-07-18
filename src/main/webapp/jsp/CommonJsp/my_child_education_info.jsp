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
<link href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.min.css" rel="stylesheet">
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/CommonValidation.js"></script>
<script type="text/javascript" src="resources/javascript/common/my_profile.js"></script>
<script src="resources/javascript/imageloadfunctions.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<title>My Child Education Info</title>
</head>
	<body>
             
              	<table width="100%">
              		 <%@include file="educational_info.jsp"%>
                 <tr>
                     <td  height="5" align="center" colspan="5">
                     		<font color="blue" size="4"><b><div id="resultDiv" ></div></b></font>
                     </td>
             	</tr>
              						
              		</table>       
</body>
</html>