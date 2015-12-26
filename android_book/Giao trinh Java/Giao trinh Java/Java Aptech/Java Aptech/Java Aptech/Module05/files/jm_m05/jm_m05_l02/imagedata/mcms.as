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
var questionOption = new Array(0);
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
		question_txt.text = questionText[i];
		correctAns[i] = new Array();
		correctAns[i] = glnFirstChild[i].childNodes[4].firstChild.nodeValue.split(",");
		sdata = correctAns[i];
		for (j=0; j<sdata.length; j++) {
			sresult[j] = sdata[j];
		}
		//trace(sresult);
		correctFeedback[i] = glnFirstChild[i].childNodes[5].firstChild.nodeValue;
		incorrectFeedback[i] = glnFirstChild[i].childNodes[6].firstChild.nodeValue;
		sType = glnFirstChild[i].childNodes[2].firstChild.nodeValue;
		options[i] = new Array();
		count = glnFirstChild[i].childNodes[1].childNodes.length;
		//trace(correctFeedback);
		for (var j = 0; j<glnFirstChild[i].childNodes[1].childNodes.length; j++) {
			options[i][j] = glnFirstChild[i].childNodes[1].childNodes[j].firstChild.nodeValue;
			questionOption.push(options[i][j]);
			//trace(options[i][j]);
			eval("option"+(j)+"_mc")._visible = 1;
			eval("option"+(j)+"_mc.option"+(j)+"_txt").text = options[i][j];
		}
		steps = new Array();
		for (var j = 0; j<glnFirstChild[i].childNodes[2].childNodes.length; j++) {
			steps[j] = glnFirstChild[i].childNodes[2].childNodes[j].firstChild.nodeValue;
			trace(glnFirstChild[i].childNodes[2].childNodes[j].firstChild.nodeValue);
			if (steps[j] == undefined) {
				eval("step"+(j)+"_mc")._visible = 0;
			} else {
				eval("step"+(j)+"_mc")._visible = 1;
				eval("step"+(j)+"_mc.step"+(j)+"_txt").text = steps[j];
			}
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
	//trace(userArray);
	//trace(sresult.length);
	if (scount == sresult.length && userArray.length == sresult.length) {
		feedback_mc.feedback_txt.text = correctFeedback;
		feedback_mc._visible = 1;
		feedback_mc.gotoAndStop(2);
		feedback_mc.feedback_txt.text = correctFeedback;
		correctCount++;
		var temp = Number(_level3.score_mc.rightCount);
		_level3.score_mc.rightCount = Number(temp)+Number(correctCount);
	} else {
		feedback_mc.feedback_txt.text = incorrectFeedback+" "+correctAns;
		feedback_mc._visible = 1;
		feedback_mc.feedback_txt.text = incorrectFeedback;
		incorrectCount++;
		var temp = _level3.score_mc.wrongCount;
		_level3.score_mc.wrongCount = Number(temp)+Number(incorrectCount);
		displayAnswer();
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
function displayAnswer() {
	for (i=0; i<sresult.length; i++) {
		eval("step"+(i)+"_mc")._visible = 1;
		var temp = sresult[i]
		eval("step"+(i)+"_mc.step"+(i)+"_txt").text = questionOption[temp-1];
		trace(temp);
	}
}
submit_btn.onRelease = function() {
	validate();
	submit_btn.enabled = 0;
	submit_btn._alpha = 50;
};
