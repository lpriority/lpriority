function getUnGradedActivites(){
  	var gradeId = $("#gradeId").val();
  	var sortBy = $("#sortBy").val();
  	var studentId = $("#studentId").val();
	$("#loading-div-background2").show();
	if(studentId != "select" && gradeId!="select" && sortBy!="select"){
		$.ajax({
			type : "GET",
			url : "getActivitiesToBeReviewedByStudent.htm",
			data : "studentId=" + studentId+"&sortBy="+sortBy+"&gradeId="+gradeId,
			success : function(response) {
				$("#contentDiv").empty();
				$("#contentDiv").append(response);
				$("#loading-div-background2").hide();
				
			}
		});
 	} 
	else if(gradeId != "select" && studentId=="select" && sortBy!="select"){
		$.ajax({
			type : "GET",
			data : "gradeId=" + gradeId+"&sortBy="+sortBy,
			url : "getActivitiesToBeReviewedByGrade.htm",
			success : function(response) {
				$("#contentDiv").empty();
				$("#contentDiv").append(response);
				$("#loading-div-background2").hide();
			}
		});
	}
	else{
		alert("Please select the grade");
		$("#contentDiv").empty();
		$("#loading-div-background2").hide();
		return false;
	}
	 
}
function openNewBookDialog(titleId){
	var gradeId=$('#gradeId').val();
	 if(gradeId == 'select')
		 {
		 systemMessage("please select the Grade");
		 return false;
		 }
		$("#loading-div-background").show();
		 var user = $('#user').val(); 
		 $("#addBookDailog").dialog({
				overflow: 'auto',
				dialogClass: 'no-close',
			    autoOpen: false,
			    position: {my: "center", at: "center", of:window ,within: $("body") },
			    draggable: true,
			    width : 800,
			    height : 550,
			    resizable : true,
			    modal : true,
			    open: function() {
	                $(this).parent().find(".ui-dialog-titlebar-close").hide();
	            }
			   /* close: function (ev, ui) {
			    	var actCount=$('#actCount').val();
			    	var bookTitleId=$('#bookTitId').val();
			    				    	
			    	systemMessage(bookTitleId);
			    	systemMessage(actCount);
			    	if(actCount>0 && bookTitleId>0){
					    window.location.reload(true);
					   }
			    	else if(bookTitleId>0 && actCount==0){
			    		 systemMessage("You Must Create One Activity"); 
			    		 $('#addBookDailog').dialog('open');
			    		 return false;
			    	   }
			    	else{
			    		//window.location.reload(true);
			    	}
			    } */
			});
		 if(titleId > 0)
			 $('#addBookDailog').attr('title', 'Edit Book').dialog();
		 else
			 $('#addBookDailog').attr('title', 'Add Book').dialog();
		 var iframe = $('#bookIframe');
		 iframe.attr('src', "openBookDialog.htm?titleId="+titleId+"&user="+user+"&gradeId="+gradeId);
		 $("#addBookDailog").dialog("open");
		$("#loading-div-background").hide();
	}

function validateForm(){
    var bookTitle = $('#bookTitle').val();
	var author = $('#author').val(); 
	var numberOfPages = parseInt($('#numberOfPages').val());
	
	if(bookTitle==''){
		systemMessage("Book Title required");
		$('#bookTitle').focus();
		return false;
	} else if(author==''){
		systemMessage("Author Name required");
		$('#author').focus();
		return false;
	} else if(numberOfPages <= 0){
		systemMessage("No of Pages not equal to 0");
		$('#numberOfPages').focus();
		return false;
	}else if(numberOfPages > 999999){
		systemMessage("No of Pages can not exceed 999999");
		$('#numberOfPages').focus();
		return false;
	} else if(!/^[0-9]+$/.test(numberOfPages)){
		systemMessage("Speacial Characters not allowed");
		$('#numberOfPages').focus();
		return false;
	} else{
		return true;
	}
}

function saveOrUpdateBook(){
	var page=0;
	var status = validateForm();
	if(status){
		$("#loading-div-background1").show();
		var formObj = document.getElementById("addBookForm");
		var formData = new FormData(formObj);
		var operation = $('#operation').val(); 	
		var user = $('#user').val(); 
		if(user == 'student'){
		$.ajax({
			type : "POST",
			url : "saveOrUpdateBook.htm",
			data: formData,
			mimeType:"multipart/form-data",
			contentType: false,
			cache: false,
			processData:false,
			success : function(data) {
				$("#loading-div-background1").hide();
				if(operation == 'create')
					$('#addBookForm')[0].reset();
				var parsedData = JSON.parse(data);
				var titleId = parsedData.titleId;
				$("#bookTitId").val(titleId);
				window.parent.$('#bookTitle'+titleId).html($('#bookTitle').val());
				window.parent.$('#author'+titleId).html($('#author').val());
				window.parent.$('#numberOfPages'+titleId).html($('#numberOfPages').val());
				window.parent.$('#con'+titleId).html("");
				if(user == 'student'){
					//systemMessage("Book sent for approvals...");
					systemMessage("Book added Sucessfully");
					$('#closeBut').css('visibility', 'hidden');
					var str="<br><br><br><tr><td width='25%' height='20' align='right' valign='middle'><span class='tabtxt'><img src='images/Common/required.gif'> Add Activity</span></td><td width='10%' height='20' align='center' valign='middle'>:</td>" +
							"<td width='65%' height='20' align='left' valign='middle' id='stdReviewTbl_"+titleId+"' class='label'>"+
							"<span class='fa fa-upload' title='Draw a picture or diagram that explains something important from the book. You can use the online draw tool or draw one on paper and take a photo of it. Upload your picture.&#013;Points Earned : 3' id='btAdd' name='btAdd' height='52' onclick='openPictureDialog("+titleId+","+page+")' style='width:30px;padding: 0.2px 10px;font-size: 23px;color: #273f4a;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);'></span>" +
							"<span class='fa fa-list-ol' title='Write 6 basic questions and 4 more challenging questions for other children to answer. For each question write 4 possible answers. Make sure one answer is correct. Identify which answer is correct.&#013;Points Earned : 4' id='btAdd' name='btAdd' height='52' onclick='openCreateQuizDialog("+titleId+","+page+")' style='width:30px;padding: 0.2px 10px;font-size: 23px;color:#88523e;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);'></span>"+
							"<span class='ion-ios-mic' title='Use the retell audio tool and record yourself explaining what the book is about and what you learned.&#013;Points Earned : 2' id='btAdd' name='btAdd' height='52' width='50' onclick='openRetellDialog("+titleId+","+page+")' style='width:40px;padding: 0.2px 10px;font-size: 28px;color:#3F51B5;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);'></span>"+
							"<span class='ion-chatbox-working' title='Tell what the book is about. Explain what you liked best about the book. Ask a question you have about the book. Answer a question someone else has about the book if there is one.&#013;Points Earned : 5' id='btAdd' name='btAdd' height='52' onclick='openReviewPage("+titleId+",0,0,"+page+")' style='width:40px;padding: 0.2px 10px;font-size: 28px;color: #5d9c13;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);'></span>"+
						    "</td></tr>" +
						    "<tr><td width='100%' height='23' align='center' colspan='3' style='padding-top:1em;'>"+
							"<br><table width='60%' border='0' cellspacing='0' cellpadding='0'><tr><td width='20%' align='center' valign='middle'>"+
							"<input type='button' class='button_green' value='Done' style='border:none' onclick='closeActivityDailog("+titleId+",0)'></td></tr>";
							/*"<td width='20%' align='left' valign='middle'><input type='button' class='button_green' value='Close' style='border:none;' onclick='closeActivityDailog("+titleId+",1)'></td></tr>";*/
									
					 document.getElementById('T3').innerHTML = str;
					//$("viewActivity" ).html(str);
					/*var i = setInterval(function(){ 
			  	       	clearInterval(i);
			  	       	window.parent.$('.ui-dialog-content:visible').dialog('close');
			  	       	window.parent.location.reload();
			  	        window.parent.document.getElementById( 'dailog' ).style.pointerEvents = 'auto'; 
		       	     	}, 2000);	*/
					return false;
				}else{
					systemMessage("Updated successfully !!");
				}			 	
			}
		});
		}else if(user == 'teacher' || user =="admin"){
							$.ajax({
								type : "POST",
								url : "teachersaveOrUpdateBook.htm",
								data: formData,
								mimeType:"multipart/form-data",
								contentType: false,
								cache: false,
								processData:false,
								success : function(data) {
									$("#loading-div-background1").hide();
									if(operation == 'create'){
										systemMessage("Book Successfully Saved...");
										var i = setInterval(function(){ 
								  	       	clearInterval(i);
								  	       	window.parent.$('.ui-dialog-content:visible').dialog('close');
								  	       	window.parent.location.reload();
								  	        window.parent.document.getElementById( 'dailog' ).style.pointerEvents = 'auto'; 
							       	     	}, 2000);			
									}else{
										systemMessage("Updated Successfully !!");
									}		 	
								}
							});
						}
	}
}

