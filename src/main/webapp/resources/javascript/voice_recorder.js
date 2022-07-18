
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


function stop(voiceRecId, playerId) {
  // stop the media stream
  //mediaStream.stop();
	mediaStream.getAudioTracks()[0].stop();
	
  // stop Recorder.js
  rec.stop();

  // export it to WAV
  rec.exportWAV(function(e){    
	var url = (window.URL || window.webkitURL).createObjectURL(e);   
	var playme = document.getElementById(playerId);
	var voiceRecorder = document.getElementById(voiceRecId);
	playme.src= url;
	playme.load();
	
	var content;
	var range;
	
	var start = 0;
    var stop = e.size - 1;

    var reader = new FileReader();

    // If we use onloadend, we need to check the readyState.
    reader.onloadend = function(evt) {
      if (evt.target.readyState == FileReader.DONE) {  //DONE == 2
    	  document.getElementById(voiceRecId).value = btoa(evt.target.result); 
    		
      }
    };
    var blob = e.slice(start, stop + 1);
    reader.readAsBinaryString(blob);
	
  });
  rec.clear();
}