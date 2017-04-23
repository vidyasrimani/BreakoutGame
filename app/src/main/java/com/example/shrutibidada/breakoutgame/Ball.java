package com.example.shrutibidada.breakoutgame;

import android.graphics.RectF;

import java.util.Random;


public class Ball {
    public static int rise = 10;
    public static double run = 4;
    public static int sliderValue = 4;
    public static boolean isTilted = false;
    public static float tiltValue = 0;
    public static boolean sliderValueChanged = false;
    int Low = 0;
    int High = 15;
    Random randomNumberGenerator;
    private float x;
    private float y;
    private float radius;
    private int direction = 0;
    private int screenX;
    private int screenY;
    private GameView view;
    private int upperPoint;
    private double angle = 45.0;


    public Ball(int givenX, int givenY, GameView view) {
        run = 10;
        rise = 10;
        this.view = view;
        this.screenX = givenX;
        this.screenY = givenY;
        this.radius = givenX / 24;
        upperPoint = (3 * screenX / 12);
        this.x = (givenX - 200 + givenX / 6) / 2;
        this.y = givenY - 500 - radius;
        randomNumberGenerator = new Random();
    }


    public float getx() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }


    public void editBallPosition() {
        if (run == 0)
            run = randomNumberGenerator.nextInt(5) + 1;
        if (isTilted) {
            isTilted = false;
            double newRun = Math.tan(Math.toRadians(tiltValue)) * 100;
            if (newRun < 0) {
                x = x + (int) Math.abs(newRun);
            } else {
                x = x - (int) Math.abs(newRun);
            }
            if (x < screenX / 24 || x > screenX - screenX / 24) {
                GameView.isGameOver = true;
                GameView.isBallMoving = false;
                return;
            }
        }
        if (!collisionDetectionAndChangeDirection(direction)) {
            if (x + run > screenX - screenX / 24 || x + run < screenX / 24) {
                int randomAngle = randomNumberGenerator.nextInt(15) - 7;
                double newRun = Math.tan(Math.toRadians(randomAngle));
                newRun += Math.abs(run);
                if (run > 0)
                    run = -newRun;
                else
                    run = newRun;

                this.x = x + (float) run;
            }
            if (y + rise < screenX / 24) {
                rise = -rise;
                int randomAngle = randomNumberGenerator.nextInt(15) - 7;
                double newRun = Math.tan(Math.toRadians(randomAngle));
                newRun += Math.abs(run);
                run = newRun;
                this.x = x + (float) run;
                boolean allBricksGone = true;
                int startCount = 7;
                for (int i = 0; i <= 2; i++) {
                    if (!allBricksGone)
                        break;
                    for (int j = 0; j < startCount; j++) {
                        Brick brick = GameView.bricks[i][j];
                        if (brick.isPresent()) {
                            allBricksGone = false;
                            break;
                        }
                    }
                    startCount++;
                }
                if (allBricksGone) {
                    GameView.isGameWon = true;
                }
            }
            if (y + rise > screenY - 500 - screenX / 24) {
                if (this.x >= view.paddle.getX() - screenX / 24 && this.x <= view.paddle.getX() + screenX / 24 + screenX / 6) {
                    rise = -rise;
                    int randomAngle = randomNumberGenerator.nextInt(15) - 7;
                    double newRun = Math.tan(Math.toRadians(randomAngle));
                    newRun += run;
                    run = newRun;
                    this.x = x + (float) run;
                } else {
                    GameView.isGameOver = true;
                    GameView.isBallMoving = false;
                }
            }
        }
        this.y = y + rise;

    }

    public boolean collisionDetectionAndChangeDirection(int existingDirection) {
        float localX = this.x;
        float localY = this.y;
        localX += run;
        localY += rise;
        int startCount = 7;
        boolean isColliding = false;
        float x = 0, y = 0, right = 0;
        RectF brickRectangle = null;
        for (int i = 0; i <= 2; i++) {
            if (isColliding)
                break;
            for (int j = 0; j < startCount; j++) {
                Brick brick = GameView.bricks[i][j];
                if (brick.isPresent()) {
                    brickRectangle = brick.getBrick();
                    x = brickRectangle.left;
                    y = brickRectangle.top;
                    right = brickRectangle.right;
                    RectF ballRectangle = new RectF(localX - radius, localY - radius, localX + radius, localY + radius);
                    if (brickRectangle.intersect(ballRectangle)) {
                        //brick.setPresent(false);
                        int color = brick.getColor();
                        color--;
                        brick.setColor(color);
                        if (color < 0) {
                            brick.setPresent(false);
                        }
                        brick.setRectangle(x, y, screenX, screenY);
                        if (brickRectangle.right == right && brickRectangle.top == (brick.getBrick().top)) {
                            int randomAngle = randomNumberGenerator.nextInt(15) - 7;
                            double newRun = Math.tan(Math.toRadians(randomAngle));
                            newRun += Math.abs(run);
                            if (run > 0)
                                run = -newRun;
                            else
                                run = newRun;
                            this.x = x + (float) run;
                        } else if (brickRectangle.left == x && brickRectangle.top == (brick.getBrick().top)) {
                            int randomAngle = randomNumberGenerator.nextInt(15) - 7;
                            double newRun = Math.tan(Math.toRadians(randomAngle));
                            newRun += Math.abs(run);
                            if (run > 0)
                                run = -newRun;
                            else
                                run = newRun;
                            this.x = x + (float) run;
                        } else {
                            int randomAngle = randomNumberGenerator.nextInt(15) - 7;
                            double newRun = Math.tan(Math.toRadians(randomAngle));
                            newRun += run;
                            run = newRun;
                            rise = -rise;
                        }
                        isColliding = true;
                        break;
                    }
                }
            }
            startCount++;
        }


        return isColliding;

    }

}