function openReviewDialog(titleId, reviewId, index,page){
	
	var iMaxWidth = 580;
    $('#reviewDailog').width(iMaxWidth);
	var iHeight = $('#reviewDailog').height();
	$("#loading-div-background").show();
	      $("#reviewDailog").dialog({
	          autoOpen: true,
	          height: 500,
	          width: 580,
	          modal: true,
	          draggable: false,
	          resizable: true,
	          close: function (ev, ui) {
				$(this).dialog('destroy');
			    } 
	      });
	      var iCurrentHeight = $("#reviewDailog").height();
	      var iCurrentWidth = $("#reviewDailog").width();
	      if (iCurrentHeight < iHeight || iCurrentWidth < iMaxWidth) {
	          var oOffset = $("#reviewDailog").parent().offset();
	          $("#reviewDailog").parent()
	              .width(iMaxWidth)
	              .position({ 
	                  top: oOffset.top - ((iHeight - iCurrentHeight) / 2),
	                  left: oOffset.left - ((iMaxWidth - iCurrentWidth) / 2)
	              });
	         $('#reviewDailog').attr('title', 'Add Review').dialog();
	     	 var iframe = $('#reviewIframe');
	     	 iframe.attr('src', "openReviewDialog.htm?&titleId="+titleId+"&reviewId="+reviewId +"&index="+index+"&page="+page);
	         $("#reviewDailog").dialog("open");
			 $("#loading-div-background").hide();
	      }
	  

}
function openReviewPage(titleId, reviewId, index){
	var page=0;
	$("#loading-div-background").show();	
	//document.getElementById("hidethis").style.display = '';
	if(!reviewId)
		reviewId = $("#reviewId"+index).val();
	if(!reviewId)
		reviewId = 0;
	 $("#reviewDailog").dialog({
			overflow: 'auto',
			dialogClass: 'no-close',
		    autoOpen: false,
		    position: {my: "center", at: "center", of:window ,within: $("body") },
		    draggable: true,
		    width : 580,
		    height : 400,
		    resizable : true,
		    modal : true,
		    close: function (ev, ui) {
		    } 
		});
     $('#reviewDailog').attr('title', 'Add Review').dialog();
	 var iframe = $('#reviewIframe');
	 iframe.attr('src', "openReviewPage.htm?&titleId="+titleId+"&reviewId="+reviewId +"&index="+index+"&page="+page);
	 $("#reviewDailog").dialog("open");
	 $("#loading-div-background").hide();
}

function saveOrUpdateReview(index,page,action){
	console.log(index);
	console.log(page);
	console.log(action);
	var status = validateReviewForm();
	if(status){
		$("#loading-div-background1").show();
		var operation = $('#operation').val(); 	
		systemMessage(operation);
		var formObj = document.getElementById("addReviewForm");
		var formData = new FormData(formObj);
		$.ajax({
			type : "POST",
			url : "saveOrUpdateReview.htm",
			data: formData,
			mimeType:"multipart/form-data",
			contentType: false,
			cache: false,
			processData:false,
			success : function(data) {
				$("#loading-div-background1").hide();
				var parsedData = JSON.parse(data);
				systemMessage(parsedData.status);
				if(action == true){
					console.log('update');
					setTimeout(function() {
					  window.parent.jQuery('#reviewDailog').dialog('close');
					}, 1500);
				}	
				var titleId = $('#titleId').val();
				var regId = $('#regId').val();
				$('#reviewId').val(parsedData.reviewId);
				window.parent.$('#reviewId'+index).val(parsedData.reviewId);
				var rating = $('#rating').val();
				var actCount=$('#actCount').val();
				$('#actCount').val(actCount+1);
				
				if(page==0 && action == true){
					systemMessage("Book & Activity Sent For Approvals!");
					setTimeout(function() {
						window.parent.jQuery('#addBookDailog').dialog('close');
						window.parent.location.reload();
					}, 1500);
				}
				/*if(window.parent.$('#review_'+titleId+"_"+regId).attr("id")){
					window.parent.$('#review_'+titleId+"_"+regId).attr('#review');
				}else{
					 var dynaContent= "";
		    		 dynaContent = addStudentReviewDynamic(titleId, parsedData.reviewId, regId, rating);
		    		 var tbody = window.parent.$("#stdReviewTbl_"+titleId+" layout-header");
		    		 tbody.after(dynaContent);		 	    		 
				}
				window.parent.$('#review_'+titleId+"_"+regId).html($('#review').val());
				setParentSelectedStarts(titleId, regId, rating);*/
			}
		});
	}
}



function validateReviewForm(){
	var review = $('#review').val();
	if(review != ""){
		return true;
	}
	if(review !=0 && review != ""){
		return true;
	}
	else{
		systemMessage("please fill the fields");
		return false;
	}
}

function openRetellDialog(titleId,page){
	
	var iMaxWidth = 580;
	$('#retellDailog').width(iMaxWidth);
	var iHeight = $('#retellDailog').height();
	
	$("#loading-div-background").show();
	 $("#retellDailog").dialog({
		 autoOpen: true,
         height: 500,
         width: 580,
         modal: true,
         draggable: false,
         resizable: true,
         close: function (ev, ui) {
            $(this).dialog('destroy');
        	 
		    } 
		});
	 var iCurrentHeight = $("#retellDailog").height();
     var iCurrentWidth = $("#retellDailog").width();
     if (iCurrentHeight < iHeight || iCurrentWidth < iMaxWidth) {
         var oOffset = $("#retellDailog").parent().offset();
         $("#retellDailog").parent()
             .width(iMaxWidth)
             .position({ 
                 top: oOffset.top - ((iHeight - iCurrentHeight) / 2),
                 left: oOffset.left - ((iMaxWidth - iCurrentWidth) / 2)
       });
	 $('#retellDailog').attr('title', 'Add Retell').dialog();
	 var iframe = $('#retellIframe');
	 iframe.attr('src', "openRetellDialog.htm?titleId="+titleId+"&page="+page);
	 $("#retellDailog").dialog("open");
	 $("#loading-div-background").hide();
     }
}

var isRecording = false;
function audioRecording(){	
	$("#status").html('');
	if(isRecording == false){	
		 $('#retellActivityRecording').addClass('blink-text');
		 $("#status").attr("class", "recording");
		 recordAudio(function(){			    		
			 $('#status').fadeIn().html("<font face='courier' color='green'><b>Recording....</b></font>");
	    	isRecording = true;
    	 });
		 isRecording = true;
	}else{
	   alert("Recording already started. Click on Stop button to proceed further");  
	   return false;
	 }
} 

