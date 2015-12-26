// MainActivity.java

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity
{
    private static final String TAG = "MainActivity";


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Log.d(TAG, "starting service");

        Button bindBtn = (Button)findViewById(R.id.bindBtn);
        bindBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View arg0) {
                startService(new Intent(MainActivity.this,
                        BackgroundService.class));
            }});

        Button unbindBtn = (Button)findViewById(R.id.unbindBtn);
        unbindBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View arg0) {
                stopService(new Intent(MainActivity.this,
                        BackgroundService.class));
            }});

    }
}
