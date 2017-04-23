package com.example.shrutibidada.breakoutgame;

import android.graphics.RectF;

class Paddle {
    private RectF rectangle;
    private float x;
    private float y;
    private float speed;

    Paddle(int givenX, int givenY) {
        x = givenX / 2 - 100;
        y = givenY - 500;
        rectangle = new RectF(x, y, x + givenX / 6, y + 50);
        speed = 350;
    }

    private float getX() {
        return x;
    }

    public float gety() {
        return y;
    }

    public void editRectangle(int xPressed, int direction) {
        if (xPressed < rectangle.left && direction == -1) {
            rectangle.left -= 20;
            rectangle.right -= 20;
        } else if (xPressed > rectangle.left && direction == 1) {
            rectangle.left += 20;
            rectangle.right += 20;
        } else {
            GameView.shouldMovePaddle = false;
        }
        this.x = rectangle.left;
    }

    public RectF getRectangle() {
        return rectangle;
    }
}
