/**
 * 
 */

// function for add school days functionality
function addDay(thisVal, sno) {
	var dayId = thisVal.value;
	if (document.getElementById('add:' + sno).checked) {
		if(confirm("Are you sure to add Day?",function(status){
			if(status){ 
				$.ajax({
					url : "AdminAddDays.htm",
					data : "dayId=" + dayId,
					success : function(data) {
						systemMessage(data);
						document.getElementById('add:' + sno).disabled = true;
						document.getElementById('remove:' + sno).disabled = false;
	
					}
				});
	
			} else {
				document.getElementById('add:' + sno).checked = false;
			}
		}));
	}
}
//function for remove school days functionality
function removeDay(thisVal, sno) {
	var DayId = thisVal.value;
	// var gradeId=document.getElementById('grade:'+sno).value;
	if (document.getElementById('remove:' + sno).checked) {
	if(confirm("Are you sure to remove Day?", function(status){
		if(status){ 
			$.ajax({
				url : "AdminRemoveDays.htm",
				data : "DayId=" + DayId,
				success : function(data) {
					systemMessage(data);
					document.getElementById('add:' + sno).checked = false;
					document.getElementById('add:' + sno).disabled = false;
					document.getElementById('remove:' + sno).disabled = true;
					document.getElementById('remove:' + sno).checked = false;
				}
			});

		} else {
			document.getElementById('remove:' + sno).checked = false;
		}
	}));
	}
}
// function for add class functionality
function addClasses() {
	var gradeId = $('#gradeId').val();

	if (gradeId == '' || gradeId == null) {
		alert("Please select a grade");
		$('#gradeId').next().show();
		return false;
	}
	var selectedclassId = new Array();
	var unSelectedclassId = new Array();
	$('input[name="classId"]:checked').each(function() {
		selectedclassId.push(this.value);
	});
	$('input[name="classId"]:not(:checked)').each(function() {
		unSelectedclassId.push(this.value);
	});
	if (selectedclassId.length == 0 && unSelectedclassId.length == 0) {
		alert("please select a class");
		return false;
	}
	$.ajax({
		url : 'addClasses.htm',
		data : "classIds=" + selectedclassId + "&gradeId=" + gradeId
				+ "&removeIds=" + unSelectedclassId,
		success : function(result) {
			systemMessage(result);
		}
	});

}
//functions add grades functionality

function addGrade(thisVal,sno){
var MasgradeId = thisVal.value;
if(document.getElementById('add:'+sno).checked) {
if(confirm("Are you sure to add grade?",function(status){
	if(status){	 
		$.ajax({  
		url : "AdminAddGrades.htm", 
        data: "MasgradeId=" + MasgradeId,
        success : function(data) { 
        	systemMessage(data);
        	document.getElementById('add:'+sno).disabled=true;
            document.getElementById('remove:'+sno).disabled=false;
          
        }  
    }); 
	}else{
		document.getElementById('add:'+sno).checked=false;
	}
	}));
	}
}
function removeGrade(thisVal,sno){
	var MasgradeId = thisVal.value;
    if(document.getElementById('remove:'+sno).checked) {
	if(confirm("Are you sure to remove grade?",function(status){
		if(status){		 	 
		$.ajax({  
			url : "AdminRemoveGrades.htm", 
            data: "MasgradeId=" + MasgradeId,
            success : function(data) { 
            	systemMessage(data);
            	document.getElementById('add:'+sno).checked=false;
            	document.getElementById('add:'+sno).disabled=false;
                document.getElementById('remove:'+sno).disabled=true;  
                document.getElementById('remove:'+sno).checked=false;
            }  
        }); 
	}else{
		document.getElementById('remove:'+sno).checked=false;
		 }
	})); 
	}
}

//Benchmark Cutoff functions

