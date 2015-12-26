public class MySMSMonitor extends BroadcastReceiver
{
    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent!=null && intent.getAction()!=null && 
ACTION.compareToIgnoreCase(intent.getAction())==0)
        {
            Object[]pduArray= (Object[]) intent.getExtras().get("pdus");
            SmsMessage[] messages = new SmsMessage[pduArray.length];
            for (int i = 0; i<pduArray.length; i++) {
                    messages[i] = SmsMessage.createFromPdu ((byte[])pduArray [i]);
            }
            Log.d("MySMSMonitor","SMS Message Received.");
        }
    }
}

