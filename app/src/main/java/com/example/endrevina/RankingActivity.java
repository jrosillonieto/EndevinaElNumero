package com.example.endrevina;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class RankingActivity extends AppCompatActivity {

    // Model: Record (intents=puntuació, nom)
    class Record {
        public int intents;
        public String file;
        public String nom;

        public Record(String _file, int _intents, String _nom ) {
            file = _file;
            intents = _intents;
            nom = _nom;
        }


    }
    // Model = Taula de records: utilitzem ArrayList
    static ArrayList<Record> records = new ArrayList<Record>();
    private Button btnReturnMenu;

    // ArrayAdapter serà l'intermediari amb la ListView
    ArrayAdapter<Record> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        btnReturnMenu = findViewById(R.id.btnTornar);
        String name = getIntent().getStringExtra("rankingName");
        int attempts = getIntent().getIntExtra("rankingAttempts", 0);
        String fileName = getIntent().getStringExtra("fileName");

        // S'afegueix el nou record
        records.add(new Record(fileName,100000/attempts, name));

        // S'ordena el ranking
        if (records.size()>1){
            Collections.sort(records, new Comparator<Record>(){

                public int compare(Record o1, Record o2)
                {
                    return o2.intents - o1.intents;
                }
            });
        }

        // Inicialitzem l'ArrayAdapter amb el layout pertinent
        adapter = new ArrayAdapter<Record>( this, R.layout.list_item, records )
        {
            @Override
            public View getView(int pos, View convertView, ViewGroup container)
            {

                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if( convertView==null ) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }

                String fileName = getItem(pos).file;
                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.nom)).setText(getItem(pos).nom);
                ((TextView) convertView.findViewById(R.id.intents)).setText(Integer.toString(getItem(pos).intents));
                Uri fileUri = Uri.fromFile(getFile(fileName));
                ((ImageView) convertView.findViewById(R.id.userImage)).setImageURI(fileUri);
                return convertView;
            }

        };

        // busquem la ListView i li endollem el ArrayAdapter
        ListView lv = (ListView) findViewById(R.id.recordsView);
        lv.setAdapter(adapter);

        btnReturnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }

    protected File getFile(String fileName){
        // Guardar a un fitxer
        File path = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(path, fileName);
    }

}