function getBenchmarkCategories() {
	var gradeId = $('#gradeId').val();
	$("#td1").empty();
	if(gradeId > 0){
		$.ajax({
			url : "getBenchmarkCategories.htm",
			data : "gradeId=" + gradeId,
			success : function(response) {
				$("#td1").html(response);
			}
		});
	}
}
function saveBenchmarkCuttoffPoints() {

	var gradeId = $("#gradeId").val();
	var category = $("[name=categoryId]");
	var fluencyCutOff = $("[name=fluencyCutOff]");
	var retllCutOff = $("[name=retellCutOff]");

	var categorys = [];
	var fluencyCutOffs = [];
	var retellCutOffs = [];
	for (var i = 0; i < category.length; i++) {
		if(fluencyCutOff[i].value > 0){
			if(retllCutOff[i].value > 0){
					categorys.push(category[i].value);
					fluencyCutOffs.push(fluencyCutOff[i].value);
					retellCutOffs.push(retllCutOff[i].value);
			}
			else{
				alert("Please enter retell cut off points cannot be zero");
				return false;
			}
			if(fluencyCutOff[i].value==''|| fluencyCutOff[i].value==null){
				alert("Please enter fluency cut off with numeric characters");
				return false;
			}
			else if(fluencyCutOff[i].value > 200 )
				{
				alert("	Fluency Cut Off Can not exced 200");
				return false;
				}
			else if(retllCutOff[i].value==''|| retllCutOff[i].value==null){
				alert("Please enter retell cut off with numeric characters");
				return false;
			}
			else if(retllCutOff[i].value > 4 )
			 {
				alert("Retell Cut Off Can not exced 4");
				return false;
			}
			
		}	
		else{
			alert("Please enter fluency cut off points cannot be zero");
			return false;
		}
	}
	$.ajax({
		url 	: "setBenchmarkCutOffMarks.htm",
		data 	: "gradeId=" + gradeId + "&categorys=" + categorys
					+ "&fluencyCutOffs=" + fluencyCutOffs + "&retellCutOffs="
					+ retellCutOffs,
		success : function(response) {
			$("#td1").empty();
			$('#gradeId').val('select');
			systemMessage(response);
		}
	});

}
function CreateClasses(){
	var classname = $('#classname').val();
	classname = classname.trim();
	if(classname== '' || classname==null){
		alert("Please enter the class name");
        $('#classname').next().show();
        return false;
    }
	var condition=/^([a-zA-Z])+([a-zA-Z0-9]+|\s+)+$/.test(classname);
	if(condition==false){
		 alert("Input should be alphanumeric");
		 return false;
	}			 	 
	$.ajax({  
		url : "CreateClass.htm", 
        data: "classname=" + classname,
        success : function(data) { 
        	systemMessage(data);
            $('#classname').val('');
        }  
   	}); 
}

function setClassStrengthDetails()
{
	 var classStrength = $('#classStrength').val();
	
		 
	 if(classStrength== '' || classStrength==null){
		 alert("Please enter the class Strength");
         $('#classStrength').next().show();
         return false;
      } 
	 if(!$.isNumeric(classStrength)|| classStrength.match(/^\d+\.\d+$/))
		 {
		  alert("please enter the numeric character");
		  $('#classStrength').val('');
		  return false;
	 }else if(classStrength<=0){
		 alert("please enter valid class strength");
		  $('#classStrength').val('');
		  return false;
	 }
	 
	 			 
	 var Leveling=$('input[name="Leveling"]:checked').val();
		
	 if(Leveling== '' || Leveling==null){
		 alert("Please select levelling");
         $('#Leveling').next().show();
         return false;
      }
	 var genderEquity=$('input[name="genderEquity"]:checked').val();
		
	 if(genderEquity== '' || genderEquity==null){
		 alert("Please select genderEquity");
         $('#genderEquity').next().show();
         return false;
      }
			 	 
	$.ajax({  
		url : "SetClassStrength.htm", 
        data: "classStrength=" + classStrength+"&Leveling="+Leveling+"&genderEquity="+genderEquity,
        success : function(data) {
        	systemMessage(data);
        }  
    }); 
}

function checkFileExt(){
	var file = document.getElementById("file").value;
	var fileExt = file.split('.').pop();
	if(fileExt=="xml"){
		return true;
	}else{
		alert("Please Upload file with .xml format");
		return false;
	}
}

function validateImageFileType(){
	var fup = document.getElementById('file');
	var fileName = fup.value;
	var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
	if(ext == "gif" || ext == "GIF" || ext == "JPEG" || ext == "jpeg" || ext == "jpg" || ext == "JPG" || ext == "png" || ext == "PNG"){
		return true;
	}else{
		systemMessage("Upload gif or jpg images only");
		fup.value="";
		fup.focus();
		return false;
	}
}

function uploadSchoolLogo() {
	var status = validateImageFileType();
	if(status){
		var formObj = document.getElementById("uploadSchoolLogoForm");
		var formData = new FormData(formObj);
		$.ajax({
			url: 'uploadSchoolLogo.htm',
			type: 'POST',
			data: formData,
			mimeType:"multipart/form-data",
			contentType: false,
			cache: false,
			processData:false,
			success: function(data){
				systemMessage(data);
			}
		});	
	}
}

function uploadCAASPPScores() {
	var status = validateFileType();
	if(status){
		var formObj = document.getElementById("uploadCAASPPScoresForm");
		var formData = new FormData(formObj);
		$.ajax({
			url: 'addCAASPPScores.htm',
			type: 'POST',
			data: formData,
			mimeType:"multipart/form-data",
			contentType: false,
			cache: false,
			processData:false,
			success: function(data){
				systemMessage(data);
			}
		});	
	}
}

function validateFileType(){
	var fup = document.getElementById('file');
	var fileName = fup.value;
	var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
	if(ext == "csv"){
		return true;
	}else{
		systemMessage("Upload CSV only");
		fup.value="";
		fup.focus();
		return false;
	}
}

