package com.androidbook.drag.drop.demo;

// This file is Palette.java
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Palette extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle icicle) {
		View v = inflater.inflate(R.layout.palette, container, false);
		return v;
	}
}