function audioStopping(){
	if(isRecording == true){
		$("#loading-div-background1").show();	
		$("#status").html('');
		playBackAudio(function(url){  
			//alert(url);
			$("#contentDiv").show();	
			$("#retellAudio").attr("src", url);
		})
		stopRecording(function(base64){			 
			var titleId = $('#titleId').val();	
			var regId = $('#regId').val();	
			var formData = new FormData();
			formData.append('audioData', base64);
			formData.append('titleId', titleId);
			console.log(formData);
			$.ajax({
				url: "uploadRRRecordRetell.htm",
			    type: 'POST',
			    data: formData,
			    contentType: false,
			    processData: false,
			    success: function() {
			    	$('#retellActivityRecording').removeClass('blink-text');
			    	isRecording = false;			    	
			    	$('#status').fadeIn().html("<font face='courier' color='red'><b>Stopped !!</b></font>");			    						
			    	/*if(window.parent.$('#retell_'+titleId+"_"+regId).attr("id")){
			    		window.parent.$('#retell_'+titleId+"_"+regId).attr("src","loadUserFile.htm?regId="+regId+"&usersFilePath=Reading_Register//"+titleId+"//Retell.wav&" + new Date().getTime());
			    		window.parent.$('#retell_'+titleId+"_"+regId).attr('style','visibility:visible;');
			    	}else{
			    	 var dynaContent= "";
			    		 dynaContent = addStudentReviewDynamic(titleId, 0, regId, 0);
			    		 var tbody = window.parent.$("#stdReviewTbl_"+titleId+" layout-header");
			    		 tbody.after(dynaContent);		 
					}*/
			    	setTimeout(function() {
						$('#status').fadeOut("slow");
					}, 500 );
		      	    $("#loading-div-background1").hide();
			    }
			});
		});
	 }else{
		 alert("Recording not yet started !!");
		 return false;
	 }
}

function checkFileExists(usersFilePath, regId, id) {
	//alert("checkfileexists="+usersFilePath);
	//usersFilePath = usersFilePath.replace(new RegExp(/\\/g),"/");
	 $("#loading-div-background1").show();
 	 $.ajax({
 		 type: "GET",
 	     url:"checkFileExists.htm",
 	     data: "usersFilePath="+usersFilePath+"&regId="+regId,
 	     success: function(status){
 	      $("#loading-div-background1").hide();
	  	      if(status == 'Exists'){
	  	    	var url = "loadUserFile.htm?regId="+regId+"&usersFilePath="+usersFilePath;
	  	    	//alert("checkfile url="+url);
	  	    	$("#"+id).attr("src", url);
	  	    	$("#contentDiv").show();	  	    	
	  	      }
 	     }
 	 });
}

function checkRRFileExists(usersFilePath, id) { 
	 $("#loading-div-background1").show();
	 $.ajax({
		 type: "GET",
	     url:"checkRRFileExists.htm",
	     data: "usersFilePath="+usersFilePath,
	     success: function(status){
	      $("#loading-div-background1").hide();
	  	      if(status == 'Exists'){
	  	    	var url = "loadRRFile.htm?usersFilePath="+usersFilePath;
	  	    	$("#"+id).attr("src", url);
	  	    	$("#contentDiv").show();	  	    	
	  	      }
	     }
	 });
}
function openPictureDialog(titleId,page){
	var iMaxWidth = 600;
	$('#pictureDailog').width(iMaxWidth);
	var iHeight = $('#pictureDailog').height();
	
	$("#loading-div-background").show();
	 $("#pictureDailog").dialog({
		 autoOpen: true,
         height: 500,
         width: 600,
         modal: true,
         draggable: false,
         resizable: true,
         close: function (ev, ui) {
        	 $(this).dialog('destroy'); 
         } 
		});
	 
	 var iCurrentHeight = $("#pictureDailog").height();
     var iCurrentWidth = $("#pictureDailog").width();
     if (iCurrentHeight < iHeight || iCurrentWidth < iMaxWidth) {
         var oOffset = $("#pictureDailog").parent().offset();
         $("#pictureDailog").parent()
             .width(iMaxWidth)
             .position({ 
                 top: oOffset.top - ((iHeight - iCurrentHeight) / 2),
                 left: oOffset.left - ((iMaxWidth - iCurrentWidth) / 2)
       });
         
	 $('#pictureDailog').attr('title', 'Add Picture').dialog();
	 var iframe = $('#pictureIframe');
	 iframe.attr('src', "openPictureDialog.htm?titleId="+titleId+"&page="+page);
	 $("#pictureDailog").dialog("open");
	 $("#loading-div-background").hide();
     }
	
}
/*function openPictureDialog(titleId,mode){
	$("#loading-div-background").show();
	$.ajax({
		type : "GET",
		url : "openPictureDialog.htm",
		data : "titleId="+titleId,
		success : function(response) {
	var dailogContainer = $(document.getElementById('pictureDailog'));
	var screenWidth = $( window ).width() - 220;
	var screenHeight = $( window ).height() - 220;
	$('#pictureDailog').empty();  
	$(dailogContainer).append(response);
	$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
		  $(".ui-dialog-titlebar-close").show();
	},close: function (ev, ui) { 
		$(this).dialog('close');  
		$(this).empty()
	},
	dialogClass: 'myTitleClass'
	});		
	$(dailogContainer).dialog("option", "title", "Upload Picture");
	$(dailogContainer).scrollTop("0");	
	 $("#loading-div-background").hide();
}
});
}*/

function validateImageFileType(st){
	
	var fup = document.getElementById('file');
	var fileName = fup.value;
	var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
	if(ext == "gif" || ext == "GIF" || ext == "JPEG" || ext == "jpeg" || ext == "jpg" || ext == "JPG" || ext == "png" || ext == "PNG"){
		return true;
	}
	else{
		systemMessage("Upload gif or png or jpg images only");
		fup.value="";
		if(st=="create")
			$("#preview").attr("src", "");
		fup.focus();
		return false;
	}

}

function uploadRRPicture(st) {
	var status = validateImageFileType(st);
	if(status){
		var formObj = document.getElementById("uploadPictureForm");
		var formData = new FormData(formObj);
		var titleId = $('#titleId').val();			
		formData.append('titleId', titleId);
		$("#rr-pic-loading-div-background").show();
		$.ajax({
			url: 'uploadRRPicture.htm',
			type: 'POST',
			data: formData,
			mimeType:"multipart/form-data",
			contentType: false,
			cache: false,
			processData:false,
			success: function(data){
				var parsedData = JSON.parse(data);
				systemMessage(parsedData.status);	
				var regId = $('#regId').val();				
				var usersFilePath = parsedData.usersFilePath;
				checkFileExists(usersFilePath, regId, "preview");
				/*if(window.parent.$('#imgDiv_'+titleId+"_"+regId).attr("id")){
					window.parent.$('#iconDiv_'+titleId+"_"+regId).hide();
					window.parent.$('#imgDiv_'+titleId+"_"+regId).show();
					window.parent.$('#imgDiv_'+titleId+"_"+regId).attr("src","loadUserFile.htm?regId="+regId+"&usersFilePath=Reading_Register//"+titleId+"//Picture.jpg");
				}else{
					 var dynaContent= "";
		    		 dynaContent = addStudentReviewDynamic(titleId, 0, regId, 0);
		    		 var tbody = window.parent.$("#stdReviewTbl_"+titleId+" layout-header");
		    		 tbody.after(dynaContent);		 
				}*/
				checkUserProfilePic(titleId, regId, usersFilePath, "profile_pic");
				$("#rr-pic-loading-div-background").hide();
			}
		});	
	}
	
}


function openCreateQuizDialog(titleId,page){
	
	var iMaxWidth = 580;
	$('#createQuizDailog').width(iMaxWidth);
	var iHeight = $('#createQuizDailog').height();
	
	$("#loading-div-background").show();
	 $("#createQuizDailog").dialog({
		 autoOpen: true,
         height: 400,
         width: 580,
         modal: true,
         draggable: false,
         resizable: true,
         close: function (ev, ui) {
        	 $(this).dialog('destroy'); 
		    } 
		});
	 
	 var iCurrentHeight = $("#createQuizDailog").height();
     var iCurrentWidth = $("#createQuizDailog").width();
     if (iCurrentHeight < iHeight || iCurrentWidth < iMaxWidth) {
         var oOffset = $("#createQuizDailog").parent().offset();
         $("#createQuizDailog").parent()
             .width(iMaxWidth)
             .position({ 
                 top: oOffset.top - ((iHeight - iCurrentHeight) / 2),
                 left: oOffset.left - ((iMaxWidth - iCurrentWidth) / 2)
       });
         
	 $('#createQuizDailog').attr('title', 'Create Quiz').dialog();
	 var iframe = $('#createQuizIframe');
	 iframe.attr('src', "openCreateQuizDialog.htm?titleId="+titleId+"&page="+page);
	 $("#createQuizDailog").dialog("open");
	 $("#loading-div-background").hide();
     }
	
	
	 /*$.ajax({
			type : "GET",				
			url : "openCreateQuizDialog.htm",
			data : "titleId="+titleId,
			async: true,
			success : function(response) {
				var screenWidth = $( window ).width()-15; //var screenWidth = $( window ).width()/2;
				var screenHeight = $( window ).height()-20; //var screenHeight = $( window ).height()/2;
				$('#createQuizDailog').empty();  
				$("#createQuizDailog").html(response);
				$("#createQuizDailog").dialog({width: screenWidth, height: screenHeight,modal: true,
					open:function () {
						$(".ui-dialog-titlebar-close").show();
					},
					close: function(event, ui){
						$(this).empty();  
						$("#createQuizDailog").dialog('destroy');
				    }
				});		
				$("#createQuizDailog").dialog("option", "title", "Create Quiz");
				$("#createQuizDailog").scrollTop("0");
				$("#loading-div-background").hide();
			}
		}); */
	}

