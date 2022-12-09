package com.smartpill.dispenser.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smartpill.dispenser.Constant;
import com.smartpill.dispenser.HomeActivity;
import com.smartpill.dispenser.R;
import com.smartpill.dispenser.database.DBHandler;
import com.smartpill.dispenser.utils.BaseActivity;
import com.smartpill.dispenser.utils.Utils;

import es.dmoral.toasty.Toasty;


public class LoginActivity extends BaseActivity {

    EditText etxtEmail, etxtPassword;
    TextView txtLogin;
    SharedPreferences sp;
    Utils utils;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        etxtEmail = findViewById(R.id.etxt_email);
        etxtPassword = findViewById(R.id.etxt_password);
        txtLogin = findViewById(R.id.txt_login);
        utils = new Utils();

        dbHandler = new DBHandler(LoginActivity.this);

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String email = sp.getString(Constant.SP_EMAIL, "");
        String password = sp.getString(Constant.SP_PASSWORD, "");

        etxtPassword.setText(password);
        etxtEmail.setText(email);

        txtLogin.setOnClickListener(v -> {
            String email1 = etxtEmail.getText().toString().trim();
            String password1 = etxtPassword.getText().toString().trim();

            if (email1.isEmpty()) {
                etxtEmail.setError(getString(R.string.please_enter_meternumber));
                etxtEmail.requestFocus();
            } else if (password1.isEmpty()) {
                etxtPassword.setError(getString(R.string.please_enter_password));
                etxtPassword.requestFocus();
            } else {
                if (dbHandler.loginUser(email1, password1)) {
                    Toasty.success(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toasty.error(LoginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
