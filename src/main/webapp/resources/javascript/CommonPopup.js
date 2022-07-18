/**
 * 
 */
function loadStates() {
	var selected = document.getElementById('countryId').value;//dwr.util.getValue('countryId');
	if(selected != 'select'){
	regService.getStates(selected, {
		callback : loadStateCallBack
	});
	}else{
		dwr.util.removeAllOptions('stateId');
		dwr.util.addOptions(stateId, ["select"]);
	}
}
function loadStateCallBack(list) {
	if (list != null) {
		dwr.util.removeAllOptions('stateId');
		$("#stateId").append(
				$("<option></option>").val('').html('Select State'));
		//dwr.util.addOptions(stateId, ["select"]);
		dwr.util.addOptions('stateId', list, 'stateId', 'name');

	} else {
		alert("No data found");
	}
}

function passwordsCompareVal(field1,field2)
{
    if(field1!=field2)
        msg = " fields are not same";
    return msg;
}

window.smoothScroll = function(target) {
    var scrollContainer = target;
    do { //find scroll container
        scrollContainer = scrollContainer.parentNode;
        if (!scrollContainer) return;
        scrollContainer.scrollTop += 1;
    } while (scrollContainer.scrollTop == 0);
    
    var targetY = 0;
    do { //find the top of target relatively to the container
        if (target == scrollContainer) break;
        targetY += target.offsetTop;
    } while (target = target.offsetParent);
    
    scroll = function(c, a, b, i) {
        i++; if (i > 30) return;
        c.scrollTop = a + (b - a) / 30 * i;
        setTimeout(function(){ scroll(c, a, b, i); }, 20);
    }
    // start scrolling
    scroll(scrollContainer, scrollContainer.scrollTop, targetY, 0);
}

var divToggle = true;
function getLPSystemRubric(divId){
	if(divToggle){
		$.ajax({
			type : "GET",
			url : "getLPSystemRubric.htm",
			success : function(response) {
				smoothScroll(document.getElementById(divId));
				$("#"+divId).html( response );
				divToggle = false;
			}
		});
	}else{
		$("#"+divId).html('');
		divToggle = true;
		setFooterHeight();
	}
}

function loadUserSections() {
	var gradeId = $('#gradeId').val();
	var classId = $('#classId').val();
	$.ajax({
		type : "GET",
		url : "getUserSections.htm",
		data : "gradeId=" + gradeId + "&classId=" + classId,
		success : function(response) {
			var userSections = response.userSections;
			$("#csId").empty(); 
			$("#csId").append(
					$("<option></option>").val('')
							.html('Select Section'));
			$.each(userSections, function(index, value) {
				$("#csId").append(
					$("<option></option>").val(value.csId).html(
							value.section.section));
			});				
		}
		
	});
}

function loadTeacherAssignLessons() {
	var csId = $('#csId').val();
	assignAssessmentsService.getTeacherAssignLessons(csId, {
		callback : teacherAssignLessonsCallBack
	});
}
function teacherAssignLessonsCallBack(list) {
	if (list != null) {
		dwr.util.removeAllOptions('lessonId');
		$("#lessonId").append(
				$("<option></option>").val('').html('Select Lesson'));
		var teacherObj = dwr.util.getValue('teacherObj');
		var myselect = document.getElementById('lessonId');		
		for(var i=0;i<list.length;i++){
			var objOption = document.createElement("option");
			var user = list[i].lesson.userRegistration.user.userType;
		    objOption.text = list[i].lesson.lessonName;
		    objOption.value = list[i].lesson.lessonId;
		    if(user =="admin" && teacherObj){
		    	objOption.style.color="blue";
		    }
		    if(user == "teacher" && !teacherObj){
		    	objOption.style.color="blue";
		    }
		    myselect.options.add(objOption);
		}

	} else {
		alert("No data found");
	}
}

