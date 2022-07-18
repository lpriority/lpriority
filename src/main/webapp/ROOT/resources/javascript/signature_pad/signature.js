var wrapper = document.getElementById("signature-pad"),
    clearButton = wrapper.querySelector("[data-action=clear]"),
    savePNGButton = wrapper.querySelector("[data-action=save-png]"),
    canvas = wrapper.querySelector("canvas"),
    signaturePad;

function resizeCanvas() {
    var ratio =  Math.max(window.devicePixelRatio || 1, 1);
    canvas.width = canvas.offsetWidth * ratio;
    canvas.height = canvas.offsetHeight * ratio;
    canvas.getContext("2d").scale(ratio, ratio);
}

window.onresize = resizeCanvas;
resizeCanvas();

signaturePad = new SignaturePad(canvas);

clearButton.addEventListener("click", function (event) {
    signaturePad.clear();
});

savePNGButton.addEventListener("click", function (event) {
    if (signaturePad.isEmpty()) {
        alert("Please provide signature first.");
    } else {
    	var blob_data = signaturePad.toDataURL();
    	var base64Data = blob_data.split(',')[1];
    	 var page = $('#page').val();
    	 var studentId=$('#studId').val();
    	 var gradeId=$('#gradeId').val();
    	 var trimesterId=$('#trimesterId').val();
    	 var fd = new FormData();
    	fd.append("imageContent", base64Data);
    	fd.append("page", page);
    	fd.append("studentId",studentId);
    	fd.append("gradeId",gradeId);
    	fd.append("trimesterId",trimesterId);
    	
    	$.ajax({
    	    type: 'POST',
    	    url: 'saveSignatureContent.htm',
    	    data: fd,
    	    contentType: false,
    	    processData: false,
    	    success: function(data){
    	       if(data){
    	    	   var domain = window.location.origin;
    	    	   window.parent.$('#signDiv').html("");
    	    	     var img = $('<img />', { 
    	    		   id: 'signatureId',
    	    		   width:130,
    	    		   height:85,
    	    		   src: domain+'/loadDirectUserFile.htm?usersFilePath='+data
    	    		 });
    	    		 img.appendTo(window.parent.$('#signDiv'));
    	       }
    	    }
    	});
    }
});