function validateQuestion(){
	var allFilled = true;    
    $(':input:not(:button)').each(function(index, element) {
        if (element.value === ''  && typeof(element.value.length) != 'undefined') {
            allFilled = false;
            $(this).css({
                "border": "1px solid red"
             });
        }
    });
	return allFilled;
}

function saveCreateQuestions(count,page){
	console.log('saveCreateQuestions');
	console.log(page);
	console.log(count);
	var status = validateQuestion();
	if(status == true){
	if(count <= 10){
		var formObj = document.getElementById("createQuestionsForm");
		var formData = new FormData(formObj);
		formData.append("rating", $("#rating").val());
		
		formData.append('page', page);
		$.ajax({
			url: 'saveCreateQuestions.htm',
			type: 'POST',
			data: formData,
			contentType: false,
			cache: false,
			processData:false,
			success: function(data){
				$("#questBut"+count).removeClass("round-button");
				$("#questBut"+count).addClass("round-done-button");
				if(count <10){
					$("#pageContent").empty();
					$("#pageContent").html(data);						
				}
				else if(count==10){
					
					if(page==0){
						systemMessage("Book & Activity Sent For Approvals!");
						setTimeout(function() {
							window.close();
							window.parent.jQuery('#createQuizDailog').dialog('close');
							window.parent.jQuery('#addBookDailog').dialog('close');
							window.parent.location.reload();
						}, 1500);
					}else{
						systemMessage("Activity is completed successfully");
						setTimeout(function() {
						  window.parent.jQuery('#createQuizDailog').dialog('close');
						}, 1500);  
					}
					
					/*$("#returnMessage").empty();
					$("#returnMessage").html("Activity is completed successfully");
					$('#returnMessage').fadeIn();
					$('#returnMessage').fadeOut(4000);*/
				    
				}
				var actCount=$('#actCount').val();
				$('#actCount').val(actCount+1);
				
			}
		});	
	}
	}else{
		systemMessage("Please fill all the fields");
	}
	
	/*if(count==10){
		console.log("ques10");
		if(page==0){
			systemMessage("Book & Activity Sent For Approvals!");
			setTimeout(function() {
				window.parent.jQuery('#createQuizDailog').dialog('close');
				window.parent.jQuery('#addBookDailog').dialog('close');
				window.parent.location.reload();
			}, 1500);
		}
	}*/
}

function updateQuizQuestions(count){
	var status = validateQuestion();
	if(status == true){
	    $(':input:not(:button)').each(function(index, element) {
	            $(this).css({"border": ""});
	    });
		var formObj = document.getElementById("createQuestionsForm");
		var formData = new FormData(formObj);
		formData.append('count', count.dataset.count);		
		$.ajax({
			url: 'updateQuizQuestions.htm',
			type: 'POST',
			data: formData,
			contentType: false,
			cache: false,
			processData:false,
			success: function(data){
				systemMessage(data.status);
			}
		});	
	}else{
		systemMessage("Please fill all the fields");
	}
}

function openTakeQuizDialog(titleId){
	var iMaxWidth = 850;
	$('#takeQuizDailog').width(iMaxWidth);
	var iHeight = $('#takeQuizDailog').height();
	
	$("#loading-div-background").show();
	 $("#takeQuizDailog").dialog({
		 autoOpen: true,
         height: 630,
         width: 850,
         modal: true,
         draggable: false,
         resizable: true,
         close: function (ev, ui) {
        	 $(this).dialog('destroy');
		    } 
		});
	 
	 var iCurrentHeight = $("#takeQuizDailog").height();
     var iCurrentWidth = $("#takeQuizDailog").width();
     if (iCurrentHeight < iHeight || iCurrentWidth < iMaxWidth) {
         var oOffset = $("#takeQuizDailog").parent().offset();
         $("#takeQuizDailog").parent()
             .width(iMaxWidth)
             .position({ 
                 top: oOffset.top - ((iHeight - iCurrentHeight) / 2),
                 left: oOffset.left - ((iMaxWidth - iCurrentWidth) / 2)
       });
	 $('#takeQuizDailog').attr('title', 'Take Quiz').dialog();
	 var iframe = $('#takeQuizIframe');
	 iframe.attr('src', "openTakeQuizDialog.htm?titleId="+titleId);
	 $("#takeQuizDailog").dialog("open");
	 $("#loading-div-background").hide();
     }
}

function getStudentAllQuizQuestionByTitleId(){
	var titleId = $('#titleId').val();	
	var studentId =$('#studentId').val();	
    if(studentId != "select"){		
		 $.ajax({
	 		 type: "GET",
	 	     url:"getStudentAllQuizQuestionByTitleId.htm",
	 	    data: "titleId="+titleId+"&studentId="+studentId,			
	 	     success: function(data){
	 	    	 $('#showQuizDiv').html(data);				
	 	     }
	 	 });
	}else{
		$('#showQuizDiv').html("");	
		systemMessage("Please select a student");
	}
}

function submitQuizAnswers(){
	var blank = false;
	$("input:radio").each(function() {
	    var val = $('input[name="'+this.name+'"]:checked').val();
	    if (val === undefined) {
	        blank = true;	       
	        return false;
	    }
	});	
	if(blank == false){
		var formObj = document.getElementById("takeQuizForm");
		var formData = new FormData(formObj);
		formData.append("rating", $("#rating").val());
		formData.append("titleId",$("#titleId").val());
		$.ajax({
			url: 'submitQuizAnswers.htm',
			type: 'POST',
			data: formData,
			contentType: false,
			cache: false,
			processData:false,
			success: function(data){
				systemMessage(data.status);
				setTimeout(function() {
				  window.parent.jQuery('#takeQuizDailog').dialog('close');
				},1500);
				
				var corrects = 0;
				for (i = 0; i < data.readRegAnswersLt.length; i++) { 
					var studentAnswer = data.readRegAnswersLt[i].answer;
					var correctAnswer = data.readRegAnswersLt[i].readRegQuestions.answer;
					if(studentAnswer == correctAnswer){
						$('input[name="readRegAnswersLt['+i+'].answer"]:checked').parent().css("color","green");
						corrects = corrects+1;
					}else{
						$("#correctAnswer"+i).css("display", "block");
						$('input[name="readRegAnswersLt['+i+'].answer"]:checked').parent().css("color","red");
					}
				}
				var str = "<font color='green'>Corrects:</font> &nbsp;&nbsp;"+corrects+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color='red'>Wrongs:</font> &nbsp;&nbsp;"+(5-corrects);
				$('#showResultDiv').html(str);
				$('#submitDiv').hide();
			}
		});	
	}else{
		 alert("Please answer all the questions");
	}
}

function setParentSelectedStarts(titleId,regId, rating){
	window.parent.$('#stars_'+titleId+'_'+regId+'  li').each(function (i) {
		  $(this).removeClass('selected');
		  if(i < rating)
	       $(this).addClass('selected');
	 });
}

