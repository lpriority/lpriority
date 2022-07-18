function clears(thisvar) {
	if (thisvar.id == 'gradeId') {
		$('#classId').empty();
		$("#classId").append($("<option></option>").val('select').html('Select Subject'));
		$('#studentId').empty();
		$("#studentId").append($("<option></option>").val('select').html('Select Student'));
		$('#addWordList').hide();
		$('#displaySetsDiv').hide();
	}
	if (thisvar.id == 'classId' || thisvar.id == 'gradeId') {
		$('#csId').empty();
		$("#csId").append($("<option></option>").val('select').html('Select Section'));
		$('#addWordList').hide();
		$('#displaySetsDiv').hide();
	}
	if (thisvar.id == 'csId' || thisvar.id == 'classId' || thisvar.id == 'gradeId') {
		$('#assignedDate').empty();
		$("#assignedDate").append($("<option></option>").val('select').html('Select Date'));
		$('#addWordList').hide();
		$('#displaySetsDiv').hide();
	}
	if (thisvar.id == 'assignedDate' || thisvar.id == 'csId' || thisvar.id == 'classId' || thisvar.id == 'gradeId') {
		$('#titleId').empty();
		$("#titleId").append($("<option></option>").val('select').html('Select Title'));
		$('#addWordList').hide();
		$('#displaySetsDiv').hide();
	}
	if (thisvar.id == 'assignedDate' || thisvar.id == 'csId' || thisvar.id == 'classId' || thisvar.id == 'gradeId' || thisvar.id == 'titleId') {
		$(document.getElementById('resultsDiv')).empty();
		$("input:radio").attr("checked", false);
	}
}

function generateSets(){
	var noOfUCSets = dwr.util.getValue('noOfUCSets');
	var noOfLCSets = dwr.util.getValue('noOfLCSets');
	if(noOfUCSets > 0 || noOfLCSets >0){
	    var upperSet = " ";
	    var lowerSet = " ";
	    var count = 0;
	    var str = "<table>";
	    var upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    var lowerCase = "abcdefghijklmnopqrstuvwxyz";
	   
		for( var i=1; i <= noOfUCSets; i++ ){
			  	var upperSet = " ";
			  	count++;
			    str += "<tr><th>Set " + count + " :</th><td><input type='text' style='border: #eeeeee; color: violet;text-transform: uppercase;' maxlength=52 and size=45 value='" + upperCase.shuffle().replace(/\B/g, " ") + "' id='upperDiv" + i + "' name='upperDiv" + i + "' readonly onblur=''></td></tr>";
		 }
	    for( var i=1; i <= noOfLCSets; i++ ){
	    	 count++;
      	     str += "<tr><th>Set " + count + " :</th><td><input type='text' style='border: #eeeeee; color: violet;text-transform: lowercase;' maxlength=52 and size=38 value='" + lowerCase.shuffle().replace(/\B/g, " ") + "' id='lowerDiv" + i + "' name='lowerDiv" + i + "' readonly onblur=''></td></tr>";
	    }
	    str += "<tr><td height=40 colSpan=8></td></tr> <tr align=\"center\"><td align=\"center\"><input type='button' class='submitButton' onClick='createTestSets()' value='Submit'></td></tr></table>";
        document.getElementById('appendDiv').innerHTML = str;
  	}else{
		alert("Please enter any one of the case sets to generate");
	}
}
function createTestSets(callback) {
    var csId = dwr.util.getValue('csId');
   	var divId = dwr.util.getValue('divId');
	var setName = dwr.util.getValue('setName');
	var mode = dwr.util.getValue('mode');
	var page = parent.document.getElementById("page").value;
	var setId = 0;
	var sets = '';
	var wordsString='';
   if(divId == 'CreateLetter'){
   }else if (divId == 'CreateWord'){
		if(wordsArray.length > 0){
			$.each( wordsArray, function( index, value ){
				sets += value+' ';
			});
		}
   }
	if(mode == 'create')
		clearWords();
	else if(mode == 'edit')
		setId = dwr.util.getValue('setId');
	
   if(!setName){
	   alert("Please enter Set Name");
	   return false;
   }
   if((setName.length) > 20){
	   alert("setName should not exced the 20 charectors");
	   return false;
   }
	if(csId != 'select'){
		$.ajax({
			url : "createTestSets.htm",
			data: "csId="+csId+"&page="+page+"&setId="+setId+"&sets="+sets+"&setName="+setName+"&mode="+mode,
			success : function(data){
				if(mode == 'create'){
					wordsArray.length = 0; 
					cnt=0
					document.getElementById("setName").value = "";
				    $("#appendDiv").html("");
					$("#buttonsDiv").hide();
					systemMessage(data);
				}else if(mode == 'edit'){
					var status = '';
					if(data == 'Test already Assigned !!'){
						data = "Set already assigned to a test..Unable update!!";
						status = 'failed';
					}else{
						status = 'success';
						dwr.util.setValue("updatedWordId",'');
						dwr.util.setValue("words",'');
						$("#words").focus();
					}
					systemMessage(data);
					if(callback)
						callback(status);
				}
			}
		});
	}else{
		alert("Please Select a Section");
		return false;
	}
}

function addSpaces(divId){
	var regex = /^[a-zA-Z]*$/;
	var val = dwr.util.getValue(divId);
	val = val.replace(/ /g,'');
	if (regex.test(val)) {
		var updateVal = '';
		for(var i=0; i<val.length; i++)
			updateVal += val.charAt(i) + ' ';     
		dwr.util.setValue(divId,updateVal)
	}else{
		 alert("Please enter only Alphabtes");
	}
}
var cnt=0;
var wordsArray =  new Array();
function collectWords(obj){
	var totalLen;
	var value;
	var index;
	var updatedId = dwr.util.getValue('updatedWordId');
	var mode = dwr.util.getValue('mode');
	var letterCount = parent.document.getElementById("letterCount").value;
	if(letterCount){
       totalLen = parseInt(letterCount);
	}else{
		totalLen = 0;
	}
	if(obj){
		value = obj.value.trim();
		if(! /\s/.test(value)){
			var letterLen = parseInt(value.length+1);
			totalLen = letterLen + totalLen; 
			parent.document.getElementById("letterCount").value = totalLen;
			index = wordsArray.indexOf(value);
		}else{
			alert("Please enter a single Word");
			return false;
		}
	}
	//if(totalLen < 200 ){
		if(index == -1){
			if(value && !updatedId){
				cnt = cnt+1;	
				var str = "<div style='height:32px;padding:0.3em;' id="+cnt+"><input type='button' class='subButtons subButtonsWhite medium' id=word_"+cnt+" onClick='editWord(this)' value="+value+" style='font-size:14px;border-radius:0.5em;width:auto;font-family:Roboto,-apple-system,BlinkMacSystemFont,Helvetica Neue,Segoe UI,Oxygen,Ubuntu,Cantarell,Open Sans,sans-serif;'/>";
				str+= "&nbsp&nbsp<i class='fa fa-times' id=remove_"+cnt+" aria-hidden='true' style='cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;' onclick='removeWord(this)'></i></br></br></div>"
				$("#appendDiv").append(str);
				$("#appendDiv").show();
				wordsArray.push(value);
				dwr.util.setValue(obj.id,"");
			}else if(value){
				var previousValue = dwr.util.getValue(updatedId);
				index = wordsArray.indexOf(previousValue);
				wordsArray[index] = value;
				 $("#"+updatedId).addClass('wordsWrap').removeClass('wordsWrapSelect');
				dwr.util.setValue(updatedId,value);
				dwr.util.setValue("words",'');
				dwr.util.setValue("updatedWordId",'');
			}else{
				$("#"+updatedId).addClass('wordsWrap').removeClass('wordsWrapSelect');
			}
			if(mode == 'create')
			$("#buttonsDiv").show();
		}else{
			alert("Word already entered !!");
		}
	/*}else{
		alert("Words total limit exceeded !!");
		dwr.util.setValue("words",'');
		return false;
	}*/
}

