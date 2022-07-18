function getCopyOfStemUnits(){
	var gradeId = $('#gradeId').val();
	var classId = $('#classId').val();
	var stemUnitIdArray = [];
	$("input:checkbox[name=stemUnitCheckbox]:checked").each(function(){
		stemUnitIdArray.push(parseInt($(this).val()));
	});
	if(stemUnitIdArray.length > 0){
		$("#loading-div-background").show();
		 $.ajax({  
				url : "getCopyOfStemUnits.htm", 
				type : "POST",
				data: "stemUnitIdArray=" + stemUnitIdArray+"&gradeId="+gradeId+"&classId="+classId, 
			  	success : function(response) { 
			  		$("#loading-div-background").hide();
			  		var stemUnitLt = response.stemUnitLt;
			  		var str = '';
			  		var status = false;
		  			for(var i=0;i<stemUnitLt.length;i++){
			  			if(stemUnitLt[i].stemUnitId > 0)
			  				str += "<tr id='tr"+stemUnitLt[i].stemUnitId+"'><td align='left' width='100%' style='padding-left:2em;padding:.2em;'><a id='stem"+stemUnitLt[i].stemUnitId+"' href='#' onclick=addStemUnit("+stemUnitLt[i].stemUnitId+") style='color:#0060ad;font-weight:bold;'>"+stemUnitLt[i].stemUnitName+"</a>&nbsp;&nbsp;<i class='fa fa-times' aria-hidden='true' style='cursor: hand; cursor: pointer;font-size: 18px;font-weight:bold;color:#CD0000;' onclick='deleteStemUnit("+stemUnitLt[i].stemUnitId+",\'"+stemUnitLt[i].isShared+"\')'></i></td></tr>";
			  			else
			  				status = true;
			  		}
		  			$("#editStemUnitDiv").append(str);
			  		$("input:checkbox[name=stemUnitCheckbox]:checked").each(function(){
			  			$(this).attr('checked',false);
			  		});
			  		if(status)
			  			alert("Some of the selected units already added by you");
			  	}
		 });
	}
}