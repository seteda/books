/***
	Copyright (c) 2008-2009 CommonsWare, LLC
	
	Licensed under the Apache License, Version 2.0 (the "License"); you may
	not use this file except in compliance with the License. You may obtain
	a copy of the License at
		http://www.apache.org/licenses/LICENSE-2.0
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/

package com.commonsware.android.containers;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.text.TextWatcher;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.EditText;

public class LinearLayoutDemo extends Activity
	implements RadioGroup.OnCheckedChangeListener {
	RadioGroup orientation;
	RadioGroup gravity;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		
		orientation=(RadioGroup)findViewById(R.id.orientation);
		orientation.setOnCheckedChangeListener(this);
		gravity=(RadioGroup)findViewById(R.id.gravity);
		gravity.setOnCheckedChangeListener(this);
	}
	
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (group==orientation) {
			if (checkedId==R.id.horizontal) {
				orientation.setOrientation(LinearLayout.HORIZONTAL);
			}
			else {
				orientation.setOrientation(LinearLayout.VERTICAL);
			}
		}
		else if (group==gravity) {
			if (checkedId==R.id.left) {
				gravity.setGravity(Gravity.LEFT);
			}
			else if (checkedId==R.id.center) {
				gravity.setGravity(Gravity.CENTER_HORIZONTAL);
			}
			else if (checkedId==R.id.right) {
				gravity.setGravity(Gravity.RIGHT);
			}
		}
	}
}