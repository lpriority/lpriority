function loadPage(url){
	window.location = url;
	storeValue("menuLevelTitle",'');
	storeValue("itemName",'');
	$('#menu').multilevelpushmenu('collapse', 1);
}
 
  function collapse() {
		$('#nav-icon').toggleClass('open');
		$( '#menu' ).multilevelpushmenu( 'collapse' );
  }
  
  function expand() {
   	$('#nav-icon').toggleClass('open');
   	if(getStoredValue("menuLevelTitle")){
   			$( '#menu' ).multilevelpushmenu( 'expand' , getStoredValue("menuLevelTitle") );
  		}else{
  			$( '#menu' ).multilevelpushmenu( 'expand' );
  		}
    }
  
  function storeValue(key, value) {
	    if (localStorage) {
	        localStorage.setItem(key, value);
	    } else {
	        $.cookies.set(key, value);
	    }
	}

	function getStoredValue(key) {
	    if (localStorage) {
	        return localStorage.getItem(key);
	    } else {
	        return $.cookies.get(key);
	    }
	}
	
	function removeStoredItem() {
		storeValue("itemName",'');
		storeValue("menuLevelTitle",'');
	}
	
	function setDropdownVal(id, val){
		var callBack = '';
		if(val){
			if(val.indexOf("_")){
				 var arr = val.replace(/\_/,'&').split('&');
				$("#"+id).find('option').each(function( i, opt ) {
				    if( opt.value === arr[0] ){ 
				        $(opt).attr('selected', 'selected');
				        callBack = arr[0];
				    }
				});
			}
		}
		return callBack;
	}
 var notifyArray = [];	
 var alertArray = [];	
 function systemMessage(data,type){
	notifyArray = $('.notify').map(function() {
		 return this;
		}).get();
	if(data){
		var delayTime = 1000;
		if(data.length < 25){
			delayTime = 2000;
		}else if(data.length < 45){
			delayTime = 3000;
		}else if(data.length < 65){
			delayTime = 6000;
		}else{
			delayTime = 8000;
		}
		
		if(notifyArray.length == 0){
			 if(type && type == 'error')
				 $.notify(data, {class: "sys-messages-error", align:"center", verticalAlign:"bottom",animationType:"scale",delay:delayTime});
			 else if(type && type == 'info')	 
				 $.notify(data, {class: "sys-messages-info", align:"center", verticalAlign:"bottom",animationType:"scale",delay:delayTime});
			 else if(type && type == 'warn')	 
				 $.notify(data, {class: "sys-messages-warning", align:"center", verticalAlign:"bottom",animationType:"scale",delay:delayTime});
			 else	 
				 $.notify(data, {class: "sys-messages-success", align:"center", verticalAlign:"bottom",animationType:"scale",delay:delayTime});
		}else{
			$("div.notify:first").remove();
			 if(type && type == 'error')
				 $.notify(data, {class: "sys-messages-error", align:"center", verticalAlign:"bottom",animationType:"scale",delay:delayTime});
			 else if(type && type == 'info')	 
				 $.notify(data, {class: "sys-messages-info", align:"center", verticalAlign:"bottom",animationType:"scale",delay:delayTime});
			 else if(type && type == 'warn')	 
				 $.notify(data, {class: "sys-messages-warning", align:"center", verticalAlign:"bottom",animationType:"scale",delay:delayTime});
			 else	 
				 $.notify(data, {class: "sys-messages-success", align:"center", verticalAlign:"bottom",animationType:"scale",delay:delayTime});
		}
	}
	
 }
 
 function alert(message){
	 notifyArray = $('.notify').map(function() {
		 return this;
		}).get();
	 if(notifyArray.length == 0){
		 $.notify(message,{class:"sys-alert-message", align:"center", verticalAlign:"middle", blur: 0.2, delay: 0,animationType:"scale"});
	 }else{
		 $("div.notify:first").remove();
		 $.notify(message,{class:"sys-alert-message", align:"center", verticalAlign:"middle", blur: 0.2, delay: 0,animationType:"scale"});
	 }		
	 return true;
 }
 
 window.confirm = function(message, callback) {
	 lnv.confirm({
		    content: message,
		    confirmBtnText: 'Confirm',
		    confirmHandler: function(){
		    	callback(true);
		    },
		    cancelBtnText: 'Cancel',
		    cancelHandler: function(){
		    	callback(false);
		    }
		})
}
 
 // desktop middle page scripts
 
 $(function(){
	  $("#upload").click(function (event) {
		    event.preventDefault();
		    $("#file").click();
	  });
	  checkProfilePicExists();
	});
 function showimagepreview(input) {
	  if (input.files && input.files[0]) {		  
		  var status =  uploadProfilePicture();
		  if(status){
			  var reader = new FileReader();
			  reader.onload = function(e) {
				  $('#imgDiv').attr('src', e.target.result);
				  $('#imgDiv').show();
				  $('#iconDiv').hide();
				  $('#remove').show();
			  }
			  reader.readAsDataURL(input.files[0]);
		  }
	  }
 }
 
 function deleteProfileImage(){
	var regId = $('#regId').val();
	if(regId > 0){
		$.ajax({
			url: 'deleteProfileImage.htm',
			type: 'POST',
			data: "regId="+regId+"&usersFilePath=profile_pic.jpg",
			success: function(data){
				if(data == 'Removed'){
				  $('input[type=file]').val("");
				  $('#iconDiv').show();  
			      $('#imgDiv').hide();
			      $('#remove').hide();
				}
			    systemMessage(data);
			}
		});
	 }
 }
 
 function uploadProfilePicture() {
		var status = validateImageFileType();
		if(status){
		   var formData = new FormData($("#uploadProfilePicForm")[0]);
			$.ajax({
				url: 'uploadProfilePic.htm',
				type: 'POST',
				data: formData,
				enctype: 'multipart/form-data',
		        processData: false, 
		        contentType: false,
		        cache: false,
				success: function(data){
				  systemMessage(data);
				}
			});	 
		}
		return status;
	}
 
 function openPreviewImage(){
	  $.bindViewer(".imgCls");  
 }
 
 function validateImageFileType(){
		var fup = document.getElementById('file');
		var fileName = fup.value;
		var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
		if(ext == "gif" || ext == "GIF" || ext == "JPEG" || ext == "jpeg" || ext == "jpg" || ext == "JPG" || ext == "png" || ext == "PNG"){
			return true;
		}else{
			systemMessage("Please upload Gif or Jpg images only");
			fup.value="";
			fup.focus();
			return false;
		}
	}
 
 function checkProfilePicExists() {
	 var regId = $('#regId').val();
	 if(regId > 0){
		 $("#loading-div-background").show();
		 $.ajax({
			 type: "GET",
		     url:"checkProfilePicExists.htm",
		     data: "regId="+regId+"&usersFilePath=profile_pic.jpg",
		     success: function(usersFilePath){
		      $("#loading-div-background").hide();
		      if(usersFilePath){
			     $('#iconDiv').hide();
		    	 $('#imgDiv').attr("src","loadDirectUserFile.htm?usersFilePath="+usersFilePath);
		    	 $('#imgDiv').show();
		      }else{
		    	  $('#iconDiv').show();  
		    	  $('#imgDiv').hide();
		    	  $('#remove').hide();
		      }
		     }
		 });
	 }
	}
 
 function getFormattedDate(unformattedDate) {	
	 if(unformattedDate == "")
	    return "";
	 var formattedDate = new Date(unformattedDate);
	 var d = "";
	 if(location.hostname == 'localhost'){
		 var now = new Date();
	 	d = new Date(unformattedDate - now.getTimezoneOffset() * 60000).toISOString().slice(0, 10).split('-');	
	 }else{
		d  = new Date(formattedDate).toISOString().slice(0, 10).split('-');
	 }
	 return d[1] +'/'+ d[2] +'/'+ d[0];
 }

 function getDBFormattedDate(unformattedDate) {
	 if(unformattedDate == "")
	        return "";
	    var formattedDate = new Date(unformattedDate);
	    var d = "";
	 if(location.hostname == 'localhost'){
		 var now = new Date();
		 d = new Date(unformattedDate - now.getTimezoneOffset() * 60000).toISOString().slice(0, 10).split('-');	
	 }else{
		 d  = new Date(formattedDate).toISOString().slice(0, 10).split('-');
	 }
	 return d[0] +'-'+ d[1] +'-'+ d[2];
 }