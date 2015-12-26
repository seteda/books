package com.androidbook.OpenGL;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.util.Log;

public class RegularPolygon 
{
	private float cx, cy, cz, r;
	private int sides;
	
	//coordinate array
	private float[] xarray = null;
	private float[] yarray = null;
	
	//texture arrray
	private float[] sarray = null;
	private float[] tarray = null;
	
	public RegularPolygon(float incx, float incy, float incz, // (x,y,z) center 
			float inr, // radius 
			int insides) // number of sides
	{
		cx = incx;
		cy = incy;
		cz = incz;
		r = inr;
		sides = insides;
		
		xarray = new float[sides];
		yarray = new float[sides];
		
		sarray = new float[sides];
		tarray = new float[sides];
		calcArrays();
		calcTextureArrays();
	}
	private void calcArrays()
	{
		float[] xmarray = this.getXMultiplierArray();
		float[] ymarray = this.getYMultiplierArray();
		
		//calc xarray
		for(int i=0;i<sides;i++)
		{
			float curm = xmarray[i];
			float xcoord = cx + r * curm;
			xarray[i] = xcoord;
		}
		this.printArray(xarray, "xarray");
		
		//calc yarray
		for(int i=0;i<sides;i++)
		{
			float curm = ymarray[i];
			float ycoord = cy + r * curm;
			yarray[i] = ycoord;
		}
		this.printArray(yarray, "yarray");
		
	}
	private void calcTextureArrays()
	{
		float[] xmarray = this.getXMultiplierArray();
		float[] ymarray = this.getYMultiplierArray();
		
		//calc xarray
		for(int i=0;i<sides;i++)
		{
			float curm = xmarray[i];
			float xcoord = 0.5f + 0.5f * curm;
			sarray[i] = xcoord;
		}
		this.printArray(sarray, "sarray");
		
		//calc yarray
		for(int i=0;i<sides;i++)
		{
			float curm = ymarray[i];
			float ycoord = 0.5f +  0.5f * curm;
			tarray[i] = ycoord;
		}
		this.printArray(tarray, "tarray");
	}
	public FloatBuffer getVertexBuffer()
	{
	    int vertices = sides + 1;
	    int coordinates = 3;
	    int floatsize = 4;
	    int spacePerVertex = coordinates * floatsize;
	    
        ByteBuffer vbb = ByteBuffer.allocateDirect(spacePerVertex * vertices);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer mFVertexBuffer = vbb.asFloatBuffer();
        
        //Put the first coordinate (x,y,z:0,0,0)
        mFVertexBuffer.put(cx); //x
        mFVertexBuffer.put(cy); //y
        mFVertexBuffer.put(0.0f); //z
        
        int totalPuts = 3;
        for (int i=0;i<sides;i++)
        {
            mFVertexBuffer.put(xarray[i]); //x
            mFVertexBuffer.put(yarray[i]); //y
            mFVertexBuffer.put(0.0f); //z
            totalPuts += 3;
        }
        Log.d("total puts:",Integer.toString(totalPuts));
		return mFVertexBuffer;
	}
	public FloatBuffer getTextureBuffer()
	{
	    int vertices = sides + 1;
	    int coordinates = 2;
	    int floatsize = 4;
	    int spacePerVertex = coordinates * floatsize;
	    
        ByteBuffer vbb = ByteBuffer.allocateDirect(spacePerVertex * vertices);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer mFTextureBuffer = vbb.asFloatBuffer();
        
        //Put the first coordinate (x,y (s,t):0,0)
        mFTextureBuffer.put(0.5f); //x or s
        mFTextureBuffer.put(0.5f); //y or t
        
        int totalPuts = 2;
        for (int i=0;i<sides;i++)
        {
        	mFTextureBuffer.put(sarray[i]); //x
        	mFTextureBuffer.put(tarray[i]); //y
            totalPuts += 2;
        }
        Log.d("total texture puts:",Integer.toString(totalPuts));
		return mFTextureBuffer;
	}
	public ShortBuffer getIndexBuffer()
	{
		short[] iarray = new short[sides * 3];
        ByteBuffer ibb = ByteBuffer.allocateDirect(sides * 3 * 2);
        ibb.order(ByteOrder.nativeOrder());
        ShortBuffer mIndexBuffer = ibb.asShortBuffer();
        for (int i=0;i<sides;i++)
        {
        	short index1 = 0;
        	short index2 = (short)(i+1);
        	short index3 = (short)(i+2);
        	if (index3 == sides+1)
        	{
        		index3 = 1;
        	}
        	mIndexBuffer.put(index1);
        	mIndexBuffer.put(index2);
        	mIndexBuffer.put(index3);
        	
        	iarray[i*3 + 0]=index1;
        	iarray[i*3 + 1]=index2;
        	iarray[i*3 + 2]=index3;
        }
        this.printShortArray(iarray, "index array"); 
        return mIndexBuffer;
	}
	private float[] getXMultiplierArray()
	{
		float[] angleArray = getAngleArrays();
		float[] xmultiplierArray = new float[sides];
		for(int i=0;i<angleArray.length;i++)
		{
			float curAngle = angleArray[i];
			float sinvalue = (float)Math.cos(Math.toRadians(curAngle));
			float absSinValue = Math.abs(sinvalue);
			if (isXPositiveQuadrant(curAngle))
			{
				sinvalue = absSinValue;
			}
			else
			{
				sinvalue = -absSinValue;
			}
			xmultiplierArray[i] = this.getApproxValue(sinvalue);
		}
		this.printArray(xmultiplierArray, "xmultiplierArray");
		return xmultiplierArray;
	}
	