function getEmailWindow(id) {
	 var left = (screen.width / 2) - (700 / 2);
	 var top = (screen.height / 2) - (400 / 2);
	 jQuery.curCSS = jQuery.css;
	 $("#dialog"+id).dialog({
			overflow: 'auto',
			dialogClass: 'no-close',
		    autoOpen: false,
		    position: {my: "center", at: "center", of:window ,within: $("body") },
		    title: 'Send Mail',
		    draggable: true,
		    width : 750,
		    height : 500,
		    resizable : true,
		    modal : true
		});
	 $("#dialog"+id).dialog("open");
}

function isValidDate(dateStr) {
    var msg = "";
    var datePat = /^(\d{1,2})(\/|-)(\d{1,2})\2(\d{4})$/;
 
    var matchArray = dateStr.match(datePat); // is the format ok?
    if (matchArray == null) {
        msg = "Date is not in a valid format.";
        return msg;
    }
 
    month = matchArray[1]; // parse date into variables
    day = matchArray[3];
    year = matchArray[4];

    if (month < 1 || month > 12) { // check month range
        msg = "Month must be between 1 and 12.";
        return msg;
    }
 
    if (day < 1 || day > 31) {
        msg = "Day must be between 1 and 31.";
        return msg;
    }
 
    if ((month==4 || month==6 || month==9 || month==11) && day==31) {
        msg = "Month "+month+" doesn't have 31 days!";
        return msg;
    }
 
    if (month == 2) { // check for february 29th
    var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    if (day>29 || (day==29 && !isleap)) {
        msg = "February " + year + " doesn't have " + day + " days!";
        return msg;
    }
    }
 
    if (day.charAt(0) == '0') day= day.charAt(1);
    return msg;  // date is valid
}

function enforceMaxLength(obj,maxLength){
	if (obj.value.length > maxLength) {
		alert("max length exceeded !!");
		obj.value = obj.value.substring(0, maxLength);
		return false;
	}
}

function openVideoDialog(userType, videoName, divId){
		var dailogContainer = $(document.getElementById(divId));
		var screenWidth = $( window ).width()-130;
		var screenHeight = $( window ).height()-10;
		var playerContent = getVideoPlayerContent();
		$(dailogContainer).append(playerContent);
		$("#"+divId).dialog({
			width: screenWidth, 
			height: screenHeight,
			position: {my: "center", at: "center", of:window ,within: $("body") },
			modal: true,
			open:function () {
			   $(".ui-dialog-titlebar-close").show();
			   $('.jp-interface').not(':first').remove();
			},close: function (ev, ui) { 
				$('#jquery_jplayer_1').jPlayer("stop");
				$(".ui-dialog-content").dialog("close");
			} 
		});	
		loadDemoVideo(userType, videoName);
		 $("#"+divId).dialog("open");
}

function getVideoPlayerContent(){
var content  = "<div id='jp_container_1' class='jp-video jp-video-360p' role='application' aria-label='media player' hieght='100%' width='30%' style='border:none;'>"+
			   "<div class='jp-type-single'>"+
				"<div id='jquery_jplayer_1' class='jp-jplayer'></div>"+
				"<div class='jp-gui'>"+
					"<div class='jp-interface'>"+
						"<div class='jp-progress'>"+
							"<div class='jp-seek-bar'>"+
								"<div class='jp-play-bar'></div>"+
							"</div>"+
						"</div>"+
						"<div class='jp-current-time' role='timer' aria-label='time'>&nbsp;</div>"+
						"<div class='jp-duration' role='timer' aria-label='duration'>&nbsp;</div>"+
						"<div class='jp-controls-holder'>"+
							"<div class='jp-controls'>"+
								"<button class='jp-play' role='button' tabindex='0'>play</button>"+
								"<button class='jp-stop' role='button' tabindex='0'>stop</button>"+
							"</div>"+
							"<div class='jp-volume-controls'>"+
								"<button class='jp-mute' role='button' tabindex='0'>mute</button>"+
								"<button class='jp-volume-max' role='button' tabindex='0'>max volume</button>"+
								"<div class='jp-volume-bar'>"+
									"<div class='jp-volume-bar-value'></div>"+
								"</div>"+
							"</div>"+
							"<div class='jp-toggles'>"+
								"<button class='jp-repeat' role='button' tabindex='0'>repeat</button>"+
								"<button class='jp-full-screen' role='button' tabindex='0'>full screen</button>"+
							"</div>"+
						"</div>"+
						"<div class='jp-details'>"+
							"<div class='jp-title' aria-label='title'>&nbsp;</div>"+
						"</div>"+
					"</div>"+
				"</div>"+
			"</div>"+
			"</div>";
	return content;
}

