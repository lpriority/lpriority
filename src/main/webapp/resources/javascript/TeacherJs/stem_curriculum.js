function loadPaths(callback){
	var gradeId = $('#gradeId').val();
	var classId=$('#classId').val();
	$("#pathId").empty(); 
	$("#pathId").append(
			$("<option></option>").val('select')
					.html('Select Path'));
	if(classId != "select"){
		$.ajax({
			type : "GET",
			url : "getStemPaths.htm",
			success : function(response) {
				var stemPaths = response.stemPaths;
				$.each(stemPaths, function(index, value) {					
					$("#pathId").append(
							$("<option></option>").val(value.stemPathId).html(
									value.stemPathDesc));
				});
			  if(callback)
	            callback();
			}
		});
	}else{
		return false;
	}
}

function loadStemContent(){
	var gradeId=$('#gradeId').val();
	var classId=$('#classId').val();
	var pathId=$('#pathId').val();
	var stemUnitContainer = $(document.getElementById('stemUnitDiv'));
	if(gradeId != "select" && classId != "select" && pathId!="select"){
		if(pathId == 3 || pathId == 2 || pathId == 1){
			if(pathId == 3 ){
				$("#addStemUnit").show();
				$("#ipalId").hide();
				$("#preMadeStemUnit").hide(); 
				$("#adoptedStemUnit").hide();
			}else if(pathId == 2){
				$("#addStemUnit").hide();
				$("#ipalId").hide();
				$("#preMadeStemUnit").show(); 
				$("#adoptedStemUnit").hide();
			}else if(pathId == 1){
				$("#adoptedStemUnit").show();
				$("#ipalId").show();
				$("#addStemUnit").hide(); 
				$("#preMadeStemUnit").hide(); 
			}
			var gradeId = document.getElementById("gradeId").value;
			var classId = document.getElementById("classId").value;
			$(stemUnitContainer).empty();
			$("#loading-div-background").show();
			 $.ajax({  
					url : "viewStemUnits.htm", 
					data: "gradeId=" + gradeId + "&classId=" + classId +"&pathId="+pathId, 
		     	    success : function(data) { 
			     	 $("#stemUnitDiv").show();
					 $(stemUnitContainer).append(data);
					 $("#loading-div-background").hide();
			     	}
			 });
		}else if(pathId == 4 ){
			$("#addStemUnit").hide(); 
			$("#preMadeStemUnit").hide(); 
			$("#adoptedStemUnit").hide();
			$("#ipalId").hide();
			var gradeId = document.getElementById("gradeId").value;
			var classId = document.getElementById("classId").value;
			$(stemUnitContainer).empty();
			$("#loading-div-background").show();
			 $.ajax({  
					url : "loadSharedStemUnits.htm", 
					data: "gradeId=" + gradeId + "&classId=" + classId +"&pathId="+pathId, 
		     	    success : function(data) { 
			     	 $("#stemUnitDiv").show();
					 $(stemUnitContainer).append(data);
					 $("#loading-div-background").hide();
			     	}
			 });
		}else{ 
			$("#addStemUnit").hide(); 
			$("#preMadeStemUnit").hide(); 
			$("#adoptedStemUnit").hide();
			$(stemUnitContainer).empty(); 
		
		}
	}else{
		systemMessage("Please Fill all the Filters");
		return false;
	}
}
function addStemUnit(stemUnitId){
	var gradeId=$('#gradeId').val();
	var classId=$('#classId').val();
	var pathId=$('#pathId').val();
	 var screenWidth = $( window ).width() - 20;
	 var screenHeight = $( window ).height() -10;
	 var iframe = $('#iframe');
	 iframe.empty();
	jQuery.curCSS = jQuery.css;
	if(gradeId != "select" && classId != "select" && pathId!="select"){	
	  $("#dialog").dialog({
			overflow: 'auto',
			dialogClass: 'no-close',
		    autoOpen: false,
		    position: {my: "center", at: "center", of:window ,within: $("body") },
		    draggable: true,
		    width : screenWidth,
		    height : screenHeight,
		    resizable : true,
		    modal : true,
		    open: function(event, ui) {
		        $(this).parent().css('position', 'fixed');
	            if(stemUnitId > 0){
	        	  var currentTab = $(this)[0].id;
	        	  synchronizeTab(stemUnitId, currentTab, '', 'Yes', '', 'focusin');
	            }
		    },
		    close: function (ev, ui) {
		    	var closeSynchronizeTabCallBack =  function (response){
			    	loadStemContent();
				}
				synHandlerService.closeSynchronizeTab(stemUnitId,{
					callback : closeSynchronizeTabCallBack
				});
		    } 
	   });
		if(stemUnitId > 0)
			$( "#dialog" ).dialog({title: "Edit STEAM Unit"});
		else
			$( "#dialog" ).dialog({title: "Create STEAM Unit"});
		iframe.attr('src', "openStemUnit.htm?stemUnitId="+stemUnitId+"&pathId="+pathId);
		$("#dialog").dialog("open");
}else{
	systemMessage("Please Fill all the Filters");
	return false;
}
}

function getUnitContent(){
	var stemUnitId = $('#stemUnitId').val();
	var pathId = window.parent.$('#pathId').val();
		if(!stemUnitId || stemUnitId < 1)
			stemUnitId = 0;
		$("#loading-div-background").show();
		$.ajax({
			url : "getUnitContent.htm",
			type : "POST",
	   		data: "stemUnitId="+stemUnitId+"&pathId="+pathId,
			success : function(data){
				$("#getUnitContent").empty(); 
				$("#getUnitContent").append(data);
				$("#loading-div-background").hide();				
			}
		});
}

function autoSaveStemUnits(obj){
	var gradeId = window.parent.$('#gradeId').val(); 
	var classId = window.parent.$('#classId').val();
	var stemUnitId=parseInt($('#stemUnitId').val());
	var updatedVal= obj.value;
	var elementId= obj.name;
	if(updatedVal == ""){
		alert("please fill the fields");
		return false;
	}
	else if(stemUnitId > 0){
		$("#loading-div-background").show();
		if(updatedVal && elementId && gradeId != "select" && classId != "select"){	
	    	$.ajax({  
	     		url : "autoSaveStemUnits.htm",
	     		type : "POST",
	       		data: "gradeId="+gradeId+"&classId=" +classId+"&stemUnitId="+stemUnitId+"&updatedVal="+updatedVal+"&elementId="+elementId,
	     		success : function(data) {
	     			$("#loading-div-background").hide();
	     			systemMessage(data.status);  
	     		}  
	    	});
		}
	}
}

function saveStemUnits(){
	var gradeId = window.parent.$('#gradeId').val(); 
	var classId = window.parent.$('#classId').val();
	var stemPathId = window.parent.$('#pathId').val();
	var stemUnitId=parseInt($('#stemUnitId').val());
	if(!stemUnitId)
		stemUnitId = 0;
	var stemUnitName = $('#stemUnitName').val();
	stemUnitName = stemUnitName.replace(/\./g, ' ');
	var stemUnitDesc = $('#stemUnitDesc').val();
	var urlLinks = $('#urlLinks').val();
	if(!urlLinks)
		urlLinks = "";
	var mode = $('#mode').val();
	var isShared = $('input[name=shareId]:checked').val();
	$("#loading-div-background").show();
	if(stemUnitName && stemUnitDesc && gradeId != "select" && classId != "select" && stemPathId!="select"){	
    	$.ajax({  
     		url : "saveStemUnits.htm",
     		type : "POST",
       		data: "gradeId="+gradeId+"&classId=" +classId+"&stemPathId="+stemPathId+"&stemUnitName="+stemUnitName+"&stemUnitDesc="+stemUnitDesc+
       			 "&isShared="+isShared+"&stemUnitId="+stemUnitId+"&urlLinks="+urlLinks+"&mode="+mode,
     		success : function(data) {
     			$("#loading-div-background").hide();
     			systemMessage(data.status);  
     			if(data.status == 'Created'){
     				document.getElementById('stemUnitId').value=data.stemUnitId;
     				$("#tab1").css("pointer-events", "auto");
     				$("#printBtn").show();
     				stemTabs.goToTab(1);
     				essUnitQues();
     			}else if(data.status == 'Updated'){
     				if(data.srcStemUnitId){
     					window.parent.$('a#stem'+stemUnitId).text(stemUnitName);
     				}
     			}
     		}  
    	});
 	}else{
 		$("#loading-div-background").hide();
		alert("Please Fill all the Filters");
		return false;
	}
}

function loadPremadeStemUnits(stemUnitId){
	var gradeId = document.getElementById("gradeId").value;
	var classId = document.getElementById("classId").value;
	var pathId = document.getElementById("pathId").value;
	var screenWidth = $( window ).width() - 80;
	var screenHeight = $( window ).height() -20;
	jQuery.curCSS = jQuery.css;
	 var iframe = $('#preMadeIframe');
	 iframe.empty();
	 if(gradeId != "select" && classId != "select" && pathId!="select"){	
	 $("#preMadedialog").dialog({
		overflow: 'auto',
		dialogClass: 'no-close',
	    autoOpen: false,
	    position: {my: "center", at: "center", of:window ,within: $("body") },
	    title: 'Pre-made STEAM Units',
	    draggable: true,
	    width : screenWidth,
	    height : window.screen.height,
	    resizable : true,
	    modal : true,
	    open: function(event, ui) {
	        $(this).parent().css('position', 'fixed');
	    },
	    close: function (ev, ui) {
	    	loadStemContent();
	    } 
	});
	 iframe.attr('src', "loadPremadeStemUnits.htm?gradeId="+gradeId+"&classId="+classId+"&pathId="+pathId);
	 $("#preMadedialog").dialog("open");
	 }else{
		 alert("Please Fill all the Filters");
		 return false;
	 }
}

