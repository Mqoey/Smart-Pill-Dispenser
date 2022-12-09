package com.smartpill.dispenser.alarm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.smartpill.dispenser.R;
import com.smartpill.dispenser.database.DBHandler;
import com.smartpill.dispenser.pill.PillActivity;
import com.smartpill.dispenser.utils.BaseActivity;

import es.dmoral.toasty.Toasty;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;

public class AddAlarmActivity extends BaseActivity {

    EditText etxtalarmName;
    TimePicker etxtalarmTime;
    TextView txtAddAlarm;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle("Add Alarm");

        etxtalarmName = findViewById(R.id.etxt_alarm_name);
        etxtalarmTime = findViewById(R.id.etxt_time);
        txtAddAlarm = findViewById(R.id.txt_add_alarm);

        dbHandler = new DBHandler(AddAlarmActivity.this);

        txtAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = "00:00";
                String name = etxtalarmName.getText().toString().trim();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    time = etxtalarmTime.getHour() + ":" + etxtalarmTime.getMinute();
                }

                if (name.isEmpty()) {
                    etxtalarmName.setError(getString(R.string.please_enter_pill_name));
                    etxtalarmName.requestFocus();
                } else {
                    dbHandler.addAlarm(name, time);
                    Toasty.success(AddAlarmActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddAlarmActivity.this, AlarmActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
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