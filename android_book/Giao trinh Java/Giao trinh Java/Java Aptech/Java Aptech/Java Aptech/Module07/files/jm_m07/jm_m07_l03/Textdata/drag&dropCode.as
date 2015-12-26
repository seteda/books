stop();
trace("yes");
var cnt;
var count = 0;
var sType;
var counter;
var scount = 0;
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
var right_Array = new Array();
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
		answer_mc.description_txt.htmlText = headings;
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
		correctFeedback[i] = glnFirstChild[i].childNodes[6].firstChild.nodeValue;
		incorrectFeedback[i] = glnFirstChild[i].childNodes[7].firstChild.nodeValue;
		trace(correctFeedback);
		trace(incorrectFeedback);
		sType = glnFirstChild[i].childNodes[2].firstChild.nodeValue;
		options[i] = new Array();
		count = glnFirstChild[i].childNodes[1].childNodes.length;
		n = glnFirstChild[i].childNodes[2].childNodes.length;
		for (var j = 0; j<=glnFirstChild[i].childNodes[2].childNodes.length-1; j++) {
			optiontab[j] = glnFirstChild[i].childNodes[2].childNodes[j].firstChild.nodeValue;
			trace(steps[j]);
			eval("option"+(j+1)+"_mc")._visible = 1;
			eval("option"+(j+1)+"_mc.option"+(j+1)+"_txt").text = optiontab[j];
			eval("option"+(j+1)+"_txt").text = optiontab[j];
			eval("heading"+(j+1)+"_txt").text = headings[j];
			eval("drag"+(j+1)+"_mc")._visible = 1;
			eval("dragBase"+(j+1)+"_mc")._visible = 1;
			eval("drag"+(j+1)+"_mc.step_"+(j+1)).htmlText = steps[j];
			eval("drop"+(j+1)+"_mc")._visible = 1;
			eval("drag"+(j+1)+"_mc")._visible = 1;
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
	//trace("the value of n is "+n);
	for (var i = 1; i<=n; i++) {
		eval("option"+i+"_txt").text = eval("option"+i);
		eval("step"+i+"_txt").text = eval("step"+i);
		var drop_y = eval("drop"+i+"_mc")._y;
		var drag_x = eval("drag"+i+"_mc")._x;
		var drag_y = eval("drag"+i+"_mc")._y;
		dropval_array.push(drop_y);
		dragval1_array.push(drag_x);
		dragval2_array.push(drag_y);
	}
	//to track the result
	counter = 0;
	lc_txt.text = lc;
	rc_txt.text = rc;
	//to check whether any object exist in drop1_mc and to note initial position of draggable objects
	for (i=1; i<=n; i++) {
		eval("drop"+i+"_mc").exist = false;
		eval("drag"+i+"_mc").initx = eval("drag"+i+"_mc")._x;
		eval("drag"+i+"_mc").inity = eval("drag"+i+"_mc")._y;
		eval("drag"+i+"_mc").trgt = "none";
		eval("drop"+i+"_mc").existItem = "none";
	}
	//to track whether any object is placed on drop targets or not
	//val= 0;
	for (i=1; i<=n; i++) {
		eval("drag"+i+"_mc").onPress = function() {
			this.startDrag(false, 100, 270, 537, 494);
			//this.startDrag(false);
			mx.behaviors.DepthControl.bringToFront(this);
			//gives the draggable object's number
			this.gotoAndStop(2);
			dragnum = this._name.substr(4, 1);
			for (j=1; j<=n; j++) {
				if (this._y == dropval_array[j]) {
					eval("drop"+j+"_mc").exist = false;
				}
			}
		};
		eval("drag"+i+"_mc").onRelease = this.releasecheck;
		eval("drag"+i+"_mc").onReleaseOutside = this.releasecheck;
	}
}
function releasecheck() {
	//submit_btn.enabled = true;
	//submit_btn._alpha = 100;
	this.stopDrag();
	this.gotoAndStop(1);
	tar = String(this._droptarget);
	holder = String(this._name);
	hlastIndex = holder.substring(4, 5);
	//trace(hlastIndex);
	lastindex = tar.lastIndexOf("drop");
	//gives dropped object's number
	dropval = Number(tar.substring(lastindex+4, lastindex+5));
	userArray[dropval] = hlastIndex;
	if (tar.substring(lastindex, lastindex+4) == "drop") {
		switch (dropval) {
		case dropval :
			if (eval("drop"+dropval+"_mc").exist == false) {
				//this.gotoAndStop(2);
				//eval("drop"+dropval+"_mc").existItem = this;
				eval("drop"+dropval+"_mc").exist = true;
			} else {
				trace("0b");
				//this.trgt = eval("drop"+dropval+"_mc");
				eval("drop"+dropval+"_mc").existItem._x = eval("drop"+dropval+"_mc").existItem.initx;
				eval("drop"+dropval+"_mc").existItem._y = eval("drop"+dropval+"_mc").existItem.inity;
				eval("drop"+dropval+"_mc").existItem.trgt = "none";
				//this._x = eval("drop"+dropval+"_mc")._x;
				//this._y = eval("drop"+dropval+"_mc")._y;
				//eval("drop"+dropval+"_mc").existItem = this;
				//this.gotoAndStop(1);
				userArray[dropval] = hlastIndex;
				trace("hello the the userArray"+userArray);
			}
			this._x = eval(tar)._x;
			this._y = eval(tar)._y;
			this.trgt = eval(tar);
			eval(tar).existItem = this;
			/*//dropval = Number(tar.substring(lastindex+4, lastindex+5));
																																																																																																																																																																																																																																																																																																																																																																																																									userArray[dropval] = hlastIndex;
																																																																																																																																																																																																																																																																																																																																																																																																									trace("hello the the userArray"+userArray);*/
			break;
		default :
			this.trgt = "none";
			this._x = eval("drag"+dragnum+"_mc").initx;
			this._y = eval("drag"+dragnum+"_mc").inity;
			this.gotoAndStop(1);
		}
	} else if (eval(tar)._name == "num") {
		trace("1");
		if (eval(tar)._parent.trgt != "none") {
			trace("1a");
			var drgnum = eval(tar).text;
			this._x = eval("drag"+drgnum+"_mc")._x;
			this._y = eval("drag"+drgnum+"_mc")._y;
			this.trgt = eval("drag"+drgnum+"_mc").trgt;
			this.trgt.existItem = this;
			this.trgt.exit = true;
			eval("drag"+drgnum+"_mc")._x = eval("drag"+drgnum+"_mc").initx;
			eval("drag"+drgnum+"_mc")._y = eval("drag"+drgnum+"_mc").inity;
			eval("drag"+drgnum+"_mc").trgt = "none";
		} else {
			trace("1b");
			this._x = this.initx;
			this._y = this.inity;
			this.trgt = "none";
			this.trgt.existItem = undefined;
		}
		//eval("drag"+drgnum+"_mc").dropped = false;
		//this.gotoAndStop(1);
	} else if ((eval(tar)._name == "dBase")) {
		nextdrop = tar.substring(5, 6);
		var stemps = this._name;
		nexttarget = stemps.substring(4, 5);
		//trace("this is the drag object"+nexttarget);
		//trace(nextdrop+"this is the thing ht i want");
		if (eval(tar)._parent.trgt != "none") {
			trace("2a");
			userArray[nextdrop] = nexttarget;
			userArray[nexttarget] = 0;
			userArray[0] = 0;
			//trace("hello the the userArray"+userArray); this is here
			var drgnum = eval(tar).text;
			this.trgt = eval(tar)._parent.trgt;
			this.trgt.existItem = this;
			this.trgt.exist = true;
			this._x = eval(tar)._parent.trgt._x;
			this._y = eval(tar)._parent.trgt._y;
			eval(tar)._parent._x = eval(tar)._parent.initx;
			eval(tar)._parent._y = eval(tar)._parent.inity;
			eval(tar)._parent.trgt = "none";
			//userArray[dropval] = hlastIndex;
			//trace("hello the the userArray"+userArray);
		} else {
			trace("2b");
			this._x = this.initx;
			this._y = this.inity;
			this.trgt.exist = false;
			this.trgt.existItem = undefined;
		}
		//this.gotoAndStop(1);
	} else {
		trace("3");
		this.trgt = "none";
		//this.dropped = false;
		this._x = this.initx;
		this._y = this.inity;
		this.trgt.existItem = undefined;
		this.gotoAndStop(1);
		userArray[0] = 0;
		userArray[hlastIndex] = 0;
		//trace("hello the the userArray"+userArray);
	}
	for (j=1; j<=n; j++) {
		myX = eval("drop"+j+"_mc")._x;
		if (this._y == dropval_array[j] && this._x == myX) {
			eval("drop"+j+"_mc").exist = true;
		}
	}
	count = 0;
	for (i=1; i<=n; i++) {
		for (j=1; j<=n; j++) {
			if (eval("drag"+i+"_mc").hitTest(eval("drop"+j+"_mc"))) {
				//trace("count "+count);
				count = count+1;
			}
		}
	}
	//trace(count);
	if (count == n) {
		submit_btn.enabled = true;
		submit_btn._alpha = 100;
	} else {
		submit_btn.enabled = false;
		submit_btn._alpha = 50;
	}
}
//This is where the submit function is written //
submit_btn.onRelease = function() {
	trace("submit action done");
	submit_btn.enabled = 0;
	submit_btn._alpha = 50;
	close_btn.enabled = 1;
	close_btn._alpha = 100;
	var rightstr = correctAnswer.toString();
	right_Array = rightstr.split(",");
	trace(right_Array.length);
	popTotal = right_Array.length;
	for (i=1; i<=n; i++) {
		eval("drag"+i+"_mc").enabled = 0;
		eval("drag"+i+"_mc")._alpha = 50;
	}
	if (String(userArray) == String(correctAnswer)) {
		scoreCount++;
		trace("yes all r right"+i);
	}
	if (scoreCount == 1) {
		tick_mc._visible = 1;
		tick_mc.gotoAndStop(2);
		tick_mc.feedback_txt.htmlText = "Click on the <b>Close</b> button to continue.";
	} else {
		tick_mc._visible = 1;
		tick_mc.feedback_txt.htmlText = incorrectFeedback[quizCounter-1]+" "+sresult+"  Click on the <b>Close</b> button to continue.";
		view_btn.enabled = 1;
		view_btn._alpha = 100;
		trace("this is wrong");
		//gotoAndStop("feedback");
		feedback_mc._visible = 1;
		displayAnswer();
	}
	for (i=0; i<=right_Array.length-1; i++) {
		if (userArray[i] == right_Array[i]) {
			eval("ans"+i)._visible = 1;
			eval("ans"+i).gotoAndStop(1);
		} else {
			eval("ans"+i)._visible = 1;
			eval("ans"+i).gotoAndStop("neg");
		}
		eval("answer_mc.ans"+(i))._visible = 1;
		eval("answer_mc.ans"+i+".corr_"+i).text = right_Array[i];
		displayAnswer();
	}
};
view_btn.onRelease = function() {
	trace("yes ");
	var temps = this.getDepth();
	trace("yes "+temps);
	for (i=0; i<=(popTotal-1); i++) {
		eval("drag"+i+"_mc").enabled = 0;
		eval("drag"+i+"_mc")._alpha = 0;
		eval("drag"+i+"_mc")._visible = 0;
	}
	answer_mc._visible = 1;
	answer_mc._alpha = 100;
	answer_mc.swapDepths(100);
	view_btn.enabled = 0;
	view_btn._alpha = 50;
};
function displayAnswer() {
	for (var j = 0; j<=right_Array.length; j++) {
		trace(steps.length+" the array is "+right_Array[j+1]);
		//eval("feedback_mc.option"+(j+1)+"_mc")._visible = 1;
		var temp = right_Array[j+1];
		eval("feedback_mc.option"+(j+1)+"_mc.option"+(j+1)+"_txt").htmlText = steps[temp-1];
		eval("tick_mc.wrongFeedback_txt").text = incorrectFeedback;
		eval("tick_mc.correctFeedback_txt").text = correctFeedback;
	}
}
