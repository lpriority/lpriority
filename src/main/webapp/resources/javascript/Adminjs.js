
function load() {
		window.document.getElementById('parentEmailId').focus();
	}
	function adminAddTeacherVal() {
		var name = $('#emailId').val();

		if (name == '' || name == null) {
			alert("Please enter the email address");
			$('#emailId').next().show();
			return false;
		} else if (IsEmail(name) == false) {
			alert("Please enter valid email address");
			$('#emailId').show();
			return false;
		}

		$.ajax({
			url : "AdminAddTeacher.htm",
			data : "emailId=" + name,
			success : function(data) {	
				systemMessage(data);
				$('#emailId').val('');
			}
		});

	}
	function adminAddParentVal()
	{
		var name = $('#emailId').val();

		if (name == '' || name == null) {
			alert("Please enter the email address");
			$('#emailId').next().show();
			return false;
		} else if (IsEmail(name) == false) {
			alert("Please enter valid email address");
			$('#emailId').show();
			return false;
		}

		$.ajax({
			url : "AdminAddParent.htm",
			data : "emailId=" + name,
			success : function(data) {	
				systemMessage(data);
				$('#emailId').val('');
			}
		});
	}
	function IsEmail(email) {
		var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if (!regex.test(email)) {
			return false;
		} else {
			return true;
		}
	}
	function adminAddUser() {
		var pemailId = $('#parentemailId').val();
		if (pemailId == '' || pemailId == null) {
			alert("Please enter the parent email address");
			$('#parentemailId').next().show();
			return false;
		} else if (IsEmail(pemailId) == false) {
			alert("Please enter valid email address");
			$('#emailId').show();
			return false;
		}
		var semailId = $('#stdemailId').val();
		if (semailId == '' || semailId == null) {
			alert("Please enter the student email address");
			$('#studentemailId').next().show();
			return false;
		} else if (IsEmail(semailId) == false) {
			alert("Please enter valid email address");
			$('#invalid_email').show();
			return false;
		}
		// var studentage=$('#studentage').val();
		var studentage = $('input[name="studentage"]:checked').val();

		if (studentage == '' || studentage == null) {
			alert("Please select student age");
			$('#studentage').next().show();
			return false;
		}
		$.ajax({
			url : "AdminAddStudent.htm",
			data : "pemailId=" + pemailId + "&semailId=" + semailId
					+ "&studentage=" + studentage,
			success : function(data) {
				systemMessage(data);
				$('#parentemailId').val('');
				$('#stdemailId').val('');
			}
		});

	}

// -------------------end Add User------------------//
	
// -------------------Delete User-------------------//

	
	function DeleteUser() {
		var emailId = $('#useremailId').val();

		if (emailId == '' || emailId == null) {
			alert("Please enter the email address");
			$('#useremailId').next().show();
			return false;
		} else if (IsEmail(emailId) == false) {
			alert("Please enter valid email address");
			$('#useremailId').show();
			return false;
		}

		$.ajax({
			url : "AdminDeleteUser.htm",
			data : "emailId=" + emailId,
			success : function(data) {
				systemMessage(data);
				$('#useremailId').val('');
			}
		});

	}

	function  updateSection(index){
		var sectionname=$('#sectionName'+index).val();
		var sectionId=$('#edit'+index).val();
		var sectionNames = document.getElementsByName("sectionName");
		var letterNumber= /^[0-9a-zA-Z ]*$/;
		for(var i = 0; i < sectionNames.length; i++) {
			if (sectionNames[i].id != 'sectionName'+index) {
				if(sectionNames[i].value == sectionname){
					alert("Section name already exists");
					$('#edit'+index).attr('checked', false);
					return false;
				}
				else if(!(letterNumber.test(sectionname)))
					{
					alert("Special charectors are not allowed");
					$('#edit'+index).attr('checked', false);
					return false;
				}		
		}
		}
		  if(confirm("Do you want update the section name?",function(status){
			if(status){
				 $.ajax({  
						url : "UpdateSections.htm", 
				        data: "sectionId=" + sectionId+"&sectionname="+sectionname,
				        success : function(data) { 
				        	$('#edit'+index).attr('checked', false);
				        	systemMessage(data);
				        }  
				    }); 
			}else{
					$('#edit'+index).attr('checked', false);
				  return false;
			}
		})); 
	}

