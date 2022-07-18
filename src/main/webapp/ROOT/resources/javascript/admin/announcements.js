/**
 * 
 */
$(document).ready(function () {
		 /*$('#returnMessage').fadeOut(4000);
		 var opr = document.getElementById("operation").value;
		 if(opr != ""){
			 changeTab(opr);
		 }*/
	});
	
	function displayRow(thisvar) {
		$.ajax({
			type : "GET",
			url : "getAnnouncements.htm",
			data : "announcementId=" + thisvar,
			success : function(response) {
				var announceObj = response.announceObj;
				$('#announceDescription').val(announceObj.announceDescription);
				$('#announceDate').val(response.announcDate);
				$('#contactPerson').val(announceObj.contactPerson);
				$('#annoncementName').val(announceObj.annoncementName);
			}
		});
	}
	function changeTab(operation){
		$('#operationMode').val(operation);
		document.getElementById("addAnnouncements").reset();
		if(operation=='add'){
			document.getElementById('announcementSelect').style.visibility="hidden";
			document.getElementById('title').innerHTML ="Add Notifications";
			document.getElementById('subTitle').innerHTML ="Add Notifications";
			$("#Image10").removeClass('button').addClass('buttonSelect');
			$("#Image11").removeClass('buttonSelect').addClass('button');
			$("#Image12").removeClass('buttonSelect').addClass('button');
		}
		else if(operation=='edit'){
			document.getElementById('announcementSelect').style.visibility="visible";
			document.getElementById('title').innerHTML ='Edit Notifications';
			document.getElementById('subTitle').innerHTML ='Edit Notifications';
			$("#Image11").removeClass('button').addClass('buttonSelect');
			$("#Image10").removeClass('buttonSelect').addClass('button');
			$("#Image12").removeClass('buttonSelect').addClass('button');
		}
		else{
			document.getElementById('announcementSelect').style.visibility="visible";
			document.getElementById('title').innerHTML ="Remove Notifications";
			document.getElementById('subTitle').innerHTML ="Remove Notifications";
			$("#Image12").removeClass('button').addClass('buttonSelect');
			$("#Image10").removeClass('buttonSelect').addClass('button');
			$("#Image11").removeClass('buttonSelect').addClass('button');
		}
	}
	
	function openAnnouncementDialog(announcementId, notificationStatusId){
		$("#loading-div-background").show();
		 var userType = $('#userType').val();
		 $("#announcementDailog").dialog({
				overflow: 'auto',
				dialogClass: 'no-close',
			    autoOpen: false,
			    position: {my: "center", at: "center", of:window ,within: $("body") },
			    draggable: true,
			    width : 550,
			    height : 550,
			    resizable : true,
			    modal : true,
			    close: function (ev, ui) {
		    	  if(!$('#archive').val())
		    		location.reload();
			    } 
			});
		 if(announcementId > 0)
			 $('#announcementDailog').attr('title', 'Edit Announcement').dialog();
		 else
			 $('#announcementDailog').attr('title', 'Add Announcement').dialog();
		 var iframe = $('#announcementIframe');
		 iframe.attr('src', "openAnnouncementDialog.htm?announcementId="+announcementId+"&notificationStatusId="+notificationStatusId);
		 $("#announcementDailog").dialog("open");
		$("#loading-div-background").hide();
	}
	
	function compareAnnouncement(thisvar){
		$.ajax({
			type : "GET",
			url : "compareAnnouncement.htm",
			data : "announcementName=" + thisvar.value,
			success : function(response) {
				if(response.status)
				{
					systemMessage("Announcement Name already exists");
					$('#annoncementName').val('');
					$('#annoncementName').focus();
					return false;
				}
			}
		});
	}
	
	function validateForm(){
	    var operationMode = $('#operationMode').val();
		var announceName = $('#annoncementName').val(); 
		var announceDesc = $('#announceDescription').val();
		var announceDate = $('#announceDate').val();
		var contactPerson = $('#contactPerson').val(); 
		var date = new Date(announceDate);
		if(operationMode!='add' ){
			var announceId=  document.getElementById('announcementId').value;
			if(announceId==0){	
				systemMessage("Select a announcement");
				$('#announcementId').focus();
				return false;
			}
		}
		if(announceName==''){
			systemMessage("Annoncement Name required");
			$('#annoncementName').focus();
			return false;
		}
		else if(announceDesc==''){
			systemMessage("Annoncement Description required");
			$('#announceDescription').focus();
			return false;
		}
		else if(announceDate==''){
			systemMessage("Annoncement Date required");
			$('#announceDate').focus();
			return false;
		}else if(date =='Invalid Date'){
			alert("Invalid Date");
			$('#announceDate').focus();
			return false;
		}
		else if(contactPerson==''){
			systemMessage("Contact Person required");
			$('#contactPerson').focus();
			return false;
		}
		else{
			return true;
		}
	}
	
	function saveOrUpdateAnnouncement(){
		var stat = validateForm();
		if(stat){
			$("#loading-div-background1").show();
		    var operation = $('#operation').val();
			var formObj = document.getElementById("announcementForm");
			var formData = new FormData(formObj);
			$.ajax({
				type : "POST",
				url : "saveOrUpdateAnnouncement.htm",
				data: formData,
				mimeType:"multipart/form-data",
				contentType: false,
				cache: false,
				processData:false,
				success : function(response) {
					$("#loading-div-background1").hide();
					if(operation == "add")
						$('#announcementForm')[0].reset();
					var parsedData = JSON.parse(response);
					systemMessage(parsedData.status);
				}
			});
		}
	}
	
	function deleteAnnouncement(announcementId){
		if(announcementId > 0){
			if(confirm("Are you sure to Delete this Notification?",function(status){
				if(status){
					$("#loading-div-background").show();
					$.ajax({
						type : "GET",
						url : "deleteAnnouncement.htm",
						data : "announcementId="+announcementId,
						contentType: false,
						cache: false,
						processData:false,
						success : function(response) {
							$("#loading-div-background").hide();
							$("#announcement"+announcementId).remove();
							systemMessage(response.status);
							setTimeout(function(){/*location.reload();*/}, 1000);
						} 
					});
				}

			}));
		}
	}
	
	function getAllArchivedNotifications(){
		var getAllArchivedNotiCallBack = function (list){
			 $("#archivedNotifDailog").dialog({
				    title:'Archived Notifications',
					overflow: 'auto',
					dialogClass: 'no-close',
				    autoOpen: false,
				    position: {my: "center", at: "center", of:window ,within: $("body") },
				    draggable: true,
				    width : 850,
				    height : 650,
				    resizable : true,
				    modal : true,
				    close: function (ev, ui) {
				    	location.reload();
				    } 
				});
			var str = "<input type='hidden' id='archive' value='archived' /><table width=90% align='center' style='font-size: 13px;font-family:cursive;margin-top:1.5em;' class='des'>";
			if(list.length > 0){
				 str += "<tr class='Divheads'><td width=10% height=40 align='center'>S.No</td><td width=40% align='center'>Notification Title</td><td width=25% align='center'>Announcement Date</td><td width=30% align='center'>Read Status</td></tr><tr><td height=10></td></tr>";
				for (var i = 0; i < list.length; i++) {
					  var ns = list[i];
					  var announceDate = new Date(ns.announcements.announceDate);
					  var fullDate = announceDate.getMonth()+1 + "/" + announceDate.getDate() + "/" +  announceDate.getFullYear();
					  str += "<tr><td width=10% align='center'>"+(i+1)+".</td><td width=40% height=25 align='center'><a href='#' style='text-decoration:underline;' onclick='openAnnouncementDialog("+ns.announcements.announcementId+","+ns.notificationStatusId+")'>"+ns.announcements.annoncementName+"</a></td><td width=25% align='center'>"+fullDate+"</td><td width=30% align='center'><label class='toggle_switch'><input type='checkbox' id='toggle"+i+"' checked class='switched_on' onChange='changeReadStatus("+ns.notificationStatusId+","+i+")'><span class='toggle_slider round'></span></label></td></tr>";
				}
			}else{
				str += "<tr><td height=20 class='status-message' align='center'> No Notifications found</td></tr>";
			}
			 str += "<tr><td height=5></td></tr></table> "
			 $('#archivedNotifDailog').html(str);
			 $("#archivedNotifDailog").dialog("open");
		}
		announcementsNEventsService.getNotificationStatusByRegId("read",{
			callback : getAllArchivedNotiCallBack
		});
	}
	
	function changeReadStatus(notificationStatusId,id){
		var className = $("#toggle"+id).attr('class');
		var toggle_status = '';
		if(className == 'switched_on'){
			$("#toggle"+id).switchClass("switched_on", "switched_off"); 
			toggle_status = "unread";
		}else{
			$("#toggle"+id).switchClass("switched_off", "switched_on"); 
			toggle_status = "read";
		}
		var changeReadStatusCallBack = function(status){
			if(status)
				systemMessage("Changed as "+toggle_status);
			else
				systemMessage("Got Error");	
		}
		announcementsNEventsService.changeReadStatus(notificationStatusId, toggle_status, {
			callback : changeReadStatusCallBack
		});
	}