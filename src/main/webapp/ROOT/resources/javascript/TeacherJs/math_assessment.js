function getQuizQuestions(){
	var csId = $('#csId').val();
	if(csId != 'select'){
		var mathQuizByCsIdCallBack = function(list){
			if(list.length > 0){
				var content = "<table width='25%' class='des'><tr><td><table width='100%' class='Divheads'><tr><td align='center'> Quiz Questions </td></tr></table></td></tr><tr><td><table width='100%' class='DivContents'>";
				for(i=0;i < list.length; i++){
					content += "<tr><td align='center' height='30' width='30%' style='' class='txtStyle'><input type='hidden' id='quizId"+i+"' name='quizId"+i+"' value="+list[i].mathQuizId+" /> "+(i+1)+".&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href='#' onclick='openQuizQuestionDialog("+i+")' style='color:black;'>"+list[i].fraction+"</a></td>" +
							  "<td width='10%'><i class='fa fa-times' aria-hidden='true' style='cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;' onclick='removeQuizQuestion("+i+")' name='remove"+i+"' id='remove"+i+"'></i></td></tr>" ;
				}
				content += "</table></td></tr></table>";
				$('#quizQuestionsDiv').html(content);
			}else{
				$('#quizQuestionsDiv').html("");
			}
		}
		mathAssessmentService.getMathQuizByCsId(csId,{
			callback : mathQuizByCsIdCallBack
		});
		$('#createQuestionBtn').show();
	}else{
		$('#createQuestionBtn').hide();
	}
}

function removeQuizQuestion(id){
	var quizId = $('#quizId'+id).val(); 
	if(quizId){
		$.ajax({
			url : "removeQuizQuestion.htm",
			data: "quizId="+quizId,
			success : function(data){
				 systemMessage(data);
				 getQuizQuestions();
			}
		});
	}
}

function openQuizQuestionDialog(id){
	var quizId = 0;
	var mode = "create";
	 if(id > -1){
		quizId = $('#quizId'+id).val(); 
		 mode = "edit";
	 }
	 var screenWidth = $( window ).width() - 600;
	 var screenHeight = $( window ).height() -260;
	 jQuery.curCSS = jQuery.css;
	 var iframe = $('#iframe');
	 iframe.attr('src', "openQuizQuestion.htm?quizId="+quizId+"&mode="+mode);
	 $("#createQuizQuestionsDialog").dialog({
		overflow: 'auto',
		dialogClass: 'no-close',
	    autoOpen: false,
	    position: {my: "center", at: "center", of:window ,within: $("body") },
	    title: 'Create/Edit Quiz Question',
	    draggable: true,
	    width : screenWidth,
	    height : screenHeight,
	    resizable : true,
	    modal : true,
	    close : function(){
	    	getQuizQuestions();
        }
	});
	 $("#createQuizQuestionsDialog").dialog( "open" );

}

