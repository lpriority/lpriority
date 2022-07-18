<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript">
    function doAjaxPost() {

        $.ajax({
            type: "GET",
            url: "subView.htm",
            success: function(response) {
                $("#subViewDiv").html( response );
            }
        });
    }
</script>
</head>
<body>
<input type="button" value="GO!" onclick="doAjaxPost();" />
<div id="subViewDiv"></div>

</body>
</html>