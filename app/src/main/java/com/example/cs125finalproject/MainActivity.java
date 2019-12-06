package com.example.cs125finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void startGame(View view) {
        Intent i = new Intent(this, GameActivity.class);

        RadioButton jotto = findViewById(R.id.jotto);
        String gameType = "mastermind";
        if (jotto.isChecked()) {
            gameType = "jotto";
        }
        i.putExtra("gameType", gameType);

        SeekBar seekBar = findViewById(R.id.seekBar);
        int value = seekBar.getProgress();
        i.putExtra("length", value);

        startActivity(i);
        finish();
    }
}