function makeQuestionContent(){
	 var mode = $('#mode').val(); 
	 if(mode == 'edit'){
		 var quizId = $('#quizId').val(); 
		 var actualAnswerArray = new Array();
		 var isBlankArray = new Array();
		 var mathQuizQuestionsByQuizIdCallBack =  function(list){
			 if(list){
				 for(i=0;i < list.length; i++){
				    var actualAnswer =  list[i].actualAnswer;
					actualAnswerArray.push(actualAnswer);
					isBlankArray.push(list[i].isBlank);
				 }
			    $('#fractionId').val(actualAnswerArray[0]);
			    var contentStr = "<span style='margin-right:-25em;color:#6c7608;margin-top:1em;'>* Click on a button to make it blank</span><table with='100%' style='border-spacing: 30px 10px;padding:12px;'><tr with='100%'>";
				    contentStr += "<td with='10%' align='center' class='title-txt'>Simplified Fraction</td><td with='30%' align='center' class='title-txt'>Word</td><td with='10%' align='center' class='title-txt'>Decimal</td><td with='10%' align='center' class='title-txt'>Percentage</td><td with='10%' align='center' class='title-txt'>Rounded Percentage</td>" +
						          "<td with='10%' align='center' class='title-txt'>Eq.Fraction1</td><td with='10%' align='center' class='title-txt'>Eq.Fraction2</td></tr>";
				    contentStr += "<tr with='100%'><td with='10%' align='center'><span></span><br><div name='simplifiedFraction' id='simplifiedFraction' class="+ (isBlankArray[0] == 'true' ? 'blank-button-cls' : 'button-cls')+" onclick='makeItBlank(this)'>"+actualAnswerArray[0]+"</div></td>" +
						      "<td with='10%' align='center'><i class='fa fa-pencil' style='cursor: pointer;' onClick=openEditBox('word')></i><br><div name='word' id='word' class="+ (isBlankArray[1] == 'true' ? 'blank-button-cls' : 'button-cls')+" onclick='makeItBlank(this)'>"+actualAnswerArray[1]+"</div></td>" +
						      "<td with='10%' align='center'><i class='fa fa-pencil' style='cursor: pointer;' onClick=openEditBox('decimal')></i><br><div name='decimal' id='decimal' class="+ (isBlankArray[2] == 'true' ? 'blank-button-cls' : 'button-cls')+" onclick='makeItBlank(this)'>"+actualAnswerArray[2]+"</div></td>" +
						      "<td with='10%' align='center'><i class='fa fa-pencil' style='cursor: pointer;' onClick=openEditBox('percentage')></i><br><div name='percentage' id='percentage' class="+ (isBlankArray[3] == 'true' ? 'blank-button-cls' : 'button-cls')+" onclick='makeItBlank(this)'>"+actualAnswerArray[3]+"</div></td>" +
						      "<td with='10%' align='center'><i class='fa fa-pencil' style='cursor: pointer;' onClick=openEditBox('roundedPercentage')></i><br><div name='roundedPercentage' id='roundedPercentage' class="+ (isBlankArray[4] == 'true' ? 'blank-button-cls' : 'button-cls')+" onclick='makeItBlank(this)'>"+actualAnswerArray[4]+"</div></td>" +
						      "<td with='10%' align='center'><i class='fa fa-pencil' style='cursor: pointer;' onClick=openEditBox('equivalentFraction1')></i><br><div name='equivalentFraction1' id='equivalentFraction1' class="+ (isBlankArray[5] == 'true' ? 'blank-button-cls' : 'button-cls')+" onclick='makeItBlank(this)'>"+ (actualAnswerArray[5]? actualAnswerArray[5]:'')+"</div></td>" +
						      "<td with='10%' align='center'><i class='fa fa-pencil' style='cursor: pointer;' onClick=openEditBox('equivalentFraction2')></i><br><div name='equivalentFraction2' id='equivalentFraction2' class="+ (isBlankArray[6] == 'true' ? 'blank-button-cls' : 'button-cls')+" onclick='makeItBlank(this)'>"+ (actualAnswerArray[6]? actualAnswerArray[6]:'')+"</div></td></tr></table>";
				$("#QuestionContentDiv").html(contentStr);
			 }
		 }
		 mathAssessmentService.getMathQuizQuestionsByQuizId(quizId,{
				callback : mathQuizQuestionsByQuizIdCallBack
		 });
		 
	 }else if(mode == 'create'){
		var fraction = $('#fractionId').val();
		if(fraction){
			var decimal = getDecimal(fraction);
			var perRounded = decimal.toFixed(2);
			perRounded = perRounded.split('.')[1]; 
			if(!perRounded)
				perRounded = "";	
			/* Commented by Rajendra to avoid rounded percentage value */
			/*var percentage = decimal.toString().split('.')[1];
			
			percentage = percentage.slice(0,3);
			percentage = percentage*10;
			percentage = String(percentage);
			  if(percentage.length > 2){
				  percentage = percentage.toString().slice(0, -2) +"."+percentage.toString().slice(-2);				
			  }*/
			percentage = (eval(fraction) * 100).toString().slice(0,5);  
			if(!percentage)
				percentage = "";	
			decimal = decimal.toString().slice(0,4);
			if(!decimal)
				decimal = "";
			
			var inWords = convertToWords(fraction);
			if(!inWords)
				inWords = "";
			var contentStr = "<span style='margin-right:-25em;color:blue;margin-top:1em;'>* Click on a button to make it blank</span><table with='100%' style='border-spacing: 30px 10px;padding:12px;'><tr with='100%'>";
			contentStr += "<td with='10%' align='center' class='title-txt'>Simplified Fraction</td><td with='30%' align='center' class='title-txt'>Word</td><td with='10%' align='center' class='title-txt'>Decimal</td><td with='10%' align='center' class='title-txt'>Percentage</td><td with='10%' align='center' class='title-txt'>Rounded Percentage</td>"+
			 			  "<td with='10%' align='center' class='title-txt'>Eq.Fraction1</td><td with='10%' align='center' class='title-txt'>Eq.Fraction2</td></tr>";
			contentStr += "<tr with='100%'><td with='10%' align='center'><span></span><br><div name='simplifiedFraction' id='simplifiedFraction' class='button-cls' onclick='makeItBlank(this)'>"+fraction+"</div></td>" +
					      "<td with='10%' align='center'><i class='fa fa-pencil' style='cursor: pointer;' onClick=openEditBox('word')></i><br><div name='word' id='word' class='button-cls' onclick='makeItBlank(this)'>"+inWords+"</div></td>" +
					      "<td with='10%' align='center'><i class='fa fa-pencil' style='cursor: pointer;' onClick=openEditBox('decimal')></i><br><div name='decimal' id='decimal' class='button-cls' onclick='makeItBlank(this)'>"+decimal+"</div></td>" +
					      "<td with='10%' align='center'><i class='fa fa-pencil' style='cursor: pointer;' onClick=openEditBox('percentage')></i><br><div name='percentage' id='percentage' class='button-cls' onclick='makeItBlank(this)'>"+percentage+"</div></td>" +
					      "<td with='10%' align='center'><i class='fa fa-pencil' style='cursor: pointer;' onClick=openEditBox('roundedPercentage')></i><br><div name='roundedPercentage' id='roundedPercentage' class='button-cls' onclick='makeItBlank(this)'>"+perRounded+"</div></td>"+
					      "<td with='10%' align='center'><i class='fa fa-pencil' style='cursor: pointer;' onClick=openEditBox('equivalentFraction1')></i><br><div name='equivalentFraction1' id='equivalentFraction1' class='button-cls' onclick='makeItBlank(this)'></div></td>" +
					      "<td with='10%' align='center'><i class='fa fa-pencil' style='cursor: pointer;' onClick=openEditBox('equivalentFraction2')></i><br><div name='equivalentFraction2' id='equivalentFraction2' class='button-cls' onclick='makeItBlank(this)'></div></td></tr></table>";

			$("#QuestionContentDiv").html(contentStr);
			$("#submitDiv").show();
		 }
		 
	 }
}

