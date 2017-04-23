package com.example.shrutibidada.breakoutgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainOptionsView extends LinearLayout {
    public static ImageButton start;
    public static TextView timer;
    public static ImageButton highScores;
    final Runnable updater = new Runnable() {
        public void run() {
            //call the activity method that updates the UI
            updaterUI();
        }
    };
    private final Handler myHandler = new Handler();
    String timerValue = null;
    final Runnable updateRunnable = new Runnable() {
        public void run() {
            //call the activity method that updates the UI
            updateUI();
        }
    };
    private GameView gameView;


    public MainOptionsView(Context context, Point point, final GameView gameView) {
        super(context);
        final Context myContext = context;
        this.gameView = gameView;
        setBackgroundColor(Color.argb(255, 242, 241, 239));
        setOrientation(HORIZONTAL);
        start = new ImageButton(context);
        start.setImageResource(R.drawable.controls_play_32);
        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                gameView.resetThings();
            }
        });
        timer = new TextView(context);
        timer.setGravity(Gravity.CENTER);
        timer.setTypeface(null, Typeface.BOLD);
        timer.setTextSize(16);
        timer.setText("00:00");
        highScores = new ImageButton(context);
        highScores.setImageResource(R.drawable.high_scores);
        LayoutParams lp = new LayoutParams(point.x / 3, 150);
        start.setLayoutParams(lp);
        addView(start);
        timer.setLayoutParams(lp);
        addView(timer);
        highScores.setLayoutParams(lp);
        highScores.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext, LeaderBoardActivity.class);
                myContext.startActivity(intent);
            }
        });
        addView(highScores);

    }

    private void updateUI() {
        // ... update the UI
        timer.setText(timerValue);
        start.setEnabled(false);
        start.getBackground().setAlpha(70);
        highScores.setEnabled(false);
        highScores.getBackground().setAlpha(70);
        OptionsView.seekBar.setEnabled(false);
    }

    private void updaterUI() {
        // ... update the UI
        GameView.isFirstGame = false;
        GameView.isBallMoving = false;
        start.setEnabled(true);
        start.getBackground().setAlpha(255);
        highScores.setEnabled(true);
        highScores.getBackground().setAlpha(255);
        GameView.isGameOver = true;
        OptionsView.seekBar.setEnabled(true);
    }

    private void doSomeHardWork() {
        //.... hard work

        long millis = System.currentTimeMillis() - GameView.startTime;
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        timerValue = String.format("%02d:%02d", minutes, seconds);

        //update the UI using the handler and the runnable
        myHandler.post(updateRunnable);

    }

    private void doSomeSoftWork() {
        myHandler.post(updater);
    }

    public void startTimerThread() {
        new Thread() {
            @Override
            public void run() {
                while (GameView.isBallMoving)
                    doSomeHardWork();
                doSomeSoftWork();
            }
        }.start();
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }
}
