'use strict';

navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;
window.AudioContext = window.AudioContext || window.webkitAudioContext;
if(getBrowser() == "Chrome"){
	var constraints = {"audio": true};//Chrome
}else if(getBrowser() == "Firefox"){
	var constraints = {audio: true}; //Firefox
}

var dataElement = document.querySelector('#data');
var downloadLink = document.querySelector('a#downloadLink');


function errorCallback(error){
}

var mediaRecorder;
var canvas; 
var audioCtx;
var canvasCtx;
var chunks = [];
var count = 0;

var audioContext = null;
var canvasContext = null;
var meter = null;
var WIDTH=100;
var HEIGHT=25;
var rafID = null;
var canvas = $('#canvas')[0];
  if(canvas)
    canvasContext = canvas.getContext('2d');
function startRecording(stream) {
	//console.log('Start recording...');
	if (typeof MediaRecorder.isTypeSupported == 'function'){
		var options = {mimeType: 'audio/webm'};
		mediaRecorder = new MediaRecorder(stream, options);
	}else{
		console.log('Using default codecs for browser');
		mediaRecorder = new MediaRecorder(stream);
	}
	/*audioCtx = new (window.AudioContext || webkitAudioContext)();
	canvas = document.getElementById("canvas");
	canvasCtx = canvas.getContext("2d");
	visualize(stream);*/
	if(canvas){
	    audioContext = new AudioContext();
		gotStream(stream);
	}
	mediaRecorder.start(10);
	mediaRecorder.ondataavailable = function(e) {
		 if (e.data.size > 0){
			 chunks.push(e.data);
		 }
	};

	mediaRecorder.onerror = function(e){
		//console.log('Error: ' + e);
		//console.log('Error: ', e);
	};


	mediaRecorder.onstart = function(){
		//console.log('Started & state = ' + mediaRecorder.state);
	};

	mediaRecorder.onstop = function(e){
		//console.log('Stopped  & state = ' + mediaRecorder.state);
		var blob = new Blob(chunks, {type: "audio/wav"});
		chunks = [];
	};

	mediaRecorder.onpause = function(){
		//console.log('Paused & state = ' + mediaRecorder.state);
	}

	mediaRecorder.onresume = function(){
		//console.log('Resumed  & state = ' + mediaRecorder.state);
	}

	mediaRecorder.onwarning = function(e){
		//console.log('Warning: ' + e);
	};
}

function recordAudio(callback){
	if (typeof MediaRecorder === 'undefined' || !navigator.getUserMedia) {
		alert('MediaRecorder not supported on your browser, use Firefox 30 or Chrome 49 instead.');
	}else {
		navigator.getUserMedia(constraints, startRecording, errorCallback);
	}
	if(callback)
		callback();
}

function stopRecording(callback){
	 mediaRecorder.stop();
	 if(meter)
		 meter.shutdown();
	 
	 var base64data;
	 var blob = new Blob(chunks, {type: "audio/wav"});
	 var reader = new window.FileReader();
     reader.readAsDataURL(blob); 
     reader.onloadend = function() {
     base64data = reader.result; 
     callback(base64data.split(',')[1]);
     }
}

function onPauseResumeClicked(){
	if(pauseResBtn.textContent === "Pause"){
		mediaRecorder.pause();
	}else{
		mediaRecorder.resume();
	}
}

function playBackAudio(callback){
	var blob = new Blob(chunks, {type: "audio/wav"});
	var url = window.URL.createObjectURL(blob);
	callback(url);
}

// volume meter
var mediaStreamSource = null;

function gotStream(stream) {
    mediaStreamSource = audioContext.createMediaStreamSource(stream);
    meter = createAudioMeter(audioContext,0.98,0.95,100);
    mediaStreamSource.connect(meter);
    drawLoop();
}

