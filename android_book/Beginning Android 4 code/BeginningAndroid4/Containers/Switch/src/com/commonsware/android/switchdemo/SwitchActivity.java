package com.commonsware.android.switchdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.CompoundButton;

public class SwitchActivity extends Activity 
  implements CompoundButton.OnCheckedChangeListener {
  Switch sw;
  
  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    setContentView(R.layout.main);
    
    sw=(Switch)findViewById(R.id.switchdemo);
    sw.setOnCheckedChangeListener(this);
  }
  
  @Override
  public void onCheckedChanged(CompoundButton buttonView,
                                 boolean isChecked) {
    if (isChecked) {
      sw.setTextOn("This switch is: on");
    }
    else {
      sw.setTextOff("This switch is: off");
    }
  }
}