function clearWords(){
	var words = dwr.util.getValue('words');
	if(words){
		dwr.util.setValue("words",'');
	}
}

function editWord(obj){
	var updatedId = dwr.util.getValue('updatedWordId');
	if(updatedId){
	  $("#"+updatedId).addClass('wordsWrap').removeClass('wordsWrapSelect');
	}
	if(obj){
		$("#"+obj.id).addClass('wordsWrapSelect').removeClass('wordsWrap');
		dwr.util.setValue("words",obj.value);
		dwr.util.setValue("updatedWordId",obj.id);
		 $("#words").focus();
	}
}
function removeWord(obj){
	if(obj){
		var mode = dwr.util.getValue('mode');
		var arr = obj.id.split('_');
		var deletedVal = dwr.util.getValue('word_'+arr[1]);
		var index = wordsArray.indexOf(deletedVal);
		if(arr[1]){
		    if(mode == 'edit'){
				wordsArray.splice( $.inArray(deletedVal, wordsArray), 1 );
				createTestSets(function(status){
					if(status == 'success'){
						$( "#"+arr[1]).remove();
					}
				});
		    }else if(mode == 'create'){
				wordsArray.splice( $.inArray(deletedVal, wordsArray), 1 );
		    	$( "#"+arr[1]).remove();
		    	dwr.util.setValue("updatedWordId",'');
				dwr.util.setValue("words",'');
				$("#words").focus();
		    }	

		}
		if(jQuery.isEmptyObject(wordsArray)){
			$("#buttonsDiv").hide();
			$("#appendDiv").html("");
		}
	}
}
var checked = false;
function getTestSets(isAutomatic){
	checked = isAutomatic;
	var divId = dwr.util.getValue('divId');
   	var gradeId = dwr.util.getValue('gradeId');
   	var csId = dwr.util.getValue('csId');
   	var setType = dwr.util.getValue('setType');
   	if(divId == 'assignLetterSet'){
   		$( "#AssignLetterSetDetailsPage").html('');
   	}else if(divId == 'assignWordSet'){
   		$( "#AssignWordSetDetailsPage").html('');
   	}
   	if(gradeId != "select"){
		earlyLiteracyService.getTestSets(gradeId,setType,csId,isAutomatic,{
			callback : getTestSetsCallBack
		});
   	}else{
   		if(divId == 'assignLetterSet'){
   	   		$( "#AssignLetterSetDetailsPage").html('');
   	   	}else if(divId == 'assignWordSet'){
   	   		$( "#AssignWordSetDetailsPage").html('');
   	   	}
   	}
}

