public class GenericPromptDialog extends ManagedActivityDialog
{
   private String mPromptMessage = null;
   private View promptView = null;
   String promptValue = null;
   private Context ctx = null;

   public GenericPromptDialog(ManagedDialogsActivity inActivity,
                                 int dialogId,
                                 String promptMessage)
   {
      super(inActivity,dialogId);
      mPromptMessage = promptMessage;
      ctx = inActivity;
   }
   public Dialog create()
   {
      LayoutInflater li = LayoutInflater.from(ctx);
      promptView = li.inflate(R.layout.promptdialog, null);
      AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
      builder.setTitle("prompt");
      builder.setView(promptView);
      builder.setPositiveButton("OK", this);
      builder.setNegativeButton("Cancel", this);
      AlertDialog ad = builder.create();
      return ad;
   }
   public void prepare(Dialog dialog)
   {
      //nothing for now
   }
   public void onClickHook(int buttonId)
   {
      if (buttonId == DialogInterface.BUTTON1)
      {
      //ok button
      String promptValue = getEnteredText();
      }
   }
   private String getEnteredText()
   {
      EditText et =
      (EditText)
      promptView.findViewById(R.id.editText_prompt);

      String enteredText = et.getText().toString();
      Log.d("xx",enteredText);
      return enteredText;
   }
}