function  DeleteCheckSection(index){
	var sectionId=$('#del'+index).val();
	if(confirm("Do you want to delete the section?",function(status){
		if(status){
			$.ajax({  
				url : "CheckSections.htm", 
		        data: "sectionId=" + sectionId,
		        success : function(response) { 
		        	  	if(response == '1'){
		        	     $('#del'+index).attr('checked', false);
		        	     systemMessage("Can't Delete. Section Already Used !!");
		              
		            }else{
		            	 DeleteSection(index);
		            	 
		            }
		       }  
		    }); 
		}else{
			$('#del'+index).attr('checked', false);
			return false;
		}
	})); 
}
function  DeleteSection(index){
	var gradeId=$('#gradeIds').val();
	 if(gradeId== '' || gradeId==null || gradeId=='select'){
		 alert("Please select the gradeName");
		 $('#gradeIds').next().show();
		 return false;
	 }
	 var classId=$('#classIds').val();
	 if(classId== '' || classId==null || classId=='select'){
		 alert("Please select the className");
		 $('#classIds').next().show();
		 return false;
	 }
	var sectionname=$('#sectionName'+index).val();
	var sectionId=$('#del'+index).val();
	$.ajax({  
		url : "DeleteSections.htm", 
		data: "gradeId=" + gradeId+"&classId="+classId+"&sectionId=" + sectionId+"&sectionname="+sectionname,
		success : function(response) { 
			$("#getsection").html(response);
			systemMessage("Deleted Successfully !!");
		}  
	}); 
}



function submitSection()
{
	var gradeId=$('#gradeId').val();
    var classId=$('#classId').val();
    var gradeLevelId=$('#gradeLevelId').val();
	var sectionName=$('#sectionName').val();
	 if(sectionName== '' || sectionName==null){
		 alert("Please enter the section name");
         $('#sectionName').next().show();
         return false;
      }
	 var condition=/^([a-zA-Z0-9]+(\s)?)+$/.test(sectionName);
	 if(condition==false){
		 alert("special characters not allowed");
		 return false;
	 }
	
	 if(gradeId!='select' && classId!='select' && gradeLevelId!='select' && classId!=0){
	 $.ajax({  
			url : "addSections.htm", 
	        data: "gradeId=" + gradeId+"&classId="+classId+"&gradeLevelId="+gradeLevelId+"&sectionName="+sectionName,
	        success : function(data) { 
	        	alert(data);          	
	            $('#gradeId').val('select');
	            $('#classId').empty();
	            $('#classId').val('select');
	            $("#classId").append($("<option></option>").val("select").html("Select Class"));
	            $('#gradeLevelId').empty();
	            $("#gradeLevelId").append($("<option></option>").val("select").html("Select GradeLevel"));
	            $('#sectionName').val('');
	            
	        }  
	    });
	 }else{
		 alert("Please Fill All The Filters");
		 return false;
	 }
	 
}

function getSection()
{
	var gradeId=$('#gradeIds').val();
	 if(gradeId== '' || gradeId==null || gradeId=='select'){
		 alert("Please select the gradeName");
        $('#gradeIds').next().show();
        return false;
     }
	 var classId=$('#classIds').val();
	 if(classId== '' || classId==null || classId=='select'){
		 alert("Please select the className");
        $('#classIds').next().show();
        return false;
     }
$.ajax({  
	url : "GetSections.htm", 
    data: "gradeId="+gradeId+"&classId="+classId,
    success: function(response) {
        $("#getsection").html(response);
    }
}); 	
}
function  validatePeriodsTimes(){
	var starttime=$('#starttime').val();
	var starttimemin=$('#starttimemin').val();
	var starttimemeridian=$('#starttimemeridian').val();
	var endtime=$('#endtime').val();
	var endtimemin=$('#endtimemin').val();
	var endtimemeridian=$('#endtimemeridian').val();
	var period=$('#period').val();
	 var startTotalTime;
     var endTotalTime;


     starttime = parseInt(starttime,10);
     starttimemin = parseInt(starttimemin,10);
     endtime = parseInt(endtime,10);
     endtimemin = parseInt(endtimemin,10);
     
     if(((starttime < 4 && starttimemeridian == "AM") || ((starttime == 12 && starttimemeridian == "AM") || (starttime == 11 && starttimemeridian == "PM"))) 
    	|| (((endtime == 12 && endtimemeridian == "AM") || ((endtime == 11 && endtimemin > 0) && endtimemeridian == "PM")) || (endtime < 4 && endtimemeridian == "AM"))){
    	 alert("Before 4 AM and After 11 PM not allowed for schedule"); 
    	 return false;
     }
     
     if(starttimemeridian=="PM") {
         if(starttime==12)
             startTotalTime = ((starttime*60)+(starttimemin));
         else
             startTotalTime = ((starttime*60)+(starttimemin)+(12*60));

     }
     else if(starttimemeridian=="AM") {
         startTotalTime = ((starttime*60)+(starttimemin));

     }
     if(endtimemeridian=="PM") {
         if(endtime==12)
             endTotalTime = ((endtime*60)+(endtimemin));
         else
             endTotalTime = ((endtime*60)+(endtimemin)+(12*60));

     }
     else if(endtimemeridian=="AM") {

         endTotalTime = ((endtime*60)+(endtimemin));

     }

     startTotalTime = parseInt(startTotalTime,10);
     endTotalTime = parseInt(endTotalTime,10);

     if(startTotalTime>=endTotalTime) {
	     alert("End time should be greater than start time");
	     return false;
     }

     if((endTotalTime)-(startTotalTime)>240){
         alert("Class schedule should not exceed 4 hours");
         return false;
     }
     return true;
}

