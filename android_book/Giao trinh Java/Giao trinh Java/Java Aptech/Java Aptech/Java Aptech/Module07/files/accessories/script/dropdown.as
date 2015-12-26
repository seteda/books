stop();
var cnt;
var count = 0;
var sType;
var counter;
var scount = 0;
var correctCount = 0;
var incorrectCount = 0;
var userArray = [0];
var steps = new Array();
var headings = new Array();
var optiontab = new Array();
var correctFeedback = new Array();
var incorrectFeedback = new Array();
var right_Array = new Array();
var tempHolder = new Array();
var popTotal;
var correctAnswer = [0];
var sdata = new Array();
var n = 0;
var sresult = new Array();
var nextdrop;
var nexttarget;
var scoreCount = 0;
var xpos = step1_mc._x;
var ypos = step1_mc._y;
dropval_array = [0];
dragval1_array = [0];
dragval2_array = [0];
var questionText = new Array();
submit_btn.enabled = 0;
submit_btn._alpha = 50;
view_btn.enabled = 0;
view_btn._alpha = 50;
close_btn.enabled = 0;
close_btn._alpha = 30;
disable_btn.enabled = false;
disable_btn.useHandCursor = false;
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
	for (var i = 0; i<glnFirstChild.length; i++) {
		questionText[i] = glnFirstChild[i].childNodes[0].firstChild.nodeValue;
		//trace(glnFirstChild[i].childNodes[5].childNodes[i].firstChild.nodeValue);
		headings.push(glnFirstChild[i].childNodes[1].childNodes[0].firstChild.nodeValue);
		description_txt.text = headings;
		answer_mc.description_txt.text = headings;
		//steps.push(glnFirstChild[i].childNodes[2].childNodes[0].firstChild.nodeValue);
		step_txt.text = glnFirstChild[i].childNodes[1].childNodes[1].firstChild.nodeValue;
		answer_mc.step_txt.text = glnFirstChild[i].childNodes[1].childNodes[1].firstChild.nodeValue;
		for (k=0; k<glnFirstChild[i].childNodes[3].childNodes.length; k++) {
			// this is where the right hand side ansers are given are retrived
			//trace(glnFirstChild[i].childNodes[3].childNodes[k].firstChild.nodeValue);
			steps.push(glnFirstChild[i].childNodes[3].childNodes[k].firstChild.nodeValue);
		}
		//trace(questionText[i]);
		question_txt.text = questionText[i];
		correctAns[i] = new Array();
		//tempHolder[k] = new Array();
		correctAns[0] = 0;
		correctAnswer[i+1] = glnFirstChild[i].childNodes[5].firstChild.nodeValue.split(",");
		//tempHolder[k] = glnFirstChild[i].childNodes[5].firstChild.nodeValue.split(",");
		//trace(correctAnswer.length);
		sdata[i] = correctAnswer[i];
		for (j=0; j<correctAnswer.length; j++) {
			sresult[j] = correctAnswer[j];
		}
		sresult = sresult.splice(1);
		correctFeedback[i] = glnFirstChild[i].childNodes[6].firstChild.nodeValue;
		incorrectFeedback[i] = glnFirstChild[i].childNodes[7].firstChild.nodeValue;
		//trace(correctFeedback);
		//trace(incorrectFeedback);
		sType = glnFirstChild[i].childNodes[2].firstChild.nodeValue;
		options[i] = new Array();
		count = glnFirstChild[i].childNodes[1].childNodes.length;
		n = glnFirstChild[i].childNodes[2].childNodes.length;
		for (var j = 0; j<=glnFirstChild[i].childNodes[2].childNodes.length-1; j++) {
			optiontab[j] = glnFirstChild[i].childNodes[2].childNodes[j].firstChild.nodeValue;
			//trace(optiontab[j]);
			eval("option"+(j+1)+"_mc")._visible = 1;
			eval("option"+(j+1)+"_mc.option"+(j+1)+"_txt").text = optiontab[j];
			eval("correctAnswer_mc.option"+(j+1)+"_mc.option"+(j+1)+"_txt").text = optiontab[j];
			eval("heading"+(j+1)+"_txt").text = headings[j];
		}
		for (j=0; j<=optiontab.length; j++) {
			for (i=0; i<steps.length; i++) {
				eval("step"+j+"_mc")._visible = 1;
				if (eval("step"+j+"_mc.combo1").getItemAt(i+1) == undefined) {
					eval("step"+j+"_mc.combo1").addItem(steps[i], i);
					eval("correctAnswer_mc.step"+j+"_mc.combo1").addItem(steps[i], i);
				}
			}
		}
	}
	//trace("the value of steps is "+steps.length);
	handcursor(sType);
	/*delete glnFirstChild;
																																																																				//
																																																																						
																																																																																																																																																																			delete myData;
																																																																																																																																																																		delete fnParseAssessment;*/
	inti();
}
//--------------------------------------------------------------------------------from this line the quiz code starts
loadXML();
function inti() {
	var combochange = new Object();
	combochange.change = function(eventObj) {
		submit_btn.enabled = false;
		submit_btn._alpha = 50;
		count = 0;
		for (i=1; i<=optiontab.length; i++) {
			if (eval("step"+i+"_mc.combo1").getSelectedIndex()<>0) {
				userArray[i] = eval("step"+i+"_mc.combo1").getSelectedIndex();
				count++;
			}
			trace(eval("step"+i+"_mc.combo1").getSelectedIndex());
		}
		if (count != 0) {
			submit_btn.enabled = true;
			submit_btn._alpha = 100;
		} else {
			submit_btn.enabled = false;
			submit_btn._alpha = 50;
		}
	};
	for (i=1; i<=optiontab.length; i++) {
		eval("step"+i+"_mc.combo1").enabled = true;
		eval("step"+i+"_mc.combo1").addEventListener("change", combochange);
	}
	function disablechoices() {
		for (i=1; i<=optiontab.length; i++) {
			//eval("step"+i+"_mc.combo1").close();
			disable_btn._x = 725.7;
			disable_btn._y = 252.6;
		}
	}
	function validate() {
		_level0.MCQ_fnShowNext();
		submit_btn.enabled = false;
		submit_btn._alpha = 50;
		var rightstr = correctAnswer.toString();
		
		right_Array = rightstr.split(",");
		trace(right_Array+" ============= the correct answer");
		if (String(userArray) == String(correctAnswer)) {
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
			feedback_mc._visible = 1;
			feedback_mc.gotoAndStop(1);
			feedback_mc.feedback_txt.text = incorrectFeedback;
			view_btn.enabled = true;
			view_btn._alpha = 100;
			incorrectCount++;
			var temp = Math.floor(Number(_level0.slide_mc.score_mc.wrongCount));
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
		/*step1_mc.combo1.addItemAt(0, "new label");
														step2_mc.combo1.addItemAt(0, "new label");
														step3_mc.combo1.addItemAt(0, "new label");
														step4_mc.combo1.addItemAt(0, "new label");*/
		for (i=0; i<=right_Array.length; i++) {
			if (userArray[i] == right_Array[i]) {
				eval("ans"+i)._visible = 1;
				eval("ans"+i).gotoAndStop(1);
			} else {
				eval("ans"+i)._visible = 1;
				eval("ans"+i).gotoAndStop("neg");
			}
		}
		//trace("the user has selected "+userArray);feedback_txtoption1_mc
	}
	submit_btn.onRelease = function() {
		validate();
		disablechoices();
	};
	view_btn.onRelease = function() {
		view_btn.enabled = 0;
		view_btn._alpha = 50;
		showAnswer();
	};
}
function showAnswer() {
	this.correctAnswer_mc._visible = true;
	for (j=0; j<=optiontab.length; j++) {
		eval("correctAnswer_mc.option"+j+"_mc")._visible = 1;
		eval("correctAnswer_mc.step"+j+"_mc")._visible = 1;
		eval("correctAnswer_mc.step"+j+"_mc.combo1").removeAll();
	}
	for (j=0; j<optiontab.length; j++) {
		eval("correctAnswer_mc.step"+(j+1)+"_mc")._visible = 1;
		var temp = right_Array[j+1];
		trace("yes it is true"+temp);
		eval("correctAnswer_mc.step"+(j+1)+"_mc.combo1").addItemAt(0, steps[temp-1]);
	}
}
