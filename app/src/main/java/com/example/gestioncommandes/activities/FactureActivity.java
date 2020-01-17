package com.example.gestioncommandes.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestioncommandes.R;
import com.example.gestioncommandes.dao.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class FactureActivity extends AppCompatActivity {
    TextView totalText;
    TextView detailsText;
    TableLayout articlesList;
    Button returnBtn;
    TextView textView;
    DatabaseHelper databaseHelper;
    int code_cmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facture);

        databaseHelper = new DatabaseHelper(this);
        totalText = findViewById(R.id.total);
        detailsText = findViewById(R.id.details);
        articlesList = findViewById(R.id.listArticles);
        returnBtn = findViewById(R.id.cmdBtn);
        textView = findViewById(R.id.textView1);

        code_cmd = (int) databaseHelper.lastRow();

        textView.setText(textView.getText().toString()+code_cmd);
        totalText.setText( totalText.getText().toString() + databaseHelper.totalArticles(code_cmd));
        detailsText.setText(detailsText.getText().toString()+databaseHelper.cmdLibelle(code_cmd));

        ArrayList<HashMap<String, String>> listArticles = databaseHelper.getArticles();

        for (int i = 0; i <listArticles.size() - 1; i++) {
            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            row.setGravity(View.TEXT_ALIGNMENT_CENTER);
            row.setBackground(Drawable.createFromPath("#F0F7F7"));
            row.setPadding(5,5,5,5);

            TextView code = new TextView(this);
            TextView lib = new TextView(this);
            TextView qte = new TextView(this);

            TableRow.LayoutParams param = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1.0f
            );
            code.setLayoutParams(param);
            lib.setLayoutParams(param);
            qte.setLayoutParams(param);

            code.setText(listArticles.get(i).get("code"));
            lib.setText(listArticles.get(i).get("lib"));
            qte.setText(listArticles.get(i).get("qte"));

            row.addView(code);
            row.addView(lib);
            row.addView(qte);

            articlesList.addView(row,i+1);
        }

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent command_intent = new Intent(getApplicationContext(), CommandeActivity.class);
                startActivity(command_intent);
            }
        });
    }
}