/*function addStudentReviewDynamic(titleId, reviewId, regId, rating){
	var dynaContent= "";
	dynaContent= "<layout-row style='border-bottom: 1pt solid #d1dee0;cursor:pointer;'>"+
   	"<layout-column width='10%'>"+
	   	"<img id='imgDiv_"+titleId+"_"+regId+"' class='imgCls' style='display:none;width: 70px;height: 70px;' onClick='openPreviewImage()'/></a>"+
		"<div id='iconDiv_"+titleId+"_"+regId+"' class='fa fa-file-image-o' aria-hidden='true' style='display:block;font-size: 50px;text-shadow: 0 1px 4px rgb(140, 156, 158);color:#00cae2;margin-top:5px;margin-bottom:5px;'></div>"+
   	"</layout-column>"+
   	"<layout-column width='30%' align='justify' style='font-weight:normal;text-align:justify;' onclick='openReviewDialog("+titleId+","+reviewId+",0)' id='review_"+titleId+"_"+regId+"'><input type='hidden' name='rate_"+titleId+"_"+regId+"' id='rate_"+titleId+"_"+regId+"' value='"+rating+"' /></layout-column>"+
    "<layout-column width='25%' align='center' onclick='openRetellDialog("+titleId+")' onclick='openRetellDialog("+titleId+")'><audio id='retell_"+titleId+"_"+regId+"' src='loadUserFile.htm?regId="+regId+"&usersFilePath=Reading_Register//"+titleId+"//Retell.wav' controls='' preload='metadata' controlslist='nodownload' style='width: 200px;visibility:hidden;'><sourc src='' type='audio/wav'></audio></layout-column>"+
    "<layout-column width='15%' align='center' onclick='openReviewDialog("+titleId+","+reviewId+",0)' align='right'>"+
        "<div class='rating-widget'>"+  
		  "<div class='rating-stars'>"+
		    "<ul id='stars_"+titleId+"_"+regId+"'>"+
		      "<li class='star' data-value='1'>"+
		        "<i class='fa fa-star fa-fw'></i>"+
		      "</li>"+
		      "<li class='star' data-value='2'>"+
		        "<i class='fa fa-star fa-fw'></i>"+
		      "</li>"+
		      "<li class='star' data-value='3'>"+
		        "<i class='fa fa-star fa-fw'></i>"+
		      "</li>"+
		      "<li class='star' data-value='4'>"+
		        "<i class='fa fa-star fa-fw'></i>"+
		      "</li>"+
		      "<li class='star' title='WOW!!!' data-value='5'>"+
		        "<i class='fa fa-star fa-fw'></i>"+
		      "</li>"+
		    "</ul>"+
		  "</div>"+
		"</div>"+
    "</layout-column>"+						   					   
"</layout-row>";
return dynaContent;		      
}*/

function checkUserProfilePic(titleId, regId, usersFilePath, type) {
	 if(regId > 0 && titleId > 0){
		 $("#loading-div-background").show();
		 $.ajax({
			 type: "GET",
		     url:"checkParentSignExists.htm",
		     data: "regId="+regId+"&usersFilePath="+usersFilePath,
		     success: function(usersFilePath){
		      $("#loading-div-background").hide();
		      if(usersFilePath){
		    	  if(type == "profile_pic"){
		    		 $('#iconDiv_'+titleId+'_'+regId).hide();
			    	 $('#imgDiv_'+titleId+'_'+regId).attr("src","loadDirectUserFile.htm?usersFilePath="+usersFilePath);
			    	 $('#imgDiv_'+titleId+'_'+regId).show();
		    	  }else if(type == "retell_audio"){
		    		 $('#retell_'+titleId+'_'+regId).attr("src","loadDirectUserFile.htm?usersFilePath="+usersFilePath);
				     $('#retell_'+titleId+'_'+regId).attr('style','visibility:visible;');
		    	  }			  
		      }else{
		    	  if(type == "profile_pic"){
			    	  $('#iconDiv_'+titleId+'_'+regId).show();  
			    	  $('#imgDiv_'+titleId+'_'+regId).hide();
			    	  $('#remove_'+titleId+'_'+regId).hide();
		    	  }else if(type == "retell_audio"){
		    		  $('#retell_'+titleId+'_'+regId).attr('style','visibility:hidden;');
		    	  }
		      }
		     }
		 });
	  }
}

function sortActivities(){
	var studentId = $("#studentId").val();
	var gradeId = $("#gradeId").val();
	var sortBy = $("#sortBy").val();
	if(studentId != "select")
	{
		$.ajax({
			type : "POST",
			url : "sortActivities.htm",
			data : "studentId=" + studentId+"&sortBy="+sortBy,
			success : function(response) {
				$("#subContentDiv").empty();
				$("#subContentDiv").append(response);
			}
		});
	}
	else{
		alert("please select the Student");
		$("#contentDiv").empty();
		return false;
	}
}

function parentSortActivities(){
	var studentId = $("#studentId").val();
	var gradeId = $("#gradeId").val();
	var sortBy = $("#sortBy").val();
	if(studentId != "select")
	{
		$.ajax({
			type : "POST",
			url : "parentSortActivities.htm",
			data : "studentId=" + studentId+"&sortBy="+sortBy,
			success : function(response) {
				$("#subContentDiv").empty();
				$("#subContentDiv").append(response);
			}
		});
	}
	else{
		alert("please select the Student");
		$("#contentDiv").empty();
		return false;
	}
}



function saveOrUpdateRetell(page){	
	console.log('in saveOrUpdateRetell ');
	console.log(page);
	var retellAudio = document.getElementById("retellAudio").duration;
	if(isNaN(retellAudio)){
		alert("Please give the Retell");
	}
    else
    {
		$("#loading-div-background1").show();
		var operation = $('#operation').val(); 
		var formData = new FormData();
		if($("#studentGradeId").val() < 3 || $("#studentGradeId").val() >= 13 )
			formData.append("rubricScore" , 0);
		else
		formData.append("rubricScore", $("#rubricScore").val());
		formData.append("titleId", $("#titleId").val());
		formData.append("mode", $("#operation").val());
		formData.append("rating", $("#rating").val());
		$.ajax({
			type : "POST",
			url : "saveOrUpdateRetell.htm",
			data: formData,
			mimeType:"multipart/form-data",
			contentType: false,
			cache: false,
			processData:false,
			success : function(data) {
				systemMessage(data);
				$("#loading-div-background1").hide();				
				var parsedData = JSON.parse(data);
				systemMessage(parsedData.status);
				if(page !== 0){
					setTimeout(function() {
					  window.parent.jQuery('#retellDailog').dialog('close');
					}, 1500);  
				}	
				if(page==0){
					systemMessage("Book & Activity Sent For Approvals!");
					setTimeout(function() {
						window.parent.jQuery('#addBookDailog').dialog('close');
						window.parent.location.reload();
					}, 1500);
				}
				
			}
		});		
    }
}
		
		

function saveOrUpdatePicture(st,page){
	var canvas = document.getElementById('isCaptured').value;
	var status = uploadRRPicture(st);
	if(st=="create"){
		var fup = document.getElementById('file');
		
		var fileName = fup.value;
		if (fileName == '' && canvas == 'false' ) {
			alert("Please upload an image or take a snapshot");
	        return false;
	    }
	}
	if(status = true){
		$("#loading-div-background1").show();	
		var formData = new FormData();
		if($("#studentGradeId").val() < 3 || $("#studentGradeId").val() >= 13)
			formData.append("rubricScore" , 0);
		else
			formData.append("rubricScore", $("#rubricScore").val());
		formData.append("titleId", $("#titleId").val());
		formData.append("mode", $("#operation").val());
		formData.append("rating", $("#rating").val());
		$.ajax({
			type : "POST",
			url : "saveOrUpdatePicture.htm",
			data: formData,
			mimeType:"multipart/form-data",
			contentType: false,
			cache: false,
			processData:false,
			success : function(data) {
				$("#loading-div-background1").hide();
				var parsedData = JSON.parse(data);
				systemMessage(parsedData.status);
				var ifram=document.getElementById("pictureIframe");
				if(page !== 0){
					setTimeout(function() {
					  window.parent.jQuery('#pictureDailog').dialog('close');
					}, 1500);  
				}	
				/*ifram.parent.removeChild(iframe);*/
				/*$('#pictureIframe').remove();
				$("#pictureDailog").dialog('close'); */
				/*if(window.parent.$('#imgDiv_'+titleId+"_"+regId).attr("id")){
					window.parent.$('#iconDiv_'+titleId+"_"+regId).hide();
					window.parent.$('#imgDiv_'+titleId+"_"+regId).show();
					window.parent.$('#imgDiv_'+titleId+"_"+regId).attr("src","loadUserFile.htm?regId="+regId+"&usersFilePath=Reading_Register//"+titleId+"//Picture.jpg&" + new Date().getTime());
				}else{
					 var dynaContent= "";
		    		 dynaContent = addStudentReviewDynamic(titleId,0,regId,0);
		    		 var tbody = window.parent.$("#stdReviewTbl_"+titleId+" layout-header");
		    		 tbody.after(dynaContent);		 
				}*/
				//systemMessage("Book & Activity Sent For Approvals!");
				if(page==0){
					systemMessage("Book & Activity Sent For Approvals!");
					setTimeout(function() {
						window.parent.jQuery('#addBookDailog').dialog('close');
						window.parent.location.reload();
					}, 1500);
				}
			}
		});
	}
}

