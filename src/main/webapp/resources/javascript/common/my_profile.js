/**
 * 
 */
var status = true;
var isValid = false;

function validateForm(formId){
	status = true;
	var spanContent;
	$("#"+formId+" :input:not(:button, :hidden)").each(function (i,e) { 
		var field = dwr.util.getValue(e.id);
		 if($('#validationError_'+e.id).length)
		 spanContent = dwr.util.getValue('validationError_'+e.id);
		if(spanContent &&  isValid == true){
			removeValidationError("validationError_"+e.id);
		}
		if(e.required){
			if($('#validationError_'+e.id).length){
				spanContent = dwr.util.getValue('validationError_'+e.id);
				if(spanContent){
					removeValidationError("validationError_"+e.id);
				}
			}
			if($("#"+e.id).get(0).tagName == 'SELECT' && field == 'select'){
				$("#"+e.id).after("<span id='validationError_"+e.id+"'><font color='red' size='2'> * Mandatory field </font></span>");
				 isValid = false;
				 status = false;
			}
			if(!field){
				$("#"+e.id).after("<span id='validationError_"+e.id+"'><font color='red' size='2'> * Mandatory field </font></span>");
				isValid = false;
				status = false;
			}
			if(e.id == "firstName" || e.id == "lastName"){
				var stat = checkForNonAlphabates(e.id);
				if(!stat){
					isValid = false;
					status = false;
				}
			}
			if(e.id == "zipcode"){
				var stat = checkForAlphabates(e.id);
				if(!stat){
					isValid = false;
					status = false;
				}
			}
		}
		if(e.id == "phonenumber"){
			var stat = checkPhoneNumber(e.id);
			if(!stat){
				isValid = false;
				status = false;
			}
		}
	});
	if(status){
		return status;
	}else{
		return status;	
	}
}
function checkForNonAlphabates(id){
	var regex = /^[a-zA-Z ]*$/;
	 if($('#validationError_'+id).length)
		var  spanContent = dwr.util.getValue('validationError_'+id);
		if(spanContent){
			removeValidationError("<span id='validationError_"+id+"'><font color='red' size='2'> * Mandatory Fields </font></span>");
		}
   if (!regex.test($("#"+id).val())) {
       $("#"+id).after("<span id='validationError_"+id+"'><font color='red' size='2'> * Please enter Alphabates only </font></span>");
       return false;
   }else{
	   return true;
   }
}
function checkForAlphabates(id){
	var regex =/^\(?([0-9]{5})\)?$/;
	 if($('#validationError_'+id).length)
		var  spanContent = dwr.util.getValue('validationError_'+id);
		if(spanContent){
			removeValidationError("<span id='validationError_"+id+"'><font color='red' size='2'> * Mandatory Fields </font></span>");
		}
   if (!regex.test($("#"+id).val())) {
       $("#"+id).after("<span id='validationError_"+id+"'><font color='red' size='2'> * Please enter only numbers for zipcode </font></span>");
       return false;
   }else{
	   return true;
   }
}
function checkPhoneNumber(id){
	 var regex = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
	 if($('#validationError_'+id).length)
			var  spanContent = dwr.util.getValue('validationError_'+id);
			if(spanContent){
				removeValidationError("validationError_"+id);
			}
	 if (!regex.test($("#"+id).val())) {
	       $("#"+id).after("<span id='validationError_"+id+"'><font color='red' size='2'> * Please enter valid phone number</font></span>");
	       return false;
	   }else{
		   return true;
	   }
}
function removeValidationError(id){
	if($('#'+id).length)
		$("span[id^="+id+"]").remove();
}

function updatePersonalInfo(){
	$('input, textarea').each(function(){
	    $(this).val(jQuery.trim($(this).val()));
	});
	var form = $("#personalInfo").serialize();
	status = validateForm('personalInfo');
	if(status == 'true'){
	  	$.ajax({
		     type: "GET",
		     url:"updatePersonalInfo.htm",
		     data: form,
		     success : function(data){
		    	 systemMessage(data);
	   	     },
		   	 error: function (){
		   		systemMessage(data,"error");
		   	 }
	  	})
	}else{
		return false;
	}
}
function checkUsername(){
	var emailId = dwr.util.getValue('emailId');
	var currentUserName = dwr.util.getValue('currentUserName');
	removeValidationError('validationError_emailId');
	
	if(emailId)
		status = emailVal(emailId);
	else
		return false;
	
	if(!status){
		if(currentUserName != emailId){
			$("#emailId").after("<span id='validationError_emailId'><font color='red' size='2'>  * Doesn't match with current User Name !! </font></span>");
			 isValid = false;
			return false;
		}
	}else{
		$("#emailId").after("<span id='validationError_emailId'><font color='red' size='2'>   * Please enter valid email address!! </font></span>");
		 isValid = false;
		return false;
	}
	isValid = true;
	if(isValid && $('#validationError_emailId').length){
		var spanContent = dwr.util.getValue('validationError_emailId');
		if(spanContent){
			removeValidationError('validationError_emailId');
		}
	}
}

