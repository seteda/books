public class MyCursor extends BetterCursorWrapper
{
   private ContentProvider mcp = null;
   public MyCursor(MatrixCursor mc, ContentProvider inCp)
   {
      super(mc);
      mcp = inCp;
   }
   public boolean requery()
   {
      MatrixCursor mc = MyContactsProvider.loadNewData(mcp);
      this.setInternalCursor(mc);
      return super.requery();
   }
}