function checkXLSFileExt(){
	var file = document.getElementById("file").value;
	var fileExt = file.split('.').pop();
	var lCriteriaId = $('#lCriteriaId').val();	
	var districtId = $('#districtId').val();
	if(lCriteriaId == 'select'){
		alert("Please select a legend criteria");
		return false;
	}
	if($("#masterId option:selected").length == 0){
		alert("Please select master grades");
		return false;
	}
	if(districtId == 'select'){
		alert("Please select a district");
		return false;
	}
	
	if(fileExt=="xls" || fileExt=="xlsx"){
		var formObj = document.getElementById("legendUploadForm");
		var formData = new FormData(formObj);
		$("#loading-div-background").show();
	    $.ajax({  
	     	url : "legendUploadFile.htm",
	     	type : "POST",
	       	data: formData,
	       	mimeType:"multipart/form-data",
	    	contentType: false,
	    	cache: false,
	    	processData:false
	    }).done(function(data) {  
	    	$("#loading-div-background").hide();
	    	var res = JSON.parse(data);
	    	systemMessage(res.status);
	 	});
	}else{
		alert("Please upload file with excel format");
		return false;
	}
}

function uploadStrands(){
	var file = document.getElementById("file").value;
	var fileExt = file.split('.').pop();
	var areaId = $('#areaId').val();	
	if(areaId == 'select'){
		alert("Please select a STEM area");
		return false;
	}
	var masterId = $('#masterId').val();	
	if(masterId == 'select'){
		alert("Please select a grade");
		return false;
	}
	var stateId = $('#stateId').val();	
	if(stateId == 'select'){
		alert("Please select a state");
		return false;
	}
	
	if(fileExt=="xlsx" || fileExt=="xls"){
		var formObj = document.getElementById("strandsUploadForm");
		var formData = new FormData(formObj);
		$("#loading-div-background").show();
	    $.ajax({  
	     	url : "uploadStrandsFile.htm",
	     	type : "POST",
	       	data: formData,
	       	mimeType:"multipart/form-data",
	    	contentType: false,
	    	cache: false,
	    	processData:false
	    }).done(function(data) {  
	    	$("#loading-div-background").hide();
	    	var res = JSON.parse(data);
	    	systemMessage(res.status);
	 	});
	}else{
		alert("Please upload file with .xls or .xlsx format");
		return false;
	}
}

function checkIOLExcelFileExt(){
	var file = document.getElementById("file").value;
	var fileExt = file.split('.').pop();
	var lCriteriaId = $('#lCriteriaId').val();	
	if(lCriteriaId == 'select'){
		alert("Please select a legend criteria");
		return false;
	}	
	if(fileExt=="xls" || fileExt=="xlsx"){
		var formObj = document.getElementById("uploadIOLRubric");
		var formData = new FormData(formObj);
		$("#loading-div-background").show();
	    $.ajax({  
	     	url : "uploadIOLRubricFile.htm",
	     	type : "POST",
	       	data: formData,
	       	mimeType:"multipart/form-data",
	    	contentType: false,
	    	cache: false,
	    	processData:false
	    }).done(function(data) {  
	    	$("#loading-div-background").hide();
	    	var res = JSON.parse(data);
	    	systemMessage(res.status);
	 	});
	}else{
		alert("Please upload file with .xls format");
		return false;
	}
}

function checkStarXLSFileExt(){
	var file = document.getElementById("file").value;
	var fileExt = file.split('.').pop();
	var trimesterId = $('#trimesterId').val();	
	if(trimesterId == 'select'){
		alert("Please select a trimester");
		return false;
	}	
	var caasppId = $('#caasppId').val();	
	if(caasppId == 'select'){
		alert("Please select a CAASPP type");
		return false;
	}	
	if(fileExt=="xls" || fileExt=="xlsx"){
		var formObj = document.getElementById("StarUploadForm");
		var formData = new FormData(formObj);
		$("#loading-div-background").show();
	    $.ajax({  
	     	url : "starUploadFile.htm",
	     	type : "POST",
	       	data: formData,
	       	mimeType:"multipart/form-data",
	    	contentType: false,
	    	cache: false,
	    	processData:false
	    }).done(function(data) {  
	    	$("#loading-div-background").hide();
	    	var res = JSON.parse(data);
	    	systemMessage(res.status);
	 	});
	}else{
		alert("Please upload file with .xls or .xlsx format");
		return false;
	}
}

function checkCaasppXLSFileExt(){
	var file = document.getElementById("file").value;
	var fileExt = file.split('.').pop();	
	
	if(fileExt=="xls" || fileExt=="xlsx"){
		var formObj = document.getElementById("caasppUploadForm");
		var formData = new FormData(formObj);
		$("#loading-div-background").show();
	    $.ajax({  
	     	url : "caasppFileUpload.htm",
	     	type : "POST",
	       	data: formData,
	       	mimeType:"multipart/form-data",
	    	contentType: false,
	    	cache: false,
	    	processData:false
	    }).done(function(data) {  
	    	$("#loading-div-background").hide();
	    	var res = JSON.parse(data);
	    	systemMessage(res.status);
	 	});
	}else{
		alert("Please upload file with .xls format");
		return false;
	}
}