package com.example.endrevina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Random random = new Random();
        int randomNumber = random.nextInt(101);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();

        int attemps = 0;
        int duration = Toast.LENGTH_SHORT;


        final Button button = findViewById(R.id.playButton);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                TextInputLayout userInput = findViewById(R.id.numberInput);
                int userNumber = Integer.valueOf(userInput.getEditText().toString());
                if (userNumber < randomNumber){
                    Toast.makeText(context, "El número es mayor que "+ userNumber, duration).show();
                    attemps++;
                }else if(userNumber > randomNumber){
                    Toast.makeText(context, "El número es menor que "+ userNumber, duration).show();
                    attemps++;
                }else{
                    Toast.makeText(context, "Has ganado! Intentos:" +attemps, duration).show();
                }

            }
        });
    }
}