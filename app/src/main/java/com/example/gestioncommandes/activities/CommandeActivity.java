package com.example.gestioncommandes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.gestioncommandes.R;
import com.example.gestioncommandes.dao.DatabaseHelper;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CommandeActivity extends AppCompatActivity {
    EditText libelleCommande;
    EditText codeCommande;
    EditText dateCommande;
    TextInputLayout libLayout;
    TextInputLayout codeLayout;
    TextInputLayout dateLayout;
    Button creeBtn, composeBtn;
    private DatePickerDialog picker;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);

        databaseHelper = new DatabaseHelper(this);

        libelleCommande = findViewById(R.id.libCmd);
        codeCommande = findViewById(R.id.codeCmd);
        dateCommande = findViewById(R.id.dateCmd);
        creeBtn = findViewById(R.id.createBtn);
        composeBtn = findViewById(R.id.compBtn);

        libLayout = findViewById(R.id.libelleLayout);
        codeLayout = findViewById(R.id.codeLayout);
        dateLayout = findViewById(R.id.dateLayout);

        libLayout.setVisibility(View.INVISIBLE);
        codeLayout.setVisibility(View.INVISIBLE);
        dateLayout.setVisibility(View.INVISIBLE);

        creeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                libLayout.setVisibility(View.VISIBLE);
                codeLayout.setVisibility(View.VISIBLE);
                dateLayout.setVisibility(View.VISIBLE);
                libelleCommande.setSelection(libelleCommande.length());
            }
        });

        dateCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CommandeActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            dateCommande.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        }
                    }, year, month, day);
                picker.show();
            }
        });

        composeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (libelleCommande.getText().toString().length() != 0 && codeCommande.getText().toString().length() != 0){
                    libLayout.setError("");
                    if (dateCommande.getText().toString().length() !=1){
                        dateCommande.setText(new SimpleDateFormat("dd/M/yyyy", Locale.getDefault()).format(new Date()));
                    }

                    Integer code = Integer.parseInt(codeCommande.getText().toString());
                    String libelle = libelleCommande.getText().toString();
                    String date = dateCommande.getText().toString();

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("code", code);
                    contentValues.put("libelle", libelleCommande.getText().toString());
                    contentValues.put("date", date);
                    if(databaseHelper.insertCommande(contentValues)){
                        Toast.makeText(getApplicationContext(), "Command Added With success !", Toast.LENGTH_LONG).show();
                        Intent compose_intent = new Intent(getApplicationContext(), CompositionActivity.class);
                        startActivity(compose_intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Already Exists, Add another Command !", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    if (libelleCommande.getText().toString().length() == 0) {
                        libLayout.setError("Veuillez remplir ce champs");
                    }else{
                        codeLayout.setError("Veuillez remplir ce champs");
                }
            }
        });
    }
}
