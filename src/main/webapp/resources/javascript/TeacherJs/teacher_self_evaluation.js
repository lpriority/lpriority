//---------------------Add User------------------------//


	function load() {
		window.document.getElementById('parentEmailId').focus();
	}
	function adminCreateTeacherReports(count,usertype) {
		
		 var performanceids = [];
		 var performances= [];
		 var expression=[];
		 var teacherRegId=0;
		 usertype=usertype.toLowerCase();
		 if(usertype=="admin"){
			  teacherRegId=$('#teacherId').val();
			 if(teacherRegId== '' || teacherRegId==null || teacherRegId=='select'){
				 systemMessage("Please select the teacher");
		        $('#teacherId').next().show();
		        return false;
		     }
		}
		 for(var i=1;i<=count;i++){
			var perid=$('#perid'+i).val();
			var performance=$('#select'+i).val();
			var exp=$('#exp'+i).val();
			performanceids.push(perid);
			performances.push(performance);
			expression.push(exp);
			 if(performance== '' || performance==null || performance=='select'){
				 systemMessage("Please select the Performance");
		       $('#select'+i).next().show();
		       return false;
			 }else if(exp== '' || exp==null || exp=='select'){
				  systemMessage("Please enter the expression");
			       $('#exp'+i).next().show();
			       return false;
				 }
		}
		
			 $.ajax({  
					url : "saveTeacherSelfEvaluationReports.htm",
					type: "POST",
					data: "performanceids="+performanceids+"&performances="+performances+"&expression="+expression+"&teacherRegId="+teacherRegId,
					success : function(data) { 
						 systemMessage(data);
						document.getElementById("adminTeacherReportForm").reset(); 
						return false;
			        }  
			    }); 
			 return false;
		}
	function getReportDates(thisvar) {
		$("#getReports").html("");
		 $.ajax({  
				url : "getTeacherReportDates.htm",
				type: "GET",
				data: "teacherId="+thisvar.value,
				success : function(response) {
					$("#reportDate").empty();
					$("#reportDate").append(
							$("<option></option>").val('select').html('select'));
			
					var teacherReportDates = response.teacherReportDates;
					$.each(teacherReportDates, function(index, value) {
						$("#reportDate").append(
								$("<option></option>").val(index).html(value));
					});
		        }  
		    }); 
	
	}
	function changeDate()
	{
		var teacherId=$('#teacherId').val();
		if(teacherId== '' || teacherId==null || teacherId=='select'){
			systemMessage("Please select the teacher");
	        $('#teacherId').next().show();
	        return false;
	     }
		 var reportDate=$('#reportDate').val();
		 if(reportDate== '' || reportDate==null || reportDate=='select'){
			$("#getReports").empty();
	        $('#reportDate').next().show();
	        return false;
	     }
		 $("#loading-div-background").show();
	$.ajax({  
		url : "getTeacherSelfReports.htm", 
	    data: "teacherId="+teacherId+"&reportDate="+reportDate,
	    success: function(response) {
	          $("#getReports").html(response);
	          $("#loading-div-background").hide();
	    }
	}); 	
	}
	
	
	


	
	