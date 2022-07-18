function addUnits(){
		var classId = document.getElementById("classId").value;
		var gradeId = document.getElementById("gradeId").value;
		var left = (screen.width / 2) - (700 / 2);
		var top = (screen.height / 2) - (400 / 2);
			 jQuery.curCSS = jQuery.css;
			 $("#dialog").dialog({
					overflow: 'auto',
					dialogClass: 'no-close',
				    autoOpen: false,
					position: {my: "center", at: "center", of:window ,within: $("body") },
				    title: 'Create Unit',
				    draggable: true,
				    width : 450,
				    height : 300,
				    left: left,
				    top : top,
				    resizable : true,
				    modal : true,
				    close : function(){
				    	loadUnits();
		         }  
				});
			 $.ajax({			
					url : "addUnitsView.htm", 
				   data: "gradeId="+gradeId+"&classId="+classId,
				    success : function(data) {
				    	 $("#dialog").empty();
				    	 $("#dialog").append(data);
				    }  
				}); 
			 $("#dialog").dialog("open");
	}

    function loadUnits() {
		var gradeId = document.getElementById("gradeId").value;
		var classId = document.getElementById("classId").value;
		var regId = document.getElementById("regId").value;
		var unitContainer = $(document.getElementById('unitDiv'));
		$(unitContainer).empty();
		var getUnitsByGradeClassUserCallBack =  function(list){
			var length = list.length;
			if(length > 0){
				var len = parseInt(length)-1;
			var str = "<table class='des' border=0 width='50%'><tr><td width='100%'><div class='Divheads'><table align='center'> <tr> " +
			"<th width='50' align='right'>S.No</th>" +
			"<th width='250' align='center' style='padding-right:1.6em;'>Unit Name</th>" +
			"<th width='200' align='center'>Add/Edit Lessons</th>"+
			"<th width='150' align='center'>remove</th>"+
			"<th width='50' align='left'>&nbsp;&nbsp;</th></tr></table></div><div class='DivContents'><table><tr><td height='10'></td></tr>";
			var count = 0;
			for (i=len;i >= 0;i--){
				count = count+1;
				var sameUser;
				if(regId == list[i].userRegistration.regId)
					sameUser = true;
				else
					sameUser = false;
			    str +=  "<tr><td width='50' align='right'>"+count+" .</td>" +
			    		"<td width='280' align='center' style='padding-bottom: 0.8em;padding-top: 0.5em;'> <input type='hidden'  id='unitId"+i+"' name='unitId"+i+"' value='"+list[i].unitId+"' /><input type='hidden'  id='unitNo"+i+"' name='unitNo"+i+"' value='"+list[i].unitNo+"' /><input type='text' style='"+ (sameUser ? '' : ' pointer-events: none; color:#8B8B8B;')+"' id='unitName"+i+"' name='unitName"+i+"' value='"+list[i].unitName+"' onBlur=\"updateUnit('"+i+"')\" /></td>" +
			    		"<td width='200' align='center' ><a href='#' onclick='goToLessonsTab("+i+")' class='subButtons subButtonsWhite medium' style='text-decoration: none;'>Lessons</a></td>" +
						"<td width='150' align='center' style='padding-left:1em;cursor: hand; cursor: pointer;"+ (sameUser ? 'color:#CD0000;' : ' pointer-events: none; color:#8B8B8B;')+"'><i class='fa fa-times' aria-hidden=true style='cursor: hand; cursor: pointer;font-size: 25px;' onclick=\"removeUnit('"+list[i].unitId+"','"+i+"')\" ></i></td>" +
						"<td width='50' align='left'><div id='result"+i+"' align='center'/></td></tr>";
			  }
			    str += "<tr><td height='10'></td></tr></table></div></td></tr></table>";
			    $(unitContainer).append(str);
			    $("#loading-div-background").hide();

			    var uId = getStoredValue('unitId');
				if(uId)
					$('#previousBtn').show();
			}else{
				 $('#previousBtn').hide();
				 $(unitContainer).append("<span class='status-message'>Units Not Yet Created </span>");
				 $("#loading-div-background").hide();
			}
		    $('#unitDiv').show();
			$('#btAdd').show();
			 setFooterHeight();
		}
		
		
		if(classId > 0){
			 $("#loading-div-background").show();
			 curriculumService.getUnitsByGradeClassUser(gradeId,classId, {
				callback : getUnitsByGradeClassUserCallBack
			});
		}else{
			$('#btAdd').hide();
			return;
		}
	}
	function creatUnit(){
		var gradeId = document.getElementById('gradeId').value;
		var classId = document.getElementById('classId').value;
		var unitName = document.getElementById('unitName').value;
		
		var message = emptyVal(unitName);

		
		if(message != ''){
			systemMessage(message);
			document.getElementById('unitName').focus();
			return false;
		}
		
		var teacherId = 0;
		if(unitName){
					$.ajax({			
						url : "createunit.htm", 
					   data: "gradeId="+gradeId+"&classId="+classId+"&unitName="+encodeURIComponent(unitName),
					    success : function(data) {	
					    	 document.getElementById('unitName').value="";
					    	 systemMessage(data);
						}  
					}); 
		  }else{
			  return false;
		  }
	}

	function updateUnit(id){
		var gradeId = document.getElementById('gradeId').value;
		var classId = document.getElementById('classId').value;
		var unitId = document.getElementById('unitId'+id).value;
		var unitName = document.getElementById('unitName'+id).value;
		var unitNo = document.getElementById('unitNo'+id).value;
		
		var message = emptyVal(unitName);
		if(message != ''){
			systemMessage(message);
			document.getElementById('unitName'+id).focus();
			return false;
		}
		if(unitName){
				$.ajax({			
					url : "updateUnit.htm", 
				   data: "gradeId="+gradeId+"&classId="+classId+"&unitId="+unitId+"&unitName="+encodeURIComponent(unitName)+"&unitNo="+unitNo,
				    success : function(data) {	
				    	systemMessage(data);
					}  
				}); 
		}else{
			systemMessage("Unit Name should not be Empty","warn");
		}
	}
	
	function removeUnit(unitId,id){
		if(unitId){
			$.ajax({			
			   url : "removeUnit.htm", 
			   data: "unitId="+unitId,
			    success : function(data) {	
			    	systemMessage(data.status);
			    	$('#previousBtn').hide();
			    	storeValue("unitId",'');
			    	if(data.status != 'Unable to remove Unit')
			    	  loadUnits();
				}  
			}); 
	  }
	}
	
	var inc = 0;
	function addActivities(){
		var activitiesSize = parseInt(document.getElementById('activitiesSize').value);
		var mode = document.getElementById("mode").value;
		if(activitiesSize){
			inc = activitiesSize;
			count = activitiesSize;
			document.getElementById('activitiesSize').value = '';
		}else{
			count=count+1;
			$.ajax({			
				   url : "addActivity.htm", 
				   data: "count="+count+"&inc="+inc+"&mode="+mode,
				    success : function(data) {	
				    	inc=inc+1;
				    	var activitiesDiv = $('#activitiesDiv');
		        		$(activitiesDiv).append(data);
		        		$('#submitDiv').show();
		        		setIndex('activities','Activity');
		        		$('#activities').attr('style','overflow-y: auto ;border-style: groove;border-width: 1px;overflow-x: hidden ;max-height: 200px;min-height: 200px; width: 90%;');
				    	$('#activities').show();
		        		smoothScroll(document.getElementById(inc-1));
					}  
				}); 
		}
	}
	
	function createLesson(){
		var createLesson = $("#createLesson").serialize();
		var lessonName = document.getElementById("lessonName").value;
		var lessonObjective = document.getElementById("lessonDesc").value;
		if(lessonName && lessonObjective){
		$.ajax({
			   type: "POST",
			   url : "createLesson.htm", 
			   data: createLesson,
			    success : function(data) {
			   	 document.getElementById('lessonName').value="";
			   	 document.getElementById('lessonDesc').value="";
			   	 $('#activitiesDiv').html("");
			   	  inc = 0;
				  addActivities();
				  systemMessage(data);
				}  
			}); 
		}else{
			systemMessage("Please enter lesson name and objective/outcome of the lesson");
			if(!lessonName){
				document.getElementById("lessonName").focus();
			}
			else{
				document.getElementById("lessonDesc").focus();
			}
		}
	}
	function dynamicAdd(id){
		var classId = document.getElementById("classId").value;
		var gradeId = document.getElementById("gradeId").value;
		var unitId = document.getElementById("unitId").value;
		var lessonId = 0;
		var left = (screen.width / 2) - (700 / 2);
		var top = (screen.height / 2) - (400 / 2);
			 jQuery.curCSS = jQuery.css;
			 $("#dialog").dialog({
				    autoOpen: false,
					position: {my: "center", at: "center", of:window ,within: $("body") },
				    draggable: true,
				    width : 900,
				    height : 500,
				    left: left,
				    top : top,
				    resizable : true,
				    modal : true,
				    close : function(){
				    	count = 0;
				    	loadLessons();
		            }  
				});
			 if(id > -1){
			 	lessonId = document.getElementById('lessonId'+id).value; 
			 	$("#dialog").dialog({ title: "Edit Activities" });
			 }else{
				 $("#dialog").dialog({ title: "Create Lesson" });
			 }
			 $.ajax({			
					url : "addLessonsView.htm", 
				   data: "gradeId="+gradeId+"&classId="+classId+"&unitId="+unitId+"&lessonId="+lessonId,
				    success : function(data) {	
				    	$("#dialog").empty();
				    	$("#dialog").append(data);
				    	$("#dialog").dialog("open");
				    }
				}); 
			
	}
	
	function loadLessons(){
		$('#btAdd').show();
		var subjectId = document.getElementById("classId").value;
		var unitId = document.getElementById("unitId").value;
		var gradeId = document.getElementById("gradeId").value;
		var regId = document.getElementById("regId").value;
		var lessonContainer = $(document.getElementById('lessonDiv'));
    	$(lessonContainer).empty();
		if(subjectId=="select"){
			alert("Please select a subject");
			return;
		}
		if(unitId == "select" || unitId == "Select Unit"){
			return;
		}
		if(unitId > 0 && subjectId > 0 && gradeId > 0){
	        $.ajax({  
				url : "editLessonsView.htm", 
				data: "unitId=" + unitId, 
		       	success : function(data) { 
		       		if(!data.trim()){
		       			$('#backBtn').show();
		       			$('#previousBtn').hide();
		       		}else{
			       		$(lessonContainer).append(data);
		       		}	
		       	}  
		    });
		}
	}
	
	function removeLesson(id){
		var unitId = document.getElementById('unitId'+id).value;
	   	var lessonId = document.getElementById('lessonId'+id).value;
		$.ajax({			
			   url : "removeLesson.htm", 
			   data: "unitId="+unitId+"&lessonId="+lessonId,
			    success : function(data) {	
			    	systemMessage(data);
			    	storeValue("lessonId",'');
		    		$('#previousBtn').hide();
			    	loadLessons();
				}  
			}); 
		}
	
	function removeActivity(id){
		var activityId = document.getElementById('activityId'+id).value;
        if(activityId) {
			$.ajax({			
				   url : "removeActivity.htm", 
				   data: "activityId="+activityId,
				    success : function(data) {	
				    	systemMessage("removed");
				    	$("#div"+id).remove();
				    	inc--;
				    	var len = $("#activities div").length-1;
				 		if(len == 0){
				 			$('#activities').attr('style','border-style: none');
				 			$('#submitDiv').hide();
				 		}else{
				 			setIndex('activities','Activity')
				 		}
					}  
				});
        }else{
        	$("#div"+id).remove();
        	inc--;
        	var len = $("#activities div").length-1;
	 		if(len == 0){
	 			$('#activities').attr('style','border-style: none');
	 			$('#submitDiv').hide();
	 		}else{
	 			setIndex('activities','Activity');
	 		}
        }
	}
	
	function lessonAutoUpdate(id){
		var unitId = document.getElementById('unitId'+id).value;
	   	var lessonId = document.getElementById('lessonId'+id).value;
	   	var lessonName = document.getElementById('lessonName'+id).value;
	   	var lessonDesc = document.getElementById('lessonDesc'+id).value;
	   	
	   	if(lessonName == ''){
	   		alert('Enter a lesson name');
	   		return false;
	   	}else if(lessonDesc == ''){
	   		alert('Enter a lesson objective');
	   		return false;
	   	}
	   	$.ajax({	
			   url : "updateLesson.htm", 
			   data: "unitId="+unitId+"&lessonId="+lessonId+"&lessonName="+encodeURIComponent(lessonName)+"&lessonDesc="+encodeURIComponent(lessonDesc),
			    success : function(data) {	
			    	systemMessage(data);
				}  
		 });
	}
	
	function updateActivity(obj){
		var activityId = document.getElementById('activityId'+parseInt(obj.id)).value;
		if(!activityId)
			activityId = 0
		var lessonId = document.getElementById('lessonId').value;
		var activityDesc = document.getElementById(obj.id).value;
		if(activityDesc){
		 $.ajax({			
			   url : "saveActivity.htm", 
			   data: "activityId="+activityId+"&activityDesc="+encodeURIComponent(activityDesc)+"&lessonId="+lessonId,
			    success : function(data) {	
			    	if(data.status == 'Failed'){
			    		systemMessage(data.status);
		    		}else{
				    	document.getElementById('activityId'+parseInt(obj.id)).value = data.activityId;
				    	systemMessage(data.status);
		    		}
				}  
			});
		}
	}
	
	 function setIndex(divId,name){
		  var cnt = 1;
			 var div = document.getElementById(divId);
			 $(div).find('input:hidden').each(function() {
				if($(this)[0].name == 'currentId')
					cnt = currentCounter;
			   });
		  var arr = $("#"+divId).children("div").map(function(){
	    	    return this.id;
	    	}).get();
		  if(arr.length == 0){
			   arr = arr = $("#"+divId+" div").map(function(){
				   return this.id;
		    	}).get();
		   }
		    var cnt = 1;
	    	for(i = 0;i<arr.length; i++){
	            if(arr[i] != (divId+'Div')){
		    		$("#"+arr[i]+" span").html('');
		    		$("#"+arr[i]+" span").text(name+" "+cnt+" . ");
		    		cnt = cnt+1;
	            }
	        } 
	    	currentCounter = cnt;
	  }
	  
	  function showQuestions(id){
			var subjectId = document.getElementById("classId").value;
			var gradeId = document.getElementById("gradeId").value;
			var assignmentTypeId = document.getElementById("assignmentTypeId").value;
			if(assignmentTypeId!=8 && assignmentTypeId!=20 && assignmentTypeId!=19){
			var unitId = document.getElementById("unitId").value;
			var lessonId = document.getElementById("lessonId").value;
			if(unitId =="select" || unitId == "Select Unit"){
				alert("Please select a unit");
				return;
			}
			if(lessonId =="select" || lessonId == "Select Lesson"){
				alert("Please select a lesson");
				return;
			}
			}
			var assignmentValue = $('#assignmentTypeId :selected').text();
			var usedFor = document.getElementById("usedFor").value;
			var tab = document.getElementById('tab').value;

			if(subjectId =="select"){
				alert("Please select a subject");
				return;
			}
			
			if(assignmentTypeId =="select" || assignmentTypeId == "Assignment Type"){
				alert("Please select a assignment type");
				return;
			}
			var count = 1;
			var noOfOptions = 1;
			
			var optionId = document.getElementById("optionId").style.visibility;
			if(assignmentTypeId!=8 && assignmentTypeId!=20 && assignmentTypeId!=19){
	        if(count>0 && gradeId > 0 && subjectId > 0 && unitId > 0 && lessonId > 0 && assignmentTypeId > 0)
	        	showAssessmentQuestions(noOfOptions,assignmentTypeId,id,assignmentValue,count,usedFor,tab,lessonId,gradeId);
	        else
	        	alert("Please fill all the filters");
	        
			}else{
				if(count>0 && gradeId > 0 && subjectId > 0 && assignmentTypeId > 0)
		        	showAssessmentQuestions(noOfOptions,assignmentTypeId,id,assignmentValue,count,usedFor,tab,-1,gradeId);
		         else
		        	alert("Please fill all the filters");
		        	
			}
		}
	function showAssessmentQuestions(noOfOptions,assignmentTypeId,id,assignmentValue,count,usedFor,tab,lessonId,gradeId){
		if(optionId == "visible"){
			if(noOfOptions <= 0 || isNaN(noOfOptions)){
				alert("Please enter number Of options");
				return;
			}
		}else{
			noOfOptions = 1;
		}
    	//var iframe = $('#iframe');
		var left = (screen.width / 2) - (700 / 2);
		var top = (screen.height / 2) - (400 / 2);
			 jQuery.curCSS = jQuery.css;
			 $("#dialog").dialog({
				    overflow:'auto',
					dialogClass: 'no-close',
				    autoOpen: false,
				    draggable: true,
				    left: (window.innerWidth - $(this).parent().parent().outerWidth())/2,
				    top : (window.innerHeight - $(this).parent().parent().outerHeight())/2,
				    resizable : true,
				    modal : true,
				    open: function() {
				    	if(assignmentTypeId == 14 && id > 0){
				    	var filename = document.getElementById("filename"+id).value;
				    	 if(filename)
				    		 document.getElementById("jacFileName").value= filename;
				    	}
				    },
				    close : function(){
			    	if(assignmentTypeId == 14 && id > 0)
			    		document.getElementById("jacFileName").value= "";
				    loadQuestions();
		         }  
				});
			    if(id > -1){
				 	$("#dialog").dialog({ title: "Edit "+assignmentValue });
				 }else{
					$("#dialog").dialog({ title: "Create "+assignmentValue });
				 }
			 
			 if(assignmentTypeId == 1 || assignmentTypeId == 2  || assignmentTypeId == 6){
			   $("#dialog").dialog({ height: 340, width : 650});
			 }else if(assignmentTypeId == 3){
    			   $("#dialog").dialog({ height: 510, width : 560});
			 }else if(assignmentTypeId == 4){
    			   $("#dialog").dialog({ height: 380, width : 630});
			 }else if(assignmentTypeId == 5){
    			   $("#dialog").dialog({ height: 360, width : 550});
			 }else if(assignmentTypeId == 7){
				   $("#dialog").dialog({ height: 700, width : 650});
			 }else if(assignmentTypeId == 8 || assignmentTypeId == 20){
				   $("#dialog").dialog({ height: 450, width : 750});
			 }else if(assignmentTypeId == 13){
			   if(id > -1){
				    $("#dialog").dialog({ height: 810, width : 1060});
				 }else{
					 $("#dialog").dialog({ height: 770, width : 1060});
				 }
			 }else if(assignmentTypeId == 14){
    			   $("#dialog").dialog({ height: 770, width : 1100});
    		 }
			 else if(assignmentTypeId == 19){
				 $("#dialog").dialog({ height: 770, width : 700});
			 }
			 var iframe = $("#iframe");
			 iframe.attr('src', "assessmentTypeView.htm?noOfQuestions="+count+"&assignmentTypeId="+assignmentTypeId+"&noOfOptions="+noOfOptions+"&assignmentValue="+assignmentValue+"&usedFor="+usedFor+"&tab="+tab+"&lessonId="+lessonId+"&questionId="+id+"&gradeId="+gradeId);
		     $("#dialog").dialog("open");
	}
	function createQuestions(){
	    var questionId = document.getElementById("question0").value;
		var assignmentTypeId = document.getElementById("assignmentTypeId").value;
		var gradeId=document.getElementById("gradeId").value;
		if(questionId){			
			var status = formValidation(assignmentTypeId);
			if(status == true){
				var formObj = document.getElementById("questionsForm");				
				var formData = new FormData(formObj);
				$.ajax({
					url: 'createAssessments.htm',
					type: 'POST',
					data: formData,
					mimeType:"multipart/form-data",
					contentType: false,
					cache: false,
					processData:false,
					success: function(data){
						 $('#questionsForm')[0].reset();
						 $("#rubric").html('');
						 $("#jackTemplateDetails").html('');
						 $("#optionsDiv").html('');
						 $('#submitDiv').hide();
						 $("#status").html('');
						  count = 0;
						  counter = 1;
						  currentDiv = '';
						  currentCounter = 0;
						  systemMessage(data);
						 return false;
					}
				});	
		    }
		}else{
			systemMessage('Question should not be empty !!');
			document.getElementById("question0").focus();
			return false;
		}
	 }
	
	function formValidation(assignmentTypeId){
		if(typeof currentCounter === "undefined")
			currentCounter = 1;
		if(assignmentTypeId == 13){
			 var ptSubjectArea = document.getElementById("ptSubjectArea").value;
			 var ptDirections = document.getElementById("ptDirections").value;
			  var rubricTypeId = document.getElementById("rubricTypeId").value;
			  var rubricScalingId = document.getElementById("rubricScalingId").value;
			   if (!ptSubjectArea){
				   systemMessage("Please enter subject area !!");
			           return false;  
			        }
		        if (!ptDirections){
		        	systemMessage("Please enter direction !!");
			           return false;  
			        }
				if (!rubricTypeId || !rubricScalingId){
					systemMessage("Please select rubric values !!");
		           return false;  
		        }
				return true;  
		}
		if(assignmentTypeId==7){
			 if(currentCounter == 1){
				 systemMessage("Please add aleast one Option");
			     return false;
			 }else{
				for(var i =0 ; i< count; i++){
					for(var j=1 ; j<=3; j++){
						if(document.getElementById("option"+j+":"+i)){
							var option = document.getElementById("option"+j+":"+i).value;
							if(!option){
								systemMessage("Options are mandatory");
								document.getElementById("option"+j+":"+i).focus();
								return false;
							}
						}
					}
					if(document.getElementById("answer:"+i)){
						var answer = document.getElementById("answer:"+i).value;
						if(!answer){
							systemMessage("Please set Correct Answers");
							return false;
						}
					}
				}
				return true;  
			 }
		 }
		 if(assignmentTypeId == 19){
			 var passage = document.getElementById("passage").value;
			 if(!passage){
				systemMessage("Comprehension Question is mandatory");
				return false;
			  }
			 if(currentCounter == 1){
				 systemMessage("Please add aleast one Question");
			     return false;
			 }
			 for(var i =0 ; i< count; i++){
				 if(document.getElementById("assignmentTypeId"+i)){
					 var assignTypeId = document.getElementById("assignmentTypeId"+i).value;				 
					 if(assignTypeId == 2 || assignTypeId == 3 || assignTypeId == 4 || assignTypeId == 5){
						 if(document.getElementById("questions:"+i)){
								var question = document.getElementById("questions:"+i).value;
								if(!question){
									systemMessage('Question should not be empty !!');
									document.getElementById("questions:"+i).focus();
									return false;
								}
							 }
					 }					 
					if(assignTypeId == 3){
						 var numOfOptions = document.getElementById('numOfOptions'+i).value;
							for(var j =1 ; j<=numOfOptions; j++){
								if(document.getElementById("option"+j+":"+i)){
									var option = document.getElementById("option"+j+":"+i).value;
									if(!option){
										systemMessage("Options are mandatory");
										document.getElementById("option"+j+":"+i).focus();
										return false;
									}
								}
							}						 
					 }
					 if(assignTypeId == 3 || assignTypeId == 4 || assignTypeId == 5){						
							if(document.getElementById("answer:"+i)){
								var answer = document.getElementById("answer:"+i).value;
								if(!answer){
									systemMessage('Answer should not be empty !!');
									document.getElementById("answer:"+i).focus();
								 return false;
								}
							}
						}
				 }				 
			 }			 
		 }
		 if(assignmentTypeId==14){
			 var file = document.getElementById("file").value;
			 if(file==''){
				 systemMessage("Please select a file");
			     return false;
			 }
			 return true; 
		 }
		if(assignmentTypeId == 8 || assignmentTypeId == 20){
			 $("#audioVoiceDiv").html('');
			 $("#recordRetellDiv").html('');
			 var title = document.getElementById("titleName").value;
			 var bGradeLevel = document.getElementById("BGradeLevel").value;
			 if(title == ''){
				 systemMessage('Please enter a title');
				 document.getElementById("titleName").focus();
				 return false;
			 }
			 if(bGradeLevel == ''){
				 systemMessage('Please enter a grade level');
				 document.getElementById("BGradeLevel").focus();
				 return false;
			 }
			 if(bGradeLevel.length >20){
				 systemMessage('Cannot exceeded 20 characters');
				 document.getElementById("BGradeLevel").focus();
				 return false;
			 }
			 return true;  
		}
		if(assignmentTypeId == 3){	
			var numOfOptions = document.getElementById('numOfOptions').value;
			for(var i =1 ; i<=numOfOptions; i++){
				if(document.getElementById("option"+i)){
					var option = document.getElementById("option"+i).value;
					if(!option){
						systemMessage("Options are mandatory");
						document.getElementById('option'+i).focus();
						return false;
					}
				}
			}
			var answer = document.getElementById("answer").value;
			if(!answer){
				systemMessage('Answer should not be empty !!');
				document.getElementById("answer").focus();
			 return false;
			}
		}
		if(assignmentTypeId == 4){
			var id;
			if(document.getElementById("id"))
				id = document.getElementById("id").value;
			else
				id= -1;
			if(document.getElementById("answer"+id)){
				var answer = document.getElementById("answer"+id).value;
				if(!answer){
					systemMessage('Answer should not be empty !!');
					document.getElementById("answer"+id).focus();
				 return false;
				}
			}
		}
		return true;  
	}
	function createTitle(){
	    var valid=true;
	    var title = document.getElementById("title").value;
		var format = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/;

		if(format.test(title)){
		  systemMessage("special characters not allowed");
		  document.getElementById("title").focus();
		  valid=false;
		  return false;
		  
		 }
		if(title && valid){
		  var jackTemplateDetails = $(document.getElementById('jackTemplateDetails'));
		  $(jackTemplateDetails).append("<input type='hidden' name='jacTemplate["+count+"].jacTemplateId' id='jacTemplateId"+count+"' value='0'/><table width='100%' id='table"+count+"'><tr><td style='padding-left: 1cm;width:80%;'><div id='titleDiv"+count+"'><h3><input type='text' id='title"+count+"' name='jacTemplate["+count+"].titleName' value='"+title+"' width='100%' class='title'  style='width:200px;font-family:Segoe UI, Frutiger,Frutiger Linotype,Dejavu Sans,Helvetica Neue, Arial, sans-serif;font-size:14px;color:#0057AF;vertical-align:middle;font-weight: 500;margin-top:-0.4em;' onblur=updateTitle("+count+") onblur=\"$('#title"+count+"').attr('class', 'title'); return false;\" /></h3></div></td><td><i class='fa fa-times' aria-hidden='true' style='cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;' onclick='removeTitle(\""+count+"\")'></i>" +
		  		"</td><td><div id='resultDiv"+count+"' ></div></td></tr></table>");
		   var titleDivContainer = $(document.getElementById("titleDiv"+count));
		   $(titleDivContainer).append(
				"<div id='contentDiv"+count+"' style='min-height:200px;'><table width='100%'><tr><td align='right' width='10%' height='40'>" +
		   		"<input type=button class='button_green' id='createAnswer"+count+"' value='Add Answers' name='createAnswer"+count+"' style='font-size: 0.75em;' onclick='getDynamicTextBox(\""+count+"\")' /></td></tr>" +
		   		"<tr><td width='80%' align='center'><div id='answersDiv"+count+"' ><input type=hidden id='currentId"+count+"' name='currentId'/><input type=hidden id='presentCounter"+count+"' name='presentCounter"+count+"'/></div></td></tr>" +
		   	    "</table></div>");
		   $(function () {
				$("#titleDiv"+count).accordion({
					collapsible : true,
					active : false
				});
			});
		   addOrUpdateJacTemplate(count);
		   count = parseInt(count)+1;
		   document.getElementById("title").value="";
		   $('#submitDiv').show();
		  }else{
			  systemMessage("Please enter a title");
			  document.getElementById("title").focus();
		  }
	  }

   function getDynamicTextBox(count) {
	   if(currentDiv < 0){
		   counter = 0;
	       currentDiv = count;
	    document.getElementById("presentCounter"+count).value = 0;
	   }else if(currentDiv != count){
		  currentDiv = count;
		  var presentCounter =  document.getElementById("presentCounter"+ parseInt(count)).value;
		  if(presentCounter){
			  counter =  parseInt(presentCounter)+1;
			  document.getElementById("presentCounter"+count).value = parseInt(presentCounter)+1;
		  }else{
			  counter = 0;
			  document.getElementById("presentCounter"+count).value = 0;
		  }
	   }else{
		  var presentCounter =  document.getElementById("presentCounter"+ parseInt(count)).value;
		  if(presentCounter){
			  counter =  parseInt(presentCounter)+1;
			  document.getElementById("presentCounter"+count).value = parseInt(presentCounter)+1;
		  }else{
			  counter = 0;
			  document.getElementById("presentCounter"+count).value = 0;
		  }
	   }
		  
	  var answersDiv = $(document.getElementById("answersDiv"+count));
	  var str = "<div id='div"+count+""+counter+"' style='height:40px;' class=tabtxt><input type='hidden' id='questionId"+count+""+counter+"' name='jacTemplate["+count+"].questionsList["+counter+"].questionId' value='0' /> <span id='span"+count+""+counter+"'></span>&nbsp;&nbsp;<input class='titleTextBox' id='answer"+count+""+counter+"' name='jacTemplate["+count+"].questionsList["+counter+"].answer' type='text'  size='25' required='required' pattern='[A-Za-z0-9\s]+' onblur='addOrUpdateAnswer(\""+count+"\",\""+counter+"\")' />&nbsp;&nbsp;<i class='fa fa-times' aria-hidden='true' style='cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;' onclick='removeAnswer(\""+count+"\",\""+counter+"\")'></i></div>";
	  $(answersDiv).append(str);
	  setIndex("answersDiv"+count,'Answer');
	  document.getElementById("currentId"+count).value = currentCounter;
	  $(document.getElementById("answer"+count+""+counter)).focus();
	  counter = parseInt(counter)+1;
	}
   
   function addOrUpdateJacTemplate(count){
		var mode = document.getElementById("mode").value;
	   	if(mode == 'Edit'){
   	    	var jacTemplateId = document.getElementById("jacTemplateId"+count).value;
			var jacQuestionFileId = document.getElementById("jacQuestionFileId").value;
			var titleName = document.getElementById("title"+count).value;
		    var valid=true;
			var format = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/;
			if(format.test(titleName)){
			  systemMessage("special characters not allowed");
			  document.getElementById("title"+count).focus();
			  valid=false;
			  return false;				  
			 }
			if(titleName && valid){
		   		$.ajax({  
		   			url : "saveJacTemplate.htm", 
		   			data: "jacQuestionFileId="+jacQuestionFileId+"&titleName="+titleName+"&jacTemplateId="+jacTemplateId, 
		   	    	success : function(data) { 
		   	    		if(jacTemplateId == 0){
		   	    			document.getElementById("jacTemplateId"+count).value = data.jacTemplateId;
		   	    		}
		   	    		systemMessage(data.status);
		   	    	}  
		   		});
			}
	   	}
	}
   function removeTitle(count){
	   	var mode = document.getElementById("mode").value;
	   	if(mode == 'Edit'){
	   		var jacTemplateId = document.getElementById("jacTemplateId"+count).value;
	   		$.ajax({  
	   			url : "deleteJacTemplate.htm", 
	   			data: "jacTemplateId=" + jacTemplateId, 
	   	    	success : function(data) { 
	   	    		systemMessage(data.delTitStatus);
	   	    		if(data.delTitStatus!="Unable to delete. Its already assigned"){
			    		$("#table"+count).remove();
			    		var len = $("#jackTemplateDetails div").length;
					   	if(len == 0)
					   	  $('#submitDiv').hide();
			    	}
	   	    	}  
	   		}); 
	   	}else{
	   		$("#table"+count).remove();
		   	var len = $("#jackTemplateDetails div").length;
		   	if(len == 0)
		   	  $('#submitDiv').hide();
	   	}
	}
		   
	function addOrUpdateAnswer(count,counter){
		var valid=true;
		var mode = document.getElementById("mode").value;
		var answer = document.getElementById("answer"+count+""+counter).value;
		var format = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/;

		if(format.test(answer)){
		  systemMessage("special characters not allowed");
		  document.getElementById("answer"+count+""+counter).focus();
		  valid=false;
		  return false;
		  
		}

		
    	if(mode == 'Edit' && valid){
    		
    		if(answer){
				var usedFor = document.getElementById("usedFor").value;
				var lessonId = document.getElementById("lessonId").value;
				var assignmentTypeId = document.getElementById("assignmentTypeId").value;
				var jacTemplateId = document.getElementById("jacTemplateId"+count).value;
				var questionId = document.getElementById("questionId"+count+""+counter).value;
				$("#jac-loading-div-background").show();
	    		$.ajax({  
	    			url : "saveQuestion.htm", 
	    			data: "assignmentTypeId="+assignmentTypeId+"&usedFor="+usedFor+"&lessonId="+lessonId+"&answer="+answer+"&jacTemplateId="+jacTemplateId+"&questionId="+questionId, 
	    	    	success : function(data) { 
	    	    		if(questionId == 0){
	    	    			document.getElementById("questionId"+count+""+counter).value = data.questionId;
	    	    		}
	    	    		systemMessage(data.status);
	    	    		$("#jac-loading-div-background").hide(); 
	    	    	}  
	    		});
    		}
    	}
	}

	function removeAnswer(count,counter){
		var mode = document.getElementById("mode").value;
		if(mode == 'Edit'){
    		var questionId = document.getElementById("questionId"+count+""+counter).value;
    		$.ajax({  
    			url : "deleteQuestion.htm", 
    			data: "questionId=" + questionId, 
    	    	success : function(data) { 
    	    		systemMessage(data.status);
    	    		if(data.status != "Failed"){
				    	$("#div"+count+""+counter).remove(); 
				    		setIndex("answersDiv"+count,'Answer');
				     		document.getElementById("currentId"+count).value = currentCounter;
				     		counter = parseInt(counter)-1;
				    }
    	    	}  
    		});
    	}else
    		{
    		$("#div"+count+""+counter).remove();
    		setIndex("answersDiv"+count,'Answer');
     		document.getElementById("currentId"+count).value = currentCounter;
     		counter = parseInt(counter)-1;
    		}
   		
	}
	

	function loadQuestions(){
			var subjectId = document.getElementById("classId").value;
			var usedFor = document.getElementById("usedFor").value;
			var assignmentTypeId = document.getElementById("assignmentTypeId").value;
			if(assignmentTypeId!=8 && assignmentTypeId!=19 && assignmentTypeId!=20){
			var lessonId = document.getElementById("lessonId").value;
			var unitId = document.getElementById("unitId").value;
			}else{
				var lessonId=0;
			}
			var gradeId=document.getElementById("gradeId").value;
			var questionContainer = $(document.getElementById('questionDiv'));
			var answerContainer = $(document.getElementById('answerDiv'));
						
			$(questionContainer).empty();  
	    	$(answerContainer).empty();  
	    	if(assignmentTypeId =="select" || assignmentTypeId == "Assignment Type"){
				$('#btAdd').hide();
				return;
			}else{
				$('#btAdd').show();
				$('#backBtn').show();
			}
	    	if(assignmentTypeId!=8 && assignmentTypeId!=19 && assignmentTypeId!=20){
	    		if(subjectId > 0 && unitId > 0 && lessonId>0){
		    		viewTypeOfQuestions(assignmentTypeId,lessonId,gradeId);
		    	}else{
		    		alert("Please fill the filters");
					return;		
		     }
	    	}else{
	    		if(subjectId > 0){
		    		viewTypeOfQuestions(assignmentTypeId,lessonId,gradeId);
		    	}else{
		    		alert("Please fill the filters");
					return;		
		    }
		    }
		}
		
	function viewTypeOfQuestions(assignmentTypeId,lessonId,gradeId){
		var questionContainer = $(document.getElementById('questionDiv'));
		var usedFor = document.getElementById("usedFor").value;
		$.ajax({  
			url : "editAssessmentTypeView.htm", 
			data: "assignmentTypeId=" + assignmentTypeId + "&lessonId=" + lessonId + "&usedFor=" + usedFor+"&gradeId="+gradeId, 
        	success : function(data) { 
        		$(questionContainer).append(data); 
        	}  
    	}); 
	}
