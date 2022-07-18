function showStudentAsessments(){
	var gradeId=$('#gradeId').val();
	var classId=$('#classId').val();
	var csId=$('#csId').val();
	var assignedDate=$('#assignedDate').val();
	assignedDate = new Date(assignedDate);
	var assignmentId=$('#title').val();
	var tab = $('#tab').val();	
	$("#Evaluate").empty(); 
	$("#StuAssessQuestionsList").empty(); 
	if(assignmentId > 0 && gradeId >0 && classId >0 && csId >0 && assignedDate != 'Invalid Date'){
		if(tab == "gradeGroup"){
			$("#loading-div-background").show();
			$.ajax({  
				url : "getGroupAssessmentTests.htm", 
			    data: "assignmentId="+assignmentId,
			    success: function(response) {
			    	 $("#Evaluate").html(response);
			    	 $("#loading-div-background").hide();
			    }
			}); 
		}else{
			$("#loading-div-background").show();
			$.ajax({  
				url : "getStudentAssessmentTests.htm", 
			    data: "assignmentId="+assignmentId,
			    success: function(response) {
			    	 $("#Evaluate").html(response);
			    	 $("#loading-div-background").hide();
			    }
			}); 
		}	
	}else{
		alert("Please Fill all the filters");
	}
}

function updateJacMarks(s)
{
	var assignmentQuestionId = $("#qid"+s).val();
	var marks=$("#marks"+s).val();
	if(marks>1){
		systemMessage("Please enter marks as 1 or 0");
		$("#marks"+s).focus();
		return false;
	}
	$.ajax({
		type : "GET",
		url : "updateJacMarks.htm",
		data : "assignmentQuestionId=" +assignmentQuestionId +"&mark=" + marks,
		success : function(response) {
			//alert(response);
		}
	});	
}
function submitJacTemplate(assignmentId)
{
	var studentAssignmentId=$("#studentAssignmentId").val();
	$.ajax({
		type : "GET",
		url : "gradeJacTemplateTest.htm",
		data : "studentAssignmentId="+studentAssignmentId,
		success : function(response) {
			
			$.ajax({  
				url : "getStudentAssessmentTests.htm", 
			    data: "assignmentId="+assignmentId,
			    success: function(response) {
			    	 $("#Evaluate").empty(); 
			    	 $("#Evaluate").html(response);
			    }
			}); 		
			$("#msg").html(response);
			document.getElementById("shows").style.visibility="visible";
			document.getElementById("jactemplate").style.visibility="hidden";
				
		}
	});
}
function gradeTest(assignmentId,assignmentTypeId){
var studentAssignmentId=$("#studentAssignmentId").val();
if(assignmentTypeId==8){
 var teacherComment = $("#teacherNotes").val();
 teacherComment.trim();
 var le=teacherComment.length;
 if(le>500){
	 alert("You have exceeded the more than 500 characters ");
	 $("#teacherNotes").focus();
     return false;
 }
if (!teacherComment.replace(/\s/g, '').length) {
	alert("Please enter comment");
	 $("#teacherNotes").focus();
    return false;
}
}
if(assignmentTypeId==8  || assignmentTypeId==20){
	var assQuesLt=document.getElementsByName("assignmentQuesList");
	var len=assQuesLt.length;
	var count=0;
	for(var i=1;i<=len;i++)
	{
	var assignQuesId=document.getElementById("assignQuesList:"+i).value;
	if(assignmentTypeId==8){
	var fluScore=document.getElementById("marks:"+assignQuesId+":"+2).value;
    var retellScore=document.getElementById("marks:"+assignQuesId+":"+3).value;
	if((fluScore!="" && fluScore>=0) && retellScore!=""){
    count=count+1;	
    }
	}else{
		var fluScore=document.getElementById("marks:"+assignQuesId+":"+1).value;
		if(fluScore!="" && fluScore>=0){
		    count=count+1;	
		   }
	}
	}
	if(count==len){
	$.ajax({  
		url : "gradeBenchmarkTests.htm",
		type: "GET",
		data: "studentAssignmentId="+studentAssignmentId+"&teacherComment="+teacherComment,
		success : function(response) { 
				$.ajax({  
					url : "getStudentAssessmentTests.htm", 
				    data: "assignmentId="+assignmentId,
				    success: function(data) {
				    	 $("#Evaluate").empty(); 
				    	 $("#Evaluate").html(data);
				    	 return false;
				    }
				}); 	
				getStudentAsessments();
				systemMessage(response);
				return false;
			}
		});
	}else{
		systemMessage("Please Fill all the fields");
		return false;
	}
}else{
var question = $("[name=questions]");
var maxMark = $("[name=maxMarks]");
var secMark = $("[name=secMarks]");
var teacherComment = $("[name=teacherComments]");

 var questions = [];
 var maxMarks= [];
 var secMarks=[];
 var teacherComments=[];
for (var i = 0; i <question.length; i++) {
	
	questions.push(question[i].value);	
	var max = maxMark[i].value;
	var sec = secMark[i].value;
	max = Number(max);
	sec = Number(sec);
	if(max < sec){
		alert("Secured marks should less than Max marks");
		$("#"+secMark[i].id).focus();
		return false;
	}
	maxMarks.push(maxMark[i].value);
	secMarks.push(secMark[i].value);
	var tcomt=teacherComment[i].value;
	var comma = tcomt.match(/,/g);
	if (!tcomt.replace(/\s/g, '').length) {
		alert("Please enter comment");
		$("#"+teacherComment[i].id).focus();
		return false;
	}
	else if(comma == ",")
	{
	alert("comma(,) Not allowed");
	$("#"+teacherComment[i].id).focus();
	return false;
	}
	 var le=tcomt.length;
	 if(le>500){
		 alert("You have exceeded the more than 500 characters ");
		 $("#"+teacherComment[i].id).focus();
	     return false;
	 }
	teacherComments.push(teacherComment[i].value);
	
}
 $.ajax({  
		url : "gradeStudentTests.htm",
		type: "GET",
		data: "studentAssignmentId="+studentAssignmentId+"&questions="+questions+"&maxMarks="+maxMarks+"&secMarks="+secMarks+"&teacherComments="+encodeURIComponent(teacherComments)+"&assignmentTypeId="+assignmentTypeId,
		success : function(response) { 
					$.ajax({  
					url : "getStudentAssessmentTests.htm", 
				    data: "assignmentId="+assignmentId,
				    success: function(data) {
				    	 $("#Evaluate").empty(); 
				    	 $("#Evaluate").html(data);
				    	 return false;
				    }
				}); 		
				$("#StuAssessQuestionsList").html("<font color=blue>"+response+"</font>");
				return false;
								
			}
		});
}
       
    } 
