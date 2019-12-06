package com.example.cs125finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        Intent intent = getIntent();

        String answer = intent.getStringExtra("answer");
        int count = intent.getIntExtra("attempts", -1);
        TextView text = findViewById(R.id.text);
        text.setText("You won! You guessed the number " + answer + " in " + count + " attempts!");
    }
}
