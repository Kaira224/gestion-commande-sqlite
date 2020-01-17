package com.example.gestioncommandes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestioncommandes.R;
import com.example.gestioncommandes.dao.DatabaseHelper;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    EditText userName;
    EditText password;
    Button loginBtn;
    TextView registerText;
    private TextInputLayout userLayout;
    private TextInputLayout passwordLayout;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);

        userName = findViewById(R.id.user);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        registerText = findViewById(R.id.registerText);

        userLayout = findViewById(R.id.userLayout);
        passwordLayout = findViewById(R.id.passwordLayout);

        loginBtn.setEnabled(false);
        if(userName.getText().toString().length() == 0)    userLayout.setError("Donnez votre User Name");
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(password.getText().toString().length()==0)   passwordLayout.setError("Saississez votre Mdp");
                userLayout.setError("");

                if(password.getText().toString().length()!=0){
                    loginBtn.setEnabled(true);
                    loginBtn.setTextColor(Color.parseColor("#FFFFFF"));
                }
                if(userName.getText().toString().length()==0){
                    userLayout.setError("Donnez votre User Name");
                    loginBtn.setEnabled(false);
                    loginBtn.setTextColor(Color.parseColor("#BDB9B9"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(password.getText().toString().length() == 0)    passwordLayout.setError("Saississez votre Mdp");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(userName.getText().toString().length() == 0)    userLayout.setError("Donnez votre User Name");

                if(userName.getText().toString().length()!=0){
                    loginBtn.setEnabled(true);
                    loginBtn.setTextColor(Color.parseColor("#FFFFFF"));
                }
                if(password.getText().toString().length()==0){
                    passwordLayout.setError("Saisissez votre User Name");
                    loginBtn.setEnabled(false);
                    loginBtn.setTextColor(Color.parseColor("#BDB9B9"));
                }else {
                    passwordLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(register_intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameUser = userName.getText().toString();
                String passwordUser = password.getText().toString();
                if (databaseHelper.isLoginValid(nameUser,passwordUser)){
                    Toast.makeText(LoginActivity.this,"Login with success", Toast.LENGTH_LONG).show();
                    Intent commande_intent = new Intent(getApplicationContext(), CommandeActivity.class);
                    startActivity(commande_intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Invalid Information, Try again !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