function savePeriod() {
	var gradeId = $('#gradeId').val();
	var period = $('#period').val();
	var starttime= parseInt($('#starttime').val());
	var starttimemin=$('#starttimemin').val();
	var starttimemeridian=$('#starttimemeridian').val();
	var endtime=parseInt($('#endtime').val());
	var endtimemin=$('#endtimemin').val();
	var endtimemeridian=$('#endtimemeridian').val();
	
	var condition=/^([a-zA-Z0-9]+(\s|\.|\')?)+$/.test(period);
	
	if(gradeId== '' || gradeId==null || gradeId=='select'){
		 alert("Please select the gradeName");
       $('#gradeId').next().show();
       return false;
    }else if (period == '' || period == null) {
    	systemMessage("Please enter the period name");
		$('#period').focus();
		$('#period').next().show();
		return false;
	}
    else if(condition==false){
    	systemMessage(" requires alphanumeric characters");
		$('#period').focus();
		return false;
	}
	else if(starttime== '' || starttime==null){
		alert("Please select Start Time");
		$('#starttime').next().show();
		return false;
	}else if(starttimemin== '' || starttimemin==null){
		alert("Please select the start Minute");
		$('#starttimemin').next().show();
		return false;
	}else if(starttimemeridian== '' || starttimemeridian==null){
		alert("Please select the start Meridian");
		$('#starttimemeridian').next().show();
		return false;
	}else if(endtime== '' || endtime==null){
		alert("Please select End Time");
		$('#endtime').next().show();
		return false;
	}else if(endtimemin== '' || endtimemin==null){
		alert("Please select the end Minute");
		$('#endtimemin').next().show();
		return false;
	}else if(endtimemeridian== '' || endtimemeridian==null){
		alert("Please select the end Meridian");
		$('#endtimemeridian').next().show();
		return false;
	}else if(starttime == endtime){
		if(starttimemin == endtimemin && endtimemeridian == starttimemeridian){
			alert("start time and end time should not be same");
			return false;
		}else if(starttimemin > endtimemin){
			alert("start time should not greater than end time ");
			return false;
		}
	}
	var isValid = validatePeriodsTimes();
	if(isValid){	
	$.ajax({
		url : "AdminAddPeriod.htm",
		method : "GET",
		data : "gradeId=" + gradeId + "&period=" + period
				+ "&starttime=" + starttime+"&starttimemin=" + starttimemin +"&starttimemeridian=" + starttimemeridian +
				"&endtime=" + endtime+"&endtimemin=" + endtimemin +"&endtimemeridian=" + endtimemeridian,
		success : function(data) {
			if(data == "Saved Successfully !!"){
				systemMessage(data);
				document.adminSetPeriodsForm.reset();
			}else{
				alert(data);
			}
		}
	});
}

}

//*******End Set Periods************//

//------------ Start Edit Periods-----------//
function getPeriods()
{
	$("#getperiod").html('');
	var gradeId=$('#gradeIds').val();
	 if(gradeId== '' || gradeId==null || gradeId=='select'){
		 alert("Please select the gradeName");
        $('#gradeIds').next().show();
        return false;
     }
	 $("#loading-div-background").show();
$.ajax({  
	url : "GetPeriods.htm", 
    data: "gradeId="+gradeId,
    success: function(response) {
          $("#getperiod").append(response);
          $("#loading-div-background").hide();
    }
}); 	
}

function  updatePeriod(index){
	  var isChecked = $('#edit'+index).is(':checked');
	  if(isChecked){
		  var lastChecked = $('#lastChecked').val();
		  if(lastChecked)
			  $(lastChecked).attr('checked', false);
		   $('#lastChecked').val('#edit'+index);
			var starttime=parseInt($('#starttime'+index).val());
			var starttimemin=$('#starttimemin'+index).val();
			var starttimemeridian=$('#starttimemeridian'+index).val();
			var endtime=parseInt($('#endtime'+index).val());
			var endtimemin=$('#endtimemin'+index).val();
			var endtimemeridian=$('#endtimemeridian'+index).val();
			var period=$('#period'+index).val();
			var periodId=$('#edit'+index).val();
			
			var condition=/^([a-zA-Z0-9]+(\s|\.|\')?)+$/.test(period);
			
			 var startTotalTime;
		     var endTotalTime;
		
		     var gradeId = $('#gradeIds').val();
		     
		     if(gradeId== '' || gradeId==null || gradeId=='select'){
				 alert("Please select the gradeName");
		       $('#gradeId').next().show();
		       return false;
		    }else if (period == '' || period == null) {
		    	systemMessage("Please enter the period name");
				$('#period').focus();
				$('#period').next().show();
				return false;
			} else if(condition==false){
				systemMessage(" requires alphanumeric characters");
				$('#period').focus();
				return false;
			}else if(starttime== '' || starttime==null){
				alert("Please select Start Time");
				$('#starttime').next().show();
				return false;
			}else if(starttimemin== '' || starttimemin==null){
				alert("Please select the start Minute");
				$('#starttimemin').next().show();
				return false;
			}else if(starttimemeridian== '' || starttimemeridian==null){
				alert("Please select the start Meridian");
				$('#starttimemeridian').next().show();
				return false;
			}else if(endtime== '' || endtime==null){
				alert("Please select End Time");
				$('#endtime').next().show();
				return false;
			}else if(endtimemin== '' || endtimemin==null){
				alert("Please select the end Minute");
				$('#endtimemin').next().show();
				return false;
			}else if(endtimemeridian== '' || endtimemeridian==null){
				alert("Please select the end Meridian");
				$('#endtimemeridian').next().show();
				return false;
			}else if(starttime == endtime){
				if(starttimemin == endtimemin && endtimemeridian == starttimemeridian){
					alert("start time and end time should not be same");
					return false;
				}else if(starttimemin > endtimemin){
					alert("start time should not greater than end time ");
					return false;
				}
			}
		     stime = parseInt(starttime,10);
		     stimemin = parseInt(starttimemin,10);
		     var etime = parseInt(endtime,10);
		     var etimemin = parseInt(endtimemin,10);
		     if(((stime < 4 && starttimemeridian == "AM") || ((stime == 12 && starttimemeridian == "AM") || (stime == 11 && starttimemeridian == "PM"))) 
		    	    	|| (((etime == 12 && endtimemeridian == "AM") || ((etime == 11 && etimemin > 0) && endtimemeridian == "PM")) || (etime < 4 && endtimemeridian == "AM"))){
		    	 alert("Before 4 AM and After 11 PM not allowed for schedule"); 
		    	 $('#edit'+index).attr('checked', false);
		    	 return false;
		     }
		     if(starttimemeridian=="PM") {
		         if(stime==12)
		             startTotalTime = ((stime*60)+(stimemin));
		         else
		             startTotalTime = ((stime*60)+(stimemin)+(12*60));
		
		     }
		     else if(starttimemeridian=="AM") {
		         startTotalTime = ((stime*60)+(stimemin));
		
		     }
		     if(endtimemeridian=="PM") {
		         if(etime==12)
		             endTotalTime = ((etime*60)+(etimemin));
		         else
		             endTotalTime = ((etime*60)+(etimemin)+(12*60));
		
		     }
		     else if(endtimemeridian=="AM") {
		
		         endTotalTime = ((etime*60)+(etimemin));
		
		     }
		     startTotalTime = parseInt(startTotalTime,10);
		     endTotalTime = parseInt(endTotalTime,10);

		     if(startTotalTime>=endTotalTime) {
		
		         alert("End time should be greater than start time");
		         $('#edit'+index).attr('checked', false);
		         return false;
		
		     }
		     if((endTotalTime)-(startTotalTime)>240){
		         alert("Class schedule should not exceed 4 hours");
		         $('#edit'+index).attr('checked', false);
		         return false;
		     }
		     if(period=="")
		     {
		         alert("please enter the period");
		         $('#period'+index).next().show();
		         return false;
		     }
			if(confirm("Do you want update the Period?",function(status){
				if(status){
					 $.ajax({  
							url : "UpdatePeriods.htm", 
					        data: "starttime=" + starttime + "&starttimemeridian="+ starttimemeridian +" &starttimemin="+ starttimemin +"&endtime="+ endtime +"&endtimemin="+ endtimemin +"&endtimemeridian="+ endtimemeridian +"&period="+period+"&periodId="+periodId,
					        success : function(data) { 
					        	if(data == "Updated Successfully !!"){
					        		systemMessage(data);
						        	$('#edit'+index).attr('checked', false);
								}else{
									alert(data);
								}
					        }  
					    })
				}else{
					 $('#lastChecked').val('');
					 $('#edit'+index).attr('checked', false);
					  return false;
				}
			}));
	 }
}

function  DeleteCheckPeriod(index)
{
	var period=$('#period'+index).val();
	var periodId=$('#del'+index).val();
if(confirm("Do you want to delete the period?",function(status){
	if(status){
		 $.ajax({  
				url : "CheckPeriods.htm", 
		        data: "periodId=" + periodId,
		        success : function(response) { 
		        	if(response == '1'){
		        		if(confirm("Classes are scheduled for this period. Do you want to remove those schedules from the school?",function(stat){
		        			if(stat){
		        				deleteClassSchedule(periodId);
		        			}else{
		        				return false;
		        			}
		        		})); 
		            }else{
		                deletePeriods(periodId);
		            }
		       }  
		    }); 
		}
	else{
		$('#del'+index).attr('checked', false);

	}
	})); 
}

function deletePeriods(periodId)
{
	 $.ajax({  
			url : "DeletePeriods.htm", 
	        data: "periodId=" + periodId,
	        success : function(response) {
	        	 $("#getperiod").html(response); 
	        	 systemMessage("Deleted Successfully !!");
	        }
	 });
}
function deleteClassSchedule(periodId)
{
	 $.ajax({  
			url : "DeleteClassSchedulePeriods.htm", 
	        data: "periodId=" + periodId,
	        success : function(response) { 
	        	 $("#getperiod").html(response); 
	        	 systemMessage("Deleted Successfully !!");
	        }
	 });
}
//------------end Edit Periods-------------//


//--------------Start Add school Info-------------//
function AddSchoolInfo()
{
	
	var schoolId=$('#schoolId').val();
	
	var schoolName=$('#schoolName').val();
	
	 var letterNumber = /^[0-9a-zA-Z, ]+$/; 
	 	
	if(schoolName== '' || schoolName==null || schoolName==0){
		systemMessage("Please enter the school Name");
		$('#schoolName').next().show();
		$('#schoolName').focus();
		return false;
	}else  if(!(letterNumber.test(schoolName)))   
	{ 
		systemMessage("Special characters are not allowed");
		$('#schoolName').next().show();
		$('#schoolName').focus();
		return false;
	} 
	var schoolAbbr=$('#schoolAbbr').val();
	
	if(schoolAbbr== '' || schoolAbbr==null || schoolAbbr==0){
		systemMessage("Please enter the School Abbreviation");
		$('#schoolAbbr').next().show();
		$('#schoolAbbr').focus();
		return false;
	}else if(!(letterNumber.test(schoolAbbr)))   
	{ 
		systemMessage("Special characters are not allowed");
		$('#schoolAbbr').next().show();
		$('#schoolAbbr').focus();
		return false;
	} 
	
	 var noOfStudents=$('#noOfStudents').val().trim();
	 
	 if(noOfStudents== '' || noOfStudents==null){
		 systemMessage("Please enter the no Of Students");
         $('#noOfStudents').next().show();
         $('#noOfStudents').focus();
         return false;
      }else if(!$.isNumeric(noOfStudents))
      {
    	  systemMessage("please enter the numeric character");
    	  $('#noOfStudents').val('');
    	  $('#noOfStudents').focus();
    	  return false;
      }
      else if(noOfStudents.length > 10)
      {
    	  systemMessage("Only 10 digits allowed");
    	  $('#noOfStudents').val('');
    	  $('#noOfStudents').focus();
    	  return false;
      }
	
	 var countryId=$('#countryId').val();
	 if(countryId== '' || countryId==null || countryId=='select'){
		 systemMessage("Please select the country");
		 $('#countryId').next().show();
         $('#countryId').focus();
         return false;
      }
	 var stateId=$('#stateId').val();
	 if(stateId== '' || stateId==null || stateId=='select'){
		 systemMessage("Please select the state");
         $('#stateId').next().show();
         $('#stateId').focus();
         return false;
      }
	 var city=$('#city').val();
	 if(city== '' || city==null || city==0){
		 systemMessage("Please enter the city name");
         $('#city').next().show();
         $('#city').focus();
         return false;
      }else if(!(letterNumber.test(city)))   
		{ 
    	  systemMessage("Special characters are not allowed");
    	  $('#city').next().show();
    	  $('#city').focus();
		  return false;
		} 
	 var schoolTypeId=$('#schoolTypeId').val();
	  if(schoolTypeId== '' || schoolTypeId==null || schoolTypeId=='select')
	   	{
		  systemMessage("Please select the school Type Id");
		  $('#schoolTypeId').next().show();
		  $('#schoolTypeId').focus();
		  return false;
	   	}
	 var schoolLevelId=$('#schoolLevelId').val();
	 if(schoolLevelId== '' || schoolLevelId==null || schoolLevelId=='select')
	 {
		 systemMessage("Please select the school level Id");
		 $('#schoolLevelId').next().show();
		 $('#schoolLevelId').focus();
		 return false;
	 }
		
	 var phoneNumber= $('#phoneNumber').val().trim();
	 	// intRegex = /[0-9 -()+]+$/; 
	   
	 intRegex = /^[0-9]+$/;
	   if(phoneNumber== '' || phoneNumber==null || phoneNumber==0){
		   systemMessage("Please enter the Phone number");
		   $('#phoneNumber').next().show();
		   $('#phoneNumber').focus();
		   return false;
       }else if((phoneNumber.length < 6) || (!intRegex.test(phoneNumber)))
       {
    	   systemMessage('Please enter a valid phone number.');
    	    $('#phoneNumber').focus();
    	    return false;
       }else if(phoneNumber.length > 10)
       {
    	   systemMessage('Phone number Only allowed 10 digits.');
    	   $('#phoneNumber').focus();
    	   return false;
       }
	  var faxNumber=$('#faxNumber').val().trim();
	  intRegex = /^[0-9]+$/; 
	    
	  if(faxNumber== '' || faxNumber==null || faxNumber==0){
		  systemMessage("Please enter the Fax number");
		   $('#faxNumber').next().show();
		   $('#faxNumber').focus();
		   return false;
	  }else if((faxNumber.length < 10) || (!intRegex.test(faxNumber)))
	  {
		  systemMessage('Please enter a valid Fax number.');
		  $('#faxNumber').focus();
		  return false;
	  }else if(faxNumber.length > 10)
	  {
		  systemMessage('Fax number Only allowed 10 digits.');
		  $('#faxNumber').focus();
		  $('#faxNumber').clear();
		  return false;
	  }
	  var districtId=0;
	  districtId=$('#districtId').val();
	  if(districtId == ""){
		  districtId = 0;
	  }
	  var promotStartDate = $('#promotStartDate').val();
	  var promotEndDate = $('#promotEndDate').val();
	  
	  $.ajax({
		  url : "SaveSchoolInfo.htm",
		  data : "schoolName=" + schoolName + "&schoolAbbr=" + schoolAbbr+
		  "&countryId=" + countryId +"&stateId=" + stateId +
		  "&city=" + city+"&phoneNumber=" + phoneNumber +
		  "&noOfStudents=" + noOfStudents+"&faxNumber=" + faxNumber+"&schoolId="+schoolId+
		  "&schoolLevelId="+schoolLevelId+"&schoolTypeId="+schoolTypeId+
		  "&promotStartDate=" + promotStartDate+"&promotEndDate=" + promotEndDate+"&districtId="+districtId,
		  success : function(data) {
			  systemMessage("School information updated successfully.");
		  }
	  });      
}
function getClasses(){
	var gradeId=$('#gradeId').val();
	 if(gradeId== '' || gradeId==null || gradeId=='select'){
		 $("#showclasses").html("");
	     document.getElementById('showSubmit').style.visibility = "hidden";	
		 alert("Please select the gradeName");
		 $('#gradeId').next().show();
         return false;
     }
	if(gradeId){ 
		$.ajax({  
			url : "GetClassses.htm", 
		    data: "gradeId="+gradeId,
		    success: function(response) {
		          $("#showclasses").html(response);
		          document.getElementById('showSubmit').style.visibility = "visible";	
		    }
		}); 
	}
}
function removeClass(classId,obj){
	var gradeId=$('#gradeId').val();
	 if(gradeId== '' || gradeId==null || gradeId=='select'){
		 alert("Please select the gradeName");
      $('#gradeId').next().show();
      return false;
   }
	var isChecked = $("#"+obj.id).is(':checked');
	if(!isChecked){
		if(confirm("Do you want to remove class for this grade?",function(status){
			if(status){
				$.ajax({  
					url : "RemoveClassses.htm", 
				    data: "gradeId="+gradeId+"&classId="+classId,
				    success: function(response) {
				    	if(response == 'Unable to remove the Class'){
				    		$("#"+obj.id)[0].checked = true;
				    	}
				    	alert(response);
				    }
				}); 	
			}else{
				  $("#"+obj.id).prop('checked', true); 
				  return false;
			}
		})); 
	}
}
function getAvailableTeachers() {
	var gradeClassId = $('#gradeClassId').val();
	$.ajax({
		type : "GET",
		url : "getAvailableTeachers.htm",
		data : "gradeClassId=" + gradeClassId,
		success : function(response) {
			var availableTeachers = response.availableTeachers;
			$("#teacherId").empty();
			$("#teacherId").append($("<option></option>").val("select").html('Select Teacher'));
			$.each(availableTeachers,function(index, value) {
				$("#teacherId").append($("<option></option>").val(value.teacher.teacherId)
						.html(value.teacher.userRegistration.title
						+ " " + value.teacher.userRegistration.firstName
						+ " " + value.teacher.userRegistration.lastName));
			});
		}
	});
}
function getAvailableTeacherSections() {
	var teacherId = $('#teacherId').val();
	var gradeClassId = $('#gradeClassId').val();
	$("#csId").empty();
	$("#csId").append(
			$("<option></option>").val("select").html(
					'Select Section'));
	$('#details').empty();
	$.ajax({
		type : "GET",
		url : "getAvailableTeacherSections.htm",
		data : "teacherId=" + teacherId + "&gradeClassId=" + gradeClassId,
		success : function(response) {
			var teacherSections = response.teacherSections;			
			$.each(teacherSections, function(index, value) {
				$("#csId").append(
						$("<option></option>").val(value.csId).html(
								value.section.section));
			});
		}
	});
}

function getScheduledClasses() {
	var gradeClassId = $('#gradeClassId').val();
	$.ajax({
		type : "GET",
		url : "getScheduledClasses.htm",
		data : "gradeClassId=" + gradeClassId,
		success : function(response) {
			var scheduledClasses = response.scheduledClasses;
			$("#csId").empty();
			$("#csId").append(
					$("<option></option>").val("select").html(
							'Select Section'));
			$.each(scheduledClasses, function(index, value) {
				$("#csId").append(
						$("<option></option>").val(value.csId).html(
								value.section.section));
			});
		}
	});
}

function getClassesNoHomeroom(index) {
	var userType = $('#userType').val();
	var gradeId = 0;
	if(userType == 'parent'){
		gradeId = $('#gradeId' + index.value).val();
	}
	else{
		gradeId = $('#gradeId').val();
	}
	$("#gradeClassId").empty();
	$("#gradeClassId").append(
			$("<option></option>").val("select").html(
					'Select Class'));
	if(gradeId>0){
		$.ajax({
			type : "GET",
			url : "getClassesByGrade.htm",
			data : "gradeId=" + gradeId,
			success : function(response) {
				var availableClasses = response.availableClasses;				
				$.each(availableClasses, function(index, value) {
					$("#gradeClassId").append(
							$("<option></option>").val(value.gradeClassId)
									.html(value.studentClass.className));
				});
	
			}
		});
	}
}

function getScheduledClassesByGradeNClass() {
	var gradeId = $('#gradeId').val();
	var classId = $('#classId').val();
	$.ajax({
		type : "GET",
		url : "getScheduledClasses.htm",
		data : "gradeClassId=" + gradeClassId,
		success : function(response) {
			var scheduledClasses = response.scheduledClasses;
			$("#csId").empty();
			$("#csId").append(
					$("<option></option>").val("select").html(
							'Select Section'));
			$.each(scheduledClasses, function(index, value) {
				$("#csId").append(
						$("<option></option>").val(value.csId).html(
								value.section.section));
			});
		}
	});
}

function studentTab(){
	$("#student").removeClass('button').addClass('buttonSelect');
	$("#teacher").removeClass('buttonSelect').addClass('button');
	$("#delete").removeClass('buttonSelect').addClass('button');
	$("#parent").removeClass('buttonSelect').addClass('button');
	document.getElementById("teadis").style.visibility='hidden';
	document.getElementById("deldis").style.visibility='hidden';
	document.getElementById("pardis").style.visibility='hidden';
	document.getElementById("studis").style.visibility='visible';
	document.adminAddUserForm.reset();
	$('input:radio[name=tab-group-1]')[3].checked = true;
}

function teacherTab(){
	$("#teacher").removeClass('button').addClass('buttonSelect');
	$("#student").removeClass('buttonSelect').addClass('button');
	$("#delete").removeClass('buttonSelect').addClass('button');
	$("#parent").removeClass('buttonSelect').addClass('button');
	document.getElementById("studis").style.visibility='hidden';
	document.getElementById("deldis").style.visibility='hidden';
	document.getElementById("pardis").style.visibility='hidden';
	document.getElementById("teadis").style.visibility='visible';
	document.adminAddTeacherForm.reset();
	$('input:radio[name=tab-group-1]')[2].checked = true;
}

function deleteTab(){
	$("#delete").removeClass('button').addClass('buttonSelect');
	$("#teacher").removeClass('buttonSelect').addClass('button');
	$("#student").removeClass('buttonSelect').addClass('button');
	$("#parent").removeClass('buttonSelect').addClass('button');
	document.getElementById("studis").style.visibility='hidden';
	document.getElementById("teadis").style.visibility='hidden';
	document.getElementById("pardis").style.visibility='hidden';
	document.getElementById("deldis").style.visibility='visible';
	document.adminDeleteUserForm.reset();
	$('input:radio[name=tab-group-1]')[0].checked = true;
}
function parentTab(){
	$("#parent").removeClass('button').addClass('buttonSelect');
	$("#student").removeClass('buttonSelect').addClass('button');
	$("#delete").removeClass('buttonSelect').addClass('button');
	$("#teacher").removeClass('buttonSelect').addClass('button');
	document.getElementById("studis").style.visibility='hidden';
	document.getElementById("deldis").style.visibility='hidden';
	document.getElementById("teadis").style.visibility='hidden';
	document.getElementById("pardis").style.visibility='visible';
	document.adminAddParentForm.reset();
	$('input:radio[name=tab-group-1]')[1].checked = true;
}
/*      Add Parent to Existing Student           */

function adminAddParent() {
	var pemailId = $('#parent2emailId').val();
	if (pemailId == '' || pemailId == null) {
		systemMessage("Please enter the parent email address");
		$('#parentemailId').next().show();
		return false;
	} else if (IsEmail(pemailId) == false) {
		systemMessage("Please enter valid email address");
		$('#emailId').show();
		return false;
	}
	var semailId = $('#studentemailId').val();
	if (semailId == '' || semailId == null) {
		systemMessage("Please enter the student email address");
		$('#studentemailId').next().show();
		return false;
	} else if (IsEmail(semailId) == false) {
		systemMessage("Please enter valid email address");
		$('#invalid_email').show();
		return false;
	}
	
	$.ajax({
		url : "AdminAddParent.htm",
		data : "pemailId=" + pemailId + "&semailId=" + semailId,
		success : function(data) {
			systemMessage(data);
			$('#parent2emailId').val('');
			$('#studentemailId').val('');
		}
	});

}

function getAvailableClasses(index) {
	var userType = $('#userType').val();
	var gradeId = 0;
	if(userType == 'parent'){
		gradeId = $('#gradeId' + index.value).val();
	}
	else{
		gradeId = $('#gradeId').val();
	}
	$.ajax({
		type : "GET",
		url : "getAvailableClasses.htm",
		data : "gradeId=" + gradeId,
		success : function(response) {
			var availableClasses = response.availableClasses;
			$("#gradeClassId").empty();
			$("#gradeClassId").append(
					$("<option></option>").val("select").html(
							'Select Class'));
			$.each(availableClasses, function(index, value) {
				$("#gradeClassId").append(
						$("<option></option>").val(value.gradeClassId)
								.html(value.studentClass.className));
			});

		}
	});
	
}

