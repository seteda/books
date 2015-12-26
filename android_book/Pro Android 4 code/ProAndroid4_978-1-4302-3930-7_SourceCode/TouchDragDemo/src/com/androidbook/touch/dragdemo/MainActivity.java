package com.androidbook.touch.dragdemo;

// This file is MainActivity.java
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	public LinearLayout counterLayout;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        counterLayout = (LinearLayout)findViewById(R.id.counters);
	}
}