function getTestSetsCallBack(list){
	var divId = dwr.util.getValue('divId');
	if(list.length > 0){
	 	if(divId == 'assignLetterSet'){
			var count = 0;
	 		var str = "<table width='55%' class='des' border=0 align='center' id='togglePage' style=font-size:14px;font-family:'Segoe UI', Frutiger, 'Frutiger Linotype', 'Dejavu Sans', 'Helvetica Neue', Arial, sans-serif;><tr><td><div class='Divheads'><table><tr><td class='headsColor'>Assign Letter Set</td></tr></table></div>";
	 		    str += "<br><div class='DivContents'><table width='100%' align='center' cellpadding='5' cellspacing='5'> ";
	 		
	 		list.forEach(function(entry) {
		 		if(entry.partType == 'K1Words')
		 			flag = false;
	 			count++;
	 			str += " <tr><td width='120' align='center' colspan='3'><input type='checkbox' class='checkbox-design' name='sets' value="+entry.setId+" id='div"+count+"'><label for='div" +count+ "' class='checkbox-label-design'></label><span class='txtStyle'><b> Set " +entry.setName+ " : </b></span></td>";
	 			str += "<td style='padding: 4px;' class='txtStyle'><font color='#004b6d'  size='3'><b>"+entry.set+"</b></font></td> </tr>" ;
	 		 });
	 		str += "</tr></table></div><br>";
	 		str += "<div class='DivContents'><table  width='100%' align='center' class='txtStyle'><tr><th width='80' align='center'>DueDate <img src='images/Common/required.gif'></th><td width='160' align='left'><input name='dueDate' type='text' id='dueDate' size='10' maxlength='15' readonly='readonly' value='' onFocus/></td>";
	 		str += "<th width='80' align='left'>Title <img src='images/Common/required.gif'></th><td width='100' align='left'><input name='titleId' type='text' id='titleId' size='15' maxlength='25'  value='' onblur='checkTitleById();'/></td><th width='200' align='center'>Record Time <img src='images/Common/required.gif'></th><td width='80' align='left' valign='middle'><input type='text' id='recordTime' name='recordTime' value='' size='3' maxlength='2' onblur='numValCheck(this.value)'/></td></tr>" +
	 				"<tr><td height=15 colSpan=30></td></tr></table></div><div class='DivContents'><table align='center'><tr><td width='180'  align='center'><div class='button_green' id='submit' onClick='assignTest()'>Assign Test</div></td></tr></table>";
	 		str += "</div></td></tr></table>";
   	   	$("#AssignLetterSetDetailsPage").html('');
		$("#AssignLetterSetDetailsPage").append(str);
	 	}else if(divId == 'assignWordSet'){
	 		var str = "";
	 		var count = 0;
	 		if(!checked){
			 		str = "<table width='90%' class='des' border=0 align='center' id='togglePage' style=font-size:14px;'Segoe UI', Frutiger, 'Frutiger Linotype', 'Dejavu Sans', 'Helvetica Neue', Arial, sans-serif;><tr><td><div class='Divheads'><table><tr><td class='headsColor'>Assign Word Set</td></tr></table></div>";
			 		str += "<div class='DivContents'><table align='center' cellpadding='5' cellspacing='5'><tr><td align='left' class='tabtxt' style='font-size:15px;color:#832294;'><input type='checkbox' class='checkbox-design' id='selAll' onClick='selectAllLists(this,"+list.length+")' /><label for='selAll' class='checkbox-label-design'>Select All</label></td></tr> ";
			 		list.forEach(function(entry) {
			 			count++;
			 			str += "<tr><th width='200' align='center' colspan='3'> <input type='checkbox' class='checkbox-design' value='"+ entry.setId +"' id='div" +count+ "' name='sets'><label for='div"+count+"' class='checkbox-label-design'>&nbsp;&nbsp;<span style='color:#ff3d00' >" +entry.setName+ " : </span> </label></th>";
			 			wordsArray.length = 0; 
			 			var wordsLt = entry.set;
			  			wordsArray = wordsLt.split(" ");
			  			var cnt = 0;
			  			str += "<td><table width='100%'><tr>";
			  			wordsArray.forEach(function(word) {
			  				if(cnt < 10){
			  					cnt = cnt+1;
				  				str += "<td width='80'style='padding-left:4px;padding-right:4px;'><font color='#004b6d'  size='3'><b>"+word+"</b></font></td>" ;
			  				}else if(cnt == 10){
			  					cnt = 0;
			  					str += "</tr><td width='80'style='padding-left:4px;padding-right:4px;'><font color='#004b6d'  size='3'><b>"+word+"</b></font></td>" ;
			  				}
			  			});
			  			str += "</tr></table></td>";
			 		});
			 		str += "</tr></table><br>";
			 		str += "<div class='DivContents'><br><table  width='870' align='center'><tr><th width='80' align='center'>DueDate <img src='images/Common/required.gif'></th><td width='160' align='left'><input name='dueDate' type='text' id='dueDate' size='10' maxlength='15' readonly='readonly'  value='' onFocus/></td>";
			 		str += "<th width='80' align='left'>Title <img src='images/Common/required.gif'></th><td width='100' align='left'><input name='titleId' type='text' id='titleId' size='15' maxlength='25'  value='' onblur='checkTitleById();'/></td><th width='200' align='center'><!--Record Time <img src='images/Common/required.gif'>--></th><td width='80' align='left' valign='middle'><input type='hidden' id='recordTime' name='recordTime' value='4' size='3' maxlength='2' onblur='numValCheck(this.value)'/></td></tr>" +
			 				"<tr><td height=35 colSpan=30></td></tr></table></div><div class='Divcontents'><table align='center'><tr><td width='180'  align='center'><div class='button_green' id='submit' onClick='assignTest()'>Assign Test<div></td></tr></table>";
			 		str += "</div></td></tr></table>";
	 		}else{
	 			str = "<table width='98%' class='des' border=0 align='center' id='togglePage' style='font-size:14px;'><tr><td><div class='Divheads'><table width='100%' align='center'><tr><th width='120' align='center' colspan=''>Set Name </th><!--<td width='100'>Record Time</td>--><td align='center'> Set </td></tr></table></div>";
		 		str += "<div class='DivContents'><table width='100%' align='left' cellpadding='5' cellspacing='5'> ";
		 		list.forEach(function(entry) {
		 			count++;
		 			str += "<tr><th width='100' align='left' colspan='1'> <input type='checkbox' class='checkbox-design' value='"+ entry.setId +"' id='div"+entry.setId+"' name='sets' onClick='checkCheckBox("+entry.setId+")'><label for='div" +entry.setId+ "' class='checkbox-label-design'>&nbsp;&nbsp;<span style="+ (entry.listType == 'System' ? 'color:#ff3d00' : 'color:#007605')+">"+entry.setName+ " : </font></label></th><td align='left' width='80'><input type='hidden' disabled id='recordTime"+entry.setId+"' name='recordTime"+entry.setId+"' value='4' size='3' maxlength='2' style='background:#ebebeb;'  onblur='numValCheck(this.value)'/></td>";
		 			wordsArray.length = 0; 
		 			var wordsLt = entry.set;
		  			wordsArray = wordsLt.split(" ");
		  			var cnt = 0;
		  			str += "<td><table width='100%'><tr>";
		  			wordsArray.forEach(function(word) {
		  				if(cnt < 10){
		  					cnt = cnt+1;
			  				str += "<td width='80'style='padding-left:4px;padding-right:4px;'><font color='#004b6d'  size='3'><b>"+word+"</b></font></td>" ;
		  				}else if(cnt == 10){
		  					cnt = 0;
		  					str += "</tr><td width='80'style='padding-left:4px;padding-right:4px;'><font color='#004b6d'  size='3'><b>"+word+"</b></font></td>" ;
		  				}
		  			});
		  			str += "</tr></table></td>";
		 		});
		 		str += "</tr></table><br>";
		 		str += "<div class='Divcontents'><table align='center' width='60%'><tr><th width='80' align='center' height='80'>Title <img src='images/Common/required.gif'></th><td width='160' align='left'><input name='titleId' type='text' id='titleId' size='15' maxlength='25'  value=''/></td><th width='80' align='center'>Test Start Date:  <img src='images/Common/required.gif'></th><td width='160' align='left'><input name='startDate' type='text' id='startDate' size='10' maxlength='15'  value='' /><tr><td width='100%' colspan='4' align='center'><div class='button_green' id='submit' onClick='autoAssignTest()' style='width:120px;font-size:12px;'>Assign Automatic Test</div></td></tr></table>";
		 		str += "</div></td></tr></table>";
	 		}
   			$('#assignTypeDiv').show();
			$( "#AssignWordSetDetailsPage").html("");
			$( "#AssignWordSetDetailsPage").append(str);
	 	}
	 	datepick();
	 	setFooterHeight();
	}
}

function selectAllLists(obj,tot){
	if($("#"+obj.id).is(':checked')){
		for(i=1;i < tot+1; i++){
		 $('#div'+i).prop('checked', true);
		}
	}else{
		for(i=1;i < tot+1; i++){
		 $('#div'+i).prop('checked', false);
		}
	}
}
function checkCheckBox(id){
	if($("#div"+id).is(':checked')){
		$("#recordTime"+id).prop("disabled", false); 
		$("#recordTime"+id).css("background","white");
	}else{
		$("#recordTime"+id).prop("disabled", true); 
		$("#recordTime"+id).css("background","#ebebeb");
	}
}

