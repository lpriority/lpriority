<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
function togglePage(thisVar, url, name){
	$.ajax({
		type : "GET",
		url : url+".htm",
		success : function(response) {
			$("#contentDiv").empty();
			$("#contentDiv").append(response);
			$("#test1").html(name);
			var current = document.getElementsByClassName("buttonSelect");
		    current[0].className = current[0].className.replace(" buttonSelect", " button");
		    var activeElement = $(thisVar);
		    activeElement.addClass('buttonSelect');
		    activeElement.removeClass('button');
		    $('title').text(name);
		}
	});
}
</script>
<title>School Information</title>
 <ul class="button-group">
	<li><a href="#" onclick="togglePage(this, 'addSchoolInfo', 'School Information')"  class="btn ${(tab == 'update school info')?'buttonSelect leftPill longTitle':'button leftPill longTitle'}">School Information </a></li>
	<li><a href="#" onclick="togglePage(this, 'sportsAndActivities', 'Sports And Activities')"   class="btn ${(tab == 'update school sports')?'buttonSelect longTitle':'button longTitle'}"> Sports & Activities </a></li>
	<li><a href="#" onclick="togglePage(this, 'gotoUploadSchoolLogo', 'Upload School Logo')"  class="btn ${(tab == 'upload school logo')?'buttonSelect rightPill longTitle':'button rightPill longTitle'}"> Upload school logo</a></li>
</ul>