function getDecimal(fraction){
	bits = fraction.split("/");
	return parseInt(bits[0],10)/parseInt(bits[1],10);
}

var blankArray = new Array();
function makeItBlank(obj){
	var mode = $('#mode').val(); 
	if(mode == 'edit'){
		var status = true;
		status = validateFraction(obj);
		if(status == true){
			var className = $("#"+obj.id).attr('class');  
			if(className == 'button-cls'){
				$("#"+obj.id).removeClass('button-cls').addClass('blank-button-cls');
			}else if (className == 'blank-button-cls'){
				$("#"+obj.id).removeClass('blank-button-cls').addClass('button-cls');
			}
		  saveQuizQuestion(function(data){
			if(data == 'Test already Assigned !!'){
				var className = $("#"+obj.id).attr('class');  
				if(className == 'button-cls'){
					if(obj.id == 'fraction'){
					if(!$('#equivalentFraction1').text() &&  !$('#equivalentFraction2').text())
						alert('Please enter equivalent fraction');
					}else{
						$("#"+obj.id).removeClass('button-cls').addClass('blank-button-cls');
					}
				}else if (className == 'blank-button-cls'){
					$("#"+obj.id).removeClass('blank-button-cls').addClass('button-cls');
				}
			}
		  });
		}
	}else{
		if(obj.id == 'fraction'){
			if(!$('#equivalentFraction1').text() &&  !$('#equivalentFraction2').text()){
				alert('Please enter equivalent fraction');
				return false;
			}
		}
		var className = $("#"+obj.id).attr('class');  
		if(className == 'button-cls'){
			$("#"+obj.id).removeClass('button-cls').addClass('blank-button-cls');
		}else if (className == 'blank-button-cls'){
			$("#"+obj.id).removeClass('blank-button-cls').addClass('button-cls');
		}
	}
}

function validateFraction(obj){
	var className = $("#"+obj.id).attr('class');  
	if(obj.id == 'fraction'){
		if(className == 'button-cls'){
			if(!$('#equivalentFraction1').text() &&  !$('#equivalentFraction2').text()){
				alert('Please enter equivalent fraction');
				return false;
			}else{
				if($('#equivalentFraction1').attr('class') == 'blank-button-cls' && $('#equivalentFraction2').attr('class') == 'blank-button-cls'){
					alert('Should not made Fraction, Equivalent Fractions are blanks');
					return false;
				}else{
					if(!$('#equivalentFraction1').text() || !$('#equivalentFraction2').text()){
						alert('Please enter equivalent fraction');
						return false;
					}
				}
			}
		}
	}else if(obj.id == 'equivalentFraction1'){
		if(className == 'button-cls'){
			if($('#fraction').attr('class') == 'blank-button-cls' && $('#equivalentFraction2').attr('class') == 'blank-button-cls'){
					alert('Should not made Fraction, Equivalent Fractions are blanks');
					return false;
			}
		}
	}else if(obj.id == 'equivalentFraction2'){
		if(className == 'button-cls'){
			if($('#fraction').attr('class') == 'blank-button-cls' && $('#equivalentFraction1').attr('class') == 'blank-button-cls'){
				    alert('Should not made Fraction, Equivalent Fractions are blanks');
					return false;
			}
		}
	}
	return true;
}

function clearFraction(){
	$('#fraction').val("");
	$("#QuestionContentDiv").html("");
	$("#submitDiv").hide();
}

function convertToWords(fraction){
	var word='';
	var bits = fraction.split("/");
	var top = parseInt(bits[0]);
	var bottom = parseInt(bits[1]);
	 var topWords=["zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"];
	 var bottomWords=['dummy', ' half', ' third', ' fourth', ' fifth', ' sixth', ' seventh', ' eighth', ' ninth', ' tenth' ];
	if(top == bottom){
		
	}else if(top > bottom){
	 
	}else if(top < bottom){
		  if(top < 10){
			  word = topWords[top]; 
		  }else{
			  word='';
			  return word;
		  }
		  if(bottom){
			  if(bottom > 10){
				  word='';
				  return word;
			  }else{
				  if(top == 1){
					  word += bottomWords[bottom-1]; 
				  }else{
					  word += bottomWords[bottom-1]+'s'; 
				  }
			  }
		  }
	}
	return word;
}

