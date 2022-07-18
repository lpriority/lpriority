var previousSelection=0;
var check=false;
var targetbox ="";
function strikeRetellText(i) {
	previousSelection=document.getElementById("retellScore").value;
    document.getElementById('retellScore').value=i;
    //document.getElementById("retellMarks").value = i;
    var currentSelection=i;
    if(previousSelection>currentSelection){
        for(var count=currentSelection+1;count<=previousSelection;count++){
            targetbox = document.getElementById('orders'+count);
            targetbox.style.backgroundColor="white";
        }
    }
    if(currentSelection==0){
        previousSelection=currentSelection;
    }
    else if(previousSelection<currentSelection){
        previousSelection=currentSelection;
    }
    for(var count=0;count<=parseInt(i);count++){
        targetbox = document.getElementById('orders'+count);
        targetbox.style.backgroundColor="grey";
    }
    return true;
}
function submitRetellTest(readingTypesId,gradeTypesId)
{
	
    var assignmentQuestionId=$("#questions").val();
    var retellmarks=window.document.getElementById('retellScore').value;
    if(!retellmarks){
    	alert("Please Select Retell Score");
    	return false;
    }    	
    var comment=window.document.getElementById("retellComment").value;
    if (!comment.replace(/\s/g, '').length) {
		alert("Please enter a comment");
	    return false;
	}
    $.ajax({
		type : "GET",
		url : "gradeRetellFluencyTest.htm",
		data : "assignmentQuestionId="+assignmentQuestionId+"&readingTypesId="+readingTypesId+"&retellmarks="+retellmarks+"&gradeTypesId="+gradeTypesId+"&comment="+comment,
		success : function(response) {			
			document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value=retellmarks;
			window.parent.$('.ui-dialog-content:visible').dialog('close');	
			systemMessage("Graded Successfully !!");
		}
	});

}

function getresponse(i)
{
    document.getElementById("retellScore").value=i;
}
function playRetellAudio(){
	
	var path = document.getElementById("audioPath").value;
		$.ajax({
		type : "GET",
		url : "playAudio.htm",
		data : "filePath="+path,
		success : function(response) {
		}
	});
}
