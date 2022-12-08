package com.smartpill.dispenser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.smartpill.dispenser.auth.LoginActivity;
import com.smartpill.dispenser.auth.RegistrationActivity;
import com.smartpill.dispenser.database.DBHandler;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    public static int splashTimeOut = 3000;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        new Handler().postDelayed(() -> {
            checkAuthStatus();
            finish();
        }, splashTimeOut);
    }

    public void checkAuthStatus() {
        dbHandler = new DBHandler(SplashActivity.this);
        boolean authStatus = dbHandler.checkLogin();
        if (authStatus) {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(SplashActivity.this, RegistrationActivity.class);
            startActivity(intent);
        }
    }
}