package com.badlogic.androidgames.framework;

public class Color {
    public static int convert (int r, int g, int b, int a) {
        return ((a & 0xff) << 24) |
               ((r & 0xff) << 16) |
               ((g & 0xff) << 8) |
               ((b & 0xff));               
    }
}