function userNameValidation(obj){
	var userName = dwr.util.getValue(obj.id);
	var currentUserName = dwr.util.getValue('currentUserName');
	removeValidationError("validationError_"+obj.id);
	if(userName){
		  status =  emailVal(userName)
		  if(status){
			  $("#"+obj.id).after("<span id='validationError_"+obj.id+"'><font color='red' size='2'>  * Please enter valid email address!! </font></span>");
			  isValid = false;
			  return false;
		  }
	  }else{
		  return false;
	  }
	if(currentUserName == userName){
		 $("#"+obj.id).after("<span id='validationError_"+obj.id+"'><font color='red' size='2'>   * New UserName should be differ with current UserName!! </font></span>");
		 isValid = false;
		return false;
	}
	if(obj.id == 'reEnteredUserName'){
		var newUserName = dwr.util.getValue('newUserName');
		if((userName && newUserName) && (userName != newUserName )){
			 $("#"+obj.id).after("<span id='validationError_"+obj.id+"'><font color='red' size='2'>   * New and Re-entered UserNames not matched!! </font></span>");
			 isValid = false;
			 return false;
		}
	}
	if(obj.id == 'newUserName'){
		var reEnteredUserName = dwr.util.getValue('reEnteredUserName');
		if((userName && reEnteredUserName)&& (userName != reEnteredUserName)){
			 $("#"+obj.id).after("<span id='validationError_"+obj.id+"'><font color='red' size='2'>  * New and Re-entered UserNames not matched!! </font></span>");
			 isValid = false;
			 return false;
		}
	}
	isValid = true;
	if(isValid && ( $('#validationError_newUserName').length || $('#validationError_reEnteredUserName').length)){
		isValid = true;
		if(isValid){
			removeValidationError("validationError_newUserName");
			removeValidationError("validationError_reEnteredUserName");
		}
	}
}

function updateUserName(){
	var form = $("#changeUserName").serialize();
	status  = validateForm('changeUserName');
	if(status == 'true' && isValid == true){
	  	$.ajax({
		     type: "GET",
		     url: "updateUserName.htm",
		     data: form,
		     success : function(data){
		    	dwr.util.setValue("currentUserName",dwr.util.getValue("newUserName"));
		    	dwr.util.setValue("emailId",'');
		    	dwr.util.setValue("newUserName",'');
		    	dwr.util.setValue("reEnteredUserName",'');
		    	systemMessage(data);
	   	     }
	   	 });
	}else{
		return false;
	}
}

function checkPassword(){
	  var password = dwr.util.getValue("password");
	  removeValidationError('validationError_password');
	  if(password)
			status =  passwordlength(password);
		else
			return false;
	  
	  if(!status){
		  var newPassword = CryptoJS.MD5(password);
		  var currentPassword = dwr.util.getValue("currentPassword");
		  if(newPassword != currentPassword){
			  $("#password").after("<span id='validationError_password'><font color='red' size='2'>  * Doesn't match with current Password !! </font></span>");
			  isValid = false;
			  return false;
		  }
		}else{
			 $("#password").after("<span id='validationError_password'><font color='red' size='2'> *  "+status+"!! </font></span>");
			 isValid = false;
			return false;
		}
	   isValid = true;
		if(isValid &&  $('#validationError_password').length ){
			var spanContent = dwr.util.getValue('validationError_password');
			if(spanContent){
				removeValidationError('validationError_password');
			}
		}
}