function autoAssignTest(){
	var setsArray = $.map($('input[name="sets"]:checked'), function(c){return c.value; }) ;	
	var recordTimeArray =  new Array();
	recordTimeArray.length = 0; 
	if(setsArray!=null && setsArray.length>0){
		var page = dwr.util.getValue('page');
		var testType = dwr.util.getValue('testType');
	    var csId = dwr.util.getValue('csId');
		var startDate = dwr.util.getValue('startDate');
	   	var students = [];
	   	$('#studentId :selected').each(function(i, selectedElement) {
	   		students[i] = $(selectedElement).val();
	   	 });
	   	if(students.length == 0){
		   alert("Please Select Students");
		   return false;
	   	}
	   	for (var i = 0; i < setsArray.length; i++) {
	   		var recordTime = dwr.util.getValue('recordTime'+setsArray[i]);
	   		var condition=/^[0-9]+$/.test(recordTime);
		    if(condition==false){
		        systemMessage("Please enter valid record time");
		        $('#recordTime'+setsArray[i]).focus();
		        return false;
		    }else if(recordTime == 0){
		    	systemMessage("Please enter valid record time");
		    	$('#recordTime'+setsArray[i]).focus();
			    return false;
		    }else{
		    	recordTimeArray.push(recordTime);
		    }
	   	}
		var titleId = dwr.util.getValue('titleId');
	   	if(!titleId){
	   		systemMessage('Please enter the Title !!');
	   		$("#titleId").focus();
	   		return false;
	   	}
	   	var condition=/^([a-zA-Z0-9]+(\s)?)+$/.test(titleId);
		 if(condition==false){
			 systemMessage("Special characters are not allowed in title");
			 $("#titleId").focus();
			 return false;
		 }
	   	var status = isValidDate(startDate);
	   	if(status){
	   		systemMessage(status);
	   		$("#startDate").focus();
	   		return false;
	   	}
	   	if(startDate){
		 	$.ajax({
				url : "autoAssignTest.htm",
				data: "setsArray="+setsArray+"&page="+page+"&testType="+testType+"&startDate="+startDate+"&titleId="+titleId+"&csId="+csId+"&students="+students+"&recordTimeArray="+recordTimeArray,
				success : function(data){
					systemMessage(data);
					if(data != "Test already Assigned !!"){ 
	   		    		getTestSets(true);
	   		    	}
				}
		 	});
	   	}
	}else{
		systemMessage("Please Select a List");
	}
}
function assignTest(){
	var setsArray = $.map($('input[name="sets"]:checked'), function(c){return c.value; }) ;	
	if(setsArray!=null && setsArray.length>0){
		var page = dwr.util.getValue('page');
		var testType = dwr.util.getValue('testType');
	   	var dueDate = dwr.util.getValue('dueDate');
	    var csId = dwr.util.getValue('csId');
	   	if(!dueDate){
	   		systemMessage('Please select due date !!');
	   		$("#dueDate").focus();
	   		return false;
	   	}
	   	var status = isValidDate(dueDate);
	   	if(status){
	   		systemMessage(status);
	   		$("#dueDate").focus();
	   		return false;
	   	}
	   	var titleId = dwr.util.getValue('titleId');
	   	if(!titleId){
	   		systemMessage('Please enter the Title !!');
	   		$("#titleId").focus();
	   		return false;
	   	}
	   	var condition=/^([a-zA-Z0-9]+(\s)?)+$/.test(titleId);
		 if(condition==false){
			 systemMessage("Special characters are not allowed in title");
			 $("#titleId").focus();
			 return false;
		 }
	   	var students = [];
	   	$('#studentId :selected').each(function(i, selectedElement) {
	   		students[i] = $(selectedElement).val();
	   	 });
	   	if(students.length == 0){
	   		systemMessage("Please Select Students");
			return false;
		}
	   	var recordTime = dwr.util.getValue('recordTime');
	   	if(!recordTime){
	   		systemMessage('Please enter Record Time');
	   		$("#recordTime").focus();
	   		return false;
	   	}else{
   		  var condition=/^[0-9]+$/.test(recordTime);
		    if(condition==false){
		    	systemMessage("Please enter valid record time");
		        $("#recordTime").focus();
		        return false;
		    }else{
		    	if(recordTime == 0){
		    		systemMessage("Please enter valid record time");
			    	$("#recordTime").focus();
				    return false;
		    	}
		    }
	   	}
   	if(dueDate){
	   	$.ajax({
			url : "assignTest.htm",
			data: "setsArray="+setsArray+"&dueDate="+dueDate+"&titleId="+titleId+"&page="+page+"&testType="+testType+"&csId="+csId+"&recordTime="+recordTime+"&students="+students,
			success : function(data){
				$("select#studentId > option").prop('selected', false);
				$("#select_all").removeAttr("checked");
				var divId = dwr.util.getValue('divId');
				if(data != "Already test assigned with same title"){ 
					if(divId == 'assignLetterSet'){
				   		$("#AssignLetterSetDetailsPage").html('');
				   		systemMessage(data);
				   	}else if(divId == 'assignWordSet'){
				   		$("#AssignWordSetDetailsPage").html('');
				   		systemMessage(data);
				   	}
				}else{
					if(divId == 'assignLetterSet'){
						systemMessage(data,"error");
				   	}else if(divId == 'assignWordSet'){
				   		systemMessage(data,"error");
				   	}
				}
			}
		});
   	}
	}else{
		systemMessage("Please Select a List");
	}
}