function loadDemoVideo(userType, videoName){
	var screenWidth = $( window ).width() - 180;
	var screenHeight = $( window ).height() - 180;
	$(".jp-interface").css("width", screenWidth);
 	$("#jquery_jplayer_1").jPlayer({               
       ready: function () {
         $(this).jPlayer("setMedia", {
        	 m4v: "loadDemoVideoFile.htm?demoVideoFilePath=LP_Tutorials/"+userType+"/"+videoName+".mp4"
         }).jPlayer("play");
       },
       cssSelectorAncestor: "#jp_container_1",
       swfPath: "resources/javascript/jplayer/jquery.jplayer.swf",
       supplied: "m4v",        
       wmode: "window",
		useStateClassSkin: true,
		autoBlur: false,
		smoothPlayBar: true,
		keyEnabled: true,
		remainingDuration: true,
		toggleDuration: true,
		preload: 'auto',
		size: {width: screenWidth, height: screenHeight},
       error: function (event) {
       },
     });
   }

function printThisDiv(divId){
	$("#loading-div-background").show();
	$("#"+divId).print();
	$("#loading-div-background").hide();
}

function removeDupsInArray(arr) {
    var result = [], map = {};
    for (var i = 0; i < arr.length; i++) {
        if (!map[arr[i]]) {
            result.push(arr[i]);
            map[arr[i]] = true;
        }
    }
    return(result);
}

function openSignaturePad(page){
	
	 var studentId=$('#studId').val();
	 var gradeId=$('#gradeId').val();
	 var trimesterId=$('#trimesterId').val();
	 $("#signatureDiv").dialog({
		overflow: 'auto',
		dialogClass: 'no-close',
	    autoOpen: false,
		position: {my: "center", at: "center", of:window ,within: $("body") },
	    title: 'Signature Pad',
	    draggable: true,
	    width : 500,
	    height : 500,
	    resizable : true,
	    modal : true,
	    close: function (ev, ui) {
	    	$('#signatureDiv').dialog('destroy');
	    } 
	 });
	 $("#signatureDiv").dialog("open");
     var iframe = $('#iframe');
     iframe.attr('src', "getSignatureContent.htm?page="+page+"&studentId="+studentId+"&gradeId="+gradeId+"&trimesterId="+trimesterId);
}
function checkParentSignExists(page) {
	 var regId = $('#userRedId').val();
	 var studentId=$('#studId').val();
	 var gradeId=$('#gradeId').val();
	 var trimesterId=$('#trimesterId').val();
	 if(regId > 0 && page){
		 $("#loading-div-background").show();
		 $.ajax({
			 type: "GET",
		     url:"checkParentSignExists.htm",
		     data: "regId="+regId+"&usersFilePath=signature/"+page+"/"+gradeId+"/"+studentId+"/"+trimesterId+"/sign.png",
		     success: function(usersFilePath){
		    	 $("#loading-div-background").hide();
		    	 if(usersFilePath){
		    		 var domain = window.location.origin;
		    		 $('#signDiv').html("");
		    		 var img = $('<img />', { 
		    			 id: 'signatureId',
		    			 width:130,
		    			 height:85,
		    			 src: domain+'/loadDirectUserFile.htm?'+ Math.random()+'&usersFilePath='+usersFilePath
		    		 });
		    		 img.appendTo($('#signDiv'));
		    	 }
		     }
		 });
	 }
}