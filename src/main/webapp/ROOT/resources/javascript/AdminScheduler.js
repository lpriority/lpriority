function above13StudentRegVal2() {
	var gender = $('input[name="gender"]:checked').val();

	if (gender == '' || gender == null) {
		alert("Please select the gender");
		$('#gender').next().show();
		return false;
	}
	var date = $('#date').val();
	if (date == '' || date == null || date == 'select') {
		alert("Please select the date");
		$('#date').next().show();
		return false;
	}
	var month = $('#month').val();
	if (month == '' || month == null || month == 'select') {
		alert("Please select the month");
		$('#month').next().show();
		return false;
	}
	var year = $('#year').val();
	if (year == '' || year == null || year == 'select') {
		alert("Please select the year");
		$('#year').next().show();
		return false;
	}

	date = date.replace(/^\s+|\s+$/g, '');
	month = month.replace(/^\s+|\s+$/g, '');
	year = year.replace(/^\s+|\s+$/g, '');

	var values = [];
	$("input[name*='classId']").each(function() {
		if (jQuery(this).is(":checked")) {
			values.push($(this).val());
		}
	});

	var other1 = $('#textother').val();
	var other2 = $('#textother2').val();
	var other3 = $('#textother3').val();
	var other4 = $('#textother4').val();

	$.ajax({
		url : "SaveStudentDetails2.htm",
		type : "POST",
		data : "gender=" + gender + "&date=" + date + "&month=" + month
				+ "&year=" + year + "&classIds=" + values + "&other1=" + other1
				+ "&other2=" + other2 + "&other3=" + other3 + "&other4="
				+ other4,
		success : function(data) {
			window.location.replace(data);
		}
	});

}

