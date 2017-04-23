package com.example.shrutibidada.breakoutgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton startBtn = (ImageButton) findViewById(R.id.playBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent startGameIntent = new Intent(MainActivity.this, BreakOutActivity.class);

                startActivity(startGameIntent);

            }
        });
    }

}



