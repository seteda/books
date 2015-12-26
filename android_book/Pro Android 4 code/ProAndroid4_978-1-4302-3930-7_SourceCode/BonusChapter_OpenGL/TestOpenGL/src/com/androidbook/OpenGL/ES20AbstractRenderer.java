package com.androidbook.OpenGL;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.util.Log;

//filename: AbstractRenderer.java
public abstract class ES20AbstractRenderer implements Renderer
{
   public static String TAG = "ES20AbstractRenderer";
   
   private float[] mMMatrix = new float[16];
   private float[] mProjMatrix = new float[16];
   private float[] mVMatrix = new float[16];
   private float[] mMVPMatrix = new float[16];
   
   private int mProgram;
   private int muMVPMatrixHandle;
   private int maPositionHandle;
   
   public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) 
   {
  	    prepareSurface(gl,eglConfig);
   }
   public void prepareSurface(GL10 gl, EGLConfig eglConfig) 
   {
	   Log.d(TAG,"preparing surface");
       mProgram = createProgram(mVertexShader, mFragmentShader);
       if (mProgram == 0) {
           return;
       }
	   Log.d(TAG,"Getting position handle:aPosition");
       maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
       checkGlError("glGetAttribLocation aPosition");
       if (maPositionHandle == -1) {
           throw new RuntimeException("Could not get attrib location for aPosition");
       }
	   Log.d(TAG,"Getting matrix handle:uMVPMatrix");
       muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
       checkGlError("glGetUniformLocation uMVPMatrix");
       if (muMVPMatrixHandle == -1) {
           throw new RuntimeException("Could not get attrib location for uMVPMatrix");
       }
   }
    public void onSurfaceChanged(GL10 gl, int w, int h) 
    {
 	   Log.d(TAG,"surface changed. Setting matrix frustum: projection matrix");
    	GLES20.glViewport(0, 0, w, h);
        float ratio = (float) w / h;
        Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }
    public void onDrawFrame(GL10 gl) 
    {
   	    Log.d(TAG,"set look at matrix: view matrix");
    	Matrix.setLookAtM(mVMatrix, 0, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
    	
  	    Log.d(TAG,"base drawframe");
        GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
        GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
  	    
        GLES20.glUseProgram(mProgram);
        checkGlError("glUseProgram");
        
        draw(gl,this.maPositionHandle);
    }
    private int createProgram(String vertexSource, String fragmentSource) {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }
   	    Log.d(TAG,"vertex shader created");
        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (pixelShader == 0) {
            return 0;
        }
        Log.d(TAG,"fragment shader created");
        int program = GLES20.glCreateProgram();
        if (program != 0) {
     	   Log.d(TAG,"program created");
            GLES20.glAttachShader(program, vertexShader);
            checkGlError("glAttachShader");
            GLES20.glAttachShader(program, pixelShader);
            checkGlError("glAttachShader");
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
                Log.e(TAG, "Could not link program: ");
                Log.e(TAG, GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
        return program;
    }
    private int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                Log.e(TAG, "Could not compile shader " + shaderType + ":");
                Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }
    private final String mVertexShader =
        "uniform mat4 uMVPMatrix;\n" +
        "attribute vec4 aPosition;\n" +
        "void main() {\n" +
        "  gl_Position = uMVPMatrix * aPosition;\n" +
        "}\n";
    private final String mFragmentShader =
        "void main() {\n" +
        "  gl_FragColor = vec4(0.5, 0.25, 0.5, 1.0);\n" +
        "}\n";
    protected void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }
    protected void setupMatrices()
    {
    	Matrix.setIdentityM(mMMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mMMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mMVPMatrix, 0);
    }
    protected abstract void draw(GL10 gl, int positionHandle);
}
