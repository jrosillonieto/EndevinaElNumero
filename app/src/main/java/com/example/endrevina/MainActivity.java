package com.example.endrevina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Random random = new Random();
        int randomNumber = random.nextInt(11);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();
        CharSequence messageWin = "¡¡Has acertado!!";
        CharSequence messageLose = "Has fallado...";

        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, messageWin, duration);

        final Button button = findViewById(R.id.playButton);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                toast.show();
            }
        });
    }
}