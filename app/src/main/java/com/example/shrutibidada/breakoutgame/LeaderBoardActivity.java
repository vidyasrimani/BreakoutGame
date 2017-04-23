package com.example.shrutibidada.breakoutgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;

public class LeaderBoardActivity extends AppCompatActivity {

    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        home = (Button) findViewById(R.id.homebutton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });
        ListView contactListView = (ListView) findViewById(R.id.highscorelist);
        CustomAdapter<HighScore> adapter = new CustomAdapter<HighScore>(this, android.R.layout.simple_list_item_2, HighScoreUtil.highScores);
        Collections.sort(HighScoreUtil.highScores);
        contactListView.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        Collections.sort(HighScoreUtil.highScores);
    }

    public void goToHome() {
        try {
            writeOutputData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, BreakOutActivity.class);
        startActivity(intent);

    }

    public void writeOutputData() throws IOException {
        File file = new File(this.getFilesDir(), "highscores");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        Writer out = null;
        try {
            out = new BufferedWriter(new FileWriter(file));
            for (HighScore highScore : HighScoreUtil.highScores) {
                String line = "";
                line += highScore.getName();
                line += "\t" + highScore.getTime();
                out.write(line + "\n");
            }

            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
