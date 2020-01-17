package com.example.gestioncommandes.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestioncommandes.R;
import com.example.gestioncommandes.dao.DatabaseHelper;
import com.google.android.material.textfield.TextInputLayout;

public class CompositionActivity extends AppCompatActivity {
    Spinner codeArticle;
    EditText qteArticle;
    EditText libArticle;
    Button ajoutBtn, factureBtn;
    TextInputLayout libLayout;
    TextInputLayout qteLayout;
    TextView textView;
    int code_cmd;

    Integer[] code = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30 };
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composition);
        databaseHelper = new DatabaseHelper(this);

        libArticle = findViewById(R.id.libelleArticle);
        qteArticle = findViewById(R.id.qteArticle);
        codeArticle = findViewById(R.id.spinnerArticle);
        ajoutBtn = findViewById(R.id.addBtn);
        factureBtn = findViewById(R.id.factureBtn);
        textView = findViewById(R.id.textView1);

        libLayout = findViewById(R.id.libelleLayout);
        qteLayout = findViewById(R.id.quantiteLayout);

        code_cmd = (int) databaseHelper.lastRow();
        factureBtn.setEnabled(false);

        textView.setText(textView.getText().toString()+code_cmd);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(CompositionActivity.this, android.R.layout.simple_spinner_item, code);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        codeArticle.setAdapter(adapter);
        //codeArticle.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        codeArticle.setSelection(0);

        ajoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (libArticle.getText().toString().length() != 0 && qteArticle.getText().toString().length() != 0){
                    Integer codeA = Integer.parseInt(codeArticle.getSelectedItem().toString());
                    String libelle = libArticle.getText().toString();
                    Integer quantite = Integer.parseInt(qteArticle.getText().toString());

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("code", codeA);
                    contentValues.put("cmd", code_cmd);
                    contentValues.put("libelle", libelle);
                    contentValues.put("quantite", quantite);

                    if(databaseHelper.insertComposition(contentValues)){
                        Toast.makeText(getApplicationContext(), "Article added with success !", Toast.LENGTH_LONG).show();
                        libArticle.setText("");
                        qteArticle.setText("");
                        codeArticle.setSelection(0);
                        factureBtn.setEnabled(true);

                    }else{
                        Toast.makeText(getApplicationContext(), "Already Exists, Add another Article !", Toast.LENGTH_LONG).show();
                    }


                } else
                if (libArticle.getText().toString().length() == 0) {
                    libLayout.setError("Veuillez remplir ce champs");
                }else{
                    qteLayout.setError("Veuillez remplir ce champs");
                }
            }
        });

        factureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facture_intent = new Intent(getApplicationContext(), FactureActivity.class);
                startActivity(facture_intent);
                finish();
            }
        });

    }
}
