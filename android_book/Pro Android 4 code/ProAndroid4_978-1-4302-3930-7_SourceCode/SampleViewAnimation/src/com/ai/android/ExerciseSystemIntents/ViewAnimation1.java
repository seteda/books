/*
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.ai.android.ExerciseSystemIntents;

import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.graphics.Camera;
import android.graphics.Matrix;

public class ViewAnimation1 extends Animation {
	float centerX, centerY;
	Camera cam = new Camera();
    public ViewAnimation1(float cx, float cy)
    {
    	centerX = cx;
    	centerY = cy;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        Log.d("d","width:" + width);
        Log.d("d","height:" + height);
        Log.d("d","pwidth:" + parentWidth);
        Log.d("d","pheight:" + parentHeight);
        setDuration(2500);
        setFillAfter(true);
        setInterpolator(new LinearInterpolator());
        
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
    	applyTransformationNew(interpolatedTime,t);
    }
    
    protected void applyTransformationNew(float interpolatedTime, Transformation t) 
    {
    	//Log.d("d","transform:" + interpolatedTime);
        final Matrix matrix = t.getMatrix();
        cam.save();
        cam.translate(0.0f, 0.0f, (1300 - 1300.0f * interpolatedTime));
        cam.rotateY(360 * interpolatedTime);
        cam.getMatrix(matrix);
        
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
        cam.restore();
    }
}
