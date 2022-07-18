/**
 * 
 */

function getCompositeChartByStudent(thisvar){
		var width = $(window).width();;
		var height = $(window).height();;
		$.ajax({
			type : "GET",
			url : "getCompositeChartByStudent.htm",
			data : "csId=" + thisvar,
			success : function(response) {
				var dailogContainer = $(document.getElementById('compositeChart'));
				$('#compositeChart').empty();  
				$(dailogContainer).append(response);
				$(dailogContainer).dialog({width: width-20, height: height,open:function () {
					  $(".ui-dialog-titlebar-close").show();
				}
			});					
				$(dailogContainer).scrollTop("0");
			}
		});
	}
function popUpAnnouncementDetails(announcementId){
	document.getElementById('announcementList').style.display ="none";
	document.getElementById('announcementDesc'+announcementId).style.display ="block";
}
function popUpAnnouncementList(announcementId){
	document.getElementById('announcementList').style.display ="block";
	document.getElementById('announcementDesc'+announcementId).style.display ="none";		
}
function popUpEventDetails(eventId){
	document.getElementById('eventList').style.display ="none";
	document.getElementById('eventDesc'+eventId).style.display ="block";	
}
function popUpEventList(eventId){
	document.getElementById('eventList').style.display ="block";
	document.getElementById('eventDesc'+eventId).style.display ="none";		
}

