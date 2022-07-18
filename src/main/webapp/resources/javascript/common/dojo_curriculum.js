/**
 * 
 */
function getClassesByGrade(index) {
	var userType ="";
	if(dijit.byId('userType') != null){
		userType = dijit.byId('userType');
	}
	
	var gradeId = 0;
	if(userType == 'parent'){
		gradeId = dijit.byId('gradeId' + index.value);
	}
	else{
		gradeId = dijit.byId('gradeId');
	}
	$.ajax({
		type : "GET",
		url : "getAvailableClasses.htm",
		data : "gradeId=" + gradeId,
		success : function(response) {			
			var availableClasses = response.availableClasses;
			dojo.empty("attendanceDiv");
			var optionData = [];
			optionData.push({id:"0",name:"Select Class"});
			$.each(availableClasses, function(index, value) {
				optionData.push({
			        id: value.gradeClassId,
			        name: value.studentClass.className
			    });
			});
			var finalData = {data:optionData};
			var str = new dojo.store.Memory(finalData);
			dijit.byId('gradeClassId').set('store',str);
			dijit.byId('gradeClassId').set("value", "0");

		}
	});
}


function getScheduledClassesDOJO() {
	var gradeClassId = dijit.byId('gradeClassId');
	$.ajax({
		type : "GET",
		url : "getScheduledClasses.htm",
		data : "gradeClassId=" + gradeClassId,
		success : function(response) {
			var scheduledClasses = response.scheduledClasses;
			dojo.empty("csId");
			
			var optionData = [];
			optionData.push({id:"0",name:"Select Section"});
			$.each(scheduledClasses, function(index, value) {
				optionData.push({
			        id: value.csId,
			        name: value.section.section
			    });
			});
			var finalData = {data:optionData};
			var str = new dojo.store.Memory(finalData);
			dijit.byId('csId').set('store',str);
			dijit.byId('csId').set("value", "0");
		}
	});
}
