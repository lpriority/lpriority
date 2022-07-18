/**
 * 
 */

             var chat = "";
             var randomColor = '#'+(Math.random()*0xFFFFFF<<0).toString(10);
             function loginChat(){
				 var pGroupStudentId = document.getElementById("pGroupStudentId").value;
             	 $.ajax({
                           type: "POST",
                          url: "loginToChat.htm",
                          data:"pGroupStudentId="+pGroupStudentId,
                          success: function() {
                          },
                          error: function(e){
                             alert("error"+e.toSource());
                          }
                     });
             }
             function exitChat(){
            	 var taskId = document.getElementById("assignedTaskId").value;
				 var pGroupStudentId = document.getElementById("pGroupStudentId").value;
             	 $.ajax({
                           type: "POST",
                          url: "logOutChat.htm",
                          data:"pGroupStudentId="+pGroupStudentId,
                          success: function() {
                            openedWindow.close();
                          },
                          error: function(e){
                             alert("error"+e.toSource());
                          }
                     });
             }
             function sendMessage(){
	            	var taskId = document.getElementById("assignedTaskId").value;
					var pGroupStudentId = document.getElementById("pGroupStudentId").value;
             		var usermsg = document.getElementById("usermsg").value;
                     $.ajax({
                           type: "POST",
                           data:"taskId="+taskId+"&pGroupStudentId="+pGroupStudentId+"&usermsg="+usermsg+"&randomColor="+randomColor,
                          url: "saveMessage.htm",
                          success: function() {
                             $("#usermsg").val("");
                          },
                          error: function(e){
                             alert("error"+e.toSource());
                          }
                     });
              }
             
              function getOnlineUsers(){
            	 	 var taskId = document.getElementById("assignedTaskId").value;
					 var pGroupStudentId = document.getElementById("pGroupStudentId").value;
					 var pGroupId = document.getElementById("pGroupId").value;					 
                     $.ajax({
                           type: "POST",
                           data:"pGroupId="+pGroupId+"&taskId="+taskId,
                          url: "getOnlineUsers.htm",
                          dataType: "json",
                          success: function(result) {
                             for(var i=0; i<result.onlineUsers.length; i++){
                                    var oo = "<a href=javascript:selectUser('"+result.onlineUsers[i].regId+"')><font color='blue'>"+result.onlineUsers[i].firstName +"</font></a>";
                                    if(i==0){
                                            $("#onlineusers").html(oo);    
                                    }else{
                                            $("#onlineusers").append("<br>"+oo);    
                                    }
                             }
                          },
                          error: function(e){
                             alert("error"+e.toSource());
                          }
                     });
              }
             
              function getMyMessages(){
              		 var taskId = document.getElementById("assignedTaskId").value;
					 var pGroupStudentId = document.getElementById("pGroupStudentId").value;
                     $.ajax({
                           type: "POST",
                           data:"taskId="+taskId+"&pGroupStudentId="+pGroupStudentId,
                          url: "getMyMessages.htm",
                          dataType: "json",
                          success: function(result) {
                             for(var i=0; i<result.messages.length; i++){
                                    var oo = "<tr bgcolor='gray' border='2' >"+
                                                              "<td>"+
                                                                            "<tr><td>"+result.messages[i].chatcontents+"</td></tr>"+
                                                               "</td>"+
                                                  "</tr>"; 
                                     if(chat != result.messages[0].chatcontents){
                                     	$("#chatbox").html(oo); 
                                        chat = result.messages[0].chatcontents;
                                        var box = document.getElementById('chatbox');
               						 	box.scrollTop = box.scrollHeight;
                                     }
                                    
                             }
                          },
                          error: function(e){
                             alert("error"+e.toSource());
                          }
                     });
              }