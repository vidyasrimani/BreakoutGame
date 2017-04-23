package com.example.shrutibidada.breakoutgame;

import android.graphics.RectF;

import java.util.Random;


public class Brick {
    Random random = new Random();
    private RectF rectangle;
    private float x;
    private float y;
    private int rowNumber;
    private boolean isPresent;
    private int color;


    public Brick(float x, float y, int rowNumber, int givenX, int givenY, int color, int existingColor) {
        this.x = x;
        this.y = y;
        this.rowNumber = rowNumber;
        color = random.nextInt(5);
        while (color == existingColor) {
            color = random.nextInt(5);
        }
        this.color = color;
        rectangle = new RectF(x, y, (int) (x + (givenX / (7 + rowNumber))), (int) (y + givenX / 12));
        this.isPresent = true;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean isPresent) {
        this.isPresent = isPresent;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setRectangle(float x, float y, int givenX, int givenY) {
        rectangle = new RectF(x, y, (int) (x + (givenX / (7 + rowNumber))), (int) (y + givenX / 12));
    }

    public RectF getBrick() {
        return rectangle;
    }

}
