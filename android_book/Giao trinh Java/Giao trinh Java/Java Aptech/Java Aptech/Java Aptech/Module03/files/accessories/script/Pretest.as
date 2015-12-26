stop();
var activity_Type;
var question_Array = new Array();
var question_txt = new Array();
var option_txt = new Array();
var userArray = new Array();
var totalOption;
var correct_Array;
var correct_Feedback;
var incorrect_Feedback;
var quizCounter = 0;
var correctCount = 0;
var incorrectCount = 0;
rightCount.text = "00";
wrongCount.text = "00";
nextQuestion_btn.enabled = 0;
nextQuestion_btn._alpha = 0;
next_btn.enabled = 0;
next_btn._alpha = 0;
submit_btn.enabled = 0;
submit_btn._alpha = 50;
// this is whre the xml file is loaded 
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
	var gnFirstChild = myData.childNodes[0].childNodes;
	for (i=0; i<gnFirstChild.length; i++) {
		question_Array.push(myData.childNodes[0].childNodes[i]);
	}
	//trace(question_Array.length);
	generateQuestion(quizCounter);
}
// this is function where the question are qenerate
function generateQuestion(sValue) {
	question_txt = question_Array[sValue].childNodes[0].firstChild;
	trace(question_Array.length);
	totalcount.text = "0"+question_Array.length;
	pagecount.text = "0"+(sValue+1);
	activity_Type = question_Array[sValue].childNodes[2].childNodes;
	correct_Array = question_Array[sValue].childNodes[3].childNodes;
	correct_Feedback = question_Array[sValue].childNodes[4].childNodes;
	incorrect_Feedback = question_Array[sValue].childNodes[5].childNodes;
	var option_txt = new Array();
	for (j=0; j<question_Array[sValue].childNodes[1].childNodes.length; j++) {
		option_txt.push(question_Array[sValue].childNodes[1].childNodes[j].firstChild);
		totalOption = option_txt.length;
		//trace(totalOption);
		eval("option"+(j)+"_mc")._visible = 1;
		eval("option"+(j)+"_mc.option"+(j)+"_txt").text = option_txt[j];
		if (String(activity_Type) == "MCSS") {
			eval("option"+j+"_mc.radio_opt"+(j+1)).useHandCursor = true;
			eval("option"+j+"_mc.radio_opt"+(j+1)).enabled = true;
			eval("option"+j+"_mc.radio_opt"+(j+1)).selected = false;
			eval("option"+j+"_mc.radio_opt"+(j+1))._visible = true;
			eval("option"+j+"_mc.check_opt"+(j+1))._visible = false;
		} else {
			eval("option"+j+"_mc.check_opt"+(j+1)).useHandCursor = true;
			eval("option"+j+"_mc.check_opt"+(j+1)).enabled = true;
			eval("option"+j+"_mc.check_opt"+(j+1)).selected = false;
			eval("option"+j+"_mc.radio_opt"+(j+1))._visible = false;
			eval("option"+j+"_mc.check_opt"+(j+1))._visible = true;
		}
	}
}
// this is the next button script ---------------------------------------------
nextQuestion_btn.onRelease = function() {
	quizCounter++;
	if (quizCounter == question_Array.length) {
		nextQuestion_btn.enabled = 0;
		nextQuestion_btn._alpha = 0;
		next_btn.enabled = 1;
		next_btn._alpha = 100;
	} else {
		closeAll();
		generateQuestion(quizCounter);
	}
};
next_btn.onRelease = function() {
	_level3.pretest = 2;
	gotoAndStop(3);
};
// The validation function for the code quiz--------------------------------------
submit_btn.onRelease = function() {
	_level0.MCQ_fnShowNext();
	var correctAnswer = new Array();
	var temp = String(correct_Array);
	for (i=0; i<temp.length; i++) {
		var scount = temp.substring(i, i+1);
		if (scount == ",") {
		} else {
			correctAnswer.push(scount);
		}
	}
	//trace(correctAnswer.length);
	if (String(activity_Type) == "MCSS") {
		if (String(counter) == String(correct_Array)) {
			tick_mc._visible = 1;
			tick_mc.gotoAndStop(2);
			tick_mc.feedback_txt.text = correct_Feedback;
			correctCount++;
			rightCount.text = "0"+correctCount;
		} else {
			tick_mc._visible = 1;
			tick_mc.feedback_txt.text = incorrect_Feedback;
			incorrectCount++;
			wrongCount.text = "0"+incorrectCount;
		}
		// this is for the tick marks
		for (j=0; j<=totalOption; j++) {
			eval("ans"+j)._visible = 1;
			eval("ans"+j).gotoAndStop("neg");
		}
		for (j=0; j<=totalOption; j++) {
			eval("ans"+j)._visible = 1;
			eval("ans"+correctAnswer).gotoAndStop(1);
		}
	} else {
		for (i=0; i<=totalOption; i++) {
			if (eval("option"+i+"_mc.check_opt"+(i+1)).selected == true) {
				var temp = eval("option"+i+"_mc.check_opt"+(i+1));
				//trace(temp);
				userArray.push(substring(temp, 42, -1));
				trace(substring(temp, 42, -1));
			}
		}
		//trace("this is th userArray"+userArray+" "+"the correct answer is "+correctAnswer);
		if (String(userArray) == String(correctAnswer)) {
			tick_mc._visible = 1;
			tick_mc.gotoAndStop(2);
			tick_mc.feedback_txt.text = correct_Feedback;
			correctCount++;
			rightCount.text = "0"+correctCount;
		} else {
			tick_mc._visible = 1;
			tick_mc.feedback_txt.text = incorrect_Feedback;
			incorrectCount++;
			wrongCount.text = "0"+incorrectCount;
		}
		for (j=0; j<=totalOption; j++) {
			eval("ans"+j)._visible = 1;
			eval("ans"+j).gotoAndStop("neg");
		}
		for (j=0; j<=totalOption; j++) {
			eval("ans"+j)._visible = 1;
			eval("ans"+correctAnswer[j]).gotoAndStop(1);
		}
	}
	submit_btn.enabled = 0;
	submit_btn._alpha = 50;
	if (quizCounter == question_Array.length-1) {
		nextQuestion_btn.enabled = 0;
		nextQuestion_btn._alpha = 0;
		nextQuestion_btn._x = 0;
		next_btn.enabled = 1;
		next_btn._alpha = 100;
		//gotoAndStop(3);
		trace("updating th value of pretest"+_level3.pretest);
	} else {
		nextQuestion_btn.enabled = 1;
		nextQuestion_btn._alpha = 100;
	}
	disable();
};
function disable() {
	for (i=0; i<=totalOption; i++) {
		//trace("option"+i+"_mc.radio_opt"+i);
		eval("option"+i+"_mc.radio_opt"+(i+1)).useHandCursor = false;
		eval("option"+i+"_mc.radio_opt"+(i+1)).enabled = false;
		eval("option"+i+"_mc.check_opt"+(i+1)).enabled = false;
		eval("option"+i+"_mc.check_opt"+(i+1)).useHandCursor = false;
	}
}
function closeAll() {
	for (i=0; i<=totalOption; i++) {
		eval("ans"+i)._visible = 0;
		eval("ans"+sresult[i]).gotoAndStop(1);
		eval("option"+i+"_mc")._visible = false;
		tick_mc._visible = 0;
		tick_mc.gotoAndStop(1);
	}
	userArray.splice(0, userArray.length);
	nextQuestion_btn.enabled = 0;
	nextQuestion_btn._alpha = 50;
}
