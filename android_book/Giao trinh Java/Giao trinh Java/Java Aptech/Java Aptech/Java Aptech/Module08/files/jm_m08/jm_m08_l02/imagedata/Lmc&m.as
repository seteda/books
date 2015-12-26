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
var correctFeedback;
var incorrectFeedback;
var tempHolder = new Array();
var popTotal;
var correctAnswer = [0];
var sdata = new Array();
var n = 0;
var sresult = new Array();
var nextdrop;
var nexttarget;
var scoreCount = 0;
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
		/*for (k=0; k<glnFirstChild[i].childNodes[1].childNodes.length; k++) {
																																																																									// this is where the heading title are retrived
																																																																									//trace(glnFirstChild[i].childNodes[1].childNodes[k].firstChild.nodeValue); 
																																																																									headings.push(glnFirstChild[i].childNodes[1].childNodes[0].firstChild.nodeValue);
																																																																									description_txt.text = headings;
																																																																								}*/
		for (k=0; k<glnFirstChild[i].childNodes[3].childNodes.length; k++) {
			// this is where the right hand side ansers are given are retrived
			//trace(glnFirstChild[i].childNodes[3].childNodes[k].firstChild.nodeValue); 
			steps.push(glnFirstChild[i].childNodes[3].childNodes[k].firstChild.nodeValue);
			//step_txt.text = steps;
		}
		trace(questionText[i]);
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
		correctFeedback = glnFirstChild[i].childNodes[6].firstChild.nodeValue;
		incorrectFeedback = glnFirstChild[i].childNodes[7].firstChild.nodeValue;
		trace(correctFeedback);
		trace(incorrectFeedback);
		sType = glnFirstChild[i].childNodes[2].firstChild.nodeValue;
		options[i] = new Array();
		count = glnFirstChild[i].childNodes[1].childNodes.length;
		n = glnFirstChild[i].childNodes[2].childNodes.length;
		//trace("the value of n is "+n);
		for (var j = 0; j<=glnFirstChild[i].childNodes[2].childNodes.length-1; j++) {
			optiontab[j] = glnFirstChild[i].childNodes[2].childNodes[j].firstChild.nodeValue;
			//trace(optiontab[j]);
			eval("option"+(j+1)+"_mc")._visible = 1;
			eval("option"+(j+1)+"_mc.option"+(j+1)+"_txt").text = optiontab[j];
			eval("heading"+(j+1)+"_txt").text = headings[j];
			eval("step"+(j+1)+"_mc")._visible = 1;
			eval("step"+(j+1)+"_mc.step_"+(j+1)).text = steps[j];
			eval("source"+(j+1))._visible = 1;
			eval("match"+(j+1))._visible = 1;
			eval("answer_mc.option"+(j+1)+"_mc")._visible = 1;
			eval("answer_mc.option"+(j+1)+"_mc.option"+(j+1)+"_txt").text = optiontab[j];
			eval("answer_mc.heading"+(j+1)+"_txt").text = headings[j];
			eval("answer_mc.step"+(j+1)+"_mc")._visible = 1;
			eval("answer_mc.step"+(j+1)+"_mc.step_"+(j+1)).text = steps[j];
			//trace(glnFirstChild[i].childNodes[2].childNodes[j].firstChild.nodeValue);
		}
	}
	handcursor(sType);
	/*delete glnFirstChild;
																																																																																																															delete myData;
																																																																																																															delete fnParseAssessment;*/
	inti();
}
//--------------------------------------------------------------------------------from this line the quiz code starts
loadXML();
function inti() {
	for (i=0; i<=n; i++) {
		eval("match"+i).enabled = false;
	}
	line_array = new Array();
	for (j=0; j<=n; j++) {
		line_array[j] = 0;
	}
	corr = 0;
	for (i=1; i<=n; i++) {
		eval("source"+i).onPress = function() {
			for (j=1; j<=n; j++) {
				eval("match"+j).enabled = true;
			}
			initial = ((this._name).substring(6));
		};
		eval("match"+i).onPress = function() {
			submit_btn.enabled = true;
			submit_btn._alpha = 100;
			final = ((this._name).substring(5));
			//trace(final)
			//trace(line_array[final]);
			eval("line"+initial).gotoAndStop(Number(final)+1);
			line_array[initial] = final;
			trace(line_array);
		};
	}
	submit_btn.onPress = function() {
		submit_btn.enabled = false;
		submit_btn._alpha = 50;
		_level3.activityDone = true;
		var userValues = line_array.toString();
		var rightValue = correctAnswer.toString();
		if (userValues == rightValue) {
			feedback_mc._visible = 1;
			feedback_mc.gotoAndStop(2);
			feedback_mc.feedback_txt.text = correctFeedback;
			correctCount++;
			var temp = Number(_level3.score_mc.rightCount);
			_level3.score_mc.rightCount = Number(temp)+Number(correctCount);
		} else {
			feedback_mc._visible = 1;
			feedback_mc.gotoAndStop(1);
			feedback_mc.feedback_txt.text = incorrectFeedback;
			incorrectCount++;
var temp = _level3.score_mc.wrongCount;
_level3.score_mc.wrongCount = Number(temp)+Number(incorrectCount);
		}
		//trace(corr);
		for (i=0; i<n; i++) {
			eval("match"+i).enabled = false;
			eval("source"+i).enabled = false;
		}
	};
}