function addEssenitialQuestion(mode){
	var questionNum =parseInt($('#essUnitQueLen').val());
	var unitQuesCnt = $('.unitQueCls').length;
	if(unitQuesCnt < 5){
		if(mode=="edit"){
		     $('#essUnitTbl tbody').append("<tr id='tr"+questionNum+"'><td></td><td width='60%' style='vertical-align:middle;'>"+
			 "<textarea class='unitQueCls' rows='3' cols='60' id='unitQues"+questionNum+"' name='unitQues"+questionNum+"' onblur='autoSaveEssenUnitQues("+questionNum+",2)'></textarea>&nbsp;&nbsp;<i class='fa fa-times' style='cursor: hand; cursor: pointer; font-size: 20px; color: #CD0000; vertical-align: middle;' onclick='removeEssQuestion("+questionNum+")'></i>" +
			 "</td><td><input type='hidden' id='unitQueId"+questionNum+"' value='0' /></td>");
		
		}else{
		     $('#essUnitTbl tbody').append("<tr id='tr"+questionNum+"'><td></td><td width='60%' style='vertical-align:middle;'>"+
			 "<textarea class='unitQueCls' rows='3' cols='60' id='unitQues"+questionNum+"' name='unitQues"+questionNum+"'></textarea>&nbsp;&nbsp;<i class='fa fa-times' style='cursor: hand; cursor: pointer; font-size: 20px; color: #CD0000; vertical-align: middle;' onclick='removeEssQuestion("+questionNum+")'></i>" +
			 "</td><td><input type='hidden' id='unitQueId"+unitQuesCnt+"' name ='unitQueId"+unitQuesCnt+"' value='0' /></td>");
		}
		document.getElementById('essUnitQueLen').value = questionNum+1;		
	}else{		
		alert("You have added Max number of questions");
	}	 
}

function removeEssQuestion(id){
	var unitQuesId =$('#unitQueId'+id).val();
	var unitQuesCnt = $('.unitQueCls').length;
	if(unitQuesCnt > 3){
		if(unitQuesId > 0){
			$.ajax({
				url : "removeEssUnitQues.htm",
				type : "POST",
		   		data: "unitQuesId="+unitQuesId,
				success : function(data){
					$('#tr'+id).remove();
				 	systemMessage(data.status);
				}
			});
		}else{
			$('#tr'+id).remove();
		}
	}else{
		alert("Minimum 3 unit questions required");
	}
}
var unitQuesArr = [];
function saveEssentialQues(){
	var essQuestion = $('#essQuestion').val(); 
	var stemUnitId=parseInt($('#stemUnitId').val());
	if(essQuestion == ""){
		alert("Please fill the Essential Question");
		return false;
	}
	if(stemUnitId == "" && stemUnitId == 0){
		alert("Please Create STEM Unit");
		return false;
	}
	
	var questionNum = $('.unitQueCls').length;
	$(".unitQueCls").each(function() {
		var unitQues = $(this)[0].value;
		if(!unitQues){
			unitQuesArr.length=0;
			alert("Please fill all the Unit Questions");
			return false;
		}else{
			unitQuesArr.push(unitQues);
		}
	});

	$("#loading-div-background").show();
	 $.ajax({  
			url : "saveEssentialQues.htm", 
			type : "POST",
			data: "essQuestion="+essQuestion+"&unitQuesArr="+unitQuesArr+"&stemUnitId="+stemUnitId,
  	    success : function(data) { 
  	    	$("#loading-div-background").hide();
  	    	$("#tab2").css("pointer-events", "auto");
  	        stemTabs.goToTab(2);
  	    	getStemAreas();
  	    	systemMessage(data);
	     }
	 });
 }

function getStemAreas(){
	var stemUnitId = $('#stemUnitId').val();
		if(stemUnitId ){
		stemAreaIdLt.length=0;
		$('#totStemArea').val(0);
		$('#selectedStemAreasDiv').html("");
		$('#contentQuestionsDiv').html("");
		var type = $('input[name="stemAreasType"]:checked').val();
		$("#loading-div-background").show();
			$.ajax({
				url : "getSTEMContentQuestions.htm",
				type : "GET",
				data: "stemUnitId="+stemUnitId+"&type="+type,
				contentType: false,
		    	cache: false,
		    	processData:false,
				success : function(response){
					$('#stemAreasDiv').html("");
				    $('#stemAreasDiv').append(response);
					$('#tab3submitDiv').hide();
				    $("#loading-div-background").hide();
				}
			});
		}
		else{
			alert("Please create STEM unit to proceed further");
			return false;
		}
}

var totStemArea = 0;
function addStemArea(stemAreaId,stemArea){
	var totStemArea = parseInt($('#totStemArea').val());
	var stemUnitId =$('#stemUnitId').val();
	if(!totStemArea)
		totStemArea = 0;
	var stemAreasContentQueDiv ='';
	var divId = stemArea.replace(/\s/g, "")+'Div';
	var contentQueDivId = stemArea.replace(/\s/g, "")+'ContentMainDiv';
	if($.inArray(stemAreaId, stemAreaIdLt) == -1){
		if(confirm("Do you want "+stemArea+" STEM Area?",function(status){
			if(status){
				var addUnitStemArea = function(status) {
					if(status != -1){
						stemAreaIdLt.push(stemAreaId);
						var stemAreaBtn = "<span id='"+divId+"'style='padding:1em;'><input type='hidden' id='unitStemAreasId"+stemAreaId+"' name='unitStemAreasId"+stemAreaId+"' value='"+status+"'><span id='button"+stemAreaId+"' class='subButtons subButtonsWhite medium' onClick='openStrandsWindow("+status+","+stemAreaId+",\""+stemArea+"\",-1)'>"+stemArea+"</span><i class='fa fa-times' aria-hidden='true' style='cursor: hand; cursor: pointer;font-size: 20px;color:#CD0000;vertical-align:middle;' onclick=removeStemArea('"+divId+"','"+contentQueDivId+"',"+stemAreaId+","+totStemArea+")></i></span>";
						$('#selectedStemAreasDiv').append(stemAreaBtn);
						stemAreasContentQueDiv += addStemAreaStandards(stemAreaId,stemArea,totStemArea);
						$('#contentQuestionsDiv').append(stemAreasContentQueDiv);
					    totStemArea=totStemArea+1;
						$('#totStemArea').val(totStemArea);
					}
					else{
						systemMessage("Unable to Add");
					}
				}
				stemCurriculumService.saveUnitStemAreas(stemUnitId, stemAreaId,{
					callback : addUnitStemArea
				 });					
			}

		}));
		}else{
			alert("Already added !!");
		}
}

function addStemAreaStandards(stemAreaId,stemArea,num){
	var stemAreasContentQueDiv = '';
	var divId = stemArea.replace(/\s/g, "")+'ContentMainDiv';
	stemAreasContentQueDiv = "<table width='85%' id='"+divId+"' style='padding-bottom: 1em;'><tr><td class='tabtxt' width='60%' style='color:#000000;'><span style='color:blue;font-weight:bold;'>"+stemArea+" </span>Content Questions: </td><td  width='40%' align='right'><span class=button_green onclick='addContentQuestions("+stemAreaId+",\""+stemArea.replace(/\s/g, "")+"\")'>Add Content Question</span></td></tr>" +
							 "<tr><td colspan='2' width='100%' id='conceptsDiv"+stemAreaId+"'></td></tr><tr><td colspan='2' width='100%'><input type='hidden' id='"+stemArea.replace(/\s/g, "")+"QuesSize' value=0><input type='hidden' id='stemArea"+stemAreaId+"' value="+stemArea+"><div id='"+stemArea.replace(/\s/g, "")+"QuestionsDiv' class='questionDiv'></td></tr></table>";
    return stemAreasContentQueDiv;
}

function removeStemArea(divId,contentQueDivId,stemAreaId,num){
	var unitStemAreasId =  $('#unitStemAreasId'+stemAreaId).val();
	var stemUnitId =$('#stemUnitId').val();
	var totStemArea = parseInt($('#totStemArea').val());
	if(unitStemAreasId > 0){
		if(confirm("Found related data. Are you Sure to delete STEM Area?",function(status){
			if(status){
				var deleteUnitStemStrandsByUnitStemAreIdCallBack = function(status) {
					if(status){
						systemMessage("Removed");
						getStemAreas();
					}else{
						systemMessage("Unable to Remove");
					}
				}
				stemCurriculumService.deleteUnitStemArea(unitStemAreasId,{  
					callback : deleteUnitStemStrandsByUnitStemAreIdCallBack
				 });
			}
		}));
	}else{
		$('#'+divId).remove();
		$('#'+contentQueDivId).remove();
		systemMessage("Removed");
		totStemArea = totStemArea-1;
		$('#totStemArea').val(totStemArea);
		if($.inArray(stemAreaId, stemAreaIdLt) > -1){
			stemAreaIdLt.splice($.inArray(stemAreaId, stemAreaIdLt), 1);
		}
	}
	
}

function addContentQuestions(stemAreaId,stemArea){
	var mode = $('#mode').val();
	var quesSize = 0;
	if($("#"+stemArea+"QuesSize").val())
		quesSize = parseInt($("#"+stemArea+"QuesSize").val());
	if(mode == 'create'){
		$("#tab3submitDiv").show();
		$("#"+stemArea+"QuestionsDiv").append('<span id="'+stemArea.replace(/\s/g, "")+'Div'+quesSize+'" style="margin-left: 2em;"><textarea rows="2" cols="100" id="'+stemArea.replace(/\s/g, "")+''+quesSize+'" name="'+quesSize+'" style="margin-bottom: 5px;margin-top:.2em;"></textarea> &nbsp;&nbsp;<i class="fa fa-times" style="cursor: hand; cursor: pointer;font-size: 20px;color:#CD0000;vertical-align:middle;" onclick="removeContentQuestion('+stemAreaId+',\''+stemArea+'\','+quesSize+')"></i><input hidden id="'+stemAreaId+':'+quesSize+'" name="'+stemAreaId+':'+quesSize+'" value=""/><br></span>');
	}else{
		$("#tab3submitDiv").hide();
		$("#"+stemArea+"QuestionsDiv").append('<span id="'+stemArea.replace(/\s/g, "")+'Div'+quesSize+'" style="margin-left: 2em;"><textarea rows="2" cols="100" id="'+stemArea.replace(/\s/g, "")+''+quesSize+'" name="'+quesSize+'" style="margin-bottom: 5px;margin-top:.2em;" onblur="updateContentQuestions(\''+stemArea+'\','+stemAreaId+','+quesSize+')"></textarea> &nbsp;&nbsp;<i class="fa fa-times" style="cursor: hand; cursor: pointer;font-size: 20px;color:#CD0000;vertical-align:middle;" onclick="removeContentQuestion('+stemAreaId+',\''+stemArea+'\','+quesSize+')"></i><input hidden id="'+stemAreaId+':'+quesSize+'" name="'+stemAreaId+':'+quesSize+'" value=""/><br></span>');
	}
	document.getElementById(stemArea.replace(/\s/g, "")+"QuesSize").value = quesSize+1;	
	$('#'+stemArea.replace(/\s/g, "")+''+quesSize).focus();
}

