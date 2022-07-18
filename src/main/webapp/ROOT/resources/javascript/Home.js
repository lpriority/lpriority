
function FirstTimeUserInfo()
{

	 var name = $("#homeEmail").val();
	 var userType=$("#userType" ).val();
	 if(name== '' || name==null){
		 alert("Please enter the email address");
         $('#homeEmail').next().show();
         return false;
      }
	 else if(IsEmail(name)==false){
    	  alert("Please enter valid email address");
          $('#homeEmail').show();
          return false;
      }
	 if(userType=="Select")
		 {
		  alert("Please select the user Type");
		  $('#userType').next.show();
		  return false;
		 }
	 	 
	$.ajax({  
		url : "FirstTimeUserInfo.htm",
        data: "emailId=" + name+"&userType="+userType,
        success : function(data) { 
        	$('#homeEmail').val('');
        	window.location.replace(data);
                       
        }  
    }); 
	

    document.getElementById('emailId').focus();
}


function homeVal()
{
    var res = "";
    var message = "";
    var homeForm=window.document.FirstTimeForm;
    var homeEmail=homeForm.homeEmail.value;
    var homeUser=homeForm.homeUser.value;

    res=emptyVal(homeEmail);
    if(res!="")
    {
        message=res;
        systemMessage("Please enter your E-Mail address ");
        homeForm.homeEmail.focus();
        return false;
    }

    res=emailVal(homeEmail);
    if(res!="")
    {
        message = res;
        systemMessage("Please enter your valid E-Mail address ");
        //homeForm.homeEmail.value="";
        homeForm.homeEmail.focus();
        return false;
    }
    res=fieldLength(homeEmail,9,45);
    if(res!="")
    {
        message = " Your E-Mail " + res;
        systemMessage(message);
        homeForm.homeEmail.value="";
        homeForm.homeEmail.focus();
        return false;
    }

    res=emptyVal(homeUser);
    if(res!="")
    {
        message=res;
        systemMessage("Please Select the UserType");
        homeForm.homeUser.focus();
        return false;
    }
    homeForm.action="HomeServlet";
    homeForm.method="post";
    homeForm.submit();
    return true;



}
function IsEmail(email) {
    var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if(!regex.test(email)) {
       return false;
    }else{
       return true;
    }
  }
