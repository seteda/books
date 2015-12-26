stop();
var count = 0;
var sType;
var counter;
var scount = 0;
var correctCount = 0;
var incorrectCount = 0;
var questionText = new Array();
var correctFeedback = new Array();
var incorrectFeedback = new Array();
var correctAns = new Array();
var options = new Array();
var score_array = new Array();
var userArray = new Array(0);
var sdata;
var sresult = new Array();
var displayData;
close_btn.enabled = 0;
close_btn._alpha = 50;
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
	//trace(myData);
	for (var i = 0; i<glnFirstChild.length; i++) {
		questionText[i] = glnFirstChild[i].childNodes[0].firstChild.nodeValue;
		//trace(questionText+"th text");
		question_txt.html = true;
		question_txt.htmlText = questionText[i];
		question1_txt.text = glnFirstChild[i].childNodes[1].childNodes;
		correctAns[i] = new Array();
		correctAns[i] = glnFirstChild[i].childNodes[3].firstChild.nodeValue.split(",");
		sdata = correctAns[i];
		for (j=0; j<sdata.length; j++) {
			sresult[j] = sdata[j];
		}
		//trace(sresult);
		correctFeedback[i] = glnFirstChild[i].childNodes[4].firstChild.nodeValue;
		incorrectFeedback[i] = glnFirstChild[i].childNodes[5].firstChild.nodeValue;
		//trace(correctFeedback[i])
		sType = glnFirstChild[i].childNodes[3].firstChild.nodeValue;
		options[i] = new Array();
		count = glnFirstChild[i].childNodes[2].childNodes.length;
		trace(glnFirstChild[i].childNodes[1].childNodes.length);
		for (var j = 0; j<glnFirstChild[i].childNodes[1].childNodes.length; j++) {
			options[j] = glnFirstChild[i].childNodes[1].childNodes[j].firstChild.nodeValue;
			trace(glnFirstChild[i].childNodes[1].childNodes[j].childNodes.length);
			
			eval("option"+(j+1)+"_txt").html = true;
			eval("option"+(j+1)+"_txt").text = options[j];
			eval("option"+(j+1)+"_txt").selected = false;
			eval("hit"+(j+1)+"_btn").useHandCursor = false;
			eval("hit"+(j+1)+"_btn").onRelease = function() {
			
				var temp = this._name;
				userArray = temp.substring(temp.lastIndexOf("_"), 3);
				selected_mc._visible = true;
			selected_mc.gotoAndStop(userArray);

				submit_btn.enabled = 1;
				submit_btn._alpha = 100;
			};
		}
	}
	handcursor(sType);
	delete glnFirstChild;
	delete myData;
	delete fnParseAssessment;
}
//--------------------------------------------------------------------------------
loadXML();
submit_btn.enabled = 0;
submit_btn._alpha = 50;
function validate() {
	_level0.MCQ_fnShowNext();
	if (String(userArray) == String(correctAns)) {
		feedback_mc._visible = 1;
		feedback_mc.gotoAndStop(2);
		feedback_mc.feedback_txt.vScrollPolicy = "auto";
		feedback_mc.feedback_txt.html = true;
		feedback_mc.feedback_txt.htmlText = correctFeedback;
		right_mc._visible = true;
		right_mc.gotoAndStop(correctAns);
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
		feedback_mc._visible = 1;
		feedback_mc.feedback_txt.vScrollPolicy = "auto";
		feedback_mc.feedback_txt.html = true
		feedback_mc.feedback_txt.htmlText = incorrectFeedback;
		right_mc._visible = true;
		right_mc.gotoAndStop(correctAns);
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
	//trace(sresult.sort());
	for (i=0; i<=options.length; i++) {
		eval("hit"+(i+1)+"_btn").useHandCursor = false;
		eval("hit"+(i+1)+"_btn").enabled = false;
	}
	close_btn.enabled = 1;
	close_btn._alpha = 100;
}
//unloadMovieNum(5);
submit_btn.onRelease = function() {
	validate();
	submit_btn.enabled = 0;
	submit_btn._alpha = 50;
};
function returnToNormal(sValue) {
	trace("function called");
	for (i=0; i<7; i++) {
		if (i == sValue) {
		} else {
			eval("option"+i+"_mc").gotoAndStop(1);
		}
	}
}