function above13StudentRegVal3() {
	var gradeLevels = $("[name=gradeLevels]");
	var classIds = $("[name=classIds]");

	var gradelevels = [];
	var classidss = [];
	for (var i = 0; i < classIds.length; i++) {

		gradelevels.push(gradeLevels[i].value);
		classidss.push(classIds[i].value);
		if (gradeLevels[i].value == 'select') {
			systemMessage('Select a Grade Level ');
			$("#gradeLevels" + i).focus();
			return false;
		}
	}
	$.ajax({
		url : "SaveStudentDetails3.htm",
		type : "POST",
		data : "gradeLevels=" + gradelevels + "&classIds=" + classidss,
		success : function(data) {
			window.location.replace(data);
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

function  validateSchedulePeriodsTimes(){
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
     return true;
}

function SaveAutomaticScheduler() {
	var noOfDays = $('#noOfDays').val();
	var noOfClassPeriods = $('#noOfClassPeriods').val();
	var periodRange = $('#periodRange').val();
	var passingTime = $('#passingTime').val();
	var homeRoomTime = $('#homeRoomTime').val();
	var noOfTeachers = $('#noOfTeachers').val();
	var noOfSectionsPerDay = $('#noOfSectionsPerDay').val();
	var startDate = $('#startDate').val();
	var endDate = $('#endDate').val();
	var daystarttime = $('#daystarttime').val();
	var daystarttimemin = $('#daystarttimemin').val();
	var daystarttimemeridian = $('#daystarttimemeridian').val();
	var dayendtime = $('#dayendtime').val();
	var dayendtimemin = $('#dayendtimemin').val();
	var dayendtimemeridian = $('#dayendtimemeridian').val();

	if (noOfDays == '' || noOfDays == null || noOfDays==0) {
	
		alert("Please enter the noOfDays per week");
		$('#noOfDays').next().show();
		return false;
	}
	else if (noOfDays > 7) {
		alert("Number Of Days can not exced 7 Days");
		$('#noOfDays').next().show();
		return false;
	}
	else if(!$.isNumeric(noOfDays)) {
		alert("please enter the numeric character");
		$('#noOfDays').next().show();
		return false;
	}

	if (noOfClassPeriods == '' || noOfClassPeriods == null || noOfClassPeriods==0) {
		alert("Please enter the noOfClassPeriods per day");
		$('#noOfClassPeriods').next().show();
		return false;
	}
	else if ( noOfClassPeriods> 7) {
		alert("Number Of Class Periods can not exced 7 ");
		$('#noOfClassPeriods').next().show();
		return false;
	}
	else if (!$.isNumeric(noOfClassPeriods)) {
		alert("please enter the numeric character");
		$('#noOfClassPeriods').next().show();
		return false;
	}

	if (periodRange == '' || periodRange == null || periodRange==0) {
		alert("Please enter the periodRange");
		$('#periodRange').next().show();
		return false;
	}
	else if (periodRange > 60) {
		alert("Period Range can not exced 60");
		$('#periodRange').next().show();
		return false;
	}
	else if (!$.isNumeric(periodRange)) {
		alert("please enter the numeric character");
		$('#periodRange').next().show();
		return false;
	}

	if (passingTime == '' || passingTime == null || passingTime==0) {
		alert("Please enter the passingTime");
		$('#passingTime').next().show();
		return false;
	}
	else if (passingTime > 15) {
		alert("Passing time can not exced 15");
		$('#passingTime').next().show();
		return false;
	}
	else if (!$.isNumeric(passingTime)) {
		alert("please enter the numeric character");
		$('#passingTime').next().show();
		return false;
	}

	if (homeRoomTime == '' || homeRoomTime == null || homeRoomTime==0) {
		alert("Please enter the home room time");
		$('#homeRoomTime').next().show();
		return false;
	}
	else if (homeRoomTime > 10) {
		alert("Passing time can not exced 10");
		$('#homeRoomTime').next().show();
		return false;
	}
	else if (!$.isNumeric(homeRoomTime)) {
		alert("please enter the numeric character");
		$('#homeRoomTime').next().show();
		return false;
	}

	if (noOfTeachers == '' || noOfTeachers == null || noOfTeachers==0) {
		alert("Please enter the noOfTeachers");
		$('#noOfTeachers').next().show();
		return false;
	}
	else if (noOfTeachers > 100) {
		alert("Number of Teachers can not exced 100");
		$('#noOfTeachers').next().show();
		return false;
	}
	else if (!$.isNumeric(noOfTeachers)) {
		alert("please enter the numeric character");
		$('#noOfTeachers').next().show();
		return false;
	}

	if (noOfSectionsPerDay == '' || noOfSectionsPerDay == null || noOfSectionsPerDay==0) {
		alert("Please enter the noOfSections Per Day");
		$('#noOfSectionsPerDay').next().show();
		return false;
	}
	else if (noOfSectionsPerDay > 5) {
		alert("Number of sections  can not exced 5");
		$('#noOfSectionsPerDay').next().show();
		return false;
	}
	else if (!$.isNumeric(noOfSectionsPerDay)) {
		alert("please enter the numeric character");
		$('#noOfSectionsPerDay').next().show();
		return false;
	}

	if (startDate == '' || startDate == null || startDate==0) {
		alert("Please enter the startDate");
		$('#startDate').next().show();
		return false;
	}

	if (endDate == '' || endDate == null || endDate==0) {
		alert("Please enter the EndDate");
		$('#endDate').next().show();
		return false;
	}
	if (!checkDates(startDate, endDate)) {
		alert("End date Should be greater than start date");
		$('#endDate').next().show();
		return false;
	}

	var status = validateSchedulePeriodsTimes();
	if(status){
		$("#loading-div-background").show();
		var formObj = document.getElementById("automaticSchedulerForm");
		var formData = new FormData(formObj);
		$.ajax({
			url: 'SaveAutomaticScheduler.htm',
			type: 'POST',
			data: formData,
			mimeType:"multipart/form-data",
			contentType: false,
			cache: false,
			processData:false,
			success: function(data){
				$("#loading-div-background").hide();
				if(data != 'Automated Scheduler Failed')
					systemMessage(data);
				else
					systemMessage(data,"error");
			}
		});	
	}
}