function openStrandsWindow(unitStemAreaId, stemAreaId, stemArea,stemGradeStrandId){
	if(stemAreaId > 0){
		$("#loading-div-background").show();
		var screenWidth = $( window ).width() - 500;
		var screenHeight = $( window ).height() -10;
		var gradeId = parseInt(parent.jQuery("#gradeId").val());
		jQuery.curCSS = jQuery.css;
		$("#dialog").dialog({
			overflow: 'auto',
			dialogClass: 'no-close',
		    autoOpen: false,
			position: {my: "center", at: "center", of:window ,within: $("body") },
		    title: 'Select Standards',
		    draggable: true,
		    width : screenWidth,
		    height : screenHeight,
		    resizable : true,
		    modal : true,
		    close: function (ev, ui) {
		    	getStemAreas();
		    } 
		});
		 var iframe = $('#iframe');
		 iframe.ready(function() {
			  iframe.attr('src', "openStrandsWindow.htm?unitStemAreaId="+unitStemAreaId+"&stemAreaId="+stemAreaId+"&stemArea="+stemArea+"&gradeId="+gradeId+"&stemGradeStrandId="+stemGradeStrandId);
		 });
		 iframe.load(function () {
			 $("#loading-div-background").hide();
		 });
		 $("#dialog").dialog("open");
	}
}

function getGradeStrandsByStemArea(stemAreaId){
	var gradeId =  parseInt($("#gradeId").val());
	var stemArea = $('#stemArea').val();
	var stemStrandConceptsLt = [];
	var unitStemStrandsId = 0;
	if(window.parent.$('#unitStemStrandsId'+stemAreaId).val()){
		unitStemStrandsId = parseInt(window.parent.$('#unitStemStrandsId'+stemAreaId).val());
	 }
	var getGradeStrandsCallBack = function(list) {
		 if (list != null) {
			 if(list.length > 0){
				dwr.util.removeAllOptions('stemGradeStrandId');
				dwr.util.addOptions('stemGradeStrandId', ["select strand"]);
				dwr.util.addOptions('stemGradeStrandId', list, 'stemGradeStrandId', 'stemStrandTitle');
				if(unitStemStrandsId > 0){
					var getUnitStemStrandsByUnitStemStrandsIdCallBack = function(unitStemStrandsObj) {
						$('select[name="stemGradeStrandId"]').val(unitStemStrandsObj.stemGradeStrands.stemGradeStrandId); 
						stemStrandConceptsLt =  unitStemStrandsObj.stemGradeStrands.stemStrandConceptsLt;
						stemStrandConceptsLt = stemStrandConceptsLt.filter(function(elem, pos) {return stemStrandConceptsLt.indexOf(elem) == pos;});
						var stemConceptsLen= unitStemStrandsObj.unitStemConceptsLt.length;
						loadStrandConcepts(stemStrandConceptsLt, unitStemStrandsId);
						 for (i=0;i<stemConceptsLen;i++){
							 var unitStemConceptsId =  unitStemStrandsObj.unitStemConceptsLt[i].stemStrandConcepts.stemStrandConceptId;
							 $('input:checkbox[id=\''+unitStemConceptsId+'\']').attr('checked',true);
							 $('#tr'+unitStemConceptsId).attr('style','color: #549c00;font-weight: bold;');
						 }
						 $('#narrative').val(unitStemStrandsObj.narrative);
					}
					stemCurriculumService.getUnitStemStrandsByUnitStemStrandsId(unitStemStrandsId,{
						callback : getUnitStemStrandsByUnitStemStrandsIdCallBack
					 });
				}
			 }else{
				systemMessage("No Standards Available");
			 }
		}else{
			systemMessage("No Standards Available");
		}
	}
	stemCurriculumService.getGradeStrands(gradeId,stemArea,{
		callback : getGradeStrandsCallBack
	 });
}

function getStrandConceptsContent(stemAreaId){
	if(stemAreaId > 0){
		var unitStemStrandsId = 0;
		var stemStrandConceptsLt = [];
		if($('#unitStemStrandsId'+stemAreaId).val()){
			unitStemStrandsId = parseInt($('#unitStemStrandsId'+stemAreaId).val());
		 }
		if(unitStemStrandsId > 0){
			var getUnitStemStrandsByUnitStemStrandsIdCallBack = function(unitStemStrandsObj) {
				for (i=0;i<unitStemStrandsObj.stemGradeStrands.stemStrandConceptsLt.length;i++){
					if(unitStemStrandsObj.unitStemConceptsLt[i]){
						stemStrandConceptsLt.push(unitStemStrandsObj.unitStemConceptsLt[i]);
					}
				 }
				stemStrandConceptsLt = stemStrandConceptsLt.filter(function(elem, pos) {return stemStrandConceptsLt.indexOf(elem) == pos;});
				 var strandConceptsDiv = "<table with='100%'><tr><td style='padding-left:1em;' height='10' class='tabtxt'>"+stemStrandConceptsLt[0].unitStemStrands.stemGradeStrands.stemStrandTitle+" &nbsp; - </td><td style='padding-left:1em;' height='10' class='tabtxt'>";
				 var j = 0;
				 var stemConcept;
				 for (i=0;i<stemStrandConceptsLt.length;i++){
					 j++;
					 if(stemStrandConceptsLt[i].stemStrandConcepts.stemConcept != ''){
						 stemConcept = stemStrandConceptsLt[i].stemStrandConcepts.stemConcept;
					 }
					 else{
						 stemConcept = "Concept " + j ; 
					 }
					strandConceptsDiv +="<a href='#' title='"+stemStrandConceptsLt[i].stemStrandConcepts.stemConceptDesc+"' class='tooltip'>"+stemConcept;
					if(i < stemStrandConceptsLt.length-1)
						 strandConceptsDiv +=", ";
				 }
				 strandConceptsDiv +="</td></tr></table>";
				 $('#conceptsDiv'+stemAreaId).html(strandConceptsDiv);
			}
			stemCurriculumService.getUnitStemStrandsByUnitStemStrandsId(unitStemStrandsId,{
				callback : getUnitStemStrandsByUnitStemStrandsIdCallBack
			 });
		}
	}
}

function getStrands(){
	$("#strandConceptsDiv").html("");
	var masterGradeId = $('#masterGradeId').val();
	var stemArea = $('#stemArea').val();
	
	if(masterGradeId == 'select' || masterGradeId == null )
		{
		alert("please select the Grade");
		}
	 if(masterGradeId != 'select' || masterGradeId != null ){
		 $.ajax({
			type : "GET",
			url : "getStemStrands.htm",
			data : "gradeId=" + masterGradeId + "&stemArea=" + stemArea,
			success : function(response2) {
				var stemGradeStrands = response2.stemGradeStrands;
				$("#stemGradeStrandId").empty();
				$("#stemGradeStrandId").append($("<option></option>").val('select').html('Select Strand'));
				$.each(stemGradeStrands, function(index, value) {
					$("#stemGradeStrandId").append($("<option></option>").val(value.stemGradeStrandId).html(value.stemStrandTitle));
				});
				if(callback)
					callback();
			}
		});

	}
}

function getStrandconcepts(){
	$("#strandConceptsDiv").html("");
	var stemGradeStrandId = $('#stemGradeStrandId').val();

		if(stemGradeStrandId != 'select'){
		var stemAreaId = $('#stemAreaId').val();
		var unitStemAreaId = 0;
		if(window.parent.$('#unitStemAreasId'+stemAreaId).val()){
			unitStemAreaId = parseInt($('#unitStemAreaId').val());
		 }
		var getStrandConceptsCallBack = function(list) {
			loadStrandConcepts(list, unitStemAreaId);
			if(unitStemAreaId > 0){
				var getUnitStemStrandsByUnitStemStrandsIdCallBack = function(unitStemStrandsObj) {
					var stemConceptsLen= unitStemStrandsObj.unitStemConceptsLt.length;
					 for (i=0;i<stemConceptsLen;i++){
						 var unitStemConceptsId =  unitStemStrandsObj.unitStemConceptsLt[i].stemStrandConcepts.stemStrandConceptId;
						 $('input:checkbox[id=\''+unitStemConceptsId+'\']').attr('checked',true);
						 $('#tr'+unitStemConceptsId).attr('style','color: #549c00;font-weight: bold;');
					 }
					 $('#narrative').val(unitStemStrandsObj.narrative);
				}
				stemCurriculumService.getUnitStemStrandsByUnitStemStrandsId(stemGradeStrandId,unitStemAreaId,{
					callback : getUnitStemStrandsByUnitStemStrandsIdCallBack
				 });
			}
		}
		stemCurriculumService.getStrandConcepts(stemGradeStrandId,{
			callback : getStrandConceptsCallBack
		 });
	}
}

