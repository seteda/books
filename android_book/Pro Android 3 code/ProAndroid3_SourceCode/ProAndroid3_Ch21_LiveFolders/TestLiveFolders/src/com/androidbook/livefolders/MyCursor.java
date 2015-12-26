package com.androidbook.livefolders;

import android.content.ContentProvider;
import android.database.MatrixCursor;
import android.util.Log;

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
       Log.i("ss", "requery called");
       MatrixCursor mc = MyContactsProvider.loadNewData(mcp);
       this.setInternalCursor(mc);
       return super.requery();
    }
}

