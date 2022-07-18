/**
 * 
 */
$(document).ready(function() {
		var divId = dwr.util.getValue('divId');
		var page = dwr.util.getValue('page');
		if(divId == 'planSchedule'){
			document.getElementById("planSchedule").style.visibility='visible';
			$("#planSchedule").show();
			$("#viewTeacherRequests").hide();
			$("#detailsPage").hide();
		
		}else if(divId == 'viewTeacherRequests'){
			$("#viewTeacherRequests").show();
			document.getElementById("viewTeacherRequests").style.visibility='visible';
			$("#planSchedule").hide();
			$("#detailsPage").hide();
		}else if(divId == 'plannerData'){
			$('#gradeId').val(document.getElementById("gradeIdHidden").value);
			var loadClassesCallBack = function(list) {
				if (list != null) {
					dwr.util.removeAllOptions('classId');
					dwr.util.addOptions('classId', ["select"]);
					dwr.util.addOptions('classId', list, 'classId', 'className');
					$('select[name="classId"]').val(document.getElementById("classIdHidden").value); 
					$('#classId').css('color','black');
				}
			}
			teacherSchedulerService.getStudentClasses(document.getElementById("gradeId").value, {
				callback : loadClassesCallBack
			});
			var formatter = function (entry) {
				var fullName = '';
				if(entry.userRegistration.title)
					fullName = entry.userRegistration.firstName+ " " +entry.userRegistration.lastName;
				else
					fullName = entry.userRegistration.title + " " +entry.userRegistration.firstName+ " " +entry.userRegistration.lastName;
				return fullName;
			}
			var loadTeachersCallBack = function(list) {
				if (list != null) {
					dwr.util.removeAllOptions('teacherId');
					dwr.util.addOptions('teacherId', ["select"]);
					dwr.util.addOptions('teacherId', list,'teacherId', formatter);
					$('select[name="teacherId"]').val(document.getElementById("teacherIdHidden").value); 
					$('#teacherId').css('color','black');
				} 
			}
			teacherSchedulerService.getTeachers(document.getElementById("gradeId").value,document.getElementById("classIdHidden").value, {
				callback : loadTeachersCallBack
			}) 
			document.getElementById("planSchedule").style.visibility='visible';
			document.getElementById("detailsPage").style.visibility='visible';
		}
	});