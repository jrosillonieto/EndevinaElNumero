package com.example.endrevina;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import java.io.File;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private int randomNumber;
    private int attempts;
    private EditText userNumberInput;
    private Button btnPlay;
    static int counter = 0;
    private String fileName;
    private String nameString;

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
                nameString = name.getText().toString();

                if (nameString.isEmpty()){
                    showDialog();
                }else {
                    counter++;
                    String sctm = String.valueOf(SystemClock.currentThreadTimeMillis());
                    fileName = String.valueOf(counter)+ String.valueOf(randomNumber) + sctm +
                            "nickImage.jpg";
                    Log.i("FileName: ", fileName);
                    dispatchTakePictureIntent();

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


    protected File getFile(){
        // Guardar a un fitxer
        File path = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photo = new File(path, fileName);
        return photo;
    }

    private void dispatchTakePictureIntent() {
        Log.i("Funcio", "Entrant");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent

        Log.i("Funcio", "Entrant2");

        // Create the File where the photo should go
        File photoFile = getFile();
        Log.i("Funcio", "Entrant3");
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Log.i("Funcio", "Entrant4");
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.endrevina",
                    photoFile);
            Log.i("Funcio", "Entrant5");
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            Log.i("Funcio", "Entrant6");
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            Log.i("Funcio", "Entrant7");


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Intent i = new Intent(getApplicationContext(), RankingActivity.class);
        i.putExtra("rankingName", nameString);
        i.putExtra("rankingAttempts", attempts);
        i.putExtra("fileName", fileName);
        startActivity(i);

    }

}