function getStudentDetailsForGrade(){
		var assignmentId = window.parent.document.getElementById( 'titleId' ).value;
		if(!assignmentId)
			assignmentId = dwr.util.getValue('titleId');
		
		var divId = window.parent.document.getElementById( 'divId' ).value;
		if(!divId)
			divId = dwr.util.getValue('divId');
		
		var studentDetailsForGradeCallBack = function (list){
		var str = "<table class='des' border=0 id='togglePage'><tr><td><div class='Divheads'><table align='center'> <tr> " +
			"<th width='100' align='center'>Select </th>" +
			"<th width='100' align='center'>Submitted </th>" +
			"<th width='100' align='center'>Student Id</th>" +
			"<th width='150' align='center'>Student Name</th>" +
			"<th width='180' align='center'>Test Status</th>" +
			"<th width='150' align='center'>Graded Status</th>" +
			"<th width='100' align='center'>Score</th>"+
			"<th width='150' align='center'>Academic Grade</th></tr></table></div><div class='DivContents'><table><tr><td>&nbsp;</td></tr>";
	
		for (i=0;i<list.length;i++){
			var submitted ="";
			if(list[i].status=='submitted'){
				submitted ="Yes";
			}
			str +=  "<tr><td width='100' align='center' style=''><input type='hidden' id='studentName"+i+"' value='"+list[i].student.userRegistration.firstName+" "+list[i].student.userRegistration.lastName+"'/> <input type='radio'  class='radio-design' id='radio"+i+"' name='radio' value='"+list[i].studentAssignmentId+"' onClick=\"getStudentsTestDetails(this,'"+i+"','"+list[i].student.userRegistration.user.userType+"','"+list[i].student.userRegistration.regId+"','"+list[i].gradedStatus+"','"+list[i].status+"','"+list[i].student.studentId+"','"+list[i].assignment.assignmentId+"')\" /><label for='radio"+i+"' class='radio-label-design'></label></td>" +
					"<td  width='100' align='center' class='txtStyle'>"+ submitted +"</td> " +
					"<td  width='100' align='center' class='txtStyle'>"+list[i].student.studentId+"</td> " +
					"<td width='200' align='center' class='txtStyle'> "+list[i].student.userRegistration.firstName+" "+list[i].student.userRegistration.lastName+"</td> " +
					"<td th width='150' align='center' class='txtStyle'>"+list[i].status+"</td>" +
					"<td width='150' align='center' class='txtStyle'>"+list[i].gradedStatus+"</td>";
			if(list[i].percentage > 0){
			str += "<td width='100' align='center' >"+list[i].percentage+"</td>"+
				   "<td width='150' align='center'><a href='javascript:void(0)' onClick=\"getLPSystemRubric('lpSystemRubricDiv')\">"+list[i].academicGrade.acedamicGradeName+" </a></td>";
			}
			str += "<td><div id='dialog"+i+"' width='100%' height='100%'  style=\"width: auto; background: url('images/Common/bg.jpg') center top repeat fixed rgb(247, 245, 245);display:none;\" title=''> "+
				   "<iframe id='iframe"+i+"' frameborder='0' width='100%' height='98%' style='overflow-y:auto;min-height:550px; max-height:550px;' src=''></iframe></div></td>";
			str += "</tr> ";
		 }
			str += "<tr><td height=15 colSpan=30></td></tr><tr><td colspan='20' width='160' align='center'><div id='lpSystemRubricDiv' align='right'/></td></tr></table></div></td></tr></table>";
	
		if(divId == 'gradeLetterSet'){
	   		$('#GradeLetterSetDetailsPage').html(str);
			window.parent.document.getElementById('GradeLetterSetDetailsPage').innerHTML=str;
	   	}else if(divId == 'gradeWordSet'){
	   		$('#GradeWordSetDetailsPage').html(str);
	   		window.parent.document.getElementById('GradeWordSetDetailsPage').innerHTML=str;
	   	}
		 $("#loading-div-background").hide();
		 var isInIFrame = (window.location != window.parent.location) ? true : false;
		 if(!isInIFrame)
			 setFooterHeight();
	}
	if(assignmentId != 'select'){
		 $("#loading-div-background").show();
		gradeAssessmentsService.getStudentAssessmentTests(assignmentId,{
			callback : studentDetailsForGradeCallBack
		});
	}else{
		if(divId == 'gradeLetterSet'){
	   		$('#GradeLetterSetDetailsPage').html('');
	   	}else if(divId == 'gradeWordSet'){
	   		$('#GradeWordSetDetailsPage').html('');
	   	}
		return false;
	}
}
function getStudentsTestDetails(obj,id,userType,regId,gradedStatus,status,studentId,assignmentId){
	 var divId = dwr.util.getValue('divId');
	 var screenWidth = $( window ).width() - 40;
	 var screenHeight = $( window ).height() -120;
	 var assignmentTitle = document.getElementById('titleId').options[document.getElementById('titleId').selectedIndex].text; 
		if(status == 'pending'){
			alert("Test not yet submitted !!");
			return false;
		}else if(status == 'submitted'){
			jQuery.curCSS = jQuery.css;
			if(divId == 'gradeLetterSet'){
				 $("#dialog"+id).dialog({
						overflow: 'auto',
						dialogClass: 'no-close',
					    autoOpen: false,
						position: {my: "center", at: "center", of:window ,within: $("body") },
					    title: 'Early Literacy Student Assessment Grading',
					    draggable: true,
					    width : 1200,
					    height : 400,
					    resizable : true,
					    modal : true,
					    close: function (ev, ui) {} 
					});
		   	}else if(divId == 'gradeWordSet'){
		   	 $("#dialog"+id).dialog({
					overflow: 'auto',
					dialogClass: 'no-close',
				    autoOpen: false,
					position: {my: "center", at: "center", of:window ,within: $("body") },
				    title: 'Early Literacy Student Assessment Grading',
				    draggable: true,
				    width : screenWidth,
				    height : screenHeight,
				    resizable : true,
				    modal : true,
				    close: function (ev, ui) {}
				});
		   	}
			 $("#dialog"+id).dialog("open");//.css({height:40, overflow:"auto"});
			 var getEarlyLiteracyGradeCallBack = function(list){
				 var setType = '';
				 var setsArray = new Array();
				 var setNameArray = new Array();
				 var setIdArray = new Array();
				 for (i=0;i<list.length;i++){
					 setsArray.push(list[i].set);
					 setNameArray.push(list[i].setName);
					 setType = list[i].setType;
					 setIdArray.push(list[i].setId);
				 }
				 var iframe = $('#iframe'+id);
				 var studentName = dwr.util.getValue('studentName'+id);
				 iframe.attr('src', "gradeEarlyLiteracyTest.htm?setsArray=" +setsArray+ "&setNameArray="+setNameArray+"&setType="+setType +"&studentAssignmentId="+obj.value + "&dialogDivId=dialog"+id+"&divId="+divId+"&userType="+userType+"&regId="+regId+"&gradedStatus="+gradedStatus+"&studentId="+studentId+"&assignmentId="+assignmentId+"&assignmentTitle="+assignmentTitle+"&page=EarlyLiteracyGrade&studentName="+studentName +"&setIdArray=" + setIdArray);
				 $("#loading-div-background").hide();
			 }
			 $("#loading-div-background").show();
			 earlyLiteracyTestsService.getEarlyLiteracyTests(obj.value,{
				callback : getEarlyLiteracyGradeCallBack
			 });
		}
}

