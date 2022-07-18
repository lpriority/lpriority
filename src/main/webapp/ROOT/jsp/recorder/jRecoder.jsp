<!DOCTYPE html>
<html>
<head lang="en">
  <meta charset="UTF-8">
  <title>jRecoder Demo</title>
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.2.min.js"></script>
  <script type="text/javascript" src="resources/javascript/recorder/jRecorder.js"></script>
  <link rel="stylesheet" href="resources/css/recorder/style.css">

</head>

<body>
 <script>
            var filename=Math.random().toString(36).slice(2)+".wav";
            $.jRecorder(
            {
               // host : '../UploadDirections?filename='+filename ,  //replace with your server path please

                callback_started_recording:     function(){callback_started(); },
                callback_stopped_recording:     function(){callback_stopped(); },
                callback_activityLevel:          function(level){callback_activityLevel(level); },
                callback_activityTime:     function(time){callback_activityTime(time); },
                callback_finished_sending:     function(time){ callback_finished_sending() },
                swf_path : 'resources/javascript/recorder/jRecorder.swf'
            }
        );
        </script>
        <div style="background-color: #eeeeee;border:1px solid #cccccc">
            Time: <span id="time">00:00</span>
        </div>
        <div style="visibility:hidden" >
            Level: <span id="level"></span>
        </div>
        <div class="timer" style="visibility:hidden"  >
            <span class="minute">00</span>:<span class="second">00</span>
        </div>
        <div id="levelbase" style="width:200px;height:20px;background-color:#ffff00">
            <div id="levelbar" style="height:19px; width:2px;background-color:red"></div>
        </div>
        <div>
            Status: <span id="status"></span>
        </div>
        <span id="countdown" style="visibility: hidden;font-weight: bold;">30</span>
        <input type="button" id="record" value="Record" style="color:red">
        <input type="button" id="stop" value="Stop">
        <input type="button" id="send" value="Send Data">
<!--        <input type="button" value="Go Back" id="GetF" onclick="javascript:return updateParent()" />-->
</body>
</html>
<script type="text/javascript">
    $('#record').click(function(){
        $.jRecorder.record(120);
    })
    $('#stop').click(function(){
        $.jRecorder.stop();
    })
    $('#send').click(function(){
        $.jRecorder.sendData();
        setInterval(function(){
            checkFile()
        },5000);
    })
    function callback_finished()
    {
        $('#status').html('Recording is finished');
    }
    function callback_started()
    {
        $('#status').html('Recording is started');
    }
    function callback_error(code)
    {
        $('#status').html('Error, code:' + code);
    }
    function callback_stopped()
    {
        $('#status').html('Stop request is accepted');
    }
    function callback_finished_recording()
    {
        $('#status').html('Recording event is finished');
    }
    function callback_finished_sending()
    {
        $('#status').html('Please wait while the recorded voice is being uploaded to the server');
    }
    function callback_activityLevel(level)
    {
        $('#level').html(level);
        if(level == -1)
        {
            $('#levelbar').css("width",  "2px");
        }
        else
        {
            $('#levelbar').css("width", (level * 2)+ "px");
        }
    }
    function callback_activityTime(time)
    {
        //$('.flrecorder').css("width", "1px");
        //$('.flrecorder').css("height", "1px");
        $('#time').html(time);
    }
</script>



