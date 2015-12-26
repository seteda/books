function hoverParagraph()
{
   $("p").hover(function () {
      $(this).css({'background-color' : 'yellow', 'font-weight' : 'bolder'});
      }, function () {
         var cssObj = {
         'background-color' : '#ddd',
         'font-weight' : '',
         'color' : 'rgb(0,40,244)'
      }
      $(this).css(cssObj);
   });
}
