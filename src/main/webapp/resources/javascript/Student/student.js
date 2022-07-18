function above13StudentRegVal()
{
	 var firstname=$('#studentFirstName').val();
	 if(firstname== '' || firstname==null){
		 alert("Please enter the first name");
         $('#studentFirstName').next().show();
         return false;
      }
	 var lastname=$('#studentLastName').val();
	 if(lastname== '' || lastname==null){
		 alert("Please enter the last name");
         $('#studentLastName').next().show();
         return false;
      }
	 var address1=$('#studentAddress').val();
	 if(address1== '' || address1==null){
		 alert("Please enter the address1");
         $('#studentAddress').next().show();
         return false;
      }
	 var address2=$('#address2').val();
//	  if(address2== '' || address2==null){
//		 alert("Please enter the address2");
//         $('#address2').next().show();
//         return false;
//      }
	 var countryId=$('#countryId').val();
	  if(countryId== '' || countryId==null || countryId=='select'){
		 alert("Please select a country");
         $('#countryId').next().show();
         return false;
      }
	 var stateId=$('#stateId').val();
	 if(stateId== '' || stateId==null || stateId=='select'){
		 alert("Please select a state");
         $('#stateId').next().show();
         return false;
      }
	 var city=$('#studentCity').val();
	 if(city== '' || city==null){
		 alert("Please enter a city name");
         $('#studentCity').next().show();
         return false;
      }
	 var parentZipcode=$('#studentZipCode').val();
	  if(parentZipcode== '' || parentZipcode==null){
		 alert("Please enter the zipcode");
         $('#studentZipCode').next().show();
         return false;
      }else if(!$.isNumeric(parentZipcode))
       {
		 alert("please enter the numeric character");
		 $('#studentZipCode').next().show();
		 return false;
	   }
	   var fldlen=parentZipcode.legth;
	   if(fldlen<5 || fldlen>5)
	    {
	    	message = "Zipcode length must be 5 numeric characters";
	        alert(message);
	        $('#studentZipCode').val('');
	        $('#studentZipCode').next().show();
	        return false;
	    }
	  var gradeId=$('#gradeId').val();
		 if(gradeId== '' || gradeId==null || gradeId=='select'){
			 alert("Please select a grade");
	         $('#gradeId').next().show();
	         return false;
	      }
	  
	 var semailId=$('#studentEmail').val();
	 if(semailId== '' || semailId==null || semailId=='select'){
		 alert("Please select a emailId");
         $('#studentEmail').next().show();
         return false;
      } else if(IsEmail(semailId)==false){
    	  alert("Please enter valid email address");
          $('#studentEmail').show();
          return false;
      }
	 
	 var studentPassword=$('#studentPassword').val();
	  if(studentPassword== '' || studentPassword==null){
		 alert("Please enter the password");
         $('#studentPassword').next().show();
         return false;
      }
	 var studentConfirmPassword=$('#studentConfirmPassword').val();
	 if(studentConfirmPassword== '' || studentConfirmPassword==null){
		 alert("Please enter the confirm password");
		 $('#studentPassword').next().show();
         return false;
      }
	 
	 if(studentPassword!=studentConfirmPassword)
		 {
		 var message="Password and confirm-password are not same";
	     alert(message);
	     $('#studentConfirmPassword').val('');
		  $('#studentPassword').val('');
	     $('#studentPassword').next().show();
	     return false;
		 }
	
	 var studentsecurityid=$('#studentsecurityid').val();
	 if(studentsecurityid== '' || studentsecurityid==null || studentsecurityid=='0'){
		 alert("Please select a security question");
         $('#studentsecurityid').next().show();
         return false;
      }
	 var securityanswer=$('#studentSecurityAnswer').val();
	 if(securityanswer== '' || securityanswer==null){
		 alert("Please enter a security answer");
         $('#studentSecurityAnswer').next().show();
         return false;
      }
    	  
	 $.ajax({  
			url : "SaveStudentDetails1.htm", 
	        data: "firstname="+firstname+"&lastname="+lastname+"&address1="+address1+"&address2="+address2+"&countryId="+countryId+"&stateId="+stateId+"&city="+city+"&studentZipcode="+parentZipcode+"&semailId="+semailId+"&studentPassword="+studentPassword+"&studentConfirmPassword="+
	        studentConfirmPassword+"&studentsecurityid="+studentsecurityid+"&securityanswer="+securityanswer+"&gradeId="+gradeId,
	        success : function(data) { 
	        	window.location.replace(data);
	        	
	            
	        }  
	    }); 
}
function above13StudentRegVal2()
{   
	
	var gender = $('input[name="gender"]:checked').val();

	if (gender == '' || gender == null) {
		alert("Please select the gender");
		$('#gender').next().show();
		return false;
	}
	 var month=$('#month').val();
	 if(month== '' || month==null || month=='select'){
		 alert("Please select the month");
         $('#month').next().show();
         return false;
      }
	 var year=$('#year').val();
	 if(year== '' || year==null || year=='select'){
		 alert("Please select the year");
         $('#year').next().show();
         return false;
      }
	 var date=$('#date').val();
	 if(date=="" || date=='select'){
	 alert("Please select the date");
        $('#date').next().show();
        return false;
      }
    date = date.replace(/^\s+|\s+$/g,'');
    month = month.replace(/^\s+|\s+$/g,'');
    year = year.replace(/^\s+|\s+$/g,'');
    
   
    
     var values = [];
     $("input[name*='classId']").each(function () {
           if (jQuery(this).is(":checked")) {
        	 values.push($(this).val());
         }
     });     
     
     var other1=$('#textother').val();
     var other2=$('#textother2').val();
     var other3=$('#textother3').val();
     var other4=$('#textother4').val();

     if(values.length <= 0){
    	 alert("Please check subjects");
         return false;
     }
	   /*  $.ajax({  
				url : "SaveStudentDetails2.htm",
				type: "POST",
				data: "gender="+gender+"&date="+date+"&month="+month+"&year="+year+"&classIds="+values+"&other1="+other1+"&other2="+other2+"&other3="+other3+"&other4="+other4,
				success : function(data) { 
		        	window.location.replace(data);
		        }  
		 });
     }else{
    	 alert("Please Check the Subject");
         return false;
     }*/

}


	function above13StudentRegVal3() {
		var gradeLevels = $("[name=gradeLevels]");
		var classIds = $("[name=classIds]");
		
		 var gradelevels = [];
		 var classidss= [];
		for (var i = 0; i < classIds.length; i++) {
			
			gradelevels.push(gradeLevels[i].value);
			classidss.push(classIds[i].value);
			if (gradeLevels[i].value == 'select') {
				systemMessage('Select a grade level ');
				$("#gradeLevels" + i).focus();
				return false;
			} 
		}
		return true;
				
	}
	
	

