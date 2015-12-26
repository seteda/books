//filename: src/…/IWidgetModelSaveContract.java
public interface IWidgetModelSaveContract
{
   public void setValueForPref(String key, String value);
   public String getPrefname();

   //return key value pairs you want to be saved
   public Map<String,String> getPrefsToSave();

   //gets called after restore
   public void init();
}
