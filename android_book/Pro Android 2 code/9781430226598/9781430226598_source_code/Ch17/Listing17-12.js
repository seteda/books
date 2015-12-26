function replaceAParagraph(newText)
{
	//locate the HTML element with an ID
	//it returns an array of matching elements
	var myParagraph = $("#MyParagraphID")[0];

	//read the old HTML from the element
	var oldText = myParagraph.html();

	//replace it with the new
	myParagraph.html(newText);

	//or simpler format
	$("#MyParagraphID").html(newText);

	//change the style of that element
	$("#MyParagraphID").css("color:red;");

	//hide the element
	$("#MyParagraphID").hide();
}
