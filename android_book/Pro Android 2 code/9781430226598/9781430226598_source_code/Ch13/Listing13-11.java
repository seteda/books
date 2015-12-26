//filename: /src/…/APrefWidgetModel.java
public abstract class APrefWidgetModel
implements IWidgetModelSaveContract
{
   private static String tag = "AWidgetModel";
   public int iid;

   public APrefWidgetModel(int instanceId) {
      iid = instanceId;
   }

   //abstract methods
   public abstract String getPrefname();
   public abstract void init();
   public Map<String,String> getPrefsToSave(){ return null;}

   public void savePreferences(Context context){
      Map<String,String> keyValuePairs = getPrefsToSave();
      if (keyValuePairs == null){
         return;
      }
      //going to save some values
      SharedPreferences.Editor prefs =
         context.getSharedPreferences(getPrefname(), 0).edit();

      for(String key: keyValuePairs.keySet()){
         String value = keyValuePairs.get(key);
         savePref(prefs,key,value);
      }
      //finally commit the values
      prefs.commit();
   }

   private void savePref(SharedPreferences.Editor prefs,
                     String key, String value) {
      String newkey = getStoredKeyForFieldName(key);
      prefs.putString(newkey, value);
   }

   private void removePref(SharedPreferences.Editor prefs, String key) {
      String newkey = getStoredKeyForFieldName(key);
      prefs.remove(newkey);
   }

   protected String getStoredKeyForFieldName(String fieldName){
      return fieldName + "_" + iid;
   }
   public static void clearAllPreferences(Context context, String prefname) {
      SharedPreferences prefs=context.getSharedPreferences(prefname, 0);
      SharedPreferences.Editor prefsEdit = prefs.edit();
      prefsEdit.clear();
      prefsEdit.commit();
   }

   public boolean retrievePrefs(Context ctx) {
      SharedPreferences prefs = ctx.getSharedPreferences(getPrefname(), 0);
      Map<String,?> keyValuePairs = prefs.getAll();
      boolean prefFound = false;

      for (String key: keyValuePairs.keySet()){
         if (isItMyPref(key) == true){
            String value = (String)keyValuePairs.get(key);
            setValueForPref(key,value);
            prefFound = true;
         }
      }
      return prefFound;
   }

   public void removePrefs(Context context) {
      Map<String,String> keyValuePairs = getPrefsToSave();
      if (keyValuePairs == null){
         return;
      }
      //going to save some values
      SharedPreferences.Editor prefs =
            context.getSharedPreferences(getPrefname(), 0).edit();
      for(String key: keyValuePairs.keySet()){
         removePref(prefs,key);
      }
      //finally commit the values
      prefs.commit();
   }
   private boolean isItMyPref(String keyname) {
      if (keyname.indexOf("_" + iid) > 0){
         return true;
      }
      return false;
   }
   public void setValueForPref(String key, String value) {
      return;
   }
}
