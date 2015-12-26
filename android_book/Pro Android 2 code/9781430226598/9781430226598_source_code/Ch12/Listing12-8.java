public class BetterCursorWrapper implements CrossProcessCursor
{
   //Holds the internal cursor to delegate methods to
   protected CrossProcessCursor internalCursor;

   //Constructor takes a crossprocesscursor as an input
   public BetterCursorWrapper(CrossProcessCursor inCursor)
   {
      this.setInternalCursor(inCursor);
   }

   //You can reset in one of the derived class's methods
   public void setInternalCursor(CrossProcessCursor inCursor)
   {
      internalCursor = inCursor;
   }

   //All delegated methods follow
   public void fillWindow(int arg0, CursorWindow arg1) {
      internalCursor.fillWindow(arg0, arg1);
   }
   // ..... other delegated methods
}

