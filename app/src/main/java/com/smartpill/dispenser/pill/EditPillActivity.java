package com.smartpill.dispenser.pill;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.smartpill.dispenser.Constant;
import com.smartpill.dispenser.R;
import com.smartpill.dispenser.database.DBHandler;
import com.smartpill.dispenser.model.Pill;

import java.io.File;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class EditPillActivity extends AppCompatActivity {

    EditText etxtpillName, etxtpillDescription, etxtpillConsumption;
    TextView txtEditpill;
    ImageView imgpill;
    DBHandler dbHandler;
    String mediaPath = "", pillID;
    private ArrayList<Pill> pillData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pill);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle(R.string.pills_details);

        etxtpillName = findViewById(R.id.etxt_edit_pill_name);
        etxtpillDescription = findViewById(R.id.etxt_edit_pill_description);
        etxtpillConsumption = findViewById(R.id.etxt_edit_pill_consumption);
        imgpill = findViewById(R.id.edit_image_pill);
        txtEditpill = findViewById(R.id.txt_edit_pill);

        dbHandler = new DBHandler(EditPillActivity.this);

        pillID = getIntent().getExtras().getString(Constant.PILL_ID);
        getPill(pillID);

        txtEditpill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatepill(pillID);
            }
        });
    }

    public void getPill(String PillID) {
        pillData = dbHandler.getPillsByID(pillID);

        final String pill_id = pillData.get(0).getPillId();
        String name = pillData.get(0).getPillName();
        String description = pillData.get(0).getPillDescription();
        String consumption = pillData.get(0).getPillConsumption();
        String image = pillData.get(0).getPillImage();

        etxtpillName.setText(name);
        etxtpillConsumption.setText(consumption);
        etxtpillDescription.setText(description);

        File imageUrl = new File(image);

        if (image != null) {
            if (image.length() < 3) {
                imgpill.setImageResource(R.drawable.image_placeholder);
            } else {
                Glide.with(EditPillActivity.this)
                        .load(imageUrl)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.image_placeholder)
                        .into(imgpill);
            }
        }
    }

    public void updatepill(String pillID) {
        pillData = dbHandler.getPillsByID(pillID);

        final String pill_id = pillData.get(0).getPillId();
        String name = pillData.get(0).getPillName();
        String description = pillData.get(0).getPillDescription();
        String consumption = pillData.get(0).getPillConsumption();
        String image = pillData.get(0).getPillImage();

        Integer status = dbHandler.updatePill(pill_id, name, description, consumption, image);

        if (status > 0){
        Toasty.success(EditPillActivity.this, "Edited successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditPillActivity.this, PillActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
            finish();
        }
        else {
            Toasty.error(EditPillActivity.this, "Failed updating", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditPillActivity.this, PillActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
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