	private float[] getYMultiplierArray()
	{
		float[] angleArray = getAngleArrays();
		float[] ymultiplierArray = new float[sides];
		for(int i=0;i<angleArray.length;i++)
		{
			float curAngle = angleArray[i];
			float sinvalue = (float)Math.sin(Math.toRadians(curAngle));
			float absSinValue = Math.abs(sinvalue);
			if (isYPositiveQuadrant(curAngle))
			{
				sinvalue = absSinValue;
			}
			else
			{
				sinvalue = -absSinValue;
			}
			ymultiplierArray[i] = this.getApproxValue(sinvalue);
		}
		this.printArray(ymultiplierArray, "ymultiplierArray");
		return ymultiplierArray;
	}
	
	private boolean isXPositiveQuadrant(float angle)
	{
		if ((0 <= angle) && (angle <= 90))
		{
			return true;
		}
		
		if ((angle < 0) && (angle >= -90))
		{
			return true;
		}
		return false;
	}
	
	private boolean isYPositiveQuadrant(float angle)
	{
		if ((0 <= angle) && (angle <= 90))
		{
			return true;
		}
		
		if ((angle < 180) && (angle >= 90))
		{
			return true;
		}
		return false;
	}
	private float[] getAngleArrays()
	{
		float[] angleArray = new float[sides];
		float commonAngle = 360.0f/sides;
		float halfAngle = commonAngle/2.0f;
		float firstAngle = 360.0f - (90+halfAngle);
		angleArray[0] = firstAngle;
		
		float curAngle = firstAngle;
		for(int i=1;i<sides;i++)
		{
			float newAngle = curAngle - commonAngle;
			angleArray[i] = newAngle;
			curAngle = newAngle;
		}
		printArray(angleArray, "angleArray");
		return angleArray;
	}
	private float getApproxValue(float f)
	{
		if (Math.abs(f) < 0.001)
		{
			return 0;
		}
		return f;
	}
	public int getNumberOfIndecies()
	{
		return sides * 3;
	}
	public static void test()
	{
		RegularPolygon triangle = new RegularPolygon(0,0,0,1,3);
	}
	private void printArray(float array[], String tag)
	{
		StringBuilder sb = new StringBuilder(tag);
		for(int i=0;i<array.length;i++)
		{
			sb.append(";").append(array[i]);
		}
		Log.d("hh",sb.toString());
	}
	private void printShortArray(short array[], String tag)
	{
		StringBuilder sb = new StringBuilder(tag);
		for(int i=0;i<array.length;i++)
		{
			sb.append(";").append(array[i]);
		}
		Log.d(tag,sb.toString());
	}
}
