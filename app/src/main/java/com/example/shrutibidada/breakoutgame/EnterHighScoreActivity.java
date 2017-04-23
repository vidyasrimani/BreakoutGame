package com.example.shrutibidada.breakoutgame;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;


public class EnterHighScoreActivity extends AppCompatActivity {

    private TextView name;
    private Button submit;
    private Button noThanks;
    private TextView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final String message = intent.getStringExtra("score");
        setContentView(R.layout.activity_enter_high_score);
        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.highscorelayout);
        Resources res = getResources(); //resource handle
        Drawable drawable = res.getDrawable(R.drawable.leaderboard); //new Image that was added to the res folder
        drawable.setAlpha(150);
        rLayout.setBackground(drawable);
        name = (TextView) findViewById(R.id.nametext);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().trim().equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please give a Name", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    HighScore hs = new HighScore();
                    hs.setName(name.getText().toString().trim());
                    hs.setTime(message);
                    HighScoreUtil.highScores.add(hs);
                    Collections.sort(HighScoreUtil.highScores);
                    submitScore();
                }
            }
        });
        noThanks = (Button) findViewById(R.id.nothanks);
        scoreView = (TextView) findViewById(R.id.scoretext);
        scoreView.setText("You are one of the top scorers!!! Your Score is " + message);
        noThanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();

            }
        });
    }

    public void goToHome() {
        Intent intent = new Intent(this, BreakOutActivity.class);
        startActivity(intent);
    }

    public void submitScore() {
        Intent intent = new Intent(this, LeaderBoardActivity.class);
        startActivity(intent);
    }
}