var isAlreadyAssigned = "";
	function updateQuestion(id){
		var assignmentTypeId = document.getElementById("assignmentTypeId").value;
		var gradeId=document.getElementById("gradeId").value;
		var question= "";		
		if(assignmentTypeId == 7 || assignmentTypeId == 19){
			if(id == 0){
				question = document.getElementById("question"+id).value;
				document.getElementById("id").value = id;
				//id = "-1";
			}else{
				var arr = id.split(':');
				question = document.getElementById("questionId"+arr[1]).value;
				document.getElementById("id").value = arr[1];
				id=arr[1];
			}
		}else{
			 question = document.getElementById("question"+id).value;
			 document.getElementById("id").value = id;
		}
		if(question){
			var status = formValidation(assignmentTypeId);
			if(status == true){
			if(assignmentTypeId == 4){
				var answer = document.getElementById("answer"+id).value;
				if(!answer){
				alert('Answer should not be empty !!');
				 return false;
				}
			}
			 var formObj = document.getElementById("questionsForm");
			 var assignmentTypeId = document.getElementById("assignmentTypeId").value;
			 var formData = new FormData(formObj);
			if((assignmentTypeId == 7) && !isAlreadyAssigned){
				checkAlreadyAssigned(formData, id, "update");
			 }else{
					$.ajax({
						url: 'updateAssessments.htm',
						data: formData,
						type: 'POST',
						mimeType:"multipart/form-data",
						contentType: false,
						cache: false,
						processData:false,
						success: function(data){
							 var parsedData = JSON.parse(data);
							 if(assignmentTypeId == 7  || assignmentTypeId == 19){
								 if(document.getElementById("questionId"+id))
									  $("#questionId"+id).val(parsedData.questionId)
							 }
							 if(assignmentTypeId == 13){
								 var questionId = document.getElementById("questionId").value;	 
								 showPtaskFiles(parsedData,questionId);
							 }
							 if(assignmentTypeId == 14){
								 var jacQuestionFileId = document.getElementById("jacQuestionFileId").value;
								 var fileStr = "<a href='assessmentsFileDownload.htm?questionId="+jacQuestionFileId+"&fileName="+parsedData.filename+"&type=JAC_Template'>"+parsedData.filename+"</a>&nbsp;&nbsp;";
								 var filesDivContainer = $(document.getElementById('filesDiv'));
									$(filesDivContainer).empty();
									$(filesDivContainer).append(fileStr);
									$("#file").val("");
									if(parsedData.filename)
										window.parent.$('#questionsForm')[0].jacFileName.value = parsedData.filename;
									    document.getElementById("jacFileName").value= parsedData.filename;
									    systemMessage(parsedData.status);
							 }else{
								 systemMessage(parsedData.status);
							 }
						}
				});
		}
	  }
	 }else{
	    systemMessage('Question should not be empty !!');
		document.getElementById("question"+id).focus();
		return false;
	 }
	}
	
	function checkAlreadyAssigned(formData, id, action){
		
	 var formObj = document.getElementById("questionsForm");
	 var formData = new FormData(formObj);
		$.ajax({
			url: 'checkAlreadyAssigned.htm',
			data: formData,
			type: 'POST',
			mimeType:"multipart/form-data",
			contentType: false,
			cache: false,
			processData:false,
			success: function(data){
				 var parsedData = JSON.parse(data);
				 if(parsedData.isAlreadyAssigned){
		     		if(action == "update"){ 
		     			 alert("Cannot update. Its already assigned");
						 return false;
						}else if(action == "remove"){
							 alert("Cannot delete. Its already assigned");
							 return false;
						 
						}else if(action == "add"){
								 isAlreadyAssigned = "true";
								 document.getElementById("assignmentId").value = parsedData.assignmentId;
						         addComprehensionOptions();
						         isAlreadyAssigned = "";
						         document.getElementById("assignmentId").value = "";
						}
					 else{
						 document.getElementById("assignmentId").value = "";
						 isAlreadyAssigned = "";
						 return false;
					 }
				 }else{
					 document.getElementById("assignmentId").value = "";
					 isAlreadyAssigned = "false";
					if(action == "update"){ 
						 id = "question:"+id;
						 updateQuestion(id);
					}else if(action == "remove"){
						 removeComprehensionSubQuestion(id);
					}else if(action == "add"){
						addComprehensionOptions();
					}
				 }
			}
		});
	}
	
	function showPtaskFiles(parsedData,questionId){
		var fileStr = "";
		var index = 0;
		if(parsedData.ptaskFiles){
			for(i = 0;i<parsedData.ptaskFiles.length; i++){
				if(parsedData.ptaskFiles[i].filename){
					index = index+1;
					fileStr += "<lable id='file"+parsedData.ptaskFiles[i].fileId+"'>&nbsp;&nbsp;"+(index)+".&nbsp;<a href='assessmentsFileDownload.htm?questionId="+questionId+"&fileName="+parsedData.ptaskFiles[i].filename+"&type=Performance_Tests'>"+parsedData.ptaskFiles[i].filename+"</a>&nbsp;&nbsp;"+
					"<i class='fa fa-times' aria-hidden='true' style='cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;' onclick='deletePerformancefile("+parsedData.ptaskFiles[i].fileId+","+questionId+")'></i></lable>";
				}
			}	
			var filesDivContainer = $(document.getElementById('filesDiv'));
			$(filesDivContainer).empty();
			$(filesDivContainer).append(fileStr);
			$("#file").val("");
			$("#noOfFiles").val("");
		}
	}
	
	function removeAssessments(id){		
		 if(confirm("Are you sure to delete?",function(status){
			if(status){
				document.getElementById("id").value = id;
				var formObj = document.getElementById("questionsForm");
				var formData = new FormData(formObj);
				$.ajax({
					url: 'removeAssessments.htm',
					type: 'POST',
					data: formData,
					mimeType:"multipart/form-data",
					contentType: false,
					cache: false,
					processData:false,
					success: function(data){
						if(data != 'Unable to Remove'){
				    		loadQuestions();
				    	}
						systemMessage(data);
					}
				});	
			}
		})); 
	}
	
	function deletePerformancefile(fileId,questionId){
		if(fileId && questionId){
			$.ajax({  
				url : "deletePerformancefile.htm", 
				data: "fileId=" + fileId+"&questionId=" + questionId, 
		    	success : function(data) { 
		    		if(data.status == true){
		    			$('#file'+fileId).hide();
		    			 showPtaskFiles(data,questionId);
		    		}
		    	}  
			}); 
		}
	}
	function filesCount(fileID,filesUploadCount){
	    var files = $('#'+fileID).prop("files");
	    var names = $.map(files, function(val) { return val.name; });
	    document.getElementById(filesUploadCount).value = files.length;
	}

	function showRubric(qNumber) {
		var rubType = document.getElementById("rubricTypeId").value;
		var mode = document.getElementById("mode").value;
		var rubScale = document.getElementById("rubricScalingId");
		var rubricContainer = document.getElementById("rubric");
		$(rubricContainer).empty();
		if(rubScale.value == ""){
			return false;
		}
		var rubScore = $("option:selected", rubScale).text();
		if(rubType > 0){
			$.ajax({  
				url : "showRubric.htm", 
				data: "rubType=" + rubType + "&rubScore=" + rubScore + "&qNumber=" + qNumber+"&mode=" + mode, 
		    	success : function(data) { 
		    		$(rubricContainer).append(data);
		    		if(mode == 'Edit'){
		    		 	updateQuestion(0); 
		    		}
		    	}  
			}); 
		}else{
			alert("Please select type of rubric");
			document.getElementById("rubricScalingId").value = "";
		}
	}
	
	function updateTitle(id){
		var titleName = $('#title'+id).val();
		var format = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/;
		if(format.test(titleName)){
		  systemMessage("special characters not allowed");
		  document.getElementById("title"+id).focus();
		}else{
			$('#title'+id).attr('class', '');
		}
		
	}
	
    function goToLessonsTab(id){
       if(id > -1){
    	 var unitId = document.getElementById('unitId'+id).value;
    	 storeValue('unitId', unitId+'_temp');
    	}
    	window.location.href = 'addLessonstoUnit.htm'; 
    }

	function backToUnitsTab(){
    	window.location.href = 'curriculumPlan.htm'; 
	}
	
	 function goToAssessmentsTab(id,currentTab){
		   var url = "";
		    if(!currentTab)
		    	currentTab = getStoredValue('currentTab');
	    	if(currentTab == 'assessments')
	    		url = 'assessments.htm';
	    	else if(currentTab == 'rti')
	    		url = 'rti.htm';
	    	else if(currentTab == 'homeworks')
	    		url = 'homeworks.htm';

	    	if(id > -1){
		    	var lessonId = document.getElementById('lessonId'+id).value;
		    	if(lessonId)
		    		storeValue('lessonId', lessonId+'_temp');
	    	}
	    	if(currentTab)
	    		storeValue('currentTab', currentTab);
			if(url){
	    		window.location.href = url; 
			}
	  }
	 function backToLessonsTab(){
	    var lessonId = document.getElementById('lessonId').value;
	    if(lessonId)
	    	storeValue('lessonId', lessonId+'_temp');
    	window.location.href = 'addLessonstoUnit.htm'; 
	}
	 
	function addOptions(){
		var optionsDiv = $(document.getElementById("optionsDiv"));
		var mode = document.getElementById("mode").value;
		currentCounter = 0;
		var str = "<div id='div"+count+"'><input type='hidden' id='questionId"+count+"' name='subQuestions[0].questionses["+count+"].questionId' value='0' required='required'><table width='100%'><tr><td align='left'><b><span id='span"+count+"' style='padding-top:0.2em;' class='tabtxt'></b></td></tr>"+
		"<tr><td width='300' align='left'>&nbsp;&nbsp;option 1.&nbsp;<input type='text' name='subQuestions[0].questionses["+count+"].option1' id='option1:"+count+"' onblur="+((mode == 'Edit') ? "updateQuestion(this.id)" :"")+" required='required'  /></td></tr>"+
	    "<tr><td width='300' align='left'>&nbsp;&nbsp;option 2.&nbsp;<input type='text' name='subQuestions[0].questionses["+count+"].option2' id='option2:"+count+"' onblur="+((mode == 'Edit') ? "updateQuestion(this.id)" :"")+" required='required'  /></td></tr>"+
	    "<tr><td width='300' align='left'>&nbsp;&nbsp;option 3.&nbsp;<input type='text' name='subQuestions[0].questionses["+count+"].option3' id='option3:"+count+"' onblur="+((mode == 'Edit') ? "updateQuestion(this.id)" :"")+" required='required'  /></td></tr>"+
	    "<tr><td height=5 colSpan=3></td></tr>"+
	    "<tr><td width='300' align='left'>Correct Answer.&nbsp;"+
		"<select name='subQuestions[0].questionses["+count+"].answer' required='required' id='answer:"+count+"' onchange="+((mode == 'Edit') ? "updateQuestion(this.id)" :"")+">"+
				"<option value=''>Select Option</option>"+
				"<option value='option1'>Option 1</option>"+
				"<option value='option2'>Option 2</option>"+
				"<option value='option3'>Option 3</option>"+
		"<select></td>" +
		"<td align='left'><i class='fa fa-times' aria-hidden='true' style='cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;' onclick='removeOption("+count+")'></i></td>"+
		"<td id='resultDiv"+count+"' align='left' class='status-message'></td></tr>"+
		"<tr><td height=5 colSpan=3></td></tr></table></div>";
		$(optionsDiv).append(str);
		setIndex("optionsDiv",'Options for blank');
		smoothScroll(document.getElementById("div"+count));
		count = parseInt(count)+1;
	} 
	
	function removeOption(id){
		var mode = document.getElementById("mode").value;
    	if(mode == 'Edit'){
    		var questionId = document.getElementById("questionId"+id).value;
	    	if(questionId > 0){
	    		if(($("#optionsDiv" ).find( "div" ).length) > 1){
		    		$.ajax({  
		    			url : "deleteQuestion.htm", 
		    			data: "questionId=" + questionId, 
		    	    	success : function(data) { 
		    	    		systemMessage(data.status);
		    	    		 $("#div"+id).remove();
					    	 setIndex("optionsDiv",'Options for blank');
		    	    	}  
		    		});
	    		}else{
	    			systemMessage("Atleast one requried !!");
	    		}
	    	}else{
	    		$("#div"+id).remove();
	    		setIndex("optionsDiv",'Options for blank');
	    	}
    	}else{
    		$("#div"+id).remove();
    		setIndex("optionsDiv",'Options for blank');
    	}
    	
	}
	function getPlayerContent(id){
		 var audioDivContent = "<table align='left' hieght='100%' width='30%' border=0>"+
	      "<tr><td><div id='jquery_jplayer"+id+"' class='jp-jplayer'></div>"+
				"<div id='jp_container"+id+"' class='jp-audio' role='application' aria-label='media player'>"+
					"<div class='jp-type-single'>"+
						"<div class='jp-gui jp-interface'>"+
							"<div class='jp-controls'>"+
								"<button class='jp-play' role='button' tabindex='0'>play</button>"+
								"<button class='jp-stop' role='button' tabindex='0'>stop</button>"+
							"</div><div class='jp-progress'><div class='jp-seek-bar'>"+
									"<div class='jp-play-bar'></div></div></div>"+
							"<div class='jp-volume-controls'>"+
								"<button class='jp-mute' role='button' tabindex='0'>mute</button>"+
								"<button class='jp-volume-max' role='button' tabindex='0'>max volume</button>"+
								"<div class='jp-volume-bar'>"+
									"<div class='jp-volume-bar-value'></div>"+
								"</div>"+
							"</div>"+
							"<div class='jp-time-holder'>"+
								"<div class='jp-current-time' role='timer' aria-label='time'>&nbsp;</div>"+
								"<div class='jp-duration' role='timer' aria-label='duration'>&nbsp;</div>"+
								"<div class='jp-toggles'>"+
									"<button class='jp-repeat' role='button' tabindex='0'>repeat</button>"+
								"</div>"+
							"</div>"+
						"</div>"+
						"<div class='jp-details'>"+
							"<div class='jp-title' aria-label='title'>&nbsp;</div>"+
						"</div>"+
				"</div>"+
			"</div>"+
	   "</td></tr></table>";
	return audioDivContent;
	  }
	
	function setAudioToPlayer(data, playerId,id){
	    $("#loading-div-background").show();
	   	var param = "usersFilePath="+data.usersFilePath+"&regId="+parseInt(data.regId);
    	$('#'+id).val(data.usersFilePath);
	   	 $.ajax({
			 type: "GET",
		     url:"checkFileExists.htm",
		     data: param,
		     success: function(status){
		      if(status == 'Exists'){
		    	  $("#jquery_jplayer"+playerId).jPlayer({
		  			ready: function (event) {
		  				$(this).jPlayer("setMedia",{
		  					title: id,
		  					wav: "loadUserFile.htm?usersFilePath="+data.usersFilePath+"&regId="+parseInt(data.regId)
		  				});
		  			},
		  			play: function() {
		  				$(this).jPlayer("pauseOthers");
		  			},
		  			swfPath: "resources/javascript/jplayer/jquery.jplayer.swf",
		  			supplied: "wav",
		  			cssSelectorAncestor: "#jp_container"+playerId,
		  			wmode: "window",
		  			useStateClassSkin: true,
		  			autoBlur: false,
		  			smoothPlayBar: true,
		  			keyEnabled: true,
		  			remainingDuration: true,
		  			toggleDuration: true,
		  			preload: 'auto'
		  		});
		      }else{
		    	  alert("Audio file not found !!");
		      }

		     }
		 });
	}
	 function addComprehensionOptions(){
		 	var comprehensionType = document.getElementById("comprehensionType").value;
		 	var optionsDiv = $(document.getElementById("optionsDiv"));
			var mode = document.getElementById("mode").value;
			var str = "";
			currentCounter = 0;
					if(comprehensionType == 2){
						str = "<div id='div"+count+"'><input type='hidden' id='questionId"+count+"' name='subQuestions[0].questionses["+count+"].questionId' value='0' required='required'>" +
							"<input type='hidden' id='assignmentTypeId"+count+"' name='subQuestions[0].questionses["+count+"].assignmentType.assignmentTypeId' value="+comprehensionType+" required='required'>" +
							"<table width='100%'>"+
							"<tr><td align='left' width=500><b><span id='span"+count+"' style='padding-top:0.2em;' class='tabtxt'></b><b><textarea cols='60' rows='1' id='questions:"+count+"' name='subQuestions[0].questionses["+count+"].question' onblur="+((mode == 'Edit') ? "updateQuestion(this.id)" :"")+"></textarea></b></td>"+
							"<td align='left'><i class='fa fa-times' aria-hidden='true' style='cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;' onclick='removeComprehensionSubQuestion("+count+")'></i></td>"+
							"<td id='resultDiv"+count+"' align='left' class='status-message'></td></tr>"+
							"<tr><td height=5 colSpan=3></td></tr></table></div>";
					}
					else if(comprehensionType == 3){			
						str = "<div id='div"+count+"'><input type='hidden' id='questionId"+count+"' name='subQuestions[0].questionses["+count+"].questionId' value='0' required='required'>" +
							"<input type='hidden' id='assignmentTypeId"+count+"' name='subQuestions[0].questionses["+count+"].assignmentType.assignmentTypeId' value="+comprehensionType+" required='required'>" +
							"<table width='100%' id='table"+count+"'><tr><td align='left'><input type='hidden' name='subQuestions[0].questionses["+count+"].numOfOptions' id ='numOfOptions"+count+"' value='5' /></td></tr></td></tr>"+
							"<tr><td align='left' width=500><b><span id='span"+count+"' style='padding-top:0.2em;' class='tabtxt'></b><b><textarea cols='60' rows='1' id='questions:"+count+"' name='subQuestions[0].questionses["+count+"].question' onblur="+((mode == 'Edit') ? "updateQuestion(this.id)" :"")+"></textarea></b></td>" +
							"<td><i class='fa fa-times' aria-hidden='true' style='cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;vertical-align:bottom;' onclick='removeComprehensionSubQuestion("+count+")'></i></td></tr>"+
							"<tr><td width='300' align='left' style='padding-left:2em;'><div id='option1"+count+"Div'>&nbsp;&nbsp;option 1.&nbsp;<input type='text' title='question:"+count+"' name='subQuestions[0].questionses["+count+"].option1' id='option1:"+count+"' onblur="+((mode == 'Edit') ? "updateQuestion(this.title)" :"")+" required='required'  /></div></td></tr>"+
						    "<tr><td width='300' align='left' style='padding-left:2em;'><div id='option2"+count+"Div'>&nbsp;&nbsp;option 2.&nbsp;<input type='text' title='question:"+count+"' name='subQuestions[0].questionses["+count+"].option2' id='option2:"+count+"' onblur="+((mode == 'Edit') ? "updateQuestion(this.title)" :"")+" required='required'  /></div></td></tr>"+
						    "<tr><td width='300' align='left' style='padding-left:2em;'><div id='option3"+count+"Div'>&nbsp;&nbsp;option 3.&nbsp;<input type='text' title='question:"+count+"' name='subQuestions[0].questionses["+count+"].option3' id='option3:"+count+"' onblur="+((mode == 'Edit') ? "updateQuestion(this.title)" :"")+" required='required'  /></div></td></tr>"+
						    "<tr><td width='300' align='left' style='padding-left:2em;'><div id='option4"+count+"Div'>&nbsp;&nbsp;option 4.&nbsp;<input type='text' title='question:"+count+"' name='subQuestions[0].questionses["+count+"].option4' id='option4:"+count+"' onblur="+((mode == 'Edit') ? "updateQuestion(this.title)" :"")+" required='required'  /></div></td></tr>"+
						    "<tr><td width='300' align='left' style='padding-left:2em;'><div id='option5"+count+"Div'>&nbsp;&nbsp;option 5.&nbsp;<input type='text' title='question:"+count+"' name='subQuestions[0].questionses["+count+"].option5' id='option5:"+count+"' onblur="+((mode == 'Edit') ? "updateQuestion(this.title)" :"")+" required='required'  /></div></td></tr>"+
						    "<tr><td height=5 colSpan=3></td></tr>"+
						    "<tr><td width='300' align='left'>Correct Answer.&nbsp;"+
							"<select name='subQuestions[0].questionses["+count+"].answer' required='required' id='answer:"+count+"' title='question:"+count+"' onchange="+((mode == 'Edit') ? "updateQuestion(this.title)" :"")+">"+
									"<option value=''>Select Option</option>"+
									"<option value='option1'>Option 1</option>"+
									"<option value='option2'>Option 2</option>"+
									"<option value='option3'>Option 3</option>"+
									"<option value='option4'>Option 4</option>"+
									"<option value='option5'>Option 5</option>"+
							"</select></td></tr><tr><td width='200' height='50' align='left' style='padding-left:1em;'><input type='button' class='subButtons subButtonsWhite medium'  value='Remove Option' onclick='removeMCQOption(\"comprehension\","+count+");' style='width:120px;' />&nbsp;&nbsp;&nbsp; " +
									"<input type='button' class='subButtons subButtonsWhite medium'  value='Add Option' onclick='addMCQOption(\"comprehension\","+count+");'/></td>"+
							"<td id='resultDiv"+count+"' align='left' class='status-message'></td></tr>"+
							"<tr><td height=5 colSpan=3></td></tr></table></div>";
					}
					else if(comprehensionType == 5){			
						str = "<div id='div"+count+"'><input type='hidden' id='questionId"+count+"' name='subQuestions[0].questionses["+count+"].questionId' value='0' required='required'><input type='hidden' id='assignmentTypeId"+count+"' name='subQuestions[0].questionses["+count+"].assignmentType.assignmentTypeId' value="+comprehensionType+" required='required'><table width='100%'><tr><td align='left'></td></tr>"+
							"<tr><td align='left' width=500><b><span id='span"+count+"' style='padding-top:0.2em;' class='tabtxt'></b><b><textarea cols='60' rows='1' id='questions:"+count+"' name='subQuestions[0].questionses["+count+"].question' onblur="+((mode == 'Edit') ? "updateQuestion(this.id)" :"")+"></textarea></b></td></tr>"+
						    "<tr><td width='300' align='left'>Correct Answer.&nbsp;"+
							"<select name='subQuestions[0].questionses["+count+"].answer' required='required' id='answer:"+count+"' onchange="+((mode == 'Edit') ? "updateQuestion(this.id)" :"")+">"+
									"<option value=''>Select Option</option>"+
									"<option value='true'>True</option>"+
									"<option value='false'>False</option>"+
							"</select></td>" +
							"<td align='left'><i class='fa fa-times' aria-hidden='true' style='cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;' onclick='removeComprehensionSubQuestion("+count+")'></i></td>"+
							"<td id='resultDiv"+count+"' align='left' class='status-message'></td></tr>"+
							"<tr><td height=5 colSpan=3></td></tr></table></div>";
					}
					else if(comprehensionType == 4){			
						str = "<div id='div"+count+"'><input type='hidden' id='questionId"+count+"' name='subQuestions[0].questionses["+count+"].questionId' value='0' required='required'><input type='hidden' id='assignmentTypeId"+count+"' name='subQuestions[0].questionses["+count+"].assignmentType.assignmentTypeId' value="+comprehensionType+" required='required'><table width='100%'><tr><td align='left'></td></tr>"+
							"<tr><td align='left' width=500><b><span id='span"+count+"' style='padding-top:0.2em;' class='tabtxt'></b><b><textarea cols='60' rows='1' id='questions:"+count+"' name='subQuestions[0].questionses["+count+"].question' onblur="+((mode == 'Edit') ? "updateQuestion(this.id)" :"")+"></textarea></b></td></tr>"+
							"<tr><td width='300' align='left'>Correct Answer.&nbsp;"+
							"<input type='text' name='subQuestions[0].questionses["+count+"].answer' required='required' id='answer:"+count+"' onchange="+((mode == 'Edit') ? "updateQuestion(this.id)" :"")+">"+
							"</td>" +
							"<td align='left'><i class='fa fa-times' aria-hidden='true' style='cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;' onclick='removeComprehensionSubQuestion("+count+")'></i></td>"+
							"<td id='resultDiv"+count+"' align='left' class='status-message'></td></tr>"+
							"<tr><td height=5 colSpan=3></td></tr></table></div>";
					}
					$(optionsDiv).append(str);
					smoothScroll(document.getElementById("div"+count));
					document.getElementById('questions:'+count).focus();
					setIndex("optionsDiv",'Question');
					count = parseInt(count)+1;
		} 
	 
	 function removeComprehensionSubQuestion(id){
			 if(!isAlreadyAssigned){
			 var formObj = document.getElementById("questionsForm");
			 var formData = new FormData(formObj);
			 checkAlreadyAssigned(formData, id, "remove");
		 }else{
			var mode = document.getElementById("mode").value;
		    	if(mode == 'Edit'){
		    		var questionId = document.getElementById("questionId"+id).value;
				    	if(questionId > 0){
				    		if(($("#optionsDiv" ).find( "div" ).length) > 1){
					    		$.ajax({  
					    			url : "deleteQuestion.htm", 
					    			data: "questionId=" + questionId, 
					    	    	success : function(data) { 
					    	    		systemMessage(data.status);
					    	    		 $("#div"+id).remove();
								    	 setIndex("optionsDiv",'Question');
					    	    	}  
					    		});
				    		}else{
				    			systemMessage("Atleast one question requried !!");
				    		}
				    	}else{
				    		$("#div"+id).remove();
				    		setIndex("optionsDiv",'Question');
				    	}
		    	}else{
		    		$("#div"+id).remove();
		    		setIndex("optionsDiv",'Question');
		    	}	
		 }
	 }
	
	 function removeMCQOption(type, id){
		 if(type == 'comprehension'){
		 var numOfOptions = parseInt(document.getElementById('numOfOptions'+id).value);
			 if(numOfOptions == 3){
				 alert('There should be atleast three options');
				 return false;
			 }
			 $("#option"+numOfOptions+''+id+'Div').hide();
			// $("#answer"+id).find('[value="option'+numOfOptions+'"]').remove();
			 $("#optionsDiv" ).find('[value="option'+numOfOptions+'"]').remove();
			 numOfOptions--;
			 document.getElementById('numOfOptions'+id).value = numOfOptions;	
			 var mode = document.getElementById('mode').value;
			 if(mode == 'Edit'){
				updateQuestion('question:'+id);
			 }
		 }else if(type == 'multiplechoice'){
			 var numOfOptions = parseInt(document.getElementById('numOfOptions').value);
			 if(numOfOptions == 3){
				 alert('There should be atleast three options');
				 return false;
			 }
			 $('#option'+numOfOptions+'Div').hide();
			 $("#answer").find('[value="option'+numOfOptions+'"]').remove();
			 numOfOptions--;
			 document.getElementById('numOfOptions').value = numOfOptions;
			 var mode = document.getElementById('mode').value;
			 if(mode == 'Edit'){
				 updateQuestion(0);
			 }
		 }
	 }
	 
	 function addMCQOption(type, id){
		 if(type == 'comprehension'){
			 var numOfOptions = document.getElementById('numOfOptions'+id).value;
			 if(numOfOptions == 5){
				 alert('There cannot be more than five options');
				 return false;
			 }
			 numOfOptions++;
			 $("#answer"+id).append($("<option></option>").val("option"+numOfOptions).html("Option "+numOfOptions));	
			 if($("#option"+numOfOptions+''+id+'Div').html()){
				 $("#option"+numOfOptions+''+id+'Div').show();
			 }else{
				 $("#table"+id+" tr:nth-child("+(numOfOptions)+"n)").after("<tr><td width='562' align='left'  ><div id='option"+numOfOptions+""+id+"Div'>&nbsp;&nbsp;option "+numOfOptions+".&nbsp; <input type='text' name='subQuestions[0].questionses["+id+"].option"+numOfOptions+"' title='question:"+id+"' id='option"+numOfOptions+"':'"+id+"' required='required' onblur='updateQuestion(this.title)' /></div></td></tr>")
			 }

			 document.getElementById('numOfOptions'+id).value = numOfOptions;
			 
		 }else if(type == 'multiplechoice'){
			 var numOfOptions = document.getElementById('numOfOptions').value;
			 if(numOfOptions == 5){
				 alert('There cannot be more than five options');
				 return false;
			 }
			 numOfOptions++;
			 $("#answer").append($("<option></option>").val("option"+numOfOptions).html("Option "+numOfOptions));	
			 if($('#option'+numOfOptions+'Div').html()){
				 $('#option'+numOfOptions+'Div').show();
			 }else{
				 $("#table tr:nth-child("+(numOfOptions-1)+"n)").after("<tr><td width='562' align='left'><div id='option"+numOfOptions+"Div' style='display: block;'>&nbsp;&nbsp;option "+numOfOptions+".&nbsp;<input type='text' name='questions[0].option"+numOfOptions+"' id='option"+numOfOptions+"' required='required' onblur='updateQuestion(0)' form='questionsForm' /></div></td></tr>")
			 }
			 document.getElementById('numOfOptions').value = numOfOptions;
		 }
	 }