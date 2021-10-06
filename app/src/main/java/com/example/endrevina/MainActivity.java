package com.example.endrevina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Random random = new Random();
        int randomNumber = random.nextInt(101);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();

        AtomicInteger attemps = new AtomicInteger();
        int duration = Toast.LENGTH_SHORT;

        final Button button = findViewById(R.id.playButton);
        button.setOnClickListener(v -> {
            EditText userInput = findViewById(R.id.userInputNumber);
            int userNumber = Integer.parseInt(userInput.getText().toString());

            if (userNumber < randomNumber){
                Toast.makeText(context, "El número es mayor que "+ userNumber, duration).show();
                attemps.getAndIncrement();
            }else if(userNumber > randomNumber){
                Toast.makeText(context, "El número es menor que "+ userNumber, duration).show();
                attemps.getAndIncrement();
            }else{
                Toast.makeText(context, "Has ganado! Intentos:" + attemps, duration).show();
                attemps.getAndIncrement();
            }

        });

    }
}