var answersArray = new Array();
function saveQuizQuestion(callBack){
	answersArray.length = 0;
	var fraction = $('#simplifiedFraction').text();
	answersArray.push("SimplifiedFraction:"+fraction);
	
	var word = $('#word').text();
	if(word)
		answersArray.push("Word:"+word);
	
	var decimal = $('#decimal').text();
	if(decimal)
		answersArray.push("Decimal:"+decimal);
	
	var percentage = $('#percentage').text();
	if(percentage)
		answersArray.push("Percentage:"+percentage);
	
	var rounded = $('#roundedPercentage').text();
	if(rounded)
		answersArray.push("RoundedPercentage:"+rounded);
	
	var equivalentFraction1 = $('#equivalentFraction1').text();
	if(equivalentFraction1){
		answersArray.push("EquivalentFraction1:"+equivalentFraction1);
	}else{
		alert("Equivalent Fraction1 mandatory field");
		openEditBox('equivalentFraction1');
		return false;
	}
	
	var equivalentFraction2 = $('#equivalentFraction2').text();
	if(equivalentFraction2){
		answersArray.push("EquivalentFraction2:"+equivalentFraction2);
	}else{
		alert("Equivalent Fraction2 mandatory field");
		openEditBox('equivalentFraction2');
		return false;
	}
	
	blankArray = $('.blank-button-cls').map(function() {
	    return this.id.replace(/^(.)|\s(.)/g, function($1) {
	        return $1.toUpperCase(); });
	}).get();
	
	if(blankArray.length == 0){
		alert("Make sure atleast one blank");
		return false;
	}
	 var csId = window.parent.$("#csId").val();
	 var mode = $('#mode').val(); 
	 var quizId = $('#quizId').val(); 
	if(fraction){
			$.ajax({
				url : "saveQuizQuestion.htm",
				data: "fraction="+fraction+"&csId="+csId+"&mode="+mode+"&quizId="+quizId+"&answersArray="+answersArray+"&blankArray="+blankArray,
				success : function(data){
					if(callBack)
						 callBack(data);
					 systemMessage(data);
				}
			});
	 }
}

function openEditBox(id){
	var val = $('#'+id).text();
	 if(!document.getElementById("edit"+id)){
		 $('#'+id).after("<textarea id='edit"+id+"' name='textarea"+id+"' style='display:none;font-size: 13px;' onblur=closeEditBox('"+id+"') class='box' onkeypress=keyPress('"+id+"')>"+val+"</textarea>");
	  }	
	showEditBox(id)
}
function showEditBox(id){
	var posX = $('#'+id).position().left, posY = $('#'+id).position().top;
	    posX = (posX - 10)+"px";
		posY = (posY - 65)+"px";
		$("#edit"+id).css({left:posX,top:posY});
		$("#edit"+id).fadeIn(500);
		$("#edit"+id).focus();
}
function closeEditBox(id){
	var val = document.getElementById("edit"+id).value;
	if(val){
		$('#'+id).html('');
		$('#'+id).html(val);
		 var mode = $('#mode').val(); 
    	 if(mode == 'edit')
    	   saveQuizQuestion();
	}
	$("#edit"+id).hide();
}
function keyPress(id){
	event = window.event;
    if(event.keyCode == 13){
    	var val = document.getElementById("edit"+id).value;
    	if(val){
	    	$('#'+id).html('');
			$('#'+id).html(val);
	    	$("#edit"+id).hide();
	    	 var mode = $('#mode').val(); 
	    	 if(mode == 'edit')
	    	   saveQuizQuestion();
    	}
     };
    return true;
}

function getQuizQuestionsContent(id){
	if(id == 'select_all'){
		var select_all = $('#select_all').val();
		if($("#select_all").is(":checked")){
			$('select#studentId > option').prop("selected", true);
		}else{
			$("select#studentId > option").prop('selected', false);
		}
		if($( "#contentDiv").text()){
			
		}else{
			loadContent();
		}
	}else if(id == 'studentId'){
		var studentId = $('#studentId').val();
		if($( "#contentDiv").text()){
			if( studentId.length == 1){
					$("#select_all").removeAttr("checked");
				return false;
			}
		}else{
			loadContent();
		}
	}
}