function goToQuestion(questionNum,page){
	var formData = new FormData();
	formData.append("questionNum", questionNum);
	formData.append("titleId", $("#titleId").val());
	formData.append("page",page);
		$.ajax({
			url: 'goToQuestion.htm',
			type: 'POST',
			data: formData,
			contentType: false, 
			cache: false,
			processData:false,
			success: function(data){
				$("#pageContent").empty();
				$("#pageContent").html(data);	
			}
	});	
}

// Teacher's Review page functions

function getActivityDetails(readRegActivityScoreId,bookTitleId){
	
	var formData = new FormData();
	formData.append("bookTitleId", bookTitleId);
	$.ajax({
		type : "POST",
		url : "getBookApproveStatus.htm",
		data: formData,
		mimeType:"multipart/form-data",
		contentType: false,
		cache: false,
		processData:false,
		success : function(data) {
			if(data=='returned'){
				systemMessage("Book Returned");
			}else{
			 $("#activityDailog").dialog({
					overflow: 'auto',
					dialogClass: 'no-close',
				    autoOpen: false,
				    position: {my: "center", at: "center", of:window ,within: $("body") },
				    draggable: true,
				    width : 900,
				    height : 550,
				    resizable : true,
				    modal : true,
				    close: function (ev, ui) {
				    	getUnGradedActivites();
				    } 
				});
		  $('#activityDailog').attr('title', 'Review Activity').dialog();
			 var iframe = $('#activityIframe');
			 iframe.attr('src', "openActivityDialog.htm?readRegActivityScoreId="+readRegActivityScoreId);
			 $("#activityDailog").dialog("open");
			}
			
		}
	});
	
	
}

function saveScore(){
	var radioStatus = $("input[name='activityApprove']:checked").val();
	var stat=1;
	var approveId=$('#approveId').val();
	var bookAppStat=$('#bookAppStat').val();
	var approveStatus=$('#approveStatus').val();
	var accApproveStatus=$('#accApproveStatus').val();
	//var activityAppStat=$('#activityAppStat').val();
	var readRegActivityScoreId = $("#readRegActivityScoreId").val();
	var bookTitleId=$('#bookTitleId').val();
	var array = $("#scoreId").val().split("-");
	var scoreId = array[0];
	var score = array[1];
	var pageRange = $("#pageRange").val();
	var activityValue = $("#activityValue").val();
	var formData = new FormData();
	formData.append('readRegActivityScoreId', readRegActivityScoreId);
	formData.append('scoreId', scoreId);	
	formData.append('score', score);	
	formData.append('pageRange', pageRange);	
	formData.append('activityValue', activityValue);	
	formData.append('bookApproveStatus',bookAppStat);
	formData.append('activityAppStat',radioStatus);
	formData.append('bookTitleId',bookTitleId);
	formData.append('approveId',approveId);
	var teacherComment=$('#teacherComment').val();
	systemMessage(teacherComment);
	if(teacherComment=="" || teacherComment==null){
		stat=0;
		systemMessage("Comment should not be empty");
		return false;
	}
	formData.append('teacherComment',teacherComment);
  /*  if(bookAppStat=='returned' || bookAppStat=='approved'){
    	var teacherComment=$('#teacherComment').val();
    	systemMessage(teacherComment);
    	if(teacherComment=="" || teacherComment==null){
    		stat=0;
    		systemMessage("Comment should not be empty");
    		return false;
    		
    	}
    	
    	formData.append('teacherComment',teacherComment);
 	}
    if(radioStatus=='returned' || radioStatus=='approved'){
    	var teacherComment=$('#teacherComment').val();
    	systemMessage(teacherComment);
    	if(teacherComment=="" || teacherComment==null){
    		stat=0;
    		systemMessage("Comment should not be empty");
    		return false;
    	}
    	formData.append('teacherComment',teacherComment);
 	}*/
    
	$("#loading-div-background").show();
	
	if(stat>0){
	$.ajax({
		type : "POST",
		url : "saveScore.htm",
		data: formData,
		contentType: false,
		cache: false,
		processData:false,
		success : function(data) {
			$("#loading-div-background").hide();
			if(data.stat && data.status && approveStatus=='waiting'){
				systemMessage("Book Approved and Score saved successfully");}
			else if(data.stat && data.status && approveStatus=='approved'){
				if(radioStatus=='approved')
				  systemMessage("Activity Approved and Score saved successfully");
				else
				  systemMessage("Activity Returned");
			}
		    else
				systemMessage("Book Returned");
			
			setTimeout(function() {
				window.parent.jQuery('#activityDailog').dialog('close');
			}, 3000);
					
			/*if ($("#activityDailog").hasClass("ui-dialog-content")){
				$('#activityDailog').dialog('close');
				
			}*/
			
		}
	});

}
		
}

function getStudentsByGradeId(gradedStatus, callback){
	var obj =	document.getElementById("gradeId");
	  if(obj){
		  var gradeId = obj.value;
		  if(gradeId  != 'select'){
				$("#loading-div-background").show();
				$.ajax({
					type : "GET",
					url : "getRRStudentsByGrade.htm",
					data : "gradeId=" + gradeId + "&gradedStatus=" +gradedStatus,
					success : function(response) {
						var students = response.students;
						$("#studentId").empty();
						$("#studentId").append($("<option></option>").
								val('select').html(
								'Select Student'));
						if(students.length>0)
						{
							$("#studentId").append($("<option></option>").
									val('0').html('ALL'));
						$.each(students, function(index, value) {
							$("#studentId").append($("<option></option>").
								val(value.studentId).html(
								value.userRegistration.firstName +" "+ value.userRegistration.lastName));
						});
						}
						$("#loading-div-background").hide();
						if(callback)
						 callback();
					}
				}); 
		  }
		  else{
		  	alert("please select the Grade");
			$("#contentDiv").empty();
			$("#studentId").empty();
			$("#studentId").append($("<option></option>").val("select").html('Select Student'));
		   }

	  }
}

function getStudentsForRRReports(callback){
	var obj =	document.getElementById("gradeId");
	  if(obj){
		  var gradeId = obj.value;
		  if(gradeId  != 'select'){
				$("#loading-div-background").show();
				$.ajax({
					type : "GET",
					url : "getRRStudentsByGrade.htm",
					data : "gradeId=" + gradeId +"&gradedStatus='graded'",
					success : function(response) {
						var students = response.students;
						$("#studentId").empty();
						$("#studentId").append($("<option></option>").
								val('select').html(
								'Select Student'));
						if(students.length>0)
							{
							$("#studentId").append($("<option></option>").
									val('0').html('ALL'));
						$.each(students, function(index, value) {
							$("#studentId").append($("<option></option>").
								val(value.studentId).html(
								value.userRegistration.firstName +" "+ value.userRegistration.lastName));
						});
							}
						$("#loading-div-background").hide();
						if(callback)
						 callback();
					}
				}); 
		  }
		  else{
		  	alert("please select the Grade");
			$("#contentDiv").empty();
			$("#studentId").empty();
			$("#studentId").append($("<option></option>").val("select").html('Select Student'));
		   }

	  }
}

