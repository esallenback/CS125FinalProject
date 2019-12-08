package com.example.cs125finalproject;

import java.util.Random;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {
    private String game = "jotto";
    private int length;
    private String theCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        length = intent.getIntExtra("length", -1);
        String gameType = intent.getStringExtra("gameType");
        EditText enterGuess = findViewById(R.id.guess);
        if (gameType.equals("mastermind")) {
            enterGuess.setInputType(InputType.TYPE_CLASS_NUMBER);

            TableLayout jottoTable = findViewById(R.id.jottoTable);
            TableLayout mastermindTable = findViewById(R.id.mastermindTable);
            mastermindTable.setVisibility(View.VISIBLE);
            jottoTable.setVisibility(View.GONE);
            theCode = generateRandomNumber();
            System.out.println(theCode);
            game = "mastermind";
        } else {
            theCode = generateRandomWord();
        }
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(length); //Filter to 10 characters
        enterGuess.setFilters(filters);
    }

    /**
     * Submit the guess!
     * @param view
     */
    public void submit(View view) {
        EditText guess = findViewById(R.id.guess);
        String text = guess.getText().toString();
        guess.setText("");
        guess.setHint("Make a guess!");

        Checker check = new Checker();
        if (!check.correctLength(text, length)) {
            String toShow = "Guess must be length of " + length + " characters long.";
            Toast toast = Toast.makeText(getApplicationContext(),toShow,Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else if(game.equals("jotto") && !check.noRepeat(text)) {
            String toShow = "Guess can have no repeating letters.";
            Toast toast = Toast.makeText(getApplicationContext(),toShow,Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else if (game.equals("jotto") && !check.isAWord(text)) {
            String toShow = "Guess must be a word in the English dictionary.";
            Toast toast = Toast.makeText(getApplicationContext(),toShow,Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            LinearLayout pastAttempts = findViewById(R.id.pastAttempts);
            View messageChunk = getLayoutInflater().inflate(R.layout.chunk_guess,
                    pastAttempts, false);
            TextView submission = messageChunk.findViewById(R.id.submission);
            submission.setText(text);
            TextView correctLocation = messageChunk.findViewById(R.id.correctSpot);
            int numberRight = 0;
            TextView attemptNum = findViewById(R.id.attempts);
            String number = attemptNum.getText().toString();
            int currentCount = Integer.parseInt(number);
            attemptNum.setText(Integer.toString(currentCount + 1));
            if (game.equals("mastermind")) {
                numberRight = check.numberCorrectMastermind(theCode, text);
                if (numberRight == length) {
                    //WINNER MASTERMIND
                    Intent intent = new Intent(this, WinActivity.class);
                    intent.putExtra("answer", theCode);
                    intent.putExtra("attempts", currentCount + 1);
                    startActivity(intent);
                    finish();
                }
                correctLocation.setText(Integer.toString(numberRight));
                correctLocation.setVisibility(View.VISIBLE);

            }
            if (game.equals("jotto") && check.theGuessIsRight(text, theCode)) {
                //WINNER JOTTO

            }
            TextView correctNumber = messageChunk.findViewById(R.id.correct);
            correctNumber.setText(Integer.toString(check.numberCorrect(text, theCode) -  numberRight));

            pastAttempts.addView(messageChunk, 0);
            messageChunk.setVisibility(View.VISIBLE);
        }
    }
    public void information(View view) {
        final ImageButton info = findViewById(R.id.info);
        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup, null);
        if (game.equals("jotto")) {
            popupView = layoutInflater.inflate(R.layout.popup, null);
        } else {
            popupView = layoutInflater.inflate(R.layout.popup_2, null);
        }
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

            ImageButton btnDismiss = (ImageButton)popupView.findViewById(R.id.dismiss);
            btnDismiss.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    popupWindow.dismiss();
                }});
                Button centerButton = findViewById(R.id.submitButton);
                popupWindow.showAsDropDown(centerButton, 0, 0);
        }
    /**
     * Change color of letter button when clicked to use as notes.
     * @param view
     */
    public void letterClicked(View view) {
        ColorDrawable buttonColor = (ColorDrawable) view.getBackground();
        int colorId = buttonColor.getColor();
        System.out.println(colorId);
        if (colorId == -6697984) { //Green
            view.setBackgroundColor(Color.parseColor("#ffffbb33"));
        } else if (colorId == -17613) { //Orange
            view.setBackgroundColor(Color.parseColor("#ffff4444"));
        } else {
            view.setBackgroundColor(Color.parseColor("#ff99cc00"));
        }
    }
    public String generateRandomWord() {
        return "house";
    }
    public String generateRandomNumber() {
        Random random = new Random();
        String toReturn = "";
        for (int i = 0; i < length; i++) {
            int randomInt = random.nextInt(10);
            String digit = Integer.toString(randomInt);
            toReturn = toReturn + digit;
        }
        return toReturn;
    }
}
