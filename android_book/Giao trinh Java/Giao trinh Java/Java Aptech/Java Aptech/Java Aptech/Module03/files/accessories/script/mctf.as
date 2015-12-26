stop();
var count = new Array();
var sType;
var counter;
var scount = 0;
var optionTrack;
var correctCount = 0;
var incorrectCount = 0;
var questionText = new Array();
var correctFeedback = new Array();
var incorrectFeedback = new Array();
var correctAns = new Array();
var options = new Array();
var correctOption = new Array();
var score_array = new Array();
var userArray = new Array();
var sdata;
var sresult = new Array();
close_btn.enabled = 0;
close_btn._alpha = 30;
tick_mc._visible = 0;
_level0.slide_mc.score_mc._visible = true;
_level0.slide_mc.score_mc.rightCount.text = "0";
_level0.slide_mc.score_mc.wrongCount.text = "0";
//-- this is for th bookmarking of the score
bookmark = SharedObject.getLocal("Bookmark", "/");
trace("drop down code ============================================ is called herer");
if (bookmark.getSize() == 0) {
	trace("first Time");
	rightScore = new Array();
	bookmark.data.rightCount = rightScore;
	_level0.slide_mc.score_mc.rightCount.text = "0";
	bookmark.data.rightCount = _level0.slide_mc.score_mc.rightCount.text;
	wrongScore = new Array();
	bookmark.data.wrongCount = wrongScore;
	_level0.slide_mc.score_mc.wrongCount.text = "0";
	bookmark.data.wrongCount = _level0.slide_mc.score_mc.wrongCount.text;
	bookmark.flush(500);
} else {
	trace("visited Time "+bookmark.data.wrongCount);
	rightScore = bookmark.data.rightCount;
	wrongScore = bookmark.data.wrongCount;
	if (bookmark.data.rightCount != undefined) {
		_level0.slide_mc.score_mc.rightCount.text = bookmark.data.rightCount;
	} else {
		_level0.slide_mc.score_mc.rightCount.text = "0";
	}
	if (bookmark.data.wrongCount != undefined) {
		trace("wrong book count is not undefined == "+wrongScore);
		_level0.slide_mc.score_mc.wrongCount.text = bookmark.data.wrongCount;
	} else {
		trace("wrong book count is  undefined");
		_level0.slide_mc.score_mc.wrongCount.text = "0";
	}
	//_level0.slide_mc.score_mc.rightCount.text = "0";
	//_level0.slide_mc.score_mc.wrongCount.text = "0";
}
// -- this is for the storing of values
function fnParseAssessment() {
	glnFirstChild = myData.childNodes[0].childNodes;
	//trace(glnFirstChild);
	for (var i = 0; i<glnFirstChild.length; i++) {
		questionText[i] = glnFirstChild[i].childNodes[0].firstChild.nodeValue;
		//trace(questionText[i]);
		question_txt.html = true;
		question_txt.htmlText = questionText[i];
		correctAns[i] = new Array();
		correctAns[i] = glnFirstChild[i].childNodes[5].firstChild.nodeValue.split(",");
		sdata = correctAns[i];
		for (j=0; j<sdata.length; j++) {
			sresult[j] = sdata[j];
		}
		//trace(sresult);
		correctFeedback[i] = glnFirstChild[i].childNodes[6].firstChild.nodeValue;
		incorrectFeedback[i] = glnFirstChild[i].childNodes[7].firstChild.nodeValue;
		sType = glnFirstChild[i].childNodes[2].firstChild.nodeValue;
		options[i] = new Array();
		optionTrack = glnFirstChild[i].childNodes[2].childNodes.length;
		trace(glnFirstChild[i].childNodes[2].childNodes.length+"it is the count");
		for (var j = 0; j<glnFirstChild[i].childNodes[2].childNodes.length; j++) {
			options[i][j] = glnFirstChild[i].childNodes[2].childNodes[j].firstChild.nodeValue;
			count = glnFirstChild[i].childNodes[2].childNodes[j].firstChild.nodeValue;
			eval("option"+(j+1)+"_mc")._visible = 1;
			eval("option"+(j+1)+"_mc.option"+(j+1)+"_txt").html = true;
			eval("option"+(j+1)+"_mc.option"+(j+1)+"_txt").htmlText = options[i][j];
			correctOption[j] = options[i][j];
			//eval("option"+(j+1)+"_mc.option"+(j+1)+"_txt").text = options[i][j];
		}
	}
	//trace(correctOption.length);
	handcursor(sType);
	delete glnFirstChild;
	delete myData;
	delete fnParseAssessment;
}
//--------------------------------------------------------------------------------
loadXML();
submit_btn.enabled = 0;
submit_btn._alpha = 50;
function handcursor(svalue) {
	//trace("handcursor been called");
	for (i=0; i<=count.length; i++) {
		for (j=1; j<3; j++) {
			eval("option"+(i+1)+"_mc.check_opt"+(j)).useHandCursor = true;
			eval("option"+(i+1)+"_mc.check_opt"+(j))._visible = true;
		}
	}
}
function submitHandler() {
	var scounter = 0;
	for (i=0; i<=count.length; i++) {
		for (j=1; j<3; j++) {
			if (eval("option"+(i+1)+"_mc.check_opt"+(j)).selected == true) {
				scounter++;
			}
		}
	}
	if (scounter >= 1) {
		submit_btn.enabled = 1;
		submit_btn._alpha = 100;
	}
}
function validate() {
	_level0.MCQ_fnShowNext();
	if (String(userArray) == String(correctAns)) {
		tick_mc._visible = 1;
		tick_mc.gotoAndStop(2);
		tick_mc.feedback_txt.html = true;
		tick_mc.feedback_txt.htmlText = correctFeedback;
		correctCount++;
		// this is where the score gets updated
			var temp = _level0.slide_mc.score_mc.rightCount.text;
			_level0.slide_mc.score_mc.rightCount.text = Number(temp)+Number(correctCount);
			rightScore = _level0.slide_mc.score_mc.rightCount.text;
			wrongScore = _level0.slide_mc.score_mc.wrongCount.text;
			bookmark.data.rightCount = rightScore;
			bookmark.data.wrongCount = wrongScore;
			trace("you have got it right: but with some value"+rightScore);
	} else {
		tick_mc._visible = 1;
		tick_mc.feedback_txt.html = true
		tick_mc.feedback_txt.htmlText = incorrectFeedback;
		incorrectCount++;
		//var temp = Math.floor(Number(_level0.slide_mc.score_mc.wrongCount.text));
			var temp = _level0.slide_mc.score_mc.wrongCount.text;
			trace(temp+" is the wrong count");
			_level0.slide_mc.score_mc.wrongCount.text = Number(temp)+Number(incorrectCount);
			//_level0.slide_mc.score_mc.wrongCount.text = 2;
			//trace(_level0.slide_mc.score_mc.wrongCount.text+" is the updated wrong count count");
			wrongScore = _level0.slide_mc.score_mc.wrongCount.text;
			rightScore = _level0.slide_mc.score_mc.rightCount.text;
			bookmark.data.rightCount = rightScore;
			bookmark.data.wrongCount = wrongScore;
			trace("you have got it right: but with some value"+bookmark.data.wrongCount);
	}
	close_btn.enabled = 1;
	close_btn._alpha = 100;
	//this is the place where we have to check for the tick mark
	disable();
	//unloadMovieNum(5);
}
function disable() {
	for (i=0; i<=count.length; i++) {
		for (j=1; j<3; j++) {
			eval("option"+(i+1)+"_mc.check_opt"+(j)).useHandCursor = false;
			eval("option"+(i+1)+"_mc.check_opt"+(j)).enabled = false;
		}
	}
}
submit_btn.onRelease = function() {
	validate();
	submit_btn.enabled = 0;
	submit_btn._alpha = 50;
	feedback_mc._visible = 1;
};
function displayAnswer() {
	for (var j = 0; j<sresult.length; j++) {
		trace(sresult.length+" the array is "+sresult[j]);
		//eval("feedback_mc.option"+(j+1)+"_mc")._visible = 1;
		var temp = sresult[j];
		//eval("feedback_mc.right_option"+(j+1)+"_mc")._visible = 1;
		//eval("feedback_mc.right_option"+(j+1)+"_mc.right_option"+(j+1)+"_txt").text = correctOption[temp-1];
		//eval("tick_mc.feedback_txt").text = incorrectFeedback;
		//trace("Hello I AM HERE");
	}
}
