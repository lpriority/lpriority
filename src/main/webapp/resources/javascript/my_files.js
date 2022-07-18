
function showHide(a1) {
    var divObject = document.getElementById('divid:'+a1);
    if(divObject.style.display == ""){
        divObject.style.display = "none";
        document.getElementById('anchorid:'+a1).innerHTML="+";
    } else {
        divObject.style.display = "";
        document.getElementById('anchorid:'+a1).innerHTML="-";
    }
}
function showHideFiles(thisvar, unitId, lessonNo) {
	
	var fileContainer = $(document.getElementById('filesDiv'+unitId+lessonNo));
	$(fileContainer).empty();
	if ($(thisvar).html() == "-") {
		$(thisvar).html("+");
	} else {
		var lessonId =  document.getElementById('lessonId'+unitId+lessonNo).value; 
		$.ajax({
			type : "GET",
			url : "getActivityByLesson.htm",
			data : "lessonId=" + lessonId,
			success : function(response) {
				$(fileContainer).append(response);
			}
		});
		$(thisvar).html("-");
	}
}
function load()
{
    window.document.getElementById("gradeId").focus();
}
function getClass(){
    var gradeId=document.getElementById("gradeId").value;
    var url = "../PopulateSelects?operation=getClassesforAdmin&gradeId="+ gradeId;
    postRequest(url);
}
function postRequest(strURL) {
    var xmlHttp;
    if(window.XMLHttpRequest){
        xmlHttp = new XMLHttpRequest();
    }
    else if(window.ActiveXObject){
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlHttp.open('POST', strURL, true);
    xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlHttp.onreadystatechange = function(){
        if (xmlHttp.readyState == 4){
            v=xmlHttp.responseText;
            v=v.replace(/^\s+|\s+$/g,'');
                javascript:window.location.reload();
        }
    }
    xmlHttp.send(strURL);
}
function saveFilePath(thisvar){
      var url = "SaveFilePath.jsp?teacherId="+thisvar.value;
    postRequest2(url);
}
function postRequest2(strURL) {
    var xmlHttp;
    if(window.XMLHttpRequest){
        xmlHttp = new XMLHttpRequest();
    }
    else if(window.ActiveXObject){
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlHttp.open('POST', strURL, true);
    xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlHttp.onreadystatechange = function(){
        if (xmlHttp.readyState == 4){
            v=xmlHttp.responseText;
            v=v.replace(/^\s+|\s+$/g,'');
            window.location="displayClassFiles.htm"
        }
    }
    xmlHttp.send(strURL);
}
function postRequest1(strURL) {
    var xmlHttp;
    if(window.XMLHttpRequest){
        xmlHttp = new XMLHttpRequest();
    }
    else if(window.ActiveXObject){
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlHttp.open('POST', strURL, true);
    xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlHttp.onreadystatechange = function(){
        if (xmlHttp.readyState == 4){
            v=xmlHttp.responseText;
            v=v.replace(/^\s+|\s+$/g,'');
            window.location="MyFiles.jsp?teacherRegId="+v;
        }
    }
    xmlHttp.send(strURL);
}
function changeMe(){
    var gradeid = document.getElementById("gradeId").value;
    var classname = document.getElementById("classId").value;
    var url = "../PopulateSelects?operation=getTeachersforAdmin&gradeId=" + gradeid+"&classId=" + classname;
    postRequest1(url);
}

function deletefolder()
{
    var filename=document.getElementById("filename").value;
    var pa=document.getElementById("pa").value
    var url ="AdminDelPrivateFolder.jsp?filename="+filename+"&pa="+pa;
    postRequest4(url);
}
function postRequest3(strURL){
    var xmlHttp;
    var opted='false';
    if(window.XMLHttpRequest){ // For Mozilla, Safari, ...
        var xmlHttp = new XMLHttpRequest();
    }
    else if(window.ActiveXObject){ // For Internet Explorer
        var xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlHttp.open('POST', strURL, true);
    xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlHttp.onreadystatechange = function(){
        if (xmlHttp.readyState == 4){
            responding=xmlHttp.responseText;
            var chk=responding.replace(/^\s+|\s+$/g,'');
            if(chk!=0)
            {
                alert("Can't delete folder. folder is not empty");
            }
                javascript:window.location.reload();
        }
    }
    xmlHttp.send(strURL);
}
function redirectCreateFolderPage(){
	document.getElementById("returnMessage").style.visibility = "hidden";
    var teacherId=document.getElementById('teacherId');
    var fileType=document.getElementById('fileType').value;
    if(fileType=="private"){
    	document.forms["createFolder"].submit();
    }else if(fileType=="public" && teacherId.value != null && teacherId.value >0){
    	document.forms["createFolder"].submit();
	}else{    	
    		document.getElementById("returnMessage").innerText= "Please select teacher";
    		document.getElementById("returnMessage").style.visibility = "visible";
    		return false;       
    }

}
function newFolder(){
	document.getElementById("returnMessage").style.visibility = "hidden";
    var gradeId=document.getElementById('gradeId');
    var classId=document.getElementById('classId');
    var fileType=document.getElementById('fileType').value;
    if((fileType=="private" || fileType=="studentPrivate" || fileType=="transfer") && classId.value != null && classId.value >0){
    	document.forms["createFolder"].submit();
    }else{    	
    		document.getElementById("returnMessage").innerText= "Please select class";
    		document.getElementById("returnMessage").style.visibility = "visible";
    		return false;       
    }

}
function createFolderPage(fileType){
	$.ajax({	
		type : "POST",
		url : "loadFolders.htm", 
	    data: "teacherId="+teacherId+"&fileType="+fileType,
	    success : function(response) {	    	
	    	$("#displayFolders").html(response);
		} 
	});
}

function loadTeacherAdminFiles(){
	$.ajax({			
		url : "loadTeacherAdminFiles.htm", 
	    data: "fileType=public",
	    success : function(response) {	    	
	    	
	    	document.getElementById("title").innerText= "Admin Files";
	    	document.getElementById("dropdownTable").style.visibility = "hidden";
	    	$("#unitLessonDiv").html(response);
		} 
	}); 
}

function creatFolder(){
	var folderName = jQuery.trim(document.getElementById('folderName').value);
	var fileType = document.getElementById('fileType').value;
	var usersFolderPath = document.getElementById('usersFolderPath').value;
	var teacherId = 0;	
	if(folderName && usersFolderPath){
		usersFolderPath = usersFolderPath+"\\"+folderName;
		$.ajax({
	  		 type: "GET",
	  	     url:  "checkFolderExists.htm",
	  	     data: "usersFolderPath="+usersFolderPath,
	  	     success: function(status){
		  	      if(status == 'Exists'){
			  	    	alert('"'+folderName+'" already existed!');
		  	      }else{
		  			  if(fileType=="public"){
			  			  teacherId = document.getElementById('teacherId').value;
			  			  if(teacherId != "select"){
			  					$.ajax({			
			  						url : "createFolder.htm", 
			  					    data: "teacherId="+teacherId+"&fileType="+fileType+"&folderName="+folderName,
			  					    success : function(data) {	
			  					    	 document.getElementById('folderName').value="";
			  					    	 systemMessage(data);
			  						}  
			  					}); 
			  			  }else{
			  				  return false;
			  			  }
			  			}else if(fileType=="private"){
			  				$.ajax({			
			  					url : "createFolder.htm", 
			  				   data: "teacherId="+teacherId+"&fileType="+fileType+"&folderName="+folderName,
			  				    success : function(data) {	
			  				    	 document.getElementById('folderName').value="";
			  				    	 systemMessage(data);
			  					}  
			  				}); 
			  			}
		  	      }
	  	     }
	  	 });
	}else{
		document.getElementById('folderName').value= "";
		$("#folderName").focus();
		systemMessage('Please enter folder name');
	}
}

function creatTeacherFolder(){
	var folderName = document.getElementById('folderName').value;
	var usersFolderPath = document.getElementById('usersFolderPath').value;
	if(folderName && usersFolderPath){
		usersFolderPath = usersFolderPath+"\\"+folderName;
		$.ajax({
	  		 type: "GET",
	  	     url:  "checkFolderExists.htm",
	  	     data: "usersFolderPath="+usersFolderPath,
	  	     success: function(status){
		  	      if(status == 'Exists'){
			  	    	alert('"'+folderName+'" already existed!');
		  	      }else{
		  	    	var teacherId = document.getElementById('teacherId').value;
		  			var gradeId = document.getElementById('gradeId').value;
		  			if(!gradeId)
		  				gradeId = window.parent.document.getElementById( 'gradeId' ).value;
		  			var classId = document.getElementById('classId').value;
		  			if(!classId)
		  				classId = window.parent.document.getElementById( 'classId' ).value;
		  			var fileType = document.getElementById('fileType').value;	
		  			if(teacherId && teacherId != "select"){
		  				if(gradeId > 0 && classId > 0 && fileType=="private" ){
		  					$.ajax({			
		  						url : "createTeacherFolder.htm", 
		  					    data: "teacherId="+teacherId+"&fileType="+fileType+"&folderName="+folderName+"&gradeId="+gradeId+"&classId="+classId,
		  					    success : function(data) {	
		  					    	 document.getElementById('folderName').value="";
		  					    	 systemMessage(data);
		  						}  
		  					}); 
		  				}else{
		  					$.ajax({			
		  						url : "createFolder.htm", 
		  					   data: "teacherId="+teacherId+"&fileType="+fileType+"&folderName="+folderName,
		  					    success : function(data) {	
		  					    	 document.getElementById('folderName').value="";
		  					    	 systemMessage(data);
		  						}  
		  					}); 
		  				}
		  			}else{
		  				return false;
		  			}
		  	      }
	  	     }
	  	   }); 		
	}else{
		alert('Please enter folder name');
	}
}

function createStudentFolder(){
	var usersFolderPath = document.getElementById('usersFolderPath').value;
	var folderName = document.getElementById('folderName').value;
	if(folderName && usersFolderPath){
		usersFolderPath = usersFolderPath+"\\"+folderName;
		$.ajax({
	  		 type: "GET",
	  	     url:  "checkFolderExists.htm",
	  	     data: "usersFolderPath="+usersFolderPath,
	  	     success: function(status){
		  	      if(status == 'Exists'){
			  	    	alert('"'+folderName+'" already existed!');
		  	      }else{
						var fileType = document.getElementById('fileType').value;
						var studentId = document.getElementById('studentId').value;
						var gradeId = document.getElementById('gradeId').value;
						var classId = document.getElementById('classId').value;
						$.ajax({			
							url : "createStudentFolder.htm", 
							 data: "studentId="+studentId+"&fileType="+fileType+"&folderName="+folderName+"&gradeId="+gradeId+"&classId="+classId,
						    success : function(data) {	
						    	 document.getElementById('folderName').value="";
						    	 systemMessage(data);
							}  
						}); 
		  	      }
	  	     }
	  	   }); 
	}else{
		alert('Please enter folder name');
	}
}
function displayLessons(){
	var unitId = document.getElementById('unitId').value;
	var fileType = document.getElementById('fileType').value;
	var gradeId = document.getElementById('gradeId').value;
	var classId = document.getElementById('classId').value;
	var page = "";
	if(unitId == 'select' || unitId == 'Select Unit' || unitId == 0){
		$("#lessonDiv").html('');
    	$("#unitLessonDiv").html('');
    	$("#showFilesDiv").html('');
		return false;
	}else{
		if(document.getElementById('page'))
			page = document.getElementById('page').value;
		 $("#loading-div-background").show();
		$.ajax({
			url : "loadLessons.htm", 
		    data : "unitId="+unitId+"&fileType="+fileType+"&gradeId="+gradeId+"&classId="+classId+"&page="+page,
		    success : function(response) {
		    	$("#lessonDiv").html(response);
		    	$("#unitLessonDiv").html(response);
		    	$("#showFilesDiv").html(response);
		    	$("#loading-div-background").hide();
			}  
		}); 	
	}
}


function uploadTeacherFiles(obj){
	var formData = new FormData(document.querySelector('uploadTeacherFile'))
	$.ajax({
		url : "uploadTeacherFiles.htm", 
		data:  formData,
		success : function() {	
			loadTeacherFolders();
		}  
	});    
}
function uploadTeacherLessonFiles(){
	var formData = new FormData(document.querySelector('uploadTeacherLessonFile'));
	$.ajax({
		url : "uploadTeacherLessonFiles.htm", 
		data:  formData,
		success : function() {	
		}  
	});    
}
function uploadStudentFiles(obj){
	var formData = new FormData(document.querySelector('uploadTeacherFile'))
	$.ajax({
		url : "uploadStudentFiles.htm", 
		data:  formData,
		success : function() {	
			loadStudentFolders();
		}  
	});    
}

function downloadFile(fileName, id){
	var folderSize = 0;
	var folderPath = document.getElementById("folderPath"+id).value;
	var filePath = folderPath+"\\"+fileName;
	loadPage("downloadFile.htm?filePath="+filePath);
}

function deleteFile(rowId,fileName,id,unitId,lessonId){		
	var teacherId;
	var fileType;	
	var folderPath;
	var folderSize = 0;
	if(confirm("Are you sure to delete this file?",function(status){
		if(status){
			teacherId = document.getElementById("teacherId").value;
			fileType = document.getElementById("fileType").value;
			folderPath = document.getElementById("folderPath"+id).value;
			data = "teacherId="+teacherId+"&fileType="+fileType+"&folderPath="+folderPath+"&fileName="+fileName;
			if(unitId && unitId > 0 && lessonId && lessonId >0)
				data += "&unitId="+unitId+"&lessonId="+lessonId;
			$.ajax({
				type:"POST",
				url : "deleteFile.htm", 
				data: data,
				success : function(response) {
					if(response.status){
						 var folderName = document.getElementById("folderName"+id).value;
						 var row = document.getElementById("tr"+''+id+''+rowId);
						 row.parentNode.removeChild(row);
						 if($("#table"+id+" > tbody").children().length > 0)
						     	folderSize = $("#table"+id+" > tbody").children().length;
						 document.getElementById('anchorid:' + id).innerHTML = "<i class='fa fa-folder-open arrow' aria-hidden='true'></i><span id='folderId"+id+"' class='folderStyle'> "+folderName+" </span> <span class='fileCountStyle'>&nbsp;&nbsp;("+folderSize+") </span>";
						 systemMessage("File deleted.","error");
						 return false;  
					}else{
						systemMessage("Unable to delete the File","error");
					}
				}  
			}); 
		}
	})); 
}

function fileValidate(id){
    var fileVal = document.getElementById("file:"+id).value; 
	if(fileVal!= ""){		
		document.getElementById('button:'+id).removeAttribute("disabled");
	}else{
		document.getElementById('button:'+id).setAttribute("disabled", true);
	}	
}

function submitChanges(){
    var filename=document.getElementById('file').value;
    alert(filename);
}

function loadFolders(){
	var fileType = document.getElementById('fileType').value;
	if(fileType=="private"){
		$("#loading-div-background").show();
		$.ajax({			
			url : "loadFolders.htm", 
		    data: "teacherId=0&fileType="+fileType,
		    success : function(response) {	   
		    	$("#displayFolders").html(response);
		    	$("#loading-div-background").hide();
			} 
		}); 
	}else if(fileType=="public"){
		var teacherId = document.getElementById('teacherId').value;
		if(teacherId != "select"){
			document.getElementById("createFolderDiv").style.display = 'block';
			 $("#loading-div-background").show();
			$.ajax({			
				url : "loadFolders.htm", 
			    data: "teacherId="+teacherId+"&fileType="+fileType,
			    success : function(response) {	  
			    	$("#displayFolders").html(response);
			    	 $("#loading-div-background").hide();
				} 
			}); 
		}else{
			  document.getElementById("createFolderDiv").style.display = 'none';
			  $("#displayFolders").html('');
			return false;
		}
	}
}

function loadStudentFolders(){
	var fileType = document.getElementById('fileType').value;
	var classId = document.getElementById('classId').value;
	var gradeId = document.getElementById('gradeId').value;
	var studentId = document.getElementById('studentId').value;
	if(classId > 0 && studentId > 0){
		 $("#loading-div-background").show();
		$.ajax({			
			url : "loadStudentFolders.htm", 
		    data: "fileType="+fileType+"&studentId="+studentId+"&gradeId="+gradeId+"&classId="+classId,
		    success : function(response) {
		    	if(fileType == "student"){
		    		$("#unitLessonDiv").html(response);
		    	}else{	
		    		document.getElementById("createFolderDiv").style.display = 'block';
		    		$("#showFilesDiv").html(response);
		    	}
		    	 $("#loading-div-background").hide();
			} 
		}); 	
	}else{
		
		if(fileType == "student"){
    		$("#unitLessonDiv").html('');
		}else{	
			document.getElementById("createFolderDiv").style.display = 'none';
    		$("#showFilesDiv").html('');
		}
			return false;
	}
}

function loadUnitsLessons(){
	var unitId = document.getElementById('unitId');	
	var fileType = document.getElementById('fileType').value;
	if(unitId.value > 0){
		if(fileType=="private"){
			$.ajax({			
				url : "loadFolders.htm", 
			    data: "fileType="+fileType,
			    success : function(response) {	    	
			    	$("#unitLessonDiv").html(response);
				} 
			}); 
		}else if(fileType=="public" || fileType=="teacher" || fileType=="studentFiles" ){
			$.ajax({			
				url : "loadUnitsLessons.htm", 
			    data: "unitId="+unitId.value+"&fileType="+fileType,
			    success : function(response) {
			    	if(fileType=="public")
			    		$("#unitLessonDiv").html(response);
			    	else if(fileType=="teacher" || fileType=="studentFiles")
			    		$("#showFilesDiv").html(response);
				} 
			}); 
		}
	}else{
		$("#unitLessonDiv").html('');
		$("#showFilesDiv").html('');
	}
}

function loadTeachers() {
	var gradeSelected = dwr.util.getValue('gradeId');
	var classSelected = dwr.util.getValue('classId');
	var regId = dwr.util.getValue('childId');
	var childGradeId = dwr.util.getValue('gradeId:'+regId);
	if(childGradeId){
		teacherSchedulerService.getTeachers(childGradeId,classSelected, {
			callback : sectionCallBack
		});
	}else{
		teacherSchedulerService.getTeachers(gradeSelected,classSelected, {
			callback : sectionCallBack
		});
	}	
}
function sectionCallBack(list) {
	if (list != null) {
		dwr.util.removeAllOptions('teacherId');
		dwr.util.addOptions(teacherId, ["Select Teacher"]);
		var teacherSelect = document.getElementById('teacherId');		
		for(var i=0;i<list.length;i++){
			var teacherOption = document.createElement("option");			
			teacherOption.text = list[i].userRegistration.firstName+" "+list[i].userRegistration.lastName;
			teacherOption.value = list[i].teacherId;		    
			teacherSelect.options.add(teacherOption);
		}

	} else {
		alert("No data found");
	}	
}
function loadStudentTeacherFolders(){
	var teacherId = document.getElementById('teacherId');
	var unitId = document.getElementById('unitId');		
	var ids = null;
	if (teacherId != null && unitId != null){
		ids = "teacherId="+teacherId.value+"&unitId="+unitId.value;
	}
	var fileType = document.getElementById('fileType').value;		
	$.ajax({			
		url : "loadStudentTeacherFiles.htm", 
	    data: ids+"&fileType="+fileType,
	    success : function(response) {
	    	$("#showFilesDiv").html(response);
	    }
	}); 		
}
function adminCreateFolderDialog() {
	var teacherId = 0;
	var fileType=document.getElementById('fileType').value;
	if(fileType == "public"){
		if(document.getElementById('teacherId').value != "select")
			teacherId=document.getElementById('teacherId').value;
	}
   var left = (screen.width / 2) - (700 / 2);
   var top = (screen.height / 2) - (400 / 2);
	 jQuery.curCSS = jQuery.css;
	 $("#dialog").dialog({
			overflow: 'auto',
			dialogClass: 'no-close',
		    autoOpen: false,
			position: {my: "center", at: "center", of:window ,within: $("body") },
		    title: 'Create Folder',
		    draggable: true,
		    width : 500,
		    height : 280,
		    left: left,
		    top : top,
		    resizable : true,
		    modal : true,
		    close : function(){
		    	loadFolders();
         }  
		});
	 var iframe = $('#iframe');
	 iframe.attr('src', "displayCreateFolder.htm?fileType="+fileType+"&teacherId="+teacherId);
	 $("#dialog").dialog("open");
}

function teacherCreateFolderDialog() {
	var teacherId = 0;
	if(document.getElementById('teacherId'))
		teacherId=document.getElementById('teacherId').value;
		var gradeId = document.getElementById('gradeId').value;
		if(!gradeId)
			gradeId = window.parent.document.getElementById( 'gradeId' ).value;
		var classId = document.getElementById('classId').value;
		if(!classId)
			classId = window.parent.document.getElementById( 'classId' ).value;
	 var fileType=document.getElementById('fileType').value;
	 var left = (screen.width / 2) - (700 / 2);
	 var top = (screen.height / 2) - (400 / 2);
	 jQuery.curCSS = jQuery.css;
	var gradeSelected = document.getElementById('gradeId');
	var classSelected = document.getElementById('classId');
	if(gradeSelected && classSelected){
		if(gradeSelected.value > 0 && classSelected.value > 0){
			 $("#dialog").dialog({
					overflow: 'auto',
					dialogClass: 'no-close',
				    autoOpen: false,
					position: {my: "center", at: "center", of:window ,within: $("body") },
				    title: 'Create Folder',
				    draggable: true,
				    width : 500,
				    height : 300,
				    left: left,
				    top : top,
				    resizable : true,
				    modal : true,
				    close : function(){
				    	loadTeacherFolders();
		            }  
				});
		 var iframe = $('#iframe');
		 iframe.attr('src', "displayTeacherCreateFolder.htm?fileType="+fileType+"&gradeId="+gradeId+"&classId="+classId+"&teacherId="+teacherId);
		 $("#dialog").dialog("open");
		}
	}
}

function studentCreateFolderDialog() {
	var fileType=document.getElementById('fileType').value;
	var gradeId=document.getElementById('gradeId').value;
	var classId=document.getElementById('classId').value;
	var studentId=document.getElementById('studentId').value;
	if(classId > 0){
	var left = (screen.width / 2) - (700 / 2);
	var top = (screen.height / 2) - (400 / 2);
		 jQuery.curCSS = jQuery.css;
		 $("#dialog").dialog({
				overflow: 'auto',
				dialogClass: 'no-close',
			    autoOpen: false,
				position: {my: "center", at: "center", of:window ,within: $("body") },
			    title: 'Create Folder',
			    draggable: true,
			    width : 500,
			    height : 280,
			    left: left,
			    top : top,
			    resizable : true,
			    modal : true,
			    close : function(){
			    	loadStudentFolders();
	         }  
			});
		 var iframe = $('#iframe');
		 iframe.attr('src', "displayStudentCreateFolder.htm?fileType="+fileType+"&studentId="+studentId+"&gradeId="+gradeId+"&classId="+classId);
		 $("#dialog").dialog("open");
	}else{
		alert('Please select class');
	}
}

function loadTeacherFolders(){
	var gradeId = document.getElementById('gradeId').value;
	var classId = document.getElementById('classId').value;
	var teacherId = document.getElementById('teacherId').value;
	var fileType = document.getElementById('fileType').value;
	
	if(gradeId > 0 && classId > 0 ){
		if(fileType=="private"){
			document.getElementById("createFolderDiv").style.display = 'block';
			ids = "teacherId="+teacherId+"&gradeId="+gradeId+"&classId="+classId;
		}else{
			displayLessons();
			return false;
		}
		$("#loading-div-background").show();
		$.ajax({			
				url : "loadTeacherFolders.htm", 
			    data: ids+"&fileType="+fileType,
			    success : function(response) {	
			    	$("#unitLessonDiv").html(response);
			    	$("#loading-div-background").hide();
				} 
			}); 
	}else{
		document.getElementById("createFolderDiv").style.display = 'none';
		$("#unitLessonDiv").html('');
	}
}

function add(){
	var folderName = document.getElementById('folderName').value;
	var fileType = document.getElementById('fileType').value;
	var studentId = document.getElementById('studentId').value;
	var gradeId = document.getElementById('gradeId').value;
	var classId = document.getElementById('classId').value;
	if(folderName){
			$.ajax({			
				url : "createStudentFolder.htm", 
				 data: "studentId="+studentId+"&fileType="+fileType+"&folderName="+folderName+"&gradeId="+gradeId+"&classId="+classId,
			    success : function(data) {	
			    	 document.getElementById('folderName').value="";
			    	 systemMessage(data);
				}  
			}); 
	}else{
		alert('Please enter folder name');
	}
}

function openEditBox(filename, count, id){
	var val = $('#'+filename+''+count).text();
	 if(!document.getElementById("edit"+count)){
		 $('#'+id+''+count).before("<textarea id='edit"+count+"' name='textarea"+count+"' style='display:none;font-size: 13px;' onblur=closeEditBox('"+count+"')  class='box' onkeypress=keyPress('"+filename+"','"+count+"')>"+val+"</textarea>");
	  }	
	showEditBox(filename, count);
}
function showEditBox(filename, count){
	var posX = $('#'+filename+''+count).position().left, posY = $('#'+filename+''+count).position().top;
		posX = (posX - 10)+"px";
		posY = (posY - 65)+"px";
		$("#edit"+count).css({left:posX,top:posY});
		$("#edit"+count).fadeIn(500);
		$("#edit"+count).select();
}

function closeEditBox(count, filename){
	var fullPath = document.getElementById("folderPath"+count).value;
	var renameVal = document.getElementById("edit"+count).value;
	if(renameVal){
		renameVal = renameVal.trim()
		 $.ajax({		
				url : "renameFolder.htm", 
				data: "folderPath="+fullPath+"&renameVal="+renameVal,
			    success : function(response) {
			    	$('#'+filename+''+count).html('');
		    	    $('#'+filename+''+count).html(renameVal);
		    		var folderPath = fullPath.substring(0, fullPath.lastIndexOf("\\"+name));
		    		document.getElementById("folderPath"+count).value = folderPath+"\\"+renameVal;
		    		document.getElementById("folderName"+count).value = renameVal;
		    		document.getElementById('folderId'+count).innerHTML = renameVal;
			    	systemMessage(response);
			    	$("#loading-div-background").hide(); 
	                $("#edit"+count).hide();
			    }
		 });
}
$("#edit"+count).hide();
}
function keyPress(filename, count){
	event = window.event;
    if(event.keyCode == 13){
    	var renameVal = jQuery.trim(document.getElementById("edit"+count).value);
    	if(renameVal){
    		renameFolder(renameVal,filename,count);
	    	$("#edit"+count).hide();
    	}else{
    		var val = $('#'+filename+''+count).text();
    		document.getElementById("edit"+count).value = val;
    		systemMessage('Folder name should not be empty');
    	}
     };
    return true;
}


function renameFolder(renameVal, filename, count){
	var fullPath = document.getElementById("folderPath"+count).value;
	$("#loading-div-background").show();
	if(renameVal){
		renameVal = renameVal.trim()
		 $.ajax({		
				url : "renameFolder.htm", 
				data: "folderPath="+fullPath+"&renameVal="+renameVal,
			    success : function(response) {
		    		$('#'+filename+''+count).html('');
		    		$('#'+filename+''+count).html(renameVal);
		    		var folderPath = fullPath.substring(0, fullPath.lastIndexOf("\\"));
		    		document.getElementById("folderPath"+count).value = folderPath+"\\"+renameVal;
		    		document.getElementById("folderName"+count).value = renameVal;
			    	systemMessage(response);
			    	$("#loading-div-background").hide();
				} 
		 });
	}
}

function deleteFolder(count){
	var fullPath = document.getElementById("folderPath"+count).value;
	if(confirm("Are you sure to delete entire folder?",function(status){
		if(status && fullPath){
			$("#loading-div-background").show();
			$.ajax({			
				url : "deleteFolder.htm", 
			    data: "folderPath="+fullPath,
			    success : function(response) {
			    	systemMessage(response);
			    	if(response == "Deleted Successfully")
			    		$("#row"+count).remove();
			    	$("#loading-div-background").hide();
				} 
			}); 
		}
	})); 
}