function passwordValidation(obj){
	
	var password = dwr.util.getValue(obj.id);
	var currentPassword = dwr.util.getValue("currentPassword");
	removeValidationError("validationError_"+obj.id);
	if(password){
		  status =  passwordlength(password)
		  if(status){
			  $("#"+obj.id).after("<span id='validationError_"+obj.id+"'><font color='red' size='2'>  * "+status+"!!  </font></span>");
			  return false;
		  }
	  }else{
		  return false;
	  }
		if(CryptoJS.MD5(password) == currentPassword){
			 $("#"+obj.id).after("<span id='validationError_"+obj.id+"'><font color='red' size='2'>  * Current and New passwords both are same!! </font></span>");
			 isValid = false;
			return false;
		}
		if(obj.id == 'reEnteredPassword'){
			var newPassword = dwr.util.getValue('newPassword');
			if((password && newPassword) && (password != newPassword )){
				 $("#"+obj.id).after("<span id='validationError_"+obj.id+"'><font color='red' size='2'>  * New and re-entered passwords must be same!! </font></span>");
				 isValid = false;
				 return false;
			}
		}
		if(obj.id == 'newPassword'){
			var reEnteredPassword = dwr.util.getValue('reEnteredPassword');
			if((password && reEnteredPassword)&& (password != reEnteredPassword)){
				 $("#"+obj.id).after("<span id='validationError_"+obj.id+"'><font color='red' size='2'>  * New and re-entered passwords must be same!! </font></span>");
				 isValid = false;
				 return false;
			}
		}
		isValid = true;
		if(isValid  && ( $('#validationError_newPassword').length || $('#validationError_reEnteredPassword').length )){
			removeValidationError("validationError_newPassword");
			removeValidationError("validationError_reEnteredPassword");
		}
}
function updatePassword(){
	var form = $("#changePassword").serialize();
	status  = validateForm('changePassword');
	if(status == 'true' && isValid == true){
	  	$.ajax({
		     type: "GET",
		     url: "updatePassword.htm",
		     data: form,
		     success : function(data){
		    	dwr.util.setValue("currentPassword",CryptoJS.MD5(dwr.util.getValue("newPassword")));
		    	dwr.util.setValue("password",'');
		    	dwr.util.setValue("newPassword",'');
		    	dwr.util.setValue("reEnteredPassword",'');
		    	systemMessage(data);
	   	     }
	   	 });
	}else{
		return false;
	}
}
function updateHomePage(){
   var form = $("#homePage").serialize();
  	$.ajax({
	     type: "GET",
	     url:"updateHomePage.htm",
	     data: form,
	     success : function(data){
	    	 systemMessage(data);
   	     }
   	 });
}

function updatePersonalInterest(){
	var userInterestArray = [];
    $.each($("input[name='userInterest']:checked"), function(){            
    	userInterestArray.push($(this).val());
    });
    $.ajax({
	     type: "GET",
	     url:"updatePersonalInterest.htm",
	     data: "userInterestArray="+userInterestArray,
	     success : function(data){
	    	 systemMessage(data);
  	     }
  	 });
}

