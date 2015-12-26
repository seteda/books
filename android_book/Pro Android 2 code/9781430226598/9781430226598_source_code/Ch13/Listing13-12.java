//filename: /src/…/BDayWidgetModel.java
public class BDayWidgetModel extends APrefWidgetModel
{
   private static String tag="BDayWidgetModel";

   // Provide a unique name to store date
   private static String BDAY_WIDGET_PROVIDER_NAME=
                  "com.ai.android.BDayWidget.BDayWidgetProvider";

   // Variables to paitn the widget view
   private String name = "anon";
   private static String F_NAME = "name";
   private String bday = "1/1/2001";
   private static String F_BDAY = "bday";
   private String url="http://www.google.com";

   // Constructor/gets/sets
   public BDayWidgetModel(int instanceId){
      super(instanceId);
   }
   public BDayWidgetModel(int instanceId, String inName, String inBday){
      super(instanceId);
      name=inName;
      bday=inBday;
   }
   public void init(){}
   public void setName(String inname){name=inname;}
   public void setBday(String inbday){bday=inbday;}
   public String getName(){return name;}
   public String getBday(){return bday;}

   public long howManyDays(){
      try {
         return Utils.howfarInDays(Utils.getDate(this.bday));
      }
      catch(ParseException x){
         return 20000;
      }
   }

   //Implement save contract
   public void setValueForPref(String key, String value){
      if (key.equals(getStoredKeyForFieldName(BDayWidgetModel.F_NAME))){
         this.name = value;
         return;
      }
      if (key.equals(getStoredKeyForFieldName(BDayWidgetModel.F_BDAY))){
         this.bday = value;
         return;
      }
   }

   public String getPrefname() {
      return BDayWidgetModel.BDAY_WIDGET_PROVIDER_NAME;
   }

   //return key value pairs you want to be saved
   public Map getPrefsToSave() {
      Map map   = new HashMap();
      map.put(BDayWidgetModel.F_NAME, this.name);
      map.put(BDayWidgetModel.F_BDAY, this.bday);
      return map;
   }

   public String toString() {
      StringBuffer sbuf = new StringBuffer();
      sbuf.append("iid:" + iid);
      sbuf.append("name:" + name);
      sbuf.append("bday:" + bday);
      return sbuf.toString();
   }
   public static void clearAllPreferences(Context ctx){
      APrefWidgetModel.clearAllPreferences(ctx,
                  BDayWidgetModel.BDAY_WIDGET_PROVIDER_NAME);
   }

   public static BDayWidgetModel retrieveModel(Context ctx, int widgetId){
      BDayWidgetModel m = new BDayWidgetModel(widgetId);
      boolean found = m.retrievePrefs(ctx);
      return found ? m:null;
   }
}
