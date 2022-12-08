package com.smartpill.dispenser.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.smartpill.dispenser.R;
import com.smartpill.dispenser.database.DBHandler;

import es.dmoral.toasty.Toasty;

public class RegistrationActivity extends AppCompatActivity {

    EditText etxtEmail, etxtPassword, etxt_FirstName, etxt_LastName, etxt_ConfirmPassword;
    TextView txtRegister;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().hide();

        etxtEmail = findViewById(R.id.etxt_email1);
        etxtPassword = findViewById(R.id.etxt_password1);
        etxt_FirstName = findViewById(R.id.etxt_firstname);
        etxt_LastName = findViewById(R.id.etxt_lastname);
        etxt_ConfirmPassword = findViewById(R.id.etxt_password_confirmation);
        txtRegister = findViewById(R.id.txt_register1);
        dbHandler = new DBHandler(RegistrationActivity.this);

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = etxtEmail.getText().toString().trim();
                String password1 = etxtPassword.getText().toString().trim();
                String password2 = etxt_ConfirmPassword.getText().toString().trim();
                String first_name1 = etxt_FirstName.getText().toString().trim();
                String last_name1 = etxt_LastName.getText().toString().trim();

                if (first_name1.isEmpty()) {
                    etxt_FirstName.setError(getString(R.string.please_enter_firstname));
                    etxt_FirstName.requestFocus();
                } else if (last_name1.isEmpty()) {
                    etxt_LastName.setError(getString(R.string.please_enter_lastname));
                    etxt_LastName.requestFocus();
                } else if (!email1.contains("@") || !email1.contains(".")) {
                    etxtEmail.setError(getString(R.string.enter_valid_email));
                    etxtEmail.requestFocus();
                } else if (password1.isEmpty()) {
                    etxtPassword.setError(getString(R.string.please_enter_password));
                    etxtPassword.requestFocus();
                } else if (password2.isEmpty()) {
                    etxt_ConfirmPassword.setError(getString(R.string.please_enter_password_confirmation));
                    etxt_ConfirmPassword.requestFocus();
                } else if (!password1.equals(password2)) {
                    etxt_ConfirmPassword.setError(getString(R.string.password_dont_match));
                    etxt_ConfirmPassword.requestFocus();
                } else {
                    dbHandler.registerUser(first_name1 + " " + last_name1, email1, password1);
                    Toasty.success(RegistrationActivity.this, "Registered successfully ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}