function loadContent(){
	var csId = $('#csId').val();
	if(csId != 'select'){
		var allMathQuizsCallBack = function(list){
			if(list.length > 0){
				var content = "<table width='100%'><tr><td align='center'><table width='50%' class='des' align='center'><tr><td align='center'><table width='100%' class='Divheads'><tr><td align='center'> Quiz Questions </td></tr></table></td></tr><tr><td align='center' width='100%'><table width='100%' class='DivContents' align='center'>";
				for(i=0;i < list.length; i++){
					content += "<tr><td align='center' height='30' width='40%' style='font-size:14px;' class='txtStyle'><input type='hidden' id='quizId"+i+"' name='quizId"+i+"' value="+list[i].mathQuizId+" /> "+(i+1)+".&nbsp;&nbsp;&nbsp;<input type='checkbox' class='checkbox-design' id='checkbox"+i+"' name='checkbox' value="+list[i].mathQuizId+" /><label for='checkbox"+i+"' class='checkbox-label-design'></label>&nbsp;&nbsp;&nbsp;<a href='#' onclick='openQuizQuestionDialog("+i+")' style="+ (list[i].csId == 0 ? 'color:blue;' : 'color:black;')+">"+list[i].fraction+"</a></td></tr>";
				}
				content += "</table></td></tr>";
				content += "<tr><td align='center'><table width='100%' style='font-size: 13px;padding-left:2em;'>" +
						   "<tr><th align='left'width='30%'>Due Date : &nbsp;&nbsp; <input type='text' id='dueDate' name='dueDate' style='width:80px;' readonly='readonly'></th>"+
        		           		"<th align='left' width='35%'>Title : &nbsp;&nbsp; <input name='titleId' type='text' id='titleId' size='20' maxlength='15'  value='' onblur='checkTitleById();'/></th>" +
        		           	    "<th align='left'width='40%'>Quiz Time ?&nbsp;&nbsp;<input type='checkbox' class='checkbox-design' id='isTimer' name='isTimer' value='' onChange='isTimerChecked()'/><label for='isTimer' class='checkbox-label-design'></label><input type='text' id='quizeTime' name='quizeTime' style='width:40px;display:none;' maxlength='2'></th>"+
        		           	"</tr></table></td></tr>"+
				           "<tr><td width='10' height='20'></td></tr><tr><td align='center'><div id='assignQuizTest' name='assignQuizTest' class='button_green' onclick='assignQuizTest()' style='font-size:12px;width:100px;'>Assign Quiz Test</div></td></tr><tr><td width='100%' height='20' align='center'></td></tr></table></td></tr><tr><td width='100%' height='20' align='center'><div id='status' class='status-message' align='center'></div></td></tr>" +
				           "<tr><td><div id='createQuizQuestionsDialog'  style='display:none;background : #f7f5f5 url(images/Common/bg.jpg) repeat fixed top center;' title=''><iframe  id ='iframe' src='' frameborder='0' scrolling='no' width='100%' height='95%' src=''></iframe></div></td></tr></table>";
				$('#contentDiv').html(content);
				pickDate();
			}else{
				$('#contentDiv').html("");
			}
		}
		mathAssessmentService.getAllMathQuizs(csId,{
			callback : allMathQuizsCallBack
		});
	}
}

function isTimerChecked(){
	var isChecked = $('#isTimer').is(":checked");
	if(isChecked)
		$('#quizeTime').show();
	else
		$('#quizeTime').hide();
}

function numValCheck(val){
	 if(val){
		 var condition=/^[0-9]+$/.test(val);
		    if(condition==false){
		    	systemMessage("Please enter valid Quiz Time");
		        $("#quizeTime").focus();
		        return false;
		    }else if(val == 0){
		    	systemMessage("Please enter valid Quiz Time");
		    	$("#quizeTime").focus();
		    	return false;
		    }else{
		    	 return  true;
		    }
	 }else{
		 alert("Please enter quiz time !!");
		 return false;
	 }
}

function assignQuizTest(){
	var quizIdArray = [];
	$. each($("input[name='checkbox']:checked"), function(){
			quizIdArray.push($(this)[0].value);
		});
	if(quizIdArray.length == 0){
		   alert("Please Select Quiz Questions !!");
		   return false;
	   	}
	var isChecked = $('#isTimer').is(":checked");
	if(isChecked){
		var quizeTime = $('#quizeTime').val();
	   var status = numValCheck(quizeTime);
	   if(status == false){
		  $("#quizeTime").focus();
		  return false;
	   }
	}
	
	var students = new Array();
   	$('#studentId :selected').each(function(i, selectedElement) {
   		if($(selectedElement).val() && $(selectedElement).val() != 'select')
   			students.push($(selectedElement).val());
   	 });
   	if(students.length == 0){
	   alert("Please Select Students !!");
	   return false;
   	}else if(students.length == 1){
   		if(confirm("Are you sure for single student?",function(status){
   			if(!status){
	   			return false;
	   		}else{
	   			assignTest(quizIdArray,students);
	   		}
   		})); 
   	}else{
   		assignTest(quizIdArray,students);
   	}
   
}

