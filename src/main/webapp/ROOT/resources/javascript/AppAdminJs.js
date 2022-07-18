/**
 * 
 */
// --------------Start Add District----------------//
function SaveDistrict()
{
	var districtId=0;
	districtId=$('#districtId').val();
	
	var districtName=$('#districtName').val();
	if(districtName== '' || districtName==null){
		 alert("Please enter the district Name");
         $('#districtName').next().show();
         return false;
      }
	 var noSchools=$('#noSchools').val();
	 if(noSchools== '' || noSchools==null){
		 alert("Please enter the no of Schools");
         $('#noSchools').next().show();
         return false;
      }else if(!$.isNumeric(noSchools))
      {
 		 alert("please enter the numeric character");
 		 $('#noSchools').val('');
 		 return false;
 	   }
	
	 var address=$('#address').val();
	 if(address== '' || address==null){
		 alert("Please enter the address");
         $('#address').next().show();
         return false;
      }
	
	 var countryId=$('#countryId').val();
	  if(countryId== '' || countryId==null || countryId=='select'){
		 alert("Please select the country");
         $('#countryId').next().show();
         return false;
      }
	 var stateId=$('#stateId').val();
	 alert(stateId);
	 if(stateId== '' || stateId==null || stateId=='select'){
		 alert("Please select the state");
         $('#stateId').next().show();
         return false;
      }
	 var city=$('#city').val();
	 if(city== '' || city==null){
		 alert("Please enter the city name");
         $('#city').next().show();
         return false;
      }
		
	 var phoneNumber=$('#phoneNumber').val();
	 intRegex = /[0-9 -()+]+$/;
	 if(phoneNumber== '' || phoneNumber==null){
		 alert("Please enter the parentphoneno");
         $('#phoneNumber').next().show();
         return false;
      }else if((phoneNumber.length < 6) || (!intRegex.test(phoneNumber)))
    	  {
    	       alert('Please enter a valid phone number.');
    	       return false;
    	  }
	 var faxNumber=$('#faxNumber').val();
	 intRegex = /[0-9 -()+]+$/;
	 if(faxNumber== '' || faxNumber==null){
		 alert("Please enter the parentphoneno");
         $('#faxNumber').next().show();
         return false;
      }else if((faxNumber.length < 10) || (!intRegex.test(faxNumber)))
    	  {
    	       alert('Please enter a valid Fax number.');
    	       return false;
    	  }
	 
	 $.ajax({
			url : "SaveDistrict.htm",
			data : "districtId="+districtId+"&districtName=" + districtName + "&noSchools=" + noSchools
					+ "&address=" + address+"&countryId=" + countryId +"&stateId=" + stateId +
					"&city=" + city+"&phoneNumber=" + phoneNumber +"&faxNumber=" + faxNumber,
			success : function(data) {
				alert(data);
				$('#districtName').val('');
				$('#noSchools').val('');
				$('#address').val('');
				$('#city').val('');
				$('#phoneNumber').val('');
				$('#faxNumber').val('');
				$("countryId").val('select'); 
				$("stateId").val('select'); 
			}
		});
      
}
function deleteDistrict(districtId){
	if(confirm("Do you really want to delete?",function(status){
		if(status){            
    	 $.ajax({
 			url : "deleteDistrict.htm",
 			data : "districtId="+districtId,
 			success : function(data) {
 				alert(data);
 				window.location.replace("displayDistricts.htm");
 			}
 		});
		}
	}));
}

function SaveDistrictAdmin()
{
    var districtId=$('#districtId').val();
	var emailId  = $('#emailId').val();
	if(districtId== '' || districtId==null || districtId=='select'){
		 alert("Please select the district");
        $('#districtId').next().show();
        return false;
     }
    if (emailId == '' || emailId == null) {
		alert("Please enter the email address");
		$('#emailId').next().show();
		return false;
	}
    if (IsEmail(emailId) == false) {
		alert("Please enter valid email address");
		$('#emailId').show();
		return false;
	}
     $.ajax({
		url : "SaveDistrictAdmin.htm",
		data : "districtId="+districtId+"&emailId=" + emailId,
		success : function(data) {
			alert(data);
			$('#emailId').val('');
			$("#districtId").val('select'); 
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
function deleteSchoolAdmin(regId){
	if(confirm("Do you really want to delete?",function(status){
		if(status){      
	    	 $.ajax({
	 			url : "deleteUserRegistration.htm",
	 			data : "regId="+regId,
	 			success : function(data) {
	 				alert(data);
	 				window.location.replace("displayUserRegistrations.htm");
	 			}
	 		});
	    } 
	}));
}
function deleteDistrictAdmin(regId){
	if(confirm("Do you really want to delete?",function(status){
		if(status){      	    	 $.ajax({
	 			url : "deleteDistrictUserRegistration.htm",
	 			data : "regId="+regId,
	 			success : function(data) {
	 				alert(data);
	 				window.location.replace("displayDistrictUsers.htm");
	 			}
	 		});
		} 
	}));
}
	 