function loadStrandConcepts(list, unitStemStrandsId){
	 if (list.length > 0) {
		 var mode = window.parent.$('#mode').val();
		 var strandConceptsDiv = "<table width='100%' style='font-family:Cambria, Palatino, \"Palatino Linotype\", \"Palatino LT STD\", Georgia, serif;'>";
		 for (i=0;i<list.length;i++){
		  if(list[i]){
			 if(i == 0 && list[0].stemGradeStrands.addedDesc != null)
				strandConceptsDiv += "<tr><td colspan='3' style='padding-top:1em;'><table with='100%'><tr><td style='padding-left:3em;' colspan='3'><b>"+list[0].stemGradeStrands.addedDesc+"</b></td></tr></table></td></tr>";
			 strandConceptsDiv += "<tr id='tr"+list[i].stemStrandConceptId+"' valign='top'><td style='padding-left:2em;'><input type='hidden' id='stemConcept"+list[i].stemStrandConceptId+"' name='stemConcept"+list[i].stemStrandConceptId+"' value='"+list[i].stemConcept+"' ><input type='checkbox' id='"+list[i].stemStrandConceptId+"' name='strandConceptId' class='checkbox-design' value='"+list[i].stemStrandConceptId+"'  onClick="+ (unitStemStrandsId > 0 ? 'saveStemAreaStrands(this)' : 'showStrandsStatus(this)')+"><label for='"+list[i].stemStrandConceptId+"' class='checkbox-label-design'></label></td><td>"+ (list[i].stemStrandConceptDetails.length > 0 ? "<div onClick=\'showOrHideConceptDiv("+i+")\' style='color:blue;text-decoration: underline;cursor: pointer;'>"+list[i].stemConcept+"</div>" : ""+list[i].stemConcept+"")+"</td><td style='font-size:14px;'>";
			 if(list[i].stemConceptDesc != null)
				 strandConceptsDiv += list[i].stemConceptDesc; 
			 strandConceptsDiv += "</td></tr>";
			 if(list[i].stemStrandConceptDetails.length > 0){
				strandConceptsDiv += "<tr><td colspan='3' style='padding-left:2em;'><table width='100%' id='toggle"+i+"' style='display:none;color:#bf370c;'>";
				 for (j=0;j<list[i].stemStrandConceptDetails.length;j++){
					 strandConceptsDiv +="<tr><td style='padding-left:4em;'>"+ (list[i].stemStrandConceptDetails[j].stemConceptSubCategory.length > 0 ? "<div onClick=\'showOrHideSubConceptDiv("+i+","+j+")\' style='color:blue;text-decoration: underline;cursor: pointer;'><li>"+list[i].stemStrandConceptDetails[j].conceptDetDesc+"</li></div>" : "<li>"+list[i].stemStrandConceptDetails[j].conceptDetDesc+"")+"</li></td></tr>";
					 if(list[i].stemStrandConceptDetails[j].stemConceptSubCategory.length){
						 strandConceptsDiv += "<tr><td colspan='3' style='padding-left:6em;'><table width='100%' id='toggle"+i+""+j+"' style='display:none;color:#bf370c;'>";
						 for (k=0;k<list[i].stemStrandConceptDetails[j].stemConceptSubCategory.length;k++){
							 strandConceptsDiv +="<tr><td style='padding-left:2em;'>"+(list[i].stemStrandConceptDetails[j].stemConceptSubCategory[k].stemConceptSubCategoryItems.length > 0 ? "<div onClick=\'showOrHideDiv("+i+","+j+","+k+")\' style='color:blue;text-decoration: underline;cursor: pointer;'><li>"+list[i].stemStrandConceptDetails[j].stemConceptSubCategory[k].subCategoryDesc+"</li></div>" : "<li>"+list[i].stemStrandConceptDetails[j].stemConceptSubCategory[k].subCategoryDesc+"")+"</li></td></tr>";
							 if(list[i].stemStrandConceptDetails[j].stemConceptSubCategory[k].stemConceptSubCategoryItems.length){
								 strandConceptsDiv += "<tr><td colspan='3' style='padding-left:6em;'><table width='100%' id='toggle"+i+""+j+""+k+"' style='display:none;color:#bf370c;'>";
								 for (m=0;m<list[i].stemStrandConceptDetails[j].stemConceptSubCategory[k].stemConceptSubCategoryItems.length;m++){
									 strandConceptsDiv +="<tr><td style='padding-left:2em;'><li>"+list[i].stemStrandConceptDetails[j].stemConceptSubCategory[k].stemConceptSubCategoryItems[m].itemDesc+"</li></td></tr>";
								 }
								 
								 strandConceptsDiv +="</table></td></tr>";
							 }
						 }
						 
						 strandConceptsDiv +="</table></td></tr>";
					 }
				 }
				strandConceptsDiv +="</table></td></tr>";
			}
		   }
		}
		 strandConceptsDiv += "<tr><td colspan='3'><table with='100%'><tr><td style='padding-left:2em;' height='40' class='tabtxt'>What is major Conceptual understanding that the students should master?</td></tr><tr><td style='padding-left:2em;'><textarea rows=3 cols=80 id=narrative name=narrative onblur="+ (unitStemStrandsId > 0 ? 'saveStemAreaStrands(this)' : '')+"></textarea></td></tr></table></td></tr>";
		 strandConceptsDiv += "</table>";
		 $("#strandConceptsDiv").html(strandConceptsDiv);
		 if(unitStemStrandsId > 0){
		   $('#submitDiv').hide();
		   $('#doneDiv').show();
		 }else{	 
		   $('#submitDiv').show();
		 }
	 }else{
		 $('#submitDiv').hide();
	 }
}
function showOrHideConceptDiv(i){
	if($("#toggle"+i).is(':hidden')){
		$("#toggle"+i).show();
	}else{
		$("#toggle"+i).hide();
	}
}

function showOrHideSubConceptDiv(i,j){
	var display = $("#toggle"+i+""+j).css("display");
	if(display == 'none')
		$("#toggle"+i+""+j).css('display', 'block');
	else if(display == 'block')
		$("#toggle"+i+""+j).css('display', 'none');
}

function showOrHideDiv(i,j, k){
	var display = $("#toggle"+i+""+j+""+k).css("display");
	if(display == 'none')
		$("#toggle"+i+""+j+""+k).css('display', 'block');
	else if(display == 'block')
		$("#toggle"+i+""+j+""+k).css('display', 'none');
}
function saveStemAreaStrands(obj){
	var stemGradeStrandId = $('#stemGradeStrandId').val();
    var mode = window.parent.$('#mode').val();
	var unitStemAreaId = $('#unitStemAreaId').val();
	var narrative = $('#narrative').val();
	var stemUnitId = window.parent.$('#stemUnitId').val();
	var strandConceptIdArr = [];
     $.each($("input[name='strandConceptId']:checked"), function(){            
    	 strandConceptIdArr.push(parseInt($(this).val()));
     });
   /*  if(strandConceptIdArr.length == 0){
    	 $('#tr'+obj.id).attr('style','color: black;font-weight: normal;');
    	 systemMessage("Please select aleast one Strand Concept");
    	 $('#narrative').focus();
    	 return false;
     }*/
    $("#loading-div-background").show();
 	if(unitStemAreaId && stemGradeStrandId){	    
     $.ajax({  
  		url : "saveStrandConcept.htm",
  		type : "GET",
    	data: "narrative=" +narrative+"&stemGradeStrandId="+stemGradeStrandId+"&strandConceptIdArr="+strandConceptIdArr+"&mode="+mode+"&unitStemAreaId="+unitStemAreaId,
  		success : function(data) {
  			$("#loading-div-background").hide();
  			if(data.unitStemStrandsId > 0){
  				if(obj){
  					var stemConcept = $('#stemConcept'+obj.id).val();
  					if($('#'+obj.id).prop('checked')){
  						systemMessage(stemConcept+" Added !!");
  						$('#tr'+obj.id).attr('style','color: #549c00;font-weight: bold;');
  					}else{ 
  						if(stemConcept != undefined){
	  						systemMessage(stemConcept+" Removed !!","error");
	  						$('#tr'+obj.id).attr('style','color: black;font-weight: normal;');
  						}
  						else{
  							systemMessage("Conceptual understanding has been saved !!");
  	  						$('#tr'+obj.id).attr('style','color: #549c00;font-weight: bold;');
  						}
  					}
  				}else{
  					systemMessage(data.status);
  				}
  			}else{
  			  systemMessage(data.status);
  			}
  		}  
 	});
 	}
}

function showStrandsStatus(obj){
	var stemConcept = $('#stemConcept'+obj.id).val();
	if($('#'+obj.id).prop('checked')){
		systemMessage(stemConcept+" Added !!");
		$('#tr'+obj.id).attr('style','color: #549c00;font-weight: bold;');
	}else{ 
		systemMessage(stemConcept+" Removed !!","error");
		$('#tr'+obj.id).attr('style','color: black;font-weight: normal;');
	}
}

function getUnitStemArea(){
	var stemUnitId =$('#stemUnitId').val();
	if(stemUnitId ){		
		var activityContainer = $(document.getElementById('activitiesDiv'));
		$(activityContainer).empty();
		if(stemUnitId != ""){
		   $("#loading-div-background").show();
		   var formatter = function (entry) {
				var stemArea = '';
				stemArea = entry.stemAreas.stemArea;
				return stemArea;
			}			 
			$.ajax({
				url : "getUnitStemAreas.htm",
				type : "GET",
				data: "stemUnitId="+stemUnitId,
				success : function(response){					
					if (response.unitStemAreasLt != null) {
						dwr.util.removeAllOptions('stemActiviyAreaId');
						dwr.util.addOptions('stemActiviyAreaId', ['Select Area']);
						dwr.util.addOptions('stemActiviyAreaId', response.unitStemAreasLt, 'unitStemAreasId', formatter);
					}
				    $("#loading-div-background").hide();
				}
			});
		}else{
			alert("Please Create STEM Unit");
			return;
		}
	}
	else{
		alert("Please create STEM unit to proceed further");
		return false;
	}
}

function getStemUnitActivities(selectObject){
	var unitStemAreaId = selectObject.value; 
	var activityContainer = $(document.getElementById('activitiesDiv'));
	$(activityContainer).empty(); 
	
	if(unitStemAreaId != "Select Area"){
		$("#loading-div-background").show();
		$.ajax({  
     		url : "getStemUnitActivities.htm",
     		type : "POST",
       		data: "unitStemAreaId=" +unitStemAreaId,
     		success : function(data) {
     			$("#createId").show();
     			$("#loading-div-background").hide();
     			$(activityContainer).append(data);
     		}  
    	});
	}else{
		$("#createId").hide();
		return;
	}
}

