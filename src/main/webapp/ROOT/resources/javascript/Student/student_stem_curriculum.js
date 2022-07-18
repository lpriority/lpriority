function getAssignedStemUnits(){
	var stemUnitContainer = $(document.getElementById('stemUnitDiv'));
	var csId = document.getElementById("csId").value;
	$(stemUnitContainer).empty();  
	if(csId != 'select'){
		var getAssignedStemUnitsCallBack = function(list) {
			$("#loading-div-background1").show();
			var length = list.length;
				if(length > 0){
					var len = parseInt(length)-1;
				var str = "<table class='des' border=0 width='35%'><tr><td width='100%'><div class='Divheads'><table align='center' width='100%'> <tr> " +
				"<th width='50' align='right'>S.No</th>" +
				"<th width='250' align='center' style='padding-right:1.6em;'>Unit Name</th>" +
				"</tr></table></div><div class='DivContents'><table width='100%' class='txtStyle'><tr><td height='10' colspan='2'></td></tr>";
				var count = 0;
				for (i=len;i >= 0;i--){
					count = count+1;
						    str +=  "<tr><td width='50' align='right'>"+count+" .</td>" +
				    		"<td width='280' height='25' align='center' style='padding-right:1.6em;'> <input type='hidden'  id='stemUnitId"+i+"' name='stemUnitId"+i+"' value='"+list[i].stemUnit.stemUnitId+"' /><a href='#' onclick='javascript:viewStemUnitWindow("+list[i].stemUnit.stemUnitId+");'>"+list[i].stemUnit.stemUnitName+"</a></td></tr>";
				  }
			    str += "<tr><td height='10'></td></tr></table></div></td></tr></table>";
			    $(stemUnitContainer).append(str);
			    $("#loading-div-background1").hide();
				}else{
					 $("#loading-div-background1").hide();
					 $(stemUnitContainer).append("No units are assigned yet"); 
				}
		}
				
		stemCurriculumService.getAssignedStemUnits(csId,{
			callback : getAssignedStemUnitsCallBack
		});
	}
}

function loadStemPaths(){
	var csId=$('#csId').val();
	$("#pathId").empty(); 
	$("#pathId").append($("<option></option>").val('select').html('Select Path'));
	if(csId != "select" && csId){
		$.ajax({
			type : "GET",
			url : "getStemPaths.htm",
			success : function(response) {
				var stemPaths = response.stemPaths;
				$.each(stemPaths, function(index, value) {					
					$("#pathId").append($("<option></option>").val(value.stemPathId).html(value.stemPathDesc));
				});
			}
		});
	}else{
		return false;
	}
}

function viewStemUnitWindow(stemUnitId){
	console.log("aslkdasdjaskld");
	 var screenWidth = $( window ).width()-240;
	 var screenHeight = $( window ).height()-40;
	 var iframe = $('#iframe');
	 iframe.empty();
	 jQuery.curCSS = jQuery.css;
	  $("#dialog").dialog({
			overflow: 'auto',
			dialogClass: 'no-close',
		    autoOpen: false,
		    position: {my: "center", at: "center", of:window ,within: $("body") },
		    draggable: true,
		    width : screenWidth,
		    height : screenHeight,
		    title: "View STEAM Unit",
		    resizable : true,
		    modal : true,
		    open: function(event, ui) {
		        $(this).parent().css('position', 'fixed');
		    },
		    close: function (ev, ui) {
		    } 
	   });
	iframe.attr('src', "viewStemUnitWindow.htm?stemUnitId="+stemUnitId);
	$("#dialog").dialog("open");
}