function IsEmail(email) {
    var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if(!regex.test(email)) {
       return false;
    }else{
       return true;
    }
  }

function loadClasses() {
	var selected = dwr.util.getValue('gradeId');
	 $("#details").hide();
	var loadClassesCallBack = function(list) {
		if (list != null) {
			dwr.util.removeAllOptions('classId');
			dwr.util.addOptions('classId', ["select"]);
			dwr.util.addOptions('classId', list, 'classId', 'className');
			
			dwr.util.removeAllOptions('teacherId');
			dwr.util.addOptions('teacherId', ["select"]);
			
			dwr.util.removeAllOptions('sectionId');
			dwr.util.addOptions('sectionId', ["select"]);

		}else{
			dwr.util.removeAllOptions('classId');
			dwr.util.addOptions('classId', ["select"]);
			
			dwr.util.removeAllOptions('teacherId');
			dwr.util.addOptions('teacherId', ["select"]);
			
			dwr.util.removeAllOptions('sectionId');
			dwr.util.addOptions('sectionId', ["select"]);
		}
	}
	if(selected != 'select'){
		teacherSchedulerService.getStudentClasses(selected, {
			callback : loadClassesCallBack
		});
	}else{
		dwr.util.removeAllOptions('classId');
		dwr.util.addOptions('classId', ["select"]);
		
		dwr.util.removeAllOptions('teacherId');
		dwr.util.addOptions('teacherId', ["select"]);
		
		dwr.util.removeAllOptions('sectionId');
		dwr.util.addOptions('sectionId', ["select"]);
		return false;
	}
}
function loadTeachers() {
	var gradeId = dwr.util.getValue('gradeId');
	var classId = dwr.util.getValue('classId');
	$("#details").hide();
	var formatter = function(entry) {
		var fullName = entry.userRegistration.title + " " +entry.userRegistration.firstName+ " " +entry.userRegistration.lastName;
		return fullName;
	}
	var loadTeachersCallBack = function(list) {
		if (list != null) {
			dwr.util.removeAllOptions('teacherId');
			dwr.util.addOptions('teacherId', ["select"]);
			dwr.util.addOptions('teacherId', list,'teacherId', formatter);
		} else {
			dwr.util.removeAllOptions('teacherId');
			dwr.util.addOptions('teacherId', ["select"]);
			
			dwr.util.removeAllOptions('sectionId');
			dwr.util.addOptions('sectionId', ["select"]);
		}
		studentService.getGradeClassId(gradeId, classId,{
			callback : gradeClassIdCallBack
		});
	}
	var gradeClassIdCallBack = function(gradeClassId){
		dwr.util.setValue('gradeClassId', gradeClassId);
	}
	if(classId != 'select'){
		teacherSchedulerService.getTeachers(gradeId,classId, {
			callback : loadTeachersCallBack
		})
	}else{
		dwr.util.removeAllOptions('teacherId');
		dwr.util.addOptions('teacherId', ["select"]);
		
		dwr.util.removeAllOptions('sectionId');
		dwr.util.addOptions('sectionId', ["select"]);
		return false;
	}
	
}
function getTeacherSections() {
	var classId = dwr.util.getValue('classId');
	var teacherId = dwr.util.getValue('teacherId');
	var studentId = dwr.util.getValue('studentId');
	var gradeClassId = dwr.util.getValue('gradeClassId');
	var gradeId = dwr.util.getValue('gradeId');
	var divId = dwr.util.getValue('divId');
	$("#details").hide();
	var section = function(list) {
		return list.classStatus.section.section;
	}
	var sectionId = function(list) {
		return list.classStatus.section.sectionId;
	}
	var teacherSectionsCallBack = function(list) {
		dwr.util.removeAllOptions('sectionId');
		dwr.util.addOptions('sectionId', ["select"]);
		if (list != null) {
			dwr.util.addOptions('sectionId',list,sectionId,section);
		} else {
			alert("No data found");
		}
		 var a = new Array();
	    $("#sectionId").children("option").each(function(x){
	            duplicate = false;
	            b = a[x] = $(this).val();
	            for (i=0;i<a.length-1;i++){
	                    if (b ==a[i]) duplicate =true;
	            }
	            if (duplicate) $(this).remove();
	    })
	}
	if(gradeId != 'select' && classId != 'select' && teacherId != 'select'){
		studentService.getSectionsForRegistration(teacherId,studentId,gradeClassId,{
			callback : teacherSectionsCallBack
		});
	} else {
		$("#details").hide();
		dwr.util.removeAllOptions('sectionId');
		dwr.util.addOptions('sectionId', ["select"]);
		return false;
	}
}

