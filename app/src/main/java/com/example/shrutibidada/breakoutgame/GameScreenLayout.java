package com.example.shrutibidada.breakoutgame;

import android.content.Context;
import android.graphics.Point;
import android.widget.LinearLayout;

public class GameScreenLayout extends LinearLayout {

    MainOptionsView mainOptionsView;
    private GameView gameView;
    private OptionsView options;

    public GameScreenLayout(Context context, Point point) {
        super(context);
        LayoutParams lpView = new LayoutParams(LayoutParams.WRAP_CONTENT, point.y - 400);

        options = new OptionsView(context, point);
        gameView = new GameView(context, point, mainOptionsView);
        mainOptionsView = new MainOptionsView(context, point, gameView);
        gameView.setMainViewOptionsView(mainOptionsView);
        mainOptionsView.setGameView(gameView);
        setOrientation(VERTICAL);

        gameView.setLayoutParams(lpView);
        addView(gameView);
        lpView = new LayoutParams(point.x, 150);
        options.setLayoutParams(lpView);
        mainOptionsView.setLayoutParams(lpView);
        addView(mainOptionsView);
        addView(options);
    }

    public void resume() {
        gameView.resume();
    }

    public void pause() {
        gameView.pause();
    }
}
