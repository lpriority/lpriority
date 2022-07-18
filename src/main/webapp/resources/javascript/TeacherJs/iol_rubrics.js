var userId = "";
function getRubricByUser(id){	
	userId = id;
	var screenWidth = $( window ).width()-10;
	var screenHeight = $( window ).height()-10;
	jQuery.curCSS = jQuery.css;
	$("#legendCriteriaDailog").dialog({
		overflow: 'auto',
		dialogClass: 'no-close',
	    autoOpen: false,
		position: {my: "center", at: "center", of:window ,within: $("body") },
	    title: 'Legend Criterias',
	    draggable: true,
	    width : screenWidth,
	    height : screenHeight,
	    resizable : true,
	    modal : true,
	    close: function (ev, ui) {} 
	});
	var legCriteriaContainer = $(document.getElementById('legendCriteriaDiv'));
	$(legCriteriaContainer).empty(); 	
	$("#loading-div-background").show();
	$.ajax({
		url : "getLegCriterias.htm",
		type : "POST",
		success : function(data){
			$(legCriteriaContainer).append(data);
        	$("#loading-div-background").hide();				
		}
	});
	$("#legendCriteriaDailog").dialog("open");
	return false;
} 

function viewSubCriteria(id){
	var count = parseInt($('#count').val());
	var lCriteriaId =  parseInt($('#lCriteriaId'+id).val());
	var subCriteriaContainer = $(document.getElementById('subCriteriaDiv'));
	$(subCriteriaContainer).empty(); 
	$("#loading-div-background").show();
	$.ajax({  
 		url : "getLegSubCriterias.htm",
 		type : "POST",
   		data: "lCriteriaId="+lCriteriaId,
 		success : function(data) {
 			$("#loading-div-background").hide();
 			$(subCriteriaContainer).append(data);
 		}  
	});
	for(var i=0;i<count;i++){
		if(i != id){
			document.getElementById("lCriteriaBut"+i).className = "button_green";
		}else{
			document.getElementById("lCriteriaBut"+i).className = "button_light_green";
		}	
	}	
}

function displayIndicators(id){
	var subCriteriaId =  $('#stemSubCriteriaId'+id).val();
	var createdBy = userId;
	var screenWidth = $( window ).width();
	var screenHeight = $( window ).height()-150;
	jQuery.curCSS = jQuery.css;
	$("#rubricDailog").dialog({
		overflow: 'auto',
		dialogClass: 'no-close',
	    autoOpen: false,
		position: {my: "center", at: "center", of:window ,within: $("body") },
	    title: 'legend Sub Criteria Rubrics',
	    draggable: true,
	    width : screenWidth,
	    height : screenHeight,
	    resizable : true,
	    modal : true,
	    close: function (ev, ui) {} 
	});
	var rubricContainer = $(document.getElementById('rubricDiv'));
	$(rubricContainer).empty(); 
	$.ajax({
		url : "getCriteriaRubrics.htm",
		type : "POST",
   		data: "createdBy="+createdBy+"&subCriteriaId="+subCriteriaId,
		success : function(data){
			$(rubricContainer).append(data);
		}
	});
	$("#rubricDailog").dialog("open");
}


