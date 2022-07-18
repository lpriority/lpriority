$('.form').find('input, textarea').on('keyup blur focus', function (e) {
  
  var $this = $(this),
      label = $this.prev('label');

	  if (e.type === 'keyup') {
			if ($this.val() === '') {
          label.removeClass('active highlight');
        } else {
          label.addClass('active highlight');
        }
    } else if (e.type === 'blur') {
    	if( $this.val() === '' ) {
    		label.removeClass('active highlight'); 
			} else {
		    label.removeClass('highlight');   
			}   
    } else if (e.type === 'focus') {
      
      if( $this.val() === '' ) {
    		label.removeClass('highlight'); 
			} 
      else if( $this.val() !== '' ) {
		    label.addClass('highlight');
			}
    }

});

$('.tab a').on('click', function (e) {
  
  e.preventDefault();
  
  $(this).parent().addClass('active');
  $(this).parent().siblings().removeClass('active');
  
  target = $(this).attr('href');

  $('.tab-content > div').not(target).hide();
  
  $(target).fadeIn(600);
  
});

function check(input) {
    if (input.value != document.getElementById('password').value) {
        alert("Password Must be Matching.");
        document.getElementById('confirmPassword').value="";
        return false;
    } else {
    }
}

function getStudentSchoolGrades(){
	var stdRegId = $('#emailId').val();
	if(stdRegId != 'select'){
		$.ajax({
			type : "GET",
			url : "getStudentSchoolGrades.htm",
			data : "stdRegId=" + stdRegId,
			success : function(response) {
				$("#regId").val(stdRegId);
				var schoolgrades = response.schoolgrades;
				$("#gradeId").empty();
				$("#gradeId").append($("<option></option>").val("select").html('Select Grade'));
				$.each(schoolgrades, function(index, value) {
					$("#gradeId").append($("<option></option>").val(value.gradeId).html(value.masterGrades.gradeName));
				});
			}
		});
	}else{
		$("#regId").val("");
		$("#gradeId").empty();
		$("#gradeId").append($("<option></option>").val("select").html('Select Grade'));
	}
}

$(window).load(function () { 
	var checkSavedVal = function(){
		var username = $("#login-username").val();
		//var password = $("#login-password").val();
		var emailLabel = $("#emailLabel");
		var passwordLabel = $("#passwordLabel");
		//if( username !== ''){
			 emailLabel.addClass('active highlight');
			 passwordLabel.addClass('active highlight'); 
		//}
	}
	window.setTimeout(checkSavedVal,50); 
});

