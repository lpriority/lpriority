/**
 * 
 */
function deleteRTIGroup() {
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteGrade(id) {
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteAcademicPerf(){
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteAssignmentType(){
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteCitizenship(){
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteComments(){
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteCountry(){
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteDay() {
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteSchool() {
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteUserReg() {
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteGradeEvent() {
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}

function deleteGradeLevel() {
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteInterest() {
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}

function deleteMasterGrade() {
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteMinute() {
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteQOR() {
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteRubricType() {
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteSchoolLevel(){
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteSchoolType(){
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteSecQuestion() {
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteState() {
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteSubInterest() {
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteTeacherPerformance() {
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteUsers() {
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}
function deleteMasterGrade() {
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			alert('Can not be deleted');
			return true;
		}else{
			return true;
		}
	}));
}

function deleteLPNews(lpNewsId) {	
	if(confirm("Do you really want to delete?",function(status){
		if(status){
			$.ajax({
				url : "deleteLPNews.htm",
				method : "POST",
				data : "lpNewsId=" + lpNewsId,
				success : function(data) {	
					 location.reload();
				}
			});
			return true;
		}else{
			return true;
		}
	}));
}
