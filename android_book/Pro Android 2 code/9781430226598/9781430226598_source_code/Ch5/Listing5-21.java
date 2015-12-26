public class Alerts
{
   public static void showAlert(String message, Context ctx)
   {
      //Create a builder
      AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
      builder.setTitle("Alert Window");

      //add buttons and listener
      PromptListener pl = new EmptyListener();
      builder.setPositiveButton("OK", pl);

      //Create the dialog
      AlertDialog ad = builder.create();

      //show
      ad.show();
   }
}

public class EmptyListener
implements android.content.DialogInterface.OnClickListener 
{
   public void onClick(DialogInterface v, int buttonId)
   {
   }
}


