/**
 * 
 */
var isRecording = false;
function recordErrorWord(id,audioFileName,count){	 
	if(isRecording == false){
		$("#resultDiv"+count).fadeOut(100, function() {
			  $(this).html("<font color='green' size='2'><b>Recording...</b></font>").fadeIn(100, function() {
				  $("#resultDiv"+count).show();
			  })
		});
		id = id+count;
		recordAudio();
		isRecording = true;
		$("#green"+count).show();
	 }else{
		  alert("Recording already started. Click on stop button to proceed further");  
		  return false;
	  }
 } 
 
 function stopErrorWord(id,count){	
	 if(isRecording == true){
		 $("#green"+count).hide();
		 isRecording = false;		 
		 $('#span'+count).show();
		 systemMessage("Stopped !!","error");
		 $("#loading-div-background1").show();
		 stopRecording(function(base64){ 
			 $("#resultDiv"+count).hide();
			 document.getElementById(id+""+count).value = base64;
			 autoSaveRflpTest(count,'save');
			 id = id+count;
			});
	 }else{
		 alert("Recording is not yet started !!");
		 return false;
	 }
  }
 
 function formSubmit(operation,gradingTypesId,page){
	 var numItems = $('.fa-check').length;
	 var totLen = $('#totLen').val();
	 var percentage = $("#percentage").val();
	 var teacherComment = $("#teacherComment").val();
	 var cnt = 0;
	 var empty=0;
     if(gradingTypesId==1 || gradingTypesId==2 || gradingTypesId==3){
     for(var i=0;i<totLen;i++){	
    	 
    	 var written = $("#written"+i).val();	
     
    	 if (written == 'select' || written == null) {
    		 alert("please select the Written Rubric Score");
    		 return false;
    	 }
    	 
    	 oral = $("#oral"+i).val();
    	 
    	 if(oral == "select") {
    		 alert("please select the Oral Rubric Score");
    		 return false;
		 }
     }
     if(percentage == "" || percentage == null)
	 {
	 alert("please enter the percentage ");
	 return false;
	 }
     else if(isNaN(percentage)){
    	 alert("Please enter valid percentage");
   	  	 return false;
     }
     else if(percentage > 100.0)
	 {
	 alert("percentage should not above 100%");
	  return false;
	 }
     else if(teacherComment == '')
	 {
	 alert("please enter the your comments");
	 return false;
	 }	 
    }
     
	 $(".fa-check").each(function( index ) {
		 if($( this ).css("display") == "inline-block" ){
			 cnt= cnt+1;
		 }
	 });
	
	 $('.DivContents :text').each(function(){
	     if($.trim($(this).val()) == "" ) empty++;	          
	  });
	
	 
	  if((cnt == totLen && empty == 0 )|| page == 'Grade'){
		 document.getElementById("operation").value = operation;
		 var formObj = document.getElementById("questionsForm");
		 var formData = new FormData(formObj);
			$.ajax({
				url: 'submitRflpTest.htm',
				type: 'POST',
				data: formData,
				mimeType:"multipart/form-data",
				contentType: false,
				cache: false,
				processData:false,
				success: function(data){
				 var parsedData = JSON.parse(data);
				 systemMessage(parsedData.result);
				if(operation == "submit"){ 
				    var i = setInterval(function(){ 
		  	       	 clearInterval(i);
		  	       	 window.parent.$('.ui-dialog-content:visible').dialog('close');
		  	         if(gradingTypesId==2 || gradingTypesId==3)
		  	       	 loadRFLPHomeworks(gradingTypesId);
		  	         else
		  	          window.parent.location.reload();
		 	       	    }, 2000); 
					} 
				}
			});	
	 }else{
		 alert("Please fill/record all the data");
	 }
 }
 
 function autoSaveRflpTest(index,operation){
	 document.getElementById("index").value = index;
	 document.getElementById("operation").value = operation;
	 var formObj = document.getElementById("questionsForm");
	 var formData = new FormData(formObj);
		$.ajax({
			url: 'autoSaveRflpTest.htm',
			type: 'POST',
			data: formData,
			mimeType:"multipart/form-data",
			contentType: false,
			cache: false,
			processData:false,
			success: function(data){
				$("#loading-div-background1").hide();
				var parsedData = JSON.parse(data);
				 systemMessage("Saved !!");
			}
		});	
 }
 function loadRFLPHomeworks(gradeTypesId) {
	 console.log("gradeTypesId="+gradeTypesId);
	 	var csId = $('#csId').val();
		$('#RflpHomeworks').html("");
		$('#rflpHomeQuestionsList').html("");
		if(csId>0){
			$("#loading-div-background").show();
		$.ajax({
			type : "GET",
			url : "getRFLPHomeworks.htm",
			data : "csId=" + csId + "&usedFor=homeworks"+"&gradeTypesId="+gradeTypesId,
			success : function(data) {
					$('#RflpHomeworks').html(data);
					$("#loading-div-background").hide();
				}
			}); 
		}else
			return false;
		   
	}