function playAudio(content,setname,id,assignmentId){
	 var button = document.getElementById('button'+id);
	 var page = dwr.util.getValue('page');
	 var audioPath = dwr.util.getValue('earlyLiteracyFilePath');
	 var status = document.getElementById('setStatus'+id).value;
	 var earlyLiteracyFilePath = "";
	 if(page == 'EarlyLiteracyGrade'){
		 if (status == -1 || status == 0 ) {
		   	 button.style.background ='linear-gradient(to bottom,rgb(244, 244, 244) 1%, rgb(255, 255, 255) 48%, rgb(217, 222, 224) 97%, rgb(222, 217, 217) 100%)';
	   	     earlyLiteracyFilePath = audioPath +'/'+assignmentId+'/'+setname+'/'+content+'/'+content+'.wav';
	   		 document.getElementById('button'+id).src = "loadDirectUserFile.htm?usersFilePath="+earlyLiteracyFilePath;
	   		 var audio = new Audio(document.getElementById('button'+id).src);
			 audio.play();
			 button.style.color= '#001323';
	         document.getElementById('score').value++;
	         document.getElementById('setStatus'+id).value=1; 
	    }else{
	   	 	button.style.background ='linear-gradient(to bottom, #03A9F4 5%, #005277 100%)';
	        button.style.color= 'white';
	        document.getElementById('score').value--;
	        document.getElementById('setStatus'+id).value=0;
	    }
	 }else if(page == 'studentTestResult'){
  	     earlyLiteracyFilePath = audioPath +'/'+assignmentId+'/'+setname+'/'+content+'/'+content+'.wav';
  		 document.getElementById('button'+id).src = "loadDirectUserFile.htm?usersFilePath="+earlyLiteracyFilePath;
  		 var audio = new Audio(document.getElementById('button'+id).src);
		 audio.play();
	 }
}

function playELTAudio(contentIndex,setnameIndex,id,assignmentId){
	var content = document.getElementById('setContent' + contentIndex).value;
	var setname = document.getElementById('setName' + setnameIndex).value;
	 var button = document.getElementById('button' + id);
	 var page = dwr.util.getValue('page');
	 var audioPath = dwr.util.getValue('earlyLiteracyFilePath');
	 var status = document.getElementById('setStatus'+id).value;
	 var earlyLiteracyFilePath = "";
	 if(page == 'EarlyLiteracyGrade'){
		 if (status == -1 || status == 0 ) {
		   	 button.style.background ='linear-gradient(to bottom,rgb(244, 244, 244) 1%, rgb(255, 255, 255) 48%, rgb(217, 222, 224) 97%, rgb(222, 217, 217) 100%)';
	   	     earlyLiteracyFilePath = audioPath +'/'+assignmentId+'/'+setname+'/'+content+'/'+content+'.wav';
	   		 document.getElementById('button'+id).src = "loadDirectUserFile.htm?usersFilePath="+earlyLiteracyFilePath;
	   		 var audio = new Audio(document.getElementById('button'+id).src);
			 audio.play();
			 button.style.color= '#001323';
	         document.getElementById('score').value++;
	         document.getElementById('setStatus'+id).value=1; 
	    }else{
	   	 	button.style.background ='linear-gradient(to bottom, #03A9F4 5%, #005277 100%)';
	        button.style.color= 'white';
	        document.getElementById('score').value--;
	        document.getElementById('setStatus'+id).value=0;
	    }
	 }else if(page == 'studentTestResult'){
 	     earlyLiteracyFilePath = audioPath +'/'+assignmentId+'/'+setname+'/'+content+'/'+content+'.wav';
 		 document.getElementById('button'+id).src = "loadDirectUserFile.htm?usersFilePath="+earlyLiteracyFilePath;
 		 var audio = new Audio(document.getElementById('button'+id).src);
		 audio.play();
	 }
}

