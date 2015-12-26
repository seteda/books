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
			eval("heading"+(j+1)+"_txt").text = headings[j];
		}
		for (j=0; j<=optiontab.length; j++) {
			for (i=0; i<steps.length; i++) {
				eval("step"+j+"_mc")._visible = 1;
				if (eval("step"+j+"_mc.combo1").getItemAt(i+1) == undefined) {
					eval("step"+j+"_mc.combo1").addItem(steps[i], i);
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
			eval("step"+i+"_mc.combo1").enabled = false;
		}
	}
	function validate() {
		_level3.activityDone = true;
		submit_btn.enabled = false;
		submit_btn._alpha = 50;
		disablechoices();
		var rightstr = correctAnswer.toString();
		var right_Array = new Array();
		right_Array = rightstr.split(",");
		trace(right_Array);
		if (String(userArray) == String(correctAnswer)) {
			feedback_mc._visible = 1;
			feedback_mc.gotoAndStop(2);
			feedback_mc.feedback_txt.text = correctFeedback;
			correctCount++;
			var temp = Number(_level3.score_mc.rightCount);
			_level3.score_mc.rightCount = Number(temp)+Number(correctCount);
			//trace("you have got it right:"+(temp+correctCount));
		} else {
			trace("you have got it wrong:");
			feedback_mc._visible = 1;
			feedback_mc.gotoAndStop(1);
			feedback_mc.feedback_txt.text = incorrectFeedback;
			incorrectCount++;
			var temp = _level3.score_mc.wrongCount;
			_level3.score_mc.wrongCount = Number(temp)+Number(incorrectCount);
			//trace("you have got it wrong:"+eval(temp+incorrectCount));
		}
		for (i=0; i<=right_Array.length; i++) {
			if (userArray[i] == right_Array[i]) {
				eval("ans"+i)._visible = 1;
				eval("ans"+i).gotoAndStop(1);
			} else {
				eval("ans"+i)._visible = 1;
				eval("ans"+i).gotoAndStop("neg");
			}
		}
		//trace("the user has selected "+userArray);feedback_txt
	}
	submit_btn.onRelease = function() {
		validate();
	};
}