function saveStemAct(){
	var formObj = document.getElementById("stemActivitiesForm");
	var formData = new FormData(formObj);
	var stemUnitId = $('#stemUnitId').val();
	if(stemUnitId > 0){		
		var stemActiviyAreaId = $('#stemActiviyAreaId').val();
		formData.append('unitStemAreasId', stemActiviyAreaId);
		$("#loading-div-background").show();
	    $.ajax({  
	     	url : "saveStemActivities.htm",
	     	type : "POST",
	       	data: formData,
	       	mimeType:"multipart/form-data",
	    	contentType: false,
	    	cache: false,
	    	processData:false
	    }).done(function(data) {    		
	    	$("#loading-div-background").hide();
	    	var res = JSON.parse(data);
	    	var noOfActQues = parseInt($('#noOfActQues').val());
	    	for(var i=0;i<noOfActQues;i++){
	    		document.getElementById("actId"+i).value = res.activities[i].stemActivityId;
	    	}
	    	systemMessage(res.status);
	 	});
	}else{
		alert("Please Create Stem Unit");
		return;
	}
}
var contentQueMap = {};
var stemAreaIdLt = [];
var stemAreaMap = {};
function saveStemContentQuestions(){
	if(stemAreaIdLt.length > 0){
		for (var i = 0; i < stemAreaIdLt.length; i++) {
			var stemAreaId =  stemAreaIdLt[i];
			var stemArea = $('#stemArea'+stemAreaId).val().replace(/\s/g, "");
			var unitStemAreasId = $('#unitStemAreasId'+stemAreaId).val();
			var keys = [];
			$('#'+stemArea+"QuestionsDiv textarea").each(function(){
				keys.push($(this).val());
		    });
			contentQueMap[unitStemAreasId] = keys;
			stemAreaMap[unitStemAreasId] = stemArea;
		}
	}
  $("#loading-div-background").show();
  $.ajax({  
     	 url : "saveStemContentQuestions.htm",
     	 type : "POST",
         contentType : "application/json",
         data : JSON.stringify(contentQueMap),
         success : function(response) {
        	 contentQueMap = {};
        	 getStemAreas();
         }		
   })
}


function essUnitQues(){
	var stemUnitId = $('#stemUnitId').val();
	if(stemUnitId ){
		var essContainer = $(document.getElementById('stemEssDiv'));
		$(essContainer).empty(); 
		if(!stemUnitId){
			stemUnitId = 0;
		}
		$("#loading-div-background").show();
		$.ajax({
			url : "openEssentialUnit.htm",
			type : "POST",
	   		data: "stemUnitId="+stemUnitId,
			success : function(data){
				$(essContainer).append(data);
				$("#loading-div-background").hide();				
			}
		});
	}
	else{
		alert("Please create STEM unit to proceed further");
		return false;
	}
}

function viewSharedActivities(id){
	var gradeId = window.parent.$('#gradeId').val(); 
	var stemAreaId = $("#stemActiviyAreaId").val(); 
	var stemUnitId = $('#stemUnitId').val();
	var screenWidth = $( window ).width();
	var screenHeight = $( window ).height();
	jQuery.curCSS = jQuery.css;
	$("#sharedDialog").dialog({
		overflow: 'auto',
		dialogClass: 'no-close',
	    autoOpen: false,
		position: {my: "center", at: "center", of:window ,within: $("body") },
	    title: 'Shared Activities',
	    draggable: true,
	    width : screenWidth,
	    height : screenHeight,
	    resizable : true,
	    modal : true,
	    close: function (ev, ui) {} 
	});
	var shareContainer = $(document.getElementById('shareDiv'));
	$(shareContainer).empty(); 
	$.ajax({
		url : "getStemSharedActivities.htm",
		type : "POST",
   		data: "gradeId="+gradeId+"&stemAreaId="+stemAreaId+"&stemUnitId="+stemUnitId,
		success : function(data){
			$(shareContainer).append(data);
			document.getElementById("parentActId").value = id;
		}
	});
	$("#sharedDialog").dialog("open");
}

function addSharedActivity(){
	var childId = $("input[name='activity']:checked").val();
	var parentActId = $("#parentActId").val();
	if(typeof(childId) == "undefined"){
		alert("Please Choose Activity");
		return false;
	}
	document.getElementById("desc"+parentActId).value = $("#shareDesc"+childId).val();
	document.getElementById("link"+parentActId).value = $("#shareLink"+childId).val();
	document.getElementById("referId"+parentActId).value = $("#activity"+childId).val();
	$("#sharedDialog").dialog("close");
}

function getFileData(myFile,span){
   var file = myFile.files[0];  
   var filename = file.name;
   document.getElementById(span.id).innerHTML = filename;   
}

function updateContentQuestions(stemArea,stemAreaId,id){
	var	unitStemAreasId = $('#unitStemAreasId'+stemAreaId).val();
	var contentQuesId =  document.getElementById(stemAreaId+':'+id).value;
	var contentQue = document.getElementById(stemArea+''+id).value;
	if(!contentQuesId)
		contentQuesId = 0
	if(!unitStemAreasId)
		unitStemAreasId = 0
		
	if(contentQue){	
		$.ajax({
			url : "updateContentQuestions.htm",
			type : "POST",
	   		data: "contentQuesId="+contentQuesId+"&unitStemAreasId="+unitStemAreasId+"&contentQue="+contentQue,
			success : function(response){
				if(contentQuesId == 0)
					document.getElementById(stemAreaId+':'+id).value = response.contentQuesId;
					systemMessage(response.status);
			}
		});
	}
}

function removeContentQuestion(stemAreaId,stemArea,j){
	var contentQuesId =  document.getElementById(stemAreaId+':'+j).value;
	if(contentQuesId){	
		$.ajax({
			url : "deleteContentQuestions.htm",
			type : "POST",
	   		data: "contentQuesId="+contentQuesId,
			success : function(response){
				systemMessage(response.status);
				$('#'+stemArea+'Div'+j).remove();
			}
		});
	}else{
		$('#'+stemArea+'Div'+j).remove();
	}
}

function stemFiveC(){
	var stemUnitId = $('#stemUnitId').val();
	if(stemUnitId ){
		var fiveCContainer = $(document.getElementById('stemFiveCDiv'));
		$("#loading-div-background").show();
		$.ajax({
			url : "getStemFiveCs.htm",
			type : "POST",
			success : function(data){
				$(fiveCContainer).empty(); 
				$(fiveCContainer).append(data);
	        	$("#loading-div-background").hide();				
			}
		});
	}
	else{
		alert("Please create STEM unit to proceed further");
		return false;
	}
}

function getUnitStemStrategies(){
	var stemUnitId = $('#stemUnitId').val();
	var answerContainer = $(document.getElementById('stemStrategiesDiv'));
	$(answerContainer).empty(); 
	if(stemUnitId > 0){
		$("#loading-div-background").show();
		$.ajax({  
     		url : "getUnitStemStrategies.htm",
     		type : "POST",
     		//dataType: "text/html",
       		data: "stemUnitId="+stemUnitId,
     		success : function(data) {
     			$(answerContainer).append(data);
     			$("#loading-div-background").hide();
     		}  
    	});
	}else{
		alert("Please Create STEM Unit to proceed further");
		return;
	}
}

function setStrategColor(id,stemUnitId){
	var stgColor=document.getElementById("tdStrateg"+id).style.backgroundColor;
	if(stgColor=="yellow"){
		document.getElementById("tdStrateg"+id).style.backgroundColor ="white";
		document.getElementById("unitStrateg"+id).checked = false;
		var stemStrategiesId=document.getElementById("unitStrateg"+id).value;
		saveStemStrategies(stemUnitId,stemStrategiesId,'remove');
	}
	else{
		document.getElementById("tdStrateg"+id).style.backgroundColor ="yellow";
		document.getElementById("unitStrateg"+id).checked = true;
		var stemStrategiesId=document.getElementById("unitStrateg"+id).value;
		saveStemStrategies(stemUnitId,stemStrategiesId,'create');
	}
	$("#tab6").css("pointer-events", "auto");
}

function saveStemStrategies(stemUnitId,stemStrategiesId,mode){
	$.ajax({  
 		url : "saveStemStrategies.htm",
 		type : "POST",
   		data: "stemUnitId="+stemUnitId+"&stemStrategiesId="+stemStrategiesId+"&mode="+mode,
 		success : function(data) {
 			$("#loading-div-background").hide();
 			systemMessage(data.status);
 		}  
	});
}