function submitGradePercentage(total){
	var setNameArray = document.getElementById('setNameArray');
	var gradMarksArray = new Array();
	var contentArray = new Array();
	var setArray = new Array();
	var id = 1;
	var setType = dwr.util.getValue('setType');
	var studentId = dwr.util.getValue('studentId');
	var studentAssignmentId = dwr.util.getValue('studentAssignmentId');
	var score = dwr.util.getValue('score');
	var percentage  = ((score / total) * 100).toFixed(2);
	var totalSetsLength = dwr.util.getValue('totalSetsLength');
	var dueDate = dwr.util.getValue('dueDate');
	var assignmentTitle = window.parent.document.getElementById('titleId').options[window.parent.document.getElementById('titleId').selectedIndex].text; 
	var hwAssignmentId=$("#hwAssignmentId").val(); 
	var setIdArray = new Array(); 
	for (var index = 1; index <= totalSetsLength; index++) {
		var setName = document.getElementById('set'+index).value;
		var setId = document.getElementById("setId" + index).value;
		setArray.push(setName);
		setIdArray.push(setId); 
		var gradedMarks = '';
		var contentStr = '';
		var setContentLength = dwr.util.getValue('setLength'+index);
		for (var con = 1; con <= setContentLength; con++) {
			if(id <= total){
				var status = document.getElementById('setStatus'+id).value;
				var content = document.getElementById('button'+id).value;
				gradedMarks = gradedMarks +' '+status; 
				if(status == '0'){
					if(contentStr)
						contentStr = contentStr +':'+content; 
					else
						contentStr = content;
				}
				id++;
			}
		}
		gradMarksArray.push(gradedMarks);
		contentArray.push(contentStr);
	}
	$.ajax({
		type : "GET",
		url : "gradeAssessment.htm",
		data : "percentage=" + percentage + "&setType="+setType+"&setArray="+setArray+"&gradMarksArray="+gradMarksArray+"&contentArray="+contentArray+"&studentAssignmentId="+studentAssignmentId+"&studentId="+studentId+"&page=EarlyLiteracyGrade"+"&dueDate="+dueDate+"&assignmentTitle="+assignmentTitle+"&hwAssignmentId="+hwAssignmentId
		+"&setIdArray="+setIdArray,
		success : function(response) {
			window.parent.document.getElementById( 'GradeLetterSetDetailsPage' ).innerHTML="";
			getStudentDetailsForGrade();
			if(response.indexOf("Graded Successfully") != -1){
				document.getElementById("printImg").style.display = 'block';
				var resp = response.split(":");
				 document.getElementById("hwAssignmentId").value=resp[1];
				 systemMessage(resp[0]);
			}else{
				 systemMessage(response);
			}
		}
		
	});
}
var mapVar;
var getNoOfAttemptsOnListCallBack = function(map){
	mapVar = map;
	
}
function printDiv() {
	
	 var studentAssignmentId = dwr.util.getValue('studentAssignmentId');
	 var getEarlyLiteracyGradeCallBack = function(list){
		 var setType = '';
		 var setsArray = new Array();
		 var setNameArray = new Array();
		 var setIdArray = new Array();
		 for (i=0;i<list.length;i++){
			 setsArray.push(list[i].set);
			 setNameArray.push(list[i].setName);
			 setIdArray.push(list[i].setId)
			 setType = list[i].setType;
		 }
		 var studentId = document.getElementById('studentId').value;
		 earlyLiteracyService.getNoOfAttemptsOnList(studentId,setNameArray,{
				callback : getNoOfAttemptsOnListCallBack
			 
		 });
		 var getGradedMarksAsListCallBack = function(gradedMarksArray){
			var teacherName = window.parent.document.getElementById('teacherName').value;
			var studentName = document.getElementById('studentName').value;
			var noOfAttemptsVar = 0;
			var assignmentTitle = window.parent.document.getElementById('titleId').options[window.parent.document.getElementById('titleId').selectedIndex].text; 
			var gradeName = window.parent.document.getElementById('gradeId').options[window.parent.document.getElementById('gradeId').selectedIndex].text; 
			var printDiv = "<table width='100%' align='center' cellpadding='2' cellspacing='8' border='0'><tr><td height='217'><table width='100%' height='217' align='center' cellpadding='2' cellspacing='8' border='0'><tr style='padding-left: 1em; font-size: 20px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><td> Student Name : <b>"+studentName+"</b></td><td> Teacher Name : <b>"+teacherName+"</b></td></tr>"+
			               "<tr style='padding-left: 1em; font-size: 20px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><td> Grade : <b>"+gradeName+"</b></td><td> Title : <b>"+assignmentTitle+"</b></td></tr>";    
			var count = 1,pageCount=1;
			var rowcount=0;
			var cnt = 0; 
			    for (var setId = 0; setId < setNameArray.length; setId++) {
			    	var rowCount=0;
					var contentArr = setsArray[setId].split(' ');
						printDiv += "<tr style='border-collapse: collapse;padding-left: 1em; font-size: 18px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans," +
								" Helvetica Neue, Arial, sans-serif;'><td colspan='2'> List Name : <b>"+setNameArray[setId]+"</b> &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; " +
										"No.Of Attempts : <b>"+mapVar[setNameArray[setId]]+"</b> </td></tr><tr><td colspan='2' style='border-collapse: collapse;'>" +
												"<table width='100%' align='center' cellpadding='2' cellspacing='8' border='1'><tr>";
						var i = 0;
						for (var content = 0; content < contentArr.length; content++) {
							
							if(setType == 'Letter'){
								if(gradedMarksArray[cnt] == '0'){
									if(count < 2){
										printDiv += "<td width='10%' colspan='1' align='center' height='217' style='font-size: 140px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><font color=black><b>"+contentArr[content]+"</b></font></td>";
										rowCount=rowCount+1;
									}else{
										i=i+1;
										printDiv += "<td width='10%' colspan='1' align='center' height='217' style='font-size: 140px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><font color=black><b>"+contentArr[content]+"</b></font></td>";
										count = 0;
										rowCount=rowCount+1;
									}
									count = count+1;
									var rem=rowCount%5;
									if(rem==0){
										printDiv +="</tr><tr>";
									}
								}
							}else if(setType == 'Word'){
								
								if(gradedMarksArray[cnt] == '0'){
									
									if(count < 2){
										printDiv += "<td colspan='1' width='20%' align='center' height='227' style='font-size: 70px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><font color=black><b>"+contentArr[content]+"</b></font></td>";
										rowCount=rowCount+1;
									}else{
										i=i+1;
										printDiv += "<td colspan='1' width='20%' align='center' height='227' style='font-size: 70px;font-family:Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;'><font color=black><b>"+contentArr[content]+"</b></font></td>";
										count = 0;
										rowCount=rowCount+1;
									}
									count = count+1;
								    var rem=rowCount%5;
									if(rem==0){
										printDiv +="</tr><tr>";
									}
								}
							}
							
							cnt = cnt+1;
							
						}
						if(i == 0){
							printDiv += "<td></td></tr>";
						}
						i = 0;
						count = 1;
						printDiv += "</tr></table></td></tr>";
			    }
				printDiv += "</table>";
				$("#printdiv").html(printDiv);
				$("#printdiv").print({
                     globalStyles: false,
                     mediaPrint: false,
                     iframe: true,
                     noPrintSelector: ".avoid-this"
                });
		 }
		
	    earlyLiteracyService.getGradedMarksAsList(studentAssignmentId,setIdArray,{
			callback : getGradedMarksAsListCallBack
		 });
	 }
	 earlyLiteracyTestsService.getEarlyLiteracyTests(studentAssignmentId,{
		callback : getEarlyLiteracyGradeCallBack
	 });
}

function numValCheck(val){
	 if(val){
		 var condition=/^[0-9]+$/.test(val);
		    if(condition==false){
		    	systemMessage("Please enter valid record time");
		        $("#recordTime").focus();
		        return false;
		    }else if(val == 0){
		    	systemMessage("Please enter valid record time");
		    	$("#recordTime").focus();
			    return false;
		    }else{
		    	return  true;
		    }
	 }
}
function setTestType(){
	
	var studentId = $('#studentId').val();
	var divId = dwr.util.getValue('divId');
	var optionLength = $('#studentId option').length;
	if(studentId == null && optionLength ==1 ){
		alert("No students are available");
		return false;
	}
	else if(studentId == null &&  optionLength>1)
		{
		alert("Please select a student");
		return false;
		}
		
	else if(studentId != 'select' && studentId!=""){
		$('#assignTypeDiv').show();
		$('#teacher').attr('checked', false); 
		if(divId == 'assignLetterSet'){
	   		if($( "#AssignLetterSetDetailsPage").text() && studentId.length > 0){
	   			if(studentId.length == 0)
	   				$("#select_all").removeAttr("checked");
	   			return false;
	   		}
	   	}

	else if(divId == 'assignWordSet'){
		if($( "#AssignWordSetDetailsPage").text() && studentId.length > 0){
			if(studentId.length == 1)
				$("#select_all").removeAttr("checked");
	   			return false;
	   		}
	   	}
		getTestSets(false);
	}
	 
	 else{
		alert("Please Select All Filters");
		return false;
	}

 }



