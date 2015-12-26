stop();
var nAttempt = 0;
var count = 0;
var sType;
var counter;
var scount = 0;
var correct_Array;
var correctCount = 0;
var incorrectCount = 0;
var questionText = new Array();
var correctFeedback = new Array();
var incorrectFeedback = new Array();
var correctAns = new Array();
var options = new Array();
var score_array = new Array();
var userArray = new Array(0);
var questionOption = new Array(0);
var sdata;
var sresult = new Array();
var xPos;
var yPos;
close_btn.enabled = 0;
close_btn._alpha = 30;
tick_mc._visible = 0;
submit_btn.enabled = 0;
submit_btn._alpha = 50;
view_btn.enabled = 0;
view_btn._alpha = 50;
xPos = submit_btn._x;
yPos = submit_btn._y;
_level0.slide_mc.score_mc._visible = true;
//-- this is for th bookmarking of the score
bookmark = SharedObject.getLocal("Bookmark", "/");
if (bookmark.getSize() == 0) {
	trace("first Time");
	rightScore = new Array();
	bookmark.data.rightCount = rightScore;
	_level0.slide_mc.score_mc.rightCount = 0;
	wrongScore = new Array();
	bookmark.data.wrongCount = wrongScore;
	_level0.slide_mc.score_mc.wrongCount = 0;
	bookmark.flush(500);
} else {
	trace("visited Time");
	rightScore = bookmark.data.rightCount;
	wrongScore = bookmark.data.wrongCount;
	_level0.slide_mc.score_mc.rightCount = rightScore;
	_level0.slide_mc.score_mc.wrongCount = wrongScore;
}
// -- this is for the storing of values
function fnParseAssessment() {
	//trace("fnParseAssessment == ");
	glnFirstChild = myData.childNodes[0].childNodes;
	//trace(glnFirstChild+" the gchildNodes");
	for (var i = 0; i<glnFirstChild.length; i++) {
		questionText[i] = glnFirstChild[i].childNodes[0].firstChild.nodeValue;
		//trace(questionText[i]);
		question_txt.text = questionText[i];
		//correct_Array[i] = new Array();
		correct_Array = glnFirstChild[i].childNodes[4].firstChild.nodeValue;
		sdata = correctAns[i];
		correctAnswer_mc.option0_txt.text = correct_Array;
		for (j=0; j<sdata.length; j++) {
			sresult[j] = sdata[j];
		}
		//trace(glnFirstChild[i].childNodes[4].firstChild.nodeValue+"is the value");
		correctFeedback[i] = glnFirstChild[i].childNodes[5].firstChild.nodeValue;
		incorrectFeedback[i] = glnFirstChild[i].childNodes[6].firstChild.nodeValue;
		sType = glnFirstChild[i].childNodes[2].firstChild.nodeValue;
		options[i] = new Array();
		count = glnFirstChild[i].childNodes[1].childNodes.length;
		//trace(incorrectFeedback);
		for (var j = 0; j<glnFirstChild[i].childNodes[1].childNodes.length; j++) {
			options[i][j] = glnFirstChild[i].childNodes[1].childNodes[j].firstChild.nodeValue;
			questionOption.push(options[i][j]);
			//trace(options[i][j]);
			eval("option"+(j)+"_mc")._visible = 1;
			eval("option"+(j)+"_mc.option"+(j)+"_txt").text = "";
			eval("option"+(j)+"_mc.option"+(j)+"_txt").setFocus();
		}
	}
	handcursor(sType);
	//delete glnFirstChild;
	//delete myData;
	//delete fnParseAssessment;
}
//--------------------------------------------------------------------------------
loadXML();
submit_btn.enabled = 0;
submit_btn._alpha = 50;
function handcursor(svalue) {
	for (i=0; i<=10; i++) {
		//trace("option"+i+"_mc.radio_opt"+i);onChanged 
		eval("option"+(i)+"_mc.radio_opt"+(i+1)).useHandCursor = true;
		eval("option"+(i)+"_mc.radio_opt"+(i+1))._visible = true;
		eval("option"+(i)+"_mc.radio_opt"+(i+1)).selected = false;
		//trace(eval("option"+(i+1)+"_mc.check_opt"+(i+1)));
	}
}
function validate() {
	_level0.MCQ_fnShowNext();
	option0_mc.option0_txt.enabled = false;
	option0_mc.option0_txt.selectable = false;
	submit_btn.enabled = false;
	submit_btn._alpha = 50;
	// this is for the Multiple choce multiple select quiz 
trace(correct_Array);
	if (String(option0_mc.option0_txt.text) == String(correct_Array)) {
		feedback_mc._visible = 1;
		feedback_mc.gotoAndStop(1);
		feedback_mc.feedback_txt.html = true;
		feedback_mc.feedback_txt.htmlText = correctFeedback;
		correctCount++;
		rightCount.text = "0"+correctCount;
		submit_btn.enabled = false;
		submit_btn._alpha = 50;
		var temp = Math.floor(Number(_level0.slide_mc.score_mc.rightCount));
		trace("you have got it right: but with some value"+temp);
		_level0.slide_mc.score_mc.rightCount = Number(temp)+Number(correctCount);
		rightScore = _level0.slide_mc.score_mc.rightCount;
		wrongScore = _level0.slide_mc.score_mc.wrongCount;
		bookmark.data.rightCount = rightScore;
		bookmark.data.wrongCount = wrongScore;
	} else {
		feedback_mc._visible = 1;
		feedback_mc.gotoAndStop(1);
		feedback_mc.feedback_txt.html = true;
		feedback_mc.feedback_txt.htmlText = incorrectFeedback;
		view_btn.enabled = true;
		view_btn._alpha = 100;
		incorrectCount++;
		var temp = Math.floor(Number(_level0.slide_mc.score_mc.wrongCount));
		trace("you have got it wrong: but with some value"+temp);
		_level0.slide_mc.score_mc.wrongCount = Number(temp)+Number(incorrectCount);
		wrongScore = _level0.slide_mc.score_mc.wrongCount;
		rightScore = _level0.slide_mc.score_mc.rightCount;
		bookmark.data.rightCount = rightScore;
		bookmark.data.wrongCount = wrongScore;
	}
	for (i=1; i<=count; i++) {
		eval("ans"+i)._visible = 1;
		eval("ans"+i).gotoAndStop("neg");
		//eval("option"+sresult[i]+"_mc.dis_mc").gotoAndStop(2);
	}
	//trace(sresult.sort());
	for (i=0; i<=count; i++) {
		eval("ans"+i)._visible = 1;
		eval("ans"+sresult[i]).gotoAndStop(1);
	}
	close_btn.enabled = 1;
	close_btn._alpha = 100;
	disable(sType);
	//unloadMovieNum(5);
}
view_btn.onRelease = function() {
		view_btn.enabled = 0;
		view_btn._alpha = 50;
		displayAnswer();
	};
function disable(svalue) {
	for (i=0; i<=count; i++) {
		//trace("option"+i+"_mc.radio_opt"+i);
		eval("option"+(i)+"_mc.radio_opt"+(i+1)).useHandCursor = false;
		eval("option"+(i)+"_mc.radio_opt"+(i+1)).enabled = false;
	}
}
function displayAnswer() {
	correctAnswer_mc._visible = true;
	correctAnswer_mc.option0_txt.selectable = false;
	}
submit_btn.onRelease = function() {
	this._parent.MCQ_fnShowNext();
	validate();
};
