 
function showTestDiv(){
	var csId = dwr.util.getValue('csId');
	document.getElementById("showLang").style.visibility = "visible";
	var langId=$('#langId').val();
	if(csId != "select" && langId!="select"){
		$.ajax({  
			type : "GET",
			url : "getPhonicTestGroups.htm", 
		    data: "&csId="+csId+"&langId="+langId,
		    success : function(data) {
		    	 $("#dataDiv").html(data);
		    	 datepick();
				 setFooterHeight();
			}
		}); 
	}else{
		$("#dataDiv").html("");
		 setFooterHeight();
	}
}


function assignPhonicSkillsTest(langId){
	 var groupIdArray = new Array();
	 if(langId==1){
	 var groupsLength =$("#groupsLength").val();
	 for (var i = 1; i <= groupsLength; i++){
			var groupId = dwr.util.getValue('groupId'+i);
			if(groupId){
				groupIdArray.push(groupId);
			}
	 }
	 }else{
		 var bpstTypes = document.getElementsByName('bpsttype');
			for(var i = 0; i < bpstTypes.length; i++){
			    if(bpstTypes[i].checked){ 
			    	bpstType = bpstTypes[i].value;
			    	groupIdArray.push(bpstType);
			    }
			}
	 }
	 var dueDate = $("#dueDate").val();
	 var csId = $("#csId").val();
	 var students = [];
	 $('#studentId :selected').each(function(i, selectedElement) {
		 if($(selectedElement).val() != 'select')
			 students[i] = $(selectedElement).val();
	 });
	 if(students.length == 0){
		 alert("Please select students");
		 return false;
	 }	
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
		 systemMessage('Please enter a title !!');
		 $("#titleId").focus();
		 return false;
	 }
	 var pattern = new RegExp(/[~`!#$%\^&*+=\-\[\]\\';,/{}|\\":<>\?]/); 
	 if (pattern.test(titleId)) {
		 systemMessage("Special Characters Not Allowed In Title");
		 $('#titleId').val("");
		 $('#titleId').focus();
		 return false;
	 }
	 if(groupIdArray.length > 0 && dueDate && titleId){
		 $.ajax({  
			 type : "GET",
			 url : "assignPhonicSkillsTest.htm", 
			 data: "groupIdArray="+groupIdArray+"&dueDate="+dueDate+"&titleId="+titleId+"&csId="+csId+"&students="+students+"&langId="+langId,
			 success : function(data) {
				 $("select#studentId > option").prop('selected', false);
				 $("#select_all").removeAttr("checked");
				 $("#dueDate").val('');
				 $("#titleId").val('');
				 if(data == "Test already Assigned !!" || data == "Failed to Assign !!"){
					 systemMessage(data,"error");
				 }else{
					 systemMessage(data);
				 }			  
				 $('input:checkbox').prop('checked', false);
			 }
		 }); 
	 }
}
function getPhonicGroupsByBpstType(bpstTypeId){
	var bpstTypes = document.getElementsByName('bpsttype');
	var bpstType=0;
	for(var i = 0; i < bpstTypes.length; i++){
	    if(bpstTypes[i].checked){
	    	$.ajax({  
				type : "GET",
				url : "getBpstGroups.htm", 
			    data: "&bpstTypeId="+bpstTypeId,
			    success : function(data) {
			    	 $("#bpstGroup"+bpstTypeId).html(data);
			    	 datepick();
					 setFooterHeight();
				}
			}); 
	    }else{
	    	bpstType = bpstTypes[i].value;
	    	$("#bpstGroup"+bpstType).html("");
	    }
	}
}
