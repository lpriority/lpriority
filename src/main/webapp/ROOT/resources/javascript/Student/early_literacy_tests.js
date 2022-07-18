 var setsMap = {};	
 var setsArray = new Array();
 var setNameArray = new Array();
 var contentArray =  new Array();
 var voiceArray =  new Array();
 var len = 0; 
 var wait = true;
 var isLastValue = false;
 var isFirstValue = true;
 var contentIndex = 0;
 var content = '';
 var arrayCursorPoint = 1;
 var setName = '';
 var mapCursorPoint = 1; 
 
 function goForAssignment(dialogDivId,studentAssignmentId,id,assignmentId,recordTime,eltTypeId){
	 if(eltTypeId == '')
		 eltTypeId = 0;
	 setsArray.length = 0;
	 setNameArray.length = 0;
	 contentArray.length = 0;
	 voiceArray.length = 0;
	 jQuery.browser = {};
	 (function () {
	     jQuery.browser.msie = false;
	     jQuery.browser.version = 0;
	     if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
	         jQuery.browser.msie = true;
	         jQuery.browser.version = RegExp.$1;
	     }
	 })();
	 var earlyLiteracyTestsCallBack = function(list) {
	 var setType = '';
	 for (i=0;i<list.length;i++){
		 setsArray.push(list[i].set);
		 setNameArray.push(list[i].setName);
		 setType = list[i].setType;
	 }
	 var iframe = $('#iframe'+id);
	 iframe.attr('src', "goForAssignment.htm?setsArray="+setsArray+"&setNameArray="+setNameArray+"&setType="+setType+"&studentAssignmentId="+studentAssignmentId+"&dialogDivId="+dialogDivId+"&assignmentId="+assignmentId+"&recordTime="+recordTime+"&eltTypeId="+eltTypeId);
	 }
	 earlyLiteracyTestsService.getEarlyLiteracyTests(studentAssignmentId,{
		callback : earlyLiteracyTestsCallBack
	 });
	 var dailogContainer = $(document.getElementById(dialogDivId));
	 var screenWidth = $( window ).width() - 200;
	 var screenHeight = $( window ).height() -30;
	 jQuery.curCSS = jQuery.css;
	 $("#"+dialogDivId).dialog({
		overflow: 'auto',
		dialogClass: 'no-close',
	    autoOpen: false,
		position: {my: "center", at: "center", of:window ,within: $("body") },
	    title: 'Early Literacy Tests',
	    draggable: true,
	    width : screenWidth,
	    height : screenHeight,
	    resizable : true,
	    modal : true
	});
	 $(".ui-dialog-titlebar-close").hide(); 
	 $("#"+dialogDivId).dialog( "open" );
 }
 function setContent(setId,lastValueInMap){
	 var name = $('#set'+setId).attr("name");
	 contentArray = setsMap[name];
	 if(contentArray){
		 if(arrayCursorPoint < contentArray.length){
			 content = contentArray[contentIndex];
			 setName = name;
			 arrayCursorPoint++;
			 contentIndex++;
			 isLastValue = false;
		 }else if(arrayCursorPoint == contentArray.length){
			 content = contentArray[contentIndex];
			 setName = name;
			 arrayCursorPoint = 1;
			 contentIndex = 0;
			 mapCursorPoint++;
			 if(lastValueInMap){
				isLastValue = true; 
			 }
		 }
	 }
 }
 
  function recordVoiceData() {
	 var len = dwr.util.getValue('setNameArrayLength');
	 if(Object.keys(setsMap).length == 0){
	        for (i = 1; i <= len; i++){
				 var set = dwr.util.getValue('set'+i);
				 var name = $('#set'+i).attr("name");
				 contentArray = set.split(' ');
				 setsMap[name] = contentArray;
			 }
			 $("#takeTest").show();
			 $("#getReady").hide();
	 } 
	 var cnt = 0;
	 var waitingTime = 0;
	     waitingTime = dwr.util.getValue('recordTime');
	 
	 $("#completed").html('');
	  if(wait){ 
		  if(len > mapCursorPoint){
				 if(contentIndex == 0 && isFirstValue){
					 setContent(mapCursorPoint,false); 
					 contentIndex = 0;
					 arrayCursorPoint --;
				 }else{
					 isFirstValue = false;
					 setContent(mapCursorPoint,false); 
				 }
		     }else if(len == mapCursorPoint){
		    	 if(contentIndex == 0 && isFirstValue){
		    		 setContent(mapCursorPoint,true);
					 contentIndex = 0;
					 arrayCursorPoint --;
				 }else{
					 isFirstValue = false;
					 setContent(mapCursorPoint,true);
				 }
		     }
		  wait = false;
		  $("#contentDiv").html('');
		  $("#setNameDiv").html('');
		  $("#contentDiv").append(content);
			if(!isFirstValue){
				$('#recordImg').css({'color': '#5caa00'});
			recordAudio();			    
		    var i = setInterval(function(){ 
				 cnt++;
				 if(cnt === 1) {
				        clearInterval(i);
				        dwr.util.setValue('content',content);
				        dwr.util.setValue('setName',setName);
				        dwr.util.setValue('lastValue',isLastValue);
				        wait = true;
				        stop();
				   }
			}, waitingTime * 1000);
			}else{
				wait = true;
				isFirstValue = false;
			}
	 }else{
		 return false;
	 }
 }

 function stop() {
	 $('#recordImg').css({'color': '#e41093'});
	 $("#loading-div-background1").show();
	 var form = document.getElementById("assignmentReadingTestForm");
	 if(!isLastValue){
	     document.getElementById("statusVal").value = "saved";	     
	 }else{
		 document.getElementById("statusVal").value = "submitted";
	 }	 
	 stopRecording(function(base64){ 
		 document.getElementById("audioData").value=base64;
	      var formData = new FormData(form);
	      $.ajax({
	        url: "recordAssessment.htm",
	        type: 'POST',
	        data: formData,
	        mimeType:"multipart/form-data",
			contentType: false,
			cache: false,
			processData:false,
	        success: function(response) {
	        	if(isLastValue){ 
	       	    	$("#takeTest").hide();
			        $("#contentDiv").html('');
			        $("#setNameDiv").html('');
			        response = JSON.parse(response);
	       	     	$("#completed").append("<span class='status-message' style='font-size: 24px;font-weight:bold;'>"+response.earlyLiteracyTestsForm.result+"</span>");
	       	     	var i = setInterval(function(){ 
		  	       	clearInterval(i);
		  	       	window.parent.$('.ui-dialog-content:visible').dialog('close');
		  	       	window.parent.location.reload();
		  	        window.parent.document.getElementById( 'dailog' ).style.pointerEvents = 'auto'; 
	       	     	}, 3000);
      	    	}else{
      	    		recordVoiceData();
      	    	}
      	    	$("#loading-div-background1").hide();
	        }
	      });
		});
 }
