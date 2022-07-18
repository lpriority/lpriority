<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		
		<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
		<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
		<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
		<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
		
		<script type="text/javascript">
			function getStudentCompositeChart(thisvar){
				var csId = document.getElementById('csId'+thisvar).value;
				var studentId = document.getElementById('studentId'+thisvar).value;
				var width = $(window).width();;
				var height = $(window).height();;
				$.ajax({
					type : "GET",
					url : "getStudentCompositeChart.htm",
					data : "csId=" + csId + "&studentId=" + studentId,
					success : function(response) {
						var dailogContainer = $(document.getElementById('studentCompositeChart'));
						$('#studentCompositeChart').empty();  
						$(dailogContainer).append(response);
						$(dailogContainer).dialog({width: width-20, height: height,open:function () {
							  $(".ui-dialog-titlebar-close").show();
						}
					});					
						$(dailogContainer).scrollTop("0");
					}
				});
			}
		</script>	
			
	</head>
	<body>
		<div align="center">
			<table border="1">
				<tr><td style="font-weight: bold;">Student Name</td><td style="font-weight: bold;">Student Email</td></tr>
				<c:forEach items="${studentRoster}" var="student" varStatus="studentCount">
					<tr>
						<td>
							<a href="javascript:void(0);" onclick="getStudentCompositeChart(${studentCount.index})" id="compositeChart">
								${student.student.userRegistration.firstName}${student.student.userRegistration.lastName}
							</a>
							<input type="hidden" id="csId${studentCount.index}" value="${student.classStatus.csId}">
							<input type="hidden" id="studentId${studentCount.index}" value="${student.student.studentId}">
						</td>
						<td>${student.student.userRegistration.emailId}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div id="studentCompositeChart" title="Student Composite Chart" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
	</body>
</html>