function getWordLists(){
	var csId = $('#csId').val();
	var getK1SetsBycsIdCallBack = function(k1SetsLt){
		if(k1SetsLt.length > 0){
			var contentDiv = "<table width='38%' align='center' border='0' class='des'><tr><td><table class='Divheads' width='100%' ><tr><td width='70%' style='padding-left:3em;'>Sets Name</td><td colspan='1' width='30%'>Remove</td></tr></table></td></tr><tr><td><table width='100%'  class='DivContents' style='padding-top:0.5em;'>";
			  for (var id = 0; id < k1SetsLt.length; id++) {
				  contentDiv += "<tr><td class='tabtxt' style='padding-left:3em;' width='70%'><input type='hidden' id='setId"+id+"' name='setId"+id+"' value='"+k1SetsLt[id].setId+"'/> <a href='#' onclick=openWordListDialog('edit',"+id+") style='color:black;'>" +k1SetsLt[id].setName+ "</a></td><td width='30%'><i class='fa fa-times' style='cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;' onclick='removeSetBysetId("+id+")'></i></td></tr>";
			  }
			  contentDiv += "</table></td></tr></table>";
			  $("#displaySetsDiv").html(contentDiv);
			  $("#displaySetsDiv").show();
		}else{
			 $("#displaySetsDiv").hide();
		}
 	    $('#addWordList').show();
	}
	if(csId != 'select'){
		 earlyLiteracyService.getK1SetsBycsId(csId,{
			callback : getK1SetsBycsIdCallBack
		 });
	}
}

function removeSetBysetId(id){
	var setId = 0;
	if(id > -1)
		setId = $('#setId'+id).val();
	if(setId > 0){
		$.ajax({
			url : "removeSetBysetId.htm",
			data: "setId="+setId,
			success : function(data){
				if(data == 'Test already Assigned !!')
					data = "Set already assigned to a test..unable to remove !!";
				 systemMessage(data);
				 if(data == 'Removed')
			    	getWordLists();
			}
		});
	}
}

function openWordListDialog(mode,id){
	var setId = 0;
	if(mode == 'edit'){
		if(id > -1)
		setId = $('#setId'+id).val();
	}
	var csId = $('#csId').val();
	var divId = $('#divId').val();
	jQuery.curCSS = jQuery.css;
	 $("#createWordListdialog").dialog({
			overflow: 'auto',
			dialogClass: 'no-close',
		    autoOpen: false,
			position: {my: "center", at: "center", of:window ,within: $("body") },
		    title: 'Create/Update Word List',
		    draggable: true,
		    width : 1200,
		    height : 650,
		    resizable : true,
		    modal : true,
		    close: function (ev, ui) {getWordLists();} 
	 });
	 $("#createWordListdialog").dialog("open");
	 var iframe = $('#iframe');
	 iframe.attr('src', "getWordListContent.htm?mode="+mode+"&setId="+setId+"&csId="+csId+"&divId="+divId);
}

function getTests(){
	 var radio =  $('input[name=eltTests]:checked').val(); 
	 $("#assignedDate").empty();
	 $("#assignedDate").append(
			$("<option></option>").val('select').html('select'));
	 $("#titleId").empty();
	 $("#titleId").append($("<option></option>").val('select').html('select'));
	 if(radio == 'system'){
	   $('#dateDiv').show();  
	   getEltAssignedDates(
			   function(){
					val = getStoredValue('assignedDate');
					var assignedDate = setDropdownVal('assignedDate', val);
					getEltPstAssignmentTitles(function(){
						val = getStoredValue('titleId');
						var titleId = setDropdownVal('titleId', val);
						if(titleId)
							$("#titleId").css('color','black');
						else
							$("#titleId").css('color','gray');
						getStudentAsessments(function(){
							getStudentDetailsForGrade();
						});
					});	
				}
	   );
	}else if('autoAssign'){
		getAutoAssignmentTitles(function(){
			val = getStoredValue('titleId');
			var titleId = setDropdownVal('titleId', val);
			if(titleId)
				$("#titleId").css('color','black');
			else
				$("#titleId").css('color','gray');
			getStudentDetailsForGrade();
		});
		$('#dateDiv').hide(); 
	}
	 
}

function getEltStudentDetails(){
	 var radio =  $('input[name=eltTests]:checked').val(); 
	 if(radio == 'system'){
		 getTests();
	 }else{
		 getTests();
	 }
}

function getAutoAssignmentTitles(callback){
	var usedFor = $('#usedFor').val();
	var csId = $('#csId').val();
	var assignmentType = dwr.util.getValue('page');
	var divId = dwr.util.getValue('divId');
	if(csId != 'select'){
		var getAutoAssignmentTitlesCallBack = function(assignmentLt){
		  $("#titleId").empty();
			$("#titleId").append(
					$("<option></option>").val('select').html('select'));
			$.each(assignmentLt, function(index, value) {
				$("#titleId").append(
						$("<option></option>").val(value.assignmentId).html(value.title));
			});
			if(callback)
				callback();
		 }
		 earlyLiteracyService.getAutoAssignmentTitles(csId, usedFor, assignmentType,{
			callback : getAutoAssignmentTitlesCallBack
		 });
		
	}else{
		if(divId == 'gradeLetterSet'){
	   		$('#GradeLetterSetDetailsPage').html('');
	   	}else if(divId == 'gradeWordSet'){
	   		$('#GradeWordSetDetailsPage').html('');
	   	}
		$("#titleId").empty();
		$("#titleId").append($("<option></option>").val('select').html('select'));
	}
}

function getEltAssignedDates(callback) {
	var usedFor = $('#usedFor').val();
	var csId = $('#csId').val();
	var page = dwr.util.getValue('page');
	if(csId != 'select'){
	$.ajax({
		type : "GET",
		url : "getAssignedDates.htm",
		data : "csId=" + csId + "&usedFor=" + usedFor+ "&page=" + page,
		success : function(response2) {
			var teacherAssignedDates = response2.teacherAssignedDates;
			if(teacherAssignedDates.length > 0){
				$("#assignedDate").empty();
				$("#assignedDate").append(
						$("<option></option>").val('select').html('select'));
				$.each(teacherAssignedDates, function(index, value) {
					$("#assignedDate").append(
							$("<option></option>").val(getDBFormattedDate(value.dateAssigned)).html(
									getFormattedDate(value.dateAssigned)));
				});
				$("#titleId").empty();
				$("#titleId").append(
						$("<option></option>").val('select').html('Select Title'));
				 if(callback)
						callback();
			}
		}
	});
	}else{
		$('#assignedDate').css('color','gray');
		$("#titleId").css('color','gray');
		$("#assignedDate").empty();
		$("#assignedDate").append(
				$("<option></option>").val('select').html('select'));
		$("#titleId").empty();
		$("#titleId").append(
				$("<option></option>").val('select').html('Select Title'));
	}
}