function deleteGradeClass(gradeId,count,cnt){
	var userTypeId = dwr.util.getValue('userTypeId');
	var stdRegId = dwr.util.getValue('stdRegId');
	if(userTypeId == 4){
		url="studentEducationalInfo.htm?stdRegId="+stdRegId;
	}else{
		url="educationalInfo.htm";
	}	
	var deleteGradeClassCallBack = function(status){
		if(status){
			$( "#"+cnt).remove();
			systemMessage('Deleted sucessfully !!', 'error');
		}else{
			systemMessage('Failed to Delete !!', 'error');
		}
	}
	if(userTypeId == 3){
		 var teacherSubjectId = dwr.util.getValue('uniqueId'+gradeId+''+count);
		 var teacherId = dwr.util.getValue('userId');
		 if(teacherId && teacherSubjectId){
			 myProfileService.deleteTeacherGradeClass(teacherSubjectId,{
					callback : deleteGradeClassCallBack
				});
			} 
	}else if(userTypeId == 5 || userTypeId == 4){
		 var sectionId = dwr.util.getValue('uniqueId'+gradeId+''+count);
		 var gradeClassId = dwr.util.getValue('gradeClassId'+gradeId+''+count);
		 var studentId = dwr.util.getValue('userId');
		 if(sectionId && gradeClassId && studentId){
			myProfileService.deleteStudentGradeClass(studentId, sectionId, gradeClassId,{
				callback : deleteGradeClassCallBack
			});
		 } 
	}
}
function updateGradeClass(gradeId, count){
	var userTypeId = dwr.util.getValue('userTypeId');
	var stdRegId = dwr.util.getValue('stdRegId');
	var url = "";
	if(userTypeId == 4){
		url="studentEducationalInfo.htm?stdRegId="+stdRegId;
	}else{
		url="educationalInfo.htm";
	}	
	var updateGradeClassCallBack = function(status){
		if(status){
			systemMessage("Updated sucessfully !!");
		}else{
			systemMessage('Failed to Update !!');
		}
	}
	if(userTypeId == 3){
		var gradeLevelId = dwr.util.getValue('gradeLevelId'+gradeId+''+count);
		var noSectionsPerDay = dwr.util.getValue('noSectionsPerDay'+gradeId+''+count);
		var noSectionsPerWeek = dwr.util.getValue('noSectionsPerWeek'+gradeId+''+count);
		var teacherSubjectId = dwr.util.getValue('uniqueId'+gradeId+''+count);
		var teacherId = dwr.util.getValue('userId');
		var status = perDayAndWeekValidation('noSectionsPerDay'+gradeId+''+count,'noSectionsPerWeek'+gradeId+''+count)
		if(teacherId && teacherSubjectId && status == true){
			myProfileService.updateTeacherGradeClass(teacherSubjectId,gradeLevelId,noSectionsPerDay,noSectionsPerWeek,{
					callback : updateGradeClassCallBack
			});
		}else if(status == false){
			$('#noSectionsPerWeek'+gradeId+''+count).focus();
			return false;
		}
	}else if(userTypeId == 5 || userTypeId == 4){
		 var gradeLevelId = dwr.util.getValue('gradeLevelId'+gradeId+''+count);
		 var sectionId = dwr.util.getValue('uniqueId'+gradeId+''+count);
		 var gradeClassId = dwr.util.getValue('gradeClassId'+gradeId+''+count);
		 var studentId = dwr.util.getValue('userId');
		 if(sectionId && gradeClassId && studentId){
			myProfileService.updateStudentGradeClass(studentId, gradeClassId, gradeLevelId,{
				callback : updateGradeClassCallBack
			});
		 } 
	}
}

