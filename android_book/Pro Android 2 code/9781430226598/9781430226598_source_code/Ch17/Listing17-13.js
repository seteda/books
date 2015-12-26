$("#MyElementID") // A specific id
$(".MyClass") //all elements matching this class
$("p") // all paragraphs
$("p.MyClass") //paragraphs with MyClass
$("div") // all divs
$(".MyClass1.MyClass2.MyClass3") // locate three classes
$("div,p,p.MyClass,#MyElementID") //matching all those

//Immediate children
$("#Main > *") // All children of Main
$("parent > child")

//Children and grand children
$("ancestor descendents")
$("form input") // all input fields in a form
$("label + input") // all inputs next to a label
$("prev + next")

//starting at myclass find siblings of type div
$(".myclass ~ div")
$("prev ~ next)
