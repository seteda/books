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
		question_txt.text = questionText[i];
		correctAns[i] = new Array();
		correctAns[i] = glnFirstChild[i].childNodes[4].firstChild.nodeValue.split(",");
		sdata = correctAns[i];
		for (j=0; j<sdata.length; j++) {
			sresult[j] = sdata[j];
		}
		trace(sresult);
		correctFeedback[i] = glnFirstChild[i].childNodes[5].firstChild.nodeValue;
		incorrectFeedback[i] = glnFirstChild[i].childNodes[6].firstChild.nodeValue;
		sType = glnFirstChild[i].childNodes[2].firstChild.nodeValue;
		options[i] = new Array();
		count = glnFirstChild[i].childNodes[1].childNodes.length;
		//trace(correctFeedback);
		for (var j = 0; j<glnFirstChild[i].childNodes[1].childNodes.length; j++) {
			options[i][j] = glnFirstChild[i].childNodes[1].childNodes[j].firstChild.nodeValue;
			//trace(options[i][j]);
			eval("option"+(j)+"_mc")._visible = 1;
			eval("option"+(j)+"_mc.option"+(j)+"_txt").text = options[i][j];
		}
		steps = new Array();
		for (var j = 0; j<glnFirstChild[i].childNodes[2].childNodes.length; j++) {
			steps[j] = glnFirstChild[i].childNodes[2].childNodes[j].firstChild.nodeValue;
			trace(glnFirstChild[i].childNodes[2].childNodes[j].firstChild.nodeValue);
			eval("step"+(j)+"_mc")._visible = 1;
			eval("step"+(j)+"_mc.step"+(j)+"_txt").text = steps[j];
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
function handcursor(svalue) {
	for (i=0; i<=count; i++) {
		//trace("option"+i+"_mc.radio_opt"+i);
		eval("option"+(i)+"_mc.check_opt"+(i+1)).useHandCursor = true;
		eval("option"+(i)+"_mc.check_opt"+(i+1))._visible = true;
		//trace(eval("option"+(i+1)+"_mc.check_opt"+(i+1)));
	}
}
function validate() {
	_level0.MCQ_fnShowNext();
	// this is for the Multiple choce multiple select quiz 
	for (i=0; i<=count; i++) {
		if (eval("option"+i+"_mc.check_opt"+(i+1)).selected == true) {
			var temp = eval("option"+i+"_mc.check_opt"+(i+1));
			userArray.push(substring(temp, 29, -1));
			//trace(substring(temp, 42, -1));
			//trace("hello "+substring(temp, 29, -1));
		}
	}
	for (i=0; i<userArray.length; i++) {
		for (j=0; j<sresult.length; j++) {
			if (userArray[i] == sresult[j]) {
				scount++;
			}
		}
	}
	trace(userArray);
	trace(sresult.length);
	if (scount == sresult.length && userArray.length == sresult.length) {
		feedback_mc.feedback_txt.text = correctFeedback;
		feedback_mc._visible = 1;
		feedback_mc.gotoAndStop(2);
		feedback_mc.feedback_txt.text = correctFeedback;
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
		feedback_mc.feedback_txt.text = incorrectFeedback+" "+correctAns;
		feedback_mc._visible = 1;
		feedback_mc.feedback_txt.text = incorrectFeedback;
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
function disable(svalue) {
	for (i=0; i<=count; i++) {
		//trace("option"+i+"_mc.radio_opt"+i);
		eval("option"+i+"_mc.check_opt"+(i+1)).useHandCursor = false;
		eval("option"+i+"_mc.check_opt"+(i+1)).enabled = false;
		eval("option"+i+"_mc.radio_opt"+(i+1))._visible = false;
		eval("option"+i+"_mc.check_opt"+(i+1))._visible = true;
	}
}
submit_btn.onRelease = function() {
	validate();
	submit_btn.enabled = 0;
	submit_btn._alpha = 50;
};