var coun=0;
var clicks = 0;
var errorsAddress = new Array();
var errorsArray = new Array();
var addedWordsArray=new Array();
var selfCorrectWordsArray=new Array();
var prosodyArray=new Array();
var wordsReadByStudent;
var view = false;
var accETable = new Map();
var scoType=0;
var teacherFlunecyMarks;



 
function displayresult()
{
    s = window.document.getElementById("question").value;
    s.replace(/^\s+|\s+$/g,'');
    var senten=s.split(']');
    var count=senten[0].split(' ').length;
    document.getElementById("wordsread").value = count;
    var m=0;
    m=document.getElementById("errors").value;
    var total=count-m;
    document.getElementById("correctwords").value=total;

}
var temp=0;
function strikeText(fieldObj, i,testType) {
 if(enable == true){
	chkErrorExists(fieldObj, i,"error");
	document.getElementById("errorTable").style.visibility = "visible";	
	var val=document.getElementById("word"+i).value;
	var etable=document.getElementById('dataTable');
    var rowCount = etable.rows.length;
	var style1 = (fieldObj.style.textDecoration!='line-through')?'line-through':'none';
	if(style1=='none'){
       	errorsAddress.splice(errorsAddress.indexOf(i), 1);
    	errorsArray.splice(errorsArray.indexOf(val), 1);
        document.getElementById("errors").value = --document.getElementById("errors").value ;// --clicks;
        fieldObj.style.color='Black';
        fieldObj.style.textDecoration="none";
        var row = document.getElementById("addedError"+i);
        //row.parentNode.removeChild(row);
        $("#addedError"+i).remove();
        autoDisplayErrorCount(testType);
        delete accETable[i];
          
     }else{
    	
        fieldObj.style.textDecoration = style1;
        fieldObj.style.color='red';
        errorsAddress.push(i);
        var lastChar = val.charAt(val.length-1);
       /* if(lastChar == ":")
        	val = val.slice(0, -1);
        if (val.endsWith(",") || val.endsWith("?") || val.endsWith(".")){
			val = val.substring(0, val.length - 1);
			}*/
        val= val.toString().replace(/[`~!@#$%^&*()_|+\-=?;:",.<>\{\}\[\]\\\/]/gi, '');
        if(val.charAt(0) === '"' || val.charAt(0) === '“' || val.charAt(0)==="\'" || val.charAt(0)==="\‘"){
        	val= val.slice(1);
  		}
  		if(val.charAt(val.length-1) === '"' || val.charAt(val.length-1) === '”' || val.charAt(val.length-1)==="\'" || val.charAt(val.length-1)==="\’"){
  			val=val.slice(0, val.length-1);
  		}
        var row = etable.insertRow(rowCount);
        etable.prepend(row);
        row.id="addedError"+i;
        var c1=row.insertCell(0);
        var c2=row.insertCell(1);
        var c3=row.insertCell(2);
        c1.style.width="200px";
        c1.innerHTML=val;
        var str="<textarea name='errComments' id='errs:"+temp+"' onblur='tempComment("+i+","+temp+")' onkeypress='return checkLength("+i+","+temp+",event)' onKeyDown='return setLength("+i+","+temp+",event)'></textarea>";
        c2.style.width ="200px";
        c2.innerHTML=str;
        var strrr="<div id='rem:"+temp+"'></div>";
        c3.style.width="50px";
        c3.innerHTML=strrr;
        document.getElementById("errs:"+temp).focus();
        ++temp;
        errorsArray.push(val);
         document.getElementById("errors").value = ++document.getElementById("errors").value; //++ clicks
         autoDisplayErrorCount(testType);
        fieldObj.style.color='red';
       
    }
   
    var errorsCnt = document.getElementById("errors").value;
    if(errorsCnt > 0){
    	if(!view)
    		$("#homeWorkDiv").show();
    }else{
    	$("#homeWorkDiv").hide();
    }

    return true;
	}
	else {
		alert("Please remove score to continue..");
	}
}
function chkErrorExists(obj, wordNum,errType){
	
	var regradeStat=$('#regradeStatus').val();
	var stylee = obj.style.textDecoration;
	var val=document.getElementById("word"+wordNum).value;
	if(stylee=='line-through' && errType!='error'){
		   errorsAddress.splice(errorsAddress.indexOf(wordNum), 1);
		   errorsArray.splice(errorsArray.indexOf(val), 1);
	       document.getElementById("errors").value = --document.getElementById("errors").value ;// --clicks;
	       obj.style.color='Black';
	       obj.style.textDecoration="none";
	       $("#addedError"+wordNum).remove();
	       if(errType!="skipped" && errType!="unKnownWord"){
	        removeErrorWord(val,wordNum,regradeStat);
	      }
	       delete accETable[wordNum];
	}
	
	var self = $("#selfCorrect"+wordNum).val();
	var pros=$("#prosody"+wordNum).val();
	var comm=$("#comment"+wordNum).val();
	 if(self){
		 obj.style.color='Black';
		 obj.style.textDecoration="none";
		 $('#selfCorrect'+wordNum).remove();
     	 $('#errorIcon1'+wordNum).remove();
     	 selfCorrectWordsArray = $.grep(selfCorrectWordsArray, function(value) {
 		    return value.indexOf(wordNum + "$") < 0;
 		});
     	//if(regradeStat=="no"){
     	var wordType=2;
     	obj.style.color='Black';
      	removeFluencyAddedWord(wordNum,self,wordType,regradeStat);
     	//} 
	 }
	 if(pros){
		 $('#prosody'+wordNum).remove();
      	$('#errorIcon2'+wordNum).remove();
      	document.getElementById("errors").value = --document.getElementById("errors").value ;
      	prosodyArray = $.grep(prosodyArray, function(value) {
  		    return value.indexOf(wordNum + "$") < 0;
  		});
      //	if(regradeStat=="no"){
      	var wordType=3;
      	removeFluencyAddedWord(wordNum,pros,wordType,regradeStat);
      //	} 
	 }
	 if(comm){
		 $('#comment'+wordNum).remove();
	     	$('#errorIcon'+wordNum).remove();
	     	document.getElementById("errors").value = --document.getElementById("errors").value ;
	     	addedWordsArray = $.grep(addedWordsArray, function(value) {
	 		    return value.indexOf(wordNum + "$") < 0;
	 		});
	     	// if(regradeStat=="no")
	     	 removeFluencyAddedWord(wordNum,comm,1,regradeStat);
	     	 
  	}

}
function strikeFluencyText(fieldObj, i, errorType,testType,regradeStat) {
	if(enable == true){
		chkErrorExists(fieldObj,i,'error');
		var j=0;
		var val=document.getElementById("word"+i).value;
		var style = (fieldObj.style.textDecoration!='line-through')?'line-through':'none';
		if(style == 'none'){
		   errorsAddress.splice(errorsAddress.indexOf(i), 1);
		   errorsArray.splice(errorsArray.indexOf(val), 1);
	       document.getElementById("errors").value = --document.getElementById("errors").value ;// --clicks;
	       fieldObj.style.color='Black';
	       fieldObj.style.textDecoration="none";
	       //var correctwords = parseInt(document.getElementById("correctwords").value);
	       //document.getElementById("correctwords").value = correctwords+1;
	       autoDisplayErrorCount(testType);
	       delete accETable[i];
	       removeErrorWord(val,i,regradeStat);
	   }
	   else{
		   fieldObj.style.textDecoration = style;
	       errorsAddress.push(i);
	       var lastChar = val.charAt(val.length-1);
	       if(lastChar == ":")
	       	val = val.slice(0, -1);
	       if (val.endsWith(",") || val.endsWith("?") || val.endsWith(".")){
				val = val.substring(0, val.length - 1);
			}
	        val=  val.toString().replace(/[`~!@#$%^&*()_|+\-=?;:",.<>\{\}\[\]\\\/]/gi, '');
	      /*  val = val.toString().replace("\â€�", ""); 
	        val = val.toString().replace("\â€œ", "");
	        val = val.toString().replace("\,", "");*/
	        //val = val.toString().replace("\'", ""); 
	        var flen=val.length;
	    	if (val.charAt(flen-1)==="\'" || val.charAt(0)==="\'"){
	    		val = val.toString().replace("\'", ""); 
	    	}
	    	
	    		
	       	if(errorType == 'unKnownWord'){
				val = val+'$';
			}else if(errorType == 'skippedWord'){
				val = val+'@';
			}
	       errorsArray.push(val);
	       document.getElementById("errors").value = ++document.getElementById("errors").value; //++ clicks
	       if(errorType == 'errorWord')
	    	  fieldObj.style.color='red';
	       else if(errorType == 'unKnownWord')
	    	  fieldObj.style.color='green';
	       else if(errorType == 'skippedWord')
	    	   fieldObj.style.color='blue';
	       else if(errorType == 'addSelfCorrect')
	    	   fieldObj.style.color='yellow';
	       autoSaveErrorWord(val,i,errorType,regradeStat);
	      //var correctwords = parseInt(document.getElementById("correctwords").value);
	      /*if(correctwords > 0)
	         document.getElementById("correctwords").value = correctwords-1;*/
	   }
	   autoDisplayErrorCount(testType);
	   var errorsCnt = document.getElementById("errors").value;
	   if(errorsCnt > 0){
	   	if(!view)
	   		$("#homeWorkDiv").show();
	   }else{
	   	$("#homeWorkDiv").hide();
	   }
	
	   return true;
	}
	else {
		alert("Please remove score to continue..");
	}
}

function editBenchmarkstrikeText(fieldObj, i,testType,regrade) {
 if(enable == true){
	 chkErrorExists(fieldObj,i,'error');
	 var assignmentQuestionId=$("#questions").val();
     var readingTypesId=$("#readingTypesId").val();
 	 var gradeTypesId=$("#gradeTypesId").val();
	 document.getElementById("errorTable").style.visibility = "visible";	
	var val=document.getElementById("word"+i).value;
	var etable=document.getElementById('dataTable');
    var rowCount = etable.rows.length;
	var style1 = (fieldObj.style.textDecoration!='line-through')?'line-through':'none';
     if(style1=='none'){
    	 errorsAddress.splice(errorsAddress.indexOf(i), 1);
    	errorsArray.splice(errorsArray.indexOf(val), 1);
        document.getElementById("errors").value = --document.getElementById("errors").value ;// --clicks;
        fieldObj.style.color='Black';
        fieldObj.style.textDecoration="none";
       var row = document.getElementById("addedError"+i);
       //row.parentNode.removeChild(row);
       $("#addedError"+i).remove();
       
       var wdRead=document.getElementById("wordsread").value;
       var errRead=document.getElementById("errors").value;
       var totRead=document.getElementById("correctwords").value;
       tot=wdRead-errRead;
       document.getElementById("correctwords").value=tot;
       $.ajax({
   		type : "GET",
   		url : "removeErrorWord.htm",
   		data : "assignmentQuestionId=" +assignmentQuestionId +"&readingTypesId=" + readingTypesId+"&gradeTypesId="+gradeTypesId+"&errorWord="+val+"&errorAddress="+i+"&wordsRead="+wdRead+"&errRead="+errRead+"&totalRead="+tot+"&regradeStat="+regrade,
   		success : function(response) {
   			document.getElementById("accuracyPer").value=response.percentage;
   			document.getElementById("accuracyMarks:"+assignmentQuestionId+":"+readingTypesId).value=response.percentage;
   		}
   	});	
     }else{
    	 
        fieldObj.style.textDecoration = style1;
        fieldObj.style.color='red';
        errorsAddress.push(i);
        var lastChar = val.charAt(val.length-1);
        /*if(lastChar == ":")
        	val = val.slice(0, -1);*/
        val= val.toString().replace(/[`~!@#$%^&*()_|+\-=?;:",.<>\{\}\[\]\\\/]/gi, '');
     	if(val.charAt(0) === '"' || val.charAt(0) === '“' || val.charAt(0)==="\'" || val.charAt(0)==="\‘"){
     		val= val.slice(1);
  		}
  		if(val.charAt(val.length-1) === '"' || val.charAt(val.length-1) === '”' || val.charAt(val.length-1)==="\'" || val.charAt(val.length-1)==="\’"){
  			val=val.slice(0, val.length-1);
  		}
  		
        var row = etable.insertRow(rowCount);
        etable.prepend(row);
        row.id="addedError"+i;
        
        var c1=row.insertCell(0);
        var c2=row.insertCell(1);
        var c3=row.insertCell(2);
        c1.style.width="200px";
        c1.innerHTML=val;
        var str="<textarea name='errComments' id='errs:"+temp+"' onkeypress='return checkLength("+i+","+temp+",event)' onKeyDown='return setLength("+i+","+temp+",event)' onblur='autoSaveComment("+temp+","+i+")'/>";
        c2.style.width ="200px";
        c2.innerHTML=str;
        var strr="<div id='rem:"+temp+"'></div>";
        c3.style.width="50px";
        c3.innerHTML=strr;
        document.getElementById("errs:"+temp).focus();
        ++temp;
        errorsArray.push(val);
        document.getElementById("errors").value = ++document.getElementById("errors").value; //++ clicks
        fieldObj.style.color='red';
        checkSelectRange(i);
        var wdRead=document.getElementById("wordsread").value;
        var errRead=document.getElementById("errors").value;
        var totRead=document.getElementById("correctwords").value;
        tot=wdRead-errRead;
        document.getElementById("correctwords").value=tot;
        var audioDuration=$("#audioDuration").val();
        if (audioDuration != undefined && audioDuration != Infinity) {
        	var wcpm = 0;
        	var count=document.getElementById("wordsread").value;
				if(audioDuration!= '') {
					wcpm = Math.round(parseInt(count) * 60 / audioDuration) ;
					document.getElementById("wcpm").value = wcpm;
				}/* else {
					wcpm = Math.round(parseInt(count) * 60) ;
				}*/
        }
	 	 
        $.ajax({
    		type : "GET",
    		url : "autoSaveErrorWord.htm",
    		data : "assignmentQuestionId=" +assignmentQuestionId +"&readingTypesId=" + readingTypesId+"&gradeTypesId="+gradeTypesId+"&errorWord="+val+"&errorAddress="+i+"&errorType=errorWord"+"&wordsRead="+wdRead+"&errRead="+errRead+"&totalRead="+tot+"&regrade="+regrade,
    		success : function(response) {
    			document.getElementById("accuracyPer").value=response.percentage;
    			document.getElementById("accuracyMarks:"+assignmentQuestionId+":"+readingTypesId).value=response.percentage;
    		}
    	});	
       
    }
   
    var errorsCnt = document.getElementById("errors").value;
    if(errorsCnt > 0){
    	if(!view)
    		$("#homeWorkDiv").show();
    }else{
    	$("#homeWorkDiv").hide();
    }

    return true;
	}else {
		alert("Please remove score to continue..");
	}
}
function tempComment(i,temp){
	document.getElementById("rem:"+temp).innerHTML="";
	var comment=document.getElementById("errs:"+temp).value;
	accETable.set(i, comment);  
}
function checkLength(i,temp,e){
	var comment=document.getElementById("errs:"+temp).value;
	var keyCode = e.keyCode == 0 ? e.charCode : e.keyCode;
    var ret = ((keyCode >= 48 && keyCode <= 57) || (keyCode >= 65 && keyCode <= 90) || (keyCode >= 97 && keyCode <= 122) || (keyCode!=37 && keyCode!=38));
    var tlength = comment.length+1;
	var set = 100;
	var remain = parseInt(set - tlength);
	if(keyCode==37 || keyCode==38)
		remain=remain+1;
	if (remain>=0) {
	document.getElementById("rem:"+temp).innerHTML=remain;
	}
	if (remain <0) {
		document.getElementById("errs:"+temp).value=comment.substring(0, tlength-1);
		document.getElementById("rem:"+temp).innerHTML=0;
		ret=false;
	}
    return ret;
    
}
function setLength(i,temp,e){
	var comment=document.getElementById("errs:"+temp).value;
	var set = 100;
	if(comment.length-1<100 && comment.length-1>=0){
	document.getElementById("rem:"+temp).innerHTML = set - comment.length+1;
	}
	}
	

function insertValuesToErrTable(gradeTypeId,value,regrade,col,optId){
	
   // $('#dataTable').remove();
   if(gradeTypeId==1 && regrade==1){
    	showTeacherAccuracyErrorList(col,optId);
    }else if(gradeTypeId==1 && regrade==0){
    	showAccuracyErrorList(value,col,optId);
    }
    else{
        showAccuracyErrorList(value,col,optId);
    }
}
function showTeacherAccuracyErrorList(col,optId){
	var etable=document.getElementById('dataTable');
	var rowCount = etable.rows.length;
    for(var z=0;z<errorsArray.length;z++){
    	
    	 if(!(errorsArray[z].endsWith('@') || errorsArray[z].endsWith('$'))){
    		 var row = etable.insertRow(rowCount);
    		 etable.prepend(row);
    	    row.id="addedError"+errorsAddress[z];
    	    var c1=row.insertCell(0);
    	    var c2=row.insertCell(1);
    	    var c3=row.insertCell(2);
    	    c1.style.width="200px";
    	    //c1.style.color=col;
    	    c1.innerHTML=errorsArray[z];
    	    if(optId==0)
    	    var str="<textarea name='errComments' id='errs:"+z+"' disabled>"+accETable.get(errorsAddress[z]) +"</textarea>";
    	    else
    	    var str="<textarea name='errComments' id='errs:"+z+"' onkeypress='return checkLength("+errorsAddress[z]+","+z+",event)' onKeyDown='return setLength("+errorsAddress[z]+","+z+",event)' onblur='autoSaveComment("+z+","+errorsAddress[z]+")'>"+accETable.get(errorsAddress[z]) +"</textarea>";
    	    c2.style.width ="200px";
    	    c2.innerHTML=str;
    	    var str1="<div id='rem:"+z+"'></div>";
            c3.style.width="50px";
            c3.innerHTML=str1;
    	}
    
    }  

}
function strikeSelfCorrectText(fieldObj, i, errorType) {
	   var style = (fieldObj.style.color=='')?'Black':'#D7D700';
	   if(style == 'Black')
	       fieldObj.style.color='D7D700';
      else
     	   fieldObj.style.color='Black';
	      
      return true;
	
	
}
function getBenchmarkTest(readingTypesId,response){			
	
   	var dailogContainer = $(document.getElementById('gradeBenchmark'));
	var screenWidth = $( window ).width() - 10;
	var screenHeight = $( window ).height() - 10;
	$(dailogContainer).append(response);
	$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true,
   open: function(event, ui) {
		  $(".ui-dialog-titlebar-close").show();
	},
   close: function(event, ui) {
	errorsArray.length = 0;
errorsAddress.length = 0;
	$("#gradeBenchmark").empty();  
    $("#ui-datepicker-div").remove();
    // $.contextMenu( 'destroy' );
     $(dailogContainer).dialog('destroy');	
    
	},
	dialogClass: 'myTitleClass'
	});	
	if(readingTypesId==1)
	$(dailogContainer).dialog("option", "title", "Accuracy Assessment");
  	else
	$(dailogContainer).dialog("option", "title", "Fluency Test");	
   
	
	$(dailogContainer).scrollTop("0");
	$("#dueDate").datepicker({
	   changeMonth: true,
       changeYear: true,
       showAnim : 'clip',
       minDate : 0
	 });

}

function openChildWindow(assignmentQuestionId,studentAssignmentId,benchmarkId,readingTypesId,gradeTypesId,userTypeId){
	  var lessonId = document.getElementById("lessonId").value;
	  var csId = document.getElementById("csId").value;
	  var studentId = document.getElementById("studentId").value;
	  var marks = 0;
	  marks = document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value;
	  if(marks=="")
		  marks=-1;
	  if(gradeTypesId==1 && userTypeId==3){
			 if(marks >=0){
				 
		 if(confirm("Would you like to regrade this test?",function(status){
			 
				if(status){
				   errorsAddress.length= 0;
				   view = false;
				   $("#loading-div-background").show();
			    	$.ajax({
	 		       		type : "GET",
	 		       		url : "getBenchmarkTest.htm",
	 		       		data : "assignmentQuestionId="+assignmentQuestionId+"&studentAssignmentId="+studentAssignmentId+"&benchmarkId="+benchmarkId +"&readingTypesId="+readingTypesId+"&marks="+marks+"&regrade=yes"+"&butt=yes&lessonId="+lessonId+"&csId="+csId+"&studentId="+studentId+"&gradeTypesId="+gradeTypesId+"&userTypeId="+userTypeId,
	 		       		success : function(response) {
	 		       		
	 		       		$("#loading-div-background").hide();
	 		       			document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value = "";
	 		       			document.getElementById("accuracyMarks:"+assignmentQuestionId+":"+readingTypesId).value ="";
	 		       		   getBenchmarkTest(readingTypesId,response);
		 		       	   document.getElementById("errorTable").style.visibility = "hidden";
	 		       	       document.getElementById("correctwords").value = 0;
	 		       	       document.getElementById("errors").value = 0;
	 		       	       document.getElementById("wordsread").value = 0;
	 		       	       document.getElementById("accuracyPer").value=0;
	 		       	       document.getElementById("fluencyComment").value="";
	 		       	       document.getElementById("wcpm").value="";
	 		       		}
	 		       	});
			   }else{
				  
				    view = true;
				    $("#loading-div-background").show();
				     $.ajax({
	 		       		type : "GET",
	 		       		url : "getBenchmarkTest.htm",
	 		       		data : "assignmentQuestionId="+assignmentQuestionId+"&studentAssignmentId="+studentAssignmentId+"&benchmarkId="+benchmarkId+"&readingTypesId="+readingTypesId+"&marks="+marks+"&regrade=no"+"&butt=no&lessonId="+lessonId+"&csId="+csId+"&studentId="+studentId+"&gradeTypesId="+gradeTypesId+"&userTypeId="+userTypeId,
	 		       		success : function(response) {
	 		       		 $("#loading-div-background").hide();
	 		       			
	 		       	    getBenchmarkTest(readingTypesId,response);
		 		       	document.getElementById("errorTable").style.visibility = "visible";
		 		        $('#correctwords').prop('disabled', true);
		 		        $('#errors').prop('disabled', true);
		 		        $('#wordsread').prop('disabled', true);
	 		       		}
	 		       	});
			   }
		   }));
	     }else{
	    	 
	    	    view = false;
	    	    $("#loading-div-background").show();
	        	$.ajax({
	       		type : "GET",
	       		url : "getBenchmarkTest.htm",
	       		data : "assignmentQuestionId="+assignmentQuestionId+"&studentAssignmentId="+studentAssignmentId+"&benchmarkId="+benchmarkId+"&readingTypesId="+readingTypesId+"&marks="+marks+"&regrade=yes"+"&butt=yes&lessonId="+lessonId+"&csId="+csId+"&studentId="+studentId+"&gradeTypesId="+gradeTypesId+"&userTypeId="+userTypeId,
	       		success : function(response) {
	       			
	       			$("#loading-div-background").hide();
	       		    getBenchmarkTest(readingTypesId,response);
	 		       	  document.getElementById("errorTable").style.visibility = "hidden";
	 		         
 		       		}
	       	});
	    }
		  }
		  else{
			 
			  view = true;
			  	$("#loading-div-background").show();
			     $.ajax({
		       		type : "GET",
		       		url : "getBenchmarkTest.htm",
		       		data : "assignmentQuestionId="+assignmentQuestionId+"&studentAssignmentId="+studentAssignmentId+"&benchmarkId="+benchmarkId+"&readingTypesId="+readingTypesId+"&marks="+marks+"&regrade=no"+"&butt=no&lessonId="+lessonId+"&csId="+csId+"&studentId="+studentId+"&gradeTypesId="+gradeTypesId+"&userTypeId="+userTypeId,
		       		success : function(response) {
		       		 $("#loading-div-background").hide();
		       	  getBenchmarkTest(readingTypesId,response);
	 		       	document.getElementById("errorTable").style.visibility = "visible";
	 		       
		       		}
		       	});
		   } 
		  
}

function submitFluencyTest(readingTypesId,gradeTypesId){
	var assignmentQuestionId=$("#questions").val();
	var wordsRead=$("#wordsread").val();
	if(wordsRead == 0){
    	alert("Please Select Read Content");
    	return false;
    } 
	var errors=$("#errors").val();
	var correctWords=$("#correctwords").val();
	var errorIdsStr="";
	var errorsStr="";
	var addedWordStr="";
	var addedSelfCorrStr="";
	var addedProsodyStr="";
	var assignmentTitle = $("#assignmentTitle").val();
	var dueDate = $("#dueDate").val();
	if(errorsAddress.length>0 && !dueDate){
    	alert("Please Select Due Date");
    	return false;
    } 
   var comment=window.document.getElementById("fluencyComment").value; 
   if (!comment.replace(/\s/g, '').length) {
		alert("Please enter a comment");
	    return false;
	}
   var le=comment.length;
   if(le>500){
  	 alert("You have exceeded the more than 500 characters ");
  	 $("#fluencyComment").focus();
       return false;
   }
	var lessonId = $("#lessonId").val();
	var csId = $("#csId").val();
	var studentId = $("#studentId").val();
    for(var i=0;i<errorsAddress.length;i++){
    	if(errorIdsStr == ""){
    		errorIdsStr = errorsAddress[i]+ ":";
    	}else{
    		if(errorsAddress[i]){
	    		if(i == errorsAddress.length-1)
	    			errorIdsStr = errorIdsStr+errorsAddress[i];
	    		else
	    			errorIdsStr = errorIdsStr+errorsAddress[i]+ ":" ;
    		}
    	}
    }
  
    for(var i=0;i<errorsArray.length;i++){
    	if(errorsStr == ""){
    		errorsStr = errorsArray[i]+ ":";
    	}else{
    		if(errorsArray[i]){
	    		if(i == errorsArray.length-1)
	    			errorsStr = errorsStr+errorsArray[i];
	    		else
	    			errorsStr = errorsStr+errorsArray[i]+":";
    		}
    	}
    }
  
    for(var i=0;i<addedWordsArray.length;i++){
    	
    	if(addedWordStr == ""){
    		addedWordStr = addedWordsArray[i]+ ":";
    	}else{
    		if(addedWordsArray[i]){
	    		if(i == addedWordsArray.length-1)
	    			addedWordStr = addedWordStr+addedWordsArray[i];
	    		else
	    			addedWordStr = addedWordStr+addedWordsArray[i]+":";
    		}
    	}
    }
    
    for(var i=0;i<selfCorrectWordsArray.length;i++){
    	if(addedSelfCorrStr == ""){
    		addedSelfCorrStr = selfCorrectWordsArray[i]+ ":";
    	}else{
    		if(selfCorrectWordsArray[i]){
	    		if(i == selfCorrectWordsArray.length-1)
	    			addedSelfCorrStr = addedSelfCorrStr+selfCorrectWordsArray[i];
	    		else
	    			addedSelfCorrStr = addedSelfCorrStr+selfCorrectWordsArray[i]+":";
    		}
    	}
    }
 for(var i=0;i<prosodyArray.length;i++){
    	
    	if(addedProsodyStr == ""){
    		addedProsodyStr = prosodyArray[i]+ ":";
    	}else{
    		if(prosodyArray[i]){
	    		if(i == prosodyArray.length-1)
	    			addedProsodyStr = addedProsodyStr+prosodyArray[i];
	    		else
	    			addedProsodyStr = addedProsodyStr+prosodyArray[i]+":";
    		}
    	}
    }
    
    var hwAssignmentId=$("#hwAssignmentId").val();
    if(errors==""){
       errors=0;
    }
    if(correctWords<0){
	  	alert("Words Read should not less than errors");
	  	return false;
	}
    if(gradeTypesId==1){
    	$.ajax({
    		type : "POST",
    		url : "gradeFluencyTest.htm",
    		//url : "gradeSelfAndPeerTest.htm",
    		data : "assignmentQuestionId="+assignmentQuestionId+"&readingTypesId="+readingTypesId+"&wordsRead="+wordsRead+"&correctWords="+correctWords+"&errors="+errors+
    		"&errorIdsStr="+errorIdsStr+"&errorsStr="+errorsStr+"&addedWordStr="+addedWordStr+"&assignmentTitle="+assignmentTitle+"&dueDate="+dueDate+"&csId="+csId+"&studentId="+studentId+"&hwAssignmentId="+hwAssignmentId+"&gradeTypesId="+gradeTypesId+"&comment="+comment+"&addedSelfCorrStr="+addedSelfCorrStr+"&addedProsodyStr="+addedProsodyStr,
    		success : function(data) {
    			document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value=correctWords;
    			document.getElementById("hwAssignmentId").value=data.assignmentId;
    			document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value=correctWords;
    			document.getElementById("accuracyMarks:"+assignmentQuestionId+":"+readingTypesId).value=data.percentageAcquired;
    			window.parent.$('.ui-dialog-content:visible').dialog('close');
    			$("#gradeBenchmark").empty();  
    			systemMessage("Graded Successfully !!");
    			errorsArray.length = 0;
    			errorsAddress.length = 0;

    		}
        });
    }else{
   
   $.ajax({
		type : "POST",
		//url : "gradeFluencyTest.htm",
		url : "gradeSelfAndPeerTest.htm",
		data : "assignmentQuestionId="+assignmentQuestionId+"&readingTypesId="+readingTypesId+"&wordsRead="+wordsRead+"&correctWords="+correctWords+"&errors="+errors+
		"&errorIdsStr="+errorIdsStr+"&errorsStr="+errorsStr+"&addedWordStr="+addedWordStr+"&assignmentTitle="+assignmentTitle+"&dueDate="+dueDate+"&csId="+csId+"&studentId="+studentId+"&hwAssignmentId="+hwAssignmentId+"&gradeTypesId="+gradeTypesId+"&comment="+comment+"&addedSelfCorrStr="+addedSelfCorrStr+"&addedProsodyStr="+addedProsodyStr,
		success : function(data) {
			document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value=correctWords;
			document.getElementById("hwAssignmentId").value=data.assignmentId;
			document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value=correctWords;
			document.getElementById("accuracyMarks:"+assignmentQuestionId+":"+readingTypesId).value=data.percentageAcquired;
			window.parent.$('.ui-dialog-content:visible').dialog('close');
			$("#gradeBenchmark").empty();  
			systemMessage("Graded Successfully !!");
			errorsArray.length = 0;
			errorsAddress.length = 0;

		}
    });
    }
}

function submitAccuracyTest(readingTypesId,gradeTypesId){
	
	if(confirm("Do you want to submit?",function(status){
	var pattern = new RegExp(/[%&]/); 
		if(status){
	var addedWordStr="";
	var assignmentQuestionId=$("#questions").val();
	var wordsRead=$("#wordsread").val();
	
	if(wordsRead == 0){
		alert("Please Select Read Content");
    	return false;
    } 
	// wcpm Added
	var wcpm = $("#wcpm").val();
	if(wcpm == 0){
		alert("Please Play Audio to get WCPM Score");
    	return false;
	}
	var errors=$("#errors").val();
	var correctWords=$("#correctwords").val();
	var errorIdsStr="";
	var errorsStr="";
	var assignmentTitle = $("#assignmentTitle").val();
	var dueDate="";
		
	/*var dueDate = $("#dueDate").val();
	if(errorsAddress.length>0 && !dueDate){
    	alert("Please Select Due Date");
    	return false;
    } */
	var lessonId = $("#lessonId").val();
	var csId = $("#csId").val();
	var studentId = $("#studentId").val();
	var comment=window.document.getElementById("fluencyComment").value; 
	   if (!comment.replace(/\s/g, '').length) {
			alert("Please enter a comment");
		    return false;
		}
	  if (pattern.test(comment)) {
			 systemMessage("Special Characters(%&) Not Allowed in Comments");
			 $("#fluencyComment").focus();
			  return false;
		 }
	   var le=comment.length;
	   if(le>500){
	  	 alert("You have exceeded the more than 500 characters ");
	  	 $("#fluencyComment").focus();
	       return false;
	   }
		
	 for(var i=0;i<errorsAddress.length;i++){
	    	if(errorIdsStr == ""){
	    		errorIdsStr = errorsAddress[i]+ ":";
	    	}else{
	    		if(errorsAddress[i]){
		    		if(i == errorsAddress.length-1)
		    			errorIdsStr = errorIdsStr+errorsAddress[i];
		    		else
		    			errorIdsStr = errorIdsStr+errorsAddress[i]+ ":" ;
	    		}
	    	}
	    }
	 for(var i=0;i<errorsArray.length;i++){
	    	if(errorsStr == ""){
	    		errorsArray[i].replace("'","\\\\'");
	    		errorsStr = errorsArray[i]+ ":";
	    	}else{
	    		if(errorsArray[i]){
		    		if(i == errorsArray.length-1)
		    			errorsStr = errorsStr+errorsArray[i];
		    		else
		    			errorsStr = errorsStr+errorsArray[i]+":";
	    		}
	    	}
	    }
	 
	 for(var i=0;i<addedWordsArray.length;i++){
	    	
	    	if(addedWordStr == ""){
	    		addedWordStr = addedWordsArray[i]+ ":";
	    	}else{
	    		if(addedWordsArray[i]){
		    		if(i == addedWordsArray.length-1)
		    			addedWordStr = addedWordStr+addedWordsArray[i];
		    		else
		    			addedWordStr = addedWordStr+addedWordsArray[i]+":";
	    		}
	    	}
	    }
   if(errors==""){
       errors=0;
    }
   var errorWordsComments = $("[name=errComments]");
   var errorComments = [];
	
		
	
	for (var i = 0; i < errorWordsComments.length; i++) {
		var comt=errorWordsComments[i].value;
		if (!comt.replace(/\s/g, '').length) {
			alert("Please enter a comment");
		    return false;
		}
		errorComments.push(errorWordsComments[i].value);
	  
	}
	if(correctWords<0){
	  	alert("Words Read should not less than errors");
	  	return false;
	}
	var hwAssignmentId=$("#hwAssignmentId").val();
	$.ajax({
		type : "POST",
		url : "gradeAccuracyTest.htm",
		data : "assignmentQuestionId="+assignmentQuestionId+"&readingTypesId="+readingTypesId+"&wordsRead="+wordsRead+"&correctWords="+correctWords+"&errors="+errors+
		"&errorIdsStr="+errorIdsStr+"&assignmentTitle="+assignmentTitle+"&errorComments="+errorComments+"&errorsStr="+encodeURIComponent(errorsStr)+"&gradeTypesId="+gradeTypesId+
		"&addedWordStr="+encodeURIComponent(addedWordStr)+"&comment="+comment+"&dueDate="+dueDate+"&csId="+csId+"&studentId="+studentId+"&hwAssignmentId="+hwAssignmentId+"&wcpm="+wcpm,
		success : function(data) {
			 document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value=correctWords;
			 var per=document.getElementById("accuracyPer").value;
			 if(data.percentageAcquired!=undefined)
			 document.getElementById("accuracyMarks:"+assignmentQuestionId+":"+readingTypesId).value=data.percentageAcquired;
			 else
		     document.getElementById("accuracyMarks:"+assignmentQuestionId+":"+readingTypesId).value=per;	 
			 window.parent.$('.ui-dialog-content:visible').dialog('close');
			$("#gradeBenchmark").empty();  
			errorsAddress.length = 0;
			
		}
    });
	}else{
		return false;
	}}));
}
function playFluncyAudio(){
	var path = document.getElementById("audioPath").value;
	
	$.ajax({
		type : "GET",
		url : "playAudio.htm",
		data : "filePath="+path,
		success : function(response) {
		}
	});
}

function allowToEnterSentenceScore(){
    if(document.getElementById('booleanValue').value=="false"){
        document.getElementById('booleanValue').value="true";
        document.getElementById('sentenceStructureScore').readOnly =false;
        document.getElementById('sentenceStructureScore').focus();
        document.getElementById('sentenceStructureScore').select();
        return false;
    }
    else{
        document.getElementById('booleanValue').value="false";
        document.getElementById('sentenceStructureScore').readOnly =true;
        return false;
    }
}
function clears(){
	$("#title").empty(); 
	$("#title").append(
			$("<option></option>").val('')
					.html('Select Title'));
	 $("#Evaluate").empty(); 
	 $("#StuAssessQuestionsList").empty(); 	 
}
	 		   
function getGradingDetails(studentAssignmentId,userTypeId){
	// var lessonId = document.getElementById("lessonId").value;
	  var csId = document.getElementById("csId").value;
	  var studentId = document.getElementById("studentId").value;
	  var assignmentId = document.getElementById("assignmentId").value;
	  var gradeTypesId=document.getElementById("gradeTypeId").value;
	  if(gradeTypesId==0){
		  alert("please select grading");
		  return false;
	  }
	  $("#loading-div-background").show();
	  $.ajax({  
			url : "getGradingTypeDetails.htm", 
		    data: "studentAssignmentId="+studentAssignmentId+"&lessonId="+lessonId+"&csId="+csId+"&studentId="+studentId+"&assignmentId="+assignmentId+"&gradeTypesId="+gradeTypesId+"&userTypeId="+userTypeId,
		    success: function(response) {
		    	 $("#displayGrading").html(response);
		    	 if(userTypeId==3){
			    	 if(gradeTypesId==2 || gradeTypesId==3)
			    	 document.getElementById("showButton").style.visibility = "hidden";
			    	 else if(gradeTypesId==1)
			    	  document.getElementById("showButton").style.visibility = "visible";
		    	 }else if(userTypeId==4 || userTypeId==5){
		    		// document.getElementById("showButton").style.visibility = "hidden"; 
		    	 }
		    	 $("#loading-div-background").hide();
		    }
		}); 
}		

function openFluencyWindow(assignmentQuestionId,studentAssignmentId,benchmarkId,readingTypesId,gradeTypesId){
	var lessonId = document.getElementById("lessonId").value;
	  var csId = document.getElementById("csId").value;
	  var studentId = document.getElementById("studentId").value;
	  var marks = 0;
	  marks = document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value;
	  if(marks=="")
		  marks=-1;
	  
	 if(marks >=0){
		 if(confirm("Would you like to regrade this test?",function(status){
				if(status){
					 
				   errorsAddress.length= 0;
				   view = false;
				   $("#loading-div-background").show();
			    	$.ajax({
	 		       		type : "GET",
	 		       		url : "getFluencyTest.htm",
	 		       		data : "assignmentQuestionId="+assignmentQuestionId+"&studentAssignmentId="+studentAssignmentId+"&benchmarkId="+benchmarkId +"&readingTypesId="+readingTypesId+"&marks="+marks+"&regrade=yes"+"&butt=yes&lessonId="+lessonId+"&csId="+csId+"&studentId="+studentId+"&gradeTypesId="+gradeTypesId,
	 		       		success : function(response) {
	 		       		$("#loading-div-background").hide();
	 		       			document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value = "";
	 		                getBenchmarkTest(readingTypesId,response);
	 		               if(readingTypesId==2){
		 		       			document.getElementById("correctwords").value = 0;
		 		       			document.getElementById("accuracyMarks:"+assignmentQuestionId+":"+readingTypesId).value ="";
		 		       		    }else{
		 		       		    document.getElementById("retellComment").value="";
		 		       		}
		 		       	   document.getElementById("errorTable").style.visibility = "hidden";
	 		       	       document.getElementById("errors").value = 0;
	 		       	       document.getElementById("wordsread").value = 0;
	 		       	       document.getElementById("fluencyComment").value = "";
	 		       		}
	 		       	});
			   }else{
				     
				    view = true;
				    $("#loading-div-background").show();
				     $.ajax({
	 		       		type : "GET",
	 		       		url : "getFluencyTest.htm",
	 		       		data : "assignmentQuestionId="+assignmentQuestionId+"&studentAssignmentId="+studentAssignmentId+"&benchmarkId="+benchmarkId+"&readingTypesId="+readingTypesId+"&marks="+marks+"&regrade=no"+"&butt=no&lessonId="+lessonId+"&csId="+csId+"&studentId="+studentId+"&gradeTypesId="+gradeTypesId,
	 		       		success : function(response) {
	 		       		 $("#loading-div-background").hide();
	 		       		getBenchmarkTest(readingTypesId,response);
		 		       	document.getElementById("errorTable").style.visibility = "visible";
		 		        $('#correctwords').prop('disabled', true);
		 		        $('#errors').prop('disabled', true);
		 		        $('#wordsread').prop('disabled', true);
	 		       		}
	 		       	});
			   }
			}));	
	     }else{
	    	 
	    	    view = false;
	    	    $("#loading-div-background").show();
	        	$.ajax({
	       		type : "GET",
	       		url : "getFluencyTest.htm",
	       		data : "assignmentQuestionId="+assignmentQuestionId+"&studentAssignmentId="+studentAssignmentId+"&benchmarkId="+benchmarkId+"&readingTypesId="+readingTypesId+"&marks="+marks+"&regrade=new"+"&butt=yes&lessonId="+lessonId+"&csId="+csId+"&studentId="+studentId+"&gradeTypesId="+gradeTypesId,
	       		success : function(response) {
	       			
	       			$("#loading-div-background").hide();
	       			getBenchmarkTest(readingTypesId,response);
	 		       	document.getElementById("errorTable").style.visibility = "hidden";
	 		         
		       		}
	       	});
	    }
}

var enable = true;
var dataCon;
var fluAddWords;
function showAllFluencyScores(regrade,assignmentTypeId) {
	$('#contextStat').val(0);
	var optId=0;
	document.getElementById("errorTable").style.visibility = "visible";	
	enable = false;
	if(assignmentTypeId==20){
		removeAccuracyErrorList();
	}
	document.getElementById("teacher").disabled=false;
	var reg=0;
	var type,col;
	if(regrade=="yes")
		reg=1;
	
	var assignmentQuestionId=$("#questions").val();	
	 $.ajax({
			type : "POST",
			url : "getSelfAndPeerFluencyScores.htm",
			data : "assignmentQuestionId="+assignmentQuestionId+"&assignmentTypeId="+assignmentTypeId,
			success : function(data) {
             dataCon=data;				
				if(document.getElementById("showFluency").value=="no"){
					
					 var fluencyMarks=data.fluencyMarks;
					 var ele = document.getElementsByClassName("myelement");
					 var ftable=document.getElementById('scoreTable');
					 
			      var i=3;
				$.each(fluencyMarks,
						function(index, value) {
					
					if(value.gradingTypes.gradingTypesId==1){
					fluAddWords=value.fluencyAddedWordsLt;
					enable=true;
					var colo="red";
					if(assignmentTypeId==20)
					insertValuesToErrTable(value.gradingTypes.gradingTypesId,value,reg,colo,optId);
					}
					
					if(value.gradingTypes.gradingTypesId!=1){
					enable = false;
				    var rowCount = ftable.rows.length;
					var row = ftable.insertRow(rowCount);
				    row.id="sco"+i;
					  
					   var c1=row.insertCell(0);
				       var c2=row.insertCell(1);
				       var c3=row.insertCell(2);
				       var c4=row.insertCell(3);
				       c1.style.width="100px";
				       c2.style.width ="100px";
				       c3.style.width ="100px";
				       c4.style.width ="150px";
				       c4.align="center";
				       if(assignmentTypeId==20){
					       var c5=row.insertCell(4);
					       c5.style.width="150px";
					       c5.align="center";
					       var c6=row.insertCell(5);
					       c6.style.width="150px";
					       c6.align="center";
				       }
				       var wordsCount=0;
				       var countErrors=0;
				       var markss=0;
				       var accScore=0;
				       var wcpm=0;
				       if(value.wordsRead!=null){
							wordsCount=value.wordsRead;
						    countErrors=value.countOfErrors;
						    markss=value.marks;
						    accScore=value.accuracyScore;
						    wcpm = value.wcpm != null ? value.wcpm : 0 ;
						}	
				       if(value.gradingTypes.gradingTypesId==2){
				    	   type="Student";
				    	   col="magenta";
				    	   if(assignmentTypeId==20)
				    	   insertValuesToErrTable(value.gradingTypes.gradingTypesId,value,reg,col,optId);
				       }else{
				    	   type="Peer";
				    	   col="#800517"; 
				    	   if(assignmentTypeId==20)
				    	   insertValuesToErrTable(value.gradingTypes.gradingTypesId,value,reg,col,optId);
				       }
						c1.innerHTML="<input type='radio' class='radio-design' id='student"+value.gradingTypes.gradingTypesId+"' name='gradeType' onClick='getFluencyScoresByGradeType("+value.gradingTypes.gradingTypesId+","+reg+","+assignmentTypeId+")' /><label for='student"+value.gradingTypes.gradingTypesId+"' class='radio-label-design'>"+type+"</label>";	
					    var str="<input type='text' id='SWordsRead' name='SWordsRead' value='"+wordsCount+"'  disabled/>";
				       c2.innerHTML=str;
				       var str="<input type='text' id='SErrors' name='SErrors' value='"+countErrors+"' disabled/>";
				       c3.innerHTML=str;
				       var str="<input type='text' id='SWordsCount' name='SWordsCount' value='"+markss+"' disabled/>";
				       c4.innerHTML=str;
				       if(assignmentTypeId==20){
				    	   var str="<input type='text' id='SAccuracyPer' name='SAccuracyPer' value='"+accScore+"' disabled/>";
					       c5.innerHTML=str;
					       var str1="<input type='text' id='wcpm"+index+"' name='wcpm' value='"+wcpm+"' disabled/>";
					       c6.innerHTML=str1;
				       }
				       $.each(value.fluencyMarksDetails,
								function(index, val) {
				    	    if(ele[val.errorsAddress-1].style.textDecoration!='line-through'){
				    	    	ele[val.errorsAddress-1].style.textDecoration='line-through';
				    	  	    ele[val.errorsAddress-1].style.color=col;
				    	    }else{
				    	       	if(ele[val.errorsAddress-1].style.color!="blue"){
				    	    	 ele[val.errorsAddress-1].style.color='#6C2DC7';}
				    	    }
				    	  	 if(val.unknownWord!=null)
				    	    	ele[val.errorsAddress-1].style.color='green';
				    	  	 if(val.skippedWord!=null)
					    	    ele[val.errorsAddress-1].style.color='blue'; 
				    	  	   
						});   
					i++;
					}
					
	    });
			
				var rowCount = ftable.rows.length;
				var row = ftable.insertRow(rowCount);
				  row.id="sco"+i;
				   var c1=row.insertCell(0);
				   var c2=row.insertCell(1);
			       var c3=row.insertCell(2);
			       var c4=row.insertCell(3);
			       c1.style.width="100px";
			       c2.style.width ="100px";
			       c3.style.width ="100px";
			       c4.style.width ="100px";
			    c1.innerHTML="<input type='radio' class='radio-design' id='allScores' name='gradeType' onClick='getFluencyScoresByGradeType(0,"+reg+","+assignmentTypeId+")' checked/><label for='allScores' class='radio-label-design'>Show All Scores</label>";		
				document.getElementById("colorDis").style.visibility = "visible";
				if(assignmentTypeId==20)
					document.getElementById("showsAcc").innerHTML="Remove Scores";
				else	
				    document.getElementById("shows").innerHTML="Remove Scores";
				 document.getElementById("showFluency").value="yes";
				}else{
		        $('#contextStat').val(1);
				enable = true;
				removeFluencyScores(data,reg,assignmentTypeId);
				
			}
			}
	 });
}
function removeFluencyScores(data,regrade,assignmentTypeId){
	var fluencyMarks=data.fluencyMarks;
	var eles = document.getElementsByClassName("myelement");
	var ftable=document.getElementById('scoreTable');
	var div=document.getElementById("contextMenuDiv");
	div.style.textAlign = "justify";
	$.each(fluencyMarks,function(index, value) {
	 if(value.gradingTypes.gradingTypesId==1){
		 clearStyles();
		 col="red";
	    setStylesByGradeType(value,col,regrade,assignmentTypeId,1);
	    getTeacherFluencyValuesbyRegrade(regrade,value.wordsRead);
	}
});
for(var m=3;m<=5;m++){
	  var row = document.getElementById("sco"+m);
	 // row.parentNode.removeChild(row);
      $("#sco"+m).remove();
}
if(assignmentTypeId==8)
	document.getElementById("shows").innerHTML="Show All Scores";
else
	document.getElementById("showsAcc").innerHTML="Show All Scores";	
document.getElementById("showFluency").value="no";
document.getElementById("colorDis").style.visibility = "hidden";
$("span").bind('click');
		
}

function getFluencyScoresByGradeType(gradeTypeId,regrade,assignmentTypeId) {
	 clearStyles();
	$('#contextStat').val(gradeTypeId);
	var fluencyMarks;
	if(assignmentTypeId==20){
		removeAccuracyErrorList();
	}
	var assignmentQuestionId=$("#questions").val();
	$.ajax({
		type : "POST",
		url : "getSelfAndPeerFluencyScores.htm",
		data : "assignmentQuestionId="+assignmentQuestionId+"&assignmentTypeId="+assignmentTypeId,
		success : function(datas) {
			fluencyMarks=datas.fluencyMarks;
			fluAddWords=datas.fluencyTeacherAddedWords;
			enable = false;
	 if(gradeTypeId==1){
		 enable=true;
	 }
	 var eles = document.getElementsByClassName("myelement");
	 var div=document.getElementById("contextMenuDiv");
	 div.style.textAlign = "justify";
	 var col="";	
	 clearStyles();
	 
	$.each(fluencyMarks,function(index, value) {
			if(gradeTypeId==0){
				
				getAllScores(value,regrade,assignmentTypeId,gradeTypeId);
				 
			}else{			
			
			 if(value.gradingTypes.gradingTypesId==2 && value.gradingTypes.gradingTypesId==gradeTypeId){
				 
					 col="magenta";
					 getSelfandPeerRegradeValues(regrade,value.wordsRead);
					 setStylesByGradeType(value,col,regrade,assignmentTypeId,gradeTypeId);
			 }else if(value.gradingTypes.gradingTypesId==3 && value.gradingTypes.gradingTypesId==gradeTypeId){
				 
					 col="#800517";
					 getSelfandPeerRegradeValues(regrade,value.wordsRead);
					 setStylesByGradeType(value,col,regrade,assignmentTypeId,gradeTypeId);	    	    
			 }else if(value.gradingTypes.gradingTypesId==1 && value.gradingTypes.gradingTypesId==gradeTypeId){
				 
					 //enable=true;
					 col="red";
					 fluAddWords=value.fluencyAddedWordsLt;
     		         setStylesByGradeType(value,col,regrade,assignmentTypeId,gradeTypeId);
     		         getTeacherFluencyValuesbyRegrade(regrade,value.wordsRead);
					 
					 } 
				
			 }
	
	});
		}  
	});
}

function clearStyles(){
	var eles = document.getElementsByClassName("myelement");
	 for(var n=0;n<eles.length;n++){
		 eles[n].style.textDecoration='none';
	  	 eles[n].style.color='';
	  	 eles[n].style.backgroundColor ='';
	  	 
	 }
}
function setStylesByGradeType(value,col,regrade,assignmentTypeId,gradeTypeId){
	 var ele = document.getElementsByClassName("myelement");
	 var count=0;
	 $.each(value.fluencyMarksDetails,
				function(index, val) {
		 		 
 	    if(ele[val.errorsAddress-1].style.textDecoration!='line-through'){
 	    	
 	    	ele[val.errorsAddress-1].style.textDecoration='line-through';
 	  	    ele[val.errorsAddress-1].style.color=col;
 	  	    
 	    }else{
 	    	
 	       	if(ele[val.errorsAddress-1].style.color!="blue"){
 	    	 ele[val.errorsAddress-1].style.color='#6C2DC7';}
 	    }
 	  	 if(val.unknownWord!=null)
 	    	ele[val.errorsAddress-1].style.color='green';
 	  	 if(val.skippedWord!=null)
	    	    ele[val.errorsAddress-1].style.color='blue'; 
 	  	   
		}); 
	 if(assignmentTypeId==20){
	 insertValuesToErrTable(value.gradingTypes.gradingTypesId,value,regrade,col,gradeTypeId);
	 }
	 	
}
function getTeacherFluencyValuesbyRegrade(regrade,wordRead){
	var eles = document.getElementsByClassName("myelement");
	if(regrade==1){
		 for(var j=0;j<errorsAddress.length;j++){
			 if(errorsArray[j].indexOf('@') !== -1){
				    eles[errorsAddress[j]-1].style.textDecoration='line-through';
	    	  	    eles[errorsAddress[j]-1].style.color='blue'; 
			    	}else{
			    		eles[errorsAddress[j]-1].style.textDecoration='line-through';
		    	  	    eles[errorsAddress[j]-1].style.color='red';
			    	} 
		 } 
		    for(var i=0;i<addedWordsArray.length;i++){
				 var str = addedWordsArray[i].split("$");
				 viewAddedWordBox(str[0],str[1]);
			 }
			for(var i=0;i<selfCorrectWordsArray.length;i++){
				var str = selfCorrectWordsArray[i].split("$");
				$('#'+str[0]).css('color', '#D7D700');
	        	 viewSelfCorrectBox(str[0],str[1]);
				}
			for(var i=0;i<prosodyArray.length;i++){
				var str = prosodyArray[i].split("$");
				viewProsodyBox(str[0],str[1]);
				}
			var wordRead=document.getElementById("wordsread").value;
		    
	 }else{
	 $.each(fluAddWords,
				function(index, val) {
		         if(val.wordType==2){
		        	 $('#'+val.wordIndex).css('color', '#D7D700');
		        	 viewSelfCorrectBox(val.wordIndex,val.addedWord);
		         }
		         if(val.wordType==1){
		        	 viewAddedWordBox(val.wordIndex,val.addedWord);
                }
		         if(val.wordType==3){
		        	 viewProsodyBox(val.wordIndex,val.addedWord);
                }
		     	    
	     }); 
}
	for(var n=0;n<wordRead;n++)
		 eles[n].style.backgroundColor ='#9AFEFF';
}
function getSelfandPeerRegradeValues(regrade,wordRead){
	var eles = document.getElementsByClassName("myelement");
	if(regrade==1){
		for(var i=0;i<addedWordsArray.length;i++){
			var str = addedWordsArray[i].split("$");
			 $('#comment'+str[0]).remove();
             $('#errorIcon'+str[0]).remove(); 
			}
		for(var i=0;i<selfCorrectWordsArray.length;i++){
			var str = selfCorrectWordsArray[i].split("$");
			 $('#selfCorrect'+str[0]).remove();
             $('#errorIcon1'+str[0]).remove(); 
			}
		for(var i=0;i<prosodyArray.length;i++){
			var str = prosodyArray[i].split("$");
			 $('#prosody'+str[0]).remove();
             $('#errorIcon2'+str[0]).remove(); 
			}
		 wordRead=document.getElementById("wordsread").value;
	    
	}else{	
				
	 $.each(fluAddWords,
				function(index, val) {
		         if(val.wordType==2){
		           	$('#'+val.wordIndex).css('color', 'Black'); 
		         	$('#selfCorrect'+val.wordIndex).remove();
		         	$('#errorIcon1'+val.wordIndex).remove();
		       }
		         if(val.wordType==1){
		        	
		        	 $('#comment'+val.wordIndex).remove();
	 	             $('#errorIcon'+val.wordIndex).remove(); 
			     }
		         if(val.wordType==3){
			        	
		        	 $('#prosody'+val.wordIndex).remove();
	 	             $('#errorIcon2'+val.wordIndex).remove(); 
			     }
		        	 
		    	    
	     });
	
	}
	for(var n=0;n<wordRead;n++)
		 eles[n].style.backgroundColor ='#9AFEFF';
	 
	 
}
function viewSelfCorrectBox(wordIndex,addedWord)
{
	if(!document.getElementById("selfCorrect"+wordIndex)){
		  $('#'+wordIndex).after("<textarea id='selfCorrect"+wordIndex+"' name='textarea"+wordIndex+"' style='display:none;font-size: 13px;' onblur='closeSelfCorrect("+wordIndex+")' class='box' onkeypress='searchKeyPress1("+wordIndex+")'>"+addedWord+"</textarea>");
     }
  	if(!document.getElementById("errorIcon1"+wordIndex))
 	  $('#'+wordIndex).after("<div id='errorIcon1"+wordIndex+"' class='fa fa-bolt fa-flip-horizontal' aria-hidden='true' style='color:#D7D700;font-size:20px;' onClick='showSelfCorrectBox("+wordIndex+")'></div>");
}
function viewAddedWordBox(wordIndex,addedWord)
{
	if(!document.getElementById("comment"+wordIndex)){
     	 $('#'+wordIndex).after("<textarea id='comment"+wordIndex+"' name='textarea"+wordIndex+"' style='display:none;font-size: 13px;' onblur='closeComment("+wordIndex+")' class='box' onkeypress='searchKeyPress("+wordIndex+")'>"+addedWord+"</textarea>");
     }
 	if(!document.getElementById("errorIcon"+wordIndex))
 	  $('#'+wordIndex).after("<div id='errorIcon"+wordIndex+"' class='fa fa-bolt fa-flip-horizontal' aria-hidden='true' style='color:#ff216c;font-size:20px;' onClick='showCommentBox("+wordIndex+")'></div>");
}
function viewProsodyBox(wordIndex,addedWord)
{
	if(!document.getElementById("prosody"+wordIndex)){
		  $('#'+wordIndex).after("<textarea id='prosody"+wordIndex+"' name='textarea"+wordIndex+"' style='display:none;font-size: 13px;' onblur='closeProsody("+wordIndex+")' class='box' onkeypress='searchKeyPress2("+wordIndex+")'>"+addedWord+"</textarea>");
     }
  	if(!document.getElementById("errorIcon2"+wordIndex))
 	  $('#'+wordIndex).after("<div id='errorIcon2"+wordIndex+"' class='fa fa-hand-paper-o' aria-hidden='true' style='color:#DC7633;font-size:20px;' onClick='showProsody("+wordIndex+")'></div>");
}
function getAllScores(value,regrade,assignmentTypeId,gradeTypeId){
		
	var wordsCount=0,countErrors=0,markss=0;
	var eles = document.getElementsByClassName("myelement");
	var teacherWordsCount=0;
	if(value.wordsRead!=null){
		wordsCount=value.wordsRead;
	    countErrors=value.countOfErrors;
	    markss=value.marks;
	}
 if(value.gradingTypes.gradingTypesId==2){
	 col="magenta";
	 setStylesByGradeType(value,col,regrade,assignmentTypeId,gradeTypeId);
 }
else if(value.gradingTypes.gradingTypesId==3){
	 col="#800517";
	 setStylesByGradeType(value,col,regrade,assignmentTypeId,gradeTypeId);
}else if(value.gradingTypes.gradingTypesId==1){
	teacherWordsCount=wordsCount;
	col="red";
	setStylesByGradeType(value,col,regrade,assignmentTypeId,gradeTypeId);
	getTeacherFluencyValuesbyRegrade(regrade);
}
for(var n=0;n<teacherWordsCount;n++)
 eles[n].style.backgroundColor ='#9AFEFF';

}


function submitZeroFluencyScore(readingTypesId,gradeTypesId){
	var assignmentQuestionId=$("#questions").val();
	
	var lessonId = $("#lessonId").val();
	var csId = $("#csId").val();
	var studentId = $("#studentId").val();  
   
    $.ajax({
		type : "GET",
		url : "submitZeroFluencyScore.htm",
		data : "assignmentQuestionId="+assignmentQuestionId+"&readingTypesId="+readingTypesId+"&csId="+csId+"&studentId="+studentId+"&gradeTypesId="+gradeTypesId,
		success : function(data) {/*
			document.getElementById("marks:"+readingTypesId).value=correctWords;
			document.getElementById("hwAssignmentId").value=data.assignmentId;
			document.getElementById("marks:"+readingTypesId).value=correctWords;
			document.getElementById("accuracyMarks:"+readingTypesId).value=data.percentageAcquired;*/
			window.parent.$('.ui-dialog-content:visible').dialog('close');
			$("#gradeBenchmark").empty();  
			errorsArray.length = 0;
			errorsAddress.length = 0;
		}
    });
}

function editStrikeFluencyText(fieldObj, i,errorType,testType,regradeStat) {
	
	if(enable == true){
		var j=0;
		var val=document.getElementById("word"+i).value;
		chkErrorExists(fieldObj, i,"error");
		var style = (fieldObj.style.textDecoration!='line-through')?'line-through':'none';
	    
		if(style == 'none'){
		   errorsAddress.splice(errorsAddress.indexOf(i), 1);
		   errorsArray.splice(errorsArray.indexOf(val), 1);
	       document.getElementById("errors").value = --document.getElementById("errors").value ;// --clicks;
	       fieldObj.style.textDecoration="none";
	       fieldObj.style.color='Black';
	       var correctwords = parseInt(document.getElementById("correctwords").value);
	       document.getElementById("correctwords").value = correctwords+1;
	       removeErrorWord(val,i,regradeStat);
	   }
	   else{
	       fieldObj.style.textDecoration = style;
	       errorsAddress.push(i);
	       var lastChar = val.charAt(val.length-1);
	       if(lastChar == ":")
	       	val = val.slice(0, -1);
	       if (val.endsWith(",") || val.endsWith("?") || val.endsWith(".")){
				val = val.substring(0, val.length - 1);
			}
	        val = val.toString().replace("\â€�", ""); 
	        val = val.toString().replace("\â€œ", "");
	        val = val.toString().replace("\,", "");
	        val = val.toString().replace("\'", ""); 
	       	if(errorType == 'unKnownWord'){
				val = val+'$';
			}else if(errorType == 'skippedWord'){
				val = val+'@';
			}
	       errorsArray.push(val);
	       document.getElementById("errors").value = ++document.getElementById("errors").value; //++ clicks
	       if(errorType == 'errorWord')
	    	  fieldObj.style.color='red';
	       else if(errorType == 'unKnownWord')
	    	  fieldObj.style.color='green';
	       else if(errorType == 'skippedWord')
	    	   fieldObj.style.color='blue';
	       else if(errorType == 'addSelfCorrect')
	    	   fieldObj.style.color='yellow';
	      var correctwords = parseInt(document.getElementById("correctwords").value);
	      if(correctwords > 0)
	         document.getElementById("correctwords").value = correctwords-1;
	      checkSelectRange(i);
	      autoSaveErrorWord(val,i,errorType,regradeStat);
	   }
	  
	   var errorsCnt = document.getElementById("errors").value;
	   if(errorsCnt > 0){
	   	if(!view)
	   		$("#homeWorkDiv").show();
	   }else{
	   	$("#homeWorkDiv").hide();
	   }
	
	   return true;
	}else {
		alert("Please remove score to continue..");
	}
}

function autoSaveComment(index,errorAddress){
	document.getElementById("rem:"+index).innerHTML="";
	var errComment=document.getElementById("errs:"+index).value;
	/*var pattern = new RegExp(/[~`!#$%\^&*+=\-\[\]\\';,/{}|\\":<>\?]/);
	if (pattern.test(errComment)) {
		 alert("Special Characters Not Allowed in Error Comments");
		 $("#errs:"+index).focus();
		 return false;
	}*/
	var assignmentQuestionId=$("#questions").val();
    var readingTypesId=$("#readingTypesId").val();
 	var gradeTypesId=$("#gradeTypesId").val();
 	 $.ajax({
 		type : "GET",
 		url : "autoSaveErrorComment.htm",
 		data : "assignmentQuestionId=" +assignmentQuestionId +"&readingTypesId=" + readingTypesId+"&gradeTypesId="+gradeTypesId+"&errComment="+errComment+"&errorAddress="+errorAddress,
 		success : function(response) {
 			//alert(response);
 		}
 	});
	 accETable.set(errorAddress, errComment);
	
}
function checkSelectRange(wordnum){
	var lastRange=0;
	if(document.getElementById("wordsRead") != null) {
		lastRange=document.getElementById("wordsRead").value;
	} else {
		lastRange=document.getElementById("wordsread").value;
	}
	if(lastRange>0){
     if(wordnum>lastRange){
		for(var i=lastRange;i<=wordnum;i++){
    		var sp = document.getElementById(''+i);         
            sp.style.backgroundColor='#9AFEFF';
    }	
		if(document.getElementById("wordsRead") != null) {
	    	document.getElementById("wordsRead").value=wordnum;
		} else {
			document.getElementById("wordsread").value=wordnum;
		}
    	
    }
    }
 }
function removeFluencyAddedWord(wordNum,value,wordType,regradeStat){
	
	var wdRead=0,totRead=0,tot=0;
	  var assignmentQuestionId=$("#questions").val();
	     var readingTypesId=$("#readingTypesId").val();
	 	 var gradeTypesId=$("#gradeTypesId").val(); 
	 	 var val=document.getElementById("word"+wordNum).value;
	 	var errRead=document.getElementById("errors").value;
	 	 if(regradeStat=="no"){
		 var wdRead=document.getElementById("wordsread").value;
	     var totRead=document.getElementById("correctwords").value;
	     tot=wdRead-errRead;
	     document.getElementById("correctwords").value=tot;
	 	 }
	     addWord=wordNum+"$"+value;
	     
 	$.ajax({
			type : "GET",
			url : "removeAddedWord.htm",
			data : "assignmentQuestionId=" +assignmentQuestionId +"&readingTypesId=" + readingTypesId+"&gradeTypesId="+gradeTypesId+"&addedWord="+addWord+"&wordsRead="+wdRead+"&errRead="+errRead+"&totalRead="+tot+"&wordType="+wordType+"&regradeStat="+regradeStat,
			success : function(response) {
			if(regradeStat=="no"){
			document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value=tot;
			document.getElementById("accuracyMarks:"+assignmentQuestionId+":"+readingTypesId).value=response.percentage;
			}	
		}
		});	
}
function saveFluencyAddedWord(wordNum,value,wordType,regradeStat){
	var wdRead=0,totRead=0,tot=0;
	
	 var assignmentQuestionId=$("#questions").val();
     var readingTypesId=$("#readingTypesId").val();
 	 var gradeTypesId=$("#gradeTypesId").val();
	 var val=document.getElementById("word"+wordNum).value;
	 var errRead=document.getElementById("errors").value;
	 if(errRead=="")
		 errRead=0;
	 if(regradeStat=="no"){
	  wdRead=document.getElementById("wordsread").value;
      totRead=document.getElementById("correctwords").value;
      tot=wdRead-errRead;
      document.getElementById("correctwords").value=tot;
     }
     addWord=wordNum+"$"+value;
    
     //alert(assignmentQuestionId+","+readingTypesId+","+gradeTypesId+","+addWord+","+wdRead+","+errRead+","+tot+","+wordType+","+regradeStat);
$.ajax({
	type : "GET",
	url : "autoSaveAddedWord.htm",
	data : "assignmentQuestionId=" +assignmentQuestionId +"&readingTypesId=" + readingTypesId+"&gradeTypesId="+gradeTypesId+"&addedWord="+addWord+"&wordsRead="+wdRead+"&errRead="+errRead+"&totalRead="+tot+"&wordType="+wordType+"&regradeStat="+regradeStat,
	success : function(response) {
		 if(regradeStat=="no"){
		document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value=tot;
		document.getElementById("accuracyMarks:"+assignmentQuestionId+":"+readingTypesId).value=response.percentage;
		 }
	}
});	
}
function autoSaveErrorWord(val,i,errorType,regrade){
	 var wdRead=0,totRead=0,tot=0;
	 var assignmentQuestionId=$("#questions").val();
     var readingTypesId=$("#readingTypesId").val();
 	 var gradeTypesId=$("#gradeTypesId").val(); 
 	 var errRead=document.getElementById("errors").value;
 	 if(regrade=="no"){
 	 var wdRead=document.getElementById("wordsread").value;
     var totRead=document.getElementById("correctwords").value;
     tot=wdRead-errRead;
     document.getElementById("correctwords").value=tot;
     }
     
      $.ajax({
 		type : "GET",
 		url : "autoSaveErrorWord.htm",
 		data : "assignmentQuestionId=" +assignmentQuestionId +"&readingTypesId=" + readingTypesId+"&gradeTypesId="+gradeTypesId+"&errorWord="+val+"&errorAddress="+i+"&errorType="+errorType+"&wordsRead="+wdRead+"&errRead="+errRead+"&totalRead="+tot+"&regrade="+regrade,
 		success : function(response) {
 		if(regrade=="no"){
 			document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value=tot;
 			document.getElementById("accuracyMarks:"+assignmentQuestionId+":"+readingTypesId).value=response.percentage;
 			}
 			}
 	});	
}

function autoSaveSkippedSentence(lstSkipWords,lstSkipErrAddress,errorType,testType,regradeStat){
	var wdRead=0,totRead=0,tot=0;
	var assignmentQuestionId=$("#questions").val();
    var readingTypesId=$("#readingTypesId").val();
	var gradeTypesId=$("#gradeTypesId").val();
	var errRead=document.getElementById("errors").value;
	if(regradeStat=="no"){
	var wdRead=document.getElementById("wordsread").value;
    var totRead=document.getElementById("correctwords").value;
    tot=wdRead-errRead;
    document.getElementById("correctwords").value=tot;
    var s=parseInt(tot) + parseInt(errRead);
    var accPercentage = parseInt(tot * 100) /parseInt(s);
	}
    if(testType=='accuracy')
    document.getElementById("accuracyPer").value=accPercentage.toFixed(2);
        
     $.ajax({
		type : "GET",
		url : "autoSaveSkippedSentence.htm",
		data : "assignmentQuestionId=" +assignmentQuestionId +"&readingTypesId=" + readingTypesId+"&gradeTypesId="+gradeTypesId+"&errorWord="+lstSkipWords+"&errorAddress="+lstSkipErrAddress+"&errorType="+errorType+"&wordsRead="+wdRead+"&errRead="+errRead
		+"&totalRead="+tot+"&regrade="+regradeStat,
		success : function(response) {
			if(testType=="fluency" && regradeStat=="no"){
			document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value=tot;
			document.getElementById("accuracyMarks:"+assignmentQuestionId+":"+readingTypesId).value=response.percentage;
			}
		}
	});	
}
function removeErrorWord(val,i,regradeStat){
	
	var wdRead=0,totRead=0,tot=0;
	
	 var assignmentQuestionId=$("#questions").val();
     var readingTypesId=$("#readingTypesId").val();
 	 var gradeTypesId=$("#gradeTypesId").val();
	 var errRead=document.getElementById("errors").value;
	 
	 if(regradeStat=="no"){
		 var wdRead=document.getElementById("wordsread").value;
		 var totRead=document.getElementById("correctwords").value;
	     tot=wdRead-errRead;
	     document.getElementById("correctwords").value=tot;
	 }
     
     $.ajax({
 		type : "GET",
 		url : "removeErrorWord.htm",
 		data : "assignmentQuestionId=" +assignmentQuestionId +"&readingTypesId=" + readingTypesId+"&gradeTypesId="+gradeTypesId+"&errorWord="+val+"&errorAddress="+i+"&wordsRead="+wdRead+"&errRead="+errRead+"&totalRead="+tot+"&regradeStat="+regradeStat,
 		success : function(response) {
 			
 			if(regradeStat=="no"){
 			document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value=tot;
 			document.getElementById("accuracyMarks:"+assignmentQuestionId+":"+readingTypesId).value=response.percentage;
 			}
 		}
 	});	
}
function autoDisplayErrorCount(testType){
	 var wdRead=document.getElementById("wordsread").value;
	 if(wdRead!=0){
		
     var errRead=document.getElementById("errors").value;
     var totRead=document.getElementById("correctwords").value;
     tot=wdRead-errRead;
     document.getElementById("correctwords").value=tot;
     if(testType=="accuracy"){
     var s=parseInt(tot) + parseInt(errRead);
     var accPercentage = parseInt(tot * 100) /parseInt(s);
     document.getElementById("accuracyPer").value=accPercentage.toFixed(2);
	 }
    } 
}
function openAccuracyWindow(assignmentQuestionId,studentAssignmentId,benchmarkId,readingTypesId,gradeTypesId,userTypeId){
	
	/*var lessonId = document.getElementById("lessonId").value;*/
	var csId = document.getElementById("csId").value;
	var studentId = document.getElementById("studentId").value;
	var marks = 0;
	marks = document.getElementById("marks:" + assignmentQuestionId + ":"+ readingTypesId).value;
	view = true;
    if(marks>0){
	$("#loading-div-background").show();
	$.ajax({
		type : "GET",
		url : "getBenchmarkTest.htm",
		data : "assignmentQuestionId=" + assignmentQuestionId
				+ "&studentAssignmentId=" + studentAssignmentId
				+ "&benchmarkId=" + benchmarkId + "&readingTypesId="
				+ readingTypesId + "&marks=" + marks + "&regrade=view"
				+ "&butt=no&csId=" + csId
				+ "&studentId=" + studentId + "&gradeTypesId=" + gradeTypesId
				+ "&userTypeId=" + userTypeId,
		success : function(response) {
			$("#loading-div-background").hide();
			getBenchmarkTest(readingTypesId, response);
			document.getElementById("errorTable").style.visibility = "visible";

		}
	});
    }else{
    	alert("Test Not Graded Yet");
    }
} 
function removeAccuracyErrorList(){
	var etable=document.getElementById('dataTable');
    var rowCount = etable.rows.length;
	 for (var k = rowCount-1; k >= 0; k--) {
     	etable.deleteRow(k);
     	
     }
}
function showAccuracyErrorList(value,col,optId){
	
	var etable=document.getElementById('dataTable');
    var rowCount = etable.rows.length;
	var m=0;
	var rowCount = etable.rows.length;
	 $.each(value.fluencyMarksDetails,
				function(index, val) {
		        
	    
   	    	/* if(!(val.errorWord.endsWith('@') || val.errorWord.endsWith('$'))){*/
	    	   if(!(val.unknownWord || val.skippedWord)){
	    		var row = etable.insertRow(rowCount); 
	    		etable.prepend(row);
	    	    row.id="addedError"+val.errorsAddress;
	    	    var c1=row.insertCell(0);
	    	    var c2=row.insertCell(1);
	    	    var c3=row.insertCell(2);
	    	    c1.style.width="200px";
	    	    c1.style.color=col;
	    	    c1.innerHTML=val.errorWord;
	    	    if(value.gradingTypes.gradingTypesId==1 && optId!=0)
	    	    var str="<textarea name='errComments' id='errs:"+m+"' onkeypress='return checkLength("+val.errorsAddress+","+m+",event)' onKeyDown='return setLength("+val.errorsAddress+","+m+",event)' onblur='autoSaveComment("+m+","+val.errorsAddress+")'>"+val.comments +"</textarea>";
	    	    else
	    	    var str="<textarea name='errComments' id='errs:"+m+"' disabled>"+val.comments +"</textarea>";	
	    	    
	    	    c2.style.width ="200px";
	    	    c2.innerHTML=str;
	    	    var strrr="<div id='rem:"+m+"'></div>";
	            c3.style.width="50px";
	            c3.innerHTML=strrr;
	    	}
 	  	   m=m+1;
		});  
}

function openFluecyResultWindow(assignmentQuestionId,studentAssignmentId,benchmarkId,readingTypesId,gradeTypesId){
	  /*var lessonId = document.getElementById("lessonId").value;*/
	  var csId = document.getElementById("csId").value;
	  var studentId = document.getElementById("studentId").value;
	  var marks = 0;
	  marks = document.getElementById("marks:"+assignmentQuestionId+":"+readingTypesId).value;
	  errorsAddress.length= 0;
	   view = true;
	   if(marks!="") {
	    $("#loading-div-background").show();
	    $.ajax({
	      		type : "GET",
	       		url : "getFluencyTest.htm",
	       		data : "assignmentQuestionId="+assignmentQuestionId+"&studentAssignmentId="+studentAssignmentId+"&benchmarkId="+benchmarkId+"&readingTypesId="+readingTypesId+"&marks="+marks+"&regrade=view"+"&butt=no&csId="+csId+"&studentId="+studentId+"&gradeTypesId="+gradeTypesId,
	       		success : function(response) {
	        		 $("#loading-div-background").hide();
		       		getBenchmarkTest(readingTypesId,response);
	 		        $('#correctwords').prop('disabled', true);
			        $('#errors').prop('disabled', true);
			        $('#wordsread').prop('disabled', true);
	 	   		}
	 	});
	    }else{
	       	alert("Test Not Graded Yet");
	    }
			  
}

function autoSaveComments(){
	 var assignmentQuestionId=$("#questions").val();
     var readingTypesId=$("#readingTypesId").val();
 	 var gradeTypesId=$("#gradeTypesId").val();
 	 var comment=window.document.getElementById("fluencyComment").value; 
 	   if (!comment.replace(/\s/g, '').length) {
 			alert("Please enter a comment");
 		    return false;
 		}
 	  var pattern = new RegExp(/[%&]/);
    	if (pattern.test(comment)) {
 			 alert("Special Characters(%&) Not Allowed in Comments");
 			 $("#fluencyComment").focusin();
 			 return false;
 	   }
 	   var le=comment.length;
 	   if(le>500){
 	  	 alert("You have exceeded the more than 500 characters ");
 	  	 $("#fluencyComment").focus();
 	       return false;
 	   }
 	 $.ajax({
  		type : "GET",
  		url : "autoSaveComments.htm",
  		data : "assignmentQuestionId=" +assignmentQuestionId +"&readingTypesId=" + readingTypesId+"&gradeTypesId="+gradeTypesId+"&comment="+comment,
  		success : function(response) {
  			
  		}
  	});	
}

/*function checkCommentLength(e){
	var comment=document.getElementById("fluencyComment").value;
	var keyCode = e.keyCode == 0 ? e.charCode : e.keyCode;
    var ret = ((keyCode >= 48 && keyCode <= 57) || (keyCode >= 65 && keyCode <= 90) || (keyCode >= 97 && keyCode <= 122) || (keyCode!=37 && keyCode!=38));
    var tlength = comment.length+1;
	var set = 10;
	var remain = parseInt(set - tlength);
	if(keyCode==37 || keyCode==38)
		remain=remain+1;
	if (remain>=0) {
	document.getElementById("rem:"+temp).innerHTML=remain;
	}
	if (remain <0) {
		document.getElementById("errs:"+temp).value=comment.substring(0, tlength-1);
		document.getElementById("rem:"+temp).innerHTML=0;
		ret=false;
	}
    return ret;
    
}*/
		  
function retestFluency(studentAssignmentId,readingTypesId,gradeTypesId,assignmentQuestionsId){
	alert('inside retestFluency');
	 $.ajax({
	  		type : "GET",
	  		url : "retestFluencyAndAccuracy.htm",
	  		data : "&studentAssignmentId="+studentAssignmentId+"&readingTypesId="+readingTypesId+"&gradeTypesId="+gradeTypesId+"&assignmentQuestionsId="+assignmentQuestionsId,
	  		success : function(response) {
	  			if(response.stat=true)
	  			alert("Test ReAssigned Successfully");
	  			else
	  			alert("Test ReAssigned Not Successfully");
	  			$('#gradeBenchmark').dialog('close');
	  			getStudentAsessments();
	  			
	  			
	  		}
	  	});	
}
	
function strikeFText(fieldObj, i, errorType,testType) {
	if(enable == true){
		chkErrorExists(fieldObj,i,'error');
		var j=0;
		var val=document.getElementById("word"+i).value;
		var style = (fieldObj.style.textDecoration!='line-through')?'line-through':'none';
		if(style == 'none'){
		   errorsAddress.splice(errorsAddress.indexOf(i), 1);
		   errorsArray.splice(errorsArray.indexOf(val), 1);
	       document.getElementById("errors").value = --document.getElementById("errors").value ;// --clicks;
	       fieldObj.style.color='Black';
	       fieldObj.style.textDecoration="none";
	       //var correctwords = parseInt(document.getElementById("correctwords").value);
	       //document.getElementById("correctwords").value = correctwords+1;
	       autoDisplayErrorCount(testType);
	       delete accETable[i];
	   }
	   else{
		   fieldObj.style.textDecoration = style;
	       errorsAddress.push(i);
	       var lastChar = val.charAt(val.length-1);
	       if(lastChar == ":")
	       	val = val.slice(0, -1);
	       if (val.endsWith(",") || val.endsWith("?") || val.endsWith(".")){
				val = val.substring(0, val.length - 1);
			}
	        val=  val.toString().replace(/[`~!@#$%^&*()_|+\-=?;:",.<>\{\}\[\]\\\/]/gi, '');
	      /*  val = val.toString().replace("\â€�", ""); 
	        val = val.toString().replace("\â€œ", "");
	        val = val.toString().replace("\,", "");*/
	        //val = val.toString().replace("\'", ""); 
	        var flen=val.length;
	    	if (val.charAt(flen-1)==="\'" || val.charAt(0)==="\'"){
	    		val = val.toString().replace("\'", ""); 
	    	}
	    	
	    		
	       	if(errorType == 'unKnownWord'){
				val = val+'$';
			}else if(errorType == 'skippedWord'){
				val = val+'@';
			}
	       errorsArray.push(val);
	       document.getElementById("errors").value = ++document.getElementById("errors").value; //++ clicks
	       if(errorType == 'errorWord')
	    	  fieldObj.style.color='red';
	       else if(errorType == 'unKnownWord')
	    	  fieldObj.style.color='green';
	       else if(errorType == 'skippedWord')
	    	   fieldObj.style.color='blue';
	       else if(errorType == 'addSelfCorrect')
	    	   fieldObj.style.color='yellow';
	      //var correctwords = parseInt(document.getElementById("correctwords").value);
	      /*if(correctwords > 0)
	         document.getElementById("correctwords").value = correctwords-1;*/
	   }
	   autoDisplayErrorCount(testType);
	   var errorsCnt = document.getElementById("errors").value;
	   if(errorsCnt > 0){
	   	if(!view)
	   		$("#homeWorkDiv").show();
	   }else{
	   	$("#homeWorkDiv").hide();
	   }
	
	   return true;
	}
	else {
		alert("Please remove score to continue..");
	}
}
function saveWCPM(assignmentQuestionId,readingTypeId,gradeTypesId){
	
	if(confirm("Do you want to submit?",function(status){
		if(status){
	var addedWordStr="";
	var assignmentQuestionId=$("#questions").val();
	var wordsRead=$("#wordsread").val();
	var wcpm=$("#wcpm").val();
	if(wordsRead == 0){
		alert("Please Select Read Content");
    	return false;
    } 
	if(wcpm == 0){
		alert("Please Play Audio to get WCPM score");
    	return false;
    } 
    $.ajax({
		type : "GET",
		url : "saveWCPM.htm",
		data : "assignmentQuestionId="+assignmentQuestionId+"&wcpm="+wcpm+"&gradeTypesId="+gradeTypesId+"&readingTypesId="+readingTypeId,
		success : function(data) {	 
			 window.parent.$('.ui-dialog-content:visible').dialog('close');
			$("#gradeBenchmark").empty();  
		}
    });
	}else{
		return false;
	}}));
}

