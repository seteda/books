public class MainActivity extends ManagedDialogsActivity
{
   //dialog 1
   private GenericManagedAlertDialog gmad =
   new GenericManagedAlertDialog(this,1,"InitialValue");

   //dialog 2
   private GenericPromptDialog gmpd =
   new GenericPromptDialog(this,2,"InitialValue");

   //menu items to start the dialogs
   else if (item.getItemId() == R.id.menu_simple_alert)
   {
      gmad.show();
   }
   else if (item.getItemId() == R.id.menu_simple_prompt)
   {
      gmpd.show();
   }
   
   //dealing with call backs
   public void dialogFinished(ManagedActivityDialog dialog, int buttonId)
   {
      if (dialog.getDialogId() == gmpd.getDialogId())
      {
         String replyString = gmpd.getReplyString();
      }
   }
}


