<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Record Audio</title>
  <style type='text/css'>
    ul { list-style: none; }
    #recordingslist audio { display: block; margin-bottom: 10px; }
  </style>
  <script src="resources/javascript/VoiceRecorder/recorder.js"></script>
  <script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
  
<script type="text/javascript">

var navigator = window.navigator;
navigator.getUserMedia = (
  navigator.getUserMedia ||
    navigator.webkitGetUserMedia ||
    navigator.mozGetUserMedia ||
    navigator.msGetUserMedia
);

var Context = window.AudioContext || window.webkitAudioContext;
var context = new Context();

// we need these variables for later use with the stop function
var mediaStream;
var rec;

function record() {
  // ask for permission and start recording
  navigator.getUserMedia({audio: true}, function(localMediaStream){
    mediaStream = localMediaStream;

    // create a stream source to pass to Recorder.js
    var mediaStreamSource = context.createMediaStreamSource(localMediaStream);

    // create new instance of Recorder.js using the mediaStreamSource
    rec = new Recorder(mediaStreamSource, {
      // pass the path to recorderWorker.js file here
      workerPath: 'resources/javascript/VoiceRecorder/recorderWorker.js'
    });

    // start recording
    rec.record();
  }, function(err){
  });
}


function stop() {
  // stop the media stream
  mediaStream.stop();

  // stop Recorder.js
  rec.stop();

  // export it to WAV
  rec.exportWAV(function(e){   
    //Recorder.forceDownload(e, "filename.wav");	
	var url = (window.URL || window.webkitURL).createObjectURL(e);
    var link = window.document.getElementById('downId');
    link.href = url;
	link.download = 'output.wav';    
	var playme = window.document.getElementById('playme');
	playme.src= url;
	playme.load();
	
	var content;
	var range;
	
	var start = 0;
    var stop = e.size - 1;

    var reader = new FileReader();

    // If we use onloadend, we need to check the readyState.
    reader.onloadend = function(evt) {
      if (evt.target.readyState == FileReader.DONE) { // DONE == 2
    	  content = evt.target.result;
    	  range = e.size;
    	  	var fd = new FormData();
    		fd.append('fname', "test.wav");
    		fd.append('data', btoa(evt.target.result));
    		$.ajax({
    			type: 'POST',
    			url: 'saveAudiorRecord.htm',
    			data: fd,
    			processData: false,
    			contentType: false
    		}).done(function(data) {
    			log.innerHTML += "\n" + data;
    		});
    		
      }
    };
    var blob = e.slice(start, stop + 1);
    reader.readAsBinaryString(blob);
	
  });
  rec.clear();
}


</script>
  
</head>
<body>
<%-- <form:form id="createQuestions"  action="createAssessments.htm" modelAttribute="questionsList" method="post" enctype="multipart/form-data"> --%>

  <h1>Recorder.js simple WAV export example</h1>

  <button onclick="record()">Record</button>
  <button onclick="stop()">Stop</button>
  
  <h2>Recordings</h2>
  <ul id="recordingslist"></ul>
  <audio id="playme" src="" controls></audio>
  
  <a id="downId">download</a>
  <input type="file" id="fId">
  
  <h2>Log</h2>
  <pre id="log"></pre>
  <input id="file" type="file" name="file" accept="audio/*;capture=microphone">/>
  <h2>start</h2></br>
  <div id="byte_content"></div>
  
  <h2>stop</h2></br>
  
<%-- </form:form> --%>
</body>
</html>
