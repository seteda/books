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
/*function loadXML() {
	myData = new XML();
	myData.ignoreWhite = true;
	myData.load("cm_c01_t01/textdata/MCSS.xml");
	myData.onLoad = fnParseAssessment;
	delete loadXML;
}*/
function fnParseAssessment() {
	glnFirstChild = myData.childNodes[0].childNodes;
	//trace(glnFirstChild);
	for (var i = 0; i<glnFirstChild.length; i++) {
		questionText[i] = glnFirstChild[i].childNodes[0].firstChild.nodeValue;
		//trace(questionText[i]);
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
			eval("option"+(j+1)+"_mc.option"+(j+1)+"_txt").text = options[i][j];
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
	if (scounter == optionTrack) {
		submit_btn.enabled = 1;
		submit_btn._alpha = 100;
	}
}
function validate() {
	_level3.activityDone = true;
	if (String(userArray) == String(correctAns)) {
		tick_mc._visible = 1;
		tick_mc.gotoAndStop(2);
		tick_mc.feedback_txt.text = correctFeedback;
		correctCount++;
		var temp = Number(_level3.score_mc.rightCount);
		_level3.score_mc.rightCount = Number(temp)+Number(correctCount);
	} else {
		tick_mc._visible = 1;
		tick_mc.feedback_txt.text = incorrectFeedback;
		incorrectCount++;
		var temp = _level3.score_mc.wrongCount;
		_level3.score_mc.wrongCount = Number(temp)+Number(incorrectCount);
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
		trace("Hello I AM HERE");
	}
}