function getActivityScore(readRegActivityScoreId, dialogClose= undefined){
	 $("#activityScoreDailog").dialog({
			overflow: 'auto',
			dialogClass: 'no-close',
		    autoOpen: false,
		    position: {my: "center", at: "center", of:window ,within: $("body") },
		    draggable: true,
		    width : 1000,
		    height : 600,
		    resizable : true,
		    modal : true,
		    close: function (ev, ui) {
		    	if(dialogClose) {
		    		getGradedActivites();
		    	}
		    } 
		});
 $('#activityScoreDailog').attr('title', 'Activity').dialog();
	 var iframe = $('#activityScoreIframe');
	 iframe.attr('src', "openActivityScoreDialog.htm?readRegActivityScoreId="+readRegActivityScoreId);
	 $("#activityScoreDailog").dialog("open");
}

function getBooksToApprove(){
	var gradeId = $("#gradeId").val();
	var academicYear = $("#academicYear").val();
	$('#contentDiv').html("");
	if(gradeId != "select" && academicYear != 'select'){
		$("#loading-div-background").show();
		$.ajax({
			type : "GET",
			url : "getBooksToApprove.htm",
			data : "gradeId=" + gradeId+ "&academicYearId=" + academicYear,
			success : function(response) {
				//var content = "";
				$("#contentDiv").empty();
				$("#contentDiv").append(response);
				/*var user = response.user;
				var readRegMasterLt = response.readRegMasterLt;
				if(readRegMasterLt.length > 0){
					content += "<input type='hidden' id='user' value='"+user+"'/>" +
					  "<table id='booksToApprove' width=95% class='des' style='font-size:13px;margin-top:.5em;font-family:\"Lato\", \"PingFang SC\", \"Microsoft YaHei\", sans-serif;'>"+
					      "<thead class='Divheads' style='text-shadow: none;color: #551a8b;'><tr align='center' height='60'>" +
						  "<td width=5%>S.No</td>" +
						  "<td width=20%>Book Title</td>" +
						  "<td width=20%>Author</td>" +
						  "<td width=10%>Pages</td>" +
						  "<td width=15%>Created By</td>" +
						  "<td width=20% align='center'>Approve</td>" +
						  "<td width=20%>Edit &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Delete</td>" +
					  "</tr></thead><tbody class='DivContents'>";
				}else{
					content += "<span class='status-message'> No data found </span>";
				}
				for (var i = 0; i < readRegMasterLt.length; i++) {
					var readRegMaster = readRegMasterLt[i];
					 content += "<tr class='txtStyle' id='tr"+readRegMaster.titleId+"' align='center' height='30'>" +
							"<td width=5%>"+(i+1)+"</td>" +
							"<td width=20%>"+readRegMaster.bookTitle+"</td>" +
							"<td width=20%>"+readRegMaster.author+"</td>" +
							"<td width=10%>"+readRegMaster.numberOfPages+"</td>" +
							"<td width=15%>"+readRegMaster.userRegistration.firstName+" "+readRegMaster.userRegistration.lastName+"</td>" +
							"<td width=20%><input type=radio name='approve"+readRegMaster.titleId+"' value='approved' "+ (readRegMaster.approved == 'approved' ? 'checked' : '')+" "+ (readRegMaster.approved != 'waiting' ? 'disabled=true' : '')+" onClick='bookApprove("+readRegMaster.titleId+")'>Approve &nbsp;<input type=radio name='approve"+readRegMaster.titleId+"' value='returned' "+ (readRegMaster.approved != 'waiting' ? 'disabled=true' : '')+"  "+ (readRegMaster.approved == 'returned' ? 'checked' : '')+" onClick='bookApprove("+readRegMaster.titleId+")'>Reject</td>" +
							"<td width=20%><i class='fa fa-pencil-square-o' aria-hidden='true' style='margin-left:-0.5em;cursor: hand; cursor: pointer;font-size: 22px;font-weight:bold;color:#185C64;' onclick='openNewBookDialog("+readRegMaster.titleId+")' ></i> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <i class='fa fa-times' aria-hidden=true style='margin-left:1em;cursor: hand; cursor: pointer;font-size: 18px;color:#c70000;' onclick='deleteBook("+readRegMaster.titleId+")'></i></td>" +
						 "</tr>";							
				}
				content += "</tbody></table>";
				$('#contentDiv').html(content);
				if(readRegMasterLt.length > 0){
					TSort_Data = new Array ('booksToApprove', 'i', 's', 's', 'i','s', '');
				    tsRegister();
				    tsSetTable('booksToApprove');
				    tsInit();    
				}*/
				$("#loading-div-background").hide();
			}
		}); 
	}else{
		$('#contentDiv').html("");
	}
}

function bookApprove(titleId){
	 if(titleId > 0){
		 var radioStatus = $("input[name='approve"+titleId+"']:checked").val();
		 var approved = $("input[name='approve"+titleId+"']:checked").val();
		 if(radioStatus == 'returned'){
			  if(confirm("Are you Sure to Reject?",function(status){
					if(status){				
						$.ajax({
							type : "POST",
							url : "updateAprrovalStatus.htm",
							data: "titleId="+titleId+"&status="+radioStatus,
							success : function(data) {
								systemMessage(data);
							}
						});
					}else{
						return false;
					}
			})); 
		 }
		 else if(approved == 'approved'){
			 if(confirm("Are you Sure for approval?",function(status){
				 if(status){				
					 $.ajax({
						 type : "POST",
						 url : "updateAprrovalStatus.htm",
						 data: "titleId="+titleId+"&status="+radioStatus,
						 success : function(data) {
							 systemMessage(data);
						 }
					 });
				 }else{
					 return false;
				 }
			 })); 
		 	}else{
			 $.ajax({
					type : "POST",
					url : "updateAprrovalStatus.htm",
					data: "titleId="+titleId+"&status="+radioStatus,
					success : function(data) {
						systemMessage(data);
					}
			 });
		}
	  }	
	}
function deleteBook(titleId,sortBy, sortingOrder,pageNumber){
  if(confirm("Are you sure to delete?",function(status){
	  if(status){	
		 $.ajax({
			type : "POST",
			url : "deleteBook.htm",
			data: "titleId="+titleId,
			success : function(data) {
				systemMessage(data);
				$('#tr'+titleId).hide();
				gotoPages(pageNumber, sortBy, sortingOrder);	
			}
		 });
	  }else{
			return false;
	  }
  })); 
}

