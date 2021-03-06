package com.example.image_editor.utils;

// simple points with two coordinates
public class DPoint {
    public double x;
    public double y;

    public DPoint() {}

    public DPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public DPoint(DPoint src) {
        this.x = src.x;
        this.y = src.y;
    }
}