function drawLoop( time ) {
   canvasContext.clearRect(0,0,WIDTH,HEIGHT);
   if(meter){
    if (meter.checkClipping())
        canvasContext.fillStyle = "green";
    else
        canvasContext.fillStyle = "green";
    canvasContext.fillRect(0, 0, meter.volume*WIDTH*1.4, HEIGHT);

    rafID = window.requestAnimationFrame( drawLoop );
   }
}
//browser ID
function getBrowser(){
	var nVer = navigator.appVersion;
	var nAgt = navigator.userAgent;
	var browserName  = navigator.appName;
	var fullVersion  = ''+parseFloat(navigator.appVersion);
	var majorVersion = parseInt(navigator.appVersion,10);
	var nameOffset,verOffset,ix;

	// In Opera, the true version is after "Opera" or after "Version"
	if ((verOffset=nAgt.indexOf("Opera"))!=-1) {
	 browserName = "Opera";
	 fullVersion = nAgt.substring(verOffset+6);
	 if ((verOffset=nAgt.indexOf("Version"))!=-1)
	   fullVersion = nAgt.substring(verOffset+8);
	}
	// In MSIE, the true version is after "MSIE" in userAgent
	else if ((verOffset=nAgt.indexOf("MSIE"))!=-1) {
	 browserName = "Microsoft Internet Explorer";
	 fullVersion = nAgt.substring(verOffset+5);
	}
	// In Chrome, the true version is after "Chrome"
	else if ((verOffset=nAgt.indexOf("Chrome"))!=-1) {
	 browserName = "Chrome";
	 fullVersion = nAgt.substring(verOffset+7);
	}
	// In Safari, the true version is after "Safari" or after "Version"
	else if ((verOffset=nAgt.indexOf("Safari"))!=-1) {
	 browserName = "Safari";
	 fullVersion = nAgt.substring(verOffset+7);
	 if ((verOffset=nAgt.indexOf("Version"))!=-1)
	   fullVersion = nAgt.substring(verOffset+8);
	}
	// In Firefox, the true version is after "Firefox"
	else if ((verOffset=nAgt.indexOf("Firefox"))!=-1) {
	 browserName = "Firefox";
	 fullVersion = nAgt.substring(verOffset+8);
	}
	// In most other browsers, "name/version" is at the end of userAgent
	else if ( (nameOffset=nAgt.lastIndexOf(' ')+1) <
		   (verOffset=nAgt.lastIndexOf('/')) )
	{
	 browserName = nAgt.substring(nameOffset,verOffset);
	 fullVersion = nAgt.substring(verOffset+1);
	 if (browserName.toLowerCase()==browserName.toUpperCase()) {
	  browserName = navigator.appName;
	 }
	}
	// trim the fullVersion string at semicolon/space if present
	if ((ix=fullVersion.indexOf(";"))!=-1)
	   fullVersion=fullVersion.substring(0,ix);
	if ((ix=fullVersion.indexOf(" "))!=-1)
	   fullVersion=fullVersion.substring(0,ix);

	majorVersion = parseInt(''+fullVersion,10);
	if (isNaN(majorVersion)) {
	 fullVersion  = ''+parseFloat(navigator.appVersion);
	 majorVersion = parseInt(navigator.appVersion,10);
	}
	return browserName;
}

/*function visualize(stream) {
	  var source = audioCtx.createMediaStreamSource(stream);

	  var analyser = audioCtx.createAnalyser();
	  analyser.fftSize = 2048;
	  var bufferLength = analyser.frequencyBinCount;
	  var dataArray = new Uint8Array(bufferLength);

	  source.connect(analyser);
	  //analyser.connect(audioCtx.destination);
	  
	 var WIDTH = 600;
	 var HEIGHT = 50;

	  draw()

	  function draw() {

	    requestAnimationFrame(draw);

	    analyser.getByteTimeDomainData(dataArray);

	    canvasCtx.fillStyle = 'rgb(200, 200, 200)';
	    canvasCtx.fillRect(0, 0, WIDTH, HEIGHT);

	    canvasCtx.lineWidth = 2;
	    canvasCtx.strokeStyle = 'rgb(0, 0, 0)';

	    canvasCtx.beginPath();

	    var sliceWidth = WIDTH * 1.0 / bufferLength;
	    var x = 0;


	    for(var i = 0; i < bufferLength; i++) {
	 
	      var v = dataArray[i] / 128.0;
	      var y = v * HEIGHT/2;

	      if(i === 0) {
	        canvasCtx.moveTo(x, y);
	      } else {
	        canvasCtx.lineTo(x, y);
	      }

	      x += sliceWidth;
	    }

	    canvasCtx.lineTo(canvas.width, canvas.height/2);
	    canvasCtx.stroke();

	  }
	}

*/

