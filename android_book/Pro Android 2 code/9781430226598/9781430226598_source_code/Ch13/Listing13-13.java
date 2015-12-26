public class Utils
{
   private static String tag = "Utils";

   public static Date getDate(String dateString)
   throws ParseException {
      DateFormat a = getDateFormat();
      Date date = a.parse(dateString);
      return date;
   }
   public static String test(String sdate){
      try {
         Date d = getDate(sdate);
         DateFormat a = getDateFormat();
         String s = a.format(d);
         return s;
      }
      catch(Exception x){
         return "problem with date:" + sdate;
      }
   }

   public static DateFormat getDateFormat(){
      SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
      //DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
      df.setLenient(false);
      return df;
   }

   //valid dates: 1/1/2009, 11/11/2009,
   //invalid dates: 13/1/2009, 1/32/2009
   public static boolean validateDate(String dateString){
      try {
         SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
         df.setLenient(false);
         Date date = df.parse(dateString);
         return true;
      }
      catch(ParseException x) {
         return false;
      }
   }

   public static long howfarInDays(Date date){
      Calendar cal = Calendar.getInstance();
      Date today = cal.getTime();
      long today_ms = today.getTime();
      long target_ms = date.getTime();
      return (target_ms - today_ms)/(1000 * 60 * 60 * 24);
   }
}
