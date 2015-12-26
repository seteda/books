package com.badlogic.androidgames.framework.gl;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.impl.GLGraphics;

public class Light {
    final float[] ambient;
    final float[] diffuse;
    final float[] position;
    final GLGraphics glGraphics;    
    
    public Light(GLGraphics glGraphics, boolean isDirectional) {
        this.glGraphics = glGraphics;
        ambient = new float[] {0.2f, 0.2f, 0.2f, 1.0f};
        diffuse = new float[] {1.0f, 1.0f, 1.0f, 1.0f};
        position = new float[] {0, 0, 0, isDirectional?0:1};        
    }
    
    public boolean isDirectional() {
        return position[3] == 0;
    }
    
    public void setAmbient(float r, float g, float b) {
        ambient[0] = r;
        ambient[1] = g;
        ambient[2] = b;
    }
    
    public void setDiffuse(float r, float g, float b) {
        diffuse[0] = r;
        diffuse[1] = g;
        diffuse[2] = b;
    }
    
    public void setPosition(float x, float y, float z) {
        position[0] = x;
        position[1] = y;
        position[2] = z;
    }
    
    public void enable(int lightNum) {
        GL10 gl = glGraphics.getGL();
        gl.glEnable(lightNum);
        gl.glLightfv(lightNum, GL10.GL_AMBIENT, ambient, 0);
        gl.glLightfv(lightNum, GL10.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(lightNum, GL10.GL_POSITION, position, 0);
    }       
}
