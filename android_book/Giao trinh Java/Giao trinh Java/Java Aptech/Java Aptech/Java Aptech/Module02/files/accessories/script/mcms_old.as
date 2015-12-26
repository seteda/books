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
/*function loadXML() {
	myData = new XML();
	myData.ignoreWhite = true;
	myData.load("cm_c01_t01/textdata/MCSS.xml");
	myData.onLoad = fnParseAssessment;
	delete loadXML;
}*/
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
		correctAns[i] = glnFirstChild[i].childNodes[3].firstChild.nodeValue.split(",");
		sdata = correctAns[i];
		for (j=0; j<sdata.length; j++) {
			sresult[j] = sdata[j];
		}
		//trace(sresult);
		correctFeedback[i] = glnFirstChild[i].childNodes[4].firstChild.nodeValue;
		incorrectFeedback[i] = glnFirstChild[i].childNodes[5].firstChild.nodeValue;
		sType = glnFirstChild[i].childNodes[2].firstChild.nodeValue;
		options[i] = new Array();
		count = glnFirstChild[i].childNodes[1].childNodes.length;
		trace(correctFeedback);
		for (var j = 0; j<glnFirstChild[i].childNodes[1].childNodes.length; j++) {
			options[i][j] = glnFirstChild[i].childNodes[1].childNodes[j].firstChild.nodeValue;
			//trace(options[i][j]);
			eval("option"+(j+1)+"_mc")._visible = 1;
			eval("option"+(j+1)+"_mc.option"+(j+1)+"_txt").text = options[i][j];
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
		if (svalue == "MCSS") {
			eval("option"+(i+1)+"_mc.radio_opt"+(i+1)).useHandCursor = true;
			eval("option"+(i+1)+"_mc.radio_opt"+(i+1))._visible = true;
			eval("option"+(i+1)+"_mc.check_opt"+(i+1))._visible = false;
		} else {
			eval("option"+(i+1)+"_mc.check_opt"+(i+1)).useHandCursor = true;
			eval("option"+(i+1)+"_mc.radio_opt"+(i+1))._visible = false;
			eval("option"+(i+1)+"_mc.check_opt"+(i+1))._visible = true;
		}
		if (i == count) {
			eval("option"+(i+1)+"_mc.radio_opt"+(i+1))._visible = false;
			eval("option"+(i+1)+"_mc.check_opt"+(i+1))._visible = false;
		}
		//trace(eval("option"+(i+1)+"_mc.check_opt"+(i+1)));
	}
}
function validate() {
	if (sType == "MCSS") {
		//trace("the counter is "+counter+"  the right answer is "+correctAns);
		if (String(counter) == String(correctAns)) {
			tick_mc._visible = 1;
			tick_mc.gotoAndStop(2);
			tick_mc.feedback_txt.text = correctFeedback;
		} else {
			tick_mc._visible = 1;
			tick_mc.feedback_txt.text = incorrectFeedback;
			//eval("ans"+counter)._visible = 1;
			//eval("ans"+counter).gotoAndStop(2);
		}
		for (i=1; i<=count; i++) {
			eval("ans"+i)._visible = 1;
			eval("ans"+i).gotoAndStop("neg");
			//eval("option"+sresult[i]+"_mc.dis_mc").gotoAndStop(2);
		}
		//trace(sresult.sort());
		for (i=0; i<=count; i++) {
			eval("ans"+i)._visible = 1;
			eval("ans"+correctAns).gotoAndStop(1);
		}
	} else {
		// this is for the Multiple choce multiple select quiz 
		for (i=0; i<=count; i++) {
			if (eval("option"+i+"_mc.check_opt"+i).selected == true) {
				var temp = eval("option"+i+"_mc.check_opt"+i);
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
		//trace(userArray);
		//trace(sresult.length);
		if (scount == sresult.length && userArray.length == sresult.length) {
			feedback_txt.text = correctFeedback;
			tick_mc._visible = 1;
			tick_mc.gotoAndStop(2);
			tick_mc.feedback_txt.text = correctFeedback;
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
			feedback_txt.text = incorrectFeedback+" "+correctAns;
			tick_mc._visible = 1;
			tick_mc.feedback_txt.text = incorrectFeedback;
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
	}
	close_btn.enabled = 1;
	close_btn._alpha = 100;
	//this is the place where we have to check for the tick mark
	/*var tracker = 0;
																																									for (i=0; i<userArray.length; i++) {
																																										for (j=0; j<sresult.length; j++) {
																																											if (userArray[i] == sresult[j]) {
																																												tracker++;
																																											}
																																										}
																																									}
																																									for (i=0; i<userArray.length; i++) {
																																										trace(tracker);
																																										if (tracker != 0 && userArray.length<=sresult.length) {
																																											eval("ans"+userArray[i])._visible = 1;
																																										} else if (tracker == 0 && userArray.length<=sresult.length) {
																																											eval("ans"+userArray[i])._visible = 1;
																																											eval("ans"+userArray[i]).gotoAndStop(2);
																																										}
																																									}*/
	disable(sType);
	//unloadMovieNum(5);
}
function disable(svalue) {
	for (i=1; i<=count; i++) {
		//trace("option"+i+"_mc.radio_opt"+i);
		if (svalue == "MCSS") {
			eval("option"+i+"_mc.radio_opt"+i).useHandCursor = false;
			eval("option"+i+"_mc.radio_opt"+i).enabled = false;
			eval("option"+i+"_mc.radio_opt"+i)._visible = true;
			eval("option"+i+"_mc.check_opt"+i)._visible = false;
		} else {
			eval("option"+i+"_mc.check_opt"+i).useHandCursor = false;
			eval("option"+i+"_mc.check_opt"+i).enabled = false;
			eval("option"+i+"_mc.radio_opt"+i)._visible = false;
			eval("option"+i+"_mc.check_opt"+i)._visible = true;
		}
	}
}
submit_btn.onRelease = function() {
	validate();
	submit_btn.enabled = 0;
	submit_btn._alpha = 50;
};