function getDetailsOfSections(){
	var divId = dwr.util.getValue('divId');
	var gradeId = dwr.util.getValue('gradeId');
	var classId = dwr.util.getValue('classId');
	var teacherId = dwr.util.getValue('teacherId');
	var sectionId = dwr.util.getValue('sectionId');
	$("#loading-div-background").show();
	if(teacherId != "select" && sectionId != "select" ){
		$.ajax({  
			type : "GET",
			url : "getDetailsOfSections.htm", 
		    data: "gradeId="+gradeId+"&classId="+classId+"&teacherId="+teacherId+"&sectionId="+sectionId,
		    success : function(data) {
		    	$("#loading-div-background").hide();
		    	document.open();
			    document.write(data);
			    document.close();

			}
		   
		}); 
	}else{
		$("#details").hide();
	}
}

function setStatusForClassRegistration(obj){
	var id = obj.id;
	var status;
	var classStatus = "alive";
	if(id == "sendRequest"){
		status = "waiting";
	}else if(id == "cancelRequest"){
		status = "cancelled";
	}
	var csId = dwr.util.getValue('csId');
	var gradeLevelId = dwr.util.getValue('gradeLevelId');
	var gradeClassId = dwr.util.getValue('gradeClassId');
	var studentId = dwr.util.getValue('studentId');
	var sectionId = dwr.util.getValue('sectionId');
	$.ajax({  
		type : "GET",
		url : "setStatusForClassRegistration.htm", 
		data: "studentId="+studentId+"&sectionId="+sectionId+"&csId="+csId+"&status="+status+"&classStatus="+classStatus+"&gradeLevelId="+gradeLevelId+"&gradeClassId="+gradeClassId,
	    success : function(response) {	    	
	    	alert(response);
		}  
	});
}

