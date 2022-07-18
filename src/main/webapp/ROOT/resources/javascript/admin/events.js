/**
 * 
 */

function changeTab(operation){
	$('#operationMode').val(operation);	
	document.getElementById("events").reset();
	if(operation=='add'){
		document.getElementById('eventSelect').style.visibility="hidden";
		document.getElementById('title').innerHTML ="Add Events";
		document.getElementById('subTitle').innerHTML ="Add Events";
		$("#Image10").removeClass('button').addClass('buttonSelect');
		$("#Image11").removeClass('buttonSelect').addClass('button');
		$("#Image12").removeClass('buttonSelect').addClass('button');
	}
	else if(operation=='edit'){
		document.getElementById('eventSelect').style.visibility="visible";
		document.getElementById('title').innerHTML ='Edit Events';
		document.getElementById('subTitle').innerHTML ='Edit Events';
		$("#Image11").removeClass('button').addClass('buttonSelect');
		$("#Image10").removeClass('buttonSelect').addClass('button');
		$("#Image12").removeClass('buttonSelect').addClass('button');
	}
	else{
		document.getElementById('eventSelect').style.visibility="visible";
		document.getElementById('title').innerHTML ="Remove Events";
		document.getElementById('subTitle').innerHTML ="Remove Events";
		$("#Image12").removeClass('button').addClass('buttonSelect');
		$("#Image10").removeClass('buttonSelect').addClass('button');
		$("#Image11").removeClass('buttonSelect').addClass('button');
	}
}
function displayEventsRow(thisvar) {
	$.ajax({
		type : "GET",
		url : "getEvents.htm",
		data : "eventId=" + thisvar,
		success : function(response) {
			var eventObj = response.eventObj;
			$('#eventName').val(eventObj.eventName);
			$('#description').val(eventObj.description);
			$('#announcementDate').val(response.announcementDate);
			$('#scheduleDate').val(response.scheduleDate);
			$('#lastDate').val(response.lastDate);
			$('#venue').val(eventObj.venue);
			$('#contactPerson').val(eventObj.contactPerson);
			$("#hours option[value='" + response.hours + "']").attr("selected","selected");
			$("#minutes option[value='" + response.minutes + "']").attr("selected","selected");
			$("#timeMeridian option[value='" + response.timeMeridian + "']").attr("selected","selected");
		}
	});
}

function openEventDialog(eventId){
	 $("#loading-div-background").show();
	 var userType = $('#userType').val();
	 $("#eventDailog").dialog({
			overflow: 'auto',
			dialogClass: 'no-close',
		    autoOpen: false,
			position: {my: "center", at: "center", of:window ,within: $("body") },
		    draggable: true,
		    width : 620,
		    height : 680,
		    resizable : true,
		    modal : true,
		    close: function (ev, ui) {if(userType == 'admin')location.reload();} 
		});
		var iframe = $('#eventIframe');
		iframe.attr('src', "openEventDialog.htm?eventId="+eventId);
		$("#eventDailog").dialog("open");
		$("#loading-div-background").hide();
	}

function compareEvent(thisvar){
	var eventId =  $("#eventId").val();
	if(eventId > 0){
		
	}else{
		eventId = 0;
	}
	$.ajax({
		type : "GET",
		url : "compareEvent.htm",
		data : "eventName=" + thisvar.value+ "&eventId="+eventId,
		success : function(response) {
			if(response.status){
				systemMessage("Event Name already exists");
				$('#eventName').val('');
				$('#eventName').focus();
				return false;
			}
		}
	});
}
function validateForm(){
	var eventName =  $("#eventName").val();
	var description =  $("#description").val();
	var announcementDate =  $("#announcementDate").val();
	var scheduleDate =  $("#scheduleDate").val();
	var lastDate =  $("#lastDate").val();
	var venue =  $("#venue").val();
	var contactPerson =  $("#contactPerson").val();
	
	var aDate = new Date(announcementDate);
	var lDate = new Date(lastDate);
	var sDate = new Date(scheduleDate);
	
	if(aDate == 'Invalid Date'){
		systemMessage("Announcement Date is mandatory");
		$("#announcementDate").focus();
		return false;
	}
	if(lDate == 'Invalid Date'){
		systemMessage("Last Date is mandatory");
		$("#lastDate").focus();
		return false;
	}
	if(sDate == 'Invalid Date'){
		systemMessage("Schedule Date is mandatory");
		$("#scheduleDate").focus();
		return false;
	}
	if(aDate > lDate){
		systemMessage("Announcement date should before Last Date");
		$("#lastDate").focus();
		return false;;
	}
	if(lDate > sDate){
		systemMessage("Last date should before Schedule Date");
		$("#scheduleDate").focus();
		return false;;
	}
	
	if(eventName==''){
		systemMessage("Event Name is required");
		$("#eventName").focus();
		return false;
	}
	else if(description==''){
		systemMessage("Description is required");
		$("#description").focus();
		return false;
	}
	else if(announcementDate==''){
		systemMessage("Announcement Date is required");
		$("#announcementDate").focus();
		return false;
	}
	else if(scheduleDate==''){
		systemMessage("Schedule Date is required");
		$("#scheduleDate").focus();
		return false;
	}
	else if(lastDate==''){
		systemMessage("Last Date is required");
		$("#lastDate").focus();
		return false;
	}
	else if(venue==''){
		systemMessage("Venue Name is required");
		$("#venue").focus();
		return false;
	}
	else if(contactPerson==''){
		systemMessage("Contact Person is required");
		$("#contactPerson").focus();
		return false;
	}
	else{
		return true;
	}
}

function saveOrUpdateEvent(){
	var stat = validateForm();
	if(stat){
		$("#loading-div-background").show();
		var operation =  $('#operation').val(); 
		var formObj = document.getElementById("eventForm");
		var formData = new FormData(formObj);
		$.ajax({
			type : "POST",
			url : "saveOrUpdateEvent.htm",
			data: formData,
			mimeType:"multipart/form-data",
			contentType: false,
			cache: false,
			processData:false,
			success : function(response) {
				$("#loading-div-background").hide();
				if(operation == 'add')
				 $('#eventForm')[0].reset();
				var parsedData = JSON.parse(response);
				systemMessage(parsedData.status);
			}
		});
	}
}

function deleteEvent(eventId){
	if(eventId > 0){
		if(confirm("Are you sure to Delete this Event?",function(status){
			if(status){
				$("#loading-div-background").show();
				$.ajax({
					type : "GET",
					url : "deleteEvent.htm",
					data : "eventId="+eventId,
					contentType: false,
					cache: false,
					processData:false,
					success : function(response) {
						$("#loading-div-background").hide();
						$("#event"+eventId).remove();
						systemMessage(response.status);
						setTimeout(function(){/*location.reload();*/}, 1000);
						
					}
				});
			}
		}));
	}
}