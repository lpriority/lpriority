/**
 * 
 */
var voices;
var utterance;
var chunkLength = 200;
var pattRegex = new RegExp('^[\\s\\S]{' + Math.floor(chunkLength / 2) + ',' + chunkLength + '}[.!?,]{1}|^[\\s\\S]{1,' + chunkLength + '}$|^[\\s\\S]{1,' + chunkLength + '} ');

$( document ).ready(function() {
	voices = speechSynthesis.getVoices();
});

function readPassage(){
	if (!window.speechSynthesis.speaking) {
		window.speechSynthesis.cancel();
		var langId = document.getElementById("langId").value;
		var word = document.getElementById("passage").value;
		voices = speechSynthesis.getVoices();
		var arr = [];
		while (word.length > 0) {
            arr.push(word.match(pattRegex)[0]);
            word = word.substring(arr[arr.length - 1].length);
        }
		$.each(arr, function () {
			utterance = new SpeechSynthesisUtterance(this.trim());
			if(langId == 1){
		    	var langObj = getLangObject(voices, 'Google UK English Female');
			    utterance.voice = langObj;
		    }else if(langId == 2){
		    	var langObj = getLangObject(voices, 'Google español');
			    utterance.voice = langObj;
		    }
		    utterance.rate = 1;
		    utterance.pitch = 1;
            window.speechSynthesis.speak(utterance);
        });
	}else{
		speechSynthesis.resume();
	    document.getElementById("speak_button").value="Play";
	}
}

function pauseReadingPassage(){
  document.getElementById("speak_button").value="Resume";
  speechSynthesis.pause();
}

function stopReadingPassage(){
  if (window.speechSynthesis.paused) {
    	 window.speechSynthesis.resume();
    	 window.speechSynthesis.cancel();
    	 document.getElementById("speak_button").value="Play";
    }else{
    	 window.speechSynthesis.cancel();
    	 document.getElementById("speak_button").value="Play";
    }
}
function spellIt(word){
	var langId = document.getElementById("langId").value;
	utterance = new SpeechSynthesisUtterance();
	voices = speechSynthesis.getVoices();
    utterance.text = word;	
    if(langId == 1){
    	var langObj = getLangObject(voices, 'Google UK English Female');
	    utterance.voice = langObj;
    }else if(langId == 2){
    	var langObj = getLangObject(voices, 'Google español');
	    utterance.voice = langObj;
    }
    utterance.rate = 1;
    utterance.pitch = 1;
    window.speechSynthesis.speak(utterance);
}

function getLangObject(voices, name){	
	var obj;
	$.each(voices, function (index) {
		if(voices[index].name == 'Google UK English Female'){
			obj = voices[index];
			return false; 
		}
	});
	return obj; 
}