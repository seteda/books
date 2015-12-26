public class PromptListener
implements android.content.DialogInterface.OnClickListener
{
   // local variable to return the prompt reply value
   private String promptReply = null;
   //Keep a variable for the view to retrieve the prompt value
   View promptDialogView = null;

   //Take in the view in the constructor
   public PromptListener(View inDialogView) 
   {
      promptDialogView = inDialogView;
   }

   //Call back method from dialogs
   public void onClick(DialogInterface v, int buttonId) 
   {
      if (buttonId == DialogInterface.BUTTON1) 
      {
         //ok button
         promptReply = getPromptText();
      }
      else 
      {
         //cancel button
         promptValue = null;
      }
   }
   
   //Just an access method for what is in the edit box
   private String getPromptText() 
   {
      EditText et = (EditText)
      promptDialogView.findViewById(R.id.promptEditTextControlId);
      return et.getText().toString();
   }

   public String getPromptReply() { return promptReply; }
}


