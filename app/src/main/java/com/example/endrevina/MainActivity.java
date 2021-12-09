package com.example.endrevina;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int randomNumber;
    private int attempts;
    private EditText userNumberInput;
    private Button btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random random = new Random();
        randomNumber = random.nextInt(101);
        Log.i("RandomNumber", String.valueOf(randomNumber));

        btnPlay = findViewById(R.id.playButton);
        userNumberInput = findViewById(R.id.userInputNumber);
        int duration = Toast.LENGTH_SHORT;

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!userNumberInput.getText().toString().isEmpty()) {
                    int userNumberInt = Integer.parseInt(userNumberInput.getText().toString());
                    if (userNumberInt < randomNumber) {
                        Toast.makeText(getApplicationContext(), "El numero és major que " + String.valueOf(userNumberInt), duration).show();
                        attempts++;
                    } else if (userNumberInt > randomNumber) {
                        Toast.makeText(getApplicationContext(), "El numero és menor que " + String.valueOf(userNumberInt), duration).show();
                        attempts++;
                    } else {
                        attempts++;
                        Log.i("GameStatus", "Player Wins!");
                        showDialog();
                    }
                }else{
                    Log.i("UserInput", "Empty Input");
                }
                userNumberInput.setText("");
                Log.i("EditTextNumber", "EditText reset");
            }
        });

        userNumberInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_DONE) {
                    Log.i("EditTextNumber", "Key Enter Pressed");
                    btnPlay.performClick();
                    return true;
                }
                return false;

            }
        });



    }
    private void showDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("¡Has guanyat! Numero: "+randomNumber);
        alert.setMessage("Enhorabona, has encertat a l'intent " + attempts + ". Introdueix el teu nom per enregistrar el record.");
        alert.setCancelable(false);
        EditText name = new EditText(this);
        alert.setView(name);

        alert.setPositiveButton("Registrar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String nameString = name.getText().toString();

                if (nameString.isEmpty()){
                    showDialog();
                }else {
                    Intent i = new Intent(getApplicationContext(), RankingActivity.class);
                    i.putExtra("rankingName", nameString);
                    i.putExtra("rankingAttempts", attempts);
                    startActivity(i);

                }

            }
        });

        alert.setNegativeButton("Cancel·lar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                resetGame();
            }
        });

        alert.show();

    }

    private void resetGame(){
        Random random = new Random();
        randomNumber = random.nextInt(101);
        attempts = 0;
        Log.i("GameStatus", "Game reset, new random number = "+String.valueOf(randomNumber));
    }
}