function viewSubCriteria(id){
	var gradeId = window.parent.$('#gradeId').val(); 
	var count = parseInt($('#count').val());
	var lCriteriaId =  parseInt($('#lCriteriaId'+id).val());
	var stemUnitId =$('#stemUnitId').val();
	if(stemUnitId == "" && stemUnitId == 0){
		alert("Please Create STEM Unit");
		return;
	}
	var subCriteriaContainer = $(document.getElementById('stemSubCriteriaDiv'));
	$(subCriteriaContainer).empty(); 
	$("#loading-div-background").show();
	$.ajax({  
 		url : "getSubCriterias.htm",
 		type : "POST",
   		data: "lCriteriaId="+lCriteriaId+"&stemUnitId="+stemUnitId+"&gradeId="+gradeId,
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

function updateIndicator(chk,id){
	var isChecked = $("#"+chk.id).is(':checked');
	var subCriteriaId =  $('#stemSubCriteriaId'+id).val();
	var stemUnitId = $('#stemUnitId').val();
	var indicatorId = document.getElementById("indicatorId"+id).value;
	var indicatorStatus = document.getElementById("indicatorStatus"+id).value;
	$("#loading-div-background").show();
	if(isChecked){		
		if(indicatorId == ""){
			indicatorId = 0;
		}
		if(indicatorStatus == "" || indicatorStatus == "inactive"){
			indicatorStatus = "active";
		}		
	}else{
		if(indicatorId == ""){
			$("#loading-div-background").hide();
			alert("Unable to remove");
			return;
		}
		if(indicatorStatus == "" || indicatorStatus == "active"){
			indicatorStatus = "inactive";
		}
	}
	$.ajax({  
 		url : "saveStemIndicator.htm",
 		type : "POST",
   		data: "subCriteriaId="+subCriteriaId+"&stemUnitId="+stemUnitId+"&indicatorId="+indicatorId+"&indicatorStatus="+indicatorStatus,
 		success : function(data) {	 			
 			document.getElementById("indicatorId"+id).value= data.stemIndicatorId;
 			document.getElementById("indicatorStatus"+id).value= data.stemStatus;
 			$("#loading-div-background").hide();
 			$("#tab5").css("pointer-events", "auto");
 			systemMessage(data.status);
 		}  
	});	
}

function displayIndicators(id){
	var subCriteriaId =  $('#stemSubCriteriaId'+id).val();
	var gradeId = window.parent.$('#gradeId').val(); 
	var screenWidth = $( window ).width();
	var screenHeight = $( window ).height()-150;
	jQuery.curCSS = jQuery.css;
	$("#stemPerIndicator").dialog({
		overflow: 'auto',
		dialogClass: 'no-close',
	    autoOpen: false,
		position: {my: "center", at: "center", of:window ,within: $("body") },
	    title: 'Stem Performance Indicators',
	    draggable: true,
	    width : screenWidth,
	    height : screenHeight,
	    resizable : true,
	    modal : true,
	    close: function (ev, ui) {} 
	});
	var indicatorContainer = $(document.getElementById('indicatorDiv'));
	$(indicatorContainer).empty(); 
	$.ajax({
		url : "getStemPerIndicators.htm",
		type : "POST",
   		data: "gradeId="+gradeId+"&subCriteriaId="+subCriteriaId,
		success : function(data){
			$(indicatorContainer).append(data);
		}
	});
	$("#stemPerIndicator").dialog("open");
}

function getStemUnitsByTeacherNAdmin() {
	var gradeId = $('#gradeId').val();
	var classId = $('#classId').val();
	var pathId=$('#pathId').val();
	var unitContainer = $(document.getElementById('unitDiv'));
	$(unitContainer).empty();
	if(gradeId > 0 && classId > 0 && pathId>0){
		$("#loading-div-background").show();
		$.ajax({
			type : "GET",
			url : "getStemUnitsByTeacherNAdmin.htm",
			data : "gradeId=" + gradeId + "&classId=" + classId+"&pathId="+pathId,
			success : function(response) {
				 $("#loading-div-background").hide();
				$(unitContainer).append(response);
				$.ajax({
					type : "GET",
					url : "getTeacherSections.htm",
					data : "gradeId=" + gradeId + "&classId=" + classId,
					success : function(response1) {
						var teacherSections = response1.teacherSections;
						$.each(teacherSections, function(index, value) {
							$("#sectionId").append($("<option></option>").val(value.csId).html(value.section.section));
						});
					}
				});
			}
		});
	}
}	

function assignStemCurriculum(){
	var gradeId = $('#gradeId').val();
	var classId = $('#classId').val();
	var sectionId = $('#sectionId').val();
	var dueDate=$('#dueDate').val();
	var checked = false;
	var stemUnitIds = new Array();
	$('input:checkbox').each(function () {
		if ($(this).is(':checked')) {
			stemUnitIds.push($(this).val());	
			checked = true;
      }
	});
	if(!checked){
		alert('please select a unit');
		return false;
	}
	else if(!sectionId){
		alert('please select a section');
		return false;
	}
	else if(!dueDate){
		alert('please select a due date');
		return false;
	}else{	
		$("#loading-div-background").show();
		$.ajax({
			url: 'assignStemCurriculum.htm',
			type: 'POST',
			data: "gradeId=" + gradeId + "&classId=" + classId+"&stemUnitIds="+stemUnitIds+"&dueDate="+dueDate+"&sectionId="+sectionId,
			success: function(data){
				$("#loading-div-background").hide();
				if(data != 'Assign Stem Units failed')
					systemMessage(data);
				else
					systemMessage(data,"error");
				getStemUnitsByTeacherNAdmin();
			}
		});	
	}
	setFooterHeight();
}

function stemAssessments(){
	var assessmentContainer = $(document.getElementById('stemAssessmentDiv'));
	$(assessmentContainer).empty(); 
	var stemUnitId =$('#stemUnitId').val();
	var gradeId = window.parent.$('#gradeId').val(); 
	if(stemUnitId == "" && stemUnitId == 0){
		alert("Please Create STEM Unit");
		return;
	}
	$("#loading-div-background").show();
	$.ajax({
		url : "getFormativeAssessments.htm",
		type : "POST",
		data : "stemUnitId="+stemUnitId+"&gradeId="+gradeId,
		success : function(data){
			$(assessmentContainer).append(data);
        	$("#loading-div-background").hide();
		}
	});
}

function updateFormativeUnit(chk,id){
	var isChecked = $("#"+chk.id).is(':checked');
	var formativeAssessmentId =  $('#formativeAssessmentId'+id).val();
	var stemUnitId = $('#stemUnitId').val();
	var formativeUnitId = document.getElementById("formativeUnitId"+id).value;
	var formativeUnitStatus = document.getElementById("formativeUnitStatus"+id).value;
	$("#loading-div-background").show();
	if(isChecked){		
		if(formativeUnitId == ""){
			formativeUnitId = 0;
		}
		if(formativeUnitStatus == "" || formativeUnitStatus == "inactive"){
			formativeUnitStatus = "active";
		}		
	}else{
		if(formativeUnitId == ""){
			$("#loading-div-background").hide();
			alert("Unable to remove");
			return;
		}
		if(formativeUnitStatus == "" || formativeUnitStatus == "active"){
			formativeUnitStatus = "inactive";
		}
	}
	$.ajax({  
 		url : "saveformativeAssessUnit.htm",
 		type : "POST",
   		data: "formativeAssessmentId="+formativeAssessmentId+"&stemUnitId="+stemUnitId+"&formativeUnitId="+formativeUnitId+"&formativeUnitStatus="+formativeUnitStatus,
 		success : function(data) {	 			
 			document.getElementById("formativeUnitId"+id).value= data.formativeUnitId;
 			document.getElementById("formativeUnitStatus"+id).value= data.formativeUnitStatus;
 			$("#loading-div-background").hide();
 			systemMessage(data.status);
 		}  
	});	
}

function getIpalResources(windowRef){
	var pathId = $("#pathId").val();
	if(pathId == "select")
	{
		alert("please select the path");
		return false;
	}
	var gradeId;
    if(windowRef == 'childWindow')
	{
		gradeId = window.parent.$('#gradeId').val();
	}
	else if(windowRef == 'parentWindow')
	{	
	 gradeId = $('#gradeId').val();
	}
	var screenWidth = $( window ).width();
	var screenHeight = $( window ).height()-150;
	jQuery.curCSS = jQuery.css;
	$("#iPalDailog").dialog({
		overflow: 'auto',
		dialogClass: 'no-close',
	    autoOpen: false,
		position: {my: "center", at: "center", of:window ,within: $("body") },
	    title: 'iPal Resources',
	    draggable: true,
	    width : screenWidth,
	    height : screenHeight,
	    resizable : true,
	    modal : true,
	    close: function (ev, ui) {} 
	});
	var iPalContainer = $(document.getElementById('iPalDiv'));
	$(iPalContainer).empty(); 	
	$.ajax({
		url : "getIpalResources.htm",
		type : "POST",
		data : "&gradeId="+gradeId,
		success : function(data){
			$(iPalContainer).append(data);
		}
	});
	$("#iPalDailog").dialog("open");
}


function autoSaveEssenUnitQues(id,quesTypeId){
	var stemUnitId =$('#stemUnitId').val();
	if(stemUnitId > 0){
		var mode=$('#mode').val();
		var question="";
		var questionId=0;
		if(quesTypeId==1){
			question=document.getElementById("essQuestion").value;
			if(!document.getElementById("essQuestionId"))
				questionId = 0;
			else
				questionId=document.getElementById("essQuestionId").value;
		}else{
			question=document.getElementById("unitQues"+id).value;
			if(!document.getElementById("unitQueId"+id))
				questionId = 0;
			else
				questionId = document.getElementById("unitQueId"+id).value;
		}
		if(question == "" && quesTypeId==1){
			alert("Please fill the Essential Question");
			return;
		}else if(question == "" && quesTypeId==2){
			alert("Please fill the Unit Question");
			return;
		}
		if(stemUnitId == "" && stemUnitId == 0){
			alert("Please Create STEM Unit");
			return;
		}
		$("#loading-div-background").show();
		$.ajax({  
	 		url : "autoSaveEssenUnitQues.htm",
	 		type : "POST",
	   		data: "stemUnitId="+stemUnitId+"&question="+question+"&quesTypeId="+quesTypeId+"&questionId="+questionId,
	 		success : function(data) {	 			
	 			$("#loading-div-background").hide();
	 			systemMessage(data.status);
	 			if(quesTypeId==1){
	 				if(document.getElementById("essQuestionId"))
	 					document.getElementById("essQuestionId").value = data.questionId;
	 			}else{
	 				if(document.getElementById("unitQueId"+id))
	 					document.getElementById("unitQueId"+id).value = data.questionId;
	 			}
	 		}  
		});		
	}
}

function displayAssessmentDetail(id){
	var screenWidth = $( window ).width()-200;
	var screenHeight = $( window ).height()-150;
	jQuery.curCSS = jQuery.css;
	$("#formativeAssessmentDailog").dialog({
		overflow: 'auto',
		dialogClass: 'no-close',
	    autoOpen: false,
		position: {my: "center", at: "center", of:window ,within: $("body") },
	    title: 'Assessment Details',
	    draggable: true,
	    width : screenWidth,
	    height : screenHeight,
	    resizable : true,
	    modal : true,
	    close: function (ev, ui) {} 
	});
	var detailContainer = $(document.getElementById('assessmentDetail'));
	$(detailContainer).empty(); 
	$.ajax({
		url : "getAssessmentDetail.htm",
		type : "POST",
   		data: "formativeAssessmentId="+id,
		success : function(data){
			$(detailContainer).append(data);
		}
	});
	$("#formativeAssessmentDailog").dialog("open");
}
function downloadContent(type){
	var stemUnitId = $('#stemUnitId').val();
	var stemUnitName = $('#stemUnitName').val();
	if(stemUnitId > 0){
		$.ajax({
			url : "stemUnitByStemUnitId.htm",
			type : "GET",
			data: "stemUnitId="+stemUnitId,
			success : function(response){	
			var	stemUnitObj = response.stemUnit;
			$("#loading-div-background").show();
				var content  = loadStemPrintContent(stemUnitObj);
				$("#printContent").html(content);
				if(type == 'print'){
					printThisStemContents("content");
				}else if(type == 'pdf'){
				     kendo.drawing.drawDOM($("#content"))
				     .then(function(group) {
				         return kendo.drawing.exportPDF(group, {
				             paperSize: "auto",
				             margin: { left: "2cm", top: "2cm", right: "2cm", bottom: "2cm" }
				         });
				     })
				     .done(function(data) {
				         kendo.saveAs({
				             dataURI: data,
				             fileName: stemUnitName
				         });
				     });
				}
				$("#content").hide();
				$("#loading-div-background").hide();
			}
		});
	}else{
		alert("No data Found");
	}
}
function printThisStemContents(divId){
	$("#"+divId).print({
        globalStyles: false,
        mediaPrint: false,
        iframe: true,
        noPrintSelector: ".avoid-this"
   });
	
}
var lcMap;
function loadStemPrintContent(stemUnitObj){
	var content = "<div id='content' width='60%' style='width:98%;padding-right:1em;'><table width='100%'>";
	content += "<tr><td height='40'></td></tr>";
	content += "<tr><td align='center' width='100%' style='border: 1px solid #007094;font-size:16px;color:blue;'><b>STEAM Unit Details</b></td></tr><tr><td align='left' width='100%' style='border: 1px solid #007094;'><table width='100%'><tr><td width='20%' style='padding-left:2em;'><b>Stem Name &nbsp;&nbsp;:</b> </td><td width='80%'>"+stemUnitObj.stemUnitName+"</td></tr><tr><td width='20%' style='padding-left:2em;'><b>Stem Desc &nbsp;&nbsp;:</b> </td><td width='80%'>"+stemUnitObj.stemUnitDesc+"</td></tr><tr><td width='20%' style='padding-left:2em;'><b>is Shared &nbsp;&nbsp;:</b> </td><td width='80%'>"+stemUnitObj.isShared+"</td></tr></table></td></tr>";
	if(stemUnitObj.stemQuestionsLt.length > 0){
		content += "<tr><td height='40'></td></tr>";
		content += "<tr><td align='center' width='100%' style='border: 1px solid #007094;font-size:16px;color:blue;'><b>Essential Question & Unit Questions</b></td></tr><tr><td align='left' width='100%' style='border: 1px solid #007094;'><table width='100%'><tr><td width='30%' style='padding-left:2em;'><b>Essential Question &nbsp;&nbsp;:</b> </td><td width='70%'>"+stemUnitObj.stemQuestionsLt[0].stemQuestion+"</td></tr><tr><td colspan='4' width='100%' style='padding-left:2em;'><b>Unit Questions &nbsp;&nbsp;:</b></td></tr>";
		for(var i=1; i<stemUnitObj.stemQuestionsLt.length;i++){
			content += "<tr><td colspan='4' width='70%' style='padding-left:4em;'>"+i+". "+stemUnitObj.stemQuestionsLt[i].stemQuestion+"</td></tr>";
		}	
		content += "</td></tr></table>";
	 }

	
	if(stemUnitObj.uniStemAreasLt.length > 0){
		content += "<tr><td height='40'></td></tr>";
		content += "<tr><td align='center' width='100%' style='border: 1px solid #007094;font-size:16px;color:blue;'><b>STEAM Areas & Content Questions</b></td></tr><tr><td align='left' width='100%' style='border: 1px solid #007094;'>";
		var mainArr = [];
		var additionalArr = [];
		for(i=0; i<stemUnitObj.uniStemAreasLt.length;i++){
			if(stemUnitObj.uniStemAreasLt[i].stemAreas.isOtherStem == "No")
				mainArr.push(stemUnitObj.uniStemAreasLt[i]);
			else
				additionalArr.push(stemUnitObj.uniStemAreasLt[i]);
		}
		var unitStemAreas = $.merge(mainArr,additionalArr);
		var isMainSTEM = true;
		var isOtherSTEM = true;
		for(var i=0; i<unitStemAreas.length;i++){
			content += "<table width='100%'>";
			if(unitStemAreas[i].stemAreas.isOtherStem == "No" && isMainSTEM){
				isMainSTEM = false;
				content += "<tr><td colspan='4' align='left' width='100%' style='border: 1px solid #007094;font-size:16px;padding-left:1em;'><b>Main STEAM Area</b></td></tr>";
			}
			else if(unitStemAreas[i].stemAreas.isOtherStem == "Yes" && isOtherSTEM){
				isOtherSTEM = false;
				content += "<tr><td colspan='4' align='left' width='100%' style='border: 1px solid #007094;font-size:16px;padding-left:1em;'><b>Additional STEAM Area</b></td></tr>";
			}
			content += "<tr><td><table width='100%' style='border: 1px solid #007094;'>";
			content += "<tr><td colspan='4' width='100%' style='padding-left:2em;'><b>"+(i+1)+". "+unitStemAreas[i].stemAreas.stemArea+"</b></td></tr>";
			var unitStemStrandsLt = unitStemAreas[i].unitStemStrandsLt;
			if(unitStemStrandsLt){
				if(unitStemStrandsLt.length>0){
				for(var cnt=0; cnt<unitStemStrandsLt.length; cnt++){
					if(unitStemStrandsLt[cnt]){
						content += "<tr><td colspan='4' width='100%' style='padding-left:6em;'>"+unitStemStrandsLt[cnt].stemGradeStrands.stemStrandTitle+"</td></tr><tr><td><table width='100%'>";
						if(unitStemStrandsLt[i] != null){
							if(unitStemStrandsLt[i].unitStemConceptsLt){
								for(j=0; j<unitStemStrandsLt[i].unitStemConceptsLt.length;j++){							
									content += "<tr><td colspan='4' width='70%' style='padding-left:8em;'>"+(j+1)+". "+unitStemStrandsLt[cnt].unitStemConceptsLt[j].stemStrandConcepts.stemConceptDesc+"</td></tr>";
								}
							}
						}
					}
				}
				content += "</table></td></tr>";
				}
				
			}
			content += "<tr><td height='10'></td></tr><tr><td align='left' width='100%' style='padding-left:4em;'><b>Content Questions</b></td></tr>";
			if(unitStemAreas[i].unitStemContentQuestionsLt){
				for(k=0; k<unitStemAreas[i].unitStemContentQuestionsLt.length;k++){
					content += "<tr><td colspan='4' width='70%' style='padding-left:6em;color:#009caf;font-weight:bold;'>"+(k+1)+". "+unitStemAreas[i].unitStemContentQuestionsLt[k].contentQuestion+"</td></tr>";
				}
			}
			if(unitStemAreas[i].stemUnitActivitiesLt){
				for(k=0; k<unitStemAreas[i].stemUnitActivitiesLt.length;k++){
					if(unitStemAreas[i].stemUnitActivitiesLt[k].stemActivityId > 0){
						content += "<tr><td colspan='4' width='70%' style='padding-left:6em;'><b>Activity</b></td></tr>";
						if(unitStemAreas[i].stemUnitActivitiesLt[k].activityDesc){
							content += "<tr><td colspan='4' width='70%' style='padding-left:8em;'><b>Description : </b>"+unitStemAreas[i].stemUnitActivitiesLt[k].activityDesc+"</td></tr>";
						}
						if(unitStemAreas[i].stemUnitActivitiesLt[k].activityLink){
							content += "<tr><td colspan='4' width='70%' style='padding-left:8em;'><b>Links : </b>"+unitStemAreas[i].stemUnitActivitiesLt[k].activityLink+"</td></tr>";
						}
						if(unitStemAreas[i].stemUnitActivitiesLt[k].fileName){
							content += "<tr><td colspan='4' width='70%' style='padding-left:8em;'><b>FileName : </b>"+unitStemAreas[i].stemUnitActivitiesLt[k].fileName+"</td></tr>";
						}
					}
				}
			}
			content += "</table></td></tr><tr><td height='10'></td></tr></table>";
		}
		content += "</td></tr>";
	  }
	if(stemUnitObj.stemUnitPerformanceIndicatorLt){
		 if(stemUnitObj.stemUnitPerformanceIndicatorLt.length > 0){
			content += "<tr><td height='40'></td></tr>";
			content += "<tr><td align='center' width='100%' style='border: 1px solid #007094;font-size:16px;color:blue;'><b>5C's Exposed to Areas</b></td></tr><tr><td align='left' width='100%' style='border: 1px solid #007094;'><table width='100%'>";
			lcMap = new Object();
			for(i=0; i<stemUnitObj.stemUnitPerformanceIndicatorLt.length;i++){
				if (stemUnitObj.stemUnitPerformanceIndicatorLt[i].legendSubCriteria.legendCriteria.legendCriteriaName in lcMap){
					var temp = lcMap[stemUnitObj.stemUnitPerformanceIndicatorLt[i].legendSubCriteria.legendCriteria.legendCriteriaName];
					temp += ','+stemUnitObj.stemUnitPerformanceIndicatorLt[i].legendSubCriteria.legendSubCriteriaName;
					lcMap[stemUnitObj.stemUnitPerformanceIndicatorLt[i].legendSubCriteria.legendCriteria.legendCriteriaName] = temp;
				}else if(stemUnitObj.stemUnitPerformanceIndicatorLt[i].status == 'active'){
					lcMap[stemUnitObj.stemUnitPerformanceIndicatorLt[i].legendSubCriteria.legendCriteria.legendCriteriaName] = stemUnitObj.stemUnitPerformanceIndicatorLt[i].legendSubCriteria.legendSubCriteriaName;
				}
			}
			Object.keys(lcMap).forEach(function(key) {
				  content += "<tr><td width='30%'><b>"+key+" &nbsp;&nbsp;:</b></td></tr><tr><td colspan='4' width='70%' style='padding-left:4em;'>"+lcMap[key]+"</td></tr>";
			});
			content += "<tr><td height='10'></td></tr></table></td></tr>";
		  }
	}
	
	if(stemUnitObj.unitStemStrategiesLt){
		 if(stemUnitObj.unitStemStrategiesLt.length > 0){
			content += "<tr><td height='40'></td></tr><tr><td align='center' width='100%' style='border: 1px solid #007094;font-size:16px;color:blue;'><b>Differentiation Strategies</b></td></tr><tr><td align='left' width='100%' style='border: 1px solid #007094;'><table width='100%'>";
			for(i=0; i<stemUnitObj.unitStemStrategiesLt.length;i++){
				if(stemUnitObj.unitStemStrategiesLt[i].stemStrategies)
					content += "<tr><td colspan='4' width='70%' style='padding-left:6em;'>"+(i+1)+". "+stemUnitObj.unitStemStrategiesLt[i].stemStrategies.strategiesDesc+"</td></tr>";
			}
			content += "</table>";
		  }
	}
	
	if(stemUnitObj.formativeAssessmentsLt){
		 if(stemUnitObj.formativeAssessmentsLt.length > 0){
			content += "<tr><td height='40'></td></tr><tr><td align='center' width='100%' style='border: 1px solid #007094;font-size:16px;color:blue;'><b>Formative Assessments</b></td></tr><tr><td align='left' width='100%'><table width='100%'>";
			content += "<tr><td width='25%' align='center' style='border: 1px solid #007094;'>Title</td><td width='75%' align='center' style='border: 1px solid #007094;'>Description</td></tr>";
			for(i=0; i<stemUnitObj.formativeAssessmentsLt.length;i++){
				if(stemUnitObj.formativeAssessmentsLt[i].status == 'active'){
					if(stemUnitObj.formativeAssessmentsLt[i].formativeAssessments)
						content += "<tr><td width='25%' style='border: 1px solid #007094;padding-left:1em;'>"+stemUnitObj.formativeAssessmentsLt[i].formativeAssessments.title+"</td><td width='75%' style='border: 1px solid #007094;padding-left:1em;'>"+stemUnitObj.formativeAssessmentsLt[i].formativeAssessments.description+"</td></tr>";
				}
			}
			content += "</table>";
		 }
	}
	content += "</td></tr></table>";
	content += "</div>";
	return content;
}

function deleteStemUnit(stemUnitId, isShared){
	if(stemUnitId){
		if(stemUnitId > 0){
			if(isShared == "Yes"){
			if(confirm("This is a Shared Unit.Are you sure to delete the unit?",function(status){
				if(status){
					$.ajax({
						url : "deleteStemUnit.htm",
						type : "POST",
				   		data: "stemUnitId="+stemUnitId,
						success : function(data){
							if(data.status == "Deleted Successfully"){
								systemMessage(data.status);
								$('#tr'+stemUnitId).remove();
								loadStemContent();
							}else{
								systemMessage(data.status,'error');
							}
						}
					});
				}else{
					  return false;
				}
			}));
		}else{
			if(confirm("Are you sure to delete the unit?",function(status){
				if(status){
					$.ajax({
						url : "deleteStemUnit.htm",
						type : "POST",
				   		data: "stemUnitId="+stemUnitId,
						success : function(data){
							if(data.status == "Deleted Successfully"){
								systemMessage(data.status);
								$('#tr'+stemUnitId).remove();
								loadStemContent();
							}else{
								systemMessage(data.status,'error');
							}
						}
					});
				}else{
					  return false;
				}
			}));
		}
	}
  }
}

function createActivity(){
	var count = parseInt($('#noOfActQues').val());
	var x = '<tr><td width="" align="center">'+
					'<input id="actId'+count+'" name="stemUnitAct['+count+'].stemActivityId" type="hidden" value="0">'+
					'<textarea id="desc'+count+'" name="stemUnitAct['+count+'].activityDesc" style="width:265px;height:84px;overflow-y: auto; overflow-x: hidden;"></textarea>'+
				'</td>'+
				'<td width="" align="center">'+					
					'<textarea id="link'+count+'" name="stemUnitAct['+count+'].activityLink" style="width:265px;height:84px;overflow-y: auto; overflow-x: hidden;"></textarea>'+					
				'</td>'+
				'<td width="" align="center">'+
					'<label class="file-upload">'+			
						'<span id="fName'+count+'" class="uploadButton">Upload FIle</span>'+
						'<input type="file" id="mFile'+count+'" name="files" style="display: none;" onchange="getFileData(this,fName'+count+');">	'+					
					'</label>  '+
					'<form:hidden path="stemUnitAct['+count+'].fileName" id="stemFileName'+count+'" value=""/>  '+
				'</td>'+
				'<td width="" align="center">'+
					'<div class="button_green" align="right" onclick="viewSharedActivities('+count+')">Shared</div>'+
					'<form:hidden path="stemUnitAct['+count+'].referActivityId" id="referId'+count+'" value=""/>'+
				'</td>'+
			'</tr>';
	$("#activitiesTable").append(x);
	$("#activityDiv").show();
	$("#noActDiv").hide();	
	document.getElementById("noOfActQues").value = count+1;
	document.getElementById("desc"+count).focus();
}

function removeUnitStandards(strandsId){
	if(confirm("Do you want Delete STEM Strand?",function(status){
		if(status){
			$.ajax({
				url : "removeUnitStandards.htm",
				type : "POST",
		   		data: "strandsId="+strandsId,
				success : function(data){
					if(data.status == "Removed STEM Strand successfully"){
						systemMessage(data.status);
						$('#rmUnitStandardId'+strandsId).remove();
					}else{
						systemMessage(data.status,'error');
					}
				}
			});
		}		
	}));
}

function synchronizeTab(stemUnitId, currentTab, currentElement, synControl, updatedVal, event){
	    var status = '';
	    var mode = $('#mode').val();
	    if(stemUnitId && mode =='edit'){
		    if(event == 'focusin'){
		    	status = 'active';
		    }else{
		    	status = 'inactive';
		    }
	
			var synchronizeTabCallBack =  function (response){
				getAllUsersOnContent(stemUnitId, currentTab);
	
				if(event == 'focusout'){
					if(currentTab == 'tab0'){
						getUnitContent();
					}else if(currentTab == 'tab1'){
						essUnitQues();
					}else if(currentTab == 'tab2'){
						getStemAreas();
					}else if(currentTab == 'tab3'){
						//getUnitStemArea();
					}else if(currentTab == 'tab4'){
						stemFiveC();
					}else if(currentTab == 'tab5'){
						getUnitStemStrategies();
					}else if(currentTab == 'tab6'){	
						stemAssessments();
					}
			   }
			}
			synHandlerService.synchronizeTab(stemUnitId, status, currentTab, currentElement, synControl, updatedVal, event, {
				callback : synchronizeTabCallBack
			});
	    }
}

function checkPPExists(synHistoryHandler,callback) {
	 if(synHistoryHandler.synHandler.userRegistration.regId > 0){
		 $.ajax({
			 type: "GET",
		     url:"checkProfilePicExists.htm",
		     data: "regId="+synHistoryHandler.synHandler.userRegistration.regId+"&usersFilePath=profile_pic.jpg",
		     success: function(usersFilePath){
		    	 if(callback){
		    		 if(usersFilePath)
						callback(usersFilePath, synHistoryHandler);
		    		 else
		    			callback('',synHistoryHandler); 
		    	 }
		     }
		 });
	 }
}
var userNamesArr = new Array();
function getAllUsersOnContent(stemUnitId, currentTab){
    var getAllUsersOnContentCallBack = function (list){
    	$("#"+currentTab+"-active-users-div").html("");
	    $(".is-active :input[type=text], textarea").each(function(e){
	    	  $('#'+this.id).css({'border': '1px solid rgb(191, 191, 191)'});
	    	 // $('#'+this.id).attr('style','border: 1px solid rgb(191, 191, 191)');
	    	  $('#'+this.id).prop('readonly', false);
	    });
	    jQuery('.border-text').each(function(index, currentElement) {
	    	currentElement.remove();
	    });
		for (var i = 0; i < list.length; i++) {
			if(list[i].currentElement != '' && list[i].currentTab == currentTab){
				var synHistoryHandler = list[i];
				checkPPExists(synHistoryHandler,function(usersFilePath, synHistoryHandler){
					var imageIcon = (usersFilePath != "" ? "<img id='imgDiv' class='imgCls' style='width: 40px;height: 40px;border-radius: 50%;vertical-align: middle;' onclick='openPreviewImage()' src='loadDirectUserFile.htm?usersFilePath="+usersFilePath+"'>" : "<span id='iconDiv' class='fa fa-user-circle-o' aria-hidden='true' style='font-size: 35px;text-shadow: 0 1px 4px rgb(140, 156, 158);color:#00cae2;vertical-align: middle;'></span>");
					userNamesArr.push("<span class='text subButtons subButtonsWhite medium' style='padding-left: 2px;padding-top:6px;padding-bottom:6px;'>"+imageIcon+"&nbsp; <span style='font-size:12px;color:black;'>"+synHistoryHandler.synHandler.userRegistration.firstName+' '+synHistoryHandler.synHandler.userRegistration.lastName+"</span></span>");
				    if (navigator.userAgent.search("Chrome") >= 0)
				    	userNamesArr = $.unique(userNamesArr);
					else
						userNamesArr = removeDupsInArray(userNamesArr);
					$("#"+currentTab+"-active-users-div").html("");
					$("#"+currentTab+"-active-users-div").append(userNamesArr);
				if(!$('#'+synHistoryHandler.synHandler.userRegistration.regId).length)
					$('#'+synHistoryHandler.currentElement).before("<legend id="+synHistoryHandler.synHandler.userRegistration.regId+" class='border-text blinking-text'>"+synHistoryHandler.synHandler.userRegistration.firstName+' '+synHistoryHandler.synHandler.userRegistration.lastName+"</legend>");
					$('#'+synHistoryHandler.currentElement).attr('style','border: 1.5px solid #2fe8ff;user-select: none;pointer-events:none;');
					$('#'+synHistoryHandler.currentElement).prop('readonly', true);
				});
			}else if(list[i].currentTab == currentTab){
				var synHistoryHandler = list[i];
				checkPPExists(synHistoryHandler,function(usersFilePath, synHistoryHandler){
					var imageIcon = (usersFilePath != "" ? "<img id='imgDiv' class='imgCls' style='width: 40px;height: 40px;border-radius: 50%;vertical-align: middle;' onclick='openPreviewImage()' src='loadDirectUserFile.htm?usersFilePath="+usersFilePath+"'>" : "<span id='iconDiv' class='fa fa-user-circle-o' aria-hidden='true' style='font-size: 35px;text-shadow: 0 1px 4px rgb(140, 156, 158);color:#00cae2;vertical-align: middle;'></span>");
					userNamesArr.push("<span class='text subButtons subButtonsWhite medium' style='padding-left: 2px;padding-top:6px;padding-bottom:6px;'>"+imageIcon+" &nbsp;<span style='font-size:12px;color:black;'>"+synHistoryHandler.synHandler.userRegistration.firstName+' '+synHistoryHandler.synHandler.userRegistration.lastName+"</span></span>");
				    if (navigator.userAgent.search("Chrome") >= 0)
				    	userNamesArr = $.unique(userNamesArr);
					else
						userNamesArr = removeDupsInArray(userNamesArr);
					$("#"+currentTab+"-active-users-div").html("");
					$("#"+currentTab+"-active-users-div").append(userNamesArr);
				});
			}
		}
		userNamesArr.length= 0;
	}
	synHandlerService.getAllUsersOnContent(stemUnitId,{
		callback : getAllUsersOnContentCallBack
	})
}
function validateUnitName()
{
var stemUnitName = $('#stemUnitName').val();
if(stemUnitName.indexOf('.') !== -1)
{
	systemMessage("Unit Name doesn't allow special characters");
	$('#stemUnitName').val("");
	return false;
}
}
