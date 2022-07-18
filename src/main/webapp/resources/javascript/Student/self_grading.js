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

function chkErrExists(obj, wordNum,errType){
	
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
	       if(regradeStat=="no" && errType!="skipped" && errType!="unKnownWord"){
	        removeErrorWord(val,wordNum);
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
     	if(regradeStat=="no"){
     	var wordType=2;
     	obj.style.color='Black';
      	removeFluencyAddedWord(wordNum,self,wordType);
     	} 
	 }
	 if(pros){
		 $('#prosody'+wordNum).remove();
      	$('#errorIcon2'+wordNum).remove();
      	document.getElementById("errors").value = --document.getElementById("errors").value ;
      	prosodyArray = $.grep(prosodyArray, function(value) {
  		    return value.indexOf(wordNum + "$") < 0;
  		});
      	if(regradeStat=="no"){
      	var wordType=3;
      	removeFluencyAddedWord(wordNum,pros,wordType);
      	} 
	 }
	 if(comm){
		 $('#comment'+wordNum).remove();
	     	$('#errorIcon'+wordNum).remove();
	     	document.getElementById("errors").value = --document.getElementById("errors").value ;
	     	addedWordsArray = $.grep(addedWordsArray, function(value) {
	 		    return value.indexOf(wordNum + "$") < 0;
	 		});
	     	 if(regradeStat=="no")
	     	 removeFluencyAddedWord(wordNum,comm,1);
	     	 
  	}

}