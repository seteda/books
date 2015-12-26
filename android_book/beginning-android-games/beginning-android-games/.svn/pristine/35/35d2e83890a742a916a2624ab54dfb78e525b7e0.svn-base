package com.badlogic.androidgames.framework.math;

import android.util.FloatMath;

public class Vector2 {
    public static float TO_RADIANS = (1 / 180.0f) * (float)Math.PI;
    public static float TO_DEGREES = (1 / (float)Math.PI) * 180;
    public float x, y;
    
    public Vector2() {    
    }       
    
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector2(Vector2 other) {
        this.x = other.x;
        this.y = other.y;
    }
    
    public Vector2 cpy() {
        return new Vector2(x, y);
    }       
    
    public Vector2 set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }
    
    public Vector2 set(Vector2 other) {
        this.x = other.x;
        this.y = other.y;
        return this;
    }
    
    public Vector2 add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }
    
    public Vector2 add(Vector2 other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }
    
    public Vector2 sub(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }
    
    public Vector2 sub(Vector2 other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }
    
    public Vector2 mul(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }
    
    public float len() {               
        return FloatMath.sqrt(x*x + y*y);
    }
    
    public Vector2 nor() {
        float len = len();
        if(len!=0) {
            this.x /= len;
            this.y /= len;
        }
        return this;
    }       
        
    public float angle() {
        float angle = (float)Math.atan2(y, x) * TO_DEGREES;
        if(angle < 0)
            angle += 360;
        return angle;
    }       
    
    public Vector2 rotate(float angle) {
        float rad = angle * TO_RADIANS;
        float cos = FloatMath.cos(rad);
        float sin = FloatMath.sin(rad);
        
        float newX = this.x * cos - this.y * sin;
        float newY = this.x * sin + this.y * cos;
        
        this.x = newX;
        this.y = newY;
        
        return this;
    }

    public float dist(Vector2 other) {
        float distX = this.x - other.x;
        float distY = this.y - other.y;        
        return FloatMath.sqrt(distX*distX + distY*distY);
    }   
    
    public float dist(float x, float y) {
        float distX = this.x - x;
        float distY = this.y - y;        
        return FloatMath.sqrt(distX*distX + distY*distY);
    }   
    
    public float distSquared(Vector2 other) {
        float distX = this.x - other.x;
        float distY = this.y - other.y;        
        return distX*distX + distY*distY;
    }

    public float distSquared(float x, float y) {
        float distX = this.x - x;
        float distY = this.y - y;        
        return distX*distX + distY*distY;
    }       
}