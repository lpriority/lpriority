<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<script>
function togglePage(thisVar,url, name){
	$("#common-loading-div-background").show();
	$.ajax({
		type : "GET",
		url : url+".htm",
		success : function(response) { 
			$("#contentDiv").empty();
			$("#contentDiv").append(response);
			$("#tab_title").html(name);
			$("title").html(name);
			if(url=="selfAndPeerReviewResults"){
			 	$('#gradeId').val('select');
			 	$('#classId').val('select');
			}
			var current = document.getElementsByClassName("buttonSelect");
		    current[0].className = current[0].className.replace(" buttonSelect", " button");
		    var activeElement = $(thisVar);
		    activeElement[0].className = activeElement[0].className.replace(" button"," buttonSelect");
		    $("#common-loading-div-background").hide();
	 	}
	}); 
}
</script>
<table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
	<tr>
		<td valign="middle" align="right">
			<nav id="primary_nav_wrap">
				<ul class="button-group">

					<li><a href="#" onclick = "togglePage(this,'getFluency','Fluency	Reading Results')" 
						class="btn ${(tab == 'fluencyResults')?'buttonSelect leftPill tooLongTitle':'button leftPill tooLongTitle'}">Fluency
							Reading Results</a></li>
					<li><a href="#" onclick = "togglePage(this,'getComprehensionResults','Comprehension Results')" 
						class="btn ${(tab =='comprehensionResults')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Comprehension
							Results</a></li>
							<li><a href="#" onclick = "togglePage(this,'selfAndPeerReviewResults','Accuracy Results')" 
						class="btn ${(tab =='selfAndPeerResults')?'buttonSelect rightPill tooLongTitle':'button rightPill tooLongTitle'}">Accuracy Results
							</a></li>

				</ul>
			</nav>
		</td>
	</tr>
	<tr>
		<td width="100%">
			<table width="100%" class="title-pad heading-up" border="0">
				<tr>
					<td id = "tab_title" class="sub-title title-border" height="40" align="left">
						<c:if test="${tab == 'fluencyResults' }">
							Fluency Reading Results
			       		</c:if> 
			       		<c:if test="${tab == 'comprehensionResults' }">
							Comprehension Results
						</c:if>
						<c:if test="${tab == 'selfAndPeerResults' }">
							Accuracy Self And Peer Results
						</c:if>
					</td>
				</tr>
					<tr><td> 
                             <div id="contentDiv">
                                 <%@include file="../Admin/fluency_results.jsp"%>
                              </div>
                            </td></tr>
			</table>
		</td>
	</tr>
</table>