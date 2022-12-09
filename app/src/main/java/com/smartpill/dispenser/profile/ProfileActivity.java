package com.smartpill.dispenser.profile;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.smartpill.dispenser.R;
import com.smartpill.dispenser.database.DBHandler;
import com.smartpill.dispenser.model.User;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    TextView txt_name, txt_email, txt_address, txt_meter_number, txt_neighbourhood, txt_area, txt_municipality;
    DBHandler dbHandler;
    ArrayList<User> userArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle("Profile");

        txt_name = findViewById(R.id.txt_user_name);
        txt_email = findViewById(R.id.txt_user_email);

        dbHandler = new DBHandler(ProfileActivity.this);

        userArrayList = dbHandler.getUser();

        String name = userArrayList.get(0).getName();
        String email = userArrayList.get(0).getEmail();

        txt_name.setText("Name : " + name);
        txt_email.setText("Email : " + email);
    }

    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}