function addEducationalInformation(){
	var userId = dwr.util.getValue('userId');
	var userTypeId = dwr.util.getValue('userTypeId');
	var stdRegId = dwr.util.getValue('stdRegId');
	if(userTypeId == 4){
		url="studentEducationalInfo.htm?stdRegId="+stdRegId;
	}else{
		url="educationalInfo.htm";
	}
	$('#gradeClassesDiv').html('');
	$('#dynamicContentDiv').html('');
	jQuery.curCSS = jQuery.css;
	 $("#dialog").dialog({
			overflow: 'auto',
			dialogClass: 'no-close',
		    autoOpen: false,
			position: {my: "center", at: "center", of:window ,within: $("body") },
		    title: 'Add Educational Information',
		    draggable: true,
		    width : 1100,
		    height:700,
		    resizable : true,
		    modal : true,
		    close : function(event, ui) {
		    	if(url == "educationalInfo.htm"){
		    		$.ajax({
		    			type : "GET",
		    			url : url,
		    			success : function(response) {
		    				$("#contentDiv").empty();
		    				$("#contentDiv").append(response);   				
		    			    setFooterHeight();
		    			}
		    		});
		    	}else{
		    		window.location.href=url;
		    	}		    	
		     }
		});
	 var iframe = $('#iframe');
	 iframe.attr('src', "addEducationalInfo.htm?userId="+userId);
	 $("#dialog").dialog("open");
}
function loadSubjects(){
	var gradeId = dwr.util.getValue('gradeId');
	var userId = dwr.util.getValue('userId');
	var userTypeId = dwr.util.getValue('userTypeId');
	
	 var subjectCallBack = function (list) {
		var str = "<input type='hidden' id='userId' name='userId' value='"+userId+"'/><input type='hidden' id='gradeId' name='gradeId' value='"+gradeId+"'/><table width='60%' align='center' cellpadding='2' cellspacing='2'><tr>";
		var inc = 1;
		for (i=0;i<list.length;i++){
			if(list[i].className != 'Home Room'){
				if(inc == 5){
					inc = 0
					str +=  "</tr><tr>"; 
				}else{
					inc = inc+1;
				}
				str +=  "<td align='center'><input type='hidden' id='id"+i+"' value='"+list[i].classId+"' /><input type='checkbox' class='checkbox-design' id='"+list[i].classId+"' name='checkbox' value='"+list[i].className+"' onclick='addDynamicFields(this)' align='center'/><label for='"+list[i].classId+"' class='checkbox-label-design'></label> </td><td width='120' align='left' colSpan=2 class='header'><font color='black'>"+list[i].className+"</font></td>";
			}
		}
		str += "</tr>	<tr><td height=16 colSpan=3></td></tr></table>";
		$('#gradeClassesDiv').html(str);
		
		if(userTypeId == 3){
			$("#dynamicContentDiv").html("<table width='60%' align='center' border='0' cellpadding='4' cellspacing='4' class='Divheads tabtxt'> <tr><td width='140' align='left'> Class Name </td> <td width='200' align='left'>Grade Level</td> <td width='140' align='center'>Per Day</td> <td width='160' align='left' style='padding-left: 25px;'>Per Week</td><td width='40'> </td></tr><tr>");
		}else if(userTypeId == 5){
			$("#dynamicContentDiv").html("<table width='60%' align='center' style='left-padding: 8em;' border='0' cellpadding='4' cellspacing='4' class='Divheads tabtxt'> <tr><th width='80' align='left'><font size='3' color='663399'><u> Class Name </font></u></th> <th width='200' align='left'><font size='3' color='663399'><u> Grade Level </font></u></th><th width='40'> </th></tr><tr>");
		}
	 }
	if(gradeId != 'select'){
		adminService1.getStudentClasses(gradeId, {
			callback : subjectCallBack
		});
	}
}
function addDynamicFields(checkbox) {
	document.getElementById("submitDynamicContentDiv").style.visibility = "visible";
	if (checkbox.checked) {
		  var div = $("<div id='div"+checkbox.id+"' align='center'> <table width='60%' align='center' cellpadding='4' cellspacing='4' class='des'> <tr>"+getDynamicTextBox(checkbox)+"<td></td></tr></table></div>");
          $("#dynamicContentDiv").append(div);
	  }else{
		  removeDynamicFields(checkbox.id);
	  }
	  
}
function removeDynamicFields(id) {
	 $("#div"+id).remove();
	 $("#"+id).prop("checked", false);
	 var noOfChecked = $("#gradeClassesDiv").find('input[name="checkbox"]:checked').length;
	 if(noOfChecked == 0){
		 document.getElementById("submitDynamicContentDiv").style.visibility = "hidden";
	 }
}
function getDynamicTextBox(checkbox) {
	var userTypeId = dwr.util.getValue('userTypeId');
	var str = "<td width='140' align='left' class='txtStyle' >"+checkbox.value+"</td>";
		str +="<td width='200' ><select id='gradeLevelId"+checkbox.id+"' name='gradeLevelId"+checkbox.id+"' style='width:130px;' class='listmenu'>"+
              "<option value='select'>Select Grade Level</option>"+
              "<option value='1'>above grade level</option>"+
              "<option value='2'>at grade level</option>"+
              "<option value='3'>below grade level</option>"+
              "</select></td>";
	if(userTypeId == 3){
		str += "<td width='120' align='right'><input id = 'noSectionsPerDay"+checkbox.id+"' name = 'noSectionsPerDay"+checkbox.id+"' type='text'  size='5' onBlur='numVal(\"noSectionsPerDay"+checkbox.id+"\")' /></td>";
		str += "<td width='140' align='right'><input id = 'noSectionsPerWeek"+checkbox.id+"' name = 'noSectionsPerWeek"+checkbox.id+"' type='text'  size='5' onBlur='numVal(\"noSectionsPerWeek"+checkbox.id+"\"),perDayAndWeekValidation(\"noSectionsPerDay"+checkbox.id+"\",\"noSectionsPerWeek"+checkbox.id+"\")' /></td>";
	 }
		str += "<td width='140'><div class='fa fa-times' style='cursor: hand; cursor: pointer;font-size: 20px;color:#CD0000;' id=remove"+checkbox.id+" onClick='removeDynamicFields("+checkbox.id+")' /></td>";
		str += "<td height=26 colSpan=3></td></tr></table></div>";
return str;
}

