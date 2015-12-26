public class Alerts
{
   public static String prompt(String message, Context ctx)
   {
      //load some kind of a view
      LayoutInflater li = LayoutInflater.from(ctx);
      View view = li.inflate(R.layout.promptdialog, null);

      //get a builder and set the view
      AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
      builder.setTitle("Prompt");
      builder.setView(view);

      //add buttons and listener
      PromptListener pl = new PromptListener(view,ctx);
      builder.setPositiveButton("OK", pl);
      builder.setNegativeButton("Cancel", pl);

      //get the dialog
      AlertDialog ad = builder.create();

      //show
      ad.show();
      return pl.getPromptReply();
   }
}