function toggleBankDetails(){
	if($("#bookBank").is(':checked')){
		$('#bookBank').prop('checked', true);
		$('#bankDetails').show();
		$('#pagination').show();
		setFooterHeight();
	}else{
		$('#bookBank').prop('checked', false);
		$('#bankDetails').hide();
		$('#pagination').hide();
	}
	
}
function setBookApproval(){
	var radioStatus = $("input[name='bookApprove']:checked").val();
	if(radioStatus== 'approved'){
		$('#finalScore').css("visibility", "visible");
		document.getElementById("bookAppStat").value="approved";
	}else if(radioStatus== 'returned'){
		$('#finalScore').css("visibility", "hidden");
		document.getElementById("bookAppStat").value="returned";
		$('#spaceId').remove();
	}
}
function closeDailog(){
	window.parent.location.reload();
}
function closeActivityDailog(titleId,stat){
	
	$.ajax({
		type : "POST",
		url : "checkActivityExists.htm",
		data : "titleId=" + titleId,
		success : function(response) {
			if(response.status){
				if(stat==0){
				systemMessage("Book & Activity Sent For Approvals!")
				window.parent.location.reload();}
				else{
					window.parent.location.reload();
				}
			}else {
				systemMessage("You must create one activity for this book")
				return false;
			}
		}
	});
	
}
function changeIcon(id,titleId,regId,readRegActvityScoreId){
	var titContent=$('#titCon_'+readRegActvityScoreId).val();
		//document.getElementById('titCon_'+titleId+'_'+regId).value;
	//$('#chat:'+regId).html("");
	if(id==1){
		var content='<i class="ion-chatbox-working" class="tooltip" style="padding: 0.2px 10px;font-size: 35px;color: #5d9c13;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);" title="'+titContent+'" onclick="getActivityScore('+readRegActvityScoreId+')"></i>';
		 $('#imgDiv11_'+titleId+'_'+regId).html(content);
	}else if(id==2){
		var content='<i class="fa fa-list-ol" class="tooltip" style="padding: 0.2px 10px;font-size: 30px;color:#88523e;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);" title="'+titContent+'" onclick="getActivityScore('+readRegActvityScoreId+')"></i>';
		$('#imgDiv22_'+titleId+'_'+regId).html(content);
	}else if(id==3){
		var content='<i class="ion-ios-mic" class="tooltip" style="padding: 0.2px 10px;font-size: 35px;color:#3F51B5;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);" title="'+titContent+'" onclick="getActivityScore('+readRegActvityScoreId+')"></i>';
		$('#imgDiv33_'+titleId+'_'+regId).html(content);
	}else if(id==4){
		var content='<i class="ion-clipboard" class="tooltip" style="padding: 0.2px 10px;font-size: 35px;color: #0078af;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);" title="'+titContent+'" onclick="getActivityScore('+readRegActvityScoreId+')"></i>';
		$('#imgDiv44_'+titleId+'_'+regId).html(content);
	}else if(id==5){
		var content='<i class="fa fa-upload" class="tooltip" style="padding: 0.2px 10px;font-size: 30px;color: #273f4a;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);" title="'+titContent+'" onclick="getActivityScore('+readRegActvityScoreId+')"></i>';
		$('#imgDiv55_'+titleId+'_'+regId).html(content);
	} 
	
		
		//document.getElementById("aaa").innerHTML ="bbb";
		//$('#chat:'+regId).html("dddddddddddd");
		//document.getElementById("icon1").innerHrml='<i class="fa fa-list-ol" class="tooltip" height="52" id="chat" width="50" style="padding: 0.2px 10px; font-size: 28px; color: #5d9c13; cursor: pointer; text-shadow: 0 1px 2px rgb(154, 154, 154);" title="${titleContent }" onclick="changeIcon(1);"></i>';
	
}
function checkUserProfileImage(titleId, regId, usersFilePath, type) {
	
	 if(regId > 0 && titleId > 0){
		 $("#loading-div-background").show();
		 $.ajax({
			 type: "GET",
		     url:"checkProfilePictureExists.htm",
		     data: "regId="+regId+"&usersFilePath="+usersFilePath,
		     success: function(usersFilePath){
		      $("#loading-div-background").hide();
		      if(usersFilePath){
		    	    if(type == 1){
		    	    // $('#iconDiv_'+titleId+'_'+regId).hide();
			    	 $('#imgDiv1_'+titleId+'_'+regId).attr("src","loadDirectUserFile.htm?usersFilePath="+usersFilePath);
			    	 $('#imgDiv1_'+titleId+'_'+regId).show();
			    	 $('#iconDiv1_'+titleId+'_'+regId).hide();
		    	    }
			    	 else if(type==2){
			    		
			    	 $('#imgDiv2_'+titleId+'_'+regId).attr("src","loadDirectUserFile.htm?usersFilePath="+usersFilePath);
			    	 $('#imgDiv2_'+titleId+'_'+regId).show();
			    	 $('#iconDiv2_'+titleId+'_'+regId).hide();
			    	 }
			    	 else if(type==3){
			    		 
			    	 $('#imgDiv3_'+titleId+'_'+regId).attr("src","loadDirectUserFile.htm?usersFilePath="+usersFilePath);
			    	 $('#imgDiv3_'+titleId+'_'+regId).show();
			    	 $('#iconDiv3_'+titleId+'_'+regId).hide();
			    	 }
			    	 else if(type==4){
			    		 
			    	 $('#imgDiv4_'+titleId+'_'+regId).attr("src","loadDirectUserFile.htm?usersFilePath="+usersFilePath);
			    	 $('#imgDiv4_'+titleId+'_'+regId).show();
			    	 $('#iconDiv4_'+titleId+'_'+regId).hide();
			    	 }
			    	 else if(type==5){
			    		 
			    	 $('#imgDiv5_'+titleId+'_'+regId).attr("src","loadDirectUserFile.htm?usersFilePath="+usersFilePath);
			    	 $('#imgDiv5_'+titleId+'_'+regId).show();
			    	 $('#iconDiv5_'+titleId+'_'+regId).hide();
			    	 }	    	  	  
		      }else{
		    	  if(type == 1){
			    	  $('#iconDiv1_'+titleId+'_'+regId).show();  
			    	  $('#imgDiv1_'+titleId+'_'+regId).hide();
			    	  //$('#remove_'+titleId+'_'+regId).hide();
		    	  }else if(type == 2){
		    		  $('#iconDiv2_'+titleId+'_'+regId).show();  
			    	  $('#imgDiv2_'+titleId+'_'+regId).hide();
		    	  }
		    	  else if(type == 3){
		    		  $('#iconDiv3_'+titleId+'_'+regId).show();  
			    	  $('#imgDiv3_'+titleId+'_'+regId).hide();
		    	  }
		    	  else if(type == 4){
		    		  $('#iconDiv4_'+titleId+'_'+regId).show();  
			    	  $('#imgDiv4_'+titleId+'_'+regId).hide();
		    	  }
		    	  else if(type == 5){
		    		  $('#iconDiv5_'+titleId+'_'+regId).show();  
			    	  $('#imgDiv5_'+titleId+'_'+regId).hide();
		    	  }
		    	  
		      }
		     }
		 });
	  }
}
function checkBookExists(masterGradesId){
	var bookTitle=document.getElementById("bookTitle").value;
	/*console.log(bookTitle1);
	var bookTitle= $.trim(bookTitle1);
	console.log(bookTitle);*/
	console.log(bookTitle);
	$.ajax({
		type : "POST",
		url : "checkBookExists.htm",
		data : "bookTitle=" + bookTitle+"&masterGradesId="+masterGradesId,
		success : function(response) {
			if(response.status){
				systemMessage("Book Exists");
				//document.getElementById("bookTitle").value="";
			 }
		}	
	});
}

function reSubmitBook(titleId){
	$.ajax({
		type : "POST",
		url : "reSubmitBook.htm",
		data : "titleId=" + titleId,
		success : function(response) {
			if(response.status){
				window.location.reload();
			 }
		}
				
	});
	
}

function getActivities(titleId, status){
	$("#loading-div-background").show();
	$.ajax({
		type : "GET",
		url : "getBookActivities.htm",
		data : "titleId=" + titleId +"&status="+status,
		success : function(response) {
			var div = document.getElementById("details:"+titleId);
			$(div).empty();			
			$(div).append(response);	
			$("#loading-div-background").hide();			
		}
				
	});
	
}
function setActivityApproval(){
	var radioStatus = $("input[name='activityApprove']:checked").val();
	//alert(radioStatus);
	if(radioStatus== 'approved'){
		$('#finalScore').css("visibility", "visible");
		document.getElementById("activityAppStat").value="approved";
	}else if(radioStatus== 'returned'){	
		$('#finalScore').css("visibility", "hidden");
		document.getElementById("activityAppStat").value="returned";
		$('#spaceId').remove();
	}
}
function gotoPages(pageNumber, sortBy, sortingOrder){
	var searchBy="";
	pageNum = pageNumber; 
	var gradeId=$("#gradeId").val();
	var maxPages = $("#maxPages").val();
	var academicYear = $("#academicYear").val();
	searchBy =$("#bookName").val();
	if(pageNumber < 0 || pageNumber > maxPages){
		return false;
	}
	pageNum = pageNumber; 
	$("#loading-div-background").show();
	$.ajax({
		type : "GET",
		url : "gotoPages.htm",
		data : "pageId="+pageNumber+"&sortBy="+sortBy+"&sortingOrder="+sortingOrder+"&searchBy="+searchBy+"&gradeId="+gradeId+ "&academicYearId=" + academicYear,
		success : function(response) {
			$("#contentDiv").empty();
			$("#contentDiv").append(response);	
			$("#loading-div-background").hide();
		}
	});	
}

function returnGradedActivity(readRegActScoreId){
	
	$.ajax({
		type : "GET",
		url : "returnGradedActivity.htm",
		data : "readRegActScoreId=" + readRegActScoreId,
		success : function(data) {
			if(data.status){
				systemMessage("Activity returned To Review");
			}
		    else
		    	systemMessage("Error Occured");
			
			setTimeout(function() {
				window.parent.jQuery('#activityScoreDailog').dialog('close');
			}, 3000);	
		}
				
	});
	
}