function submitEducationalInfo(){
	var status = true;
	var classIdArr = new Array();
	var gradeLevelIdArr = new Array();
	var noSectionsPerDayArr = new Array();
	var noSectionsPerWeekArr = new Array();
	
	var userTypeId = dwr.util.getValue('userTypeId');
	var gradeId = dwr.util.getValue('gradeId');
	var userId = dwr.util.getValue('userId');
	var noOfChecked = $("#gradeClassesDiv").find('input[name="checkbox"]:checked').length;
	  if(noOfChecked != 0){
			$('input[name="checkbox"]:checked').each(function() {
				if(userTypeId == 3){
					var gradeLevelId = dwr.util.getValue("gradeLevelId"+this.id);
					var noSectionsPerDay = dwr.util.getValue("noSectionsPerDay"+this.id);
					var noSectionsPerWeek = dwr.util.getValue("noSectionsPerWeek"+this.id);
					if(gradeLevelId == 'select'){
						alert('Please select a grade level !!');
						status = false;
					}else if(!noSectionsPerDay){
						alert('Please enter number of sections per day !!');
						 $("#noSectionsPerDay"+this.id).focus();
						 status = false;
					}else if(!noSectionsPerWeek){
						alert('Please enter number of sections per day !!');	
						 $("#noSectionsPerWeek"+this.id).focus();
						 status = false;
					}else if(!numVal("noSectionsPerDay"+this.id)){
						 status = false;
					}else if(!numVal("noSectionsPerWeek"+this.id)){
						 status = false;
					}else if(noSectionsPerDay > noSectionsPerWeek){
						alert('Sections per day should not exceeds per week !!');
						$("#noSectionsPerWeek"+this.id).focus();
						status = false;
					}
					if(status == true){
						gradeLevelIdArr.push(gradeLevelId);
						noSectionsPerDayArr.push(noSectionsPerDay);
						noSectionsPerWeekArr.push(noSectionsPerWeek);
						classIdArr.push(this.id);
					}else if(status == false){
						return status;
					}
					
				}else if(userTypeId == 5){
					var gradeLevelId = dwr.util.getValue("gradeLevelId"+this.id);
					if(gradeLevelId == 'select'){
						alert('Please select a grade level !!');
						status = false;
					}
					if(status == true){
						gradeLevelIdArr.push(gradeLevelId);
						classIdArr.push(this.id);
					}else if(status == false){
						return status;
					}
				}
			});
		if(status == true){
			 if(userTypeId == 3){
				  $.ajax({
					     type: "GET",
					     url:"addTeacherGradeClass.htm",
					     data: "teacherId="+userId+"&gradeId="+gradeId+"&gradeLevelIdArr="+gradeLevelIdArr+"&noSectionsPerDayArr="+noSectionsPerDayArr+"&noSectionsPerWeekArr="+noSectionsPerWeekArr+"&classIdArr="+classIdArr,
					     success : function(data){
					    	 systemMessage(data);
				  	     }
				  	 });
				  }else if(userTypeId == 5){
					  $.ajax({
						     type: "GET",
						     url:"addStudentGradeClass.htm",
						     data: "studentId="+userId+"&gradeId="+gradeId+"&gradeLevelIdArr="+gradeLevelIdArr+"&classIdArr="+classIdArr,
						     success : function(data){
						    	 systemMessage(data);
					  	     }
					   });
				  }
			}
	  }else{
		  return false ;
	  }
}

function resetEducationInfo(){
	$('input[name="checkbox"]:checked').each(function() {
		dwr.util.setValue("noSectionsPerDay"+this.id,'');
		dwr.util.setValue("noSectionsPerWeek"+this.id,'');
		dwr.util.setValue("gradeLevelId"+this.id,'select');
	});
}
function numVal(id)
{
	var value = dwr.util.getValue(id);
	if(value){
	    var condition=/^[0-9]+$/.test(value);
	    if(condition==false){
	        msg="Please enter a numeric value";
	        alert(msg);
	        $('#'+id).focus();
	        return false;
	    }else{
	       return true;
	    }
	}
}
function perDayAndWeekValidation(noSectionsPerDayId,noSectionsPerWeekId){
	var noSectionsPerDay = parseInt(dwr.util.getValue(noSectionsPerDayId));
	var noSectionsPerWeek = parseInt(dwr.util.getValue(noSectionsPerWeekId));
	if(noSectionsPerDay && noSectionsPerWeek){
		if(noSectionsPerDay > noSectionsPerWeek){
	  	    msg="Sections per day should not greater than per week !!";
	        alert(msg);
	        return false;
		}else{
			return true;
		}
	}
}