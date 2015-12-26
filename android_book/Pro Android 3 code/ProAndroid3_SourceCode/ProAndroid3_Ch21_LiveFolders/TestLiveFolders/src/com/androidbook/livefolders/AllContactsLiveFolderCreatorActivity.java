package com.androidbook.livefolders;

import com.androidbook.livefolders.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.LiveFolders;

public class AllContactsLiveFolderCreatorActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();
        final String action = intent.getAction();
        
        if (LiveFolders.ACTION_CREATE_LIVE_FOLDER.equals(action)) 
        {
            setResult(RESULT_OK, 
            		createLiveFolder(MyContactsProvider.CONTACTS_URI,
            					"Contacts LF",
            					R.drawable.icon)
            		);
        } else 
        {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
    
    private Intent createLiveFolder(Uri uri, String name, int icon) 
    {
        final Intent intent = new Intent();
        intent.setData(uri);
        intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_NAME, name);
        intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_ICON,
                Intent.ShortcutIconResource.fromContext(this, icon));
        intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_DISPLAY_MODE, 
        				LiveFolders.DISPLAY_MODE_LIST);
        return intent;
    }
}