function assignTest(quizIdArray,students){
	    var dueDate = $('#dueDate').val(); 
		var titleId = $('#titleId').val(); 
		var csId = $('#csId').val(); 
		var pattern = new RegExp(/[~`!#$%\^&*+=\-\[\]\\';,/{}|\\":<>\?]/); 
		if (pattern.test(titleId)) {
			systemMessage("Special Characters Not Allowed In Title");
		    $('#titleId').val("");
		    $('#titleId').focus();
		    return false;
		}
		var quizeTime = 0; 
		if($('#quizeTime').val())
			quizeTime = parseInt($('#quizeTime').val());
		if(titleId && dueDate){
			$.ajax({
				url : "assignQuizTest.htm",
				data: "dueDate="+dueDate+"&titleId="+titleId+"&csId="+csId+"&quizeTime="+quizeTime+"&quizIdArray="+quizIdArray+"&students="+students,
				success : function(data){
			      $('#dueDate').val(''); 
		    	  $('#titleId').val(''); 
		    	  $('#quizeTime').val('');
		    	  $("input[name='checkbox']:checkbox").prop('checked',false);
		    	  systemMessage(data);
				}
			});
		}else{
			alert("Please fill all mandatory fields");
		}
}

function getMathAssignedDates(callback){
	var csId = $('#csId').val();
    if(csId !=  'select'){
		var usedFor = $('#usedFor').val();
		var page = dwr.util.getValue('page');
		$.ajax({
			type : "GET",
			url : "getMathAssignedDates.htm",
			data : "csId=" + csId + "&usedFor=" + usedFor+ "&page=" + page,
			success : function(response2) {
				var teacherAssignedDates = response2.teacherAssignedDates;
				$("#assignedDate").empty();
				$("#assignedDate").append(
						$("<option></option>").val('select').html('select date'));
				$.each(teacherAssignedDates, function(index, value) {
					$("#assignedDate").append(
							$("<option></option>").val(getDBFormattedDate(value.dateAssigned)).html(
									getFormattedDate(value.dateAssigned)));
				});
				if(callback)
				  callback();
			}
		});

	}else{
		$("#assignedDate").empty();
		$("#assignedDate").append(
				$("<option></option>").val('select').html('select'));
		$("#titleId").empty();
		$("#titleId").append(
				$("<option></option>").val('select').html('select'));
		$('#studentDetailsPage').html('');
	}
}

function getStudentDetailsForGrade(){
	var assignmentId = window.parent.document.getElementById( 'titleId' ).value;
	if(!assignmentId)
		assignmentId = dwr.util.getValue('titleId');
	var assignmentTypeId = window.parent.document.getElementById('assignmentTypeId').value;
	if(!assignmentTypeId)
		assignmentTypeId = dwr.util.getValue('assignmentTypeId');
	var studentDetailsForGradeCallBack = function (list){
	var str = "<table class='des' border=0><tr><td><div class='Divheads'><table align='center' cellpadding='5' cellspacing='5'> <tr> " +
		"<th width='106' align='center'>Select </th>" +
		"<th width='106' align='center'>Student Id</th>" +
		"<th width='150' align='center'>Student Name</th>" +
		"<th width='120' align='center'>Test Status</th>" +
		"<th width='150' align='center'>Graded Status</th>" +
		"<th width='106' align='center'>Percentage</th>" +
		"<th width='106' align='center'>Academic Grade</th>" +
		"</table></div><div class='DivContents'><table><tr><td>&nbsp;</td></tr>";

	for (i=0;i<list.length;i++){
		var percentage = (list[i].percentage == null) ? "" : list[i].percentage;
		var acedamicGradeName='';
		if(list[i].academicGrade)
			  acedamicGradeName = (list[i].academicGrade.acedamicGradeName == null) ? "" : list[i].academicGrade.acedamicGradeName;
			str +=  "<tr><td width='106' align='center' height='25'> <input type='radio' class='radio-design' id='radio"+i+"' name='radio' value='"+list[i].studentAssignmentId+"' onClick=\"getStudentsTestDetails(this,'"+i+"','"+list[i].student.userRegistration.user.userType+"','"+list[i].student.userRegistration.regId+"','"+list[i].gradedStatus+"','"+list[i].status+"','"+list[i].student.studentId+"','"+list[i].assignment.assignmentId+"')\" /><label for='radio"+i+"' class='radio-label-design'></label></td>" +
					"<td  width='130' align='center' class='txtStyle'>"+list[i].student.studentId+"</td> " +
					"<td width='150' align='center' class='txtStyle'> "+list[i].student.userRegistration.firstName+" "+list[i].student.userRegistration.lastName+"</td> " +
					"<td th width='150' align='center' class='txtStyle'>"+list[i].status+"</td>" +
					"<td width='150' align='center' class='txtStyle'>"+list[i].gradedStatus+"</td>"+
					"<td th width='150' align='center' class='txtStyle'>"+percentage+"</td>" +
					"<td th width='150' align='center' class='txtStyle'>"+acedamicGradeName+"</td>" ;
	
			str += "<td><div id='dialog"+i+"'  style=\"background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;display:none;\" title=''> "+
				   "<iframe id='iframe"+i+"' frameborder='0' scrolling='no' width='100%' height='98%' src='' style='min-height:32em;max-height:32em;'></iframe></div></td>";
			str += "</tr> ";
	 }
		str += "<tr><td height=15 colSpan=30></td></tr><tr><td colspan='20' width='160' align='center'><div id='lpSystemRubricDiv' align='right'/></td></tr></table></div></td></tr></table>";
   		$('#studentDetailsPage').html(str);
   		$("#loading-div-background").hide();
	   	 var isInIFrame = (window.location != window.parent.location) ? true : false;
		 if(!isInIFrame)
			 setFooterHeight();
	}
	if(assignmentId != 'select'){
		 $("#loading-div-background").show();
		 mathAssessmentService.getStudentAssessmentTests(assignmentId, assignmentTypeId, {
			callback : studentDetailsForGradeCallBack
		});
	}else{
	   	$('#studentDetailsPage').html('');
		return false;
	}
}

function getStudentsTestDetails(obj,id,userType,regId,gradedStatus,status,studentId,assignmentId){
	var page = $('#page').val();
	 var screenWidth = $( window ).width() - 150;
	 var screenHeight = $( window ).height() - 100;
			jQuery.curCSS = jQuery.css;
				 $("#dialog"+id).dialog({
						overflow: 'auto',
						dialogClass: 'no-close',
					    autoOpen: false,
						position: {my: "center", at: "center", of:window ,within: $("body") },
					    title: 'Math Assessment Test Grading',
					    draggable: true,
					    width : screenWidth,
					    height : screenHeight,
					    resizable : true,
					    modal : true,
					    close : function(){}  
					});
			 var iframe = $('#iframe'+id);
			     iframe.attr('src', "getStudentsTestDetails.htm?studentAssignmentId="+obj.value +"&userType="+userType+"&regId="+regId+"&studentId="+studentId+"&assignmentId="+assignmentId+"&status="+status+"&gradedStatus="+gradedStatus+"&page="+page);
			    if(status == 'pending'){
                  alert("Test not yet submitted !!");
		        }else{
		        	 $("#dialog"+id).dialog("open");
		        }
}

function getStudentMathAssessmentDetails(){
	var fraction='';
	var inWords='';
	var decimal='';
	var percentage='';
	var perRounded='';
	var eqFraction1=''; var eqFraction2='';
	
	var contentStr = "<table with='100%' style='border-spacing: 30px 10px;padding:12px;'><tr with='100%'>";
		contentStr +="<td with='10%' align='center' class='title-txt'>Simplified Fraction</td>" +
					"<td with='30%' align='center' class='title-txt'>Word</td>" +
					"<td with='10%' align='center' class='title-txt'>Decimal</td>" +
					"<td with='10%' align='center' class='title-txt'>Percentage</td>" +
					"<td with='10%' align='center' class='title-txt'>Rounded Percentage</td>" +
					"<td with='10%' align='center' class='title-txt'>Eq. Fraction 1</td>" +
					"<td with='10%' align='center' class='title-txt'>Eq. Fraction 2</td>" +
					"</tr>";
	contentStr += "<tr with='100%'><td with='10%' align='center'><span></span><br><div name='fraction' id='fraction' class='button-cls'>"+fraction+"</div></td>" +
			      "<td with='10%' align='center'><div name='word' id='word' class='button-cls' onclick='makeItBlank(this)'>"+inWords+"</div></td>" +
			      "<td with='10%' align='center'><div name='decimal' id='decimal' class='button-cls' onclick='makeItBlank(this)'>"+decimal+"</div></td>" +
			      "<td with='10%' align='center'><div name='percentage' id='percentage' class='button-cls' onclick='makeItBlank(this)'>"+percentage+"</div></td>" +
			      "<td with='10%' align='center'><div name='rounded' id='rounded' class='button-cls' onclick='makeItBlank(this)'>"+perRounded+"</div></td>" +
			      "<td with='10%' align='center'><div name='eqFraction1' id='eqFraction1' class='button-cls' onclick='makeItBlank(this)'>"+eqFraction1+"</div></td>" +
			      "<td with='10%' align='center'><div name='eqFraction2' id='eqFraction2' class='button-cls' onclick='makeItBlank(this)'>"+eqFraction2+"</div></td>" +
			      "</tr></table>"
	$("#QuestionContentDiv").html(contentStr);
}

function udpateMarkStatus(id,count){
	var className = $("#id"+id).attr('class');  
	var studentMathAssessMarksId = $("#studentMathAssessMarksId"+id).val();
	var studentAssignmentId = $("#studentAssignmentId").val();
	var mark = $("#mark"+id).val();
		if(mark == 0){
			mark = 1;
		}else if(mark == 1){
			mark = 0;
		}
	var correct = parseInt($(".correct-button-cls").length); 
	var wrong = parseInt($(".wrong-button-cls").length);
	if(studentMathAssessMarksId){
		if(className == 'wrong-button-cls'){
			correct = correct+1;
			wrong = wrong-1;
		}
		else{
			correct = correct-1;
			wrong = wrong+1;
		}
		$.ajax({
			url : "udpateMarkStatus.htm",
			data: "studentMathAssessMarksId="+studentMathAssessMarksId+"&mark="+mark+"&correct="+correct+"&wrong="+wrong+"&studentAssignmentId="+studentAssignmentId,
			success : function(data){
			  if(className == 'wrong-button-cls'){
				 $("#id"+id).removeClass('wrong-button-cls').addClass('correct-button-cls');
				 $("#mark"+id).val(1);
			  }else if (className == 'correct-button-cls'){
				 $("#id"+id).removeClass('correct-button-cls').addClass('wrong-button-cls');
				 $("#mark"+id).val(0);
			  }
			  getScore(count);	
			  updatePercentage(data.percentage, data.acedamicGrade);
			  systemMessage(data.status);
			}
		});
 	}
}

function getScore(count){
	var correct = parseInt($("#table"+count +" .correct-button-cls").length); 
	var wrong = parseInt($("#table"+count +" .wrong-button-cls").length); 
	$("#score"+count).text(correct+'/'+(correct+wrong));
	getTotScore();
}

function getTotScore(){
	var correct = parseInt($(".correct-button-cls").length); 
	var wrong = parseInt($(".wrong-button-cls").length); 
	$("#totscore").text(correct+'/'+(correct+wrong));
}
function updatePercentage(percentage, acedamicGrade){
	$("#percentage").text(percentage);
	$("#academicGrade").text(acedamicGrade);
}

function goForMathAssessmentTest(dialogDivId,studentAssignmentId,testCount){
	$("#loading-div-background").show();
	var iframe = $('#iframe'+testCount);
	 iframe.attr('src', "getMathAssessmentQues.htm?studentAssignmentId="+studentAssignmentId+"&testCount="+testCount+"&returnMessage=''");
	 var screenWidth = $( window ).width()/4*3;
	 var screenHeight = $( window ).height() -15;
	 jQuery.curCSS = jQuery.css;
	 $("#"+dialogDivId).dialog({
		overflow : 'auto',
		dialogClass: 'no-close',
	    autoOpen: false,
	    position: {my: "center", at: "center", of:window ,within: $("body") },
	    title: 'Math Assessment Test',
	    draggable: true,
	    width : screenWidth,
	    height : screenHeight,
	    resizable : true,
	    autoResize:true,
	    modal : true,
	    close : function(){ /*window.location = 'goToStudentRTIPage.htm';*/}  
	});
	 $("#"+dialogDivId).dialog( "open" );
	 $("#loading-div-background").hide();
}

function showAnalysisType(){
	var titleId = $('#titleId').val();
	if(titleId != 'select'){
		$("#analysisDiv").show(); 
		$("#byPercentage").prop('checked', true);    
		getStudentDetailsByPercentage();
	}else{
		$("#studentDetailsPage").html(''); 
	}
}

function getStudentDetailsByPercentage(){
	$("#setsDropdownDiv").hide(); 
	var assignmentId = $('#titleId').val();
	var assignmentTypeId = window.parent.document.getElementById('assignmentTypeId').value;
	if(!assignmentTypeId)
		assignmentTypeId = dwr.util.getValue('assignmentTypeId');
	if(mathQuizId != 'select' && assignmentId != 'select'){
		$.ajax({
			url : "getStudentDetailsByPercentage.htm",
			data: "assignmentId="+assignmentId+"&assignmentTypeId="+assignmentTypeId,
			success : function(data){
				$("#studentDetailsPage").html(data); 
			}
		});
	}else{
		$("#studentDetailsPage").html(""); 
	}
}

function getByMathSet(){
	$("#setsDropdownDiv").show(); 
	$("#studentDetailsPage").html(''); 
	var mathQuizByCsIdCallBack = function(list){
		dwr.util.removeAllOptions('mathQuizId');
		dwr.util.addOptions('mathQuizId', ["select"]);
		dwr.util.addOptions('mathQuizId', list, 'mathQuizId', 'fraction');
	}
	var assignmentId = $('#titleId').val();
	mathAssessmentService.getMathQuizsByAssignmentId(assignmentId,{
		callback : mathQuizByCsIdCallBack
	});
}
function getResultByMathQuizId(){
	var mathQuizId = $('#mathQuizId').val();
	if(mathQuizId != "select"){
		var assignmentId = $('#titleId').val();
		var assignmentTypeId = window.parent.document.getElementById('assignmentTypeId').value;
		if(!assignmentTypeId)
			assignmentTypeId = dwr.util.getValue('assignmentTypeId');
		if(mathQuizId != 'select' && assignmentId != 'select'){
			$.ajax({
				url : "getResultByMathQuizId.htm",
				data: "mathQuizId="+mathQuizId+"&assignmentId="+assignmentId+"&assignmentTypeId="+assignmentTypeId,
				success : function(data){
					$("#studentDetailsPage").html(data); 
				}
			});
		}
	}else{
		$("#studentDetailsPage").html(""); 
	}
}

function assignGameToStudents(){
	var dueDate = $('#dueDate').val(); 
	var titleId = $('#titleId').val(); 
	var instructId = $('#instructId').val();
	var csId = $('#csId').val(); 
	var students = new Array();
	$('#studentId :selected').each(function(i, selectedElement) {
   		if($(selectedElement).val() && $(selectedElement).val() != 'select')
   			students.push($(selectedElement).val());
   	 });
   	if(students.length == 0){
	   alert("Please Select Students !!");
	   return false;
   	} else if(titleId && dueDate && instructId){
		$.ajax({
			url : "assignGameToStudents.htm",
			type: 'POST',
			data: "dueDate="+dueDate+"&titleId="+titleId+"&csId="+csId+"&instructId="+instructId+"&students="+students,
			success : function(data){
		      $('#dueDate').val(''); 
	    	  $('#titleId').val(''); 
	    	  $('#instructId').val(''); 
	    	  systemMessage(data);
			}
		});
	}else{
		alert("Please fill all mandatory fields");
	}	
}

function showDiv(id){
	if(id == 'select_all'){
		var select_all = $('#select_all').val();
		if($("#select_all").is(":checked")){
			$('select#studentId > option').prop("selected", true);
		}else{
			$("select#studentId > option").prop('selected', false);
		}		
	}else if(id == 'studentId'){
		var studentId = $('#studentId').val();
		if( studentId.length == 1){
			$("#select_all").removeAttr("checked");
			return false;
		}
	}
	document.getElementById('assignDiv').style.display = '';
}


