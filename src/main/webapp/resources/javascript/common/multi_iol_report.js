function getMultiYearIOLReport(studentId,teacherId,className){
		$("#MultiReportsDialog").html("");
		  $("#loading-div-background1").show();
		   	$.ajax({
				type : "GET",
				url : "getMultiIOLReportsByStudent.htm",
				data : "studentId=" + studentId + "&teacherId=" + teacherId+"&className="+className,
				success : function(response) {
					var reportContainer = $(document.getElementById('MultiReportsDialog'));
					var screenWidth = $( window ).width() - 130;
					var screenHeight = $( window ).height() - 130;
					$(reportContainer).empty(); 
					$(reportContainer).append(response);
					$(reportContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
						  $(".ui-dialog-titlebar-close").show();
					},
					close: function( event, ui ) { 
						$("#MultiReportsDialog").dialog('destroy');
					},
					dialogClass: 'myTitleClass'
					});		
					$(reportContainer).dialog("option", "title", "Learning Indicator");
					$(reportContainer).scrollTop("0");
					$("#loading-div-background1").hide();
				}
			});
		
	}
