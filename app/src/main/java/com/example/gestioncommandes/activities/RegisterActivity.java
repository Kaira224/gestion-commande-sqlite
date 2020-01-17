package com.example.gestioncommandes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestioncommandes.R;
import com.example.gestioncommandes.dao.DatabaseHelper;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    EditText userName;
    EditText password;
    Button registerBtn;
    RadioGroup gender;
    private TextInputLayout userLayout;
    private TextInputLayout passwordLayout;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = new DatabaseHelper(this);

        userName = findViewById(R.id.user);
        password = findViewById(R.id.password);
        registerBtn = findViewById(R.id.registerBtn);
        gender = findViewById(R.id.gender);

        userLayout = findViewById(R.id.userLayout);
        passwordLayout = findViewById(R.id.passwordLayout);

        registerBtn.setEnabled(false);

        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(password.getText().toString().length() == 0) passwordLayout.setError("Donnez un mot de passe");
                if(password.getText().toString().length()!=0){
                    registerBtn.setEnabled(true);
                    registerBtn.setTextColor(Color.parseColor("#FFFFFF"));
                }
                if(userName.getText().toString().length()==0){
                    registerBtn.setEnabled(false);
                    registerBtn.setTextColor(Color.parseColor("#BDB9B9"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(userName.getText().toString().length() == 0) userLayout.setError("Donnez votre User Name");
                if(userName.getText().toString().length()!=0){
                    registerBtn.setEnabled(true);
                }
                if(password.getText().toString().length()==0){
                    registerBtn.setEnabled(false);
                    registerBtn.setTextColor(Color.parseColor("#BDB9B9"));
                }else {
                    passwordLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameUser = userName.getText().toString();
                String passwordUser = password.getText().toString();
                RadioButton userGender = findViewById(gender.getCheckedRadioButtonId());
                String genderUser = userGender.getText().toString();

                ContentValues contentValues = new ContentValues();
                contentValues.put("username", nameUser);
                contentValues.put("password", passwordUser);
                contentValues.put("gender", genderUser);

                databaseHelper.insertUser(contentValues);
                Toast.makeText(RegisterActivity.this, "Sign in with success !", Toast.LENGTH_LONG).show();

                Intent login_intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login_intent);

            }
        });
    }
}
